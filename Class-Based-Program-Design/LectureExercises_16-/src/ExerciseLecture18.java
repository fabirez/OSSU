/*Exercise

Design a data representation for your registrar’s office.

Information about a course includes 
	a department name (a String),
	a course number,
	an instructor,
	and an enrollment,
	which you should represent with a list of students.

For a student, the registrar keeps track of the 
	first and last name and
	the list of courses for which the student has enrolled.

For an instructor, the registrar also keeps track of 
	the first and last name as well 
	as a list of currently assigned courses.

Construct examples of at least three courses,
	at least two professors (one of whom teaches more than one course)
	and at least four students (at least one of whom is enrolled in more than one class).
*/

import tester.*;

interface IList2<T> {
}

// empty generic list
class MtList2<T> implements IList2<T> {
}

// non-empty generic list
class ConsList2<T> implements IList2<T> {
  T first;
  IList2<T> rest;

  ConsList2(T first, IList2<T> rest) {
    this.first = first;
    this.rest = rest;
  }
}

class Student {
  String firstName;
  String lastName;
  IList2<Course> courses;

  Student(String firstName, String lastName, IList2<Course> courses) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.courses = courses;
  }

  void addCourse(Course c) {
    this.courses = new ConsList2<Course>(c, this.courses);
  }
}

class Instructor {
  String firstName;
  String lastName;
  IList2<Course> courses;

  Instructor(String firstName, String lastName, IList2<Course> courses) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.courses = courses;
  }

  void addCourse(Course c) {
    this.courses = new ConsList2<Course>(c, this.courses);
  }
}

class Course {
  String departmentName;
  int courseNumber;
  Instructor instructor;
  IList2<Student> enrollment;

  Course(String departmentName, int courseNumber, Instructor instructor,
      IList2<Student> enrollment) {
    this.departmentName = departmentName;
    this.courseNumber = courseNumber;
    this.instructor = instructor;
    this.enrollment = enrollment;
  }

  void addStudent(Student s) {
    this.enrollment = new ConsList2<Student>(s, this.enrollment);
  }
}

class ExamplesCourses {

  Student s1;
  Student s2;
  Student s3;
  Student s4;
  Student s5;
  Student s6;

  Instructor i1;
  Instructor i2;
  Instructor i3;

  Course c1;
  Course c2;
  Course c3;
  Course c4;
  Course c5;
  Course c6;

  IList2<Student> los1;
  IList2<Student> los2;
  IList2<Student> los3;

  IList2<Course> mtCourse;
  IList2<Course> loc1;
  IList2<Course> loc2;
  IList2<Course> loc3;

  void initConditions() {
    this.mtCourse = new MtList2<Course>();

    this.s1 = new Student("Alice", "Red", mtCourse);
    this.s2 = new Student("Bob", "Blue", mtCourse);
    this.s3 = new Student("Carl", "Cyan", mtCourse);
    this.s4 = new Student("George", "Gray", mtCourse);
    this.s5 = new Student("Hector", "Black", mtCourse);
    this.s6 = new Student("Isaac", "White", mtCourse);

    this.los1 = new ConsList2<Student>(s1, new ConsList2<Student>(s2, new MtList2<Student>()));

    this.los2 = new ConsList2<Student>(s3, new ConsList2<Student>(s4, new MtList2<Student>()));

    this.los3 = new ConsList2<Student>(s5, new ConsList2<Student>(s6, new MtList2<Student>()));

    this.i1 = new Instructor("Dylan", "Yellow", mtCourse);
    this.i2 = new Instructor("Ester", "Green", mtCourse);
    this.i3 = new Instructor("Fiffy", "Purple", mtCourse);

    this.c1 = new Course("Departemnt1", 1, i1, los1);
    this.c2 = new Course("Departemnt2", 2, i2, los2);
    this.c3 = new Course("Departemnt3", 3, i3, los3);
    this.c4 = new Course("Departemnt4", 4, i1, los1);
    this.c5 = new Course("Departemnt5", 5, i2, los2);
    this.c6 = new Course("Departemnt6", 6, i3, los3);

    this.loc1 = new ConsList2<Course>(c1, new ConsList2<Course>(c2, new MtList2<Course>()));

    this.loc2 = new ConsList2<Course>(c3, new ConsList2<Course>(c4, new MtList2<Course>()));

    this.loc3 = new ConsList2<Course>(c5, new ConsList2<Course>(c6, new MtList2<Course>()));
  }

  void testInstructor(Tester t) {
    initConditions();
    t.checkExpect(i1.courses, new MtList2<Course>());

    i1.addCourse(c2);
    i1.addCourse(c1);
    t.checkExpect(i1.courses, loc1);

    i2.addCourse(c4);
    i2.addCourse(c3);
    t.checkExpect(i2.courses, loc2);
  }

  void testStudent(Tester t) {
    initConditions();
    t.checkExpect(s1.courses, new MtList2<Course>());

    s1.addCourse(c2);
    s1.addCourse(c1);
    t.checkExpect(s1.courses, loc1);

    s2.addCourse(c4);
    s2.addCourse(c3);
    t.checkExpect(s2.courses, loc2);

    s3.addCourse(c6);
    s3.addCourse(c5);
    t.checkExpect(s3.courses, loc3);

    s4.addCourse(c5);
    t.checkExpect(s4.courses, new ConsList2<Course>(c5, new MtList2<Course>()));
  }
}
