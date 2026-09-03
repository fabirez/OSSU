// Binary search trees are generic over the type of data they contain.
// To describe an ordering among the values, we need a comparator.
// We will use Java’s **Comparator** interface for this purpose.

/*Do Now!
	Valid binary search trees seem to be organized pretty similarly to how well-formed ancestry trees were organized.
	Try writing out in English a description of the "well-formed ancestry tree property" and of the "valid binary search tree property".
	What’s the only difference between them?
*/


// > A "well formed ancestry tree property", is a tree that rapresent the anchestors of a person.
// > Dividing 
//		- the right sub-tree with the    "dad" side of the family 
//		- the  left sub-tree with the "mother" side of the family
// > A "valid binary search tree property", is a tree that rapresenting the given data based on a comparator.
// > Dividing 
//		- the right sub-tree with the  larger values
//		- the  left sub-tree with the smaller values
  
// > The difference, is how we compare and create the sub-tree.

//       +-------------------------+
//       | abstract class ABST<T>  |
//       +-------------------------+
//       | Comparator<T> order -------------+
//       +-------------------------+         |
//                  / \                      |
//                  ---                      |
//                   |                       |
//           -----------------               |
//           |               |               |
//      +---------+    +------------+        |
//      | Leaf<T> |    | Node<T>    |        |
//      +---------+    +------------+        |
//                     | T data     |        |
//                     | ABST left  |        |
//                     | ABST right |        |
//                     +------------+        |
//                                           V
// +---------------+    +-------------------------+
// | Book          |    | Comparator<T>          |
// +---------------+    +-------------------------+
// | String title  |    | int compare(T t1, T t2) |
// | String author |    +-------------------------+
// | int price     |
// +---------------+

/*
 - [x] Design the classes 
			- [x] BooksByTitle
			- [x] BooksByAuthor
			- [x] BooksByPrice 
			that allow us to compare the books by their title, their author,
			or price respectively (i.e. these classes will implement the Comparator<T> interface).
			String-based comparisons should be alphabetical; numeric comparisons should be by increasing value.

- [x] Design the classes that represent a binary search tree,
			following the class diagram shown above.

			The Node and Leaf constructors should take arguments in the order of its fields as above,
			starting with the inherited ones.

			Make examples of data, including binary search trees of Books.

			(In this example we use an abstract class, rather than an interface, because we need the order field.).
			Please write comments above each example so that we know more about what examples you are creating.

- [x] Design the method insert that takes an item and produces a new binary search tree with the given item inserted in the correct place.
			If the value is a duplicate according to the tree order, insert it into the right-side subtree.
			This should work for any of the comparators above.
			(Where should the newly created nodes obtain their ordering from?) Make sure you’ve tested this method thoroughly before proceeding as the other methods depend on a properly constructed tree.
			Remember, you want to design incrementally, make sure one method fully works before proceeding to another.

- [x] Design the method present that takes an item and returns whether that item is present in the binary search tree.
			This method should use the Comparator object used to build the tree 
			(i.e. if BooksByTitle is used, then this method returns true if there is a book with the same title as the item, etc.)

- [x] Design the method getLeftmost that returns the leftmost item contained in this tree.
			In the Leaf class,
			you should throw an exception: throw new RuntimeException("No leftmost item of an empty tree")

- [x] Design the method getRight that returns the tree containing all but the leftmost item of this tree.
			In the Leaf class,
			you should throw an exception: throw new RuntimeException("No right of an empty tree")

- [x] Design the method sameTree that determines whether this binary search tree is the same as the given one:
			that is,
			they have matching structure and matching data in all nodes.

- [x] Design the method sameData that determines whether this binary search tree contains the same data as the given tree.

- [x] Copy into your project the IList<T> interface and its related classes.
			Make examples of IList<Book>,
			including examples that contain the same books (in the same order) as some of your binary search trees.

- [x] Design the method buildList for the classes that represent the binary search tree of type T that produces a list of items in the tree in the sorted order.
			HINT: Draw a simple BST with 2 or 3 items, and the expected output of buildList.
			Now think about how to produce this list, given the tree. Now try with bigger examples
*/

import tester.*;

interface Comparator<T>
{
	// return  a positive number if t1  > t2
	// return               zero if t1 == t2
	// return  a negative number if t1  < t2
	int compare(T t1, T t2);
}

class BookByTitle implements Comparator<Book>{
	public int compare(Book t1, Book t2){
		return t1.title.compareTo(t2.title);
	}
}

class BookByAuthor implements Comparator<Book>{
	public int compare(Book t1, Book t2){
		return t1.author.compareTo(t2.author);
	}
}

class BookByPrice implements Comparator<Book>{
	public int compare(Book t1, Book t2){
		return t1.price - t2.price;
	}
}

abstract class ABST<T>
{
	Comparator<T> order;
	ABST(Comparator<T> order){ this.order=order; }

	abstract ABST<T> getLeftTree();
	abstract ABST<T> getRightTree();
	abstract T getData();

	// insert the given item in the bst and produce it
  abstract ABST<T> insert(T item);

	// return true if the given item is prensent in the tree; false otherwise
  abstract boolean present(T item);
	// returns the leftmost item contained in this tree.
	abstract T getLeftmost();

	// returns the tree containing all but the leftmost item of this tree.
	abstract ABST<T> getRight(); 
	abstract ABST<T> getRightHelper();

	// produce true if this tree is the same as the given tree; false otherwise.
	// same means :
	//  1. matching structure 
	//  2. matching data			(It's already the same type, (T) thanks to che compile check)
	abstract boolean sameTree(ABST<T> that);
	abstract boolean sameLeftTree(ABST<T> that);
	abstract boolean sameRightTree(ABST<T> that);

	// produce how many node are present in a tree.
	abstract int length();
	// determines whether this binary search tree contains the same data as the given tree.
	abstract boolean sameData(ABST<T> that);
	abstract boolean sameDataHelper(ABST<T> that);
	abstract boolean checkData(T data);

	// produces a list of items in the tree in the sorted order.
	abstract IList<T> buildList();
	abstract IList<T> buildListHelper(ABST<T> tree, int length, int cont);
}


class Node<T> extends ABST<T>{
	T data;
	ABST<T>  left;
	ABST<T> right;

	Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right){
		super(order);
		this.data=data;
		this.left=left;
		this.right=right;
	}

	ABST<T> insert(T item){
		if(this.order.compare(item, this.data) >= 0){
			return new Node<T>(this.order, this.data, this.left, this.right.insert(item));
		}else{
			return new Node<T>(this.order, this.data, this.left.insert(item), this.right);
		}
	}

  boolean present(T item)
	{
		int res = this.order.compare(item, this.data);
		if(res == 0){
			return true;
		}else if(res > 0){
			return this.right.present(item);
		}else{
			return this.left.present(item);
		}
	}

	T getLeftmost()
	{
		if(this.left instanceof Leaf){
			return this.data;
		}else{
			return this.left.getLeftmost();
		}
	}

	ABST<T> getRight()
	{
		// Edge case: if the root itself is the minimun val, just return the right sub-tree.
		if(this.left instanceof Leaf){
			return this.right;
		}else{
		  return new Node<T>(
				this.order,
				this.data,
				this.left.getRightHelper(),
				this.right); 
		}
		
	}

	ABST<T> getRightHelper()
	{
		if(this.left instanceof Leaf){
			return new Leaf<T>(this.order);
		}else{
			return new Node<T>(
				this.order,
				this.data,
				this.left.getRightHelper(),
				this.right); 
		}
	}

	boolean sameTree(ABST<T> that) { return this.sameLeftTree(that) && this.sameRightTree(that); }

	boolean sameLeftTree(ABST<T> that)
	{
		if(
		(this.left instanceof Node && that.getLeftTree() instanceof Node) 
		||
		(this.left instanceof Leaf && that.getLeftTree() instanceof Leaf) 
	)
		{
			return this.left.sameLeftTree(that.getLeftTree());
		}else{
			return false;
		}
	}

	boolean sameRightTree(ABST<T> that)
	{ 
		if(
		(this.right instanceof Node && that.getRightTree() instanceof Node)
		||
		(this.right instanceof Leaf && that.getRightTree() instanceof Leaf)
	)
		{
			return this.right.sameRightTree(that.getRightTree());
		}else{
			return false;
		}
	}

	ABST<T> getLeftTree(){ return this.left; }
	ABST<T> getRightTree(){ return this.right; }
	T getData(){ return this.data; }

	
	int length(){ return 1 + this.left.length() + this.right.length(); }

	boolean sameData(ABST<T> that)
	{
		if(this.length() != that.length()){
			return false;
		}else{
			return this.sameDataHelper(that);
		}
	}

	boolean sameDataHelper(ABST<T> that)
	{
		if(that.checkData(this.data)){
			return this.left.sameDataHelper(that) && this.right.sameDataHelper(that);
		}else{
			return false;
		}
	}

	// Data -> Boolean
	// navigate the entire tree, and return true fi one fo the node share the same data as the given one; otherwise false;
	boolean checkData(T data){
		if(this.order.compare(data, this.data) == 0){
			return true;
		}else{
			return this.right.checkData(data) || this.left.checkData(data);
		}
	}

	IList<T> buildList()
	{
		return this.buildListHelper(this, this.length(), 0);
	}

	IList<T> buildListHelper(ABST<T> tree, int length, int cont)
	{
		if(length == cont){
			return new MtList<T>();
		}else{
			return new ConsList<T>(
				tree.getLeftmost(),
				tree.buildListHelper(tree.getRight(), length, cont + 1)
			);
		}
	}

}

class Leaf<T> extends ABST<T>{
	Leaf(Comparator<T> order){
		super(order);
	}

	ABST<T> insert(T item){ return new Node<T>(this.order, item, new Leaf<T>(this.order), new Leaf<T>(this.order)); }

  boolean present(T item){ return false; }

	T getLeftmost() { throw new RuntimeException("No leftmost item of an empty tree"); }

	ABST<T> getRight() { throw new RuntimeException("No right of an empty tree"); }
	ABST<T> getRightHelper(){ throw new RuntimeException("No right of an empty tree"); }

	boolean sameTree(ABST<T> that) { return that instanceof Leaf; }
	boolean sameLeftTree(ABST<T> that) { return that instanceof Leaf; }
	boolean sameRightTree(ABST<T> that) { return that instanceof Leaf; }
	ABST<T> getLeftTree(){ return this; }
	ABST<T> getRightTree(){ return this; }
	T getData(){ throw new RuntimeException("No data in an empty tree"); }


	int length(){ return 0; }

	boolean sameData(ABST<T> that) { return false; }
	boolean sameDataHelper(ABST<T> that) { return true; }
	boolean checkData(T data){ return false; }

	IList<T> buildList(){ throw new RuntimeException("Cannot build a list from a leaf");  };
	IList<T> buildListHelper(ABST<T> tree, int length, int cont) {  throw new RuntimeException("Cannot build a list from a leaf");  };
}

class Book{
	String title;
	String author;
	int price;

	Book(String title, String author, int price){
		this.title=title;
		this.author=author;
		this.price=price;
	}
}

interface IList<T>{ }

class ConsList<T> implements IList<T>{
	T first;
	IList<T> rest;

	ConsList(T first, IList<T> rest){
		this.first=first;
		this.rest=rest;
	}
}

class MtList<T> implements IList<T>{
	MtList(){}
}

class ExamplesABST{
	ExamplesABST(){}

	Book b0 = new Book("Structure and Interpretation of Computer Programs", "Hal Abelson", 80);
	Book b1 = new Book("Introduction to Algorithms", "Thomas H. Cormen", 93);
	Book b2 = new Book("The Art of Computer Programming", "Donald Knuth", 150);
	Book b3 = new Book("Computer Systems: A Programmer's Perspective", "Randal  Bryant", 170);

	Book b4 = new Book("Book1", "Author1", 190);
	Book b5 = new Book("Book2", "Author2", 70);
	Book b6 = new Book("Book3", "Author3", 85);

	Comparator<Book> bBP = new BookByPrice();
	Comparator<Book> bBA = new BookByAuthor();
	Comparator<Book> bBT = new BookByTitle();

	// Price
	ABST<Book> l0 = new Leaf<Book>(bBP);
	ABST<Book> n0 = new Node<Book>(bBP, b0, l0, l0);
	ABST<Book> n1 = new Node<Book>(bBP, b1, l0, l0);
	ABST<Book> n2 = new Node<Book>(bBP, b2, l0, l0);
	ABST<Book> n3 = new Node<Book>(bBP, b3, l0, l0);
	ABST<Book> n4 = new Node<Book>(bBP, b4, l0, l0);
	ABST<Book> n5 = new Node<Book>(bBP, b5, l0, l0);
	ABST<Book> n6 = new Node<Book>(bBP, b6, l0, l0);

	// Author
	ABST<Book> l0A = new Leaf<Book>(bBA);
	ABST<Book> n0A = new Node<Book>(bBA, b0, l0A, l0A);
	ABST<Book> n1A = new Node<Book>(bBA, b1, l0A, l0A);
	ABST<Book> n2A = new Node<Book>(bBA, b2, l0A, l0A);
	ABST<Book> n3A = new Node<Book>(bBA, b3, l0A, l0A);
	ABST<Book> n4A = new Node<Book>(bBA, b4, l0A, l0A);
	ABST<Book> n5A = new Node<Book>(bBA, b5, l0A, l0A);
	ABST<Book> n6A = new Node<Book>(bBA, b6, l0A, l0A);

	// Title
	ABST<Book> l0T = new Leaf<Book>(bBT);
	ABST<Book> n0T = new Node<Book>(bBT, b0, l0T, l0T);
	ABST<Book> n1T = new Node<Book>(bBT, b1, l0T, l0T);
	ABST<Book> n2T = new Node<Book>(bBT, b2, l0T, l0T);
	ABST<Book> n3T = new Node<Book>(bBT, b3, l0T, l0T);
	ABST<Book> n4T = new Node<Book>(bBT, b4, l0T, l0T);
	ABST<Book> n5T = new Node<Book>(bBT, b5, l0T, l0T);
	ABST<Book> n6T = new Node<Book>(bBT, b6, l0T, l0T);

	/*
	  b1
	 /  \
	b0   b2
	*/
	// Binary search tree based on the price of the book
	ABST<Book> t0 = new Node<Book>(bBP, b1, n0, n2);

	/*
	  b5
	 /  \
	b4   b6
	*/
	// Binary search tree based on the title of the book
	ABST<Book> t1 = new Node<Book>(bBT, b5, n4T, n6T);
	/*
	  b5
	 /  \
	b4   b6
	*/
	// Binary search tree based on the author of the book
	ABST<Book> t2 = new Node<Book>(bBA, b5, n4A, n6A);

	IList<Book> mtBook = new MtList<Book>();

	IList<Book> lob0 = new ConsList<Book>(b0, 
	new ConsList<Book>(b1,
	new ConsList<Book>(b2,
	mtBook)));

	IList<Book> lob1 = new ConsList<Book>(b4, 
	new ConsList<Book>(b5,
	new ConsList<Book>(b6,
	mtBook)));

	boolean testInsert(Tester t){
		/*
		  n1
		 /  \
		n0   n2
					\
					n3
		*/
		ABST<Book> n2_ = new Node<Book>(bBP, b2, l0, n3);
		ABST<Book> t0_ = new Node<Book>(bBP, b1, n0, n2_);

		/*
		  n1
		 /  \
		n0   n2
					\
					n3
						\
						n4
		*/
		ABST<Book> n3_ = new Node<Book>(bBP, b3, l0, n4);
		ABST<Book> n2_1 = new Node<Book>(bBP, b2, l0, n3_);
		ABST<Book> t0_1 = new Node<Book>(bBP, b1, n0, n2_1);

		/*
		  n1
		 /  \
		n0   n2
	 /				
	n5
		*/
		ABST<Book> n0_1 = new Node<Book>(bBP, b0, n5, l0);
		ABST<Book> t0_2 = new Node<Book>(bBP, b1, n0_1, n2);

		/*
		  n1
		 /  \
		n0   n2
		  \			
			 n6
		*/
		ABST<Book> n0_2 = new Node<Book>(bBP, b0, l0,   n6);
		ABST<Book> t0_3 = new Node<Book>(bBP, b1, n0_2, n2);

		return
		// Inserting node to the right sub-tree
		t.checkExpect(t0.insert(b3), t0_) 
		&&
		// Inserting node to the right, right sub-tree
		t.checkExpect(t0_.insert(b4), t0_1)
		&&
		// Inserting node to the left sub-tree
		t.checkExpect(t0.insert(b5), t0_2)
		&&
		// Inserting node to the left, right sub-tree
		t.checkExpect(t0.insert(b6), t0_3)
		;
	}

	boolean testPresent(Tester t){
		Book b0_ = new Book("Book0_", "Author0_", 80);
		Book b1_ = new Book("Book1", "Author0_", 293);
		Book b2_ = new Book("Book0_", "Author1", 849);
		return
		// Searching by price
		t.checkExpect(t0.present(b1), true)
		&&
		t.checkExpect(t0.present(b0), true)
		&&
		t.checkExpect(t0.present(b2), true)
		&&
		t.checkExpect(t0.present(b3), false)
		&&
		// Searching by title
		t.checkExpect(t1.present(b0), false)
		&&
		t.checkExpect(t1.present(b4), true)
		&&
		t.checkExpect(t1.present(b5), true)
		&&
		// Searching by author
		t.checkExpect(t2.present(b0), false)
		&&
		t.checkExpect(t2.present(b4), true)
		&&
		t.checkExpect(t2.present(b5), true)
		&&
		// Different book same price
		t.checkExpect(t0.present(b0_), true)
		&&
		// Different book same title
		t.checkExpect(t1.present(b1_), true)
		&&
		// Different book same author
		t.checkExpect(t2.present(b2_), true)
		;
	}

	boolean testGetLeftmost(Tester t){
		/*
		  n1
		 /  \
		n0   n2
	 /				
	n5
		*/
		ABST<Book> n0_1 = new Node<Book>(bBP, b0, n5, l0);
		ABST<Book> t0_2 = new Node<Book>(bBP, b1, n0_1, n2);
		return
		t.checkExpect(t0.getLeftmost(), b0)
		&&
		t.checkExpect(t1.getLeftmost(), b4)
		&&
		t.checkExpect(t2.getLeftmost(), b4)
		&&
		t.checkExpect(t0_2.getLeftmost(), b5)
		&&
		t.checkExpect(n0.getLeftmost(), b0)
		&&
		t.checkException(new RuntimeException("No leftmost item of an empty tree"), l0, "getLeftmost")
		;
	}


	boolean testGetRight(Tester t){
		/*
		  b1
		   \
		    b2
		*/
		ABST<Book> t0_ = new Node<Book>(bBP, b1, l0, n2);

		/*
		  b5
		    \
		     b6
		*/
		ABST<Book> t1_ = new Node<Book>(bBT, b5, l0T, n6T);
		/*
		  b5
		    \
		     b6
		*/
		ABST<Book> t2_ = new Node<Book>(bBA, b5, l0A, n6A);

		/* t0_2
		  n1
		 /  \
		n0   n2
	 /				
	n5
 /
n6
		*/
		Book b6 = new Book("Book6", "Author6", 5);
		ABST<Book> n6_1 = new Node<Book>(bBP, b6,   l0, l0);
		ABST<Book> n5_1 = new Node<Book>(bBP, b5, n6_1, l0);
		ABST<Book> n0_1 = new Node<Book>(bBP, b0, n5_1, l0);
		ABST<Book> t0_2 = new Node<Book>(bBP, b1, n0_1, n2);

		ABST<Book> n5_2 = new Node<Book>(bBP, b5,   l0, l0);
		ABST<Book> n0_2 = new Node<Book>(bBP, b0, n5_2, l0);
		ABST<Book> t0_3 = new Node<Book>(bBP, b1, n0_2, n2);

		return
		t.checkExpect(t0.getRight(), t0_)
		&&
		t.checkExpect(t1.getRight(), t1_)
		&&
		t.checkExpect(t2.getRight(), t2_)
		&&
		t.checkExpect(t0_2.getRight(), t0_3)
		;
	}

	boolean testSameTree(Tester t){
		return
		t.checkExpect(t0.sameTree(t1), true)
		&&
		t.checkExpect(t1.sameTree(t2), true)
		&&
		t.checkExpect(t2.sameTree(t0), true)
		;
	}

	boolean testLength(Tester t){
		return
		t.checkExpect(t0.length(), 3)
		&&
		t.checkExpect(t1.length(), 3)
		&&
		t.checkExpect(t2.length(), 3)
		&&
		t.checkExpect(l0.length(), 0)
		;
	}

	boolean testSameData(Tester t){
// bstA:       bstB:       bstC:       bstD:
//      b3          b3          b2          b3
//     /  \        /  \        /  \        /  \
//    b2  b4      b2  b4      b1   b4     b1   b4
//   /           /                /             \
// b1           b1               b3              b5

		Book b1_ = new Book("Book1_", "Author1_", 10);
		Book b2_ = new Book("Book2_", "Author2_", 20);
		Book b3_ = new Book("Book3_", "Author3_", 30);
		Book b4_ = new Book("Book4_", "Author4_", 40);
		Book b5_ = new Book("Book5_", "Author5_", 50);

	  ABST<Book> n1_ = new Node<Book>(bBP, b1_, l0, l0);
	  ABST<Book> n2_ = new Node<Book>(bBP, b2_, n1_, l0);
	  ABST<Book> n3_ = new Node<Book>(bBP, b3_, l0, l0);
	  ABST<Book> n4_ = new Node<Book>(bBP, b4_, l0, l0);
	  ABST<Book> n5_ = new Node<Book>(bBP, b5_, l0, l0);


		ABST<Book> bstA = new Node<Book>(bBP, b3_, n2_, n1_);
		ABST<Book> bstB = new Node<Book>(bBP, b3_, n2_, n1_);

	  ABST<Book> n4_1 = new Node<Book>(bBP, b4_, n3_, l0);
		ABST<Book> bstC = new Node<Book>(bBP, b2_, n1_, n4_1);

	  ABST<Book> n4_2 = new Node<Book>(bBP, b4_, l0, n5_);
		ABST<Book> bstD = new Node<Book>(bBP, b3_, n1_, n4_2);
		// bstA has the sameData as bstB
		// bstA has the sameData as bstC
		// bstA does not have the sameData as bstD
		return
		t.checkExpect(t0.sameData(t1), false)
		&&
		t.checkExpect(t0.sameData(t0), true)
		&&
		t.checkExpect(l0.sameData(l0), false)
		&&
		t.checkExpect(bstA.sameData(bstB), true)
		&&
		t.checkExpect(bstA.sameData(bstC), true)
		&&
		t.checkExpect(bstA.sameData(bstD), false)
		;
	}

	boolean testBuildList(Tester t){
		return
		t.checkExpect(t0.buildList(), lob0);
	}
}
