<!-- Alex H. and Gabe G. -->
<!-- CS 304 October 18, 2012    -->
<!-- This page handles all queries initiated from the textbox -->

<html>
<head>
<title> hwk 4 attempt 1 </title>
<link rel = "stylesheet" type="text/css" href="attempt1.css">
</head>

<body>

<div class = header>
<h1> Search the Wellesley Movie Database </h1>
</div>
<!-- KEEPS FORM AND RESULTING INFO SIDE BY SIDE -->
<div class = wrapper> 

<div class = forms>

<!-- SETS UP FORMS TO SUBMIT INFORMATION -->
<form method="POST" action="main_html.php">





<!-- SETS UP SELECT MENU AND SETS UP SELECTION PROCESS  -->
<select name ="options" value = 'none'>

<?php

 //GETS INFO TO SET UP SELECTION MENU - make into a function?
require("db_connect.php"); 
require("functions.php");


?>

</select>
</br>
<input type = "textbox" name="text" >
</br>
<input type = "submit" name = "submit1" value = "search"> 
</form>

</div>

<div class = infos> 

<?php

  // GETS QUERY INFO
  

 //IF AN OPTION HAS BEEN SELECTED...
if (isset($_POST['options'])) {


// GIVE VARIABLE NAMES TO TEXT FIELD AND SELECTED OPTION
$text = $_POST['text'];
$option = $_POST['options'];


// DETERMINE IF SUBMIT HAS BEEN CLICKED..
if(isset($_POST['submit1'])) {

// THEN PRINT PAGE BASED ON ENTERED DATA
print_page($option, $text);


 } //else do nothing, wait.
}




// ALL MATERIAL IN "TEST-INFO" FILE WAS HERE  

 
 
 // 
//  
//  
//   // GETS QUERY INFO
//  $value = $_GET['nm'];
//  
//  
//  // DISPLAYS PAGE
//  actor_page_nm($value);
  


//require ("form_action.php");


?>



</div>
</div>

</body>
</html>