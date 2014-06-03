#use "../hoilic/load-hoilic.ml";;

let repl = HoilicEnvInterp.repl

let testCell() = 
  let _ = HoilicEnvInterp.runFile "test-cell.hic" [] in 
  ()
