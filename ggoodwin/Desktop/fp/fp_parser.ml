type token =
  | Lprim of (string)
  | Leval
  | Lpar
  | Rpar
  | Lang
  | Rang
  | Lsqu
  | Rsqu
  | Lcom
  | Lscl
  | Leol
  | Lapplytoall
  | Lins
  | Lo
  | Lcond
  | Lcst
  | Lbu
  | Lwhile
  | LT
  | LF
  | Laff
  | LDef
  | LUndef
  | LShow
  | LQuit
  | LLoad
  | LSave
  | Ls of (int)
  | Lr of (int)
  | Lint of (int)
  | Lident of (string)
  | Lvar of (string)
  | Lstr of (string)

open Parsing;;
# 2 "fp_parser.mly"
open Fp;;
# 40 "fp_parser.ml"
let yytransl_const = [|
  258 (* Leval *);
  259 (* Lpar *);
  260 (* Rpar *);
  261 (* Lang *);
  262 (* Rang *);
  263 (* Lsqu *);
  264 (* Rsqu *);
  265 (* Lcom *);
  266 (* Lscl *);
  267 (* Leol *);
  268 (* Lapplytoall *);
  269 (* Lins *);
  270 (* Lo *);
  271 (* Lcond *);
  272 (* Lcst *);
  273 (* Lbu *);
  274 (* Lwhile *);
  275 (* LT *);
  276 (* LF *);
  277 (* Laff *);
  278 (* LDef *);
  279 (* LUndef *);
  280 (* LShow *);
  281 (* LQuit *);
  282 (* LLoad *);
  283 (* LSave *);
    0|]

let yytransl_block = [|
  257 (* Lprim *);
  284 (* Ls *);
  285 (* Lr *);
  286 (* Lint *);
  287 (* Lident *);
  288 (* Lvar *);
  289 (* Lstr *);
    0|]

let yylhs = "\255\255\
\001\000\001\000\001\000\001\000\001\000\001\000\001\000\001\000\
\002\000\002\000\002\000\002\000\002\000\002\000\002\000\002\000\
\005\000\005\000\005\000\005\000\005\000\005\000\005\000\005\000\
\005\000\005\000\005\000\003\000\003\000\007\000\007\000\004\000\
\004\000\006\000\006\000\000\000\000\000\000\000"

let yylen = "\002\000\
\005\000\003\000\003\000\002\000\002\000\003\000\003\000\001\000\
\001\000\001\000\001\000\001\000\003\000\002\000\003\000\003\000\
\001\000\001\000\001\000\001\000\002\000\002\000\002\000\003\000\
\003\000\003\000\003\000\005\000\001\000\003\000\001\000\003\000\
\001\000\003\000\001\000\002\000\002\000\002\000"

let yydefred = "\000\000\
\000\000\000\000\000\000\000\000\017\000\000\000\000\000\000\000\
\008\000\000\000\000\000\000\000\000\000\000\000\009\000\010\000\
\000\000\000\000\000\000\000\000\000\000\000\000\018\000\019\000\
\011\000\020\000\012\000\036\000\000\000\000\000\000\000\000\000\
\037\000\000\000\038\000\000\000\000\000\014\000\000\000\000\000\
\000\000\000\000\021\000\022\000\023\000\000\000\000\000\000\000\
\000\000\000\000\005\000\000\000\000\000\004\000\000\000\000\000\
\000\000\000\000\013\000\027\000\000\000\015\000\000\000\024\000\
\025\000\026\000\000\000\002\000\003\000\006\000\007\000\016\000\
\030\000\000\000\032\000\034\000\000\000\000\000\001\000\028\000"

let yydgoto = "\004\000\
\028\000\039\000\030\000\040\000\031\000\042\000\032\000"

let yysindex = "\018\000\
\065\255\097\255\129\255\000\000\000\000\097\255\027\255\129\255\
\000\000\129\255\129\255\097\255\129\255\129\255\000\000\000\000\
\231\254\240\254\247\254\012\255\248\254\249\254\000\000\000\000\
\000\000\000\000\000\000\000\000\013\255\025\255\015\255\020\255\
\000\000\129\255\000\000\032\255\000\255\000\000\028\255\035\255\
\029\255\034\255\000\000\000\000\000\000\097\255\129\255\030\255\
\037\255\038\255\000\000\042\255\043\255\000\000\097\255\129\255\
\129\255\058\255\000\000\000\000\097\255\000\000\129\255\000\000\
\000\000\000\000\129\255\000\000\000\000\000\000\000\000\000\000\
\000\000\053\255\000\000\000\000\054\255\129\255\000\000\000\000"

let yyrindex = "\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\001\000\011\000\
\000\000\000\000\000\000\000\000\000\000\000\000\061\255\000\000\
\063\255\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000"

let yygindex = "\000\000\
\000\000\006\000\253\255\008\000\003\000\010\000\209\255"

let yytablesize = 278
let yytable = "\035\000\
\031\000\055\000\037\000\060\000\041\000\048\000\029\000\033\000\
\073\000\074\000\029\000\036\000\043\000\044\000\049\000\046\000\
\047\000\045\000\001\000\002\000\003\000\050\000\051\000\054\000\
\052\000\053\000\055\000\005\000\056\000\006\000\058\000\007\000\
\038\000\008\000\057\000\059\000\061\000\063\000\010\000\011\000\
\062\000\064\000\012\000\013\000\014\000\015\000\016\000\068\000\
\069\000\066\000\067\000\065\000\070\000\071\000\023\000\024\000\
\025\000\026\000\027\000\041\000\072\000\060\000\078\000\077\000\
\079\000\005\000\033\000\006\000\075\000\007\000\035\000\008\000\
\076\000\000\000\080\000\009\000\010\000\011\000\000\000\000\000\
\012\000\013\000\014\000\015\000\016\000\000\000\017\000\018\000\
\019\000\020\000\021\000\022\000\023\000\024\000\025\000\026\000\
\027\000\005\000\000\000\006\000\000\000\007\000\000\000\008\000\
\000\000\000\000\000\000\000\000\010\000\011\000\000\000\000\000\
\012\000\013\000\014\000\015\000\016\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\023\000\024\000\025\000\026\000\
\027\000\005\000\000\000\034\000\000\000\000\000\000\000\008\000\
\000\000\000\000\000\000\000\000\010\000\011\000\000\000\000\000\
\012\000\013\000\014\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\023\000\024\000\000\000\026\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\031\000\000\000\031\000\000\000\000\000\000\000\
\031\000\031\000\031\000\031\000\029\000\000\000\029\000\031\000\
\000\000\000\000\029\000\029\000\000\000\029\000"

let yycheck = "\003\000\
\000\000\002\001\006\000\004\001\008\000\031\001\001\000\002\000\
\056\000\057\000\000\000\006\000\010\000\011\000\031\001\013\000\
\014\000\012\000\001\000\002\000\003\000\031\001\011\001\011\001\
\033\001\033\001\002\001\001\001\014\001\003\001\034\000\005\001\
\006\001\007\001\015\001\004\001\009\001\009\001\012\001\013\001\
\006\001\008\001\016\001\017\001\018\001\019\001\020\001\011\001\
\011\001\047\000\021\001\046\000\011\001\011\001\028\001\029\001\
\030\001\031\001\032\001\063\000\055\000\004\001\010\001\067\000\
\011\001\001\001\006\001\003\001\061\000\005\001\008\001\007\001\
\063\000\255\255\078\000\011\001\012\001\013\001\255\255\255\255\
\016\001\017\001\018\001\019\001\020\001\255\255\022\001\023\001\
\024\001\025\001\026\001\027\001\028\001\029\001\030\001\031\001\
\032\001\001\001\255\255\003\001\255\255\005\001\255\255\007\001\
\255\255\255\255\255\255\255\255\012\001\013\001\255\255\255\255\
\016\001\017\001\018\001\019\001\020\001\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\028\001\029\001\030\001\031\001\
\032\001\001\001\255\255\003\001\255\255\255\255\255\255\007\001\
\255\255\255\255\255\255\255\255\012\001\013\001\255\255\255\255\
\016\001\017\001\018\001\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\028\001\029\001\255\255\031\001\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\002\001\255\255\004\001\255\255\255\255\255\255\
\008\001\009\001\010\001\011\001\002\001\255\255\004\001\015\001\
\255\255\255\255\008\001\009\001\255\255\011\001"

let yynames_const = "\
  Leval\000\
  Lpar\000\
  Rpar\000\
  Lang\000\
  Rang\000\
  Lsqu\000\
  Rsqu\000\
  Lcom\000\
  Lscl\000\
  Leol\000\
  Lapplytoall\000\
  Lins\000\
  Lo\000\
  Lcond\000\
  Lcst\000\
  Lbu\000\
  Lwhile\000\
  LT\000\
  LF\000\
  Laff\000\
  LDef\000\
  LUndef\000\
  LShow\000\
  LQuit\000\
  LLoad\000\
  LSave\000\
  "

let yynames_block = "\
  Lprim\000\
  Ls\000\
  Lr\000\
  Lint\000\
  Lident\000\
  Lvar\000\
  Lstr\000\
  "

let yyact = [|
  (fun _ -> failwith "parser")
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 3 : string) in
    let _4 = (Parsing.peek_val __caml_parser_env 1 : Fp.fct) in
    Obj.repr(
# 46 "fp_parser.mly"
                            ( Def(_2,_4) )
# 258 "fp_parser.ml"
               : Fp.cmd))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : string) in
    Obj.repr(
# 47 "fp_parser.mly"
                            ( Undef _2 )
# 265 "fp_parser.ml"
               : Fp.cmd))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : string) in
    Obj.repr(
# 48 "fp_parser.mly"
                            ( Show _2 )
# 272 "fp_parser.ml"
               : Fp.cmd))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 1 : Fp.expr) in
    Obj.repr(
# 49 "fp_parser.mly"
                            ( Exp _1 )
# 279 "fp_parser.ml"
               : Fp.cmd))
; (fun __caml_parser_env ->
    Obj.repr(
# 50 "fp_parser.mly"
                            ( Quit )
# 285 "fp_parser.ml"
               : Fp.cmd))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : string) in
    Obj.repr(
# 51 "fp_parser.mly"
                            ( Load _2 )
# 292 "fp_parser.ml"
               : Fp.cmd))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : string) in
    Obj.repr(
# 52 "fp_parser.mly"
                            ( Save _2 )
# 299 "fp_parser.ml"
               : Fp.cmd))
; (fun __caml_parser_env ->
    Obj.repr(
# 53 "fp_parser.mly"
                            ( None )
# 305 "fp_parser.ml"
               : Fp.cmd))
; (fun __caml_parser_env ->
    Obj.repr(
# 57 "fp_parser.mly"
                    ( T )
# 311 "fp_parser.ml"
               : Fp.expr))
; (fun __caml_parser_env ->
    Obj.repr(
# 58 "fp_parser.mly"
                    ( F )
# 317 "fp_parser.ml"
               : Fp.expr))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : int) in
    Obj.repr(
# 59 "fp_parser.mly"
                    ( Int (Num.Int _1) )
# 324 "fp_parser.ml"
               : Fp.expr))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : string) in
    Obj.repr(
# 60 "fp_parser.mly"
                    ( Var _1 )
# 331 "fp_parser.ml"
               : Fp.expr))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : Fp.expr) in
    Obj.repr(
# 61 "fp_parser.mly"
                    ( _2 )
# 338 "fp_parser.ml"
               : Fp.expr))
; (fun __caml_parser_env ->
    Obj.repr(
# 62 "fp_parser.mly"
                    ( Seq [] )
# 344 "fp_parser.ml"
               : Fp.expr))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : 'list) in
    Obj.repr(
# 63 "fp_parser.mly"
                    ( Seq _2 )
# 351 "fp_parser.ml"
               : Fp.expr))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : Fp.fct) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : Fp.expr) in
    Obj.repr(
# 64 "fp_parser.mly"
                    ( App(_1, _3) )
# 359 "fp_parser.ml"
               : Fp.expr))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : string) in
    Obj.repr(
# 68 "fp_parser.mly"
                    ( Prim _1 )
# 366 "fp_parser.ml"
               : 'fatom))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : int) in
    Obj.repr(
# 69 "fp_parser.mly"
                    ( Sel _1 )
# 373 "fp_parser.ml"
               : 'fatom))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : int) in
    Obj.repr(
# 70 "fp_parser.mly"
                    ( RSel _1 )
# 380 "fp_parser.ml"
               : 'fatom))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : string) in
    Obj.repr(
# 72 "fp_parser.mly"
                    ( User _1 )
# 387 "fp_parser.ml"
               : 'fatom))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 0 : 'fatom) in
    Obj.repr(
# 74 "fp_parser.mly"
                         ( ApplyToAll _2 )
# 394 "fp_parser.ml"
               : 'fatom))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 0 : 'fatom) in
    Obj.repr(
# 75 "fp_parser.mly"
                         ( Insert _2 )
# 401 "fp_parser.ml"
               : 'fatom))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 0 : Fp.expr) in
    Obj.repr(
# 76 "fp_parser.mly"
                         ( Constant _2 )
# 408 "fp_parser.ml"
               : 'fatom))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : 'fctlist) in
    Obj.repr(
# 77 "fp_parser.mly"
                         ( Construction _2 )
# 415 "fp_parser.ml"
               : 'fatom))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : 'fatom) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : Fp.expr) in
    Obj.repr(
# 78 "fp_parser.mly"
                         ( Bu(_2,_3) )
# 423 "fp_parser.ml"
               : 'fatom))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : 'fatom) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'fatom) in
    Obj.repr(
# 79 "fp_parser.mly"
                         ( While(_2,_3) )
# 431 "fp_parser.ml"
               : 'fatom))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : Fp.fct) in
    Obj.repr(
# 80 "fp_parser.mly"
                         ( _2 )
# 438 "fp_parser.ml"
               : 'fatom))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 4 : 'comp) in
    let _3 = (Parsing.peek_val __caml_parser_env 2 : 'comp) in
    let _5 = (Parsing.peek_val __caml_parser_env 0 : Fp.fct) in
    Obj.repr(
# 84 "fp_parser.mly"
                             ( Condition(_1,_3,_5) )
# 447 "fp_parser.ml"
               : Fp.fct))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : 'comp) in
    Obj.repr(
# 85 "fp_parser.mly"
                             ( _1 )
# 454 "fp_parser.ml"
               : Fp.fct))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'fatom) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'comp) in
    Obj.repr(
# 89 "fp_parser.mly"
                   ( Composition(_1,_3) )
# 462 "fp_parser.ml"
               : 'comp))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : 'fatom) in
    Obj.repr(
# 90 "fp_parser.mly"
                   ( _1 )
# 469 "fp_parser.ml"
               : 'comp))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : Fp.expr) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'list) in
    Obj.repr(
# 93 "fp_parser.mly"
                   ( _1 :: _3 )
# 477 "fp_parser.ml"
               : 'list))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : Fp.expr) in
    Obj.repr(
# 94 "fp_parser.mly"
                   ( [ _1 ] )
# 484 "fp_parser.ml"
               : 'list))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : Fp.fct) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'fctlist) in
    Obj.repr(
# 98 "fp_parser.mly"
                     ( _1 :: _3 )
# 492 "fp_parser.ml"
               : 'fctlist))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : Fp.fct) in
    Obj.repr(
# 99 "fp_parser.mly"
                     ( [ _1 ] )
# 499 "fp_parser.ml"
               : 'fctlist))
(* Entry cmd *)
; (fun __caml_parser_env -> raise (Parsing.YYexit (Parsing.peek_val __caml_parser_env 0)))
(* Entry exp *)
; (fun __caml_parser_env -> raise (Parsing.YYexit (Parsing.peek_val __caml_parser_env 0)))
(* Entry fct *)
; (fun __caml_parser_env -> raise (Parsing.YYexit (Parsing.peek_val __caml_parser_env 0)))
|]
let yytables =
  { Parsing.actions=yyact;
    Parsing.transl_const=yytransl_const;
    Parsing.transl_block=yytransl_block;
    Parsing.lhs=yylhs;
    Parsing.len=yylen;
    Parsing.defred=yydefred;
    Parsing.dgoto=yydgoto;
    Parsing.sindex=yysindex;
    Parsing.rindex=yyrindex;
    Parsing.gindex=yygindex;
    Parsing.tablesize=yytablesize;
    Parsing.table=yytable;
    Parsing.check=yycheck;
    Parsing.error_function=parse_error;
    Parsing.names_const=yynames_const;
    Parsing.names_block=yynames_block }
let cmd (lexfun : Lexing.lexbuf -> token) (lexbuf : Lexing.lexbuf) =
   (Parsing.yyparse yytables 1 lexfun lexbuf : Fp.cmd)
let exp (lexfun : Lexing.lexbuf -> token) (lexbuf : Lexing.lexbuf) =
   (Parsing.yyparse yytables 2 lexfun lexbuf : Fp.expr)
let fct (lexfun : Lexing.lexbuf -> token) (lexbuf : Lexing.lexbuf) =
   (Parsing.yyparse yytables 3 lexfun lexbuf : Fp.fct)
;;
# 103 "fp_parser.mly"
  
# 534 "fp_parser.ml"
