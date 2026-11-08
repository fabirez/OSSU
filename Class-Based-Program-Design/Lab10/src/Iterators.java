/*
### Problem 3: Iterators

Create a class `ListOfLists<T>` that stores an `ArrayList` of `ArrayList<T>`.
This class must have the following methods:

- [x] A method `void addNewList()` that adds a new empty ArrayList\<T> to the end of the list-of-lists.
- [x] A method `void add(int index,T object)` that adds the provided object to the end of the ArrayList\<T> at the provided index in teh list-of-lists. If the index is invalid, this method should throw an IndexOutOfBoundsException with a suitable message.
- [x] A method `ArrayList<T> get(int index)` that returns the ArrayList\<T> at the provided index in the list-of-lists. If the index is invalid, this method should throw an IndexOutOfBoundsException with a suitable message.
- [x] A method `int size()` that returns the number of lists in this list-of-lists.

Now make the `ListOfLists<T>` iterable. The intended behavior should be as follows:

*/

import tester.*;
import java.util.ArrayList;
import java.util.Iterator;

class ListOfListsIterator<T> implements Iterator<T>
{
	ArrayList<ArrayList<T>> items;
	int nextIdx;
	int nextItemIdx;

	ListOfListsIterator(ListOfLists<T> lol)
	{
		this.items = lol.a;
		this.nextIdx = 0;
		this.nextItemIdx = 0;
	}

	// return true, if the next value is NOT empty
	// otherwise false, and EFFECT: add +1 at this.nextIdx
	public void jumpEmpty(){
		for (int i = this.nextIdx;
		i < this.items.size();
		i++){
			// check if the current list-of-list is not empty, and if the next (curr item) that shoudl return, exist in the list or we already saw all of them
			if(this.items.get(i).size() > 0 && this.nextItemIdx < this.items.get(i).size()){ 
				return; 
			}
			this.nextIdx = this.nextIdx +1;
			this.nextItemIdx = 0;
		}
	}

	// Do we have the next value in this list ?
	public boolean hasNext() { 
		this.jumpEmpty();
		return this.nextIdx < this.items.size();
	}

	// Do we have the next value in this list ?
	public boolean hasNextItem() { return this.nextItemIdx < this.items.get(this.nextIdx).size(); }

	// Produce the next value
	// EFFECT: mutate the value of nextIdx by adding one.
	public T next()
	{
		T item = this.items.get(this.nextIdx).get(this.nextItemIdx);
		this.nextItemIdx = this.nextItemIdx + 1;
		return item;
	}

	 public void remove() { throw new UnsupportedOperationException("Don't do this!"); }
}

class ListOfLists<T> implements Iterable<T>{
	ArrayList<ArrayList<T>> a;

	ListOfLists(ArrayList<ArrayList<T>> a){ this.a = a; }


	ListOfLists(){ this.a = new ArrayList<ArrayList<T>>(); }

	// add a new empty arraylist at the end of the *list-of-list*
	void addNewList(){ a.add(new ArrayList<T>()); }

	// adds the provided object to the end of the ArrayList<T> at the provided index in teh list-of-lists.
	void add(int index, T object){ this.get(index).add(object); }

  // returns the ArrayList<T> at the provided index in the list-of-lists.
	// If the index is invalid, this method should throw an IndexOutOfBoundsException with a suitable message.
	ArrayList<T> get(int index){
		// If the index is not valid, throw an exception
		if(index >= this.a.size() || index < 0){
			throw new IndexOutOfBoundsException("The given index is out of boundaries");
		}else{
			// Get the array at the given index
			return this.a.get(index);
		}
	}
	// that returns the number of lists in this list-of-lists
	int size(){ return this.a.size();} 

  public Iterator<T> iterator() { return new ListOfListsIterator<T>(this); }
}

class ExamplesIterator{
	ListOfLists<Integer> lol;

	void initData(){
		this.lol = new ListOfLists<Integer>();
	}

	void testAddNewList(Tester t){
		this.initData();
		t.checkExpect(this.lol, new ListOfLists<Integer>());
		t.checkExpect(this.lol.size(), 0);
		this.lol.addNewList();
		t.checkExpect(this.lol.size(), 1);
		this.lol.addNewList();
		t.checkExpect(this.lol.size(), 2);
	}

	void testAdd(Tester t){
		this.initData();
		this.lol.addNewList();
		this.lol.addNewList();

		// Add 1 in the first (0) list-of-list
		this.lol.add(0,1);
		// Add 2 in the first (0) list-of-list
		this.lol.add(0,2);
		t.checkExpect(this.lol.get(0).get(0), 1);
		t.checkExpect(this.lol.get(0).get(1), 2);

		// Add 3 in the second (1) list-of-list
		this.lol.add(1,3);
		// Add 4 in the second (1) list-of-list
		this.lol.add(1,4);
		t.checkExpect(this.lol.get(1).get(0), 3);
		t.checkExpect(this.lol.get(1).get(1), 4);

		// Add 5 in the third (2) list-of-list
		// (Not possible, since we have only two list)
		t.checkException(new IndexOutOfBoundsException("The given index is out of boundaries"), this.lol, "add", 2,5);
		// Add 6 in the third (2) list-of-list
		// (Not posisble, since we have only two list)
		t.checkException(new IndexOutOfBoundsException("The given index is out of boundaries"), this.lol, "add", 2,6);
	}

	void testGet(Tester t){
		this.initData();

		this.lol.addNewList();
		this.lol.addNewList();
		ArrayList<Integer> expected = new ArrayList<Integer>();
		// Get the first (0) list-of-list
		t.checkExpect(this.lol.get(0), expected);
		// Get the second (1) list-of-list
		t.checkExpect(this.lol.get(1), expected);
		// Get the second (2) list-of-list
		// (Not possible, since we have only two list)
		t.checkException(new IndexOutOfBoundsException("The given index is out of boundaries"), this.lol, "get", 2);
	}

	void testSize(Tester t){
		this.initData();
		t.checkExpect(this.lol.size(), 0);
		this.lol.addNewList();
		t.checkExpect(this.lol.size(), 1);
		this.lol.addNewList();
		t.checkExpect(this.lol.size(), 2);
		this.lol.addNewList();
		this.lol.addNewList();
		t.checkExpect(this.lol.size(), 4);
		this.lol.add(0,1);
		this.lol.add(0,2);
		t.checkExpect(this.lol.size(), 4);
	}


	void testListOfLists(Tester t) {
			ListOfLists<Integer> lol = new ListOfLists<Integer>();
			//add 3 lists
			lol.addNewList();
			lol.addNewList();
			lol.addNewList();

			//add elements 1,2,3 in first list
			lol.add(0,1);
			lol.add(0,2);
			lol.add(0,3);

			//add elements 4,5,6 in second list
			lol.add(1,4);
			lol.add(1,5);
			lol.add(1,6);

			//add elements 7,8,9 in third list
			lol.add(2,7);
			lol.add(2,8);
			lol.add(2,9);

			//iterator should return elements in order 1,2,3,4,5,6,7,8,9
			int number = 1;
			for (Integer num:lol) {
					t.checkExpect(num,number);
					number = number + 1;
			}
	}
}

