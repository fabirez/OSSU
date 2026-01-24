from dateutil import parser;

class Workout(object):
    cal_per_hours = 200;
    def __init__(self, start, end, calories=None):
        self.start = parser.parse(start); #objects of type datetime
        self.end= parser.parse(end);
        self.calories=calories;
        self.icon = '😅';
        self.kind = 'Workout';

    def __str__(self):
        """Return a text-based graphical depiction of the workout"""
        width = 16
        retstr =  f"|{'–'*width}|\n"
        retstr += f"|{' ' *width}|\n"
        retstr += f"| {self.icon}{' '*(width-3)}|\n"  #assume width of icon is 2 chars - len('🏃🏽‍♀️');  doesn't do what you'd epxect
        retstr += f"| {self.kind}{' '*(width-len(self.kind)-1)}|\n"
        retstr += f"|{' ' *width}|\n"
        duration_str = str(self.get_duration())
        retstr += f"| {duration_str}{' '*(width-len(duration_str)-1)}|\n"
        cal_str = f"{round(self.get_calories(),1)}"
        retstr += f"| {cal_str} Calories {' '*(width-len(cal_str)-11)}|\n"

        retstr += f"|{' ' *width}|\n"
        retstr +=  f"|{'_'*width}|\n"

        return retstr

    def __eq__(self, other):
        """Returns true if this workout is equal to another workout, false o.w."""
        # the \ breaks up the line
        return type(self) == type(other) and \
                self.start == other.start and \
                self.end == other.end and \
                self.kind == other.kind and \
                self.get_calories() == other.get_calories()

    def get_end(self):
        return self.end;
    def get_start(self):
        return self.start;
    def get_duration(self):
        """Return the duration of the workout, as a datetime.interval object"""
        return self.end-self.start
    def get_calories(self):
        if(self.calories == None):
            return Workout.cal_per_hours*(self.end-self.start).total_seconds()/3600;
        else:
            return self.calories;
    def set_calories(self, calories):
        self.calories = calories;
    def set_start(self,start):
        self.start = start;
    def set_end(self,end):
        self.end = end;


my_workout = Workout("24/01/2026 13:00", "24/01/2026 15:00", 350)
my_workout_1 = Workout("01/01/2021 15:30", "01/01/2021 15:00", 320)
my_workout_2 = Workout("01/01/2021 15:35", "01/01/2021 16:00", 300)


class RunWorkout(Workout):
    """
    @params {datetime} start 
        When the run workout start DD/MM/YYYY HH:MM
    @params {datetime} end
        When the run workout end DD/MM/YYYY HH:MM
    @params {int} elev
    @params {int | None} 
        The number of calories consumed
    @params {list of tuples | None}
        A tuple of integers representing the latitude and longitude (lat,lon)
    """
    cals_per_km=100;
    def __init__(self, start, end, elev=0, calories=None, route_gps_points=None):
        super().__init__(start, end, calories)
        self.icon = "🏃" ;
        self.kind = "Running";
        self.elev = elev;
        self.route_gps_points = route_gps_points; 

    def get_elev(self):
        return self.elev;

    def get_calories(self):
        """Return the total calories consumed by the workout, derived
        using 1) the GPS points if supplied, 2) calories, if supplied,
        or 3) an estimate of the calories based on the duration"""
        if (self.route_gps_points != None):
            dist = 0
            lastP = self.route_gps_points[0]
            for p in self.route_gps_points[1:]:
                dist += gpsDistance(lastP,p)
                lastP = p
            return dist * RunWorkout.cals_per_km
        else:
            return super().get_calories()
    def get_route_gps_point(self):
        return self.route_gps_point;

    def set_elev(self,elev):
        self.elev = elev;
    def set_route_gps_point(self,srgp):
        self.route_gps_point = srgp;
    def __eq__(self,other):
        return super().__eq__(other) and self.elev == self.elev;
        

r = RunWorkout("24/01/2026 13:00", "24/01/2026 15:00",350, 100)


w1 = Workout('9/30/2021 2:20 PM','9/30/2021 2:50 PM')  
w2 = Workout('9/30/2021 2:20 PM','9/30/2021 2:50 PM', 450)  
rw1 = RunWorkout('9/30/2021 2:20 PM','9/30/2021 2:50 PM', 250) 
rw2 = RunWorkout('9/30/2021 2:20 PM','9/30/2021 2:50 PM', 450, 700) 
rw3 = RunWorkout('9/30/2021 2:20 PM','9/30/2021 2:50 PM', calories=300) 

# print(w1 == w2)  # False since only length of workout is the same
# print(w1 == w3)  # False since only length of workout is the same
# print(w2 == w3)  # True since the length and calories are equal
# print(w1 == rw1)  # False since the types of w1 and rw1 are not the same 
# print(rw1 == rw2) # False since the elevation is different
# print(rw1 == rw3) # True since the types, workout length, and elev is the same

