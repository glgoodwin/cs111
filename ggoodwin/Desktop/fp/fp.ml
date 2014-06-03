(* FP: functional programming system as defined by [Backus1978]   *)
(*                                                                *)
(*                        october 2003 christophe.deleuze@free.fr *)


(* TODO: better error handling (?)
  a trace function showing steps of evaluation,
  pretty-printing of matrix
*)

(* maybe unify Prim and User (allow User def to temp override prim one)? *)

let version = "oc-fp version 0.21"
let banner = version ^ "\n(c) 2003 Christophe Deleuze\n\n"

open Printf

(* ----------------------- abstract grammar  ----------------------------- *)

type fct = 
  | Prim of string			(* primitive functions  *)
  | Sel of int | RSel of int            (* two other primitives *)
  | User of string			(* user defined fcts    *)

	(* functional forms *)

  | ApplyToAll of fct
  | Insert of fct 
  | Construction of fct list
  | Composition of fct * fct
  | Condition of fct * fct * fct
  | Constant of expr 
  | Bu of fct * expr  
  | While of fct * fct

(* expressions *)
and expr =
  | Bottom
  | Void
  | T | F
  | Var of string
  | Int of Num.num
  | Seq  of expr list
  | App of fct * expr
;;

type cmd = 
  | Def of string * fct
  | Undef of string
  | Show of string
  | Exp of expr
  | Quit
  | Load of string
  | Save of string
  | None
;;

(* ------------------------------- printers ---------------------------- *)


let rec string_of_fct2 f bc = match f with
  (* bc means put braces around composition *)
  | Prim s -> s
  | Sel n  -> (string_of_int n) ^ "s"
  | RSel n -> (string_of_int n) ^ "r"
  | User s -> s

  | Insert     fct -> sprintf "/%s" (string_of_fct2 fct true)
  | ApplyToAll fct -> sprintf "@%s" (string_of_fct2 fct true)
  | Construction l -> 
      (List.fold_left (fun s f -> s ^ 
	(if s="[ " then "" else ", ") ^ (string_of_fct f)) "[ " l) ^ " ]"
  | Composition (f1,f2) -> (if bc then "( " else "") ^
      (string_of_fct f1) ^ " o " ^ (string_of_fct f2) ^ 
      (if bc then " )" else "")
  | Condition(p,f,g) -> (string_of_fct p) ^ " -> " ^ (string_of_fct f)
      ^ " ; " ^ (string_of_fct g)
  | Constant e -> "~" ^ (string_of_expr e)
  | Bu(f,e) -> "bu " ^ (string_of_fct2 f true) ^ " " ^ (string_of_expr e)
  | While(p,f) -> sprintf "while %s %s" (string_of_fct2 p true)
	(string_of_fct2 f true)

and string_of_expr = function
  | T   -> "T" | F   -> "F" | Bottom -> "."
  | Int n  -> Num.string_of_num n
  | Var   v  -> v
  | Seq(l) -> 
      (List.fold_left (fun s e -> s ^ 
	(if s="<" then "" else", ") ^ (string_of_expr e)) "<" l) ^">" 
  | App(fct,e)    -> (string_of_fct fct) ^  ":" ^ (string_of_expr e)
and
    string_of_fct f = string_of_fct2 f false;;

let print_expr e = print_string (string_of_expr e); print_newline();;
let print_fct  f = print_string (string_of_fct f); print_newline();;
(*#install_printer print_expr;;
#install_printer print_fct;;*)


(* ---------------------------- user definitions ----------------------- *)

let defs = Hashtbl.create 50;;		(* table of defs *)

let add_user_def name fct = Hashtbl.add  defs name fct
let get_user_def name     = Hashtbl.find defs name
let del_user_def name     = Hashtbl.remove defs name


(* ------------------------------- evaluator -------------------------- *)

(* it probably could be cleaned up a bit *)


(* most filters are not exhaustive

   they could be, by returning 'Bottom' on a last '_' filter, but we
   prefer to let the filtering fail, catch the failure in the eval_fct
   function that turns this into a 'Eval_bottom' exception, catched in
   the top function.

   this may be an arguable choice...
 *)


exception Eval_bottom of expr		(* bad arguments        *)
exception Eval_error of expr		(* unknown function     *)
exception Eval_bug of string		(* should never happen! *)


(* apply fct to each elt of the list *)
let applyToAll fct = function
  | Seq l -> Seq (List.map (fun e -> App (fct, e)) l)
  | _ -> failwith "Invalid arg in applyToAll";;

(* insert fct between all elts of the list *)
(* ZZZ: /* : <> crashes *)
let insert fct = function
  | Seq l -> List.fold_left
	(fun expr e -> if expr = Void then e else App (fct, Seq([e; expr])))
	Void
	(List.rev l)
  | _ -> failwith "Invalid arg in insert";;

(* transpose a matrix *)
let transpose args = 
  let rec trans acc args = match args with
  | (Seq []) :: r ->  List.rev acc
  | l  -> 
    let h = List.map (fun (Seq il) -> List.hd il) l
    and t = List.map (fun (Seq il) -> Seq (List.tl il)) l
    in
    trans ((Seq h) :: acc) t
  in
  match args with
  | (Seq l) -> Seq (trans [] l)
  | _ -> failwith "Invalid arg in Transpose";;


let match_seq e f = 
  try match e with (Seq l) -> f l
  with | _ -> failwith "Needed a Sequence!"


(* arithmetic operations, with simplification *)
let eval_arith fct e1 e2 =
  match fct,e1,e2 with
  | "+", Int a, Int b -> Int (Num.add_num a b)
  | "-", Int a, Int b -> Int (Num.sub_num a b)
  | "*", Int a, Int b -> Int (Num.mult_num a b)
  | "div", Int a, Int b -> Int (Num.div_num a b)
  | "÷", Int a, Int b -> Int (Num.div_num a b)
	
  | "+", Int (Num.Int 0), e       -> e
  | "+", e,       Int (Num.Int 0) -> e
  | "-", e,       Int (Num.Int 0) -> e
  | "*", Int (Num.Int 1), e       -> e
  | "*", e,       Int (Num.Int 1) -> e
  | "*", Int (Num.Int 0), e       -> Int (Num.Int 0)
  | "*", e,       Int (Num.Int 0) -> Int (Num.Int 0)
  | "div", e,     Int (Num.Int 1) -> e

	(* no possible simplification *)
  | _ , Var a, Var b -> App(Prim fct, (Seq [e1; e2]))

	(* bad args *)
  | _ -> raise (Eval_bottom (App(Prim fct, (Seq [e1;e2]))))
;;


let rec eval = function
    
  | Void | T | F as s -> s
  | Int n   -> Int n
  | Var v     -> Var v
  | Seq l     -> Seq (List.map eval l)
  | App(fct,e)-> eval_fct fct (eval e)
      
and eval_fct fct e = try
  match fct with 
    
    (* functional forms *)
    
  | Insert fct      -> eval (insert fct e)
  | ApplyToAll fct  -> eval (applyToAll fct e)
  | Composition(f1,f2) -> eval_fct f1 (eval_fct f2 e)
  | Construction l -> Seq (List.map (fun o -> eval_fct o e) l)
  | Constant e -> eval e
  | Condition(p,f,g) -> if (eval_fct p e) = T then
      eval_fct f e else eval_fct g e
  | Bu(f,o) -> eval_fct f (Seq [ o; e ])
  | While(p,f) -> let re = ref e in 
    while (eval_fct p !re) = T do re := eval_fct f !re done; eval !re
	
	
  | Prim s -> eval_prim s e
	
  | Sel  n -> match_seq e (fun l -> List.nth l (n-1))
  | RSel n -> match_seq e (fun l -> List.nth l ((List.length l) - n))
	
  | User s -> eval_fct 
	(try get_user_def s with Not_found -> raise (Eval_error (App(fct,e))))
	e
	
with
| Eval_error e -> raise (Eval_error e)
| _ -> raise (Eval_bottom (App(fct,e)));


and eval_prim s e = match s with

  (* primary functions, except Sel and RSel *)

| "+" | "-" | "*" | "div" | "÷"
  -> (match e with Seq ( [e1;e2]) -> eval_arith s (eval e1) (eval e2))
      
| "transpose" -> transpose e
| "tl"    -> match_seq e (fun l -> Seq (List.tl l))
| "tlr"   -> match_seq e (fun l -> Seq (List.rev (List.tl (List.rev l))))
| "distl" -> match_seq e (fun [ y; (Seq z) ]
                          -> (Seq (List.map (fun zi -> (Seq [ y; zi ])) z)))
| "distr" -> match_seq e (fun [ (Seq y); z ] 
                          -> (Seq (List.map (fun yi -> (Seq [ yi; z ])) y)))
| "apndl"   -> match_seq e (fun [ y ; (Seq z) ] -> (Seq  (y :: z)))
| "apndr"   -> match_seq e (fun [ (Seq y) ; z ] -> (Seq  (y @ [ z ])))
| "rotl"    -> (match e with Seq [] -> Seq [] | Seq (h::t) -> Seq(t @ [h] ))
| "rotr"    -> (match e with Seq [] -> Seq []
  | Seq l -> let rl = List.rev l in
    Seq(List.rev ((List.tl rl) @ [List.hd rl])))
| "reverse" -> match_seq e (fun l -> Seq (List.rev l))
| "length"  -> match_seq e (fun l -> Int (Num.Int (List.length l)))
| "id"     -> e

| "atom" -> (match e with T | F | Int _ | Seq [] -> T | _ -> F)
| "eq"   -> (match e with Seq [e1;e2] -> if e1 = e2 then T else F)	 
| "null" -> (match e with Seq [] -> T | _ -> F)

| "and"  -> (match e with Seq [e1;e2] ->
              (match (eval e1, eval e2) with T,T -> T | T,F | F,T | F,F -> F))

| "or"   -> (match e with Seq [e1;e2] ->
              (match (eval e1, eval e2) with F,F -> F | T,F | F,T | T,T -> T))

| "not"  -> (match e with F -> T | T -> F)

| _ -> raise (Eval_bug ("Unknown primitive " ^ s))
;;
