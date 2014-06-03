(* Hofl Environments (with a fixpoint operator) *)

module type ENV = sig
  type 'a env
  val empty: 'a env
  val bind : string -> 'a -> 'a env -> 'a env
  val bindAll : string list -> 'a list -> 'a env -> 'a env
  val make : string list -> 'a list -> 'a env 
  val lookup : string -> 'a env -> 'a option
  val bindThunk : string -> (unit -> 'a) -> 'a env -> 'a env
  val bindAllThunks : string list -> (unit -> 'a) list -> 'a env -> 'a env
  val merge : 'a env -> 'a env -> 'a env
  val fix : ('a env -> 'a env) -> 'a env 
  (* for testing *)
  val fromFun : (string -> 'a option) -> 'a env
  val toFun : 'a env -> (string -> 'a option)
end 

module Env : ENV = struct
  type 'a env = string -> 'a option 
  let empty = fun s -> None
  let bind name valu env = 
    fun s -> if s = name then Some valu else env s
  let bindAll names vals env = ListUtils.foldr2 bind env names vals 
  let make names vals = bindAll names vals empty
  let lookup name env = env name  
  (* New for HOFL environments *)
  let bindThunk name valuThunk env = 
    fun s -> if s = name then Some (valuThunk ()) else env s
  let bindAllThunks names valThunks env = 
    ListUtils.foldr2 bindThunk env names valThunks
  let merge env1 env2 =
    fun s -> (match env1 s with 
                None -> env2 s
              | some -> some)  
  let fix gen = let rec envfix s = (gen envfix) s in envfix
  (* for testing *)
  let fromFun f = f
  let toFun f = f
end
