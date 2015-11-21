package handlers;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import objects.UserObject;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ActionType;
import utils.Cache;
import utils.MongoConnection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        UserObject userObject = getUser(key);
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

  //TODO implement database side, may leak connections atm
  public static UserObject getUser(String key) {
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("users");
    BasicDBObject dbObject = new BasicDBObject("key", key);
    final List<UserObject> users = new ArrayList<>();
    Block<Document> mapToUsers = document -> users.add(new UserObject(document.getString("name"), document.getString("email"),document.getString("password"),
        new BigDecimal(document.getDouble("commitment")), new BigDecimal(document.getDouble("balance")),document.getString("key")));
    collection.find(dbObject).forEach(mapToUsers);
    if (users.size() == 0 || users.size() > 1) {
      LOGGER.warn("Database has {} mappings to the same key {}", users.size(), key);
      throw new IllegalStateException("Database has multiple users mapped to the same key! Key is : " + key);
    } else {
      LOGGER.info("Got user: {}", users.get(0));
      return users.get(0);
    }
  }

  public static void handleBlacklist(UserObject userObject) {
    //We reduce balance by commitment amount, may need more engineering and cats here
    LOGGER.info("Handling blacklist with user object: {}", userObject);
    userObject.setBalance(userObject.getBalance().subtract(userObject.getCommitment()));
    updateUser(userObject);


  }

  //TODO maybe use morphia to map
  public static void updateUser(UserObject userObject) {
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("users");
    //Morphia morphia = new Morphia();
    //Morphia s = morphia.map(UserObject.class);
    //Datastore datastore = morphia.createDatastore(Cache.dbname);
    //collection.updateOne(userObject.)
    //collection

  }
}
