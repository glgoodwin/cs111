(* Table implementation of environments *)

module Env : ENV = struct
  module T = Map.Make(String) (* String Tables *)
  type 'a env = 'a T.t
  let empty = T.empty
  let bind name valu env = T.add name valu env
  let bindAll names vals env = ListUtils.foldr2 bind env names vals
  let make names vals = bindAll names vals empty
  let lookup name env = 
      (try 
         Some(T.find name env)
       with 
         Not_found -> None)
  let remove name env = T.remove name env
  let removeAll names env = ListUtils.foldr remove env names
  let merge env1 env2 = 
    T.fold bind
           env1 (* Env folded over *)
           env2 (* Null value of fold *)
  
end
