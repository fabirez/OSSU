import tester.*;

interface IShape {
 
}
class Circle implements IShape {
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
}
class Rect implements IShape {
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
}

class ExamplesIShape{
  ExamplesIShape(){}
  Circle c1 = new Circle(3, 4, 5);
  Circle c2 = new Circle(4, 5, 6);
  Circle c3 = new Circle(3, 4, 5);
  Rect r1 = new Rect(3, 4, 5, 5);
  Rect r2 = new Rect(4, 5, 6, 7);
  Rect r3 = new Rect(3, 4, 5, 5);
   
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
}
