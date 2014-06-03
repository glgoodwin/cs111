#!/usr/local/bin/python2.7

# PARSES THE FORM and invokes the other python file to check/insert into db
import cgi
import cgitb; cgitb.enable()
import os

import finalQueries
from hw5connect import file_contents


def main():


    fs = cgi.FieldStorage()
    ## Makes sure the required fields are entered
    if 'oppid' in fs:
        new_opp_id = fs.getvalue('oppid') 
	##Checks if oppid is already in the database
	isDupe = finalQueries.checkForID('opps','opp_id',new_opp_id)
	if isDupe == False: 
		NULL = "NULL"
		if 'orgid' in fs:     
			orgid = fs.getvalue('orgid')
		else:
			 orgid = NULL
		if 'individ' in fs:     
			individ = fs.getvalue('individ')
		else:
			 individ = NULL
		if 'title' in fs:     
			title = fs.getvalue('title')
		else:
			 title = NULL
		if 'oppdesc' in fs:     
			oppdesc = fs.getvalue('oppdesc')
		else:
			oppdesc = NULL
		if 'dateadd' in fs:     
			dateadd = fs.getvalue('dateadd')
		else: 
			dateadd = NULL

  		finalQueries.insert('opps',new_opp_id,orgid,individ,title,oppdesc,dateadd)
    		print "Content-type: text/html\n\n"
    		print 'The listing was added'
    

    	else:
    		print "Content-type: text/html\n\n"
		print "That ID is already taken, please choose a new one"


		

	       
    #If oppid is missing
    else:
	print "required field missing"
    
    #Show the form again    
    print file_contents('oppsInsert.html')

if __name__ == '__main__':
	print 'Content-type: text/html\n'
   	main()
   