/* FILE NAME: GohomeWorld.java
 * AUTHOR: Sohie
 * WHEN: Fall 2009
 * WHAT: For the purpose of 
 * Task on Debugging 
 * This version contains bugs!
 *
 * MODIFICATION HISTORY: 
 * Based on WallHuggerWorld from lecture
*/

import java.awt.*;
public class GohomeWorld extends BuggleWorld {
  public void run () {
    GoHomeBuggle bubba = new GoHomeBuggle();
    bubba.setPosition(new Location(7, 6));
    bubba.tick128();     
  }  

  // The following main() method is needed to run the applet as an application
  public static void main (String[] args) {
    runAsApplication(new GohomeWorld(), "GohomeWorld"); 
  }

 
//**************************************************************
class GoHomeBuggle extends TickBuggle {
  
     public void followWall() {
    if (isFacingWall()) {
      left();
    } else {
      forward();
    }
     }
          
     public void tick() {
          if (((getPosition().x==1) && (getPosition().y==1))){
              
              
          }else{ followWall();}
     }
}

     }
