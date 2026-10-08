/*Do Now!
	What kind of Java abstraction is most appropriate here?
*/

// > I don't know, which kinda of abstraction they are refering to.


/*Do Now!
	Implement hasNext and next.
*/

/*
  public boolean hasNext() {
    return nextIdx < items.size();
  }
 
  public T next() {
		T item = items.get(nextIdx);
		this.nextIdx = this.nextIdx + 1;
    return item;
  }
*/

/*Do Now!
	Try to implement an Iterator<T> for IList<T>.
	What state should you store instead of indices?
*/

// > T, the element itself.

/*Do Now!
	Define a DequeForwardIterator that advances through a Deque, just as we did above with an IList.
*/

// > DequeForwardIterator that advances through a Deque
/*
class DequeForwardIterator<T> implements Iterator<T>{
	ANode<T> node;
	DequeForwardIterator(ANode<T> node) {this.node = node;}
	public boolean hasNext()
	{
		return this.node instanceof Node;
	}
	public T next()
	{
		Node<T> currNode = this.node.asNode();
		T currData = currNode.data;
		this.node = this.node.next;
		return currData;
	}
	public void remove()
	{
    throw new UnsupportedOperationException("Don't do this!");
	}
}
*/

/*Do Now!
	Define a DequeReverseIterator that walks backward through a Deque from the last item to the first.
	The code should be very nearly identical to the forward iterator.
*/

/*
class DequeReverseIterator<T> implements Iterator<T>{
	ANode<T> node;
	DequeReverseIterator(ANode<T> node) {this.node = node;}
	public boolean hasNext()
	{
		return this.node instanceof Node;
	}
	public T next()
	{
		Node<T> currNode = this.node.asNode();
		T currData = currNode.data;
		this.node = this.node.prev;
		return currData;
	}
	public void remove()
	{
    throw new UnsupportedOperationException("Don't do this!");
	}
}
*/

/*
Do Now!
	Do this.
	"We can also define an iterator that takes only the first 𝑛 items from another iterator:"
*/

/*
class TakeN<T> implements Iterator<T> {
  Iterator<T> source;
  int len;
  int count;
  TakeN(Iterator<T> source, int len) {
    this.source = source;
    this.len = len;
    this.count = 0;
  }
  public boolean hasNext() {
    boolean answer = count < len;
		return this.count < this.len && this.source.hasNext();
  }
  public T next() {
		this.count += 1;
		return this.source.next();
  }
  public void remove() {
    this.source.remove();
  }
}
**Lecture implementation**
class TakeN<T> implements Iterator<T> {
  Iterator<T> source;
  int howMany;
  int countSoFar;
  TakeN(Iterator<T> source, int n) {
    this.source = source;
    this.howMany = n;
    this.countSoFar = 0;
  }

	public boolean hasNext() {
		return (this.countSoFar < this.howMany) && this.source.hasNext();
	}

	public T next() {
		this.countSoFar = this.countSoFar + 1;
		return this.source.next();
	}

  public void remove() {
    // We can remove an item if our source can remove the item
    this.source.remove(); // so just delegate to the source
  }
}
*/

/*Do Now!
class IListIterator<T> implements Iterator<T> {
  IList<T> items;
  IListIterator(IList<T> items) {
    this.items = items;
  }

  public boolean hasNext() {
    return this.items.isCons();
  }
  public T next() {
		ConsList<T> itemsAsCons = this.items.asCons();
		T answer = itemsAsCons.first;
		this.items = itemsAsCons.rest;
		return answer;
  }
  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}
	Define the isCons() and asCons() methods to complete this code.
*/

/*
  // In ConsList<T>
	public boolean isCons() { return true; }
  // In MtList<T>
	public boolean isCons() { return false; }

  // In ConsList<T>
	public asCons() { return this; }
  // In MtList<T>
	public asCons() { throw new RuntimeException("An empty list cannot be cons."); }
 */

/* Exercise
	Define a higher-order iterator that takes two iterators and alternates items from each of them.
*/

/*
//ASSUME: they got the same length
class AlternateIterator<T> implements Iterator<T> {
  Iterator<T> source1;
  Iterator<T> source2;
  int len;
  int count;
  Iterator<T> curr;

  AlternateIterator(Iterator<T> source1, Iterator<T> source2, int len) {
    this.source1 = source1;
    this.source2 = source2;
    this.len = len;
    this.count = 0;
		this.curr = source1;
  }

  public boolean hasNext() {
			return this.count < this.len && this.curr.hasNext();
  }

  public T next() {
		T answer = this.curr.next();
		this.count += 1;
		if(count % 2 == 0){
			this.curr = this.source1;
		}else{
			this.curr = this.source2;
		}
		return answer;
  }

  public void remove() {
    this.curr.remove();
  }
}
*/


/*Do Now!
	Try implementing a PreOrderIterator for a tree. The code is very similar to BreadthFirstIterator.
*/

/*
class PreOrderIterator<T> implements Iterator<T> {
  Deque<IBinaryTree<T>> worklist;
  PreOrderIterator(IBinaryTree<T> source) {
    this.worklist = new Deque<IBinaryTree<T>>();
    this.addIfNotLeaf(source);
  }
  // EFFECT: only adds the given binary-tree if it's not a leaf
  void addIfNotLeaf(IBinaryTree bt) {
    if (bt.isNode()) {
      this.worklist.addAtHead(bt); 
    }
  }
  public boolean hasNext() {
    // we have a next item if the worklist isn't empty
    return this.worklist.size() > 0;
  }
  public T next() {
    // Get (and remove) the first item on the worklist --
    // and we know it must be a BTNode
    BTNode<T> node = this.worklist.removeAtHead().asNode();
		// Add the children of the node to the head of the list
    this.addIfNotLeaf(node.right); 
    this.addIfNotLeaf(node.left); 
    // return the answer
    return node.data;
  }
	public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}
*/

/*Exercise
	Try implementing post-order and in-order traversals as iterators.
	They are somewhat subtler than the two we have done so far; in particular,
	figuring out what to add to the worklist is tricky.
*/

/*
 NOTE: Not tested
class PostOrderIterator<T> implements Iterator<T> {
  Deque<IBinaryTree<T>> worklist;
  PreOrderIterator(IBinaryTree<T> source) {
    this.worklist = new Deque<IBinaryTree<T>>();
    this.addIfNotLeaf(source);
  }
  // EFFECT: only adds the given binary-tree if it's not a leaf
	// Build recursively the worklist, until we found the leaf.
  void addIfNotLeaf(IBinaryTree bt) {
    if (bt.isNode()) {
      this.worklist.addAtHead(bt); 
			this.addIfNotLeaf(bt.getRightNode());
			this.addIfNotLeaf(bt.getLeftNode());
    }
  }
  public boolean hasNext() {
    // we have a next item if the worklist isn't empty
    return this.worklist.size() > 0;
  }
  public T next() {
    // Get (and remove) the first item on the worklist --
    // and we know it must be a BTNode
    BTNode<T> node = this.worklist.removeAtHead().asNode();
    // return the answer
    return node.data;
  }
	public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

 NOTE: Not tested
class  InOrderIterator<T> implements Iterator<T> {
  Deque<IBinaryTree<T>> worklist;
  PreOrderIterator(IBinaryTree<T> source) {
    this.worklist = new Deque<IBinaryTree<T>>();
    this.addIfNotLeaf(source);
  }

  // EFFECT: only adds the given binary-tree if it's not a leaf
  void addIfNotLeaf(IBinaryTree bt) {
    if (bt.isNode()) {
      this.worklist.addAtHead(bt); 
    }
  }

  // EFFECT: add the given bt before the given refNode
  void AddNodeAfter(IBinaryTree bt, BTNode<T> refNode) {
    if (bt.isNode()) {
      this.worklist.addAfter(bt, refNode);
    }
  }

  // EFFECT: add the given bt after the given refNode
  void AddNodeBefore(IBinaryTree bt, BTNode<T> refNode) {
    if (bt.isNode()) {
      this.worklist.addBefore(bt, refNode); 
    }
  }

  public boolean hasNext() {
    // we have a next item if the worklist isn't empty
    return this.worklist.size() > 0;
  }
  public T next() {
    // Get (and remove) the first item on the worklist --
		// and we know it must be a BTNode
    BTNode<T> node = this.worklist.getHead();
		// Add the children of the node to the head of the list
    this.addBefore(node.left, node); 
    this.addAfter(node.right, node); 
		// Remove the first item on the worklist
		// and we know it must be a BTNode
    BTNode<T> headNode = this.worklist.removeAtHead().asNode();
    // return the answer
    return node.data;
  }
	public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

	Not tested beacuse there is no data about "BTNode", "IBinaryTree"
 */


import java.util.ArrayList;
import java.util.Iterator;
import tester.*;

//ASSUME: they got the same length
class AlternateIterator<T> implements Iterator<T> {
  Iterator<T> source1;
  Iterator<T> source2;
  int len;
  int count;
  Iterator<T> curr;

  AlternateIterator(Iterator<T> source1, Iterator<T> source2, int len) {
    this.source1 = source1;
    this.source2 = source2;
    this.len = len;
    this.count = 0;
		this.curr = source1;
  }

  public boolean hasNext() {
			return this.count < this.len && this.curr.hasNext();
  }

  public T next() {
		T answer = this.curr.next();
		this.count += 1;
		if(count % 2 == 0){
			this.curr = this.source1;
		}else{
			this.curr = this.source2;
		}
		return answer;
  }

  public void remove() {
    this.curr.remove();
  }
}

interface IFunc<T, U>
{
	U apply(T t);
}


interface IFunc1<T>
{
	T apply(T t);
}



interface IList<T> extends Iterable<T> 
{
	<U> IList<U> map(IFunc<T, U> fn);
	IList<T> add(T t);
	boolean isCons();
	ConsList<T> asCons();
}

class IListIterator<T> implements Iterator<T> {
  IList<T> items;
  IListIterator(IList<T> items) {
    this.items = items;
  }

  public boolean hasNext() {
    return this.items.isCons();
  }
  public T next() {
		ConsList<T> itemsAsCons = this.items.asCons();
		T answer = itemsAsCons.first;
		this.items = itemsAsCons.rest;
		return answer;
  }
  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}


class ArrayListIterator<T> implements Iterator<T> {
  // the list of items that this iterator iterates over
  ArrayList<T> items;
  // the index of the next item to be returned
  int nextIdx;
  // Construct an iterator for a given ArrayList
  ArrayListIterator(ArrayList<T> items) {
    this.items = items;
    this.nextIdx = 0;
  }
 
  public boolean hasNext() {
    return nextIdx < items.size();
  }
 
  public T next() {
		T item = items.get(nextIdx);
		this.nextIdx = this.nextIdx + 1;
    return item;
  }
 
  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

class DequeForwardIterator<T> implements Iterator<T>{
	ANode<T> node;
	DequeForwardIterator(ANode<T> node) {this.node = node;}
	public boolean hasNext()
	{
		return this.node instanceof Node;
	}
	public T next()
	{
		Node<T> currNode = this.node.asNode();
		T currData = currNode.data;
		this.node = this.node.next;
		return currData;
	}
	public void remove()
	{
    throw new UnsupportedOperationException("Don't do this!");
	}
}

class DequeReverseIterator<T> implements Iterator<T>{
	ANode<T> node;
	DequeReverseIterator(ANode<T> node) {this.node = node;}
	public boolean hasNext()
	{
		return this.node instanceof Node;
	}
	public T next()
	{
		Node<T> currNode = this.node.asNode();
		T currData = currNode.data;
		this.node = this.node.prev;
		return currData;
	}
	public void remove()
	{
    throw new UnsupportedOperationException("Don't do this!");
	}
}

class MultSelf implements IFunc<Integer, Integer>{
	public Integer apply(Integer n){
		return n * n;
	}
}



class ConsList<T> implements IList<T>
{
	T first; IList<T> rest;
	ConsList(T first, IList<T> rest)
	{
		this.first=first;
		this.rest=rest;
	}
	public Iterator<T> iterator() { return new IListIterator<T>(this); }
	public <U> IList<U> map(IFunc<T, U> fn) 
	{
		IList<U> newList = new MtList<U>();
		for(T t : this){
			newList = newList.add(fn.apply(t));
		}
		return newList;
	}
	public IList<T> add(T t){ return new ConsList<T>(this.first, this.rest.add(t)); }
	public boolean isCons() { return true; }
	public ConsList<T> asCons() { return this; }
}

class MtList<T> implements IList<T>
{
	MtList(){}

  public Iterator<T> iterator() { return new IListIterator<T>(this); }
	public boolean isCons() { return false; }
	public ConsList<T> asCons() { throw new RuntimeException("An empty list cannot be cons."); }
	public <U> IList<U> map(IFunc<T, U> fn) { throw new RuntimeException("Cannot map an empty list"); }
	public IList<T> add(T t){ return new ConsList<T>(t, this); }
}

class Up implements IFunc1<String>
{
	public String apply(String s) { return s.toUpperCase(); }
}

class ExamplesIterator{
	ArrayList<String> los;
	ArrayListIterator<String> iter;

	IList<Integer> lon;
	IList<Integer> mtLon;

	ANode<String> n4;
	ANode<String> n5;
	ANode<String> n6;
	ANode<String> n7;

	ANode<String> s2;

	Deque<String> deque2;

	void initData()
	{
		this.deque2 = new Deque<String>();
		this.s2 = new Sentinel<String>();

		this.n4 = new Node<String>("abc");
		this.n5 = new Node<String>("bcd");
		this.n6 = new Node<String>("cde");
		this.n7 = new Node<String>("def");

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

		this.los = new ArrayList<String>();
		this.iter = new ArrayListIterator<String>(los);
		los.add("a"); los.add("b"); los.add("c");

		this.mtLon = new MtList<Integer>();
		this.lon = new ConsList<Integer>(1, new ConsList<Integer>(2, new ConsList<Integer>(3, this.mtLon)));
	}

	void testIterator(Tester t)
	{
		this.initData();
		IFunc<Integer, Integer> mS = new MultSelf();
		IList<Integer> expected = new ConsList<Integer>(1, new ConsList<Integer>(4, new ConsList<Integer>(9, this.mtLon)));
		t.checkExpect(this.lon.map(mS), expected);

		IFunc1<String> uP = new Up();
		// this.deque2.allUp(uP);

		ANode<String> s_ = new Sentinel<String>();
		Deque<String> d_ = new Deque<String>(s_);

		ANode<String> n4_ = new Node<String>("abc");
		ANode<String> n5_ = new Node<String>("bcd");
		ANode<String> n6_ = new Node<String>("cde");
		ANode<String> n7_ = new Node<String>("def");
		s_.next = n7_;
		s_.prev = n4_;

		n7_.prev = s_;
		n7_.next = n6_;

		n6_.prev = n7_;
		n6_.next = n5_;

		n5_.prev = n6_;
		n5_.next = n4_;
		
		n4_.prev = n5_;
		n4_.next = s_;

		// NOTE:  isn't the best way to check this!
		Deque<String> reverseDeque = this.deque2.allUpReverse(uP);
		ANode<String> header = reverseDeque.header;
		t.checkExpect(header.next.getData(), "def");
		ANode<String> first = header.next;
		t.checkExpect(first.next.getData(),  "cde");
		ANode<String> second = first.next;
		t.checkExpect(second.next.getData(), "bcd");
		ANode<String> third = second.next;
		t.checkExpect(third.next.getData(),  "abc");


		// Alternate Iterator
		IList<Integer> lon1 = new ConsList<Integer>(1, 
		new ConsList<Integer>(2, 
		new ConsList<Integer>(3, 
		new ConsList<Integer>(4, 
		new MtList<Integer>()))));

		IList<Integer> lon2 = new ConsList<Integer>(4, 
		new ConsList<Integer>(5, 
		new ConsList<Integer>(6, 
		new ConsList<Integer>(7, 
		new MtList<Integer>()))));

		Iterator<Integer> aI = new AlternateIterator<Integer>(lon1.iterator(), lon2.iterator(), 4);

		Integer sum = 0;
		while(aI.hasNext()){
			Integer el = aI.next();
			sum += el;
		}

		t.checkExpect(sum, 12);
	}
}
