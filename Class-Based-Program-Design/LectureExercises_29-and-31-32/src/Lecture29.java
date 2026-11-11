/*Do Now!
	Would using an ArrayList<Integer> instead of a Deque<Integer> or IList<Integer> be any more efficient?
*/

// > No, we will have the same problem of the Deque.


/* Do Now!
	Is there another data structure we have seen where accessing one particular value is extremely efficient (i.e., constant time), and accessing any item is still more efficient than 𝑂(𝑛) ?
*/

// > Hashmap ?



/*Do Now!
	Confirm that this tree is full and that it obeys the heap-ordering property.

           80
       /       \
      60        50
    /    \    /    \
   30    50  40    20
  /  \   /
10  20  15

*/

// > Is full beacuse every level is *complete* (every subtree has 2 childrens)
// > Only the last level, is not complete but must be filled from left to right.
// > Heap-ordering property, in this case the max heap satisfy the parent >= children

/*Do Now!
	If we add 70 next to the 15, what should we do to restore the heap-ordering property?
*/

// > Swap it with the smalles value from the above level

/*Do Now!
	Insert 90 into the final heap above.
*/

/*
           90
       /        \
     70          80
    /  \        /  \
  30   60     50   20
 / \   / \    /
10 20 15 50  40

*/

/* Do Now!
	Remove the next maximum value from the heap above.
*/

/*
          70
       /     \
      60      50
    /  \     /  \
  30   50  40   20
 / \   / 
10 20 15
*/
/*Do Now!
	What’s accumulating in the tail of the ArrayList, after each removal?
*/

// > The maximum values of the heap, in another perspective a sorted ArrayList of integers.

/*Exercise

```
To heapsort a list:
   Build a heap from all the items in the list.
   Remove all the items from the heap.

To build a heap from an ArrayList:
   For each i from 1 to the size of the ArrayList,
       upheap(i)

To upheap at index idx:
   Let parentIdx = floor((idx - 1) / 2)
   If the item at index idx is greater than the item at index parentIdx:
       Swap items at indices idx and parentIdx
       upheap(parentIdx)

To remove the maximum item:
   Swap the item at index 0 with the item at the index of the last heap item
   downheap(0)

To downheap at index idx:
   Let leftIdx = 2 * idx + 1
   Let rightIdx = 2 * idx + 2
   If the item at index idx is less than either of the items at leftIdx or rightIdx:
      Let biggestIdx = index of the largest of the items at leftIdx or rightIdx
      Swap items at indices idx and biggestIdx
      downheap(biggestIdx)
```

- [ ] Implement all the algorithms sketched above in Java.
- [ ] Try working through both versions of buildHeap, using the data [1, 2, 3, 4, 5, 6, 7, 8, 9]. Do you wind up with the same heaps? If not, do you nevertheless wind up with two valid heaps?
- [ ] Try finishing the heapsort algorithm on the two heaps you just obtained.
- [ ] Try working through heapsort on the data [9, 8, 7, 6, 5, 4, 3, 2, 1]. (Hint: buildHeap should be particularly easy!)
*/


import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Integer;
import tester.*;

interface IComparator<T> 
{
  // Returns a negative number if t1  < t2
  // Returns zero              if t1 == t2
  // Returns a positive number if t1  > t2
	int compare(T t1, T t2); 
}

class CompNumber implements IComparator<Integer>{
	public int compare(Integer n1, Integer n2){ return n1 - n2; }
}

class Heap<T>{
	ArrayList<T> items;
	IComparator<T> comparator;
	Heap(ArrayList<T> items, IComparator<T> comparator){ this.items = items; this.comparator = comparator; }
	// NOTE: Assuming the heap as items of type Integer.
	Heap(IComparator<T> comparator){ this.items = new ArrayList<T>(); this.comparator = comparator; }

	// return all the items in the heap
	ArrayList<T> getItems(){ return this.items; }

	// return the size
	int size(){ return this.items.size(); }

	void add(T el){
		this.items.add(el);
		if(this.size() == 1){ return; }
		this.rightPlace(el, this.items.size() - 1);
	}

	// Swap the given elements
	// (first arg) with the (second arg) and viceversa
	void swap(int elIDX, int parentIDX){
	  if(parentIDX < 0 || elIDX < 0 || parentIDX >= this.size() || elIDX >= this.size()){
				throw new RuntimeException("The given index is greater or equal than the length or is less than zero.");
		}
		
		T temp = this.items.get(elIDX);
		this.items.set(elIDX, this.items.get(parentIDX));
		this.items.set(parentIDX, temp);
	}

	void rightPlace(T el, int elIDX){
		int parentIDX = (int) Math.floor((elIDX - 1) / 2);
		if(parentIDX < 0){
			// Then the new element is the root.
			return; 	
		}
		T parent = this.items.get(parentIDX);
		if( this.comparator.compare(parent, el) >= 0 ) return;
		this.swap(elIDX, parentIDX);
		this.rightPlace(el, parentIDX);
	}

	// return the greatest children if exist; otherwise null.
	//	EFFECT: mutate the given hashmap with children as a key, and the index as a value.
	T getGreatestChildren(int currIDX, HashMap<T, Integer> res){
		int size = this.size();

		int lChildIDX = (2 * currIDX) + 1;
		int rChildIDX = (2 * currIDX) + 2;

		// If both are not valid, return void. 
		//	(if the size == 0, if we are at the last level, if we are the only element left (leaf);
		if ( (lChildIDX < 0 || lChildIDX >= size) && (rChildIDX < 0 || rChildIDX >= size) ) return null;

		// If left doens't exit, return the right.
		if(lChildIDX < 0 || lChildIDX >= size) {
				res.put(this.items.get(rChildIDX), rChildIDX);
				return this.items.get(rChildIDX);
		}else if(rChildIDX < 0 || rChildIDX >= size) {
		// If right doens't exit, return the left.
				res.put(this.items.get(lChildIDX), lChildIDX);
				return this.items.get(lChildIDX);
		}else{
		// If both are valid, then continue.
		
			T lChild = this.items.get(lChildIDX);
			T rChild = this.items.get(rChildIDX);
			// Get the greatest children
			T gChild = null;
			int gChildIDX = -1;

			if(this.comparator.compare(lChild,rChild) <= 0){
				gChild = rChild;
				gChildIDX = rChildIDX;
			}else{
				gChild = lChild;
				gChildIDX = lChildIDX;
			}
			res.put(gChild, gChildIDX);
			return gChild;
		}

	}

	void rightPlaceFromTop(int currIDX){
		T curr = this.items.get(currIDX);

		HashMap<T, Integer> greatest = new HashMap<T, Integer>();
		T gChild = this.getGreatestChildren(currIDX, greatest);
		if(gChild == null) return;
		int gChildIDX = greatest.get(gChild);


		// if the new element is >= than the greatest child, then it's in the right place,
		// We are satisfying the max-heap property.
		if(this.comparator.compare(curr, gChild) >= 0) return;
		this.swap(currIDX, gChildIDX);
		// The new index of the element that need to find the place, is now the one
		// of the prev greatecstChildren
		this.rightPlaceFromTop(gChildIDX);
	}

	void remove(){
		if(this.size() <= 0) return;

		int lastIDX = this.size() - 1;
		int currIDX = 0;

		// NOTE: i keep this part of the code here instead of abastracting with rightPlaceFromTop,
		// beacuse it's the only time we need to **remove** an element from the array.

		// Swap the firt item (root), with the last item
		this.swap(currIDX, lastIDX);
		// Remove the root (most valuable element) from the Heap
		this.items.remove(lastIDX);
		// Check if heap had only one item, after the remove.
		if(this.size() <= 0) return;
		// Find the right position for the new element that now is in the root position
		rightPlaceFromTop(currIDX);

	}

	// To heapsort a list:
	//    Build a heap from all the items in the list.
	//    Remove all the items from the heap.

	// produce a new heap sorted based on this one
	Heap<T> heapsort(){
		Heap<T> res = new Heap<T>(this.comparator);

		while(this.size() > 0){
			T root = this.items.get(0);
			res.add(root);
			this.remove();
		}

		return res;

	}

}

class ExamplesHeap{
	Heap<Integer> h1;
	Heap<Integer> h2;
	Heap<Integer> h3;

	void initData(){
		this.h1 = new Heap<Integer>(new ArrayList<Integer>(), new CompNumber());
		this.h2 = new Heap<Integer>(new ArrayList<Integer>(), new CompNumber());
		h2.add(30); h2.add(20); h2.add(10);
	  //				 30
	  //			 /   \
	  //			20   10
		this.h3 = new Heap<Integer>(new ArrayList<Integer>(), new CompNumber());
		h3.add(80); h3.add(70); h3.add(60); h3.add(40); h3.add(30); h3.add(10); 
		h3.add(50); h3.add(20);
	  //				 80
	  //			 /   \
	  //			70   60
	  //		 / \   / \ 
	  //		40 30 10 50
	  //	 /
	  //	20
	}

	void testSwap(Tester t){
		this.initData();
		ArrayList<Integer> exp = new ArrayList<Integer>();
		exp.add(20); exp.add(30); exp.add(10);
		// Swap the first index with the second
		this.h2.swap(0, 1);
		t.checkExpect(this.h2.getItems(), exp);
		// Again, undo the prev swap.
		this.h2.swap(0, 1);
		t.checkExpect(this.h2.getItems(), this.h2.getItems());

		t.checkException(new RuntimeException("The given index is greater or equal than the length or is less than zero."),
		this.h2, "swap", -1, 1);

		t.checkException(new RuntimeException("The given index is greater or equal than the length or is less than zero."),
		this.h2, "swap", 0, -1);

		t.checkException(new RuntimeException("The given index is greater or equal than the length or is less than zero."),
		this.h2, "swap", this.h2.size(), 1);

		t.checkException(new RuntimeException("The given index is greater or equal than the length or is less than zero."),
		this.h2, "swap", 0, this.h2.size());


		t.checkException(new RuntimeException("The given index is greater or equal than the length or is less than zero."),
		this.h2, "swap", this.h2.size() + 1, 1);

		t.checkException(new RuntimeException("The given index is greater or equal than the length or is less than zero."),
		this.h2, "swap", 0, this.h2.size() + 1);

		this.initData();
		ArrayList<Integer> exp1 = new ArrayList<Integer>();
		exp1.add(10); exp1.add(20); exp1.add(30);
		this.h2.swap(0, this.h2.size() - 1);
		t.checkExpect(this.h2.getItems(), exp1);

		this.initData();
		this.h2.swap(this.h2.size() - 1, 0);
		t.checkExpect(this.h2.getItems(), exp1);

	}


	void testAdd(Tester t){
		this.initData();
		//	  30
		// 	 / \
		// 	20 10
		this.h2.add(40);
		//			40
		//		 / \
		//		30 10
		//	 /
		//	20
		ArrayList<Integer> exp = new ArrayList<Integer>();
		exp.add(0, 40); exp.add(1, 30); exp.add(2, 10); exp.add(3, 20);
		t.checkExpect(this.h2.getItems(), exp);

		this.h2.add(50);
		//			50
		//		 / \
		//		40 10
		//	 / \
		//	20 30
		ArrayList<Integer> exp1 = new ArrayList<Integer>();
		exp1.add(0, 50); exp1.add(1, 40); exp1.add(2, 10); exp1.add(3, 20); exp1.add(4, 30);
		t.checkExpect(this.h2.getItems(), exp1);

		this.h2.add(60);
		//			 60
		//		 /   \
		//		40   50
		//	 / \   /
		//	20 30 10
		ArrayList<Integer> exp2 = new ArrayList<Integer>();
		exp2.add(0, 60); exp2.add(1, 40); exp2.add(2, 50); exp2.add(3, 20); exp2.add(4, 30); exp2.add(5, 10);
		t.checkExpect(this.h2.getItems(), exp2);

		this.h2.add(80);
		// 		  80
		// 	  /    \
		// 	 40    60
		//  / \   / \ 
		// 20 30 10 50
		ArrayList<Integer> exp3 = new ArrayList<Integer>();
		exp3.add(0, 80); exp3.add(1, 40); exp3.add(2, 60); exp3.add(3, 20); exp3.add(4, 30); exp3.add(5, 10); exp3.add(6, 50);
		t.checkExpect(this.h2.getItems(), exp3);
 
		this.h2.add(70);
	  //				 80
	  //			 /   \
	  //			70   60
	  //		 / \   / \ 
	  //		40 30 10 50
	  //	 /
	  //	20
		ArrayList<Integer> exp4 = new ArrayList<Integer>();
		exp4.add(0, 80); exp4.add(1, 70); exp4.add(2, 60); exp4.add(3, 40); exp4.add(4, 30); exp4.add(5, 10); exp4.add(6, 50);exp4.add(7, 20);
		t.checkExpect(this.h2.getItems(), exp4);
	}

	void testRemove(Tester t){
		this.initData();
		// H1: Is Empty
		//this.h1.remove();
		//t.checkExpect(this.h1.getItems(), new ArrayList<Integer>());

		// H2:
		//	 30
		// 	 / \
		// 	20 10
		this.h2.remove();
		//	  20
		// 	 /
		// 	10 
		ArrayList<Integer> exp1 = new ArrayList<Integer>();
		exp1.add(0,20); exp1.add(1,10);
		t.checkExpect(this.h2.getItems(), exp1);
		this.h2.remove();
		// 	10 
		ArrayList<Integer> exp2 = new ArrayList<Integer>();
		exp2.add(0,10);
		t.checkExpect(this.h2.getItems(), exp2);

		this.h2.remove();
		// 	Empty queue, removing the last element (10)
		ArrayList<Integer> exp3 = new ArrayList<Integer>();
		t.checkExpect(this.h2.getItems(), exp3);

		// H3:
	  //				 80
	  //			 /   \
	  //			70   60
	  //		 / \   / \ 
	  //		40 30 10 50
	  //	 /
	  //	20
		this.h3.remove();
	  //				 70
	  //			 /   \
	  //			40   60
	  //		 / \   / \ 
	  //		20 30 10 50
		ArrayList<Integer> exp = new ArrayList<Integer>();
		exp.add(0,70); exp.add(1,40); exp.add(2,60); 
		exp.add(3,20); exp.add(4,30); exp.add(5,10);
		exp.add(6,50);
		t.checkExpect(this.h3.getItems(), exp);
	}


	// NOTE: NEED TO START WITH A NORMAL LIST!
	void testHeapSort(Tester t){

		this.initData();
		ArrayList<Integer> exp = new ArrayList<Integer>();
		// Sorting on a empty heap.
		t.checkExpect(this.h1.heapsort(), this.h1);

		// H2:
		//	 30
		// 	 / \
		// 	20 10
	}
}
