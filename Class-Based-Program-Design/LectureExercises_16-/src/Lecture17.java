//       +----------------------------------------+
//        |               +------------+           |
//        V               |            V           |
// +--------------+       |     +---------------+  |
// | Author       |       |     | Book          |  |
// +--------------+       |     +---------------+  |
// | String first |       |     | String title  |  |
// | String last  |       |     | int price     |  |
// | int yob      |       |     | int quantity  |  |
// | Book book    |-------+     | Author author |--+
// +--------------+             +---------------+
// Represents authors of books
class Author {
  String first;
  String last;
  int yob;
  Book book;
  Author(String fst, String lst, int yob, Book bk) {
    this.first = fst;
    this.last = lst;
    this.yob = yob;
    this.book = bk;
  }
}
 
// Represent books
class Book {
  String title;
  int price;
  int quantity;
  Author author;
  Book(String title, int price, int quantity, Author ath) {
    this.title = title;
    this.price = price;
    this.quantity = quantity;
    this.author = ath;
  }
}
/*Do Now!

	Try creating an example of the following classic text in computer science,
	using the data representation above:
	Donald E. Knuth.  The Art of Computer Programming (volume 1).

	Addison Wesley, Reading, Massachusetts.  1968.

	(Knuth was born in 1938.) Can you do it?
*/

// No, i can't. 
// I would not have the author, or the book, if i implement one or the other.

/*Do Now!
	What do you think will happen? Why?
*/

// > True, because the values are the same.
// > We are not checking the refs

/*Exercise
	The last line of the code above invokes checkExpect, and passes it two Authors...
	and yet the test terminates and passes! How do you think the tester library manages this,
	when our definitions of sameness (so far) would result in our program running forever?
*/

// > 

/*Do Now!
	Which one?
*/
// > Random function. The util and the Math one

class Counter {
  int val;
  Counter() {
    this(0);
  }
  Counter(int initialVal) {
    this.val = initialVal;
  }
  int get() {
    int ans = this.val;
    this.val = this.val + 1;
    return ans;
  }
}
/*
class ExamplesCounter {
  boolean testCounter(Tester t) {
    Counter c1 = new Counter();
    Counter c2 = new Counter(2);
    // What should these tests be?
    return t.checkExpect(c1.get(), ???)              // Test 1
        && t.checkExpect(c2.get(), ???)              // Test 2
        && t.checkExpect(c1.get() == c1.get(), ???)  // Test 3
        && t.checkExpect(c2.get() == c1.get(), ???)  // Test 4
        && t.checkExpect(c2.get() == c1.get(), ???)  // Test 5
        && t.checkExpect(c1.get() == c1.get(), ???)  // Test 6
        && t.checkExpect(c2.get() == c1.get(), ???); // Test 7
  }
}
*/

/*Do Now!

Fill in the ??? in the tests above.
/*
    return t.checkExpect(c1.get(),   1)                // Test 1
        && t.checkExpect(c2.get(),   3)                // Test 2
        && t.checkExpect(c1.get() == c1.get(), false)  // Test 3
        && t.checkExpect(c2.get() == c1.get(),  true)  // Test 4
        && t.checkExpect(c2.get() == c1.get(),  true)  // Test 5
        && t.checkExpect(c1.get() == c1.get(), false)  // Test 6
        && t.checkExpect(c2.get() == c1.get(), false); // Test 7
*/

/*Do Now!
	If side effects are so convenient for interacting with the outside world,
	how does (big-bang ...) work? We certainly had no mutation in Racket,
	yet big-bang could draw to the screen and get input from the user.
*/

// > I think we still had that, since it needed to mutate (update)
// > local variables for the new state.
// > If for mutation we means to change a value assigned to a variable,
// > but if the big bang recreate everytime the variable from scatch'
// > then it's still a mutation ? Or it's not consider that anymore ?
// > But at the same time, if we call the big bang with the same arguments
// > On different time, we get different results, like the value of the tick
// > that never stop.
// > This can be considered a side-effect and that means there is mutation going on.
// > So all of the work was abstracted from us!

