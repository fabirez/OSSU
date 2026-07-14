import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Color.RED, Color.GRAY, etc.)
import javalib.worldcanvas.*;   //  display images on a WorldCanvas 
 
interface ITree {
  OutlineMode OUTLINE = OutlineMode.SOLID;
  Color GRAY = Color.GRAY;
  Utility0 UTILITY = new Utility0();

  // renders your ITree to a picture. 
  WorldImage draw();
  WorldImage drawHelp(int x, int y);

  // that computes whether any of the twigs in the tree (either stems or branches) 
  // are pointing downward rather than upward.
  boolean isDropping();

  //  takes the current tree and a given tree and produces a Branch using the given arguments,
  //  with this tree on the left and the given tree on the right... but with a twist, literally.
  ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree);
  ITree combineHelp(int newLength, double newTheta);

  // returns the width of the tree.
  // ASSUME: that leaves are drawn as circles, and their size is used as their radius.
  double getWidth();
  double getWidthHelp(int x, int max);
  double getMostRight(int x, int max);
  double getMostLeft(int x, int max);
}
 
class Leaf implements ITree {
  int size; // represents the radius of the leaf
  Color color; // the color to draw it

  Leaf(int size, Color color){
    this.size = size;
    this.color = color;
  }

  /* TEMPLATE
  FIELDS
  ... this.size ...    -- int
  ... this.color ...   -- Color
  METHODS
  ... this.draw() ...                               -- WorldIMage
  ... this.drawHelp(int, int) ...                   -- WorldIMage
  ... this.isDrooping() ...                         -- boolean
  ... this.combine(int, int, double, double, ITree) -- ITree
  ... this.combineHelp(int, double)                 -- ITree
  */


  // renders Leaf to a picture. 
  public WorldImage draw(){
    return this.drawHelp(0,0);
  }
  public WorldImage drawHelp(int x, int y){
    return new CircleImage(this.size, OUTLINE, this.color).movePinhole(x, y);
  }

  // return true if this stem or the tree (branchs or stems) are pointing downward rather than upward; false otherwise.
  public boolean isDropping(){
    return false;
  }

  //  takes the current tree and a given tree and produces a Branch using the given arguments,
  //  with this tree on the left and the given tree on the right... but with a twist, literally.
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree){
    return this;
  }
  public ITree combineHelp(int newLength, double newTheta){
    return this;
  }


  // In this program, i'm using the leaf as we use the Mt to the lists.
  // When we reach a leaf, return the max value founded in the tree.
  public double getWidth(){
    return this.getWidthHelp(0,0);
  }
  public double getWidthHelp(int x, int max){
    if(this.size + x > max){
      return this.size + x;
    }else{
      return max;
    }
  }
  public double getMostRight(int x, int max){
    if(this.size + x > max){
      return this.size + x;
    }else{
      return max;
    }
  }
  public double getMostLeft(int x, int max){
    if(this.size + x > max){
      return this.size + x;
    }else{
      return max;
    }
  }
}
 
class Stem implements ITree {
  // How long this stick is
  int length;
  // The angle (in degrees) of this stem, relative to the +x axis
  double theta;
  // The rest of the tree
  ITree tree;


  /* TEMPLATE
  FIELDS
  ... this.length ...    -- int
  ... this.theta ...     -- double
  ... this.tree ...      -- ITree METHODS
  METHODS:
  ... this.draw() ...                               -- WorldIMage
  ... this.drawHelp(int, int) ...                   -- WorldIMage
  ... this.isDropping() ...                         -- boolean
  ... this.combine(int, int, double, double, ITree) -- ITree 
  ... this.combineHelp(int, double)                 -- ITree


  METHODS FOR FIELDS:
  ... this.tree.draw() ...                               -- WorldIMage
  ... this.tree.drawHelp(int, int) ...                   -- WorldIMage
  ... this.tree.isDropping() ...                         -- boolean
  ... this.tree.combine(int, int, double, double, ITree) -- ITree 
  ... this.tree.combineHelp(int, double)                 -- ITree
  */

  Stem(int length, double theta, ITree tree){
    this.length=length;
    this.theta=theta;
    this.tree=tree;
  }


  // renders Steam and the tree to a picture. 
  public WorldImage draw(){
    return this.drawHelp(0,0);
  }
  public WorldImage drawHelp(int x, int y){
    return
    new OverlayImage( 
      new LineImage(
        new Posn(
          UTILITY.getBranchX(this.length, this.theta),
          UTILITY.getBranchY(this.length, this.theta)
        ),
          GRAY
      ).movePinhole(
          UTILITY.getBranchX(this.length, this.theta) / 2 + x,
          UTILITY.getBranchY(this.length, this.theta) / 2 + y),
     this.tree.drawHelp(
        UTILITY.getBranchX(this.length, this.theta) + x,
        UTILITY.getBranchY(this.length, this.theta) + y)
    );
  }

  // return true if this stem or the tree (branchs or stems) are pointing downward rather than upward; false otherwise.
  public boolean isDropping(){
    return Math.sin(Math.toRadians(this.theta)) < 0 || this.tree.isDropping();
  }



  //  takes the current tree and a given tree and produces a Branch using the given arguments,
  //  with this tree on the left and the given tree on the right... but with a twist, literally.
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree){
    return this;
  }
  public ITree combineHelp(int newLength, double newTheta){
    return this.tree.combineHelp(newLength, newTheta);
  }

  public double getWidth(){
    return this.getWidthHelp(0,0);
  }
  public double getWidthHelp(int x, int max){
    if(Math.abs(UTILITY.getBranchX(this.length, this.theta) + x) > max){
      return this.tree.getWidthHelp(
        Math.abs(UTILITY.getBranchX(this.length, this.theta)) + x,
        Math.abs(UTILITY.getBranchX(this.length, this.theta)) + x);
    }else{
      return this.tree.getWidthHelp(
        UTILITY.getBranchX(this.length, this.theta) + x,
        max);
    }
  }

  public double getMostRight(int x, int max){
    if(Math.abs(UTILITY.getBranchX(this.length, this.theta) + x) > max){
      return this.tree.getMostRight(
        Math.abs(UTILITY.getBranchX(this.length, this.theta)) + x,
        Math.abs(UTILITY.getBranchX(this.length, this.theta)) + x);
    }else{
      return this.tree.getMostRight(
        UTILITY.getBranchX(this.length, this.theta) + x,
        max);
    }
  }

  public double getMostLeft(int x, int max){
    if(Math.abs(UTILITY.getBranchX(this.length, this.theta) + x) > max){
      return this.tree.getMostLeft(
        Math.abs(UTILITY.getBranchX(this.length, this.theta)) + x,
        Math.abs(UTILITY.getBranchX(this.length, this.theta)) + x);
    }else{
      return this.tree.getMostLeft(
        UTILITY.getBranchX(this.length, this.theta) + x,
        max);
    }
  }

}
 
class Branch implements ITree {
  // How long the left and right branches are
  int leftLength;
  int rightLength;
  // The angle (in degrees) of the two branches, relative to the +x axis,
  double leftTheta;
  double rightTheta;
  // The remaining parts of the tree
  ITree left;
  ITree right;


  /* TEMPLATE
  FIELDS
  ... this.leftLength ...     -- int
  ... this.rightLength ...    -- int
  ... this.leftTheta ...      -- double
  ... this.rightTheta ...     -- double
  ... this.left ...           -- ITree
  ... this.right ...          -- ITree

  METHODS
  ... this.draw() ...                               -- WorldIMage
  ... this.drawHelp(int, int) ...                   -- WorldIMage
  ... this.isDropping() ...                         -- boolean
  ... this.combine(int, int, double, double, ITree) -- ITree 
  ... this.combineHelp(int, double)                 -- ITree

  METHODS FOR FIELDS:
  ... this.left.draw()  ...                               -- WorldIMage
  ... this.right.draw() ...                               -- WorldIMage
  ... this.left.drawHelp(int, int)  ...                   -- WorldIMage
  ... this.right.drawHelp(int, int) ...                   -- WorldIMage
  ... this.left.isDropping() ...                          -- boolean
  ... this.right.isDropping() ...                         -- boolean
  ... this.left.combine(int, int, double, double, ITree)  -- ITree 
  ... this.right.combineHelp(int, double)                 -- ITree
  */

  Branch(int  leftLength, int rightLength, double leftTheta, double rightTheta, ITree left, ITree right){
    this.leftLength=leftLength;
    this.rightLength=rightLength;
    this.leftTheta=leftTheta;
    this.rightTheta=rightTheta;
    this.left=left; 
    this.right=right;
  }

  // renders Steam and the tree to a picture. 
  public WorldImage draw(){
    return this.drawHelp(0, 0);
  }
  public WorldImage drawHelp(int x, int y){
    return new OverlayImage(
        new OverlayImage(
        new LineImage(
          new Posn(
            UTILITY.getBranchX(this.leftLength, this.leftTheta),
            UTILITY.getBranchY(this.leftLength, this.leftTheta) 
          ),
          GRAY)
        .movePinhole(
          UTILITY.getBranchX(this.leftLength, this.leftTheta) / 2 + x,
          UTILITY.getBranchY(this.leftLength, this.leftTheta) / 2 + y),
        this.left.drawHelp(
          UTILITY.getBranchX(this.leftLength, this.leftTheta) + x,
          UTILITY.getBranchY(this.leftLength, this.leftTheta) + y)
      ), 
        new OverlayImage(
        new LineImage(
          new Posn(
           UTILITY.getBranchX(this.rightLength, this.rightTheta),
           UTILITY.getBranchY(this.rightLength, this.rightTheta)),
          GRAY)
        .movePinhole(
           UTILITY.getBranchX(this.rightLength, this.rightTheta) / 2 + x,
           UTILITY.getBranchY(this.rightLength, this.rightTheta) / 2 + y),
        this.right.drawHelp(
           UTILITY.getBranchX(this.rightLength, this.rightTheta) + x,
           UTILITY.getBranchY(this.rightLength, this.rightTheta) + y)
      )
      );
  }


  // return true if this stem or the tree (branchs or stems) are pointing downward rather than upward; false otherwise.
  public boolean isDropping(){
    return Math.sin(Math.toRadians(this.leftTheta)) < 0 || Math.sin(Math.toRadians(this.rightTheta)) < 0 || this.left.isDropping() || this.right.isDropping();
  }


  /* Branch version
  * public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree){
  *   return new Branch(leftLength, rightLength, leftTheta, rightTheta, this, otherTree);
  * }
  */

  //  takes the current tree and a given tree and produces a Branch using the given arguments,
  //  with this tree on the left and the given tree on the right... but with a twist, literally.
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree){
    return new MergedTree(
      new Stem(leftLength, leftTheta, this.combineHelp(leftLength, leftTheta)),
      new Stem(rightLength, rightTheta, otherTree.combineHelp(rightLength, rightTheta))
    );
  }
  // ASSUME: The old stem has an angle of 90 degrees 
  public ITree combineHelp(int newLength, double newTheta){
    return new Branch(this.leftLength, this.rightLength, newTheta + (this.leftTheta - 90), newTheta + (this.rightTheta - 90), this.left.combineHelp(newLength, newTheta), this.right.combineHelp(newLength, newTheta));
  }


  // Since the first call is on branch, we can easy sum up the rest of the tree with the extreme found on both branches.
  public double getWidth(){
    return 
      this.left.getWidthHelp(Math.abs(UTILITY.getBranchX(this.leftLength, this.leftTheta)), Math.abs(UTILITY.getBranchX(this.leftLength, this.leftTheta)))
    + this.right.getWidthHelp(Math.abs(UTILITY.getBranchX(this.rightLength, this.rightTheta)), Math.abs(UTILITY.getBranchX(this.rightLength, this.rightTheta)));
  }
  public double getWidthHelp(int x, int max){
    return 
      this.left.getWidthHelp(Math.abs(UTILITY.getBranchX(this.leftLength, this.leftTheta)) + x, Math.abs(UTILITY.getBranchX(this.leftLength, this.leftTheta)) + x > max ?  Math.abs(UTILITY.getBranchX(this.leftLength, this.leftTheta)) + x : max) 
    + this.right.getWidthHelp(Math.abs(UTILITY.getBranchX(this.rightLength, this.rightTheta)) + x, Math.abs(UTILITY.getBranchX(this.rightLength, this.rightTheta)) + x > max ?  Math.abs(UTILITY.getBranchX(this.rightLength, this.rightTheta)) + x : max);
  }

  public double getMostRight(int x, int max){
    return 
      Math.max(
      this.left.getWidthHelp(Math.abs(UTILITY.getBranchX(this.leftLength, this.leftTheta)) + x, Math.abs(UTILITY.getBranchX(this.leftLength, this.leftTheta)) + x > max ?  Math.abs(UTILITY.getBranchX(this.leftLength, this.leftTheta)) + x : max),
      this.right.getWidthHelp(Math.abs(UTILITY.getBranchX(this.rightLength, this.rightTheta)) + x, Math.abs(UTILITY.getBranchX(this.rightLength, this.rightTheta)) + x > max ?  Math.abs(UTILITY.getBranchX(this.rightLength, this.rightTheta)) + x : max)
    );
  }

  public double getMostLeft(int x, int max){
    return 
      Math.max(
      this.left.getWidthHelp(Math.abs(UTILITY.getBranchX(this.leftLength, this.leftTheta)) + x, Math.abs(UTILITY.getBranchX(this.leftLength, this.leftTheta)) + x > max ?  Math.abs(UTILITY.getBranchX(this.leftLength, this.leftTheta)) + x : max),
      this.right.getWidthHelp(Math.abs(UTILITY.getBranchX(this.rightLength, this.rightTheta)) + x, Math.abs(UTILITY.getBranchX(this.rightLength, this.rightTheta)) + x > max ?  Math.abs(UTILITY.getBranchX(this.rightLength, this.rightTheta)) + x : max)
    );
  }
}


class MergedTree implements ITree{
  ITree leftStem;
  ITree rightStem;

  /* TEMPLATE
  FIELDS
  ... this.leftStem ...           -- ITree
  ... this.rightStem ...          -- ITree

  METHODS
  ... this.draw() ...                                    -- WorldIMage
  ... this.drawHelp(int, int) ...                        -- WorldIMage
  ... this.isDropping() ...                              -- boolean
  ... this.combine(int, int, double, double, ITree)      -- ITree 
  ... this.combineHelp(int, double)                      -- ITree

  METHODS FOR FIELDS:
  ... this.leftStem.draw()  ...                               -- WorldIMage
  ... this.rightStem.draw() ...                               -- WorldIMage
  ... this.leftStem.drawHelp(int, int)  ...                   -- WorldIMage
  ... this.rightStem.drawHelp(int, int) ...                   -- WorldIMage
  ... this.leftStem.isDropping() ...                          -- boolean
  ... this.rightStem.isDropping() ...                         -- boolean
  ... this.leftStem.combine(int, int, double, double, ITree)  -- ITree 
  ... this.rightStem.combineHelp(int, double)                 -- ITree
  */

  MergedTree(ITree leftStem, ITree rightStem){
    this.leftStem = leftStem;
    this.rightStem = rightStem;
  }

  // renders your ITree to a picture. 
  public WorldImage draw(){
    return this.drawHelp(0,0);
  }
  public WorldImage drawHelp(int x, int y){
    return new OverlayImage( 
      this.leftStem.drawHelp(x,y),
      this.rightStem.drawHelp(x,y));
  }

  // that computes whether any of the twigs in the tree (either stems or branches) 
  // are pointing downward rather than upward.
  public boolean isDropping(){
    return this.leftStem.isDropping() || this.rightStem.isDropping();
  };

  //  takes the current tree and a given tree and produces a Branch using the given arguments,
  //  with this tree on the left and the given tree on the right... but with a twist, literally.
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree){
    return new MergedTree(
      new Stem(leftLength, leftTheta, this.combineHelp(leftLength, leftTheta)),
      new Stem(rightLength, rightTheta, otherTree.combineHelp(rightLength, rightTheta))
    );
  }
  public ITree combineHelp(int newLength, double newTheta){
    return this;
  }

  // Since we are on stem, we cannot sum anymore the branchs, but the two stems, that's why we call getMostLeft and getMostRight. 
  public double getWidth(){
    return this.getWidthHelp(0,0);
  }
  public double getWidthHelp(int x, int max){
    return this.leftStem.getMostLeft(x, max) + this.rightStem.getMostRight(x, max);
  }
  public double getMostRight(int x, int max){
    return Math.max(this.leftStem.getMostLeft(x, max), this.rightStem.getMostRight(x, max));
  }
  public double getMostLeft(int x, int max){
    return Math.max(this.leftStem.getMostLeft(x, max), this.rightStem.getMostRight(x, max));
  }
}

class Utility0{
  Utility0(){}

  // getTwigsX
  public int getBranchX(int length, double theta){
    return (int) (length * Math.cos(Math.toRadians(theta)));
  }
  // getTwigsY
  public int getBranchY(int length, double theta){
    return (int) (length * Math.sin(Math.toRadians(theta)));
  }
}


class ExamplesITree{
  ExamplesITree(){}

  ITree leaf0 = new Leaf(10, Color.RED);
  ITree leaf1 = new Leaf(15, Color.BLUE);
  ITree leaf2 = new Leaf(15, Color.GREEN);
  ITree leaf3 = new Leaf(8, Color.ORANGE);

  //  Branch with a left angle of 135 degrees and a right angle of 45 degrees points on both upward diagonals.
  ITree tree1 = new Branch(30, 30, 135, 40, this.leaf0, this.leaf1);
  ITree tree2 = new Branch(30, 30, 115, 65, this.leaf2, this.leaf3);
  // For isDropping
  ITree tree3 = new Branch(30, 30, 240, 65, this.leaf2, this.leaf3);
  ITree tree4 = new Branch(30, 30, 200, 65, this.leaf2, this.leaf3);
  // For combine
  ITree tree5 = new Branch(30, 30, 150 + (135 - 90), 150 + (40 - 90), this.leaf0, this.leaf1);
  ITree tree6 = new Branch(30, 30, 30 + (115 - 90), 30 + (65 - 90), this.leaf2, this.leaf3);

  ITree tree7 = new Branch(30, 30, 150 + (115 - 90), 150 + (65 - 90), this.leaf2, this.leaf3);
  ITree tree8 = new Branch(30, 30,  30 + (135 - 90), 30  + (40 - 90), this.leaf0, this.leaf1);

  // A Stem at an angle of 90 degrees is growing straight up;
  ITree stem1 = new Stem(40, 90, this.tree1);
  ITree stem2 = new Stem(50, 90, this.tree2);
  // For isDropping
  ITree stem3 = new Stem(50, 210, this.tree2);
  ITree stem4 = new Stem(50, 230, this.tree2);

  ITree mt0 = new MergedTree(
    new Stem(40, 150, this.tree5),
    new Stem(50,  30, this.tree6));

  ITree mt1 = new MergedTree(
    new Stem(40, 150, this.tree7),
    new Stem(50,  30, this.tree8));

  // Utility constants 
  OutlineMode OUTLINE = OutlineMode.SOLID;
  Color GRAY = Color.GRAY;
  Color RED = Color.RED;


  boolean testDraw(Tester t){
    return
    t.checkExpect(this.leaf0.draw(), new CircleImage(10, OUTLINE, RED));
  }

  boolean testDrawTree(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return 
    // c.drawScene(s.placeImageXY(this.stem1.draw(), 250, 250)) 
    // c.drawScene(s.placeImageXY(this.tree1.combine(40, 50, 150, 30, this.tree2).draw(), 250, 250)) 
    // c.drawScene(s.placeImageXY(this.tree1.combine(40, 50, 150, 30, this.tree2).draw(), 250, 250)) 
    c.drawScene(s.placeImageXY(this.mt0.draw(), 250, 250)) 
    && c.show();
  } 

  boolean testCombine(Tester t){
    return
    t.checkExpect(this.tree1.combine(40, 50, 150, 30, this.tree2), this.mt0) && 
    t.checkExpect(this.tree2.combine(40, 50, 150, 30, this.tree1), this.mt1);
  }

  // test the method is dropping for ITree
  boolean testIsDropping(Tester t) {
    return 
    t.checkExpect(this.tree1.isDropping(), false) &&
    t.checkExpect(this.tree2.isDropping(), false) &&
    t.checkExpect(this.stem1.isDropping(), false) &&
    t.checkExpect(this.stem2.isDropping(), false) &&
    t.checkExpect(this.leaf0.isDropping(), false) &&
    t.checkExpect(this.leaf1.isDropping(), false) &&
    t.checkExpect(this.tree3.isDropping(),  true) &&
    t.checkExpect(this.tree4.isDropping(),  true) &&
    t.checkExpect(this.stem3.isDropping(),  true) &&
    t.checkExpect(this.stem4.isDropping(),  true);
  } 

  // test the method getWidth for ITree
  boolean testGetWidth(Tester t){
    return
    t.checkExpect(this.tree1.getWidth(), 68.0) &&
    t.checkExpect(this.tree2.getWidth(), 47.0) &&
    t.checkExpect(this.stem1.getWidth(), 68.0) &&
    t.checkExpect(this.stem2.getWidth(), 47.0) &&
    t.checkExpect(this.mt0.getWidth(),  152.0) 
    ;
  }
}

