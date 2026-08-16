import tester.*;

interface IEntertainment {
    //compute the total price of this Entertainment
    double totalPrice();
    //computes the minutes of entertainment of this IEntertainment
    int duration();
    //produce a String that shows the name and price of this IEntertainment
    String format();
    //is this IEntertainment the same as that one?
    boolean sameEntertainment(IEntertainment that);
    //is this Magazine the same as the given one?
		boolean sameMagazine(Magazine m);
    //is this TVSeries the same as the given one?
		boolean sameTVSeries(TVSeries tv);
    //is this a Podcast the same as the given one?
		boolean samePodcast(Podcast p);
}


// Lift the common fields to an abstract class AEntertainment.
// Make sure you include a constructor in the abstract class, and change the constructors in the derived classes accordingly. Run the program and make sure all test cases work as before.

abstract class AEntertainment implements IEntertainment {
    String name;
    double price; //represents price per issue
    int installments; //number of episodes in this Podcast

		AEntertainment(String name, double price, int installments){
			this.name=name;
			this.price=price; 
			this.installments=installments;
		}

    //computes the price of a yearly subscription to this Magazine
    public double totalPrice() {
        return this.price * this.installments;
    }

    //computes the minutes of entertainment of this Podcast
    public int duration() {
        return 50 * this.installments;
    }

    //is this IEntertainment the same as that one?
    public abstract boolean sameEntertainment(IEntertainment that);

    //is this Magazine the same as the given one?
		public boolean sameMagazine(Magazine m){
			return false;
		}
    //is this TVSeries the same as the given one?
		public boolean sameTVSeries(TVSeries tv){
			return false;
		}
    //is this a Podcast the same as the given one?
		public boolean samePodcast(Podcast p){
			return false;
		}

    //produce a String that shows the name and price of this TVSeries
    public String format() {
        return this.name + ", " + this.price + ".";
    }
}

class Magazine extends AEntertainment {
    String genre;
    int pages;
    
    Magazine(String name, double price, String genre, int pages, int installments) {
				super(name, price, installments);
        this.genre = genre;
        this.pages = pages;
    }
    
    //computes the minutes of entertainment of this Magazine, (includes all installments)
    public int duration() {
        return 5 * this.pages;
			
    }
    
    //is this Magazine the same as that IEntertainment?
    public boolean sameEntertainment(IEntertainment that) {
        return that.sameMagazine(this);
    }

    //is this Magazine the same as the given one?
		public boolean sameMagazine(Magazine m){
			return this.name == m.name;
		}
}

class TVSeries extends AEntertainment {
    String corporation;
    
    TVSeries(String name, double price, int installments, String corporation) {
				super(name, price, installments);
        this.corporation = corporation;
    }
    
    //is this TVSeries the same as that IEntertainment?
    public boolean sameEntertainment(IEntertainment that) {
        return that.sameTVSeries(this);
    }

    //is this TVSeries the same as the given one?
		public boolean sameTVSeries(TVSeries tv){
			return this.name == tv.name;
		}
}

class Podcast extends AEntertainment {
    
    Podcast(String name, double price, int installments) {
				super(name, price, installments);
    }
    
    //is this Podcast the same as that IEntertainment?
    public boolean sameEntertainment(IEntertainment that) {
        return that.samePodcast(this);
    }

    //is this a Podcast the same as the given one?
		public boolean samePodcast(Podcast p){
			return this.name == p.name;
		}
}

class ExamplesEntertainment {
    IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
    IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
    IEntertainment serial = new Podcast("Serial", 0.0, 8);

    IEntertainment javaMagazine = new Magazine("Java Rebels", 6.96, "Geek", 96, 96);
    IEntertainment blackMirror = new TVSeries("Black Mirror", 3.98, 3,"HBO");
    IEntertainment murial = new Podcast("Murial", 0.0, 98);
    
		// Make one more example of data for each of the three classes and add more tests for the totalPrice method (that is already defined) using them.

    //testing total price method
    boolean testTotalPrice(Tester t) {
        return t.checkInexact(this.rollingStone.totalPrice(), 2.55*12, .0001) 
        && t.checkInexact(this.houseOfCards.totalPrice(), 5.25*13, .0001)
        && t.checkInexact(this.serial.totalPrice(), 0.0, .0001)
        && t.checkInexact(this.javaMagazine.totalPrice(), 6.96*96, .0001);
    }

    //testing duration method
    boolean testDuration(Tester t) {
        return t.checkExpect(this.rollingStone.duration(), 5*60) 
        && t.checkExpect(this.houseOfCards.duration(), 50*13)
        && t.checkExpect(this.serial.duration(), 50*8)
        && t.checkExpect(this.javaMagazine.duration(), 5*96);
    }

    //testing format method
    boolean testFormat(Tester t) {
        return t.checkExpect(this.rollingStone.format(), "Rolling Stone, 2.55.") 
        && t.checkExpect(this.houseOfCards.format(), "House of Cards, 5.25.")
        && t.checkExpect(this.serial.format(), "Serial, 0.0.")
        && t.checkExpect(this.javaMagazine.format(), "Java Rebels, 6.96.")
        && t.checkExpect(this.blackMirror.format(), "Black Mirror, 3.98.")
        && t.checkExpect(this.murial.format(), "Murial, 0.0.")
		;
    }

    //testing sameEntertainment method
    boolean testSameEntertainment(Tester t) {
        return t.checkExpect(this.rollingStone.sameEntertainment(this.rollingStone), true) 
        && t.checkExpect(this.rollingStone.sameEntertainment(this.houseOfCards), false)
        && t.checkExpect(this.houseOfCards.sameEntertainment(this.houseOfCards), true)
        && t.checkExpect(this.serial.sameEntertainment(this.serial), true)
        && t.checkExpect(this.blackMirror.sameEntertainment(this.javaMagazine), false)
        && t.checkExpect(this.murial.sameEntertainment(this.serial), false)
		;
    }
    
}
