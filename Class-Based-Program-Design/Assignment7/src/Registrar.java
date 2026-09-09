/*
The registrar’s office maintains a great deal of information about classes, instructors, and students.
Your task in this problem is to model that data, and implement a few methods on it.
We deliberately do not give you the class diagram for this problem:
from the description below,
you should properly design whatever classes you think are relevant.

Please use generic lists (i.e. IList<T>) for this problem.

## 7.1 Data constraints

- [x] A Course consists of 
			a name (a String),
			an Instructor named prof,
			and a list of Students named students.
			No Course can be constructed without an Instructor available to teach it.
			Hint: Do you think that you should provide a list when constructing a Course? Why or why not?
- [x] An Instructor has a name and a list of Courses named courses he or she teaches.
			Instructors are initially constructed without any Courses to teach.
- [x] A Student has a name, an id number (an int), and a list of Courses they are taking.
			Students are initially constructed without taking any Courses.

- [x] It should always be the case that any Student who is enrolled in a Course should appear in the list of Students for that Course,
			and the Course should likewise appear in the Student’s list of Courses.

- [x] It should always be the case that the Instructor for any Course should have that Course appear in the Instructor’s list of Courses.

## 7.2 Methods and examples to design

- [x] Design a method void enroll(Course c) that enrolls a Student in the given Course.
			Design any helper methods as appropriate.
- [x] Design a method boolean classmates(Student c) that determines whether the given Student is in any of the same classes as this Student.
- [x] Design a method boolean dejavu(Student c) that determines whether the given Student is in more than one of this Instructor’s Courses.
- [x] Construct example data consisting of at least five Students, at least four Courses and at least two Instructors. Test all your methods thoroughly.
*/

import tester.*;


interface IPred<T1, T2>{
	boolean apply(T1 t1, T2 t2);
}

class SameCourse implements IPred<Course, Course>{
	public boolean apply(Course c1, Course c2){
		return c1.name.equals(c2.name);
	}
}

class SameStudent implements IPred<Student, Student>{
	public boolean apply(Student s1, Student s2){
		return s1.id == s2.id;
	}
}

class SameInstructor implements IPred<Instructor, Instructor>{
	public boolean apply(Instructor i1, Instructor i2){
		return i1.name.equals(i2.name);
	}
}


interface IList<T>
{
	// produce true if the given item is in the list; false otherwise
	boolean contains(IPred<T, T> pred, T item);

	// produce true if one of the item in this list is the same as one in the given one; false otherwise
	boolean containsList(IPred<T, T> pred, IList<T> loi);

	// produce how many times an item in this list it's equal to another item of the given list.
	// WARNING: start count with 0
	int containsListCount(IPred<T, T> pred, IList<T> loi, int count);
}

class ConsList<T> implements IList<T> 
{
	T first;
	IList<T> rest;
	ConsList(T first, IList<T> rest){
		this.first=first;
		this.rest=rest;
	}

	public boolean contains(IPred<T, T> pred, T item)
	{
		if(pred.apply(this.first, item)){
			return true;
		}else{
			return this.rest.contains(pred, item);
		}
	}


	public boolean containsList(IPred<T, T> pred, IList<T> loi)
	{
		if(loi.contains(pred, this.first)){
			return true;
		}else{
			return this.rest.containsList(pred, loi);
		}
	}

	public int containsListCount(IPred<T, T> pred, IList<T> loi, int count)
	{
		if(loi.contains(pred, this.first)){
			return this.rest.containsListCount(pred, loi, count + 1);
		}else{
			return this.rest.containsListCount(pred, loi, count);
		}
	}
}

class MtList<T> implements IList<T>
{
	MtList(){}
	public boolean contains(IPred<T, T> pred, T item) { return false; }
	public boolean containsList(IPred<T, T> pred, IList<T> loi){ return false; }
	public int containsListCount(IPred<T, T> pred, IList<T> loi, int count) { return count; }
} 


class Instructor{
	String name;
	IList<Course> courses;

	Instructor(String name,IList<Course> courses){
		this.name=name;
		this.courses=courses;
	}

	//  produce true if the given Student is in more than one of this Instructor’s Courses; otherwise false.
	boolean dejavu(Student s) {
		return this.courses.containsListCount(new SameCourse(), s.courses, 0) > 1;
	}
}

class Student{
	String name;
	int id;
	IList<Course> courses;

	Student(String name, int id, IList<Course> courses){
		this.name=name;
		this.id=id;
		this.courses=courses;
	}

	// EFFECT: Add a new course to this student
	void enroll(Course c){
		c.addStudent(this);
		this.courses = new ConsList<Course>(c, this.courses);
	}

	// determines whether the given Student is in any of the same classes as this Student.
	boolean classmates(Student c){
		return this.courses.containsList(new SameCourse(), c.courses);
	}

}

class Course{
	String name;
	Instructor prof;
	IList<Student> students;

	Course(String name, Instructor prof, IList<Student> students){
		this.name=name;
		this.prof=prof;
		this.students=students;
	}

	// EFFECT: Add a new student to this course
	void addStudent(Student s){
		this.students = new ConsList<Student>(s, this.students);
	}
}


class ExamplesRegistrar{
	Instructor i1;
	Instructor i2;
	Instructor i3;

	Student s1;
	Student s2;
	Student s3;
	Student s4;
	Student s5;

	IList<Student> los1;
	IList<Student> los2;
	IList<Student> los3;

	Course c1;
	Course c2;
	Course c3;
	Course c4;

	// IList<Course> loc1;
	// IList<Course> loc2;
	// IList<Course> loc3;

	void initData(){
		this.i1 = new Instructor(   "Anne", new MtList<Course>());
		this.i2 = new Instructor(    "Bob", new MtList<Course>());
		this.i3 = new Instructor("Charlie", new MtList<Course>());

		this.s1 = new Student(    "Dug", 0, new MtList<Course>());
		this.s2 = new Student(  "Ester", 1, new MtList<Course>());
		this.s3 = new Student("Federik", 2, new MtList<Course>());
		this.s4 = new Student( "George", 3, new MtList<Course>());
		this.s5 = new Student( "Hannah", 4, new MtList<Course>());

	  this.los1 = new ConsList<Student>(s1,
		new ConsList<Student>(s2,
		new ConsList<Student>(s3,
		new MtList<Student>())));
	  this.los2 = new ConsList<Student>(s1,
		new ConsList<Student>(s2,
		new MtList<Student>()));
	  this.los3 = new ConsList<Student>(s1,
		new ConsList<Student>(s3,
		new MtList<Student>()));

		// - [ ] It should always be the case that any Student who is enrolled in a Course should appear in the list of Students for that Course,
		//				and the Course should likewise appear in the Student’s list of Courses.
		// - [ ] It should always be the case that the Instructor for any Course should have that Course appear in the Instructor’s list of Courses.

		this.c1 = new Course("Course1", i1, new MtList<Student>());
		// Add the course in the instructor
		this.i1.courses = new ConsList<Course>(c1, this.i1.courses);

		this.c2 = new Course("Course2", i2, new MtList<Student>());
		// Add the course in the instructor
		this.i2.courses = new ConsList<Course>(c2, this.i2.courses);

		this.c3 = new Course("Course3", i3, new MtList<Student>());
		// Add the course in the instructor
		this.i3.courses = new ConsList<Course>(c3, this.i3.courses);

		this.c4 = new Course("Course4", this.i1, new MtList<Student>());
		// Add the course in the instructor
		this.i1.courses = new ConsList<Course>(c4, this.i1.courses);
	}

	void testEnroll(Tester t){
		this.initData();
		IPred<Student, Student> sS = new SameStudent();
		IPred< Course,  Course> sC = new  SameCourse();

		t.checkExpect( this.s1.courses.contains(sC, c1), false);
		t.checkExpect(this.c1.students.contains(sS, s1), false);

		// Add the course to the student
		this.s1.enroll(this.c1);
		// Check if the course is present in the list of course of the student
		t.checkExpect(this.s1.courses.contains(sC, this.c1),  true);
		// Check if the student is present in the list of students of the course
		t.checkExpect(this.c1.students.contains(sS, s1), true);

		this.s2.enroll(this.c1);
		// Check if the course is present in the list of course of the student
		t.checkExpect(this.s2.courses.contains(sC, this.c1), true);
		// Check if the student is present in the list of students of the course
		t.checkExpect(this.c1.students.contains(sS, s2), true);


		this.s3.enroll(this.c2);
		// Check if the course is present in the list of course of the student
		t.checkExpect(this.s3.courses.contains(sC, this.c2), true);
		// Check if the student is present in the list of students of the course
		t.checkExpect(this.c2.students.contains(sS, s3), true);

		this.s4.enroll(this.c3);
		// Check if the course is present in the list of course of the student
		t.checkExpect(this.s4.courses.contains(sC, this.c3), true);
		// Check if the student is present in the list of students of the course
		t.checkExpect(this.c3.students.contains(sS, s4), true);

		this.s5.enroll(this.c4);
		// Check if the course is present in the list of course of the student
		t.checkExpect(this.s5.courses.contains(sC, this.c4), true);
		// Check if the student is present in the list of students of the course
		t.checkExpect(this.c4.students.contains(sS, s5), true);

	}


	void testClassmates(Tester t){
		this.initData();
		IPred< Course,  Course> sC = new  SameCourse();

		t.checkExpect(this.s1.classmates(this.s2), false);

		this.s1.enroll(this.c1);
		t.checkExpect(this.s1.courses.contains(sC, c1), true);
		t.checkExpect(this.s1.classmates(this.s2), false);
		this.s2.enroll(this.c1);
		t.checkExpect(this.s2.courses.contains(sC, c1), true);
		t.checkExpect(this.s1.classmates(this.s2),  true);
		this.s3.enroll(this.c1);
		t.checkExpect(this.s3.courses.contains(sC, c1), true);
		t.checkExpect(this.s3.classmates(this.s2),  true);

		this.s4.enroll(this.c3);
		t.checkExpect(this.s4.courses.contains(sC, c3), true);
		t.checkExpect(this.s4.classmates(this.s5), false);
		this.s5.enroll(this.c3);
		t.checkExpect(this.s5.courses.contains(sC, c3), true);
		t.checkExpect(this.s4.classmates(this.s5),  true);

		t.checkExpect(this.s4.classmates(this.s1),  false);
		t.checkExpect(this.s4.classmates(this.s2),  false);
		t.checkExpect(this.s4.classmates(this.s3),  false);

		t.checkExpect(this.s5.classmates(this.s1),  false);
		t.checkExpect(this.s5.classmates(this.s2),  false);
		t.checkExpect(this.s5.classmates(this.s3),  false);

	}


	void testDejavu(Tester t){
		this.initData();
		IPred< Course,  Course> sC = new  SameCourse();

		t.checkExpect(this.i1.dejavu(this.s1), false);
		t.checkExpect(this.i1.dejavu(this.s2), false);

		this.s1.enroll(this.c1);
		t.checkExpect(this.i1.dejavu(this.s1), false);
		t.checkExpect(this.i1.dejavu(this.s2), false);
	
		// Ensure that the instructor have the couse	
		t.checkExpect(this.i1.courses.contains(sC, this.c1), true);
		t.checkExpect(this.i1.courses.contains(sC, this.c4), true);

		this.s1.enroll(c4);
		t.checkExpect(this.i1.dejavu(this.s1), true);
		t.checkExpect(this.i1.dejavu(this.s2), false);

		this.s2.enroll(this.c1);
		t.checkExpect(this.i1.dejavu(this.s1), true);
		t.checkExpect(this.i1.dejavu(this.s2), false);
		this.s2.enroll(this.c2);
		this.s2.enroll(this.c3);
		t.checkExpect(this.i1.dejavu(this.s2), false);
		t.checkExpect(this.i1.dejavu(this.s2), false);
		this.s2.enroll(this.c4);
		t.checkExpect(this.i1.dejavu(this.s2), true);
	}
}
