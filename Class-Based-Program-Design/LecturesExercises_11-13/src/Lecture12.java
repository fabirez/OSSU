import tester.*;

interface IFoo {
  boolean sameFoo(IFoo that);
  boolean sameX(X that);
  boolean sameY(Y that);
  boolean sameZ(Z that);
}

class X implements IFoo{
  String name;

  X(String name){
    this.name = name;
  }

  public boolean sameY(Y that) { return false; }
  public boolean sameZ(Z that) { return false; }
  public boolean sameFoo(IFoo that) { return that.sameX(this); }
  public boolean sameX(X that) { return this.name.equals(that.name); }
}


class Y implements IFoo{
  String name;

  Y(String name){
    this.name = name;
  }

  public boolean sameX(X that) { return false; }
  public boolean sameZ(Z that) { return false; }
  public boolean sameFoo(IFoo that) { return that.sameY(this); }
  public boolean sameY(Y that) { return this.name.equals(that.name); }
}


class Z implements IFoo{
  String name;

  Z(String name){
    this.name = name;
  }

  public boolean sameX(X that) { return false; }
  public boolean sameY(Y that) { return false; }
  public boolean sameFoo(IFoo that) { return that.sameZ(this); }
  public boolean sameZ(Z that) { return this.name.equals(that.name); }
}
class ExamplesIFoo{
  ExamplesIFoo(){}

  IFoo x0 = new X("X");
  IFoo x1 = new X("X1");
  IFoo x2 = new X("X");

  IFoo y0 = new Y("Y");
  IFoo y1 = new Y("Y1");
  IFoo y2 = new Y("Y");

  IFoo z0 = new Z("Z");
  IFoo z1 = new Z("Z1");
  IFoo z2 = new Z("Z");

  boolean testSameFoo(Tester t){
    return

    // Reflexivity
    t.checkExpect(x0.sameFoo(x0), true) &&
    t.checkExpect(y0.sameFoo(y0), true) &&
    t.checkExpect(z0.sameFoo(z0), true) &&
    // Symmetry
    t.checkExpect(x0.sameFoo(x2), true) &&
    t.checkExpect(x2.sameFoo(x0), true) &&
    t.checkExpect(y0.sameFoo(y2), true) &&
    t.checkExpect(y2.sameFoo(y0), true) &&
    t.checkExpect(z0.sameFoo(z2), true) &&
    t.checkExpect(z2.sameFoo(z0), true) &&
    // Transitivity
    t.checkExpect(x0.sameFoo(x1), false) &&
    t.checkExpect(x0.sameFoo(x2), true) &&
    t.checkExpect(x1.sameFoo(x2), false) &&
    t.checkExpect(y0.sameFoo(y1), false) &&
    t.checkExpect(y0.sameFoo(y2), true) &&
    t.checkExpect(y1.sameFoo(y2), false) &&
    t.checkExpect(z0.sameFoo(z1), false) &&
    t.checkExpect(z0.sameFoo(z2), true) &&
    t.checkExpect(z1.sameFoo(z2), false) &&
    // Totality
    t.checkExpect(x0.sameFoo(y0), false) &&
    t.checkExpect(y0.sameFoo(x0), false) &&
    t.checkExpect(x0.sameFoo(z0), false) &&
    t.checkExpect(z0.sameFoo(x0), false) &&
    t.checkExpect(y0.sameFoo(z0), false) &&
    t.checkExpect(z0.sameFoo(y0), false);
  }

}

