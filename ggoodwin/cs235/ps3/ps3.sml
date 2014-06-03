(* This is the file ps3.sml.
   It begins with some given definitions. 
   At the bottom of the file, you should add your definitions for PS3.  *)

fun extend (pre, strings) = 
  List.map (fn str => str ^ "s")
           (List.filter (String.isPrefix pre) strings)

fun caesar (n,str) = 
  String.implode
    (List.map (fn c => Char.chr (((Char.ord c) + n) mod 256))
              (String.explode str))

fun twist (c,s) = 
  let val (a,b) = List.partition (fn x => x <= c) (String.explode s)
  in String.implode (a @ b)
  end

fun transform s = 
  let val len = String.size s
      fun process i = 
	    if i = len then 
              ""
            else let val rest = process (i+1)
                     val c = String.sub (s,i)
		 in if Char.isAlpha c then 
                       (String.str (Char.toLower c)) ^ rest
		    else
		      rest
		 end
  in process 0
  end

fun first s = String.str (String.sub (s,0))

fun butFirst s = String.substring (s, 1, (String.size s) - 1)

val LTests = ["", 
              "a", "b", 
              "aa", "ab", "ba", "bb", 
              "aaa", "aab", "aba", "abb", "baa", "bab", "bba", "bbb"]


(*********************** Put your definitions below **************************)




(* Non Recursive Transform *)
fun transform2 (s) = 
        
	String.implode (List.map(fn l => Char.toLower l) (List.filter (fn c => Char.isAlpha c) (String.explode s)))


(* Recursive Count ab *)	
fun countabRec s = 
    if s = "" then 
	(0,0)
    else
	let val (a,b) = countabRec (butFirst s)
	in if  first s = "a" then
	    ((fn z => z+1) a, b)
	   else if first s = "b" then
	       (a,(fn y => y+1) b)
		else 
		    (a,b)
	end 
    
(* Iterative Count ab *)
fun countabIter s = 
    let val len = String.size s
	fun loop (i, a, b) =
	   
	    if i = len then 
		(a,b)
		else if String.sub (s,i) = #"a" then 
		    loop (i+1, a+1, b)
		  
		    else if String.sub (s,i) = #"b" then
		       loop (i+1, a, b+1)
			else 
			   loop (i+1,a,b)
			   
	  in loop (0,0,0)
	     end  

fun isInL1 s =
    let val (a,b) = countabRec s
	in if a mod 2 = 0 then
	    true
	    else if b mod 2 > 0 then
		true
		else 
		    false
    end

fun genstringsHelp (sl1, sl2) =
 
  if length sl1 = 0  then
       [""]
	else
     	List.map(fn c =>hd sl1 ^ c) sl2 @ genstringsHelp(tl sl1, sl2) 


fun genStrings (n, charList)=
    if n = 0 then
	[""] 
    else
	    let  val stringList = genStrings (n-1, charList)
		 val strings = List.map(fn c => Char.toString c) charList 
	    in genstringsHelp(stringList, strings)
	    end
  
