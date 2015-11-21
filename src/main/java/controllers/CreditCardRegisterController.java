package controllers;

import handlers.CreditCardHandler;
import objects.CreditCardObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Created by hans on 21.11.15.
 */
@Path("/creditcardregister")
public class CreditCardRegisterController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardRegisterController.class);

  @GET
  public void registerCreditCard(@QueryParam("email") String email, @QueryParam("cardnumber") String cardnumber,
                                 @QueryParam("expmonth") int expmonth, @QueryParam("expyear") int expyearm,
                                 @QueryParam("cvc") String cvc, @QueryParam("holder") String holder) {
    LOGGER.info("Registering credit card!");
    CreditCardObject creditCardObject = new CreditCardObject(email, cardnumber, expmonth, expyearm, cvc, holder);
    LOGGER.info("The credit card object is {}", creditCardObject);
    CreditCardHandler.addCreditCard(creditCardObject);
  }

}
