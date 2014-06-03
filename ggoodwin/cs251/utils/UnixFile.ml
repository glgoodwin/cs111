(* [lyn, 1/28/09] Unix file utilities, including one to change CVS Repositories *)

#use "../utils/StringUtils.ml";;
#use "../utils/File.ml";;
#load "str.cma";; (* loads string-matching library *)
#load "unix.cma";; (* loads unix library *)

(* Can only use this module in version of caml supporting Unix ops. 

   http://caml.inria.fr/pub/docs/manual-ocaml/manual035.html

   Programs that use the unix library must be linked as follows:

        ocamlc other options unix.cma other files
        ocamlopt other options unix.cmxa other files

   For interactive use of the unix library, do:

        ocamlmktop -o mytop unix.cma
        ./mytop

   or (if dynamic linking of C libraries is supported on your platform), start ocaml and type #load "unix.cma";;.

*)

module type UNIX_FILE = sig

  val forEachFile : string -> (string -> unit) -> unit
      (* perform an action on each file reachable from a given directory *)
  val changeCVSRepository: string -> string -> string -> unit
      (* changeCVSRepository startDir oldName newName 
           walks over all files rooted at startDir 
           and changes oldName to newName in every CVS/Repository file *)
end
 
module UnixFile : UNIX_FILE = struct

  open StringUtils 

  let rec forEachFile filename action = 
   try 
    let st = Unix.stat filename in 
    let kind = st.Unix.st_kind in 
    if kind = Unix.S_REG then
      (print ("\nRegular File: " ^ filename); 
       action filename)
    else if kind = Unix.S_DIR then
      (print ("\nDirectory: " ^ filename); 
       forEachFileInDir filename action)
    else if kind = Unix.S_CHR then
      print ("\n*** Ignoring Character Device: " ^ filename ^ " ***")
    else if kind = Unix.S_BLK then
      print ("\n*** Ignoring Block Device: " ^ filename ^ " ***")
    else if kind = Unix.S_LNK then
      print ("\n*** Ignoring Symbolic Link: " ^ filename ^ " ***")
    else if kind = Unix.S_FIFO then
      print ("\n*** Ignoring Named Pipe: " ^ filename ^ " ***")
    else if kind = Unix.S_SOCK then
      print ("\n*** Ignoring Socket: " ^ filename ^ " ***")
    else 
      print ("\n*** Unknown file type: " ^ filename ^ " ***")
   with Unix.Unix_error (x,y,z) -> 
      print ("\n*** Not a file: " ^ filename ^ " ***")

  and forEachFileInDir dirname action = 
    let dir = Unix.opendir dirname in 
    let rec loopThroughFiles () = 
      let next = (Unix.readdir dir) in (* reads next filename in dir *)
        if (next = ".") || (next = "..") then 
         loopThroughFiles() (* ignore here and parent files *)
        else 
        (forEachFile (Filename.concat dirname next) action; 
         loopThroughFiles())
    in try loopThroughFiles ()
       with End_of_file -> () (* readdir throws End_of_file exception when done *)

  and fileCheck expected actual = 
    if expected <> actual then
      raise (Failure ("fileCheck: expected " ^ expected ^ " but got " ^ actual))
    else ()

  let changeCVSRepository startDir oldname newname = 
    let changeRep filename = 
        if (Filename.basename filename) = "Repository"
           && (Filename.basename (Filename.dirname filename)) = "CVS"
        then
           (print "\n $$$ Found a repository! $$$";
            let lines = File.fileToLines filename in 
               if (List.length lines) = 1 then
                 let line = List.hd lines in 
                 (print ("\n$$$ Old line = " ^ line); 
                  let newLine = Str.global_replace (Str.regexp_string oldname) newname line in 
                   (print ("\n$$$ New line = " ^ newLine); 
                    print ("\n$$$ Changing old line to new line"); 
                    File.linesToFile [newLine] filename))
               else 
                 print ("*** Doesn't have one line ***"))
        else 
           ()
   in
     forEachFile startDir changeRep
     
end

