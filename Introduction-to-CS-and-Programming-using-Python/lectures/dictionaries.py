import math
def find_grades(grades,students):
    """
        @params {dict} grades
            is a dict mapping student names (str) to grades (str)
        @params {dict} students 
            is a list of student names
        @returns {list}
            containing the grades for students (in same order)
    """
    only_grades = [] 
    for student in students:
        only_grades.append(grades[student])
    return only_grades;


d = {'Ana': 'B', 'Matt': 'C', 'John':'B','Katy':'A'}

print("Starting assert...")
assert find_grades(d, ['Matt', 'Katy']) == ['C', 'A']
print("Assert 1:done")
assert find_grades(d, ['Ana', 'John'])  == ['B', 'B']
print("Assert 2:done")



def find_in_L(Ld,k):
    """
        @params {list} Ld 
            is a list of dicts
        @params {int}  k
            is an int
        @returns {Boolean}
            True if k is a key in any dicts of Ld,
            False otherwise
    """
    for d in Ld:
        if(k in d): 
            return True
    return False;

d1 = {1:2,3:4,5:6}
d2 = {2:4, 4:6}
d3 = {1:1, 3:9, 4:16, 5:25}

assert find_in_L([d1,d2,d3], 2)  == True
print("Assert 3:done")
assert find_in_L([d1,d2,d3], 25) == False
print("Assert 4:done")
assert find_in_L([d1,d2,d3], 1) == True
print("Assert 5:done")
assert find_in_L([d1,d2,d3], 3) == True
print("Assert 6:done")



def count_matches(d):
    """
        @params {dict} d 
            is a dict
        @returns {int}
            how many entries in d, have the key equal to its value
    """
    counter = 0;
    for k,v in d.items():
        if(k == v): 
            counter += 1; 
    return counter;

d4 = {1:2,3:4,5:6}
d5 = {1:2,'a':'a',5:5}

assert count_matches(d4) == 0 
print("Assert 7:done")
assert count_matches(d5) == 2
print("Assert 8:done") 



def get_average(data,what):
    """
        @params {dict} data
            rapresent the students with different grades on different topics
        @params {string} what
            rapresent the topic
        @return {float}
            the average of the choosed topic
    """
    all_data = []
    for stud in data.keys():
        all_data = all_data + data[stud][what]
    return math.floor(sum(all_data)/len(all_data) * 10) / 10


my_d = {
    'eric': {'ps': [8,4,5],  'mq': [6,7]},
    'ana' : {'ps': [8,4,5],  'mq': [6,7]},
    'john': {'ps': [8,4,5],  'mq': [6,7]}
}


assert get_average(my_d, 'ps') == 5.6
print("Assert 9:done")
assert get_average(my_d, 'mq') == 6.5
print("Assert 10:done") 


def find_frequent_word(word_dict):
    """
    @params {dict} word_dict 
        A dictionary where keys are words, and values are counts.
    @returns {tuple}
        A tuple containing a list of words with the highest frequency and 
                     the frequency count.
    """
    word = []  
    highest = max(word_dict.values())  
    for k, v in word_dict.items():
        if v == highest:
            word.append(k) 
    return (word, highest)  

    
def occurs_often(word_dict, x):
    """
    @params {dict} word_dict
        A dictionary where keys are words, and values are counts.
    @params {int} x 
        The frequency threshold. Words with a frequency greater than `x` will be considered.
    @returns {list}
        A list of tuples where each tuple contains a list of words 
        with the highest frequency and the frequency value.
    """
    freq_list = []  
    word_freq_tuple = find_frequent_word(word_dict)
    
    # Continue until no words have a frequency greater than x
    while word_freq_tuple[1] > x:
        freq_list.append(word_freq_tuple)
        
        for word in word_freq_tuple[0]:
            # Mutate the dict
            # For each word with the highest frequency, remove it from the dictionary

            # e.g., if the first word is repeated 3 times, we remove it,
            # making the next iteration consider the words that are repeated 2 times 
            # and still have a frequency greater than x.
            del(word_dict[word])  
        
        # Recalculate the most frequent word 
        word_freq_tuple = find_frequent_word(word_dict)
    
    return freq_list  

word_dict = {'rah':2, 'ah':3, 'rom':1, 'mah':3, 'ro':1}
assert occurs_often(word_dict, 2) == [(['ah', 'mah'], 3)]
print("Assert 11:done") 
assert occurs_often(word_dict, 1) == [(['rah'], 2)]
print("Assert 12:done") 
assert occurs_often(word_dict, 3) == []
print("Assert 13:done") 

