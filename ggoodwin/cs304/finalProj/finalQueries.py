# Gabe and Alex
# CS304 Final Project
# This file contains all of the functions that we will need for interacting with our database. Having them in a file will make it easier to implement them into our forms later on.


# connect to the database. Will be changed to the team account in the future. 
conn = MySQLdb.connect(host='localhost',user='ggoodwin',passwd='eevaengi',db='ggoodwin_db')
curs = conn.cursor()

#!/usr/local/bin/python2.7
# The search function has the user specify the table and column.  We are going to allow the user to search by a variety of different fields, which they will choose through a drop down menu next to the search text field.
# It will be coded into the web page which table and column is associated with a given search by field.






def search(table,col, string):
    query =  "select * from "+table+"  where "+col+ " like concat('%',"+string+",'%')"
    curs.execute(query)


# The insert function takes a table and the values to insert.  Becuase different tables take different values, the *values will put whatever arguments the user inputs into a list.  The function then iterates over the list to get the values and inserts them into the given table.  This will require that users enter a NULL value if they are not entering data into every field. However, this should be fairly easy to encode into our form

def insert(table, *args):
    query = "insert into "+table+" values"+ str(args)
    curs.execute(query)


#The delete function is similar to the previous two functions.

def delete(table,col,string):
    query = "delete from  "+table+" where "+col+ " = "+string
    curs.execute(query)


#lets us see if a given id is already in the database
def checkForID(table, col, id):
    query = "select * from "+table+" where "+col+ " = "+string
    curs.execute(query)
    curs.fetchone()
    if curs.fetchone() != None:
        return True
    else:
        return False
