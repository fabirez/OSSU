import tester.*;
// NOTE: Templates and purpose statements left out: You should fill them in yourself!
interface IGamePiece0 {
  int getValue();
}
class BaseTile implements IGamePiece0 {
  int value;
  BaseTile(int value) { this.value = value; }
  public int getValue() { return this.value; }
}
class MergeTile implements IGamePiece0 {
  IGamePiece0 piece1, piece2;
  MergeTile(IGamePiece0 piece1, IGamePiece0 piece2) {
    this.piece1 = piece1;
    this.piece2 = piece2;
  }
  public int getValue() {
    return this.piece1.getValue() + this.piece2.getValue() + this.getValue();
  }
}
 
class ExamplesGamePiece {
  IGamePiece0 four = new MergeTile(new BaseTile(2), new BaseTile(2));
  boolean testGetValue(Tester t) {
    return t.checkExpect(four.getValue(), 4);
  }
}

/*
Do Now!

What implementation mistakes do you see in the code above? 
(Ignore the lack of purpose statements and templates: 
those are deliberate since we do not intend to give you the answer here!) 
Write down your predictions first, before running this code.
*/

// > Infinite recursion (stack overflow) caused by `this.getValue` in the body of the function`getValue` by the class `MergeTile`. (WRONG)
// > A NullPointerException, i think caused by `this.piece1 = piece2` inside the constrcut by the class `MergeTile`

/*Do Now!

Try clicking on these links, yourself. What happens when you click on a link that doesn’t belong to your own code?
*/
// > Navigate in the implementation of that class by java 

/*Do Now!

What line of code do you think might be useful to pause on? Why?
*/
// > Based on the stack trace, i would say 18 that is the function that throw the exception.
