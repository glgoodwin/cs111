// *********** Natasha Kellaway's Objects for the object library ************
// Function: Draws a drumkit cymbal, with or without a stand

/*

  The scale of the cymbal can be chosen by the user.The bigger the
  scaleFactor, the bigger the cymbal becomes.

  The color of the cymbal is automatically gold.

  The user can decide whether or not the cymbal has a stand.

  nkellawCymbal takes the parameters in the following order:

  1. if the cymbal has a stand
  2. stand height (only important if the cymbal has a stand)

  The initial view is looking at the cymbal from the bottom. So, if it
  has a stand, the initial view is looking at the bottom of the stand
  with the length of the stand and the cymbal going back along the z-axis.

  If the user specifies that a stand is present, the color of the
  stand is automatically black.

  The color of the stand is automatically black.

  The origin lies at the center at the center of the stand.

*/
 
void nkellawaCymbal(bool hasStand, GLfloat standHeight) {

  glPushMatrix();

  // draw cymbal
  
  glPushMatrix();

  twTriple gold = {255/255.0, 204/255.0, 0};

  twColor(gold,0,0);

  glScalef(1,1,0.1);

  glutSolidTorus(0.8,1,50,50);

  glPopMatrix();

  // draw a stand if user specifies

  if(hasStand){

  glPushMatrix();

  twColorName(TW_BLACK);

  glPushMatrix();

  // draw body of stand

  glTranslatef(0,0,-0.2);

  twCylinder(0.15,0.15,standHeight,50,50);

  glPopMatrix();

  // draw base of stand

  glPushMatrix();

  glTranslatef(0,0,standHeight-0.2);
  glScalef(1,1,0.1);

  glutSolidTorus(0.4,0.5,50,50);

  glPopMatrix();

  glPopMatrix();

  }

  glPopMatrix();

}

/* *********** My Object for the object library ************

 Function: Draws a drum kit including: a stool, a snare drum, a floor tom
 2 wing toms,a bass drum, a ride cymbal, a crash cymal, and a hi-hat

  nkellawaDrumkit takes the parameters in the following order:

  1. color of the drum heads
  2. color of the drum sides
  3. color of the stool seat
  4. radius of the snare drum
  5. height of the snare drum
  6. height of the right (ride) cymbal

  The user gets to choose the color scheme of the drumkit.

  Once the user chooses the snare drum radius and height, the other 4
  drums' (bass, floor tom, left wing tom, right wing tom) dimensions
  are based off of these measurements.

  Likewise, the user can choose the height of the right (ride) cymbal, and
  the other 2 cymbals' (ride cymbal and hi-hat) dimensions are based off
  of that.

  nkellawaDrumkit takes advantage of 2 helper methods -- nkellawaSnareDrum
  and nkellawaCymbal -- each which use a helper method to draw the legs and
  stands respectively. The details for these helper methods are stored in
  nkellawaSnareDrum.cc and nkellawaCymbal.cc, as these two components 
  specify 2 particular objects.


*/

// helper method used to draw legs in nkellawaSnareDrum

static void nkellawaDrumLeg(GLfloat transX,GLfloat transY,GLfloat transZ,
                     GLfloat drumRadius,GLfloat drumHeight){

  glPushMatrix();

  twColorName(TW_BLACK);

  glTranslatef(transX,transY,transZ);

  twCylinder(drumRadius/20,drumRadius/20,drumHeight,50,50);

  glPopMatrix();

}
 
// Function: Draws a snare drum, with or without legs

/*

  The radius of the drum head and the height of the drum can
  be chosen by the user.

  Also, the color of the drum head and the side of the drum
  can be chosen by the user.

  The user can decide whether or not the drum has legs.

  It takes the parameters in the following order:

  1. radius of drum head
  2. height of drum
  3. color of drum heads
  4. color of drum side
  5. if the drum has legs

  The initial view shows only the top drum head, with the body (and legs)
  pointing back along the z-axis.

  The original lies at the center of the bottom drum head.

  nkellawaSnareDrum employs a helper method called nkellawaDrumLeg if the
  user specifies that they want legs on the drum.
  
  nkellawaDrumLeg takes five parameters in the following order:

  1. x-axis translation of the drum leg
  2. y-axis translation of the drum leg
  3. z-axis translation of the drum leg
  4. radius of the drum
  5. height of the drum

   The color of the legs is automatically black.


*/

void nkellawaSnareDrum(GLfloat radius, GLfloat height, 
                       twTriple headColor, twTriple sideColor,
                       bool hasLegs) {

  glPushMatrix();

  // draw bottom head
  
  glPushMatrix();

  twColor(headColor,0,0);
  twDisk(radius,50);

  // draw top head

  glTranslatef(0,0,height);
  twDisk(radius,50);

  glPopMatrix();

  // draw side

  glPushMatrix();

  twColor(sideColor,0,0);
  twCylinder(radius,radius,height,50,50);

  glPopMatrix();

  // draw legs if user specifies

  if(hasLegs){

    glPushMatrix();

    // draw upper-right leg

    nkellawaDrumLeg(radius*0.6,radius*0.6,-height,radius,height);

    //draw upper-left leg

    nkellawaDrumLeg(-radius*0.6,radius*0.6,-height,radius,height);

    //draw lower-right leg

    nkellawaDrumLeg(radius*0.6,-radius*0.6,-height,radius,height);

    //draw lower-left leg

    nkellawaDrumLeg(-radius*0.6,-radius*0.6,-height,radius,height);

    

    glPopMatrix();

    }

  glPopMatrix();

}

// helper method used to draw the stand in nkellawaCymbal

static void nkellawaCymbalStand(GLfloat standHeight){

  glPushMatrix();

  twColorName(TW_BLACK);

  glPushMatrix();

  // draw body of stand

  glTranslatef(0,0,-0.2);

  twCylinder(0.15,0.15,standHeight,50,50);

  glPopMatrix();

  // draw base of stand

  glPushMatrix();

  glTranslatef(0,0,standHeight-0.2);
  glScalef(1,1,0.1);

  glutSolidTorus(0.4,0.5,50,50);

  glPopMatrix();

  glPopMatrix();

}
 
void nkellawaDrumkit(twTriple headDrumColor, twTriple sideDrumColor,
                     twTriple seatColor, GLfloat snareRadius,
                     GLfloat snareHeight,
                     GLfloat rightCymbalHeight){

  glPushMatrix();

  // draw stool

  GLfloat stoolRadius = snareRadius*0.75;
  GLfloat stoolHeight = snareHeight*0.5;

  glTranslatef(0,-snareHeight/2,0);
  glRotatef(90,-1,0,0);

  nkellawaSnareDrum(stoolRadius,stoolHeight,seatColor,seatColor,true);

  glPopMatrix();

  //draw snare drum

  glPushMatrix();            // snare drum coordinate system

  glRotatef(90,-1,0,0);
  glTranslatef(-snareRadius*1.5,snareRadius*1.5,0);
  
  nkellawaSnareDrum(snareRadius,snareHeight,headDrumColor,sideDrumColor,true);

  // draw floor tom-tom

  glPushMatrix();

  glTranslatef((snareRadius*2)+(stoolRadius*2),0,0);
  
  nkellawaSnareDrum(snareRadius*1.2,snareHeight*1.2,headDrumColor,sideDrumColor,true);

  glPopMatrix();

  glPopMatrix();           // end snare drum coordinate system

  // draw bass drum

  glPushMatrix();                // bass drum coordinate system


  GLfloat bassRadius = snareRadius*1.5;
  GLfloat bassHeight = snareRadius*1.5;

  glTranslatef(0,snareHeight,-(stoolRadius+snareRadius)*2.5);

  nkellawaSnareDrum(bassRadius,bassHeight,headDrumColor,sideDrumColor,false);

  //draw right wing-tom

  glPushMatrix();

  GLfloat rtWingTomRadius = snareRadius*0.6; //bigger than left wing tom
  GLfloat wingTomHeight = snareHeight*1.2;

  glTranslatef(bassRadius,bassRadius*0.75,bassHeight/2);
  glRotatef(30,-1,0,0);

  nkellawaSnareDrum(rtWingTomRadius,wingTomHeight,headDrumColor,sideDrumColor,false);

  glPopMatrix();

  //draw left wing-tom

  glPushMatrix();

  GLfloat ltWingTomRadius = snareRadius*0.5;

  glTranslatef(-bassRadius,bassRadius*0.75,bassHeight/2);
  glRotatef(30,-1,0,0);

  nkellawaSnareDrum(ltWingTomRadius,wingTomHeight,headDrumColor,sideDrumColor,false);

  glPopMatrix();

  // draw right cymbal

  glPushMatrix();

  glTranslatef(bassRadius*2,rightCymbalHeight/2,bassHeight/2);
  glRotatef(90,1,0,0);

  nkellawaCymbal(true,rightCymbalHeight);

  glPopMatrix();
  

  //draw left cymbal
  
  glPushMatrix();

  glTranslatef(-bassRadius*2,rightCymbalHeight/2+(rightCymbalHeight*0.2),bassHeight/2);
  glRotatef(90,1,0,0);

  nkellawaCymbal(true,rightCymbalHeight*1.2);

  glPopMatrix();

  glPopMatrix();            // end bass drum coordinate system


  //draw hi-hat

  glPushMatrix();

   GLfloat hiHatHeight = rightCymbalHeight*0.75;

  glTranslatef(-snareRadius*2.5,hiHatHeight/2,0);
  glRotatef(90,1,0,0);


  nkellawaCymbal(true,hiHatHeight);

  glTranslatef(0,0,hiHatHeight*0.05);     // move to lower-hihat-cymbal position
  
  nkellawaCymbal(false,0);      // draw lower cymbal

  glPopMatrix();


  // removed by Scott
  //  glPopMatrix();    //final pop matrix

}
// *********** My Object for the object library ************
// Function: Draws a drumstick

/*

  The length of the drumstick can be chosen by the user.

  The color of the drumstick is automatically light brown.

  The user can decide whether or not the cymbal has a stand.

  The only argument for nkellawaDrumstick is:

  1. the length

  The initial view is looking at the cymbal from the bottom. Of the 
  drumstick. The shaft and tip of the drumstick point back along the
  z-axis. 

  The origin lies in the center of the drumstick shaft.

*/

void nkellawaDrumstick(GLfloat length){

  glPushMatrix();

  twTriple stickColor = {1,204/255.0,102/255.0};

  twColor(stickColor,0,0);

  glPushMatrix();

  // draw shaft of stick
  twCylinder(0.05,0.05,length,50,50);

  //draw bottom of stick
  twDisk(0.05,50);

  glPopMatrix();

  // draw tip of stick
  
  glPushMatrix();

  glScalef(0.5,0.5,1);
  glTranslatef(0,0,-length*0.05);
  glutSolidSphere(0.12,50,50);

  glPopMatrix();

  glPopMatrix();

}
