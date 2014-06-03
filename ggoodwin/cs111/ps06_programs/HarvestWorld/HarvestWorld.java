import java.awt.*;

public class HarvestWorld extends BagelBuggleWorld
{

        public static void main (String[] args)
        {
                runAsApplication(new HarvestWorld(), "HarvestWorld"); 
        }

        public void run ()
        {
                Harvester hannah = new Harvester();
                hannah.brushUp();
               params.setIntResult("bagels", hannah.harvestField());
              
            
                
        }
        
}


// Define your Harvester class here.
// Don't forget that the specified methods MUST
// meet the invariant that the buggle's state
// (position, heading, color, and brush state) will
// not be changed by execution of the methods.
// Assume the buggle's brush is initially up when each
// method is called.
class Harvester extends Buggle{
  public int harvestField(){
    left();
    int bagels = harvestBagels();                
    stackBagels(bagels);
    forward(bagels);
    int spaces = pullTarp();
    backward(bagels);
    markRow(bagels,spaces);
    right();
    if(!isFacingWall()){
      forward();
      int totalBagels = bagels + harvestField();
      backward();
      return totalBagels;
    }else{
      return bagels;
    }
        }     
    
  public int harvestBagels(){
    if(!isFacingWall()){
      forward();
      if(isOverBagel()){
        pickUpBagel();
        int bagels= 1 + harvestBagels();
        backward();
        return bagels;
      }else{
        int bagels = harvestBagels();
        backward();
        return bagels;
      }}else{
        return 0;
      }
  }
  public void stackBagels(int numberBagels){
    if(numberBagels==0){
      ;
    }else{
      forward();
      dropBagel();
      stackBagels(numberBagels -1);
      backward();
    }
  }
  public int pullTarp(){
  if(!isFacingWall()){
    forward();
    if(!isOverBagel()){
    int space = 1 + pullTarp();
    setCellColor(Color.black);
    backward();
    return space;
  }else{
    int space = pullTarp();
    return space;
  }}else{
    return 0;
}
}
  public void markRow(int numberBagels, int numberSpaces){
    dropInt(numberSpaces);
    if(numberSpaces > numberBagels){
      setCellColor(Color.red);
    }else if(numberSpaces < numberBagels){
        setCellColor(Color.green);
    }else{
      ; //do nothing
    }
  }
}
