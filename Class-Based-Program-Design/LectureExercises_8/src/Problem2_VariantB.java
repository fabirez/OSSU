/*
# Variant B

Again, the list must contain numbers satisfying the three requirements above.
Again, order does not matter.
This time, a given number in the list may only be used to satisfy a single requirement; however,
duplicate numbers are permitted to satisfy multiple requirements. 
So, (list 6 5) does not meet all the criteria for this variant, but (list 6 5 6) does.

> Do Now!
> Design a new method on lists of integers to check for this stricter property.
> How does your design differ from Variant A?
*/

import tester.*;

interface ILoNumberB{ 
  boolean treeR();

  boolean isEven(int num);
  boolean isPositiveAndOdd(int num);
  boolean isBetween5And10(int num);
  
  boolean treeRHelper(boolean fr, boolean sr, boolean tr);
}


class ConsLoNumberB implements ILoNumberB{
  int    first;
  ILoNumberB rest;


  ConsLoNumberB(int first, ILoNumberB rest){
    this.first = first;
    this.rest =  rest;
  }

  public boolean isEven(int num){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumberB
     */
    return this.first % 2 == 0;
  }
  
  public boolean isPositiveAndOdd(int num){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumberB
     * Method
     * this.first.isEven()           -- boolean
     */
    return this.first >= 0 && this.first % 2 != 0;
  }

  public boolean isBetween5And10(int num){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumberB
     * Method
     * this.first.isEven()           -- boolean
     * this.first.isPositiveAndOdd() -- boolean
     */
    return this.first >= 5 && this.first <= 10;
  }

  public boolean treeR(){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumberB
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
     * this.rest  -- ILoNumberB
     * Method
     * this.first.isEven()           -- boolean
     * this.first.isPositiveAndOdd() -- boolean
     * this.first.isBetween5And10()  -- boolean
     */

    if(fr == false && sr == false && tr == false){
      if(isEven(this.first)){
        return this.rest.treeRHelper( true, sr, tr);
      }else if(isPositiveAndOdd(this.first)){
        return this.rest.treeRHelper(fr, true, tr);
      }else if(isBetween5And10(this.first)){
        return this.rest.treeRHelper(fr, sr, true);
      }else{
        return this.rest.treeRHelper(fr, sr, tr);
      }
    }

    else if(fr == true && sr == false && tr == false){
      if(isPositiveAndOdd(this.first)){
        return this.rest.treeRHelper(fr, true, tr);
      }else if(isBetween5And10(this.first)){
        return this.rest.treeRHelper(fr, sr, true);
      }else{
        return this.rest.treeRHelper(fr, sr, tr);
      }
    }

    else if(fr == false && sr == true && tr == false){
      if(isEven(this.first)){
        return this.rest.treeRHelper(true, sr, tr);
      }else if(isBetween5And10(this.first)){
        return this.rest.treeRHelper(fr, sr, true);
      }else{
        return this.rest.treeRHelper(fr, sr, tr);
      }
    }

    else if(fr == false && sr == false && tr == true){
      if(isEven(this.first)){
        return this.rest.treeRHelper(true, sr, tr);
      }else if(isPositiveAndOdd(this.first)){
        return this.rest.treeRHelper(fr, true, tr);
      }else{
        return this.rest.treeRHelper(fr, sr, tr);
      }
    }

    else if(fr == true && sr == true && tr == false){
      if(isBetween5And10(this.first)){
        return this.rest.treeRHelper(fr, sr, true);
      }else{
        return this.rest.treeRHelper(fr, sr, tr);
      }

    }else if(fr == true && sr == false && tr == true){
      if(isPositiveAndOdd(this.first)){
        return this.rest.treeRHelper(fr, true, tr);
      }else{
        return this.rest.treeRHelper(fr, sr, tr);
      }
    }else if(fr == false && sr == true && tr == true){
      if(isEven(this.first)){
        return this.rest.treeRHelper( true, sr, tr);
      }else{
        return this.rest.treeRHelper(fr, sr, tr);
      }
    }else{
      return true;
    }
    }
  }



class MtLoNumberB implements ILoNumberB{
  MtLoNumberB(){}

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

class ExamplesILoNumberB{
  ExamplesILoNumberB(){}

  ILoNumberB empty = new MtLoNumberB();

  ILoNumberB lon0 = new ConsLoNumberB(6, new ConsLoNumberB(5, this.empty));
  ILoNumberB lon1 = new ConsLoNumberB(6, new ConsLoNumberB(5, new ConsLoNumberB(6, this.empty)));

  ILoNumberB lon2 = new ConsLoNumberB(4, new ConsLoNumberB(3, this.empty));
  ILoNumberB lon3 = new ConsLoNumberB(1, new ConsLoNumberB(2, new ConsLoNumberB(3, this.empty)));

  ILoNumberB lon4 = new ConsLoNumberB(-1, new ConsLoNumberB(2, new ConsLoNumberB(6, this.empty)));
  ILoNumberB lon5 = new ConsLoNumberB(1, new ConsLoNumberB(2, new ConsLoNumberB(6, this.empty)));

  boolean testTreeR(Tester t){
    return
    t.checkExpect(this.lon0.treeR(), false) &&
    t.checkExpect(this.lon1.treeR(),  true) &&
    t.checkExpect(this.lon2.treeR(), false) &&
    t.checkExpect(this.lon3.treeR(), false) &&
    t.checkExpect(this.lon4.treeR(), false) &&
    t.checkExpect(this.lon5.treeR(),  true);
  }
}

