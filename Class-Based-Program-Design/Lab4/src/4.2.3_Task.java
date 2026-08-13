/* # Lab 4: Working with Abstract Classes, Problem Solving
## 4.2 Problem Solving Practice
## 4.2.3 Task

- Design a class for a task.
  A task has an id (an integer) and a (possibly empty) list of prerequisites,
  represented as the ids of the tasks that need to be completed before that task is.

- Design a method on a list of tasks which produces the list of the tasks one can complete from these tasks.
  You can assume all task ids are unique.
  Before beginning to program, sketch the helpers you’ll need to solve this problem, and ask yourself some questions:
    - can task dependencies be cyclic? 
        No, because if it is cyclic it cannot be "completed"
    - Are any tasks guaranteed to be completable?
        
  Be sure to understand these questions and what they imply for your program, as well as your examples and test cases.
  Your method should be able to terminate no matter what list it is operating on.
  NOTE: that in the problem definition alone a list of tasks can represent two different things:
  the tasks the method is being called on, and the subset of those tasks that can actually be completed.
  Keeping track of what list of tasks means what as you complete the problem is essential.
*/
import tester.*;

interface ILoPre{ 
  ILoPre getPre();
  boolean solvable(Task task, ILoPre solvedPre);
  boolean solvableHelp(ILoPre solvedPre);
  // produce true if given pre is inside the given listof pre 
  boolean solvableList(int pre);

  // produce the merged given listof pre with the current one
  ILoPre append(ILoPre other);
}

class ConsLoPre implements ILoPre{ 
  int first;
  ILoPre rest;
  ConsLoPre(int first, ILoPre rest){
    this.first = first;
    this.rest = rest;
  }

  public ILoPre getPre(){
  /*
  FIELDS
  this.first  -- int
  this.rest   -- ILoPre
  */
    return this;
  }


  public boolean solvable(Task task, ILoPre solvedPre){
    return task.getPre().solvableHelp(solvedPre);
  }


  public boolean solvableHelp(ILoPre solvedPre){
    /*
    FIELDS
    this.first  -- int
    this.rest   -- ILoPre
    METHODS
    this.getPre -- ILoPre
    */
    if(solvedPre.solvableList(this.first)){
      return this.rest.solvableHelp(solvedPre);
    }else{
      return false;
    }
  }


  public boolean solvableList(int pre){
    if(this.first == pre){
      return true;
    }else{
      return this.rest.solvableList(pre);
    }
  }
  public ILoPre append(ILoPre other){
    return new ConsLoPre(this.first, this.rest.append(other));
  }

}

class MtLoPre implements ILoPre{
  MtLoPre(){ }

  public ILoPre getPre(){
    return this;
  }

  public boolean solvable(Task task, ILoPre solvedPre){
    return true;
  }

  public boolean solvableHelp(ILoPre solvedPre){
    return true;
  }

  public boolean solvableList(int pre){
    return false;
  }

  public ILoPre append(ILoPre other){
    return other;
  }
}


interface ILoTask{ 
  // produces the list of the tasks one can complete from these tasks. You can assume all task ids are unique.
  ILoTask completeTask();
  ILoTask solvePre(ILoPre solvedPre, ILoPre solvingPre, ILoTask currentTasks, ILoTask solvedTask); 

  // produce the a new list, with the given list at the end and the current at first (this)
  ILoTask appendTask(Task that);
}

class Task{
  int id;
  ILoPre lop;
  Task(int id, ILoPre lop){
    this.id = id;
    this.lop = lop;
  }

  /*
  FIELDS
  this.first  -- int
  this.rest   -- ILoPre
  */

  public int getId(){
    return this.id;
  }

  public ILoPre getPre(){
    return this.lop.getPre();
  }

  public boolean solvable(ILoPre solvedPre){
    return this.lop.solvableHelp(solvedPre);
  }

}

class ConsLoTask implements ILoTask{ 
  Task first;
  ILoTask rest;
  ConsLoTask(Task first, ILoTask rest){
    this.first = first;
    this.rest = rest;
  }

  public ILoTask completeTask(){
    return this.solvePre(new MtLoPre(), new MtLoPre(), new MtLoTask(), new MtLoTask());
  }

  public ILoTask solvePre(ILoPre solvedPre, ILoPre solvingPre, ILoTask currentTasks, ILoTask solvedTask){
    if(this.first.solvable(solvedPre)){
      return this.rest.solvePre(solvedPre, new ConsLoPre(this.first.getId(), solvingPre), currentTasks, solvedTask.appendTask(this.first));
    }else{
      return this.rest.solvePre(solvedPre, solvingPre, new ConsLoTask(this.first, currentTasks), solvedTask);
    }
  }

  public ILoTask appendTask(Task that){
    return new ConsLoTask(this.first, this.rest.appendTask(that));
  }
}

class MtLoTask implements ILoTask{
  MtLoTask(){ }

  public ILoTask completeTask(){
    return new MtLoTask();
  }

  public ILoTask solvePre(ILoPre solvedPre, ILoPre solvingPre, ILoTask currentTasks, ILoTask solvedTask){
    if(solvingPre instanceof MtLoPre){
      // Break, Stop the recursion
      return solvedTask; 
    }else{
      return currentTasks.solvePre(solvedPre.append(solvingPre), new MtLoPre(), new MtLoTask(), solvedTask);
    }
  }

  public ILoTask appendTask(Task that){
    return new ConsLoTask(that, this);
  }
}

class ExamplesILoTask{
  ExamplesILoTask(){}

  ILoPre mtPre = new MtLoPre();
  Task t0 = new Task(0, mtPre);

  ILoPre p1 = new ConsLoPre(this.t0.getId(), this.mtPre);
  Task t1 = new Task(1, this.p1);

  ILoPre p2 = new ConsLoPre(this.t1.getId(), new ConsLoPre(this.t0.getId(), mtPre));
  Task t2 = new Task(2, this.p2);

  ILoPre p3 = new ConsLoPre(this.t0.getId(), mtPre);
  Task t3 = new Task(3, this.p3);

  ILoPre p4 = new ConsLoPre(this.t3.getId(), mtPre);
  Task t4 = new Task(4, this.p4);

  ILoPre p5 = new ConsLoPre(this.t3.getId(), new ConsLoPre(this.t0.getId(), mtPre));
  Task t5 = new Task(5, this.p5);

  ILoTask mtTsk = new MtLoTask();

  ILoPre lop0 = new ConsLoPre(0,new ConsLoPre(1, new ConsLoPre(2, new MtLoPre())));
  ILoTask lot0 = new ConsLoTask(this.t0,
    new ConsLoTask(this.t1,
      new ConsLoTask(this.t2,
        this.mtTsk)));

  ILoPre lop1 = new ConsLoPre(0,new ConsLoPre(1, new ConsLoPre(2, new ConsLoPre(4, new MtLoPre()))));
  // T4 needs T3 for be solved, but T3 isn't in the list:
  ILoTask lot1 = new ConsLoTask(this.t0,
    new ConsLoTask(this.t1,
      new ConsLoTask(this.t2,
        new ConsLoTask(this.t4,
        this.mtTsk))));


  ILoPre lop2 = new ConsLoPre(0,new ConsLoPre(1, new ConsLoPre(2, new ConsLoPre(4, new ConsLoPre(5, new MtLoPre())))));
  // T5 needs a known taks, and an unknown one,
  // T4 and T5 needs T3 for be solved, but T3 isn't in the list:
  ILoTask lot2 = new ConsLoTask(this.t0,
    new ConsLoTask(this.t1,
      new ConsLoTask(this.t2,
        new ConsLoTask(this.t4,
          new ConsLoTask(this.t5,
        this.mtTsk)))));

  // Every task can be solved without any other tasks
  ILoTask lot3 = new ConsLoTask(this.t0, new ConsLoTask(this.t0, new ConsLoTask(this.t0, this.mtTsk)));

  // All the tasks cannot be solvable by the other tasks
  ILoTask lot4 = new ConsLoTask(this.t1, new ConsLoTask(this.t2, new ConsLoTask(this.t3, this.mtTsk)));

  // Test getId method
  boolean testGetId(Tester t){
    return 
    t.checkExpect(this.t0.getId(), 0) && 
    t.checkExpect(this.t1.getId(), 1) && 
    t.checkExpect(this.t2.getId(), 2);
  }

  // Test getPre method
  boolean testGetPre(Tester t){
    return 
    t.checkExpect(this.t0.getPre(), new MtLoPre()) && 
    t.checkExpect(this.t1.getPre(), this.p1) && 
    t.checkExpect(this.t2.getPre(), this.p2);
  }

  // Test solvable method
  boolean testSolvable(Tester t){
    return 
    t.checkExpect(this.lop1.solvable(this.t0, new MtLoPre()),      true) && 
    t.checkExpect(this.lop1.solvable(this.t0, this.t0.getPre()),   true) && 
    t.checkExpect(this.lop1.solvable(this.t1, this.t1.getPre()),   true) && 
    t.checkExpect(this.lop1.solvable(this.t2, this.t2.getPre()),   true) &&
    t.checkExpect(this.lop1.solvable(this.t1, this.t4.getPre()),  false) &&
    t.checkExpect(this.lop1.solvable(this.t2, this.t1.getPre()),  false)
    ;
  }

  // Test solvableHelp method
  boolean testSolvableHelp(Tester t){
    return 
    t.checkExpect(this.p1.solvableHelp(this.p1),  true) && 
    t.checkExpect(this.p2.solvableHelp(this.p2),  true) && 
    t.checkExpect(this.p3.solvableHelp(this.p4), false) && 
    t.checkExpect(this.p4.solvableHelp(this.p2), false);
  }

  // Test append method
  boolean testAppend(Tester t){
    return 
    t.checkExpect(this.p1.append(this.p2),  new ConsLoPre(this.t0.getId(), this.p2)) && 
    t.checkExpect(this.p2.append(this.p3),  new ConsLoPre(this.t1.getId(), new ConsLoPre(this.t0.getId(), this.p3))) && 
    t.checkExpect(this.p3.append(this.p4),  new ConsLoPre(this.t0.getId(), this.p4)) && 
    t.checkExpect(this.p4.append(this.p5),  new ConsLoPre(this.t3.getId(), this.p5));
  }

  // Test append method
  boolean testAppendTask(Tester t){
    return 
    t.checkExpect(this.lot0.appendTask(this.t3),  new ConsLoTask(this.t0, new ConsLoTask(this.t1, new ConsLoTask(this.t2, new ConsLoTask(this.t3, this.mtTsk))))) &&
    t.checkExpect(this.lot1.appendTask(this.t3),  new ConsLoTask(this.t0, new ConsLoTask(this.t1, new ConsLoTask(this.t2, new ConsLoTask(this.t4, new ConsLoTask(this.t3, this.mtTsk)))))) &&
    t.checkExpect(this.lot2.appendTask(this.t4),  new ConsLoTask(this.t0, new ConsLoTask(this.t1, new ConsLoTask(this.t2, new ConsLoTask(this.t4, new ConsLoTask(this.t5, new ConsLoTask(this.t4, this.mtTsk))))))) && 
    t.checkExpect(this.lot3.appendTask(this.t5),  new ConsLoTask(this.t0, new ConsLoTask(this.t0, new ConsLoTask(this.t0, new ConsLoTask(this.t5, this.mtTsk)))));
  }


  boolean testCompleteTask(Tester t){
    return 
    t.checkExpect(this.lot0.completeTask(), 
      new ConsLoTask(this.t0, new ConsLoTask(this.t1, new ConsLoTask(this.t2, this.mtTsk)))) &&
    t.checkExpect(this.lot1.completeTask(), 
      new ConsLoTask(this.t0, new ConsLoTask(this.t1, new ConsLoTask(this.t2, this.mtTsk)))) &&
    t.checkExpect(this.lot2.completeTask(), 
      new ConsLoTask(this.t0, new ConsLoTask(this.t1, new ConsLoTask(this.t2, this.mtTsk)))) &&
    t.checkExpect(this.lot3.completeTask(), this.lot3) &&
    t.checkExpect(this.lot4.completeTask(), new MtLoTask());
      
  }

}
