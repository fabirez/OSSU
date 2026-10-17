
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


import tester.*;

interface IFunc<A, R> {
  R apply(A arg);
}

interface IFunc2<A1, A2, R> {
  R apply(A1 fn, A2 base);
}

// class GetAllVertex implements IFunc<, IList<Vertex>>

class GetAllEdges implements IFunc<Vertex, IList<Edge>>{
	public IList<Edge> apply(Vertex v){
		return v.getAllEdge();
	}
}

class FirstConc implements IFunc2<IList<Edge>, IList<Edge>, IList<Edge>>{
	public IList<Edge> apply(IList<Edge> l, IList<Edge> bl)
	{
		return bl.concatenate(l);
	}
}

interface IList<T>
{ 
	int length();
	T getFirst();
	IList<T> getRest();
	<U> IList<U> map(IFunc<T, U> f);
	IList<T> concatenate(IList<T> l);
  <U> U foldr(IFunc2<T, U, U> f, U base);
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

  public <U> U foldr(IFunc2<T, U, U> f, U base)
	{
    return f.apply(this.first,
                   this.rest.foldr(f, base));
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
}


class Vertex {
  // ... any data about vertices, such as people's names, or place's GPS coordinates ...
  IList<Edge> outEdges; // edges from this node
	Vertex(IList<Edge> outEdges){ this.outEdges=outEdges; }
	Vertex(){}

	IList<Edge> getAllEdge(){ return this.outEdges; }
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
}

class Graph {
  IList<Vertex> allVertices;
	Graph(IList<Vertex> allVertices){ this.allVertices=allVertices; }
	// get all the edge in this graph.
	//
	IList<Edge> getAllEdge(){
		IList<IList<Edge>> allEdge = this.allVertices.map(new GetAllEdges());

		IList<Edge>        firstEdge = allEdge.getFirst();
		IList<IList<Edge>> restEdges = allEdge.getRest();

		return restEdges.foldr(new FirstConc(), firstEdge);
		// return new MtLo<Edge>();
	}
}

class ExamplesGraph{
	Edge   e1;
	Edge   e2;
	Edge   e3;

	Vertex v1;
	Vertex v2;
	Vertex v3;

	Graph g1;

	void initData(){
		// Vertex
		this.v1 = new Vertex();
		this.v2 = new Vertex();
		this.v3 = new Vertex();

		// Edge, and connection with vertex
		this.e1 = new Edge(this.v1, this.v2, 0);
		this.v1.outEdges = new ConsLo<Edge>(this.e1, new MtLo<Edge>());
		this.e2 = new Edge(this.v2, this.v3, 0);
		this.v2.outEdges = new ConsLo<Edge>(this.e2, new MtLo<Edge>());
		this.e3 = new Edge(this.v3, this.v1, 0);
		this.v3.outEdges = new ConsLo<Edge>(this.e3, new MtLo<Edge>());

		// Graph
		IList<Vertex> allVertex = new ConsLo<Vertex>(this.v1,
			new ConsLo<Vertex>(this.v2,
				new ConsLo<Vertex>(this.v3,
					new MtLo<Vertex>())));
		this.g1 = new Graph(allVertex);
	}


	void testGetAllEdge(Tester t){
	  this.initData();
		IList<Edge> expected = new ConsLo<Edge>(this.e1,
		new ConsLo<Edge>(this.e2,
		new ConsLo<Edge>(this.e3,
		new MtLo<Edge>())));
		t.checkExpect(this.g1.getAllEdge(), expected);
	}
}
