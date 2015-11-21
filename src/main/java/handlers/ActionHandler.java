package handlers;


import objects.UserObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ActionType;

/**
 * Created by hans on 21.11.15.
 */
public class ActionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ActionHandler.class);

  public static void handleAction(String key, ActionType actionType) {
    LOGGER.info("Handling action {}", actionType);

    switch (actionType) {
      case BLACKLISTCLICK:
        LOGGER.info("Handling blacklist event with key: {} and ActionType: {}", key, actionType);
        UserObject userObject = UserHandler.getUser(key);
        handleBlacklist(userObject);
        //return handleBlacklistClick(key);
        break;
      case CUSTOM:
        LOGGER.info("Handling custom event with key: {} and ActionType: {}", key, actionType);
        break;
      default:
        LOGGER.warn("Action {} not handled!", actionType);
        throw new IllegalArgumentException("Action not handled: " + actionType.toString());
    }
  }

  public static void handleBlacklist(UserObject userObject) {
    //We reduce balance by commitment amount, may need more engineering and cats here
    LOGGER.info("Handling blacklist with user object: {}", userObject);
    userObject.setBalance(userObject.getBalance().subtract(userObject.getCommitment()));
    UserHandler.updateBalance(userObject);

  }
}
