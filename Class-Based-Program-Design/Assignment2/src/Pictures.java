import tester.*;
/*
### Problem 2: A Picture is worth a thousand words

Define the file Pictures.java that will contain the entire solution to this problem.

For this problem, you’re going to implement a small fragment of the image library you’ve been using for Fundies 1 and will be using in Fundies 2.

Each picture is either a single `Shape` or a `Combo` that connects one or more pictures.

Each `Shape` has a kind, which is a string describing what simple shape it is (e.g., "circle" or "square"), and a size.
(For this problem, we will simplify and assume that each simple shape is as tall as it is wide.)

A Combo consists of a name describing the resulting picture, and an operation describing how this image was put together.

There are three kinds of operations: Scale (takes a single picture and draws it twice as large),
Beside (takes two pictures, and draws picture1 to the left of picture2),
and Overlay (takes two pictures, and draws top-picture on top of bottom-picture, with their centers aligned).

- [x] Design the classes (and interfaces) needed to represent the given information. 
- [x] Sketch the class diagram for the classes and interfaces you have designed. !!! DO THIS AFTER!
    (You can draw this on paper, or in ASCII art as a comment in your submitted file. You do not need to hand this in.)

- [x] In the ExamplesPicture class define example data that represents the following images (the colors don’t matter; they’re just for illustration here):
  - [x] A circle - ![image](pict_55.png), a single circle of size 20
  - [x] A square - ![image](pict_56.png), a single square of size 30
  - [x] A big circle - ![image](pict_57.png), the result of scaling circle
  - [x] A square on circle - ![image](pict_58.png)
  - [x] A doubled square on circle - ![image](pict_59.png)
  You should define each picture by its name (e.g. square or bigCircle).
  Any combo image should use the description text given above as its description.
  Our test program will use this data to test your methods.
- [x] Add to your examples one more example of picture for each of the possible operations. Do not modify the existing pictures.
- [x] Design the method getWidth that computes the overall width of this picture.
  Hint: follow the design recipe... working through examples really helps.
- [x] Design the method countShapes that computes the number of single shapes involved in producing the final image.
  Note: Make sure you count every shape each time it is used.
- [x] Design the method comboDepth, that computes how deeply operations are nested in the construction of this picture. For example, the comboDepth of the last example picture above is 3.
- [x] Design the method mirror. This should leave the entire image unchanged, except Beside combos, which should have their two sub-images flipped (all names can remain untouched). This mirroring should happen the entire way down the image.
- [x] Tricky! Design the method pictureRecipe that takes an integer depth, and produces a String representing the contents of this picture, where the recipe for the picture has been expanded only depth times. For example, the pictureRecipe at depth 0 for the last example image above is "doubled square on circle", at depth 2 is "beside(overlay(square, big circle), overlay(square, big circle))", and at depth 3 or more is "beside(overlay(square, scale(circle)), overlay(square, scale(circle)))".
  In more detail: invoking pictureRecipe on a Combo produces its name if the given depth is less than or equal to 0, and the formula of its mixture (at that depth) otherwise.
  Hint: If you get stuck, you may want to use a wish list to determine subproblems that may be of use to you, and that you can delegate to when needed.
*/

/*
                    +-----------------------------------------------+
                    | IPicture                                      |
                    +-----------------------------------------------+                        
                    | int getWidth()                                |                        
                    | int countShapes()                             |                        
                    | int comboDepth()                              |                        
                    | IPicture mirror()                             |                        
                    | String pictureRecipe(int depth)               |                        
                    +-----------------------------------------------+                        
                                          |                                                  
                                         / \                                                 
                                         ---                                                 
                                          |                                                  
                          --------------------------------                                   
                          |                              |                                   
   +-----------------------+               +----------------------------------+              
   | Shape                 |               | Combo                            |              
   +-----------------------+               +----------------------------------+              
   | String kind           |             +-| IOperation operation             |              
   | int size              |             | | String name                      |              
   +-----------------------+             | +----------------------------------+              
   | int getWidth()        |             | | int getWidth()                   |              
   | int countShapes()     |             | | int countShapes()                |              
   | int comboDepth()      |             | | int comboDepth()                 |              
   | IPicture mirror()     |             | | IPicture mirror()                |              
   | String pictureRecipe()|             | | String pictureRecipe(int depth)  |              
   +-----------------------+             | +----------------------------------+              
                                         |                                                   
                                         v                                                   
                    +-----------------------------------------------+                        
                    | IOperation                                    |
                    +-----------------------------------------------+                        
                    | int getPictureWidth()                         |                        
                    | int countPictureShapes()                      |                        
                    | int comboPictureDepth()                       |                        
                    | IOperation pictureMirror()                    |                        
                    | String pictureRecipe(int depth)               |                        
                    +-----------------------------------------------+                        
                                          |                                                  
                                         / \                                                 
                                         ---                                                 
                                          |                                                  
                    ---------------------------------------------------------------------                                 
                    |                                       |                           |      
   +----------------+-----------------+    +----------------+---------------+   +-------+-----------------------+                     
   | Scale                            |    | Beside                         |   | Overlay                       |                     
   +----------------------------------+    +--------------------------------+   +-------------------------------+                     
   | String description               |    | String description             |   | String description            |        
 +-| IPicture picture1                |  +-| IPicture picture1              |   | IPicture top                  |----+    
 | +----------------------------------+  +-| IPicture picture2              |   | IPicture bottom               |----+        
 | | int    getPictureWidth()         |  | |+-------------------------------+  +--------------------------------+    |   
 | | int        countShapes()         |  | |int           getPictureWidth() |  |int           getPictureWidth() |    |    
 | | int  comboPictureDepth()         |  | |int               countShapes() |  |int               countShapes() |    |       
 | | IPicture pictureMirror()         |  | |int         comboPictureDepth() |  |int         comboPictureDepth() |    |      
 | | String   pictureRecipe(int depth)|  | |IPicture        pictureMirror() |  |IPicture        pictureMirror() |    |       
 | +---------------------------------+|  | |String pictureRecipe(int depth) |  |String pictureRecipe(int depth) |    |       
 |                                       | +--------------------------------+  +--------------------------------+    |     
 |                                       |                                                                           |
 |                                       |                                                                           |              
 |                                       |                                                                           |               
 |                  +--------------------+--------------------------+                                                |
 +------------------| IPicture                                      |------------------------------------------------+
                    +-----------------------------------------------+                        
                        

*/

interface IPicture{
  // return the width of the picture;
  int getWidth();

  // return the number of single shapes involved in producing the final image;
  int countShapes();

  //  return how many deeply operations are nested in the construction of this picture;
  int comboDepth();

  // if the picture is Beside combos, return their two sub-images flipped (all names can remain untouched) otherwise just the pciture unchanged.
  IPicture mirror();

  // consume an integer depth, produces a String representing the contents of this picture, where the recipe for the picture has been expanded only depth times.
  String pictureRecipe(int depth);
} 
interface IOperation{
  int getPictureWidth();

  // return the number of single shapes involved in producing the final image;
  int countPictureShapes();

  //  return how many deeply operations are nested in the construction of this picture;
  int comboPictureDepth();

  // if the picture is Beside combos, return their two sub-images flipped (all names can remain untouched) otherwise just the pciture unchanged.
  IOperation pictureMirror();

  // consume an integer depth, produces a String representing the contents of this picture, where the recipe for the picture has been expanded only depth times.
  String pictureRecipe(int depth);
}


class Shape implements IPicture{
  String kind; 
  int    size;

  Shape(String kind, int size){
    this.kind=kind;
    this.size=size;
  }
    /*Field
     * this.kind -- String
     * this.size -- int
    */

  public int getWidth(){
    return this.size;
  }

  public int countShapes(){
    /*Field
     * this.kind -- String
     * this.size -- int
     * Methods:
     * this.getWidth() -- int
    */
    return 1;
  }
  public int comboDepth(){
    /*Field
     * this.kind -- String
     * this.size -- int
     * Methods:
     * this.getWidth()    -- int
     * this.countShapes() -- int
    */
    return 0;
  }

  public IPicture mirror(){
    /*Field
     * this.kind -- String
     * this.size -- int
     * Methods:
     * this.getWidth()    -- int
     * this.countShapes() -- int
     * this.comboDepth()  -- int
    */
    return this;
  }
  public String pictureRecipe(int depth){
      return this.kind;
  }
}

class Combo implements IPicture{
  String name; 
  IOperation operation; // ??? Operation or ListOf operations ?

  Combo(String name, IOperation operation){
    this.name=name;
    this.operation=operation;
  }
  public int getWidth(){
    /*Field
     * this.name       -- String
     * this.operation  -- IOperation
    */
    return this.operation.getPictureWidth();
  }

  public int countShapes(){
    /*Field
     * this.name       -- String
     * this.operation  -- IOperation
     * Methods:
     * this.getWidth()    -- int
     * Methods of fields:
     * this.operation.getPictureWidth() -- int
    */
    return this.operation.countPictureShapes();
  }

  public int comboDepth(){
    /*Field
     * this.name       -- String
     * this.operation  -- IOperation
     * Methods:
     * this.getWidth()    -- int
     * this.countShapes() -- int
     * Methods of fields:
     * this.operation.getPictureWidth() -- int
     * this.operation.countPictureShapes() -- int
    */
    return this.operation.comboPictureDepth();
  }

  public IPicture mirror(){
    /*Field
     * this.name       -- String
     * this.operation  -- IOperation
     * Methods:
     * this.getWidth()    -- int
     * this.countShapes() -- int
     * this.comboDepth()  -- int
     * Methods of fields:
     * this.operation.getPictureWidth()    -- int
     * this.operation.countPictureShapes() -- int
    */
    return new Combo(this.name, this.operation.pictureMirror());
  }

  public String pictureRecipe(int depth){
    /*Field
     * this.name       -- String
     * this.operation  -- IOperation
     * Methods:
     * this.getWidth()    -- int
     * this.countShapes() -- int
     * this.comboDepth()  -- int
     * this.mirror()      -- IPicture
     * Methods of fields:
     * this.operation.getPictureWidth()    -- int
     * this.operation.countPictureShapes() -- int
     * this.operation.pictureMirror()      -- IPicture
    */
    if(depth == 0){
      return this.name;
    }else{
     // beside(Square on Circle, Square on Circle)
      return this.operation.pictureRecipe(depth - 1);
    }
  }

}

class Scale implements IOperation{
  String description;
  IPicture picture1;

  Scale(String description, IPicture picture1){
    this.description=description;
    this.picture1=picture1;
  }


  public int getPictureWidth(){
    /* Field
     * this.name       -- String
     * this.picture1   -- IPicture
     * Methods on Field
     * picture1.getWidth   -- int
    */
    return this.picture1.getWidth() * 2;
  }

  public int countPictureShapes(){
    /*Field
     * this.name       -- String
     * this.picture1   -- IPicture
     * Methods of fields:
     * this.picture1.countShapes() -- int
    */
    return this.picture1.countShapes();
  }

  public int comboPictureDepth(){
    /*Field
     * this.name       -- String
     * this.picture1   -- IPicture
     * Methods
     * this.getPictureWidth()      -- int
     * this.countPictureShapes()   -- int
     * Methods of fields:
     * this.picture1.getWidth()    -- int
     * this.picture1.countShapes() -- int
     * this.picture1.comboDepth()  -- int
    */
    return 1 + this.picture1.comboDepth(); 
  }

  public IOperation pictureMirror(){
    /*Field
     * this.name       -- String
     * this.picture1   -- IPicture
     * Methods
     * this.getPictureWidth()      -- int
     * this.countPictureShapes()   -- int
     * Methods of fields:
     * this.picture1.getWidth()    -- int
     * this.picture1.countShapes() -- int
     * this.picture1.comboDepth()  -- int
    */
    return this;
  }

  public String pictureRecipe(int depth){
    /*Field
     * this.name       -- String
     * this.picture1   -- IPicture
     * Methods
     * this.getPictureWidth()      -- int
     * this.countPictureShapes()   -- int
     * Methods of fields:
     * this.picture1.getWidth()    -- int
     * this.picture1.countShapes() -- int
     * this.picture1.comboDepth()  -- int
    */
    return "scale(" + this.picture1.pictureRecipe(depth) + ")";
  }
}

class Beside implements IOperation{
  String description;
  IPicture picture1;
  IPicture picture2;

  Beside(String description, IPicture picture1, IPicture picture2){
    this.description=description;
    this.picture1=picture1;
    this.picture2=picture2;
  }

  public int getPictureWidth(){
    /* Field
     * this.description    -- String
     * this.picture1       -- IPicture
     * this.picture2       -- IPicture
     * Methods on Field
     * picture1.getWidth   -- int
     * picture2.getWidth   -- int
     * this.picture1.countShapes() -- int
     * this.picture2.countShapes() -- int
    */
    return this.picture1.getWidth() + this.picture2.getWidth(); 
  }

  public int countPictureShapes(){
    /*Field
     * this.description    -- String
     * this.picture1       -- IPicture
     * this.picture2       -- IPicture
     * Methods of fields:
     * this.operation.getPictureWidth() -- int
    */
    return this.picture1.countShapes() + this.picture2.countShapes();
  }

  public int comboPictureDepth(){
    /*Field
     * this.description    -- String
     * this.picture1       -- IPicture
     * this.picture2       -- IPicture
     * Methods
     * this.getPictureWidth()      -- int
     * this.countPictureShapes()   -- int
     * Methods of fields:
     * this.picture1.getWidth()    -- int
     * this.picture2.getWidth()    -- int
     * this.picture1.countShapes() -- int
     * this.picture2.countShapes() -- int
     * this.picture1.comboDepth()  -- int
     * this.picture2.comboDepth()  -- int
    */
    return 1 + this.picture1.comboDepth();
  }

  public IOperation pictureMirror(){
    /*Field
     * this.description    -- String
     * this.picture1       -- IPicture
     * this.picture2       -- IPicture
     * Methods
     * this.getPictureWidth()      -- int
     * this.countPictureShapes()   -- int
     * Methods of fields:
     * this.picture1.getWidth()    -- int
     * this.picture2.getWidth()    -- int
     * this.picture1.countShapes() -- int
     * this.picture2.countShapes() -- int
     * this.picture1.comboDepth()  -- int
     * this.picture2.comboDepth()  -- int
    */
    return new Beside(this.description,this.picture2, this.picture1);
  }

  public String pictureRecipe(int depth){
    /*Field
     * this.description    -- String
     * this.picture1       -- IPicture
     * this.picture2       -- IPicture
     * Methods
     * this.getPictureWidth()      -- int
     * this.countPictureShapes()   -- int
     * Methods of fields:
     * this.picture1.getWidth()    -- int
     * this.picture2.getWidth()    -- int
     * this.picture1.countShapes() -- int
     * this.picture2.countShapes() -- int
     * this.picture1.comboDepth()  -- int
     * this.picture2.comboDepth()  -- int
    */
    return "beside(" + this.picture1.pictureRecipe(depth) + ", " +  this.picture2.pictureRecipe(depth) + ")";
  }

}

class Overlay implements IOperation{
  String description;
  IPicture topPicture;
  IPicture bottomPicture;

  Overlay(String description, IPicture topPicture, IPicture bottomPicture){
    this.description=description;
    this.topPicture=topPicture;
    this.bottomPicture=bottomPicture;
  }
  public int getPictureWidth(){
    /* Field
     * this.description       -- String
     * this.topPicture        -- IPicture
     * this.bottomPicture     -- IPicture
     * Methods on Field
     * this.topPicture.getWidth()    -- int
     * this.bottomPicture.getWidth() -- int
    */
    return Math.max(this.topPicture.getWidth(), this.bottomPicture.getWidth()); 
  }

  public int countPictureShapes(){
    /*Field
     * this.description       -- String
     * this.topPicture        -- IPicture
     * this.bottomPicture     -- IPicture
     * Methods of fields:
     * this.topPicture.countShapes()    -- int
     * this.bottomPicture.countShapes() -- int
    */
    return this.topPicture.countShapes() + this.bottomPicture.countShapes();
  }

  public int comboPictureDepth(){
    /*Field
     * this.description       -- String
     * this.topPicture        -- IPicture
     * this.bottomPicture     -- IPicture
     * Methods
     * this.getPictureWidth()      -- int
     * this.countPictureShapes()   -- int
     * Methods of fields:
     * this.topPicture.getWidth()       -- int
     * this.bottomPicture.getWidth()    -- int
     * this.topPicture.countShapes()    -- int
     * this.bottomPicture.countShapes() -- int
     * this.topPicture.comboDepth()     -- int
     * this.bottomPicture.comboDepth()  -- int
    */
    return 1 + this.topPicture.comboDepth() + this.bottomPicture.comboDepth();
  }

  public IOperation pictureMirror(){
    /*Field
     * this.description       -- String
     * this.topPicture        -- IPicture
     * this.bottomPicture     -- IPicture
     * Methods
     * this.getPictureWidth()      -- int
     * this.countPictureShapes()   -- int
     * Methods of fields:
     * this.topPicture.getWidth()       -- int
     * this.bottomPicture.getWidth()    -- int
     * this.topPicture.countShapes()    -- int
     * this.bottomPicture.countShapes() -- int
     * this.topPicture.comboDepth()     -- int
     * this.bottomPicture.comboDepth()  -- int
    */
    return this;
  }

  public String pictureRecipe(int depth){
    /*Field
     * this.description       -- String
     * this.topPicture        -- IPicture
     * this.bottomPicture     -- IPicture
     * Methods
     * this.getPictureWidth()      -- int
     * this.countPictureShapes()   -- int
     * Methods of fields:
     * this.topPicture.getWidth()       -- int
     * this.bottomPicture.getWidth()    -- int
     * this.topPicture.countShapes()    -- int
     * this.bottomPicture.countShapes() -- int
     * this.topPicture.comboDepth()     -- int
     * this.bottomPicture.comboDepth()  -- int
    */
    return "overlay(" + this.topPicture.pictureRecipe(depth) + ", " + this.bottomPicture.pictureRecipe(depth) + ")";
  }
}

class ExamplesPicture{
  ExamplesPicture(){}

   IPicture circle = new Shape("circle", 20);
   IPicture square = new Shape("square", 30);

   IOperation   scale = new Scale("takes a single picture and draws it twice as large", this.circle);
   IPicture bigCircle = new Combo("Big circle", this.scale);

   IOperation      overlay = new Overlay("takes two pictures, and draws top-picture on top of bottom-picture, with their centers aligned",  this.square, this.bigCircle);
   IPicture squareOnCircle = new Combo("Square on Circle", this.overlay);

   IOperation              beside = new Beside("takes two pictures, and draws picture1 to the left of picture2", this.squareOnCircle, this.squareOnCircle);
   IPicture doubledSquareOnCircle = new Combo("Doubled square on circle", this.beside);


   IOperation   scale0 = new Scale("takes a single picture and draws it twice as large", this.square);
   IPicture  bigSquare = new Combo("Big square", this.scale0);

   IOperation      overlay0 = new Overlay("takes two pictures, and draws top-picture on top of bottom-picture, with their centers aligned", this.circle, this.bigSquare);
   IPicture  circleOnSquare = new Combo("Circle on square", this.overlay0);

   IOperation              beside0 = new Beside("takes two pictures, and draws picture1 to the left of picture2", this.circleOnSquare, this.circleOnSquare);
   IPicture  doubledCircleOnSquare = new Combo("Doubled circle on square", this.beside0);

   IOperation         beside1 = new Beside("takes two pictures, and draws picture1 to the left of picture2", this.circleOnSquare, this.squareOnCircle);
   IOperation     testBeside1 = new Beside("takes two pictures, and draws picture1 to the left of picture2", this.squareOnCircle, this.circleOnSquare);
   IPicture        resBeside1 = new Combo("One circle on square and a square on a circle", this.beside1);
   IPicture    testResBeside1 = new Combo("One circle on square and a square on a circle", this.testBeside1);


    boolean testGetWidth(Tester t){
      return 
      t.checkExpect(this.circle.getWidth(), 20) &&
      t.checkExpect(this.square.getWidth(), 30) &&
      t.checkExpect(this.bigCircle.getWidth(), 40) &&
      t.checkExpect(this.squareOnCircle.getWidth(), 40) &&
      t.checkExpect(this.doubledSquareOnCircle.getWidth(), 80);
    }

    boolean testCountShapes(Tester t){
      return 
      t.checkExpect(this.circle.countShapes(), 1) &&
      t.checkExpect(this.square.countShapes(), 1) &&
      t.checkExpect(this.bigCircle.countShapes(), 1) &&
      t.checkExpect(this.squareOnCircle.countShapes(), 2) &&
      t.checkExpect(this.doubledSquareOnCircle.countShapes(), 4);
    }

    boolean testComboDepth(Tester t){
      return 
      t.checkExpect(this.circle.comboDepth(), 0) &&
      t.checkExpect(this.square.comboDepth(), 0) &&
      t.checkExpect(this.bigCircle.comboDepth(), 1) &&
      t.checkExpect(this.squareOnCircle.comboDepth(), 2) &&
      t.checkExpect(this.doubledSquareOnCircle.comboDepth(), 3);
    }

    boolean testMirror(Tester t){
      return 
      t.checkExpect(this.circle.mirror(),                 this.circle) &&
      t.checkExpect(this.square.mirror(),                 this.square) &&
      t.checkExpect(this.bigCircle.mirror(),           this.bigCircle) &&
      t.checkExpect(this.squareOnCircle.mirror(), this.squareOnCircle) &&
      t.checkExpect(this.resBeside1.mirror(),   this.testResBeside1);
    }

    boolean testPictureRecipe(Tester t){
      return 
      t.checkExpect(this.circle.pictureRecipe(1),         "circle") &&
      t.checkExpect(this.circle.pictureRecipe(5),         "circle") &&
      t.checkExpect(this.square.pictureRecipe(1),         "square") &&
      t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(0), "Doubled square on circle") &&
      t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(1), "beside(Square on Circle, Square on Circle)") &&
      t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(2), "beside(overlay(square, Big circle), overlay(square, Big circle))") &&
      t.checkExpect(this.doubledSquareOnCircle.pictureRecipe(3), "beside(overlay(square, scale(circle)), overlay(square, scale(circle)))");
    }
   
}
