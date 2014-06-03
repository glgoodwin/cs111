
(***************************************************************
 SIMPREX adds the simprec construct to Sigmex = Bindex + sigma
 ****************************************************************)

module Simprex = struct

  open Sexp
  open FunUtils
  open ListUtils

  exception SyntaxError of string
  exception Unbound of string list 

  (************************************************************
   Abstract Syntax
   ************************************************************)

  type var = string

  type pgm = Pgm of string list * exp (* param names, body *)

  and exp =
      Lit of int (* integer literal with value *)
    | Var of var (* variable reference *)
    | BinApp of binop * exp * exp (* primitive application with rator, rands *)
    | Bind of var * exp * exp (* bind name to value of defn in body *)
    | Sigma of var * exp * exp * exp (* name * lo * hi * body *)
    | Simprec of exp * var * var * exp * exp 
       (* zero-exp * n-var * ans-var * comb-exp * arg-exp *)

  and binop = 
    | Add | Sub | Mul | Div | Rem (* binary arithmetic ops *)


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
    | BinApp(_,r1,r2) -> freeVarsExps [r1;r2]
    | Bind(name,defn,body) -> 
        S.union (freeVarsExp defn)
                     (S.diff (freeVarsExp body)
                                  (S.singleton name))
    | Sigma(name,lo,hi,body) -> 
        S.union (S.diff (freeVarsExp body)
                        (S.singleton name))
                (S.union (freeVarsExp lo)
                         (freeVarsExp hi))
    (* add a clause here to handle simprec *)
    | Simprec (zero, num, ans, combine, arg) -> 
	S.union (S. diff (freeVarsExp combine) 
		         (S.union (S.singleton num) 
		                   (S.singleton ans)))
	        (S.union (freeVarsExp zero) (freeVarsExp arg))
	
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
   ************************************************************)

  (* val subst : exp -> exp Env.env -> exp *)
  let rec subst exp env = 
    match exp with 
      Lit i -> exp
    | Var v -> (match Env.lookup v env with 
	          Some e -> e
                | None -> exp)
    | BinApp(op,r1,r2) -> BinApp(op, subst r1 env, subst r2 env)
    | Bind(name,defn,body) -> 
        (* Take the simple approach of renaming every name. 
           With more work, could avoid renaming unless absolutely necessary. *)
	let name' = StringUtils.fresh name in 
          Bind(name', subst defn env, subst (rename1 name name' body) env)
	  (* note: could be cleverer and do a single substitution/renaming *)
    | Sigma(name,lo,hi,body) -> 
	let name' = StringUtils.fresh name in 
          Sigma(name', subst lo env, subst hi env, subst (rename1 name name' body) env)
    (* add a clause here to handle simprec *)
    | Simprec(zero, num, ans, combine, arg) ->
	let num' = StringUtils.fresh num and ans' = StringUtils.fresh ans in
	Simprec(subst zero env, num', ans', subst (rename1 ans ans' (rename1 num num' combine)) env, subst arg env)

  (* val subst1 : var -> exp -> exp -> exp *)
  (* subst1 <exp> <var> <exp'> substitutes <exp> for <var> in <exp'> *)
  and subst1 newexp name exp = subst exp (Env.make [name] [newexp])

  (* val substAll : string list -> exp list -> exp -> exp *)
  (* substAll <exps> <vars> <exp'> substitutes <exps> for <vars> in <exp'> *)
  and substAll newexps names exp = subst exp (Env.make names newexps)

  (* val rename1 : var -> var -> exp -> exp *)
  (* rename1 <oldName> <newName> <exp> renames <oldName> to <newName> in <exp> *)
  and rename1 oldname newname exp = subst1 (Var newname) oldname exp 

  (* val renameAll : string list -> var list -> exp -> exp *)
  (* rename <oldNames> <newNames> <exp> renames <oldNames> to <newNames> in <exp> *)
  and renameAll oldnames newnames exp = 
    substAll (map (fun s -> Var s) newnames) oldnames exp 

  (************************************************************
   Parsing from S-Expressions 
   ************************************************************)

  (* val sexpToPgm : Sexp.sexp -> pgm *)
  let rec sexpToPgm sexp = 
    match sexp with 
      Seq [Sym "simprex"; Seq formals; body] -> 
            Pgm(map symToString formals, sexpToExp body)
    (* treat Bindex and Intex programs as Simprex programs *)
    | Seq [Sym "bindex"; Seq formals; body] -> 
            Pgm(map symToString formals, sexpToExp body)
    | Seq [Sym "intex"; Int n; body] -> 
            Pgm(map 
                  (fun i -> "$" ^ (string_of_int i))
                  (ListUtils.range 1 n),
                sexpToExp body)

    | _ -> raise (SyntaxError ("invalid Simprex program: " ^ (sexpToString sexp)))

  (* val symToString : Sexp.sexp -> string *)
  and symToString sexp =
    match sexp with 
      Sym s -> s
    | _ -> raise (SyntaxError ("symToString: not a string -- " ^ (sexpToString sexp)))

  (* val sexpToExp : Sexp.sexp -> exp *)
  and sexpToExp sexp = 
    match sexp with 
      Int i -> Lit i
    | Sym s -> Var s
    (* Translate Intex arg references ($ n) as $n *)
    | Seq [Sym "$"; Int(n)] -> Var ("$" ^ (string_of_int n))
    | Seq [Sym p; rand1x; rand2x] -> 
        BinApp(stringToBinop p, sexpToExp rand1x, sexpToExp rand2x)
    | Seq [Sym "bind"; Sym name; defnx; bodyx] -> 
        Bind (name, sexpToExp defnx, sexpToExp bodyx)
    (* comment this out when you do Problem 2 part d *)
    (* | Seq [Sym "sigma"; Sym name; lox; hix; bodyx] -> 
        Sigma (name, sexpToExp lox, sexpToExp hix, sexpToExp bodyx) *)
    (* comment this in and flesh out when you do Problem 2 part d *)


    (* We are making the assumption that the lo expression evaluates to the integer 1*)
    | Seq [Sym "sigma"; Sym name; lox; hix; bodyx] -> 
       let loVar = StringUtils.fresh "lo"  
       and hiVar = StringUtils.fresh "hi"
       and loDecVar = StringUtils.fresh "lodec" 
       and ansVar = StringUtils.fresh "ans" in 
       Bind(loVar, sexpToExp lox, 
            Bind(hiVar, sexpToExp hix, 
                 Bind(loDecVar, BinApp(Sub, Var loVar, Lit 1), 
                      Simprec(Lit 0, 
                              name,
                              ansVar, 
                              BinApp(Add, sexpToExp bodyx, Var ansVar),
			      sexpToExp hix))))
    (* Add a clause here to handle simprec *)
    | Seq [Sym "simprec"; zero; Seq [Sym num; Sym ans; combine]; arg] -> 
	Simprec (sexpToExp zero, num, ans, sexpToExp combine, sexpToExp arg)

    | _ ->  raise (SyntaxError ("invalid Simprex expression: "  
                                ^ (sexpToString sexp)))

  (* val stringToBinop : string -> binop *)
  and stringToBinop s = 
    match s with 
    | "+" -> Add
    | "-" -> Sub
    | "*" -> Mul
    | "/" -> Div
    | "%" -> Rem
    |  _ -> raise (SyntaxError ("invalid Simprex primop: " ^ s))

  (* val stringToExp : string -> exp *)
  and stringToExp s = sexpToExp (stringToSexp s) 

  (* val stringToPgm : string -> pgm *)
  and stringToPgm s = sexpToPgm (stringToSexp s)

  (************************************************************
   Unparsing to S-Expressions
   ************************************************************)

  (* val pgmToSexp : pgm -> Sexp.sexp *)
  let rec pgmToSexp p = 
    match p with 
      Pgm (fmls, e) -> 
        Seq [Sym "sigmex"; Seq(map (fun s -> Sym s) fmls); expToSexp e]

  (* val expToSexp : exp -> Sexp.sexp *)
  and expToSexp e = 
    match e with 
      Lit i -> Int i
    | Var s -> Sym s
    | BinApp (rator, rand1, rand2) -> 
        Seq [Sym (binopToString rator); expToSexp rand1; expToSexp rand2]
    | Bind(n,d,b) -> Seq [Sym "bind"; Sym n; expToSexp d; expToSexp b]
    | Sigma(name,lo,hi,body) -> 
        Seq [Sym "sigma"; Sym name; expToSexp lo; expToSexp hi; expToSexp body]
    (* add a clause here to handle simprec *) 
    | Simprec (zero, num, ans, combine, arg) -> 
	Seq [Sym "simprec"; expToSexp zero; Seq [Sym num; Sym ans; expToSexp combine]; expToSexp arg]
  (* val binopToString : binop -> string *)
  and binopToString p = 
    match p with 
    | Add -> "+"
    | Sub -> "-" 
    | Mul -> "*" 
    | Div -> "/" 
    | Rem -> "%" 

  (* val expToString : exp -> string *)
  and expToString s = sexpToString (expToSexp s)

  (* val pgmToString : pgm -> string *)
  and pgmToString s = sexpToString (pgmToSexp s)

  (************************************************************
   Simple Testing
   ************************************************************)

  and freeVarsPgmString str = setToList (freeVarsPgm (stringToPgm str))
  and freeVarsExpString str = setToList (freeVarsExp (stringToExp str))
  and freeVarsPgmFile str = 
    setToList (freeVarsPgm (stringToPgm (File.fileToString str)))
  and freeVarsExpFile str = 
    setToList (freeVarsExp (stringToExp (File.fileToString str)))

  and substString expString envString = 
    StringUtils.println
      (expToString
        (subst (stringToExp expString)
               (stringToExpEnv envString)))

  and stringToExpEnv str =
    let bindings = 
      match stringToSexp str with 
        Seq binds -> map (fun bind -> 
                                 match bind with 
                                   Seq [Sym name; sexp] -> 
                                     (name, sexpToExp sexp) 
                                 | _ -> raise (Failure ("wrong format for binding"
							 ^ (sexpToString bind))))
                               binds
      |	_ -> raise (Failure ("wrong format for bindings" ^ str)) in 
    let (names,exps) = ListUtils.unzip bindings in 
    Env.make names exps 

end 




