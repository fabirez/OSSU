# the starting yearly salary
yearly_salary = float(input("Enter your yearly salary: ")); 
# the month salary 
monthly_salary = yearly_salary / 12;

#  the portion of salary to be saved *decimal form* (0.1 -> 10%)
portion_saved = float(input("Enter the percent of your salary to save, as a decimal: "));
# The cost of your dream home
cost_of_dream_home = float(input("Enter the cost of your dream home: "));

# The semi-annual salary raise ( semi_annual_raise), 
# which is a decimal percentage (e.g. 0.1 for 10%)
semi_annual_raise = float(input("Enter the sami annual raise: "));

more_money = yearly_salary * semi_annual_raise;

#the percentage of the total cost needed for a down payment.
# Assume portion_down_payment = 0.25 (25%)
portion_down_payment = 0.25;


down_payment_of_dream_home = cost_of_dream_home * 0.25;

# The amount that you have saved thus far is amount_saved, which starts at $0.
amount_saved=0;

# Annual rate of return r
r = 0.05;

# How many months, we need for pay the dream home
months = 1;

# cont che months, for the annual rate
counter_months = 0;

while (amount_saved < down_payment_of_dream_home):
    amount_saved += monthly_salary * portion_saved;
    amount_saved += amount_saved * (r/12)
    months += 1;
    counter_months += 1;
    if(counter_months == 6):
        monthly_salary += more_money 
        counter_months = 0;



print(months)

