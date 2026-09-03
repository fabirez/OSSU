/*Do Now!

Write out explicitly the predicate and comparator interfaces for Runners and Authors.
*/

/*
interface IRunnersPredicate {
	boolean apply(Runner r);
} 
interface IRunnersComparator{
	boolean compare(Runner r1, Runner r2);
}


class isFirstRunner implements IRunnersPredicate{
	public boolean apply(Runner r){
		return r.pos == 1;
	}
}

class ComesBefore implements IRunnersComparator{
	public boolean compare(Runner r1, Runner r2){
		return r1.time - r2.time;
	}
}


interface IAuthorsPredicate {
	boolean apply(Author a);
} 
interface IAuthorsComparator{
	boolean compare(Author a1, Author a2);
}


class isShakespeare implements IRunnersPredicate{
	public boolean apply(Author a){
		return a.surname.equal("Shakespeare");
	}
}


class ComesBefore implements IRunnersComparator{
	public boolean compare(Author a1, Author a2){
		return a1.compareTo(a2);
	}
}
*/

/*Do Now!

What varies between the interfaces you just defined?
*/

// > Different types and names for paramaters

/*Do Now!
Suppose we forgot to write the <T> syntax. What would Java report as the error if we defined

interface IPred {
  boolean apply(T t);
}

instead? (Read it carefully!)
*/


// > I think java will search for the "T" class, doens't find it, and run a runtimeError.

/*Do Now!

Try defining this in Eclipse, and see what error message is generated.
*/

// > IPred is a raw type. References to generic type IPred<T> should be parameterized

/*Do Now!

Revise our definition of the RunnerIsInFirst50 predicate over Runners, and revise the examples that use it.
*/

/*
 interface IPred<T> {
   boolean apply(T t);
 }

 class RunnerIsPosLessThan50 implements IPred<Runner> { 
   public boolean apply(Runner r){ return r.pos <= 50; } 
 }

IPred<Runner> inFirst50 = new RunnerIsPosLessThan50(...);
*/

/*Do Now!
 * Define a generic ConsList class.
*/
/*
class ConsList<T> implements IList<T>{
	T first;
	IList<T> rest;

	ConsList(T first, ILo<T> rest){
		this.first=first;
		this.rest=rest;
	}

  public IList<T> filter(IPred<T> pred) {return this;}
  public IList<T> sort(IComparator<T> comp) {return this;}
  public int length() {return 0;}
}
*/

/*Do Now!
 * Implement sort for ConsList<T>. Implement whatever helper methods you need, as well.
*/

/*
interface IComparator<T> { int compare(T t1, T t2); }

class ComparatorByString implements IComparator<String> {
  public int compare(String t1, String t2) {
    return t1.compareTo(t2);
  }
}

// In ConsList<T>
  public IList<T> sort(IComparator<T> comp){
		return this.rest.sort(comp).insert(comp, this.first);
	}

  public IList<T> insert(IComparator<T> comp, T el){
		if(comp.compare(el, this.first) < 0){
			return new ConsList<T>(el, this);
		}else{
			return new ConsList<T>(this.first, this.rest.insert(comp, el));
		}
	}

// In MtList<T>
	public IList<T> sort(IComparator<T> comp){ return this; }
	public IList<T> insert(IComparator<T> comp, T el){ return new ConsList<T>(el, this); }
*/

/*Do Now!

I claim that we need this parameter “just for this method”. But another possibility seems to be to add U as a type parameter to IList itself, like this:
interface IList<T, U> {
  ...
  IList<U> map(IFunc<T, U> f);
  ...
}
What goes wrong with that approach? (There are at least two big problems.)
*/

// > 1 big problem, is that a list cannot ahve Two types, we want just to hadnle one.
// > 2 big problem, All other methods should inherit that, also if they don't need it, so it's like having a "special list" for a "special operation"

/*Do Now!

Why not?
*/

/*Do Now!

Define a function object to compute the perimeter of a Circle, and use it to compute a list of the perimeters of a list of Circles. (Ignore IShape for now.
*/

// > Because we are returnig a differen type `<U>` and not eh type of the list <T>


/*Do Now!

Define a function object to compute the perimeter of a Circle, and use it to compute a list of the perimeters of a list of Circles. (Ignore IShape for now.)
*/

/*
class ComputePerimeter implements IFunc<Circle, Double>{
	public Double apply(Circle c){
		return Math.floor(2 * Math.PI * c.radius);
	}

	public <U> IList<U> map(IFunc<T, U> f) {
		return new ConsList<U>(f.apply(this.first), this.rest.map(f));
	}

	IFunc<Circle, Double> computePerimeter = new ComputePerimeter();
	listOfCircle.map(computePerimeter) -> listOfPerimeter
}
*/
/*Do Now!
*Why? What precisely is in our template? (Specifically, what are we allowed to do with this.first?)
*/

// > Beacuse if we define another list, with another type, totalPrice will not work anymore. (Doesn't have any sense, since the
// type will be differente.

/*Do Now!
	What goes wrong now?
*/

// > because there is no such method in the generic IList<T> interface! (wrong)
//

/*Do Now!
	Pretend for a moment that we had a [List-of Book] in Racket.
	Can you express total-price as a function in Racket, using foldr?
*/

/*
```racket
(define (total-price lob)
	(foldr + 0 lob))
```
*/
