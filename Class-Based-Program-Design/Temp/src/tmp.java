import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.Random;

// IFunc[W]ithout[A]rguments
interface IFuncWA<U> {
  U apply();
}

interface IFuncWAIndex<U> {
  U apply(int idx);
}

interface IFunc<T, U> {
  U apply(T t);
}

interface IFunc2<A1, A2, R> {
  R apply(A1 arg1, A2 arg2);
}

interface IPred2<A1, A2> {
  boolean apply(A1 arg1, A2 arg2);
}

interface IPred<T> {
  boolean apply(T t);
}

class MyPosn extends Posn {

  // standard constructor
  MyPosn(int x, int y) {
    super(x, y);
  }

  // constructor to convert from a Posn to a MyPosn
  MyPosn(Posn p) {
    this(p.x, p.y);
  }

  // add the given posn to the current posn
  MyPosn add(MyPosn vel) {
    return new MyPosn(this.x + vel.x, this.y + vel.y);
  }

  public int getCenterX(int radius) {
    return this.x + radius;
  }

  public int getCenterY(int radius) {
    return this.y + radius;
  }

  // produce the new velocity of a bullet after explosion
  // ASSUME: that only x can be 0, y is different than 0.
  public MyPosn explodeDirection(int idx, int n) {
    int newY = (int) Math.sin(idx * (360 / (n + 1))) * this.y;
    int newX = (int) Math.cos(idx * (360 / (n + 1)));
    if (newY != 0) {
      return new MyPosn(newX, newY);
    }
    else {
      return new MyPosn(newX, this.y);
    }
  }

  // get position x
  public int getPosX() {
    return this.x;
  }

  // get position y
  public int getPosY() {
    return this.y;
  }
}

interface IGameItem {

  // produce a game item with the update pos based on vel
  public IGameItem move();

  // produce true if a game item is NOT off screen; otherwise false
  public boolean isNotOffScreen(int width, int height);

  // produce a WorldImage that rapresetn the current drawed ship
  // On what should i drwa the image? The scene ? The world ?
  abstract public WorldImage draw();

  // place the ship with pos x y on the scene
  public WorldScene place(WorldScene s);

  // get center x
  public int getCenterX();

  // get center y
  public int getCenterY();

  // produce true if the ship is colliding with the given bullet; false otherwise
  public boolean isCollidingWith(IGameItem other);

  // get position x
  public int getPosX();

  // get position y
  public int getPosY();

  // get hit coutner
  public int getHitCounter();

  // get radius
  public int getRadius();

  // get pos
  public MyPosn getPos();

  // get vel
  public MyPosn getVel();
}

abstract class AGameItem implements IGameItem {
  MyPosn pos;
  MyPosn vel;
  int radius;

  AGameItem(MyPosn pos, MyPosn vel, int radius) {
    this.pos = pos;
    this.vel = vel;
    this.radius = radius;
  }

  // produce a game item with the update pos based on vel
  abstract public IGameItem move();

  // produce true if a game item is NOT off screen; otherwise false
  public boolean isNotOffScreen(int width, int height) {
    return this.getPosX() <= width && this.getPosY() <= height && this.getPosX() >= 0
        && this.getPosY() >= 0;
  }

  // produce a WorldImage that rapresetn the current drawed ship
  // On what should i drwa the image? The scene ? The world ?
  abstract public WorldImage draw();

  // place the ship with pos x y on the scene
  public WorldScene place(WorldScene s) {
    return s.placeImageXY(this.draw(), this.getPosX(), this.getPosY());
  }

  // get center x
  public int getCenterX() {
    return this.pos.getCenterX(this.radius);
  }

  // get center y
  public int getCenterY() {
    return this.pos.getCenterY(this.radius);
  }

  // produce true if the ship is collidign with the given bullet; false otherwise
  public boolean isCollidingWith(IGameItem other) {
    double dx = this.getCenterX() - other.getCenterX();
    double dy = this.getCenterY() - other.getCenterY();
    double distance = Math.sqrt(dx * dx + dy * dy);
    return distance < this.getRadius() + other.getRadius();
  }

  // get position x
  public int getPosX() {
    return this.pos.getPosX();
  }

  // get position y
  public int getPosY() {
    return this.pos.getPosY();
  }

  // get hit counter if exist, or throw a runtime error.
  abstract public int getHitCounter();

  // get radius
  public int getRadius() {
    return this.radius;
  }

  // get pos
  public MyPosn getPos() {
    return this.pos;
  }

  // get vel
  public MyPosn getVel() {
    return this.vel;
  }
}

class Ship extends AGameItem {
  Ship(MyPosn pos, MyPosn vel, int radius) {
    super(pos, vel, radius);
  }

  // produce a ship with the update pos based on vel
  public Ship move() {
    return new Ship(this.pos.add(this.vel), this.vel, this.radius);
  }

  // produce a worldimage that rapresetn the current drawed ship
  // on what should i drwa the image? the scene ? the world ?
  public WorldImage draw() {
    return new CircleImage(this.radius, OutlineMode.SOLID, Color.CYAN);
  }

  public int getHitCounter() {
    throw new RuntimeException("Ship doesn't have hit counter field");
  }
}

class Bullet extends AGameItem {
  int hitCounter;

  Bullet(MyPosn pos, MyPosn vel, int radius, int hitCounter) {
    super(pos, vel, radius);
    this.hitCounter = hitCounter;
  }

  // produce a bullet with the update pos based on vel
  public Bullet move() {
    return new Bullet(this.pos.add(this.vel), this.vel, this.radius, this.hitCounter);
  }

  // produce a WorldImage that rapresetn the current drawed bullet
  // On what should i drwa the image? The scene ? The world ?
  public WorldImage draw() {
    return new CircleImage(this.radius, OutlineMode.SOLID, Color.PINK);
  }

  public int getHitCounter() {
    return this.hitCounter;
  }

}

interface ILo<T> {
  // produce a list of ships, with updated pos
  <U> ILo<U> map(IFunc<T, U> fn);

  // produce a list of ships, without the ships offscreen
  ILo<T> filter(IPred<T> fn);

  <U> U foldr(IFunc2<T, U, U> fn, U base);

  // append the given list (as a tail) on the current list
  ILo<T> append(ILo<T> that);

  // append the given el (as a tail) on the current list
  ILo<T> appendEl(T that);

  // remove the ship from the list based on the given index
  ILo<T> filterIdx(IPred2<Integer, Integer> fn, int idx, int idxToRemove);

  // produce the length of the current list
  int length();

  // produce the length of the current list
  <U> ILo<U> buildList(IFuncWA<U> fn, int cont);

  // produce the length of the current list
  <U> ILo<U> buildListWithIndex(IFuncWAIndex<U> fn, int cont, int idx);

}

class ConsLo<T> implements ILo<T> {
  T first;
  ILo<T> rest;

  ConsLo(T first, ILo<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public <U> ILo<U> map(IFunc<T, U> fn) {
    return new ConsLo<U>(fn.apply(this.first), this.rest.map(fn));
  }

  public ILo<T> filter(IPred<T> fn) {
    if (fn.apply(this.first)) {
      return new ConsLo<T>(this.first, this.rest.filter(fn));
    }
    else {
      return this.rest.filter(fn);
    }
  }

  public <U> U foldr(IFunc2<T, U, U> fn, U base) {
    return fn.apply(this.first, this.rest.foldr(fn, base));
  }

  public ILo<T> append(ILo<T> that) {
    return new ConsLo<T>(this.first, this.rest.append(that));
  }

  public ILo<T> appendEl(T that) {
    return new ConsLo<T>(this.first, this.rest.appendEl(that));
  }

  public ILo<T> filterIdx(IPred2<Integer, Integer> fn, int idx, int idxToRemove) {
    if (fn.apply(idx, idxToRemove)) {
      return new ConsLo<T>(this.first, this.rest.filterIdx(fn, idx + 1, idxToRemove));
    }
    else {
      return this.rest.filterIdx(fn, idx + 1, idxToRemove);
    }
  }

  public int length() {
    return 1 + this.rest.length();
  }

  public <U> ILo<U> buildList(IFuncWA<U> fn, int cont) {
    throw new RuntimeException("Cannot build a list, already builded.");
  }

  public <U> ILo<U> buildListWithIndex(IFuncWAIndex<U> fn, int cont, int idx) {
    throw new RuntimeException("Cannot build a list, already builded.");
  }
}

class MtLo<T> implements ILo<T> {
  MtLo() {
  }

  public <U> ILo<U> map(IFunc<T, U> fn) {
    return new MtLo<U>();
  }

  public ILo<T> filter(IPred<T> fn) {
    return new MtLo<T>();
  }

  public <U> U foldr(IFunc2<T, U, U> func, U base) {
    return base;
  }

  public ILo<T> append(ILo<T> that) {
    return that;
  }

  public ILo<T> appendEl(T that) {
    return new ConsLo<T>(that, this);
  }

  public ILo<T> filterIdx(IPred2<Integer, Integer> fn, int idx, int idxToRemove) {
    return this;
  }

  public <U> ILo<U> buildList(IFuncWA<U> fn, int cont) {
    if (cont == 0) {
      return new MtLo<U>();
    }
    else {
      return new ConsLo<U>(fn.apply(), buildList(fn, cont - 1));
    }
  }

  public int length() {
    return 0;
  }

  public <U> ILo<U> buildListWithIndex(IFuncWAIndex<U> fn, int cont, int idx) {
    if (cont == 0) {
      return new MtLo<U>();
    }
    else {
      return new ConsLo<U>(fn.apply(idx), buildListWithIndex(fn, cont - 1, idx + 1));
    }
  }
}

class UtilityCollision {
  ILo<IGameItem> updatedShips;
  ILo<IGameItem> updatedBullets;

  UtilityCollision() {
    this.updatedShips = new MtLo<IGameItem>();
    this.updatedBullets = new MtLo<IGameItem>();
  }

  UtilityCollision(ILo<IGameItem> updatedShips, ILo<IGameItem> updatedBullets) {
    this.updatedShips = updatedShips;
    this.updatedBullets = updatedBullets;
  }

  public UtilityCollision setUpdatedShips(ILo<IGameItem> ships) {
    return new UtilityCollision(ships, this.updatedBullets);
  }

  public UtilityCollision setUpdatedBullet(ILo<IGameItem> bullets) {
    return new UtilityCollision(this.updatedShips, bullets);
  }

  public UtilityCollision addBullet(IGameItem bullet) {
    return new UtilityCollision(this.updatedShips, this.updatedBullets.appendEl(bullet));
  }

  public UtilityCollision addBullets(ILo<IGameItem> bullets) {
    return new UtilityCollision(this.updatedShips, this.updatedBullets.append(bullets));
  }
}

class CollisionWithBullet implements IPred<IGameItem> {
  IGameItem b;

  CollisionWithBullet(IGameItem b) {
    this.b = b;
  }

  public boolean apply(IGameItem s) {
    return !this.b.isCollidingWith(s);
  }
}

// Increase the size of the bullet and increase the hitCounter
class SpreadBullets implements IFuncWA<IGameItem> {
  IGameItem b;
  int counter;

  SpreadBullets(IGameItem b, int counter) {
    this.b = b;
    this.counter = counter;
  }

  public IGameItem apply() {
    // TODO: Make this constants
    int incrSize = 2;
    int maxSize = 10;

    return new Bullet(b.getPos(), b.getVel(), Math.min(maxSize, b.getRadius() + incrSize),
        b.getHitCounter() + 1);
  }
}

// ref can be anything, here in this function object, 
// the ref is a IGameItem
class GenerateBulletWithRef implements IFuncWAIndex<IGameItem> {
  IGameItem ref;

  GenerateBulletWithRef(IGameItem ref) {
    this.ref = ref;
  }

  public IGameItem apply(int idx) {
    return new Bullet(this.ref.getPos(),
        this.ref.getVel().explodeDirection(idx, this.ref.getHitCounter() + 2), this.ref.getRadius(),
        this.ref.getHitCounter());
  }
}

class Collision implements IFunc2<IGameItem, UtilityCollision, UtilityCollision> {
  ILo<IGameItem> los;

  Collision(ILo<IGameItem> los) {
    this.los = los;
  }

  public UtilityCollision apply(IGameItem b, UtilityCollision u) {
    ILo<IGameItem> updatedShips = los.filter(new CollisionWithBullet(b));

    if (updatedShips.length() != los.length()) {
      int howManyBullets = b.getHitCounter() + 2;
      ILo<IGameItem> spreadBullets = new MtLo<IGameItem>().buildListWithIndex(
          new GenerateBulletWithRef(new SpreadBullets(b, howManyBullets).apply()), howManyBullets,
          0);
      return u.setUpdatedShips(updatedShips).addBullets(spreadBullets);
    }
    else {
      return u.addBullet(b);
    }
  }
}

class MoveAll implements IFunc<IGameItem, IGameItem> {
  public IGameItem apply(IGameItem t) {
    return t.move();
  }
}

class GenerateShip implements IFuncWA<IGameItem> {
  int width;
  int height;

  GenerateShip(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public IGameItem apply() {
    int velocity = 5;
    int edge = 50;
    double calculate_size = (1.0 / 30.0) * this.height;
    int sizeShip = (int) calculate_size;
    if (new Random().nextInt(2) == 0) {
      return new Ship(new MyPosn(0,
          // Preventing the circle to spawn to much near to the top and bottom edge
          Math.max(edge, new Random().nextInt(this.height) - edge)), new MyPosn(velocity, 0),
          // NOTE: this shouldn't be the way, need a global constant
          sizeShip);
    }
    else {
      return new Ship(new MyPosn(this.width,
          // Preventing the circle to spawn to much near to the top and bottom edge
          Math.max(edge, new Random().nextInt(this.height) - edge)), new MyPosn(-velocity, 0),
          // NOTE: this shouldn't be the way, need a global constant
          sizeShip);
    }
  }
}

class GenerateBullet implements IFuncWA<IGameItem> {
  int width;
  int height;

  GenerateBullet(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public IGameItem apply() {
    int edge = 10;
    int RADIUS_BULLET = 2;
    // NOTE: Change the velocity here.
    return new Bullet(new MyPosn(this.width / 2, this.height - edge), new MyPosn(0, -3),
        RADIUS_BULLET, 0);
  }
}

class PlaceAll implements IFunc2<IGameItem, WorldScene, WorldScene> {
  public WorldScene apply(IGameItem t, WorldScene s) {
    return s.placeImageXY(t.draw(), t.getPosX(), t.getPosY());
  }
}

class OffScreen implements IPred<IGameItem> {
  int WIDTH;
  int HEIGHT;

  OffScreen(int WIDTH, int HEIGHT) {
    this.WIDTH = WIDTH;
    this.HEIGHT = HEIGHT;
  }

  public boolean apply(IGameItem t) {
    return t.isNotOffScreen(this.WIDTH, this.HEIGHT);
  }
}

class RemoveByIndex implements IPred2<Integer, Integer> {
  public boolean apply(Integer idx, Integer idxToRemove) {
    return idx != idxToRemove;
  }
}

class MyGame extends World {
  int WIDTH = 500;
  int HEIGHT = 300;
  int currentTick;

  int bullets;
  int destroyedShip;
  ILo<IGameItem> currentShips;
  ILo<IGameItem> currentBullets;

  MyGame(int bullets) {
    if (bullets <= 0) {
      throw new IllegalArgumentException(
          "The player cannot start without less or equal 0 bullets.");
    }
    this.WIDTH = WIDTH;
    this.HEIGHT = HEIGHT;
    this.currentTick = 1;
    this.destroyedShip = 0;
    this.currentShips = new MtLo<IGameItem>().buildList(new GenerateShip(this.WIDTH, this.HEIGHT),
        new Random().nextInt(4));
    this.bullets = bullets;
    // The current bullets on the screen
    this.currentBullets = new MtLo<IGameItem>();
  }

  MyGame(int bullets, int destroyedShip, ILo<IGameItem> currentShips, int currentTick,
      ILo<IGameItem> currentBullets) {
    this.WIDTH = WIDTH;
    this.HEIGHT = HEIGHT;
    this.bullets = bullets;
    this.destroyedShip = destroyedShip;
    this.currentShips = currentShips;
    this.currentTick = currentTick;
    this.currentBullets = currentBullets;
  }

  public MyGame onTick() {
    return this.moveAll().removeOffScreen().checkCollision().incrementGameTick().generateShips();
  }

  public MyGame checkCollision() {
    UtilityCollision updatedElements = this.currentBullets.foldr(new Collision(this.currentShips),
        new UtilityCollision());
    if (updatedElements.updatedShips instanceof ConsLo) {
      ILo<IGameItem> newShips = updatedElements.updatedShips;
      ILo<IGameItem> newBullets = updatedElements.updatedBullets;
      return new MyGame(this.bullets, this.destroyedShip, newShips, this.currentTick, newBullets);
    }
    else {
      return new MyGame(this.bullets, this.destroyedShip, this.currentShips, this.currentTick,
          this.currentBullets);
    }
  }

  public MyGame incrementGameTick() {
    return new MyGame(this.bullets, this.destroyedShip, this.currentShips, this.currentTick + 1,
        this.currentBullets);
  }

  // Generate random ships
  public MyGame generateShips() {
    if (this.currentTick % 28 == 0) {
      ILo<IGameItem> newShips = new MtLo<IGameItem>()
          .buildList(new GenerateShip(this.WIDTH, this.HEIGHT), new Random().nextInt(4));
      return new MyGame(this.bullets, this.destroyedShip, this.currentShips.append(newShips),
          this.currentTick, this.currentBullets);
    }
    else {
      return this;
    }
  }

  // Move all the ships and bullets at every tick
  public MyGame moveAll() {
    ILo<IGameItem> movingShips = this.currentShips.map(new MoveAll());
    ILo<IGameItem> movingBullets = this.currentBullets.map(new MoveAll());
    return new MyGame(this.bullets, this.destroyedShip, movingShips, this.currentTick,
        movingBullets);
  }

  // remove all the ships out of the screen at every thick
  public MyGame removeOffScreen() {
    ILo<IGameItem> los = this.currentShips.filter(new OffScreen(this.WIDTH, this.HEIGHT));
    ILo<IGameItem> lob = this.currentBullets.filter(new OffScreen(this.WIDTH, this.HEIGHT));
    return new MyGame(this.bullets, this.destroyedShip, los, this.currentTick, lob);
  }

  public WorldScene makeScene() {
    // Make a new scene.
    WorldScene s = new WorldScene(this.WIDTH, this.HEIGHT);
    // Show the current number of bullets on the scene
    s = s.placeImageXY(new TextImage("Bullets: " + this.bullets, Color.black), this.WIDTH - 50, 10);
    // Show the current number of destroyed Ships on the scene
    s = s.placeImageXY(new TextImage("Ship: " + this.destroyedShip, Color.black), this.WIDTH - 50,
        30);
    // Place all the ships on the scene
    s = this.currentShips.foldr(new PlaceAll(), s);
    // Place all the bullets on the scene
    s = this.currentBullets.foldr(new PlaceAll(), s);
    return s;
  }

  // Handle the " " space key,
  // if it's pressed then we remove a bullets and shoot.
  // otherwise we return the current world
  public MyGame onKeyEvent(String key) {
    if (key.equals(" ") && this.bullets > 0) {
      ILo<IGameItem> newBullets = new MtLo<IGameItem>()
          .buildList(new GenerateBullet(this.WIDTH, this.HEIGHT), 1);
      return new MyGame(this.bullets - 1, this.destroyedShip, this.currentShips, this.currentTick,
          this.currentBullets.append(newBullets));
    }
    else {
      return this;
    }
  }

  public WorldScene makeEndScene() {
    return new WorldScene(this.WIDTH, this.HEIGHT)
        .placeImageXY(new TextImage("Game Over", Color.black), this.WIDTH / 2, this.HEIGHT / 2);
  }

  public WorldEnd worldEnds() {
    if (this.bullets <= 0 && this.currentBullets instanceof MtLo) {
      return new WorldEnd(true, this.makeEndScene());
    }
    else {
      return new WorldEnd(false, this.makeEndScene());
    }
  }
}

class Examples {
  Examples() {
  }

  int WIDTH_GAME = 500;
  int HEIGHT_GAME = 300;
  int RADIUS_BULLET = 2;
  double calc_radius_ship = (1.0 / 30.0) * HEIGHT_GAME;
  int RADIUS_SHIP = (int) calc_radius_ship;

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

  IGameItem s0 = new Ship(p0, v0, RADIUS_SHIP);
  IGameItem s1 = new Ship(p1, v1, RADIUS_SHIP);
  IGameItem s2 = new Ship(p2, v2, RADIUS_SHIP);
  IGameItem s3 = new Ship(p3, v0, RADIUS_SHIP);
  IGameItem s4 = new Ship(p4, v1, RADIUS_SHIP);
  IGameItem s5 = new Ship(p5, v2, RADIUS_SHIP);

  IGameItem b0 = new Bullet(p0, v0, RADIUS_BULLET, 0);
  IGameItem b1 = new Bullet(p1, v1, RADIUS_BULLET, 0);
  IGameItem b2 = new Bullet(p2, v2, RADIUS_BULLET, 0);
  IGameItem b3 = new Bullet(p3, v0, RADIUS_BULLET, 0);
  IGameItem b4 = new Bullet(p4, v1, RADIUS_BULLET, 0);
  IGameItem b5 = new Bullet(p5, v2, RADIUS_BULLET, 0);

  ILo<IGameItem> mtIGameItem = new MtLo<IGameItem>();

  // List of ships
  ILo<IGameItem> los0 = new ConsLo<IGameItem>(s0,
      new ConsLo<IGameItem>(s1, new ConsLo<IGameItem>(s2, mtIGameItem)));

  ILo<IGameItem> los1 = new ConsLo<IGameItem>(s3,
      new ConsLo<IGameItem>(s4, new ConsLo<IGameItem>(s5, mtIGameItem)));

  ILo<IGameItem> los2 = new ConsLo<IGameItem>(s3, new ConsLo<IGameItem>(s4, mtIGameItem));

  ILo<IGameItem> los3 = new ConsLo<IGameItem>(s0,
      new ConsLo<IGameItem>(s1, new ConsLo<IGameItem>(s2, new ConsLo<IGameItem>(s3,
          new ConsLo<IGameItem>(s4, new ConsLo<IGameItem>(s5, mtIGameItem))))));

  ILo<IGameItem> los4 = new ConsLo<IGameItem>(s3,
      new ConsLo<IGameItem>(s4, new ConsLo<IGameItem>(s5,
          new ConsLo<IGameItem>(s3, new ConsLo<IGameItem>(s4, mtIGameItem)))));

  // List of bullets
  ILo<IGameItem> lob0 = new ConsLo<IGameItem>(b0,
      new ConsLo<IGameItem>(b1, new ConsLo<IGameItem>(b2, mtIGameItem)));

  ILo<IGameItem> lob1 = new ConsLo<IGameItem>(b3,
      new ConsLo<IGameItem>(b4, new ConsLo<IGameItem>(b5, mtIGameItem)));

  ILo<IGameItem> lob2 = new ConsLo<IGameItem>(b3, new ConsLo<IGameItem>(b4, mtIGameItem));

  ILo<IGameItem> lob3 = new ConsLo<IGameItem>(b0,
      new ConsLo<IGameItem>(b1, new ConsLo<IGameItem>(b2, new ConsLo<IGameItem>(b3, mtIGameItem))));

  ILo<IGameItem> lob4 = new ConsLo<IGameItem>(b3,
      new ConsLo<IGameItem>(b4, new ConsLo<IGameItem>(b5, new ConsLo<IGameItem>(b1, mtIGameItem))));

  // Functions Objects
  IFunc<IGameItem, IGameItem> mvA = new MoveAll();
  IPred<IGameItem> oS = new OffScreen(WIDTH_GAME, HEIGHT_GAME);
  IPred2<Integer, Integer> rBI = new RemoveByIndex();
  IFuncWA<IGameItem> gS = new GenerateShip(WIDTH_GAME, HEIGHT_GAME);
  IFuncWA<IGameItem> gB = new GenerateBullet(WIDTH_GAME, HEIGHT_GAME);

  IFuncWAIndex<IGameItem> gBWR = new GenerateBulletWithRef(new Bullet(p0, v0, RADIUS_BULLET, 0));

  // testing the move method
  boolean testMove(Tester t) {
    return t.checkExpect(s0.move(), s3) && t.checkExpect(s1.move(), s4)
        && t.checkExpect(s2.move(), s5);
  }

  boolean testMoveAll(Tester t) {
    IGameItem b0_ = new Bullet(new MyPosn(WIDTH_GAME / 2, HEIGHT_GAME - 10), new MyPosn(0, -6),
        RADIUS_BULLET, 0);
    ILo<IGameItem> lob4_ = new ConsLo<IGameItem>(b0_, new ConsLo<IGameItem>(b0_,
        new ConsLo<IGameItem>(b0_, new ConsLo<IGameItem>(b0_, mtIGameItem))));
    return t.checkExpect(los0.map(mvA), los1) && t.checkExpect(lob0.map(mvA), lob1)
        && t.checkExpect(new MtLo<IGameItem>().buildList(gB, 4).map(mvA), lob4_);
  }

  // testing generate function object
  boolean testGenerate(Tester t) {
    Bullet b0_ = new Bullet(new MyPosn(WIDTH_GAME / 2, HEIGHT_GAME - 10), new MyPosn(0, -6),
        RADIUS_BULLET, 0);
    ILo<IGameItem> lob0_ = new ConsLo<IGameItem>(b0_, mtIGameItem);
    return
    // Generate Bullet
    t.checkExpect(mtIGameItem.buildList(gB, 1), lob0_);
  }

  boolean testIsOffScreen(Tester t) {
    int WIDTH = 500;
    int HEIGHT = 500;
    return t.checkExpect(s0.isNotOffScreen(WIDTH, HEIGHT), true)
        && t.checkExpect(s2.isNotOffScreen(WIDTH, HEIGHT), true)
        && t.checkExpect(s5.isNotOffScreen(WIDTH, HEIGHT), false);
  }

  // testing the filter off screen ship method
  boolean testFilterOffScreenShip(Tester t) {
    ILo<IGameItem> los0_ = new ConsLo<IGameItem>(s0, new ConsLo<IGameItem>(s1, mtIGameItem));
    return t.checkExpect(los0.filter(oS), los0_) && t.checkExpect(los1.filter(oS), los2);
  }

  // testing the append method
  boolean testAppend(Tester t) {
    return t.checkExpect(los0.append(los1), los3) && t.checkExpect(los1.append(los2), los4)
        && t.checkExpect(los1.append(mtIGameItem), los1);
  }

  // testing the append el method
  boolean testAppendEl(Tester t) {
    return t.checkExpect(lob0.appendEl(b3), lob3) && t.checkExpect(lob1.appendEl(b5), lob4);
  }

  // testing the is colliding with method
  boolean testIsCollidingWith(Tester t) {
    return t.checkExpect(s0.isCollidingWith(b0), true)
        && t.checkExpect(s1.isCollidingWith(b0), false)
        && t.checkExpect(b0.isCollidingWith(s0), true)
        && t.checkExpect(b1.isCollidingWith(s0), false)
        && t.checkExpect(b5.isCollidingWith(s5), true);
  }

  // testing the length method
  boolean testLength(Tester t) {
    return t.checkExpect(los0.length(), 3) && t.checkExpect(los2.length(), 2)
        && t.checkExpect(lob0.length(), 3) && t.checkExpect(lob2.length(), 2)
        && t.checkExpect(mtIGameItem.length(), 0);
  }

  // testing remove ship by index
  boolean testRemoveShipByIndex(Tester t) {
    ILo<IGameItem> los0_ = new ConsLo<IGameItem>(s1, new ConsLo<IGameItem>(s2, mtIGameItem));
    ILo<IGameItem> los1_ = new ConsLo<IGameItem>(s3, new ConsLo<IGameItem>(s5, mtIGameItem));
    ILo<IGameItem> los2_ = new ConsLo<IGameItem>(s3, mtIGameItem);
    ILo<IGameItem> los3_ = new ConsLo<IGameItem>(s0,
        new ConsLo<IGameItem>(s1, new ConsLo<IGameItem>(s3,
            new ConsLo<IGameItem>(s4, new ConsLo<IGameItem>(s5, mtIGameItem)))));
    return t.checkExpect(los0.filterIdx(rBI, 0, 0), los0_)
        && t.checkExpect(los1.filterIdx(rBI, 0, 1), los1_)
        && t.checkExpect(los2.filterIdx(rBI, 0, 1), los2_)
        && t.checkExpect(los3.filterIdx(rBI, 0, 7), los3)
        && t.checkExpect(los3.filterIdx(rBI, 0, 2), los3_);
  }

  // testing the build list 
  boolean testBuildList(Tester t) {
    ILo<IGameItem> lob0_ = new ConsLo<IGameItem>(b0,
        new ConsLo<IGameItem>(b0, new ConsLo<IGameItem>(b0, mtIGameItem)));
    return t.checkExpect(new MtLo<IGameItem>().buildListWithIndex(gBWR, 3, 0), lob0_);
  }

  // testing the MyGame constructor
  boolean testGame(Tester t){
    return
    t.checkConstructorException(new IllegalArgumentException("The player cannot start without less or equal 0 bullets."), "MyGame", -1)
    &&
    t.checkConstructorException(new IllegalArgumentException("The player cannot start without less or equal 0 bullets."), "MyGame", 0)
  ;
  }

  // testing explode direction
  boolean testExplodeDirection(Tester t) {
    return t.checkExpect(new MyPosn(0, 3).explodeDirection(0, 0), new MyPosn(1, 3))
        && t.checkExpect(new MyPosn(0, 3).explodeDirection(0, 1), new MyPosn(0, 3))
        && t.checkExpect(new MyPosn(0, 3).explodeDirection(0, 2), new MyPosn(-1, 3))
        && t.checkExpect(new MyPosn(0, 3).explodeDirection(0, 3), new MyPosn(0, -3));
  }

  // testing the big bang method
  boolean testBigBang(Tester t) {
    double TICK_RATE = 1.0 / 28.0;
    int INITIAL_BULLET = 10;
    MyGame world = new MyGame(INITIAL_BULLET);
    return world.bigBang(WIDTH_GAME, HEIGHT_GAME, TICK_RATE);
  }
}
