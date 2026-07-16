/* Exercise
  Design a method append for lists of Strings twice: first in direct style,
  and then again using an accumulator parameter.
  Do you notice any differences in the output? (Did you write enough tests?)
*/
import tester.*;

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
