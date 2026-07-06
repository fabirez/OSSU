/* # 3.1 Working with the debugger
 *
 *  The sliding-tiles game [2048](https://gabrielecirulli.github.io/2048/) was all the rage a couple of years ago.
 *  The gameplay itself is not that complicated, but since we don’t have all the necessary skills yet, we aren’t going to implement the full game right now.
 *  Instead, we’re going to model some of the data behind the gameplay.
 *
 *  Conceptually, a game piece is either a base tile or a merge tile that has two component tiles it merged from.
 *  Every game piece can tell you its value, and can merge with another game piece to form a combined tile.
 *  Assume all base tiles will have a positive, integer value: for 2048, the default starting value is 2.
 *
 * ;; A GamePiece is one of
 * ;; -- (make-base-tile Number)
 * ;; -- (make-merge-tile GamePiece GamePiece)
 *  
 * (define-struct base-tile [value])
 * (define-struct merge-tile [piece1 piece2])
 *
 *  Convert this data definition into Java classes and interfaces. Make examples of several merged tiles.
 *
 *  With the game-piece classes above, design the following:
 *
 *  - Design a method getValue that returns the value of a game piece. The value of a merged tile is always the sum of the values of its components.
 *
 *  - Design a method merge that combines this game piece with the given game piece to form a merged piece. (Ignore the precise validity-checking rules of 2048 for now; suppose that any two tiles can be merged.)
 *  
 *  - Design a method isValid that checks whether this game piece was created according to the rules of 2048: only equal-valued pieces can merge.
 */

import tester.*;



interface IGamePiece{ 
  // returns the value of a game piece
  // The value of a merged tile is always the sum of the values of its components.
  int getValue();
  // combines this game piece with the given game piece to form a merged piece
  IGamePiece merge(IGamePiece otherPiece);
  // checks whether this game piece was created according to the rules of 2048: only equal-valued pieces can merge.
  boolean isValid();
}

class MergeTitle implements IGamePiece {
  IGamePiece piece1;
  IGamePiece piece2;

  MergeTitle(IGamePiece piece1, IGamePiece piece2){
    this.piece1 = piece1;
    this.piece2 = piece2;
  }

  /* TEMPLATE
  FIELDS
   ... this.piece1 ...           -- int
   ... this.piece2 ...           -- int
  METHODS
  ... this.getValue() ...               -- int
  ... this.merge(IGamePiece) ...        -- IGamePiece
  ... this.isValid() ...                -- boolean
  METHODS FOR FIELDS:
   ... this.piece1.getValue() ...           -- int
   ... this.piece1.merge(IGamePiece) ...    -- IGamePiece
   ... this.piece1.isValid() ...            -- boolean

   ... this.piece2.getValue() ...           -- int
   ... this.piece2.merge(IGamePiece) ...    -- IGamePiece
   ... this.piece2.isValid() ...            -- boolean
  */

  public int getValue(){
    return this.piece1.getValue() + this.piece2.getValue();
  }

  public IGamePiece merge(IGamePiece otherPiece){
    return new MergeTitle(this, otherPiece);
  }

  public boolean isValid(){
   return this.piece1.getValue() == this.piece2.getValue();
  }
}

abstract class AGamePiece implements IGamePiece {
  int tile;

  AGamePiece(int tile){
    this.tile = tile;
  }


  /* TEMPLATE
  FIELDS
   ... this.tile ...           -- int
  METHODS
  ... this.getValue() ...               -- int
  ... this.merge(IGamePiece) ...        -- IGamePiece
  ... this.isValid() ...                -- boolean
  */

  public int getValue(){
    return this.tile;
  }

  public IGamePiece merge(IGamePiece otherPiece){
    return new MergeTitle(this, otherPiece);
  }

  public boolean isValid(){
   return true;
  }
}

class Tile extends AGamePiece{
  Tile(int tile){
    super(tile);
  }   
  
  /* TEMPLATE
  FIELDS
   ... this.tile ...           -- int
  METHODS
  ... this.getValue() ...               -- int
  ... this.merge(IGamePiece) ...        -- IGamePiece
  ... this.isValid() ...                -- boolean
  */
}


class ExamplesIGamePiece{
  ExamplesIGamePiece(){}

  AGamePiece p1_0 =  new Tile(2);
  AGamePiece p1_1 =  new Tile(2);
  AGamePiece p2_0 =  new Tile(4);
  AGamePiece p2_1 =  new Tile(4);
  AGamePiece p3_0 =  new Tile(8);
  AGamePiece p3_1 =  new Tile(8);
  AGamePiece p4_0 =  new Tile(16);
  AGamePiece p4_1 =  new Tile(16);

  IGamePiece p1_m = new MergeTitle(p1_0, p1_1);
  IGamePiece p2_m = new MergeTitle(p2_0, p2_1);
  IGamePiece p3_m = new MergeTitle(p3_0, p3_1);
  IGamePiece p4_m = new MergeTitle(p4_0, p4_1);

  IGamePiece p5_m = new MergeTitle(p4_0, p3_1);
  IGamePiece p6_m = new MergeTitle(p2_0, p1_1);


  boolean testGetValue(Tester t){
    return 
    t.checkExpect(this.p1_0.getValue(), 2) &&
    t.checkExpect(this.p2_0.getValue(), 4) &&
    t.checkExpect(this.p3_0.getValue(), 8) &&
    t.checkExpect(this.p4_0.getValue(),16) &&
    t.checkExpect(this.p1_m.getValue(), 4) &&
    t.checkExpect(this.p2_m.getValue(), 8) &&
    t.checkExpect(this.p3_m.getValue(),16) &&
    t.checkExpect(this.p4_m.getValue(),32);
  }

  boolean testMerge(Tester t){
    return 
    t.checkExpect(this.p1_0.merge(this.p1_1), this.p1_m) &&
    t.checkExpect(this.p2_0.merge(this.p2_1), this.p2_m) &&
    t.checkExpect(this.p3_0.merge(this.p3_1), this.p3_m) &&
    t.checkExpect(this.p4_0.merge(this.p4_1), this.p4_m) &&
    t.checkExpect(this.p1_m.merge(this.p2_m), new MergeTitle(this.p1_m, this.p2_m)) &&
    t.checkExpect(this.p1_m.merge(this.p2_0), new MergeTitle(this.p1_m, this.p2_0));
  }

  boolean testIsValid(Tester t){
    return 
    t.checkExpect(this.p1_0.isValid(),  true) &&
    t.checkExpect(this.p2_0.isValid(),  true) &&
    t.checkExpect(this.p3_m.isValid(),  true) &&
    t.checkExpect(this.p4_m.isValid(),  true) &&
    t.checkExpect(this.p5_m.isValid(), false) &&
    t.checkExpect(this.p6_m.isValid(), false);
  }
}
