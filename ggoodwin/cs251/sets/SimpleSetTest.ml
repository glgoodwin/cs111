module SimpleSetTest = 
  functor (Set: SET) -> struct 
    let test () = 
      let s1 = Set.fromList [5;2;6;1;4]
      and s2 = Set.fromList [2;8;6;3]
      in ( s1, 

           [Set.toList s1; 
            Set.toList s2; 
            Set.toList (Set.insert 3 s1); 
            Set.toList (Set.delete 5 s1); 
            Set.toList (Set.union s1 s2); 
            Set.toList (Set.intersection s1 s2); 
            Set.toList (Set.difference s1 s2)], 

           [Set.toString string_of_int s1;
            Sexp.sexpToString(Set.toSexp (fun x -> Sexp.Int x) s1)]

            )
 end

