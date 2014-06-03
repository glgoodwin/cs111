let rec ef1 = fun s' -> (fromFun (fun s -> lookup s (bind "a" (eval (Lit (Int 3)) ef1) empty)) s');;

let rec ef2 s = lookup s (bindThunk "a" (fun _ -> (eval (Lit (Int 3)) (fromFun ef2))) 
                             (bindThunk "b" (fun _ -> (eval (Var "a") (fromFun ef2)))
                               empty));;
