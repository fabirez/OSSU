class Circle(object):
    def __init__(self,coordinate,radius):
        if(type(coordinate) != Coordinate):
            raise ValueError("As a first argument, we need a Coordinate object")
        if(type(radius) != int):
            raise ValueError("As a second argument, we need an int object")
        self.center = coordinate;
        self.radius = radius;

    def is_inside(self,point):
        """
            Returns true if point is in self, False otherwise
        """
        if(type(point) != Coordinate):
            raise ValueError("As a first argument, we need a Coordinate object")

        return point.distance(self.center) < self.radius;

class Coordinate(object):
    def __init__(self,x,y):
        self.x=x;
        self.y=y;

    def distance(self,other):
        x_diff_sq = (self.x-other.x)**2;
        y_diff_sq = (self.y-other.y)**2;
        return (x_diff_sq+y_diff_sq)**0.5;

    def to_origin(self):
        self.x = 0;
        self.y = 0;

c = Coordinate(3, 4)
o = Coordinate(0, 0)
assert c.distance(o) == 5.0
c.to_origin()
assert c.x == 0 and c.y == 0;


c_0 = Coordinate(2,2)
r_0 = 2 
circle_0 = Circle(c_0,r_0)
assert circle_0.is_inside(Coordinate(10,10)) == False
assert circle_0.is_inside(Coordinate(1 ,1 )) == True



##################################
## Represent a simple fraction
##################################

class SimpleFraction(object):
    def __init__(self,num,den):
        if(type(num) != int or type(den) != int):
            raise ValueError("Need an object of type int")
        self.num = num;
        self.den = den;
    def add(self, other):
        """ Assume: they have the same den """
        return (self.num + other.num)/self.den

    def sub(self, other):
        """ Assume: they have the same den """
        return (self.num - other.num)/self.den

    def times(self, other):
        """ Assume: they have the same den """
        return (self.num * other.num)/(self.den * other.den)


    def invert(self):
        """ Sets self's num to denom and vice versa.
            Returns None. """
        # temp = self.num;
        # self.num = self.den;
        # self.den = temp;
        (self.num, self.den) = (self.den, self.num)

    def get_inverse(self):
        """ Returns a float rapresenting 1/self """
        return self.den/self.num;

    def __str__(self):
        return  "Num:" + str(self.num) + "\nDen:" + str(self.den)

    def __mul__(self, other):
        return (self.num * other.num)/(self.den * other.den);


f_0 = SimpleFraction(3,4);
f_1 = SimpleFraction(1,4);
assert f_0.add(f_1)   == 1.0
assert f_0.sub(f_1)   == 0.5
assert f_0.times(f_1) == 0.1875
assert round(f_0.get_inverse(),2) == 1.33,f_0.get_inverse()
f_0.invert();
assert f_0.num == 4 and f_0.den == 3, [f_0.num, f_0.den]
print(f_0)

print(f_0 * f_1)

#########################
## Represent a fraction 
########################

class Fraction(object):
    """ A number represented as a fraction """
    def __init__(self, num, denom):
        """ num and denom are integers """
        self.num = num
        self.denom = denom
    def __str__(self):
        """ Returns a string representation of self """
        return str(self.num) + "/" + str(self.denom)
    def __mul__(self, other):
        """ Returns a new fraction representing the addition """
        top = self.num*other.num
        bottom = self.denom*other.denom
        return Fraction(top, bottom)
    def __add__(self, other):
        """ Returns a new fraction representing the addition """
        top = self.num*other.denom + self.denom*other.num
        bottom = self.denom*other.denom
        return Fraction(top, bottom)
    def __sub__(self, other):
        """ Returns a new fraction representing the subtraction """
        top = self.num*other.denom - self.denom*other.num
        bottom = self.denom*other.denom
        return Fraction(top, bottom)
    def __truediv__(self, other):
        """ Returns a new fraction representing the subtraction """
        top = self.num*other.denom
        bottom = self.denom*other.num
        return Fraction(top, bottom)
    def __float__(self):
        """ Returns a float value of the fraction """
        return self.num/self.denom
    def reduce(self):
        """ Returns a new fraction the reduced version of self 
            using the greatest common divisor """
        def gcd(n, d):
            while d != 0:
                (d, n) = (n%d, d)
            return n
        if self.denom == 0:
            return None
        elif self.denom == 1:
            return Fraction(self.num,1)
        else:
            greatest_common_divisor = gcd(self.num,self.denom)
            top = int(self.num/greatest_common_divisor)
            bottom = int(self.denom/greatest_common_divisor)
            return Fraction(top, bottom)
    def invert(self):
        """ Returns a new fraction representing 1/self """
        return Fraction(self.denom, self.num)

f0 = Fraction(3,4)
f1 = Fraction(1,4)
f2 = Fraction(5,1)

print(f0)
print(f1)
print(f2)

assert f0 + f1   == 1.0
assert f0 - f1   == 0.5

t0 = f0 * f1
assert type(t0) == Fraction

assert float(f0) == 0.75
assert float(f1) == 0.25
assert float(f2) == 5.0

