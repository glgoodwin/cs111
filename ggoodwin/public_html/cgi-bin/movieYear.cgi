#!/usr/local/python2.7

import cgi
import cgitb;cgitb.enable() 
import os

def main():
	form_data = cgi.FieldStorage()
	fillers = {}
	fillers['scriptname'] = os.environ['SCRIPT_NAME'] if 'SCRIPT_NAME' in os.environ else "

	if form_data.has_key('year'):
	fillers['yearname'] = year
	fillers['movieList'] = 
		