import random
def prob_dice(side):
        dice = ['.',':',':.','::','::.',':::'];
        Nsims = 10000;
        count = 0;
        for i in range(Nsims):
            roll = random.choice(dice)
            if roll == side:
                    count += 1
        print(count/Nsims)


prob_dice('.') 
prob_dice('::') 
            
