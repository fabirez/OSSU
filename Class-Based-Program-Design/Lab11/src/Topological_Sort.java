/*
- [x] Create an example Curriculum of Courses representing the example courses above.

- [x] Create an example `ArrayList<Course>` of the class schedule you worked out,
			with prerequisites first and advanced courses later in the list.

- [x] Create some testing helpers.
			- [x] First, design a method on Curriculum,
					  `boolean comesAfterPrereqs(ArrayList<Course> schedule, Course c)`
					  that checks whether the provided Course appears in the schedule after all of its prerequisites do 
						(and, clearly, that all the prerequisites appear in the schedule!).

			- [x] Second, design a method 
						`boolean validSchedule(ArrayList<Course> schedule)`,
						that checks whether the schedule is valid for all the courses in it.

	Your example `ArrayList<Course>` from above should satisfy this helper.

- [x] Design a void method process on Course that takes in a `ArrayList<Course>` called processed,
			representing the courses that have been processed and added to the eventual result so far.
			If this course is in processed,
			then do nothing; otherwise, process this course’s prereqs, then add it to processed.
			HINT: implement this method recursively, rather than iteratively with loops and a stack as you did for DFS in lecture.

- [x] Design a method topSort on Curriculum that takes in an `ArrayList<Course>`.
			The method should create a `new ArrayList<Course>` to store the eventual result,
			then process every course in the given list with that result list.
			Once every course has been processed, return the result list.
*/

import java.util.ArrayList;
import tester.*;

class Curriculum {
  ArrayList<Course> courses;
  Curriculum() { this.courses = new ArrayList<Course>(); }
  // EFFECT: adds another course to the set of known courses
  void addCourse(Course c) { this.courses.add(c); }
  // add methods here...

	// checks whether the given Course appears in the schedule after all of its prerequisites
	// and, that all the prerequisites appear in the schedule!
	boolean comesAfterPrereqs(ArrayList<Course> schedule, Course c)
	{
		// get the prerequisites of the course
		ArrayList<Course> currPre = c.getPre();
		// get the index of the course from the schedule
		int posCourse = schedule.indexOf(c);
		// if the course doesn't exist, return false.
		if(posCourse < 0) return false;
		// return true if the curr course doens't not have any pre.
		if(currPre.size() <= 0) return true;
		// check if the pre, comes before the course 
		for(Course p: currPre){
			// get the index of the pre from the schedule
			int posPre = schedule.indexOf(p);
			//  return false
			// if pre comes after the course
			// OR
			// if pre doens't exist in the schedule
			if(posPre > posCourse || posPre < 0) return false;
		}

		// if we come here, then the pre exist and comes before the course in the schedule.
		return true;
	}

	// checks whether the given schedule is valid for all the courses in it.
	boolean validSchedule(ArrayList<Course> schedule){
		// check if all the course are in the right position.
		// for doing so, we can check theri pre and be sure that they comes after
		for(Course c: schedule){
			if(this.comesAfterPrereqs(schedule, c) == false) return false;
		}
		return true;
	}

	// process every course in the given list once every course has been processed, return the result list.
	ArrayList<Course> topSort(ArrayList<Course> courses){
		ArrayList<Course> res = new ArrayList<Course>();
		for(Course c: courses){ c.process(res); }
		return res;
	}

}
class Course {
  String name;
  ArrayList<Course> prereqs;
  Course(String name) { this.name = name; this.prereqs = new ArrayList<Course>(); }
  // EFFECT: adds a course as a prereq to this one
  void addPrereq(Course c) { this.prereqs.add(c); }
	// produce the prereqs of this course
  ArrayList<Course> getPre(){ return this.prereqs; }
	// the given courses represent the courses that have been processed and added to the eventual result so far.
	void process(ArrayList<Course> processed)
	{
		if(processed.contains(this)) return;
		ArrayList<Course> currPre = this.prereqs;
		for(Course c: currPre){
			c.process(processed);
		}
		processed.add(this);
		return;
	}
}

class CurriculumOfCourses{
	// Fundamentals 1
	Course F1;
	// Fundamentals 2
	Course F2;
	// Database Design
	Course DD;
	// Computer Systems
	Course CS;
	// Algorithms and Data
	Course AD;
	// Theory of Computation
	Course ToC;
	// Object-Oriented Design
	Course OOD;
	// Large-Scale Parallel Data Processing
	Course LSPDP;
	// Programming Languages
	Course PL;
	// Compilers
	Course C;
	// Schedule
	ArrayList<Course> schedule;
	// Curriculum
	Curriculum c;

	void initData(){
		// Fundamentals 1
		this.F1 = new Course("Fundamentals 1");

		// Fundamentals 2
		this.F2 = new Course("Fundamentals 2");
		this.F2.addPrereq(this.F1);

		// Database Design
		this.DD = new Course("Database Design");
		this.DD.addPrereq(this.F1);

		// Algorithms and Data
		this.AD = new Course("Algorithms and Data");
		this.AD.addPrereq(this.F2);

		// Computer Systems
		this.CS = new Course("Computer Systems");
		this.CS.addPrereq(this.F2);

		// Theory of Computation
		this.ToC = new Course("Theory of Computation");
		this.ToC.addPrereq(this.F2);

		// Object-Oriented Design
		this.OOD = new Course("Object-Oriented Design");
		this.OOD.addPrereq(this.F2);

		// Large-Scale Parallel Data Processing
		this.LSPDP = new Course("Large-Scale Parallel Data Processing");
		this.LSPDP.addPrereq(this.AD); this.LSPDP.addPrereq(this.CS);

		// Programming Languages
		this.PL = new Course("Programming Languages");
		this.PL.addPrereq(this.ToC); this.PL.addPrereq(this.OOD);

		// Compilers
		this.C = new Course("Compilers");
		this.C.addPrereq(this.PL);

		// Schedule 
		this.schedule = new ArrayList<Course>();
		this.schedule.add(this.F1);
		this.schedule.add(this.F2);
		this.schedule.add(this.DD);
		this.schedule.add(this.AD);
		this.schedule.add(this.CS);
		this.schedule.add(this.ToC);
		this.schedule.add(this.OOD);
		this.schedule.add(this.LSPDP);
		this.schedule.add(this.PL);
		this.schedule.add(this.C);

		// Curriculum
		this.c = new Curriculum();
		// Adding them in reverse for testing purpose
		this.c.addCourse(this.C);
		this.c.addCourse(this.PL);
		this.c.addCourse(this.LSPDP);
		this.c.addCourse(this.OOD);
		this.c.addCourse(this.DD);
		this.c.addCourse(this.ToC);
		this.c.addCourse(this.CS);
		this.c.addCourse(this.AD);
		this.c.addCourse(this.F2);
		this.c.addCourse(this.F1);
	}


	void testComesAfterPrereqs(Tester t){
		this.initData();
		Curriculum c = new Curriculum();
		t.checkExpect(c.comesAfterPrereqs(this.schedule,    this.F1), true);
		t.checkExpect(c.comesAfterPrereqs(this.schedule,    this.F2), true);
		t.checkExpect(c.comesAfterPrereqs(this.schedule,    this.DD), true);
		t.checkExpect(c.comesAfterPrereqs(this.schedule,    this.AD), true);
		t.checkExpect(c.comesAfterPrereqs(this.schedule,    this.CS), true);
		t.checkExpect(c.comesAfterPrereqs(this.schedule,   this.ToC), true);
		t.checkExpect(c.comesAfterPrereqs(this.schedule,   this.OOD), true);
		t.checkExpect(c.comesAfterPrereqs(this.schedule, this.LSPDP), true);
		t.checkExpect(c.comesAfterPrereqs(this.schedule,    this.PL), true);
		t.checkExpect(c.comesAfterPrereqs(this.schedule,     this.C), true);

		// Empty schedule, with Fundamentals that has no Pre
		ArrayList<Course> schedule_ = new ArrayList<Course>();
		t.checkExpect(c.comesAfterPrereqs(schedule_, this.F1), false);

		// Fundamentals 1 in the wrong position	
		ArrayList<Course> schedule1 = new ArrayList<Course>();
		schedule1.add(this.F2); 
		schedule1.add(this.DD); 
		// These courses above all needs F1 as a pre.
		schedule1.add(this.F1);
		// F1 has no pre, it must still retrun true.
		t.checkExpect(c.comesAfterPrereqs(schedule1, this.F1), true);
		// F2 has F1 as pre, but comes before.
		t.checkExpect(c.comesAfterPrereqs(schedule1, this.F2), false);

		// Fundamentals 2 in the wrong position	
		ArrayList<Course> schedule2 = new ArrayList<Course>();
    schedule2.add(this.AD);
    schedule2.add(this.CS);
    schedule2.add(this.ToC);
    schedule2.add(this.OOD);
		// These courses above all needs F2 as a pre.
		schedule2.add(this.F2); 
		t.checkExpect(c.comesAfterPrereqs(schedule2,  this.AD), false);
		t.checkExpect(c.comesAfterPrereqs(schedule2,  this.CS), false);
		t.checkExpect(c.comesAfterPrereqs(schedule2, this.ToC), false);
		t.checkExpect(c.comesAfterPrereqs(schedule2, this.OOD), false);


		// Algorithm and Data in the wrong poistion
		ArrayList<Course> schedule3 = new ArrayList<Course>();
		schedule3.add(this.CS);
		schedule3.add(this.LSPDP);
		// LSPDP needs CS and AD as pre, but in this case ONLY CS is in the right position.
		schedule3.add(this.AD);
		t.checkExpect(c.comesAfterPrereqs(schedule3, this.LSPDP), false);
		// Computer System in the wrong poistion
		ArrayList<Course> schedule4 = new ArrayList<Course>();
		schedule4.add(this.AD);
		schedule4.add(this.LSPDP);
		// LSPDP needs CS and AD as pre, but in this case ONLY AD is in the right position.
		schedule4.add(this.CS);
		t.checkExpect(c.comesAfterPrereqs(schedule4, this.LSPDP), false);

		// Theory of Computation in the wrong poistion
		ArrayList<Course> schedule5 = new ArrayList<Course>();
		schedule5.add(this.OOD);
		schedule5.add(this.PL);
		// PL needs Toc and OOD as pre, but in this case ONLY OOD is in the right position.
		schedule5.add(this.ToC);
		t.checkExpect(c.comesAfterPrereqs(schedule5, this.PL), false);
		// Object Orientation Design in the wrong poistion 
		ArrayList<Course> schedule6 = new ArrayList<Course>();
		schedule6.add(this.ToC);
		schedule6.add(this.PL);
		// PL needs Toc and OOD as pre, but in this case ONLY ToC is in the right position.
		schedule6.add(this.OOD);
		t.checkExpect(c.comesAfterPrereqs(schedule6, this.PL), false);


		// Programming Language in the wrong position
		ArrayList<Course> schedule7 = new ArrayList<Course>();
		schedule7.add(this.C);
		// C need PL as a pre
		schedule7.add(this.PL);
		t.checkExpect(c.comesAfterPrereqs(schedule7, this.C), false);

		// Check a pre of a task, but that pre has not his pre satisfied.
		// Example A needs B. B needs C; but C is not in the schedule.
		// What should happen ?

		//  AD needs F2;
		//  F2 needs F1;
		//  but F1 doesn't exist in the schedule
		ArrayList<Course> schedule8 = new ArrayList<Course>();
		schedule8.add(this.F2); 
    schedule8.add(this.AD);
		t.checkExpect(c.comesAfterPrereqs(schedule8,  this.AD), true);
		t.checkExpect(c.comesAfterPrereqs(schedule8,  this.F2), false);
	}

	void testValidSchedule(Tester t){
		this.initData();
		Curriculum c = new Curriculum();

		// F1 needs no pre. 
		ArrayList<Course> schedule = new ArrayList<Course>();
		schedule.add(this.F1); 
		t.checkExpect(c.validSchedule(schedule), true);

		// F2 needs F1. 
		// F1 needs no pre. 
		ArrayList<Course> schedule1 = new ArrayList<Course>();
		schedule1.add(this.F1); 
		schedule1.add(this.F2); 
		t.checkExpect(c.validSchedule(schedule1), true);

		// ===================== 
		// Algorithms and Data
		// ===================== 
		//  AD needs F2;
		//  F2 needs F1;
		//  but F1 doesn't exist in the schedule
		ArrayList<Course> schedule2 = new ArrayList<Course>();
		schedule2.add(this.F2); 
    schedule2.add(this.AD);
		t.checkExpect(c.validSchedule(schedule2), false);
		//  AD needs F2;
		//  F2 needs F1;
		//  F1 needs no pre;
		ArrayList<Course> schedule3 = new ArrayList<Course>();
		schedule3.add(this.F1); 
		schedule3.add(this.F2); 
    schedule3.add(this.AD);
		t.checkExpect(c.validSchedule(schedule3), true);

		// ===================== 
		// Computer System
		// ===================== 
		//  CS needs F2;
		//  F2 needs F1;
		//  F1 needs no pre;
		ArrayList<Course> schedule4 = new ArrayList<Course>();
		schedule4.add(this.F1); 
		schedule4.add(this.F2); 
    schedule4.add(this.CS);
		t.checkExpect(c.validSchedule(schedule4), true);

		// ===================== 
		// Large-Scale Parallel Data Processing
		// ===================== 
		//  LSPDP needs AD and CS;
		//  AD needs F2;
		//  CS needs F2;
		//  F2 needs F1;
		//  F1 needs no pre;
		ArrayList<Course> schedule5 = new ArrayList<Course>();
		schedule5.add(this.LSPDP); 
		t.checkExpect(c.validSchedule(schedule5), false);

		ArrayList<Course> schedule6 = new ArrayList<Course>();
		schedule6.add(this.AD); 
		schedule6.add(this.CS); 
		schedule6.add(this.LSPDP); 
		t.checkExpect(c.validSchedule(schedule6), false);

		ArrayList<Course> schedule7 = new ArrayList<Course>();
		schedule7.add(this.F2); 
		schedule7.add(this.AD); 
		schedule7.add(this.CS); 
		schedule7.add(this.LSPDP); 
		t.checkExpect(c.validSchedule(schedule7), false);

		ArrayList<Course> schedule8 = new ArrayList<Course>();
		schedule8.add(this.F1); 
		schedule8.add(this.F2); 
		schedule8.add(this.AD); 
		schedule8.add(this.CS); 
		schedule8.add(this.LSPDP); 
		t.checkExpect(c.validSchedule(schedule8), true);

		ArrayList<Course> schedule9 = new ArrayList<Course>();
		schedule9.add(this.F2); 
		schedule9.add(this.F1); 
		schedule9.add(this.AD); 
		schedule9.add(this.CS); 
		schedule9.add(this.LSPDP); 
		t.checkExpect(c.validSchedule(schedule9), false);

		// ===================== 
		// Compilers
		// ===================== 
		//  C needs PL;
		//  PL needs OOD;
		//  PL needs ToC;
		//  OOD needs F2;
		//  ToC needs F2;
		//  F2 needs F1;
		ArrayList<Course> schedule10 = new ArrayList<Course>();
		schedule10.add(this.F1); 
		schedule10.add(this.F2); 
		schedule10.add(this.ToC);
		schedule10.add(this.OOD); 
		schedule10.add(this.PL); 
		schedule10.add(this.C); 
		t.checkExpect(c.validSchedule(schedule10), true);

		ArrayList<Course> schedule11 = new ArrayList<Course>();
		schedule11.add(this.F2); 
		schedule11.add(this.ToC);
		schedule11.add(this.OOD); 
		schedule11.add(this.PL); 
		schedule11.add(this.C); 
		t.checkExpect(c.validSchedule(schedule11), false);

		ArrayList<Course> schedule12 = new ArrayList<Course>();
		schedule12.add(this.F2); 
		schedule12.add(this.F1); 
		schedule12.add(this.ToC);
		schedule12.add(this.OOD); 
		schedule12.add(this.PL); 
		schedule12.add(this.C); 
		t.checkExpect(c.validSchedule(schedule12), false);

		ArrayList<Course> schedule13 = new ArrayList<Course>();
		schedule13.add(this.ToC);
		schedule13.add(this.OOD); 
		schedule13.add(this.PL); 
		schedule13.add(this.C); 
		t.checkExpect(c.validSchedule(schedule13), false);

		ArrayList<Course> schedule14 = new ArrayList<Course>();
		schedule14.add(this.PL); 
		schedule14.add(this.C); 
		t.checkExpect(c.validSchedule(schedule14), false);

		ArrayList<Course> schedule15 = new ArrayList<Course>();
		schedule15.add(this.C); 
		t.checkExpect(c.validSchedule(schedule15), false);
	}


	void testProcess(Tester t){
		this.initData();
		// =====
		// F1 (depth:0)
		// =====
		// F1 is already processed
		ArrayList<Course> processed = new ArrayList<Course>();
		processed.add(this.F1); 
		this.F1.process(processed);
		// Since it's alreddy processed, nothing should change.
		ArrayList<Course> exp = new ArrayList<Course>();
		exp.add(this.F1);
		t.checkExpect(processed, exp);

		// ===============
		// F2 (depth:1)
		// ===============
		// F2 is not processed, with F1 (his pre) already in the processed list
		ArrayList<Course> processed1 = new ArrayList<Course>();
		processed1.add(this.F1); 
		this.F2.process(processed1);
		// Just add, F2 since his pre it's already processed
		ArrayList<Course> exp1 = new ArrayList<Course>();
		exp1.add(this.F1); exp1.add(this.F2);
		t.checkExpect(processed1, exp1);

		// F2 is not processed, this time processed is EMPTY!
		ArrayList<Course> processed2 = new ArrayList<Course>();
		this.F2.process(processed2);
		// add F1 because is the pre of F2, and F2 himself!
		ArrayList<Course> exp2 = new ArrayList<Course>();
		exp2.add(this.F1); exp2.add(this.F2);
		t.checkExpect(processed2, exp2);

		// ===============
		// depth:2
		// ===============

		// AD is not processed, processed is EMPTY!
		ArrayList<Course> processed3 = new ArrayList<Course>();
		this.AD.process(processed3);
		// add F1, F2 because:
		// AD -> F2
		// F2 -> F1
		// F1 -> none
		ArrayList<Course> exp3 = new ArrayList<Course>();
		exp3.add(this.F1); exp3.add(this.F2); exp3.add(this.AD);
		t.checkExpect(processed3, exp3);
		// AD is not processed, processed contains F1 
		ArrayList<Course> processed4 = new ArrayList<Course>();
		processed4.add(this.F1);
		this.AD.process(processed4);
		// Add only F2 and AD
		ArrayList<Course> exp4 = new ArrayList<Course>();
		exp4.add(this.F1); exp4.add(this.F2); exp4.add(this.AD);
		t.checkExpect(processed4, exp4);
		// AD is not processed, processed contains F1, F2 
		ArrayList<Course> processed5 = new ArrayList<Course>();
		processed5.add(this.F1); processed5.add(this.F2);
		this.AD.process(processed5);
		// add only AD
		ArrayList<Course> exp5 = new ArrayList<Course>();
		exp5.add(this.F1); exp5.add(this.F2); exp5.add(this.AD);
		t.checkExpect(processed5, exp5);

		// AD is processed!
		ArrayList<Course> processed6 = new ArrayList<Course>();
		processed6.add(this.F1); processed6.add(this.F2); processed6.add(this.AD);
		this.AD.process(processed6);
		// add only AD
		ArrayList<Course> exp6 = new ArrayList<Course>();
		exp6.add(this.F1); exp6.add(this.F2); exp6.add(this.AD);
		t.checkExpect(processed6, exp6);

		// ===============
		// depth:3
		// ===============

		// Large-Scale Parallel Data Processing
		this.LSPDP = new Course("Large-Scale Parallel Data Processing");
		this.LSPDP.addPrereq(this.AD); this.LSPDP.addPrereq(this.CS);

		// LSPDP is not processed, processed is EMPTY!
		ArrayList<Course> processed7 = new ArrayList<Course>();
		this.LSPDP.process(processed7);
		// add AD, CS, F1, F2 because:
		// LSPDP -> AD
			// AD -> F2
			// F2 -> F1
			// F1 -> none
		// LSPDP -> CS
			// CS -> F2 
			// F2 -> F1
			// F1 -> none
		ArrayList<Course> exp7 = new ArrayList<Course>();
		exp7.add(this.F1); exp7.add(this.F2); exp7.add(this.AD);
		exp7.add(this.CS); exp7.add(this.LSPDP); 
		t.checkExpect(processed7, exp7);

		// LSPDP is not processed, processed contains F1 
		ArrayList<Course> processed8 = new ArrayList<Course>();
		processed8.add(this.F1);
		this.LSPDP.process(processed8);
		// Add only F2, AD, CS, LSPDP
		ArrayList<Course> exp8 = new ArrayList<Course>();
		exp8.add(this.F1); exp8.add(this.F2); exp8.add(this.AD);
		exp8.add(this.CS); exp8.add(this.LSPDP); 
		t.checkExpect(processed8, exp8);

		// LSPDP is not processed, processed contains AD, F2, F1
		ArrayList<Course> processed9 = new ArrayList<Course>();
		processed9.add(this.F1); processed9.add(this.F2); processed9.add(this.AD);
		this.LSPDP.process(processed9);
		// Add only CS and LSPDP
		ArrayList<Course> exp9 = new ArrayList<Course>();
		exp9.add(this.F1); exp9.add(this.F2); exp9.add(this.AD);
		exp9.add(this.CS); exp9.add(this.LSPDP); 
		t.checkExpect(processed9, exp9);

		// LSPDP is processed.
		ArrayList<Course> processed10 = new ArrayList<Course>();
		processed10.add(this.F1); processed10.add(this.F2); processed10.add(this.AD);
		processed10.add(this.CS); processed10.add(this.LSPDP); 
		this.LSPDP.process(processed10);
		// Add only CS and LSPDP
		ArrayList<Course> exp10 = new ArrayList<Course>();
		exp10.add(this.F1); exp10.add(this.F2); exp10.add(this.AD);
		exp10.add(this.CS); exp10.add(this.LSPDP); 
		t.checkExpect(processed10, exp10);

		// ===============
		// depth:4
		// ===============
		// C -> PL
			// PL -> TOC
				// TOC -> F2
					// F2 -> F1
						// F1 -> none
			// PL -> OOD
				// OOD -> F2
					// F2 -> F1
						// F1 -> none
		// C is processed.
		ArrayList<Course> processed11 = new ArrayList<Course>();
		processed11.add(this.F1); processed11.add(this.F2); processed11.add(this.ToC);
		processed11.add(this.PL); processed11.add(this.OOD); processed11.add(this.C);
		this.C.process(processed11);
		// Add all the pre
		ArrayList<Course> exp11 = new ArrayList<Course>();
		exp11.add(this.F1); exp11.add(this.F2); exp11.add(this.ToC);
		exp11.add(this.PL); exp11.add(this.OOD); exp11.add(this.C);
		t.checkExpect(processed11, exp11);
	}

	void testTopSort(Tester t){
		this.initData();
		Curriculum c = new Curriculum();

		// All the courses already in a good position!
		ArrayList<Course> courses = new ArrayList<Course>();
		courses.add(this.C);
		ArrayList<Course> exp = new ArrayList<Course>();
		exp.add(this.F1); exp.add(this.F2);  exp.add(this.ToC);
		exp.add(this.OOD); exp.add(this.PL); exp.add(this.C);
		t.checkExpect(c.topSort(courses), exp);

		// Curriculum with not the right order
		ArrayList<Course> courses1 = new ArrayList<Course>();
		courses1.add(this.C); courses1.add(this.ToC);
		courses1.add(this.OOD); courses1.add(this.PL);
		courses1.add(this.F1); courses1.add(this.F2);  
		t.checkExpect(c.topSort(courses1), exp);

		// Curriculum with course in reverse order
		ArrayList<Course> exp1 = new ArrayList<Course>();
		exp1.add(this.F1);  exp1.add(this.F2); exp1.add(this.ToC);
		exp1.add(this.OOD); exp1.add(this.PL); exp1.add(this.C);
		exp1.add(this.AD);  exp1.add(this.CS); exp1.add(this.LSPDP);
		exp1.add(this.DD);
		t.checkExpect(this.c.topSort(this.c.courses), exp1);
	}
}
