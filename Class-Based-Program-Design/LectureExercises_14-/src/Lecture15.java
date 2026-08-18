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


