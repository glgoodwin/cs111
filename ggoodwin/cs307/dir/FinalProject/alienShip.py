## Gabe Goodwin as part of final project
"""Alien ship- includes keyboard callback to turn on and off shrink ray
   'a' turns it on and 's' turns it off""" 

from TW import *

global abduction
abduction = False

def init():
    global qobj #so I can use gluCylinder
    qobj = gluNewQuadric();
    gluQuadricNormals(qobj, GLU_SMOOTH);
    gluQuadricTexture( qobj, GL_TRUE )

    global sphere  #for texture mapped sphere
    sphere = gluNewQuadric();
    gluQuadricDrawStyle(sphere, GLU_FILL);
    gluQuadricTexture(sphere, GL_TRUE);
    gluQuadricNormals(sphere, GLU_SMOOTH);
    
    global textureIDs
    textureIDs = glGenTextures(2) # get all the texture ids 

    twLoadTexture(textureIDs[0],twPathname('metal.ppm'))
    twLoadTexture(textureIDs[1],twPathname("alien.ppm"))

def drawAlienShip():
    "Body of Alien Ship"
    glColor3f(.5,.5,.5)
    glPushMatrix()
    glScalef(3,.5,3)
    glBindTexture(GL_TEXTURE_2D, textureIDs[0])
    gluSphere(sphere,1,20,20)
    glPopMatrix()
    glPushMatrix()
    glTranslatef(0,.5,0)
    glRotatef(270,1,0,0)
    glBindTexture(GL_TEXTURE_2D, textureIDs[1])
    gluCylinder(qobj,.6,.6,.4,20,20)
    glPopMatrix()

def drawDeathRay():
    glDisable(GL_TEXTURE_2D)
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glDepthMask(GL_FALSE);
        
     # Turn off lighting
    #glDisable(GL_LIGHTING);

    glColor4f(0,1,0,0.5);
    glPushMatrix()
    glTranslatef(0,-.5,0)
    glRotatef(90,1,0,0)
    gluCylinder(qobj,.2,1,4,20,20)
    glPopMatrix()
        
    glDisable(GL_BLEND);

    glDepthMask(GL_TRUE);
        
    # Turn on lighting
    #glEnable(GL_LIGHTING);

def drawScene(abduct):
    drawAlienShip()
    if abduct == True:
        drawDeathRay()
    
    
    

def display():
        twDisplayInit(0.7, 0.7, 0.7) 
        twCamera()

        glEnable(GL_TEXTURE_2D)

        drawScene(abduction)

        glFlush()                   
        glutSwapBuffers()

def keys(key, x, y):
    global abduction
    if key == 'a':
        print "now abducting"
        abduction = True
    if key == 's':
        print 'stopping abduction'
        abduction = False

    glutPostRedisplay()

def main():
        glutInit(sys.argv)
        glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH)
        twInitWindowSize(500,500)
        twBoundingBox(-3,3,-3,3,-3,3)
        glutCreateWindow(sys.argv[0])
   
        twMainInit()
        twKeyCallback('a',keys,"turn on abduction mode")
        twKeyCallback('s',keys,"turn off abduction mode")
        init()
        glutDisplayFunc(display) 
        glutMainLoop()
        

if __name__ == '__main__':
    main()
