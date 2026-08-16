/* # Lab 4: Working with Abstract Classes, Problem Solving
## 4.2 Problem Solving Practice
## 4.2.2 Consevutive Int

- Design a method on a list of integers which produces the integer (or a value indicating the list was empty) that appears in the longest consecutive sublist.
  For example, the list of 1,1,5,5,5,4,3,4,4,4 would produce 5 
  (NOTE: that ties are broken by the sublist that appears earlier in the list).
*/
import tester.*;

interface ILoInt{ 
  // produces the integer (or a value indicating the list was empty) that appears in the longest consecutive sublist.
  int getConsecutiveNum();
  int getConsecutiveNumHelp(int consNum, int rep, int newNum, int newRep);
}


class ConsLoInt implements ILoInt{ 
  int first;
  ILoInt rest;

  ConsLoInt(int first, ILoInt rest){
    this.first = first;
    this.rest = rest;
  }

  public int getConsecutiveNum(){
    return this.rest.getConsecutiveNumHelp(this.first, 1, 0, 0);
  }

  public int getConsecutiveNumHelp(int consNum, int rep, int newNum, int newRep){
    if(newRep > rep){
      return this.getConsecutiveNumHelp(newNum, newRep, 0, 0);
    }else{
      if(this.first == consNum){
        return this.rest.getConsecutiveNumHelp(consNum, rep + 1, 0, 0);
      }else{
      if(this.first == newNum){
        return this.rest.getConsecutiveNumHelp(consNum, rep, newNum, newRep + 1);
      }else{
        return this.rest.getConsecutiveNumHelp(consNum, rep,  this.first, 1);
       }
      }
    }
  }
}

// NOTE:
// If i decide that -1, means empty list, that would not be correct.
// Because a list of integer can have also -1, and can be the most consecutive number in the list.
// So how can i differ my -1 with the empty -1.
// And since every integer can be in the list, and we cannot return a different type
// Throwing an Exceptions seems the best choice, also because, doens't exist a
// consecutive number on an empty list.

class MtLoInt implements ILoInt{
  MtLoInt(){ }

  public int getConsecutiveNum(){
    throw new RuntimeException("There is no consectuive number on an empty list.");
  }

  public int getConsecutiveNumHelp(int consNum, int rep, int newNum, int newRep){
    if(newRep > rep){
      return newNum;
    }else{
      return consNum;
    }
  }

}

class ExamplesILoInt{
  ExamplesILoInt(){};

  ILoInt mt = new MtLoInt();

  ILoInt lon0 = new ConsLoInt(1,
    new ConsLoInt(2,
      new ConsLoInt(3,
        this.mt)));

  ILoInt lon1 = 
    new ConsLoInt(1,
    new ConsLoInt(1,
      new ConsLoInt(5,
        new ConsLoInt(5,
          new ConsLoInt(5,
            new ConsLoInt(4,
              new ConsLoInt(3,
                new ConsLoInt(4, 
                  new ConsLoInt(4,
                    new ConsLoInt(4,
                        this.mt))))))))));

  ILoInt lon2 = 
    new ConsLoInt(1,
    new ConsLoInt(1,
      new ConsLoInt(5,
        new ConsLoInt(5,
          new ConsLoInt(5,
            new ConsLoInt(4,
              new ConsLoInt(3,
                new ConsLoInt(4, 
                  new ConsLoInt(4,
                    new ConsLoInt(4,
                      new ConsLoInt(4,
                        this.mt)))))))))));

  boolean testGetConsecutiveNum(Tester t){
    return
    t.checkExpect(lon0.getConsecutiveNum(),  1) &&
    t.checkExpect(lon1.getConsecutiveNum(),  5) &&
    t.checkExpect(lon2.getConsecutiveNum(),  4) &&
    t.checkException(new RuntimeException("There is no consectuive number on an empty list."), new MtLoInt(), "getConsecutiveNum")
    ;
  }
}
