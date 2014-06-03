#!/bin/env python2.6

import cgi

def main():
    print 'executing main'
    if 1 < 2:
        print '1 less than 2'

if __name__ == '__main__':
    print 'Content-type: text/html\n'
    print 'hello Globe!'
    main()
