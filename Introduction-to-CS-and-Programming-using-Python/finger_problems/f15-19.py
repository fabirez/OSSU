
###################################
### Finger Exercise Lecture 15
###################################
def recur_power(base, exp):
    """
    Hint: Base case is when exp = 0. Otherwise, in the recursive
    case you return base * base^(exp-1).

    @params {int | float} base
    @params {int} exp

    @returns {int} 
    Returns base to the power of exp using recursion.
    """
    if(exp <= 1):
        return base;
    else:
        return recur_power(base,exp-1) * base

assert recur_power(2,6) == 64
assert recur_power(2,5) == 32
assert recur_power(2,4) == 16
assert recur_power(2,3) == 8
assert recur_power(2,2) == 4
assert recur_power(2,1) == 2

###################################
### Finger Exercise Lecture 16
###################################
def flatten(L):
    """ 
    L: a list 
    Returns a copy of L, which is a flattened version of L 
    """
    if(len(L) == 0):
        return L;
    if(type(L[0]) == list):     
        return flatten(L[0]) + flatten(L[1:]);
    else:
        return [L[0]] + flatten(L[1:])

# What i started from :
# L = [1,2,3] v* 
# L = [1,[2],3]
# L = [[1,[2],3], [4,5,6]]
# Examples:
L = [[1,4,[6],2],[[[3]],2],4,5]
assert flatten(L) == [1,4,6,2,3,2,4,5]

###################################
### Finger Exercise Lecture 17
###################################
class Circle():
     def __init__(self, radius):
         """ Initializes self with radius """
         self.radius = radius;
     def get_radius(self):
         """ Returns the radius of self """
         return self.radius
     def set_radius(self, radius):
         """ radius is a number
         Changes the radius of self to radius """
         self.radius = radius
     def get_area(self):
         """ Returns the area of self using pi = 3.14 """
         return self.radius * 3.14
     def equal(self, c):
         """ c is a Circle object
         Returns True if self and c have the same radius value """
         return self.radius == c.radius
     def bigger(self, c):
         """ c is a Circle object
         Returns self or c, the Circle object with the bigger radius """
         return self if self.radius > c.radius else c;

###################################
### Finger Exercise Lecture 18
###################################
class Circle1():
 def __init__(self, radius):
    """ Initializes self with radius """
    self.radius = radius;
 def get_radius(self):
    """ Returns the radius of self """
    return self.radius;
     # your code here
 def __add__(self, c):
    """ c is a Circle object
    Returns a new Circle object whose radius is
    the sum of self and c's radius """
    return Circle(self.radius + c.radius)
 def __str__(self):
    """ A Circle's string representation is the radius """
    return str(self.radius)

###################################
### Finger Exercise Lecture 19
###################################
class Container(object):
     """
     A container object is a list and can store elements of any type
     """
     def __init__(self):
         """
         Initializes an empty list
         """
         self.myList = []

     def size(self):
         """
         Returns the length of the container list
         """
         return len(self.myList)

     def add(self, elem):
         """
         Adds the elem to one end of the container list, keeping the end
         you add to consistent. Does not return anything
         """
         self.myList.append(elem)

     def get_list(self):
         """
         Returns the current list
         """
         return self.myList

print("#######################################")
print("# Assert Container")
print("#######################################")
c = Container()
assert c.size() == 0;
c.add(1)
c.add(2)
c.add(3)
assert c.size() == 3;
assert c.get_list() == [1,2,3];
print("v Asset Passed")


class Stack(Container):
     """
     A subclass of Container. Has an additional method to remove elements.
     """
     def remove(self):
         """
         The newest element in the container list is removed
         Returns the element removed or None if the queue contains no elements
         """
         if len(self.myList) == 0:
            return None
         a = self.myList[0]
         self.myList=self.myList[1:]
         return a



print("#######################################")
print("# Assert Stack")
print("#######################################")

s = Stack()
assert s.size() == 0;
s.add(1)
assert s.size() == 1;
s.add(2)
assert s.size() == 2;
s.add(3)
assert s.size() == 3;
s.remove()
assert s.get_list() == [2,3]
assert s.size() == 2;

print("v Asset Passed")
