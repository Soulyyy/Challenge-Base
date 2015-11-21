package controllers;

import handlers.UserHandler;
import objects.UserObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by hans on 21.11.15.
 */
@Path("/register")
public class RegistrationController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);


  @GET
  public void registerUser(@QueryParam("name") String username, @QueryParam("email") String email, @QueryParam("password") String password) {
    LOGGER.info("Registering user with name {}, email {} and password {}", username, email, password);
    if (!UserHandler.isEmailUsed(email)) {
      UserObject userObject = new UserObject(username, email, password);
      UserHandler.addUser(userObject);
    }
  }

}
