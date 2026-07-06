/* Do Now!
*
* The location field is named center in the Circle class and represents the location of the center of the circle,
* but is named nw in the Square and Rect classes, and represents the location of the north-west corner of the square or rectangle.
* Does it make sense to consider these to be “the same” field? Why or why not?
*/

// > Yes, because they all inherit the same fields and methods from the class.
//   They can have a different meaning or purpose for each clss, but that doesn't make
//   them different types. They all are using the constructor by CartPt.


/* Do Now!
* Does it make sense to be able to construct an AShape? What would that mean?
*/

// > Yes, that mean that every class that implentes the interface IShape will inheritate the fields 
// > declared inside the abstracted class

