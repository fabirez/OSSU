/*
# Variant A
Suppose you are given a list of integers. Your task is to determine if this list contains:

- A number that is even
- A number that is positive and odd
- A number between 5 and 10, inclusive

The order in which you find numbers in the list satisfying these requirements does not matter. The list could have many more numbers than you need. Any number in the list may satisfy multiple requirements. For example, the list (in Racket notation) (list 6 5) satisfies all three requirements, while the list (list 4 3) does not.

> Do Now!
> Design a method on lists of integers to check whether the list satisfies these criteria.
> Hint: what information do you need to propagate down the recursive calls as you process the list?
*/

import tester.*;

interface ILoNumber{ 
  boolean treeR();

  boolean isEven(int num);
  boolean isPositiveAndOdd(int num);
  boolean isBetween5And10(int num);
  
  boolean treeRHelper(boolean fr, boolean sr, boolean tr);
}


class ConsLoNumber implements ILoNumber{
  int    first;
  ILoNumber rest;


  ConsLoNumber(int first, ILoNumber rest){
    this.first = first;
    this.rest =  rest;
  }

  public boolean isEven(int num){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumber
     */
    return this.first % 2 == 0;
  }
  
  public boolean isPositiveAndOdd(int num){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumber
     * Method
     * this.first.isEven()           -- boolean
     */
    return this.first >= 0 && this.first % 2 != 0;
  }

  public boolean isBetween5And10(int num){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumber
     * Method
     * this.first.isEven()           -- boolean
     * this.first.isPositiveAndOdd() -- boolean
     */
    return this.first >= 5 && this.first <= 10;
  }

  public boolean treeR(){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumber
     * Method
     * this.first.isEven()           -- boolean
     * this.first.isPositiveAndOdd() -- boolean
     * this.first.isBetween5And10()  -- boolean
     */
    return this.treeRHelper(false,false,false);
  }

  public boolean treeRHelper(boolean fr, boolean sr, boolean tr){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumber
     * Method
     * this.first.isEven()           -- boolean
     * this.first.isPositiveAndOdd() -- boolean
     * this.first.isBetween5And10()  -- boolean
     */
    if(fr == false && sr == false && tr == false){
      return this.rest.treeRHelper(
        isEven(this.first),
        isPositiveAndOdd(this.first),
        isBetween5And10(this.first));
    }
    else if(fr == true && sr == false && tr == false){
      return this.rest.treeRHelper(
        fr,
        isPositiveAndOdd(this.first),
        isBetween5And10(this.first));
    }
    else if(fr == false && sr == true && tr == false){
      return this.rest.treeRHelper(
        isEven(this.first),
        sr,
        isBetween5And10(this.first));
    }
    else if(fr == false && sr == false && tr == true){
      return this.rest.treeRHelper(
        isEven(this.first),
        isPositiveAndOdd(this.first),
        tr);
    }
    else if(fr == true && sr == true && tr == false){
      return this.rest.treeRHelper(
        fr,
        sr,
        isBetween5And10(this.first));
    }else if(fr == true && sr == false && tr == true){
      return this.rest.treeRHelper(
        fr,
        isPositiveAndOdd(this.first),
        tr);
    }else if(fr == false && sr == true && tr == true){
      return this.rest.treeRHelper(
        isEven(this.first),
        sr,
        tr);
    }
      return true;
    }
  }



class MtLoNumber implements ILoNumber{
  MtLoNumber(){}

  public boolean isEven(int num){
    return false;
  }
  
  public boolean isPositiveAndOdd(int num){
    return false;
  }

  public boolean isBetween5And10(int num){
    return false;
  }

  public boolean treeR(){
    return false;
  }

  public boolean treeRHelper(boolean fr, boolean sr, boolean tr){
    if(fr == true && sr == true && tr == true){
      return true;
    }else{
    return false;
    }
  }
}

class ExamplesILoNumber{
  ExamplesILoNumber(){}

  ILoNumber empty = new MtLoNumber();

  ILoNumber lon0 = new ConsLoNumber(6, new ConsLoNumber(5, this.empty));
  ILoNumber lon1 = new ConsLoNumber(4, new ConsLoNumber(3, this.empty));

  ILoNumber lon2 = new ConsLoNumber(1, new ConsLoNumber(2, new ConsLoNumber(3, this.empty)));

  ILoNumber lon3 = new ConsLoNumber(-1, new ConsLoNumber(2, new ConsLoNumber(6, this.empty)));
  ILoNumber lon4 = new ConsLoNumber(1, new ConsLoNumber(2, new ConsLoNumber(6, this.empty)));



  boolean testTreeR(Tester t){
    return
    t.checkExpect(this.lon0.treeR(),  true) &&
    t.checkExpect(this.lon1.treeR(), false) &&
    t.checkExpect(this.lon2.treeR(), false) &&
    t.checkExpect(this.lon3.treeR(), false) &&
    t.checkExpect(this.lon4.treeR(),  true);
  }
}

