/*Do Now!
	How could we revise our data types to accommodate more prolific authors?
*/

// > IList<Book>

/*
// In ExampleBooks
boolean testTwoBooks(Tester t) {
  this.initTestConditions();
  // Test 1: check that knuth hasn't written any books yet
  boolean test1 = t.checkExpect(this.knuth.book, null);
  // Modify knuth to know about volume 1
  this.knuth.updateBook(this.taocp1);
  // Test 2: check that knuth's book was written by knuth
  boolean test2 = t.checkExpect(this.knuth.book.author, this.knuth);
  // Modify knuth to know about volume 2
  this.knuth.updateBook(this.taocp2);
  // Test 3: check that knuth's new book was written by knuth
  boolean test3 = t.checkExpect(this.knuth.book.author, this.knuth);
  // Test 4: check that both books' authors wrote those books
  boolean test4 =
    t.checkExpect(this.taocp1.author.book, this.taocp1) &&
    t.checkExpect(this.taocp2.author.book, this.taocp2);
  return test1 && test2 && test3 && test4;
}
*Do Now!
	Which of these tests pass, and which of these tests fail?
*/

// > Test 4, `t.checkExpect(this.taocp1.author.book, this.taocp1) &&`, this return false,
// > since we changed it for test2 `this.knuth.updateBook(this.taocp2);`


/*
// In examples class
void testBookAuthors(Tester t) {
  this.initTestConditions();
  Author shakespeare = new Author("William", "Shakespeare", 1564, null);
  Book tcoe = new Book("The Comedy of Errors", 42, 1, shakespeare);
  // Test 1: check that neither knuth nor shakespear have written any books yet
  t.checkExpect(this.knuth.book, null);
  t.checkExpect(shakespeare.book, null);
  // Test 2: check that setting shakespeare's book to taocp fails
  t.checkException(
    new RuntimeException("book was not written by this author"),
    shakespeare, "updateBook", this.taocp1);
}

*Do Now!
	Try to modify updateBook yourself to detect this mistake and throw the appropriate error.
*/
/*
void updateBook(Book b) {
  if (this.book != null) {
    throw new RuntimeException("trying to add second book to an author");
  }else if(!b.author.sameAuthor(this)){
    throw new RuntimeException(""book was not written by this author"");
	}
  else {
    this.book = b;
  }
}
*/

/*Do Now!
	Revise the constructor for Author so that it does not take a Book parameter,
	but still initializes the book field to null.
*/
/*
class Author {
  String first;
  String last;
  int yob;
  IList<Book> books;
  Author(String fst, String lst, int yob) {
    this.first = fst;
    this.last = lst;
    this.yob = yob;
    this.books = new MtList<Book>();
  }
}
*/

/*Do Now!
	Revise updateBook to be a new method,
	addBook, that adds the new book to the list of books.
*/

/*
void addBook(Book b) {
  this.books = new ConsList<Book>(b, this.books);
}
*/

/*Do Now!
	At first glance,
	it might look like we are creating a cycle in the book list itself:
	it seems like we’re creating a new Cons whose first is the given book,
	and whose rest is this.books,
	which is a Cons whose first is the given book,
	and whose rest is this.books, ...
	What’s wrong with this reasoning?
*/

// > The simple fact that it ends `MtList<Book>, and the books are different.
// > Just adding a new book in the list.


/*Do Now!

Draw the object diagram that’s described above.
*/

//												+-------------+
// 												| IList<Book> |
// 												+-------------+
// 												      |
// 												     / \
// 												     ---
//        +---------------------+
//        |							+-------------------------------------------+	
//        |             v                                           |
//				|			 +------------------+            +---------------+  |
// 				|			 | Author						|            | Book          |  |
// 				|			 +------------------+            +---------------+  |
// 				|			 | String first		  |            | String title  |  |
// 				|			 | String last  	  |            | int price     |  |
// 				|			 | int yob      	  |            | int quantity  |  |
//				+----->| IList<Book> books|            | Author author |--+
//						   +------------------+            +---------------+
