import tester.*;
/*
Problem 1: Embroidery

The art of creating decorative designs on fabric using a needle and thread is called embroidery.
For instance,
the following designs for a tree motif,
a flower motif or bird motif may be created by an embroidery stitch such as a cross stitch or a chain stitch:

!IMAGE

The following DrRacket data definition describes a simple piece of embroidery:

;;An EmbroideryPiece is a (make-embroidery-piece String Motif)
(define-struct embroidery-piece (name motif))
 
;; Motif is one of
;; -- CrossStitchMotif
;; -- ChainStitchMotif
;; -- GroupMotif
 
;; A CrossStitchMotif is a (make-cross-stitch-motif String Double)
(define-struct cross-stitch-motif (description difficulty))
;; interpretation: difficulty is a number between 0 and 5, with 5 being the most difficult
 
;; A ChainStitchMotif is a (make-chain-stitch-motif String Double)
(define-struct chain-stitch-motif (description difficulty))
;; interpretation: difficulty is a number between 0 and 5, with 5 being the most difficult
 
;; A GroupMotif is a (make-group-motif String [List-of Motif])
(define-struct group-motif (description motifs))

We are giving you the names of the classes or interfaces you will probably need — make sure you use these names to define your interfaces and classes.

- Draw a class diagram for the classes that represent this data definition. (It is optional to submit your diagram. You can draw this on paper, or in ASCII art as a comment in your submitted file.)

- Define Java classes that represent the definitions above.

- Name your examples class ExamplesEmbroidery

In the ExamplesEmbroidery class design the example of the following:

- A piece named "Pillow Cover", with the following motifs:
  - A "nature" group motif that has a
    - "bird" cross-stitch motif with 4.5 difficulty, a

    - "tree" chain-stitch motif with 3.0 difficulty, and a

    - "flowers" group motif with a
        - "rose" cross-stitch motif with 5.0 difficulty, a

        - "poppy" chain-stitch motif with 4.75 difficulty, and a

        - "daisy" cross-stitch motif with 3.2 difficulty

Name this example pillowCover Our test program will check that the field pillowCover in the class ExamplesEmbroidery represents this information. (You may name the other motifs, and all the items inside them, anything you like, though the names should be reasonably descriptive.)

- Design the method averageDifficulty that computes the average difficulty of all of the cross-stitch and chain-stitch motifs in an EmbroideryPiece.
Hint: you will need some helper methods for this...

- Tricky! Design the method embroideryInfo that produces one String that has in it all names of cross-stitch and chain-stitch motifs in an EmbroideryPiece,
    their stitch types in parentheses, and each motif separated by comma and space. The String should end in a period.

So for the Pillow Cover example above this String would be

"Pillow Cover: bird (cross stitch), tree (chain stitch), rose (cross stitch), poppy (chain stitch), daisy (cross stitch)."

Note: You can combine two Strings with a + operator, or by invoking the method concat:
      assuming both s1 and s2 are Strings,
      then both s1 + s2 and s1.concat(s2) do the same thing as the BSL expression (string-append s1 s2).

Note: There is a comma and space between any two entries, but not after the last one.
*/


//                +--------------------------------+
//  +-----------> | IMotif                         |<--------------------------------------------------------------------------------------------+
//  |             +--------------------------------+                                                                                             |
//  |             +--------------------------------+                                                                                             |
//  |             +--------------------------------+                                                                                             |
//  |                               |                                                                                                             |
//  |                              / \                                                                                                            |
//  |                              ---                                                                                                            |
//  |                               |                                                                                                             |
//  |                               -------------------------------------------------------------------------------                               |
//  |                                               |                           |                                 |                               |     
//  |  +--------------------------------+  +--------------------------------+ +--------------------------------+  +--------------------------------+ |
//  |  | EmbroideryPiece                |  | CrossStichMotif                | | ChainStitchMotif               |  | GroupMotif                     | |
//  |  +--------------------------------+  +--------------------------------+ +--------------------------------+  +--------------------------------+ |
//  |  | String name                    |  +--------------------------------+ +--------------------------------+  | String description             | |
//  +- | IMotif motif                   |  | String description             | | String description             |  | IMotif motifs                  |-+
//     +--------------------------------+  | double difficulty              | | double difficulty              |  +--------------------------------+
//                                         +--------------------------------+ +--------------------------------+  
                                                                      




interface ILoMotif { 
  // return the average difficulty of all of the cross-stitch and chain-stitch motifs in an EmbroideryPiece
  double averageDifficulty();

  // return lenght of the listof IMotif without counting GroupMotif
  int averageDifficultyLengthHelp();

  // return sums of all the diffcult of each IMotif excluidng GroupMotif
  double averageDifficultySumHelp();

  // return all names of cross-stitch and chain-stitch motifs in an EmbroideryPiece
  String embroideryInfo();
  String embroideryInfoHelp(String description);
}

class ConsLoMotif implements ILoMotif{
   IMotif   first;
   ILoMotif rest;

   ConsLoMotif(IMotif first, ILoMotif rest){
    this.first = first;
    this.rest  = rest;
  }

  // return lenght of the listof Motif
  public int averageDifficultyLengthHelp(){
    return this.first.averageDifficultyLengthHelp() + this.rest.averageDifficultyLengthHelp();
  }

  // return sums of all the diffcult of each IMotif excluidng GroupMotif
  public double averageDifficultySumHelp(){
    return this.first.averageDifficultySumHelp() + this.rest.averageDifficultySumHelp();
  }

  public double averageDifficulty(){
    /*Fields
    * this.first -- IMotif
    * this.rest  -- ILoMotif
    */
    return this.averageDifficultySumHelp() / this.averageDifficultyLengthHelp();
  }

  // return all names of cross-stitch and chain-stitch motifs in an EmbroideryPiece
  public String embroideryInfo(){
    /*Fields
    * this.first -- IMotif
    * this.rest  -- ILoMotif
    * Methods Field
    * this.first.embroideryInfo() -- String
    */
    return this.rest.embroideryInfoHelp(this.first.embroideryInfo());
  }

  // concatenate the list present in the group if any.
  public String embroideryInfoHelp(String description){
    if(this.first.isAGroup()){
      return this.first.getGroup().embroideryInfoHelp(description);
    }else{
      return description + ", " +  this.rest.embroideryInfoHelp(this.first.embroideryInfo());
    }
  }

}

class MtLoMotif implements ILoMotif{
  MtLoMotif(){};

  public double averageDifficulty(){
    return 0.0;
  }

  public int averageDifficultyLengthHelp(){
    return 0;
  }

  public double averageDifficultySumHelp(){
    return 0.0;
  }

  // return all names of cross-stitch and chain-stitch Strings in an EmbroideryString
  public String embroideryInfo(){
    return "";
  }

  public String embroideryInfoHelp(String description){ 
    return description;
  }
}

interface IMotif { 
  // return the average difficulty of all of the cross-stitch and chain-stitch motifs in an EmbroideryPiece
  double averageDifficulty();

  // return the average difficulty of all of the cross-stitch and chain-stitch motifs in an EmbroideryPiece
  double averageDifficultySumHelp();

  // return 1 if it's a CrossStich or ChainStitch otherwise 0;
  int averageDifficultyLengthHelp();

  // return all names of cross-stitch and chain-stitch motifs in an EmbroideryPiece
  String embroideryInfo();

  // return true if it is a group; false otherwise.
  boolean isAGroup();

  // return the listof Motif inside a group; otherwise MtLoMotif if it's not a group
  ILoMotif getGroup();
}


class EmbroideryPiece {
  String name; 
  IMotif motif;

  EmbroideryPiece(String name, IMotif motif){
    this.name  = name; 
    this.motif = motif;
  }

  double averageDifficultySumHelp(){
    /*Fields
    * this.name  -- String
    * this.motif -- IMotif;
    */
    return this.motif.averageDifficultySumHelp();
  }

  int averageDifficultyLengthHelp(){
    /*Fields
    * this.name  -- String
    * this.motif -- IMotif;
    */
    return this.motif.averageDifficultyLengthHelp();
  }

  // return the average difficulty of all of the cross-stitch and chain-stitch motifs in an EmbroideryPiece
  double averageDifficulty(){
    return this.motif.averageDifficulty();
  }

  String embroideryInfo(){
    return this.name + ": " + this.motif.embroideryInfo() + ".";
  }

  public boolean isAGroup(){
    return false;
  }
}

class CrossStichMotif implements IMotif{
  String description;
  double  difficulty;

  CrossStichMotif(String description, double difficulty){
    this.description = description;
    this.difficulty  = difficulty;
  }

  public double averageDifficultySumHelp(){
   /*Fields
   * this.description  -- String
   * this.difficulty   -- double
  */
   return this.difficulty;
  }

  public int averageDifficultyLengthHelp(){
   /*Fields
   * this.description  -- String
   * this.difficulty   -- double
  */
   return 1;
  }

  public double averageDifficulty(){
    return this.difficulty;
  }

  public String embroideryInfo(){
    return this.description + " (cross stitch)";
  }

  public boolean isAGroup(){
    return false;
  }

  public ILoMotif getGroup(){
    return new MtLoMotif();
  }

}

class ChainStitchMotif implements IMotif{
  String description;
  double  difficulty;

  ChainStitchMotif(String description, double difficulty){
    this.description = description;
    this.difficulty  = difficulty;
  }

   public double averageDifficultySumHelp(){
    /*Fields
    * this.description  -- String
    * this.difficulty   -- double
   */
    return this.difficulty;
  }

  public int averageDifficultyLengthHelp(){
   /*Fields
   * this.description  -- String
   * this.difficulty   -- double
  */
   return 1;
  }

  public double averageDifficulty(){
    /*Fields
    * this.description  -- String
    * this.difficulty   -- double
    */
    return this.difficulty;
  }

  public String embroideryInfo(){
    return this.description + " (chain stitch)";
  }

  public boolean isAGroup(){
    return false;
  }

  public ILoMotif getGroup(){
    return new MtLoMotif();
  }
}

class GroupMotif implements IMotif{
  String description;
  ILoMotif motifs;

  GroupMotif(String description, ILoMotif motifs){
    this.description = description;
    this.motifs      = motifs;
  }

   public double averageDifficultySumHelp(){
    /*Fields
    * this.name  -- String
    * this.motif -- IMotif;
    */
    return this.motifs.averageDifficultySumHelp();
  }

  public int averageDifficultyLengthHelp(){
   /*Fields
   * this.description  -- String
   * this.difficulty   -- double
  */
   return this.motifs.averageDifficultyLengthHelp();
  }

  // return the average difficulty of all of the cross-stitch and chain-stitch motifs in an EmbroideryPiece
  public double averageDifficulty(){
    return this.motifs.averageDifficulty();
  }

  // return all names of cross-stitch and chain-stitch motifs in an EmbroideryPiece
  public String embroideryInfo(){
    return this.motifs.embroideryInfo();
  }

  public boolean isAGroup(){
    return true;
  }

  public ILoMotif getGroup(){
    return this.motifs;
  }

}     

class ExamplesEmbroidery{
  ExamplesEmbroidery(){}
  
  IMotif c1 = new  CrossStichMotif("bird", 4.50);
  IMotif c2 = new ChainStitchMotif("tree", 3.00);
  
  IMotif c3 = new  CrossStichMotif( "rose", 5.00);
  IMotif c4 = new ChainStitchMotif("poppy", 4.75);
  IMotif c5 = new  CrossStichMotif("daisy", 3.20);

  ILoMotif empty = new MtLoMotif();

  ILoMotif lom_2 = new ConsLoMotif(this.c3, new ConsLoMotif(this.c4, new ConsLoMotif(this.c5, this.empty)));

  IMotif      g2 = new GroupMotif("flowers", this.lom_2);
  ILoMotif lom_1 = new ConsLoMotif(this.c1, new ConsLoMotif(this.c2, new ConsLoMotif(this.g2, this.empty)));


  IMotif g1 = new GroupMotif( "nature", this.lom_1);
  EmbroideryPiece pillowCover = new EmbroideryPiece("Pillow Cover",this.g1);

  String resultEmbroideryInfo = "Pillow Cover: bird (cross stitch), tree (chain stitch), rose (cross stitch), poppy (chain stitch), daisy (cross stitch).";
  
  boolean testAverageDifficutly(Tester t){
    return t.checkExpect(this.pillowCover.averageDifficulty(), 4.09);
  }

  boolean testAverageDifficutlyLengthHelp(Tester t){
    return t.checkExpect(this.pillowCover.averageDifficultyLengthHelp(), 5);
  }

  boolean testAverageDifficutlySumHelp(Tester t){
    return t.checkExpect(this.pillowCover.averageDifficultySumHelp(), 20.45);
  }

  boolean testEmbroideryInfo(Tester t){
    return t.checkExpect(this.pillowCover.embroideryInfo(), this.resultEmbroideryInfo);
  }

}

