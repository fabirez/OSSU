/*Do Now!
*Convince yourself that == for integers and booleans and equals on Strings obey the four properties above.
*/

// >  Let list them:
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

class CartPt {
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

/*Do Now!
*
*Revise the definition of Book so that its author field is now of type Author,
*where Authors have first and last names, and two Authors are the same when both names are the same.
*Revise the sameBook method. What methods must it delegate to?
*/

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
     */
    return this.title.equals(that.title) &&
           this.author.sameAuthor(that.author);
  }
}


