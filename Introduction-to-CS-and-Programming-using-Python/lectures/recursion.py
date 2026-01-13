import time
def mult(a,b):
    # Base case
    if(b==1):
        return a; 
    # Recursive step
    else:
        return a+mult(a,b-1);

assert mult(5,4) == 20

#
# ### Base case
# # x = 1; x=2; -> 1
# ### Recursion step
# # fib(x-1) + fib(x+2)
#
# months = 34;
#
# ### Innefficient fibonacci
# # Start slowing down with more n months 
# # E.g with 34 months, we got 11,405,773 recursive call
# start_time = time.time()
# def fib(x):
#     if x == 1 or x == 2:
#         return 1;
#     else:
#         return fib(x-1) + fib(x-2)
#
# fib(months)
#
# end_time = time.time()
# elapsed_time = end_time - start_time
# print(f"fib resolved in: {elapsed_time} seconds")
#
# ### Efficient fibonacci
# # Store with a dict, the computation did in the past.
# # E.g with 34 months, we got 65 recursive calls.
# start_time_1 = time.time()
# def fib_efficient(n,d):
#     if n in d: 
#         return d[n]
#     else:
#         res=fib_efficient(n-1, d)+fib_efficient(n-2, d)
#         d[n] = res
#         return res
#
# d = {1:1, 2:1}
# fib_efficient(months, d)
#
# end_time_1 = time.time()
# elapsed_time_1 = end_time_1 - start_time_1
# print(f"fib_efficient resolved in: {elapsed_time_1} seconds")
#
# start_time = time.time()
# def score_count(x):
#     """
#         Returns all the ways to make a score of x adding 
#         1,2, and/or 3 together. Order doesn't matter.
#     """
#     if(x == 1):
#         return 1;
#     elif(x == 2):
#         return 2;
#     elif(x == 3):
#         return 3;
#     else:
#         return score_count(x-1) + score_count(x-2) + score_count(x-3);
#
# print(score_count(30))
# end_time = time.time()
# print(f"score_count: {end_time - start_time}")
#
#
# start_time__perf = time.time()
# d = {1:1,2:2,3:3}
# def score_count__perf(x,d):
#     """
#         Returns all the ways to make a score of x adding 
#         1,2, and/or 3 together. Order doesn't matter.
#     """
#     if(x in d):
#         return d[x]
#     else:    
#         res = score_count(x-1) + score_count(x-2) + score_count(x-3);
#         d[x] = res;
#         return res
# end_time__perf = time.time()
# score_count__perf(30,d)
# print(f"score_count__perf: {end_time__perf - start_time__perf}")

####################
### Lists
####################

def total_iter(L):
    result = 0;
    for e in L:
        result += e
    return result

test = [30,40,50]
assert total_iter(test) == 120

def total_rec(L,idx):
    res = 0;
    if(idx >= 0):
        res +=  L[idx] + total_rec(L, idx - 1)
    return res;

assert total_rec(test, len(test) - 1) == 120



def total_rec_2(L,idx):
    res = 0;
    if(idx <= len(L) - 1):
        print("---------------------------")
        print(f"res Before= {res} ")
        print(f"L[idx] Before= {L[idx]} ")
        print(f"IDX Before= {idx} ")
        print("---------------------------")
        res +=  L[idx] + total_rec_2(L, idx + 1)
        print("---------------------------")
        print(f"{L[idx]} + {total_rec_2(L, idx + 1)} ")
        print(f"idx After= {idx} ")
        print("---------------------------")
    else:
        print("-[/!\\]-[/!\\]-[/!\\]-[/!\\]")
        print(f"res= {res} ")
        print(f"idx= {idx} ")
        print("-[/!\\]-[/!\\]-[/!\\]-[/!\\]")

    return res;

assert total_rec_2([10,20,30,40,50,60], 0) == 210


def total_rec_3(L):
    if(len(L) == 1):
        return L[0]
    else:
        # a = L[0]
        # return a + total_rec_3(L)
        # del(L[0])
        return L[0] + total_rec_3(L[1:])

print(total_rec_3([10,20,30,40,50,60]))

## Exercise
def total_len_recur(L):
    if len(L) == 1:
        return  len(L[0])
    else: 
        return len(L[0]) + total_len_recur(L[1:])

test = ["ab", "c", "defgh"]
assert total_len_recur(test) == 8

## Exercise 2
def in_list(L,e):
    if len(L) == 1:
        return  L[0] == e
    else:
        return True if L[0] == e else in_list(L[1:], e); 
        # return in_list(L[1:], e)

test = ["ab", "c", "defgh"]
assert in_list(test,"ab") == True
assert in_list(test,"d") == False


## Flattean a list with only one level of Loe(list of elements)

def flatten_rec(L):
    if len(L) == 1:
        return L[0]
    else:
        return L[0] + flatten_rec(L[1:])
    

l_f =[[1],[2,3,4],[5,6,7,8,9]]

assert flatten_rec(l_f) == [1,2,3,4,5,6,7,8,9]

## Exercise
def in_list_of_list_0(L,e):
    """
        L is a list whose eleemnts are list containing list.
        Return True if e is an element within the list of L 
        and False otherwise
    """
    if(len(L) == 0):
        return False;

    new_L = flatten_rec(L)
    if(new_L[0] == e):
       return True
    else:
        if(new_L[0] == e):
            return True
        else:
            return in_list_of_list_0(L[1:], e)


test = [[1,2],[3,4],[5,6,7]]
assert in_list_of_list_0(test,0) == False
assert in_list_of_list_0(test,3) == True

def in_list_of_list_1(L,e):
    """
        L is a list whose eleemnts are list containing list.
        Return True if e is an element within the list of L 
        and False otherwise
    """
    return in_list(flatten_rec(L),e)
        

test = [[1,2],[3,4],[5,6,7]]
assert in_list_of_list_1(test,0) == False
assert in_list_of_list_1(test,3) == True  


def in_list_of_list_2(L,e):
    """
        L is a list whose eleemnts are list containing list.
        Return True if e is an element within the list of L 
        and False otherwise
    """
    # if(e in L[0]):
    #     return True
    if(len(L) == 1):
        return e in L[0]
    else:
        # return False if len(L) == 1 else in_list_of_list_2(L[1:], e); 
         if(e in L[0]):
             return True
         else:
             return in_list_of_list_2(L[1:], e)


test = [[1,2],[3,4],[5,6,7]]
assert in_list_of_list_2(test,0) == False
assert in_list_of_list_2(test,3) == True  

def reverse_list(L):
    if (len(L) == 1):
        if (type(L[0]) == list):
            return [reverse_list(L[0])]
        else:
            return L
    else:
        if (type(L[0]) != list):
            # If we got an element, we can arleady concatenate
            return reverse_list(L[1:]) + [L[0]]
        else:
            # If we got another list, let's just recall the 
                # function again on her.
            return reverse_list(L[1:]) + [reverse_list(L[0])]
        # return  reverse_list(L[1:]) + [L[0]] 

test = [1,2,3,4]
assert reverse_list(test) == [4,3,2,1]
test = [2,4,9,20]
assert reverse_list(test) == [20,9,4,2]
test = [2,[4,9],20]
assert reverse_list(test) == [20,[9,4],2]
test = [[1,2],3,4,[[5,6],[7,8]] ]
assert reverse_list(test) == [[[8, 7], [6, 5]], 4, 3, [2, 1]]

# Smallest problem for resolve reverse_list:
def sub_last(L):
    """
     How can i 
     1. Remove the first element
     2. Append at the end of the *new sublist*
          And, if there is arleady an el in the sublist
            add the nel el, before.
             Eg: [1] -> add 2 -> [2,1] 
    """
    i = 0;
    l = []
    while i <= 1:
        L = L + [L[0]]
        del(L[0])
        i+=1;
    return L


test = [1,2,3,4]
assert sub_last(test) ==  [3,4,1,2]

# Lecture version
def reverse_list_1(L):
    if (len(L) == 1):
        if (type(L[0]) != list):
            return L
        else:
            return [reverse_list_1(L[0])]
    else:
        if (type(L[0]) != list):
            return reverse_list_1(L[1:]) + [L[0]]
        else:
            return reverse_list_1(L[1:]) + [reverse_list_1(L[0])]
    
test = [1,2,3,4]
assert reverse_list_1(test) == [4,3,2,1]
test = [2,4,9,20]
assert reverse_list_1(test) == [20,9,4,2]
test = [2,[4,9],20]
assert reverse_list_1(test) == [20,[9,4],2]
test = [[1,2],3,4,[[5,6],[7,8]]]
assert reverse_list_1(test) == [[[8, 7], [6, 5]], 4, 3, [2, 1]]



# Lecture version cleaned up
def reverse_list_2(L):
    if L == []:
        return []
    elif type(L[0]) != list:
        return reverse_list_2(L[1:]) + [L[0]]
    else:
        return reverse_list_2(L[1:]) + [reverse_list_2(L[0])]

test = [1,2,3,4]
assert reverse_list_2(test) == [4,3,2,1]
test = [2,4,9,20]
assert reverse_list_2(test) == [20,9,4,2]
test = [2,[4,9],20]
assert reverse_list_2(test) == [20,[9,4],2]
test = [[1,2],3,4,[[5,6],[7,8]]]
assert reverse_list_2(test) == [[[8, 7], [6, 5]], 4, 3, [2, 1]]

####################################################################
############## ANSWERS TO AT HOME #######################
####################################################################
# Q1. Memoize the code to find possible scores in basketball
def score_count(x, d):
    if x in d:
        return d[x]
    else:
        score = score_count(x-1, d)+score_count(x-2, d)+score_count(x-3, d) 
        d[x] = score
        return score
    
# d = {1:1, 2:2, 3:3}
# print(score_count(4, d))  # prints 6
# print(score_count(6, d))  # prints 20
# print(score_count(13, d))  # prints 1431

# Q2
def in_list_of_lists_mod(L, e):
    """
    L is a list whose elements are either
        * lists containing ints or
        * ints
    Returns True if e is an element within L or 
    sublists of L and False otherwise. 
    """
    if len(L)==1 and type(L[0])!=list:
        return e==L[0]
    elif len(L)==1 and type(L[0])==list:
        return e in L[0]
    elif type(L[0])!=list:
        if e==L[0]:
            return True
        else:
            return in_list_of_lists_mod(L[1:], e)
    elif type(L[0])==list:
        if e in L[0]:
            return True
        else:
            return in_list_of_lists_mod(L[1:], e)


# test = [[1,2],3,4,5,6,7]
# print(in_list_of_lists_mod(test, 3))  # prints True
# test = [[1,2],[3,4,5],6,7]
# print(in_list_of_lists_mod(test, 3))  # prints True
# test = [[1,2],[3,4,5],6,7]
# print(in_list_of_lists_mod(test, 10))  # prints False

# Q3
def my_deepcopy(L):
    """ 
    Implements a recursive version of copy.deepcopy().
    L is a list, containing lists or list of lists, etc.
    Returns a new list with the same structure as L that 
    contains copies (recursively) of every sublist 
    """
    pass
    if len(L) == 0:
        return []
    elif type(L[0]) != list:
        return [L[0]] + my_deepcopy(L[1:])
    else:
        return [my_deepcopy(L[0])] + my_deepcopy(L[1:])

# myL = ["abc", ['d'], ['e', ['f', 'g']]]
# my_newL = my_deepcopy(myL)
# print(myL)
# print(my_newL)
# myL[2][1][0] = 1
# print(myL)      # should be ['abc', ['d'], ['e', [1, 'g']]]
# print(my_newL)  # should be ['abc', ['d'], ['e', ['f', 'g']]]

# Q4
def f(L):
    """ L is a non-empty list of lowercase letters.
    Returns the letter earliest in the alphabet. """
    if len(L) == 1:
        return L[0]
    else:
        if L[0] < f((L[1:])):
            return L[0]
        else:
            return f(L[1:])
        
# print(f(['z', 'a', 'b', 'c', 'd']))  # should print 'a'


def g(L, e):
    """ L is list of ints, e is an int
    Returns a count of how many times e occurrs in L  """
    if len(L) == 0:
        return 0
    elif len(L) == 1:
        if e == L[0]:
            return 1
        else:
            return 0
    else:
        if L[0] == e:
            return 1+g(L[1:], e)
        else:
            return g(L[1:], e)
    
# print(g([1,2,3,1], 1))     # should print 2
# print(g([1,1,2,3,1,1], 1)) # should print 4
    

def h(L, e):
    """ L is list, e is an int
    Returns a count of how many times e occurrs in L or 
    (recursively) any sublist of L
    """
    if len(L) == 0:
        return 0
    else:
        if type(L[0])==int:
            if L[0] == e:
                return 1+h(L[1:], e)
            else:
                return h(L[1:], e)
        elif type(L[0])== list:
            if e in L[0]:
                return h(L[0], e)+h(L[1:], e)
            else:
                return h(L[1:], e)
    
# print(h([1,2,[3],1], 1))        # should print 2
# print(h([1,2,[3,1,[1,[1]]]], 1))  # should print 4
