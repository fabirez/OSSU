/*Do Now!
	Why not? What helper method will be needed?
*/


/*Do Now!
	What kind of loop should be used?
*/

// While loop, since we don't know how much time we should itereate!


// Extra exercise

import tester.*;
import java.util.ArrayList;

interface IComparator<T>{
  boolean compare(T t, T t1);
}

// produce true if t <= t1; otherwsie false.
class CompString implements IComparator<String>{
  public boolean compare(String t, String t1){
    return t.compareTo(t1) <= 0;
  }
}

class ArrayUtility{
    // ==============================
    // Merge Sort
    // ==============================

     // EFFECT: Sorts the provided list according to the given comparator
    <T> void mergesort(ArrayList<T> arr, IComparator<T> comp) {
      // Create a temporary array
      ArrayList<T> temp = new ArrayList<T>();
      // Make sure the temporary array is exactly as big as the given array
      for (int i = 0; i < arr.size(); i = i + 1) { temp.add(arr.get(i)); }
      mergesortHelp(arr, temp, comp, 0, arr.size());
    }

    // EFFECT: Sorts the provided list in the region [loIdx, hiIdx)
    //        according to the given comparator.
    //         Modifies both lists in the range [loIdx, hiIdx)
    <T> void mergesortHelp(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp, int loIdx, int hiIdx) {
      // Step 0: stop when finished
      if (hiIdx - loIdx <= 1) {
        return; // nothing to sort
      }
      // Step 1: find the middle index
      int midIdx = (loIdx + hiIdx) / 2;
      // Step 2: recursively sort both halves
      mergesortHelp(source, temp, comp, loIdx, midIdx);
      mergesortHelp(source, temp, comp, midIdx, hiIdx);
      // Step 3: merge the two sorted halves
      merge(source, temp, comp, loIdx, midIdx, hiIdx);
    }

    <T> void merge(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp, int loIdx, int midIdx, int hiIdx) {

      // **Starting** IDX of the elemnt on the "left sub-arr" that need to be sorted
      int currLo = loIdx;
      // **Starting** IDX of the elemnt on the "right sub-arr" that need to be sorted
      int currHi = midIdx;

      // Available index of the (generateed, new) temp array
      int currIdx = loIdx;


      while(currLo < midIdx && currHi < hiIdx){
        if(comp.compare(source.get(currLo), source.get(currHi))){
          temp.set(currIdx, source.get(currLo));
          // **Next** Starting IDX of the elemenet on the "left sub-arr" that need to be sorted
          currLo = currLo + 1;
        }else{
          temp.set(currIdx, source.get(currHi));
          // **Next** Starting IDX of the elemenet on the "right sub-arr" that need to be sorted
          currHi = currHi + 1;
        }

        // Next available index of the (generateed, new) temp array
        currIdx = currIdx + 1;
      }

      // The loop above, ends after one of the side it's fully sorted.
      // The remaining part it's > than whatever we putted in the tmp array.
      // Then we just need to put whatever left, on the front of the array (temp) generated above.
      
      while(currLo < midIdx){
        temp.set(currIdx, source.get(currLo));
        currLo = currLo + 1;
        currIdx = currIdx + 1;
      }


      while(currHi < hiIdx){
        temp.set(currIdx, source.get(currHi));
        currHi = currHi + 1;
        currIdx = currIdx + 1;
      }

      // Paste the final *sorted* array generated (temp) into the source (source) 
      while(loIdx < hiIdx){
        source.set(loIdx, temp.get(loIdx));
        loIdx = loIdx + 1;
      }
    }


  // switch the position of the first given idx element with the second given idx element in the given array
    <T> void swap(ArrayList<T> arr, int idx, int idx1){
      T temp = arr.get(idx);
      arr.set(idx, arr.get(idx1));
      arr.set(idx1, temp);
    }


  <T> T selectKth(ArrayList<T> arr, IComparator<T> compare, int k){
    // Sort the array, with just ONE pivot element 
    // if k <= pivotIdx, then *do things* in the left arr
    // else *do things* in the right arr.
    if(k < 1 || arr.size() <= 0 || k > arr.size()) throw new RuntimeException("Cannot find any k value");
    this.selectKthHelper(arr, compare, 0, arr.size(), k);
    return arr.get(k-1);
  }

  <T> void selectKthHelper(ArrayList<T> arr, IComparator<T> compare, int lo, int hi, int k){

    if(lo >= hi || lo == hi - 1){ return; }

    T pEl = arr.get(lo);
    int pIDX = partition(arr, compare, lo, hi, pEl);

    if(k - 1 == pIDX) return;

    if(k - 1 > pIDX){
      this.selectKthHelper(arr, compare, pIDX + 1,  hi, k);
    }else{
      this.selectKthHelper(arr, compare, lo, pIDX, k);
    }

  }



  <T> int partition(ArrayList<T> arr, IComparator<T> compare, int lo, int hi, T pEl)
  {
    int currLo = lo;
    int currHi = hi - 1;

    T currEl = arr.get(currLo);
    while(currLo < currHi){

      while(currLo < hi && compare.compare(arr.get(currLo), pEl)){  
        currLo += 1; 
      }
      while(currHi >= lo && !compare.compare(arr.get(currHi), pEl)){    currHi -= 1; 
      }

      if(currLo < currHi){
        this.swap(arr, currLo, currHi);
      }

    }

    this.swap(arr, lo, currHi);
    return currHi;
  }

  //=================================
  // Implemention with swap place
  //=================================


  // return the k element most smaller from the given ArrayList<T> based on the given IComparator 
  <T> T selectKthSwapPlace(ArrayList<T> arr, IComparator<T> compare, int k){
    // Sort the array, with just ONE pivot element 
    // if k <= pivotIdx, then *do things* in the left arr
    // else *do things* in the right arr.
    if(k < 1 || arr.size() <= 0 || k > arr.size()) throw new RuntimeException("Cannot find any k value");

    // Array with the placeholder null on the pivot idx.
    ArrayList<T> temp = new ArrayList<T>();
    for(
      int i = 0;
      i < arr.size();
      i = i + 1
    )
    {
      temp.add(arr.get(i));
    }

    this.selectKthSwapPlaceHelper(arr, temp, compare, 0, arr.size(), k);
    return arr.get(k-1);
  }

  <T> void selectKthSwapPlaceHelper(ArrayList<T> arr, ArrayList<T> temp, IComparator<T> compare, int lo, int hi, int k){

    if(lo >= hi || lo == hi - 1){ return; }

    T pivotEL = arr.get(lo);
    int pivotIdx = partitionSwapPlace(arr, temp, compare, lo, hi, pivotEL);
    if(k - 1 == pivotIdx) return;

    if(k - 1 > pivotIdx){
      this.selectKthSwapPlaceHelper(arr, temp,compare, pivotIdx + 1,  hi, k);
    }else{
      this.selectKthSwapPlaceHelper(arr, temp,compare, lo, pivotIdx, k);
    }

  }

  // returns the final index (position of where the pivot el, is sorted) of the pivotEl
  <T> int partitionSwapPlace(ArrayList<T> arr, ArrayList<T> temp, IComparator<T> compare, int lo, int hi, T pivotEl){

    int currHi = hi - 1;
    int currLo = lo;

    for(int i = lo + 1; i < hi; i++){
      T currEl = arr.get(i);

      if(compare.compare(currEl, pivotEl)){
        temp.set(currLo, currEl);
        currLo += 1;
      }else{
        temp.set(currHi, currEl);
        currHi -= 1;
      }
    }

    temp.set(currLo, pivotEl);

    for(int i = lo; i < hi; i++){ arr.set(i,temp.get(i)); }
    return currLo;
  }

}

class ExamplesKElement{
  ArrayList<String> los;
  ArrayUtility u;
  void initData(){
    this.u = new ArrayUtility();

    this.los = new ArrayList<String>();
    this.los.add("watermelon"); 
    this.los.add("apple"); 
    this.los.add("banana"); 
    this.los.add("cherry"); 
    this.los.add("date"); 
  }

  void testSelectK(Tester t){
    this.initData();
     t.checkExpect(new ArrayUtility().selectKth(this.los, new CompString(), 1), "apple");
    this.initData();
     t.checkExpect(new ArrayUtility().selectKth(this.los, new CompString(), 3), "cherry");
    this.initData();
     t.checkExpect(new ArrayUtility().selectKth(this.los, new CompString(), 5), "watermelon");
  }

  void testSelectKthSwapPlace(Tester t){
    this.initData();
     t.checkExpect(new ArrayUtility().selectKthSwapPlace(this.los, new CompString(), 1), "apple");
    this.initData();
     t.checkExpect(new ArrayUtility().selectKthSwapPlace(this.los, new CompString(), 3), "cherry");
    this.initData();
     t.checkExpect(new ArrayUtility().selectKthSwapPlace(this.los, new CompString(), 5), "watermelon");
  }

  void testSwap(Tester t){
    this.initData();
    ArrayList<Integer> loi = new ArrayList<Integer>();
    loi.add(0); loi.add(1);

   // Swap the el at the index 0, with the one at index 1 in the "loi" array.
    this.u.swap(loi, 0, 1);

    ArrayList<Integer> expected = new ArrayList<Integer>();
    expected.add(1); expected.add(0);
    t.checkExpect(loi, expected);

    ArrayList<Integer> loi_1 = new ArrayList<Integer>();
    loi_1.add(0); loi_1.add(1); loi_1.add(2); loi_1.add(3);

   // Swap the el at the index 0, with the one at last index (3) in the "loi_1" array.
    this.u.swap(loi_1, 0, loi_1.size() - 1);

    ArrayList<Integer> expected_1 = new ArrayList<Integer>();
    expected_1.add(3); expected_1.add(1); expected_1.add(2); expected_1.add(0);
    t.checkExpect(loi_1, expected_1);
  }

  void testMergeSort(Tester t){
    this.initData();
    ArrayList<String> expected =  new ArrayList<String>();
    expected.add("apple"); 
    expected.add("banana"); 
    expected.add("cherry"); 
    expected.add("date"); 
    expected.add("watermelon"); 
    this.u.mergesort(this.los, new CompString());
    t.checkExpect(this.los, expected);

    // Merge sort on a empty array
    ArrayList<String> los_1 =  new ArrayList<String>();
    this.u.mergesort(los_1, new CompString());
    t.checkExpect(los_1, los_1);

    // Merge sort on a array with one element
    ArrayList<String> los_2 =  new ArrayList<String>();
    los_2.add("A");
    this.u.mergesort(los_2, new CompString());
    t.checkExpect(los_2, los_2);

    // Merge sort on a array with tree element
    ArrayList<String> los_3 =  new ArrayList<String>();
    los_3.add("banana"); 
    los_3.add("apple"); 
    los_3.add("cherry"); 
    this.u.mergesort(los_3, new CompString());
    t.checkExpect(los_3, los_3);

  }
}
