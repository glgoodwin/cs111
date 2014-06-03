<?php
require ("db_connect.php");
require("functions.php");

 //IF AN OPTION HAS BEEN SELECTED...
if (isset($_POST['options'])) {


// GIVE VARIABLE NAMES TO TEXT FIELD AND SELECTED OPTION
$text = $_POST['text'];
$option = $_POST['options'];


// DETERMINE IF SUBMIT HAS BEEN CLICKED, then act accordingly
if(isset($_POST['submit1'])) {


print_page($option, $text);


//TEST INFO
//display_actors_movies($text);
//display_titles_actors($text);

 }
}




// ALL MATERIAL IN "TEST-INFO" FILE WAS HERE  

?>