
// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
    MTLoBuddy() {}

	public boolean sameBuddy(Person that){ return false; }

    public int countCommonBuddiesHelper(Person that){return 0;}

    public ILoBuddy append(ILoBuddy that){ return that; } 
	
	public ILoBuddy appendPerson(Person that){ return new ConsLoBuddy(that, this); }

	public ILoBuddy uniqueList(ILoBuddy currentList){ return currentList; }

    public ILoBuddy buildList(ILoBuddy visitedBuddy){ return this; };
    // NOTE: This method is here only for debug.
    public ILoString buildListString(ILoBuddy visitedBuddy){ return new MtLoString(); };
    public int length(){ return 0; }
}

