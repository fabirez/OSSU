// Why are equals and hashCode imporant?
// > hashCode permette di creare quanto piu possibile, un id univoco, che farà da indice e che quindi garantira alla prossimo get / put un look up da O(1).
// > equals invece, rassicura che il valore all'indice generato dall'hashcode non si tratta di una collissione ma del valore che stiamo realmente cercando.


// What method of a given object does the ArrayList contains use to check if that object is already in an array list?
// > Use the method `public boolean equals(Object that)`. 

/*
	How do you think Java’s HashMap decides if a given key is in the hash map?
	(hint: there are two method calls. First java calls one method which returns an integer, and then if necessary, it calls another method which returns a boolean. You have already seen both of these methods ).
*/

// > First compute the `hashCode(key) -> int ` if it exists in the hashTable, 
// > then use `equals` to verify that is not a collission but the key that we are looking for.
// > otherwise just return null.


/*
	Why do you think it isn’t enough for java’s HashMap to use just the key’s hash code to decide if a given key is in the map,
	and when do you think the extra call to the key’s equals method happens?
*/

// > Because, there can be hash collission, maybe two objects aren't equal but they have the same "hash code"
// > An equals is required, when the value exist and for being sure it's the same object.

import tester.*;
import java.util.HashMap;

class Runner{
     String name;
     int age;
 
     Runner(String name, int age){
             this.age = age;
             this.name = name;
     }

		public boolean equals(Object o)
		{
			if(o instanceof Runner){
				Runner r = (Runner) o;
				return r.age == this.age && r.name.equals(this.name);
			}
			return false;
		}

		public int hashCode()
		{ 
			return this.name.hashCode() * 10000 + this.age;
		}
}


class ExamplesRunner{
	Runner r1;
	Runner r2;
	Runner r3;
	Runner r4;


	void initData(){
		this.r1 = new Runner("Bob", 44);
		this.r2 = new Runner("Bob", 44);
		this.r3 = new Runner("Luk", 29);
		this.r4 = new Runner("Jul", 29);
	}

	void testEquals(Tester t){
		this.initData();
		// Same name same age.
		t.checkExpect(this.r1.equals(this.r2),  true);
		// Different name and age.
		t.checkExpect(this.r1.equals(this.r3), false);
		// Different name and age.
		t.checkExpect(this.r1.equals(this.r4), false);
		// Same name different age.
		t.checkExpect(this.r3.equals(this.r4), false);
	}

	void testHashCode(Tester t){
		this.initData();
		// Same name same age.
		t.checkExpect(this.r1.hashCode(), this.r2.hashCode());
	}

	void testHashMap(Tester t){
		this.initData();
		// Runner: (positive) Integer assigned to them
		HashMap<Runner, Integer> marathon = new HashMap();

		// Runner 1 and 2 with the same number 
		marathon.put(this.r1, 101);
		marathon.put(this.r2, 101);
		// Runner 3 
		marathon.put(this.r3, 948);
		// Runner 3 
		marathon.put(this.r4, 123);

		// Getting the same value from r1 and r2, since they are equals, and have the same hashCode.
		t.checkExpect(marathon.get(this.r1), 101);
		t.checkExpect(marathon.get(this.r2), 101);
		// Modifying the value of r2, will modify also the value of r1, this beacuse we forevre had only p
		// one key in the hashTbale since they have the same hashCode.
		marathon.put(this.r2, 201);
		t.checkExpect(marathon.get(this.r1), 201);
		t.checkExpect(marathon.get(this.r2), 201);

		t.checkExpect(marathon.get(this.r3), 948);
		t.checkExpect(marathon.get(this.r4), 123);
	}
}
