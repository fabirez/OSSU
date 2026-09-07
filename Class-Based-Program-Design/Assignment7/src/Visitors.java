/*
- [x] Implement the following class diagram:
      +-------------------+
      | IArith            |
      +-------------------+
      +-------------------+
          /_\      /_\
           |        |----------------------------------------------------------------
           |        |                                                               |
+-------------+  +------------------------------+           +------------------------------------------+
| Const       |  | UnaryFormula                 |           |              BinaryFormula               |
+-------------+  +------------------------------+           +------------------------------------------+
| double num  |  | Function<Double,Double> func |           | BiFunction <Double, Double, Double> func |
+-------------+  | String name                  |           | String name                              |
                 | IArith child                 |           | IArith left                              |
                 +------------------------------+           | IArith right                             |
                                                            +------------------------------------------+


Specifically,
the above represents an arithmetic expression.
The Function and BiFunction in UnaryFormula and BinaryFormula respectively denote the arithmetic operation to be applied to their respective operands (child and left,right).

- [x] You must support 4 binary formulas (named 
		- [x] "plus",
		- [x] "minus",
		- [x] "mul"
		- [x] "div" 
		representing addition, subtraction, multiplication and division respectively),
		and 2 unary formulas (named 
		- [x] "neg"
		- [x] "sqr" 
		representing negation and squaring respectively).

- [x] Design an interface IArithVisitor<R> representing a visitor that visits an IArith and produces a result of type R.
			The visitor must be usable as a Function object on IArith producing a result of type R.
			For example,
			given an IArith object named iObj,
			and a SomeVisitor class, we should be able to write new SomeVisitor().apply(iObj).

- [x] Design an accept(IArithVisitor<R>) method for the IArith interface and implement it on Const, UnaryFormula and BinaryFormula.

- [x] Design an EvalVisitor that visits an IArith and evaluates the tree to a Double answer.

- [x] Design a PrintVisitor that visits an IArith and produces a String showing the fully-parenthesized expression in Racket-like prefix notation 
			(i.e. "(div (plus 1.0 2.0) (neg 1.5))"),
			using the name for Formulas and using Double.toString(num) on Consts.

- [x] Design a DoublerVisitor that visits an IArith and produces another IArith, where every Const in the tree has been doubled.

- [x] When evaluating the result,
			the computer on which this program will run has no support for negative numbers.
			Design a NoNegativeResults visitor that visits an IArith and produces a Boolean that is true,
			if a negative number is never encountered at any point during its evaluation.
*/
import tester.*;

interface IFunc<T, R>{
	R apply(T t);
}


interface Function<R,T>{ 
	R apply(T n) ;
}

class Sqr implements Function<Double, Double>{
	public Double apply(Double n){
		return Math.sqrt(n);
	}
}

class Neg implements Function<Double, Double>{
	public Double apply(Double n){
		return n * -1;
	}
}

interface BiFunction<R, T1, T2>{ 
	R apply(T1 n1, T2 n2) ;
}

class Plus implements BiFunction<Double, Double, Double>{
	public Double apply(Double n1, Double n2){
		return n1 + n2;
	}
}

class Minus implements BiFunction<Double, Double, Double>{
	public Double apply(Double n1, Double n2){
		return n1 - n2;
	}
}

class Mul implements BiFunction<Double, Double, Double>{
	public Double apply(Double n1, Double n2){
		return n1 * n2;
	}
}

class Div implements BiFunction<Double, Double, Double>{
	public Double apply(Double n1, Double n2){
		return n1 / n2;
	}
}

interface IArithVisitor<R>{ 
	R visitC(Const c);
	R visitU(UnaryFormula u);
	R visitB(BinaryFormula b);
}


class EvalVisitor implements IArithVisitor<Double>{
	public Double visitC(Const c){
		return c.num;
	}

	public Double visitU(UnaryFormula u){
		return u.func.apply(u.child.accept(this)); 
	}

	public Double visitB(BinaryFormula b){
		return b.func.apply(b.left.accept(this), b.right.accept(this));
	}
}

class PrintVisitor implements IArithVisitor<String>{

	public String visitC(Const c){
		return String.valueOf(c.num);
	}

	public String visitU(UnaryFormula u){
		return "(" + u.name + " " + u.child.accept(this) + ")"; 
	}

	public String visitB(BinaryFormula b){
		return "(" + b.name + " " + b.left.accept(this) + " " + b.right.accept(this) + ")";
	}
}

class DoubleVisitor implements IArithVisitor<IArith>{

	public IArith visitC(Const c){
		return new Const(c.num * 2);
	}

	public IArith visitU(UnaryFormula u){
		return new UnaryFormula(u.func, u.name, u.child.accept(this)); 
	}

	public IArith visitB(BinaryFormula b){
		return new BinaryFormula(b.func, b.name, b.left.accept(this), b.right.accept(this));
	}
}

class NoNegativeResults implements IArithVisitor<Boolean>{
	public Boolean visitC(Const c){
		return c.num > 0;
	}

	public Boolean visitU(UnaryFormula u){
		double childNum = u.child.accept(new EvalVisitor());
		return childNum > 0 && u.func.apply(u.child.accept(new EvalVisitor())) > 0; 
	}

	public Boolean visitB(BinaryFormula b){
		double leftNum =  b.left.accept(new EvalVisitor());
		double rightNum = b.right.accept(new EvalVisitor());
		return leftNum > 0 && rightNum > 0 && b.func.apply(leftNum, rightNum) > 0;
	}
}

interface IArith{ 
	<R> R accept(IArithVisitor<R> visitor);
}

class Const implements IArith{
	double num;
	Const(double num){
		this.num = num;
	}

	public <R> R accept(IArithVisitor<R> visitor){
		return visitor.visitC(this);
	}
}

class UnaryFormula implements IArith{
	Function<Double,Double> func;
	String name;
	IArith child;

	UnaryFormula(Function<Double,Double> func, String name, IArith child){
		this.func = func;
		this.name = name;
		this.child = child;
	}

	public <R> R accept(IArithVisitor<R> visitor){
		return visitor.visitU(this);
	}

}

class BinaryFormula implements IArith{
 BiFunction <Double, Double, Double> func;
 String name;
 IArith left;
 IArith right;

	BinaryFormula(BiFunction <Double, Double, Double> func, String name, IArith left, IArith right){
		this.func = func;
		this.name = name;
		this.left = left;
		this.right = right;
	}

	public <R> R accept(IArithVisitor<R> visitor){
		return visitor.visitB(this);
	}
}

class ExamplesArith{
	// Function
	Function<Double,Double> neg;
	Function<Double,Double> sqr;

	// [Bi]naryFunction 
	BiFunction<Double,Double,Double> plus; 
	BiFunction<Double,Double,Double> minus; 
	BiFunction<Double,Double,Double> mul; 
	BiFunction<Double,Double,Double> div; 

	// Const
	IArith c1;
	IArith c2;
	IArith c4;

	// UnaryFormula
	IArith u1;
	IArith u2;

	// BinaryFormula
	IArith b1;
	IArith b2;
	IArith b3;
	IArith b4;
	IArith b5;

	// Tree
	IArith t1;

	void initData(){
		// Function
		this.neg = new Neg();
		this.sqr = new Sqr();

		// [Bi]naryFunction 
		this.plus = new Plus();
		this.minus = new Minus();
		this.mul = new Mul();
		this.div = new Div();


		// Const
		this.c1 = new Const(1.0);
		this.c2 = new Const(2.0);
		this.c4 = new Const(4.0);

		// UnaryFormula
		this.u1 = new UnaryFormula(
			this.neg,
			"neg",
			this.c1
		);
		this.u2 = new UnaryFormula(
			this.sqr,
			"sqr",
			this.c4
		);


		// BinaryFormula
		this.b1 = new BinaryFormula(
			this.plus,
			"plus",
			this.c1,
			this.c2
		);
		this.b2 = new BinaryFormula(
			this.minus,
			"minus",
			this.c1,
			this.c2
		);
		this.b3 = new BinaryFormula(
			this.mul,
			"mul",
			this.c1,
			this.c2
		);
		this.b4 = new BinaryFormula(
			this.div,
			"div",
			this.c1,
			this.c2
		);
		this.b5 = new BinaryFormula(
			this.plus,
			"plus2",
			this.b1,
			this.b2
		);
	}

	void testUnaryFormula(Tester t){
		this.initData();
		// t.checkExpect(u1.neg(), -1.0);
		// t.checkExpect(u2.sqr(),  2.0);

		IArithVisitor<Double> eV = new EvalVisitor();
		t.checkExpect(u1.accept(eV), -1.0);
		t.checkExpect(u2.accept(eV),  2.0);
	}

	void testBinaryFormula(Tester t){
		this.initData();

		// t.checkExpect( b1.plus(),  3.0);
		// t.checkExpect(b2.minus(), -1.0);
		// t.checkExpect(  b3.mul(),  2.0);
		// t.checkExpect(  b4.div(),  0.5);


		IArithVisitor<Double> eV = new EvalVisitor();
		t.checkExpect(b1.accept(eV),  3.0);
		t.checkExpect(b2.accept(eV), -1.0);
		t.checkExpect(b3.accept(eV),  2.0);
		t.checkExpect(b4.accept(eV),  0.5);
	}

	void testEvalVisitor (Tester t){
		this.initData();

		IArith b0_ = new BinaryFormula(
				this.mul,
				"Mul2",
				new Const(8.0),
				new Const(10.0)
		);

		IArith b1_ = new UnaryFormula(
				this.sqr,
				"Sqr2",
				new Const(64.0));

		IArith b2_ = new BinaryFormula(
				this.mul,
				"Mul3",
				new UnaryFormula(
				this.sqr,
				"Sqr2",
				new Const(64.0)),
				new Const(10.0)
			);

		IArith b3_ = new BinaryFormula(
			this.minus,
			"Minus2",
			new Const(80.0),
			new Const(80.0));

		IArith b4_ = new BinaryFormula(
			this.minus,
			"Minus3",
			new BinaryFormula(
				this.mul,
				"Mul3",
				new UnaryFormula(
				this.sqr,
				"Sqr2",
				new Const(64.0)),
				new Const(10.0)
			),
			new Const(80.0)
		);
		IArithVisitor<Double> eV = new EvalVisitor();

		t.checkExpect(b5.accept(eV),  2.0);
		t.checkExpect(b0_.accept(eV), 80.0);
		t.checkExpect(b1_.accept(eV),  8.0);
		t.checkExpect(b2_.accept(eV), 80.0);
		t.checkExpect(b3_.accept(eV),  0.0);
		t.checkExpect(b4_.accept(eV),  0.0);
	}

 void testPrintVisitor(Tester t){
		this.initData();

		IArithVisitor<String> pV = new PrintVisitor();
		t.checkExpect(c1.accept(pV), "1.0");
		t.checkExpect(u1.accept(pV), "(neg 1.0)");
		t.checkExpect(u2.accept(pV), "(sqr 4.0)");
		t.checkExpect(b1.accept(pV), "(plus 1.0 2.0)");
		t.checkExpect(b2.accept(pV), "(minus 1.0 2.0)");
		t.checkExpect(b3.accept(pV), "(mul 1.0 2.0)");
		t.checkExpect(b4.accept(pV), "(div 1.0 2.0)");
		t.checkExpect(b5.accept(pV), "(plus2 (plus 1.0 2.0) (minus 1.0 2.0))");
	}

	void testDoubleVisitor(Tester t){
		this.initData();

		IArithVisitor<IArith> dV = new DoubleVisitor();
		IArith c1_ = new Const(2.0);
		IArith c2_ = new Const(4.0);
		IArith c4_ = new Const(8.0);
		IArith u1_ = new UnaryFormula(
			this.neg,
			"neg",
			c1_
		);
		IArith u2_ = new UnaryFormula(
			this.sqr,
			"sqr",
			c4_
		);

		IArith b1_ = new BinaryFormula(
			this.plus,
			"plus",
			c1_,
			c2_
		);
		IArith b2_ = new BinaryFormula(
			this.minus,
			"minus",
			c1_,
			c2_
		);
		IArith b5_ = new BinaryFormula(
			this.plus,
			"plus2",
			b1_,
			b2_
		);
		t.checkExpect(c1.accept(dV), c1_);
		t.checkExpect(u1.accept(dV), u1_);
		t.checkExpect(u2.accept(dV), u2_);
		t.checkExpect(b1.accept(dV), b1_);
		t.checkExpect(b2.accept(dV), b2_);
		t.checkExpect(b5.accept(dV), b5_);
	}

 void testNoNegativeResults(Tester t){
		this.initData();

		IArithVisitor<Boolean> nNR = new NoNegativeResults();

		IArith b1_ = new BinaryFormula(
			this.plus,
			"plus",
			u1,
			c2
		);

		t.checkExpect(c1.accept(nNR), true);
		t.checkExpect(u1.accept(nNR), false);
		t.checkExpect(b1_.accept(nNR), false);
		t.checkExpect(b1.accept(nNR), true);
		t.checkExpect(b2.accept(nNR), false);
	}
}
