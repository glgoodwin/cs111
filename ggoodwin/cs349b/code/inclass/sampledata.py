#!/usr/bin/python
#Python sample code written by Scott Anderson

# Simple strings

x = 'spam, '
y = 'eggs, '

# lists.  Mutable, so you can add as many cheeses as you can think of...

cheeses = [ 'swiss', 'gruyere', 'cheddar', 'stilton', 'roquefort', 'brie' ]

# tuples.  Immutable, so you can't change these

troupe = ('Cleese', 'Palin', 'Idle', 'Chapman', 'Gilliam', 'Jones')

# hashes

#Tyler changed the variable name to uni to reflect the fact that Cambridge and Oxford are UK universities not colleges
uni = { 'Cleese': 'Cambridge',
            'Chapman' : 'Cambridge',
            'Palin': 'Oxford',
            'Jones': 'Oxford',
            'Idle': 'Cambridge',
            'Gilliam': 'Occidental' }


            
sports=['football','tennis','ice hockey','lacrosse', 'field hockey','basketball','baseball','swimming']
inorout=['out','both','in','out','out','in','out','in']

sportsloc=zip(sports,inorout) #built-in function combines list element-wise

