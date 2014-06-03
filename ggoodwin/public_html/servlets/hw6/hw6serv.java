import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.lang.*;

/* Written by Scott D. Anderson
   Scott.Anderson@acm.org
   March 2005

   This servlet allows someone to fill in a director for a movie that is
   missing one.  We accept the director's name, id, and birthdate
   (trusting those) and the movie is chosen from a menu of those that
   don't have directors.  You can give just the director's name if you
   know they are in the database, but we're trusting you on this.

   Bugs:  should check the types of form inputs.  Trust the user less.

   Updated, March 2008 to use the Webdb object and simplify the code.
   Also, removed instance variables that might not be thread-safe.

*/

public class DirectorUpdate extends HttpServlet {

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

    /** Process the request data.  We trust that it's all there; ideally,
     * we should check and complain if stuff is missing or has invalid
     * values.  For example, we're just trusting this date, but what if
     * the format is wrong? */

    private void processForm(HttpServletRequest request, PrintWriter out, Connection dbh)
        throws SQLException
    {
        String title = request.getParameter("title");
        if( title == null || title.equals("") ) return;
        String nm  = request.getParameter("directorid");
        String ry  = request.getParameter("releaseyear");
        String tt  = request.getParameter("movieid");
	String director  = request.getParameter("director");
        try {
            if(updateDatabase(dbh,out,director,nm,bd,tt)) {
                out.println("<p>updated this movie in the database");
            } else {
                out.println("<p>Director was already in the database");
            }
            out.println("<p>Updated this movie");
        }
        catch (AmbiguousDataException e) {
            out.println("<p>The name "+director+" was ambiguous.  Please try again, being more specific.");
        }
    }

    /** Updates the database with this director's data.  We insert the
        person, if necessary, and update the movie.  Returns true if the
        person needed to be added. In normal use, the method prints
        nothing, so that it can be more flexible and re-usable, but the
        PrintWriter is supplied for debugging. */

    private boolean updateDatabase(Connection dbh, PrintWriter out, String name, String nm, String birthdate, String tt)
        throws SQLException, AmbiguousDataException
    {
        boolean result;   /* true if any person matches the given name */
        
        /* Note the use of the concat(). This is a MySQL function that
        concatenates its arguments.  It's necessary here because MySQL
        doesn't look inside strings for the ? marker of a perpared
        statement, which makes sense.  We do, however, want to put %
        markers around the user's string, and by doing the concatenation
        in MySQL, the resulting query might be a bit more efficient.  At
        least, it can't hurt to try. */

        PreparedStatement query = dbh.prepareStatement
            ("SELECT nm,name FROM person WHERE name LIKE concat('%',?,'%')");
        query.setString(1,name);

        ResultSet rs = query.executeQuery();

        // out.println("<p>Looking up "+name);
        if( ! rs.next() ) {
            result = true;
            // There's no such entry, so insert the data.  
            PreparedStatement insert = dbh.prepareStatement
                ("INSERT INTO name(nm,name,birthdate,addedby) VALUES (?,?,?,1)");
            insert.setString(1,nm);
            insert.setString(2,name);
            insert.setString(3,birthdate);
            insert.execute();
        } else {
            nm = rs.getString(1);
            name = rs.getString(2);
            /* Check if there's a second match.  If so, complain.  If not, assume the single match is correct. */
            if( rs.next() ) {
                throw new AmbiguousDataException();
                
            } else {
                result = false;
                out.println("<p>That director has name "+name+" and id "+nm);
            }
        }

        /* Now, update the director of the movie */
        PreparedStatement update = dbh.prepareStatement
            ("UPDATE movie SET director = ? WHERE tt = ?");
        update.setString(1,nm);
        update.setString(2,tt);
        update.executeUpdate();

        return result;
    }

    /** This fetches a list of all movies with null directors and makes them into a menu.
     */
    
    private void printForm(PrintWriter out, Connection dbh)
        throws SQLException
    {
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
            ("SELECT tt,title FROM movie WHERE director IS NULL or releaseyear IS NULL");
        while( rs.next() ) {
            out.println("<option value='" +
                        rs.getString(1) +
                        "'>" +
                        rs.getString(2));
        }
        out.println("</select>");
        out.println("<p><input type='Choose using menu'>");
	out.println("<p><input type='Delete this movie'>");
	out.println("<p><input type='Search by title'>");
	out.println("<p><input type='Update movie'>");

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