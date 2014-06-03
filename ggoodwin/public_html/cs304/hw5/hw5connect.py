#!/usr/local/bin/python2.7

#Python file for hw5 functionality
import MySQLdb
import cgi
import cgitb; cgitc.enable()

Error = MySQLdb.Error

def file_contents(filename):
    file = open(filename,'r')
    contents = file.read()
    file.close()
    return contents

# Login credentials
## dsn = {
##     'username': 'ggoodwin',
##     'password': 'secret',
##     'hostname': 'localhost',
##     'database': 'wmdb'
##    }

the_db_connection = False;


## Method to connect to wmdb
def connect(dsn):
    global the_db_connection
    if not the_db_connection:
        try:
            the_db_connection = MySQLdb.connect(
                host = dsn['hostname'],
                user=dsn['username'],
                passwd=dsn['password'];
                db=dsn['database'])

            the_db_connection.autocommit(True)
        except MySQLdb.Error, e:
            print ("Couldn't connect to data base. MySQL error %d: %s" %
                   (e.args[0], e.args[1]))
        return the_db_connection

######################
if __name__ == '__main__':
    print 'starting test code'
    import sys
    dsnfile = sys.argv[1]
    module = __import__(dsnfile)
    dsn = module.dsn
    dsn['database']='ggoodwin_db'
    c = connect(dsn)
    print 'successfully connected'
    curs = c.cursor(MySQLdb.cursors.DictCursor) # results as Dictionaries
    curs.execute('select user() as user, database() as db')
    row = curs.fetchone()
    print 'connected to %s as %s' % (row['db'],row['user'])
    
