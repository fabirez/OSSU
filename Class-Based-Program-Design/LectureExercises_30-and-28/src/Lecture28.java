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
  boolean comp(T t, T t1);
}

// produce true if t <= t1; otherwsie false.
class CompString implements IComparator<String>{
  public boolean comp(String t, String t1){
    return t.compareTo(t1) <= 0;
  }
}

class ArrayUtility{

  // switch the position of the first given idx element with the second given idx element in the given array
    <T> void swap(ArrayList<T> arr, int idx, int idx1){
      T temp = arr.get(idx);
      arr.set(idx, arr.get(idx1));
      arr.set(idx1, temp);
    }


  // return the k element most smaller from the given ArrayList<T> based on the given IComparator 
  <T> T selectKth(ArrayList<T> arr, IComparator<T> compare, int k){
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

    this.selectKthHelper(arr, temp, compare, 0, arr.size(), k);
    return arr.get(k-1);
  }

  <T> void selectKthHelper(ArrayList<T> arr, ArrayList<T> temp, IComparator<T> compare, int lo, int hi, int k){

    if(lo >= hi || lo == hi - 1){ return; }

    T pivotEL = arr.get(lo);
    int pivotIdx = partition(arr, temp, compare, lo, hi, pivotEL);
    if(k - 1 == pivotIdx) return;

    if(k - 1 > pivotIdx){
      this.selectKthHelper(arr, temp,compare, pivotIdx + 1,  hi, k);
    }else{
      this.selectKthHelper(arr, temp,compare, lo, pivotIdx, k);
    }

  }

  // returns the final index (position of where the pivot el, is sorted) of the pivotEl
  <T> int partition(ArrayList<T> arr, ArrayList<T> temp, IComparator<T> compare, int lo, int hi, T pivotEl){

    int currHi = hi - 1;
    int currLo = lo;

    for(int i = lo + 1; i < hi; i++){
      T currEl = arr.get(i);

      if(compare.comp(currEl, pivotEl)){
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

  void testSelectKth(Tester t){
    this.initData();
     t.checkExpect(new ArrayUtility().selectKth(this.los, new CompString(), 1), "apple");
    this.initData();
     t.checkExpect(new ArrayUtility().selectKth(this.los, new CompString(), 3), "cherry");
    this.initData();
     t.checkExpect(new ArrayUtility().selectKth(this.los, new CompString(), 5), "watermelon");
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
}
