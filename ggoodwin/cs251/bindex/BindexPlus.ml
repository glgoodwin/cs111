(***************************************************************
 BindexPlus adds sigma, bindseq, and bindpar to Bindex.
 It does not implement fold or uniquify.
 ****************************************************************)

module BindexPlus = struct

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
    | BinApp of binop * exp * exp (* binary operator application with rator, rands *)
    | Bind of var * exp * exp (* binding of name to value of defn in body *)
    | Bindpar of var list * exp list * exp (* parallel binding of names to defns in body *)
    | Bindseq of var list * exp list * exp (* sequential binding of names to defns in body *)
    | Sigma of var * exp * exp * exp (* name * lo * hi * body *)

  and binop = 
    | Add | Sub | Mul | Div | Rem (* binary arithmetic ops *)

  (************************************************************
   Free Variables (Replaces Static Arg Checking)
   ************************************************************)

  module S = Set.Make(String) (* String Sets *)

  (* val listToSet : S.elt list -> S.t *)
  let listToSet strs = foldr S.add S.empty strs

  (* val setToListList : S.t -> S.elt list *)
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
    | BinApp(_,r1,r2) -> S.union (freeVarsExp r1) (freeVarsExp r2)
    | Bind(name,defn,body) -> 
        S.union (freeVarsExp defn)
                (S.diff (freeVarsExp body)
                        (S.singleton name))
    | Bindpar(names,defns,body) -> 
        S.union (freeVarsExps defns)
                (S.diff (freeVarsExp body)
                        (listToSet names))
    | Bindseq(names,defns,body) -> 
        foldr2 (fun n d fvs -> 
	         S.union (freeVarsExp d)
                         (S.diff fvs (S.singleton n)))
               (freeVarsExp body)
               names
               defns

    | Sigma(name,lo,hi,body) -> 
        S.union (S.diff (freeVarsExp body)
		        (S.singleton name))
                (S.union (freeVarsExp lo)
                         (freeVarsExp hi))

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
          raise (Unbound (setToList unbounds))

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
          (* Bind(name', subst defn env, subst (rename1 name name' body) env) *)
	  (* note: could be cleverer and do a single substitution/renaming *)
          Bind(name', subst defn env, subst body (Env.bind name (Var name') env))

    (* Extensions *)
    | Bindpar(names,defns,body) -> 
	let names' = map StringUtils.fresh names in
          Bindpar(names', 
		  map ((flip subst) env) defns, 
		  subst (renameAll names names' body) env)
                  (* or subst body (bindAll names names' env) *)
    | Bindseq(names,defns,body) -> 
      (* 
        (* This following version with foldl2 works, 
           but the version with explicit recursion is probably clearer! *)
	let names' = map StringUtils.fresh names in
        let revEnvs = foldl2 [env]
                             (fun es n n'-> (Env.bind n (Var n') (List.hd es)) :: es)
	                     names
                             names' in
            Bindseq(names',
                    map2 subst defns (rev (List.tl revEnvs)),
                    subst body (List.hd revEnvs))
	 *)
	let (names',defns',body') = substBindseq (zip (names, defns)) body env
	    in Bindseq(names',defns',body')
    | Sigma(name,lo,hi,body) -> 
	let name' = StringUtils.fresh name in 
          Sigma(name', subst lo env, subst hi env, subst (rename1 name name' body) env)

and substBindseq bindings body env =
  match bindings with 
    [] -> ([], [], subst body env)
  | ((name,defn):: bindings') -> 
      let name' = StringUtils.fresh name in 
      let (names',defns',body') = 
        substBindseq bindings' body (Env.bind name (Var name') env)
	in (name'::names', (subst defn env)::defns', body')

  (* val subst1 : exp -> var -> exp -> exp *)
  (* subst1 <exp> <var> <exp'> substitutes <exp> for <var> in <exp'> *)
  and subst1 newexp name exp = subst exp (Env.make [name] [newexp])

  (* val substAll: exp list -> var list -> exp -> exp *)
  (* subst <exps> <vars> <exp'> substitutes <exps> for <vars> in <exp'> *)
  and substAll newexps names exp = subst exp (Env.make names newexps)

  (* val rename1 : var -> var -> exp -> exp *)
  (* rename1 <oldName> <newName> <exp> renames <oldName> to <newName> in <exp> *)
  and rename1 oldname newname exp = subst1 (Var newname) oldname exp 

  (* val renameAll : var list -> var list -> exp -> exp *)
  (* rename <oldNames> <newNames> <exp> renames <oldNames> to <newNames> in <exp> *)
  and renameAll oldnames newnames exp = 
    substAll (List.map (fun s -> Var s) newnames) oldnames exp 

  (************************************************************
   Parsing from S-Expressions 
   ************************************************************)

  (* val sexpToPgm : Sexp.sexp -> pgm *)
  let rec sexpToPgm sexp = 
    match sexp with 
      Seq [Sym "bindex"; Seq formals; body] -> 
            Pgm(List.map symToString formals, sexpToExp body)
    | Seq [Sym "intex"; Int n; body] -> 
            Pgm(List.map 
                  (fun i -> "$" ^ (string_of_int i))
                  (ListUtils.range 1 n),
                sexpToExp body)
    | _ -> raise (SyntaxError ("invalid Bindex program: " ^ (sexpToString sexp)))

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
    | Seq [Sym "bind"; Sym name; defnx; bodyx] -> 
        Bind (name, sexpToExp defnx, sexpToExp bodyx)
    | Seq [Sym "bindpar"; bindingsx; bodyx] -> 
        let (names,defns) = parseBindings bindingsx in 
  	  Bindpar(names, defns, sexpToExp bodyx)
    | Seq [Sym "bindseq"; bindingsx; bodyx] -> 
        let (names,defns) = parseBindings bindingsx in 
  	  Bindseq(names, defns, sexpToExp bodyx)
    | Seq [Sym "sigma"; Sym name; lox; hix; bodyx] -> 
        Sigma (name, sexpToExp lox, sexpToExp hix, sexpToExp bodyx)
    (* Careful: this must come last, or else conflicts with bindseq and bindpar! *)
    | Seq [Sym p; rand1x; rand2x] -> 
        BinApp(stringToBinop p, sexpToExp rand1x, sexpToExp rand2x)
    | _ ->  raise (SyntaxError ("invalid Bindex expression: "  
                                ^ (sexpToString sexp)))

  (* val parseBinding : Sexp.sexp -> (string, exp) *)
  and parseBinding sexp = 
    match sexp with 
      Seq [Sym name; defn] -> (name, sexpToExp defn)
    | _ -> raise (SyntaxError ("parseBinding -- invalid binding form: " 
			       ^ (sexpToString sexp)))

  (* val parseBindings : Sexp.sexp -> (strings, exps) *)
  and parseBindings sexp = 
     match sexp with 
       Seq bindingsx -> unzip (map parseBinding bindingsx)
     | _ -> raise (SyntaxError ("parseBindings -- invalid bindings list: " 
			       ^ (sexpToString sexp)))

  (* val stringToBinop : string -> binop *)
  and stringToBinop s = 
    match s with 
    | "+" -> Add
    | "-" -> Sub
    | "*" -> Mul
    | "/" -> Div
    | "%" -> Rem
    |  _ -> raise (SyntaxError ("invalid Bindex primop: " ^ s))

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
        Seq [Sym "bindex"; Seq(List.map (fun s -> Sym s) fmls); expToSexp e]

  (* val expToSexp : exp -> Sexp.sexp *)
  and expToSexp e = 
    match e with 
      Lit i -> Int i
    | Var s -> Sym s
    | BinApp (rator, rand1, rand2) -> 
        Seq [Sym (binopToString rator); expToSexp rand1; expToSexp rand2]
    | Bind(n,d,b) -> Seq [Sym "bind"; Sym n; expToSexp d; expToSexp b]
    | Bindpar(ns,ds,b) -> Seq [Sym "bindpar"; 
                               Seq (map2 (fun n d -> Seq[Sym n; expToSexp d]) ns ds);
                               expToSexp b]
    | Bindseq(ns,ds,b) -> Seq [Sym "bindseq"; 
                               Seq (map2 (fun n d -> Seq[Sym n; expToSexp d]) ns ds);
                               expToSexp b]
    | Sigma(name,lo,hi,body) -> 
        Seq [Sym "sigma"; Sym name; expToSexp lo; expToSexp hi; expToSexp body]

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

end 




