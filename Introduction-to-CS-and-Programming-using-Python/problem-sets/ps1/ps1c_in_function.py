def part_c(initial_deposit):
	#########################################################################
	
	# The months we got for paying the down paymnet
	months = 36
	# The price of our dream home
	cost_house = 800000;
	# The percentual of the down payment (0.25 -> 25%)
	down_payment = 0.25; 
	# The amount of money  of the down_pay
	down_pay = cost_house * down_payment; 
	# epsilon
	epsilon = 100
	# months in a year
	year = 12.0
	
	##################################################################################################
	## Determine the lowest rate of return needed to get the down payment for your dream home below ##
	##################################################################################################
	# The range where r lies
	high = 1;
	low = 0.00;
	# Rendiment needed to achieve a down payment
	r = (high + low) / 2;
	# The result, of finding the lowest rate 
	res = initial_deposit * (1 + r/year)**months
	# Steps taked by the biserction search
	steps = 1
	
	
	while(True):
	    if(res > down_pay):
	        high = r;
	    else:
	        low = r;
	
	    r = (high + low) / 2;
	    res = initial_deposit * (1 + r/year)**months;
	    steps += 1
	
	    if(res >= down_pay - epsilon and res <= down_pay + epsilon):
	       break; 
	    if(r >= 1): 
	        r = None
	        break;
	print("Steps:", steps)
	
	######################################
	## Version without bisection search ##
	######################################
	# # The initial amount in your savings account ( initial_deposit)
	# # initial_deposit = float(input("Your initial amount: "))
	# initial_deposit = float(input("Initial deposit: "));
	# # The months we got for paying the down paymnet
	# months = 36
	# # The price of our dream home
	# cost_house = 800000;
	# # The percentual of the down payment (0.25 -> 25%)
	# down_payment = 0.25; 
	# # The amount of money  of the down_pay
	# down_pay = cost_house * down_payment; 
	# # Rendiment needed to achieve a down payment
	# r = 0;
	# # How much we increment the r, for find the right one.
	# increment = 0.0001
	# # The result, of finding the lowest rate 
	# res = initial_deposit * (1 + r/12)**36
	# while(res <= down_pay - 100):
	#     r += increment;
	#     res = initial_deposit * (1 + r/12)**36;
	#
	# print(res)
	return r, steps