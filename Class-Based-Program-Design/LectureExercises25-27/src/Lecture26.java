import tester.*;
import java.util.HashMap;
/*Do Now!
  What could possibly go wrong with the approach mentioned above?
*/

// > What if we have two first name with the same length ?
// > One will override the one already in memory, and we will lost any record
//
/*Do Now!
	Design a single implementation for equals that will suffice for Circle, Square and Rect.
 */

/*
// NOTE: My solution
public boolean equals(Object other){
  if(other instanceof Circle){
    return sameCircle((Circle) other);
  }else if(other instanceof Square){
    return sameCircle((Square) other);
  }else if(other instanceof Rect){
    return sameCircle((Rect) other);
  }else{
    return false;
  }
}

// NOTE: Lecture solution
public boolean equals(Object other) {
  if (!other instanceof IShape) { return false; }
  // this cast is safe, because we just checked instanceof
  IShape that = (IShape)other;
  return this.sameShape(that);
}
*/

/*Do Now!
	Now that we’ve overridden equals,
	our shape classes have violated an important principle.
	What principle is it? Fix it!
*/

// > We need to implement also the custom hashCode function, for each class / shape.

interface IShape {
  boolean sameShape(IShape that);
  boolean sameCircle(Circle that);
  boolean sameRect(Rect that);
}
abstract class AShape implements IShape {
  public boolean sameCircle(Circle that) { return false; }
  public boolean sameRect(Rect that) { return false; }
  public boolean equals(Object that) {
    if(!(that instanceof IShape)) { return false; }
    return this.sameShape((IShape) that);
  }
}

class Circle extends AShape {
  int radius;
  Circle(int radius) { this.radius = radius; }
  public int hashCode(){ return this.radius * 10000;}
  public boolean sameShape(IShape that) { return that.sameCircle(this); }
  public boolean sameCircle(Circle that) { return that.radius == this.radius && this.hashCode() == that.hashCode(); }
}

class Rect extends AShape {
  int w, h;
  Rect(int w, int h) { this.w = w; this.h = h; }
  public int hashCode(){ return (this.w + this.h) * 10000;}
  public boolean sameRect(Rect that) { return this.w == that.w && this.h == that.h && this.hashCode() == that.hashCode(); }
  public boolean sameShape(IShape that) { return that.sameRect(this); }
}


class ExamplesLecture26{
  ExamplesLecture26(){}
  Circle c1 = new Circle(5);
  Circle c2 = new Circle(6);
  Circle c3 = new Circle(5);
  Rect r1 = new Rect(5, 5);
  Rect r2 = new Rect(6, 7);
  Rect r3 = new Rect(5, 5);

  void testEquals(Tester t){
    t.checkExpect(c1.equals(c2), false);
    t.checkExpect(c1.hashCode() == c2.hashCode(), false);

    t.checkExpect(c1.equals(c3), true);
    t.checkExpect(c1.hashCode() == c3.hashCode(), true);

    t.checkExpect(c3.equals(c2), false);
    t.checkExpect(c3.hashCode() == c2.hashCode(), false);


    t.checkExpect(r1.equals(r2), false);
    t.checkExpect(r1.hashCode() == r2.hashCode(), false);

    t.checkExpect(r1.equals(r3), true);
    t.checkExpect(r1.hashCode() == r3.hashCode(), true);

    t.checkExpect(r3.equals(r2), false);
    t.checkExpect(r3.hashCode() == r2.hashCode(), false);
  }
}
