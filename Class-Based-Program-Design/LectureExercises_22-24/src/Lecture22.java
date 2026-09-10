
/* Do Now!
	What’s wrong with this code?

	// Returns the index of the first item passing the predicate at or after the
	// given index, or -1 if no such such item was found
	<T> int findHelp(ArrayList<T> arr, IPred<T> whichOne, int index) {
		if (whichOne.apply(arr.get(index)) {
			return index;
		}
		else {
			return findHelp(arr, whichOne, index + 1);
		}
	}

*/

// > We will use index that doens't exist (never return -1), since we increase them at every recursive call, and got a runtime exception.
//
/*Do Now!
	What would happen if we had used > instead of >=?

	// Returns the index of the first item passing the predicate at or after the
	// given index, or -1 if no such such item was found
	<T> int findHelp(ArrayList<T> arr, IPred<T> whichOne, int index) {
		if (index >= arr.size()) {
			return -1;
		}
		else if (whichOne.apply(arr.get(index)) {
			return index;
		}
		else {
			return findHelp(arr, whichOne, index + 1);
		}
	}

*/

// > Same excpetion as before, since the index starts from 0.

/*Do Now!
	What indices would we check if we were searching for “blueberry”?
*/

// > 1 or 2.


/*Do Now!
	What’s wrong with this code?
*/

// > The mid index is not a int type and we should "floor" it, but a double, and not handling the base case (-1).

/*Do Now!
	What would happen if we didn’t add or subtract 1 from midIdx in the recursive calls?
*/
// > Infinitre recursion, beacuse we cannot check the idx we need, instead we end up in a recursion between the same indexes.

/*Do Now!
	What would happen if our exit condition were if (loxIdx >= highIdx)...?
*/

// > The same, the lowIdx and highIdx will never be equal so it's a useless check.


/*Do Now!
	What kind of intervals were we using in version 1 of our binary search code?
*/

// >  [lowIdx, maxIdx]

/*Do Now!
	Confirm this — use the definition of semi-open above.
*/

// >  If the maxIndex is excluded, then we don't need anymore to do -1, but we still need +1 on the lower.

/*
	// In ArrayUtils
	// Returns the index of the target string in the given ArrayList, or -1 if the string is not found
	// Assumes that the given ArrayList is sorted aphabetically
	// Assumes that [lowIdx, highIdx) is a semi-open interval of indices
	int binarySearchHelp_v2(ArrayList<String> strings, String target, int lowIdx, int highIdx) {
		int midIdx = (lowIdx + highIdx) / 2; 
		if (lowIdx == highIdx) {
			return -1;                                                           // not found
		}
		else if (target.compareTo(strings.get(midIdx)) == 0) {
			return midIdx;                                                       // found it!
		}
		else if (target.compareTo(strings.get(midIdx)) > 0) {
			return this.binarySearchHelp_v2(strings, target, midIdx + 1 , highIdx); // too low
		}
		else {
			return this.binarySearchHelp_v2(strings, target, lowIdx, midIdx) // too high
		}
	}
*/

/*Do Now!
	Suppose we didn’t add 1 in the last case. Construct a test case that causes the search to recur forever.
*/

// > t.checkExpect(new ArrayUtils().binarySearch_v2(this.sS, "unknown"), -1);

/*Do Now!
	How did we decide that “apple” was the appropriate replacement for “kiwi”?
*/

// > Based on the built-in compare java method, and also alphabetical.

/*Do Now!
	How did we decide that “banana” was the appropriate replacement for “cherry”?
*/

// > Same as above.

import java.util.ArrayList;
import tester.*;

class ArrayUtils {

	int binarySearch_v1(ArrayList<String> strings, String target) {
		return this.binarySearchHelp_v1(strings, target, 0, strings.size() - 1);
	}
	// Returns the index of the target string in the given ArrayList, or -1 if the string is not found
	// ASSUME: that the given ArrayList is sorted aphabetically
	int binarySearchHelp_v1(ArrayList<String> strings, String target, int lowIdx, int highIdx) {
		int midIdx = (lowIdx + highIdx) / 2;
		if (lowIdx > highIdx) {
			return -1;                                                           // not found
		}
		else if (target.compareTo(strings.get(midIdx)) == 0) {
			return midIdx;                                                       // found it!
		}
		else if (target.compareTo(strings.get(midIdx)) > 0) {
			return this.binarySearchHelp_v1(strings, target, midIdx + 1, highIdx); // too low
		}
		else {
			return this.binarySearchHelp_v1(strings, target, lowIdx, midIdx - 1); // too high
		}
	}


	int binarySearch_v2(ArrayList<String> strings, String target) {
		return this.binarySearchHelp_v2(strings, target, 0, strings.size());
	}
	// Returns the index of the target string in the given ArrayList, or -1 if the string is not found
	// ASSUME: that the given ArrayList is sorted aphabetically
	// ASSUME: that [lowIdx, highIdx) is a semi-open interval of indices
	int binarySearchHelp_v2(ArrayList<String> strings, String target, int lowIdx, int highIdx) {
		int midIdx = (lowIdx + highIdx) / 2;
		if (lowIdx >= highIdx) {
			return -1;                                                           // not found
		}
		else if (target.compareTo(strings.get(midIdx)) == 0) {
			return midIdx;                                                       // found it!
		}
		else if (target.compareTo(strings.get(midIdx)) > 0) {
			return this.binarySearchHelp_v2(strings, target, midIdx + 1, highIdx); // too low
		}
		else {
			return this.binarySearchHelp_v2(strings, target, lowIdx, midIdx);     // too high
		}
	}

}

class ExamplesArray{
	ArrayList<String> sS;
	void initData(){
		this.sS = new ArrayList<String>();
		this.sS.add("apple");
		this.sS.add("banana");
		this.sS.add("cherry");
		this.sS.add("date");
		this.sS.add("fig");
		this.sS.add("grape");
		this.sS.add("honedew");
		this.sS.add("kiwi");
		this.sS.add("watermelon");
	}
	void testBinarySearchV2(Tester t){
		this.initData();
		t.checkExpect(this.sS.get(0), "apple");
		t.checkExpect(this.sS.get(1), "banana");
		t.checkExpect(this.sS.get(2), "cherry");
		t.checkExpect(this.sS.get(3), "date");
		t.checkExpect(this.sS.get(4), "fig");
		t.checkExpect(this.sS.get(5), "grape");
		t.checkExpect(this.sS.get(6), "honedew");
		t.checkExpect(this.sS.get(7), "kiwi");
		t.checkExpect(this.sS.get(8), "watermelon");

		t.checkExpect(new ArrayUtils().binarySearch_v2(this.sS, "apple"), 0);
		t.checkExpect(new ArrayUtils().binarySearch_v2(this.sS, "fig"), 4);
		t.checkExpect(new ArrayUtils().binarySearch_v2(this.sS, "watermelon"), 8);
	}
}
