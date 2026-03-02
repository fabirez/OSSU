
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
    # your code here
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
     self.myList = [];
 def size(self):
     """
     Returns the length of the container list
     """
     return len(self.myList);
 # Your code here
 def add(self, elem):
     """
     Adds the elem to one end of the container list, keeping the end
     you add to consistent.

     Does not return anything
     """
     self.myList.append(elem)


class Stack(Container):
    """
     A subclass of Container. Has an
    """
    def remove(self):
         """
         The newest element in the container list is removed
         Returns the element removed or None if the queue contains no elements
         """
         currLen = self.size()
         if(currLen > 0):
             last = self.myList[currLen-1];
             self.myList = self.myList[:currLen-1];
             return last;
         else:
            return None;


print("=======================================")
print("Assert for Container started...");
# Tests for the Container class
container = Container()

# Test initial size (should be 0)
assert container.size() == 0, "Test Failed: Container size should be 0 initially."

# Test adding an element
container.add(5)
assert container.size() == 1, "Test Failed: Container size should be 1 after adding one element."
assert container.myList == [5], "Test Failed: Container list should contain the element [5]."

# Test adding multiple elements
container.add(10)
container.add(15)
assert container.size() == 3, "Test Failed: Container size should be 3 after adding three elements."
assert container.myList == [5, 10, 15], "Test Failed: Container list should contain [5, 10, 15]."
print("Assert for Container passed")
print("=======================================\n")

print("=======================================")
print("Assert for Stack started...");
stack = Stack()

assert stack.size() == 0, "Test Failed: Stack size should be 0 initially."

stack.add(5)
assert stack.size() == 1, "Test Failed: Stack size should be 1 after adding one element."
assert stack.myList == [5], "Test Failed: Stack list should contain [5]."

stack.add(10)
stack.add(15)
assert stack.size() == 3, "Test Failed: Stack size should be 3 after adding three elements."
assert stack.myList == [5, 10, 15], "Test Failed: Stack list should contain [5, 10, 15]."

removed = stack.remove()
assert removed == 15, f"Test Failed: Expected removed element to be 15, but got {removed}."
assert stack.size() == 2, "Test Failed: Stack size should be 2 after removing one element."
assert stack.myList == [5, 10], "Test Failed: Stack list should contain [5, 10] after removing the last element."

removed = stack.remove()
assert removed == 10, f"Test Failed: Expected removed element to be 10, but got {removed}."
removed = stack.remove()
assert removed == 5, f"Test Failed: Expected removed element to be 5, but got {removed}."
assert stack.size() == 0, "Test Failed: Stack size should be 0 after removing all elements."
assert stack.myList == [], "Test Failed: Stack list should be empty after removing all elements."
# Test removing from an empty stack (should return None)
removed = stack.remove()
assert removed is None, f"Test Failed: Expected removed element to be None, but got {removed}."
print("Assert for Stack passed");
print("=======================================\n")

###################################
### Finger Exercise Lecture 20
###################################
class Queue(Container):
     """
     A subclass of Container. Has an additional method to remove elements.
     """
     def remove(self):
         """
         The newest element in the container list is removed
         Returns the element removed or None if the queue contains no elements
         """
         currLen = self.size()
         if(currLen > 0):
             last = self.myList[currLen-1];
             self.myList = self.myList[:currLen-1];
             return last;
         else:
            return None;


print("=======================================")
print("Assert for queue started...");
queue = Queue()

assert queue.size() == 0, "Test Failed: Queue size should be 0 initially."

queue.add(5)
assert queue.size() == 1, "Test Failed: Queue size should be 1 after adding one element."
assert queue.myList == [5], "Test Failed: Queue list should contain [5]."

queue.add(10)
queue.add(15)
assert queue.size() == 3, "Test Failed: Queue size should be 3 after adding three elements."
assert queue.myList == [5, 10, 15], "Test Failed: Queue list should contain [5, 10, 15]."

removed = queue.remove()
assert removed == 15, f"Test Failed: Expected removed element to be 15, but got {removed}."
assert queue.size() == 2, "Test Failed: Queue size should be 2 after removing one element."
assert queue.myList == [5, 10], "Test Failed: Queue list should contain [5, 10] after removing the last element."

removed = queue.remove()
assert removed == 10, f"Test Failed: Expected removed element to be 10, but got {removed}."
removed = queue.remove()
assert removed == 5, f"Test Failed: Expected removed element to be 5, but got {removed}."
assert queue.size() == 0, "Test Failed: Queue size should be 0 after removing all elements."
assert queue.myList == [], "Test Failed: Queue list should be empty after removing all elements."
# Test removing from an empty queue (should return None)
removed = queue.remove()
assert removed is None, f"Test Failed: Expected removed element to be None, but got {removed}."
print("Assert for queue passed");
print("=======================================\n")


###################################
### Finger Exercise Lecture 23
###################################

# Question 1: Choose the worst-case asymptotic order of growth (upper and lower bound)
## for the following function. Assume n = a. 

def running_product(a):
    """ a is an int """
    product = 1
    for i in range(5,a+5):
        product *= i
        if product == a:
            return True
    return False

# Answer 1: Theta(n)

# Question 2: Choose the worst-case asymptotic order of growth (upper and lower bound)
## for the following function. Assume n = len(L).

def tricky_f(L, L2):
    """ L and L2 are lists of equal length """
    inL = False
    for e1 in L:
        if e1 in L2:
            inL = True
    inL2 = False
    for e2 in L2:
        if e2 in L:
            inL2 = True
    return inL and inL2

# Answer 2: Theta(n^2)

# Question 3: Choose the worst-case asymptotic order of growth (upper and lower bound)
## for the following function.

def sum_f(n):
    """ n > 0 """
    answer = 0
    while n > 0:
        answer += n%10
        n = int(n/10)
    return answer

# Answer 3: Theta(log n)
