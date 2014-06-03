## Gabe Goodwin
## Space Shuttle

from TW import *

def init():
    global qobj #so I can use gluCylinder
    qobj = gluNewQuadric();
    gluQuadricNormals(qobj, GLU_SMOOTH);
    gluQuadricTexture( qobj, GL_TRUE )


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

        
        



def display():
        twDisplayInit(0.7, 0.7, 0.7) 
        twCamera()                   

        drawShuttle()

        glFlush()                   
        glutSwapBuffers()           

def main():
        glutInit(sys.argv)
        glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH)
        twInitWindowSize(500,500)
        twBoundingBox(-2,2,-2,2,-2,2)
        glutCreateWindow(sys.argv[0])
        glutDisplayFunc(display)    
        twMainInit()
        init()
        glutMainLoop()
        

if __name__ == '__main__':
    main()




