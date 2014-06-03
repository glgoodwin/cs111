#!/usr/local/bin/python2.7

#import cgi
#import cgitb; cgitb.enable()
import MySQLdb
import hw5connect
import wendy_dsn

!/usr/local/bin/python2.7

import cgi
import cgitb; cgitb.enable()
import os
import MySQLdb
from cgi_utils_sda import file_contents
from ggoodwin_dsn import dsn 
import hw5connect



#========================================
# Functions that make the page functional
def insertActor(nm, name, birthdate):
#    curs = conn.cursor(MySQLdb.cursors.DictCursor)
 #   data1 = (nm,)
  #  data2 = (name,)
   # data3 = (birthdate,)
    curs.execute('insert into person values ( %s, %s, %s)', nm, name, birthdate)
    print "successfully inserted"
    
    


def main():
    dsn = ggoodwin_dsn.py
    dsn['database'] = 'wmdb'
    conn = hw5connect.connect(dsn)
    return get
    
    

# functions to control insertion of actors from webpage
if __name__ == '__main__':
    print 'Content-type: text/html\n'
    scriptname = sys.argv[0]
    form_data = cgi.FieldStorage()
    if form_data.has_key('username'):
        print ("<p>Hello %s; it's nice to meet you." %
               form_data['username'])
    else:
        print file_contents('hw5page.html').format(script=scriptname)


#=====================================================

def insertActor(nm, name, birthdate):


def main():
    conn = hw5connect.connect(dsn)
    

# functions to control insertion of actors from webpage
if __name__ == '__main__':
    print 'Content-type: text/html\n'
    scriptname = sys.argv[0]
    form_data = cgi.FieldStorage()
    if form_data.has_key('username'):
        print ("<p>Hello %s; it's nice to meet you." %
               form_data['username'])
    else:
        print file_contents('name-form.html').format(script=scriptname)
