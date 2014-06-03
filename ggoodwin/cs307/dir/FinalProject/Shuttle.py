## Gabe Goodwin
## Space Shuttle

from TW import *

def init():
    global qobj #so I can use gluCylinder
    qobj = gluNewQuadric();
    gluQuadricNormals(qobj, GLU_SMOOTH);
    gluQuadricTexture( qobj, GL_TRUE )

    global textureIDs
    textureIDs = glGenTextures(2) # get all the texture ids 

    twLoadTexture(textureIDs[0],twPathname('david4.ppm'))
    twLoadTexture(textureIDs[1],twPathname("iggy.ppm"))


def drawShuttle():
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
        glBindTexture(GL_TEXTURE_2D, textureIDs[0])
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
        

def display():
        twDisplayInit(0.7, 0.7, 0.7) 
        twCamera()

        glEnable(GL_TEXTURE_2D)

        drawShuttle()

        glFlush()                   
        glutSwapBuffers()           

def main():
        glutInit(sys.argv)
        glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH)
        twInitWindowSize(500,500)
        twBoundingBox(-1.7,1.7,-1.7,1.7,-1.7,1.7)
        glutCreateWindow(sys.argv[0])
        glutDisplayFunc(display)    
        twMainInit()
        init()
    
        glutMainLoop()
        

if __name__ == '__main__':
    main()




