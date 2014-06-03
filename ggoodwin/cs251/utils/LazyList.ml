(* OCAML implementation of lazy lists *)

module type LAZY_LIST = sig

    type 'a lazyList 
    val zNil : unit -> 'a lazyList
    val zCons : ('a promise * 'a lazyList) -> 'a lazyList
    val head : 'a lazyList -> 'a
    val tail : 'a lazyList -> 'a lazyList
    val isEmpty : 'a lazyList -> bool
    val singleton : 'a -> 'a lazyList
    val append : 'a lazyList -> 'a lazyList -> 'a lazyList
    val map : ('a -> 'b) -> 'a lazyList -> 'b lazyList
    val flatten : ('a lazyList) lazyList -> 'a lazyList
    val toList : ('a lazyList) -> 'a list
    exception EmptyLazyList

end

module LazyList: LAZY_LIST = struct 

  exception EmptyLazyList

  type 'a lazyListNode = LNil | LCons of (('a promise) * ('a lazyListNode promise))
  type 'a lazyList = 'a lazyListNode promise

  (* 
     Note: the following definitions use the convention that 
     variables beginning with "p" denote promises and
     variables beginning with "zl" are lazy lists (which are kinds of promises)
  *)

  let zNil ()  = makePromise (fun () -> LNil)

  let zCons(pHead,pTail) = makePromise (fun () -> LCons(pHead,pTail))

  let head zl =
    match (force zl) with
      LNil -> raise EmptyLazyList
    | (LCons(pHead,_)) -> force(pHead)

  let tail zl =
     match (force zl) with
       LNil -> raise EmptyLazyList
     | (LCons(_,pTail)) -> pTail

  let isEmpty zl =
    match (force zl) with
      LNil -> true
     | _ -> false

  let singleton valu = zCons(makePromise(fun () -> valu), 
			     makePromise(fun () -> LNil))

  let rec map f zl = makePromise(fun () -> map' f (force zl))

  and map' f llnode = 
    match llnode with 
      LNil -> LNil
    | LCons(pHead,pTail) -> 
	LCons(makePromise(fun () -> f(force(pHead))), 
	      map f pTail)

  let rec append zl1 zl2 = makePromise(fun () -> force(append' (force zl1) zl2))

  and append' llnode zl = 
    match llnode with 
      LNil -> zl
    | LCons(pHead, pTail) -> zCons(pHead, append pTail zl)
	  
  let rec flatten zl = makePromise(fun () -> force(flatten' (force zl)))

  and flatten' llnode = 
    match llnode with 
      LNil -> zNil()
    | LCons(pHead,pTail) -> append (force pHead) (flatten pTail)

  let rec toList lz  = 
    match (force lz) with 
      LNil -> []
    | LCons(pHead,pTail) -> (force pHead) :: (toList pTail)

  end
