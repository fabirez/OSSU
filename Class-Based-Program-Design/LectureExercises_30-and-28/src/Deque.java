class Deque<T>{
  ANode<T> header;
  Deque(){ this.header=new Sentinel<T>(); }
  Deque(ANode<T> header)
  {
    if(header instanceof Sentinel){
      this.header=header; 
    }else{
      throw new IllegalArgumentException("Dequee can only accept sentinel as header.");
    }
  }
  // counts the number of nodes in a list Deque
  int size()
  {
    if(this.header.getNext() == null){
      return 0;
    }else{
      return this.header.getNext().size();
    }
  }

  // return true if the deque is empty; otherwise false
  boolean isEmpty(){ return this.size() <= 0; }

  // consumes a value of type T and inserts it at the front of the list.
  void addAtHead(T newEl)
  {
      ANode<T> currHead = this.header.getNext();
      // Create the new nod and make it point to the next first item, and the sentinel as prev.
      ANode<T> newHead = new Node<T>(currHead, this.header, newEl);
      // Make the sentinel point at the newHead.
      this.header.next = newHead;
      // Modify the prev of the previous head (currHead)
      currHead.prev = newHead;
    
  }

  // consumes a value of type T and inserts it at the tail of this list.
  void addAtTail(T newEl)
  {
      ANode<T> currTail = this.header.getPrev();
      // Create the new node and make it point to the next first item, and the sentinel as prev.
      ANode<T> newTail = new Node<T>(this.header, currTail, newEl);
      // Make the sentinel point at the newTail.
      this.header.prev = newTail;
      // Modify the prev of the previous head (currTail)
      currTail.next = newTail;
  }

  // adding a tail or a head in a list empty, has the same logic
  // that's why this function exitst
  // EFFECT: create a new node in an empty list, with the given item
  void addFirstNode(T item){
      // Create the new nod and make it point to sentinel in both prev and next.
      ANode<T> newNode = new Node<T>(this.header, this.header, item);
      // Make the sentinel point at the newNode.
      this.header.next = newNode;
      this.header.prev = newNode;
  }

  // removing a tail or a head in a list white ONE node, has the same logic
  // that's why this function exitst
  // remove the first node in a list with one item.
  ANode<T> removeFirstNode(ANode<T> item){
      // Make the sentinel point at the newNode.
      this.header.next = null;
      this.header.prev = null;
      return item;
  }

  T remove(String whichOne)
  {

    if(
        !whichOne.toLowerCase().equals("head")
        ||
        !whichOne.toLowerCase().equals("tail")
      )
    {
      throw new RuntimeException("Can only remove items from head and tail, please use these two values.");
    }

      // If there are no items
      if(this.size() == 0)
      {
        throw new RuntimeException("Cannot remove an item from an empty list");
      }

      // If there is only one item
      if(this.header.size() == 1)
      {
        return this.removeFirstNode(this.header.getNext()).getData();
      }

      if(whichOne.toLowerCase().equals("head"))
      {
        return this.removeFromHead();
      }else
      {
        return this.removeFromTail();
      }
  }

  void add(String whichOne, T item)
  {

    if(
      whichOne.toLowerCase().equals("head") == false
      &&
      whichOne.toLowerCase().equals("tail") == false
      )
    {
      throw new RuntimeException("Can only add items to head and tail, please use these two values.");
    }

      // If the list is empty
      if(this.size() == 0)
      {
        this.addFirstNode(item);
        return;
      }

      if(whichOne.toLowerCase().equals("head"))
      {
        this.addAtHead(item);
         return;
      }else
      {
        this.addAtTail(item);
         return;
      }
  }

  // removes the first node from this Deque.
  // Throw a RuntimeException if an attempt is made to remove from an empty list.
  // return the item that’s been removed from the list.
  T removeFromHead()
  {
    if(this.isEmpty()) { throw new RuntimeException("Cannot remove from an empty Deque!"); }
    // Get curr head to remove from the list
    ANode<T> headToRemove = this.header.getNext();
    // Get the future new head from the list, by the next of the currHead
    ANode<T> newHead = headToRemove.getNext();
    // The header points to our new header
    this.header.next = newHead;
    // The new head point to the header
    newHead.prev = this.header;
    // Return the item that's been removed
    return headToRemove.getData();
  }

  // removes the last node from this Deque.
  // Throw a RuntimeException if an attempt is made to remove from an empty list.
  // return the item that’s been removed from the list.
  T removeFromTail()
  {
    if(this.isEmpty()) { throw new RuntimeException("Cannot remove from an empty Deque!"); }
    // Get the current tail to remove
    ANode<T> tailToRemove = this.header.getPrev();
    // Get the future new head from the list, by the next of the currHead
    ANode<T> newTail = tailToRemove.getPrev();
    // The header points to our new header
    this.header.prev = newTail;
    // The new head point to the header
    newTail.next = this.header;
    // Return the item that's been removed
    return tailToRemove.getData();
  }

  // find for the class Deque that takes an Predicate<T> and produces
  // the first node in this Deque for which the given predicate returns true.
  // If the predicate never returns true for any value in the Deque,
  // then the find method should return the header node in this Deque.
  ANode<T> find(IPred<T> pred)
  {
    if(this.size() == 0)
    {
      return this.header;
    }

    return this.header.getNext().find(pred);
  }
  // remove the given node from the list and return it, if it exist;
  // otherwise return the header.
  ANode<T> removeNode(ANode<T> node)
  {
    // If there is only one element, let's just reset the header
    // with next and prev pointing to null
    if(this.size() == 1)
    {
      ANode<T> nodeToRemove = this.header.getNext();
      this.header.next = null;
      this.header.prev = null;
      return nodeToRemove;
    }

    // Create the right predicate based on the giving node.
    IPred<T> pred = new SameNode<T>(node);

    // Find handle the case when the size is 0 (list empty)
    ANode<T> nodeToRemove = this.find(pred);
    if(nodeToRemove instanceof Sentinel)
    {
      return this.header;
    }else
    {
      ANode<T> prev = nodeToRemove.getPrev();
      ANode<T> next = nodeToRemove.getNext();
      prev.next = next;
      next.prev = prev;
      return nodeToRemove;
    }
  }

  boolean contains(T el)
  {
    if(this.size() == 0)
    {
      return false;
    }

    return this.header.getNext().contains(el);
  }


}

abstract class ANode<T>{
  ANode<T> next; ANode<T> prev;

  ANode(ANode<T> next, ANode<T> prev)
  {
    this.next=next;
    this.prev=prev;
  }

  abstract int size();
  abstract T getData();
  ANode<T> getNext(){ return this.next; }
  ANode<T> getPrev(){ return this.prev; }
  abstract ANode<T> find(IPred<T> pred);
  abstract boolean contains(T el);
}

class Node<T> extends ANode<T>{
  T data;

  Node(T data)
  {
    super(null, null);
    this.data = data;
  }

  Node(ANode<T> next, ANode<T> prev, T data)
  {
    super(next, prev);
    if(prev == null || next == null){
      throw new IllegalArgumentException("The next or the prev node, cannot be null; If you inted to have null values please only pass data.");
    }
    this.data = data;
  }

  ANode<T> find(IPred<T> pred)
  {
    if(pred.apply(this.data)){
      return this;
    }else{
      return this.getNext().find(pred);
    }
  }

  boolean contains(T el)
  {
    if(el == this){
      return true;
    }else{
      return this.getNext().contains(el);
    }
  }

  T getData(){ return this.data; }
  int size(){ return 1 + this.next.size(); }
}


class Sentinel<T> extends ANode<T>{
  // a constructor that takes zero arguments, and initializes the next and prev fields of the Sentinel to the Sentinel itself.
  Sentinel(){ super(null, null); }

  Sentinel(ANode<T> next, ANode<T> prev){ super(next, prev); }
  T getData(){ throw new RuntimeException("Sentinel doesn't have data!"); }
  int size(){ return 0; }
  // If we are there, it means we searched in the whole list and didn't find a
  // node that satisfy the pred
  ANode<T> find(IPred<T> pred) { return this; }
  boolean contains(T el) { return false; }
}
