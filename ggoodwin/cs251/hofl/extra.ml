

  let withZeroValus f = 
    fun vs -> 
      match vs with 
        [] -> f ()
      | _ -> raise (EvalError ("Expected zero values but got: " ^ (valusToString vs)))

  let withOneValu f = 
    fun vs -> 
      match vs with 
        [v] -> f v
      | _ -> raise (EvalError ("Expected one value but got: " ^ (valusToString vs)))

  let withTwoValus f = 
    fun vs -> 
      match vs with 
        [v1;v2] -> f v1 v2
      | _ -> raise (EvalError ("Expected two values but got: " ^ (valusToString vs)))

  let withTwoInts f = 
    withTwoValus
      (fun v1 v2 -> 
	match (v1,v2) with 
          (Int i1, Int i2) -> f i1 i2
	| _  -> raise (EvalError ("Expected two integers but got: "
				  ^ (valusToString [v1;v2]))))
  let withBool f = 
    withOneValu
     (fun v -> 
       match v with 
         Bool b -> f b
       | _ -> raise (EvalError ("Expected a boolean but got "
				^ (valuToString v))))

  let withTwoBools f = 
    withTwoValus
     (fun v1 v2 -> 
       match (v1,v2) with 
         (Bool b1, Bool b2) -> f b1 b2
       | _ -> raise (EvalError ("Expected two booleans but got: "
				^ (valusToString [v1;v2]))))

  let withPair f = 
    withOneValu
     (fun v -> 
       match v with 
         Pair (v1,v2) -> f (v1,v2)
       | _ -> raise (EvalError ("Expected a pair but got "
				^ (valuToString v))))

  let withList f = 
    withOneValu
     (fun v -> 
       match v with 
         List vs -> f vs
       | _ -> raise (EvalError ("Expected a list but got "
				^ (valuToString v))))

