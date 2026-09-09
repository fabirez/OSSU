
// represents a list of Person's buddies
interface ILoBuddy {
	// produce true if the given buddy is present in the list; otherwise false
	boolean sameBuddy(Person that);

	// if the buddy int he list is present in that buddies, return 1 otherwise 0.
  int countCommonBuddiesHelper(Person that);
	
	// Append the given list as a tail
	ILoBuddy append(ILoBuddy that);

	// Append the given list as a tail
	ILoBuddy appendPerson(Person that);

	// pruduce a list without duplicates
	ILoBuddy uniqueList(ILoBuddy currentList);

	ILoBuddy buildList(ILoBuddy visitedBuddy);
	// NOTE: This method is here only for debug.
	ILoString buildListString(ILoBuddy visitedBuddy);

	//produce the the length of the list
	int length();
}
