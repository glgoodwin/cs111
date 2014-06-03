#Gabe Goodwin
#2/25/2012
#H2
# The clown becomes slightly scewed when you change the inout variables,
# but maintains the same basic shape. 

from TW import *

def drawClown(h,al,ll,hs,hl): #h=12 al=3 ll=3 hs=1.5
    #draw circle on origin
    glColor3f(1,0,10)
    glutSolidSphere(.15,20,20)

    
    #Draw Right (our right, clowns left) leg
    glColor3f(1,0,0)
    glPushMatrix()
    glTranslatef(1.5,(ll-1),-1)
    glRotatef(25,1,0,0)
    glScalef(1,ll,1)
    glutSolidCube(1)
    glPopMatrix()
    
    #Draw Left (our left, clown right) leg
    glColor3f(0,1,0)
    glPushMatrix()
    glTranslatef(-1.5,(ll-1),1)
    glRotatef(-20,1,0,0)
    glScalef(1,ll,1)
    glutSolidCube(1)
    glPopMatrix()

    #Draw Body
    glColor3f(0,0,1)
    glPushMatrix()  #body coordinate system
    glTranslatef(0,(h-1),0)
    glScalef(3,(h-(hs+ll)),2)
    glutSolidSphere(1,20,20)

    #Draw Buttons
    glColor3f(0,1,0)
    glTranslatef(0,0,1)
    glScalef(.25,.25,.25)
    glutSolidSphere(.3,20,20)
    glTranslatef(0,h/3,-.25)
    glScalef(.25,.25,.25)
    glutSolidSphere(1,20,20)
    glTranslatef(0,-h/3,1)
    glutSolidSphere(1,20,20)
    glPopMatrix()

    #Draw Head
    glColor3f(1,1,0)
    glPushMatrix()  #head corrdinate system
    glTranslatef(0,(ll+h+1),0)
    glutSolidSphere(hs,20,20)

    #Draw Face
    glColor3f(0,0,0)
    glPushMatrix()
    glTranslatef((hs/2),0,hs)
    glutSolidSphere(.15,20,20) #left eye
    glPopMatrix()
    glPushMatrix()
    glTranslatef(-(hs/2),0,hs)
    glutSolidSphere(.15,20,20) #right eye
    glPopMatrix()

    glColor3f(1,0,0)
    glPushMatrix()
    glTranslatef(0,-(hs/3),hs)
    glutSolidSphere(.2,20,20) #nose
    glPopMatrix()

    glColor3f(1,0,0)
    glPushMatrix()
    glTranslatef(0,-hs/2,hs/1.5)
    glRotatef(160,1,0,0)
    glutSolidTorus(.1,.6,20,20) #mouth
    
    glPopMatrix()
    glPopMatrix()
    

    #Draw Right Arm
    glColor3f(1,0,1)
    glPushMatrix()
    glTranslatef(3,(h+1),0)
    glRotatef(10,0,0,1)
    glScalef(al,1,1)
    glutSolidCube(1)
    glPopMatrix()

    #Draw Left Arm
    glPushMatrix()
    glTranslatef(-3,(h+1),0)
    glRotatef(-10,0,0,1)
    glScalef(al,1,1)
    glutSolidCube(1)
    glPopMatrix()

    #Draw Right Foot
    glColor3f(0,1,1)
    glPushMatrix()
    glTranslatef(1.5,0,0)
    glRotatef(20,1,0,0)
    glScalef(1,.2,1)
    glutSolidSphere(1,20,20)
    glPopMatrix()

    #Draw Left Foot
    glPushMatrix()
    glTranslatef(-1.5,1,3)
    glRotatef(-20,1,0,0)
    glScalef(1,.2,1)
    glutSolidSphere(1,20,20)
    glPopMatrix()

    #Draw Right Hand
    glPushMatrix()
    glTranslatef((al+2),(h+1.5),0)
    glScalef(.5,.5,.2)
    glutSolidSphere(1,20,20)
    glPopMatrix()

    #Draw Left Hand
    glColor3f(2,0,0)
    glPushMatrix()
    glTranslatef((-(al+2)),(h+1.5),0)
    glScalef(.5,.5,.2)
    glutSolidSphere(1,20,20)
    glPopMatrix()

    #Draw Hat
    glColor3f(0,2,0)
    glPushMatrix()
    glTranslate(-1,(h+ll+hs+.5),0)
    glRotatef(-55,0,0,1)
    glRotatef(-90,0,1,0)
    glutSolidCone(1,3,20,20)
    glPopMatrix()

    #Draw PomPom
    glColor3f(1,3,0)
    glPushMatrix()
    glTranslate(-hl,(h+ll+hs+hl),0)
    glutSolidSphere(.25,20,20)
    glPopMatrix()


    


if __name__ == '__main__':

    def display():
        twDisplayInit(0.7, 0.7, 0.7) # clear background to 70% gray
        twCamera()                   # set up the camera

        glColor3f(1,1,0.94)     # ivory snow
        drawClown(8,3,3,1.5,3)

        glFlush()                   # clear the graphics pipeline
        glutSwapBuffers()           # make this the active framebuffer

    def main():
        glutInit(sys.argv)
        glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH)
        twInitWindowSize(500,500)
        twBoundingBox(-5,5,0,15,-3,3)
        glutCreateWindow(sys.argv[0])
        glutDisplayFunc(display)    # register the callback
        twMainInit()
        glutMainLoop()
        
    # basic display if run as a script
    main()
