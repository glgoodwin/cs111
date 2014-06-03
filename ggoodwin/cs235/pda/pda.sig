(*
 * pda.sig
 *)

signature PDA =
  sig
    type raw = {
                 stats : Sym.sym Set.set,
                 start : Sym.sym,
                 accepting : Sym.sym Set.set,
                 trans : Stack_tran.stack_tran Set.set
               }
    type pda
    val valid : raw -> bool
    val fromRaw : raw -> pda
    val toRaw : pda -> raw          
    val inputFromLabToks : (int * Lex.tok) list -> pda * (int * Lex.tok) list
    val fromString : string -> pda
    val input : string -> pda
    val toPP : pda -> PP.pp
    val toString : pda -> string
    val output : string * pda -> unit
    val states : pda -> Sym.sym Set.set
    val startState : pda -> Sym.sym
    val acceptingStates : pda -> Sym.sym Set.set
    val transitions : pda -> Stack_Tran.stack_tran Set.set
    val compare : pda * pda -> order (*combined comparison of states, transitions, etc.*)
    val equal : pda * pda -> bool (*true if FAs are identical, down to 
				   state names*)
    val numStates : pda -> int
    val numTransitions : pda -> int
    val alphabet : pda -> Sym.sym Set.set
    (*state * stack * test str -> (state * stack) set *)
    val transitionFun : pda -> Sym.sym * Str.str * Str.str -> (Sym.sym * Str.str) Set.set
    val accepted : pda -> Str.str -> bool

    (*concurrency: epsilon transitions*)
    val processStr : pda -> (Sym.sym * Str.str) Set.set * Str.str -> (Sym.sym * Str.str) Set.set 
          (*given a set of sym/stack pairs and a str x, returns a set of sym/stack pairs reachable from 
							   those pairs by consuming x*)

    (**)
    (*ambiguous grammars are undecidable?*)
    (*equal languages *)

    (*proof= convert to chomsky normal form. *)

    (*new versions of processStr: a "verbose" version that does a JFLAP-like check for each runthrough*)
    (*a "quiet", processStrQuiet version that runs as it does now.*)
    (*and a "limit" version of processStr that only follows a path some n number of times.*)
    (*max stack depth.*)

    val findLP : pda -> Sym.sym Set.set * Str.str * Sym.sym Set.set -> StackLP.stack_lp
    val findAcceptingLP : pda -> Str.str -> StackLP.stack_lp
    val toGram : pda -> Gram.gram
    val fromGram : Gram.gram -> pda
    val fromFA : fa -> pda

    val union : pda * pda -> pda (*union of two pdas*)
    val concat : pda * pda -> pda (*concatenation of two pdas*)
    val rev : pda -> pda (*reversal of pda*)
    val closure : pda -> pda (*kleene closure of pda*)
    val intersection : pda * fa -> pda (*intersection of pda and fa*)
    val minus : pda * fa -> pda (*difference of pda and fa*)
    val isomorphic : pda * pda -> bool
    val simplify : pda -> pda
    val renameStates : pda * SymRel.sym_rel -> pda (*rename states according to given 
							      sym relation*)
    val renameStatesCanonically : pda -> pda (*rename states as A,B,C...*)
    val isomorphism : pda * pda * SymRel.sym_rel -> bool (*true if pdas are isomorphic*)
    val findIsomorphism : pda * pda -> SymRel.sym_rel (*returns the sym rel denoting the isomorphic relationship between the states in the pdas*)
    val renameAlphabet : pda * SymRel.sym_rel -> pda (*renames transitions (and stackops) according to sym relation*)
    val emptyStr : pda (*returns a pda accepting empty string*)
    val emptySet : pda (*pda that accepts no strings*)
    val fromSym : Sym.sym -> pda (*produces the pda that accepts the str of the sym param*)
    val fromStr : Str.str -> pda (*produces the pda that accepts the str param*)
    val jforlanNew : unit -> pda
    val jforlanEdit : pda -> pda
    val jforlanValidate : string -> unit
    val jforlanPretty : string -> unit



  end
