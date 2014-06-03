(*
 *PDA.sml
 *
 *Many of these functions have been shamelessly stolen from Alley Stoughton's
 *forlan modules.
 *)


structure PDA (*:> PDA*) = 
struct

structure M = Messages
structure L = Lex

(*************************************** Types ********************************)

type raw = {stats      : Sym.sym Set.set,
	    start      : Sym.sym,
            accepting  : Sym.sym Set.set,
            trans      : StackTran.stack_tran Set.set
	   }
type pda = raw


fun checkStart(start, stats) =
  if not(SymSet.memb(start, stats))
  then M.errorPP
    (fn () =>
        [PP.fromString "invalid", PP.fromString "start",
         PP.fromString "state", PP.fromString ":",
         PP.quote(Sym.toPP start)])
  else ()


fun checkAccepting(nil, _)         = ()
  | checkAccepting(q :: qs, stats) =
    if not(SymSet.memb(q, stats))
    then M.errorPP
      (fn () =>
          [PP.fromString "invalid", PP.fromString "accepting",
                  PP.fromString "state", PP.fromString ":",
                  PP.quote(Sym.toPP q)])
      else checkAccepting(qs, stats)

(*checks that for all triples elt of trans, the two states are
elts of stats.*)
fun checkTransitions(nil,                _)     = ()
  | checkTransitions((q, _, _, _, r) :: trans, stats) =
      if not(SymSet.memb(q, stats))
        then M.errorPP
             (fn () =>
                   [PP.fromString "invalid", PP.fromString "state",
                    PP.fromString "in", PP.fromString "transition",
                    PP.fromString ":", PP.quote(Sym.toPP q)])
      else if not(SymSet.memb(r, stats))
        then M.errorPP
             (fn () =>
                   [PP.fromString "invalid", PP.fromString "state",
                    PP.fromString "in", PP.fromString "transition",
                    PP.fromString ":", PP.quote(Sym.toPP r)])
      else checkTransitions(trans, stats)

fun check{stats, start, accepting, trans} =
      (checkStart(start, stats);
       checkAccepting(Set.toList accepting, stats);
       checkTransitions(Set.toList trans, stats))

fun valid raw =
      (M.quiet(fn () => check raw); true)
        handle _ => false

fun fromRaw (raw : raw) : pda = (check raw; raw)

fun toRaw(pda : pda) : raw = pda

(*********************************** Input ***********************************)

(*eats up the tokens using specialized functions for each type.*)

fun inpPDA lts =
      let val lts              =
                L.checkInLabToks(L.Heading "{states}", lts)
          val (stats, lts)     = SymSet.inputFromLabToks lts
          val lts              =
                L.checkInLabToks(L.Heading "{startstate}", lts)
          val (start, lts)     = Sym.inputFromLabToks lts
          val lts              =
                L.checkInLabToks(L.Heading "{acceptingstates}", lts)
          val (accepting, lts) = SymSet.inputFromLabToks lts
          val lts              =
                L.checkInLabToks(L.Heading "{transitions}", lts)
          val (trans, lts)     = StackTranSet.inputFromLabToks lts
          val raw              =
                {stats     = stats,
                 start     = start,
                 accepting = accepting,
                 trans     = trans}
      in (fromRaw raw, lts) end

val inputFromLabToks = inpPDA

fun fromString s =
      case inpPDA(L.lexString s) of
           (pda, [(_, L.EOF)]) => pda
         | (_,  nil)          => M.cannotHappen() 
         | (_,  lt :: _)      => L.unexpectedLabTok lt

fun input fil =
      case inpPDA(L.lexFile fil) of
           (pda, [(_, L.EOF)]) => pda
         | (_,  nil)          => M.cannotHappen() 
         | (_,  lt :: _)      => L.unexpectedLabTok lt

(**output**)
fun toPP{stats, start, accepting, trans} =
      PP.block(true,
               [PP.block(true,
                         [PP.fromString "{states}",
                         SymSet.toPP stats]),
                PP.block(true,
                         [PP.fromString "{start state}",
                          Sym.toPP start]),
                PP.block(true,
                         [PP.fromString "{accepting states}",
                          SymSet.toPP accepting]),
                PP.block(true,
                         [PP.fromString "{transitions}",
                          StackTranSet.toPP trans])])

val toString = PP.toString o toPP

fun output("",  pda) = (print(toString pda); print PP.newline)
  | output(fil, pda) =
      case SOME(TextIO.openOut fil) handle _ => NONE of
           NONE     =>
             M.errorPP
             (fn () =>
                   [PP.fromString "unable", PP.fromString "to",
                    PP.fromString "open", PP.fromString "file",
                    PP.fromString ":",
                    PP.quote(PP.fromStringSplitEscape fil)])
         | SOME stm =>
             (TextIO.output(stm, toString pda);
              TextIO.output(stm, PP.newline);
              TextIO.closeOut stm)

(************************General functions*******************************)
fun states (pda : pda) = #stats pda

fun startState (pda : pda) = #start pda

fun acceptingStates (pda : pda) = #accepting pda

fun transitions (pda : pda) = #trans pda

fun compare(pda1, pda2) =
      case SymSet.compare(states pda1, states pda2) of
           LESS    => LESS
         | EQUAL   =>
             (case Sym.compare(startState pda1, startState pda2) of
                   LESS    => LESS
                 | EQUAL   =>
                     (case SymSet.compare(acceptingStates pda1,
                                          acceptingStates pda2) of
                           LESS    => LESS
                         | EQUAL   =>
                             StackTranSet.compare
                             (transitions pda1, transitions pda2)
                         | GREATER => GREATER)
                 | GREATER => GREATER)
         | GREATER => GREATER

fun equal pdaPair = compare pdaPair = EQUAL

fun numStates pda = Set.size(states pda)

fun numTransitions pda = Set.size(transitions pda)

(*alphabet here referring to the set of syms which compose the lang. of pda*)
(*gerates a list of the syms used in the strings read at*)
fun alphabet pda =
    SymSet.genUnion(Set.mapToList (fn (_, xs, _, _, _) => SymSet.fromList xs)
                                  (transitions pda))

(*the set of syms which appear in stack operations i.e. pushes & pops*)
fun stackAlphabet pda =
    SymSet.genUnion(Set.mapToList (fn (_, _, ys, zs, _) => SymSet.fromList (ys@zs))
                                  (transitions pda))

(*transitions that point to state p in pda.*)
fun transTo pda p = 
    let val trans = transitions pda
	fun traverse nil = nil
	  | traverse ((q,x,y,z,r) :: ts) =
	    (case Sym.compare (p,r) of 
		 EQUAL => ((q,x,y,z,r) :: (traverse ts))
	       | _ => traverse ts
	    )
	val result = StackTranSet.fromList (traverse (Set.toList trans))
    in
	if (StackTranSet.equal (result, Set.empty)) then
	    NONE
	else SOME result
    end

(*transitions which point from state p in pda.*)
fun transFrom pda p =
    let val trans = transitions pda
	fun traverse nil = nil
	  | traverse ((q,x,y,z,r) :: ts) =
	    (case Sym.compare (q,p) of 
		 LESS => (traverse ts)
	       | EQUAL => ((q,x,y,z,r) :: (traverse ts))
	       | GREATER => nil
	    )
	val result = StackTranSet.fromList (traverse (Set.toList trans))
    in
	if (StackTranSet.equal (result, Set.empty)) then
	    NONE
	else SOME result
    end

fun listToString tostr = List.foldr (fn (x,ans) => (tostr x)^","^ans) "";

(*returns a list of the states which are connected to start in BFS order.
unconnected states returned in natural Sym order.*)
(*FIX: avoid concatenation. not efficient.*)
fun BFSTraversal pda =
    let fun traverseSubgraph (nil, olds, queued) = List.rev olds
	  | traverseSubgraph (current :: news, olds, queued) = 
	    let val next_trans = transFrom pda current
		val nexts = 
		    case next_trans of
			NONE => Set.empty
		      | SOME next_trans => SymSet.map
					       (fn (_,_,_,_,q) => q) 
					       next_trans
		val nexts = SymSet.minus (nexts,queued)
		val queued = SymSet.union (nexts,queued)
	    in
		traverseSubgraph (news @ (Set.toList nexts), 
				  current :: olds, queued)
	    end
	val connectedResult = 	traverseSubgraph ([startState pda], nil, 
						  Set.empty)
	val fullResult = connectedResult @
			 (Set.toList
			  (SymSet.minus ((states pda),
					 (SymSet.fromList connectedResult))))
    in 
	fullResult
    end

(*returns a list of the states which are connected to start in BFS order.
unconnected states returned in natural Sym order.*)
(*FIX: avoid concatenation. not efficient.*)
fun DFSTraversal pda =
    let fun traverseSubgraph (nil, olds, queued) = List.rev olds
	  | traverseSubgraph (current :: news, olds, queued) = 
	    let val next_trans = transFrom pda current
		val nexts = 
		    case next_trans of
			NONE => Set.empty
		      | SOME next_trans => SymSet.map
					       (fn (_,_,_,_,q) => q) 
					       next_trans
		val nexts = SymSet.minus (nexts,queued)
		val queued = SymSet.union (nexts,queued)
	    in
		traverseSubgraph ((Set.toList nexts) @ news, 
				  current :: olds, queued)
	    end
	val connectedResult = 	traverseSubgraph ([startState pda], nil, 
						  Set.empty)
	val fullResult = connectedResult @
			 (Set.toList
			  (SymSet.minus ((states pda),
					 (SymSet.fromList connectedResult))))
    in 
	fullResult
    end

(*NOTE: If the pda graph contains cycles that can infinitely build the stack,
 *this bad boy loops infinitely. *)
fun processStr pda = 
    let val trans        = transitions pda
	val compareState = Set.comparePair(Sym.compare, Str.compare)
        val comparePair  = Set.comparePair(compareState, Str.compare)
        val unionPairSet = Set.union comparePair
        val membPairSet  = Set.memb comparePair
 
        fun newPairs(nil,                      ((_,_), _)) = nil
          | newPairs((p, y, v, w, r) :: trans, ((q,s), x)) =
            if Sym.equal(p, q)
            then case Str.removePrefix(y, x) of
                     NONE   => newPairs(trans, ((q,s), x))
                   | SOME z => case (PDAStack.stackop (v, w) s) of 
				   NONE => newPairs(trans, ((q,s),x))
				 | SOME t => ((r,t), z) :: 
					     newPairs(trans, ((q,s), x))
            else newPairs(trans, ((q,s), x))

	fun closure(nil,         olds) = olds
          | closure(new :: news, olds) =
            if membPairSet(new, olds)
            then closure(news, olds)
            else closure(newPairs(Set.toList trans, new) @ news,
                         unionPairSet(Set.sing new, olds))

        local
            fun res nil               = nil
              | res (((q,s), x) :: pairs) =
                if null x then (q,s) :: res pairs else res pairs
        in
        fun results pairs = Set.fromList compareState (res(Set.toList pairs))
        end

	fun processStr(qs's, x) =
	    let val init = Set.toList(Set.times(qs's, Set.sing x))
	    in results(closure(init, Set.empty)) end
    in processStr end



(*fun accepted pda =
      let val procStr   = processStr pda
          val start     = startState pda
          val accepting = acceptingStates pda

	  fun only_states pairs = SymSet.map (fn (q,s) => q) pairs

          fun accepted x =
                Set.isNonEmpty(SymSet.inter (only_states
			 (procStr(Set.sing (start, nil), x)),
                                            accepting))
      in accepted end*)


val emptySet : pda = 
    {stats     = Set.sing(Sym.fromString "A"),
     start     = Sym.fromString "A",
     accepting = Set.empty,
     trans     = Set.empty}

val emptyStr : pda =
      {stats     = Set.sing(Sym.fromString "A"),
       start     = Sym.fromString "A",
       accepting = Set.sing(Sym.fromString "A"),
       trans     = Set.empty}

fun renameStackAlphabet (({stats,start,accepting,trans},r) : 
			 (pda * SymRel.sym_rel)) : pda = 
    {stats = stats,
     start = start,
     accepting = accepting,
     trans = 
     (StackTranSet.map (fn (a,x,y,z,b) => 
			   (a, x, Str.renameAlphabet (y,r), 
			    Str.renameAlphabet (z,r), b)) trans)
    }

fun renameStates (({stats, start, accepting, trans},r) : (pda * SymRel.sym_rel)) : pda = 
    let fun renameOne x = SymRel.applyFunction r x
	val stats' = SymRel.apply (r,stats)
	val start' = renameOne start
	val accepting' = SymRel.apply (r,accepting)
	val trans' = StackTranSet.map (fn (a, x, y, z, b) => 
				  (renameOne a, x, y, z,
				   renameOne b)) trans
    in {stats     = stats',
        start     = start',
        accepting = accepting',
        trans     = trans'}
      end


(*Given an ordering of the states as a sym list, rename in that order.*)
(*Can be combined with DFS and BFS traversal results to rename states in
that order. This has made debugging infinitely easier. *)
fun renameStatesCanonicallyByOrdering pda ordering = 
    let 
	val indexToSym = if (Set.size (states pda)) <= 26 then
			     (fn i => Sym.fromString (String.str
				(Char.chr (Char.ord(#"A") + i))))
			   else
			       (fn i => Sym.fromString (Int.toString i))
	fun genrel (nil,_) = nil
	  | genrel (p::ps,index) = (p,(indexToSym index))::(genrel (ps,index+1))
	val rel = Set.fromList SymRel.comparePair (genrel (ordering,0))
    in
	renameStates (pda,rel)
    end

(*generate a relation between A,B,C,D.... and the current set of states. If 
num states > 26, make a relation 1 2 3 4 5 *)
fun renameStatesCanonically (pda : pda) : pda = 
    let	val stats = states pda
	fun position x = valOf(Set.position (fn y => Sym.equal (x,y)) stats)
	val r = if (Set.size stats) <= 26 then
		    SymRel.mlFunctionToFunction 
			(fn q => (Sym.fromString (String.str (Char.chr 
		       (Char.ord(#"A") + (position q) -1)))), stats)
		else
		    SymRel.mlFunctionToFunction ((fn q => (Sym.fromString 
		    (Int.toString (position q)))), stats)
    in
	renameStates (pda,r)
    end

(*accept states have empty tran to start of pda2.*)
fun concat ((pda1 : pda),(pda2 : pda)) : pda =
    let val renam1 = (fn s => Sym.fromString("<"^(Sym.toString s)^"1>"))

	val renam2 = (fn s => Sym.fromString("<"^(Sym.toString s)^"2>"))
	
	val pda1 = renameStates (pda1,SymRel.mlFunctionToFunction (renam1,
				 states pda1))
	
	val pda2 = renameStates (pda2,SymRel.mlFunctionToFunction (renam2,
				 states pda2))

	val pda1 = renameStackAlphabet (pda1,SymRel.mlFunctionToFunction (renam1,
				 stackAlphabet pda1))
	
	val pda2 = renameStackAlphabet (pda2,SymRel.mlFunctionToFunction (renam2,
				 stackAlphabet pda2))


	val start2 = startState pda2

	val trans = StackTranSet.union
		    (StackTranSet.union 
			 (transitions pda1, transitions pda2),
		    StackTranSet.map 
			(fn st => (st, nil, nil, nil, (startState pda2))) 
			(acceptingStates pda1))
	val conc =
	{     stats     = SymSet.union (states pda1, states pda2),
	      start     = startState pda1,
	      accepting = acceptingStates pda2,
	      trans     = trans
	}
    in
	conc
    end
    


fun normalize (pda : pda) : pda =
   let fun renam a = Sym.fromString (String.concat ["<",(Sym.toString a),">"])

       (*FIX: this adds pop symbols to EACH of the pda's accept states. For
	PDAs with multiple accept states, this is a LOT of unnecessary 
	overhead--if you have n accept states and a stack alphabet with m 
	symbols, that's n*m extra productions.*)
       fun emptyStackBeforeAccept (pda : pda) : pda = 
	 (*rel for renaming states*)
         let val state_rel = SymRel.mlFunctionToFunction (renam,(states pda))
	     val stack_rel = SymRel.mlFunctionToFunction (renam,(stackAlphabet pda))
	     val % = Str.fromString "%"
	     val emptyMarker = Str.fromString "e"
	     fun transToDumpStack (a, xs) = 
		 let val % = Str.fromString "%" in
		     StackTranSet.map 
			 (fn x => (a, %, [x], %, a))
			 xs
	         end
	     val pda = renameStackAlphabet ((renameStates (pda,state_rel)),
					    stack_rel)
	     val old_stats = states pda
	     val old_start = startState pda
	     val old_accepting = acceptingStates pda
	     val old_trans = transitions pda

	     val new_start_state = Sym.fromString "A"
	     val new_penultimate_state = Sym.fromString "B"
	     val new_accepting_state = Sym.fromString "C"
	     val new_accepting = Set.sing new_accepting_state

	     val new_stats = SymSet.genUnion [old_stats,
					      (Set.sing new_start_state),
					      new_accepting,
					      (Set.sing new_penultimate_state)]

	     val trans_new_start = (Set.sing 
					(new_start_state,
					 %,%,emptyMarker,old_start))

	     val trans_new_penultimate = StackTranSet.map 
					 (fn acc => (acc, %,%,%, new_penultimate_state))
					 old_accepting

	     val trans_new_accepting = (Set.sing
					(new_penultimate_state,
					 %,emptyMarker,%,new_accepting_state))
					 
(*	     val trans_new_accepting = (StackTranSet.map (fn acc => 
			(acc,%,emptyMarker,%,new_accepting_state)) old_accepting)*)

	     val trans_new_stack_dump = transToDumpStack (new_penultimate_state,
							  (stackAlphabet pda))

(*             val trans_new_stack_dump = (StackTranSet.genUnion 
			      (Set.mapToList (fn acc => 
				(transToDumpStack (acc,(stackAlphabet pda))))
					old_accepting))*)

	     val new_trans = StackTranSet.genUnion [old_trans, 
						    trans_new_start,
						    trans_new_penultimate,
						    trans_new_accepting,
						    trans_new_stack_dump]
	 in
	     {stats =     new_stats,
	      start =     new_start_state,
	      accepting = new_accepting,
	      trans =     new_trans
	     }
	 end
       (*Trans (a,x,[],[],b),(a,x,[y],[],b) and (a,x,[],[z],b) stay the same.
        Trans that push and pop strs of greater than length 1 are split up.
	(a,x,[y1,y2,y3,..,yk],[z1,z2,z3,...,zi],b) becomes [(a,x,[y1],[],c1),
	(c1,[],[y2],[],c2),...,(ck,[],[],[z1],ck+1),...,(cn,[],[],[zi],b)]
	To easily ensure that the new states we're adding don't exist already
	(a tough proposition when there can be multiple transitions for any pair
	of states), we first rename all our states by wrapping brackets around
	them and then construct new state names which split a tran from A->B as
	<A,B,n>, where n is a unique number for the entire set of newly 
	constructed states (not just trans from A->B).
	*)
       fun splitTransitions (pda : pda) : pda = 
	   let val state_rel = SymRel.mlFunctionToFunction (renam,(states pda))
	       val stack_rel = SymRel.mlFunctionToFunction (renam,(stackAlphabet pda))
	       val pda' = renameStates ((renameStackAlphabet (pda,stack_rel)),
					state_rel)
	       val stats' = states pda'
	       val start' = startState pda'
	       val accepting' = acceptingStates pda'
	       val trans' = transitions pda'

	       fun newStateName (s1,s2,n) = Sym.fromString (String.concat ["<",
						(Sym.toString s1),",",
						(Sym.toString s2),",",
						(Int.toString n),">"])

	       fun splitTrans ts = 
		 let fun split (newstats,newtrans,nil,n) = (newstats,newtrans)
		       | split (newstats,newtrans,(a,x,y,z,b)::olds,n) =
			 let val c = newStateName(a,b,n)
			 in
			  case (y,z) of
			      (y1::y2::ys,z) => split (a :: newstats,
						       (a,x,[y1],[],c) :: newtrans,
						       (c,[],y2::ys,z,b) :: olds,
						       n+1)

			    | ([y1],z1::zs)  => split (a :: newstats,
						       (a,x,[y1],[],c) :: newtrans,
						       (c,[],[],z1::zs,b) :: olds,
						       n+1)

			    | (_,z1::z2::zs) => split (a :: newstats,
						       (a,x,[],[z1],c) :: newtrans,
						       (c,[],[],z2::zs,b) :: olds,
						       n+1)
						      
			    | _               => split (a :: b :: newstats,
							(a,x,y,z,b) :: newtrans,
							olds,
							n)
						 
			 end
		   in
		       split (nil,nil,ts,0)
		   end
	       val (ss, ts) = splitTrans (Set.toList trans')
	       val stats_new = SymSet.fromList (ss)
	       val trans_new = StackTranSet.fromList(ts)

	   in
	       {stats =     stats_new,
		start =     start',
		accepting = accepting',
		trans =     trans_new
	       }
	   end
   in
	emptyStackBeforeAccept (splitTransitions pda)
   end


(*straight up copied from fa.sml. perhaps it would be worthwhile to 
rethink the notion of reachability in pda terms; or write a liftToFA 
function that would strip out stack ops in our PDA to make an FA.
I mean, we already totally depend on Forlan keeping its current contract.*)
fun reachableFrom (pda : pda) =
      let val trans = transitions pda
          fun newStates(nil,                _) = nil
            | newStates((q, x, y, z, r) :: trans, p) =
                if Sym.equal(q, p)
                then r :: newStates(trans, p)
                else newStates(trans, p)

          fun closure(nil,         olds) = olds
            | closure(new :: news, olds) =
                if SymSet.memb(new, olds)
                then closure(news, olds)
                else closure(newStates(Set.toList trans, new) @ news,
                             SymSet.union(Set.sing new, olds))

          fun reachFrom qs = closure(Set.toList qs, Set.empty)
      in reachFrom end

(*ToGram notes*)
(*Prior to conversion, PDAs are normalized so that every transition either pushes
 *or pops a single symbol or performs no stack operation, and so that the stack
 *is guaranteed to be empty before any final state can be reached.*)

(*Below are our conversion rules:
 *1. For every transition (p,x,%,%,q), add a production
 *      <p,q> -> x
 *
 *2. For every pair of transitions (p,x,%,z,q) and (r,y,z,%,s), s.t. r is reachable*
 * from q, add a production
 *      <p,s> -> x<q,r>y
 *
 * NOTE: "reachable" in this context means more than just "has transition to". 
 * Paths of this type necessarily form nested structures, like balanced parentheses.
 * eg "push a, push b, pop b, pop a, push c, pop c" is a valid path.
 * "push a, push b, pop a, ..." is NOT a valid path--b is on top of the stack, so we 
 * can't pop an a.
 * For our purposes, "reachable" includes only valid paths.
 * 
 *3. For every LH or RH variable <s,s> generated by rules 1 and 2, add a production
 *	<s,s> -> %
 *
 *4. For every pair of LH variables <p,s>,<s,q> generated so far, 
 * add a production
 *      <p,q> -> <p,s>,<s,q>
 * Repeat step 4 process until no new productions are generated.
 *
 * 
 * Because it's easy to generate productions from transitions but difficult to 
 * extract important transition information (eg states involved) from a prod, 
 * we use an object called a stateprod to keep track of information about our
 * productions.
 *
 * In ML terms
 *    type variable = Sym.sym * Sym.sym * Sym.sym
 *                    (state 1, state 2, variable representing those states)
 *    type stateprod = variable * Prod.prod * variable list
 *                    (LH variable, actual production, any RH variables in prod)
 * 
 *)

fun toGram (pda : pda) : Gram.gram =
  let val pda = normalize pda
      val stats = states pda
      val start = startState pda
      val accepting = acceptingStates pda
      val trans = transitions pda

      (*Creating unique variable names is a pain!*)
      (*Global variables are clumsy, but it's also silly to check whether our
       *complex variable names are in the PDA's alphabet every time we generate
       *a new one if the alphabet is simple--like 99.99% of alphabets will
       *probably be.*)
      (*Check with Lyn about whether the interpreter optimizes any of this.*)
      val alph = alphabet pda
      val has_complex_alph = Set.exists (fn s => (Sym.size s) > 1) alph

      fun newVarNameSimple (s1, s2) = Sym.fromString (String.concat ["<",
			                           (Sym.toString s1),",",
		                                   (Sym.toString s2),">"])

      (*Strategy: append a number onto the end of the var name until we find 
       *a varname that isn't in the alphabet. Should yield consistent, predictable
       *results for any two vars we pass it--the algorithm depends on it.*)
      fun newVarNameComplex (s1,s2) = 
	  let fun newComplex (sym1,sym2,seed) = 
		  let val v = Sym.fromString (String.concat ["<",
			           (Sym.toString s1),",",
		                   (Sym.toString s2),",",
			           (Int.toString seed),">"])
		  in 
		      if (SymSet.memb (v,alph)) then
			  newComplex (sym1,sym2,seed+1)
		      else
			  v
		  end
	  in
	      newComplex (s1,s2,0)
	  end

      val newVarName = if (has_complex_alph) then 
			   newVarNameComplex
		       else 
			   newVarNameSimple

      (*Functions for manipulating stateprods.*)
      (*The type declarations aren't necessary, but they help readers to
       understand the structure of the stateprod, so keep 'em.*)
      type variable = Sym.sym * Sym.sym * Sym.sym
      type stateprod = variable * Prod.prod * variable Set.set
      val cmpvar = Set.compareTriple (Sym.compare, Sym.compare, Sym.compare)
      val cmpsp = Set.compareTriple (cmpvar, Prod.compare, Set.compare cmpvar)

      fun makeStateProdT1 (p,x,_,_,q) = 
	  let val v = newVarName (p,q) in
	      ((p,q,v),(v,x),Set.empty)
	  end

      fun makeStateProdT2 ((p,x,_,_,q),(r,u,_,_,s)) =
	  let val (vps, vqr) = (newVarName(p,s),newVarName(q,r)) in
	      ((p,s,vps),(vps,(x @ [vqr] @ u)), (Set.sing (q,r,vqr)))
	  end

      fun makeStateProdT3 s = 
	  let val v = newVarName (s,s) in
	      ((s,s,v),(v,[]),Set.empty)
	  end

      fun makeStateProdT4 (((p,q,vpq),_,_),((r,s,vrs),_,_)) =
	  let val vps = newVarName (p,s) in
	      ((p,s,vps),(vps,[vpq,vrs]),
	       (Set.fromList cmpvar [(p,q,vpq),(r,s,vrs)]))
	  end

      fun nameOfVar (_, _, varname) = varname

      fun extractProd (_, prod, _) = prod

      fun extractVars state_prod_set = Set.genUnion cmpvar 
		(Set.mapToList
	        (fn (lv, prd, rvs) => Set.union cmpvar ((Set.sing lv), rvs))
	        state_prod_set)


      (*Step 1: trans (p,x,[],[],q) become new prods <p,q> -> x *)

      val noptrans = Set.filter (fn (p,x,[],[],q) => true | _ => false) trans

      val type1stateprods = Set.map cmpsp makeStateProdT1 noptrans


      (*Step 2: trans (p,x,[],y,q),(r,u,v,[],s) st. y=v must become a new
       production <p,s> -> x<q,r>u*)

      val trans_twice = Set.times (trans, trans)
      val reachable_init = fromRaw
				    ({stats     = stats,
				    start     = start,
				    accepting = accepting,
				    trans     = noptrans})

      fun matches ((p,x,y,z,q),(r,u,v,w,s)) = 
	  case z of
	      [] => false
	    | a :: [] => (case v of 
			      [] => false
			    | b :: [] => Sym.equal (a,b)
			    | _ => false)
	    | _ => false

      fun addTrans (pda, newtrans)
	= {stats     = states pda,
	   start     = startState pda,
	   accepting = acceptingStates pda,
	   trans     = (StackTranSet.union ((transitions pda), newtrans))}
	  
      fun unzipSet cmp s = 
	  let fun unzip nil = nil
		| unzip ((t1,t2) :: tps) = t1 :: t2 :: (unzip tps)
	  in
	      Set.fromList cmp (unzip (Set.toList s))
	  end
	  
      fun isReachableFrom pda ((_,_,_,_,q),(r,_,_,_,_)) = 
	  SymSet.memb (r,(reachableFrom pda (Set.sing q)))
	  
      fun genType2s (reachable_pda, oldstateprods) =
	  let val new_tran_pairs = Set.filter
				  (fn (t1,t2) => 
				      (matches (t1,t2))
				      andalso 
				      (isReachableFrom reachable_pda (t1,t2)))
				  trans_twice
				       
	      val new_stateprods = Set.minus cmpsp
			    ((Set.map cmpsp makeStateProdT2 new_tran_pairs),
			     oldstateprods)

	      val new_trans = unzipSet StackTran.compare new_tran_pairs
	  in
	      if(Set.isEmpty new_stateprods) then
		  oldstateprods
	      else
		  genType2s (addTrans (reachable_pda, new_trans),
			     Set.union cmpsp (oldstateprods,new_stateprods))
	  end

      val type2stateprods = genType2s (reachable_init, Set.empty)


      (*Step 3 : any variables <s,s> generated in steps 1 & 2 need to have 
	prods going to %.*)

      val type3vars = Set.filter
	              (fn (s1,s2,_) => Sym.equal (s1,s2))
	              (extractVars 
			   (Set.union cmpsp (type1stateprods,type2stateprods)))

      val type3stateprods = Set.map cmpsp 
		            makeStateProdT3
			    (SymSet.map (fn (s,_,_) => s) type3vars)

     (*Step 4: any variables <p,q>,<q,r> must have a prod <p,r> -> <p,q><q,r>*)
      fun genType4s oldstateprods = 
	  let val cross_prod = Set.times (oldstateprods,oldstateprods)
	      val shares_state = 
		  Set.map cmpsp makeStateProdT4
			 (Set.filter
			  (fn (((p,q,vpq),_,_),((r,s,vrs),_,_)) => 
			                          Sym.equal (q,r))
			  cross_prod)
	      val news = Set.minus cmpsp (shares_state, oldstateprods)
	  in
	      if (Set.isEmpty news) then
		  oldstateprods
	      else
		  genType4s (Set.union cmpsp (oldstateprods,news))
	  end

      val allstateprods = genType4s (Set.genUnion cmpsp [type1stateprods, 
							 type2stateprods,
							 type3stateprods])

      (*Finally, extract data from stateprods to create the grammar*)
      val startvar = newVarName (start, (Set.hd accepting))

      val vars = SymSet.union (Set.sing startvar, 
			   (SymSet.map nameOfVar (extractVars allstateprods)))
		 
      val prods = ProdSet.map extractProd allstateprods

  in
      Gram.fromRaw
	  ({vars   =  vars,
	    start  =  startvar,
	    prods  =  prods})
  end


fun accepted pda =
    let val gram = toGram pda
    in
	Gram.generated gram
    end

end;
