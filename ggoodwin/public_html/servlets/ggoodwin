import java.io.*;               // for Printwriter
import java.sql.*;              // for Connection

public class Webdb {

    private static final String HOSTNAME = "puma.wellesley.edu";
    private static final String USERNAME = "webdb";
    private static final String PASSWORD = "sybase";

    public static Connection connect(String database)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {

        // protocol:subprotocol://host/database
        String url = "jdbc:mysql://"+ HOSTNAME + "/" + database;
        Connection con = null;

        // The following loads and instantiates the Java Module (Class)
        // called Connector/J which implements the JDBC API to the MySQL
        // database.  Connector/J is the official JDBC driver for MySQL.
        // See online documentation at mysql.com.  To use it, you have to
        // have the top-level directory or the jar file on your CLASSPATH.
        // The invocation of newInstance() is for certain buggy JVMs where
        // the static class initializer isn't run until an instance is
        // made.
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance(); // yuck
        }
        catch( ClassNotFoundException e ) {
            // This output will go into the logs. Look in $CATALINA_HOME/logs/catalina.out
            System.err.println("Couldn't load MySQL Driver: " + e);
            throw e;
        }
        catch( InstantiationException e ) {
            // This output will go into the logs. Look in $CATALINA_HOME/logs/catalina.out
            System.err.println("Couldn't load MySQL Driver: " + e);
            throw e;
        }
        catch( IllegalAccessException e ) {
            // This output will go into the logs. Look in $CATALINA_HOME/logs/catalina.out
            System.err.println("Couldn't load MySQL Driver: " + e);
            throw e;
        }
        
        try {
            con = DriverManager.getConnection(url,USERNAME,PASSWORD);
        }
        catch (SQLException e) {
            System.err.println("Cannot connect to database: " + e); 
            throw e;
        }
        return con;
    }

    public static void printException(Exception e, PrintWriter out)
    {
        out.println("<p>Exception"+e.toString()+"<br>");
        out.println("<pre>");
        // Stack Trace is ugly, but gives line numbers
        e.printStackTrace(out);  
        out.println("</pre>");
    }
}
