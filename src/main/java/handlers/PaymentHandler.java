package handlers;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import objects.PaymentObject;
import objects.UserObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hans on 21.11.15.
 */
public class PaymentHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentHandler.class);

  public static PaymentObject makePayment(UserObject userObject, Map<String, Object> cardMap) {

    String status = "No payment made yet";
    UserHandler.updateBalance(userObject);
    if (userObject.getBalance().doubleValue() < -5.0) {
      LOGGER.info("User with name : {} and token: {} requesting", userObject.getUsername());

      LOGGER.info("Current Stripe api key is {}", Cache.token);
      Stripe.apiKey = Cache.token;

      final Map<String, Object> chargeParams = new HashMap<>();
      chargeParams.put("amount", (int)(Math.round(userObject.getBalance().doubleValue() * -100.0)));
      chargeParams.put("currency", "usd");
      chargeParams.put("card", cardMap);

      try {
        Charge charge = Charge.create(chargeParams);
        status = charge.getStatus();
        LOGGER.info("Charged!");
        CharityHandler.updateCharityBalance(CharityHandler.getCharity("Somalia"), userObject.getBalance().doubleValue() * -1.0);
        UserHandler.updateBalance(userObject, 0.0);
      } catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
        LOGGER.info("Payment failed with chargeparams {}", chargeParams);
        status = "failure";
        e.printStackTrace();
      }
    }
    String message = CharityHandler.getCharity("Somalia").getMessage();
    return new PaymentObject(userObject.getUsername(), message, status);
  }


}
