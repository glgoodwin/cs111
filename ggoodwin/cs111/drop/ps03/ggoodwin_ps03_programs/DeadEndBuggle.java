/*Gabrielle Goodwin
 * 2/11/2010
 * CS 111 PS 3
 * Task 2
 */ 


public class DeadEndBuggle extends TickBuggle
{
    Location start = new Location(1, 1);
    
    public void tick()
    {if ((((getPosition().x==1) && (getPosition().y==1) && (getHeading().equals(Direction.SOUTH)))||(getPosition().x==1) && (getPosition().y==1)&&(isOverBagel()))){
      // above text allows buggle to stop back at starting position
      ;}else
      if(!(isFacingWall())){
      right();
      if (isFacingWall()){
        left();
        forward();//keeps buggle in Straight line 
      }
      else{//when buggle finds a opening in the wall
        left();
        right();
        forward();
      }}
    else{//when the buggle is facing a wall, looks for corners
        right();
        if(isFacingWall()){
        left();
        left(); //looks for wall on left hand side
        if(isFacingWall()&&(!(isOverBagel()))){
        dropBagel();// drops bagle if in a dead end without a bagel
        left(); 
        }}else{ 
          forward();}}}} // when the opening is on the left hand side
      
        
  
        
    
 
   
      
    
            


