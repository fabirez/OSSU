def f(y,p,c,s):
    """
        @params {number} y
            Yearly salary of the user
        @params {number} p
            The portion saved each month, in percentage
        @params {number} c
            The cost of the house to buy
        @params {number} s
            The semi annual raise, in percentage
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
# The semi annula rais, which is a decimal percentage (0.1 for 10%)
    semi_annual_raise = float(s)

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
# How many months passed, for calculate the new payment
    counter_months = 0;

    while (amount_saved < down_payment_of_dream_home):
        amount_saved += amount_saved * (r/12);
        amount_saved += monthly_salary * portion_saved;
        months += 1;
        counter_months += 1
        if counter_months == 6:
            yearly_salary += yearly_salary * semi_annual_raise;
            monthly_salary = yearly_salary / 12;
            counter_months = 0;
    return months; 

print("Starting assets...")
assert f(110000,0.15,750000 ,0.03) == 92 ,str(f(110000,0.15,750000,0.03))
print("Asset 1:done")
assert f(350000,0.3,10000000,0.05) == 131 ,str(f(350000,0.3,10000000,0.05))
print("Asset 2:done")
