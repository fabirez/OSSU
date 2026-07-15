/*Do Now!

How might you represent this?
*/

// > I would create an interface for the game itself (ITetris),
// > then an abstract class that implements the interface and declares all the default methods and fields that every Tetris piece must have (APiece),
// > and finally create different piece classes that inherit from the abstract class.



/* Do Now!
 * Why should the second constructor for Square not try to make up its own value for topLeftY?
*/

// > Because, when we initialize the instance, the constructor requires two parameters again, even if the value of the second parameter will not be used.


/*Do Now!
* Why?
*/

// > Because, it doen'ts have any logic, since we already have one. And we make java check for something that shouldn't.


/*Do Now!
* With the tools we have so far, define this Date class.
*/
/*
class Date{
  int   day;
  int month;
  int  year;

  Date(int day, int month, int  year){
    this.day = day;
    this.month = month;
    this.year = year;
  }
}
*/


/*Do Now!
Design a method checkRange, that takes a number to test, a minimum and maximum value for the range, and an error message to throw.
*/

/*
public int checkRange(int num, int min, int max, String errMsg) {
  if (num >= min && num <= max) {
    return num;
  }
  else {
    throw new IllegalArgumentException(errMsg);
  }
}
*/

/*Exercise
* Explore the documentation for the tester library,
* and see how to test for exceptions raised by methods.
* Write a test for checkRange that checks for this exceptional behavior.
*/
import tester.*;

class Utils {
  int checkRange(int val, int min, int max, String msg) {
    if (val >= min && val <= max) {
      return val;
    }
    else {
      throw new IllegalArgumentException(msg);
    }
  }
}

class Date{
  int year; 
  int month; 
  int day;

  Date(int year, int month, int day) {
    this.year = new Utils().checkRange(year, 1500, 2100,
      "Invalid year: " + Integer.toString(year));
    this.month = new Utils().checkRange(month, 1, 12,
      "Invalid month: " + Integer.toString(month));
    this.day = new Utils().checkRange(day, 1, 31,
      "Invalid day: " + Integer.toString(day));
  }
}

class ExamplesDate{
  ExamplesDate(){}

  boolean testConstructorException(Tester t){
    return
    t.checkConstructorException(new IllegalArgumentException("Invalid year: 53000"), "Date", 53000,  12,    30) &&
    t.checkConstructorException(new IllegalArgumentException("Invalid month: 130"),  "Date",  2093, 130,    30) &&
    t.checkConstructorException(new IllegalArgumentException("Invalid day: 30930"),  "Date",  2093,  12, 30930);
    }

  boolean testCheckRangeException(Tester t){
    return 
    t.checkException(new IllegalArgumentException("Invalid number: 10"), new Utils(), "checkRange", 10, 0, 9, "Invalid number: 10") &&
    t.checkException(new IllegalArgumentException("Invalid number: -1"), new Utils(), "checkRange", -1, 0, 9, "Invalid number: -1");
    }

}

