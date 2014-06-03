(* History: 

   1/27/09: Added close_in channel to file-reading functions. 

 *)

module type FILE = sig

  val fileToString : string -> string (* filename to contents *)
  val fileToLines : string -> string list (* filename to lines *)
  val fileToWords : string -> string list (* filename to words *)
  val stringToFile : string -> string -> unit (* string to filename to done *)
  val linesToFile : string list -> string -> unit (* lines to filename to done *)
  val fileToLineArray : string -> string array (* filename to contents *)
  val lineArrayToFile : string array -> string -> unit (* filename to contents *)
  val randomizeLines : string -> string -> unit (* filename to contents *)
  val filterLines : (string -> bool) -> string -> string -> unit 
      (* predicate to infilename to outfilename to done *)
  val mapLines : (string -> string) -> string -> string -> unit 
      (* string transform to infilename to outfilename to done *)

end
 
module File : FILE = struct

  (* (* Recursive version can use too much stack space for large files *)
  let fileToLines filename = 
    let channel = open_in filename in 
      let rec readLoop strings = 
        try 
          let line = input_line channel in 
            readLoop (line :: strings)
         with 
           End_of_file -> (close_in channel; List.rev strings)
       in readLoop []
    *)

  let fileToLines filename = 
    let channel = open_in filename in 
      let rec readLoop strings = 
        match (try 
                 Some (input_line channel)
               with 
                 End_of_file -> None)
        with 
          Some line -> readLoop (line :: strings)
        | None -> (close_in channel; List.rev strings)
       in readLoop []


  (* (* Recursive version can use too much stack space for large files *)
     let fileToWords filename = 
     List.flatten (List.map StringUtils.stringToWords (fileToLines filename))
   *)

   let fileToWords filename = 
    let channel = open_in filename in 
      let rec readLoop strings = 
        match (try 
                 Some (input_line channel)
               with 
                 End_of_file -> None)
        with 
          Some line -> let words = StringUtils.stringToWords line in 
                         readLoop ((List.rev words) @ strings)
        | None -> (close_in channel; List.rev strings)
       in readLoop []

  let fileToString filename = 
    String.concat "\n" (fileToLines filename)

  let stringToFile string filename =
    let channel = open_out filename in
       (output_string channel string;
        close_out channel)

  let linesToFile lines filename =
    let channel = open_out filename in
      (List.iter (fun s -> output_string channel (s ^ "\n")) lines; 
       close_out channel)

  let fileToLineArray filename = 
    let lineList = fileToLines filename in 
    let len = List.length lineList in
    let lineArray = Array.make len "" in 
    let rec init i xs = 
      match xs with 
        [] -> ()
      |	(x::xs') -> (Array.set lineArray i x; 
                     init (i+1) xs')
    in (init 0 lineList; lineArray)

  let lineArrayToFile lineArray filename = 
    let channel = open_out filename in
    (Array.iter (fun s -> output_string channel (s ^ "\n"))
       lineArray;
     close_out channel) 

  (* Randomize the lines in a file *)
  let randomizeLines infile outfile = 
    let lineArray = fileToLineArray infile in 
    let swap i j = 
     (let v = Array.get lineArray i in 
      let _ = Array.set lineArray i (Array.get lineArray j) in 
        Array.set lineArray j v) in 
    (* Create a random permutation of numArray *)
    let _ = Random.init(1234567) in
    let rec permute i = 
      if i < 0 then 
        ()
      else 
        (swap i (Random.int (i + 1)); 
         permute (i-1)) in 
     let _ = permute ((Array.length lineArray) - 1)
     in lineArrayToFile lineArray outfile

  (* Filter the lines in a file *)
  let filterLines pred infile outfile = 
    linesToFile 
      (List.filter pred (fileToLines infile))
      outfile

  (* Map over the lines in a file *)
  let mapLines f infile outfile = 
    linesToFile 
      (List.map f (fileToLines infile))
      outfile

end
