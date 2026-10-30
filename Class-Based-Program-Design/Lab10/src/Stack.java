import tester.*;

class Stack<T> {
  Deque<T> contents;
	Stack(Deque<T> contents){this.contents = contents;}
	Stack(){this.contents = new Deque<T>();}
	// adds an item to the stack
  void push(T item) { this.contents.addAtTail(item); }
	// return ture if the current stack is empty, otherwise false.
  boolean isEmpty() { return this.contents.isEmpty(); }
	// removes and returns the top of the stack
  T pop(){ return this.contents.removeFromTail(); }
}


class ExamplesStack{
	Stack<String>  sS;
	void initData(){ this.sS = new Stack<String>(); }
	void testPush(Tester t){
		this.initData();
		this.sS.push("Hello");
		this.sS.push("World");
		t.checkExpect(this.sS.pop(),  "World");
		t.checkExpect(this.sS.pop(),  "Hello");
		t.checkException(new RuntimeException("Cannot remove from an empty Deque!"), this.sS, "pop");
		t.checkExpect(this.sS.isEmpty(), true);
	}
}
