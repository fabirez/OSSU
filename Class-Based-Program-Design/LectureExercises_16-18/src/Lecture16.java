/*Do Now!

Try writing ShapeArea using casting. What works well in this approach, and what doesn’t?
*/

/*
class ShapeArea implements IFunc<IShape, Double> {
	public Double apply(IShape shape){
		if(shape instanceof Circle) {
			return Math.PI * ((Circle)shape).radius * ((Circle)shape).radius;
		}else{
			// * 1.0 converting the result to double
			return ((Rect)shape).w * ((Rect)shape).h * 1.0;
		}
	}
}
*/

// > That's really ugly to look at, and it's not scalable.
// > If tomorrow i want to add another `IShape`, we will need more checks




/*Do Now!

Suppose we add the following class:
```java
class Square implements IShape {
  int x, y, size;
  String color;
  Square(int x, int y, int size, String color) {
    this.x = x;
    this.y = y;
    this.size = size;
    this.color = color;
  }
}
```
What changes do you need to make to extend the ShapeArea class to handle this new case?
*/
/*
  // In IShape2Double
  double applyToSquare(Square square);

  // In ShapeArea
  public double applyToSquare(Square square) {
    return square.size * square.size * 1.0;
  }

	// In Square
  public double beAppliedToBy(IShape2Double func) {
    return func.applyToSquare(this);
  }

*/

/*Do Now!
	Rewrite the ShapeArea class to implement IShapeVisitor<Double>, instead of the IShape2Double interface.
*/
/*
class ShapeArea implements IShapeVisitor<Double> {
  public Double visitCircle(Circle circle) {
    return Math.PI * circle.radius * circle.radius;
  }

  public Double visitRect(Rect rect) {
    return rect.w * rect.h * 1.0;
  }

  public Double visitSquare(Square square) {
    return square.size * square.size * 1.0;
  }
}
*/

/*Do Now!
	Implement this method. You should need only a single method call.
*/

// > the method is `accept`. 
