# the starting yearly salary
yearly_salary = float(input("Enter your yearly salary: ")); 
# the month salary 
monthly_salary = yearly_salary / 12;

#  the portion of salary to be saved *decimal form* (0.1 -> 10%)
portion_saved = float(input("Enter the percent of your salary to save, as a decimal: "));
# The cost of your dream home
cost_of_dream_home = float(input("Enter the cost of your dream home: "));
#the percentage of the total cost needed for a down payment.
# Assume portion_down_payment = 0.25 (25%)
portion_down_payment = 0.25;
down_payment_of_dream_home = cost_of_dream_home * 0.25;
# The amount that you have saved thus far is amount_saved, which starts at $0.
amount_saved=0;
# Annual rate of return r
r = 0.05;
# How many months, we need for pay the dream home
months = 0;
# cont che months, for the annual rate
counter_months = 0;

while (amount_saved < down_payment_of_dream_home):
    amount_saved += amount_saved * (r/12)
    amount_saved += monthly_salary * portion_saved;
    months += 1;

print("For the payment you need: ",months)
