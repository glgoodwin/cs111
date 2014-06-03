#!/usr/local/bin/python2.7
#Gabe Goodwin 11/2/12
#A variety of functions that interact with the database.  Unfortunately, connects directly to the db. Could not get hw5connect to work properly.

import MySQLdb
import hw5connect
import cgi
import cgitb; cgitb.enable()
import os
import MySQLdb

from ggoodwin_dsn import dsn 
import hw5connect



#========================================
# Functions that make the page functional
conn = MySQLdb.connect(host='localhost',user='ggoodwin',passwd='eevaengi',db='wmdb')
curs = conn.cursor()


#Adds an actor into person
def insertActor(nm, name, birthdate):
    curs.execute('insert into person values ( %s, %s, %s, 120)', [(nm),(name),(birthdate)])

#Checks if an actor is already in person using nm    
def checkForActorDupes(nm):
    curs.execute('select * from person where nm = %s', nm)
    curs.fetchone()
    if curs.fetchone() != None:
        return True
    else:
        return False

#Checks if movie is in movie based on title
def checkForMovieDupes(tt):
    curs.execute('select * from movie where title = %s', tt)
    curs.fetchone()
    if curs.fetchone() != None:
        return True
    else:
        return False
#inserts a movie into movie
def insertMovie(tt,title,year):
    curs.execute('insert into movie values(%s,%s,%s, NULL, 120)',[(tt),(title),(year)])

#checks if a credit needs to be added and adds it if necessary
def checkAndInsertCredit(tt,nm):
    curs.execute('select * from credit where tt = %s and nm = %s', (tt,nm))
    curs.fetchone()
    if curs.fetchone() == None:        
        curs.execute('insert into credit values (%s,%s)',(tt,nm))
    

if __name__ == '__main__':
    print 'Content-type: text/html\n'
    scriptname = sys.argv[0]
    form_data = cgi.FieldStorage()
    if form_data.has_key('username'):
        print ("<p>Hello %s; it's nice to meet you." %
               form_data['username'])
    else:
        print file_contents('hw5page.html').format(script=scriptname)


