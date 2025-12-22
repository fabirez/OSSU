###################################
### Finger Exercise Lecture 1
###################################
# Assume three variables are already defined for you: a, b, and c.
# Create a variable called total that adds a and b then multiplies the result by c. Include a last line in your code to print the value: print(total)
a = 10;
b = 20;
c = 30;
total = (a+b) * c;
print(total)

###################################
### Finger Exercise Lecture 2
###################################
# Assume you are given a variable named number (has a numerical value).
# Write a piece of Python code that prints out one of the following strings: 

# positive if the variable number is positive
# negative if the variable number is negative
# zero if the variable number is equal to zero

number = 10;

if number > 0:
    print("positive")
elif number < 0:
    print("negative")
else:
    print("zero")

###################################
### Finger Exercise Lecture 3
###################################
# Assume you are given a positive integer variable named N.
# Write a piece of Python code that prints hello world on separate lines, N times.
# You can use either a while loop or a for loop.

N = 10;

for i in range(N):
    print("hello world");



