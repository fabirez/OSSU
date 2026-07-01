/* # Variant C

Again, the list must contain numbers satisfying the three requirements above.
Again, order does not matter.
Again, a given number in the list may only be used to satisfy a single requirement.
This time, however, the list may not contain any extraneous numbers.
So, (list 6 5 6) satisfies all our criteria for this variant, but (list 6 5 42 6) does not.

> Do Now!
> Design a third method on lists of integers to check whether the list meets this new property.
*/

import tester.*;

interface ILoNumberC{ 
  boolean treeR();

  boolean isEven(int num);
  boolean isPositiveAndOdd(int num);
  boolean isBetween5And10(int num);
  
  boolean treeRHelper(boolean fr, boolean sr, boolean tr);

  int count();
}


class ConsLoNumberC implements ILoNumberC{
  int    first;
  ILoNumberC rest;


  ConsLoNumberC(int first, ILoNumberC rest){
    this.first = first;
    this.rest =  rest;
  }

  public boolean isEven(int num){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumberC
     */
    return this.first % 2 == 0;
  }
  
  public boolean isPositiveAndOdd(int num){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumberC
     * Method
     * this.first.isEven()           -- boolean
     */
    return this.first >= 0 && this.first % 2 != 0;
  }

  public boolean isBetween5And10(int num){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumberC
     * Method
     * this.first.isEven()           -- boolean
     * this.first.isPositiveAndOdd() -- boolean
     */
    return this.first >= 5 && this.first <= 10;
  }

  public int count(){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumberC
     * Method
     * this.first.isEven()           -- boolean
     * this.first.isPositiveAndOdd() -- boolean
     * this.first.isBetween5And10()  -- boolean
     */
    return 1 + this.rest.count();
  }

  public boolean treeR(){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumberC
     * Method
     * this.isEven()           -- boolean
     * this.isPositiveAndOdd() -- boolean
     * this.isBetween5And10()  -- boolean
     * this.count()            -- int
     */
    if(this.count() != 3){
      return false;
    }else{
      return this.treeRHelper(false,false,false);
    }
  }


  public boolean treeRHelper(boolean fr, boolean sr, boolean tr){
    /* Field
     * this.first -- int
     * this.rest  -- ILoNumberC
     * Method
     * this.isEven()           -- boolean
     * this.isPositiveAndOdd() -- boolean
     * this.isBetween5And10()  -- boolean
     * this.treeR()            -- boolean
     * this.count()            -- int
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



class MtLoNumberC implements ILoNumberC{
  MtLoNumberC(){}

  public boolean isEven(int num){
    return false;
  }
  
  public boolean isPositiveAndOdd(int num){
    return false;
  }

  public boolean isBetween5And10(int num){
    return false;
  }

  public int count(){
    return 0;
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

class ExamplesILoNumberC{
  ExamplesILoNumberC(){}

  ILoNumberC empty = new MtLoNumberC();

  ILoNumberC lon0 = new ConsLoNumberC(6, new ConsLoNumberC(5, new ConsLoNumberC(6, this.empty)));
  ILoNumberC lon1 = new ConsLoNumberC(6, new ConsLoNumberC(5, new ConsLoNumberC(42, new ConsLoNumberC(6, this.empty))));

  ILoNumberC lon2 = new ConsLoNumberC(6, new ConsLoNumberC(5, this.empty));
  ILoNumberC lon3 = new ConsLoNumberC(4, new ConsLoNumberC(3, this.empty));
  ILoNumberC lon4 = new ConsLoNumberC(1, new ConsLoNumberC(2, new ConsLoNumberC(3, this.empty)));

  ILoNumberC lon5 = new ConsLoNumberC(-1, new ConsLoNumberC(2, new ConsLoNumberC(6, this.empty)));
  ILoNumberC lon6 = new ConsLoNumberC(1, new ConsLoNumberC(2, new ConsLoNumberC(6, this.empty)));

  boolean testTreeR(Tester t){
    return
    t.checkExpect(this.lon0.treeR(),  true) &&
    t.checkExpect(this.lon1.treeR(), false) &&
    t.checkExpect(this.lon2.treeR(), false) &&
    t.checkExpect(this.lon3.treeR(), false) &&
    t.checkExpect(this.lon4.treeR(), false) &&
    t.checkExpect(this.lon5.treeR(), false) &&
    t.checkExpect(this.lon6.treeR(),  true);
  }
}

