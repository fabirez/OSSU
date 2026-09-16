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


import java.util.ArrayList;
import java.util.Iterator;
import tester.*;



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




class Deque<T> implements Iterable<T>{
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

	public Iterator<T> iterator() { return new DequeForwardIterator<T>(this.header.next); }
	public Iterator<T> iteratorReverse() { return new DequeReverseIterator<T>(this.header.prev); }
	public void allUp(IFunc1<T> fn)
	{
		for(T t: this)
		{
			t = fn.apply(t);
		}
	}

	public Deque<T> allUpReverse(IFunc1<T> fn)
	{
		ANode<T> newHeader = new Sentinel<T>();
		Deque<T> newDeque = new Deque<T>(newHeader);
		Iterator<T> iterator = this.iteratorReverse();
		ANode<T> prev = newHeader;
		while( iterator.hasNext() )
		{
			T t = iterator.next();
			if(prev instanceof Sentinel){
				newHeader.next = new Node<T>(t);
				prev = newHeader.next;
				prev.prev = newHeader;
			}else{
				System.out.println(t);
				prev.next = new Node<T>(t);
				prev.prev = prev;
				prev = prev.next;
			}
		}
		// Setting last node, pointing to the header.
		prev.next = newHeader;

		return newDeque;
	}
}


abstract class ANode<T>{
	ANode<T> next; ANode<T> prev;

	ANode(ANode<T> next, ANode<T> prev)
	{
		this.next=next;
		this.prev=prev;
	}
	abstract Node<T> asNode();
	abstract T getData();
}

class Node<T> extends ANode<T>{
	T data;

	Node(T data)
	{
		super(null, null);
		this.data = data;
	}
	Node<T> asNode(){ return this; }
	T getData(){ return this.data; }
}


class Sentinel<T> extends ANode<T>{
	// a constructor that takes zero arguments, and initializes the next and prev fields of the Sentinel to the Sentinel itself.
	Sentinel(){ super(null, null); }
	Node<T> asNode(){ throw new RuntimeException("Sentinel is not a node"); }
	T getData(){  throw new RuntimeException("Sentinel has no data"); }
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
	}
}
