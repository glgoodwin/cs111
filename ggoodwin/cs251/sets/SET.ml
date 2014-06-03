(*********************************************************************

A simple set signature. 
It is assumed that the component type of these set admit
comparison via <, =, and >. However, it is relatively straightforward 
to extend  the notion of set to one holding any kind of value
with a notion of comparison (i.e. satisfying the OrderedType signature).
As an example of this, see the Set.Make functor at
http://caml.inria.fr/ocaml/htmlman/libref/Set.Make.html.  

*********************************************************************)

module type SET = sig 

  type 'a set
  val empty : 'a set                    (* the empty set *)
  val singleton : 'a -> 'a set          (* a set with one element *)
  val insert : 'a -> 'a set -> 'a set   (* insert elt into given set *)
  val delete : 'a -> 'a set -> 'a set   (* delete elt from given set *)
  val isEmpty: 'a set -> bool           (* is the given set empty? *)
  val size : 'a set -> int              (* number of distinct elements in given set *)
  val member : 'a -> 'a set -> bool     (* is elt a member of given set? *)
  val union: 'a set -> 'a set -> 'a set (* union of two sets *)
  val intersection: 'a set -> 'a set -> 'a set (* intersection of two sets *)
  val difference: 'a set -> 'a set -> 'a set   (* difference of two sets *)
  val fromList : 'a list -> 'a set      (* create a set from a list *)
  val toList : 'a set -> 'a list        (* list all set elts, sorted low to high *)
  val toSexp : ('a -> Sexp.sexp) 
                -> 'a set -> Sexp.sexp  (* translates set into s-expression rep. *)
  val fromSexp : (Sexp.sexp -> 'a) 
                 -> Sexp.sexp -> 'a set (* translates s-expression rep. into set *)
  val toString : ('a -> string) 
                 -> 'a set -> string    (* returns string representation of set *)

end 
