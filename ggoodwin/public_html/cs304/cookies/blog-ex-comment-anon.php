<?php

require_once("MDB2.php");
require_once("/home/cs304/public_html/php/MDB2-functions.php");
require_once("/home/cs304/public_html/php/blog-functions.php");
require_once("/home/cs304/public_html/php/webdb-dsn.inc");


$dbh = db_connect($webdb_dsn);

if(isset($_POST['new_entry'])) {
  $poster = $_COOKIE['poster'];
     insertPost($dbh,$poster,$_POST['new_entry']);
}

printPageTop('Blog 304: Read/Post');

printPostings($dbh);

print "<hr>\n";

printCommentForm1();
?>

</body>
</html>


