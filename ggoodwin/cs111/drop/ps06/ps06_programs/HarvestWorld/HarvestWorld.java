/* Gabrielle Goodwin
 * PS 6 task 3
 * 3/15/10
 * */
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
    //harvests the whole field of bagels
    //invariant
    left();//turns the buggle so it is facing the column of bagels
    int bagels = harvestBagels();                
    stackBagels(bagels);
    forward(bagels);// sets the buggle up to pull the tarp
    int spaces = pullTarp();
    backward(bagels);//returns buggle to bottom row
    markRow(bagels,spaces);
    right();//turns buggle to start a new row
    if(!isFacingWall()){// repeats pattern for a new row
      forward();
      int totalBagels = bagels + harvestField();
      backward();
      return totalBagels;
    }else{//if is at the last row of bagels
      return bagels;
    }
        }     
    
  public int harvestBagels(){
    //picks up and counts the bagels in a column
    //invariant
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
    //stacks the # of bagels it picked up into a straight line
    //invariant
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
    //paints all the empty cells above the row of bagels black
    //invariant
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
    //This method paints the current cell green if numberBagels is greater than numberSpaces.
    //The current cell is painted red if numberBagels is less than numberSpaces. 
    //The current cell is not painted if the numberBagels is equal to numberSpaces.
    //The buggle also marks the cell with the numberSpaces (using dropInt())
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

