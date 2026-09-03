/*Exercise
 *How can I take an IList<IShape> and get a list of the perimeters of the shapes? 
 (Remember, we only implemented area() as a method!)

Try to design a solution to this problem.
Where does the pattern above get stuck? How might the techniques from the last few lectures help?
*/

import tester.*;


interface IFunc<A, R> {
  R apply(A arg);
}

class ComputePerimeter implements IFunc<IShape, Double>{
	public Double apply(IShape s){
		return s.perimeter();
	}
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



interface IShape {
  boolean sameShape(IShape that);
  boolean sameCircle(Circle that);
  boolean sameRect(Rect that);
  boolean sameSquare(Square that);
	double perimeter();
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

	public abstract double perimeter();
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

	public double perimeter(){
		return Math.floor(2 * Math.PI * this.radius);
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

	public double perimeter(){
		return Math.floor(2 * this.w * this.h); 
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

	public double perimeter(){
		return Math.floor(size);
	}
}


class Examples{
  Examples(){}

  Circle c1 = new Circle(3, 4, 5);
  Circle c2 = new Circle(4, 5, 6);
  Circle c3 = new Circle(3, 4, 5);

  Rect r1 = new Rect(3, 4, 5, 5);
  Rect r2 = new Rect(4, 5, 6, 7);
  Rect r3 = new Rect(3, 4, 5, 5);

  Square s1 = new Square(3, 4, 5);
  Square s2 = new Square(4, 5, 7);
  Square s3 = new Square(3, 4, 5);
 
	IList<IShape> mtShape = new MtList<IShape>();
	IList<IShape> los0 = new ConsList<IShape>(c1, new ConsList<IShape>(r1, new ConsList<IShape>(s1, mtShape)));
	IList<Double> mtDouble = new MtList<Double>();
	IList<Double> lop0 = new ConsList<Double>(31.0, new ConsList<Double>(50.0, new ConsList<Double>(5.0, mtDouble)));

	IFunc<IShape,Double> cP = new ComputePerimeter();

	boolean testPerimeter(Tester t){
		return
		t.checkExpect(c1.perimeter(), 31.0)
		&&
		t.checkExpect(r1.perimeter(), 50.0)
		&&
		t.checkExpect(s1.perimeter(), 5.0)
		;
	} 

	boolean testMap(Tester t){
		return
		t.checkExpect(los0.map(cP), lop0);
	}
}
