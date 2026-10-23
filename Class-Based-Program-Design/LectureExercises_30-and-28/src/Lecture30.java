
/*Do Now!
	Which is more general: directed graphs or undirected graphs?
*/

// > Since we can convert undirect to direct everytime, i would say direct.

/*Do Now!
	Which is more general: weighted graphs or unweighted graphs?
*/

// > Same reason as above, weighted.

/*
	Design a method for Graph to collect all the edges in the graph.
*/

/*Do Now!
	In the adjacency-matrix representation,
	we could find all the vertices reachable from a given vertex by looking in a row of the matrix,
	and we could find all the vertices that reach a given vertex by looking in a column of the matrix. (Why?)
	In the adjacency-list representation,
	we can easily find all the vertices reachable from a given vertex: it’s simply the outEdges field.
	Design a method inEdges on Graph that computes the list of vertices that reach the given vertex.
*/

// > Because, the row of a matrix rapresent the node/vertex itself
// > And the indexes inisde the row (cols), rapresents the positions of other edges and 
// > if there are any edges with a weight or not.

import tester.*;

interface IFunc<A, R> {
  R apply(A arg);
}

interface IFunc2<A1, A2, R> {
  R apply(A1 fn, A2 base);
}

interface IPred<A>{
	boolean apply(A a);
}

interface IPred2<A1, A2>{
	boolean apply(A1 a1, A2 a2);
}

class IsHeavy implements IPred<Edge>{
	public boolean apply(Edge e){ return e.getWeight() > 0; }
}

class GetAllEdges implements IFunc<Vertex, IList<Edge>>{
	public IList<Edge> apply(Vertex v){
		return v.getAllEdge();
	}
}

class FirstConc implements IFunc2<IList<Edge>, IList<Edge>, IList<Edge>>{
	public IList<Edge> apply(IList<Edge> l, IList<Edge> bl)
	{
		return l.concatenate(bl);
	}
}

class SameVertex implements IPred<Edge>{
	Vertex v;
	SameVertex(Vertex v){ this.v = v; }
	public boolean apply(Edge e){ 

		System.out.println("Vertex to find in fobj");
		System.out.println(this.v);

		System.out.println("Edge out:");
		System.out.println(e.to);

		System.out.println("Confrontation");
		System.out.println(e.to == this.v);
		System.out.println("");
		return e.to == this.v; 
	}
}

class HasEdgeOut implements IPred<Vertex>{
	Vertex vOut;
	HasEdgeOut(Vertex vOut){ this.vOut = vOut; }
	public boolean apply(Vertex v){ return v.outEdges.some(new SameVertex(this.vOut)); }
}

interface IList<T>
{ 
	int length();
	T getFirst();
	IList<T> getRest();
	<U> IList<U> map(IFunc<T, U> f);
	IList<T> filter(IPred<T> pred);
	IList<T> concatenate(IList<T> l);
  <U> U foldr(IFunc2<T, U, U> f, U base);
	boolean some(IPred<T> pred);
	// Return the element at the given idx
	T get(int idx);
}

class ConsLo<T> implements IList<T>{
	T        first;
	IList<T>  rest;

	ConsLo(T first, IList<T> rest){
		this.first = first;
		this.rest = rest;
	}

	public int length(){ return 1 + this.rest.length(); }
	public T getFirst(){ return this.first; }
	public IList<T> getRest(){ return this.rest; }
	public IList<T> concatenate(IList<T> l2){ return new ConsLo<T>(this.first, this.rest.concatenate(l2)); }
	public <U> IList<U> map(IFunc<T, U> f) 
	{
		return new ConsLo<U>(f.apply(this.first), this.rest.map(f));
	}
	public IList<T> filter(IPred<T> pred) 
	{
		if(pred.apply(this.first))
		{
			return new ConsLo<T>(this.first, this.rest.filter(pred));
		}else{
			return this.rest.filter(pred);
		}
		
	}

  public <U> U foldr(IFunc2<T, U, U> f, U base)
	{
    return f.apply(this.first,
                   this.rest.foldr(f, base));
	}


	public T get(int idx)
	{
			if(idx == 0){
				return this.first;
			}else{
				return this.rest.get(idx - 1);
		}
	}


	public boolean some(IPred<T> pred)
	{
		if(pred.apply(this.first))
		{
			return true; 
		}
		else
		{
			return this.rest.some(pred);
		}
	}

	
}

class MtLo<T> implements IList<T>{
	MtLo(){}
	public int length(){ return 0; }
	public T getFirst(){ throw new RuntimeException("An empty list dosn't have any first."); }
	public IList<T> getRest(){ throw new RuntimeException("An empty list dosn't have any rest."); }
	public IList<T> concatenate(IList<T> l2){ return l2; }
	public <U> IList<U> map(IFunc<T, U> f) { return new MtLo<U>(); }
  public <U> U foldr(IFunc2<T, U, U> f, U base) { return base; }
  public IList<T> filter(IPred<T> pred) { return new MtLo<T>(); }
	public T get(int idx)
	{
		 throw new RuntimeException("There is no element at the given index"); 
		// return null; Or we can return null.
	}
	public boolean some(IPred<T> pred) { return false; }
}


class Vertex {
  // ... any data about vertices, such as people's names, or place's GPS coordinates ...
  IList<Edge> outEdges; // edges from this node
	Vertex(IList<Edge> outEdges){ this.outEdges=outEdges; }
	Vertex(){ this.outEdges = new MtLo<Edge>(); }

	IList<Edge> getAllEdge(){ return this.outEdges; }
	boolean hasEdgeOut(Vertex v){ return this.outEdges.some(new SameVertex(v)); }
}

class Edge {
  Vertex from;
	Vertex to;
	int weight;
	Edge(Vertex from, Vertex to, int weight){
		this.from=from;
		this.to=to;
		this.weight=weight;
	}
	int getWeight(){ return this.weight; }
}

class Graph {
  IList<Vertex> allVertices;
	Graph(IList<Vertex> allVertices){ this.allVertices=allVertices; }
	// get all the edge in this graph.
	IList<Edge> getAllEdge(){
		return this.allVertices.map(new GetAllEdges()).foldr(new FirstConc(), new MtLo<Edge>());
	}

	// get all the edge with weight > 0 in this graph.
	IList<Edge> getHeavyEdge(){
		return this.allVertices.map(new GetAllEdges()).foldr(new FirstConc(), new MtLo<Edge>()).filter(new IsHeavy());
	}

	// Returns a list of all vertices in the graph that have an edge pointing to the given target vertex 
	IList<Vertex> inEdge(Vertex v){ return this.allVertices.filter(new HasEdgeOut(v)); }
}

class ExamplesGraph{
	Edge   e1;
	Edge   e2;
	Edge   e3;

	Edge   e4;
	Edge   e5;
	Edge   e6;

	Vertex v1;
	Vertex v2;
	Vertex v3;

	Vertex v4;
	Vertex v5;
	Vertex v6;

	Graph g1;
	Graph g2;
	Graph g3;

	void initData(){
		// Vertex
		this.v1 = new Vertex();
		this.v2 = new Vertex();
		this.v3 = new Vertex();

		this.v4 = new Vertex();
		this.v5 = new Vertex();
		this.v6 = new Vertex();

		// Edge, and connection with vertex
		this.e1 = new Edge(this.v1, this.v2, 0);
		this.v1.outEdges = new ConsLo<Edge>(this.e1, new MtLo<Edge>());
		this.e2 = new Edge(this.v2, this.v3, 0);
		this.v2.outEdges = new ConsLo<Edge>(this.e2, new MtLo<Edge>());
		this.e3 = new Edge(this.v3, this.v1, 0);
		this.v3.outEdges = new ConsLo<Edge>(this.e3, new MtLo<Edge>());

		this.e4 = new Edge(this.v4, this.v5, 1);
		this.v4.outEdges = new ConsLo<Edge>(this.e4, new MtLo<Edge>());
		this.e5 = new Edge(this.v5, this.v6, 3);
		this.v5.outEdges = new ConsLo<Edge>(this.e5, new MtLo<Edge>());
		this.e6 = new Edge(this.v6, this.v4, 5);
		this.v6.outEdges = new ConsLo<Edge>(this.e6, new MtLo<Edge>());
		
		// Graph
		IList<Vertex> allVertex = new ConsLo<Vertex>(this.v1,
			new ConsLo<Vertex>(this.v2,
				new ConsLo<Vertex>(this.v3,
					new MtLo<Vertex>())));
		this.g1 = new Graph(allVertex);
		// An empty graph
		this.g2 = new Graph(new MtLo<Vertex>());
		// Grapth with heavy edges		
		IList<Vertex> allVertex1 = new ConsLo<Vertex>(this.v4,
			new ConsLo<Vertex>(this.v5,
				new ConsLo<Vertex>(this.v6,
					new MtLo<Vertex>())));
		this.g3 = new Graph(allVertex1);
	}


	void testGetAllEdge(Tester t){
	  this.initData();
		IList<Edge> expected = new ConsLo<Edge>(this.e1,
		new ConsLo<Edge>(this.e2,
		new ConsLo<Edge>(this.e3,
		new MtLo<Edge>())));

		t.checkExpect(this.g1.getAllEdge(), expected);
		t.checkExpect(this.g2.getAllEdge(), new MtLo<Edge>());
	}

	void testGetHeavyEdge(Tester t){
	  this.initData();
		// Graph with only edges without heavy.
		t.checkExpect(this.g1.getHeavyEdge(), new MtLo<Edge>());
		// Empty Graph 
		t.checkExpect(this.g2.getHeavyEdge(), new MtLo<Edge>());
		// Heavy Graph (weight > 0)
		IList<Edge> expected = new ConsLo<Edge>(this.e4,
		new ConsLo<Edge>(this.e5,
		new ConsLo<Edge>(this.e6,
		new MtLo<Edge>())));
		t.checkExpect(this.g3.getHeavyEdge(), expected);
		// Graph with heavy and free edge
		Vertex v7 = new Vertex();
		Vertex v8 = new Vertex();

		Edge e7 = new Edge(v7, v8, 3);
		v7.outEdges = new ConsLo<Edge>(e7, new MtLo<Edge>());
		Edge e8 = new Edge(v8, v7, 0);
		v8.outEdges = new ConsLo<Edge>(e8, new MtLo<Edge>());

		IList<Vertex> allVertex = new ConsLo<Vertex>(v7,
			new ConsLo<Vertex>(v8,
					new MtLo<Vertex>()));
		Graph g4 = new Graph(allVertex);

		IList<Edge> expected1 = new ConsLo<Edge>(e7,
		new MtLo<Edge>());
		t.checkExpect(g4.getHeavyEdge(), expected1);
	}


	void testinEdge(Tester t){
		this.initData();

		Vertex v1_ = new Vertex();
		Vertex v2_ = new Vertex();
		Vertex v3_ = new Vertex();
		Vertex v4_ = new Vertex();
		
		Edge e1_ = new Edge(v1_, v2_, 0);
	  v1_.outEdges = new ConsLo<Edge>(e1_, new MtLo<Edge>());
		Edge e2_ = new Edge(v2_, v3_, 0);
		v2_.outEdges = new ConsLo<Edge>(e2_, new MtLo<Edge>());
		Edge e3_ = new Edge(v3_, v1_, 0);
		v3_.outEdges = new ConsLo<Edge>(e3_, new MtLo<Edge>());
		Edge e4_ = new Edge(v4_, v1_, 0);
		v4_.outEdges = new ConsLo<Edge>(e4_, new MtLo<Edge>());
		
		// Graph
		IList<Vertex> allVertex = new ConsLo<Vertex>(v1_,
			new ConsLo<Vertex>(v2_,
				new ConsLo<Vertex>(v3_,
					new ConsLo<Vertex>(v4_,
					new MtLo<Vertex>()))));
		Graph g1_ = new Graph(allVertex);


		IList<Vertex> expected0 = new ConsLo<Vertex>(this.v3, new MtLo<Vertex>());
		t.checkExpect(this.g1.inEdge(this.v1), expected0);
		IList<Vertex> expected1 = new ConsLo<Vertex>(this.v1, new MtLo<Vertex>());
		t.checkExpect(this.g1.inEdge(this.v2), expected1);
		IList<Vertex> expected2 = new ConsLo<Vertex>(this.v2, new MtLo<Vertex>());
		t.checkExpect(this.g1.inEdge(this.v3), expected2);
		// A vertex that deesn't exist in the graph
		t.checkExpect(this.g1.inEdge(this.v6), new MtLo<Vertex>());
		// A vertex that has more than 1 edge coming
		t.checkExpect(g1_.inEdge(v1_), new ConsLo<Vertex>(v3_, new ConsLo<Vertex>(v4_, new MtLo<Vertex>())));
		// A vertex that without any edge coming.
		t.checkExpect(g1_.inEdge(v4_), new MtLo<Vertex>());
	}

}


class ExamplesList{
	IList<Integer> loi;
	IList<String>	 los;

	void initData(){
		this.loi = new ConsLo<Integer>(1,
		 new ConsLo<Integer>(2,
				new ConsLo<Integer>(3,
					new MtLo<Integer>())));

		this.los = new ConsLo<String>("Bob",
		 new ConsLo<String>("Cheat",
				new ConsLo<String>("Don",
					new MtLo<String>())));
	}


	void testGet(Tester t){
		this.initData();
		t.checkExpect(this.loi.get(0), 1);
		t.checkExpect(this.loi.get(1), 2);
		t.checkExpect(this.loi.get(2), 3);

		t.checkExpect(this.los.get(0), "Bob");
		t.checkExpect(this.los.get(1), "Cheat");
		t.checkExpect(this.los.get(2), "Don");

		t.checkExpect(this.los.get(2), "Don");
		// Checking the method with negative integer
		t.checkException(new RuntimeException("There is no element at the given index"), loi, "get", -1);
		t.checkException(new RuntimeException("There is no element at the given index"), los, "get", -1);

		// Checking the method with +1 out of boundary 
		t.checkException(new RuntimeException("There is no element at the given index"), loi, "get", loi.length());
		t.checkException(new RuntimeException("There is no element at the given index"), los, "get", los.length());
	}
}
