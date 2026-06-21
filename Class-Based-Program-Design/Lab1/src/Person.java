/*Problem 1

Here is a data definition in DrRacket:

;; to represent a person
;; A Person is (make-person String Number String)
(define-struct person [name age gender])
 
(define tim (make-person "Tim" 23 "Male"))
(define kate (make-person "Kate" 22 "Female"))
(define rebecca (make-person "Rebecca" 31 "Non-binary"))
Draw the class diagram that represents this data.

Define the class Person that implements this data definition 
and the class ExamplesPerson that contains the examples defined above.
You should do this in a new Person.java file.
Right click the default package under Lab1 and select New > File. 
Name it Person.java.

Run your program to make sure it works.*/

/*
+----------------+
| Person         |
+----------------+
| String name    |
| Author age     |
| Number gender  |
+----------------+
*/

/* ***Solution Problem 1***
 class Person{
 
  String name;
  int age;
  String gender;

  Person(String name, int age, String gender){
    this.name = name;
    this.age = age;
    this.gender = gender;
  }
}

class ExamplesPerson{
  ExamplesPerson(){}

  Person p1 = new Person("Tim", 23, "Male");
  Person p2 = new Person("Kate", 22, "Female");
  Person p3 = new Person("Rebecca", 31, "Non-binary");
}
*/

/*
Problem 2

Modify your data definitions so that for each person we also record the person’s address.
For each person’s address we only record the city and the state; each of these should be its own field.
Create an Address class to contain the address information,
then modify the Person data definition to include an Address.

Draw the class diagram for this data definition

Define Java classes that represent this data definition.

Tim lives in Boston, MA, Kate lives in Warwick, RI, and Rebecca lives in Nashua, NH.

Make examples of these data and add two more people.*/


/*
+----------------+
| Person         |
+----------------+
| String name    |
| Author age     |
| Number gender  |
| Address address|--+
+----------------+  |
                    |
                    v
            +-------------+
            | Address     |
            +-------------+
            | String city |
            | String state|
            +-------------+
*/

class Person{
  String name;
  int age;
  String gender;
  Address address;

  Person(String name, int age, String gender, Address address){
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.address = address;
  }
}

class Address{
  String city; String state;

  Address(String city, String state){
    this.city = city;
    this.state = state;
  }
}

class ExamplesPerson{
  ExamplesPerson(){}

  Address a1 = new Address("Boston", "MA");
  Address a2 = new Address("Warwick", "RI");
  Address a3 = new Address("Nashua", "NH");

  Person  p1 = new Person("Tim", 23, "Male", this.a1);
  Person  p2 = new Person("Kate", 22, "Female", this.a2);
  Person  p3 = new Person("Rebecca", 31, "Non-binary", this.a3);
}

/*
Problem 3

A deli menu includes soups, salads, and sandwiches.
Every item has a name and a price (in cents - so we have whole numbers only).

For each soup and salad we note whether it is vegetarian or not.

Salads also specify the name of any dressing being used.

For a sandwich we note the kind of bread,
and two fillings (e.g peanut butter and jelly; or ham and cheese).

Assume that every sandwich will have two fillings, and ignore extras (mayo, mustard, tomatoes, lettuce, etc.)

Define classes to represent each of these kinds of menu items.
Think carefully about what type each field of each class should be.
Do you need to define any interfaces?
Construct at least two examples each of soups, salads, and sandwiches.
*/

// ASSUME: Every sandwich will have two fillings.

interface IDeliMenu{}

class Soups implements IDeliMenu{
  String name;
  int price;
  Boolean vegeterian;

  Soups(String name, int price, Boolean vegeterian){
    this.name=name;
    this.price=price;
    this.vegeterian=vegeterian;
  }
}

class Salads implements IDeliMenu{
  String name;
  int price;
  Boolean vegeterian;
  String dressing;

  Salads(String name, int price, Boolean vegeterian, String dressing){
    this.name=name;
    this.price=price;
    this.vegeterian=vegeterian;
    this.dressing=dressing;
  }
}

class Sandwiches implements IDeliMenu{
  String name;
  int price;
  String kob; // [K]ind [o]f [b]read
  String fls0; // [f]i[l]lings
  String fls1; // [f]i[l]lings
  
  Sandwiches(String name, int price, String kob, String fls0, String fls1){
    this.name=name;
    this.price=price;
    this.kob=kob;
    this.fls0=fls0;
    this.fls1=fls1;
  }
}

class ExamplesDaliMenu{
  ExamplesDaliMenu(){}
  
  IDeliMenu soups1 = new Soups("Soup 1", 10, true);
  IDeliMenu soups2 = new Soups("Soup 2", 20, false);

  IDeliMenu salads1 = new Salads("Salads 1", 10, false, "mayo");
  IDeliMenu salads2 = new Salads("Salads 2", 20, false, "mustards");

  IDeliMenu sandwiches1 = new Sandwiches("Sandwiches 1", 10, "Bread 1", "Fillings 1", "Fillings 2");
  IDeliMenu sandwiches2 = new Sandwiches("Sandwiches 2", 20, "Bread 2", "Fillings 1", "Fillings 2");
}





