/*
              +---------------+
              | Deque<String> |
              +---------------+
              | header ----------+
              +---------------+  |
                  +--------------+
                  |
                  v
            +------------------+
+---------->| Sentinel<String> |<-------------------------------------------+
|           +------------------+                                            |
|       +---- next             |                                            |
|       |   | prev ------------------------------------------------+        |
|       |   +------------------+                                   |        |
|       |                                                          |        |
|       v                                                          v        |
| +--------------+   +--------------+   +--------------+   +--------------+ |
| | Node<String> |   | Node<String> |   | Node<String> |   | Node<String> | |
| +--------------+   +--------------+   +--------------+   +--------------+ |
| | "abc"        |   | "bcd"        |   | "cde"        |   | "def"        | |
| | next ----------->| next ----------->| next ----------->| next ----------+
+-- prev         |<--- prev         |<--- prev         |<--- prev         |
  +--------------+   +--------------+   +--------------+   +--------------+


     +---------------+
     | Deque<String> |
     +---------------+
     | header ---------+
     +---------------+ |
                       |
              +--------+
+----+        |     +----+
|    |        |     |    |
|    V        V     V    |
|  +------------------+  |
|  | Sentinel<String> |  |
|  +------------------+  |
+--- next             |  |
   | prev ---------------+
   +------------------+

        +--------------------+
        | Deque<T>           |
        +--------------------+
        | Sentinel<T> header |-+
        +--------------------+ |
                               |
    +--------------------------+
    |
    |
    |      +---------------+
    |      | ANode<T>      |
    |      +---------------+
    |      | ANode<T> next |
    |      | ANode<T> prev |
    |      +---------------+
    |         /_\     /_\
    |          |       |
    |     +----+       |
    V     |            |
+-------------+      +---------+
| Sentinel<T> |      | Node<T> |
+-------------+      +---------+
+-------------+      | T data  |
                     +---------+

*/

/*
- [x] Define the classes ANode<T>, Node<T>, Sentinel<T>, and Deque<T>.

- [x] For Sentinel<T>,
			define a constructor that takes zero arguments,
			and initializes the next and prev fields of the Sentinel to the Sentinel itself.

- [x] For Node<T>,
			define two constructors:
			the first one takes just a value of type T,
			initializes the data field,
			and then initializes next and prev to null.

			The second convenience constructor should take a value of type T and two ANode<T> nodes,
			initialize the data field to the given value,
			initialize the next and prev fields to the given nodes,
			and also update the given nodes to refer back to this node.

			Throw an IllegalArgumentException in this constructor if either of the given nodes is null.
			(You can use if (theNode == null) { ... } to test for null-ness.)
			NOTE: carefully the order of the arguments in this constructor!
			The order should match the class diagram above;
			getting the order wrong will result in oddly “backwards” lists.

- [x] For Deque<T>,
			define two constructors:
			one which takes zero arguments and initializes the header to a new Sentinel<T>,
			and another convenience constructor which takes a particular Sentinel value to use.

- [x] Make examples of three lists: 
			the empty list,
			a list of Strings with the values ("abc", "bcd", "cde", and "def") shown in the drawing at the beginning of this problem,
			and a list with (at least) four values that are not ordered lexicographically.

			(HINT: If you’ve defined your constructors correctly,
			you shouldn’t need to use any explicit assignment statements in your examples class!)

			(Make more examples as needed to test the methods you define.)
			Name your examples class ExamplesDeque, and your first three examples deque1, deque2 and deque3.


## Methods

- [x] Design the method size that counts the number of nodes in a list Deque,
			not including the header node.
			(I.e., just count the Nodes and not the Sentinel.)

- [x] Design the method addAtHead for the class Deque that consumes a value of type T and inserts it at the front of the list.
			Be sure to fix up all the links correctly!

- [x] Design the method addAtTail for the class Deque that consumes a value of type T and inserts it at the tail of this list.
			Again, be sure to fix up all the links correctly!

- [x] Design the method removeFromHead for the class Deque that removes the first node from this Deque.
			Throw a RuntimeException if an attempt is made to remove from an empty list.
			Be sure to fix up all the links correctly! As with ArrayList’s remove method,
			return the item that’s been removed from the list.

- [x] Design the method removeFromHead for the class Deque that removes the first node from this Deque.
			Throw a RuntimeException if an attempt is made to remove from an empty list.
			Be sure to fix up all the links correctly! As with ArrayList’s remove method,
			return the item that’s been removed from the list.

- [x] Design the method removeFromTail for the class Deque that removes the last node from this Deque,
			analogous to removeFromHead above.
			Again, be sure to fix up all the links correctly!

- [x] You probably have duplicate code in
			addAtHead,
			addAtTail,
			removeFromHead
			and removeFromTail.
			Revise your code to abstract out any duplication into helper methods
			— most likely, helper methods on ANode<T> and its subclasses.


- [x] Design the method find for the class Deque that takes an Predicate<T> and produces the first node in this Deque for which the given predicate returns true.
			If the predicate never returns true for any value in the Deque,
			then the find method should return the header node in this Deque.

			(HINT: think carefully about the return type for find!)

- [x] Design the method removeNode for the class Deque that removes the given node from this Deque.
			(Unlike removeFromHead or removeFromTail, this method does not need to return anything. Why?)
			If the given node is the Sentinel header, the method does nothing.

			(HINT: think again about the return type from find!)

			If you’ve revised your code to remove duplication as suggested above,
			this method should be very short and simple to implement.

*/

import tester.*;

interface IPred<T>{ boolean apply(T t); }

class SameString implements IPred<String>{
	String s;
	SameString(String s){ this.s = s;}
	public boolean apply(String t) { return this.s.equals(t); }
}

class SameNode<T> implements IPred<T>{
	ANode<T> n;
	SameNode(ANode<T> n){ this.n = n;}
	// ASSUME: the data of this node and the given data is of type String
	public boolean apply(T data) { return n.getData().equals(data); }
}

class Deque<T>{
	ANode<T> header;
	Deque(){ this.header=new Sentinel<T>(); }
	Deque(ANode<T> header)
	{
		if(header instanceof Sentinel){
			this.header=header; 
		}else{
			throw new IllegalArgumentException("Dequee can only accept sentinel as header.");
		}
	}
	// counts the number of nodes in a list Deque
	int size()
	{
		if(this.header.getNext() == null){
			return 0;
		}else{
			return this.header.getNext().size();
		}
	}

	// consumes a value of type T and inserts it at the front of the list.
	void addAtHead(T newEl)
	{
		  ANode<T> currHead = this.header.getNext();
			// Create the new nod and make it point to the next first item, and the sentinel as prev.
			ANode<T> newHead = new Node<T>(currHead, this.header, newEl);
			// Make the sentinel point at the newHead.
			this.header.next = newHead;
			// Modify the prev of the previous head (currHead)
			currHead.prev = newHead;
		
	}

	// consumes a value of type T and inserts it at the tail of this list.
	void addAtTail(T newEl)
	{
		  ANode<T> currTail = this.header.getPrev();
			// Create the new node and make it point to the next first item, and the sentinel as prev.
			ANode<T> newTail = new Node<T>(this.header, currTail, newEl);
			// Make the sentinel point at the newTail.
			this.header.prev = newTail;
			// Modify the prev of the previous head (currTail)
			currTail.next = newTail;
	}

	// adding a tail or a head in a list empty, has the same logic
	// that's why this function exitst
	// EFFECT: create a new node in an empty list, with the given item
	void addFirstNode(T item){
			// Create the new nod and make it point to sentinel in both prev and next.
			ANode<T> newNode = new Node<T>(this.header, this.header, item);
			// Make the sentinel point at the newNode.
			this.header.next = newNode;
			this.header.prev = newNode;
	}

	// removing a tail or a head in a list white ONE node, has the same logic
	// that's why this function exitst
	// remove the first node in a list with one item.
	ANode<T> removeFirstNode(ANode<T> item){
			// Make the sentinel point at the newNode.
			this.header.next = null;
			this.header.prev = null;
			return item;
	}

	ANode<T> remove(String whichOne)
	{

		if(
				!whichOne.toLowerCase().equals("head")
				||
				!whichOne.toLowerCase().equals("tail")
			)
		{
			throw new RuntimeException("Can only remove items from head and tail, please use these two values.");
		}

			// If there are no items
			if(this.size() == 0)
			{
				throw new RuntimeException("Cannot remove an item from an empty list");
			}

			// If there is only one item
			if(this.header.size() == 1)
			{
				return this.removeFirstNode(this.header.getNext());
			}

			if(whichOne.toLowerCase().equals("head"))
			{
				return this.removeFromHead();
			}else
			{
				return this.removeFromTail();
			}
	}

	void add(String whichOne, T item)
	{

		if(
			whichOne.toLowerCase().equals("head") == false
			&&
			whichOne.toLowerCase().equals("tail") == false
			)
		{
			throw new RuntimeException("Can only add items to head and tail, please use these two values.");
		}

			// If the list is empty
			if(this.size() == 0)
			{
				this.addFirstNode(item);
				return;
			}

			if(whichOne.toLowerCase().equals("head"))
			{
				this.addAtHead(item);
				 return;
			}else
			{
				this.addAtTail(item);
				 return;
			}
	}

	// removes the first node from this Deque.
	// Throw a RuntimeException if an attempt is made to remove from an empty list.
	// return the item that’s been removed from the list.
	ANode<T> removeFromHead()
	{
		// Get curr head to remove from the list
		ANode<T> headToRemove = this.header.getNext();
		// Get the future new head from the list, by the next of the currHead
		ANode<T> newHead = headToRemove.getNext();
		// The header points to our new header
		this.header.next = newHead;
		// The new head point to the header
		newHead.prev = this.header;
		// Return the item that's been removed
		return headToRemove;
	}

	// removes the last node from this Deque.
	// Throw a RuntimeException if an attempt is made to remove from an empty list.
	// return the item that’s been removed from the list.
	ANode<T> removeFromTail()
	{
		// Get the current tail to remove
		ANode<T> tailToRemove = this.header.getPrev();
		// Get the future new head from the list, by the next of the currHead
		ANode<T> newTail = tailToRemove.getPrev();
		// The header points to our new header
		this.header.prev = newTail;
		// The new head point to the header
		newTail.next = this.header;
		// Return the item that's been removed
		return tailToRemove;
	}

	// find for the class Deque that takes an Predicate<T> and produces
	// the first node in this Deque for which the given predicate returns true.
	// If the predicate never returns true for any value in the Deque,
	// then the find method should return the header node in this Deque.
	ANode<T> find(IPred<T> pred)
	{
		if(this.size() == 0)
		{
			return this.header;
		}

		return this.header.getNext().find(pred);
	}
	// remove the given node from the list and return it, if it exist;
	// otherwise return the header.
	ANode<T> removeNode(ANode<T> node)
	{
		// If there is only one element, let's just reset the header
		// with next and prev pointing to null
		if(this.size() == 1)
		{
			ANode<T> nodeToRemove = this.header.getNext();
			this.header.next = null;
			this.header.prev = null;
			return nodeToRemove;
		}

		// Create the right predicate based on the giving node.
		IPred<T> pred = new SameNode<T>(node);

		// Find handle the case when the size is 0 (list empty)
		ANode<T> nodeToRemove = this.find(pred);
		if(nodeToRemove instanceof Sentinel)
		{
			return this.header;
		}else
		{
			ANode<T> prev = nodeToRemove.getPrev();
			ANode<T> next = nodeToRemove.getNext();
			prev.next = next;
			next.prev = prev;
			return nodeToRemove;
		}
	}


}

abstract class ANode<T>{
	ANode<T> next; ANode<T> prev;

	ANode(ANode<T> next, ANode<T> prev)
	{
		this.next=next;
		this.prev=prev;
	}

	abstract int size();
	abstract T getData();
	ANode<T> getNext(){ return this.next; }
	ANode<T> getPrev(){ return this.prev; }
	abstract ANode<T> find(IPred<T> pred);
}

class Node<T> extends ANode<T>{
	T data;

	Node(T data)
	{
		super(null, null);
		this.data = data;
	}

	ANode<T> find(IPred<T> pred)
	{
		if(pred.apply(this.data)){
			return this;
		}else{
			return this.getNext().find(pred);
		}
	}

	Node(ANode<T> next, ANode<T> prev, T data)
	{
		super(next, prev);
		if(prev == null || next == null){
			throw new IllegalArgumentException("The next or the prev node, cannot be null; If you inted to have null values please only pass data.");
		}
		this.data = data;
	}

	T getData(){ return this.data; }
	int size(){ return 1 + this.next.size(); }
}


class Sentinel<T> extends ANode<T>{
	// a constructor that takes zero arguments, and initializes the next and prev fields of the Sentinel to the Sentinel itself.
	Sentinel(){ super(null, null); }

	Sentinel(ANode<T> next, ANode<T> prev){ super(next, prev); }
	T getData(){ throw new RuntimeException("Sentinel doesn't have data!"); }
	int size(){ return 0; }
	// If we are there, it means we searched in the whole list and didn't find a
	// node that satisfy the pred
	ANode<T> find(IPred<T> pred) { return this; }
}

class ExamplesDeque{
	// Node
	ANode<String> n1;
	ANode<String> n2;
	ANode<String> n3;
	ANode<String> n4;
	ANode<String> n5;
	ANode<String> n6;
	ANode<String> n7;
	ANode<String> n8;
	ANode<String> n9;
	ANode<String> n10;
	ANode<String> n11;

	// Sentinel
	ANode<String> s1;
	ANode<String> s2;
	ANode<String> s3;

	// Deque
	Deque<String> d1;
	Deque<String> d2;

	// Examples required by assignmnet
	Deque<String> deque1;
	Deque<String> deque2;
	Deque<String> deque3;

	void initData(){
		this.s1 = new Sentinel<String>();

		this.s2 = new Sentinel<String>();

		this.s3 = new Sentinel<String>();

		this.n1 = new Node<String>("Hello");
		this.n2 = new Node<String>("World");
		this.n3 = new Node<String>("!");

		this.n4 = new Node<String>("abc");
		this.n5 = new Node<String>("bcd");
		this.n6 = new Node<String>("cde");
		this.n7 = new Node<String>("def");

		this.n8  = new Node<String>("qwe");
		this.n9  = new Node<String>("rty");
		this.n10 = new Node<String>("uio");
		this.n11 = new Node<String>("pas");

		this.d1 = new Deque<String>();
		this.d2 = new Deque<String>(this.s1);

		// Examples from assignment
		this.deque1 = new Deque<String>();

		this.deque2 = new Deque<String>();

		this.deque2.header = this.s2;
		this.s2.next = this.n4;
		this.s2.prev = this.n7;

		this.n4.prev = this.s2;
		this.n4.next = this.n5;

		this.n5.prev = this.n4;
		this.n5.next = this.n6;

		this.n6.prev = this.n5;
		this.n6.next = this.n7;
		
		this.n7.prev = this.n6;
		this.n7.next = this.s2;


		this.deque3 = new Deque<String>();
		
		this.deque3.header = this.s3;
		this.s3.next =  this.n8;
		this.s3.prev = this.n11;

		this.n8.prev = this.s3;
		this.n8.next = this.n9;

		this.n9.prev =  this.n8;
		this.n9.next = this.n10;

		this.n10.prev =  this.n9;
		this.n10.next = this.n11;
		
		this.n11.prev = this.n10;
		this.n11.next = this.s3;
	}


	void testException(Tester t){
		this.initData();
		t.checkException("Test for invalid field 'data' on a sentinel",
										 new RuntimeException("Sentinel doesn't have data!"),
										 this.s1,
										 "getData");

		t.checkConstructorException("Test for invalid nodes on a node",
												        new IllegalArgumentException("The next or the prev node, cannot be null; If you inted to have null values please only pass data."),
																"Node",
																null, this.s1, "hello");

		t.checkConstructorException("Test for invalid nodes on a node",
												        new IllegalArgumentException("The next or the prev node, cannot be null; If you inted to have null values please only pass data."),
																"Node",
																this.s1, null, "hello");

		t.checkConstructorException("Test for invalid nodes on a node",
												        new IllegalArgumentException("The next or the prev node, cannot be null; If you inted to have null values please only pass data."),
																"Node",
																null, null, "hello");

		t.checkConstructorException("Test for invalid header on a dequee",
												        new IllegalArgumentException("Dequee can only accept sentinel as header."),
																"Deque",
																this.n1);

		t.checkException("Test for trying to remove a node from an empty list",
										 new RuntimeException("Can only remove items from head and tail, please use these two values."),
										 this.deque1,
										 "remove",
										 "tail");
	}
		

	void testCheckSanity(Tester t){
		this.initData();

		t.checkExpect(this.n1.getData(), "Hello");
		t.checkExpect(this.n1.prev, null);
		t.checkExpect(this.n1.next, null);

		t.checkExpect(this.s1.prev, null);
		t.checkExpect(this.s1.next, null);
	}

	void testSize(Tester t){
		this.initData();

		t.checkExpect(this.deque1.size(), 0);
		t.checkExpect(this.deque2.size(), 4);
		t.checkExpect(this.deque3.size(), 4);
	}


	void testAddAtHead(Tester t){
		this.initData();

		Sentinel<String> s1_ = new Sentinel<String>();
		Deque<String> d1_ = new Deque<String>(
			s1_
		);

		t.checkExpect(this.deque1.size(), 0);

		this.deque1.add("head", "Hello");
		Node<String> n1_ = new Node<String>(s1_, s1_, "Hello");
		s1_.next = n1_;
		s1_.prev = n1_;

		t.checkExpect(s1_.next, n1_);
		t.checkExpect(s1_.prev, n1_);
		t.checkExpect(n1_.next, s1_);
		t.checkExpect(n1_.prev, s1_);
		t.checkExpect(d1_.size(), 1);


		this.deque2.add("head", "Hello");
		Node<String> n2_ = new Node<String>(this.n4, this.s2, "Hello");
		t.checkExpect(n2_.next, this.n4);
		t.checkExpect(n2_.prev, this.s2);
		// Sentinel header in deque 2
		t.checkExpect(this.s2.next, n2_);
		t.checkExpect(n2_.next, this.n4);
		t.checkExpect(this.n4.prev, n2_);

		this.deque3.add("head", "World");
		Node<String> n3_ = new Node<String>(this.n8, this.s3, "World");
		t.checkExpect(n3_.next, this.n8);
		t.checkExpect(n3_.prev, this.s3);
		// Sentinel header in deque 2
		t.checkExpect(this.s3.next, n3_);
		t.checkExpect(n3_.next, this.n8);
		t.checkExpect(this.n8.prev, n3_);
	}

	void testAddAtTail(Tester t){
		this.initData();

		Sentinel<String> s1_ = new Sentinel<String>();
		Deque<String> d1_ = new Deque<String>(
			s1_
		);

		t.checkExpect(this.deque1.size(), 0);

		this.deque1.add("tail", "Hello");
		Node<String> n1_ = new Node<String>(s1_, s1_, "Hello");
		s1_.next = n1_;
		s1_.prev = n1_;

		t.checkExpect(s1_.next, n1_);
		t.checkExpect(s1_.prev, n1_);
		t.checkExpect(n1_.next, s1_);
		t.checkExpect(n1_.prev, s1_);
		t.checkExpect(d1_.size(), 1);


		this.deque2.add("tail", "Hello");
		Node<String> n2_ = new Node<String>(this.s2, this.n7, "Hello");
		t.checkExpect(n2_.next, this.s2);
		t.checkExpect(n2_.prev, this.n7);
		// Sentinel header in deque 2
		t.checkExpect(this.s2.prev, n2_);
		t.checkExpect(n2_.next, this.s2);
		t.checkExpect(this.n7.next, n2_);

		this.deque3.add("tail", "World");
		Node<String> n3_ = new Node<String>(this.s3, this.n11, "World");
		t.checkExpect(n3_.next,  this.s3);
		t.checkExpect(n3_.prev, this.n11);
		// Sentinel header in deque 2
		t.checkExpect(this.s3.prev, n3_);
		t.checkExpect(this.n11.next, n3_);
	}

	void testRemoveFromHead(Tester t){
		this.initData();

		Deque<String> expectedDeque2 = new Deque<String>();
		ANode<String> s2_ = new Sentinel<String>();
		ANode<String> n5_ = new Node<String>("bcd");
		ANode<String> n6_ = new Node<String>("cde");
		ANode<String> n7_ = new Node<String>("def");

		expectedDeque2.header = s2_;
		s2_.next = n5_;
		s2_.prev = n7_;

		n5_.prev = s2_;
		n5_.next = n6_;

		n6_.prev = n5_;
		n6_.next = n7_;
		
		n7_.prev = n6_;
		n7_.next = s2_;

		t.checkExpect(this.deque2.removeFromHead(), this.n4);
		t.checkExpect(this.deque2, expectedDeque2);
		t.checkExpect(this.s2.next, this.n5);
		t.checkExpect(this.n5.prev, this.s2);

		t.checkExpect(this.deque3.removeFromHead(), this.n8);
		t.checkExpect(this.s3.next, this.n9);
		t.checkExpect(this.n9.prev, this.s3);
	}

	void testRemoveFromTail(Tester t){
		this.initData();

		Deque<String> expectedDeque2 = new Deque<String>();
		ANode<String> s2_ = new Sentinel<String>();
		ANode<String> n4_ = new Node<String>("abc");
		ANode<String> n5_ = new Node<String>("bcd");
		ANode<String> n6_ = new Node<String>("cde");

		expectedDeque2.header = s2_;
		s2_.next = n4_;
		s2_.prev = n6_;

		n4_.prev = s2_;
		n4_.next = n5_;

		n5_.prev = n4_;
		n5_.next = n6_;

		n6_.prev = n5_;
		n6_.next = s2_;

		t.checkExpect(this.deque2.removeFromTail(), this.n7);
		t.checkExpect(this.deque2, expectedDeque2);
		t.checkExpect(this.s2.prev, this.n6);
		t.checkExpect(this.n6.next, this.s2);

		t.checkExpect(this.deque3.removeFromTail(), this.n11);
		t.checkExpect(this.s3.prev, this.n10);
		t.checkExpect(this.n10.next, this.s3);
	}

	void testFind(Tester t){
		this.initData();

		// Function objects for deque2
		IPred<String> sABC = new SameString("abc");
		IPred<String> sBCD = new SameString("bcd");
		IPred<String> sCDE = new SameString("cde");
		IPred<String> sDEF = new SameString("def");

		// Function objects for deque3
		IPred<String> sQWE = new SameString("qwe");
		IPred<String> sRTY = new SameString("rty");
		IPred<String> sUIO = new SameString("uio");
		IPred<String> sPAS = new SameString("pas");

		// Function objects for testing when the node is not founded. 
		IPred<String> sABCDEF = new SameString("abcdef");

		t.checkExpect(this.deque1.find(sABC), this.s1);
		t.checkExpect(this.deque1.find(sABCDEF), this.s1);

		t.checkExpect(this.deque2.find(sABC), this.n4);
		t.checkExpect(this.deque2.find(sBCD), this.n5);
		t.checkExpect(this.deque2.find(sCDE), this.n6);
		t.checkExpect(this.deque2.find(sDEF), this.n7);
		t.checkExpect(this.deque2.find(sABCDEF), this.s2);

		t.checkExpect(this.deque3.find(sQWE),  this.n8);
		t.checkExpect(this.deque3.find(sRTY),  this.n9);
		t.checkExpect(this.deque3.find(sUIO), this.n10);
		t.checkExpect(this.deque3.find(sPAS), this.n11);
		t.checkExpect(this.deque3.find(sABCDEF), this.s3);
	}

	void testRemoveNode(Tester t){
		this.initData();

		// t.checkExpect(this.deque1.find(sABC), this.s1);
		// t.checkExpect(this.deque1.find(sABCDEF), this.s1);

		t.checkExpect(this.deque2.removeNode(this.n4), this.n4);
		Deque<String> expectedDeque2 = new Deque<String>();
		ANode<String> s2_ = new Sentinel<String>();
		ANode<String> n5_ = new Node<String>("bcd");
		ANode<String> n6_ = new Node<String>("cde");
		ANode<String> n7_ = new Node<String>("def");

		expectedDeque2.header = s2_;
		s2_.next = n5_;
		s2_.prev = n7_;

		n5_.prev = s2_;
		n5_.next = n6_;

		n6_.prev = n5_;
		n6_.next = n7_;
		
		n7_.prev = n6_;
		n7_.next = s2_;

		t.checkExpect(this.deque2, expectedDeque2);

		t.checkExpect(this.deque2.removeNode(this.n5), this.n5);
		s2_.next = n6_;
		n6_.prev = s2_;
		t.checkExpect(s2_.getNext(), n6_);
		t.checkExpect(n6_.getPrev(), s2_);
		t.checkExpect(this.deque2, expectedDeque2);

		t.checkExpect(this.deque2.removeNode(this.n6), this.n6);
		s2_.next = n7_;
		n7_.prev = s2_;
		t.checkExpect(s2_.getNext(), n7_);
		t.checkExpect(n7_.getPrev(), s2_);
		t.checkExpect(this.deque2, expectedDeque2);

		t.checkExpect(this.deque2.removeNode(this.n7), this.n7);
		s2_.next = null;
		s2_.prev = null;
		t.checkExpect(s2_.getNext(), null);
		t.checkExpect(s2_.getPrev(), null);
		t.checkExpect(this.deque2, expectedDeque2);

		// Remove a node that doens't exist in the list
		t.checkExpect(this.deque3.removeNode(n5_), this.s3);

		// Remove a node in a empty list
		t.checkExpect(this.deque1.removeNode(this.n1), this.s1);
	}
}
