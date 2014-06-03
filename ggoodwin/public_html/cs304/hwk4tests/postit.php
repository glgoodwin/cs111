<html>
<head>
<title> test post </title>


</head>

<body>

<form method="POST" action="<?php echo $_SERVER['PHP_SELF']?>">
<input type = "textbox" name="text">
</br>
</br>
This is what was just in the textbox:  
<?php
$textvalue = $_POST['text']; //? $_POST['text'] : null);
echo $textvalue;
echo "</br>";


if($textvalue == "yo dudes") {
  echo ("we got your back bro");
 }
 else {
   echo ("hey new dude, not cool man");
 }
 
  


?>

</br>
<input type = "submit" name = "submit1" value = "submit it"> 

</form>
</body>
</html>