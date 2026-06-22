/*
Problem 1: Our Canine Friends
We are designing the data collection for the American Kennel Club.
For each dog we need to collect the following information:

name: to be represented as a String

breed: of dog to be represented as a String

yob: the year of birth given as a four digit number to be represented as an int

state: of residence – given as the standard two letter abbreviation to be represented as a String

hypoallergenic: a boolean value, true, if the dog is hypoallergenic, or false if not

Design the class Dog that represents the information about each dog for the census.

Make at least three examples of instances of this class, in the class ExamplesDog.
Two of the examples should be objects named 
huffle and pearl and should represent the following two dogs:

Hufflepuff, a Wheaten Terrier, born in 2012, resides in TX, and is hypoallergenic

Pearl, a Labrador Retriever, born in 2016, resides in MA, and is not hypoallerge
*/



class Dog {
  String name;
  String breed;
  int yob;
  String state;
  Boolean hypoallergenic;

  Dog(String name, String breed, int yob, String state, Boolean hypoallergenic){
    this.name = name;
    this.breed = breed;
    this.yob = yob;
    this.state = state;
    this.hypoallergenic = hypoallergenic;
  }
}


class ExamplesDog{
  ExamplesDog(){}

  Dog hpf = new Dog("Hufflepuff",    "Wheaten Terrier", 2021, "TX",  true);
  Dog prl = new Dog(     "Pearl", "Labrador Retriever", 2016, "MA", false);
}
