
// represents a Person with a user name and a list of buddies
class Person {

    String username;
    ILoBuddy buddies;

    Person(String username) {
        this.username = username;
        this.buddies = new MTLoBuddy();
    }

    // return the username by this person
    String getUsername(){ return this.username; }
    // return the buddies by this person
    ILoBuddy getBuddies(){ return this.buddies; }

    // returns true if this Person has that as a direct buddy
    boolean hasDirectBuddy(Person that) {
        return this.buddies.sameBuddy(that);
    }
    

    // returns the number of people who will show up at the party 
    // given by this person
    int partyCount(){
        ILoBuddy allBuddy = this.buddies.buildList(new ConsLoBuddy(this, new MTLoBuddy())).appendPerson(this).uniqueList(new MTLoBuddy());
        return allBuddy.length();
    }

    // returns the number of people that are direct buddies 
    // of both this and that person
    int countCommonBuddies(Person that) {
        return this.buddies.countCommonBuddiesHelper(that);
    }

    // will the given person be invited to a party 
    // organized by this person?
    boolean hasExtendedBuddy(Person that) {
        ILoBuddy allBuddy = this.buddies.buildList(new ConsLoBuddy(this, new MTLoBuddy())).appendPerson(this);
        return allBuddy.sameBuddy(that);
    }

    // EFFECT:
    // Change this person's buddy list so that it includes the given person
    void addBuddy(Person buddy)
    {
       this.buddies = new ConsLoBuddy(buddy, this.buddies);
    }

}
