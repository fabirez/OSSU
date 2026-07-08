/* # Problem 1: Understanding the String class
*
* For all questions in this problem, be sure to follow the design recipe carefully:
*
* - Give sufficient examples of data, and sufficient tests, to test your methods thoroughly.
* - If you find yourself wanting to use a field-of-field, stop. Fill out the template for each method, and figure out another design.
* - Think carefully about how to use dynamic dispatch, and where to define methods, to keep your code as simple and clean as possible.
*
* Note: The following method defined for the class String may be useful:
* ```java
* // does this String come before the given String lexicographically?
* // produce value < 0   --- if this String comes before that String
* // produce value zero  --- if this String is the same as that String
* // produce value > 0   --- if this String comes after that String
* int compareTo(String that)
* ```
*
* - > > > When referring to strings, this is known as a “case-insensitive” sort,
*         since when it examines two strings, it converts everything to lowercase (or uppercase — would it make any difference?) before comparing them,
*         so the comparison can’t use—or be sensitive to—differences in casing. 
*
* - [x] Design the method sort that produces a new list, sorted in alphabetical order, treating all Strings as if they were given in all lowercase.
*
*  NOTE: The String class defines the method toLowerCase that produces a String just like the one that invoked the method, but with all uppercase letters converted to lowercase.
*
* - [x] Design the method isSorted that determines whether this list is sorted in alphabetical order, in a case-insensitive way.
*  HINT: You will likely need a helper method. You may want to review the accumulator style functions we have seen in DrRacket.
* - [x] Design the method interleave that takes this list of Strings and a given list of Strings, and produces a list where the first, third, fifth... elements are from this list, and the second, fourth, sixth... elements are from the given list. Any “leftover” elements (when one list is longer than the other) should just be left at the end.
* - [x] Design the method merge that takes this sorted list of Strings and a given sorted list of Strings, and produces a sorted list of Strings that contains all items in both original lists, including duplicates. (This is not the same computation as for interleave, but the two methods are similar. Can you construct an example of two lists such that interleaving them and merging them produce different results? Can you construct another example where the two results are the same?)
* - [x] Design the method reverse that produces a new list of Strings containing the same elements as this list of Strings, but in reverse order.
*  HINT: The cleanest solution to this problem uses a helper method, in a style seen already in this problem.
* - [x] Design the method isDoubledList that determines if this list contains pairs of identical strings, that is, the first and second strings are the same, the third and fourth are the same, the fifth and sixth are the same, etc.
*  HINT: Think carefully about how to test this method.
* - > > > This isn’t the same as the typical definition of palindromes, which asks whether a single string contains the same letters when read in either order.
* - [ ] Design the method isPalindromeList that determines whether this list contains the same words reading the list in either order.
*  HINT: Several of the methods defined above will be helpful in your solution.
*
* Submit your work in the completed Strings.java file.
*
* WARNING: Be extra careful not to name your file "String.java" – or else Java will think that you’re trying to redefine the built-in String class, and everything will break in creatively bizarre ways.
 */

// CS 2510, Assignment 3

import tester.*;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  // produces a new list, sorted in alphabetical order, treating all Strings as if
  // they were given in all lowercase.
  ILoString sort();

  ILoString insert(String s);

  // determines whether this list is sorted in alphabetical order, in a
  // case-insensitive way.
  boolean isSorted();

  boolean isSortedHelp(String s, boolean acc);

  // produces a list where the first, third, fifth... elements are from this list,
  // and the second, fourth, sixth... elements are from the given list.
  // Any “leftover” elements (when one list is longer than the other) should just
  // be left at the end.
  ILoString interleave(ILoString los);
  ILoString interleaveHelp(ILoString los, int acc, ILoString leftLos);
  // return the lenght of the list
  int count();
  // return the first of the list
  String getFirst();
  // return the rest of the list
  ILoString getRest();
  // produces a sorted list of Strings that contains all items in both original lists, including duplicates.
  ILoString merge(ILoString other);
  // produces a new list of Strings containing the same elements as this list of Strings, but in reverse order.
  ILoString reverse();
  ILoString reverseHelp(ILoString acc);
  // determines if this list contains pairs of identical strings, that is,
  // the first and second strings are the same, the third and fourth are the same, the fifth and sixth are the same, etc.
  boolean isDoubledList();
  boolean isDoubledListHelp(String prev,int acc);
  // determines whether this list contains the same words reading the list in either order.
  boolean isPalindromeList();
  boolean isPalindromeListHelper(ILoString reverseList, int acc);
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  // produces a new list, sorted in alphabetical order, treating all Strings as if
  // they were given in all lowercase.
  public ILoString sort() {
    return this;
  }

  public ILoString insert(String s) {
    return new ConsLoString(s, this);
  }

  // determines whether this list is sorted in alphabetical order, in a
  // case-insensitive way.
  public boolean isSorted() {
    return true;
  }

  public boolean isSortedHelp(String s, boolean acc) {
    return acc;
  }

  // produces a list where the first, third, fifth... elements are from this list,
  // and the second, fourth, sixth... elements are from the given list.
  // Any “leftover” elements (when one list is longer than the other) should just
  // be left at the end.
  public ILoString interleave(ILoString los) {
    return los;
  }
  public ILoString interleaveHelp(ILoString los, int acc, ILoString leftLos){
        return leftLos;
  }

  // return the lenght of the list
  public int count(){
        return 0;
  }

  // return the first of the list
  public String getFirst(){
      return "";
  }

  // return the rest of the list
  public ILoString getRest(){
      return this;
  }

  // produces a sorted list of Strings that contains all items in both original lists, including duplicates.
  public ILoString merge(ILoString other){
    return other;
  }

  // produces a new list of Strings containing the same elements as this list of Strings, but in reverse order.
  public ILoString reverse(){
    return this;
  }
  public ILoString reverseHelp(ILoString acc){
    return acc;
  }

  // determines if this list contains pairs of identical strings, that is,
  // the first and second strings are the same, the third and fourth are the same, the fifth and sixth are the same, etc.
  public boolean isDoubledList(){
    return true;
  }
  public boolean isDoubledListHelp(String prev, int acc){
    return true; 
  }

  // determines whether this list contains the same words reading the list in either order.
  public boolean isPalindromeList(){
    return true;
  }
  public boolean isPalindromeListHelper(ILoString reverseList, int acc){
    return true;
  }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

    /*
     TEMPLATE
     FIELDS:
     ... this.first ...         -- String
     ... this.rest ...          -- ILoString
     
     METHODS
     ... this.combine() ...                                  -- String
     ... this.sort() ...                                     -- ILoString
     ... this.insert(String) ...                             -- ILoString
     ... this.isSorted() ...                                 -- Boolean
     ... this.isSortedHelp(String, Boolean) ...              -- Boolean     
     ... this.interleave(ILoString) ...                      -- ILoString     
     ... this.interleaveHelp(ILoString, int, ILoString) ...  -- ILoString     
     ... this.count() ...                                    -- int     
     ... this.getFirst() ...                                 -- String
     ... this.getRest() ...                                  -- ILoString     
     ... this.merge(ILoString) ...                           -- ILoString     
     ... this.reverse(ILoString) ...                         -- ILoString     

     METHODS FOR FIELDS
     ... this.first.concat(String) ...                           -- String
     ... this.first.compareTo(String) ...                        -- int
     ... this.rest.combine() ...                                 -- String
     ... this.rest.sort() ...                                    -- ILoString
     ... this.rest.isSorted() ...                                -- Boolean           
     ... this.rest.isSortedHelp(String, Boolean) ...             -- Boolean     
     ... this.rest.interleave(ILoString) ...                     -- ILoString     
     ... this.rest.interleaveHelp(ILoString, int, ILoString) ... -- ILoString     
     ... this.rest.count() ...                                   -- int     
     ... this.rest.getFirst() ...                                -- String
     ... this.rest.getRest() ...                                 -- ILoString     
     ... this.rest.merge(ILoString) ...                          -- ILoString     
     ... this.rest.reverse(ILoString) ...                        -- ILoString     
     */

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  public ILoString sort() {
    return this.rest.insert(this.first);
  }

  public ILoString insert(String s) {
    if (this.first.toLowerCase().compareTo(s.toLowerCase()) <= 0) {
      return new ConsLoString(this.first, this.rest.insert(s));
    }
    else {
      return new ConsLoString(s, this.rest.insert(this.first));
    }
  }

  // determines whether this list is sorted in alphabetical order, in a
  // case-insensitive way.
  public boolean isSorted() {
    return this.rest.isSortedHelp(this.first, true);
  }

  public boolean isSortedHelp(String s, boolean acc) {
    if (acc == false) {
      return false;
    }
    else {
      if (this.first.toLowerCase().compareTo(s.toLowerCase()) < 0) {
        return this.rest.isSortedHelp(this.first, false);
      }
      else {
        return this.rest.isSortedHelp(this.first, true);
      }
    }
  }

  // produces a list where the first, third, fifth... elements are from this list,
  // and the second, fourth, sixth... elements are from the given list.
  // Any “leftover” elements (when one list is longer than the other) should just
  // be left at the end.
  public ILoString interleave(ILoString los) {
    if(this.count() == 0){
      return los;
    }else if(los.count() == 0){
      return this;
    }else{
      if(this.count() > los.count()){
          return this.interleaveHelp(los, los.count()  * 2, this);
      }else{
          return this.interleaveHelp(los, this.count() * 2, los);
      }
    }
  }
  public ILoString interleaveHelp(ILoString los, int acc, ILoString leftLos){
        if(new Utility().isEven(acc)){
            return new ConsLoString(this.first, this.rest.interleaveHelp(los, acc - 1, los));
        }else{
            return new ConsLoString(los.getFirst(), this.interleaveHelp(los.getRest(), acc - 1, this));
        }
  }


  // return the lenght of the list
  public int count(){
        return 1 + this.rest.count();
  }

  // return the first of the list
  public String getFirst(){
      return this.first;
  }

  // return the rest of the list
  public ILoString getRest(){
      return this.rest;
  }

  // produces a sorted list of Strings that contains all items in both original lists, including duplicates.
  public ILoString merge(ILoString other){
    return new ConsLoString(this.first, this.rest.merge(other)).sort();
  }

  // produces a new list of Strings containing the same elements as this list of Strings, but in reverse order.
  public ILoString reverse(){
    return this.reverseHelp(new MtLoString());
    // return this.rest.reverseHelp(new ConsLoString(this.first, acc));
  }
  public ILoString reverseHelp(ILoString acc){
    return this.rest.reverseHelp(new ConsLoString(this.first, acc));
  }

  // determines if this list contains pairs of identical strings, that is,
  // the first and second strings are the same, the third and fourth are the same, the fifth and sixth are the same, etc.
  public boolean isDoubledList(){
    return this.rest.isDoubledListHelp(this.first, 1);
  }
  public boolean isDoubledListHelp(String prev, int acc){
    if(acc == 1){
      if(this.first.equals(prev)){
        return this.rest.isDoubledListHelp(this.first, 0);
      }else{
        return false;
      }
    }else{
      return this.rest.isDoubledListHelp(this.first, 1);
    }
  }


  // determines whether this list contains the same words reading the list in either order.
  public boolean isPalindromeList(){
    return this.isPalindromeListHelper(this.reverse(), (int) Math.floor(this.count() / 2));
  }
  public boolean isPalindromeListHelper(ILoString reverseList, int acc){
    if(acc == 0){
      return true;
    }else{
      if(this.first.equals(reverseList.getFirst())){
        return this.rest.isPalindromeListHelper(reverseList.getRest(), acc - 1);
      }else{
        return false;
      }
    }
  }
}


class Utility{
    Utility(){}
    public boolean isEven(int acc){
        return acc % 2 == 0; 
    }
}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mary = new ConsLoString("Mary ", new ConsLoString("had ", new ConsLoString("a ",
      new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))));

  ILoString los0 = new ConsLoString("b", new ConsLoString("a",
      new ConsLoString("a", new ConsLoString("z", new ConsLoString("w", new MtLoString())))));
  // [S]orted
  ILoString losS = new ConsLoString("a", new ConsLoString("a",
      new ConsLoString("b", new ConsLoString("w", new ConsLoString("z", new MtLoString())))));

  ILoString group = new ConsLoString("Bob", new ConsLoString("Alice", new ConsLoString("Kevin",
      new ConsLoString("George", new ConsLoString("Philip", new MtLoString())))));
  // [S]orted
  ILoString groupS = new ConsLoString("Alice", new ConsLoString("Bob", new ConsLoString("George",
      new ConsLoString("Kevin", new ConsLoString("Philip", new MtLoString())))));
  // [D]oubled[L]ist
  ILoString groupDL = new ConsLoString("Bob", new ConsLoString("Bob", new ConsLoString("Alice",
      new ConsLoString("Alice", new MtLoString()))));
  // [P]alindrome
  ILoString groupP = new ConsLoString("Bob", new ConsLoString("Alice", new ConsLoString("Alice",
      new ConsLoString("Bob", new MtLoString()))));

  ILoString alphabet0 = new ConsLoString("a", new ConsLoString("c",
      new ConsLoString("e", new ConsLoString("g", new ConsLoString("i", new MtLoString())))));
  ILoString alphabet1 = new ConsLoString("b", new ConsLoString("d",
      new ConsLoString("f", new ConsLoString("h", new ConsLoString("j", new MtLoString())))));
  // [S]orted
  ILoString alphabetS = new ConsLoString("a", new ConsLoString("b", new ConsLoString("c",
      new ConsLoString("d",
          new ConsLoString("e", new ConsLoString("f", new ConsLoString("g", new ConsLoString("h",
              new ConsLoString("i", new ConsLoString("j", new MtLoString()))))))))));
  // [R]everse
  ILoString alphabet0R = new ConsLoString("i", new ConsLoString("g", new ConsLoString("e", new ConsLoString("c", new ConsLoString("a", new MtLoString())))));
  ILoString alphabet1R = new ConsLoString("j", new ConsLoString("h", new ConsLoString("f", new ConsLoString("d", new ConsLoString("b", new MtLoString())))));
  // [D]oubled[L]ist
  ILoString alphabetDL = new ConsLoString("a", new ConsLoString("a", new ConsLoString("b", new ConsLoString("b", new ConsLoString("c", new MtLoString())))));
  // [P]alindrome
  ILoString alphabetP = new ConsLoString("a", new ConsLoString("b", new ConsLoString("c", new ConsLoString("b", new ConsLoString("a", new MtLoString())))));

  ILoString vocals0 = new ConsLoString("a",
      new ConsLoString("i", new ConsLoString("u", new MtLoString())));
  ILoString vocals1 = new ConsLoString("e", new ConsLoString("o", new MtLoString()));
  // [S]orted
  ILoString vocalsS = new ConsLoString("a", new ConsLoString("e",
      new ConsLoString("i", new ConsLoString("o", new ConsLoString("u", new MtLoString())))));
  // [R]everse
  ILoString vocals0R = new ConsLoString("u", new ConsLoString("i", new ConsLoString("a", new MtLoString())));
  ILoString vocals1R = new ConsLoString("o", new ConsLoString("e", new MtLoString()));
  // [D]oubled[L]ist
  ILoString vocalsDL = new ConsLoString("a", new ConsLoString("a", new ConsLoString("e", new ConsLoString("e", new MtLoString()))));
  // [P]alindrome
  ILoString vocalsP = new ConsLoString("a", new ConsLoString("e", new ConsLoString("e", new ConsLoString("a", new MtLoString()))));

  ILoString vocals2 = new ConsLoString("u",
      new ConsLoString("a", new ConsLoString("e", new MtLoString())));
  ILoString vocals3 = new ConsLoString("i", new ConsLoString("o", new MtLoString()));
  // [I]nterleave
  ILoString vocalsI = new ConsLoString("u", new ConsLoString("i",
      new ConsLoString("a", new ConsLoString("o", new ConsLoString("e", new MtLoString())))));

  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
  }

  // test the method sort for the lists of Strings
  boolean testSort(Tester t) {
    return t.checkExpect(this.los0.sort(), this.losS)
        && t.checkExpect(this.group.sort(), this.groupS);
  }

  // test the method sort for the lists of Strings
  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.mary.isSorted(), false) && t.checkExpect(this.los0.isSorted(), false)
        && t.checkExpect(this.losS.isSorted(), true) && t.checkExpect(this.group.isSorted(), false)
        && t.checkExpect(this.groupS.isSorted(), true);
  }

  // test the method interleave for the lists of Strings
  boolean testInterleave(Tester t) {
    return t.checkExpect(this.alphabet0.interleave(this.alphabet1), this.alphabetS)
        && t.checkExpect(this.vocals0.interleave(this.vocals1), this.vocalsS)
        && t.checkExpect(this.vocals2.interleave(this.vocals3), this.vocalsI)
        && t.checkExpect(new MtLoString().interleave(this.vocals1), this.vocals1)
        && t.checkExpect(this.vocals0.interleave(new MtLoString()), this.vocals0);
  }

  // test the method merge for the lists of Strings
  boolean testMerge(Tester t) {
    return t.checkExpect(this.alphabet0.merge(this.alphabet1), this.alphabetS)
        && t.checkExpect(this.vocals0.merge(this.vocals1), this.vocalsS)
        && t.checkExpect(this.vocals2.merge(this.vocals3), this.vocalsS)
        && t.checkExpect(new MtLoString().merge(this.vocals1), this.vocals1)
        && t.checkExpect(this.vocals0.merge(new MtLoString()), this.vocals0);
  }

  // test the method reverse for the lists of Strings
  boolean testReverse(Tester t) {
    return t.checkExpect(this.alphabet0.reverse(), this.alphabet0R)
    && t.checkExpect(this.alphabet1.reverse(), this.alphabet1R)
    && t.checkExpect(this.vocals0.reverse(), this.vocals0R)
    && t.checkExpect(this.vocals1.reverse(), this.vocals1R)
    && t.checkExpect(new MtLoString().reverse(), new MtLoString());
  }

  // test the method isDoubledList for the lists of Strings
  boolean testIsDoubledList(Tester t) {
    return t.checkExpect(this.alphabet0.isDoubledList(), false)
    && t.checkExpect(this.alphabetDL.isDoubledList(),    true)
    && t.checkExpect(this.vocals0.isDoubledList(),      false)
    && t.checkExpect(this.vocalsDL.isDoubledList(),      true)
    && t.checkExpect(this.group.isDoubledList(),        false)
    && t.checkExpect(this.groupDL.isDoubledList(),       true)
    && t.checkExpect(new MtLoString().isDoubledList(),   true);
  }

  // test the method  for the lists of Strings
  boolean testIsPalindromeList(Tester t) {
    return t.checkExpect(this.alphabet0.isPalindromeList(), false)
    && t.checkExpect(this.alphabetP.isPalindromeList(),     true)
    && t.checkExpect(this.vocals0.isPalindromeList(),       false)
    && t.checkExpect(this.vocalsP.isPalindromeList(),       true)
    && t.checkExpect(this.group.isPalindromeList(),         false)
    && t.checkExpect(this.groupP.isPalindromeList(),        true)
    && t.checkExpect(new MtLoString().isPalindromeList(),   true);
  }
}
