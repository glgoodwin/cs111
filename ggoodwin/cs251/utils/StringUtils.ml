module type STRING_UTILS = sig
  val print: string -> unit
  val println: string -> unit
  val intToString: int -> string
  val floatToString: float -> string
  val boolToString: bool -> string
  val charToString: char -> string
  val pairToString: ('a -> string) -> ('b -> string) -> ('a * 'b) -> string
  val tripleToString: ('a -> string) -> ('b -> string) -> ('c -> string) 
                        -> ('a * 'b * 'c) -> string
  val listToString: ('a -> string) -> ('a list) -> string
  val optionToString: ('a -> string) -> ('a option) -> string
  val explode: string -> char list
  val implode: char list -> string
  val stringToWords: string -> string list 
  val fresh: string -> string 
end


module StringUtils : STRING_UTILS = struct

  (* Unbuffered string printers *)
  let print s = (print_string s; flush_all ())

  let println s = (print_string s; print_string "\n"; flush_all ())

  let intToString i = string_of_int i

  let floatToString f = string_of_float f

  let boolToString b = string_of_bool b

  let charToString c = String.make 1 c

  let pairToString fstToString sndToString (a,b) =
    "(" ^ (fstToString a) ^ ", " ^ (sndToString b) ^ ")" 

  let tripleToString fstToString sndToString thdToString (a,b,c) =
    "(" ^ (fstToString a) ^ ", " 
        ^ (sndToString b) ^ ", " 
        ^ (thdToString c) ^ ")" 

  (* (* This version is inefficient *)
  let listToString eltToString xs = 
    match xs with 
      [] -> "[]"
    | x::xs' -> 
        "[" ^ (eltToString x) 
            ^ (List.fold_right (fun x s -> "; " ^ (eltToString x) ^ s)
                          xs'
                          "]")
  *)

  let listToString eltToString xs = 
    "[" ^ (String.concat "; " (List.map eltToString xs)) ^ "]"

  let optionToString eltToString opt =
    match opt with 
      None -> "None"
    | Some x -> "Some " ^ (eltToString x)
   
  (* Converts a string to a list of chars *)
  let explode str = 
    let len = String.length str in
    let rec walk i = 
      if i >= len then 
        []
      else 
        (String.get str i) :: (walk (i+1))
    in walk 0

  (* Converts a list of chars to a string *)
  let implode chars = 
    let len = (List.length chars) in 
    let result = String.create len in 
     let rec fillLoop i cs = 
           if i >=  len then 
             result
           else 
             (String.set result i (List.hd cs); 
              fillLoop (i+1) (List.tl cs))
      in fillLoop 0 chars 

  (* Returns the list of words in a string. 
     A word is a continous sequence of letters and digits, including
     internal hyphens, underscores, and single quotes. Ignores all
     other punctuation. Maintains capitalization. *)

  let stringToWords s = 
    (* scan left to right looking for words *)
    let len = String.length s in 
      let rec scanWords i = 
        if i >= len then 
          [] 
        else 
          let c = String.get s i in 
            if isWordChar c then 
              scanWords' (i+1) [c]
            else
              scanWords (i+1) (* Skip non-word chars *)

      and scanWords' i revChars = 
        if (i >= len) then 
          [implode(List.rev revChars)]
        else 
          let c = String.get s i in 
            if isWordChar(c) || 
               (isInternalChar(c) && i+1 < len && isWordChar(String.get s (i+1)))
            then 
              scanWords' (i+1) (c::revChars)
            else 
              (implode(List.rev revChars))::(scanWords (i+1))

      and isWordChar c = 
           ('a' <= c && c <= 'z')
        || ('A' <= c && c <= 'Z')
        || ('0' <= c && c <= '9')

      and isInternalChar c = c = '-' || c = '_' || c = '\''

      in scanWords 0

  (* fresh creates a "fresh" name for the given string
     by adding a "." followed by a unique number.
     If the given string already contains a dot, 
     fresh just changes the number. E.g., fresh "foo.17"
     will give a string of the form "foo.XXX" *)
  let fresh = 
    let counter = ref 0 in 
      fun str -> 
	let base = (try let i = String.index str '.' in String.sub str 0 i    
                    with Not_found -> str) in 
	let count = !counter in 
	let _ = counter := count + 1 in 
  	  base ^ "." ^ (string_of_int count)

end
