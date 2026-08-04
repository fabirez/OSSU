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

}

class MtLoPre implements ILoPre{
  MtLoPre(){ }

  public ILoPre getPre(){
    return this;
  }
}


interface ILoTask{ 
  // produces the list of the tasks one can complete from these tasks. You can assume all task ids are unique.
  // ILoPre completeTask();
  // prduce the list containing all the IDS by all the tasks in the list
  ILoPre getAllId();
  // produce true if a task has all the pre solvable (the task in pre are prensents in the lisk of tasks) otherwise false;
  // boolean isSolvable(); 
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

}

class ConsLoTask implements ILoTask{ 
  Task first;
  ILoTask rest;
  ConsLoTask(Task first, ILoTask rest){
    this.first = first;
    this.rest = rest;
  }
  
  // public ILoPre completeTask(){
  // return this.completeTaskHelp(new MtLoInt());

    // if(this.first.isSolvable()){
    //  return new ConsLoPre(this.first, this.rest.completeTask());
    // }else{
    //  return this.rest.completeTask();
    // }
  // }

  // public ILoPre completeTaskHelp(ILoPre acc){
  // if(this.first.isSolvable(ILoPre acc)){
  //  return new ConsLoPre(this.first, this.rest.completeTask());
  // }else{
  //  return this.rest.completeTask();
  // }
  // }

  public ILoPre getAllId(){
    return new ConsLoPre(this.first.getId(), this.rest.getAllId());
  }

  // public boolean isSolvable(ILoPre acc){
  //  return this.rest.isSolvableHelp(this.first.getPre());
  // }

  // Check if pre exist in the list.
  //  1. Get the id
  //  2. Compare it with the task id we got in the list, if there is one return true, false otherwise.
  
  // public boolean isSolvableHelp(ILoPre acc){
  //  return this.checkId(acc);
  // }
}

class MtLoTask implements ILoTask{
  MtLoTask(){ }

  // public ILoPre completeTask(){
  //   return new MtLoPre();
  // }

  public ILoPre getAllId(){
    return new MtLoPre();
  }

  public boolean isSolvable(){
    return true;
  }
  public boolean isSolvableHelp(ILoPre preList){
    return true;
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

  // Test getAllId method
  boolean testGetAllId(Tester t){
    return 
    t.checkExpect(this.lot0.getAllId(), this.lop0) &&
    t.checkExpect(this.lot1.getAllId(), this.lop1) && 
    t.checkExpect(this.lot2.getAllId(), this.lop2);
  }
  

  // boolean testCompleteTask(Tester t){
  //   return 
  //   t.checkExpect(this.lot0.completeTask(), 
  //     new ConsLoTask(this.t0, new ConsLoTask(this.t1, new ConsLoTask(this.t2, this.mtTsk)));
  //   t.checkExpect(this.lot1.completeTask(), 
  //     new ConsLoTask(this.t0, new ConsLoTask(this.t1, new ConsLoTask(this.t2, this.mtTsk)));
  //   t.checkExpect(this.lot2.completeTask(), 
  //     new ConsLoTask(this.t0, new ConsLoTask(this.t1, new ConsLoTask(this.t2, this.mtTsk)));
  // }

}


