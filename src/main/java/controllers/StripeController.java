package controllers;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import objects.PaymentObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Cache;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hans on 20/11/2015.
 */
@Path("/stripe")
public class StripeController {

  private static final Logger LOGGER = LoggerFactory.getLogger(StripeController.class);


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public PaymentObject makePayment(@QueryParam("name") String name, @QueryParam("token") String token) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
    LOGGER.info("User with name : {} and token: {} requesting", name, token);

    LOGGER.info("Current Stripe api key is {}", Cache.token);
    Stripe.apiKey = Cache.token;

    final Map<String, Object> cardParams = new HashMap<>();
    cardParams.put("number", "4242424242424242");
    cardParams.put("exp_month", 12);
    cardParams.put("exp_year", 2015);
    cardParams.put("cvc", "123");
    cardParams.put("name", "J Bindings Cardholder");;

    final Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("amount", 100);
    chargeParams.put("currency", "usd");
    chargeParams.put("card", cardParams);

    final Charge charge = Charge.create(chargeParams);
    LOGGER.info(charge.toString());


    PaymentObject paymentObject = new PaymentObject(name, token);
    return paymentObject;
  }


}
