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
	IList<T> add(T el);
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

	public IList<T> add(T el) { return new ConsLo<T>(el, this); }
	
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
}
