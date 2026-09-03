/*
## Let there be JSON
- [x] Implement the JSONVisitor<T> interface, 
      which is a IFunc<JSON, T> and follows the visitor pattern over JSONs.

- [x] Define a JSONToNumber visitor,
      which coverts a JSON to its number value.
      Blanks are converted to 0,
      booleans 0 or 1 depending on if the value is false or true,
      strings their length,
      and numbers their value.

- [x] Map over a list of JSON and produce all of their numbers as a test.

## What’s data without a little self-reference?
- [x] Extend the visitor pattern on JSONs as needed.
- [x] A JSONList’s value is the sum of all of its sub-values converted to their number value. 
       Extend JSONToNumber as needed.

## Finders, keepers
- [x] Implement an IPred<T> interface, which is a function that always returns a boolean.
      Be sure to extend it with the proper interface.

- [x] Design the findSolutionOrElse method on IList<T>’s,
      which has the following header: <U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup).
      It finds the first element in the list where the result of function applied to that element passes the predicate,
      and then returns that result. If no such element is found, backup is returned.

## JSON: The whole shebang
- [x] Extend the visitor pattern on JSONs as needed.
 
- [x] Define a JSONObject’s numeric value is the sum of all of the values of its sub-JSON components;
      the string keys are ignored. Extend JSONToNumber as needed.

- [x] Define a JSONFind visitor,
      which is constructed with a string and returns the first JSON value it finds in a pair with that string as the keyword.

      If no such element can be found, return a JSONBlank.
      HINT: In order to know whether to continue searching, you’ll need to know if your current value is blank or not.
      You may wish to use instanceof here, and we will allow it here,
      though there are cleaner solutions... but we have not encountered the Java concepts necessary for them yet.
*/
import tester.*;

interface JSONVisitor<R> extends IFunc<JSON, R>, IFunc2<JSON, R, R>
{
  R applyToBlank(JSONBlank b);
  R applyToNumber(JSONNumber n);
  R applyToBool(JSONBool b);
  R applyToString(JSONString s);
  R applyToList(JSONList l);
  R applyToObject(JSONObject o);
}

class JSONGetValue implements IFunc<Pair<String, JSON>, JSON>{
  public JSON apply(Pair<String, JSON> p){
    return p.y;
  }
}

class JSONGetValueFromKey implements IFunc<Pair<String, JSON>, JSON>{
  String key;
  JSONGetValueFromKey(String key){ this.key=key; }
  public JSON apply(Pair<String, JSON> p){
    if(key.equals(p.x)){
      return p.y;
    }else{
      return new JSONBlank();
    }
  }
}

class IsDifferentFromBlank implements IPred<JSON>{
  public boolean apply(JSON val){
    return !(val instanceof JSONBlank);
  }
}

// produce the value of the given key
class JSONFind implements JSONVisitor<JSON>{
  String key;
  JSONFind(String key){this.key=key;}

  public JSON applyToBlank(JSONBlank b){ return b; }
  public JSON applyToNumber(JSONNumber n){ return n; }
  public JSON applyToBool(JSONBool b){return b; }
  public JSON applyToString(JSONString s){ return s; }
  public JSON applyToList(JSONList l){ return l; }
  public JSON applyToObject(JSONObject o)
  {
    return o.pairs.findSolutionOrElse(
      new JSONGetValueFromKey(this.key),
      new IsDifferentFromBlank(),
      new JSONBlank()
    );
  }

  public JSON apply(JSON j){ return j.accept(this); }
  public JSON apply(JSON j, JSON b){ return j.accept(this); }
}


// coverts a JSON to its number value.
class JSONToNumber implements JSONVisitor<Integer> {
  // pruduce 0 for a blank value
  public Integer applyToBlank(JSONBlank b){ return 0; }
  // produce the value of number 
  public Integer applyToNumber(JSONNumber n){ return n.number; }
  // produce 0 or 1 depending on if the value is false or true
  // false -> 0
  //  true -> 1
  public Integer applyToBool(JSONBool b){
    if(b.bool){
      return 1;
    }else{
      return 0;
    }
  }
  // produce the length of the string
  public Integer applyToString(JSONString s){ return s.str.length(); }
  //produce the sum of all of its sub-values converted to their number value. 
  public Integer applyToList(JSONList l){ return l.values.foldr(new JSONToNumber(), 0); }
  // produce the sum of all of the values of its sub-JSON components;
  public Integer applyToObject(JSONObject o){ 
    IList<JSON> onlyValues = o.pairs.map(new JSONGetValue());
    return onlyValues.foldr(new JSONToNumber(), 0);
  }

  //apply for IFunc<JSON, U>
  public Integer apply(JSON j){ return j.accept(this); }
  //apply for IFunc2<JSON, U, U>
  public Integer apply(JSON j, Integer b){ return j.accept(this) + b; }
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

//a list of JSON values
class JSONList implements JSON {
  IList<JSON> values;
  JSONList(IList<JSON> values) { this.values = values; }
  public <R> R accept(JSONVisitor<R> visitor){ return visitor.applyToList(this); }
}

// a list of JSON pairs
class JSONObject implements JSON {
  IList<Pair<String, JSON>> pairs;
  JSONObject(IList<Pair<String, JSON>> pairs) { this.pairs = pairs; }
  public <R> R accept(JSONVisitor<R> visitor){ return visitor.applyToObject(this); }


}
 
// generic pairs
class Pair<X, Y> {
  X x;
  Y y;
 
  Pair(X x, Y y) {
    this.x = x;
    this.y = y;
  }
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

  JSON loj = new JSONList(JSONList);

  IList<Integer> expectedList = new ConsList<Integer>(0, 
  new ConsList<Integer>(9,
  new ConsList<Integer>(1,
  new ConsList<Integer>(0,
  new ConsList<Integer>(4,
  new MtList<Integer>())))));

  Pair<String, JSON> p1 = new Pair(    "nothing", blk);
  Pair<String, JSON> p2 = new Pair(     "number", num);
  Pair<String, JSON> p3 = new Pair( "booleanTrue", bolT);
  Pair<String, JSON> p4 = new Pair("booleanFalse", bolF);
  Pair<String, JSON> p5 = new Pair(      "string", str);
  Pair<String, JSON> p6 = new Pair(        "list", loj);

  JSON JSONObj = new JSONObject(
    new ConsList<Pair<String, JSON>>(p1,
    new ConsList<Pair<String, JSON>>(p2,
    new ConsList<Pair<String, JSON>>(p3,
    new ConsList<Pair<String, JSON>>(p4,
    new ConsList<Pair<String, JSON>>(p5,
    new ConsList<Pair<String, JSON>>(p6,
    new MtList<Pair<String, JSON>>()))))))
  );

  JSONVisitor<JSON> jF = new JSONFind("nothing");

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
      &&
      t.checkExpect(JSONList.foldr(jTN, 0), 14)
      &&
      t.checkExpect(JSONObj.accept(new JSONToNumber()),    28)
      ;
  }


  boolean testJSONFind(Tester t){
    JSONVisitor<JSON> jF1 = new JSONFind("nothing");
    JSONVisitor<JSON> jF2 = new JSONFind("number");
    JSONVisitor<JSON> jF3 = new JSONFind("booleanTrue");
    JSONVisitor<JSON> jF4 = new JSONFind("booleanFalse");
    JSONVisitor<JSON> jF5 = new JSONFind("string");
    JSONVisitor<JSON> jF6 = new JSONFind("list");
    JSONVisitor<JSON> jF7 = new JSONFind("andsjkndajksndknas");
    return
      t.checkExpect(blk.accept(jF), blk)
      &&
      t.checkExpect(num.accept(jF), num)
      &&
      t.checkExpect(JSONObj.accept(jF1), blk)
      &&
      t.checkExpect(JSONObj.accept(jF2), num)
      &&
      t.checkExpect(JSONObj.accept(jF3), bolT)
      &&
      t.checkExpect(JSONObj.accept(jF4), bolF)
      &&
      t.checkExpect(JSONObj.accept(jF5), str)
      &&
      t.checkExpect(JSONObj.accept(jF6), loj)
      &&
      t.checkExpect(JSONObj.accept(jF7), blk)
    ;
  }
}
