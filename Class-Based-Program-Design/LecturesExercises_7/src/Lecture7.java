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

// To return the younger of this ancestor tree and the given ancestor tree
IAT youngerIAT(IAT other);
// To return either this ancestor tree (if this ancestor tree is younger
// than the given yob) or the given ancestry tree
IAT youngerIATHelp(IAT other, int otherYob);

// To compute the youngest parent of this ancestry tree
IAT youngestParent();

// To compute the youngest grandparent of this ancestry tree
IAT youngestGrandparent();

// To compute the youngest ancestor in the given generation of this ancestry tree
IAT youngestAncInGen(int gen);

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

  public IAT youngerIAT(IAT other) { return other; }
  // To return either this Unknown (if this Unknown is younger than the
  // given yob) or the given ancestry tree
  public IAT youngerIATHelp(IAT other, int otherYob) { return other; }

  // To compute the youngest parent of this Unknown
  public IAT youngestParent() { return new Unknown(); }

  // To compute the youngest grandparent of this Unknown
  public IAT youngestGrandparent() { return new Unknown(); }

  // To compute the youngest ancestor in the given generation of this Unknown
  public IAT youngestAncInGen(int gen) {
      if (gen == 0) {
          return this;
      }
      else {
          return new Unknown();
      }
  }
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


  // To return the younger of this Person and the given ancestor tree
  public IAT youngerIAT(IAT other) {
      /* Template
       * Fields:
       * this.yob -- int
       * ... others as before
       * Methods:
       * this.youngerIAT(IAT) -- IAT
       * this.youngerIATHelp(IAT, int) -- IAT
       * ... others as before
       * Methods on fields
       * ... others as before
       * Parameters
       * other -- IAT
       * Methods on parameters
       * other.youngerIAT(IAT) -- IAT
       * other.youngerIATHelp(IAT, int) -- IAT
       */
       return other.youngerIATHelp(this, this.yob);
  }
  // To return either this Person (if this Person is younger than the
  // given yob) or the given ancestry tree
  public IAT youngerIATHelp(IAT other, int otherYob) {
      /* same template as above */
      if (this.yob > otherYob) {
          return this;
      }
      else {
          return other;
      }
  }

  // To compute the youngest parent of this Person
  public IAT youngestParent() {
      /* Template:
       * Fields:
       * this.mom -- IAT
       * this.dad -- IAT
       * ... others as before
       * Methods:
       * this.youngestParent() -- IAT
       * this.youngerIAT(IAT other) --- IAT
       * this.youngerIATHelp(IAT other, int otherYob) --- IAT
       * Methods of fields:
       * this.mom.youngestParent() -- IAT
       * this.mom.youngerIAT(IAT other) --- IAT
       * this.mom.youngerIATHelp(IAT other, int otherYob) --- IAT
       * this.dad.youngestParent() -- IAT
       * this.dad.youngerIAT(IAT other) --- IAT
       * this.dad.youngerIATHelp(IAT other, int otherYob) --- IAT
       */
    return this.mom.youngerIAT(this.dad);
  }

  // To compute the youngest grandparent of this Person
  public IAT youngestGrandparent() {
      /* Template:
       * Fields:
       * this.mom -- IAT
       * this.dad -- IAT
       * ... others as before
       * Methods:
       * this.youngestParent() -- IAT
       * this.youngestGrandparent() -- IAT
       * this.youngerIAT(IAT other) --- IAT
       * this.youngerIATHelp(IAT other, int otherYob) --- IAT
       * Methods of fields:
       * this.mom.youngestParent() -- IAT
       * this.mom.youngestGrandparent() -- IAT
       * this.mom.youngerIAT(IAT other) --- IAT
       * this.mom.youngerIATHelp(IAT other, int otherYob) --- IAT
       * this.dad.youngestParent() -- IAT
       * this.dad.youngestGrandparent() -- IAT
       * this.dad.youngerIAT(IAT other) --- IAT
       * this.dad.youngerIATHelp(IAT other, int otherYob) --- IAT
       */
      // return this.mom.youngestParent().youngerIAT(this.dad.youngestParent());
      return this.youngestAncInGen(2);
  }

  // To compute the youngest ancestor in the given generation of this Person
  public IAT youngestAncInGen(int gen) {
      /* Template:
       * Fields:
       * this.mom -- IAT
       * this.dad -- IAT
       * ... others as before
       * Methods:
       * this.youngestAncInGen(int gen) -- IAT
       * this.youngerIAT(IAT other) --- IAT
       * this.youngerIATHelp(IAT other, int otherYob) --- IAT
       * Methods of fields:
       * this.mom.youngestAncInGen(int gen) -- IAT
       * this.mom.youngerIAT(IAT other) --- IAT
       * this.mom.youngerIATHelp(IAT other, int otherYob) --- IAT
       * this.dad.youngestAncInGen(int gen) -- IAT
       * this.dad.youngerIAT(IAT other) --- IAT
       * this.dad.youngerIATHelp(IAT other, int otherYob) --- IAT
       * Parameters:
       * gen -- int
       */
      if (gen == 0) {
          return this;
      }
      else {
          return this.mom.youngestAncInGen(gen - 1).youngerIAT(this.dad.youngestAncInGen(gen - 1));
      }
  }
}

interface ILoString { 
  // produce a new listof string, with the given string.
  ILoString append(String newString);

  // produce a new listof string, with the given string. Using the accumulator
  // ASSUME: the accumulator is never > than the length of the list
  ILoString appendAcc(String newString, int acc);
}

class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    ConsLoString(String first, ILoString rest) {
        this.first = first;
        this.rest = rest;
    }


  // return a new listof string, with the given string.
  public ILoString append(String newString){
    /*Field
     * this.first --    String
     * this.rest  -- ILoString
     * */
    return new ConsLoString(this.first, this.rest.append(newString));
  }


  // return a new listof string, with the given string. Using the accumulator
  // ASSUME: the accumulator is never > than the length of the list
  public ILoString appendAcc(String newString, int acc){
    /*Field
     * this.first --    String
     * this.rest  -- ILoString
     * */
    if(acc == 0){
      return this.rest.appendAcc(newString, acc);
    }else{
      return new ConsLoString(this.first, this.rest.appendAcc(newString, acc - 1));
    }
  }

}
class MtLoString implements ILoString {
    MtLoString() { }

  public ILoString append(String newString){
    return new ConsLoString(newString, this);
  }

  public ILoString appendAcc(String newString, int acc){
    // return new MtLoString();
    return new ConsLoString(newString, this);
  }
}

class ExamplesILoString {
  ExamplesILoString(){}

  ILoString empty = new MtLoString();
  ILoString  los1 = new ConsLoString("One", new ConsLoString("Two", this.empty));
  ILoString  los2 = new ConsLoString("How Are you?", this.empty);
  ILoString  los3 = new ConsLoString("Hello", this.los2);

  ILoString  los1_s1 = new ConsLoString("One", new ConsLoString("Two", new ConsLoString("Three", this.empty)));
  ILoString  los1_s2 = new ConsLoString("How Are you?", new ConsLoString("I'm good", this.empty));
  ILoString  los1_s3 = new ConsLoString("Hello", new ConsLoString("How Are you?", new ConsLoString("Fine", this.empty)));

  boolean testAppend(Tester t) {
        return
            t.checkExpect(this.los1.append("Three"),    this.los1_s1) &&
            t.checkExpect(this.los2.append("I'm good"), this.los1_s2) &&
            t.checkExpect(this.los3.append("Fine"),     this.los1_s3) &&
            t.checkExpect(this.empty.append("Hello"),   new ConsLoString("Hello", this.empty));
    }

  boolean testAppendAcc(Tester t) {
        return
            t.checkExpect(this.los1.appendAcc("Three", 2),    this.los1_s1) &&
            t.checkExpect(this.los2.appendAcc("I'm good", 1), this.los1_s2) &&
            t.checkExpect(this.los3.appendAcc("Fine", 2),     this.los1_s3) &&
            t.checkExpect(this.empty.appendAcc("Hello", 0),   new ConsLoString("Hello", this.empty));
    }
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

    boolean testYoungerIAT(Tester t) {
        return
            t.checkExpect(this.andrew.youngerIAT(this.bill), this.andrew) &&
            t.checkExpect(this.bill.youngerIAT(this.andrew), this.andrew) &&
            t.checkExpect(this.bree.youngerIAT(this.dixon),    this.bree) &&
            t.checkExpect(this.enid.youngerIAT(new Unknown()), this.enid) &&
            t.checkExpect(new Unknown().youngerIAT(this.enid), this.enid) &&
            t.checkExpect(new Unknown().youngerIAT(new Unknown()), new Unknown());
    }

    boolean testYoungestParent(Tester t) {
        return
            t.checkExpect(this.andrew.youngestParent(), this.bree) &&
            t.checkExpect(this.bill.youngestParent(), this.candace) &&
            t.checkExpect(this.bree.youngestParent(),    this.cameron) &&
            t.checkExpect(new Unknown().youngestParent(), new Unknown());
    }

    boolean testYoungestGrandparent(Tester t) {
        return
            t.checkExpect(this.andrew.youngestGrandparent(), this.candace) &&
            t.checkExpect(this.bill.youngestGrandparent(), this.darren) &&
            t.checkExpect(this.bree.youngestGrandparent(),    this.dixon) &&
            t.checkExpect(new Unknown().youngestGrandparent(), new Unknown());
    }


    boolean testYoungestAncInGen(Tester t) {
        return
            t.checkExpect(this.andrew.youngestAncInGen(1),    this.bree) &&
            t.checkExpect(this.andrew.youngestAncInGen(2), this.candace) &&
            t.checkExpect(this.andrew.youngestAncInGen(3),   this.dixon) &&
            t.checkExpect(this.andrew.youngestAncInGen(0), this.andrew);
    }
}


/* Do Now!

  Complete the definition of youngestGrandparent, now that youngestAncInGen is defined.
*/

/*
  // To compute the youngest grandparent of this Person
  public IAT youngestGrandparent() {
      /* Template:
       * Fields:
       * this.mom -- IAT
       * this.dad -- IAT
       * ... others as before
       * Methods:
       * this.youngestParent() -- IAT
       * this.youngestGrandparent() -- IAT
       * this.youngerIAT(IAT other) --- IAT
       * this.youngerIATHelp(IAT other, int otherYob) --- IAT
       * Methods of fields:
       * this.mom.youngestParent() -- IAT
       * this.mom.youngestGrandparent() -- IAT
       * this.mom.youngerIAT(IAT other) --- IAT
       * this.mom.youngerIATHelp(IAT other, int otherYob) --- IAT
       * this.dad.youngestParent() -- IAT
       * this.dad.youngestGrandparent() -- IAT
       * this.dad.youngerIAT(IAT other) --- IAT
       * this.dad.youngerIATHelp(IAT other, int otherYob) --- IAT
       
       return this.youngestAncInGen(2);
  }
*/



/* Exercise
  Design a method append for lists of Strings twice: first in direct style,
  and then again using an accumulator parameter.
  Do you notice any differences in the output? (Did you write enough tests?)
*/


