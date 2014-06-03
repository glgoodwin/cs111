module MiniFPInterp = struct
  
  open MiniFP
  open ListUtils
  open FunUtils

  exception EvalError of string

  let rec apply funform obj = 
      match (funform, obj) with
        (* The Add and Trans forms are already implemented *)
       	(Add, Seq[Int x; Int y]) -> Int(x+y)
      | (Trans, Seq xs) -> Seq(transposeSeqs xs)
        (* Flesh out the remaining clauses below *)
      |	(Id, Seq xs) -> Seq(xs)
      |	(Sub, Seq[Int x; Int y]) -> Int(x-y)
      |	(Mul, Seq[Int x; Int y]) -> Int(x*y)
      |	(Div, Seq[Int x; Int y]) -> Int(x/y)
      |	(Distl, Seq[y; Seq xs]) -> Seq (map (fun x -> Seq[y;x]) xs)
      |	(Distr, Seq[Seq xs; y]) -> Seq (map (fun x -> Seq[x;y]) xs)
      (*| (Sel y, Seq xs) -> Seq (iterate (fun (n,x::xs') -> (n-1,xs')) 
                                                       (fun (n,xs) -> 
                                                         match (n,xs) with
                                                          (_,[]) -> raise (EvalError ("Selection index out of bounds")) 
                                                         |(0,_) -> List.hd(xs)) 
                                                       (y, xs))  I think this function needs a helper function *)
      |	(Const y, _) -> y
      |	(Map 
      |	_ -> raise (EvalError ("Ill-formed application: apply "
                               ^ (funFormToString funform)
			       ^ " " 
                               ^ (objToString obj)))

  (* To simplify your life, transposeSeq is already implemented for you *)
  and transposeSeqs seqs =
    if not (ListUtils.for_all isSeq seqs) then 
      raise (EvalError ("transpose -- not a sequence of sequences: "
                        ^ (objToString (Seq seqs))))
    else
      let xs = List.map seqElts seqs in 
      match xs with 
	[] -> [] 
      |	(x::xs') -> let len = List.length x 
                     in if ListUtils.for_all (fun ys -> (List.length ys) = len) xs'
   		        then (let rec recur zs = 
			  match zs with
				 ([]::_) -> [] 
                               | _ -> Seq(List.map List.hd zs)::(recur(List.map List.tl zs))
   			       in recur xs)
                        else raise (EvalError ("transpose -- not a regular sequence of sequences: "
					       ^ (objToString (Seq seqs))))

  and isSeq obj = 
    match obj with 
      Seq(_) -> true
    | _ -> false

  and seqElts obj = 
    match obj with 
      Seq(xs) -> xs
    | _ -> raise (EvalError "not a sequence")

end
