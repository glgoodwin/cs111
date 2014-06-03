''' Gabrielle Goodwin
    cs307 Assignment 1
    Bacon St. House
    2/9/2012 '''

import sys

from TW import *

def drawPart1(location, y_rot_degrees, size):
    'Calls upon the def that draws the smaller part of the house and places it'
    'in the correct position'
    glPushMatrix()
    glTranslate(location[0],location[1],location[2])
    glRotate(y_rot_degrees,0,1,0)
    drawPiece(makePieceVertexArray(size[0],size[1],size[2]))
    glPopMatrix()

def drawPart2(location, y_rot_degrees, size):
    'place the small, connecting piece'
    glPushMatrix()
    glTranslate(location[0],location[1],location[2])
    glRotate(y_rot_degrees,0,1,0)
    drawPiece2(makePieceVertexArray2(size[0],size[1],size[2]))
    glPopMatrix()

def drawPart3(location, y_rot_degrees, size):
    'places the largest part of the house'
    glPushMatrix()
    glTranslate(location[0],location[1],location[2])
    glRotate(y_rot_degrees,0,1,0)
    drawPiece3(makePieceVertexArray3(size[0],size[1],size[2]))
    glPopMatrix()

def drawHouse():
    'puts all the pieces of the house together'
    drawPart1((25,0,70),0,(30,15,25))
    drawPart2((70,0,70),90,(25,15,20))
    drawPart3((115,0,88),90,(40,30,45))
    drawOverhang(house)

def makePieceVertexArray( w, h, len ):
    '''Creates and returns an array of vertices for the smaller part of the house.
w is the width, h is the height, len is the length'''
    front = [ [ 0, 0, 0 ],       # left bottom 
              [ w, 0, 0 ],       # right bottom
              [ w, h, 0 ],       # right top 
              [ 0, h, 0 ],       # left top
              [ w*0.5, h+w*0.5, 0 ] # ridge 
              ]
    # list comprehension to construct back just like front except for Z
    back = [ [v[0], v[1], -len] for v in front ]
    front.extend(back)          # NOT "append," which only adds one item, even given a list
    return front

def makePieceVertexArray2( w, h, len ):
    '''Creates and returns an array of vertices for the connecting piece.
w is the width, h is the height, len is the length'''
    front = [ [ 0, 0, 0 ],       # left bottom 
              [ w, 0, 0 ],       # right bottom
              [ w, h, 0 ],       # right top 
              [ 0, h, 0 ],       # left top
              [ w*0.5, h*.5+w*.5, 0 ] # ridge 
              ]
    # list comprehension to construct back just like front except for Z
    back = [ [v[0], v[1], -len] for v in front ]
    front.extend(back)          # NOT "append," which only adds one item, even given a list
    return front

def makePieceVertexArray3( w, h, len ):
    '''Creates and returns an array of vertices for the largest part of the house.
w is the width, h is the height, len is the length'''
    front = [ [ 0, 0, 0 ],       # left bottom 
              [ w, 0, 0 ],       # right bottom
              [ w, h, 0 ],       # right top 
              [ 0, h, 0 ],       # left top
              [ w*0.5, h*.6+w*.6, 0 ] # ridge
         
              ]
    # list comprehension to construct back just like front except for Z
    back = [ [v[0], v[1], -len] for v in front ]
    front.extend(back)          # NOT "append," which only adds one item, even given a list
    return front

def add_overhang(verts):
    'creates vertices for the part of the roof that sticks out from wall'
    overhang = [ [115, 30, 88],
                 [115, 15, 90],
                 [115, 15, 88],
                 [70, 30, 88],
                 [70, 15, 90],
                 [70, 15, 88]
                     ]
    verts.extend(overhang)

'initializes the vertices'
house = makePieceVertexArray3(40,30,45)
add_overhang(house)

def drawOverhang(c):
    'draws the overhang'
    twColorName(TW_BLUE)
    drawTri(c,10,11,12)
    twColorName(TW_GREEN)
    drawTri(c,13,14,15)
    twColorName(TW_ORANGE)
    drawQuad(c,15,14,11,12)
    twColorName(TW_BLACK)
    drawQuad(c,14,13,10,11)
# =====================================================================
# Two useful helper functions.  Probably general enough to move to a
# library like TW, but that hides too much for this early demo.

def drawTri(verts, a, b, c):
    '''Draw a triangle, given an vertex array and three indices into it, in CCW order'''
    glBegin(GL_TRIANGLES)
    glVertex3fv(verts[a])
    glVertex3fv(verts[b])
    glVertex3fv(verts[c])
    glEnd()

def drawQuad(verts, a, b, c, d):
    '''Draw a quad, given an vertex array and four indices into it, in CCW order'''
    glBegin(GL_QUADS)
    glVertex3fv(verts[a])
    glVertex3fv(verts[b])
    glVertex3fv(verts[c])
    glVertex3fv(verts[d])
    glEnd()

# ================================================================

              
def drawPiece(b):
    '''draws the smaller part of house'''
    twColorName(TW_PINK)
    drawQuad(b, 0, 1, 2, 3)     # front
    drawTri(b, 3, 2, 4)
    twColorName(TW_PINK)
    drawQuad(b, 5, 6, 7, 8)
    drawTri(b, 7, 8, 9)
    twColorName(TW_BLUE)
    drawQuad(b, 0, 3, 8, 5)     # left side
    twColorName(TW_MAROON)
    drawQuad(b, 1, 2, 7, 6)     # right side
    twColorName(TW_OLIVE)
    drawQuad(b, 3, 4, 9, 8)     # left roof
    twColorName(TW_BLACK)
    drawQuad(b, 2, 4, 9, 7)     # right roof

def drawPiece2(b):
    '''draws the connecting piece'''
    twColorName(TW_BLUE)
    drawQuad(b, 0, 1, 2, 3)     # front
    drawTri(b, 3, 2, 4)
    twColorName(TW_GREEN)
    drawQuad(b, 5, 6, 7, 8)
    drawTri(b, 7, 8, 9)
    twColorName(TW_PINK)
    drawQuad(b, 0, 3, 8, 5)     # left side
    twColorName(TW_PINK)
    drawQuad(b, 1, 2, 7, 6)     # right side
    glColor3ub(255,20,147)      #RGB Color 1
    drawQuad(b, 3, 4, 9, 8)     # left roof
    glColor3ub(100,149,237)     #RGB Color 2
    drawQuad(b, 2, 4, 9, 7)     # right roof

def drawPiece3(b):
    '''draws the largest part of house'''
    twColorName(TW_BLUE)
    drawQuad(b, 0, 1, 2, 3)     # front
    drawTri(b, 3, 2, 4)
    twColorName(TW_GREEN)
    drawQuad(b, 5, 6, 7, 8)
    drawTri(b, 7, 8, 9)
    twColorName(TW_PINK)
    drawQuad(b, 0, 3, 8, 5)     # left side
    twColorName(TW_MAROON)
    drawQuad(b, 1, 2, 7, 6)     # right side
    twColorName(TW_OLIVE)
    drawQuad(b, 3, 4, 9, 8)     # left roof
    twColorName(TW_BLACK)
    drawQuad(b, 2, 4, 9, 7)     # right roof
    twColorName(TW_RED)


# ================================================================
# a callback function, to draw the scene, as necessary

def display():
    twDisplayInit(0.7, 0.7, 0.7) # clear background to 70% gray
    twCamera()                   # set up the camera

    drawHouse()

    glFlush()                   # clear the graphics pipeline
    glutSwapBuffers()           # make this the active framebuffer

# ================================================================

def main():
    glutInit(sys.argv)
    glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH)
    twInitWindowSize(500,500)
    twBoundingBox(25,115,0,42,45,90) #bounding box set around house
    glutCreateWindow(sys.argv[0])
    glutDisplayFunc(display)    # register the callback
    ## twSetMessages(TW_ALL_MESSAGES)
    twMainInit()
    glutMainLoop()

if __name__ == '__main__':
    main()
