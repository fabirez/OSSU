import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.Random;

/*
- [x] Your world must have a constructor that just takes an integer,
			which represents the number of bullets a player has to shoot. That is how your graders will launch your world.

- [x] Your game must end when there are no more bullets to fire and there are no bullets left on the screen.

- [x] So long as there are bullets left to fire,
			the player should be able to press the space bar to fire a bullet from the center of the bottom of the screen.

- [x] As well as the bullets and the ships,
	the player must also be able to see how many bullets are left and how many ships have been destroyed so far.


- [ ] Ships should not spawn at every tick.
			Ships should spawn with some fixed frequency, and a non-zero, random amount of them should spawn at the same time.

- [ ] Ships should spawn at either the left or right ends of the screen,
			and then move across the screen.

- [ ] Ships should not be able to spawn at the very top or very bottom of the screen,
			but their spawn point along the y-axis should be random.

- [ ] Two or more identical ships spawning at the same time is fine.


- [ ] Ships should move with what appears to be smooth motion, and move in a straight line across the screen.

- [ ] All ships should have the same speed (the magnitude of their velocity).

- [ ] Ships that have flown past the edge of the screen should be removed from the game.

- [ ] Ships should be visually represented as a circle with a fixed color and radius.

- [ ] When a ship is hit by a bullet, it disappears.
		WARNING: Need to implement bullet first

- [ ] If a bullet hits two or more ships simultaneously, all of the ships should disappear.
		WARNING: Need to implement bullet first
*/


class MyPosn extends Posn{

  // standard constructor
  MyPosn(int x, int y) {
    super(x, y);
  }
 
  // constructor to convert from a Posn to a MyPosn
  MyPosn(Posn p) {
    this(p.x, p.y);
  }

	// add the given posn to the current posn
	MyPosn add(MyPosn vel){
		return new MyPosn(this.x + vel.x, this.y + vel.y);
	}

}

class Ship{
	MyPosn pos; MyPosn vel;

	Ship(MyPosn pos, MyPosn vel){
		this.pos=pos;
		this.vel=vel;
	}

	// produce a ship with the update pos based on vel
	public Ship move(){
		return new Ship(this.pos.add(this.vel), this.vel);
	}

	// produce true if a ship is outside of the screen 
	//	(pos.x bigger than the given width or pos.y bigger than given height)
	//	otherwise false.
	public boolean isOffScreen(int width, int height){
		return this.pos.x > width || this.pos.y > height;
	}

	// produce a WorldImage that rapresetn the current drawed ship
	// On what should i drwa the image?  The scene ? The world ?
	public WorldImage draw(){
		return new CircleImage(30, OutlineMode.SOLID, Color.RED);
	}

	// place the ship with pos x y on the scene
	public WorldScene place(WorldScene s){
		return s.placeImageXY(this.draw(), this.pos.x, this.pos.y);
	}

}

interface ILoShip{ 
	// produce a list of ships, with updated pos
	ILoShip moveAll();
	// produce a list of ships, without the ships offscreen
	ILoShip filterOffScreenShip(int width, int height);
	// place all the ships on the given scene
	WorldScene placeAll(WorldScene s);
	ILoShip append(ILoShip that);
}

class ConsLoShip implements ILoShip{
	Ship first; 
	ILoShip rest;

	ConsLoShip(Ship first, ILoShip rest){
		this.first=first;
		this.rest=rest;
	}

	// produce a list of ships, with updated pos
	public ILoShip moveAll(){
		return new ConsLoShip(this.first.move(), this.rest.moveAll());
	}

	// produce a list of ships, without the ships offscreen
	public ILoShip filterOffScreenShip(int width, int height){
		if(this.first.isOffScreen(width, height)){
			return this.rest.filterOffScreenShip(width, height);
		}else{
			return new ConsLoShip(this.first, this.rest.filterOffScreenShip(width, height));
		}
	}

	// place all the ships on the given scene
	public WorldScene placeAll(WorldScene s){
		return this.rest.placeAll(this.first.place(s));
	}

	// generate a new list with the current one as head, and the given one as tail.
	public ILoShip append(ILoShip that){
		return new ConsLoShip(this.first, this.rest.append(that));
	}
}

class MtLoShip implements ILoShip{
	MtLoShip(){}

	public ILoShip moveAll(){ return this; }

	public ILoShip filterOffScreenShip(int width, int height){ return this; }
	
	public WorldScene placeAll(WorldScene s){ return s; }

	public ILoShip append(ILoShip that){ return that; }

}

class Utility{

	Ship randomShip(int maxWidth, int height){
		int velocity = 10;

		int edge = 50;
		if(new Random().nextInt(2) == 0){
			return new Ship(
				new MyPosn(
					0,
					// Preventing the circle to spawn to much near to the top and bottom edge
					Math.max(edge, new Random().nextInt(height) - edge)), 
				new MyPosn(velocity,0));
		}else{
			return new Ship(
				new MyPosn(
					maxWidth,
					// Preventing the circle to spawn to much near to the top and bottom edge
					Math.max(edge, new Random().nextInt(height) - edge)), 
				new MyPosn(-velocity,0));
		}
	}

	ILoShip randomShips(int length, int width, int height){
		if(length <= 0){
			return new MtLoShip();
		}else{
			return new ConsLoShip(this.randomShip(width, height),this.randomShips(length - 1, width, height)); 
		}
	}
}


class MyGame extends World {
	int WIDTH;
	int HEIGHT;
	int currentTick;

	int bullets;
	int destroyedShip;
	ILoShip currentShips;
	
	MyGame(int bullets){
		if ( bullets <= 0 ) {
			throw new IllegalArgumentException("The player cannot start without less or equal 0 bullets.");
		}
		this.WIDTH = 1000;
		this.HEIGHT = 500;
		this.currentTick = 1;
		this.destroyedShip = 0;
		this.currentShips = new Utility().randomShips(new Random().nextInt(10), this.WIDTH, this.HEIGHT);
		this.bullets = bullets;
	}

	MyGame(int bullets, int destroyedShip, ILoShip currentShips, int currentTick){
		this.WIDTH = 1000;
		this.HEIGHT = 500;
		this.bullets = bullets;
		this.destroyedShip = destroyedShip;
		this.currentShips = currentShips;
		this.currentTick = currentTick;
	}


	public MyGame onTick(){
		return this.moveAll().removeShipsOffScreen().incrementGameTick().generateShips();
	}

	public MyGame incrementGameTick(){
		return new MyGame(this.bullets, this.destroyedShip, this.currentShips, this.currentTick + 1);
	}

	// Generate random ships 
	public MyGame generateShips(){
		if(this.currentTick % 30 == 0){
		return new MyGame(this.bullets, this.destroyedShip, 
			this.currentShips.append(new Utility().randomShips(new Random().nextInt(10), this.WIDTH, this.HEIGHT)),
			this.currentTick);
		}else{
			return this;
		}
	}

	// Move all the ships at every tick
	public MyGame moveAll(){
		return new MyGame(this.bullets, this.destroyedShip, this.currentShips.moveAll(), this.currentTick);
	}

	// remove all the ships out of the screen at every thick
	public MyGame removeShipsOffScreen(){
		return new MyGame(this.bullets, this.destroyedShip, this.currentShips.filterOffScreenShip(this.WIDTH, this.HEIGHT), this.currentTick);
	}


	public WorldScene makeScene() {
		//Make a new scene.
		WorldScene scene = new WorldScene(this.WIDTH, this.HEIGHT);
		scene = this.bulletInfo(scene);
		scene = this.shipInfo(scene);
		scene = this.placeAll(scene);
		return scene;
	}


	// Show the current number of bullets
	public WorldScene bulletInfo(WorldScene s){
		return s.placeImageXY(new TextImage("Bullets: " + this.bullets, Color.black), this.WIDTH - 50, 10);
	}
	// Show the current number of destroyed Ships
	public WorldScene shipInfo(WorldScene s){
		return s.placeImageXY(new TextImage("Ship: " + this.destroyedShip, Color.black), this.WIDTH - 50, 30);
	}
	public WorldScene placeAll(WorldScene s){
		return this.currentShips.placeAll(s);
	}

	// Handle the " " space key,
	//	if it's pressed then we remove a bullets and shoot.
	//	otherwise we return the current world
	public MyGame onKeyEvent(String key) {
		 if (key.equals(" ") && this.bullets > 0) {
			// this.shot() MyGame -> MyGame
			 return new MyGame(this.bullets - 1, this.destroyedShip, this.currentShips, this.currentTick);
		 }else {
			 return this;
		 }
	}

	// TODO:!!! 
	// Check if there are still any bullet on the screen
	//	if there are any, return true
	//	otherwsie false.
	// public boolean bulletsOnScren(){
	// 	return ...;
	// }

	public WorldScene makeEndScene() {
		return new WorldScene(this.WIDTH, this.HEIGHT).placeImageXY(new TextImage("Game Over", Color.red), this.WIDTH / 2, this.HEIGHT / 2);
	}

	public WorldEnd worldEnds() {
		// if (this.bullets <= 0 && this.bulletsOnScren() == false) {
		if(this.bullets <= 0){
			return new WorldEnd(true, this.makeEndScene());
		} else {
			return new WorldEnd(false, this.makeEndScene());
		}
	}
}

class ExamplesGame{
	ExamplesGame(){}

	MyPosn p0 = new MyPosn(0, 0);
	MyPosn p1 = new MyPosn(250, 250);
	MyPosn p2 = new MyPosn(500, 500);
	MyPosn p3 = new MyPosn(1, 0);
	MyPosn p4 = new MyPosn(252, 250);
	MyPosn p5 = new MyPosn(503, 500);

	// Velocity (only x)
	MyPosn v0 = new MyPosn(1, 0);
	MyPosn v1 = new MyPosn(2, 0);
	MyPosn v2 = new MyPosn(3, 0);

	Ship s0 = new Ship(p0, v0);
	Ship s1 = new Ship(p1, v1);
	Ship s2 = new Ship(p2, v2);
	Ship s3 = new Ship(p3, v0);
	Ship s4 = new Ship(p4, v1);
	Ship s5 = new Ship(p5, v2);

	ILoShip mtShip = new MtLoShip();
	ILoShip los0 = new ConsLoShip(s0, new ConsLoShip(s1, new ConsLoShip(s2, mtShip)));
	ILoShip los1 = new ConsLoShip(s3, new ConsLoShip(s4, new ConsLoShip(s5, mtShip)));	
	ILoShip los2 = new ConsLoShip(s3, new ConsLoShip(s4, mtShip));	

	boolean testMove(Tester t){
		return
		t.checkExpect(s0.move(), s3)
		&&
		t.checkExpect(s1.move(), s4)
		&&
		t.checkExpect(s2.move(), s5)
		;
	}

	boolean testMoveAll(Tester t){
		return
		t.checkExpect(los0.moveAll(), los1);
	}

	boolean testIsOffScreen(Tester t){
		int WIDTH = 500;
		int HEIGHT = 500;
		return
		t.checkExpect(s0.isOffScreen(WIDTH, HEIGHT), false)
		&&
		t.checkExpect(s2.isOffScreen(WIDTH, HEIGHT), false)
		&&
		t.checkExpect(s5.isOffScreen(WIDTH, HEIGHT), true)
		;
	}


	boolean testFilterOffScreenShip(Tester t){
		int WIDTH = 500;
		int HEIGHT = 500;
		return
		t.checkExpect(los0.filterOffScreenShip(WIDTH, HEIGHT), los0)
		&&
		t.checkExpect(los1.filterOffScreenShip(WIDTH, HEIGHT), los2)
		;
	}

	boolean testBigBang(Tester t) {
		MyGame world = new MyGame(10);
	   return world.bigBang(1000, 500, 1.0/28.0);
	 }

	// boolean testGame(Tester t){
	// 	return
	// 	t.checkConstructorException(new IllegalArgumentException("The player cannot start without less or equal 0 bullets."), "MyGame", -1) 
	// 	&&
	// 	t.checkConstructorException(new IllegalArgumentException("The player cannot start without less or equal 0 bullets."), "MyGame",  0)
	// 	&&
	// 	t.checkConstructorException(new IllegalArgumentException("The player cannot start without less or equal 0 bullets."), "MyGame", -1, 1)
	// 	;
	// }
}
  
