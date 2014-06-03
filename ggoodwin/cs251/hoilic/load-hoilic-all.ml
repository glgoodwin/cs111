(* Load all parameter passing mechanisms for HOILIC *)

(* Call-by-value (CBV) *)
#use "../valex/load-valex.ml"
#use "../hoilec/Env.ml" (* new version of env that handles side effects *)
#use "../hoilic/Hoilic.ml" (* Call-by-value/call-by-reference syntax *)
#use "../hoilic/HoilicEnvInterp.ml" (* Call-by-value interpreter *)

(* Call-by-reference (CBR) *)
#use "../hoilic/HoilicCBREnvInterp.ml" (* Call-by-reference interpreter *)

(* Call-by-name (CBN) *)
#use "../hoilic/HoilicCBN.ml" (* Call-by-name syntax *)
#use "../hoilic/HoilicCBNEnvInterp.ml" (* Call-by-name interpreter *)

(* Call-by-lazy (CBL) = call-by-need *)
#use "../utils/Promise.ml" (* promises for call-by-lazy evaluation *)
#use "../hoilic/HoilicCBL.ml" (* Call-by-lazy syntax *)
#use "../hoilic/HoilicCBLEnvInterp.ml" (* Call-by-lazy interpreter *)

(* #use "../hoilec/HoilecInterpTest.ml" 
module HoilecEnvInterpTest = HoilecInterpTest(HoilecEnvInterp)
let testEnvInterp = HoilecEnvInterpTest.test *)

(* evaluate a hoilic expression string under all four parameter-passing mechanisms *)
let testHoilicExpString s = 
  let print = StringUtils.print in 
  let println = StringUtils.println in 
  let pgmString = "(hoilic () " ^ s ^ ")" in 
  let cbvPgm = Hoilic.stringToPgm pgmString in 
  let _ = print "Value of expression in CBV Hoilic: " in
  let _ = 
    try 
      println (Hoilic.valuToString (HoilicEnvInterp.run cbvPgm []))
    with
      Hoilic.EvalError s -> (println ("EvalError: " ^ s))
    | Hoilic.SyntaxError s -> (println ("SyntaxError: " ^ s))
    | Sexp.IllFormedSexp s -> (println ("SexpError: " ^ s))
    | Sys_error s -> (println ("Sys_error: " ^ s)) in
  let _ = print "Value of expression in CBR Hoilic: " in
  (* CBR interpreter uses CBV syntax *)
  let _ = 
    try 
      println (Hoilic.valuToString (HoilicCBREnvInterp.run cbvPgm [])) 
    with
      Hoilic.EvalError s -> (println ("EvalError: " ^ s))
    | Hoilic.SyntaxError s -> (println ("SyntaxError: " ^ s))
    | Sexp.IllFormedSexp s -> (println ("SexpError: " ^ s))
    | Sys_error s -> (println ("Sys_error: " ^ s)) in
  let cbnPgm = HoilicCBN.stringToPgm pgmString in 
  let _ = print "Value of expression in CBN Hoilic: " in
  let _ = 
    try 
      println (HoilicCBN.valuToString (HoilicCBNEnvInterp.run cbnPgm [])) 
    with
      HoilicCBN.EvalError s -> (println ("EvalError: " ^ s))
    | HoilicCBN.SyntaxError s -> (println ("SyntaxError: " ^ s))
    | Sexp.IllFormedSexp s -> (println ("SexpError: " ^ s))
    | Sys_error s -> (println ("Sys_error: " ^ s)) in
  let cblPgm = HoilicCBL.stringToPgm pgmString in 
  let _ = print "Value of expression in CBL Hoilic: " in
  let _ = 
    try 
      println (HoilicCBL.valuToString (HoilicCBLEnvInterp.run cblPgm [])) 
    with
      HoilicCBL.EvalError s -> (println ("EvalError: " ^ s))
    | HoilicCBL.SyntaxError s -> (println ("SyntaxError: " ^ s))
    | Sexp.IllFormedSexp s -> (println ("SexpError: " ^ s))
    | Sys_error s -> (println ("Sys_error: " ^ s)) in
  ()

(* evaluate a hoilic expression string under all four parameter-passing mechanisms *)
let testHoilicExpFile filename = testHoilicExpString (File.fileToString filename)





