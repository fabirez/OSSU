import tester.*;
import java.util.ArrayList;

class Examples {
  Examples(){}

  void testArray(Tester t) {
   ArrayList<Integer> alist = new ArrayList<Integer>();
   alist.add(1); alist.add(2); alist.add(3);
   
   alist.remove(1);
   ArrayList<Integer> exp = new ArrayList<Integer>();
   exp.add(0, 1); exp.add(1, 3);
   t.checkExpect(alist, exp);

   exp.add(0,0); 
   ArrayList<Integer> exp_2 = new ArrayList<Integer>();
    exp_2.add(0,0); exp_2.add(1, 1); exp_2.add(2, 3);
   t.checkExpect(exp, exp_2);
  System.out.println("Otp");
  }

}

