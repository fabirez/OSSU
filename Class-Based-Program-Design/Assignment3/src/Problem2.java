import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Color.RED, Color.GRAY, etc.)
import javalib.worldcanvas.*;   //  display images on a WorldCanvas 
 
interface ITree {
  OutlineMode OUTLINE = OutlineMode.SOLID;
  Color GRAY = Color.GRAY;
 /* see methods below */ 
  WorldImage draw();
  // !!!
  // WorldImage drawHelp(int x, int y);
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
  ... this.draw() ...  -- WorldIMage

  METHODS FOR FIELDS:
 
  */

  public WorldImage draw(){
    return new VisiblePinholeImage(new CircleImage(this.size, OUTLINE, this.color));
  }
  // !!!
  // WorldImage drawHelp(int x, int y);
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
  ... this.tree ...      -- ITree

  METHODS
  ... this.draw() ...    -- WorldIMage

  METHODS FOR FIELDS:
  ... this.tree.draw() ...  -- WorldIMage
  */

  Stem(int length, double theta, ITree tree){
    this.length=length;
    this.theta=theta;
    this.tree=tree;
  }

  public WorldImage draw(){
    return new LineImage(new Posn(0, this.length), GRAY);
  }
  // !!!
  // WorldImage drawHelp(int x, int y);
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
  ... this.draw() ...    -- WorldIMage

  METHODS FOR FIELDS:
  ... this.tree.draw()  ...  -- WorldIMage
  ... this.left.draw()  ...  -- WorldIMage
  ... this.right.draw() ...  -- WorldIMage
  */

  Branch(int  leftLength, int rightLength, double leftTheta, double rightTheta, ITree left, ITree right){
    this.leftLength=leftLength;
    this.rightLength=rightLength;
    this.leftTheta=leftTheta;
    this.rightTheta=rightTheta;
    this.left=left; 
    this.right=right;
  }

  public WorldImage draw(){
    return new VisiblePinholeImage(
      new OverlayImage( 
        new OverlayImage( 
          new LineImage(new Posn((int) (this.leftLength * Math.cos(this.leftTheta)), this.leftLength), GRAY).movePinhole(15, 15),
          this.left.draw()),
        new OverlayImage(
          new LineImage( 
            new Posn((int) (this.rightLength * Math.sin(this.rightTheta)), this.rightLength), GRAY), this.right.draw())
      ));
  }


  // !!!
  // WorldImage drawHelp(int x, int y);

  // WARNING: Experimneting out the library
    // public WorldImage draw(){
    //   return new VisiblePinholeImage(
    //     new BesideImage( 
    //       new OverlayImage( 
    //         new LineImage( 
    //           new Posn((int) (this.leftLength * Math.cos(this.leftTheta)), this.leftLength), GRAY), this.left.draw()),
    //       new OverlayImage(
    //         new LineImage( 
    //           new Posn((int) (this.rightLength * Math.sin(this.rightTheta)), this.rightLength), GRAY), this.right.draw())
    //     ));
    // }
    // Without the rest of the tree
    // public WorldImage draw(){
    //   return new VisiblePinholeImage(
    //     new BesideImage( 
    //       new VisiblePinholeImage(new LineImage(new Posn((int) (this.leftLength * Math.cos(this.leftTheta)), this.leftLength), GRAY)),
    //       new VisiblePinholeImage(new LineImage(new Posn((int) (this.rightLength * Math.sin(this.rightTheta)), this.rightLength), GRAY))
    //     ));
    //  }
    //

}


class ExamplesITree{
  ExamplesITree(){}

  ITree leaf0 = new Leaf(10, Color.RED);
  ITree leaf1 = new Leaf(15, Color.BLUE);
  ITree tree1 = new Branch(30, 30, 135, 40, this.leaf0, this.leaf1);

  ITree leaf2 = new Leaf(15, Color.GREEN);
  ITree leaf3 = new Leaf(8, Color.ORANGE);
  ITree tree2 = new Branch(30, 30, 115, 65,this.leaf2, this.leaf3);

  ITree stem1 = new Stem(40, 90, this.tree1);
  ITree stem2 = new Stem(50, 90, this.tree2);

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
    return c.drawScene(s.placeImageXY(this.tree1.draw(), 250, 250))
        && c.show();
  } 
}
