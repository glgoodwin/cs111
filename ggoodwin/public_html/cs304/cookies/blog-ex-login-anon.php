<?php

require_once("MDB2.php");
require_once("/home/cs304/public_html/php/MDB2-functions.php");
require_once("/home/cs304/public_html/php/blog-functions.php");
require_once("/home/cs304/public_html/php/webdb-dsn.inc");

$dbh = db_connect($webdb_dsn);

$msg = "";
$login = "";

if(isset($_POST['user'])) {

    $user = $_POST['user'];
    if( loginCredentialsAreOkay($dbh,$user,$_POST['pass']) ) {
      setcookie('poster',$user);
          $msg = "<p>Welcome, $user!\n" . 
        "Click here to get started <a href='blog-ex-comment-anon.php'>commenting</a>\n";
    } else {
        $msg = "<p>Sorry, that's incorrect.  Please try again\n";
    }
}

printPageTop('Blog 304: Login');

// Finally, we can print the result of the login attempt.
print $msg;

printLoginForm();

?>

</body>
</html>
