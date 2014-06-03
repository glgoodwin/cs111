<html>
<head>
<title>ahoiz db</title>
<link rel = "stylesheet" type="text/css" href="ahoiz_db.css">

<?php
$username = $_GET['username'];
print ($username);
?>

</head>
<body>

<!-- sets up basic html to hold queries -->
<div class= "header">
some wargs.
</div>


<?php
//script name - works, checked below with echo
// echo $_SERVER['PHP_SELF'];



// The following loads the Pear MDB2 class
require_once("MDB2.php");
//require_once("/home/cs304/public_html/php/MDB2-functions.php");
require_once("ahdb-dsn.inc");
$dbh = &MDB2::factory($ahdb_dsn);
//$dbh = db_connect($ahdb_dsn);


// error message if connection fails
if(PEAR::isError($dbh)) {
    die("Error while connecting : " . $dbh->getMessage());
 }
?>

<div class = "page">
<div class = "left">
hey loogit

<?php
 // form references itself as action..?
echo "<form method=GET action=" . $_SERVER['PHP_SELF'] . ">";

?>
</br>
<input type = "text" value = "usrnm" name = "username">
<input type = "submit" name = "submitt" value = "submit it">
</form>


</div> <!-- closes left -->


<div class = "right">
<?php
// use $dbh
// select and print out list of actor names




$sql = "SELECT nm, name FROM person";
$resultset = $dbh->query($sql);
if(PEAR::isError($resultset)) {
    die('Failed to issue query, error message : ' . $resultset->getMessage());
 }

while($row = $resultset->fetchRow(MDB2_FETCHMODE_ASSOC)) {
    echo "<li> nm " . $row['nm'] . ": " . $row['name'] . "\n";
 }

?>
</div> <!-- closes right -->
</div> <!-- closes page -->

yo?








</body>
</html>
