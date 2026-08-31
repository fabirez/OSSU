/*
- [x] To begin, implement the equivalent of foldr from ISL on IList<T>s.
      What new types will you need?

- [x] Write a test that sums a list of numbers using your foldr method.
*/
import tester.*;

// Represents functions of signature A -> R, for some argument type A and
// result type R
interface IFunc<A, R> {
  R apply(A input);
}

// Represents functions of signature A1 A2 -> R
interface IFunc2<A1, A2, R> {
  R apply(A1 fn, A2 base);
}
 
// generic list
interface IList<T> {
  // map over a list, and produce a new list with a (possibly different)
  // element type
  <U> IList<U> map(IFunc<T, U> f);

  <U> U foldr(IFunc2<T, U, U> f, U base);
}

class SumInt implements IFunc2<Integer, Integer, Integer>{
  public Integer apply(Integer n, Integer base){
    return n + base;
  }
}
 
// empty generic list
class MtList<T> implements IList<T> {
  public <U> IList<U> map(IFunc<T, U> f) {
    return new MtList<U>();
  }

  public <U> U foldr(IFunc2<T, U, U> f, U base)
  {
    return base;
  }
}
 
// non-empty generic list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
 
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
 
  public <U> IList<U> map(IFunc<T, U> f) {
    return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }

  public <U> U foldr(IFunc2<T, U, U> f, U base)
  {
    return f.apply(this.first,
                    this.rest.foldr(f, base));
  }
}

class Examples{
  Examples(){};

  IList<Integer> lon = new ConsList<Integer>(1,
  new ConsList<Integer>(2, 
    new ConsList<Integer>(3, 
      new MtList<Integer>())));

  IFunc2<Integer, Integer, Integer> sI = new SumInt();

  boolean testSumInteger(Tester t){
    return 
    t.checkExpect(lon.foldr(sI, 0), 6);
  }
}

