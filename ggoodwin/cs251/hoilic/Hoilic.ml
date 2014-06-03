(***************************************************************
 In HOILIC (Higher Order Imperative Language with Implicit Cells), 
 all variables are mutable (as in Java & Scheme) and there is 
 a variable assigment operator (<-). We take the Scheme-like approach 
 of defining BINDREC as syntactic sugar in HOILIC. 

 HOILIC does *not* support the explicit cells of HOILIC, but
 these are easy to add. 
 ****************************************************************)

module Hoilic = struct

  open Sexp
  open List
  open FunUtils
  open ListUtils
  open StringUtils

  exception SyntaxError of string
  exception Unbound of string list 
  exception EvalError of string

  (************************************************************
   Abstract Syntax
   ************************************************************)

  type var = string

  type pgm = Pgm of var list * exp (* param names, body *) (* Same as HOFL *)

  and exp = 
      Lit of valu (* value literals *)
    | Var of var (* variable reference *)
    | PrimApp of primop * exp list (* primitive application with rator, rands *)
    | If of exp * exp * exp (* conditional with test, then, else *)
    | Abs of var * exp (* function abstraction *)
    | App of exp * exp (* function application *)
     (* HOILIC expressions include variable assignments, but BINDREC is now sugar *)
    | Assign of var * exp (* variable assignment (new in HOILIC) *)

  and valu = 
      Int of int 
    | Bool of bool
    | Char of char
    | String of string
    | Symbol of string
    | List of valu list
    | Fun of var * exp * valu ref Env.env (* In HOILIC, vars bound to implicit cells *)

  and primop = Primop of var * (valu list -> valu)

  let primopName (Primop(name,_)) = name

  let primopFunction (Primop(_,fcn)) = fcn

  (* val valuToSexp : valu -> sexp *)
  let rec valuToSexp valu = 
    match valu with 
      Int i -> Sexp.Int i
    | Bool b -> Sym (if b then "#t" else "#f")
    | Char c -> Sexp.Chr c
    | String s -> Sexp.Str s
    | Symbol s -> Seq [Sym "sym"; Sym s]
    | List [] -> Sym "#e" (* special case *)
    | List xs -> Seq (Sym "list" :: (map valuToSexp xs))
    | Fun _ -> Sym "<fun>"

  (* val valuToString : valu -> string *)
  let valuToString valu = sexpToString (valuToSexp valu)

  (* val valusToString : valu list -> string *)
  and valusToString valus = sexpToString (Seq (map valuToSexp valus))

  (* val displayValue: valu -> unit *)
  (* Displays a value. Does not display double quotes around a top-level
     string value, but does display them around any nested string values
     (e.g., string values in a list). *)
  let displayValu v = 
    match v with 
      String s -> StringUtils.print s (* special case for top-level strings *)
    | _ -> StringUtils.print (valuToString v)

  (************************************************************
   Dynamic Type Checking
   ************************************************************)

  let checkZeroArgs f = 
    fun vs -> 
      match vs with 
        [] -> f ()
      | _ -> raise (EvalError ("Expected zero arguments but got: " ^ (valusToString vs)))

  let checkOneArg check f = 
    fun vs -> 
      match vs with 
        [v] -> check v f
      | _ -> raise (EvalError ("Expected one argument but got: " ^ (valusToString vs)))

  let checkTwoArgs (check1,check2) f = 
    fun vs -> 
      match vs with 
        [v1;v2] -> check1 v1 (fun x1 -> check2 v2 (fun x2 -> f x1 x2))
      | _ -> raise (EvalError ("Expected two arguments but got: " ^ (valusToString vs)))

  let checkInt v f = 
    match v with 
      Int i -> f i
    | _  -> raise (EvalError ("Expected an integer but got: " ^ (valuToString v)))

  let checkBool v f = 
    match v with 
      Bool b -> f b
    | _  -> raise (EvalError ("Expected a boolean but got: " ^ (valuToString v)))

  let checkChar v f = 
    match v with 
      Char c -> f c
    | _  -> raise (EvalError ("Expected a char but got: " ^ (valuToString v)))

  let checkString v f = 
    match v with 
      String s -> f s
    | _  -> raise (EvalError ("Expected a string but got: " ^ (valuToString v)))

  let checkSymbol v f = 
    match v with 
      Symbol s -> f s
    | _  -> raise (EvalError ("Expected a symbol but got: " ^ (valuToString v)))

  let checkList v f = 
    match v with 
      List vs -> f vs
    | _  -> raise (EvalError ("Expected a list but got: " ^ (valuToString v)))

  let checkAny v f = f v (* always succeeds *)

  let arithop f = checkTwoArgs (checkInt,checkInt) (fun i1 i2 -> Int(f i1 i2))
  let relop f = checkTwoArgs (checkInt,checkInt) (fun i1 i2 -> Bool(f i1 i2))
  let logop f = checkTwoArgs (checkBool,checkBool) (fun b1 b2 -> Bool(f b1 b2))
  let pred f = checkOneArg checkAny (fun v -> Bool(f v))

  let primops = [ 
    (* Arithmetic ops *)
    Primop("+", arithop (+));
    Primop("-", arithop (-));
    Primop("*", arithop ( * ));
    Primop("/", arithop (fun x y -> 
                           if (y = 0) then 
                             raise (EvalError ("Division by 0: " 
					       ^ (string_of_int x)))
                           else x/y)); 
    Primop("%", arithop (fun x y -> 
                           if (y = 0) then 
                             raise (EvalError ("Remainder by 0: " 
					       ^ (string_of_int x)))
                           else x mod y));

    (* Relational ops *)
    Primop("<", relop (<)); 
    Primop("<=", relop (<=)); 
    Primop("=", relop (=)); 
    Primop("!=", relop (<>));
    Primop(">=", relop (>=)); 
    Primop(">", relop (>)); 

    (* Logical ops *)
    Primop("bool=", logop (fun b1 b2 -> b1=b2));
    Primop("not", checkOneArg checkBool (fun b -> Bool(not b)));
    Primop("and", logop (&&)); (* *not* short-circuit! *)
    Primop("or", logop (||)); (* *not* short-circuit! *)

    (* Char ops *)
    Primop("char=", checkTwoArgs (checkChar, checkChar) (fun c1 c2 -> Bool(c1=c2)));
    Primop("char<", checkTwoArgs (checkChar, checkChar) (fun c1 c2 -> Bool(c1<c2)));
    Primop("int->char", checkOneArg checkInt (fun i -> Char(char_of_int i)));
    Primop("char->int", checkOneArg checkChar (fun c -> Int(int_of_char c)));
    Primop("explode", checkOneArg checkString 
	     (fun s -> List (let rec loop i chars = 
                               if i < 0 then chars
                               else loop (i-1) ((Char (String.get s i)) :: chars)
                              in loop ((String.length s)-1) [])));
    Primop("implode", checkOneArg checkList 
	     (fun chars -> String (let rec recur cs = 
                                     match cs with 
                                       [] -> ""
                                     | ((Char c)::cs') -> (String.make 1 c) ^ (recur cs')
				     | _ -> raise (EvalError "Non-char in implode")
	                           in recur chars)));

    (* String ops *)
    Primop("str=", checkTwoArgs (checkString,checkString) (fun s1 s2 -> Bool(s1=s2)));
    Primop("str<", checkTwoArgs (checkString,checkString) (fun s1 s2 -> Bool(s1<s2)));
    Primop("strlen", checkOneArg checkString (fun s -> Int(String.length s)));
    Primop("str+", checkTwoArgs (checkString,checkString) (fun s1 s2 -> String(s1^s2)));
    Primop("toString", checkOneArg checkAny (fun v -> String(valuToString v)));

    (* Symbol ops *)
    Primop("sym=", checkTwoArgs (checkSymbol,checkSymbol) (fun s1 s2 -> Bool(s1=s2)));
    Primop("sym->string", checkOneArg checkSymbol (fun s -> String s)); 
    Primop("string->sym", checkOneArg checkString (fun s -> Symbol s)); 

    (* List ops *)
    Primop("prep", checkTwoArgs (checkAny,checkList) (fun v vs -> List (v::vs)));
    Primop("head", checkOneArg checkList 
                    (fun vs -> 
		       match vs with 
			 [] -> raise (EvalError "Head of an empty list")
                       | (v::_) -> v));
    Primop("tail", checkOneArg checkList
	            (fun vs -> 
                      match vs with 
                        [] -> raise (EvalError "Tail of an empty list")
                      | (_::vs') -> List vs'));
    Primop("empty?", checkOneArg checkList (fun vs -> Bool(vs = []))); 
    Primop("empty", checkZeroArgs (fun () -> List []));
    Primop("nth", checkTwoArgs (checkInt,checkList) 
	     (fun i vs -> if (i < 1) || (i > (List.length vs)) 
	                  then raise (EvalError ("nth -- out-of-bounds index " ^ (string_of_int i)))
                          else List.nth vs (i-1)));

    (* Printing ops (carried over from HOILEC) *)
    Primop("print", checkOneArg checkAny (fun v -> (displayValu v; v)));
    Primop("println", checkOneArg checkAny (fun v -> (displayValu v; StringUtils.println ""; v)));

    (* Errors *)
    Primop("error", checkTwoArgs (checkString,checkAny)
                      (fun s x -> raise (EvalError ("Hoilic Error -- " ^ s ^ ":" ^ (valuToString x))))); 

    (* Predicates *)
    Primop("int?", pred (fun v -> match v with Int _ -> true | _ -> false));
    Primop("bool?", pred (fun v -> match v with Bool _ -> true | _ -> false));
    Primop("char?", pred (fun v -> match v with Char _ -> true | _ -> false));
    Primop("sym?", pred (fun v -> match v with Symbol _ -> true | _ -> false));
    Primop("string?", pred (fun v -> match v with String _ -> true | _ -> false));
    Primop("list?", pred (fun v -> match v with List _ -> true | _ -> false));
    Primop("fun?", pred (fun v -> match v with Fun _ -> true | _ -> false));

  ] 

  let primopEnv = Env.make (map (fun (Primop(name,_)) -> name) primops) primops

  let isPrimop s = match Env.lookup s primopEnv with Some _ -> true | None -> false

  let findPrimop s = Env.lookup s primopEnv 

  (************************************************************
   Free Variables 
   ************************************************************)

  module S = Set.Make(String) (* String Sets *)

  (* val listToSet : S.elt list -> S.t *)
  let listToSet strs = foldr S.add S.empty strs

  (* val setToList : S.t -> S.elt list *)
  let setToList set = S.elements set 

  (* val freeVarsPgm : pgm -> S.t *)
  (* Returns the free variables of a program *)
  let rec freeVarsPgm (Pgm(fmls,body)) = 
    S.diff (freeVarsExp body) (listToSet fmls)

  (* val freeVarsExp : exp -> S.t *)
  (* Returns the free variables of an expression *)
  (* direct version *)
  and freeVarsExp e = 
    match e with 
      Lit i -> S.empty
    | Var s -> S.singleton s
    | PrimApp(_,rands) -> freeVarsExps rands
    | If(tst,thn,els) -> freeVarsExps [tst;thn;els]
    | Abs(fml,body) -> S.diff (freeVarsExp body) (S.singleton fml)
    | App(rator,rand) -> freeVarsExps [rator;rand]
    | Assign(name,rhs) -> S.add name (freeVarsExp rhs)

  (* val freeVarsExps : exp list -> S.t *)
  (* Returns the free variables of a list of expressions *)
  (* direct version *)
  and freeVarsExps es = 
    foldr S.union S.empty (map freeVarsExp es)

  (* val varCheck : pgm -> unit *)
  and varCheck pgm = 
    let unbounds = freeVarsPgm pgm 
     in if S.is_empty unbounds then
          () (* OK *)
        else 
          raise (Unbound (S.elements unbounds))

  (************************************************************
   Substitution & Renaming

   Substitution does not make sense in HOILIC because meanings
   of variables can change over time!

   However, renaming still makes sense. 
   ************************************************************)

  (* val rename : exp -> var Env.env -> exp *)
  let rec rename exp env = 
    match exp with 
      Lit i -> exp
    | Var v -> (match Env.lookup v env with 
	          Some v' -> Var v'
                | None -> exp)
    | PrimApp(op,rands) -> PrimApp(op, map (flip rename env) rands)
    | If(tst,thn,els) -> If(rename tst env, rename thn env, rename els env)
    | Abs(fml,body) -> 
	let fml' = fresh fml in 
	  Abs(fml', rename (rename1 fml fml' body) env)
    | App(rator,rand) -> App(rename rator env, rename rand env)
    | Assign(name,rhs) -> 
        Assign((match Env.lookup name env with 
	           Some name' -> name'
                 | None -> name), rename rhs env)

  (* val rename1 : var -> var -> exp -> exp *)
  and rename1 oldname newname exp = rename exp (Env.make [oldname] [newname])

  (* val rename_all : string list -> var list -> exp -> exp *)
  and rename_all oldnames newnames exp = rename exp (Env.make oldnames newnames)

  (************************************************************
   Desugaring (val desugar : sexp -> sexp)
   ************************************************************)

  let rec desugar sexp = 
    let sexp' = desugarRules sexp in 
      if sexp' = sexp then (* efficient in OCAML if they're pointer equivalent *)
        match sexp with 
        | Seq sexps -> Seq (map desugar sexps)
        | _ -> sexp
      else desugar sexp'

  and desugarRules sexp = 
    match sexp with 

    (* Handle Intex arg refs as var refs *)
      Seq [Sym "$"; Sexp.Int i] -> Sym ("$" ^ (string_of_int i)) 

    (* Note: the following desugarings for && and || allow 
       non-boolean expressions for second argument! *)
    | Seq [Sym "&&"; x; y] -> Seq [Sym "if"; x; y; Sym "#f"]
    | Seq [Sym "||"; x; y] -> Seq [Sym "if"; x; Sym "#t"; y]

    (* Scheme-style cond *)
    | Seq [Sym "cond"; Seq [Sym "else"; defaultx]] -> defaultx
    | Seq (Sym "cond" :: Seq [testx; bodyx] :: clausexs) -> 
        Seq [Sym "if"; testx; bodyx; Seq(Sym "cond" :: clausexs)]

    (* list desugarings *)
    | Seq [Sym "list"] -> Sym "#e"
    | Seq (Sym "list" :: headx :: tailsx) -> 
	Seq [Sym "prep"; headx; Seq (Sym "list" :: tailsx)]

    (* Scheme-like quotation *)
    | Seq [Sym "quote"; Sexp.Int i] -> Sexp.Int i (* These are sexps, not Valex valus! *)
    | Seq [Sym "quote"; Chr i] -> Chr i 
    | Seq [Sym "quote"; Str i] -> Str i 
    (* Quoted special symbols denote themselves *)
    | Seq [Sym "quote"; Sym "#t"] -> Sym "#t"
    | Seq [Sym "quote"; Sym "#f"] -> Sym "#f"
    | Seq [Sym "quote"; Sym "#e"] -> Sym "#e"
    (* Other quoted symbols s denote (sym s) *)
    | Seq [Sym "quote"; Sym s] -> Seq [Sym "sym"; Sym s]
    (* (quote (x1 ... xn)) -> (list (quote x1) ... (quote xn)) *)
    | Seq [Sym "quote"; Seq xs] -> 
        Seq (Sym "list" :: (map (fun x -> Seq[Sym "quote"; x]) xs))

    (* All forms of bind desugar into other HOFL forms *)
    | Seq [Sym "bind"; Sym name; defnx; bodyx] -> 
	Seq [Seq[Sym "abs"; Sym name; bodyx]; defnx]

    | Seq [Sym "bindseq"; Seq[]; bodyx] -> bodyx
    | Seq [Sym "bindseq"; Seq ((Seq[Sym name;defnx])::bindingxs); bodyx]
        -> Seq[Sym "bind"; Sym name; defnx; Seq[Sym "bindseq"; Seq bindingxs; bodyx]]

    (* In HOFL/HOILEC/HOILIC, *can* handle bindpar as an s-expression desugaring *)
    | Seq [Sym "bindpar"; Seq bindingxs; bodyx] -> 
	let (names, defnxs) = parseBindings bindingxs in
	Seq (Seq[Sym "fun"; Seq(map (fun n -> Sym n)  names); bodyx] :: defnxs)

    (* BINDREC is syntactic sugar in HOILIC! *)
    (* (bindrec ((I1 E1) ...) E_body) 
          ~~>
       (bindpar ((I1 (sym *black-hole* )) ...)
         (seq (<- I1 E1) ... E_body))
     *)
    | Seq [Sym "bindrec"; Seq bindingxs; bodyx] -> 
	let (names, defnxs) = parseBindings bindingxs in
	Seq[Sym "bindpar"; 
	     Seq(map (fun n -> Seq[Sym n; Seq[Sym "sym"; Sym "*black-hole*"]])  names);
	     Seq(Sym "seq" :: 
		 ((map2 (fun name defnx -> Seq[Sym "<-"; Sym name; defnx]) 
		        names 
		        defnxs) 
		  @ [bodyx]))]

    (*
    (* LET and LET* are synonyms for BINDPAR and BINDSEQ *)
    | Seq [Sym "let"; bindingxs; bodyx] -> Seq [Sym "bindpar"; bindingxs; bodyx] 
    | Seq [Sym "let*"; bindingxs; bodyx] -> Seq [Sym "bindseq"; bindingxs; bodyx] *)

    (* Use the "fun" syntax for multiple argument functions (just curried functions) *)
    | Seq [Sym "fun"; Seq []; bodyx] -> Seq [Sym "abs"; Sym (fresh "ignore"); bodyx]
    | Seq [Sym "fun"; Seq [Sym fml]; bodyx] -> Seq [Sym "abs"; Sym fml; bodyx]
    | Seq [Sym "fun"; Seq (Sym fml :: formals) ; bodyx] -> 
	Seq [Sym "abs"; Sym fml; Seq [Sym "fun"; Seq formals ; bodyx]]

    (* Syntactic sugar for SEQ in HOILEC is carried over to HOILIC *)
    |  Seq [Sym "seq"] -> Sym "#f" 
    |  Seq (Sym "seq":: exps) -> 
	let names = map (fun _ -> fresh "seq") exps in 
	Seq [Sym "bindseq"; 
	     Seq(ListUtils.map2 (fun name exp -> Seq [Sym name; exp]) names exps);
	     Sym (ListUtils.last names)]

    (* Syntactic sugar for WHILE in HOILEC is carried over to HOILIC *)
    | Seq [Sym "while"; testx; bodyx] -> 
	let loopName = fresh "loop" in
	Seq [Sym "bindrec"; 
	      Seq[Seq[Sym loopName; 
		       Seq[Sym "fun"; Seq[]; 
			    Seq[Sym "if"; 
				 testx; 
				 Seq[Sym "seq"; bodyx; Seq[Sym loopName]];
				 Sym "#f"]]]];
	      Seq[Sym loopName]]

    (* Desugar multiple argument function calls into nested single-argument calls *)
    | Seq (sexp1 :: sexp2 :: sexp3 :: rest) when (not (isKeyword sexp1)) -> 
       Seq(Seq[sexp1; sexp2] :: sexp3 :: rest)

    (* Desugar nullary function call into application to false *)
    | Seq [sexp1] when (not (isKeyword sexp1)) -> Seq[sexp1; Sym "#f"]
    | _ -> sexp

  and isSpecial s = 
    List.mem s ["if"; "abs"; "<-"; "$"; "&&"; "||"; "cond"; "list"; "quote"; 
                 "bind"; "bindseq"; "bindpar"; "bindrec"; "fun"; "seq"; "while"]

  and isKeyword sexp = 
    match sexp with 
      Sym s -> isSpecial s || isPrimop s
    | _ -> false

  (************************************************************
   Parsing from S-Expressions 
   ************************************************************)

  (* val sexpToPgm : Sexp.sexp -> pgm *)
  and sexpToPgm sexp = 
    match sexp with 
      Seq ((Sym "hoilic" | Sym "hofl") :: Seq formals :: bodyx :: declxs) -> 
	Pgm(map symToString formals, 
	    sexpToExp(Seq[Sym "bindrec"; 
			   Seq(map (fun (name,defnx) -> Seq[Sym name;defnx])
				 (flatten (map declToBindings declxs)));
			   bodyx]))
    (* Handle Valex programs as well *)
    | Seq [Sym "valex"; Seq formals; bodyx] -> 
            Pgm(map symToString formals, sexpToExp bodyx)
    (* Handle Bindex programs as well *)
    | Seq [Sym "bindex"; Seq formals; bodyx] -> 
            Pgm(map symToString formals, sexpToExp bodyx)
    (* Handle Intex programs as well *)
    | Seq [Sym "intex"; Sexp.Int n; bodyx] -> 
            Pgm(map 
                  (fun i -> "$" ^ (string_of_int i))
                  (ListUtils.range 1 n),
                sexpToExp bodyx)
    | _ -> raise (SyntaxError ("invalid Hoilic program: " ^ (sexpToString sexp)))

  (* val declToBindings: Sexp.sexp -> string * Sexp.sexp *)
  (* Decls have the form (def I_name I_defn) or (load S_filename),
       where S_filename must be a literal string and the file
       named by the string must contain a sequence of defs. 
     Decls of the form (def (I_0 I_1 .. I_n) I_body) are treated
       as sugar for (def I_0 (fun (I_1 .. I_n) I_body))
     A decl of the form (def ...) denotes a list of a single binding.
     A decl of the form (load <filename>) denotes a list of all the bindings
       that can be recursively reached from <filename>.
    *)
  and declToBindings decl = 
    match desugarDecl decl with 
      Seq [Sym "def"; Sym name; defnx] -> [(name, defnx)]
    | Seq [Sym "load"; Str filename] -> 
        flatten (map declToBindings (Sexp.fileToSexps filename))
    | _ -> raise (SyntaxError ("ill-formed decl: " ^ (sexpToString decl)))

  and desugarDecl decl = 
    match decl with 
    | Seq [Sym "def"; Seq (Sym name :: formals); bodyx] -> 
	Seq [Sym "def"; Sym name; Seq [Sym "fun"; Seq formals; bodyx]]
    | _ -> decl

  (* val symToString : Sexp.sexp -> string *)
  and symToString sexp =
    match sexp with 
      Sym s -> s
    | _ -> raise (SyntaxError ("symToString: not a string -- " ^ (sexpToString sexp)))

  and sexpToExp sexp = sexpToExp' (desugar sexp)

  (* val sexpToExp : Sexp.sexp -> exp *)
  and sexpToExp' sexp = 
    match sexp with 
      Sexp.Int i -> Lit (Int i)
    | Sexp.Chr c -> Lit (Char c)
    | Sexp.Str s -> Lit (String s)
    (* Symbols beginning with # denote special values (not variables!) *)
    | Sym s when String.get s 0 = '#' -> Lit (stringToSpecialValue s)
    | Sym s -> Var s
    | Seq [Sym "sym"; Sym s] -> Lit (Symbol s)
    | Seq [Sym "if"; testx; thenx; elsex] -> 
        If(sexpToExp' testx, sexpToExp' thenx, sexpToExp' elsex)
    | Seq [Sym "abs"; Sym fml; bodyx] -> Abs(fml, sexpToExp' bodyx)
    | Seq [Sym "<-"; Sym name; rhsx] -> Assign(name, sexpToExp' rhsx)
    (* This clause must be last! *)
    | Seq (ratorx :: randxs) -> 
        (match (ratorx, randxs) with 
           (Sym p, _) when isPrimop p -> 
	     PrimApp(valOf (findPrimop p), map sexpToExp' randxs)
	 | (_, [randx]) -> App(sexpToExp' ratorx, sexpToExp' randx)
	 | _ -> raise (SyntaxError ("invalid Hoilic application: "
                                    ^ (sexpToString sexp)))
	  )
    | _ ->  raise (SyntaxError ("invalid Hoilic expression: "  
                                ^ (sexpToString sexp)))

  (* parse bindings of the form ((<name_1> <defnx_1>) ... (<name_n> <defnx_n>))
     into ([name_1;...;name_n], [defnx_1; ...; defnx_n]) *)
  and parseBindings bindingxs = 
    unzip (map (fun bindingx -> 
                 (match bindingx with 
		   Seq[Sym name; defnx] -> (name, defnx)
		 | _ -> raise (SyntaxError ("ill-formed Hoilic binding"
					    ^ (sexpToString bindingx)))))
             bindingxs)

  (* Strings beginning with # denote special values *)
  and stringToSpecialValue s =
    match s with 
    | "#t" -> Bool true   (* true and false are keywords *)
    | "#f" -> Bool false  (* for literals, not variables *)
    | "#e" -> List []     (* empty list literal *)
    | _ -> raise (SyntaxError ("Unrecognized special value: " ^ s))

  (* val stringToExp : string -> exp *)
  and stringToExp s = sexpToExp (stringToSexp s) (* Desugar when possible *)

  (* val stringToPgm : string -> pgm *)
  and stringToPgm s = sexpToPgm (stringToSexp s)

  (************************************************************
   Unparsing to S-Expressions
   ************************************************************)

  (* val pgmToSexp : pgm -> Sexp.sexp *)
  and pgmToSexp p = 
    match p with 
      Pgm (fmls, e) -> 
        Seq [Sym "hoilic"; Seq(map (fun s -> Sym s) fmls); expToSexp e]

  (* val expToSexp : exp -> Sexp.sexp *)
  and expToSexp e = 
    match e with 
      Lit v -> valuToSexp v 
    | Var s -> Sym s
    | PrimApp (rator, rands) -> 
        Seq ((Sym (primopName rator)) :: map expToSexp rands)
    | If(tst,thn,els) -> Seq [Sym "if"; expToSexp tst; expToSexp thn; expToSexp els]
    | Abs(fml,body) -> Seq [Sym "abs"; Sym fml; expToSexp body]
    | App(rator,rand) -> Seq [expToSexp rator; expToSexp rand]
    | Assign(name,rhs) -> Seq [Sym "<-"; Sym name; expToSexp rhs]

  (* val expToString : exp -> string *)
  and expToString s = sexpToString (expToSexp s)

  (* val pgmToString : pgm -> string *)
  and pgmToString s = sexpToString (pgmToSexp s)

end 




