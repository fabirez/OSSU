/* # Lab 4: Working with Abstract Classes, Problem Solving
## 4.2 Problem Solving Practice
## 4.2.1 Maybe X

- Recall \[Maybe X\] from last semester; that is, a [Maybe X] is either an X or false. Design a [Maybe Int] interface and the relevant classes.
  NOTE: that false is somewhat of a red herring here, as it just represents an absent value.
  Think about what kind of class would represent an absent value well.
*/
import tester.*;

interface IMaybeInt{ 
  // produce true if it is an int; otherwise false
  boolean isInt();
}

class MaybeInt implements IMaybeInt{ 
  int val;
  MaybeInt(int val){
    this.val = val;
  }

  public boolean isInt(){
    return true;
  }
}

class MtInt implements IMaybeInt{
  boolean val;
  MtInt(){
    this.val = false;
  }

  public boolean isInt(){
    return false;
  }
}

class ExamplesIMaybeInt{
  ExamplesIMaybeInt(){};

  IMaybeInt mi0 = new MaybeInt(1);
  IMaybeInt mi1 = new MaybeInt(2);
  IMaybeInt mi2 = new MaybeInt(3);

  IMaybeInt mti = new MtInt();

  boolean testIsInt(Tester t){
    return
    t.checkExpect(mi0.isInt(),  true) &&
    t.checkExpect(mi1.isInt(),  true) &&
    t.checkExpect(mi2.isInt(),  true) &&
    t.checkExpect(mti.isInt(), false);
  }
}
