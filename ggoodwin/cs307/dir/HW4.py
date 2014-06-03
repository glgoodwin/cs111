""" Gabe Goodwin
    HW4 Lit Scene
    3/16/2012
    
"""

import sys

from TW import *

### ================================================================

def display():
    twDisplayInit();
    twCamera();

    #Ambient Light
    lightPos1 = ( -.3, .5, .5, 0)
    twGrayLight(GL_LIGHT0,lightPos1,0.1,.7,0.2);
    twAmbient(.3)

    #spotlight 1
    lightPos2 = (.2,.8,.15,1)
    twGraySpotlight(GL_LIGHT1,lightPos2,.5,0.7,0.6,
                    (0,-1,0),   # direction is straight down
                    22,         # cutoff angle
                    10          # spot_exponent
                    )
    #spotlight2
    lightPos3 = (.2,.5,.15,1)
    twGraySpotlight(GL_LIGHT2,lightPos3,.5,0.7,0.6,
                    (0,1,-.1),   # direction is straight up
                    22,         # cutoff angle
                    10          # spot_exponent
                    )
    
    glEnable(GL_LIGHTING);

    wallColor = (47/244.0, 79/255.0, 79/255.0)
    floorColor = (205/255.0, 133/255.0, 63/255.0)
    ballColor = (166/255.0,166/255.0,166/255.0)
    sconceColor = (200/255.0,200/255.0,200/255.0)

    glShadeModel(GL_FLAT)
    ## back wall
    glNormal3f(0,0,1)
    twColor(wallColor,.7,50)
    glPushMatrix()
    glRotatef(-90,1,0,0)
    twDrawUnitSquare(50,50)
    glPopMatrix()
    ## left wall
    glNormal3f(1,0,0)
    glPushMatrix()
    glRotatef(90,0,0,1)
    twDrawUnitSquare(50,50)
    glPopMatrix()
    ## floor
    glNormal3f(0,1,0)
    twColor(floorColor,.2,20)   
    twDrawUnitSquare(50,50)


    ##draw ball
    twColor(ballColor,.2,15)
    glPushMatrix()
    glTranslatef(.3,.05,.2)
    glScalef(.1,.1,.1)
    glShadeModel(GL_SMOOTH)
    glutSolidSphere(.5,20,20)
    glPopMatrix()

    ##draw Sconce
    twColor(sconceColor,1,0)
    glPushMatrix()
    glTranslatef(.2,.7,.15)
    glRotatef(90,1,0,0)
    glScalef(.1,.1,.1)
    glShadeModel(GL_SMOOTH)
    glutSolidCone(.3,.7,20,20)
    glPopMatrix()

    glPushMatrix()
    glTranslatef(.2,.6,.15)
    glRotatef(-90,1,0,0)
    glScalef(.1,.1,.1)
    glShadeModel(GL_SMOOTH)
    glutSolidCone(.3,.7,20,20)
    glPopMatrix()

    

    

    glFlush();
    glutSwapBuffers();

### ================================================================

def main():
    glutInit(sys.argv)
    glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH)
    min = -0.1
    max = 1.1
    twBoundingBox(min,max,min,max,min,max)
    twInitWindowSize(500,500)
    glutCreateWindow(sys.argv[0])
    glutDisplayFunc(display)
    twMainInit()
    glutMainLoop()

if __name__ == '__main__':
    main()

