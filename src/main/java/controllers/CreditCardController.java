package controllers;

import handlers.CreditCardHandler;
import objects.CreditCardObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by hans on 21.11.15.
 */
@Path("/creditcard")
public class CreditCardController {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<CreditCardObject> getCreditCards(@QueryParam("email") String email) {
    return CreditCardHandler.getCardsForUser(email);
  }
}
