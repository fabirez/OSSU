/*
Problem 2: Icecream
Here is a data definition in DrRacket:

;; An IceCream is one of:
;; -- EmptyServing
;; -- Scooped
 
;;An EmptyServing is a (make-empty-serving Boolean)
(define-struct empty-serving (cone))
 
;;A Scooped is a (make-scooped IceCream String)
(define-struct scooped (more flavor))
 
Draw the class diagram that represents this data definition.
You may draw this as ASCII-art and include it in your submission, if you wish.
Or you can just draw it on paper and not submit it.
Regardless, we think it will help you in visualizing how the data is organized.

Convert this data definition into Java.
Make sure you use the same names for data types and for the fields,
as are used in the DrRacket data definitions, converted into Java style standards.
Make sure that the constructor arguments are given in the same order as shown.

Create examples in a class ExamplesIceCream.
Include in your examples the following two ice cream orders:

– a cup of ice cream with scoops of "mint chip", "coffee", "black raspberry", and "caramel swirl"

– a cone with scoops of "chocolate", "vanilla", and "strawberry"

Hint: This is similar to the Lecture 2 - 2.4
Self-referential unions: Ancestor trees.
Essentially this will creating a list (i.e. your order will be a self-referential ice cream : ) ).

Make sure the two sample orders given above are named order1 and order2.

Note: the descriptions above are listed in the order that you would order this in real life.
Think carefully how this should be represented as data.
*/

//                 
//             +---------------------+  
//             |                     | 
//             v                     | 
//         +--------+                | 
//         |IceCream|                | 
//         +--------+                | 
//              / \                  | 
//              ---                  | 
//               |                   | 
//       -----------------           | 
//       |               |           | 
// +-------------+   +-------------+ | 
// | EmptyServing|   | Scooped     | | 
// +-------------+   +-------------+ | 
// |Boolean cone |   |Icecream more|-+ 
// +-------------+   |String flavor|        
//                   +-------------+
//
//



interface IceCream{ }

class EmptyServing implements IceCream{
  Boolean cone;
  
  EmptyServing(Boolean cone){
    this.cone = cone;
  }
}

class Scooped implements IceCream {
  IceCream more;
  String flavor;
  
  Scooped(IceCream more, String flavor){
    this.more = more;
    this.flavor = flavor;
  }
}

class ExamplesIceCream {
  ExamplesIceCream(){}

  IceCream p1 = new Scooped(new Scooped(new Scooped(new Scooped(new EmptyServing(true), "caramel swirl"), "black raspberry"), "coffee"), "mint-chip");
  IceCream p2 = new Scooped(new Scooped(new Scooped(new EmptyServing(false), "strawberry"), "vanilla"), "chocolate");
}
