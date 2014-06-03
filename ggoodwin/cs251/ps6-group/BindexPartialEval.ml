module BindexPartialEval = struct

  open FunUtils
  open ListUtils
  open Bindex

  

  let rec partialEval (Pgm(fmls,body)) = 
    Pgm (fmls, (peval body Env.empty))

  and peval exp env = 
   match exp with 
     Lit i -> Lit i 
   | Var name -> (match (Env.lookup name env) with
                   None -> exp
                  |Some i -> Lit i)
   | BinApp (rater, rand1, rand2) ->
       (match (peval rand1 env, peval rand2 env) with
        (Lit i, Lit j) -> (if (((=) j 0) && (((=) rater Div) || ((=) rater Rem))) then (BinApp (rater, Lit (i), Lit (j)))
	  else Lit (BindexEnvInterp.binApply rater i j))
       | (Lit i, _) -> BinApp (rater, Lit (i), (peval rand2 env))
       | (_, Lit i) -> BinApp (rater, (peval rand1 env),  Lit (i))
       | (_, _) ->  BinApp (rater, (peval rand1 env),  (peval rand2 env))) 
   | Bind (name, defn, body) ->
       (match (peval defn env) with
	 Lit i -> peval body (Env.bind name i env)
       | simplifiedDefn -> Bind(name, simplifiedDefn, peval body env))

end
