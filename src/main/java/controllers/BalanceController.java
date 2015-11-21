package controllers;

import handlers.UserHandler;
import objects.BalanceObject;
import objects.UserObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by hans on 21.11.15.
 */
@Path("/balance")
public class BalanceController {

  private final Logger LOGGER = LoggerFactory.getLogger(BalanceController.class);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public BalanceObject getBalance(@QueryParam("key") String key) {
    UserObject userObject = UserHandler.getUser(key);
    BalanceObject balanceObject = new BalanceObject(userObject.getBalance().doubleValue());
    LOGGER.info("User with key {}, with balance {}", key, balanceObject);
    return balanceObject;
  }
}
