/* HW6 Java Servlets
 */

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.lang.*;

public class MovieUpdate extends HttpServlet {

 final static String DIRECTIONS = "<p>" +
        "This is a web application that lists movies that lack directors, and " +
        "allows you to specify the director for a movie.  You can either correct " +
        "an existing director or provide one that is missing.";

    // Here's where all the real work happens

    private void doRequest(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();

        Connection dbh = null;
        try {
            printPageHeader(out);
            dbh = Webdb.connect("wmdb");
            processForm(req,out,dbh);
            printForm(out,dbh);
        }
        catch (SQLException e) {
            out.println("Error: "+e);
        }
        catch (Exception e) {
            e.printStackTrace(out);
        }
        finally {
            close(dbh);
        }
        out.println("</body>");
        out.println("</html>");
    }


    /** Close the database connection. Should be called in a "finally"
     * clause, so that it gets done no matter what. */

    private void close(Connection dbh) {
        if( dbh != null ) {
            try {
                dbh.close();
            }
            catch( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    private void printPageHeader(PrintWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Movie Update Page</title>");
        out.println("</head>");
        out.println("<body>");
        out.println(DIRECTIONS);
    }

 private void processForm(HttpServletRequest request, PrintWriter out, Connection dbh)
        throws SQLException
    {
      if (submit == null ) return;
      if (submit == button1){

      }
      if (submit == button2){

      }

      if (submit = button3){
        String title = request.getParameter("title");
        if( title == null || title.equals("") ) return;
        String nm  = request.getParameter("directorid");
        String ry  = request.getParameter("releaseyear");
        String tt  = request.getParameter("movieid");
	String director  = request.getParameter("director");
      }

      if (submit = button4){

      }
    }




	private boolean updateDatabase(Connection dbh, PrintWriter out, String title, String nm, String ry, String tt, String director)
        throws SQLException, AmbiguousDataException
    {
        boolean result;   /* true if any person matches the given name */
 
        PreparedStatement query1 = dbh.prepareStatement
            ("SELECT tt,title,release,director FROM movie WHERE title LIKE concat('%',?,'%')");
        query1.setString(1,title);

        ResultSet rs1 = query1.executeQuery();

        // out.println("<p>Looking up "+name);
        if( ! rs.next() ) {
            result = false;
            // There's no such entry, so insert the data.  
            //PreparedStatement insert = dbh.prepareStatement
                ("INSERT INTO name(nm,name,birthdate,addedby) VALUES (?,?,?,1)");
            //insert.setString(1,nm);
            //insert.setString(2,name);
            //insert.setString(3,birthdate);
            //insert.execute();
        } else {
	  result = true;
            tt = rs.getString(1);
            title = rs.getString(2);
	    release = rs.getString(3);
	    director = rs.getString(4);

	    PreparedStatement query2 = dbh.prepareStatement
            ("SELECT nm, FROM person WHERE name LIKE concat('%',?,'%')");
	    query2.setString(1,name);

	    ResultSet rs2 = query2.executeQuery();
	    directorid = rs.getString(1);



        /* Now, update the director of the movie */
	    // PreparedStatement update = dbh.prepareStatement
            //("UPDATE movie SET director = ? WHERE tt = ?");
	    //update.setString(1,nm);
	    //update.setString(2,tt);
	    //update.executeUpdate();

        return result;
	}
    }


  /* Set up the webform */

    private void printForm(PrintWriter out, Connection dbh)
      {
    String button1 = "Choose using menu";
    String button2 = "Delete this movie";
    String button3 = "Search by title";
    String button4 = "Update Movie";
        throws SQLException
    
        out.println("<form method='post' action='MovieUpdate'>");
        out.println("<p>Title <input type='text' name='title'>");
        out.println("<p>Movie id <input type='text' name='movieid'>");
        out.println("<p>Release year <input type='text' name='releaseyear'>");
	out.println("<p>Director id <input type='text' name='directorid'>");
	out.println("<p>Director <input type='text' name='director'>");
        out.println("<p>Movies without directors or release years:");
        out.println("<select name='movieid'>");
        Statement movies = dbh.createStatement();
        ResultSet rs = movies.executeQuery
            ("SELECT tt,title FROM movie WHERE director IS NULL or movie.release IS NULL");
        while( rs.next() ) {
            out.println("<option value='" +
                        rs.getString(1) +
                        "'>" +
                        rs.getString(2));
        }
        out.println("</select>");
        out.println("<p><input type='submit' name='submit' value='"+button1+"'>");
	out.println("<p><input type='submit' name='submit' value='"+button2+"'>");
	out.println("<p><input type='submit' name='submit' value='"+button3+"'>");
	out.println("<p><input type='submit' name='submit' value='"+button4+"'>");
	//	out.println("<p><input type='Delete this movie'>");
	//out.println("<p><input type='Search by title'>");
	//out.println("<p><input type='Update movie'>");

        out.println("</form>");
    }





    // ================================================================

    // These are the entry points for HttpServlets
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        doRequest(req,res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        doRequest(req,res);
    }



}