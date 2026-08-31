/*
- [x] Implement the JSONVisitor<T> interface, which is a IFunc<JSON, T> and follows the visitor pattern over JSONs.

- [x] Define a JSONToNumber visitor, which coverts a JSON to its number value.
      Blanks are converted to 0,
      booleans 0 or 1 depending on if the value is false or true,
      strings their length,
      and numbers their value.

- [x] Map over a list of JSON and produce all of their numbers as a test.
*/
import tester.*;

interface JSONVisitor<R> extends IFunc<JSON, R>
{
  R applyToBlank(JSONBlank b);
  R applyToNumber(JSONNumber n);
  R applyToBool(JSONBool b);
  R applyToString(JSONString s);
}

class JSONToNumber implements JSONVisitor<Integer> {

  public Integer applyToBlank(JSONBlank b){ return 0; }
  public Integer applyToNumber(JSONNumber n){ return n.number; }
  public Integer applyToBool(JSONBool b){
    if(b.bool){
      return 1;
    }else{
      return 0;
    }
  }
  public Integer applyToString(JSONString s){ return s.str.length(); }
  public Integer apply(JSON j){ return j.accept(this); }
}



// a json value
interface JSON 
{
  <R> R accept(JSONVisitor<R> visitor);
}
 
// no value
class JSONBlank implements JSON {
  public <R> R accept(JSONVisitor<R> visitor){ return visitor.applyToBlank(this); }
}
 
// a number
class JSONNumber implements JSON {
  int number;
  JSONNumber(int number) { this.number = number; }
  public <R> R accept(JSONVisitor<R> visitor){ return visitor.applyToNumber(this); }
}
 
// a boolean
class JSONBool implements JSON {
  boolean bool;
  JSONBool(boolean bool) { this.bool = bool; }
  public <R> R accept(JSONVisitor<R> visitor){ return visitor.applyToBool(this); }
}
 
// a string
class JSONString implements JSON {
  String str;
  JSONString(String str) { this.str = str; }
  public <R> R accept(JSONVisitor<R> visitor){ return visitor.applyToString(this); }
}

class ExamplesJSON
{
  ExamplesJSON(){};
  JSON blk = new JSONBlank();

  JSON num = new JSONNumber(9);

  JSON bolT = new JSONBool(true);
  JSON bolF = new JSONBool(false);

  JSON str = new JSONString("JSON");

  JSONVisitor<Integer> jTN = new JSONToNumber();

  IList<JSON> JSONList = new ConsList<JSON>(blk, 
  new ConsList<JSON>(num,
  new ConsList<JSON>(bolT,
  new ConsList<JSON>(bolF,
  new ConsList<JSON>(str,
  new MtList<JSON>())))));

  IList<Integer> expectedList = new ConsList<Integer>(0, 
  new ConsList<Integer>(9,
  new ConsList<Integer>(1,
  new ConsList<Integer>(0,
  new ConsList<Integer>(4,
  new MtList<Integer>())))));

  boolean testJSONToNumber(Tester t)
  {
    return
      t.checkExpect(blk.accept(jTN), 0)
      &&
      t.checkExpect(num.accept(jTN), 9)
      &&
      t.checkExpect(bolT.accept(jTN), 1)
      &&
      t.checkExpect(bolF.accept(jTN), 0)
      &&
      t.checkExpect(str.accept(jTN), 4)
      &&
      t.checkExpect(JSONList.map(jTN), expectedList)
      ;
  }
}
