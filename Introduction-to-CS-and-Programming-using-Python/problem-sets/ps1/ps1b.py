## 6.100A PSet 1: Part B
## Name:
## Time Spent:
## Collaborators:

##########################################################################################
## Get user input for yearly_salary, portion_saved, cost_of_dream_home, semi_annual_raise below ##
##########################################################################################

# the starting yearly salary
yearly_salary = float(input("Enter your yearly salary: ")); 
#  the portion of salary to be saved *decimal form* (0.1 -> 10%)
portion_saved = float(input("Enter the percent of your salary to save, as a decimal: "));
# The cost of your dream home
cost_of_dream_home = float(input("Enter the cost of your dream home: "));
# The semi-annual salary raise ( semi_annual_raise), 
# which is a decimal percentage (e.g. 0.1 for 10%)
semi_annual_raise = float(input("Enter the sami annual raise: "));

#########################################################################
## Initialize other variables you need (if any) for your program below ##
#########################################################################

# the month salary 
monthly_salary = yearly_salary / 12;
# The percentage of the total cost needed for a down payment.
# Assume portion_down_payment = 0.25 (25%)
portion_down_payment = 0.25;
# down_payment_of_dream_home
down_payment_of_dream_home = cost_of_dream_home * portion_down_payment;
# The amount that you have saved thus far is amount_saved, which starts at $0.
amount_saved=0;
# Annual rate of return r
r = 0.05;
# How many months, we need for pay the dream home
months = 0;
# How many months passed, for calculate the new payment
counter_months = 0;

###############################################################################################
## Determine how many months it would take to get the down payment for your dream home below ## 
###############################################################################################


while (amount_saved < down_payment_of_dream_home):
    amount_saved += amount_saved * (r/12);
    amount_saved += monthly_salary * portion_saved;
    months += 1;
    if months % 6 == 0:
        yearly_salary += yearly_salary * semi_annual_raise;
        monthly_salary = yearly_salary / 12;
