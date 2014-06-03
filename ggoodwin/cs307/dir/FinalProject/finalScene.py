'''Gabrielle Goodwin
Final project
Draws a space scene depicting an alien ship shrinking a space shuttle
Keyboard Callbacks:
   0 -reset animation
   1 -begin animation
   2 -save frames
   a -turn on shrink ray
   s -turn off shrink ray '''
from TW import *


#global variables
shrink = False
Time = 0
DeltaT = .1
scale = .5
ypos = 7.0

SaveFrames = False
 

# ================================================================

def init():
    global qobj #so I can use gluCylinder
    qobj = gluNewQuadric();
    gluQuadricNormals(qobj, GLU_SMOOTH);
    gluQuadricTexture( qobj, GL_TRUE )

    global sphere
    sphere = gluNewQuadric();
    gluQuadricTexture(sphere, GL_TRUE);
    gluQuadricNormals(sphere, GLU_SMOOTH);

    global textureIDs
    textureIDs = glGenTextures(9) # get all the texture ids 

    twLoadTexture(textureIDs[0],twPathname("newtech.ppm"))
    twLoadTexture(textureIDs[1],twPathname("iggy.ppm"))
    twLoadTexture(textureIDs[2],twPathname("solarpanel.ppm"))
    twLoadTexture(textureIDs[3],twPathname("stars.ppm"))
    twLoadTexture(textureIDs[4],twPathname("metal.ppm"))
    twLoadTexture(textureIDs[5],twPathname("moonsurface.ppm"))
    twLoadTexture(textureIDs[6],twPathname("earth3.ppm"))
    twLoadTexture(textureIDs[7],twPathname("alien.ppm"))
    twLoadTexture(textureIDs[8],twPathname("david4.ppm"))
 
def drawSpace():

    """Draws a six sided box that is texture mappeed with stars"""

    glBindTexture(GL_TEXTURE_2D, textureIDs[3])
    glBegin(GL_QUADS);

    ## front
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 0,0,0);
    glTexCoord2f(0,1); glVertex3f( 0,10,0); 
    glTexCoord2f(1,1); glVertex3f( 10,10,0); 
    glTexCoord2f(1,0); glVertex3f( 10,0,0); 
    ## left
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 0,10,10); 
    glTexCoord2f(0,1); glVertex3f( 0,10,0); 
    glTexCoord2f(1,1); glVertex3f( 0,0,0); 
    glTexCoord2f(1,0); glVertex3f( 0,0,10);
    ## 
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 0,0,10);
    glTexCoord2f(0,1); glVertex3f( 0,10,10); 
    glTexCoord2f(1,1); glVertex3f( 10,10,10); 
    glTexCoord2f(1,0); glVertex3f( 10,0,10); 
    ## 
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 10,0,0); 
    glTexCoord2f(0,1); glVertex3f( 10,10,0); 
    glTexCoord2f(1,1); glVertex3f( 10,10,10); 
    glTexCoord2f(1,0); glVertex3f( 10,0,10);

    ##top
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 0,10,0); 
    glTexCoord2f(0,1); glVertex3f( 0,10,10); 
    glTexCoord2f(1,1); glVertex3f( 10,10,10); 
    glTexCoord2f(1,0); glVertex3f( 10,10,0);

    #bottom
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 0,0,0); 
    glTexCoord2f(0,1); glVertex3f( 0,0,10); 
    glTexCoord2f(1,1); glVertex3f( 10,0,10); 
    glTexCoord2f(1,0); glVertex3f( 10,0,0);
    

    glEnd();


def drawSatellite():

    """Draws a satellite with texture mapping"""
    
    glPushMatrix()

    #Middle square
    glColor3f(1,1,1)
    glEnable(GL_TEXTURE_GEN_S) #enable texture coordinate generation
    glEnable(GL_TEXTURE_GEN_T)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP)
    glBindTexture(GL_TEXTURE_2D, textureIDs[0])
    glutSolidCube(1)
    glDisable(GL_TEXTURE_GEN_S) #disable texture coordinate generation
    glDisable(GL_TEXTURE_GEN_T)


    # Cylinder on top 
    glColor3f(1,1,1)
    glPushMatrix()
    glTranslatef(0,.5,0)
    glRotatef(270,1,0,0)
    glBindTexture(GL_TEXTURE_2D, textureIDs[1])
    gluCylinder(qobj,.3,.3,.3,20,20)
    glPopMatrix()

    #Small right connecting cylinder
    connectorColor = (139.0/255.0, 137.0/255.0, 137.0/255.0)
    glColor3fv(connectorColor)
    glPushMatrix()
    glTranslatef(.5,-.2,0)
    glRotatef(90,0,1,0)
    gluCylinder(qobj,.1,.1,.3,20,20)
    glPopMatrix()

    #Right Wing
    glColor3f(1,1,1)
    glPushMatrix()
    glTranslatef(.7,-.2,0)
    glRotatef(90,0,1,0)
    glScalef(5,.3,.5)
    glBindTexture(GL_TEXTURE_2D, textureIDs[2])
    gluCylinder(qobj,.3,.3,5,20,20)
    glPopMatrix()

    #small left connecting Cylinder
    glColor3fv(connectorColor)
    glPushMatrix()
    glTranslatef(-.5,-.2,0)
    glRotatef(-90,0,1,0)
    gluCylinder(qobj,.1,.1,.3,20,20)
    glPopMatrix()

    #Left Wing
    glColor3f(1,1,1)
    glPushMatrix()
    glTranslatef(-.7,-.2,0)
    glRotatef(-90,0,1,0)
    glScalef(5,.3,.5)
    glBindTexture(GL_TEXTURE_2D, textureIDs[2])
    gluCylinder(qobj,.3,.3,5,20,20)
    glPopMatrix()
    glDisable(GL_TEXTURE_GEN_S) #disable texture coordinate generation
    glDisable(GL_TEXTURE_GEN_T)

    glPopMatrix()

def drawShuttle():
    """Draws a space shuttle"""
    
    glRotatef(-90,0,1,0)
    glRotatef(-45,1,0,0)
    ## Draw Body of the shuttle
    glDisable(GL_TEXTURE_2D)
    glColor3f(1,1,1)
    glBegin(GL_QUADS)
    glVertex3f(-1,0,2)
    glVertex3f(0,1,2)
    glVertex3f(0,1,-2)
    glVertex3f(-1,0,-2)

    glVertex3f(1,0,2)
    glVertex3f(0,1,2)
    glVertex3f(0,1,-2)
    glVertex3f(1,0,-2)

    glVertex3f(-1,0,2)
    glVertex3f(1,0,2)
    glVertex3f(1,0,-2)
    glVertex3f(-1,0,-2)
    glEnd()


    ##Draw Back of Shuttle
    glBegin(GL_TRIANGLES)
    glVertex3f(-1,0,-2)
    glVertex3f(1,0,-2)
    glVertex3f(0,1,-2)    
    glEnd()

    glPushMatrix()
    glColor3f(1,0,0)
    glTranslate(0,.6,-2.2)
    gluCylinder(qobj,.2,.2,.2,20,20)
    glPopMatrix()
    glPushMatrix()
    glTranslate(.3,.3,-2.2)
    gluCylinder(qobj,.2,.2,.2,20,20)
    glPopMatrix()
    glPushMatrix()
    glTranslate(-.3,.3,-2.2)
    gluCylinder(qobj,.2,.2,.2,20,20)
    glPopMatrix()

    ## Draw Wings
    glPushMatrix()
    glBegin(GL_TRIANGLES)
    glVertex3f(1,0,-2)
    glVertex3f(1,0,.5)
    glVertex3f(2,0,-2)
    glEnd()
    glPopMatrix()
    glPushMatrix()
    glBegin(GL_TRIANGLES)
    glVertex3f(-1,0,-2)
    glVertex3f(-1,0,.5)
    glVertex3f(-2,0,-2)
    glEnd()
    glPopMatrix()
    glPushMatrix()
    glBegin(GL_TRIANGLES)
    glVertex3f(0,1,-1)
    glVertex3f(0,2,-2)
    glVertex3f(0,1,-2)
    glEnd()
    glPopMatrix()

    
    #draw front end of shuttle
    glColor3f(1,1,1)
    glBegin(GL_TRIANGLES)
    glVertex3f(-1,0,2)
    glVertex3f(1,0,2)
    glVertex3f(0,1,2)    
    glEnd()

    #Draw seat for david tennant
    glEnable(GL_TEXTURE_2D)
    glBindTexture(GL_TEXTURE_2D, textureIDs[8])
    glBegin(GL_QUADS)
    glTexCoord2f(0,1);glVertex3f(.3,0,2.01)
    glTexCoord2f(1,1);glVertex3f(-.3,.0,2.01)
    glTexCoord2f(1,0);glVertex3f(-.3,.69,2.01)
    glTexCoord2f(0,0);glVertex3f(.3,.69,2.01)
    glEnd()
    glDisable(GL_TEXTURE_2D)

    ##Draw Nose of the Shuttle

    glColor3f(1,1,1)
    glBegin(GL_QUADS)
    glVertex3f(-.6,.4,2)
    glVertex3f(0,.4,2.6)
    glVertex3f(0,0,3)
    glVertex3f(-1,0,2)

    glVertex3f(.6,.4,2)
    glVertex3f(0,.4,2.6)
    glVertex3f(0,0,3)
    glVertex3f(1,0,2)
    glEnd()

    glBegin(GL_TRIANGLES)
    glVertex3f(-1,0,2)
    glVertex3f(1,0,2)
    glVertex3f(0,0,3)
    glEnd()
    
    # switch to transparency
    glPushMatrix()
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glDepthMask(GL_FALSE);

    glColor4f(.5,.5,.5,0.5);

    glBegin(GL_TRIANGLES)
    glVertex3f(0,1,2)
    glVertex3f(-.6,.4,2)
    glVertex3f(0,.4,2.6)

    glVertex3f(0,1,2)
    glVertex3f(.6,.4,2)
    glVertex3f(0,.4,2.6)
    glEnd()
    
    glDisable(GL_BLEND);

    glDepthMask(GL_TRUE);
    glPopMatrix()
        

def drawAlienShip():
    """Body of Alien Ship"""
    glEnable(GL_TEXTURE_2D)
    glColor3f(1,1,1)
    glPushMatrix()
    glScalef(3,.5,3)
    glBindTexture(GL_TEXTURE_2D, textureIDs[4])
    gluSphere(sphere,1,20,20)
    glPopMatrix()
    glPushMatrix()
    glTranslatef(0,.5,0)
    glRotatef(270,1,0,0)
    glBindTexture(GL_TEXTURE_2D, textureIDs[7])
    gluCylinder(qobj,.6,.6,.4,20,20)
    glPopMatrix()

def drawShrinkRay():
    "Draws a green 'shrink ray that comes from the bottom of the alien ship"
    glDisable(GL_TEXTURE_2D)
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glDepthMask(GL_FALSE);

    glColor4f(0,1,0,0.5);
    glPushMatrix()
    glTranslatef(0,-.5,0)
    glRotatef(90,1,0,0)
    gluCylinder(qobj,.2,1,4,20,20)
    glPopMatrix()
        
    glDisable(GL_BLEND);

    glDepthMask(GL_TRUE);
        

def drawMoon():
    "draws a texture mapped sphere"
    glColor3f(1,1,1)
    glEnable(GL_TEXTURE_GEN_S) #enable texture coordinate generation
    glEnable(GL_TEXTURE_GEN_T)
    glBindTexture(GL_TEXTURE_2D, textureIDs[5])
    glutSolidSphere(.5,20,20)
    glDisable(GL_TEXTURE_GEN_S) #disable texture coordinate generation
    glDisable(GL_TEXTURE_GEN_T)

def drawEarth():
    """draws a large tecture mapped sphere that shows through the bottom of the space box"""
    glColor3f(1,1,1)
    glBindTexture(GL_TEXTURE_2D, textureIDs[6])
    gluSphere(sphere,6,20,20)


SaveFrames = False
FrameNumber = 1

FrameFileTemplate = "/tmp/animation.ppm"

def saveFrame():
    global FrameNumber, SaveFrames
    if not SaveFrames:
            return
    file = FrameFileTemplate % int(FrameNumber)
    FrameNumber += 1
    if FrameNumber > 999:
        print "Sorry, this program assumes 3 digit numbers.  Please update it"
        sys.exit(1)
    glFlush()    
    twSaveFrame(file, False)

def drawShrinkage(smaller):
    """turns the shrink ray on and off"""
    drawAlienShip()
    if smaller == True:
        drawShrinkRay()


def drawScene():

    drawSpace()
    glPushMatrix()
    
    glTranslatef(3,3,3)
    glRotatef(85,0,1,0)
    glScalef(.5,.5,.5)
    drawSatellite()
    glPopMatrix()

    glPushMatrix()
    glTranslatef(2,8,1)
    drawMoon()
    glPopMatrix()

    glPushMatrix()
    glTranslatef(5,-4.7,5)
    glRotatef(300,0,0,1)
    drawEarth()
    glPopMatrix()

    
    # Matrix for Shuttle animation
    glPushMatrix()
    global scale

    A = [ 5,1,5] # start of line

    B = [ 6, -1, 5 ] # end of line

    dir = twVector(A,B) # direction of motion
    
    if( 0<=  Time <= 25 ):
    
        param = (Time - 15)/(25-15);
    
        P = twPointOnLine(A,dir,param) # computes P = A + dir*param
    
        glTranslatef(*P)
        global pos
        pos = P
        glScalef(.5,.5,.5)
        drawShuttle();

    elif (Time <49):
        glTranslatef(*pos)
        glRotatef(40,0,0,1)
        glScalef(.5,.5,.5)
        drawShuttle()

    if ( 49<= Time <= 53.8):
        # Make the shuttle Shrink
        glTranslatef(*pos)
        glRotatef(40,0,0,1)
        glScalef(scale,scale,scale)
        drawShuttle()
    glPopMatrix()


    ## Matrix for alien ship animation
    glPushMatrix()
    global shrink
    shrink = False
    C = [ 9,7, -5] # start of line

    D = [ 7.5, 7, -2 ] # end of line

    dir2 = twVector(D,C) # direction of motion
    
    if( 28<=  Time <= 48.2 ):
    
        param = (Time - 15)/(25-15);
    
        Q = twPointOnLine(C,dir2,param) # computes P = A + dir*param
    
        glTranslatef(*Q)
        print Q
        global pos2
        pos2 = Q
        drawShrinkage(shrink);

    elif (48.2 < Time < 54  ):
        glTranslatef(*pos2)
        shrink = True
        drawShrinkage(shrink)

    if(54 <= Time < 59 ):
        global ypos
        shrink = False
    
        glTranslatef(4.049999999999979, ypos, 4.900000000000041)
        drawShrinkage(shrink);

    glPopMatrix()

    
## ================================================================

def myCamera():
    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()
    gluPerspective(90, 1, 1, 50)
    glMatrixMode(GL_MODELVIEW)
    glLoadIdentity()
    gluLookAt(8,8,8, 5,5,5, 0,1,0)

def display():
    twDisplayInit();
    myCamera();

    glEnable(GL_TEXTURE_2D);

    drawScene()
    
    glFlush();
    glutSwapBuffers()

    if SaveFrames:
        saveFrame()

def keys(key, x, y):
    #For alienship
    global shrink, Time, SaveFrames
    if key == 'a':
        print "now shrinking"
        shrink = True
    if key == 's':
        print 'stopping shrinking'
        shrink = False

    # for shuttle
    global ypos, Time, scale
    if key == '0':
        Time = 0
        scale = .5
        ypos = 7.0
        glutIdleFunc(None)
        glutPostRedisplay()
    elif key == '1':
        glutIdleFunc(idleCallback)

    if key == '2':
        SaveFrames = not SaveFrames
        print "the program %s save frames in %s" % (
            "will" if SaveFrames else "won't",
            FrameFileTemplate )

    glutPostRedisplay()


def idleCallback():
    global Time, ypos,scale
    Time += DeltaT
    if (Time >= 49):
        scale = scale - .01
    if (Time > 54):
        ypos = ypos + .2
    glutPostRedisplay()




### ================================================================

def main():
    glutInit(sys.argv)
    
    if len(sys.argv) > 1:
            DeltaT = float(sys.argv[1])
    glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH)

    twBoundingBox(0,7,0,7,0,7)
    twInitWindowSize(500,500)
    glutCreateWindow(sys.argv[0])
    twMainInit()
    twKeyCallback('a',keys,"turn on shrink mode")
    twKeyCallback('s',keys,"turn off shrink mode")
    twKeyCallback('0', keys, "restart animation")
    twKeyCallback("1", keys, "start animation")
    twKeyCallback("2", keys, "toggle saving frames")
    glutDisplayFunc(display)
    init()
    glutMainLoop()


if __name__ == '__main__':
    main()
