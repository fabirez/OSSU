/* Do Now!
	What kinds of things should we look for, when looking for a “good” algorithm ?
	(What does “good” even mean in this context?) Brainstorm several possibilities.
*/


// > The first things that comes up in my mind are, time (how muhc time takes an algorithm to produce a correct output)
// > The second is, the correctness less edge case, 0 is perfect.
// > Thir the space ? How much memory an algorith takes from

/*Do Now!
	Figure out this cost. Justify your answer.
*/

/*
- Constants (like 1, true, or "hello") are free of cost.

- Every arithmetic operation costs 1
     unit, plus the costs associated with evaluating its subexpressions.

- Every method invocation costs 1
     unit, plus the costs associated with evaluating the arguments, plus the cost of evaluating the method body.

- Every statement costs 1
     unit, plus the costs associated with evaluating its subexpressions.
*/

// > Following the list above, 
// > for each item in the list n in ConsList, we have two cost model
//	1. the arithmetic operation
//	2. the method call `length`
//	so it's n * (1+1)
// > only one method call `length`on MtLo = 1
// > the return statement on MtLo = 1
// > so it's (1+1)
// > n * (1+1) + (1+1)

