import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Greetings extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html"); // have to set the MIME type of the response
        PrintWriter out = response.getWriter(); // get an output stream (not System.out)

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Greetings Page</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h1>Howdy everyone!</h1>");
        out.println("</body>");
        out.println("</html>");
    }
}
