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
    if 'orgid' in fs:
        new_org_id = fs.getvalue('orgid') 
	##Checks if oppid is already in the database
	isDupe = finalQueries.checkForID('orgs','org_id',new_org_id)
	if isDupe == False: 
		NULL = "NULL"
		if 'orgname' in fs:     
			orgname = fs.getvalue('orgname')
		else:
			 orgname = NULL
		if 'address' in fs:     
			address = fs.getvalue('address')
		else:
			 address = NULL
		if 'zip' in fs:     
			zip = fs.getvalue('zip')
		else:
			 zip = NULL
		if 'url' in fs:     
			url = fs.getvalue('url')
		else:
			url = NULL
		if 'orgphone' in fs:     
			orgphone = fs.getvalue('orgphone')
		else: 
			orgphone = NULL
		if 'orgbio' in fs:     
			orgbio = fs.getvalue('orgbio')
		else: 
			orgbio = NULL

  		finalQueries.insert('orgs',new_org_id,orgname,address,zip,url,orgphone,orgbio)
    		print "Content-type: text/html\n\n"
    		print 'The listing was added'
    

    	else:
    		print "Content-type: text/html\n\n"
		print "That ID is already taken, please choose a new one"


		

	       
    #If oppid is missing
    else:
	print "required field missing"
    
    #Show the form again    
    print file_contents('orgInsert.html')

if __name__ == '__main__':
	print 'Content-type: text/html\n'
   	main()