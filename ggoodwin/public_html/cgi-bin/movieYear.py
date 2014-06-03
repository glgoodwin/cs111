#!/usr/local/bin/python2.7

#import cgi
#import cgitb; cgitb.enable()
import MySQLdb
import dbconn
import wendy_dsn

def getMovies(conn,selected_year):
    curs = conn.cursor(MySQLdb.cursors.DictCursor) # results as Dictionaries
    # notice the trick to get a tuple of one
    data = (selected_year,)
    curs.execute('select name,birthdate from person where year(realease_date) = %s',
                 data)
    lines = []
    while True:
        row = curs.fetchone()
        if row == None:
            return "\n".join(lines)
        lines.append('<li>{name} born on {birthdate}'.format(**row))
