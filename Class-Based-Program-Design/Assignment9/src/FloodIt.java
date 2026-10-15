import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;


//Represents a single square of the game area
class Cell {
  // In logical coordinates, with the origin at the top-left corner of the screen
  int x;
  int y;
  int w;
  int h;
  Color color;
  boolean flooded;
  // the four adjacent cells to this one
  // Cell left;
  // Cell top;
  // Cell right;
  // Cell bottom;
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
		s.placeImageXY(this.draw(), col * FloodItWorld.BOARD_LENGHT + this.w / 2, row * FloodItWorld.BOARD_LENGHT + this.h / 2);
	}
}


class FloodItWorld extends World {
  // Size of the board
  static int BOARD_LENGHT = 14;
  // Size of the board
  static int BOARD_SIZE = BOARD_LENGHT * BOARD_LENGHT;
  // Utility
  Utility u = new Utility(); 
  // All the cells of the game
  ArrayList<ArrayList<Cell>> board = this.generateBoard();
  FloodItWorld(){ }
  // draws the game
  public WorldScene makeScene() { 
		WorldScene scene = new WorldScene(FloodItWorld.BOARD_SIZE, FloodItWorld.BOARD_SIZE);
		for(ArrayList<Cell> r: this.board)
		{
			for(Cell c: r)
			{
				c.drawAt(c.x, c.y, scene);
			}
		}
		return scene;
  }

  // generate initial board game with random colors 
  // TODO: get the *given* size
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
        Cell cell = new Cell(i % boardLen, (int) Math.floor(i / boardLen),  boardLen,  boardLen, randomColor, false);
        row.add(cell);
        if((i + 1) % boardLen == 0){
          cells.add(row);
          row = new ArrayList<Cell>();
        }
      }
    return cells;
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


class ExamplesGame{
  void testGame(Tester t){
    FloodItWorld g = new FloodItWorld();
    g.bigBang(FloodItWorld.BOARD_SIZE, FloodItWorld.BOARD_SIZE);
  }
}
