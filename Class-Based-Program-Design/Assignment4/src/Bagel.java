import tester.*;

interface IBagelRecipe{
  // CONSTANTS
  double WATER=3.0;
  double SALT=0.075;
  double MALT=0.075;

  double WATER_VOL=106.25;
  double MALT_VOL=1.1458333;

  double WEIGHT_YEAST=5.0;
  double WEIGHT_SALT=10.0;
  double WEIGHT_MALT=11.0;
  double WEIGHT_WATER=8.0;
  double WEIGHT_FLOUR=4.25;

  Utility U = new Utility();

  // METHODS
  boolean sameRecipe(BagelRecipe that);
}

class BagelRecipe implements IBagelRecipe{
  double flour, water, yeast, salt, malt;

  BagelRecipe(double flour, double water, double yeast, double salt, double malt){
      if(U.checkConstraints(flour, water, yeast, salt, malt)){
        this.flour=flour;
        this.water=water;
        this.yeast=yeast;
        this.salt=salt; 
        this.malt=malt;
      }
  }

  BagelRecipe(double flour, double yeast){
    // Invokes the first constructor
    this(flour, WATER, yeast, SALT, MALT);
  }

  BagelRecipe(double flour, double yeast, double salt){
    // Invokes the first constructor
    this(
      U.convert(flour, WEIGHT_FLOUR, false),
      WATER_VOL,
      U.convert(yeast, WEIGHT_YEAST, true),
      U.convert(salt, WEIGHT_SALT, true),
      MALT_VOL);
  }

  public boolean sameRecipe(BagelRecipe that){
    return 
    U.checkWithin(this.flour,that.flour) 
    &&
    U.checkWithin(this.water,that.water) 
    &&
    U.checkWithin(this.yeast,that.yeast) 
    &&
    U.checkWithin(this.salt,that.salt) 
    &&
    U.checkWithin(this.malt,that.malt);
  }
}

class Utility{
  int CUP=48;
  double WITHIN = 0.001;

  boolean checkWithin(double i1, double i2){
    return Math.abs(i1 - i2) < WITHIN;
  }

  // produce the converted volume to weight 
  double convert(double vol, double weight, boolean teaspoon){
    if(teaspoon == true){
      return vol / CUP * weight;
    }else{
      return vol * weight;
    }
  }

  // Your main constructor should take in all of the fields and enforce all above constraints to ensure a perfect bagel recipe.
  boolean checkConstraints(double flour, double water, double yeast, double salt, double malt){
    if(Math.abs(flour - water) > WITHIN){
      throw new IllegalArgumentException("the weight of the flour should be equal to the weight of the water");
    }else if(Math.abs(yeast - malt) > WITHIN){
      throw new IllegalArgumentException("the weight of the yeast should be equal the weight of the malt");
    }else if(Math.abs(salt + yeast - flour / 20) > WITHIN){
      throw new IllegalArgumentException("the weight of the salt + yeast should be 1/20th the weight of the flour");
    }else if(flour <= 0 || water <= 0 || yeast <= 0 || salt <= 0 || malt <= 0){
      throw new IllegalArgumentException("the ingrediend cannot be less or equal 0");
    }else{
      return true;
    }
  }
}


// Implement the method sameRecipe(BagelRecipe other) which returns true if the same ingredients have the same weights to within 0.001 ounces.
class ExamplesBagel{
  ExamplesBagel(){}

  BagelRecipe b1 = new BagelRecipe(1.0, 1.0, 0.025, 0.025, 0.025);
  BagelRecipe b2 = new BagelRecipe(3.0, 0.075);
  BagelRecipe b3 = new BagelRecipe(25.0, 11.0, 20.0);

  boolean testSameRecipe(Tester t){
    return
    t.checkExpect(b1.sameRecipe(b1), true) &&
    t.checkExpect(b2.sameRecipe(b2), true) &&
    t.checkExpect(b1.sameRecipe(b2), false) &&
    t.checkExpect(b2.sameRecipe(b3), false) &&
    t.checkExpect(b3.sameRecipe(b3), true);
  }
}
