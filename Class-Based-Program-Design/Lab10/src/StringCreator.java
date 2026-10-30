import tester.*;

class StringCreator{
	String s;
	Stack<String> cache;

	StringCreator(){ 
		 this.s="";
		 this.cache= new Stack(); 
	 }
	StringCreator(String s){ 
		 this.s=s;
		 this.cache= new Stack(); 
	 }
	// adds a character to the end of the string
	void add(Character c){
		 this.cache.push(this.s);
		 this.s = s + c;
	}
	// removes the last character of the string
	void remove(){
		int size = s.length();
		if(size <= 0){ return; }
		this.cache.push(this.s);
		this.s = this.s.substring(0, size - 1) ;
	 }
  // returns the current string.
	String getString() { return this.s; }

  // returns the prev state of the current string.
	String undo() { 
		 if(this.cache.isEmpty()){ return this.s; } 
		 String prev = this.cache.pop();
		 this.s = prev;
		 return prev;
	 }
}

class StringCreatorExamples{
	StringCreator sc;
	void initData(){
		this.sc = new StringCreator("a");
	}

	void testAdd(Tester t) {
		this.initData();
		this.sc.add('b');
		t.checkExpect(this.sc.getString(), "ab");
		this.sc.add('c');
		t.checkExpect(this.sc.getString(), "abc");
	}

	void testRemove(Tester t) {
		this.initData();
		this.sc.remove();
		t.checkExpect(this.sc.getString(), "");
		// Remove from an empty list.
		this.sc.remove();
		t.checkExpect(this.sc.getString(), "");
	}


	void testRun(Tester t) {
			StringCreator creator = new StringCreator();
			t.checkExpect(creator.getString(),"");
			creator.add('c');
			creator.add('d');
			t.checkExpect(creator.getString(),"cd");
			creator.add('e');
			t.checkExpect(creator.getString(),"cde");
			creator.remove();
			creator.remove();
			t.checkExpect(creator.getString(),"c");
			creator.undo(); //undoes the removal of 'd'
			t.checkExpect(creator.getString(),"cd");
			creator.undo(); //undoes the removal of 'e'
			creator.undo(); //undoes the addition of 'e'
			t.checkExpect(creator.getString(),"cd");
			creator.add('a');
			t.checkExpect(creator.getString(),"cda");
			creator.undo(); //undoes the addition of 'a'
			creator.undo(); //undoes the addition of 'd'
			creator.undo(); //undoes the addition of 'c'
			t.checkExpect(creator.getString(),"");
			creator.undo(); //no effect, there is nothing to undo
	}
}
