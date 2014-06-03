module type PRED_SET = sig
  type 'a set
  val empty: 'a set
  val singleton: 'a -> 'a set
  val member: 'a -> 'a set -> bool
  val union: 'a set -> 'a set -> 'a set
  val intersection: 'a set -> 'a set -> 'a set
  val difference:'a set -> 'a set -> 'a set
  val fromList: 'a list -> 'a set
  val fromPred: ('a -> bool) -> 'a set
  val toPred: 'a set -> ('a -> bool)
end


module PredSet : PRED_SET = struct 

  type 'a set = 'a -> bool 

  let empty = fun y -> true (* Replace this stub *)

  let singleton x = fun y -> true (* Replace this stub *)

  let member x s = s x 

  let union s1 s2 = fun y -> true (* Replace this stub *)

  let intersection s1 s2 = fun y -> true (* Replace this stub *)

  let difference s1 s2 = fun y -> true (* Replace this stub *)

  let fromList xs = fun y -> true (* Replace this stub *)

  let fromPred p = fun y -> true (* Replace this stub *)

  let toPred s = fun y -> true (* Replace this stub *)

end
