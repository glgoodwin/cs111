
<?php

// QUERY1: RETURNS ID AND NAME OF ACTOR REQUESTED IN SEARCH 

$names = "SELECT nm, name FROM person WHERE name like '%$textvalue%'"; 
$resulting_names = $dbh->query($names);
if(PEAR::isError($resulting_names)) {
    die('Failed to issue query, error message : ' . $resulting_names->getMessage());
 }


// PRINT OUT RESULTS OF QUERY1 
while($row = $resulting_names->fetchRow(MDB2_FETCHMODE_ASSOC)) {
    echo "<li> nm " . $row['nm'] . ": " . $row['name'] . "\n";
 }
 




?>