import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.lang.*;

/* Written by Scott D. Anderson
   Scott.Anderson@acm.org
   March 2005

Updated, March 2007 to fix the username/password and DBMS server.
Updated, March 2008 to use the Webdb object and simplify the code.
Rewrote, March 2010 to list actors instead of machines.

*/

public class ActorList extends HttpServlet {

    // Here's where all the real work happens

    private void doRequest(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out= res.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>");
        out.println("<title>Actor List Page</title>");
        out.println("</head>");
        out.println("<body>");

        Connection con = null;
        try {
            con = Webdb.connect("wmdb");
            Statement query = con.createStatement();
            ResultSet result = query.executeQuery("SELECT name FROM person ORDER BY name");
            
            out.println("<p>Current Actors (and Directors) are:");
            out.println("<table cols='1'>");
            int n=0;
            while(result.next()) {
                n++;
                String actorName = result.getString(1);
                if(!result.wasNull()) {
                    out.println("<tr><td>"+actorName+"</td></tr>");
                } else {
                    out.println("<tr><td> &nbsp; </td></tr>");
                }
            }
            out.println("</table>");
            out.println("<p>A total of "+n+" people.");
        }
        catch (Exception e) {
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
        finally {
            if( con != null ) {
                try {
                    con.close();
                }
                catch( Exception e ) {
                    out.println("<pre>");
                    e.printStackTrace(out);
                    out.println("</pre>");
                }
            }
        }
        out.println("</body>");
        out.println("</html>");
    }

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
