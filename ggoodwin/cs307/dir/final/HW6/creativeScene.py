'''Gabrielle Goodwin
'''
from TW import *
 

# ================================================================

def init():
    global qobj #so I can use gluCylinder
    qobj = gluNewQuadric();
    gluQuadricNormals(qobj, GLU_SMOOTH);
    gluQuadricTexture( qobj, GL_TRUE )

    global textureIDs
    textureIDs = glGenTextures(6) # get all the texture ids 

    twLoadTexture(textureIDs[0],twPathname("newtech.PPM"))
    twLoadTexture(textureIDs[1],twPathname("iggy.PPM"))
    twLoadTexture(textureIDs[2],twPathname("solarpanel.PPM"))
    twLoadTexture(textureIDs[3],twPathname("stars.PPM"))
    twLoadTexture(textureIDs[4],twPathname("atmo.PPM"))
    twLoadTexture(textureIDs[5],twPathname("moonsurface.PPM"))
    
def drawSpace():
    #Draws and textures the 6 walls that make up the enviornment

    glBindTexture(GL_TEXTURE_2D, textureIDs[3])
    glBegin(GL_QUADS);

    ## front
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 0,0,0);
    glTexCoord2f(0,1); glVertex3f( 0,5,0); 
    glTexCoord2f(1,1); glVertex3f( 5,5,0); 
    glTexCoord2f(1,0); glVertex3f( 5,0,0); 
    ## left
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 0,5,5); 
    glTexCoord2f(0,1); glVertex3f( 0,5,0); 
    glTexCoord2f(1,1); glVertex3f( 0,0,0); 
    glTexCoord2f(1,0); glVertex3f( 0,0,5);
    ## 
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 0,0,5);
    glTexCoord2f(0,1); glVertex3f( 0,5,5); 
    glTexCoord2f(1,1); glVertex3f( 5,5,5); 
    glTexCoord2f(1,0); glVertex3f( 5,0,5); 
    ## 
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 5,0,0); 
    glTexCoord2f(0,1); glVertex3f( 5,5,0); 
    glTexCoord2f(1,1); glVertex3f( 5,5,5); 
    glTexCoord2f(1,0); glVertex3f( 5,0,5);

    ##top
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 0,5,0); 
    glTexCoord2f(0,1); glVertex3f( 0,5,5); 
    glTexCoord2f(1,1); glVertex3f( 5,5,5); 
    glTexCoord2f(1,0); glVertex3f( 5,5,0);
    

    glEnd();

    glBindTexture(GL_TEXTURE_2D, textureIDs[4])
    glBegin(GL_QUADS);

    #bottom
    glColor3f(1,1,1)
    glTexCoord2f(0,0); glVertex3f( 0,0,0); 
    glTexCoord2f(0,1); glVertex3f( 0,0,5); 
    glTexCoord2f(1,1); glVertex3f( 5,0,5); 
    glTexCoord2f(1,0); glVertex3f( 5,0,0);
    glEnd();
    


def drawSatellite():
    #Draws a satellite object. Takes no arguments
    glPushMatrix()

    #Middle square
    glColor3f(1,1,1)
    glEnable(GL_TEXTURE_GEN_S) #enable texture coordinate generation
    glEnable(GL_TEXTURE_GEN_T)
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
    #Draws a space shuttle. Currently untextured
        ## Draw Body of the shuttle
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

        ##Draw Nose of the Shuttle
        glBegin(GL_TRIANGLES)
        glVertex3f(-1,0,2)
        glVertex3f(0,1,2)
        glVertex3f(0,0,3)

        glVertex3f(1,0,2)
        glVertex3f(0,1,2)
        glVertex3f(0,0,3)

        glVertex3f(-1,0,2)
        glVertex3f(1,0,2)
        glVertex3f(0,0,3)
        glEnd()

        ##Draw Back of Shuttle
        glBegin(GL_TRIANGLES)
        glVertex3f(-1,0,-2)
        glVertex3f(1,0,2)
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

def drawMoon():
    #draws a sphere and textures it with a moon pattern
    glColor3f(1,1,1)
    glEnable(GL_TEXTURE_GEN_S) #enable texture coordinate generation
    glEnable(GL_TEXTURE_GEN_T)
    glBindTexture(GL_TEXTURE_2D, textureIDs[5])
    glutSolidSphere(.5,20,20)
    glDisable(GL_TEXTURE_GEN_S) #disable texture coordinate generation
    glDisable(GL_TEXTURE_GEN_T)
    

def drawScene():
    drawSpace()
    glPushMatrix()
    glTranslatef(1,2,2)
    glRotatef(30,1,0,0)
    glScalef(.3,.3,.3)
    drawSatellite()
    glPopMatrix()
    
    glPushMatrix()
    glTranslatef(3,1,2.5)
    glRotatef(-90,0,1,0)
    glRotatef(-45,1,0,0)
    glScalef(.3,.3,.3)
    drawShuttle()
    glPopMatrix()

    glPushMatrix()
    glTranslatef(2,4,1)
    glScalef(.5,.5,.5)
    drawMoon()
    glPopMatrix()


## ================================================================

def display():
    twDisplayInit();
    twCamera();

    glEnable(GL_TEXTURE_2D);

    drawScene()
    
    glFlush();
    glutSwapBuffers()


### ================================================================

def main():
    global DeltaT
    glutInit(sys.argv)
    if len(sys.argv) > 1:
            DeltaT = float(sys.argv[1])
    glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH)

    twBoundingBox(0,3,0,3,0,3)
    twInitWindowSize(500,500)
    glutCreateWindow(sys.argv[0])
    glutDisplayFunc(display)
    twMainInit()
    init()
    glutMainLoop()


if __name__ == '__main__':
    main()