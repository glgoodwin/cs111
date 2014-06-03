<?php

 // CONNECT TO DATABASE

require_once("MDB2.php");
require_once("ahdb-dsn.inc");

$dbh = &MDB2::factory($ahdb_dsn);

// error message if connection fails
if(PEAR::isError($dbh)) {
    die("Error while connecting : " . $dbh->getMessage());
 }


// CREATE MENU OPTIONS

  //creates array for the menu options
$searchfor = array( 'choose one', 'Actor','Title','Both');
 
//determines if and what has been selected
if (isset($_POST['search_choice'])) {
  $selected_option = $_POST['search_choice'];
 } else {
  $selected_option = 'none';
 }



// FILL OPTIONS  into menu. not totally sure how this works.
for ($i = 1; $i <=3; $i++) {
  $selection = $searchfor[$i];
  if ($i == $selected_option) {
    echo "<option value= '$i' selected> $selection </option>\n";
  } else {
    echo "<option value ='$i'> $selection </option>\n";
  }

 }

// TEXT BOX IS CREATED IN MAIN FILE


?>
