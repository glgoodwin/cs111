import java.awt.*; // 

// You should change the name of this class to better reflect the nature of your project. 
class JavierBuggle extends Animation { 

    public JavierBuggle(){
//part 1
      this.setNumberFrames(260);
      Sprite r1 = new BuggleBoard(Color.red, Color.blue, Color.black,20,320,120,320,220,320);
      this.addSprite(r1);
      Sprite r2 = new RightBuggle(Color.green, 50, 20, 170, 5);
      this.addSprite(r2);
      this.setInactive(r2,12);
      this.setActive(r2,31);
      this.setInactive(r2,61);
      this.setHidden(r1,63);
      this.setHidden(r2,63);
      this.addSprite(new MovingUpText (Color.blue, 330,290, "Buggle World... ")); 
      this.addSprite(new MovingUpText (Color.blue, 330,310, "a peaceful place where Buggles lived in harmony"));
      this.addSprite(new MovingUpText (Color.blue, 330,330,"eating bagels and minding their own business,"));
      this.addSprite(new MovingUpText (Color.blue, 330,350,"until one day..."));//this finishes the first part of the story
      
//part 2
      Sprite r7 = new ColorChangeBackground(Color.red, Color. white);
      this.addSprite(r7);
      this.setHidden(r7,1);
      this.setInactive(r7,1);
      this.setVisible(r7,70);
      this.setActive(r7,70);
      this.setHidden(r7,110);
      this.setInactive(r7,110);
      Sprite r3 = new TextBalloon(Color.black,"I want a waffle", 90, 100,100,50);
      Sprite r4 = new RightBuggle(Color.pink,150,10,150,5);
      this.addSprite(r3);
      this.addSprite(r4);
      this.setHidden(r3,1);
      this.setHidden(r4,1);
      this.setInactive(r4,1);
      this.setInactive(r3,1);
      this.setVisible(r3,70);//makes Text balloon visible
      this.setVisible(r4,63);//makes buggle visible
      this.setActive(r4,63);//makes buggle active
      this.setActive(r3,70);//makes text balloon active
      this.setInactive(r4,69);//makes buggle inactive
      this.setHidden(r4,110);
      this.setHidden(r3,110);
      Sprite r5 = new MovingUpText (Color.blue,330,290, "One buggle asked...");
      Sprite r6 = new MovingUpText (Color.blue,330,330, "for a waffle.");
      this.addSprite(r5);
      this.addSprite(r6);
      this.setHidden(r5,1);
      this.setHidden(r6,1);
      this.setInactive(r5,1);
      this.setInactive(r6,1);
      this.setVisible(r5,63);
      this.setVisible(r6,63);
      this.setActive(r6,63);
      this.setActive(r5,63);
     
//part 3
      Sprite r12 = new MarchingBuggles(Color.red,20,150,50);
      this.addSprite(r12);
      this.setHidden(r12,1);
      this.setInactive(r12,1);
      this.setVisible(r12,111);
      this.setActive(r12,111);
      this.setHidden(r12,184);
      this.setInactive(r12,184);
      
      Sprite r13 = new TextBalloon(Color.black,"Buggles Eat Bagels!", 90,100,150,50);
      this.addSprite(r13);
      this.setHidden(r13,1);
      this.setInactive(r13,1);
      this.setVisible(r13,111);
      this.setActive(r13,111);
      this.setHidden(r13,184);
      this.setInactive(r13,184);
      Sprite r14 = new TextBalloon(Color.black, "Buggles Eat Bagels!", 20,50,150,50);
      this.addSprite(r14);
      this.setHidden(r14,1);
      this.setInactive(r14,1);
      this.setVisible(r14,111);
      this.setActive(r14,111);
      this.setHidden(r14,184);
      this.setInactive(r14,184);
      
      Sprite r8 = new MovingUpText(Color.blue,330,290, "with that one question");
      Sprite r9 = new MovingUpText(Color.blue,330,330," an entire world of conspiracy and");
      Sprite r10 = new MovingUpText(Color.blue,330,350,"brainwashing was revealed. ");
      this.addSprite(r8);
      this.addSprite(r9);
      this.addSprite(r10);
      this.setHidden(r8,1);
      this.setHidden(r9,1);
      this.setHidden(r10,1);
      this.setInactive(r8,1);
      this.setInactive(r9,1);
      this.setInactive(r10,1);
      this.setVisible(r8,111);
      this.setVisible(r9,111);
      this.setVisible(r10,111);
      this.setActive(r8,111);
      this.setActive(r9,111);
      this.setActive(r10,111);
    
      //r11 was another line of text,I deleted it, that is why there is no r11 in my code
      
//Part 4
      Sprite r15 = new MovingUpText(Color.blue,330,290, "Join Javier the Buggle");
      Sprite r16 = new MovingUpText(Color.blue,330,310, "As he embarks on a dangerous quest to introduce");
      Sprite r17 = new MovingUpText(Color.blue,330,330, "BuggleWorld to all new breakfast foods,");
      Sprite r18 = new MovingUpText(Color.blue,330,370, "In the first ever Buggle movie:");
      Sprite r19 = new MovingUpText(Color.green,330,410,"Buggles Don't Have To Eat Bagels");
      Sprite r20 = new MovingUpText(Color.blue,330,450, "Coming soon to theaters in BuggleWorld");
      this.addSprite(r15);
      this.addSprite(r16);
      this.addSprite(r17);
      this.addSprite(r18);
      this.addSprite(r19);
      this.setHidden(r15,1);
      this.setHidden(r16,1);
      this.setHidden(r17,1);
      this.setHidden(r18,1);
      this.setHidden(r19,1);
      this.setHidden(r20,1);
      this.setInactive(r15,1);
      this.setInactive(r16,1);
      this.setInactive(r17,1);
      this.setInactive(r18,1);
      this.setInactive(r19,1);
      this.setInactive(r20,1);
      this.setVisible(r15,185);
      this.setVisible(r16,185);
      this.setVisible(r17,185);
      this.setVisible(r18,185);
      this.setVisible(r19,185);
      this.setVisible(r20,185);  
      this.setActive(r15,185);
      this.setActive(r16,185);
      this.setActive(r17,185);
      this.setActive(r18,185);
      this.setActive(r19,185);
      this.setActive(r20,185);
      
      Sprite r21 = new BuggleGrid(20,20,300,300);
      this.addSprite(r21);
      this.setHidden(r21,1);
      this.setVisible(r21,185);
      
      Sprite r22 = new BouncingBuggle(Color.pink,100,150,100);
      this.addSprite(r22);
      this.setHidden(r22,1);
      this.setInactive(r22,2);
      this.setVisible(r22,185);
      this.setActive(r22,185);


      
      
     
        
                                      
                                                 
                                       
// Add your sprits and animation controls here. 
      // Study other animations and the AnimationWorld contract
      //    for the kind of things that you can do 
    }

}
