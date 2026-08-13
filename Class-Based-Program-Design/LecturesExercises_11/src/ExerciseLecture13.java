/*
       +-----------+
       | ILoRunner |<-------------------+
       +-----------+                    |
       +-----------+                    |
             / \                        |
             ---                        |
              |                         |
    ----------------------              |
    |                    |              |
+------------+    +----------------+    |
| MtLoRunner |    | ConsLoR        |    |
+------------+    +----------------+    |
+------------+  +-| Runner first   |    |
                | | ILoRunner rest |----+
                | +----------------+
                |
                v
      +----------------+
      | Runner         |
      +----------------+
      | String name    |
      | int age        |
      | int bib        |
      | boolean isMale |
      | int pos        |
      | int time       |
      +----------------+
*/
import tester.Tester;



interface IRunnerPredicate{
  boolean apply(Runner r);
}

class AndPredicate implements IRunnerPredicate{
  IRunnerPredicate left;
  IRunnerPredicate right;

  AndPredicate(IRunnerPredicate left, IRunnerPredicate right){
    this.left=left;
    this.right=right;
  }

  public boolean apply(Runner r){
    return this.left.apply(r) && this.right.apply(r);
  }
}

class OrPredicate implements IRunnerPredicate{
  IRunnerPredicate left;
  IRunnerPredicate right;

  OrPredicate(IRunnerPredicate left, IRunnerPredicate right){
    this.left=left;
    this.right=right;
  }

  public boolean apply(Runner r){
    return this.left.apply(r) || this.right.apply(r);
  }
}

class RunnerIsMale implements IRunnerPredicate{ 
  public boolean apply(Runner r){ return r.isMale; } 
}

class RunnerIsFemale implements IRunnerPredicate {
  public boolean apply(Runner r){ return !r.isMale; } 
}

class RunnerIsPosLessThan50 implements IRunnerPredicate { 
  public boolean apply(Runner r){ return r.pos <= 50; } 
}

class RunnerIsUnder4Hours implements IRunnerPredicate { 
  public boolean apply(Runner r){ return r.time <= 240; } 
}

class RunnerIsYoungerThan40 implements IRunnerPredicate { 
  public boolean apply(Runner r){ return r.age < 40; } 
}


class Runner {
 String name; int age;   int bib;  boolean isMale; int pos; int time;

  Runner(String name, int age,   int bib,  boolean isMale, int pos, int time){
    this.name=name;
    this.age=age;
    this.bib=bib;
    this.isMale=isMale;
    this.pos=pos;
    this.time=time;
  }
}

interface ILoRunner{
  ILoRunner find(IRunnerPredicate pred);
}

class ConsLoRunner implements ILoRunner{
  Runner first;
  ILoRunner rest;

  ConsLoRunner(Runner first, ILoRunner rest){
    this.first = first;
    this.rest = rest;
  }


  public ILoRunner find(IRunnerPredicate pred){
    if(pred.apply(this.first)){
        return new ConsLoRunner(this.first, this.rest.find(pred));
    }else{
        return this.rest.find(pred);
    }
  }

}

class MtLoRunner implements ILoRunner{
  MtLoRunner(){}

  public ILoRunner find(IRunnerPredicate pred){
    return this;
  }
}


class ExamplesRunner{
  ExamplesRunner(){}

  Runner johnny = new Runner("Kelly", 97, 999, true, 30, 360);
  Runner frank  = new Runner("Shorter", 32, 888, true, 245, 130);
  Runner bill = new Runner("Rogers", 36, 777, true, 119, 129);
  Runner joan = new Runner("Benoit", 29, 444, false, 18, 155);
   
  ILoRunner mtlist = new MtLoRunner();
  ILoRunner list1 = new ConsLoRunner(johnny, new ConsLoRunner(joan, mtlist));
  ILoRunner list2 = new ConsLoRunner(frank, new ConsLoRunner(bill, list1));

  ILoRunner list50Pos = new ConsLoRunner(johnny, new ConsLoRunner(joan, this.mtlist));
  ILoRunner list4Hours_1 = new ConsLoRunner(joan, mtlist);
  ILoRunner list4Hours_2 = new ConsLoRunner(frank, new ConsLoRunner(bill, new ConsLoRunner(joan, mtlist)));

  ILoRunner list4HoursAndMale = new ConsLoRunner(frank, new ConsLoRunner(bill, mtlist));


  IRunnerPredicate isMale = new RunnerIsMale();
  IRunnerPredicate isFemale = new RunnerIsFemale();
  IRunnerPredicate isLessThan50 = new RunnerIsPosLessThan50();
  IRunnerPredicate isUnder4Hours = new RunnerIsUnder4Hours();
  IRunnerPredicate isYoungerThan40 = new RunnerIsYoungerThan40();

  IRunnerPredicate isAllMaleAndUnder4Hours = new AndPredicate(isMale, isUnder4Hours);
  IRunnerPredicate isAllFemaleAndYoungerThan40 = new AndPredicate(isFemale, isYoungerThan40);

  // isAllFemaleAndYoungerThan40AndLesttThan50
  IRunnerPredicate isAllF = new AndPredicate(isAllFemaleAndYoungerThan40, isLessThan50);

  // Find all runners who are female or who finish in less than 4 hours.”
  IRunnerPredicate isAllFemaleOrUnderThan4Hours = new OrPredicate(isFemale, isUnder4Hours);

  boolean testFind(Tester t){
    return
    // OnlyMaleRunner
    t.checkExpect(this.list1.find(isMale), new ConsLoRunner(johnny, mtlist)) &&
    t.checkExpect(this.list2.find(isMale), new ConsLoRunner(frank, new ConsLoRunner(bill, new ConsLoRunner(johnny, mtlist)))) &&
    // OnlyFemaleRunner
    t.checkExpect(this.list1.find(isFemale), new ConsLoRunner(joan, mtlist)) &&
    t.checkExpect(this.list2.find(isFemale), new ConsLoRunner(joan, mtlist)) &&
    // OnlyRunnerIn50Pos
    t.checkExpect(this.list1.find(isLessThan50), list50Pos) &&
    t.checkExpect(this.list2.find(isLessThan50), list50Pos) &&
    // OnlyRunnerUnder4Hours
    t.checkExpect(this.list1.find(isUnder4Hours), list4Hours_1) &&
    t.checkExpect(this.list2.find(isUnder4Hours), list4Hours_2) &&
    // isAllMaleAndUnder4Hours
    t.checkExpect(this.list1.find(isAllMaleAndUnder4Hours), mtlist) &&
    t.checkExpect(this.list2.find(isAllMaleAndUnder4Hours), list4HoursAndMale) &&
    //  isAllFemaleAndYoungerThan40AndLesttThan50
    t.checkExpect(this.list1.find(isAllF), new ConsLoRunner(joan, mtlist)) &&
    t.checkExpect(this.list2.find(isAllF), new ConsLoRunner(joan, mtlist)) &&
    // isAllFemaleOrUnderThan4Hours
    t.checkExpect(this.list1.find(isAllFemaleOrUnderThan4Hours), new ConsLoRunner(joan, mtlist)) &&
    t.checkExpect(this.list2.find(isAllFemaleOrUnderThan4Hours), new ConsLoRunner(frank, new ConsLoRunner(bill, new ConsLoRunner(joan, mtlist))));
  }
}

