package controllers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import handlers.CreditCardHandler;
import handlers.PaymentHandler;
import handlers.UserHandler;
import objects.CreditCardObject;
import objects.PaymentObject;
import objects.UserObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MongoConnection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by hans on 21.11.15.
 */
@Path("/payment")
public class PaymentController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public PaymentObject makePayment(@QueryParam("email") String email, @QueryParam("cardnumber") String cardnumber) {
    LOGGER.info("Making payment with email {} and cardnumber {}", email, cardnumber);
    CreditCardObject creditCardObject = CreditCardHandler.getCard(email, cardnumber);
    UserObject userObject = UserHandler.getUser(email);
    Map<String, Object> paymentMap = creditCardObject.getCreditCardMap();
    return PaymentHandler.makePayment(userObject, paymentMap);
  }


}
