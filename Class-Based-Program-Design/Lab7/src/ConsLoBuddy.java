interface ILoString { 
    ILoString append(ILoString that);
}

class ConsLoString implements ILoString {
    String    first;
    ILoString  rest;
    ConsLoString(String first, ILoString rest){
        this.first=first;
        this.rest=rest;
    }

    public ILoString append(ILoString that)
    {
        return new ConsLoString(this.first, this.rest.append(that));
    }
}

class MtLoString implements ILoString {
    public ILoString append(ILoString that){ return that; }
}

// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

    Person first;
    ILoBuddy rest;

    ConsLoBuddy(Person first, ILoBuddy rest) {
        this.first = first;
        this.rest = rest;
    }

	// produce true if the given buddy is present in the list; otherwise false
    public boolean sameBuddy(Person that)
    {
        if(this.first.getUsername().equals(that.username)){
            return true;
        }else{
            return this.rest.sameBuddy(that);
        }
    }

	// if the buddy int he list is present in that buddies, return 1 otherwise 0.
    public int countCommonBuddiesHelper(Person that) 
    {
        if(that.hasDirectBuddy(this.first)){
            return 1 + this.rest.countCommonBuddiesHelper(that);
        }else{
            return this.rest.countCommonBuddiesHelper(that);
        }
    }


	// Append the given list as a tail
    public ILoBuddy append(ILoBuddy that){ return new ConsLoBuddy(this.first, this.rest.append(that)); }

	// Append the given person as a head
	public ILoBuddy appendPerson(Person that){ return new ConsLoBuddy(that, this); }

	// pruduce a list without duplicates
	public ILoBuddy uniqueList(ILoBuddy currentList)
    {
        if(currentList.sameBuddy(this.first)){
            return this.rest.uniqueList(currentList);
        }else{
            return this.rest.uniqueList(new ConsLoBuddy(this.first, currentList));
        }
    }

    // NOTE: This method is here only for debug.
	// return a new list created based on the buddy, and on the buddies by the buddy
    public ILoString buildListString(ILoBuddy visitedBuddy) 
    {
        if(visitedBuddy.sameBuddy(this.first)){
            // if the buddy already exist in the list, then we don't need to get the buddies beacuse we already did.
            return this.rest.buildListString(visitedBuddy);
        }else{
            return new ConsLoString(
                this.first.getUsername(),
                // Get all the buddies of this buddy
                this.first.getBuddies()
                // Build a list based on the buddy of this buddies, and get the buddies of the buddies.
                .buildListString(visitedBuddy.appendPerson(this.first))
                // Do the same on the rest of the buddies of this buddy.
                .append(this.rest.buildListString(visitedBuddy))
            );
        }
    }

    public ILoBuddy buildList(ILoBuddy visitedBuddy) 
    {
        if(visitedBuddy.sameBuddy(this.first)){
            // if the buddy already exist in the list, then we don't need to get the buddies beacuse we already did.
            return this.rest.buildList(visitedBuddy);
        }else{
            return new ConsLoBuddy(
                this.first,
                // Get all the buddies of this buddy
                this.first.getBuddies()
                // Build a list based on the buddy of this buddies, and get the buddies of the buddies.
                .buildList(visitedBuddy.appendPerson(this.first))
                // Do the same on the rest of the buddies of this buddy.
                .append(this.rest.buildList(visitedBuddy))
            );
        }
    }

    public int length()
    { 
        return 1 + this.rest.length(); 
    }

}
