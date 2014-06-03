'''Gabe Goodwin
   3/28/2012
   HW 5 Textured Barn '''

'''Parts of code borrowd from TW Barn by Scott Anderson'''

import sys

from TW import *

def makeBarnVertexArray( w, h, len ):
    '''Creates and returns an array of vertices for the barn.
w is the width, h is the height, len is the length'''
    front = [ [ 0, 0, 0 ],       # left bottom 
              [ w, 0, 0 ],       # right bottom
              [ w, h, 0 ],       # right top 
              [ 0, h, 0 ],       # left top
              [ w*0.5, h+w*0.5, 0 ] # ridge 
              ]
    # list comprehension to construct back just like front except for Z
    back = [ [v[0], v[1], -len] for v in front ]
    front.extend(back)              return front

BarnVertices = makeBarnVertexArray(30,40,50)

# =====================================================================
'Sets up polygons with the texture vertex arrays'

def drawTri(verts, a, b, c):
    glBegin(GL_TRIANGLES)
    glTexCoord2f(0,3.4) 
    glVertex3fv(verts[a])
    glTexCoord2f(6.0,3.4)
    glVertex3fv(verts[b])
    glTexCoord2f(6.0,0)
    glVertex3fv(verts[c])
    glEnd()

def drawQuadfront(verts, a, b, c, d):
    glBegin(GL_QUADS)
    glTexCoord2f(0,0)
    glVertex3fv(verts[a])
    glTexCoord2f(8.0,0)
    glVertex3fv(verts[b])
    glTexCoord2f(8.0,8.0)
    glVertex3fv(verts[c])
    glTexCoord2f(0,8.0)
    glVertex3fv(verts[d])
    glEnd()

def drawQuadSides(verts, a, b, c, d):
    'The sides have a different pattern of texture vertices, and therefore require a seperate function'
    glBegin(GL_QUADS)
    glTexCoord2f(0,0)
    glVertex3fv(verts[a])
    glTexCoord2f(0,8.0)
    glVertex3fv(verts[b])
    glTexCoord2f(8.0,8.0)
    glVertex3fv(verts[c])
    glTexCoord2f(8.0,0)
    glVertex3fv(verts[d])
    glEnd()
    
roofcolor = (170.0/255.0, 170.0/255.0, 170.0/255.0)
    
# ================================================================

def drawBarn(b):
    '''draws the barn, sets up the textures and surface normals'''
    twColor(roofcolor, .2,.2)
    glBindTexture(GL_TEXTURE_2D, int(textureIDs[0]));
    glNormal3f(0,0,1)
    drawQuadfront(b, 0, 1, 2, 3)     
    drawTri(b, 3, 2, 4)# front
    glBindTexture(GL_TEXTURE_2D, int(textureIDs[0]));
    glNormal3f(0,0,-1)
    drawQuadfront(b, 5, 6, 7, 8)
    drawTri(b, 7, 8, 9)#back
    glBindTexture(GL_TEXTURE_2D, int(textureIDs[0]));
    glNormal3f(-1,0,0)
    drawQuadSides(b, 0, 3, 8, 5)     # left side
    glBindTexture(GL_TEXTURE_2D, int(textureIDs[0]));
    glNormal3f(-1,0,0)
    drawQuadSides(b, 1, 2, 7, 6)     # right side
    glBindTexture(GL_TEXTURE_2D, int(textureIDs[1]));
    glNormal3f(-1,1,0)
    drawQuadSides(b, 3, 4, 9, 8)     # left roof
    glBindTexture(GL_TEXTURE_2D, int(textureIDs[1]));
    glNormal3f(1,1,0)
    drawQuadSides(b, 2, 4, 9, 7)     # right roof

def init():
    global textureIDs
    textureIDs = glGenTextures(2); # get all the texture ids 


    twLoadTexture(textureIDs[0],twPathname("siding.ppm"))
    twLoadTexture(textureIDs[1],twPathname("roofing.ppm"))


# ================================================================
# a callback function, to draw the scene, as necessary

def display():
    twDisplayInit(0.7, 0.7, 0.7) # clear background to 70% gray
    twCamera()                   # set up the camera
    glPushAttrib(GL_ALL_ATTRIB_BITS);
    

    glEnable(GL_LIGHTING)
    twGrayLight( GL_LIGHT0, (-.5,.5,.5,0), 0.3, 0.2, 1) # light from upper left

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
    glEnable(GL_TEXTURE_2D)
    
    drawBarn(BarnVertices)      # draw the barn

    glPopAttrib()
    glFlush()                   # clear the graphics pipeline
    glutSwapBuffers()           # make this the active framebuffer

# ================================================================

def main():
    glutInit(sys.argv)
    glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH)
    twInitWindowSize(500,500)
    twVertexArray(BarnVertices) # set up the bounding box
    glutCreateWindow(sys.argv[0])
    glutDisplayFunc(display)    # register the callback
    twMainInit()
    init()
    glutMainLoop()

if __name__ == '__main__':
    main()


