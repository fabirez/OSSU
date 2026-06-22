/* Exercise Sorting

Suppose we wanted to sort the books by title, instead of by price.
We cannot use the < operator to compare Strings.
Instead, Strings have a method compareTo(String) that returns:

- A negative integer if this String is lexicographically before the given String
- 0 if the strings are lexicographically equal
- A positive integer if this String is lexicographically after the given String

Use this method to define a method titleBefore on Books, analogous to cheaperThan, and revise sort and/or insert to use it.
*/

import tester.*;

class Book {
    String name;
    String author;
    double price;
    int year;
    Book(String name, String author, double price, int year) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.year = year;
    }
    double getPrice() {
        return this.price;
    }
    // to return the discounted price of this book given the discount rate 
    double discount(double rate) {
        return this.price - (rate * this.price);
    }
    // to return a new book with the same author and name as this book,
    // but with price discounted at the given rate
    Book discountedBook(double rate) {
        return new Book(this.name, this.author, this.discount(rate), this.year);
    }

    Boolean titleBefore(Book that){
    return this.name.compareTo(that.name) <= 0;
  }
}



/*
 * GOAL:
 * Represent a bunch of books, and be able to compute
 * - their total price
 * - how many books we have
 * - all the books published before the given year
 * - a sorted list of books
 */

/*
 * A list of books is one of
 * empty
 * (cons book list-of-books)
 */

// DYNAMIC DISPATCH: deciding which method definition to invoke (in which class)
// based on the information available at runtime of the object that's invoking
// the method

interface ILoBook {
    // to compute the total price of all books in this list of books
    double totalPrice();
    // to count how many books are in this list of books
    int count();
    // to return a list of all the books in this list of books 
    // published before the given year
    ILoBook allBooksBefore(int year);
    // to construct a list of books that contains the same books as
    // this list of books, but sorted increasing by price
    ILoBook sortByPrice();
    ILoBook insert(Book b);
    // to construct a list of books that contains the same books as
    // this list of books, but sorted increasing by title 
    ILoBook sortByTitle();
    ILoBook insertTitle(Book b);
}
class MtLoBook implements ILoBook {
    MtLoBook() {
        // nothing to do!
    }
    public double totalPrice() {
        return 0;
    }
    public int count() {
        return 0;
    }
    public ILoBook allBooksBefore(int year) {
        return this;
        // return new MtLoBook();
    }
    public ILoBook sortByPrice() {
        return this;
        // return new MtLoBook();
    }

    public ILoBook insert(Book b) {
        return new ConsLoBook(b, this);
    }

    public ILoBook sortByTitle() {
        return this;
        // return new MtLoBook();
    }

    public ILoBook insertTitle(Book b) {
        return new ConsLoBook(b, this);
    }
}
class ConsLoBook implements ILoBook {
    Book first;
    ILoBook rest;
    ConsLoBook(Book first, ILoBook rest) {
        this.first = first;
        this.rest = rest;
    }
    public double totalPrice() {
        /* Template:
         * FIELDS:
         * this.first -- Book
         * this.rest -- ILoBook
         * METHODS:
         * this.totalPrice() -- double
         * this.count() -- int
         * this.allBooksBefore(int) -- ILoBook
         * this.sortByPrice() -- ILoBook
         * METHODS OF FIELDS:
         * this.first.getPrice() -- double ****
         * this.first.discount(double) -- double
         * this.first.discountedBook(double) -- Book
         * this.rest.totalPrice() -- double  *****
         * this.rest.count() -- int
         * this.rest.allBooksBefore(int) -- ILoBook
         * this.rest.sortByPrice() -- ILoBook
         */
        return this.first.getPrice() + this.rest.totalPrice();
    }
    public int count() {
        return 1 + this.rest.count();
    }
    public ILoBook allBooksBefore(int year) {
        if (this.first.year < year) {
            return new ConsLoBook(
                this.first,
                this.rest.allBooksBefore(year));
        } else {
            return this.rest.allBooksBefore(year);
        }
    }
    public ILoBook sortByPrice() {
        /*
         * Assuming we have:
         * ILoBook insert(Book) -- on cons? on ilobook?
         * boolean isCheaper(Book) -- on Book
         */

        return this.rest.sortByPrice().insert(this.first);
    }
    // to insert the given book into this sorted list of books 
   public ILoBook insert(Book b){
    if(b.getPrice() < this.first.getPrice()){
      return new ConsLoBook(b, this.sortByPrice());
    }else{
      return new ConsLoBook(this.first, this.rest.insert(b));
    }
   }


    public ILoBook sortByTitle() {
        /*
         * Assuming we have:
         * ILoBook insert(Book) -- on cons? on ilobook?
         * boolean isCheaper(Book) -- on Book
         */

        return this.rest.sortByTitle().insertTitle(this.first);
    }

    // to insert the given book into this sorted list of books 
   public ILoBook insertTitle(Book b){
    if(this.first.titleBefore(b)){
      return new ConsLoBook(b, this);
    }else{
      return new ConsLoBook(this.first, this.rest.insertTitle(b));
    }
  }
  

}

class ExamplesBooks {
  Book htdp = new Book("HtDP", "MF", 0.0, 2014);
  Book hp = new Book("HP & the search for more money", "JKR", 9000.00, 2015);
  Book gatsby = new Book("The Great Gatsby", "FSF", 15.99, 1930);
  ILoBook mtList = new MtLoBook();
  ILoBook twoBooks = new ConsLoBook(this.htdp, 
                         new ConsLoBook(this.hp, 
                             this.mtList));
  ILoBook threeBooks = new ConsLoBook(this.gatsby, this.twoBooks);
  ILoBook againBooks = new ConsLoBook(this.htdp, new ConsLoBook(this.gatsby, new ConsLoBook(this.hp, this.mtList)));
  ILoBook againBooks1 = new ConsLoBook(this.hp, new ConsLoBook(this.htdp, new ConsLoBook(this.gatsby, this.mtList)));
  ILoBook againBooks2 = new ConsLoBook(this.gatsby, new ConsLoBook(this.hp, new ConsLoBook(this.htdp, this.mtList)));
 
  ILoBook booksSortedByPrice = new ConsLoBook(this.htdp, new ConsLoBook(this.gatsby, new ConsLoBook(this.hp, this.mtList)));
  ILoBook booksSortedByTitle = new ConsLoBook(this.htdp, new ConsLoBook(this.hp, new ConsLoBook(this.gatsby, this.mtList)));
  
  
  boolean testSortByPrice(Tester t) {
      return t.checkExpect(this.threeBooks.sortByPrice(), this.booksSortedByPrice)
          && t.checkExpect(this.againBooks.sortByPrice(), this.booksSortedByPrice)
          && t.checkExpect(this.againBooks1.sortByPrice(), this.booksSortedByPrice)
          && t.checkExpect(this.againBooks2.sortByPrice(), this.booksSortedByPrice)
          ;
  }

  boolean testSortByTitle(Tester t) {
      return t.checkExpect(this.threeBooks.sortByTitle(), this.booksSortedByTitle)
          && t.checkExpect(this.againBooks.sortByTitle(), this.booksSortedByTitle)
          && t.checkExpect(this.againBooks1.sortByTitle(), this.booksSortedByTitle)
          && t.checkExpect(this.againBooks2.sortByTitle(), this.booksSortedByTitle)
          ;
  }
}

