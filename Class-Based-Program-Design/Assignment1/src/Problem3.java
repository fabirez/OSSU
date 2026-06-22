/*
 * Problem 3: Traveling
 *
 *
We’ve been asked to help build a new medieval adventure game,
Summer Is Coming.
We’re trying to figure out the gameplay mechanics,
so we’re starting with representations for travel around the game world.

Players can live in three types of housing: a Hut, an Inn, and a Castle.

A Hut has a capacity and the current population of its population.
The population must be less than the capacity.

An Inn has a name,
capacity and the current population of its population as well as the number of stalls in its stable.
The population must be less than the capacity.

A Castle has a name,
the family-name of the owners,
the current population of its population as well as the number of carriages it can hold in its carriage-house.

There are 2 types of transportation in this game:
- Horse

- Carriage

Each type of transportation should have a from and a to housing.
Horses also have a name and a color (which you may represent using the color’s name).
They can only go to an inn if there is room in the stables, but they can go to any hut or castle.

Carriages can only carry a limited supply of tonnage and only travel from Inns to Castles or vice versa.
When they go to a Castle there must be room for them in the carriage house.

Define six examples of housing, including:
hovel: Capacity 5, population 1

winterfell: Named "Winterfell", family name "Stark", population 500, can hold 6 carriages

crossroads: Named "Inn At The Crossroads", capacity 40, population 20, 12 stalls

The others can be whatever you wish.
Define four types of travel, two of each kind.

Name your examples horse1, carriage2, etc., and your examples class ExamplesTravel.

Please note: We’re placing a lot of restrictions on the data, such as the population being less than capacity,
possible destinations of carriages, etc.
However we aren’t (yet) actually enforcing these in the code.

The ways to enforce these constraints will be further explored later in the semester.
For now, you are expected to create examples that conform to these constraints
*/



// housing  !!! do we need this as interface? YES

interface IHousing { }

class Hut implements IHousing{
  int capacity; 
  int population; // !!! method?

  Hut(int capacity, int population){
    this.capacity=capacity;
    this.population=population; 
  }
}



class Inn implements IHousing{
  String name;
  int capacity;
  int population;
  int stalls;

  Inn(String name, int capacity, int population, int stalls){
    this.name=name;
    this.capacity=capacity;
    this.population=population;
    this.stalls=stalls;
  }
}

class Castle implements IHousing {
  String name;
  String familyName;
  int population; // !!! method?
  int noc; // [N]umber [o]f [c]arriages

  Castle(String name, String familyName, int population, int noc){
    this.name=name;
    this.familyName=familyName;
    this.population=population; 
    this.noc=noc;
  }
}

// transportation !!! interface?


// They can only go to an inn if there is room in the stables, but they can go to any hut or castle.
class Horse{
  IHousing from;
  IHousing to;
  String name;
  String color;

  Horse(IHousing from, IHousing to, String name, String color){
    this.from=from;
    this.to=to;
    this.name=name;
    this.color=color;
  }
}


// Carriages can only carry a limited supply of tonnage and only travel from Inns to Castles or vice versa.
// When they go to a Castle there must be room for them in the carriage house.
class Carriages{
  IHousing from;
  IHousing to; 
  Carriages(IHousing from, IHousing to){
    this.from=from;
    this.to=to;
  }
}

class ExamplesTravel{
  ExamplesTravel(){}

  IHousing hovel = new Hut(5,1);
  IHousing winterfell = new Castle("Winterfell", "Stark", 500, 6);
  IHousing crossroads = new Inn("Inn At The Crossroads", 40, 20, 12);

  IHousing vovel = new Hut(7,2);
  IHousing summerfell = new Castle("Summerfell", "Carge", 700, 7);
  IHousing riveroads = new Inn("Inn At the riveroads", 70, 30, 22);
  
  Horse     horse1 =     new Horse(this.hovel, this.winterfell, "Horse 1", "Red");
  Carriages carraiges1 = new Carriages(this.winterfell,this.crossroads);
}
