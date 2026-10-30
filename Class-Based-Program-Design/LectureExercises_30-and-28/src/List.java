import java.util.Iterator;

interface IList<T> extends Iterable<T>
{ 
	boolean isCons();
	ConsLo<T> asCons();
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
	// Return the last element in the list
	T getTail();
	// Return a list with the given element as head
	IList<T> add(T el);
	// Return a list with the given element as tail
	IList<T> insert(T el);
	// Return a list without the last element (tail)
	IList<T> removeTail();
	// Return a list without the first that matches the pred and all after the given element
	// [1,2,3]
	// removeThisAndAllAfter(2) -> [1]
	IList<T> removeThisAndAllAfter(IPred<T> pred);
	// Return a list without the elements after the first matche
	// example
	// ```java
	// [1,2,3]
	// removeAllAfter(2) -> [1,2]
	// ```
 IList<T> removeAllAfter(IPred<T> pred);
}

class IListIterator<T> implements Iterator<T> {
  IList<T> items;
  IListIterator(IList<T> items) {
    this.items = items;
  }

  public boolean hasNext() {
    return this.items.isCons();
  }
  public T next() {
		ConsLo<T> itemsAsCons = this.items.asCons();
		T answer = itemsAsCons.first;
		this.items = itemsAsCons.rest;
		return answer;
  }
  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}

class ConsLo<T> implements IList<T>{
	T        first;
	IList<T>  rest;

	ConsLo(T first, IList<T> rest){
		this.first = first;
		this.rest = rest;
	}

	public Iterator<T> iterator() { return new IListIterator<T>(this); }
	public boolean isCons() { return true; }
	public ConsLo<T> asCons() { return this; }
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

	// Produce the list with the given element as the head of the list
	public IList<T> add(T el) { return new ConsLo<T>(el, this); }
	// Produce the list with the given element as the tail of the list
	public IList<T> insert(T el) { return new ConsLo<T>(this.first, this.rest.insert(el)); }
	public T getTail() { return this.get(this.length() - 1); }
	public IList<T> removeTail() { 
		int newLength = this.length() - 2;
		IList<T> res = new MtLo<T>();
		
		for(
			int i = newLength;
			i >= 0;
			i = i - 1
		)
	  {
			res = res.add(this.get(i));
		}
		return res;
	}
	// remove the given element and all the rest that follows it.
	public IList<T> removeThisAndAllAfter(IPred<T> pred)
	{
		if(pred.apply(this.first))
		{
			return new MtLo<T>();
		}else{
			return new ConsLo<T>(this.first, 
							this.rest.removeThisAndAllAfter(pred));
		}
	}

 // remove the rest of the list after the given element
	public IList<T> removeAllAfter(IPred<T> pred)
	{
		if(pred.apply(this.first))
		{
			return new ConsLo<T>(this.first, 
							new MtLo<T>());
		}else{
			return new ConsLo<T>(this.first, 
							this.rest.removeAllAfter(pred));
		}
	}
	
}

class MtLo<T> implements IList<T>{
	MtLo(){}
  public Iterator<T> iterator() { return new IListIterator<T>(this); }
	public boolean isCons() { return false; }
	public ConsLo<T> asCons() { throw new RuntimeException("An empty list cannot be cons."); }
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
	public IList<T> add(T el) { return new ConsLo<T>(el, new MtLo<T>()); }
	// Produce the list with the given element as the tail of the list
	public IList<T> insert(T el) { return new ConsLo<T>(el, new MtLo<T>()); }
	public T getTail() { throw new RuntimeException("There is no tail in an empty list!");  }
	public IList<T> removeTail() { throw new RuntimeException("There is no tail in an empty list!"); }
	public IList<T> removeThisAndAllAfter(IPred<T> pred){ return new MtLo<T>(); }
	public IList<T> removeAllAfter(IPred<T> pred) { return new MtLo<T>(); }
}
