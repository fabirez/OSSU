/*Do Now!
	Translate the code of findMin to use a while loop instead of a counted-for loop.
*/

/*
	

*/

import java.util.ArrayList;
import tester.*;

interface IFunc2<T1, T2>{
  int compare(T1 t1, T2 t2);
}

class Utils {

 <T> T findMin(IFunc2<T, T> comp, ArrayList<T> arr) 
	{
		int initialIdx = 0;
		int start = initialIdx + 1;
		int end = arr.size();
		int currIdx = start;

		T base = arr.get(initialIdx);

		if(end == 1) return base;

		while (currIdx < end) {
			if(comp.compare(arr.get(currIdx), base) < 0){
				base = arr.get(currIdx);
			}
			currIdx = currIdx + 1;
		}

		return base;
	}

  boolean checksDuplicate(ArrayList<Integer> arr) {

    for (int idx = 0; idx < arr.size(); idx = idx + 1) {
      int ref = arr.get(idx);

      for (int j = idx + 1; j < arr.size(); j = j + 1) {

        if (arr.get(j) == ref) {
          return true;
        }
      }

    }

    return false;

  }

  boolean getsToOne(int n) {
    ArrayList<Integer> arr = new ArrayList<Integer>();

    while (n > 1) {
      if (n % 2 == 0) {
        n = n / 2;
      }
      else {
        n = 3 * n + 1;
      }
      arr.add(n);

      if (this.checksDuplicate(arr)) {
        return false;
      }
    }

    return true;
  }

}
