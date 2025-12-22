def f(y,p,c):
    """
        @params {number} y
            Yearly salary of the user
        @params {number} p
            The portion saved each month in percentage
        @params {number} c
            The cost of the house to buy
        @return {int} months
            How many months we need for pay the house
    """
# the starting yearly salary
    yearly_salary = float(y)
# the month salary 
    monthly_salary = yearly_salary / 12;
#  the portion of salary to be saved *decimal form* (0.1 -> 10%)
    portion_saved = float(p)
# The cost of your dream home
    cost_of_dream_home = float(c)
# The percentage of the total cost needed for a down payment.
# Assume portion_down_payment = 0.25 (25%)
    portion_down_payment = 0.25;
# down_payment_of_dream_home
    down_payment_of_dream_home = cost_of_dream_home * 0.25;
# The amount that you have saved thus far is amount_saved, which starts at $0.
    amount_saved=0;
# Annual rate of return r
    r = 0.05;
# How many months, we need for pay the dream home
    months = 0;

    while (amount_saved < down_payment_of_dream_home):
        amount_saved += amount_saved * (r/12);
        amount_saved += monthly_salary * portion_saved;
        months += 1;
    return months; 

print("Starting assets...")
assert f(112000,0.17,  750000) == 97 ,str(f(112000,0.17,  750000))
print("Asset 1:done")
assert f(65000 ,0.20,  400000) == 79 ,str(f(65000 ,0.20,  400000))
print("Asset 2:done")
assert f(350000,0.3, 10000000) == 189,str(f(350000,0.3, 10000000))
print("Asset 3:done")

