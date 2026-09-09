/*Do Now!
	Which class have we seen in the homework assignments that is most akin to the ArrayList<T> class? 
	(Hint: which class essentially implements the interface above?)
*/

// > ConsList<T>

/*Do Now!
	Implement this swap method. What new method on ArrayLists will be needed?
*/

// > A method with the feature of editing an array, by index.
// > Modify the value at the given index, with my new value.

/*Exercise
	Implement foldr and foldl for ArrayLists, first using recursion and then again using for-each loops. (Hint: one of these will be much harder than the other, when using for-each loops.)
*/

import java.util.ArrayList;
import tester.*;



interface IFunc3<T, U>{
	U apply(T t);
}

interface IFunc3_1<T1, T2>{
	void apply(T1 t1, T2 t2);
}

class SumInteger implements IFunc3_1<Integer, Integer>{
	public void apply(Integer t, Integer base){
		base = base + t;
	}
}

class CreateArray implements IFunc3_1<Integer, ArrayList<Integer>>{
	public void apply(Integer t, ArrayList<Integer> base){
		base.add(t);
	}
}

class ArrayUtils {
  // EFFECT: Exchanges the values at the given two indices in the given array
  // <T> void swap(ArrayList<T> arr, int index1, int index2) {
  //   T tmp = arr.get(index1);
  //   arr.add(index1, arr.get(index2));
  //   arr.add(index2, tmp);
  // }
	// NOTE: Lecture implementation
	// The set method is defined to return a T value: specifically,
	// it returns the old value at the index being modified
	<T> void swap(ArrayList<T> arr, int index1, int index2) {
		arr.set(index2, arr.set(index1, arr.get(index2)));
	}

	// <T, U> ArrayList<U> map(ArrayList<T> arr, IFunc3<T, U> func) {
	//   return this.mapHelper(arr, func, 0, new ArrayList<U>());
	// }
	//
	// <T, U> ArrayList<U> mapHelper(ArrayList<T> arr, IFunc3<T, U> func, int idx, ArrayList<U> newArr) {
	// 	if(idx == (arr.size()-1)){
	// 		return newArr;
	// 	}else{
	// 		newArr.add(idx, func.apply(arr.get(idx)));
	// 		return this.mapHelper(
	// 			arr,
	// 			func,
	// 			idx + 1,
	// 			newArr
	// 		);
	// 	}
	// }

  // NOTE: Lecture implementation
	<T, U> ArrayList<U> map(ArrayList<T> arr, IFunc3<T, U> func) {
		ArrayList<U> result = new ArrayList<U>();
		return this.mapHelp(arr, func, 0, result);
	}
	// Computes the result of mapping the given function over the source list
	// from the given current index to the end of the list, and returns the
	// given destination list
	// EFFECT: modifies the destination list to contain the mapped results
	<T, U> ArrayList<U> mapHelp(ArrayList<T> source, IFunc3<T, U> func, int curIdx, ArrayList<U> dest) {
		if (curIdx >= source.size()) {
			return dest;
		}
		else {
			dest.add(func.apply(source.get(curIdx)));
			return this.mapHelp(source, func, curIdx + 1, dest);
		}
	}

	// NOTE: recursive way
	// <T, U> U foldr(ArrayList<T> arr, IFunc3_1<T, U> func, U base) {
	// 	return this.foldrHelp(arr, func, base, 0);
	// }
	//
	// <T, U> U foldrHelp(ArrayList<T> arr, IFunc3_1<T, U> func, U base, int curIdx) {
	// 	if (curIdx >= arr.size()) {
	// 		return base;
	// 	}
	// 	else {
	// 		func.apply(arr.get(curIdx), base);
	// 		return this.foldrHelp(arr, func, base, curIdx + 1);
	// 	}
	// }
	// NOTE: recursive way
	// <T, U> U foldl(ArrayList<T> arr, IFunc3_1<T, U> func, U base) {
	// 	return this.foldlHelp(arr, func, base, arr.size() - 1);
	// }
	//
	// <T, U> U foldlHelp(ArrayList<T> arr, IFunc3_1<T, U> func, U base, int curIdx) {
	// 	if (curIdx < 0) {
	// 		return base;
	// 	}
	// 	else {
	// 		func.apply(arr.get(curIdx), base);
	// 		return this.foldlHelp(arr, func, base, curIdx - 1);
	// 	}
	// }


	<T, U> U foldr(ArrayList<T> arr, IFunc3_1<T, U> func, U base) {
		for (T t : arr) {
			func.apply(t, base);
		}
		return base;
	}

	<T, U> U foldl(ArrayList<T> arr, IFunc3_1<T, U> func, U base) {
		arr = this.reverse(arr);
		for (T t : arr) {
			func.apply(t, base);
		}
		return base;
	}


	<T> ArrayList<T> reverse(ArrayList<T> arr){
		// How many times, and also the index in the middle (edge case) 
		int times = (int) Math.floor(arr.size() / 2);
		return this.reverseHelp(arr, times, 0, arr.size() - 1); 
	}	
	<T> ArrayList<T> reverseHelp(ArrayList<T> arr, int times, int leftIdx, int rightIdx){
		if(times <= 0){
			return arr;
		}else{
			this.swap(arr, leftIdx, rightIdx);
			return this.reverseHelp(arr, times - 1, leftIdx+1, rightIdx-1);
		}
	}	


}




class ExamplesArray{
 void testGet(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    t.checkException(new IndexOutOfBoundsException("Index 0 out of bounds for length 0"),
                     someStrings, "get", 0);
    someStrings.add("First string");
    someStrings.add("Second string");
    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Second string");
    t.checkException(new IndexOutOfBoundsException("Index 3 out of bounds for length 2"),
                     someStrings, "get", 3);
  }

  void testAdd(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    someStrings.add("First string");
    someStrings.add("Second string");
    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Second string");
 
    // Insert this item at index 1, and move everything else back
		// NOTE: Add with index
    someStrings.add(1, "Squeezed in");
    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Squeezed in");
    t.checkExpect(someStrings.get(2), "Second string");
  }

  void testSwap(Tester t) {
    ArrayList<String> someStrings = new ArrayList<String>();
    someStrings.add("First string");
    someStrings.add("Second string");
    t.checkExpect(someStrings.get(0), "First string");
    t.checkExpect(someStrings.get(1), "Second string");
 
    // Insert this item at index 1, and move everything else back
		new ArrayUtils().swap(someStrings, 0, 1);
    t.checkExpect(someStrings.get(0), "Second string");
    t.checkExpect(someStrings.get(1), "First string");
  }

  void testFoldr(Tester t) {
    ArrayList<Integer> someNumbers = new ArrayList<Integer>();
    someNumbers.add(1);
    someNumbers.add(2);
    t.checkExpect(someNumbers.get(0), 1);
    t.checkExpect(someNumbers.get(1), 2);
 
		IFunc3_1<Integer, ArrayList<Integer>> cA = new CreateArray();
		ArrayList<Integer> resFoldr = new ArrayUtils().foldr(someNumbers, cA, new ArrayList<Integer>());
    t.checkExpect(resFoldr, someNumbers);

		ArrayList<Integer> resFoldl = new ArrayUtils().foldl(someNumbers, cA, new ArrayList<Integer>());
		  ArrayList<Integer> someNumbers1 = new ArrayList<Integer>();
		  someNumbers1.add(2);
		  someNumbers1.add(1);
		  t.checkExpect(someNumbers1.get(0), 2);
		  t.checkExpect(someNumbers1.get(1), 1);
		  t.checkExpect(resFoldl, someNumbers1);
  }

  void testReverse(Tester t) {
    ArrayList<Integer> someNumbers = new ArrayList<Integer>();
    someNumbers.add(1);
    someNumbers.add(2);
    t.checkExpect(someNumbers.get(0), 1);
    t.checkExpect(someNumbers.get(1), 2);

    ArrayList<Integer> expected = new ArrayList<Integer>();
    expected.add(2);
    expected.add(1);
    t.checkExpect(new ArrayUtils().reverse(someNumbers), expected);

    ArrayList<Integer> someNumbers2 = new ArrayList<Integer>();
    someNumbers2.add(1);
    someNumbers2.add(2);
    someNumbers2.add(3);
    someNumbers2.add(4);
    t.checkExpect(someNumbers2.get(2), 3);
    t.checkExpect(someNumbers2.get(3), 4);

    ArrayList<Integer> expected2 = new ArrayList<Integer>();
    expected2.add(4);
    expected2.add(3);
    expected2.add(2);
    expected2.add(1);
    t.checkExpect(new ArrayUtils().reverse(someNumbers2), expected2);
 

    ArrayList<Integer> onlyOne = new ArrayList<Integer>();
    onlyOne.add(1);
    t.checkExpect(onlyOne.get(0), 1);

    ArrayList<Integer> expected3 = new ArrayList<Integer>();
    expected3.add(1);
    t.checkExpect(new ArrayUtils().reverse(onlyOne), expected3);
 
  }
}

