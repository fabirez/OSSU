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
* - [ ] Design the method interleave that takes this list of Strings and a given list of Strings, and produces a list where the first, third, fifth... elements are from this list, and the second, fourth, sixth... elements are from the given list. Any “leftover” elements (when one list is longer than the other) should just be left at the end.
* - Design the method merge that takes this sorted list of Strings and a given sorted list of Strings, and produces a sorted list of Strings that contains all items in both original lists, including duplicates. (This is not the same computation as for interleave, but the two methods are similar. Can you construct an example of two lists such that interleaving them and merging them produce different results? Can you construct another example where the two results are the same?)
* - Design the method reverse that produces a new list of Strings containing the same elements as this list of Strings, but in reverse order.
*  HINT: The cleanest solution to this problem uses a helper method, in a style seen already in this problem.
* - Design the method isDoubledList that determines if this list contains pairs of identical strings, that is, the first and second strings are the same, the third and fourth are the same, the fifth and sixth are the same, etc.
*  HINT: Think carefully about how to test this method.
* - > > > This isn’t the same as the typical definition of palindromes, which asks whether a single string contains the same letters when read in either order.
*  Design the method isPalindromeList that determines whether this list contains the same words reading the list in either order.
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
    // produces a new list, sorted in alphabetical order, treating all Strings as if they were given in all lowercase.
    ILoString sort();
    ILoString insert(String s);
    // determines whether this list is sorted in alphabetical order, in a case-insensitive way.
    boolean isSorted();
    boolean isSortedHelp(String s, boolean acc);
    // produces a list where the first, third, fifth... elements are from this list, and the second, fourth, sixth... elements are from the given list.
    // Any “leftover” elements (when one list is longer than the other) should just be left at the end.
    // ILoString interleave(ILoString los);
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
    MtLoString(){}
    
    // combine all Strings in this list into one
    public String combine() {
       return "";
    }  

    //produces a new list, sorted in alphabetical order, treating all Strings as if they were given in all lowercase.
    public ILoString sort() {
       return this;
    }  
    public ILoString insert(String s){
       return new ConsLoString(s,this);
    }

    // determines whether this list is sorted in alphabetical order, in a case-insensitive way.
    public boolean isSorted(){
      return true; 
    }
    public boolean isSortedHelp(String s, boolean acc){
      return acc;
    }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    
    ConsLoString(String first, ILoString rest){
        this.first = first;
        this.rest = rest;  
    }
    
    /*
     TEMPLATE
     FIELDS:
     ... this.first ...         -- String
     ... this.rest ...          -- ILoString
     
     METHODS
     ... this.combine() ...                       -- String
     ... this.sort() ...                          -- ILoString
     ... this.insert(String) ...                  -- ILoString
     ... this.isSorted() ...                      -- Boolean
     ... this.isSortedHelp(String, Boolean) ...   -- Boolean     

     METHODS FOR FIELDS
     ... this.first.concat(String) ...            -- String
     ... this.first.compareTo(String) ...         -- int
     ... this.rest.combine() ...                  -- String
     ... this.sort() ...                          -- ILoString
     ... this.isSorted() ...                      -- Boolean
     ... this.isSortedHelp(String, Boolean) ...   -- Boolean     
     
     */
    
    // combine all Strings in this list into one
    public String combine(){
        return this.first.concat(this.rest.combine());
    }  

    public ILoString sort(){
      return this.rest.insert(this.first);
    }  
    public ILoString insert(String s){
      if(this.first.toLowerCase().compareTo(s.toLowerCase()) <= 0){
        return new ConsLoString(this.first, this.rest.insert(s));
      }else{
        return new ConsLoString(s, this.rest.insert(this.first));
      }
    }

    // determines whether this list is sorted in alphabetical order, in a case-insensitive way.
    public boolean isSorted(){
      return this.rest.isSortedHelp(this.first, true); 
    }
    public boolean isSortedHelp(String s, boolean acc){
      if(acc == false){
      return false;
      }else{
        if(this.first.toLowerCase().compareTo(s.toLowerCase()) < 0){
          return this.rest.isSortedHelp(this.first, false);
        }else{
          return this.rest.isSortedHelp(this.first, true);
        }
      }
    }
    
}

// to represent examples for lists of strings
class ExamplesStrings{
    
    ILoString mary = new ConsLoString("Mary ",
                    new ConsLoString("had ",
                        new ConsLoString("a ",
                            new ConsLoString("little ",
                                new ConsLoString("lamb.", new MtLoString())))));
    

    ILoString los0 = new ConsLoString("b",
                    new ConsLoString("a",
                        new ConsLoString("a",
                            new ConsLoString("z",
                                new ConsLoString("w", new MtLoString())))));

    ILoString los1 = new ConsLoString("a",
                    new ConsLoString("a",
                        new ConsLoString("b",
                            new ConsLoString("w",
                                new ConsLoString("z", new MtLoString())))));

    ILoString group = new ConsLoString("Bob",
                    new ConsLoString("Alice",
                        new ConsLoString("Kevin",
                            new ConsLoString("George",
                                new ConsLoString("Philip", new MtLoString())))));

    ILoString groupS = new ConsLoString("Alice",
                    new ConsLoString("Bob",
                        new ConsLoString("George",
                            new ConsLoString("Kevin",
                                new ConsLoString("Philip", new MtLoString())))));
    
    // test the method combine for the lists of Strings
    boolean testCombine(Tester t){
        return 
            t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
    }

    // test the method sort for the lists of Strings
    boolean testSort(Tester t){
        return
         t.checkExpect(this.los0.sort(), this.los1) &&
         t.checkExpect(this.group.sort(), this.groupS);
    }

    // test the method sort for the lists of Strings
    boolean testIsSorted(Tester t){
        return
         t.checkExpect(this.mary.isSorted(),   false) &&
         t.checkExpect(this.los0.isSorted(),   false) &&
         t.checkExpect(this.los1.isSorted(),    true) &&
         t.checkExpect(this.group.isSorted(),  false) &&
         t.checkExpect(this.groupS.isSorted(),  true);
    }
}
