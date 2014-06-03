structure StackTranSet :> STACK_TRAN_SET =
struct

structure L  = Lex
structure M  = Messages

(******************* Specializations of Functions from Set *******************)

val fromList = Set.fromList StackTran.compare

val compare = Set.compare StackTran.compare

val memb = Set.memb StackTran.compare

val subset = Set.subset StackTran.compare

val equal = Set.equal StackTran.compare

val map = fn f => Set.map StackTran.compare f

val mapFromList = fn f => Set.mapFromList StackTran.compare f

val union = Set.union StackTran.compare

val genUnion = Set.genUnion StackTran.compare

val inter = Set.inter StackTran.compare

val genInter = Set.genInter StackTran.compare

val minus = Set.minus StackTran.compare

val powSet = Set.powSet StackTran.compare

(*input: P, x:y/z -> Q*)

fun inpBarNESymSet lts =
      let val (a, lts) = Sym.inputFromLabToks lts
      in case lts of
              (_, L.Bar) :: lts =>
                let val (bs, lts) = inpBarNESymSet lts
                in (SymSet.union(Set.sing a, bs), lts) end
            | _                 => (Set.sing a, lts)
      end

(*mod*)
fun inpTranFam lts =
      let val (q, lts)  = Sym.inputFromLabToks lts
          val lts       = L.checkInLabToks(L.Comma, lts)
          val (x, lts)  = Str.inputFromLabToks lts
	  val lts       = L.checkInLabToks(L.Colon, lts)
	  val (y, lts)  = Str.inputFromLabToks lts
	  val lts       = L.checkInLabToks(L.Slash, lts)
	  val (z, lts)  = Str.inputFromLabToks lts
          val lts       = L.checkInLabToks(L.SingArr, lts)
          val (rs, lts) = inpBarNESymSet lts
      in (map (fn r => (q, x, y, z, r)) rs, lts) end


fun inpNETranFamSet lts =
      let val (trans, lts) = inpTranFam lts
      in case lts of
              (_, L.Semicolon) :: lts =>
                let val (tran's, lts) = inpNETranFamSet lts
                in (union(trans, tran's), lts) end
            | _                       => (trans, lts)
      end

fun inpTranFamSet (lts as (_, L.Sym _) :: _) = inpNETranFamSet lts
  | inpTranFamSet lts                        = (Set.empty, lts)

val inputFromLabToks = inpTranFamSet


(******output************)
(*      P,x:y/z -> Q*)

fun symListToBarPPList nil       = nil
  | symListToBarPPList [b]       = [Sym.toPP b]
  | symListToBarPPList (b :: bs) =
      PP.decorate("", Sym.toPP b, " |") :: symListToBarPPList bs

fun symSetToBarPP bs = PP.block(true, symListToBarPPList(Set.toList bs))

fun tranFamToPP(q, x, y, z, rs) =
      PP.block(true,
               [PP.decorate("",
                            PP.block(true,
                                     [PP.comma(Sym.toPP q), 
				      PP.block(false,
					       [(Str.toPP x),
						PP.fromString(":"),
						(Str.toPP y),
						PP.fromString("/"),
						(Str.toPP z)
					       ])
				     ]),
                            " ->"),
                symSetToBarPP rs])

local
  fun toPPList ((q, x, y, z, r) :: (trans as (q', x', y', z', r') :: _), ps) =
        let val ps = SymSet.union(Set.sing r, ps)
        in if Sym.equal(q', q) andalso Str.equal(x', x) 
	      andalso (PDAStack.compareOp ((y,z),(y',z')) = EQUAL)
           then toPPList(trans, ps)
           else PP.semicolon(tranFamToPP(q, x, y, z, ps)) ::
                toPPList(trans, Set.empty)
        end
    | toPPList ([(q, x, y, z, r)], ps)                               =
        [tranFamToPP(q, x, y, z, SymSet.union(Set.sing r, ps))]
    | toPPList _                                               =
        M.cannotHappen()
in
  fun tranListToPP nil   = PP.empty
    | tranListToPP trans = PP.block(true, toPPList(trans, Set.empty))
end

fun toPP trans = tranListToPP(Set.toList trans)

fun toString trans = PP.toString(toPP trans)

fun output("",  bs) = (print(toString bs); print PP.newline)
  | output(fil, bs) =
      case SOME(TextIO.openOut fil) handle _ => NONE of
           NONE     =>
             M.errorPP
             (fn () =>
                   [PP.fromString "unable", PP.fromString "to",
                    PP.fromString "open", PP.fromString "file",
                    PP.fromString ":",
                    PP.quote(PP.fromStringSplitEscape fil)])
         | SOME stm =>
             (TextIO.output(stm, toString bs);
              TextIO.output(stm, PP.newline);
              TextIO.closeOut stm)
end;
