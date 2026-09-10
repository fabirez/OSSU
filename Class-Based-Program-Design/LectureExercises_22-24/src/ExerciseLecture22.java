/* Exercise
	Practice using the counted-for loop:
	- [x] design a method `<T> ArrayList<T> interleave(ArrayList<T> arr1, ArrayList<T> arr2)`
				that takes two ArrayLists of the same size,
				and produces an output ArrayList consisting of one item from arr1,
				then one from arr2, then another from arr1, etc.
	- [x] Design a method <T> ArrayList<T> unshuffle(ArrayList<T> arr)
				that takes an input ArrayList and produces a new list containing the first, third, fifth ... items of the list,
				followed by the second, fourth, sixth ... items.
*/

/* Exercise, Finding the minimum value
	- [x] Design the missing method to finish the sort method above:
				this method should find the minimum value in the not-yet-sorted part of the given ArrayList<String>.
*/

import java.util.ArrayList;
import tester.*;

class ArrayUtils2 {

  // EFFECT: Exchanges the values at the given two indices in the given array
	// The set method is defined to return a T value: specifically,
	// it returns the old value at the index being modified
	<T> void swap(ArrayList<T> arr, int index1, int index2) {
		arr.set(index2, arr.set(index1, arr.get(index2)));
	}

	// find the mininmum value in an arraylist of strings, starting from the given index.
	int findMinValue(ArrayList<String> arr, int minIdx){ 
		for (int idx = minIdx + 1;
				 idx < arr.size();
				 idx = idx + 1) 
		{
			if(arr.get(minIdx).compareTo(arr.get(idx)) > 0){
				minIdx = idx;
			}
		}
		return minIdx;
	}

	// EFFECT: Sorts the given list of strings alphabetically
	void sort(ArrayList<String> arr) {

		// ArrayList<String> tempArr = new ArrayList<String>();
		// for (int idx = 0;
		// 		 idx < arr.size();
		// 		 idx = idx + 1) {
		// 	tempArr.add(arr.get(idx));
		// }

		for (int idx = 0;
				 idx < arr.size();
				 idx = idx + 1) {
			int idxOfMinValue = this.findMinValue(arr, idx);
			this.swap(arr, idx, idxOfMinValue);
		}
	}

	<T> ArrayList<T> interleave(ArrayList<T> arr1, ArrayList<T> arr2){
		ArrayList<T> newArr = new ArrayList<T>();
		for(int idx = 0;
				idx < arr1.size();
				idx = idx + 1)
		{
			if(idx % 2 == 0){
				newArr.add(idx, arr1.get(idx));
			}else{
				newArr.add(idx, arr2.get(idx));
			}
		}
		return newArr;
	}

 <T> ArrayList<T> unshuffle(ArrayList<T> arr){
		ArrayList<T> head = new ArrayList<T>();
		ArrayList<T> tail = new ArrayList<T>();

		for(int idx = 0;
				idx < arr.size();
				idx = idx + 1)
		{
			if(idx % 2 == 0){
				head.add(arr.get(idx));
			}else{
				tail.add(arr.get(idx));
			}
		}

		for(int idx = 0;
				idx < tail.size();
				idx = idx + 1)
		{
				head.add(tail.get(idx));
		}

		return head;
	}
}

class ExamplesArray2{
	ArrayList<String> sS;
	ArrayList<String> sS2;
	void initData(){
		this.sS = new ArrayList<String>();
		this.sS.add("apple");
		this.sS.add("banana");
		this.sS.add("cherry");
		this.sS.add("date");
		this.sS.add("fig");
		this.sS.add("grape");

		this.sS2 = new ArrayList<String>();
		this.sS2.add("ann");
		this.sS2.add("bob");
		this.sS2.add("charlie");
		this.sS2.add("kat");
		this.sS2.add("shidono");
		this.sS2.add("caffeine");
	}

	void testInterleave(Tester t){
		this.initData();

		ArrayList<String> onlyOne1 = new ArrayList<String>();
		onlyOne1.add("hello");
		t.checkExpect(onlyOne1.get(0), "hello");
		ArrayList<String> onlyOne2 = new ArrayList<String>();
		onlyOne2.add("world");
		t.checkExpect(onlyOne2.get(0), "world");

		ArrayList<String> onlyOne_expected = new ArrayList<String>();
		onlyOne_expected.add("hello");
		t.checkExpect(new ArrayUtils2().interleave(onlyOne1, onlyOne2), onlyOne_expected);

		ArrayList<String> expected = new ArrayList<String>();
		expected.add("apple");
		expected.add("bob");
		expected.add("cherry");
		expected.add("kat");
		expected.add("fig");
		expected.add("caffeine");
		t.checkExpect(new ArrayUtils2().interleave(this.sS, this.sS2), expected);
	}

	void testUnshuffle(Tester t){
		this.initData();

		ArrayList<String> expected = new ArrayList<String>();
		expected.add("apple");
		expected.add("cherry");
		expected.add("fig");
		expected.add("banana");
		expected.add("date");
		expected.add("grape");
		t.checkExpect(new ArrayUtils2().unshuffle(this.sS), expected);

		ArrayList<String> expected1 = new ArrayList<String>();
		expected1.add("ann");
		expected1.add("charlie");
		expected1.add("shidono");
		expected1.add("bob");
		expected1.add("kat");
		expected1.add("caffeine");
		t.checkExpect(new ArrayUtils2().unshuffle(this.sS2), expected1);
	}

	void testFindMinValue(Tester t){
		this.initData();
		t.checkExpect(new ArrayUtils2().findMinValue(this.sS, 0), 0);

		ArrayList<String> a1 = new ArrayList<String>();
		a1.add("shidono");
		a1.add("bob");
		a1.add("kat");

		t.checkExpect(new ArrayUtils2().findMinValue(a1, 0), 1);
		t.checkExpect(new ArrayUtils2().findMinValue(a1, 1), 1);
		t.checkExpect(new ArrayUtils2().findMinValue(a1, 2), 2);
	}


	void testSort(Tester t){
		this.initData();

		ArrayList<String> a1 = new ArrayList<String>();
		a1.add("apple");
		a1.add("cherry");
		a1.add("fig");
		a1.add("banana");
		a1.add("date");
		a1.add("grape");

		ArrayList<String> e1 = new ArrayList<String>();
		e1.add("apple");
		e1.add("banana");
		e1.add("cherry");
		e1.add("date");
		e1.add("fig");
		e1.add("grape");

		new ArrayUtils2().sort(a1);
		t.checkExpect(a1, e1);

		ArrayList<String> a2 = new ArrayList<String>();
		a2.add("date");
		a2.add("fig");
		a2.add("apple");

		ArrayList<String> e2 = new ArrayList<String>();
		e2.add("apple");
		e2.add("date");
		e2.add("fig");


		new ArrayUtils2().sort(a2);
		t.checkExpect(a2, e2);

		ArrayList<String> a3 = new ArrayList<String>();
		a3.add("ann");
		new ArrayUtils2().sort(a3);
		t.checkExpect(a3, a3);

		ArrayList<String> a4 = new ArrayList<String>();
		a4.add("bob");
		a4.add("ann");
		new ArrayUtils2().sort(a4);

		ArrayList<String> e4 = new ArrayList<String>();
		e4.add("ann");
		e4.add("bob");
		t.checkExpect(a4, e4);

		ArrayList<String> a5 = new ArrayList<String>();
		a5.add("ann");
		a5.add("bob");
		new ArrayUtils2().sort(a5);
		t.checkExpect(a5, a5);
	}

}

