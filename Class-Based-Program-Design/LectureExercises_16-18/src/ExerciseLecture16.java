/*Exercise
Compare this implementation to the implementation of sameShape,
and figure out what methods are the analogues of sameShape,
sameCircle and sameRect,
and what roles the IShape and IShape2Double interfaces play.
*/

/* 
  public boolean sameShape(IShape that) {
      return that.sameCircle(this);
  }

  public double beAppliedToBy(IShape2Double func) {
    return func.applyToCircle(this);
  }
*/
// > `beAppliedToBy`, It's going to replace sameShape
// > `applyToCircle`, It's going to replace sameCircle

// > `IShape` gives us the permission to interact with all of the shapes.
// > `IShape2Double` call the method using the information given by IShape. 
//      (if it's a circle, if it's a rect)

/*Exercise
  Do the same thing, this time trying to compute the perimeters of the shapes.
*/
import tester.*;
// Represents a function object defined over Shapes that returns a Double
interface IShape2Double {
  double applyToCircle(Circle circle);
  double applyToRect(Rect rect);
  double applyToSquare(Square square);
}

// Implements a function taking a Shape and returning a Double,
// that computes the area of the given shape
class ShapeArea implements IShape2Double {
  public double applyToCircle(Circle circle) {
    return Math.PI * circle.radius * circle.radius;
  }
  public double applyToRect(Rect rect) {
    return rect.w * rect.h * 1.0;
  }

  public double applyToSquare(Square square) {
    return square.size * square.size * 1.0;
  }
}

class ShapePerimeter implements IShape2Double {
  public double applyToCircle(Circle circle) {
    return 2 * Math.PI * circle.radius;
  }
  public double applyToRect(Rect rect) {
    return 2 * (rect.w * rect.h) * 1.0;
  }

  public double applyToSquare(Square square) {
    return 2 * (square.size * square.size) * 1.0;
  }
}

interface IFunc<T,U>
{
  <U> U apply (T t);
}

interface IShapeVisitor<R> extends IFunc<IShape, R>{
  R visitCircle(Circle c);
  R visitRect(Rect r);
  R visitSquare(Square s);
}

class ShapeAreaVisitor implements IShapeVisitor<Double> {
  // Everything from the IShapeVisitor interface:
  public Double visitCircle(Circle c) { return Math.PI * c.radius * c.radius; }
  public Double visitSquare(Square s) { return s.size * s.size * 1.0; }
  public Double visitRect(Rect r) { return r.w * r.h * 1.0; }
 
  // Everything from the IFunc interface:
  public Double apply(IShape s) { return s.accept(this); }
}


interface IShape {
  // To return the result of applying the given function to this shape
  double beAppliedToBy(IShape2Double func);

  <R> R accept(IShapeVisitor<R> visitor);
}

class Circle implements IShape {
  int x, y;
  int radius;
  String color;
  Circle(int x, int y, int r, String color) {
    this.x = x;
    this.y = y;
    this.radius = r;
    this.color = color;
  }

  // To return the result of applying the given function to this Circle
  public double beAppliedToBy(IShape2Double func) {
    return func.applyToCircle(this);
  }

  // To return the result of applying the given visitor to this Circle
  public <R> R accept(IShapeVisitor<R> visitor) { return visitor.visitCircle(this); }
}
class Rect implements IShape {
  int x, y, w, h;
  String color;
  Rect(int x, int y, int w, int h, String color) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.color = color;
  }

  // To return the result of applying the given function to this Rect
  public double beAppliedToBy(IShape2Double func) {
    return func.applyToRect(this);
  }

  // To return the result of applying the given visitor to this Circle
  public <R> R accept(IShapeVisitor<R> visitor) { return visitor.visitRect(this); }
}

class Square implements IShape {
  int x, y, size;
  String color;
  Square(int x, int y, int size, String color) {
    this.x = x;
    this.y = y;
    this.size = size;
    this.color = color;
  }

  // To return the result of applying the given function to this Square
  public double beAppliedToBy(IShape2Double func) {
    return func.applyToSquare(this);
  }

  // To return the result of applying the given visitor to this Square
  public <R> R accept(IShapeVisitor<R> visitor) { return visitor.visitSquare(this); }
}

interface IList<T> {
	<U> IList<U> map(IFunc<T, U> f);
}

class ConsList<T> implements IList<T>{
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest){
    this.first = first;
    this.rest = rest;
  }

	public <U> IList<U> map(IFunc<T, U> f) {
		return new ConsList<U>(f.apply(this.first), this.rest.map(f));
	}

}

class MtList<T> implements IList<T>{
  MtList(){}

	public <U> IList<U> map(IFunc<T, U> f) { return new MtList<U>(); }
}


class ExamplesIShapes{
  ExamplesIShapes(){}

  IList<IShape> shapes = new ConsList<IShape>(new Circle (0,0,10, "red"),
  new ConsList<IShape>(new Rect(0,0, 10, 10, "green"),
    new MtList<IShape>()));

  IList<Double> expectedList = new ConsList<Double>( 314.15,
    new ConsList<Double>( 100.00,
  new MtList<Double>()));

  IList<String> expectedList1 = new ConsList<String>( "red",
  new ConsList<String>( "green", new MtList<String>()));


  boolean testIShapeMap(Tester t){
    return t.checkInexact(shapes.map(new ShapeAreaVisitor()), expectedList, 0.001)
    ;
  }
}
