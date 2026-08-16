//=========================
//Do Now
//=========================

/*Do Now!

What kinds of questions will we need to ask about Runners to solve these problems?
*/


// > Who are the final standing (if for final standing it means the podium, then we can take the 3 runner with less time)
// > We can take the runner with less time
// > Check-in, we have the field name


/* Do Now!
 Reconstruct the insertion-sort algorithm, for a list of Runners as sorted by their times in increasing order.
*/


/*
interface ILoRunner{
  ILoRunner find(IRunnerPredicate pred);
  ILoRunner sortByTime();
  ILoRunner insert(Runner r);
}

// In Runner
public boolean lessTime(Runner that){
  return this.time < that.time;
}
// In ConsLoRunner
public ILoRunner sortByTime(){
  return this.rest.sortByTime().insert(this.first); 
}

public ILoRunner insert(Runner r){
  if(this.first.lessTime(r)){
    return new ConsLoRunner(this.first, this.rest.insert(r));
  }else{
    return new ConsLoRunner(r, this);
  }
}

// In MtLoRunner
public ILoRunner sortByTime(){
  return this;
}

public ILoRunner insert(Runner r){
  return new ConsLoRunner(r, this);
}

*/

/*Do Now!

What signature should this interface define?
*/

// > ILoRunner ...(IRunnerPredicate r)
//   Runner -> ILoRunner

/*Do Now!

Design this class.
*/

/*
interface ICompareRunners {
  // Returns true if r1 comes before r2 according to this ordering
  boolean comesBefore(Runner r1, Runner r2);
}

class ComesBefore implements ICompareRunners{
  public boolean comesBefore(Runner r1, Runner r2){
    return r1.time < r2.time;
  }
}
*/

/*Do Now!

Revise the sorting methods above to use this new abstraction.
*/


/*
interface ILoRunner{
  ILoRunner sortByTime(ICompareRunners pred);
  ILoRunner insert(ICompareRunners pred, Runner r);
}

// In ConsLoRunner
public ILoRunner sortByTime(ICompareRunners pred){
  return this.rest.sortByTime(pred).insert(pred, this.first); 
}

public ILoRunner insert(ICompareRunners pred, Runner r){
  if(pred.comesBefore(this.first, r)){
    return new ConsLoRunner(this.first, this.rest.insert(pred, r));
  }else{
    return new ConsLoRunner(r, this);
  }
}

// In MtLoRunner
public ILoRunner sortByTime(ICompareRunners pred){
  return this;
}

public ILoRunner insert(ICompareRunners pred, Runner r){
  return new ConsLoRunner(r, this);
}

*/


/*Do Now!

Can you simplify the method above, given this flexibility and given that here we’re essentially comparing two numbers?
*/

// > r1.time - r2.time 

/*Do Now!
How much of our code above can we reuse to solve this new question?
*/

// > Sort by time, and return only the first runner 
/*
interface ILoRunner{
  // Finds the fastest Runner in this list of Runners
  Runner findWinner();
  // Finds the first Runner in this list of Runners
  Runner getFirst();
}

// In ConsLoRunner
  public Runner findWinner(){
    return this.rest.sortBy(new ComesBefore()).getFirst(); 
  }

  public Runner getFirst(){
    return this.first;
  }
}

// In MtLoRunner
  public Runner findWinner(){
    throw new RuntimeException("There is no winner in a empty list of runner!");
  }

  public Runner getFirst(){
    throw new RuntimeException("There is no Runner in a empty list of runner!");
  }
*/


/*Do Now!

Try to implement this method on the ConsLoRunner class. Where does it get stuck?
*/

// I do no thave the first runner to compare with the other ones in the recursion. (need 2 runner)

/*Do Now!
  Implement findMinHelp for MtLoRunner and ConsLoRunner.
*/


/*
  // In ConsLoRunner 
  public Runner findMinHelp(IRunnerComparator comp, Runner acc){
    if(comp.compareByTime(this.first, acc) > 0){
      return this.rest.findMyHelp(comp, this.first);
    }else{
      return this.rest.findMyHelp(comp, acc);
    }
  }
  // In MtLoRunner
  public Runner findMinHelp(IRunnerComparator comp, Runner acc){
    return acc;
  }
*/

/*Do Now!
  Design a CompareByName comparator that compares two Runners by their names.
*/

/*
class CompareByName implements IRunnerComparator{
  public int compare(Runner r1, Runner r2){
    return r1.name.compareTo(r2.name); 
  }
}
*/