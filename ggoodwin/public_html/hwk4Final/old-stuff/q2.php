

// QUERY2: RETURNS SOMETHING ELSE
$names = "SELECT nm, name FROM person WHERE name = '$textvalue'"; 
$resulting_names = $dbh->query($names);
if(PEAR::isError($resulting_names)) {
    die('Failed to issue query, error message : ' . $resulting_names->getMessage());
 }


// PRINT OUT RESULTS OF QUERY2
while($row = $resulting_names->fetchRow(MDB2_FETCHMODE_ASSOC)) {
    echo "<li> nm " . $row['nm'] . ": " . $row['name'] . "\n";
 }
 