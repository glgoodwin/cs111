'''
HW3: camera angles
Part 1

This program constructs 5 different views of the barn by changing gluPerspective
as well as gluLookAt.

By Dana Bullister and Gabe Goodwin

March 4th, 2012

Acknowledgements: Used some code and comments from BarnViews.py created by Scott D. Anderson

'''

import sys
from math import sqrt,sin
from random import random
from TW import *

### ================================================================

## We're using twWireBarn, which draws a unit-size barn: a barn in a unit
## cube.  The BARN_SIZE is the scaling factor we're using.

BARN_SIZE = 30
BARN_WIDTH = 10
BARN_HEIGHT = 20
BARN_LEN = 30

## parameters for gluPerspective
FOVY1 = 35 
FOVY2 = 30
FOVY3= 30
FOVY4= 60
FOVY5= 60
ASPECT_RATIO = 1

## The cube that contains the barn fits inside a sphere whose diameter is
## sqrt(3*BARN_SIZE^2). Thus, the radius of that bounding sphere is the
## following.

BS_RADIUS  = BARN_SIZE*sqrt(3)/2

## The eye needs to be a little farther from the center of the bounding
## sphere than the radius of the bounding sphere.  In fact, to maintain
## the 90 degree FOVY and have the eye be as close as possible, we can do
## a little geometry to determine that the eye radius must be the
## following. 

EYE_RADIUS = BS_RADIUS*sqrt(2)

## Near can be anywhere between the eye and the bounding sphere, but for
## the sake of computing a legal location, we will make the image plane be
## tangent to the bounding sphere, and so NEAR is the
## EYE_RADIUS-BS_RADIUS.  Similarly, we'll make the FAR side of the
## frustum be tangent to the bounding sphere, so FAR is
## EYE_RADIUS+BS_RADIUS.
  
NEAR = EYE_RADIUS-BS_RADIUS
FAR  = EYE_RADIUS+BS_RADIUS

## The following global variable determines which view of the barn we
## have.  It is set in the key callbacks and selects a case that sets up
## the the camera location.

ViewMode = '1'

def setCamera():
    '''Configures the camera for one of several fixed setups, depending on a global variable named 'ViewMode.' '''
    # Note that Python doesn't have switch statements, so we have this:
    if ViewMode == '1': 
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FOVY1,ASPECT_RATIO,NEAR,EYE_RADIUS+BS_RADIUS + 20);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(0,0,50,   
                  0,0,-15,
                  0,1,0);

    if ViewMode == '2': 
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FOVY2,ASPECT_RATIO,NEAR,EYE_RADIUS+BS_RADIUS + 40);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(30,0,60,    
                  0,0,-15,
                  0,1,0);

    if ViewMode == '3': 
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FOVY3,ASPECT_RATIO,NEAR,EYE_RADIUS+BS_RADIUS + 60);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(30,42,60,    
                  0,0,-30,
                  0,1,0);

    if ViewMode == '4': 
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FOVY4,ASPECT_RATIO,NEAR,EYE_RADIUS+BS_RADIUS + 60);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(0,80,0,    
                  0,0,0,
                  1,0,0);

    if ViewMode == '5': 
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FOVY5,ASPECT_RATIO,NEAR,EYE_RADIUS+BS_RADIUS + 60);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(0,80,0,    
                  0,0,0,
                  .25,0,-.75);
        
def display():
    twDisplayInit();
    setCamera();
    twSetOriginPointSize(10)
    twCoordinateFrameCues()     # to show axes and such
 
    glPushMatrix();
    glScalef(BARN_WIDTH,BARN_HEIGHT,BARN_LEN);
    sideColor = (1,0,0)
    roofColor=(0.2,0.2,0.2)
    endColor =(0,0,1)
    twWireBarn(sideColor,roofColor,endColor);
    glPopMatrix();
    
    glFlush();
    glutSwapBuffers();

def myCamSettings (key, x, y):
    global ViewMode
    ViewMode = key;
    glutPostRedisplay();

def main():
    glutInit(sys.argv)
    glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH)
    twBoundingBox(0,5,0,5,-5,0);
    twInitWindowSize(500,500)
    glutCreateWindow(sys.argv[0])
    glutDisplayFunc(display)
    glLineWidth(1)               
    twMainInit()
    twKeyCallback('1', myCamSettings, "View from the first camera set-up");
    twKeyCallback('2', myCamSettings, "View from the second camera set-up");
    twKeyCallback('3', myCamSettings, "View from the third camera set-up");
    twKeyCallback('4', myCamSettings, "View from the fourth camera set-up");
    twKeyCallback('5', myCamSettings, "View from the fifth camera set-up");
    glutMainLoop()

if __name__ == '__main__':
    main()
