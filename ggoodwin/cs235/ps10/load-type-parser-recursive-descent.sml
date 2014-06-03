use "Token.sml";
use "AST.sml";
use "Types.lex.sml";
use "Scanner.sml";
use "TypeParserRecursiveDescent.sml";
use "TypeParserRecursiveDescentTester.sml";
Control.Print.printLength := 1000; 
Control.Print.printDepth := 1000; 
Control.Print.stringDepth := 1000; 
open TypeParserRecursiveDescent;
open TypeParserRecursiveDescentTester;

