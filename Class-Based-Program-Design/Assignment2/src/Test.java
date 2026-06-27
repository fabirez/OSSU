import tester.*;

interface ILoString{
  ILoString appendList(ILoString other);
}

class MtLoString implements ILoString{
  MtLoString(){}

  public ILoString appendList(ILoString other){
    return other;
  }
}

class ConsLoString implements ILoString{
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest){
    this.first = first;
    this.rest  = rest;
  }

  public ILoString appendList(ILoString other){
     return new ConsLoString(this.first, this.rest.appendList(other));
  }
}



class ExamplesILoString{
  ExamplesILoString(){}


  ILoString empty = new MtLoString();
  ILoString los_1 = new ConsLoString("hello", new ConsLoString("how", new ConsLoString("are", this.empty)));
  ILoString los_2 = new ConsLoString("you", new ConsLoString("?", new ConsLoString("goooood!", this.empty)));

  ILoString los_s =  new ConsLoString("hello", new ConsLoString("how", new ConsLoString("are", new ConsLoString("you", new ConsLoString("?", new ConsLoString("goooood!", this.empty))))));


  boolean testAppendList(Tester t){
    return t.checkExpect(this.los_1.appendList(this.los_2), this.los_s);
  }
}

