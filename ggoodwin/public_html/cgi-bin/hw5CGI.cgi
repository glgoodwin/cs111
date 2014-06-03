#!/usr/local/bin/python2.7

#Gabe Goodwin 11/2/2012
# PARSES THE FORM and invokes the other python file to check/insert into db
import cgi
import cgitb; cgitb.enable()
import os

import hw5py2
from hw5connect import file_contents


def main():


    fs = cgi.FieldStorage()
    ## Makes sure the required fields are entered
    if 'actornm' in fs  and 'actorname' in fs:
        actornm = int(fs.getvalue('actornm')) 
	##Checks if actor is already in the database
	isDupe = hw5py2.checkForActorDupes(actornm)
	if isDupe == False:      
		actorname = fs.getvalue('actorname')
		if 'actorbday' in fs:
			actorbday = fs.getvalue('actorbday')
		else:
			actorbday = ('0000-00-00')

  		hw5py2.insertActor(actornm,actorname,actorbday)
    		print "Content-type: text/html\n\n"
    		print 'actor was added'
    

    	else:
    		print "Content-type: text/html\n\n"
		print "actor already in database"


		
	##Only adds a movie if there is an actor, goes through all 5 rows
	for i in range(5):
		#If there is a title, checks if it is in the db 
		if 'movie'+str(i)+'_title' in fs:
			movietitle = fs.getvalue('movie'+str(i)+'_title')
			isDupe = hw5py2.checkForMovieDupes(movietitle)
			#Checks for other fields 
			if 'movie'+str(i)+'_tt' in fs and 'movie'+str(i)+'_year' in fs:
				moviett = int(fs.getvalue('movie'+str(i)+'_tt'))
				if isDupe == False: 
					movieyear = fs.getvalue('movie'+str(i)+'_year')
	  				hw5py2.insertMovie(moviett,movietitle,movieyear)
					hw5py2.checkAndInsertCredit(moviett,actornm)
	    				print '  --movie'+str(i)+' was added'
				#adds actor to movie credit, if movie in db and values there
	    			else:
					hw5py2.checkAndInsertCredit(moviett,actornm)
					print '  --movie '+str(i)+' already in database'

				#asks for tt, year if those values are empty
			else:
				print'  --movie '+str(i)+' Not Added, please add missing fields'

		#If there is not title in that row
		else:
			i += 1
	       
    #IF Actor nm or actor name is missing
    else:
	print "required field missing"
    
    #Show the form again    
    print file_contents('hw5page.html')

if __name__ == '__main__':
   # print_headers(None)
   # print main()
   main()
   
