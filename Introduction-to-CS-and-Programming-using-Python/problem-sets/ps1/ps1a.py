## 6.100A PSet 1: Part A
## Name:
## Time Spent:
## Collaborators:

##################################################################################
## Get user input for yearly_salary, portion_saved and cost_of_dream_home below ##
##################################################################################
# the starting yearly salary
yearly_salary = float(input("Enter your yearly salary: ")); 
#  the portion of salary to be saved *decimal form* (0.1 -> 10%)
portion_saved = float(input("Enter the percent of your salary to save, as a decimal: "));
# The cost of your dream home
cost_of_dream_home = float(input("Enter the cost of your dream home: "));


#########################################################################
## Initialize other variables you need (if any) for your program below ##
#########################################################################
# the month salary 
monthly_salary = yearly_salary / 12;

portion_down_payment = 0.25;
###############################################################################################
## Determine how many months it would take to get the down payment for your dream home below ## 
###############################################################################################
# down_payment_of_dream_home
down_payment_of_dream_home = cost_of_dream_home * portion_down_payment;
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
