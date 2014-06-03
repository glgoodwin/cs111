<?php

/*
// FUNCTION: GET COUNT

// RETURNS NUMBER OF ENTRIES BASED ON WHAT TO COUNT ON, AND USER ENTERED STRING
function get_count($column, $searchString, $table) {
global $dbh;

// QUERIES THE DATABASE USING SPECIFIED PARAMETERS
$query = "SELECT count($column) AS n FROM $table where $column like  '%$searchString%'";
$result = $dbh->query($query);
if(PEAR::isError($result)) {
    die('Failed to issue query, error message : ' . $result->getMessage());
 }

// GETS ROW WITH COUNT, ASSIGNS VARIABLE
$row = $result->fetchRow(MDB2_FETCHMODE_ASSOC);
$theCount = $row['n'];

return $theCount;
}




*/





//PRINT OUT RESULTS OF QUERY2
//while($row = $resulting_count->fetchRow(MDB2_FETCHMODE_ASSOC)) {
  //echo "<li>  " . $row['name'] . ": " . $row['birthdate'] . "\n";
  //}
 







?>