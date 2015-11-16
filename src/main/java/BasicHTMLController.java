import org.glassfish.jersey.server.mvc.Viewable;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Hans on 16/11/2015.
 */
@Path("/html")
public class BasicHTMLController {

  @GET
  @Produces(MediaType.TEXT_HTML)
  public Response getBasicHtml(HttpServletResponse response) throws IOException {
    //response.sendRedirect("../HelloHTML.html");
    return Response.status(200).entity(new Viewable("/", "HelloHTML.html")).build();
  }
}
