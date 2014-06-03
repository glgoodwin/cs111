# examples for oc-FP, v0.21

Def eq0 = eq o [ id, ~0 ]
Def sub1 =  - o [ id, ~1 ]

# the factorial function, classic recursive definition
Def fact = eq0 -> ~1 ;  * o [ id, fact o sub1 ]

# an iterative definition of the factorial
Def ifact2 =  eq0 o 1s -> 2s; ifact2 o [ sub1 o 1s , * ]
Def ifact = ifact2 o [ id , ~1 ]

# inner product
Def IP = /+ o @* o transpose

# matrix multiply
Def MM = @@IP o @distl o distr o [ 1s, transpose o 2s ]

# iota - generate list of n first integers
Def iota2 = eq0 o 1s -> 2s ; iota2 o [ sub1 o 1s, apndl ]
Def iota = iota2 o [ id, ~<> ]

# an imperative version
Def iiota = 2s o while (not o eq0 o 1s) [ sub1 o 1s, apndl ] o [ id, ~<> ]

# a non repetitive definition of factorial
Def cfact = /* o iota

# an imperative def of fact
Def impfact = 2s o while (not o eq0 o 1s) [ sub1 o 1s, * ] o [ id, ~1 ]
