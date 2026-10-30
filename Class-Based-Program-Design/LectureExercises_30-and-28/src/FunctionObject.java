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
	public boolean apply(Edge e){ return e.to == this.v; }
}

class SameVertex1 implements IPred<Vertex>{
	Vertex v;
	SameVertex1(Vertex v){ this.v = v; }
	public boolean apply(Vertex v1){ return v1 == this.v; }
}

class HasEdgeOut implements IPred<Vertex>{
	Vertex vOut;
	HasEdgeOut(Vertex vOut){ this.vOut = vOut; }
	public boolean apply(Vertex v){ return v.outEdges.some(new SameVertex(this.vOut)); }
}

class SameString implements IPred<String>{
	String s;
	SameString(String s){ this.s = s;}
	public boolean apply(String t) { return this.s.equals(t); }
}

class SameInteger implements IPred<Integer>{
	int i_;
	SameInteger(int i_){ this.i_ = i_; }
	public boolean apply(Integer i) { return this.i_ == i; }
}

class SameNode<T> implements IPred<T>{
	ANode<T> n;
	SameNode(ANode<T> n){ this.n = n;}
	// ASSUME: the data of this node and the given data is of type String
	public boolean apply(T data) { return n.getData().equals(data); }
}
