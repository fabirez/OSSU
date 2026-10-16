import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.Random;

interface IPred<T>
{
	boolean apply(T t);
}
class OnlyOdd implements IPred<Integer>{ public boolean apply(Integer number) { return number % 2 != 0; } }
class OnlyEven implements IPred<Integer>{ public boolean apply(Integer number) { return number % 2 == 0; } }
class OnlyNickname implements IPred<String>{ public boolean apply(String s) { return s.length() <= 3; } }

// Represents an individual tile
class Tile {
  // The number on the tile.  
	// Use 0 to represent the hole
  int value;
	// Width of the tile
  int WIDTH=50;
	// Height of the tile
  int HEIGHT=50;
	// Col of the tile (0, 3)
	int col;
	// Row of the tile (0, 3)
	int row;

	Tile(int value, int col, int row)
	{
		this.value = value; 
		this.col = col;
		this.row = row;
	}

	WorldImage draw()
	{
		if(this.value == 0){
			return new RectangleImage(this.WIDTH, this.HEIGHT, OutlineMode.OUTLINE, Color.CYAN).movePinhole(-this.WIDTH / 2, -this.HEIGHT / 2);
		}
		return new OverlayImage(
			new TextImage(String.valueOf(this.value), Math.min(this.WIDTH, this.HEIGHT) * 0.25 , Color.RED),
			new RectangleImage(this.WIDTH, this.HEIGHT, OutlineMode.OUTLINE, Color.BLACK)
		).movePinhole(-this.WIDTH / 2, -this.HEIGHT / 2);
	}

  // Draws this tile onto the background at the specified logical coordinates
  // void drawAt(int col, int row, WorldImage background, WorldScene s) 
  void drawAt(int col, int row, WorldScene s) 
	{
		// return this.draw().movePinhole(new Posn(col * 100, row * 100));
		s.placeImageXY(this.draw(), col * this.WIDTH, row * this.HEIGHT);
	}
}
 
class FifteenGame extends World {
	// Width of the game
	int WIDTH=500;
	// Height of the game
	int HEIGHT=500;
  // represents the idx of hole
	int idxHole = 15;
  // represents the rows of tiles
  ArrayList<ArrayList<Tile>> tiles;
	// TODO: move this somewhere else
	// rightEdgesIdx
	ArrayList<Integer> rightIdxs;
	// rapresent the availables moves
	ArrayList<Integer> availableMoves;
	ArrayList<ArrayList<Integer>> presets=this.generatePresets();

	FifteenGame()
	{
		this.tiles = this.generateBoard();
		this.rightIdxs = this.generateRightEdgesIdx();
		this.availableMoves = this.possibleMoves();
	}

	// produce an array containing all the indexes on the right edge of the board.
	ArrayList<Integer> generateRightEdgesIdx()
	{
		ArrayList<Integer> newArr = new ArrayList<Integer>();
		newArr.add(3);
		newArr.add(7);
		newArr.add(11);
		newArr.add(15);
		return newArr;
	}



	// produce an array of integer, that rapresents the values of the tils.
	// ArrayList<Integer> generatePresetOfValues()
	// {
	// 	ArrayList<Integer> res =  new ArrayList<Integer>();
	// 	for(
	// 	int i = 1;
	// 	i <= 16;
	// 	i = i + 1)
	// 	{
	// 		res.add(i);
	// 	}
	// 	return res;
	// }


	// presets of preset
	// a preset is just an arrray of integer, that rapresent the value of the tile
	ArrayList<ArrayList<Integer>> generatePresets()
	{
		ArrayList<ArrayList<Integer>> vals = new ArrayList<ArrayList<Integer>>();

		ArrayList<Integer> val1 = new ArrayList<Integer>();
		val1.add(13);  val1.add(2); val1.add(10); val1.add(3);
		 val1.add(1); val1.add(12);  val1.add(8); val1.add(4);
		 val1.add(5);  val1.add(0);  val1.add(9); val1.add(6);
		val1.add(15); val1.add(14); val1.add(11); val1.add(7);

		ArrayList<Integer> val2 = new ArrayList<Integer>();
		 val2.add(6); val2.add(13);  val2.add(7); val2.add(10);
		 val2.add(8);  val2.add(9); val2.add(11);  val2.add(0);
		val2.add(15);  val2.add(2); val2.add(12);  val2.add(5);
		val2.add(14);  val2.add(3);  val2.add(1);  val2.add(4);

		ArrayList<Integer> val3 = new ArrayList<Integer>();
		val3.add(12); val3.add(1); val3.add(2); val3.add(5);
		val3.add(11); val3.add(6); val3.add(5); val3.add(8);
		val3.add(7); val3.add(10); val3.add(9); val3.add(4);
		val3.add(0); val3.add(13); val3.add(14); val3.add(3);

		vals.add(val1); vals.add(val2); vals.add(val3);
		return vals;
	}

	ArrayList<ArrayList<Tile>> generateBoard()
	{
		ArrayList<ArrayList<Tile>> res =  new ArrayList<ArrayList<Tile>>();
		// Choose a random preset 
		ArrayList<Integer> preset = this.presets.get(new Random().nextInt(3));

		ArrayList<Tile> row = new ArrayList<Tile>();

		for(int i = 0;
		i < preset.size();
		i = i + 1)
		{
			int val = preset.get(i);
			row.add(new Tile(val, i % 4, (int) Math.floor(i / 4)));
			if(val == 0){ this.idxHole = i; }
			if((i + 1) % 4 == 0){
				res.add(row);
			  row = new ArrayList<Tile>();
			}
		}
		return res;
	}

  // draws the game
  public WorldScene makeScene() { 
		WorldScene scene = new WorldScene(this.WIDTH, this.HEIGHT);
		for(ArrayList<Tile> r: this.tiles)
		{
			for(Tile t: r)
			{
				t.drawAt(t.col, t.row, scene);
			}
		}
		return scene;
	}

	// produce the edge of the give indx.
	// (the most right idx tile)
	int getEdge(int currIdx)
	{
		boolean flag = false;
		int theEdge = 0;
		for(int edge: this.rightIdxs)
		{
			if(flag == false && edge - currIdx >= 0){
				flag = true;
				theEdge = edge;
			}
		}
		return theEdge;
	}

	int generateTopIdx(int currIdx)
	{
		int edge = getEdge(currIdx);
		// How far away from the edge curr is
		// It's the same af all the edges of the board.
		// So if we need to move 1 up, and we are 2 tiles away.
		// We can subtract the edge of the above row by 2,
		// and get the tile above us.
		int diff =  edge - currIdx;
		// The number row, is the same
		// as the index of the arrayOfEdges (rightIdxs).
		// So, gettin the number row and subtracting 1 
		// we go up, and take also the curr edge of the row.
		int numberRow = this.rightIdxs.indexOf(edge);
		// Go in the up row
		int upRow = numberRow - 1;
		if(upRow < 0){
			return -1;
		}
		// Get the new edge of this row (upRow)
		int upEdge = this.rightIdxs.get(upRow);
		// use the diff of before, to go again to the initial position.
		int up = upEdge - diff;
		// Return the position above the hole.
		return up;
	}

	int generateBotIdx(int currIdx)
	{
		int edge = getEdge(currIdx);
		// How far away from the edge curr is
		// It's the same af all the edges of the board.
		// So if we need to move 1 up, and we are 2 tiles away.
		// We can subtract the edge of the above row by 2,
		// and get the tile above us.
		int diff =  edge - currIdx;
		// The number row, is the same
		// as the index of the arrayOfEdges (rightIdxs).
		// So, gettin the number row and subtracting 1 
		// we go up, and take also the curr edge of the row.
		int numberRow = this.rightIdxs.indexOf(edge);
		// Go in the up row
		int downRow = numberRow + 1;
		if(downRow > 3){
			return -1;
		}
		// Get the new edge of this row (upRow)
		int downEdge = this.rightIdxs.get(downRow);
		// use the diff of before, to go again to the initial position.
		int down = downEdge - diff;
		// Return the position above the hole.
		return down;
	}


	// After every move (swichting the tile with the hole)
	// compute the indexes of the Tile, 
	// that can switch position with the hole
	ArrayList<Integer> possibleMoves(){
		int rightEdge = getEdge(this.idxHole);
		int leftEdge = rightEdge - 3;

		int  leftIdx = idxHole - 1;
		int rightIdx = idxHole + 1;

		int topIdx = generateTopIdx(idxHole);
		int botIdx = generateBotIdx(idxHole);

		ArrayList<Integer> movesIdx = new ArrayList<Integer>();
		// We will use -1 for representing, an unvailable move.
		// This gives us the power of using the indexes as reference for
		// the new position.
		// [left, right, top, bot]
		if(leftIdx >= leftEdge){
			movesIdx.add(leftIdx);
		}else{
			movesIdx.add(-1);
		}

		if(rightIdx <= rightEdge){
			movesIdx.add(rightIdx);
		}else{
			movesIdx.add(-1);
		}

		// Top and Bot idx, become -1 
		// in the generate functions above.
		movesIdx.add(topIdx);
		movesIdx.add(botIdx);

		return movesIdx;
	}
	
	// produce an array rapresenting the row and the idx in that row,
	// based on the given idx (0, 15) 
	public ArrayList<Integer> getRowAndIdx(int thatIdx)
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		double x = (float)thatIdx / 4;
		int row  = (int) Math.floor(x);
		double dec = x - Math.floor(x);
		int idx = (int) (dec * 4);

		arr.add(row);
		arr.add(idx);
		return arr;
	}

	// swap the given tile with the hole in the board (tiles)
	public void swap(int selectedTileIdx){
		ArrayList<Integer> tilePos = getRowAndIdx(selectedTileIdx);
		int tileRow = tilePos.get(0);
		int tileCol = tilePos.get(1);

		ArrayList<Integer> holePos = getRowAndIdx(this.idxHole);
		int holeRow = holePos.get(0);
		int holeCol = holePos.get(1);

		Tile selectedTile = this.tiles.get(tileRow).get(tileCol);

		Tile newTile = new Tile(selectedTile.value, holeCol, holeRow);
		Tile newHole = new Tile(0, tileCol, tileRow);

		this.tiles.get(tileRow).set(tileCol, newHole);
		this.tiles.get(holeRow).set(holeCol, newTile);

		this.idxHole = selectedTileIdx;
	}

  // handles keystrokes
  public void onKeyEvent(String k) {
    // needs to handle up, down, left, right to move the hole
    // extra: handle "u" to undo moves

		// [left, right, up, down]
		//   0     1      2    3  
		if(k.equals("left") && this.availableMoves.get(0) != -1){
			int selectedTile = this.availableMoves.get(0);
			// Swap the hole with the current tile
			swap(selectedTile);
		}

		if(k.equals("right") && this.availableMoves.get(1) != -1){
			int selectedTile = this.availableMoves.get(1);
			// Swap the hole with the current tile
			swap(selectedTile);
		}

		if(k.equals("up") && this.availableMoves.get(2) != -1){
			int selectedTile = this.availableMoves.get(2);
			// Swap the hole with the current tile
			swap(selectedTile);
		}

		if(k.equals("down") && this.availableMoves.get(3) != -1){
			int selectedTile = this.availableMoves.get(3);
			// Swap the hole with the current tile
			swap(selectedTile);
		}

		// Compute the new possible moves
		this.availableMoves =	possibleMoves();
		// Draw them on the screen
		if(this.checkVictory()){
			this.endOfWorld("Game over");
		}else{
			makeScene();
		}
  }

	public boolean checkVictory(){
		for(
		int y = 0;
		y < 4;
		y = y + 1)
		{
			for(
			int x = 0; 
			x < 4; 
			x = x + 1)
			{
				// Calculate the val of the tile.
				// Based on the current row and col,
				// adding 1, beacuse of the index
				// and multiply by 4 since, we got only 4 rows.
				int val = 4 * y + x + 1;
				if(val == 16) { val = 0; }
				if(val != this.tiles.get(y).get(x).value) { return false; }
			}
		}
		return true;
	}
}

class Utility{
	Utility(){}
	// produce a new ArrayList<T> containing all the items of the given list that pass the predicate.
	// Use a for-each loop or a counted-for loop, whichever seems easier. (Try both!)
	<T> ArrayList<T> filter(ArrayList<T> arr, IPred<T> pred)
	{
		ArrayList<T> newArr = new ArrayList<T>();
		for(T t: arr)
		{
			if(pred.apply(t)){ newArr.add(t); }
		}
		return newArr;
	}
	// counted-for loop implementation
	<T> ArrayList<T> filter2(ArrayList<T> arr, IPred<T> pred)
	{
		ArrayList<T> newArr = new ArrayList<T>();
		for(int i = 0;
		i < arr.size();
		i = i + 1)
		{
			T el = arr.get(i);
			if(pred.apply(el)){ newArr.add(el); }
		}
		return newArr;
	}
}

class ExamplesUtility{

	ArrayList<Integer> aN;
	ArrayList<String> aS;

	void initData(){
		// array of numbers (integer)
		this.aN = new ArrayList<Integer>();
		this.aN.add(1); this.aN.add(2); this.aN.add(3);
		this.aN.add(4); this.aN.add(5); this.aN.add(6);

		// array of strings (names)
		this.aS = new ArrayList<String>();
		this.aS.add("Bob");
		this.aS.add("Anna");
		this.aS.add("Clairo");
		this.aS.add("Dexter");
		this.aS.add("Ester");
		this.aS.add("Filip");
	}

	void testFilter(Tester t){
		this.initData();
			Utility u = new Utility();

			IPred<Integer> oD = new OnlyOdd();
			IPred<Integer> oE = new OnlyEven();
			IPred<String> oN = new OnlyNickname();


			ArrayList<Integer> aN_ = new ArrayList<Integer>();	
			aN_.add(1); aN_.add(3); aN_.add(5);
			t.checkExpect(u.filter(this.aN, oD), aN_);

			ArrayList<Integer> aN_1 = new ArrayList<Integer>();	
			aN_1.add(2); aN_1.add(4); aN_1.add(6);
			t.checkExpect(u.filter(this.aN, oE), aN_1);

			ArrayList<String> aS_ = new ArrayList<String>();	
			aS_.add("Bob");
			t.checkExpect(u.filter(this.aS, oN), aS_);
	}
}




class ExampleFifteenGame {
  void testGame(Tester t) {
    FifteenGame g = new FifteenGame();
    g.bigBang(200, 200);
  }
}

	// NOTE: You can delete this, 
	// implentation of the possibleMove method, with the argument
	// only for testing purpose, now donsn't exist anymore.

	// void testWinner(Tester t){
	// 	ArrayList<ArrayList<Integer>> winnerTiles = new ArrayList<ArrayList<Integer>>();
	// 	for(
	// 	int y = 0;
	// 	y < 4;
	// 	y = y + 1)
	// 	{
	// 		ArrayList<Integer> row = new ArrayList<Integer>();
	// 		for(
	// 		int x = 0; 
	// 		x < 4; 
	// 		x = x + 1)
	// 		{
	// 			// Calculate the val of the tile.
	// 			// Based on the current row and col,
	// 			// adding 1, beacuse of the index
	// 			// and multiply by 4 since, we got only 4 rows.
	// 			int val = 4 * y + x + 1;
	// 			if(val == 16) {
	// 				row.add(0);
	// 			}else{
	// 				row.add(val);
	// 			}
	// 		}
	// 			winnerTiles.add(row);
	// 		}
	//
	// 	ArrayList<Integer> winnerTiles1Row = new ArrayList<Integer>();
	// 	winnerTiles1Row.add(1); winnerTiles1Row.add(2); winnerTiles1Row.add(3); winnerTiles1Row.add(4);
	// 	t.checkExpect(winnerTiles.get(0), winnerTiles1Row);
	//
	// 	ArrayList<Integer> winnerTiles2Row = new ArrayList<Integer>();
	// 	winnerTiles2Row.add(5); winnerTiles2Row.add(6); winnerTiles2Row.add(7); winnerTiles2Row.add(8);
	// 	t.checkExpect(winnerTiles.get(1), winnerTiles2Row);
	//
	// 	ArrayList<Integer> winnerTiles3Row = new ArrayList<Integer>();
	// 	winnerTiles3Row.add(9); winnerTiles3Row.add(10); winnerTiles3Row.add(11); winnerTiles3Row.add(12);
	// 	t.checkExpect(winnerTiles.get(2), winnerTiles3Row);
	//
	// 	ArrayList<Integer> winnerTiles4Row = new ArrayList<Integer>();
	// 	winnerTiles4Row.add(13); winnerTiles4Row.add(14); winnerTiles4Row.add(15); winnerTiles4Row.add(0);
	// 	t.checkExpect(winnerTiles.get(3), winnerTiles4Row);
	//
	// 	ArrayList<ArrayList<Integer>> winnerTiles_ = new ArrayList<ArrayList<Integer>>();
	// 	winnerTiles_.add(winnerTiles1Row); winnerTiles_.add(winnerTiles2Row);
	// 	winnerTiles_.add(winnerTiles3Row); winnerTiles_.add(winnerTiles4Row);
	// 	t.checkExpect(winnerTiles, winnerTiles_);
	//
	// 	t.checkExpect(new FifteenGame().checkVictory(winnerTiles), true);
	// 	t.checkExpect(new FifteenGame().checkVictory(winnerTiles_), true);
	// }

	// void testPossibleMove(Tester t){
	//    FifteenGame g = new FifteenGame();
	//
	// 	// Top Left, edge case
	// 	ArrayList<Integer> e0 = new ArrayList<Integer>();
	// 	e0.add(1); e0.add(4);
	// 	t.checkExpect(g.possibleMoves(0), e0);
	// 	// Bottom Right, edge case
	// 	ArrayList<Integer> e1 = new ArrayList<Integer>();
	// 	e1.add(14); e1.add(11);
	// 	t.checkExpect(g.possibleMoves(15), e1);
	// 	// Bottom Left, edge case
	// 	ArrayList<Integer> e2 = new ArrayList<Integer>();
	// 	e2.add(13); e2.add(8);
	// 	t.checkExpect(g.possibleMoves(12), e2);
	// 	// Top Right, edge case
	// 	ArrayList<Integer> e3 = new ArrayList<Integer>();
	// 	e3.add(2); e3.add(7);
	// 	t.checkExpect(g.possibleMoves(3), e3);
	//
	// 	ArrayList<Integer> e4 = new ArrayList<Integer>();
	// 	e4.add(5); e4.add(7); e4.add(2); e4.add(10);
	// 	t.checkExpect(g.possibleMoves(6), e4);
	// }

