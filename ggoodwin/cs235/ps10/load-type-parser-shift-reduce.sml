use "../utils/ListUtils.sml";
use "Token.sml";
use "AST.sml";
use "Types.lex.sml";
use "Scanner.sml";
use "TypeParserShiftReduce.sml";
use "TypeParserShiftReduceTester.sml";
Control.Print.printLength := 1000; 
Control.Print.printDepth := 1000; 
Control.Print.stringDepth := 1000; 
open TypeParserShiftReduce;
open TypeParserShiftReduceTester;

