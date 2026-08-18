import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Color.RED, Color.GRAY, etc.)
import javalib.worldcanvas.*;


class MyPosn extends Posn {

  // standard constructor
  MyPosn(int x, int y) {
    super(x, y);
  }
 
  // constructor to convert from a Posn to a MyPosn
  MyPosn(Posn p) {
    this(p.x, p.y);
  }

	// given another MyPosn will add its x and y values to this one and output a new MyPosn.
	MyPosn add(MyPosn that){
		return new MyPosn(this.x + that.x, this.y + that.y);
	}

  // given two numbers representing the width and height of a screen, determines if this position lies outside of it.
	boolean isOffScreen(int width, int height){
		return this.x < 0 || this.y < 0 || this.x > width || this.y > height;
	}
}

class Circle {
 
  MyPosn position; // in pixels
  MyPosn velocity; // in pixels/tick

	Circle(MyPosn position, MyPosn velocity){
		this.position=position;
		this.velocity=velocity;
	}

	// outputs a Circle in its new position after one tick.
	Circle move(){
		return new Circle(this.position.add(this.velocity), this.velocity);
	}

	// given two numbers representing the width and height of a screen, determines if a single circle lies outside of it.
	boolean isOffScreen(int width, int height){
		return this.position.isOffScreen(width, height);
	}
	// outputs a WorldImage representing the circle.
  WorldImage draw(){
    return new CircleImage(20, OutlineMode.SOLID, Color.BLUE);
  }

	// given a WorldScene places a drawing of the circle at the appropriate position.
	WorldScene place(WorldScene s){
		return s.placeImageXY(this.draw(), this.position.x, this.position.y);
	}
}

interface ILoCircle{
	// moves every Circle in the list.
	ILoCircle moveAll();

	// given two numbers representing the width and height of a screen,
	// removes every circle in a list of circles that is offscreen.
  ILoCircle removeOffscreen(int width, int height);

	// places a list of circles on a given WorldScene.
  WorldScene placeAll(WorldScene s);

	// count how many circles are off screen
	int howManyOffScreen(int width, int height);
}

class ConsLoCircle implements ILoCircle{
	Circle    first;
	ILoCircle  rest;

	ConsLoCircle(Circle first, ILoCircle rest){
		this.first = first;
		this.rest = rest;
	}

	// moves every Circle in the list.
	public ILoCircle moveAll(){
		return new ConsLoCircle(this.first.move(), this.rest.moveAll());
	}

	// given two numbers representing the width and height of a screen,
	// removes every circle in a list of circles that is offscreen.
	public ILoCircle removeOffscreen(int width, int height){
		if(this.first.isOffScreen(width, height)){
			return this.rest.removeOffscreen(width, height);
		}else{
			return new ConsLoCircle(this.first, this.rest.removeOffscreen(width, height));
		}
	}

 	 // places a list of circles on a given WorldScene.
	 public WorldScene placeAll(WorldScene s){
		return this.rest.placeAll(this.first.place(s));
	}

	 // count how many circles are off screen
	public int howManyOffScreen(int width, int height){
		if(this.first.isOffScreen(width, height)){
			return 1 + this.rest.howManyOffScreen(width, height);
		}else{
			return this.rest.howManyOffScreen(width, height);
		}
	}
}

class MtLoCircle implements ILoCircle{ 

	// count how many circles are off screen
	public int howManyOffScreen(int width, int height){
		return 0;
	}

	 public WorldScene placeAll(WorldScene s){
		return s;
	}

	// moves every Circle in the list.
	public ILoCircle moveAll(){
		return this;
	}

	// given two numbers representing the width and height of a screen,
	// removes every circle in a list of circles that is offscreen.
	public ILoCircle removeOffscreen(int width, int height){
		return this;
	}
}

class MyGame extends World{
	int WIDTH;
	int HEIGHT;
	int currentTick; // ???
	int endTick;    // ???

	int limitCircle;  // How many circle can go out of the screen, until the game is over
	ILoCircle currentCircles;  // Initial list of circle, present in the game

	MyGame(int width, int height, int currentTick, int endTick, int limitCircle, ILoCircle currentCircles){
		if ( width < 0 || height < 0 || endTick < 2) {
			throw new IllegalArgumentException("Invalid arguments passed to constructor.");
		}
		this.WIDTH = width;
		this.HEIGHT = height;
		this.currentTick = currentTick;
		this.endTick = endTick;
		this.limitCircle = limitCircle;
		this.currentCircles = currentCircles;
	}

	public WorldScene makeScene(){
		return this.currentCircles.placeAll(new WorldScene(this.WIDTH, this.HEIGHT));
	}

	public WorldScene makeAFinalScene(){
		return new WorldScene(this.WIDTH, this.HEIGHT).placeImageXY(new TextImage("Game Over", Color.RED), this.WIDTH / 2, this.HEIGHT / 2);
	}

	// determine when the game is over, which is when the count of circles to go offscreen is 0 or less.
	public WorldEnd worldEnds() {
		if (limitCircle <= 0) {
			return new WorldEnd(true, this.makeAFinalScene());
		} else {
			return new WorldEnd(false, this.makeScene());
		}
	}


	// which will add a circle to the game where the player clicked. For now, the circle should just move straight up.
	public MyGame onMouseClicked(Posn pos){
		 return new MyGame(this.WIDTH,
			this.HEIGHT,
			this.currentTick,
			this.endTick+10,
			this.limitCircle,
			new ConsLoCircle(new Circle(new MyPosn(pos.x, pos.y), new MyPosn(0, -3)), this.currentCircles));
	}

	//  move the circles on screen, remove the ones off screen, and decrement the count of circles that are left to move offscreen.
	public MyGame onTick(){
		return this.moveAll().removeCircleOffScreenAndDecrementCount();
	}


	// moves every Circle in the game.
	public MyGame moveAll(){
		return new MyGame(this.WIDTH, this.HEIGHT, this.currentTick, this.endTick+10, this.limitCircle, this.currentCircles.moveAll());
	}

	// given two numbers representing the width and height of a screen,
	// removes every circle in a list of circles that is offscreen.
	public MyGame removeCircleOffScreenAndDecrementCount(){
		return new MyGame(this.WIDTH, this.HEIGHT, this.currentTick, this.endTick+10, this.limitCircle - this.currentCircles.howManyOffScreen(this.WIDTH, this.HEIGHT), this.currentCircles.removeOffscreen(this.WIDTH, this.HEIGHT));
	}
}


class ExamplesSimpleGame{

	MyPosn p1 = new MyPosn(150,150);
	MyPosn p2 = new MyPosn(50, 50);
	MyPosn p3 = new MyPosn(200, 200);
	MyPosn p4 = new MyPosn(250, 250);
	MyPosn p5 = new MyPosn(350, 350);

	// Velocity
	MyPosn v1 = new MyPosn(1,1);
	MyPosn v2 = new MyPosn(2,2);
	MyPosn v3 = new MyPosn(3,3);

	Circle c1 = new Circle(p1, v1);
	Circle c2 = new Circle(p2, v1);
	Circle c3 = new Circle(p3, v1);
	Circle c4 = new Circle(new MyPosn(151, 151), v1);
  Circle c5 = new Circle(new MyPosn(51, 51), v1);
	Circle c6 = new Circle(new MyPosn(201, 201), v1);

	// off screen
	Circle offC1 = new Circle(new MyPosn(501, 501), v1);
	Circle offC2 = new Circle(new MyPosn( -1,  -1), v1);
	Circle offC3 = new Circle(new MyPosn(501, 250), v1);
	Circle offC4 = new Circle(new MyPosn(250, 501), v1);
	Circle offC5 = new Circle(new MyPosn( -1, 250), v1);
	Circle offC6 = new Circle(new MyPosn(250,  -1), v1);

	ILoCircle mtCircle = new MtLoCircle();
	ILoCircle loc1 = new ConsLoCircle(c1, new ConsLoCircle(c2, new ConsLoCircle(c3, mtCircle)));
	ILoCircle loc2 = new ConsLoCircle(c4, new ConsLoCircle(c5, new ConsLoCircle(c6, mtCircle)));
	ILoCircle loc3 = new ConsLoCircle(c1, new ConsLoCircle(offC2, new ConsLoCircle(c3, mtCircle)));
	ILoCircle loc4 = new ConsLoCircle(c4, new ConsLoCircle(c5, new ConsLoCircle(offC6, mtCircle)));
	ILoCircle loc5 = new ConsLoCircle(offC4, new ConsLoCircle(offC5, new ConsLoCircle(offC6, mtCircle)));

	// testing add method
	boolean testAdd(Tester t){
		return 
		t.checkExpect(p1.add(p2), p3) 
		&&
		t.checkExpect(p2.add(p3), p4) 
		&&
		t.checkExpect(p1.add(p3), p3);
	}

	// testing is off screen method
	boolean testIsOffScreen(Tester t){
		int WIDTH=500;
		int HEIGHT=500;
		return 
		t.checkExpect(new MyPosn(0,0).isOffScreen(WIDTH, HEIGHT), false) 
		&&
		t.checkExpect(new MyPosn(WIDTH, HEIGHT).isOffScreen(WIDTH, HEIGHT), false) 
		&&
		t.checkExpect(new MyPosn(-1,0).isOffScreen(WIDTH, HEIGHT), true)
		&&
		t.checkExpect(new MyPosn(0,-1).isOffScreen(WIDTH, HEIGHT), true)
		&&
		t.checkExpect(new MyPosn(501, 0).isOffScreen(WIDTH, HEIGHT), true)
		&&
		t.checkExpect(new MyPosn(0, 501).isOffScreen(WIDTH, HEIGHT), true)
		&&
		t.checkExpect(p1.isOffScreen(WIDTH, HEIGHT), false)
		&&
		t.checkExpect(p2.isOffScreen(WIDTH, HEIGHT), false)
		&&
		t.checkExpect(p3.isOffScreen(WIDTH, HEIGHT), false)
		;
	}

	// testing move method
	boolean testMove(Tester t){
		return 
		t.checkExpect(c1.move(), c4)
		&&
		t.checkExpect(c2.move(), c5)
		&&
		t.checkExpect(c3.move(), c6)
		;
	}

	// testing move method
	boolean testMoveAll(Tester t){
		return 
		t.checkExpect(loc1.moveAll(), loc2); 
	}
 // testing is off screen method
	boolean testIsOffScreenCircle(Tester t){
		int WIDTH=500;
		int HEIGHT=500;
		return
		t.checkExpect(c1.isOffScreen(WIDTH, HEIGHT), false)
		&&
		t.checkExpect(c2.isOffScreen(WIDTH, HEIGHT), false)
		&&
		t.checkExpect(c3.isOffScreen(WIDTH, HEIGHT), false)
		&&
		t.checkExpect(offC1.isOffScreen(WIDTH, HEIGHT), true)
		&&
		t.checkExpect(offC2.isOffScreen(WIDTH, HEIGHT), true)
		&&
		t.checkExpect(offC3.isOffScreen(WIDTH, HEIGHT), true)
		&&
		t.checkExpect(offC4.isOffScreen(WIDTH, HEIGHT), true)
		&&
		t.checkExpect(offC5.isOffScreen(WIDTH, HEIGHT), true)
		&&
		t.checkExpect(offC6.isOffScreen(WIDTH, HEIGHT), true)
		;
	}

 // testing remove off screen method
	boolean testRemoveOffscreen(Tester t){
		int WIDTH=500;
		int HEIGHT=500;
		return
		t.checkExpect(loc1.removeOffscreen(WIDTH, HEIGHT), loc1)
		&&
		t.checkExpect(loc2.removeOffscreen(WIDTH, HEIGHT), loc2)
		&&
		t.checkExpect(loc3.removeOffscreen(WIDTH, HEIGHT), new ConsLoCircle(c1, new ConsLoCircle(c3, mtCircle)))
		&&
		t.checkExpect(loc4.removeOffscreen(WIDTH, HEIGHT), new ConsLoCircle(c4, new ConsLoCircle(c5, mtCircle)))
		&&
		t.checkExpect(loc5.removeOffscreen(WIDTH, HEIGHT), mtCircle)
		;
	}

 // testing remove off screen method
	boolean testHowManyOffScreen(Tester t){
		int WIDTH=500;
		int HEIGHT=500;
		return
		t.checkExpect(loc1.howManyOffScreen(WIDTH, HEIGHT), 0)
		&&
		t.checkExpect(loc2.howManyOffScreen(WIDTH, HEIGHT), 0)
		&&
		t.checkExpect(loc3.howManyOffScreen(WIDTH, HEIGHT), 1)
		&&
		t.checkExpect(loc4.howManyOffScreen(WIDTH, HEIGHT), 1)
		&&
		t.checkExpect(loc5.howManyOffScreen(WIDTH, HEIGHT), 3)
		;
	}





 // testing draw circle method
	boolean testDrawCircle(Tester t){
		WorldCanvas c = new WorldCanvas(500, 500);
		WorldScene s = new WorldScene(500, 500);
		return c.drawScene(
			s
			.placeImageXY(c1.draw(), 250, 250)
			.placeImageXY(c2.draw(), 50, 50)
		)
				&& c.show();
	}

 // testing place circle method
	boolean testPlaceCircle(Tester t){
		WorldCanvas c = new WorldCanvas(500, 500);
		WorldScene s = new WorldScene(500, 500);
		return c.drawScene(c1.place(s))
				&& c.show();
	}

 // testing placeAll circle method
	boolean testPlaceAll(Tester t){
		WorldCanvas c = new WorldCanvas(500, 500);
		WorldScene s = new WorldScene(500, 500);
		return c.drawScene(loc1.placeAll(s))
				&& c.show();
	}

  boolean testBigBang(Tester t) {
	  int worldWidth = 500;
    int worldHeight = 500;
		double tickRate = 7.0/28.0;
		MyGame w = new MyGame(worldWidth, worldHeight, 1, 20, 5, this.mtCircle);
    return w.bigBang(worldWidth, worldHeight, tickRate);
  }
}
