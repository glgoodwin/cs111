#!/usr/local/bin/python2.7
# PARSES THE FORM and invokes the other python file
import cgi
import cgitb; cgitb.enable()
import os

import hw5py2
from cgi_utils_sda import file_contents,print_headers


def main():
##      = {'month': ''}
##     fillers = {}
##     fillers['scriptname'] = os.environ['SCRIPT_NAME'] if 'SCRIPT_NAME' in os.environ else ''

    fs = cgi.FieldStorage()
    if 'input' in fs:
        actornm = int(fs.getvalue('actornm'))
        actorname = fs.getvalue('actorname')
        actorbday = fs.getvalue('actorbday')
        print 'You have inserted data'
    else:
        print "Please enter data"
##         actornm = int(fs.getvalue('actornm'))
##         actorname = fs.getvalue('actorname')
##         actorbday = fs.getvalue('actorbday')
        
       ##  fillers['monthname'] = month_names[month]
##         fillers['actorlist'] = actorNamesByMonth.main(month)
##         fillers['monthmenu'] = generate_month_menu(month)
##     else:
##         fillers['monthname'] = 'None'
##         fillers['actorlist'] = ''
##         fillers['monthmenu'] = generate_month_menu(0)

    #tmpl = file_contents('actorNamesByMonth.tmpl')
    #fillers['footer'] = file_contents('footer.part')
    #return tmpl.format(**)
    file = file_contents('hw5py2.py')
    #return file.insertActors(actornm,actorname,actorbday)

if __name__ == '__main__':
   # print_headers(None)
   # print main()
   main()
   
