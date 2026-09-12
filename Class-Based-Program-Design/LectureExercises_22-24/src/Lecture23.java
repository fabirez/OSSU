/* Do Now!

	Suppose we want to implement a method akin to Racket’s build-list function:
	it should take a number 𝑛
	and a function object,
	and produce an ArrayList that results from invoking that function object on all numbers from 0
	to 𝑛−1

	Do it.

*/

/* Do Now!
	Design a method in ArrayUtils to capitalize all titles. Which loop form should we use?
*/


/* Do Now!
	What goes wrong with this approach?

	// In ArrayUtils
	// EFFECT: Modifies all the books in the given ArrayList, to capitalize their titles

	void capitalizeTitles_bad(ArrayList<Book> books) {
		for (Book b : books) {
			b = new Book(b.title.toUpperCase(), b.author);
		}
	}
*
*/
// > The original reference will change and every book, in the entire program now have the title uppercase.


/*Do Now!
	Confirm that this approach works,
	by drawing the object diagram for the test above (suitably modified to call capitalizeTitles_good),
	and work through where the changes occur.
*/


/*
 +-------+  +-------+  +-------+  +-------+  +-------+
 | Book  |  | Book  |  | Book  |  | Book  |  | Book  |
 +-------+  +-------+  +-------+  +-------+  +-------+  
 | author|  | author|  | author|  | author|  | author|
 | TITLE |  | TITLE |  | TITLE |  | TITLE |  | TITLE | 
 +-------+  +-------+  +-------+  +-------+  +-------+ 

// > Same reference, same book, but with the new title uppercase.
*/




/* Exercise
	What goes wrong if we tried removing the old book and adding the new one, as in the first option?
*/

// > We lost the "alias" of the book.

import java.util.ArrayList;
import tester.*;

interface IFunc<T, U>{
	U apply(T t);
}

class Values implements IFunc<Integer, Integer>{
	public Integer apply(Integer idx) { return idx; }
}

class MulValues implements IFunc<Integer, Integer>{
	public Integer apply(Integer idx) { return idx * idx; }
}

class BookUppercase implements IFunc<Book, Book>{
	public Book apply(Book b) { return new Book(b.title.toUpperCase(), b.author, b.price); }
}

class ArrayUtils3 {

	<T, U> ArrayList<U> buildList(int len, IFunc<Integer, U> fn)
	{
		ArrayList<U> newArr = new ArrayList<U>();
		for(int idx = 0;
				idx < len;
				idx = idx + 1)
		{
			U newEl = fn.apply(idx);
			newArr.add(newEl);
		}

		return newArr;
	}

	// In ArrayUtils
	<T, U> ArrayList<U> map(ArrayList<T> arr, IFunc<T, U> func) 
	{
		ArrayList<U> result = new ArrayList<U>();
		for (T t : arr) {
			result.add(func.apply(t));
		}
		return result;
	}
}

class Book{
	String title; String author; double price;

	Book(String title, String author, double price){
		this.title=title;
		this.author=author;
		this.price=price;
	}
}


class ExamplesArray3{
	ArrayList<String> sS;
	ArrayUtils3       aU;
  ArrayList<Book>  lob;

	Book b1;
	Book b2;
	Book b3;

	void initData(){
		this.sS = new ArrayList<String>();
		this.lob = new ArrayList<Book>();
		this.aU = new ArrayUtils3();

		this.b1 = new Book("How to design program", "Matthias Felleisen", 10);
		this.b2 = new Book("Structure and Interpretation of Computer Programs", "Harold Abelson", 85);
		this.b3 = new Book("Designing Data-Intensive Applications", "Martin Kleppmann", 50);
	}

	void testBuildList(Tester t){
		this.initData();

		ArrayList<Integer> expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		expected.add(2);
		expected.add(3);
		expected.add(4);
		expected.add(5);
		expected.add(6);
		expected.add(7);
		expected.add(8);
		expected.add(9);

		IFunc<Integer, Integer> v = new Values();
		t.checkExpect(aU.buildList(10, v), expected);

		ArrayList<Integer> expected1 = new ArrayList<Integer>();
		expected1.add(0);
		expected1.add(1);
		expected1.add(4);
		expected1.add(9);
		expected1.add(16);

		IFunc<Integer, Integer> mV = new MulValues();
		t.checkExpect(aU.buildList(5, mV), expected1);
	}

	void testMap(Tester t){
		this.initData();

		IFunc<Book, Book> bU = new BookUppercase();

		// ArrayList with one book
		this.lob.add(this.b1);
		t.checkExpect(this.lob.size(), 1);
		t.checkExpect(this.lob.get(0), this.b1);

		ArrayList<Book> expected = new ArrayList<Book>();
		Book b1_ = new Book("HOW TO DESIGN PROGRAM", "Matthias Felleisen", 10);

		expected.add(b1_);
		this.lob = aU.map(this.lob, bU);
		t.checkExpect(this.lob, expected);
		t.checkExpect(this.lob.size(), 1);
		t.checkExpect(this.lob.get(0), b1_);

		// ArrayList with two books
		this.lob.add(this.b2);
		this.lob.add(this.b3);
		t.checkExpect(this.lob.size(), 3);
		t.checkExpect(this.lob.get(1), this.b2);
		t.checkExpect(this.lob.get(2), this.b3);

		Book b2_ = new Book("STRUCTURE AND INTERPRETATION OF COMPUTER PROGRAMS", "Harold Abelson", 85);
		Book b3_ = new Book("DESIGNING DATA-INTENSIVE APPLICATIONS", "Martin Kleppmann", 50);
		expected.add(b2_);	
		expected.add(b3_);	

		this.lob = aU.map(this.lob, bU);
		t.checkExpect(this.lob, expected);
	}
}
