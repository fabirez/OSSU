import tester.*;

interface IAT {
// To compute the number of known ancestors of this ancestor tree (*excluding* this ancestor tree itself)
int count();

// To compute the number of known ancestors of this ancestor tree (*including* this ancestor tree itself)
int countHelp();

//To compute how many ancestors of this ancestor tree (*excluding* this ancestor tree itself) are women older than 40 (in the current year)?
int femaleAncOver40();

//To compute how many ancestors of this ancestor tree (*including* this ancestor tree itself) are women older than 40 (in the current year)?
int femaleAncOver40Help();

// counts how many generations (including this IAT’s generation) are completely known.
int numTotalGens();
int numTotalGensHelper(); 
boolean bothParentsKnown(); 

// return true if it is a persone, false otherwise;
boolean isKnown();

// counts how many generations (including this IAT’s generation) are at least partially known.
int numPartialGens();

//To compute whether this ancestor tree is well-formed: are all known
//people younger than their parents?
// boolean wellFormed();

//To compute the names of all the known ancestors in this ancestor tree
//(including this ancestor tree itself)
// ILoString ancNames();

//To compute this ancestor tree's youngest grandparent
// IAT youngestGrandparent();
}

class Unknown implements IAT {
   Unknown() { }
  // To compute the number of known ancestors of this Unknown (*excluding* this Unknown itself)
  public int count() { return 0; }

  // To compute the number of known ancestors of this Unknown (*including* this Unknown itself)
  public int countHelp() { return 0; }

  // To compute how many ancestors of this ancestor tree (*excluding* this ancestor tree itself) are women older than 40 (in the current year)?
  public int femaleAncOver40() { return 0; }

  // To compute how many ancestors of this ancestor tree (*including* this ancestor tree itself) are women older than 40 (in the current year)?
  public int femaleAncOver40Help() { return 0; }

  // return true if it is a person, false otherwise;
  public boolean isKnown(){ return false; }

  // counts how many generations (including this IAT’s generation) are completely known.
  public int numTotalGens() { return 0; }
  public int numTotalGensHelper() { return 0; }
    public boolean bothParentsKnown() { return false; }

  // counts how many generations (including this IAT’s generation) are at least partially known.
  public int numPartialGens() { return 0; }
}

class Person implements IAT {
    String name;
    int yob;
    boolean isMale;
    IAT mom;
    IAT dad;
    Person(String name, int yob, boolean isMale, IAT mom, IAT dad) {
        this.name = name;
        this.yob = yob;
        this.isMale = isMale;
        this.mom = mom;
        this.dad = dad;
    }

  // return true if the person is over 40, false otherwise.
  boolean over40(){
    return 2022 - this.yob > 40;
  }

  // To compute the number of known ancestors of this Person (excluding this Person itself)
  public int count() {
      /* Template:
       * Fields:
       * this.name -- String
       * this.yob -- int
       * this.isMale -- boolean
       * this.mom -- IAT
       * this.dad -- IAT
       * Methods:
       * this.count() -- int
       * Methods of fields:
       * this.mom.count() -- int
       * this.dad.count() -- int
       */
    return this.mom.countHelp() + this.dad.countHelp();
  }

  // To compute the number of known ancestors of this Unknown (*including* this Unknown itself)
  public int countHelp() { 
    return this.mom.countHelp() + this.dad.countHelp() + 1;
  }

  //To compute how many ancestors of this ancestor tree (*excluding* this ancestor tree itself) are women older than 40 (in the current year)?
  public int femaleAncOver40() {
    return this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
  }

  //To compute how many ancestors of this ancestor tree (*including* this ancestor tree itself) are women older than 40 (in the current year)?
  public int femaleAncOver40Help() {
    if(this.isMale == false && this.over40()){
      return 1 + this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
    }else{
      return this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
    }
  }

  // return true if it is a persone, false otherwise;
  public boolean isKnown(){
    return true;
  }

  // counts how many generations (including this IAT’s generation) are at least partially known.
  public int numPartialGens() { 
        return 1 + Math.max(this.mom.numPartialGens(), this.dad.numPartialGens());
  }

    // ===============================================================================================
    // ===============================================================================================
    // ===============================================================================================

    public int numTotalGens(){
        return 1 + this.numTotalGensHelper();
    }

    // produce true if the mom and dad are not Unknown (?); otherwise false
    public boolean bothParentsKnown(){
        return this.mom.isKnown() && this.dad.isKnown();
    }

    public int numTotalGensHelper(){
        if(this.mom.bothParentsKnown() && this.dad.bothParentsKnown()){
            return 1 + Math.max(this.mom.numTotalGensHelper(), this.dad.numTotalGensHelper());
        }else{
            return 0 + Math.max(this.mom.numTotalGensHelper(), this.dad.numTotalGensHelper());
        }
    }


}

interface ILoString { }

class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    ConsLoString(String first, ILoString rest) {
        this.first = first;
        this.rest = rest;
    }
}
class MtLoString implements ILoString {
    MtLoString() { }
}


class ExamplesIAT {
    IAT enid = new Person("Enid", 1904, false, new Unknown(), new Unknown());
    IAT edward = new Person("Edward", 1902, true, new Unknown(), new Unknown());
    IAT emma = new Person("Emma", 1906, false, new Unknown(), new Unknown());
    IAT eustace = new Person("Eustace", 1907, true, new Unknown(), new Unknown());
 
    IAT david = new Person("David", 1925, true, new Unknown(), this.edward);
    IAT daisy = new Person("Daisy", 1927, false, new Unknown(), new Unknown());
    IAT dana = new Person("Dana", 1933, false, new Unknown(), new Unknown());
    IAT darcy = new Person("Darcy", 1930, false, this.emma, this.eustace);
    IAT darren = new Person("Darren", 1935, true, this.enid, new Unknown());
    IAT dixon = new Person("Dixon", 1936, true, new Unknown(), new Unknown());
 
    IAT clyde = new Person("Clyde", 1955, true, this.daisy, this.david);
    IAT candace = new Person("Candace", 1960, false, this.dana, this.darren);
    IAT cameron = new Person("Cameron", 1959, true, new Unknown(), this.dixon);
    IAT claire = new Person("Claire", 1956, false, this.darcy, new Unknown());
 
    IAT bill = new Person("Bill", 1980, true, this.candace, this.clyde);
    IAT bree = new Person("Bree", 1981, false, this.claire, this.cameron);
 
    IAT andrew = new Person("Andrew", 2001, true, this.bree, this.bill);
 
    boolean testCount(Tester t) {
        return
            t.checkExpect(this.andrew.count(), 16) &&
            t.checkExpect(this.david.count(), 1) &&
            t.checkExpect(this.enid.count(), 0) &&
            t.checkExpect(new Unknown().count(), 0);
    }

    boolean testFemaleAncOver40(Tester t) {
        return
            t.checkExpect(this.andrew.femaleAncOver40(), 8) &&
            t.checkExpect(this.bree.femaleAncOver40(), 3) &&
            t.checkExpect(this.darcy.femaleAncOver40(), 1) &&
            t.checkExpect(this.enid.femaleAncOver40(), 0) &&
            t.checkExpect(new Unknown().femaleAncOver40(), 0);
    }

    boolean testNumGens(Tester t) {
        return
            t.checkExpect(this.andrew.numTotalGens(), 3) &&
            t.checkExpect(this.andrew.numPartialGens(), 5) &&
            t.checkExpect(this.enid.numTotalGens(), 1) &&
            t.checkExpect(this.enid.numPartialGens(), 1) &&
            t.checkExpect(new Unknown().numTotalGens(), 0) &&
            t.checkExpect(new Unknown().numPartialGens(), 0);
    }

    // boolean testWellFormed(Tester t) {
    //     return
    //         t.checkExpect(this.andrew.wellFormed(), true) &&
    //         t.checkExpect(new Unknown().wellFormed(), true) &&
    //         t.checkExpect(
    //             new Person("Zane", 2000, true, this.andrew, this.bree).wellFormed(),
    //             false);
    // }
    // boolean testAncNames(Tester t) {
    //     return
    //         t.checkExpect(this.david.ancNames(),
    //             new ConsLoString("David",
    //                 new ConsLoString("Edward", new MtLoString()))) &&
    //         t.checkExpect(this.eustace.ancNames(),
    //             new ConsLoString("Eustace", new MtLoString())) &&
    //         t.checkExpect(new Unknown().ancNames(), new MtLoString());
    // }
    // boolean testYoungestGrandparent(Tester t) {
    //     return
    //         t.checkExpect(this.emma.youngestGrandparent(), new Unknown()) &&
    //         t.checkExpect(this.david.youngestGrandparent(), new Unknown()) &&
    //         t.checkExpect(this.claire.youngestGrandparent(), this.eustace) &&
    //         t.checkExpect(this.bree.youngestGrandparent(), this.dixon) &&
    //         t.checkExpect(this.andrew.youngestGrandparent(), this.candace) &&
    //         t.checkExpect(new Unknown().youngestGrandparent(), new Unknown());
    // }
}

/* Do Now
 Draw the ancestry tree for the example data above,
 and confirm that the test outputs are actually what we expect.
*/

// No unkwon represented on the leafs

//                          Andrew
//                      /             \
//                  Bree               Bill
//               /       \          /       \
//          Claire     Cameron   Candace   Clyde
//         /    \       /   \     /   \    /   \
//     Darcy     ?     ?   Dixon Dana Darren Daisy David
//    /    \                    / \           /    \
// Emma  Eustace             Enid  ?         ?    Edward
//
//
//

/* Do Now
  What exactly will invoking this.mom.count() produce?
  Specialize the purpose statement for count to this.mom.
*/
// > produce the number of her ancestry tree

/* Do Now
 *

  public int count() {
      return this.mom.count() + this.dad.count();
  }
  Does this pass our tests? Why or why not?
*/

// > No, beacuse we are not counting
//
//


/* Do Now
 *
  // In Person:
  public int count() {
      return 1 + this.mom.count() + this.dad.count();
  }

 Does this pass our tests? Why or why not?
*/

// Still not, since we are counting the son itself, and it's plus 1.



/* Do Now
 *

// In IAT:
  // To compute the number of known ancestors of this ancestor tree (excluding this ancestor tree itself)
  int count();
  // To compute the number of known ancestors of this ancestor tree (*including* this ancestor tree itself)
  int countHelp();

// In Unknown:
  // To compute the number of known ancestors of this Unknown (excluding this Unknown itself)
  public int count() { return 0; }
  // To compute the number of known ancestors of this Unknown (*including* this Unknown itself)
  public int countHelp() { return 0; }

// In Person:
  // To compute the number of known ancestors of this Person (excluding this Person itself)
  public int count() {
      return this.mom.countHelp() + this.dad.countHelp();
  }

  // To compute the number of known ancestors of this Person (*including* this Person itself)
  public int countHelp() {
      return 1 + this.mom.countHelp() + this.dad.countHelp();
  }
 Does this pass our tests? Why or why not?

*/

// Yes, because we are counting only the anchestors that we need to!


/* Do Now!
Adapt the previous solution to count, to design the method femaleAncOver40.
*/

/* Do Now!

  // In Person:
  // To compute how many ancestors of this Person (excluding this Person itself)
  // are women older than 40 (in the current year).
  public int femaleAncOver40() {
      return this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
  }
  // To compute how many ancestors of this Person (*including* this Person itself)
  // are women older than 40 (in the current year).
  public int femaleAncOver40Help() {
      if (2015 - this.yob > 40 && !this.isMale) {
          return 1 + this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
      }
      else {
          return this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
      }
  }

  Does this pass our tests? Why or why not?
*/

// > Yes, beacuse the accumulator method seems good to me!
//

/* Exercise
 *
 *
Design the method numTotalGens,
which counts how many generations (including this IAT’s generation) are completely known.

Design the method numPartialGens,
which counts how many generations (including this IAT’s generation) are at least partially known.

These methods should match the following behavior:

boolean testNumGens(Tester t) {
    return
        t.checkExpect(this.andrew.numTotalGens(), 3) &&
        t.checkExpect(this.andrew.numPartialGens(), 5) &&
        t.checkExpect(this.enid.numTotalGens(), 1) &&
        t.checkExpect(this.enid.numPartialGens(), 1) &&
        t.checkExpect(new Unknown().numTotalGens(), 0) &&
        t.checkExpect(new Unknown().numPartialGens(), 0);
}

*/
