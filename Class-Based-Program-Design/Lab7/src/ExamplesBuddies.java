import tester.*;


// runs tests for the buddies problem
public class ExamplesBuddies{
	Person ann;
	Person bob;
	Person cole;
	Person dan;
	Person ed;
	Person fay;
	Person gabi;
	Person hank;
	Person jan;
	Person kim;
	Person len;

	ILoBuddy mtBuddy = new MTLoBuddy();
	void initData(){
		this.ann  = new Person( "Ann");
		this.bob  = new Person( "Bob");
		this.cole = new Person("Cole");
		this.dan  = new Person( "Dan");
		this.ed   = new Person(  "Ed");
		this.fay  = new Person( "Fay");
		this.gabi = new Person("Gabi");
		this.hank = new Person("Hank");
		this.jan  = new Person( "Jan");
		this.kim  = new Person( "Kim");
		this.len  = new Person( "Len");

		this.ann.buddies = new ConsLoBuddy(this.bob, new ConsLoBuddy(this.cole, mtBuddy));
		this.bob.buddies = new ConsLoBuddy(this.ann, new ConsLoBuddy(this.ed, new ConsLoBuddy(this.hank, mtBuddy)));
		this.cole.buddies = new ConsLoBuddy(this.dan, mtBuddy); 
		this.dan.buddies = new ConsLoBuddy(this.cole, mtBuddy); 
		this.ed.buddies = new ConsLoBuddy(this.fay, mtBuddy); 
		this.fay.buddies = new ConsLoBuddy(this.ed, new ConsLoBuddy(this.gabi, mtBuddy)); 
		this.gabi.buddies = new ConsLoBuddy(this.ed, new ConsLoBuddy(this.fay, mtBuddy)); 
		this.hank.buddies = mtBuddy;
		this.jan.buddies = new ConsLoBuddy(this.kim, new ConsLoBuddy(this.len, mtBuddy)); 
		this.kim.buddies = new ConsLoBuddy(this.jan, new ConsLoBuddy(this.len, mtBuddy)); 
		this.len.buddies = new ConsLoBuddy(this.jan, new ConsLoBuddy(this.kim, mtBuddy)); 
	}

	void testAddBuddy(Tester t){
		this.initData();
		t.checkExpect(this.ann.buddies, new ConsLoBuddy(this.bob, new ConsLoBuddy(this.cole, mtBuddy)));
		this.ann.addBuddy(this.dan);
		t.checkExpect(this.ann.buddies, new ConsLoBuddy(this.dan, new ConsLoBuddy(this.bob, new ConsLoBuddy(this.cole, mtBuddy))));

		t.checkExpect(this.hank.buddies, mtBuddy);
		this.hank.addBuddy(this.gabi);
		t.checkExpect(this.hank.buddies, new ConsLoBuddy(this.gabi, mtBuddy));
	}

	void testAddDirectBuddy(Tester t){
		this.initData();
		t.checkExpect(this.ann.hasDirectBuddy(this.bob),  true);
		t.checkExpect(this.ann.hasDirectBuddy(this.dan), false);

		t.checkExpect(this.fay.hasDirectBuddy(this.gabi),  true);
		t.checkExpect(this.fay.hasDirectBuddy(this.hank), false);
	}

	void testCountCommonBuddies(Tester t){
		this.initData();
		t.checkExpect(this.ann.countCommonBuddies(this.bob), 0);
		t.checkExpect(this.ann.countCommonBuddies(this.dan), 1);

		t.checkExpect(this.fay.countCommonBuddies(this.gabi), 1);
		t.checkExpect(this.fay.countCommonBuddies(this.hank), 0);
	}

	void testBuildList(Tester t){
		this.initData();

		// Direct and Indirect buddies in ann:
		// (bob,
		//		ann, 
		//			ed,
		//				fay,
		//					gabi,
		//		hank,
		//	  cole,
		//			dan,).

		ILoBuddy expectedList = new ConsLoBuddy(this.bob, 
		new ConsLoBuddy(this.ed,
		new ConsLoBuddy(this.fay,
		new ConsLoBuddy(this.gabi,
		new ConsLoBuddy(this.hank,
		new ConsLoBuddy(this.cole,
		new ConsLoBuddy(this.dan,
		new MTLoBuddy())))))));
		// Return the list without the person who calls buildList, in this case Ann (the person that organized the party)
		t.checkExpect(this.ann.buddies.buildList(new ConsLoBuddy(this.ann, new MTLoBuddy())), expectedList);
	}

	// test buildList with append
	// Here we have the full list, including the person that organized the party
	void testBuildListWithAppend(Tester t){
		this.initData();

		ILoBuddy expectedList = new ConsLoBuddy(this.ann, 
		new ConsLoBuddy(this.bob, 
		new ConsLoBuddy(this.ed,
		new ConsLoBuddy(this.fay,
		new ConsLoBuddy(this.gabi,
		new ConsLoBuddy(this.hank,
		new ConsLoBuddy(this.cole,
		new ConsLoBuddy(this.dan,
		new MTLoBuddy()))))))));
		t.checkExpect(this.ann.buddies.buildList(new ConsLoBuddy(this.ann, new MTLoBuddy())).appendPerson(this.ann), expectedList);
	}

	void testHasExtendedBuddy(Tester t) {
		this.initData();

		t.checkExpect(this.ann.hasExtendedBuddy(this.bob),  true);
		t.checkExpect(this.ann.hasExtendedBuddy(this.ann),  true);
		t.checkExpect(this.ann.hasExtendedBuddy(this.ed),   true);
		t.checkExpect(this.ann.hasExtendedBuddy(this.fay),  true);
		t.checkExpect(this.ann.hasExtendedBuddy(this.gabi), true);
		t.checkExpect(this.ann.hasExtendedBuddy(this.hank), true);
		t.checkExpect(this.ann.hasExtendedBuddy(this.cole), true);
		t.checkExpect(this.ann.hasExtendedBuddy(this.dan),  true);

		t.checkExpect(this.ann.hasExtendedBuddy(this.len), false);
		t.checkExpect(this.ann.hasExtendedBuddy(this.kim), false);
		t.checkExpect(this.ann.hasExtendedBuddy(this.jan), false);
	}

	void testPartyCount(Tester t) {
		this.initData();

		t.checkExpect(this.ann.partyCount(),  8);
		t.checkExpect(this.bob.partyCount(),  8);
		t.checkExpect(this.ed.partyCount(),   3);
		t.checkExpect(this.hank.partyCount(), 1);
		t.checkExpect(this.jan.partyCount(),  3);
		t.checkExpect(this.kim.partyCount(),  3);
		t.checkExpect(this.len.partyCount(),  3);
	}
}
