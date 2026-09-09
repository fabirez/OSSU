/*Do Now!
	These examples are not complete. What other edge cases might there be?
*/

// > the given person name is not present in the list, or the list is empty

/*Exercise
	Well...maybe we don’t need to remove this `ConsLoPerson` from the list — maybe we just need to remove its *data*.
	Why couldn’t we just “move”
	1. the data from this.rest.first into this.first, 
	2. and then do the same thing to this.rest,
	3. moving the items up one by one to eliminate the current one?
	Give three technical reasons why this approach fails.
	(Hint: consider types, aliasing, and any base cases.)
*/

// > 1. this.rest.first, can be undefined, since this.rest can be MtLo, and there is no field `first`.
// > 2. this.rest.first, it's also a bad pattern `field-of-field`.
// > 3. The previous node, will point, refer to the removed node.


/*Do Now!
	There is a serious but subtle problem with this single line of code.
	What is it?
*/

// > If we should remove the first node, then nothing happens.
// ```java
// // In ConsLoPerson
// void removePerson(String name) {
//   this.rest.removePersonHelp(name, this);
// }
// ```
// this.rest = (2) -> (3) -> (4)
// this = (1) -> (2) -> (3) -> (4)
// prev.rest = this.rest; -> 2,3,4 = 2,3,4

/*Do Now!
	Do these tests pass? Why or why not? Draw an object diagram to illustrate the situation.
*/

/*
  // Check that Eric is no longer a coworker
  t.checkExpect(this.work.findPhoneNum("Eric"), -1);
  // Check that Eric is still a friend
  t.checkExpect(this.friends.findPhoneNum("Eric"), this.eric.num);
}
*/
// > We remove Eric, from the list of work, but we never add him again in the list of friends.
// > That's why the last check will fail.
// The only ref, that we have from Eric is the on in work

/*
+------+ +------+ +-------+ +------+ +-------+ +-------+ +------+ +-------+ 
| Anne | | Bob  | | Clyde | | Gail | | Eric  | | Henry | | Dana | | Frank | 
| 1234 | | 3456 | | 6789  | | 1357 | | 12469 | | 7924  | | 9345 | | 8602  | 
+------+ +------+ +-------+ +------+ +-------+ +-------+ +------+ +-------+ 
    ^        ^        ^        ^        ^          ^        ^        ^             
    |        |        |        |        |          |        |        +-----------+ 
    |        |        |        |        +----------|--------------------------+  | 
    |        |        |        |                   |        +------+          |  |
    |        |        +--------|-------------------|----+          |          |  |                  
    |        +-----------------|-------------+     |    |          |          |  |                  
    |                          |             |     |    |          |          |  +-------+          
    |          +---------------+             |     |    |          |          |          |          
    |          |          +------------------|-----+    |          |          |          |          
 +--|----+  +--|----+  +--|----+  ++         |          |          |          |          |          
 | first |  | first |  | first |  ||      +--|----+  +--|----+  +--|----+  +--|----+  +--|----+   ++
 | rest --->| rest --->| rest --->||      | first |  | first |  | first |  | first |  | first |   ||
 +-------+  +-------+  +-------+  ++      | rest --->| rest --->| rest --->| rest --->| rest ---> ||
  ^                                       +-------+  +-------+  +-------+  +-------+  +-------+   ++
  |                                         ^
friends                                     |
                                          work
*/

/*Do Now!
	Before adding the sentinel node, we could access the first data item of a non-empty list using `theList.first`.
	Now that we have a sentinel node, how can we access the first item of the list?
*/

// > Good question, i think by sentinel.rest.getFirst() or sentinel.rest.first 

/*Do Now!
	Which notion of equality—extensional or intensional—could we possibly use here that works on values of arbitrary type T?
*/

// > if we can access to a field `t.name`, then intensional.
// > In the extensional we are not sure that they share the same fields.


/*Exercise
	Try implementing addPerson2 that inserts a person with the given name and number onto the front of the list. How does having a sentinel help here?
*/

/*
void addPerson2(String name, int num) {
	this.sentinel.rest = new ConsLoPerson2(new Person2(name, num), this.sentinel.rest);
}
*/


