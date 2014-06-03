#Python file for hw5 functionality

def file_contents(filename):
    file = open(filename,'r')
    contents = file.read()
    file.close()
    return contents

# Login credentials
dsn = {
    'username': 'ggoodwin',
    'password': 'secret',
    'hostname': 'localhost',
    'database': 'wmdb'
   }

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
# functions to control insertion of actors from webpage
