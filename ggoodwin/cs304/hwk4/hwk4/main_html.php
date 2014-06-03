<html>
<head>
<title> hwk 4 attempt 1 </title>
<link rel = "stylesheet" type="text/css" href="attempt1.css">
</head>

<body>

<!-- KEEPS FORM AND RESULTING INFO SIDE BY SIDE -->
<div class = wrapper> 

<div class = forms>

<!-- SETS UP FORMS TO SUBMIT INFORMATION -->
<form method="POST" action="<?php echo $_SERVER['PHP_SELF']?>">

<input type = "textbox" name="text" >
<input type = "submit" name = "submit1" value = "submit it"> 


<!-- SETS UP SELECTE MENU AND SETS UP SELECTION PROCESS  -->
<select name ="options" value = 'none'>

<?php

 //GETS INFO TO SET UP SELECTION MENU - make into a function?
require("db_connect.php"); 


?>

</select>

</form>
</div>

<div class = infos> 

<?php

  // GETS QUERY INFO
  
 
  
require ("form_action.php");


?>

</div>
</div>

</body>
</html>