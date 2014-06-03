'''
HW3: camera angles
Part 2

This program constructs 6 different views of the barn by changing gluPerspective
as well as gluLookAt. It also constructs callbacks for all 6 views such to form
a slide show

By Dana Bullister and Gabe Goodwin

March 4th, 2012

Acknowledgements: Used some code and comments from Fence.py created by Scott D. Anderson

'''

import sys

try:
  from TW import *
except:
  print '''
ERROR: Couldn't import TW.
        '''

## A picket is, essentially, a barn with two horizontal rails.  The rails
## are 2D quads, and this vertex array gives the vertices for the lower rail. 

def drawRail():
    '''Draws one of the rails through a picket.  This is just a flat quad of
length 5 (parallel to x) and height 2 (parallel to y).'''
    rail = (
      (0,0,0),
      (5,0,0),
      (5,2,0),
      (0,2,0)
      )
    glBegin(GL_QUADS);
    if True:
        glVertex3fv(rail[0]);
        glVertex3fv(rail[1]);
        glVertex3fv(rail[2]);
        glVertex3fv(rail[3]);
    glEnd();

def drawPicket():
    '''Draws one picket.  The picket is 5 wide, 10 high, and 2 deep,
with the reference point at the lower left front of the picket. Rails
stick out 0.5 to the left and are flat planes through the middle of
the picket, with a width of 5 and a height of 2, with the bottom edge
at heights 1 and 4.'''
    maroon = (0.5,0,0)
    black  = (0,0,0)
    orange = (1,0.5,0)

    glPushMatrix();
    glScalef(4,10,2);           # must scale to create 4*10*2 barn
    twSolidBarn(maroon, black, orange);
    glPopMatrix();
    glPushMatrix();
    twColorName(TW_OLIVE);
    glTranslatef(-0.5,1,-1);
    drawRail();
    glTranslatef(0,3,0);
    drawRail();
    glPopMatrix();

ViewMode = '1'


#---------------------------------------------------------------------------------
# Original input values for gluPerspective and gluLookAt
#---------------------------------------------------------------------------------
FOVY = 80
NEAR = 5
FAR = 300

EyeX = 0
EyeY = 60 
EyeZ = 70

AtX = 0
AtY = 10 
AtZ = -10 

Up = (0,1,0)

#---------------------------------------------------------------------------------
# Values of how much original gluPerspective and gluLookAt values change between each frame
#---------------------------------------------------------------------------------

cFOVY = 0 
cNEAR = 0
cFAR = 0

cEyeX = 1 
cEyeY = -8 
cEyeZ = -29 

cAtX = 0
cAtY = 2 
cAtZ = -30 

def setCamera():
    '''Configures the camera for one of several fixed setups, depending on a global variable named 'ViewMode.' '''
    # Note that Python doesn't have switch statements, so we have this:
    if ViewMode == '1': 
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FOVY,1,NEAR,FAR);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(EyeX,EyeY,EyeZ,   
                  AtX,AtY,AtZ,
                  *Up)
        
    if ViewMode == '2':
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FOVY + cFOVY,1,NEAR + cNEAR,FAR + cFAR);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(EyeX + cEyeX,EyeY + cEyeY,EyeZ + cEyeZ,   
                  AtX + cAtX,AtY + cAtY,AtZ + cAtZ,
                  *Up)
        
    if ViewMode == '3':
        n = 2
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FOVY + n*cFOVY,1,NEAR + n*cNEAR,FAR + n*cFAR);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(EyeX + n*cEyeX,EyeY + n*cEyeY,EyeZ + n*cEyeZ,   
                  AtX + n*cAtX,AtY + n*cAtY,AtZ + n*cAtZ,
                  *Up)
        
    if ViewMode == '4':
        n = 3
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FOVY + n*cFOVY,1,NEAR + n*cNEAR,FAR + n*cFAR);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(EyeX + n*cEyeX,EyeY + n*cEyeY,EyeZ + n*cEyeZ,   
                  AtX + n*cAtX,AtY + n*cAtY,AtZ + n*cAtZ,
                  *Up)
        
    if ViewMode == '5':
        n = 4
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FOVY + n*cFOVY,1,NEAR + n*cNEAR,FAR + n*cFAR);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(EyeX + n*cEyeX,EyeY + n*cEyeY,EyeZ + n*cEyeZ,   
                  AtX + n*cAtX,AtY + n*cAtY,AtZ + n*cAtZ,
                  *Up)

    if ViewMode == '6':
        n = 5
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FOVY + n*cFOVY,1,NEAR + n*cNEAR,FAR + n*cFAR);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(EyeX + n*cEyeX,EyeY + n*cEyeY,EyeZ + n*cEyeZ,   
                   AtX + n*cAtX,AtY + n*cAtY,AtZ + n*cAtZ,
                  *Up)

# The following is an arbitrary numeric identifier for this display list.
# Here, we use 100 just because it is clearly not a coordinate of a
# vertex, a scale factor, or any other number in the program. */

PICKET = 100

def drawInit():
    '''Create a call list for one picket of the fence.  The first
argument is our numeric constant.  The second requests that the
graphics pipeline just record this display list, but don't draw
anything.'''
    glNewList(PICKET, GL_COMPILE);
    drawPicket();
    glEndList();

def display():
    twDisplayInit();
    setCamera();

    # draw ground and sky, using default colors
    twSky();
    twGround();

    # draw front fence
    glPushMatrix();
    glTranslatef(-40,0,0);
    for i in range(20):
        glCallList(PICKET);
        glTranslatef(5,0,0);
    glPopMatrix();
  
    # draw right side fence
    glPushMatrix();
    glTranslatef(60,0,0);
    glRotatef(90,0,1,0); 
    for i in range(25):
        glCallList(PICKET);
        glTranslatef(5,0,0);
    glPopMatrix();
  
    # draw left side fence
    glPushMatrix();
    glTranslatef(-40,0,0);
    glRotatef(90,0,1,0);
    for i in range(17):
        glCallList(PICKET);
        glTranslatef(5,0,0);
    glPopMatrix();

    # draw barn
    glPushMatrix();
    glTranslatef(-40,0,-125);
    glRotatef(-90,0,1,0);
    glScalef(40,35,50);
    teal      = (0,0.5,0.5)
    dark_blue = (0,0,0.5)
    cyan      = (0,1,1)
    twSolidBarn(teal,dark_blue,cyan);
    glPopMatrix();

    glFlush();
    glutSwapBuffers()

def myCamSettings (key, x, y):
  global ViewMode
  ViewMode = key;
  glutPostRedisplay();

def main():
    glutInit(sys.argv)
    glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH)   
    twBoundingBox(-45,65,0,65,-130,5);
    twInitWindowSize(500,500)
    glutCreateWindow(sys.argv[0])
    drawInit();
    glLineWidth(2);
    glutDisplayFunc(display)
    twMainInit()
    twKeyCallback('1', myCamSettings, "View from the first camera set-up");
    twKeyCallback('2', myCamSettings, "View from the second camera set-up");
    twKeyCallback('3', myCamSettings, "View from the second camera set-up");
    twKeyCallback('4', myCamSettings, "View from the second camera set-up");
    twKeyCallback('5', myCamSettings, "View from the second camera set-up");
    twKeyCallback('6', myCamSettings, "View from the second camera set-up");    
    glutMainLoop()

if __name__ == '__main__':
    main()
