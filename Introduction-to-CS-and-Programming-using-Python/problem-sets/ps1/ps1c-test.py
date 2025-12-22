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
    down_pay = cost_house * 0.25; 
# The range where r lies
    max = 1;
    min = 0.01;
# Rendiment needed to achieve a down payment
    r = (max + min) / 2;
# The result, of finding the lowest rate 
    res = initial_deposit * (1 + r/12)**36
# Steps taked by the biserction search
    steps = 0
# Break when the res is between -100 +100 of downpay
    while(True):
        if(res > down_pay):
            max = r;
        else:
            min = r;
        r = (max + min) / 2;
        res = initial_deposit * (1 + r/12)**36;
        steps += 1
        if(res >= down_pay - 100 and res < down_pay + 100):
            return [r,steps] 
        if(r >= 1): return None;

print("Starting assets...")
a = f(65000)
steps = 12;
assert a[0] >= 0.380615234375 - 0.001 or a[0] < 0.380615234375 + 0.001 and a[1] >= steps - 2 or a[1] < steps + 2,str(a)
print("Asset 1:done")
b = f(150000)
steps = 11;
assert b[0] >= 0.09619140625 - 0.001 or b[0] < 0.09619140625 + 0.001 and b[1] >= steps - 2 or b[1] < steps + 2,str(b)
print("Asset 2:done")
c = f(1000)
assert c == None ,str(c)
print("Asset 3:done")
