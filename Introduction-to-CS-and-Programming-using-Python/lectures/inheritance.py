class Animal(object): 
    def __init__(self,age):
        self.age = age;
        self.name = None;
    def __str__(self):
        return "animal:"+str(self.name)+":"+str(self.age)

    def get_age(self):
        return self.age;
    def get_name(self):
        return self.name;

    def set_age(self,age):
        self.age = age
    def set_name(self,name):
        self.name = name;


a = Animal(4)
# print(a)
b = Animal(6)
# print(b)
### EXAMPLES: using Animal objects in code

def animal_dict(L):
    """ L is a list
    Returns a dict, d, mappping an int to an Animal object. 
    A key in d is all non-negative ints, n, in L. A value 
    corresponding to a key is an Animal object with n as its age. """
    d = {}
    for n in L:
        if type(n) == int and n >= 0:
            d[n] = Animal(n)
    return d

L = [2,5,'a',-5,0]
animals = animal_dict(L)
print(animals)



def make_animals(L1, L2):
    """ L1 is a list if ints and L2 is a list of str
        L1 and L2 have the same length
    Creates a list of Animals the same length as L1 and L2.
    An animal object at index i has the age and name
    corresponding to the same index in L1 and L2, respectively. """
    L = [];
    for idx in range(len(L1)):
        age=L1[idx];
        name=L2[idx];
        a = Animal(age)
        a.set_name(name)
        L.append(a);
    return L;


L1 = [2,5,1]
L2 = ["blobfish", "crazyant", "parafox"]
animals = make_animals(L1, L2)
print(animals)     # note this prints a list of animal objects
for i in animals:  # this prints the indivdual animals
    print(i)


class Cat(Animal):
    def speak(self):
        print("meow")
        return "meow"
    def __str__(self):
        return "cat:"+str(self.name)+":"+str(self.age)

c = Cat(5)
c.set_name("fluffy")
assert c.get_name() == "fluffy"
assert c.speak() == "meow"
assert c.get_age() == 5
try:
    a.speak()
except Exception as AttributeError:
    print("Error because there is no speak method for Animal class")


class Person(Animal):
    def __init__(self, name, age):
        Animal.__init__(self, age)
        self.set_name(name)
        self.friends = []
    def get_friends(self):
        return self.friends.copy()
    def speak(self):
        print("hello")
    def add_friend(self, fname):
        if fname not in self.friends:
            self.friends.append(fname)
    def age_diff(self, other):
        diff = self.age - other.age
        print(abs(diff), "year difference")
    def __str__(self):
        return "person:"+str(self.name)+":"+str(self.age)

#print("\n---- person tests ----")
p1 = Person("jack", 30)
p2 = Person("jill", 25)
assert p1.get_name() == "jack";
assert (p1.get_age())
# print(p2.get_name())
# print(p2.get_age())
# print(p1)
# p1.speak()
# p1.age_diff(p2)
# p1.add_friend('ana')
# p1.add_friend('bob')
# p1.add_friend('bob')
# print(p1.get_friends())

# Write the function according to this spec
def make_pets(d):
    """ d is a dict mapping a Person obj to a Cat obj
    Prints, on each line, the name of a person, 
    a colon, and the name of that person's cat """

    for p,c in d.items():
        person_name = p.get_name();
        cat_name = c.get_name();
        print(person_name + ":" + cat_name);




p1 = Person("ana", 86)
p2 = Person("james", 7)
c1 = Cat(1)
c1.set_name("furball")
c2 = Cat(1)
c2.set_name("fluffsphere")

d = {p1:c1, p2:c2}
make_pets(d)

import random;

class Student(Person):
    def __init__(self,name,age,topic=None):
        Person.__init__(self,name,age);
        self.topic = topic;
    def __str__(self):
        return "student:"+str(self.name)+":"+str(self.age)+":"+str(self.major)

    def set_topic(self,topic):
        self.topic = topic;
    def get_topic(self):
        return self.topic;

    def speak(self):
        r = random.random()
        if(r <= 0.25):
            print("I\'m studing")
        elif(r > 0.25 and r <= 0.5):
            print("I should eat")
        elif(r > 0.5 and r <= 0.75):
            print("I\'m zooming")
        else:
            print("I\'m out")

s1 = Student('alice', 20, "CS")
s2 = Student('beth', 18)
assert s1.get_name() == 'alice';
s1.speak();
assert s2.get_name() == 'beth';
s2.speak();




class Rabbit(Animal):
    tag=0;
    def __init__(self, age, parent1=None, parent2=None):
        Animal.__init__(self, age)
        self.p1=parent1;
        self.p2=parent2;
        self.rid=Rabbit.tag;
        Rabbit.tag+=1;

    def get_parent1(self):
        return self.p1;
    def get_parent2(self):
        return self.p2;

    def get_rid(self):
        return str(self.rid).zfill(5);

    def set_parent1(self,p):
        self.p1=p;
    def set_parent2(self,p):
        self.p2=p;

    def __add__(self,p2):
        # returning object of same type as this class
        return Rabbit(0,self,p2);
    def __eq__(self, oth):
        # compare the ids of self and other's parents
        # don't care about the order of the parents
        parents_same = (self.p1.rid == oth.p1.rid and self.p2.rid == oth.p2.rid)
        parents_opp = (self.p2.rid == oth.p1.rid and self.p1.rid == oth.p2.rid)
        return parents_same or parents_opp


r1 = Rabbit(3)
r2 = Rabbit(4)
r3 = Rabbit(5)
print("r1:", r1)
print("r2:", r2)
print("r3:", r3)
assert r1.get_parent1() == None
assert r2.get_parent2() == None
assert r1.get_rid() == "00000"
assert r2.get_rid() == "00001"
assert r3.get_rid() == "00002"

r4 = r1 + r2;
assert type(r4) == Rabbit;

r5 = r3+r4
r6 = r4+r3

print(r5 == r6)
print(r4 == r6)

##################################################
# Exercise
##################################################

# Write the class Employee as a subclass of Person
class Employee(Person):
    """ An Employee contains an extra data attribute, salary as an int """
    def __init__(self, name, age, salary=0):
        """ initializes self as a Person with a salary data attribute, initially 0 """
        Person.__init__(self,name,age);
        self.salary = salary;
        self.salaries = [0]
    def get_salary(self):
        """ returns self's salary """
        return self.salary;
    def set_salary(self, s):
        """ s is an int
        Sets self's salary data attribute to s """
        self.salary = s;
        self.salaries.append(self.salary)
    def salary_change(self, n):
        """ n is an int (positive or negative)
        Adds n to self's salary. If the result is negative, sets 
        self's salary to 0. Otherwise sets self's salary to the new value. """
        self.salary+=n;
        if(self.salary < 0):
            self.salary=0;
        self.salaries.append(self.salary)
    def has_friends(self):
        """ Returns True if self's friend list is empty and False otherwise """
        return len(self.friends) != 0;
    def past_salaries_list(self):
        """ Keeps track of all salaries self has had in the order they've changed. 
        i.e. whenever the salary changes, keep track of it.
        Hint: you may need to add an additional data attribute to Employee.
        Returns a copy of the list of all salaries self has had, in order. """
        return self.salaries.copy();
    def past_salary_freq(self):
        """ Returns a dictionary where the key is a salary number and the 
        value is how many times self's salary has changed to that number. """
        D = {}
        for el in self.salaries:
            if (el in D):
                D[el]+=1;
            else:
                D[el]=1;
        return D;

# For example:
print("##################################################")
print("# Employee teest")
print("##################################################")
e = Employee("ana", 35)
assert e.get_salary() == 0;
e.set_salary(1000)
assert e.get_salary() == 1000;
e.salary_change(2000)
assert e.get_salary() == 3000;
e.salary_change(-50000)
assert e.get_salary() == 0;
assert e.has_friends() == False;
e.add_friend("bob")
assert e.has_friends() == True 
assert e.past_salaries_list() == [0,1000,3000,0] 
assert e.past_salary_freq() == {0: 2, 1000: 1, 3000: 1}
print("Passed")
# Write a function that meets this specification
def counts(L):
    """ L is a list of Employee and Person objects 
    Returns a tuple of a count of:
      * how many Person objects are in L
      * how many Employee objects are in L 
      * the number of unique names among Employee and Person objects """
    cPerson=0;
    cEmployee=0;
    D={}
    for el in L:
        if(type(el) == Person):
            cPerson+=1;
        else:
            cEmployee+=1;
        D[el.get_name()]=0;

    uNames = len(D.keys())
    return (cPerson, cEmployee, uNames)

print("##################################################")
print("# Counts function test")
print("##################################################")
# make employees and people
L = []
L.append(Person('ana',8))
L.append(Person('bob',25))
L.append(Employee('ana',35))
L.append(Employee('cara',18))
L.append(Employee('dan',53))
assert counts(L) == (2,3,4)
print("Passed")
