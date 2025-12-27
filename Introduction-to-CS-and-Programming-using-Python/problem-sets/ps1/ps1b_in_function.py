def part_b(yearly_salary, portion_saved, cost_of_dream_home, semi_annual_raise):
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
	return months