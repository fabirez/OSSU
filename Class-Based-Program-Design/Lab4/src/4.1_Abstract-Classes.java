/* Problem1: Books and Abstraction 
## 4.1 Abstract Classes

The following class diagram represents a library system that records the books that have been borrowed. There are three kinds of books: regular books, reference books, and audio books.

Reference books can be taken out for just two days, while other kinds of books may be borrowed for two weeks. The overdue fees are 10 cents per day for reference books and regular books, and 20 cents per day for audio books.

Audio books and regular books have both authors and titles; reference books only have titles.

The day when the book is taken out and the day due are counted as days since the library opened on New Year’s Day in 2001. So, for example, an audio book taken out recently would be recorded as taken out on the day 7778 with due date on the day 7792.

               +-------+
               | IBook |
               +-------+
                  / \\
                  ---
                   |
       ---------------------------------------
       |                  |                  |
+---------------+  +---------------+  +---------------+
| Book          |  | RefBook       |  | AudioBook     |
+---------------+  +---------------+  +---------------+
| String title  |  | String title  |  | String title  |
| String author |  | int dayTaken  |  | String author |
| int dayTaken  |  +---------------+  | int dayTaken  |
+---------------+                     +---------------+
- [x] Design the interfaces and classes that represent the library borrowing system.
  Define the abstract class ABook and lift those fields that can be lifted to this class.
- [x] Design the method daysOverdue that consumes the number that represents today in the library date-recording system and produces the number of days this book is overdue.
       If the number is negative, the book can still be out for that many days.
- [x] Design the method isOverdue that produces a boolean value that informs us whether the book is overdue on the given day.
- [x] Design the method computefine that computes the fine for this book, if the book is returned on the given day.

For all methods, think carefully whether they should be designed being implemented solely in the abstract class, implemented solely in the concrete classes, or implemented in the abstract class and then overridden in some of the concrete classes.
*/

import tester.*;

interface IBook{ 
  int TWOWEEKS = 14;
  int TWODAYS = 2;
  //  consumes the number that represents today in the library date-recording system and produces the number of days this book is overdue.
  // If the number is negative, the book can still be out for that many days.
  int daysOverdue(int TODAY);
  // produces a boolean value that informs us whether the book is overdue on the given day.
  boolean isOverdue(int day);
  // Design the method computeFine that computes the fine for this book,
  // if the book is returned on the given day.
  int computeFine(int day);
}



class ABook implements IBook{
  String title; 
  int dayTaken;

  ABook(String title, int dayTaken){
    this.title = title;
    this.dayTaken = dayTaken;
  }

  /* TEMPLATE
  FIELDS
  ... this.title ...    -- String
  ... this.dayTaken ... -- int
  METHODS
  ... this.daysOverdue(int) ...
  ... this.isOverdue(int)   ...
  ... this.computeFine(int) ...
  */

  //  consumes the number that represents today in the library date-recording system and produces the number of days this book is overdue.
  // If the number is negative, the book can still be out for that many days.
  public int daysOverdue(int today){
    /* TEMPLATE
    FIELDS
    ... this.title ...    -- String
    ... this.dayTaken ... -- int
    METHODS
    */
    return today - (this.dayTaken + TWOWEEKS);
  }

  // produces a boolean value that informs us whether the book is overdue on the given day.
  public boolean isOverdue(int day){
    /* TEMPLATE
    FIELDS
    ... this.title ...    -- String
    ... this.dayTaken ... -- int
    METHODS
    ... this.daysOverdue(int) ...
    */
    return this.daysOverdue(day) >= 0;
  }

  // Design the method computeFine that computes the fine for this book,
  // if the book is returned on the given day.
  public int computeFine(int day){
    /* TEMPLATE
    FIELDS
    ... this.title ...    -- String
    ... this.dayTaken ... -- int
    METHODS
    ... this.daysOverdue(int) ...
    ... this.isOverdue(int)   ...
    */
    return (day - this.dayTaken) * 10;
  }
}

class Book extends ABook{
  String author;

  Book(String title, String author, int dayTaken){
    super(title, dayTaken);
    this.author = author;
  }
}

class RefBook extends ABook{
  RefBook(String title, int dayTaken){
    super(title, dayTaken);
  }

  //  consumes the number that represents today in the library date-recording system and produces the number of days this book is overdue.
  // If the number is negative, the book can still be out for that many days.
  public int daysOverdue(int today){
    /* TEMPLATE
    FIELDS
    ... this.title ...    -- String
    ... this.dayTaken ... -- int
    */
    return today - (this.dayTaken + TWODAYS);
  }
}

class AudioBook extends ABook{
  String author;

  AudioBook(String title, String author, int dayTaken){
    super(title, dayTaken);
    this.author = author;
  }

  public int computeFine(int day){
    /* TEMPLATE
    FIELDS
    ... this.title ...    -- String
    ... this.dayTaken ... -- int
    METHODS
    ... this.daysOverdue(int) ...
    ... this.isOverdue(int)   ...
    */
    return (day - this.dayTaken) * 20;
  }
}

class ExamplesIBook{
  ExamplesIBook(){}

  ABook nb0 = new Book("NTtile0", "NAuthor0", 7778);
  ABook nb1 = new Book("NTtile1", "NAuthor1", 7775);
  ABook nb2 = new Book("NTtile2", "NAuthor2", 7773);

  ABook rb0 = new RefBook("RTtile0", 7780);
  ABook rb1 = new RefBook("RTtile1", 7779);
  ABook rb2 = new RefBook("RTtile2", 7776);

  ABook ab0 = new AudioBook("ATtile0", "AAuthor0", 7778);
  ABook ab1 = new AudioBook("ATtile1", "AAuthor1", 7779);
  ABook ab2 = new AudioBook("ATtile2", "AAuthor2", 7776);

  int TODAY = 7780;
  int TWOWEEKS = 14;
  int TWODAYS = 2;
  int TENCENTS = 10;
  int TWENTYCENTS = 20;

  boolean testDaysOverdue(Tester t){
    return
    t.checkExpect(nb0.daysOverdue(TODAY), TODAY - (7778 + TWOWEEKS)) &&
    t.checkExpect(rb0.daysOverdue(TODAY), TODAY - (7780 + TWODAYS)) &&
    t.checkExpect(ab0.daysOverdue(TODAY), TODAY - (7778 + TWOWEEKS));
  }

  boolean testIsOverdue(Tester t){
    return
    t.checkExpect(nb0.isOverdue(7792),  true) &&
    t.checkExpect(rb0.isOverdue(7783),  true) &&
    t.checkExpect(ab0.isOverdue(7790), false);
  }


  boolean testComputeFine(Tester t){
    return
    t.checkExpect(nb0.computeFine(7780),  TENCENTS * 2) &&
    t.checkExpect(rb0.computeFine(7783),  TENCENTS * 3) &&
    t.checkExpect(ab0.computeFine(7790),  TWENTYCENTS * 12);
  }
}
