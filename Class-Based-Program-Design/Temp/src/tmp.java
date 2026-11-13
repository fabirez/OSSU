import tester.*;
import java.util.ArrayList;

class Examples {
  Examples(){}

  void testArray(Tester t) {
   ArrayList<Integer> alist = new ArrayList<Integer>();
   alist.add(1); alist.add(2); alist.add(3);
   
   alist.remove(1);
   ArrayList<Integer> exp = new ArrayList<Integer>();
   exp.add(1); exp.add(3);
   t.checkExpect(alist, exp);
  }

}

