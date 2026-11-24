import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;
import java.util.HashMap;


//Represents a single square of the game area
class Cell {
  // In logical coordinates, with the origin at the top-left corner of the screen
  int x;
  int y;
  int w;
  int h;
  Color color;
  boolean flooded;
  Cell(int x, int y, int w, int h, Color color, boolean flooded){
    this.x=x;
    this.y=y;
    this.w=w;
    this.h=h;
    this.color=color;
    this.flooded=flooded;
  }
  public WorldImage draw(){ return new RectangleImage(this.w, this.h, OutlineMode.SOLID, this.color); }
  // Draws this Cell on the background at x, y
  void drawAt(int col, int row, WorldScene s) 
	{
		s.placeImageXY(this.draw(), col + this.w / 2, row + this.h / 2);
	}
  // return the color of the current cell
  Color getColor(){ return this.color; }
}

interface ICollection<T>{ 
  // produce true if the list is empty, false otherwise. 
  boolean isEmpty();
  // add the given element, in the head of the list. 
  void add(T item);
  // remove the first element in the stack, and returns it.
  T remove();
}

class Stack<T> implements ICollection<T>{
  ArrayList<T> items;
  Stack(){ this.items = new ArrayList<T>(); }

  // produce true if the list is empty, false otherwise. 
  public boolean isEmpty(){ return this.items.size() <= 0; }
  // add the given element, in the head of the list. 
  public void add(T item){ this.items.add(0, item); }
  // remove the first element in the stack, and returns it.
  public T remove(){ return this.items.remove(0); }
}

class FloodItWorld extends World {
  // Size of the board
  static int BOARD_LENGHT = 14;
  // Size of the board
  static int BOARD_SIZE = BOARD_LENGHT * BOARD_LENGHT;
  // Size of the wrapper include the board and the text that count the steps.
  static int WRAPPER_SIZE = BOARD_SIZE + 50; 
  // Max steps (moves) on the board
  static int MAX_MOVES = 25;
  // Utility
  Utility u = new Utility(); 
  // All the cells of the game
  ArrayList<ArrayList<Cell>> board = this.generateBoard();
  // Pivot, the game start with the Cell top-left.
  Cell pivot = this.board.get(0).get(0);
  // true if the game is solved; false otherwise.
  boolean winner = false;
  // Counter on how many clicks, the user did untitl finishing the game
  int steps = 0;

  FloodItWorld(){ }

  // draws the game
  // handle the winner, loser scene and each updates of the board (cells) on every click.
  public WorldScene makeScene() { 
		WorldScene scene = new WorldScene(FloodItWorld.BOARD_SIZE, FloodItWorld.BOARD_SIZE);
    if(this.winner){
      scene.placeImageXY(new TextImage("You Win", Color.black), BOARD_SIZE / 2, BOARD_SIZE / 2);
    }else if(this.steps > FloodItWorld.MAX_MOVES){
      scene.placeImageXY(new TextImage("You Lost, press 'r' to try again.", Color.black), BOARD_SIZE / 2, BOARD_SIZE / 2);
    }else{
      for(ArrayList<Cell> r: this.board)
      {
        for(Cell c: r)
        {
          c.drawAt(c.x, c.y, scene);
        }
      }
      
      scene.placeImageXY(new TextImage("Steps: " + this.steps, Color.black), BOARD_SIZE / 2, BOARD_SIZE + 25);
    }
		return scene;
  }

  // handle the logic at each click on the board
  // EFFECT: mutate the current pivot, add 1 to the steps if the clicked cell has a different color of the pivot
	public void onMouseClicked(Posn pos){
     // What about the ones that are already a pivot in the *past* (It's useless because they have the same color of the one that is the pivot.)
    Cell clickedCell = this.getCell(pos);
    // It's the same, we don't want to do nothing.
    if(clickedCell.getColor() == this.pivot.getColor()) return;
    // The clicked cell becomes the pivot
    this.pivot = clickedCell;
    this.steps = this.steps + 1;
    dfs();  
    return;
  }

  // handle the logic when we press a key,
  // in this case "r" for restarting the game.
  // EFFECT: mutate the baord with a new one, reset winner to false and steps to zero
	public void onKeyEvent(String key) {
		 if (key.equals("r")) {
      this.board = this.generateBoard();
      this.winner = false;
      this.steps = 0;
		 }
	}

  // produce the length of the board.
  int getBoardLength(){ return BOARD_LENGHT; }

  // produce the length of the board.
  int getBoardSize(){ return BOARD_SIZE; }

  // generate initial board game with random colors 
	ArrayList<ArrayList<Cell>> generateBoard()
	{
     ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
     ArrayList<Cell> row = new ArrayList<Cell>();
     int boardLen = FloodItWorld.BOARD_LENGHT;
     int boardSize = FloodItWorld.BOARD_SIZE;
      for(int i = 0;
      i < boardSize;
      i = i + 1)
      {
        Color randomColor = this.u.getRandomColor();
        Cell cell = new Cell((i % boardLen) * boardLen, (int) Math.floor(i / boardLen) * boardLen,  boardLen,  boardLen, randomColor, false);
        row.add(cell);
        // When we fill fully a row in case is 14 * 14, then i +1 (beacuse of the index) % 14 (size) it will be equal 0.
        if((i + 1) % boardLen == 0){
          cells.add(row);
          row = new ArrayList<Cell>();
        }
      }
    return cells;
	 }

  // produce the index from the given x
  int computeRow(int x) { return (int) Math.floor(x / BOARD_LENGHT); }
  // produce the index from the given y
  int computeCol(int y) { return (int) Math.floor(y / BOARD_LENGHT); }
  // produce the Cell at the given [pos]ition, if it doens't exist return null;
  Cell getCell(Posn pos){
    if(pos.x < 0  || pos.x >= BOARD_SIZE || pos.y < 0 || pos.y >= BOARD_SIZE) return null;
    int rowIDX = computeRow(pos.x);
    int colIDX = computeCol(pos.y);
    return this.board.get(colIDX).get(rowIDX);
  }

  // start from the top-left, cell in the game and check if the adjcent cells have the pivot color,
  // if we didn't saw the cells before then we add them in the worklist, 
  // otherwise we add it in the hashmap alreadySaw.
  // EFFECT: If we saw all the cell in the board we mutate winner to true.
  void dfs(){
    // Cell that will represent the initial worklist,
    // always start from (0,0) or in other words 
    // the first cell in the list. (initialCell)
    Stack<Cell> worklist = new Stack<Cell>();
    Cell initialCell = this.getCell(new Posn(0,0));
    // The initial cell, start always with a flooded= true;
    initialCell.flooded = true;
    worklist.add(initialCell);
    Color pivotColor = this.pivot.getColor();
    HashMap<Cell, Boolean> alreadySaw = new HashMap<Cell, Boolean>();
    
    while(!worklist.isEmpty()){
      Cell currentCell = worklist.remove();
      Color prevColor  = currentCell.getColor();
      currentCell.color = pivotColor;
      // X and Y here doesn't rapresent the current pos of the click by the mouse
      // but are the initial position of the cell, when they are positioned in the board.
      // E.g
      // the first cell, will have a x and y = (0,0) that represent the top left.
      // WIth this information we can easily get the adjacent cells!
      int x = currentCell.x;
      int y = currentCell.y;

      Cell lftCell = getCell(new Posn(x - 1, y));
      Cell topCell = getCell(new Posn(x, y - 1));
      Cell rgtCell = getCell(new Posn(x + BOARD_LENGHT, y));
      Cell btmCell = getCell(new Posn(x, y + BOARD_LENGHT));

      ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
      adjacentCells.add(lftCell);
      adjacentCells.add(topCell);
      adjacentCells.add(rgtCell);
      adjacentCells.add(btmCell);

      // Add new cells in the worklist,
      for (Cell c: adjacentCells){
        //  if they have the same color as the pivot 
        //  OR 
        //  if the cell was already flooded
        if(c != null && (c.getColor() == pivotColor || c.getColor() == prevColor) && !alreadySaw.containsKey(c)){ 
          // change the adjacent cell with the new pivot color (if needed, sometimes, we are just *conquering new ones with the same color*)
          // c.color = pivotColor;
          // c.flooded = true;
          alreadySaw.put(c, true);
          // add it in the worklist
          worklist.add(c); 
        }
      }
      // If we saw all the cell in the board, then there are no left.
      if(alreadySaw.size() == BOARD_SIZE) {
        this.winner = true;
      }
    }
  }
}

class Utility{
  static ArrayList<Color> colors = new Utility().generateColors();
  Utility(){}
  ArrayList<Color> generateColors(){
    ArrayList<Color> colors = new ArrayList<Color>();
    colors.add(Color.CYAN); colors.add(Color.RED); colors.add(Color.MAGENTA);
    colors.add(Color.YELLOW); colors.add(Color.BLUE); colors.add(Color.PINK);
    return colors; 
  }

  Color getRandomColor(){
    int colorSize = colors.size();
    int colorIdx = new Random().nextInt(colorSize);
    Color color = colors.get(colorIdx);
    return color;
  }
}


class ExamplesUtility{
  ArrayList<Color> colors;
  Utility u;

  void initData(){
    this.u = new Utility();
    this.colors = u.generateColors();
  }

  void testGenerateColors(Tester t){
    this.initData();
    // generate an array of colors.
    t.checkExpect(u.generateColors(), this.colors);
  }

  void testRandomColor(Tester t){
    this.initData();
    // get a ranomd color from the colors array
    t.checkOneOf(u.getRandomColor(), Color.CYAN, Color.RED,Color.MAGENTA, Color.BLUE, Color.YELLOW, Color.PINK);
  }

}


class ExamplesCell {
  Cell c1; 
  Cell c2; 
  Cell c3; 

  void initData(){
    this.c1 = new Cell(  0,   0, 14, 14, Color.GREEN, false);
    this.c2 = new Cell( 84,  97, 14, 14, Color.YELLOW, false);
    this.c3 = new Cell(182, 195, 14, 14, Color.RED, false);
  }

  void testGetColor(Tester t){
    this.initData();
    t.checkExpect(this.c1.getColor(),  Color.GREEN);
    t.checkExpect(this.c2.getColor(), Color.YELLOW);
    t.checkExpect(this.c3.getColor(),    Color.RED);
  }
}


class ExamplesStack {
  Stack<Cell> s1; 
  Stack<Cell> s2; 
  Stack<Cell> s3; 

  Cell c1; 
  Cell c2; 
  Cell c3; 

  void initData(){
    this.c1 = new Cell(  0,   0, 14, 14, Color.GREEN, false);
    this.c2 = new Cell( 84,  97, 14, 14, Color.YELLOW, false);
    this.c3 = new Cell(182, 195, 14, 14, Color.RED, false);

    // Empty stack
    this.s1 = new Stack<Cell>();
    // Stack with one Cell
    this.s2 = new Stack<Cell>();
    s2.add(this.c1);
    // Stack with more than one Cell
    this.s3 = new Stack<Cell>();
    s3.add(this.c1); s3.add(this.c2); s3.add(this.c3);
  }

  void testStack(Tester t){
    this.initData();
    // Testing the stack without element
    t.checkExpect(this.s1.isEmpty(),    true);
    t.checkException(new IndexOutOfBoundsException("Index 0 out of bounds for length 0"), this.s1, "remove");
    this.s1.add(this.c1);
    t.checkExpect(this.s1.isEmpty(),    false);
    t.checkExpect( this.s1.remove(),  this.c1);
    t.checkExpect(this.s1.isEmpty(),    true);

    // Testing the stack with ONE element
    t.checkExpect( this.s2.remove(),  this.c1);
    t.checkExpect(this.s2.isEmpty(),     true);

    this.s2.add(this.c2);
    t.checkExpect(this.s2.isEmpty(),    false);
    t.checkExpect( this.s2.remove(),  this.c2);

    this.s2.add(this.c3);
    this.s2.add(this.c2);
    this.s2.add(this.c1);
    t.checkExpect(this.s2.isEmpty(),    false);
    t.checkExpect( this.s2.remove(),  this.c1);
    t.checkExpect( this.s2.remove(),  this.c2);
  }
}


class ExamplesGame{

  void testComputeX(Tester t){
    FloodItWorld g = new FloodItWorld();
    // how many el are in a row  and col
    int boardLength = g.getBoardLength();
    // total element in rows and col
    int boardSize = g.getBoardSize();

    // NOTE: 
    // In the test below, we have two differents "-1".
    // 1. For the position since we start from (0,0)
    // 2. For the index. (the ones in checkExpect)

    // Clicking on the first cell (top-left)
    // The position produced after clicking on the half of the first cell
    int middleX = (boardLength - 1) / 2;
    // The position produced after clicking on the right edge of the first cell
    int endX = boardLength - 1;
    // Testing row
    t.checkExpect(g.computeRow(0), 0);
    t.checkExpect(g.computeRow(middleX), 0);
    t.checkExpect(g.computeRow(endX), 0);
    // Testing col
    t.checkExpect(g.computeCol(0),       0);
    t.checkExpect(g.computeCol(middleX), 0);
    t.checkExpect(g.computeCol(endX),    0);

    // Clicking on the middle index
    // The position produced after clicking on left edge of the middle cell
    int middleStartX = boardLength * (boardLength  / 2) - boardLength;
    // The position produced after clicking on the right edge of the middle cell
    int middleHalfX = middleStartX + ((boardLength - 1) / 2);
    // The position produced after clicking on the right edge of the middle cell
    int middleEndX = (boardLength) * (boardLength  / 2) - 1;
    // Testing row
    t.checkExpect(g.computeRow(middleStartX), (boardLength  / 2) - 1);
    t.checkExpect(g.computeRow(middleHalfX),  (boardLength  / 2) - 1);
    t.checkExpect(g.computeRow(middleEndX),   (boardLength  / 2) - 1);
    // Testing col
    t.checkExpect(g.computeCol(middleStartX), (boardLength  / 2) - 1);
    t.checkExpect(g.computeCol(middleHalfX),  (boardLength  / 2) - 1);
    t.checkExpect(g.computeCol(middleEndX),   (boardLength  / 2) - 1);

    // Clicking on the last index
    // The position produced after clicking on left edge of the middle cell
    int lastStartX = (boardSize - boardLength);
    // The position produced after clicking on the right edge of the middle cell
    int lastHalfX = lastStartX + ((boardLength  - 1) / 2);
    // The position produced after clicking on the right edge of the middle cell
    int lastEndX = boardSize - 1;
    // Testing row
    t.checkExpect(g.computeRow(lastStartX), boardLength - 1);
    t.checkExpect(g.computeRow(lastHalfX),  boardLength - 1);
    t.checkExpect(g.computeRow(lastEndX),   boardLength - 1);
    // Testing col
    t.checkExpect(g.computeCol(lastStartX), boardLength - 1);
    t.checkExpect(g.computeCol(lastHalfX),  boardLength - 1);
    t.checkExpect(g.computeCol(lastEndX),   boardLength - 1);

  }
  void testGame(Tester t){
    FloodItWorld g = new FloodItWorld();
    g.bigBang(FloodItWorld.BOARD_SIZE, FloodItWorld.WRAPPER_SIZE);
  }
}
