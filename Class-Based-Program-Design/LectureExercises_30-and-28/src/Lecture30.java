
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

/*Do Now!
	How can we tell when two vertices of the graph are “the same”?
	Which notion of equality is most appropriate here?
*/

// > By comparing the address memory of each instance 
// > The most appropriate notion is the native `==` here.

/*Do Now!
	Why can we not just simplify the if statement to return (e.to == dest) || e.to.hasPathTo(dest)?
*/

// > Beacuse it will stop the loop, in any case (if we found the veritx or if we don't) and we want ideally to stop it only if we find that.

/*Do Now!
	Will this code always work? Give an explanation of why, or an example graph that breaks it.
*/

// > No, it will not always work. Beacuse what happens if a Node is not 'netowrked' to the chaing of edge by the first node ?
/*
    A -> B -> D -> E
    ^
    |
    C
*/

/*Do Now!
	Why are C and D “backwards” in the worklist?
*/
// > Simple beacuse as the method says `addAtHead`, we add the new element at head 
// > so the last element in the given list become the first

/*Do Now!
	Trace through the evaluation of this method,
	trying to find a path between A and C.
	Which nodes, if any, are left over in the worklist?
*/



/*
// In Graph
boolean hasPathBetween(Vertex from, Vertex to) {
  Deque<Vertex> alreadySeen = new Deque<Vertex>();
  Deque<Vertex> worklist = new Deque<Vertex>();
 
  // Initialize the worklist with the from vertex
  worklist.addAtHead(from);
  // As long as the worklist isn't empty...
  while (!worklist.isEmpty()) {
    Vertex next = worklist.removeFromHead();
    if (next.equals(to)) {
      return true; // Success!
    }
    else if (alreadySeen.contains(next)) {
      // do nothing: we've already seen this one
    }
    else {
      // add all the neighbors of next to the worklist for further processing
      for (Edge e : next.outEdges) {
        worklist.addAtHead(e.to);
      }
      // add next to alreadySeen, since we're done with it
      alreadySeen.addAtHead(next);
    }
  }
  // We haven't found the to vertex, and there are no more to try
  return false;
}
*/

// 1. Starts with worklist: [], alreadySeen: []
// 2. Add A in the worklist: [A]
// 3. Since the worklist is not empty, we are in the while loop,
//		and remove A from the worklist 
//			`worklist:[]` next = A
// 4. Since A isn't the node we are looking for, and it's not in already seen.
//		We add all 
//			the neighbords to the worklist:[B] 
//			and A to alreadySeen:[A]
// 5. `worklist=[B]` is not empty, another iteration of the loop
//		and remove B from it
//			`worklist:[]` next = B
// 6. Since B isn't the node we are looking for, and it's not in already seen.
//		We add all 
//			the neighbords to the workList:[D, C]
//			and B to alreadySeen:[B, A]
// 7. `worklist=[D, C]` is not empty, another iteration of the loop
//		and remove D from it
//			`worklist:[C]` next = D
// 8. Since D isn't the node we are looking for, and it's not in already seen.
//		We add all 
//			the neighbords to the worklist (none)
//			and D to alreadySeen:[D, B, A]
//
// 9. `worklist=[C]` is not empty, another iteration of the loop
//		and remove C from it
//			`worklist:[]` next = C
// 10. Since D is the node we are looking for, return true.


/*Exercise
	Extend the code above so that instead of merely returning true,
	it returns a list of the vertices along the path from the source to the destination.
*/

import tester.*;



// Represents a mutable collection of items
interface ICollection<T> {
  // Is this collection empty?
  boolean isEmpty();
  // EFFECT: adds the item to the collection
  void add(T item);
  // Returns the first item of the collection
  // EFFECT: removes that first item
  T remove();
}

class Stack<T> implements ICollection<T> {
  Deque<T> contents;
  Stack() {
    this.contents = new Deque<T>();
  }
  public boolean isEmpty() {
    return this.contents.isEmpty();
  }
  public T remove() {
    return this.contents.removeFromHead();
  }
  public void add(T item) {
    this.contents.addAtHead(item);
  }
}

class Queue<T> implements ICollection<T> {
  Deque<T> contents;
  Queue() {
    this.contents = new Deque<T>();
  }
  public boolean isEmpty() {
    return this.contents.isEmpty();
  }
  public T remove() {
    return this.contents.removeFromHead();
  }
  public void add(T item) {
    this.contents.addAtTail(item); // NOTE: Different from Stack!
  }
}


class Vertex {
  // ... any data about vertices, such as people's names, or place's GPS coordinates ...
  IList<Edge> outEdges; // edges from this node
	Vertex(IList<Edge> outEdges){ this.outEdges=outEdges; }
	Vertex(){ this.outEdges = new MtLo<Edge>(); }

	IList<Edge> getAllEdge(){ return this.outEdges; }
	boolean hasEdgeOut(Vertex v){ return this.outEdges.some(new SameVertex(v)); }
	// Implementation by the lecture
	boolean hasPathTo(Vertex dest, IList<Vertex> saw) {
		for (Edge e : this.outEdges) {
			if( saw.some(new SameVertex1(e.to)) ) return false;
			if (   e.to == dest            // can get there in just one step
					|| e.to.hasPathTo(dest, saw.add(e.to))) { // can get there on a path through e.to
				return true;
			}
		}
		return false;
	}
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

	// Returns true if in the graph we have an edge pointing to the given vertex; otherwise false.
	boolean hasPath(Vertex v) { return this.allVertices.get(0).hasPathTo(v, new MtLo<Vertex>()); }

	boolean bfs(Vertex from, Vertex to) { return searchHelp(from, to, new Queue<Vertex>()); }
	boolean dfs(Vertex from, Vertex to) { return searchHelp(from, to, new Stack<Vertex>()); }
	boolean searchHelp(Vertex from, Vertex to, ICollection<Vertex> worklist) {
		Deque<Vertex> alreadySeen = new Deque<Vertex>();
	 
		// Initialize the worklist with the from vertex
		worklist.add(from);
		// As long as the worklist isn't empty...
		while (!worklist.isEmpty()) {
			Vertex next = worklist.remove();
			if (next.equals(to)) {
				return true; // Success!
			}
			else if (alreadySeen.contains(next)) {
				// do nothing: we've already seen this one
			}
			else {
				// add all the neighbors of next to the worklist for further processing
				for (Edge e : next.outEdges) {
					worklist.add(e.to);
				}
				// add next to alreadySeen, since we're done with it
				alreadySeen.addAtHead(next);
			}
		}
		// We haven't found the to vertex, and there are no more to try
		return false;
	}
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
    // A ------> B
    // ^         |
    // |         v
    // +---------C
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


	void testHasPath(Tester t){
		this.initData();

		t.checkExpect(this.g1.hasPath(this.v1), true);
		t.checkExpect(this.g1.hasPath(this.v2), true);
		t.checkExpect(this.g1.hasPath(this.v3), true);
		// Testing with a given vertex that is not in the graph
		t.checkExpect(this.g1.hasPath(this.v4), false);
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

	void testAdd(Tester t){
		this.initData();
		IList<Integer> expected = new ConsLo<Integer>(0,
		new ConsLo<Integer>(1,
		 new ConsLo<Integer>(2,
				new ConsLo<Integer>(3,
					new MtLo<Integer>()))));

		IList<String> expected1 = new ConsLo<String>("Al",
		new ConsLo<String>("Bob",
		 new ConsLo<String>("Cheat",
				new ConsLo<String>("Don",
					new MtLo<String>()))));

		t.checkExpect(this.loi.add(0), expected);
		t.checkExpect(this.los.add("Al"), expected1);
	}
}
