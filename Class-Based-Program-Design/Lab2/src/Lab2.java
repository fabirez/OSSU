import tester.*;

// Represents a mode of transportation
interface IMOT {
  //returns true if this mode of transportation is at least
  //as efficient as the given mpg, false otherwise
  boolean isMoreFuelEfficientThan(int mpg);
}
 
// Represents a bicycle as a mode of transportation
class Bicycle implements IMOT {
  String brand;
 
  Bicycle(String brand) {
    this.brand = brand;
  }


   public boolean isMoreFuelEfficientThan(int mpg){
        return true;
   }
}
 
// Represents a car as a mode of transportation
class Car implements IMOT {
  String make;
  int mpg; // represents the fuel efficiency in miles per gallon
 
  Car(String make, int mpg) {
    this.make = make;
    this.mpg = mpg;
  }

  //compare this car's fuel efficiency to the given fuel efficiency
  public boolean isMoreFuelEfficientThan(int mpg) {
   return this.mpg >= mpg;
  }
}
 
// Keeps track of how a person is transported
class Person {
  String name;
  IMOT mot;
 
  Person(String name, IMOT mot) {
    this.name = name;
    this.mot = mot;
  }

    boolean motMeetsFuelEfficiency(int mpg) {
      return this.mot.isMoreFuelEfficientThan(mpg);
    }
}

class ExamplesPerson {
  IMOT diamondback = new Bicycle("Diamondback");
  IMOT toyota = new Car("Toyota", 30);
  IMOT lamborghini = new Car("Lamborghini", 17);
 
  Person bob = new Person("Bob", diamondback);
  Person ben = new Person("Ben", toyota);
  Person becca = new Person("Becca", lamborghini);

   boolean testMotMeetsFuelEfficiency(Tester t){
        return
        t.checkExpect(this.bob.motMeetsFuelEfficiency(10),    true) &&
        t.checkExpect(this.ben.motMeetsFuelEfficiency(30),    true) &&
        t.checkExpect(this.becca.motMeetsFuelEfficiency(17),  true) &&
        t.checkExpect(this.becca.motMeetsFuelEfficiency(10),  true) &&
        t.checkExpect(this.bob.motMeetsFuelEfficiency(15),    true) &&
        t.checkExpect(this.ben.motMeetsFuelEfficiency(15),    true) &&
        t.checkExpect(this.becca.motMeetsFuelEfficiency(15),  true) &&
        t.checkExpect(this.bob.motMeetsFuelEfficiency(25),    true) &&
        t.checkExpect(this.ben.motMeetsFuelEfficiency(25),    true) &&
        t.checkExpect(this.becca.motMeetsFuelEfficiency(25), false);
    }
}

/*Do Now!
    Does this person's mode of transportation meet the given fuel
    efficiency target (in miles per gallon)?
    How would you design a function like this in BSL?
*/

// > Following the design recipe, and using the keyword `cond` for indentifying the type of the transportation.


/*Do Now!
    Now that we have a signature and purpose,
    and figured out which class this method should be in, create some examples for how this method should behave.
*/

/*
   boolean testMotMeetsFuelEfficiency(t Tester){
        return
        t.checkExpect(this.bob.motMeetsFuelEfficiency(10),   true) &&
        t.checkExpect(this.ben.motMeetsFuelEfficiency(30),    true) &&
        t.checkExpect(this.becca.motMeetsFuelEfficiency(17),  true) &&
        t.checkExpect(this.becca.motMeetsFuelEfficiency(10), false);
    }
*/

/* Do Now!
    Why do we not want to simply write a getMpg method on IMOT and its implementing classes?
*/

// > Make no sense for the bicycle class, in this case. Insteag of a int a boolean return value it's more consistent


/* Do Now!

Write tests on our examples for a target fuel efficiency of 15 mpg. What about 25 mpg?
*/

/*
   boolean testMotMeetsFuelEfficiency(t Tester){
        return
        t.checkExpect(this.bob.motMeetsFuelEfficiency(15),    true) &&
        t.checkExpect(this.ben.motMeetsFuelEfficiency(15),    true) &&
        t.checkExpect(this.becca.motMeetsFuelEfficiency(15),  true) &&
        t.checkExpect(this.bob.motMeetsFuelEfficiency(25),    true) &&
        t.checkExpect(this.ben.motMeetsFuelEfficiency(25),    true) &&
        t.checkExpect(this.becca.motMeetsFuelEfficiency(25), false);
    }
*/

/* Exercise

How would we do this if we wanted to compare to another mode of transportation’s fuel efficiency instead of a given target fuel efficiency? More precisely, what if we wanted to change the method header in Person to the following:
boolean motIsMoreFuelEfficientThan(IMOT mot)
Where do you get stuck?
*/
 
// > Again, here the bicycle, beacuse we don't have a *real* way to compare it with *cars*
//

//====================================================================================================
// 2.2 Methods for Structured Data
//====================================================================================================
//
// Follow the design recipe to design the following methods for these classes:

// - Make examples of at least four pet owners - two for each of the pets, as you need to make sure you cover the two boolean values.

// - Design a method with the following purpose and header:

// - is this Person older than the given Person? boolean isOlder(Person other)

// - Design a method sameNamePet that has the following purpose statement:
// does the name of this person's pet match the given name?

//     We’ve given you the purpose statement, so start with examples and the template.
//     When you make the template for the class Person, you should notice that you do not know anything about the pet’s name.
//     You will therefore need a helper method, somewhere in the class hierarchy that represents IPets, that will check whether this pet’s name matches the given name.

//    But to design such a method,
//    you first need to define the purpose statements and the header in the interface IPet.
//    Remember, Java requires that this method be declared public - though at this time, for our simple programs, this designation is irrelevant.

//    You then need to define this method in every class that implements the interface.

// - Some people do not have pets, and sometimes–sadly–the pets do perish.
//   Add a new class NoPet to this class hierarchy so that we can represent Persons who do not have a pet.

// Make sure you add new examples!

// - Design a method perish in the class Person that produces a person whose pet has perished.


// Here are some unfinished classes that represent pets and pet owners:

// WARNING: using PETOWNER instead of PERSON

// to represent a pet owner
class PetOwner {
    String name;
    IPet pet;
    int age;
 
    PetOwner(String name, IPet pet, int age) {
        this.name = name;
        this.pet = pet;
        this.age = age;
    }

    // produce true if the current PetOwner is older than the given one; false otherwise
    boolean isOlder(PetOwner other){
        return this.age >= other.age;
    }

    boolean sameNamePet(String name){
        return this.pet.sameName(name);
    }

    PetOwner perish(){
        return new PetOwner(this.name, new NoPet(), this.age);
    }
}
// to represent a pet
interface IPet { 
    // return true if the current 
    boolean sameName(String name);
}
 
// to represent a pet cat
class Cat implements IPet {
    String name; 
    String kind;
    boolean longhaired;
 
    Cat(String name, String kind, boolean longhaired) {
        this.name = name;
        this.kind = kind;
        this.longhaired = longhaired;
    }

    public boolean sameName(String name){
        return this.name.equals(name);
    }
}
 
// to represent a pet dog
class Dog implements IPet {
    String name;
    String kind;
    boolean male;
 
    Dog(String name, String kind, boolean male) {
        this.name = name;
        this.kind = kind;
        this.male = male;
    }

    public boolean sameName(String name){
        return this.name.equals(name);
    }
}

class NoPet implements IPet {
    NoPet(){}

    public boolean sameName(String name){
        return false;
    }
}

class ExamplesPetOwner {
  ExamplesPetOwner(){}

    IPet c1 = new Cat("Cat 1", "",  true);
    IPet c2 = new Cat("Cat 2", "", false);

    IPet d1 = new Dog("Dog 1", "",  true);
    IPet d2 = new Dog("Dog 2", "", false);

    IPet np = new NoPet();

    PetOwner p1 = new PetOwner("Person 1", c1, 20);
    PetOwner p2 = new PetOwner("Person 2", c2, 30);

    PetOwner p3 = new PetOwner("Person 3", d1, 40);
    PetOwner p4 = new PetOwner("Person 4", d2, 50);

    PetOwner p5 = new PetOwner("Person 5", np, 60);
    PetOwner p6 = new PetOwner("Person 6", np, 70);


    PetOwner np1 = new PetOwner("Person 1", np, 20);
    PetOwner np2 = new PetOwner("Person 2", np, 30);

    PetOwner np3 = new PetOwner("Person 3", np, 40);
    PetOwner np4 = new PetOwner("Person 4", np, 50);

    boolean testIsOlder(Tester t){
        return 
        t.checkExpect(this.p1.isOlder(this.p2), false) &&
        t.checkExpect(this.p2.isOlder(this.p1),  true) &&
        t.checkExpect(this.p3.isOlder(this.p4), false) &&
        t.checkExpect(this.p4.isOlder(this.p3),  true) &&
        t.checkExpect(this.p5.isOlder(this.p6), false) &&
        t.checkExpect(this.p6.isOlder(this.p5),  true);
    }

    boolean testSameNamePet(Tester t){
        return 
        t.checkExpect(this.p1.sameNamePet("Cat 1"),  true) &&
        t.checkExpect(this.p2.sameNamePet("Cat 2"),  true) &&
        t.checkExpect(this.p3.sameNamePet("Dog 1"),  true) &&
        t.checkExpect(this.p4.sameNamePet("Dog 2"),  true) &&
        t.checkExpect(this.p5.sameNamePet("Dog 2"), false) &&
        t.checkExpect(this.p6.sameNamePet("Dog 2"), false) &&
        t.checkExpect(this.p4.sameNamePet("Cat 1"), false) &&
        t.checkExpect(this.p3.sameNamePet("Cat 2"), false) &&
        t.checkExpect(this.p2.sameNamePet("Dog 1"), false) &&
        t.checkExpect(this.p1.sameNamePet("Dog 2"), false);
    }

    boolean testPerish(Tester t){
        return 
        t.checkExpect(this.p1.perish(), np1) &&
        t.checkExpect(this.p2.perish(), np2) &&
        t.checkExpect(this.p3.perish(), np3) &&
        t.checkExpect(this.p4.perish(), np4) &&
        t.checkExpect(this.p5.perish(),  p5) &&
        t.checkExpect(this.p6.perish(),  p6);
    }
}

