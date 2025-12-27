###################################
### Finger Exercise Lecture 6
###################################
# Assume you are given an integer 0 <= N <= 1000
# Write a piece of Python code that uses bisection search to guess N.
# The code prints two lines: count: with how many guesses it took to find N,
# and answer: with the value of N.
# Hints: If the halfway value is exactly in between two integers, choose the smaller one.
import math

min = 0;
max = 1000;
middle = (min+max) //2
N = 20;

while middle != N:
    if N > middle:
        min = middle;
    else:
        max = middle;
    middle = math.ceil((min+max) //2);

print("Starting assert...")
assert middle == N, "instead we got this:" + str(N);
print("Assert 1:done")

 
###################################
### Finger Exercise Lecture 7
###################################
def eval_quadratic(a, b, c, x):
    """
    @params {int} a
    @params {int} b
    @params {int} c
         a,b,c numerical values for the coefficients of a quadratic equation
    @params {int} x:
        numerical value at which to evaluate the quadratic.
    @retrun {float} Returns the value of the quadratic a×x² + b×x + c.
    """
    return (a*(x**2) + b*x + c)

# Examples:    
assert eval_quadratic(1,1,1,1) == 3, "instead we got this:" + str(eval_quadratic(1,1,1,1));
print("Assert 2:done")
assert eval_quadratic(1,1,1,2) == 7, "instead we got this:" + str(eval_quadratic(1,1,1,1));
print("Assert 3:done")
assert eval_quadratic(10,2,28,2) == 72, "instead we got this:" + str(eval_quadratic(10,2,28,2));
print("Assert 4:done")



def two_quadratics(a1, b1, c1, x1, a2, b2, c2, x2):
    """
    a1, b1, c1: one set of coefficients of a quadratic equation
    a2, b2, c2: another set of coefficients of a quadratic equation
    x1, x2: values at which to evaluate the quadratics
    Evaluates one quadratic with coefficients a1, b1, c1, at x1.
    Evaluates another quadratic with coefficients a2, b2, c2, at x2.
    Prints the sum of the two evaluations. Does not return anything.
    """
    return eval_quadratic(a1,b1,c1,x1) + eval_quadratic(a2,b2,c2,x2)



# Examples:    
assert two_quadratics(1,1,1,2,1,1,1,1) == 10, "instead we got this:" + str( two_quadratics(1,1,1,2,1,1,1,1));
print("Assert 5:done")
assert two_quadratics(1,1,1,2,10,2,28,2) == 79, "instead we got this:" + str(two_quadratics(1,1,1,2, 10,2,28,2));
print("Assert 6:done")
assert two_quadratics(1,1,1,1,1,1,1,1) == 6, "instead we got this:" + str(eval_quadratic(1, 1, 1, 1, 1, 1, 1, 1));
print("Assert 7:done")


###################################
### Finger Exercise Lecture 8
###################################
def same_chars(s1, s2):
    """
    @param {string} s1
    @param {string} s2
    @returns {boolean}
        True is a character in s1 is also in s2, and vice versa.
        If a character only exists in one of s1 or s2, returns False.
    """
    for el in s1:
        if el not in s2:
            return False;

    for el in s2:
        if el not in s1:
            return False;

    return True;

# Examples:
assert same_chars("abc", "cab")     == True,  "Instead we got this " + str(same_chars("abc", "cab"))
print("Assert 8:done")
assert same_chars("abccc", "caaab") == True,  "Instead we got this " + str(same_chars("abc", "cab"))
print("Assert 9:done")
assert same_chars("abcd", "cabaa")  == False, "Instead we got this " + str(same_chars("abc", "cab"))
print("Assert 10:done")
assert same_chars("abcabc", "cabz") == False, "Instead we got this " + str(same_chars("abc", "cab"))
print("Assert 11:done")



###################################
### Finger Exercise Lecture 9
###################################
def dot_product(tA, tB):
    """
    Assumes tA and tB are the same length.

    @param {tuple} tA 
        a tuple of numbers
    @param {tuple} tB
        a tuple of numbers of the same length as tA
    @return {tuple}
        a tuple where the:
            * first element is the length of one of the tuples
            * second element is the sum of the pairwise products of tA and tB
    """
    res = 0;
    for i in range(len(tA)):
        res += tA[i] * tB[i];
    return (len(tA), res)

# Examples:
tA = (1, 2, 3)
#     *  *  *
tB = (4, 5, 6)   

tC = (1, 1, 1)
#     *  *  *
tD = (1, 1, 1)   

tE = (10, 22, 13)
#     *   *   *
tF = (14, 25, 96)   

assert dot_product(tA, tB) == (3,32), "Instead we got this " + str(dot_product(tA, tB))
print("Assert 12:done")
assert dot_product(tC, tD) == (3,3), "Instead we got this " + str(dot_product(tC, tD))
print("Assert 13:done")
assert dot_product(tE, tF) == (3,1938), "Instead we got this " + str(dot_product(tE, tF))
print("Assert 14:done")

###################################
### Finger Exercise Lecture 10
###################################
def all_true(n, Lf):
    """ 
    @param {int} n 
    @param {list} Lf 
        is a list of functions that take in an int and return a Boolean
    @returns {boolean} 
        True if each and every function in Lf returns True when called with n as a parameter.
        Otherwise returns False. 
    """
    
    for el in Lf:
        if(el(n) == False):
            return False;
    return True;

# Examples:    

a = 2;
assert all_true(a, [lambda n:n%2==0, lambda n:n > 1, lambda n:n < 100]) == True, "Instead we got this " + all_true(a, [lambda n:n%2==0, lambda n:n > 1, lambda n:n < 100]) 
print("Assert 15:done")
a = 3;
assert all_true(a, [lambda n:n%2==0, lambda n:n > 1, lambda n:n < 100]) == False, "Instead we got this " + all_true(a, [lambda n:n%2==0, lambda n:n > 1, lambda n:n < 100]) 
print("Assert 16:done")
a = 101;
assert all_true(a, [lambda n:n%2==0, lambda n:n > 1, lambda n:n < 100]) == False, "Instead we got this " + all_true(a, [lambda n:n%2==0, lambda n:n > 1, lambda n:n < 100]) 
print("Assert 17:done")
a = 16;
assert all_true(a, [lambda n:n%2==0, lambda n:n > 1, lambda n:n < 100]) == True, "Instead we got this " + all_true(a, [lambda n:n%2==0, lambda n:n > 1, lambda n:n < 100]) 
print("Assert 18:done")
a = 32;
assert all_true(a, [lambda n:n%2==0, lambda n:n > 1, lambda n:n < 100]) == True, "Instead we got this " + all_true(a, [lambda n:n%2==0, lambda n:n > 1, lambda n:n < 100]) 
print("Assert 19:done")


###################################
### Finger Exercise Lecture 11
###################################
def remove_and_sort(Lin, k):
    """ 
    Mutates Lin to remove the first k elements in Lin and 
    then sorts the remaining elements in ascending order.
    If you run out of items to remove, Lin is mutated to an empty list.

    @param {list} Lin
        is a list of ints
    @param {int} k 
        is an int >= 0
    @return {None}
    """
    flag = True;    
    for el in Lin:
        if(k == el):
            flag = False;
            L.remove(el) 

    if(flag):
        Lin.clear();
    else:
        Lin.sort();


# Examples:
L = [1,6,3]
k = 1
remove_and_sort(L, k)

assert L == [3,6], "Instead we got this " + str(L)
print("Assert 20:done")
L = [1,6,3]
k = 3
remove_and_sort(L, k)
assert L == [1,6], "Instead we got this " + str(L)
print("Assert 21:done")
L = [1,6,3]
k = 6
remove_and_sort(L, k)
assert L == [1,3], "Instead we got this " + str(L)
print("Assert 22:done")
L = [1,6,3]
k = 10
remove_and_sort(L, k)
assert L == [], "Instead we got this " + str(L)
print("Assert 23:done")
 
###################################
### Finger Exercise Lecture 12
###################################
def count_sqrts(nums_list):
    """
    @Assumes that nums_list only contains positive numbers and that there are no duplicates.

    @params {list} nums_list
    @returns {int} 
        how many elements in nums_list are 
        exact squares of elements in the same list, including itself.
    """
    res = 0;
    for el in nums_list:
        if((el**2) in nums_list):
            res += 1;
    return res;

# Examples:    
assert count_sqrts([3,4,2,1,9,25]) == 3, "Instead we got this " + str(count_sqrts([3,4,2,1,9,25]))
print("Assert 24:done")
assert count_sqrts([4,16,2,256,65536]) == 4, "Instead we got this " + str(count_sqrts([3,4,2,1,9,25]))
print("Assert 25:done")
assert count_sqrts([0,0,0]) == 3, "Instead we got this " + str(count_sqrts([0,0,0]))
print("Assert 26:done")

###################################
### Finger Exercise Lecture 13
###################################
def sum_str_lengths(L):
    """
    @params {list} L 
        Assume is a non-empty list containing either: 
             string elements or 
             a non-empty sublist of string elements
    @returns {int}
        the sum of the length of all strings in L and 
        lengths of strings in the sublists of L.
        If L contains an element that is not a string or a list, or L's sublists 
        contain an element that is not a string, raise a ValueError.
    """
    res = len(L[0]);
    for item in L[1]:
        try:
            res += len(item)
        except ValueError:
            print("You cannot have something different than a string")
    return res;

        

assert sum_str_lengths(["abcd", ["e", "fg"]]) == 7, "Instead we got this " + str(sum_str_lengths(["abcd", ["e", "fg"]]))
print("Assert 27:done")
assert sum_str_lengths(["abcd", ["e", "fgh1i"]]) == 10, "Instead we got this " + str(sum_str_lengths(["abcd", ["e", "fgh1i"]]))
print("Assert 28:done")
# print(sum_str_lengths([12, ["e", "fg"]]))      # raises ValueError
# print(sum_str_lengths(["abcd", [3, "fg"]]))    # raises ValueError


def keys_with_value(aDict, target):
    """
    @assume that keys and values in aDict are integers or strings.

    @params {dict} aDict 
    @params {int|str} target
    @returns  {list}
        a sorted list of the keys in aDict with the value target.
        If aDict does not contain the value target, returns an empty list.
    """
    L = []
    for k,v in aDict.items():
        if v == target:
            L.append(k)
    return L;

assert keys_with_value({1:2, 2:4, 5:2}, 2) == [1,5] 
print("Assert 29:done")
assert keys_with_value({1:4, 2:4, 5:4}, 4) == [1,2,5] 
print("Assert 30:done")


def all_positive(d):
    """
    Suppose an element in d is a key k mapping to value v (a non-empty list).

    @parmas {dict} d
        is a dictionary that maps int:list
    @returns {list} 
        the sorted list of all k whose v elements sums up to a 
        positive value.
    """
    L = []
    for k,v in d.items():
        if sum(v) >= 0:
            L.append(k)
    return sorted(L)

assert all_positive({5:[2,-4], 2:[1,2,3], 1:[2]}) == [1,2]
print("Assert 31:done")
assert all_positive({5:[2,4], 2:[1,2,3], 1:[2]}) == [1,2,5]
print("Assert 32:done")
assert all_positive({10:[2,4], 0:[0], 1:[1,-2]}) == [0,10]
print("Assert 33:done")

