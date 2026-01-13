
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
