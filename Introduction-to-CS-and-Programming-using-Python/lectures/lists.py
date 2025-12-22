L1 = ['re']
L2 = ['mi']
L3 = ['do']
L4 = L1 + L2 
L3.append(L4) 
L = L1.append(L3) 

print("Starting assert....");
assert L4 == ['re','mi'], "Something went wrong"
print("Assert 1 ");
assert L3 == ['do',['re', 'mi']], "Something went wrong"
print("Assert 2 ");
assert L  == None, "Something went wrong"
print("Assert 3 ");

def make_ordered_list(n):
    """ 
        @param {int}
            n is a positive int
        @returns {list} 
            containing all ints in order from 0 to n (inclusive)
    """
    L = []
    for i in range(n+1):
        L.append(i)
    return L;


assert make_ordered_list(10) == [0,1,2,3,4,5,6,7,8,9,10], "Something went wrong"
print("Assert 4 ");
assert make_ordered_list(5) == [0,1,2,3,4,5], "Something went wrong"
print("Assert 5 ");
assert make_ordered_list(2) == [0,1,2], "Something went wrong"
print("Assert 6 ");
             

def remove_elem(L,e):
    """ 
    @param {list} L 
    @param {any}  e
    @returns {list}
        A new list with elements in the same order as L
        but without any eleemnts equalt to e
    """
    newL = []
    for i in range(len(L)):
        if(L[i] != e):
           newL.append(L[i]);
    return newL;

assert remove_elem([1,2,3],1) == [2,3],"Instead we go this " + str(remove_elem([1,2,3],1))
print("Assert 7 ");
assert remove_elem([1,2,3],2) == [1,3], "Something went wrong"
print("Assert 8 ");
assert remove_elem([1,2,3],3) == [1,2], "Something went wrong"
print("Assert 9 ");
assert remove_elem([1,2,2,2],2) == [1], "Something went wrong"
print("Assert 10 ");
assert remove_elem([1,2,2,2],1) == [2,2,2], "Something went wrong"
print("Assert 11 ");



def count_words(sen):
    """ 
    @param {string} sen
        rapresenting a sentence 
    @returns {int}
        how many words are in s.
        A word is a sequence of char between spaces
    """
    return len(sen.split(" "));

assert count_words("Hello it's me") == 3, "Instead we go this " + str(count_words("Hello it's me"))
print("Assert 12 ");
assert count_words("Hello") == 1, "Instead we go this " + str(count_words("Hello"))
print("Assert 13 ");
assert count_words("Hello it's") == 2, "Instead we go this " + str(count_words("Hello it's"))
print("Assert 14 ");



def sort_words(sen):
    """ 
    @param {string} sen
        rapresenting a sentence 
    @returns {list}
        A list containing all the words in sen but
        sorted in aplhabetical order.
    """
    return sorted(sen.split(" "));


assert sort_words("look at this photo") == ['at', 'look', 'photo', 'this'], "Instead we go this " + str(sort_words("look at this photo"))
print("Assert 13 ");
assert sort_words("look this photo") == ['look', 'photo', 'this'], "Instead we go this " + str(sort_words("look this photo"))
print("Assert 14 ");


def square_list(L):
    for i in range(len(L)):
         L[i] = L[i]**2

L1 = [1,2,3]

assert L1 == [1,2,3], "Instead we go this " + str(L1)
print("Assert 15 ");
print("Mutating L1...")
square_list(L1)
assert L1 == [1,4,9], "Instead we go this " + str(L1)
print("Assert 16 ");
print("Mutating-Clear L1...")
L1.clear()
assert L1 == [], "Instead we go this " + str(L1)
print("Assert 17 ");
