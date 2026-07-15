import tester.*;

interface IShape {
  boolean sameShape(IShape that);
  boolean sameCircle(Circle that);
  boolean sameRect(Rect that);
  boolean sameSquare(Square that);
}


abstract class AShape implements IShape{
  AShape(){}

  public boolean sameCircle(Circle that){
    return false;
  }
  public boolean sameRect(Rect that){
    return false;
  }
  public boolean sameSquare(Square that){
    return false;
  }
}

class Circle extends AShape {
  int x, y;
  int radius;
  Circle(int x, int y, int radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
  }
  
  public boolean sameCircle(Circle that) {
    /* Template:
     * Fields:
     * this.x, this.y, this.radius
     *
     * Fields of parameters:
     * that.x, that.y, that.radius
     */
    return this.x == that.x &&
           this.y == that.y &&
           this.radius == that.radius;
  }

  public boolean sameShape(IShape that) {
      return that.sameCircle(this);
  }

}
class Rect extends AShape {
  int x, y;
  int w, h;

  Rect(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  
  public boolean sameRect(Rect that) {
    /* Template:
     * Fields:
     * this.x, this.y, this.w, this.h
     *
     * Fields of parameters:
     * that.x, that.y, that.w, that.h
     */
    return this.x == that.x &&
           this.y == that.y &&
           this.w == that.w &&
           this.h == that.h;
  }

  public boolean sameShape(IShape that) {
      return that.sameRect(this);
  }
}

// Since our priority is the "samness" i think it's ebtter
// to extend it from AShape than Rect
class Square extends AShape {
  int x;
  int y;
  int size;

  Square(int x, int y, int size){
    this.x = x;
    this.y = y;
    this.size = size;
  } 

  public boolean sameShape(IShape that) {
      return that.sameSquare(this);
  }

  public boolean sameSquare(Square that) {
    /* Template:
     * Fields:
     * this.x, this.y, this.size
     *
     * Fields of parameters:
     * that.x, that.y, that.size
     */
    return this.x == that.x &&
           this.y == that.y &&
           this.size == that.size;
  }
}


class ExamplesIShape{
  ExamplesIShape(){}
  Circle c1 = new Circle(3, 4, 5);
  Circle c2 = new Circle(4, 5, 6);
  Circle c3 = new Circle(3, 4, 5);
  Rect r1 = new Rect(3, 4, 5, 5);
  Rect r2 = new Rect(4, 5, 6, 7);
  Rect r3 = new Rect(3, 4, 5, 5);
  Square s1 = new Square(3, 4, 5);
  Square s2 = new Square(4, 5, 7);
  Square s3 = new Square(3, 4, 5);
   
  boolean testSameCircle(Tester t){
    return
    t.checkExpect(c1.sameCircle(c2), false) &&
    t.checkExpect(c2.sameCircle(c1), false) &&
    t.checkExpect(c1.sameCircle(c3), true)  &&
    t.checkExpect(c3.sameCircle(c1), true);
  }
   
  boolean testSameRect(Tester t){
    return
    t.checkExpect(r1.sameRect(r2), false) &&
    t.checkExpect(r2.sameRect(r1), false) &&
    t.checkExpect(r1.sameRect(r3), true)  &&
    t.checkExpect(r3.sameRect(r1), true);
  }
  
  boolean testSameSquare(Tester t){
    return
    t.checkExpect(s1.sameSquare(s2), false) &&
    t.checkExpect(s2.sameSquare(s1), false) &&
    t.checkExpect(s1.sameSquare(s3), true)  &&
    t.checkExpect(s3.sameSquare(s1), true);
  }

  boolean testSameShape(Tester t){
    return 
    t.checkExpect(r1.sameShape(c1), false) &&
    t.checkExpect(c1.sameShape(r1), false) &&
    t.checkExpect(s1.sameShape(r1), false) &&
    t.checkExpect(r1.sameShape(s1), false) &&
    t.checkExpect(c1.sameShape(s1), false) &&
    t.checkExpect(c1.sameShape(c3),  true) &&
    t.checkExpect(r1.sameShape(r3),  true) &&
    t.checkExpect(s1.sameShape(s3),  true);
  }
}
