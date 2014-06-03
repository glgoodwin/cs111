(***************************************************************
 Valex adds the following to Bindex: 

 A version of Valex that performs desugarings (of some, not all)
 constructs "all at once" directly in sexpToExp. 
 ****************************************************************)


(***************************************************************
 Valex adds the following to Bindex: 
 * boolean, character, pair, and list values 
 * conditionals 
 * a library of primitives
 * generalized primitive application with dynamic type checking
 * desugaring 
 ****************************************************************)

module Valex = struct

  open Sexp
  open List
  open FunUtils
  open ListUtils

  exception SyntaxError of string
  exception Unbound of string list 
  exception EvalError of string

  (************************************************************
   Abstract Syntax
   ************************************************************)

  type var = string

  type pgm = Pgm of string list * exp (* param names, body *)

  and exp =
      Lit of valu (* integer, boolean, and character literals *)
    | Var of var (* variable reference *)
    | PrimApp of primop * exp list (* primitive application with rator, rands *)
    | Bind of var * exp * exp (* bind name to value of defn in body *)
    | If of exp * exp * exp (* conditional with test, then, else *)

  and valu = 
      Int of int 
    | Bool of bool
    | Char of char
    | String of string
    | Symbol of string
    | List of valu list

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

  (* val valuToString : valu -> string *)
  let valuToString valu = sexpToString (valuToSexp valu)

  (* val valusToString : valu list -> string *)
  and valusToString valus = sexpToString (Seq (map valuToSexp valus))

  (* val valusToString : valu list -> string *)
  (* and valusToString vs = "{" ^ (String.concat "," (map valuToString vs)) ^ "}" *)
  (* and valusToString vs = "{" ^ (String.concat "," (map valuToString vs)) ^ "}" *)

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

    (* Symbol op *)
    Primop("sym=", checkTwoArgs (checkSymbol,checkSymbol) (fun s1 s2 -> Bool(s1=s2)));

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

    (* Predicates *)
    Primop("int?", pred (fun v -> match v with Int _ -> true | _ -> false));
    Primop("bool?", pred (fun v -> match v with Bool _ -> true | _ -> false));
    Primop("char?", pred (fun v -> match v with Char _ -> true | _ -> false));
    Primop("sym?", pred (fun v -> match v with Symbol _ -> true | _ -> false));
    Primop("string?", pred (fun v -> match v with String _ -> true | _ -> false));
    Primop("list?", pred (fun v -> match v with List _ -> true | _ -> false));

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
  and freeVarsExp e = 
    match e with 
      Lit i -> S.empty
    | Var s -> S.singleton s
    | PrimApp(_,rands) -> freeVarsExps rands
    | Bind(name,defn,body) -> 
        S.union (freeVarsExp defn)
                     (S.diff (freeVarsExp body)
                                  (S.singleton name))
    | If(tst,thn,els) -> freeVarsExps [tst;thn;els]

  (* val freeVarsExps : exp list -> S.t *)
  (* Returns the free variables of a list of expressions *)
  (* direct version *)
  and freeVarsExps es = 
    fold_right S.union (map freeVarsExp es) S.empty

  (* val varCheck : pgm -> unit *)
  and varCheck pgm = 
    let unbounds = freeVarsPgm pgm 
     in if S.is_empty unbounds then
          () (* OK *)
        else 
          raise (Unbound (S.elements unbounds))

  (************************************************************
   Substitution & Renaming
   ************************************************************)

  (* val subst : exp -> exp Env.env -> exp *)
  let rec subst exp env = 
    match exp with 
      Lit i -> exp
    | Var v -> (match Env.lookup v env with 
	          Some e -> e
                | None -> exp)
    | PrimApp(op,rands) -> PrimApp(op, map (flip subst env) rands)
    | Bind(name,defn,body) -> 
        (* Take the simple approach of renaming every name. 
           With more work, could avoid renaming unless absolutely necessary. *)
	let name' = StringUtils.fresh name in 
          Bind(name', subst defn env, subst (rename1 name name' body) env)
	  (* note: could be cleverer and do a single substitution/renaming *)
    | If(tst,thn,els) -> If(subst tst env, subst thn env, subst els env)

  (* val subst1 : exp -> var -> exp -> exp *)
  (* subst1 <exp> <var> <exp'> substitutes <exp> for <var> in <exp'> *)
  and subst1 newexp name exp = subst exp (Env.make [name] [newexp])

  (* val substAll: exp list -> var list -> exp -> exp *)
  (* substAll <exps> <vars> <exp'> substitutes <exps> for <vars> in <exp'> *)
  and substAll newexps names exp = subst exp (Env.make names newexps)

  (* val rename1 : var -> var -> exp -> exp *)
  (* rename1 <oldName> <newName> <exp> renames <oldName> to <newName> in <exp> *)
  and rename1 oldname newname exp = subst1 (Var newname) oldname exp 

  (* val renameAll : var list -> var list -> exp -> exp *)
  (* rename <oldNames> <newNames> <exp> renames <oldNames> to <newNames> in <exp> *)
  and renameAll oldnames newnames exp = 
    substAll (map (fun s -> Var s) newnames) oldnames exp 

  (************************************************************
   Desugaring (val desugar : sexp -> sexp)
   ************************************************************)

  let rec desugar sexp = 
    let sexp' = desugarRules sexp in 
      if sexp' = sexp then (* efficient in OCAML if they're pointer equivalent *)
        match sexp with 
          Seq sexps -> Seq (map desugar sexps)
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
    | Seq [Sym "cond"; Seq [Sym "else"; default]] -> default
    | Seq (Sym "cond" :: Seq [test; result] :: clauses) -> 
        Seq [Sym "if"; test; result; Seq(Sym "cond" :: clauses)]

    | Seq [Sym "bindseq"; Seq[]; body] -> body
    | Seq [Sym "bindseq"; Seq ((Seq[Sym name;defn])::bindings); body]
        -> Seq[Sym "bind"; Sym name; defn; Seq[Sym "bindseq"; Seq bindings; body]]
    (* Note: can't handle bindpar here, because it requires renaming *)
    (* See sexpToExp' below for handling bindpar *)

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

    | _ -> sexp

  (* For testing *)
  let desugarString str = 
    StringUtils.println (sexpToString (desugar (stringToSexp str)))

  (************************************************************
   Parsing from S-Expressions 
   ************************************************************)

  (* val sexpToPgm : Sexp.sexp -> pgm *)
  let rec sexpToPgm sexp = 
    match sexp with 
      Seq [Sym "valex"; Seq formals; body] -> 
            Pgm(map symToString formals, sexpToExp body)
    (* Handle Bindex programs as well *)
    | Seq [Sym "bindex"; Seq formals; body] -> 
            Pgm(map symToString formals, sexpToExp body)
    (* Handle Intex programs as well *)
    | Seq [Sym "intex"; Sexp.Int n; body] -> 
            Pgm(map 
                  (fun i -> "$" ^ (string_of_int i))
                  (ListUtils.range 1 n),
                sexpToExp body)
    | _ -> raise (SyntaxError ("invalid Valex program: " ^ (sexpToString sexp)))

  (* val symToString : Sexp.sexp -> string *)
  and symToString sexp =
    match sexp with 
      Sym s -> s
    | _ -> raise (SyntaxError ("symToString: not a string -- " ^ (sexpToString sexp)))

  (* val sexpToExp : Sexp.sexp -> exp *)
  and sexpToExp sexp = 
    match sexp with 
      Sexp.Int i -> Lit (Int i)
    | Sexp.Chr c -> Lit (Char c)
    | Sexp.Str s -> Lit (String s)
    (* Symbols beginning with # denote special values (not variables!) *)
    | Sym s when String.get s 0 = '#' -> Lit (stringToSpecialValue s)
    | Sym s -> Var s
    | Seq [Sym "sym"; Sym s] -> Lit (Symbol s)
    | Seq [Sym "bind"; Sym name; defnx; bodyx] -> 
        Bind (name, sexpToExp defnx, sexpToExp bodyx)
    | Seq [Sym "if"; tstx; thnx; elsx] -> 
        If(sexpToExp tstx, sexpToExp thnx, sexpToExp elsx)

     (* "All-at-once" desugarings *)
    | Seq [Sym "$"; Sexp.Int i] -> Var ("$" ^ (string_of_int i))
    | Seq [Sym "&&"; rand1x; rand2x] -> 
	If(sexpToExp rand1x, sexpToExp rand2x, Lit (Bool false))
    | Seq [Sym "||"; rand1x; rand2x] -> 
	If(sexpToExp rand1x, Lit (Bool true), sexpToExp rand2x)
    | Seq (Sym "cond" :: clausexs) -> desugarCond clausexs
    | Seq [Sym "bindseq"; Seq []; bodyx] -> sexpToExp bodyx
    | Seq [Sym "bindseq"; Seq ((Seq[Sym name; defnx])::bindingxs); body] -> 
        sexpToExp (Seq[Sym "bind"; Sym name; defnx; 
                       Seq[Sym "bindseq"; Seq bindingxs; body]])
    (*
    | Seq [Sym "bindseq"; Seq bindingxs; bodyx] -> 
	let (names, defns) = parseBindings bindingxs in
	desugarBindseq names defns (sexpToExp bodyx)
     *)
    | Seq [Sym "bindpar"; Seq bindingxs; bodyx] -> 
	let (names, defns) = parseBindings bindingxs in
	let names' = map StringUtils.fresh names in 
	desugarBindseq names' defns (renameAll names names' (sexpToExp bodyx))
    | Seq (Sym "list" :: eltxs) -> desugarList eltxs
    | Seq [Sym "quote"; sexp] -> Lit (desugarQuote sexp)

    (* This clause must be last! *)
    | Seq (Sym p :: randsx) when isPrimop p -> 
	PrimApp(valOf (findPrimop p), map sexpToExp randsx)
    | _ ->  raise (SyntaxError ("invalid Valex expression: "  
                                ^ (sexpToString sexp)))

  (* Strings beginning with # denote special values *)
  and stringToSpecialValue s =
    match s with 
    | "#t" -> Bool true   (* true and false are keywords *)
    | "#f" -> Bool false  (* for literals, not variables *)
    | "#e" -> List []     (* empty list literal *)
    | _ -> raise (SyntaxError ("Unrecognized special value: " ^ s))

  (* parse bindings of the form ((<namex_1> <defnx_1>) ... (<namex_n> <defnx_n>))
     into ([name_1;...;name_n], [defn_1; ...; defn_n]) *)
  and parseBindings bindingxs = 
    unzip (map (fun bindingx -> 
                 (match bindingx with 
		   Seq[Sym name; defn] -> (name, sexpToExp defn)
		 | _ -> raise (SyntaxError ("ill-formed bindpar binding"
					    ^ (sexpToString bindingx)))))
             bindingxs)

  and desugarCond clausexs =   (* clausesx is a list of sexp clauses *)
    match clausexs with 
      [Seq[Sym "else"; defaultx]] -> sexpToExp defaultx
    | (Seq[testx; resultx])::restx -> 
        If(sexpToExp testx, sexpToExp resultx, desugarCond restx)
    | _ -> raise (SyntaxError ("invalid cond clauses: " 
			       ^ (sexpToString (Seq clausexs))))

  (* defns and body have already been parsed *)
  and desugarBindseq names defns body = 
    match (names,defns) with 
      ([],[]) -> body
    | (name::names', defn::defns') -> 
        Bind(name, defn, desugarBindseq names' defns' body)
    | _ -> raise (SyntaxError "shouldn't happen")

  and desugarBindseq2 names defns body = 
    foldr2 (fun name defn rest -> Bind(name, defn, rest)) body names defns

  and desugarList eltxs = 
    match eltxs with 
      [] -> Lit(List[])
    | eltx::eltxs' -> PrimApp(valOf(findPrimop "prep"), [sexpToExp eltx; desugarList eltxs'])

  (* turns an sexp directly into a literal value *)
  and desugarQuote sexp = 
    match sexp with 
      Sexp.Int i -> Int i
    | Sexp.Chr s -> Char s
    | Sexp.Str s -> String s
    | Sexp.Sym "#t" -> Bool true
    | Sexp.Sym "#f" -> Bool false
    | Sexp.Sym "#e" -> List []
    | Sexp.Sym s -> Symbol s
    | Seq eltxs -> List (map desugarQuote eltxs)
    | _ -> raise (SyntaxError ("invalid quoted expression" ^ (sexpToString sexp)))

  (* val stringToExp : string -> exp *)
  and stringToExp s = sexpToExp (stringToSexp s) (* Desugar when possible *)

  (* val stringToPgm : string -> pgm *)
  and stringToPgm s = sexpToPgm (stringToSexp s)

  (************************************************************
   Unparsing to S-Expressions
   ************************************************************)

  (* val pgmToSexp : pgm -> Sexp.sexp *)
  let rec pgmToSexp p = 
    match p with 
      Pgm (fmls, e) -> 
        Seq [Sym "valex"; Seq(map (fun s -> Sym s) fmls); expToSexp e]

  (* val expToSexp : exp -> Sexp.sexp *)
  and expToSexp e = 
    match e with 
      Lit v -> valuToSexp v
    | Var s -> Sym s
    | PrimApp (rator, rands) -> 
        Seq (Sym (primopName rator) :: map expToSexp rands)
    | Bind(n,d,b) -> Seq [Sym "bind"; Sym n; expToSexp d; expToSexp b]
    | If(tst,thn,els) -> Seq [Sym "if"; expToSexp tst; expToSexp thn; expToSexp els]

  (* val expToString : exp -> string *)
  and expToString s = sexpToString (expToSexp s)

  (* val pgmToString : pgm -> string *)
  and pgmToString s = sexpToString (pgmToSexp s)

end 




