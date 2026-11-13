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

- [x] Implement all the algorithms sketched above in Java.
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

	// Used only for heapsort
	int heapSize;
	Heap(ArrayList<T> items, IComparator<T> comparator){ this.items = items; this.comparator = comparator; this.heapSize = 0; }
	// NOTE: Assuming the heap as items of type Integer.
	Heap(IComparator<T> comparator){ this.items = new ArrayList<T>(); this.comparator = comparator; this.heapSize = 0; }

	// TODO: check if you need this
	// get the current size, and remove the given tail that represents the sorted items "removed" in-place from the heap
	int getHeadTailIDX(){ return this.size() - this.heapSize; }

	// return all the items in the heap
	ArrayList<T> getItems(){ return this.items; }

	// return the size
	int size(){ return this.items.size(); }

	void add(T el){
		this.items.add(el);
		if(this.getHeadTailIDX() == 1){ return; }
		// swap the new element with the head of the heap size.
		this.rightPlace(el, this.getHeadTailIDX() - 1);
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
	//	EFFECT: mutate the given hashmap with the greatest children as a key, and the index as a value.
	//	if the greatest children exist, otherwise will not mutate the given hashmap.
	T getGreatestChildren(int currIDX, HashMap<T, Integer> res){
		int size = this.getHeadTailIDX();

		int lChildIDX = (2 * currIDX) + 1;
		int rChildIDX = (2 * currIDX) + 2;

		// If left is not valid, then right is not valid too. return null. 
		//	(if the size == 0, if we are at the last level, if we are the only element left (leaf);
		if ( (lChildIDX < 0 || lChildIDX >= size) ) return null;

		// Here we are not the leaf.
		// It's useless to check if left is valid, 
		// if we are not the leaf and we are respecting the fullness property,
		// then the left must exist.
		// So, we just need to check if the right in this case exist.
		if(rChildIDX < 0 || rChildIDX >= size) {
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

	T remove(){
		if(this.size() <= 0) throw new RuntimeException("Cannot remove any item in an empty heap!");

		int lastIDX = this.getHeadTailIDX() - 1;
		int currIDX = 0;
		T root = this.items.get(currIDX);
		
		System.out.println(lastIDX);

		// NOTE: i keep this part of the code here instead of abastracting with rightPlaceFromTop,
		// beacuse it's the only time we need to **remove** an element from the array.

		// Swap the firt item (root), with the last item
		this.swap(currIDX, lastIDX);
		// Delimiter 
		// rest of the items ||| root
		this.heapSize = this.heapSize + 1;
		
		// 30, 20, 10
		// 10 ||| 30, 20
		// heapSize = 2
		// size = 3 - 2
		
		// (3 - 2) - 1 = 0
		
		//
		// Remove the root (most valuable element) from the Heap
		// this.items.remove(lastIDX);
		// Check if heap had only one item, after the remove.
		if(this.getHeadTailIDX() <= 0) return root;

		// Find the right position for the new element that now is in the root position
		rightPlaceFromTop(currIDX);

		return root;
	}

	// To heapsort a list:
	//    Build a heap from all the items in the list.
	//    Remove all the items from the heap.

	// produce a new heap sorted based on this one
	ArrayList<T> heapsort(ArrayList<T> alist, IComparator<T> compare){
		// The heap builded by the given list
		this.items = new ArrayList<T>();
		this.comparator = compare;
		
		// Building the heap with upheap
		for(int i = 0; i < alist.size(); i=i+1){
			T root = alist.get(i);
			this.add(root);
		}

		// Getting the sorted list with downheap
		for(int i = 0; i < alist.size(); i=i+1){ 
			T item = this.remove();
			// removing the element inserted in the tail with remove, for
			// having a descendent order.
			this.items.remove(this.getHeadTailIDX());
			this.items.add(item);
			System.out.println("After removing one: ");
			System.out.println(this.items);
		}

		System.out.println(this.items);

		return this.items;
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
		// ArrayList<Integer> exp1 = new ArrayList<Integer>();
		// exp1.add(0,20); exp1.add(1,10);
		// t.checkExpect(this.h2.getItems(), exp1);
		// this.h2.remove();
		// // 	10 
		// ArrayList<Integer> exp2 = new ArrayList<Integer>();
		// exp2.add(0,10);
		// t.checkExpect(this.h2.getItems(), exp2);
		//
		// this.h2.remove();
		// // 	Empty queue, removing the last element (10)
		// ArrayList<Integer> exp3 = new ArrayList<Integer>();
		// t.checkExpect(this.h2.getItems(), exp3);
		//
		// // H3:
		//  //				 80
		//  //			 /   \
		//  //			70   60
		//  //		 / \   / \ 
		//  //		40 30 10 50
		//  //	 /
		//  //	20
		// this.h3.remove();
		//  //				 70
		//  //			 /   \
		//  //			40   60
		//  //		 / \   / \ 
		//  //		20 30 10 50
		// ArrayList<Integer> exp = new ArrayList<Integer>();
		// exp.add(0,70); exp.add(1,40); exp.add(2,60); 
		// exp.add(3,20); exp.add(4,30); exp.add(5,10);
		// exp.add(6,50);
		// t.checkExpect(this.h3.getItems(), exp);
	}



	void testHeapSort(Tester t){
		this.initData();

		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(0,10); arr.add(1,20); arr.add(2,30);

		ArrayList<Integer> exp = new ArrayList<Integer>();
		exp.add(0,30); exp.add(1,20); exp.add(2,10);
		t.checkExpect(this.h2.heapsort(arr, new CompNumber()), exp);

		this.initData();
		ArrayList<Integer> arr1 = new ArrayList<Integer>();
		arr1.add(0,8);
		arr1.add(1,2);
		arr1.add(2,18);
		arr1.add(3,3);
		arr1.add(4,0);
		arr1.add(5,28);
		arr1.add(6,15);
		arr1.add(7,30);
		arr1.add(8,29);

		ArrayList<Integer> exp1 = new ArrayList<Integer>();
		exp1.add(0,30); exp1.add(1,29); exp1.add(2,28);
		exp1.add(3,18); exp1.add(4,15); exp1.add(5, 8);
		exp1.add(6, 3); exp1.add(7, 2); exp1.add(8, 0);
		t.checkExpect(this.h2.heapsort(arr1, new CompNumber()), exp1);

		this.initData();
		ArrayList<Integer> arr2 = new ArrayList<Integer>();
		ArrayList<Integer> exp2 = new ArrayList<Integer>();
		t.checkExpect(this.h2.heapsort(arr2, new CompNumber()), exp2);
		
		this.initData();
		ArrayList<Integer> arr3 = new ArrayList<Integer>();
		arr3.add(0,0);
		ArrayList<Integer> exp3 = new ArrayList<Integer>();
		exp3.add(0,0);
		t.checkExpect(this.h2.heapsort(arr3, new CompNumber()), exp3);

		this.initData();
		ArrayList<Integer> arr4 = new ArrayList<Integer>();
		arr4.add(0,0); arr4.add(1,0); arr4.add(2,0);
		ArrayList<Integer> exp4 = new ArrayList<Integer>();
		exp4.add(0,0);  exp4.add(1,0); exp4.add(2,0);
		t.checkExpect(this.h2.heapsort(arr4, new CompNumber()), exp4);
	}
}
