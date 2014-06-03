(* Hofl Environments (with a fixpoint operator) *)

module type ENV = sig
  exception DefineError of string
  exception BlackHole of string
  type 'a env
  val empty: 'a env
  val bind : string -> 'a -> 'a env -> 'a env
  val bindAll : string list -> 'a list -> 'a env -> 'a env
  val make : string list -> 'a list -> 'a env 
  val lookup : string -> 'a env -> 'a option
  val global : unit -> 'a env (* returns a new mutable global environment *)
  val define: string -> 'a -> 'a env -> unit (* define/change global binding *)
  val bindrecAll : string list -> ('a env -> 'a list) -> 'a env -> 'a env
    (* extend environment with a bindrec frame *)
end 

module Env : ENV = struct
  exception DefineError of string
  exception BlackHole of string
  module Table = Map.Make(String) (* String Tables *)
  type 'a frame = 
      Global of ('a Table.t) ref (* for global env *)
    | Bindrec of ('a option ref) Table.t (* for bindrec frames *)
    | Simple of string * 'a (* for all other frames (single binding only) *)
  type 'a env = 'a frame list (* environment is a list of frames *)
  let rec infiniteLoop () = infiniteLoop () 
  let empty = [] 
  let bind name valu env = (Simple (name,valu))::env
  let bindAll names vals env = ListUtils.foldr2 bind env names vals 
  let make names vals = bindAll names vals empty
  let rec lookup name env = 
    match env with 
      [] -> None
    | (Simple(n,v)::env') -> if n = name then (Some v) else lookup name env'
    | (Global(tblCell)::_) -> (try Some(Table.find name (!tblCell)) with Not_found -> None)
    | (Bindrec(tbl)::env') -> 
	(try 
	  (match !(Table.find name tbl) with 
	    None -> raise (BlackHole ("black hole encountered for " ^ name))
                    (* Another option is to loop infinitely:
                       infiniteLoop() (* never return for black hole *)
                     *)
	  | Some v -> Some v)
	with
	  Not_found -> lookup name env')
  let global () = [Global(ref Table.empty)]
  let define name valu env = 
    match env with 
      (Global(tblCell)::_) -> tblCell := Table.add name valu (!tblCell)
    | _ -> raise (DefineError "define on a non-global environment!")
  let bindrecAll names valsFun env = 
    let cells = ListUtils.map (fun _ -> ref None) names in 
      (* initialize cells with black holes *)
    let bindrecTable = ListUtils.foldr2 Table.add Table.empty names cells in 
    let newEnv = Bindrec(bindrecTable)::env in 
    let vals = valsFun newEnv in 
    let _ = ListUtils.for_each2 (fun cell valu -> cell := Some valu) cells vals in
    newEnv
end
