#!/usr/local/bin/python2.7

# yes, this script has bugs in it
# it's for a class exercise

import cgi

def main():
    print 'executing main'
    if 1 < 2: 
        print '1 less than 2'
    
if __name__ == '__main__':
	print 'Content-type: text/html\n'
	main()
