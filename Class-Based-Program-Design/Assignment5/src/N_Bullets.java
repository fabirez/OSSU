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

- [x] Ships should not spawn at every tick.
			Ships should spawn with some fixed frequency, and a non-zero, random amount of them should spawn at the same time.

- [x] Ships should spawn at either the left or right ends of the screen,
			and then move across the screen.

- [x] Ships should not be able to spawn at the very top or very bottom of the screen,
			but their spawn point along the y-axis should be random.

- [x] Two or more identical ships spawning at the same time is fine.

- [x] Ships should move with what appears to be smooth motion, and move in a straight line across the screen.

- [x] All ships should have the same speed (the magnitude of their velocity).

- [x] Ships that have flown past the edge of the screen should be removed from the game.

- [x] Ships should be visually represented as a circle with a fixed color and radius.

- [ ] When a ship is hit by a bullet, it disappears.
		WARNING: Need to implement bullet first

- [ ] If a bullet hits two or more ships simultaneously, all of the ships should disappear.
		WARNING: Need to implement bullet first


- [~] Bullets should move with what appears to be smooth motion,
			and move in a straight line across the screen.

- [~] All bullets should have the same speed (the magnitude of their velocity).
		It is fine if bullet speeds vary slightly due to rounding.

- [x] Bullets that have flown past the edge of the screen should be removed from the game.

- [x] Bullets should be visually represented as a circle with a fixed color; their size is discussed below.
		TODO: change the size

- [ ] When a bullet collides with a ship, it disappears and "explodes" into many bullets.
		The initial position of all of them should be the same as that of the destroyed bullet.

- [ ] When a bullet a player fired hits a ship, it should explode into two bullets.
			When one of those bullets hits a ship, it should explode into three bullets, etc.

- [ ] In the nth explosion (where n is 1 in the explosion of the player-fired bullet), for each bullet i it explodes into, 0 <= i <= n, the bullet should fire off at i * (360 / (n + 1)) degrees.

- [ ] Bullets should grow in size along with n, so the higher up in the chain of explosions the bullet originated from, the bigger it should be.
			It should stop growing after some explosion, however, so it doesn’t take up too much of the screen.

- [ ] If two or more bullets hit the same ship simultaneously, all of the bullets should explode.


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

	public int getCenterX(int radius){
		return this.x + radius;
	}

	public int getCenterY(int radius){
		return this.y + radius;
	}

}

class Bullet{
	MyPosn pos; MyPosn vel; int radius;

	Bullet(MyPosn pos, MyPosn vel, int radius){
		this.pos=pos;
		this.vel=vel;
		this.radius=radius;
	}

	// produce a bullet with the update pos based on vel
	public Bullet move(){
		return new Bullet(this.pos.add(this.vel), this.vel, this.radius);
	}

	// produce true if a bullet is outside of the screen 
	//	(pos.x bigger than the given width or pos.y bigger than given height)
	//	otherwise false.
	public boolean isOffScreen(int width, int height){
		return this.pos.x > width || this.pos.y > height || this.pos.x < 0 || this.pos.y < 0;
	}

	// produce a WorldImage that rapresetn the current drawed bullet
	// On what should i drwa the image?  The scene ? The world ?
	public WorldImage draw(){
		return new CircleImage(this.radius, OutlineMode.SOLID, Color.PINK);
	}

	// place the bullet with pos x y on the scene
	public WorldScene place(WorldScene s){
		return s.placeImageXY(this.draw(), this.pos.x, this.pos.y);
	}

	// get center x of the bullet
	public int getCenterX(){
		return this.pos.getCenterX(this.radius);
	}

	// get center y of the bullet
	public int getCenterY(){
		return this.pos.getCenterY(this.radius);
	}

	// produce true if the bullet is collidign with the given ship; false otherwise
  public boolean isCollidingWith(Ship other) {
    double dx =
      this.getCenterX() - other.getCenterX();
    double dy =
      this.getCenterY() - other.getCenterY();
    double distance = Math.sqrt(dx * dx + dy * dy);
    return distance < this.radius + other.radius;
  }

}

interface ILoBullet{ 
	// produce a list of bullets, with updated pos
	ILoBullet moveAll();
	// produce a list of bullets, without the bullets offscreen
	ILoBullet filterOffScreenBullet(int width, int height);
	// place all the bullets on the given scene
	WorldScene placeAll(WorldScene s);
	// Append the given list of bullet (as a tail) on the current one
	ILoBullet append(ILoBullet that);
	// Append the given bullet (as a tail) on the current list of bullet
	ILoBullet appendEl(Bullet that);

	// produce the indexes of both the collided ship and bullet if any, 
	// or -1 in both idxShip, idxBullet of the IdexUtility values.
	IndexUtility collision(ILoShip ships);
	IndexUtility collisionHelper(ILoShip ships, int idxBullet);
	

	// update the bullet in the list at the given index (spread)
	// updateBulletByIndex(collisionIndex);
}

class ConsLoBullet implements ILoBullet{
	Bullet first; 
	ILoBullet rest;

	ConsLoBullet(Bullet first, ILoBullet rest){
		this.first=first;
		this.rest=rest;
	}

	// produce a list of bullets, with updated pos
	public ILoBullet moveAll(){
		return new ConsLoBullet(this.first.move(), this.rest.moveAll());
	}

	// produce a list of bullets, without the bullets offscreen
	public ILoBullet filterOffScreenBullet(int width, int height){
		if(this.first.isOffScreen(width, height)){
			return this.rest.filterOffScreenBullet(width, height);
		}else{
			return new ConsLoBullet(this.first, this.rest.filterOffScreenBullet(width, height));
		}
	}

	// Append the given list of bullet (as a tail) on the current one
	public WorldScene placeAll(WorldScene s){
		return this.rest.placeAll(this.first.place(s));
	}

	// generate a new list with the current one as head, and the given one as tail.
	public ILoBullet append(ILoBullet that){
		return new ConsLoBullet(this.first, this.rest.append(that));
	}

	// Append the given bullet (as a tail) on the current list of bullet
	public ILoBullet appendEl(Bullet that){
		return new ConsLoBullet(this.first, this.rest.appendEl(that));
	}

	// produce the indexes of both the collided ship and bullet if any, 
	// or -1 in both idxShip, idxBullet of the IdexUtility values.
	public IndexUtility collision(ILoShip ships){
		return this.collisionHelper(ships, 0);
	}

	public IndexUtility collisionHelper(ILoShip ships, int idxBullet){
		int idxShip = ships.collisionShips(this.first);
		if(idxShip >= 0){
			return new IndexUtility(idxShip, idxBullet);
		}else{
			return this.rest.collisionHelper(ships, idxBullet + 1);
		}
	}

}

class MtLoBullet implements ILoBullet{
	MtLoBullet(){}

	public ILoBullet moveAll(){ return this; }

	public ILoBullet filterOffScreenBullet(int width, int height){ return this; }
	
	public WorldScene placeAll(WorldScene s){ return s; }

	public ILoBullet append(ILoBullet that){ return that; }

	public ILoBullet appendEl(Bullet that){ return new ConsLoBullet(that, this); }

	public IndexUtility collision(ILoShip ships){ return new IndexUtility(-1,-1); }
	public IndexUtility collisionHelper(ILoShip ships, int idxBullet){ return new IndexUtility(-1,-1); }

}

// ====

class Ship{
	MyPosn pos; MyPosn vel; int radius;

	Ship(MyPosn pos, MyPosn vel, int radius){
		this.pos=pos;
		this.vel=vel;
		this.radius=radius;
	}

	// produce a ship with the update pos based on vel
	public Ship move(){
		return new Ship(this.pos.add(this.vel), this.vel, this.radius);
	}

	// produce true if a ship is outside of the screen 
	//	(pos.x bigger than the given width or pos.y bigger than given height)
	//	otherwise false.
	public boolean isOffScreen(int width, int height){
		return this.pos.x > width || this.pos.y > height || this.pos.x < 0 || this.pos.y < 0;
	}

	// produce a WorldImage that rapresetn the current drawed ship
	// On what should i drwa the image?  The scene ? The world ?
	public WorldImage draw(){
		return new CircleImage(this.radius, OutlineMode.SOLID, Color.RED);
	}

	// place the ship with pos x y on the scene
	public WorldScene place(WorldScene s){
		return s.placeImageXY(this.draw(), this.pos.x, this.pos.y);
	}


	// get center x 
	public int getCenterX(){
		return this.pos.getCenterX(this.radius);
	}

	// get center y 
	public int getCenterY(){
		return this.pos.getCenterY(this.radius);
	}

	// produce true if the ship is collidign with the given bullet; false otherwise
  public boolean isCollidingWith(Bullet other) {
    double dx =
      this.getCenterX() - other.getCenterX();
    double dy =
      this.getCenterY() - other.getCenterY();
    double distance = Math.sqrt(dx * dx + dy * dy);
    return distance < this.radius + other.radius;
  }

}

interface ILoShip{ 
	// produce a list of ships, with updated pos
	ILoShip moveAll();
	// produce a list of ships, without the ships offscreen
	ILoShip filterOffScreenShip(int width, int height);
	// place all the ships on the given scene
	WorldScene placeAll(WorldScene s);
	// Append the given list of ship (as a tail) on the current one
	ILoShip append(ILoShip that);
	// produce the index of the collided ship, or -1 if not of the ship is hitted
	int collisionShips(Bullet b);
	int collisionShipsHelper(Bullet b, int idxShip);
	// remove the ship from the list based on the given index
	ILoShip removeShipByIndex(int collisionIndex);
	ILoShip removeShipByIndexHelper(int collisionIndex, int currIndex, Ship prev);
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

	// produce the index of the hitted ship, or -1 if the ships is not hitted.
	public int collisionShips(Bullet b){
		return this.collisionShipsHelper(b, 0);
	}

	public int collisionShipsHelper(Bullet b, int idxShip){
		if(b.isCollidingWith(this.first)){
			return idxShip;
		}else{
			return this.rest.collisionShipsHelper(b, idxShip + 1);
		}
	}

	// remove the ship from the list based on the given index
	public ILoShip removeShipByIndex(int collisionIndex){
		if(collisionIndex == 0){
			return this.rest;
		}else{
			return this.rest.removeShipByIndexHelper(collisionIndex, 1, this.first);
		}
	}
	public ILoShip removeShipByIndexHelper(int collisionIndex, int currIndex, Ship prev){
		if(currIndex == collisionIndex){
			return new ConsLoShip(prev, this.rest);
			// new ConsLoShip(prev,  this.rest);
		}else{
			return new ConsLoShip(prev, this.rest.removeShipByIndexHelper(collisionIndex, currIndex + 1, this.first));
		}
	}
}

class MtLoShip implements ILoShip{
	MtLoShip(){}

	public ILoShip moveAll(){ return this; }

	public ILoShip filterOffScreenShip(int width, int height){ return this; }
	
	public WorldScene placeAll(WorldScene s){ return s; }

	public ILoShip append(ILoShip that){ return that; }

	public ILoShip filterHittedShips(Bullet that){ return this; }

	public int collisionShips(Bullet b){ return -1; }
	public int collisionShipsHelper(Bullet b, int idxShip){ return -1; }

	// remove the ship from the list based on the given index
	public ILoShip removeShipByIndex(int collisionIndex){ return this; }
	public ILoShip removeShipByIndexHelper(int collisionIndex, int currIndex, Ship prev){ return new ConsLoShip(prev, this); }
}


class IndexUtility{
	int idxShip; int idxBullet;
	IndexUtility(int idxShip, int idxBullet){
		this.idxShip = idxShip;
		this.idxBullet = idxBullet;
	}
}

class Utility{
	Ship randomShip(int maxWidth, int height){
		int velocity = 5;
		int edge = 50;
		if(new Random().nextInt(2) == 0){
			return new Ship(
				new MyPosn(
					0,
					// Preventing the circle to spawn to much near to the top and bottom edge
					Math.max(edge, new Random().nextInt(height) - edge)), 
				new MyPosn(velocity,0),
				// WARNING: this shouldn't be the way, need a global constant
			30);
		}else{
			return new Ship(
				new MyPosn(
					maxWidth,
					// Preventing the circle to spawn to much near to the top and bottom edge
					Math.max(edge, new Random().nextInt(height) - edge)), 
				new MyPosn(-velocity,0),
				// WARNING: this shouldn't be the way, need a global constant
				30
			);
		}
	}

	ILoShip randomShips(int length, int width, int height){
		if(length <= 0){
			return new MtLoShip();
		}else{
			return new ConsLoShip(this.randomShip(width, height),this.randomShips(length - 1, width, height)); 
		}
	}

	Bullet generateBullet(int width, int height){
		int edge = 10;
		// WARNING: Change the velocity here.
		return new Bullet(new MyPosn(width/ 2, height - edge), new MyPosn(0, -3), 20);
	}
}


class MyGame extends World {
	int WIDTH;
	int HEIGHT;
	int currentTick;

	int bullets;
	int destroyedShip;
	ILoShip currentShips;
	ILoBullet currentBullets;
	
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
		// The current bullets on the screen
		this.currentBullets = new MtLoBullet();
	}

	MyGame(int bullets, int destroyedShip, ILoShip currentShips, int currentTick, ILoBullet currentBullets){
		this.WIDTH = 1000;
		this.HEIGHT = 500;
		this.bullets = bullets;
		this.destroyedShip = destroyedShip;
		this.currentShips = currentShips;
		this.currentTick = currentTick;
		// The current bullets on the screen
		this.currentBullets = currentBullets;
	}


	public MyGame onTick(){
		return this.moveAll().removeOffScreen().checkCollision().incrementGameTick().generateShips();
	}

	public MyGame checkCollision(){
		IndexUtility idxU = this.currentBullets.collision(this.currentShips);
		if(idxU.idxShip >= 0){
			ILoShip newShips = this.currentShips.removeShipByIndex(idxU.idxShip);
			// ILoShip newBullets = this.currentBullets.updateBulletByIndex(collisionIndex);
			return new MyGame(this.bullets, this.destroyedShip, newShips, this.currentTick, this.currentBullets);
		}else{
			return new MyGame(this.bullets, this.destroyedShip, this.currentShips, this.currentTick, this.currentBullets);
		}
	}

	public MyGame incrementGameTick(){
		return new MyGame(this.bullets, this.destroyedShip, this.currentShips, this.currentTick + 1, this.currentBullets);
	}

	// Generate random ships 
	public MyGame generateShips(){
		if(this.currentTick % 30 == 0){
		return new MyGame(this.bullets, this.destroyedShip, 
			this.currentShips.append(new Utility().randomShips(new Random().nextInt(10), this.WIDTH, this.HEIGHT)),
			this.currentTick,
			this.currentBullets);
		}else{
			return this;
		}
	}

	// Move all the ships and bullets at every tick
	public MyGame moveAll(){
		return new MyGame(this.bullets, this.destroyedShip, this.currentShips.moveAll(), this.currentTick, this.currentBullets.moveAll());
	}

	// remove all the ships out of the screen at every thick
	public MyGame removeOffScreen(){
		return new MyGame(this.bullets, this.destroyedShip, this.currentShips.filterOffScreenShip(this.WIDTH, this.HEIGHT), this.currentTick, this.currentBullets.filterOffScreenBullet(this.WIDTH, this.HEIGHT));
	}


	public WorldScene makeScene() {
		//Make a new scene.
		WorldScene scene = new WorldScene(this.WIDTH, this.HEIGHT);
		scene = this.bulletInfo(scene);
		scene = this.shipInfo(scene);
		scene = this.placeAllShips(scene);
		scene = this.placeAllBullets(scene);
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

	// Design all the (current)ships on the scene
	public WorldScene placeAllShips(WorldScene s){
		return this.currentShips.placeAll(s);
	}

	// Design all the (current)bullets on the scene
	public WorldScene placeAllBullets(WorldScene s){
		return this.currentBullets.placeAll(s);
	}

	// Handle the " " space key,
	//	if it's pressed then we remove a bullets and shoot.
	//	otherwise we return the current world
	public MyGame onKeyEvent(String key) {
		 if (key.equals(" ") && this.bullets > 0) {
			// this.shot() MyGame -> MyGame
			 return new MyGame(this.bullets - 1, this.destroyedShip, this.currentShips, this.currentTick, this.currentBullets.appendEl(new Utility().generateBullet(this.WIDTH, this.HEIGHT)));
		 }else {
			 return this;
		 }
	}
	public WorldScene makeEndScene() {
		return new WorldScene(this.WIDTH, this.HEIGHT).placeImageXY(new TextImage("Game Over", Color.red), this.WIDTH / 2, this.HEIGHT / 2);
	}

	public WorldEnd worldEnds() {
		if (this.bullets <= 0 && this.currentBullets instanceof MtLoBullet) {
			return new WorldEnd(true, this.makeEndScene());
		} else {
			return new WorldEnd(false, this.makeEndScene());
		}
	}
}

class ExamplesGame{
	ExamplesGame(){}

	int RADIUS_BULLET = 20;
	int RADIUS_SHIP = 30;

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

	Ship s0 = new Ship(p0, v0, RADIUS_SHIP);
	Ship s1 = new Ship(p1, v1, RADIUS_SHIP);
	Ship s2 = new Ship(p2, v2, RADIUS_SHIP);
	Ship s3 = new Ship(p3, v0, RADIUS_SHIP);
	Ship s4 = new Ship(p4, v1, RADIUS_SHIP);
	Ship s5 = new Ship(p5, v2, RADIUS_SHIP);

	Bullet b0 = new Bullet(p0, v0, RADIUS_BULLET);
	Bullet b1 = new Bullet(p1, v1, RADIUS_BULLET);
	Bullet b2 = new Bullet(p2, v2, RADIUS_BULLET);
	Bullet b3 = new Bullet(p3, v0, RADIUS_BULLET);
	Bullet b4 = new Bullet(p4, v1, RADIUS_BULLET);
	Bullet b5 = new Bullet(p5, v2, RADIUS_BULLET);

	ILoShip mtShip = new MtLoShip();
	ILoShip los0 = new ConsLoShip(s0, new ConsLoShip(s1, new ConsLoShip(s2, mtShip)));
	ILoShip los1 = new ConsLoShip(s3, new ConsLoShip(s4, new ConsLoShip(s5, mtShip)));	
	ILoShip los2 = new ConsLoShip(s3, new ConsLoShip(s4, mtShip));	
	ILoShip los3 = new ConsLoShip(s0, new ConsLoShip(s1, new ConsLoShip(s2, new ConsLoShip(s3, new ConsLoShip(s4, new ConsLoShip(s5, mtShip))))));	
	ILoShip los4 = new ConsLoShip(s3, new ConsLoShip(s4, new ConsLoShip(s5, new ConsLoShip(s3, new ConsLoShip(s4, mtShip)))));	

	ILoBullet mtBullet = new MtLoBullet();
	ILoBullet lob0 = new ConsLoBullet(b0, new ConsLoBullet(b1, new ConsLoBullet(b2, mtBullet)));
	ILoBullet lob1 = new ConsLoBullet(b3, new ConsLoBullet(b4, new ConsLoBullet(b5, mtBullet)));	
	ILoBullet lob2 = new ConsLoBullet(b3, new ConsLoBullet(b4, mtBullet));	

	ILoBullet lob3 = new ConsLoBullet(b0, new ConsLoBullet(b1, new ConsLoBullet(b2, new ConsLoBullet(b3, mtBullet))));	
	ILoBullet lob4 = new ConsLoBullet(b3, new ConsLoBullet(b4, new ConsLoBullet(b5, new ConsLoBullet(b1, mtBullet))));


	// testing the move method
	boolean testMove(Tester t){
		return
		t.checkExpect(s0.move(), s3)
		&&
		t.checkExpect(s1.move(), s4)
		&&
		t.checkExpect(s2.move(), s5)
		;
	}

	// testing the moveAll method
	boolean testMoveAll(Tester t){
		return
		t.checkExpect(los0.moveAll(), los1);
	}

	// testing the is off screen method
	boolean testIsOffScreen(Tester t){
		int WIDTH = 500;
		int HEIGHT = 500;
		return
		t.checkExpect(s0.isOffScreen(WIDTH, HEIGHT), false)
		&&
		t.checkExpect(s2.isOffScreen(WIDTH, HEIGHT), false)
		&&
		t.checkExpect(s5.isOffScreen(WIDTH, HEIGHT),  true)
		;
	}


	// testing the filter off screen ship method
	boolean testFilterOffScreenShip(Tester t){
		int WIDTH = 500;
		int HEIGHT = 500;
		return
		t.checkExpect(los0.filterOffScreenShip(WIDTH, HEIGHT), los0)
		&&
		t.checkExpect(los1.filterOffScreenShip(WIDTH, HEIGHT), los2)
		;
	}

	// testing the append method
	boolean testAppend(Tester t){
		return
		t.checkExpect(los0.append(los1),   los3)
		&&
		t.checkExpect(los1.append(los2),   los4)
		&&
		t.checkExpect(los1.append(mtShip), los1)
		;
	}
	
	// testing the append el method
	boolean testAppendEl(Tester t){
		return
		t.checkExpect(lob0.appendEl(b3), lob3)
		&&
		t.checkExpect(lob1.appendEl(b5), lob4)
		;
	}

	// testing the is colliding with method
	boolean testIsCollidingWith(Tester t){
		return
		t.checkExpect(s0.isCollidingWith(b0),  true)
		&&
		t.checkExpect(s1.isCollidingWith(b0), false)
		&&
		t.checkExpect(b0.isCollidingWith(s0),  true)
		&&
		t.checkExpect(b1.isCollidingWith(s0), false)
		&&
		t.checkExpect(b5.isCollidingWith(s5), true)
		;
	}

	// testing the collision with method
	boolean testCollision(Tester t){
		MyPosn p6 = new MyPosn(100, 100);
		MyPosn v3 = new MyPosn(4, 0);
		return
		t.checkExpect(lob0.collision(los0),  new IndexUtility(0,0))
		&&
		t.checkExpect(new ConsLoBullet(b5, mtBullet).collision(los4),  new IndexUtility(2,0))
		&&
		t.checkExpect(new ConsLoBullet(b5, mtBullet).collision(los1),  new IndexUtility(2,0))
		&&
		t.checkExpect(new ConsLoBullet(new Bullet(p6, v3, RADIUS_BULLET), mtBullet).collision(los1),  new IndexUtility(-1,-1))
		;
	}

	// testing remove ship by index
	boolean testRemoveShipByIndex(Tester t){
		ILoShip los0_ = new ConsLoShip(s1, new ConsLoShip(s2, mtShip));
		ILoShip los1_ = new ConsLoShip(s3, new ConsLoShip(s5, mtShip));	
		ILoShip los2_ = new ConsLoShip(s3, mtShip);	
		ILoShip los3_ = new ConsLoShip(s0, new ConsLoShip(s1, new ConsLoShip(s3, new ConsLoShip(s4, new ConsLoShip(s5, mtShip)))));	
		return
		t.checkExpect(los0.removeShipByIndex(0), los0_)
		&&
		t.checkExpect(los1.removeShipByIndex(1), los1_)
		&&
		t.checkExpect(los2.removeShipByIndex(1), los2_)
		&&
		t.checkExpect(los3.removeShipByIndex(7), los3)
		&&
		t.checkExpect(los3.removeShipByIndex(2), los3_)
		;
	}

	// testing the MyGame constructor
	boolean testGame(Tester t){
		return
		t.checkConstructorException(new IllegalArgumentException("The player cannot start without less or equal 0 bullets."), "MyGame", -1) 
		&&
		t.checkConstructorException(new IllegalArgumentException("The player cannot start without less or equal 0 bullets."), "MyGame",  0)
		;
	}

	// testing the MyGame constructor
	// boolean testGenerateShip(Tester t){
	// 	return
	// 	// MyGame(int bullets, int destroyedShip, ILoShip currentShips, int currentTick, ILoBullet currentBullets){
	// 	t.checkExpect(new MyGame(10, 0, los0, 30, mtBullet).generateShips(), mtShip) 
	// 	;
	// }

	// testing the big bang method
	boolean testBigBang(Tester t) {
		MyGame world = new MyGame(10).generateShips();
	   return world.bigBang(1000, 500, 1.0/28.0);
	 }
}
  
