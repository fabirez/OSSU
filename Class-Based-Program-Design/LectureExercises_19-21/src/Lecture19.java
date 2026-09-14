/*Do Now!
	Why not?
*/

// > Because we are  creating a new list with the new number, not updating the existing list with a new number.
// > Basically, we are not changin anything, but returning sometingh new.

/*Do Now!
	Design a method on Person that changes the phone number to the given one.
	What return type should this method have?
*/

// > Void
/*
void changeNumber(int newNumber){
	that.number = neNumber;
}
*/


/*Do Now!
 We already have the start of a test fixture for phone lists.
 Modify the definition of initData so that it initializes all the Person fields of the examples class, 
 too,
 instead of allowing them to be initialized just once (and hoping they never get changed).
*/

// > Code below


/*Do Now!
	Follow the arrows in the diagram above to convince yourself that all three of these names refer to the same object.
*/

// > Yes, they refer to the same object

/*
// In ExamplesPhoneLists
void testAliasing(Tester t) {
  // Create two Person objects that are the same
  Person johndoe1 = new Person("John Doe", 12345);
  Person johndoe2 = new Person("John Doe", 12345);
  // Alias johndoe1 to johndoe3
  Person johndoe3 = johndoe1;
 
  // Check that all three John Does are the same according to samePerson
  t.checkExpect(johndoe1.samePerson(johndoe2), true);
  t.checkExpect(johndoe1.samePerson(johndoe3), true);
 
  // Modify johndoe1
  johndoe1.name = "Johnny Deere";
 
  // Now let's try the same tests.  Which of them will pass?
  t.checkExpect(johndoe1.samePerson(johndoe2), true);
  t.checkExpect(johndoe1.samePerson(johndoe3), true);
}
Do Now!
	Determine which of these tests pass, and which fail. Draw an object diagram to help, if necessary.
*/

// > t.checkExpect(johndoe1.samePerson(johndoe2), true); -> true
// > t.checkExpect(johndoe1.samePerson(johndoe3), true); -> true
// > t.checkExpect(johndoe1.samePerson(johndoe2), true); -> false
// > t.checkExpect(johndoe1.samePerson(johndoe3), true); -> true

/*
 *
class Counter {
  int val;
  Counter() {
    this(0);
  }
  Counter(int initialVal) {
    this.val = initialVal;
  }
  int get() {
    int ans = this.val;
    this.val = this.val + 1;
    return ans;
  }
}

class ExamplesCounter {
  void testCounter(Tester t) {
    Counter c1 = new Counter();
    Counter c2 = new Counter(5);
    Counter c3 = c1;
    // What should these tests be?
    t.checkExpect(c1.get(), ???);             // Test 1
    t.checkExpect(c2.get(), ???);             // Test 2
    t.checkExpect(c3.get(), ???);             // Test 3
    t.checkExpect(c1.get() == c3.get(), ???); // Test 4
    t.checkExpect(c1.get() == c1.get(), ???); // Test 5
    t.checkExpect(c2.get() == c1.get(), ???); // Test 6
    t.checkExpect(c2.get() == c1.get(), ???); // Test 7
    t.checkExpect(c1.get() == c1.get(), ???); // Test 8
    t.checkExpect(c2.get() == c1.get(), ???); // Test 9
  }
}
Do Now!

Fill in the ??? in the tests above.
*/
/*
class ExamplesCounter {
  void testCounter(Tester t) {
    Counter c1 = new Counter();
    Counter c2 = new Counter(5);
    Counter c3 = c1;
    // What should these tests be?
    t.checkExpect(c1.get(), 1);             // Test 1
    t.checkExpect(c2.get(), 6);             // Test 2
    t.checkExpect(c3.get(), 2);             // Test 3
    t.checkExpect(c1.get() == c3.get(), false); // Test 4
    t.checkExpect(c1.get() == c1.get(), false); // Test 5
    t.checkExpect(c2.get() == c1.get(),  true); // Test 6
    t.checkExpect(c2.get() == c1.get(),  true); // Test 7
    t.checkExpect(c1.get() == c1.get(), false); // Test 8
    t.checkExpect(c2.get() == c1.get(), false); // Test 9
  }
}
*/

/*Do Now!
	Design the method T find(IPred<T> whichOne) for IList<T>,
	which uses the given predicate to determine which item in the list to return.
*/
/*

// In IList
// Finds and returns the person in this list matching the given predicate,
// or null if no such person is found
T find(IPred<T> whichOne);

// In ConsList
public T find(IPred<T> fn)
{
	if(fn.apply(this.first)){
		return this.first;
	}else{
		return this.rest.find(fn);
	}
}
// In MtList
public T find(IPred<T> fn) {return;}
*/

/*Do Now!
	Revise the rest of the tests for changePhone to use IList<Person>. Create whatever function objects are needed.
*/

import tester.*;

// Represents functions of signature A -> R, for some argument type A and
// result type R
interface IFunc<A, R> {
  R apply(A input);
}

// Represents functions of signature A1 A2 -> R
interface IFunc2<A1, A2, R> {
  R apply(A1 fn, A2 base);
}

// Represents functions of signature A1 -> Boolean
interface IPred<T> {
  boolean apply(T t);
}



// Returns true if this list contains a person with the given name
class SameName implements IPred<Person>{
	String name;
	SameName(String name){ this.name=name; }
	public boolean apply(Person p){ return p.name.equals(this.name); }
}


	// Change the phone number for the person in this list with the given name
	// ILoPerson changePhone(String name, int newNum);
	// EFFECT: Change the phone number for the person in this list with the given name

	// void changePhone(String name, int newNum);

class ChangePhone implements IFunc2<Person, Void, Void>{
	String name; int newNum;
	ChangePhone(String name, int newNum){this.name=name; this.newNum=newNum;}
	public Void apply(Person p, Void base){
		if(p.name.equals(this.name)){
			p.phone = this.newNum;
		}
			return base;
	}
}

class FindPhoneNum implements IFunc2<Person, Integer, Integer>{
	String name;
	FindPhoneNum(String name){this.name=name;}
	public Integer apply(Person p, Integer base){
		// base != -1, if we already found the number in the list,
		// the person appears two times.
		if(p.name.equals(this.name) && base == -1){
			return p.phone;
		}else{
			return base;
		}
	}
}


class SamePersonName implements IPred<Person>{
	String name;
	SamePersonName(String name){ this.name=name; }
	public boolean apply(Person p){ return p.name.equals(this.name); }
}

class ChangeNumber implements IFunc<Person, Void>{
	int newNum;
	ChangeNumber(int newNum){ this.newNum = newNum; }
	public Void apply(Person p){ 
		p.phone = this.newNum; 
		return null;
	}
}

interface IList<T> {
	// Finds and returns the person in this list matching the given predicate,
	// or null if no such person is found
	// T find(IPred<T> whichOne);

  <U> U foldr(IFunc2<T, U, U> f, U base);

	// EFFECT: Finds and modifies the person in this list matching the
	// given predicate, by using the given operation
	Void find(IPred<T> whichOne, IFunc<T, Void> whatToDo);
}

class ConsList<T> implements IList<T>{
	T first;
	IList<T> rest;

	ConsList(T first, IList<T> rest){
		this.first=first;
		this.rest=rest;
	}

	// public T find(IPred<T> whichOne)
	// {
	// 	if(whichOne.apply(this.first)){
	// 		return this.first;
	// 	}else{
	// 		return this.rest.find(whichOne);
	// 	}
	// }

	public Void find(IPred<T> whichOne, IFunc<T, Void> whatToDo) {
		if (whichOne.apply(this.first)) {
			whatToDo.apply(this.first);
		}
		else {
			this.rest.find(whichOne, whatToDo);
		}
		return null;
	}

  public <U> U foldr(IFunc2<T, U, U> f, U base)
	{
    return f.apply(this.first,
                   this.rest.foldr(f, base));
	}

}

class MtList<T> implements IList<T>{
	MtList(){}

	// public T find(IPred<T> fn) {return null;}
	public Void find(IPred<T> whichOne, IFunc<T, Void> whatToDo) { return null; }
  public <U> U foldr(IFunc2<T, U, U> f, U base){ return base; }
}


interface ILoPerson {
  // Returns true if this list contains a person with the given name
  boolean contains(String name);
	// Finds the person in this list with the given name and returns their phone number,
  // or -1 if no such person is found
  int findPhoneNum(String name);
	// Change the phone number for the person in this list with the given name
	// ILoPerson changePhone(String name, int newNum);
	// EFFECT: Change the phone number for the person in this list with the given name
	void changePhone(String name, int newNum);
}

class MtLoPerson implements ILoPerson {
	MtLoPerson(){};
	// Returns true if this empty list contains a person with the given name
	public boolean contains(String name) { return false; }

	// Finds the person in this empty list with the given name and returns their phone number,
	// or -1 if no such person is found
	public int findPhoneNum(String name) { return -1; }

	// Change the phone number for the person in this empty list with the given name
	// public ILoPerson changePhone(String name, int newNum) { return this; }
	// EFFECT: Change the phone number for the person in this list with the given name
	public void changePhone(String name, int newNum){return;};
}

class ConsLoPerson implements ILoPerson {
	Person first;
	ILoPerson rest;
	ConsLoPerson(Person first, ILoPerson rest){
		this.first=first;
		this.rest=rest;
	}
	// Returns true if this non-empty list contains a person with the given name
	public boolean contains(String name) {
		return this.first.name.equals(name) || this.rest.contains(name);
	}

	// Finds the person in this non-empty list with the given name and returns their phone number,
	// or -1 if no such person is found
	public int findPhoneNum(String name) {
		if (this.first.name.equals(name)) {
			return this.first.phone;
		}
		else {
			return this.rest.findPhoneNum(name);
		}
	}
	// NOTE: Same methods, the first is in the "old way", the second is in the "mutation way"
	// Change the phone number for the person in this non-empty list with the given name
	// public ILoPerson changePhone(String name, int newNum) {
	// 	if (this.first.name.equals(name)) {
	// 		return new ConsLoPerson(new Person(name, newNum), this.rest);
	// 	}
	// 	else {
	// 		return new ConsLoPerson(this.first, this.rest.changePhone(name, newNum));
	// 	}
	// }
	// EFFECT: Change the phone number for the person in this non-empty list with the given name
	public void changePhone(String name, int newNum) {
		if (this.first.name.equals(name)) {
			this.first.changeNum(newNum); // do the update
		}
		else {
			this.rest.changePhone(name, newNum); // keep searching the rest of the list
		}
	}
}

class Person {
  String name;
  int phone;
  Person(String name, int phone) {
    this.name = name;
    this.phone = phone;
  }
  // Returns true when the given person has the same name and phone number as this person
  boolean samePerson(Person that) {
    return this.name.equals(that.name) && this.phone == that.phone;
  }

	// EFFECT: modifies this person's phone number to be the given one
	void changeNum(int newNum) {
		this.phone = newNum;
	}
}

class ExamplePhoneLists {
  Person anne;
  Person bob; 
  Person clyde;
  Person dana; 
  Person eric;  
  Person frank;
  Person gail; 
  Person henry; 
  Person irene; 
  Person jenny; 
 
  IList<Person> friends, family, work;
  void initData() {
		this.anne = new Person("Anne", 1234);
		this.bob  = new Person("Bob", 3456);
		this.clyde= new Person("Clyde", 6789);
		this.dana = new Person("Dana", 1357);
		this.eric = new Person("Eric", 12469);
		this.frank= new Person("Frank", 7294);
		this.gail = new Person("Gail", 9345);
		this.henry= new Person("Henry", 8602);
		this.irene= new Person("Irene", 91302);
		this.jenny= new Person("Jenny", 8675309);

    this.friends =
      new ConsList<Person>(this.anne, new ConsList<Person>(this.clyde,
        new ConsList<Person>(this.gail, new ConsList<Person>(this.frank,
          new ConsList<Person>(this.jenny, new MtList<Person>())))));
    this.family =
      new ConsList<Person>(this.anne, new ConsList<Person>(this.dana,
        new ConsList<Person>(this.frank, new MtList<Person>())));
    this.work =
      new ConsList<Person>(this.bob, new ConsList<Person>(this.clyde,
        new ConsList<Person>(this.dana, new ConsList<Person>(this.eric,
          new ConsList<Person>(this.henry, new ConsList<Person>(this.irene,
            new MtList<Person>()))))));
  }



	void testFindPhoneNum(Tester t) {
		this.initData();
		IFunc2<Person, Integer, Integer> fPN = new FindPhoneNum("Frank");
		t.checkExpect(this.friends.foldr(fPN, -1), 7294);
		t.checkExpect(this.family.foldr(fPN, -1), this.friends.foldr(fPN, -1));
		t.checkExpect(this.frank.phone, 7294);

		// Change phone number with foldr (my impl.)
		// IFunc2<Person, Void, Void> fCP = new ChangePhone("Frank", 9021);
		// this.family.foldr(fCP, null);

		// Change phone number with find (lecture impl.)
		IPred<Person>       sP =  new SamePersonName("Frank");
		IFunc<Person, Void> cN =  new ChangeNumber(9021);
		this.family.find(sP, cN);

		t.checkExpect(this.friends.foldr(fPN, -1), 9021);
		t.checkExpect(this.family.foldr(fPN, -1), this.friends.foldr(fPN, -1));
		t.checkExpect(this.frank.phone, 9021);
	}

	void testChangeNum(Tester t) {
		this.initData();
		t.checkExpect(this.frank.phone, 7294);
		IFunc2<Person, Void, Void> fCP = new ChangePhone("Frank", 9021);
		this.family.foldr(fCP, null);
		t.checkExpect(this.frank.phone, 9021);
	}


	// In ExamplesPhoneLists
	void testAliasing(Tester t) {
		// Create two Person objects that are the same
		Person johndoe1 = new Person("John Doe", 12345);
		Person johndoe2 = new Person("John Doe", 12345);
		// Alias johndoe1 to johndoe3
		Person johndoe3 = johndoe1;
	 
		// Check that all three John Does are the same according to samePerson
		t.checkExpect(johndoe1.samePerson(johndoe2), true);
		t.checkExpect(johndoe1.samePerson(johndoe3), true);
	 
		// Modify johndoe1
		johndoe1.name = "Johnny Deere";
	 
		t.checkExpect(johndoe1.samePerson(johndoe2), false);
		t.checkExpect(johndoe1.samePerson(johndoe3), true);
	}

}

