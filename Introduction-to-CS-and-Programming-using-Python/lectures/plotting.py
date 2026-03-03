import matplotlib.pyplot as plt


#set line width
plt.rcParams['lines.linewidth'] = 2
#set font size for titles
plt.rcParams['axes.titlesize'] = 16
#set font size for labels on axes
plt.rcParams['axes.labelsize'] = 16
#set size of numbers on x-axis
plt.rcParams['xtick.labelsize'] = 10
#set size of numbers on y-axis
plt.rcParams['ytick.labelsize'] = 10
#set size of ticks on x-axis
plt.rcParams['xtick.major.size'] = 5
#set size of ticks on y-axis
plt.rcParams['ytick.major.size'] = 5
#set size of markers
plt.rcParams['lines.markersize'] = 10
#set number of examples shown in legends
plt.rcParams['legend.numpoints'] = 1
#set the font size globally
plt.rcParams['xtick.labelsize']=20
plt.rcParams['ytick.labelsize']=20
plt.rcParams['axes.labelsize'] = 26 
plt.rcParams['axes.titlesize'] = 26 
plt.rcParams["figure.figsize"] = (15,10)

########################
## Plotting many lines 
########################
nVals = []
linear = []
quadratic = []
cubic = []
exponential = []

for i in range(0, 30):
    nVals.append(i)
    linear.append(i)
    quadratic.append(i**2)
    cubic.append(i**3)
    exponential.append(1.5**i)

plt.title("Test")
plt.xlabel("X")
plt.ylabel("Y")
# #### Plotting one line
plt.plot(nVals, quadratic)
# Draw with the graphic backend (QT)
plt.show()

#################################################################
##### Plotting multiple lines with labels in the SAME PLOT
#################################################################

months = range(1, 13, 1)
boston = [28,32,39,48,59,68,75,73,66,54,45,34]
plt.plot(months, boston, label = 'Boston')
phoenix = [54,57,61,68,77,86,91,90,84,73,61,54]
plt.plot(months, phoenix, label = 'Phoenix')
# Add labels and title
plt.title('Ave. Temperatures')
plt.xlabel('Month')
plt.ylabel('Degrees F')
# Change x axis labels to custom labels
plt.xticks((1,2,3,4,5,6,7,8,9,10,11,12),
          ('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'))

plt.legend(loc = 'best', fontsize=20) # position it automatically

plt.show();

#################################################################
##### Plotting multiple PLOT using subplot
#################################################################
months = range(1, 13, 1)           
boston = [28,32,39,48,59,68,75,73,66,54,45,34]
plt.subplot(2,1,1)
plt.ylim(0, 100)
plt.plot(months, boston, 'b-')
plt.ylabel('Degrees F')
plt.title('Boston vs. Phoenix')
plt.xticks((1,2,3,4,5,6,7,8,9,10,11,12),
          ('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'))
phoenix = [54,57,61,68,77,86,91,90,84,73,61,54]
plt.subplot(2,1,2)
plt.ylim(0, 100)
plt.plot(months, phoenix, 'r--')
plt.ylabel('Degrees F')
plt.xticks((1,2,3,4,5,6,7,8,9,10,11,12),
          ('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'))

plt.show();


#################################################################
##### Plotting using data from a file
#################################################################

###### Read file data and plot it
def getUSPop(fileName):
    inFile = open(fileName, 'r')
    dates, pops = [], []
    # Change 12,203 -> 12203 
    # Otherwise python can't convert it
    for l in inFile:
        line = ''
        for c in l:
            if c in '0123456789 ':
                line += c
        line = line.split(' ')
        dates.append(int(line[0]))
        pops.append(int(line[1]))
    return dates, pops

# dates, pops = getUSPop('lec25_USPopulation.txt')
# plt.plot(dates, pops)
# plt.title('Population in What Is Now U.S.\n' +\
#           '(Native Am. Excluded Before 1860)')
# plt.xlabel('Year')
# plt.ylabel('Population')
### Change the scale, of the line (see better the difference)
# plt.semilogy()   
# plt.show()
