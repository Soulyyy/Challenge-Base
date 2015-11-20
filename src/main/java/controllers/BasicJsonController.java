package controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.awt.*;

/**
 * Created by Hans on 16/11/2015.
 */
@Path("/json")
public class BasicJsonController {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response basicResponse() {
    String resp = "Hello World in JSON!";
    return Response.status(200).entity(resp).build();
  }
}
