import tester.*;
import java.util.HashMap;

class CourseCatalog {
	HashMap<Course, String> c;
	CourseCatalog(){ this.c = new HashMap(); }

	// add or update a course with a room in the hashmap 
	// return the updated or added room (val)
	String addCourse(Course course, String room){ 
		this.c.put(course, room); 
		return room;
	}

	// Produce the room if exist; otherwise null
	String findRoom(Course course){ return this.c.get(course); }

	// Return true if the give course exist in the hashmap; false otherwise.herwise.
	boolean isScheduled(Course course){ return this.c.containsKey(course); }
}

class Course{
	String code;
	int year;
	Course(String code, int year){
		this.code = code;
		this.year = year;
	}


	public boolean equals(Object that)
	{
		if(that instanceof Course){
			Course c = (Course)that;
			return this.code.equals(c.code)
						 && 
						 this.year == c.year;
		}
		return false;
	}
	public int hashCode(){ return this.code.hashCode() * 10000 + this.year; }
}

class ExamplesCourse{
	Course  c1;
	Course c1_;
	Course  c2;
	Course  c3;

	CourseCatalog cc1;

	void initData(){
		this.c1  = new Course("CsX19", 2019);
		this.c1_ = new Course("CsX19", 2019);
		this.c2  = new Course("CsY15", 2015);
		this.c3  = new Course("CsY16", 2016);

		this.cc1  = new CourseCatalog();
	}

	void testEquals(Tester t){
	  this.initData();
	  t.checkExpect(this.c1.equals(this.c2), false); 
	  t.checkExpect(this.c1.equals(this.c1_), true);
	}

	void testHashCode(Tester t){
	  this.initData();
	  t.checkExpect(this.c1.hashCode(), this.c1_.hashCode()); 
	}


	void testAddCourse(Tester t){
		this.initData();
		t.checkExpect(this.cc1.addCourse(this.c1, "RoomA"), "RoomA");
		t.checkExpect(this.cc1.addCourse(this.c2, "RoomB"), "RoomB");
		t.checkExpect(this.cc1.addCourse(this.c3, "RoomC"), "RoomC");
	}


	void testFindRoom(Tester t){
		this.initData();

		t.checkExpect(this.cc1.findRoom(this.c1), null);
		t.checkExpect(this.cc1.addCourse(this.c1, "RoomA"), "RoomA");
		t.checkExpect(this.cc1.findRoom(this.c1), "RoomA");
		t.checkExpect(this.cc1.findRoom(this.c1_), "RoomA");
		t.checkExpect(this.cc1.findRoom(this.c2), null);
	}

	void testIsScheduled(Tester t){
		this.initData();

		t.checkExpect(this.cc1.isScheduled(this.c1), false);
		t.checkExpect(this.cc1.addCourse(this.c1, "RoomA"), "RoomA");
		t.checkExpect(this.cc1.isScheduled(this.c1), true);
		t.checkExpect(this.cc1.isScheduled(this.c1_), true);
	}

}

