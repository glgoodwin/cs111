#!/bin/sh

OC=ocamlopt

echo
echo Lots of warnings about non exhautive pattern matchings
echo will be displayed.  Don\'t worry about that.

$OC -c fp.mli
ocamlyacc fp_parser.mly
ocamllex fp_lexer.mll
$OC -c fp_parser.mli
$OC -c fp_lexer.ml
$OC -c fp_parser.ml
$OC -c fp.ml
$OC -c fpmain.ml
$OC int_misc.cmx nat.cmx big_int.cmx arith_flags.cmx string_misc.cmx ratio.cmx num.cmx fp_parser.cmx fp_lexer.cmx fp.cmx fpmain.cmx -o fp

strip fp