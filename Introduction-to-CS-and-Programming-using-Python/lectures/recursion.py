
def mult(a,b):
    # Base case
    if(b==1):
        return a; 
    # Recursive step
    else:
        return a+mult(a,b-1);

print(mult(5,4))
