/* Gabrielle Goodwin
 * CS 111 PS 3
 * Task 1 Follower
 * 2/10/2010
 * */

public class Follower extends TickBuggle 
{
public void tick()
{ if (!(isOverBagel())){// stops the buggle at the end of the trail
  ;}else{
  pickUpBagel();

  if (isFacingBagelTrail()) {
    forward();// checks in front for bagel
    
  }else{
    left();
    if (isFacingBagelTrail()){ 
    forward();//checks to the left for a bagel
    
    }else{
      right();
      right();
      if (isFacingBagelTrail()){
        forward();//checks to the right for a bagel
        
      }else{ 
        left();//returns buggle to original heading
  }


    
    }}}}public boolean isFacingBagelTrail(){ //buggle takes a step forward and looks under it for bagel
      
  if (isFacingWall()) {
    return false;
  }else{
    brushUp();
    forward();
    boolean result = isOverBagel();
    backward();
    brushDown();
    return result;
  }}
}
 


   