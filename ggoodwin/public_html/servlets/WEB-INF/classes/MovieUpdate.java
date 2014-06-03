/* HW6 Java Servlets
   Gabe Goodwin
   11/15/2012
 */

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.lang.*;

public class MovieUpdate extends HttpServlet {
  //index.html file
  out.println("<title>Gabe's Web Apps</title>");
  out.println("</head>");
  out.println("<body>");

  out.println("<h1>Gabe's Web Apps</h1>");

  out.println("<ul>");
  out.println("<li><a href='http://cs.wellesley.edu:8080/ggoodwin/serlet/MovieUpdate.java'> Java Applet</a>");
  out.println("</body>");
  out.println("</html>");


  //Code for Application

  //Strings to set button names
  String button1 = "Choose using menu";
  String button2 = "Delete this movie";
  String button3 = "Search by title";
  String button4 = "Update Movie";
  // Strings to use while updating database
  String title;
  String movieid;
  String release;
  String dirid;
  String dir;


 final static String DIRECTIONS = "<p>" +
        "This web application allows you to update or delete movies that lack directors or releaseyears. " +
        " You can either correct an existing director/release year or provide one that is missing.\n";
 

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


  //Process form processes the form data and interacts with the database
  // chose to use one method rather than creating a seperate 'updatDatabase' method
 private void processForm(HttpServletRequest request, PrintWriter out, Connection dbh)
 throws SQLException
    {
      String submit = request.getParameter("submit");
      if (submit == null ) {
	 title = "";
	 movieid = "";
	 release = "";
	 dirid = "";
	 dir = "";
	 }
      else if (submit.equals(button1)){ //Choose movie from menu
	movieid = request.getParameter("moviefromlist");
	PreparedStatement query1 = dbh.prepareStatement
            ("SELECT tt,title,movie.release,director FROM movie WHERE tt = ?");
	 query1.setString(1,movieid);
	ResultSet rs1 = query1.executeQuery();
	while (rs1.next()){
	  //  out.println("<p> We have executed the query.");
       	  title = rs1.getString(2);
	  //out.println("<p> title = "+title);
	  release = rs1.getString(3);
	  //out.println("<p> release = "+release);
	  dirid = rs1.getString(4);
	  //out.println("<p> dirid = "+dirid);
	  PreparedStatement query2 = dbh.prepareStatement
	    ("SELECT name FROM person WHERE nm = ?");
	  query2.setString(1,dirid);
	  ResultSet rs2 = query2.executeQuery();
	  while(rs2.next()){   
	    dir = rs2.getString(1);
	  }
	}
      }
      else if (submit.equals(button2)){ //Delete the movie
	movieid = request.getParameter("movieid");
	if(movieid.equals("")){
	  out.println("<p>No movie was selected. Please search for or select a movie to delete.");
	       }else{
	     PreparedStatement deleteQuery = dbh.prepareStatement
	       ("delete FROM movie WHERE tt = ?");
	     deleteQuery.setString(1,movieid);
	     out.println("<p>Movie with the id "+movieid+" was deleted from the database.");
	}}
   

      else if (submit.equals(button3)){ //Search by title
	title = request.getParameter("title");
	if (title.equals("")){
	      out.println("<p>Please enter a title in the Title field.");
	    }else{
	      PreparedStatement query1 = dbh.prepareStatement
		("SELECT tt,title,movie.release,director FROM movie where title like concat('%',?,'%')");
	      query1.setString(1,title);
	      ResultSet rs3 = query1.executeQuery();
	      while (rs3.next()){
		//  out.println("<p> We have executed the query.");
		movieid = rs3.getString(1);
		title = rs3.getString(2);
		//out.println("<p> title = "+title);
		release = rs3.getString(3);
		//out.println("<p> release = "+release);
		dirid = rs3.getString(4);
		//out.println("<p> dirid = "+dirid);
		PreparedStatement query2 = dbh.prepareStatement
		  ("SELECT name FROM person WHERE nm = ?");
		query2.setString(1,dirid);
		ResultSet rs4 = query2.executeQuery();
		while(rs4.next()){   
		  dir = rs4.getString(1);
		}
	      }
	      
	}}
	
      else if (submit.equals(button4)){//Update Movie
	//Assume that the user knows what they are doing if they provide both a title and id. This could backfire
	// and should be updated in the future.
	//out.println("You have pressed button 4");
	title = request.getParameter("title");
	movieid = request.getParameter("movieid");
	if (movieid.equals("") || title.equals("")){
	  out.println("<p>No movie has been selected.Please search for or select a movie to update.");
	    }else{
	//first make sure title and id match, so that we know the user has the right movie
	  PreparedStatement checkQuery = dbh.prepareStatement
		("SELECT title  FROM movie where tt = ?");
	      checkQuery.setString(1,movieid);
	      ResultSet rs5 = checkQuery.executeQuery();
	      String idTitle = "";
	      while (rs5.next()){
	      idTitle = rs5.getString(1);
	      }
	      if(title.equals(idTitle)){
		   //Go ahead and complete update
		   //Begin by getting info from form
		   release = request.getParameter("releaseyear");
		   dirid = request.getParameter("directorid");
		   dir = request.getParameter("director");
		   //then delete original entry from db
		   PreparedStatement delQuery = dbh.prepareStatement
		     ("delete FROM movie where tt = ?");
		   delQuery.setString(1,movieid);
		   delQuery.executeQuery();
		   //Next insert the new data into the db
		   PreparedStatement fixQuery = dbh.prepareStatement
		     ("insert into movie values(?,?,?,?");
		   fixQuery.setString(1,movieid);
		   fixQuery.setString(2,title);
		   fixQuery.setString(3,release);
		   fixQuery.setString(4,dirid);
		   out.println("<p> The movie "+title+" has been updated.");
		   //Check if the director needs to be added to the database
		   PreparedStatement checkDir = dbh.prepareStatement
		     ("select FROM person where nm = ?");
		   checkDir.setString(1,dirid);
		   ResultSet rs6 = checkDir.executeQuery();
		   if(rs6.next()){
		     ;//Do nothing, director is there
		   }else{
		     //add the director
		     PreparedStatement insertDir = dbh.prepareStatement
		       ("insert into person values(?,?)");
		     insertDir.setString(1,dirid);
		     insertDir.setString(2,dir);
		     out.println("<p> The director "+dir+" has been added.");
		   }
	      }else{
		    //if title and id don't match, let them know
		    out.println("<p>The movie title and id do not match. Please check before updating.");
	      }
	}
      }
    }


  /* Set up the webform */

    private void printForm(PrintWriter out, Connection dbh)
      throws SQLException
      {
    
        out.println("<form method='post' action='MovieUpdate'>");
        out.println("<p>Title <input type='text' name='title' value = '"+title+"'>");
        out.println("<p>Movie id <input type='text' name='movieid' value = '"+movieid+"'>");
        out.println("<p>Release year <input type='text' name='releaseyear' value = '"+release+"'>");
	out.println("<p>Director id <input type='text' name='directorid' value = '"+dirid+"'>");
	out.println("<p>Director <input type='text' name='director' value = '"+dir+"'>");
        out.println("<p>Movies without directors or release years:");
        out.println("<select name='moviefromlist'>");
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