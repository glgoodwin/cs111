open Fp
open Fp_parser
open Fp_lexer

open Printf;;


let save_file s = printf "Saving definitions to %s.\n" s;
  let f = open_out s in
  Hashtbl.iter (fun n d -> fprintf f "Def %s = %s\n" n (string_of_fct d)) defs;
  close_out f; T;;


let rec load_file s = printf "Loading definitions from %s.\n" s;
  let f = open_in s in
  try
    while true do
      eval_cmd (cmd lexer (Lexing.from_string ((input_line f) ^"\n")))
    done; T
  with | End_of_file -> close_in f; T | _ -> F


and eval_cmd comd = 
  try
    match comd with
    | None     -> T
    | Quit     -> printf "Ciao.\n"; exit 0; T
    | Load s   -> load_file s
    | Save s   -> save_file s
    | Def(s,f) -> add_user_def s f; T
    | Undef s  -> (try del_user_def s; T with Not_found -> F)
    | Show s   -> (try if s = "all" then
	Hashtbl.iter (fun f d -> printf "%s = %s\n" f (string_of_fct d)) defs
    else print_fct (get_user_def s);
	T
    with Not_found -> F)
    | Exp e    -> eval e
  with 
  | Eval_error e -> printf "Unknown function: %s\n" (string_of_expr e); Bottom
  | Eval_bottom e -> printf "Eval failed: %s\n" (string_of_expr e); Bottom
;;

let
    exe line = eval_cmd (cmd lexer (Lexing.from_string line))
;;



(* the interaction loop *)

print_string banner;

while true do
  print_string "# "; flush stdout;
  try
    print_expr (exe ((try input_line stdin with End_of_file -> "Quit") ^ "\n"))
  with
  | Failure s -> printf "%s\n" s 
  | Parsing.Parse_error -> printf "Parse error\n"
done;;
