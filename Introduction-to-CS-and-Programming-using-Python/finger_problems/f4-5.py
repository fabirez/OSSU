###################################
### Finger Exercise Lecture 4
###################################
# Assume you are given a positive integer variable named N.
# Write a piece of Python code that finds the cube root of N.
# The code prints the cube root if N is a perfect cube or it prints error 
# if N is not a perfect cube. 
# Hint: use a loop that increments a counter you decide when the counter should stop.

N = 13;
guess= 1;
flag= False;

while (guess < (N**3)):
    guess += 1;
    if(guess == (N**3)):
        flag= True

if(flag):
    print(guess)
else:
    print("error")


###################################
### Finger Exercise Lecture 5
###################################
# Assume you are given a string variable named my_str.
# Write a piece of Python code that prints out 
# a new string containing the even indexed characters of my_str.
# For example, if my_str = "abcdefg" then your code should print out aceg.

my_str = "abcdefg"
new_str = ""
# for i in range(len(my_str)):
#     if(i%2==0):
#        new_str += my_str[i] 

# pythonic way ~
for i in range(0,len(my_str),2):
       new_str += my_str[i] 

print("Starting assert...")
assert new_str == "aceg", "Instead we got " + new_str
print("Assert 1:done")

