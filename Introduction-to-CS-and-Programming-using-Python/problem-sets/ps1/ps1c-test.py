def f(i):
    """
        @params {number} i 
             The initial amount from the user
        @return {list|None}
            If the r, is > 0 < 1,
                return a list with the r the user need, and the steps 
                we needed for calculate it.
            Otherwise return Null
    """
# The initial amount in your savings account ( initial_deposit)
# initial_deposit = float(input("Your initial amount: "))
    initial_deposit = float(i);


# The months we got for paying the down paymnet
    months = 36
# The price of our dream home
    cost_house = 800000;
# The percentual of the down payment (0.25 -> 25%)
    down_payment = 0.25; 
# The amount of money  of the down_pay
    down_pay = cost_house * down_payment; 
# The range where r lies
    high = 1;
    low = 0.01;
    epsilon = 100
# Rendiment needed to achieve a down payment
    r = (high + low) / 2;
# The result, of finding the lowest rate 
    res = initial_deposit * (1 + r/12)**months
# Steps taked by the biserction search
    steps = 0
# Break when the res is between [-100 +100](epsilon) of downpay
    while(True):
        if(res > down_pay):
            high = r;
        else:
            low = r;
        r = (high + low) / 2;
        res = initial_deposit * (1 + r/12)**36;
        steps += 1

        if(res >= down_pay - epsilon and res < down_pay + epsilon):
            return [r,steps] 
        if(r >= 1): return None;

print("Starting assets...")
r,steps = f(65000)
expected_steps = 12;
assert r >= 0.380615234375 - 0.001 or r < 0.380615234375 + 0.001 and steps >= expected_steps - 2 or steps < expected_steps + 2,str([r,steps])
print("Asset 1:done")
r,steps = f(150000)
expected_steps = 11;
assert r >= 0.09619140625 - 0.001 or r < 0.09619140625 + 0.001 and steps >= expected_steps - 2 or steps < expected_steps + 2,str(b)
print("Asset 2:done")
c = f(1000)
assert c == None ,str(c)
print("Asset 3:done")
