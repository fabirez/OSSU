/*Do Now!
*Convince yourself that == for integers and booleans and equals on Strings obey the four properties above.
*/

// >  Let's list them:
// -  Reflexivity: x == x
//                 1 == 1
//                 true == true
//                 "hello".equals("hello")
//
// -     Symmetry:  x == y, y === x
//                  "hello".equals("hel" + "lo"), "hel" + "lo".equals("hello")
//
// - Transitivity:  x == z, y == z, x == y
//                  "hello".equals("he" + "llo"), "hel" + "lo".equals("he" + "llo"), "hello".equals("hel" + "lo")
//
// -     Totality:    We can compare any two objects of the same type, and obtain a correct answer.
//                    1 == 1 -> true, 1 == 2 -> false
//                    true == true -> true, true == false -> false
//                    "hello".equals("hel" + "lo") -> true, "hel" + "lo".equals("goodbye") -> false
//


/*Do Now!
*
*How might we define sameness for books?
*/
// Comparing the title and the author with the given one, usign the `equals` method by String.
// this.title.equals(other.title) && this.author.equals(other.author);
// >  Reflexivity: x == x
//                 this.book1.equal(this.book1)
//
// -     Symmetry:  x == y, y === x
//                  this.book1.equal(this.book2), this.book2.equal(this.book1)
//
// - Transitivity:  x == z, y == z, x == y
//                  this.book1.equal(this.book3), this.book2.equal(this.book3), this.book1.equal(this.book2)
//
//-      Totality:  We can compare any two objects of the same type, and obtain a correct answer.
//                  this.book1.equal(this.book4) -> false,
//                  this.book2.equal(this.book4) -> false,
//                  this.book3.equal(this.book4) -> false,
//                  this.book2.equal(this.book1) -> true
//


/*Do Now!
*
*Define the method samePoint for the CartPt class.
*/

/*class CartPt {
    int x;
    int y;
    
    CartPt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean samePoint(CartPt other){
      return this.x == other.x && this.y == other.y;
  }
}
*/

/*Do Now!
*
*Revise the definition of Book so that its author field is now of type Author,
*where Authors have first and last names, and two Authors are the same when both names are the same.
*Revise the sameBook method. What methods must it delegate to?
*/

/*
class Author {
  String firstName;
  String lastName;

  Author(String firstName, String lastName){
    this.firstName = firstName;
    this.lastName = lastName;
  }
  boolean sameAuthor(Author that) {
    return 
           this.firstName.equals(that.firstName) &&
           this.lastName.equals(that.lastName);
  }

}

class Book {
  String title;
  Author author;

  Book(String title, Author author) {
    this.title = title;
    this.author = author;
  }
  boolean sameBook(Book that) {
    /* Fields:
     * this.title  -- String
     * this.author -- Author
     *
     * Methods of fields:
     * this.title.equals(String)      -> Boolean
     * this.author.sameAuthor(Author) -> Boolean
     *
     * Fields of parameters:
     * that.title
     * that.author
     * /
    return this.title.equals(that.title) &&
           this.author.sameAuthor(that.author);
  }
}
*/

/*Do Now!
*
*What could go wrong with taking this position?
*/

//>  Some properties that exist on a Circle are not guaranteed to exist on every IShape implementation.
//    That will throw an error.
//
//    Even if two shapes expose the same properties, they can still represent different types.
//    At that point, the comparison becomes misleading.
//
//    Having the same size does not imply that two shapes are equal.
//    For example, a Circle with a size of 10 and a Rectangle with a size of 10 would compare as equal,
//    even though they are fundamentally different shapes.

/*Do Now!
*
*Which of our properties for sameness have we violated here?
*/

// > Totality:    We can compare any two objects of the same type, and obtain a correct answer.


/*Do Now!
*
*Implement sameShape for the Rect class, following the same pattern as for Circle. What test should you write to confirm that it works?
*/

/*public boolean sameShape(IShape that) {
  if (that instanceof Rect) {
    // that is-a Rect -- we can safely cast!
    return this.sameRect((Rect)that);
  }
  else {
    // that is not a Rect
    return false;
  }
}
*/

/*
  public boolean sameShape(IShape that) {
    if (that instanceof Square) {
      // that is-a Square -- we can safely cast!
      return this.sameSquare((Square)that);
    }
    else {
      // that is not a Square
      return false;
    }
  }
*/

/*
  public boolean sameSquare(Square that) {
    /* Template:
     * Fields:
     * this.x, this.y, this.size
     *
     * Fields of parameters:
     * that.x, that.y, that.size
     * /
    return this.x == that.x &&
           this.y == that.y &&
           this.w == that.w;
  } 
*/

// > It's not working, anytime i got a Rect in the condition with `instanceof`, it evaluates to true.

/*Do Now!

Which property of sameness have we violated?
*/ 

// > Symmetry

/*Do Now!

But r1 and s1 both describe rectangles with width and height of 5, at position (3,4). Why are they not equal?
*/

// > I suppose, because they have not the same type.

/*Do Now!
*
*Why must we have Square respond to isRect with false — surely all Squares are Rects?
*/
// > Type.

