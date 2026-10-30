import tester.*;
import java.util.ArrayList;

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

class Utils {
  <T> ArrayList<T> reverse(ArrayList<T> source) {
		if(source.size() <= 0) return source;
		Stack<T> s = new Stack();
		ArrayList<T> res = new ArrayList<T>();
		for (T t: source){
			s.push(t);
		}
		while(!s.isEmpty()){
			res.add(s.pop());
		}
		return res;
	}
}

class ExampleUtils{
	ArrayList<String> aS;

	void initData(){
		this.aS = new ArrayList<String>();
		this.aS.add("A"); this.aS.add("B"); this.aS.add("C");

	}


	void testReverse(Tester t){
	  this.initData();
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("C"); expected.add("B"); expected.add("A");
		t.checkExpect(new Utils().reverse(this.aS), expected);

		// Array with one element
		ArrayList<String> los1 = new ArrayList<String>();
		los1.add("A");
		t.checkExpect(new Utils().reverse(los1), los1);

		// Empty array
		ArrayList<String> los2 = new ArrayList<String>();
		t.checkExpect(new Utils().reverse(los2), los2);
	}


}
