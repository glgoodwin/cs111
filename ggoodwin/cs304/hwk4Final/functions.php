<-- Alex H. and Gabe G. -->
<-- CS 304 October 18, 2012    -->
<-- This page contains all queries used to build this site -->


<?php

// FUNCTION: GETS DB HANDLE

function getDBH($query) {
global $dbh;
$result = $dbh->query($query);
if(PEAR::isError($result)) {
    die('Failed to issue query, error message : ' . $result->getMessage());
 }
 return $result;
}




// FUNCTION: GIVES TABLE TO USE IN QUERY BASED ON OPTION SELECTED

function getTable($selected_option) {
$table;
if ($selected_option == 1) { 
  $table = "person";   
 } else if ($selected_option == 2) {
  $table = "movie";
} else {
  echo "oh yeah, why not both";
}
return $table;
 }



// FUNCTION: GIVES COLUMN TO USE IN QUERY BASED ON OPTION SELECTED

function getColumn($selected_option) {
$column;
if ($selected_option == 1) { 
  $column = "name";   
 } else if ($selected_option == 2) {
  $column = "title";
} else {
  echo "oh yeah, why not both";
}
return $column;
 }



// FUNCTION: GET COUNT

// RETURNS NUMBER OF ENTRIES BASED ON WHAT TO COUNT ON, AND USER-ENTERED STRING
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


// FUNCTION: PRINT ALL ACTORS THAT WERE IN TITLE (movie) (as hyperlinks)
function display_titles_actors($title) {
global $dbh;
$q= "select person.name, person.birthdate, person.nm from credit inner join movie using (tt) inner join person using (nm) WHERE title = '$title'";
$result = getDBH($q);

while($row = $result->fetchRow(MDB2_FETCHMODE_ASSOC)) {

$nmm = $row['nm'];
$rr = $row['name'];

echo "<a href='search_nm.php?nm=$nmm'>" . $rr . "</a> </br>";
 
}
}




// FUNCTION: PRINT ALL MOVIES ACTOR WAS IN (as hyperlinks)
function display_actors_movies($name) {
global $dbh;
$q= "select movie.title, movie.release, movie.tt from credit inner join movie using (tt) inner join person using (nm) WHERE name = '$name'";
$result = getDBH($q);

while($row = $result->fetchRow(MDB2_FETCHMODE_ASSOC)) {

$ttt = $row['tt'];
$rr = $row['title'];

echo "<a href='search_tt.php?tt=$ttt'>" . $rr . "</a> </br>";
 
}
}




//RETURN ACTOR'S MAIN PAGE
function actor_page ($table, $column, $search_string) {
global $dbh;
$q = "SELECT name, birthdate FROM $table where $column like '%$search_string%'";
$result = getDBH($q);
$row = $result->fetchRow(MDB2_FETCHMODE_ASSOC);
$name = $row['name'];

echo "<h1>" . $row['name'] . "</h1>";
echo "<h2> born on " . $row['birthdate'] . "</h2>";

display_actors_movies($name);
}


//RETURN ACTOR'S MAIN PAGE -- USING NM

function actor_page_nm ($nm) {
global $dbh;
$q = "SELECT name, birthdate FROM person where nm = $nm";
$result = getDBH($q);
$row = $result->fetchRow(MDB2_FETCHMODE_ASSOC);
$name = $row['name'];

echo "<h1>" . $row['name'] . "</h1>";
echo "<h2> born on " . $row['birthdate'] . "</h2>";

display_actors_movies($name);
}




//RETURN MOVIE'S MAIN PAGE
function movie_page ($table, $column, $search_string) {
global $dbh;
$q = "SELECT title, `release` FROM $table where $column like '%$search_string%'";
$result = getDBH($q);
$row = $result->fetchRow(MDB2_FETCHMODE_ASSOC);
$title = $row['title'];

echo "<h1>" . $row['title'] . "</h1>";
echo "<h2> release date:  " . $row['release'] . "</h2>";

display_titles_actors($title);
}



//RETURN MOVIE'S MAIN PAGE -- USING TT

function movie_page_tt ($tt) {
global $dbh;
$q = "SELECT title, `release` FROM movie where tt= $tt";
$result = getDBH($q);
$row = $result->fetchRow(MDB2_FETCHMODE_ASSOC);
$title = $row['title'];

echo "<h1>" . $row['title'] . "</h1>";
echo "<h2> Released in " . $row['release'] . "</h2>";

display_titles_actors($title);
}







// RETURN HYPERLINK LIST OF ACTORS

function actor_links($table, $column, $search_string) {
global $dbh;
$q= "SELECT name, birthdate, nm FROM $table where $column like '%$search_string%'";
$result = getDBH($q);

echo "<h2>Did you mean one of these actors?</h2>";

while($row = $result->fetchRow(MDB2_FETCHMODE_ASSOC)) {
// echo "<li> " . $row['name'] . ": " . $row['birthdate'] . "\n";
 
   $nmm = $row['nm'];
   
 $rr = $row['name'];
 echo "<a href='search_nm.php?nm=$nmm'>" . $rr . "</a> </br>";
 
} 
}





// RETURN HYPERLINK LIST OF MOVIES

function movie_links($table, $column, $search_string) {
global $dbh;
$q= "SELECT title, `release`, tt FROM $table where $column like '%$search_string%'";
$result = getDBH($q);


echo "<h2>Did you mean one of these movies?</h2>";

while($row = $result->fetchRow(MDB2_FETCHMODE_ASSOC)) {
 //echo "<li> " . $row['title'] . ": " . $row['release'] . "\n";
 
  $ttt = $row['tt'];
  
 $rr = $row['title'];
 echo "<a href='search_tt.php?tt=$ttt'>" . $rr . "</a> </br>";

} 
}


// FUNCTION: DETERMINE WHAT PAGE TO DISPLAY BASED ON 
// SELECTED OPTION AND STRING ENTERED BY USER
	
function print_page($option, $search_string) {

$table = getTable($option);
$column = getColumn($option);
$count = get_count($column,$search_string, $table);


//IF 'ACTOR' IS SELECTED..
if($option == 1){

// AND IF 'COUNT' = 1...
if ($count == 1) {

//RETURN ACTOR'S MAIN PAGE
actor_page($table, $column, $search_string);

// BUT IF COUNT = 2...
} else {  

// PRINT LIST OF CHOICES 
actor_links($table, $column, $search_string);

}
} else { // TITLE IS SELECTED...

// AND IF 'COUNT' = 1...
if ($count== 1) {

// DISPLAY MOVIE'S MAIN PAGE
movie_page ($table, $column, $search_string);

// BUT IF COUNT = 2...
} else {  


// PRINT LIST OF CHOICES 
movie_links($table, $column, $search_string);

}
}
}





//----------------------------------
//while($row = $result->fetchRow(MDB2_FETCHMODE_ASSOC)) {
 //echo "<li> " . $row['title'] . ": " . $row['release'] . "\n";
//}
//}



// TEST
//$theColumn = getColumn(1);
//echo $theColumn;







?>