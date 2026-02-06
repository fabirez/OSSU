import time

## define two functions
def convert_to_km(m):
    return m * 1.609

def compound(invest, interest, n_months):
    total=0
    for i in range(n_months):
       total = total * interest + invest
    return total

##creates a list [1, 10, 100, ...] to test different input sizes
L_N = [1]
for i in range(8):
    L_N.append(L_N[-1]*10)



###### print time and ops/sec for constant fcn
# for N in L_N:
#     t = time.perf_counter()
#     km = convert_to_km(N)
#     dt = time.perf_counter()-t
#     print (f"convert_to_km({N}) took {dt:.2e} sec ({1/dt:,.2f}/sec)")



## ------------------------------------------------------------
## EXAMPLE: Timing computations with list data structures 
## ------------------------------------------------------------

##### sum of elements in L
def sum_of(L):
    total = 0.0
    for elt in L:
        total = total + elt
    return total

L_N = [1]
for i in range(8):
    L_N.append(L_N[-1]*10)

# for N in L_N:
#     L = list(range(N)) # makes list of length N [0,1,2,3,...N]
#     t = time.perf_counter()
#     s = sum_of(L)
#     dt = time.perf_counter()-t
#     print (f"sum_of length({N}) took {dt:.2e} seconds ({1/dt:,.2f}/sec)")


## ------------------------------------------------------------
## FIND ELEMENT IN A LIST
## ------------------------------------------------------------

## brute force algorithm
def is_in(L, x):
    for elt in L:
        if elt==x: 
            return True
    return False

## bisection search algorithm
def binary_search(L, x):
    """ Returns True if x is in L. L must be sorted """
    lo = 0
    hi = len(L)
    while hi-lo > 1:
        mid = (hi+lo) // 2
        if L[mid] <= x:
            lo = mid
        else:
            hi = mid
    return L[lo] == x

def default_search(L,x):
    return x in L;

L_N = [1]
for i in range(8):
    L_N.append(L_N[-1]*10)

prev_time_brute = None
prev_time_binary = None
prev_time_builtin = None

##### print time and ops/sec for constant fcn
def count_time(f):
    for N in L_N:
        L = list(range(N))
        t = time.perf_counter()
        km = f(L,N)
        dt = time.perf_counter()-t
        print (f"{f.__name__}({N}) took {dt:.2e} sec ({1/dt:,.2f}/sec)")

# count_time(is_in)
# print("---------------------------------------")
# count_time(binary_search)
# print("---------------------------------------")
# count_time(default_search)
# print("---------------------------------------")

## ------------------------------------------------------------
## EXAMPLE: Counting operations
## ------------------------------------------------------------

count=0;

# define functions
def is_in_counter(L, x):
    global count
    count += 1
    for elt in L:
        count += 2 # set elt, if == test
        if elt==x: return True
    return False

def binary_search_counter(L, x):
    """ returns True if x is in L
        L must be sorted """
    global count
    lo = 0
    hi = len(L)
    count += 3 #set lo, hi, len
    while hi-lo > 1:
        count += 2 #while test, -
        mid = (hi+lo) // 2
        count += 3 #+, //, assign mid
        if L[mid] <= x:
            lo = mid
        else:
            hi = mid
        count += 3 #access mid, if test, assign mid
    count += 3 #access lo, == test, return
    return L[lo] == x

########### counting ops for is_in
L_N = [1]
for i in range(8):
    L_N.append(L_N[-1]*10)

is_in_counts = []
for N in L_N:
    count = 0
    L = range(N)
    for x in [L[0], L[len(L)//2], L[-1]]:
        my_bool = is_in_counter(L, x)
    print ('is_in with', N, 'elements did', count, 'operations')
    is_in_counts.append(count)
    
    
print("---------------------------------------")
########### counting ops for binary_search
L_N = [1]
for i in range(10):
    L_N.append(L_N[-1]*10)

bin_search_counts = []
for N in L_N:
    count = 0
    L = range(N)
    for x in [L[0], L[len(L)//2], L[-1]]:
        my_bool = binary_search_counter(L, x)
    print ('binary_search for ', N, 'elements, did', count, 'operations')
    bin_search_counts.append(count)
    
