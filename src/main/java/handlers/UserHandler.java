package handlers;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import objects.UserObject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MongoConnection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hans on 21.11.15.
 */
public class UserHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserHandler.class);

  //TODO implement database side, may leak connections atm
  public static UserObject getUser(String key) {
    LOGGER.info("Getting user with key {}");
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("users");
    BasicDBObject dbObject = new BasicDBObject("key", key);
    final List<UserObject> users = new ArrayList<>();
    Block<Document> mapToUsers = document -> users.add(new UserObject(document.getString("name"), document.getString("email"), document.getString("password"),
        new BigDecimal(document.getDouble("commitment")), new BigDecimal(document.getDouble("balance")), document.getString("key")));
    collection.find(dbObject).forEach(mapToUsers);
    if (users.size() == 0 || users.size() > 1) {
      LOGGER.warn("Database has {} mappings to the same key {}", users.size(), key);
      throw new IllegalStateException("Database has multiple users mapped to the same key! Key is : " + key);
    } else {
      LOGGER.info("Got user: {}", users.get(0));
      return users.get(0);
    }
  }

  //TODO maybe use morphia to map
  public static void updateUser(UserObject userObject) {
    LOGGER.info("Updating with user object {}", userObject);
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("users");
    //Ghetto mapping
    collection.updateOne(Filters.eq("key", userObject.getKey()), new Document("$set", new Document("balance", userObject.getBalance().doubleValue())));
    //collection.updateOne(query, change);
    LOGGER.info("Updated object to {}", userObject);

  }


  public static boolean isEmailUsed(String email) {
    LOGGER.info("Checking email usage of email {}", email);
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("users");
    BasicDBObject query = new BasicDBObject("email", email);
    FindIterable<Document> cursor = collection.find(query);
    boolean resp = cursor.iterator().hasNext();
    LOGGER.info("Email is used: {}", resp);
    if(resp) {
      LOGGER.warn("The email is in use by user: {}", cursor.iterator().next());
    }
    return resp;
  }

  public static void addUser(UserObject userObject) {
    LOGGER.info("Adding user {}", userObject);
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("users");
    Document document = new Document().append("name", userObject.getUsername()).append("email", userObject.getEmail())
        .append("password", userObject.getPassword()).append("commitment", userObject.getCommitment().doubleValue())
        .append("balance", userObject.getBalance().doubleValue()).append("key", userObject.getKey());
    collection.insertOne(document);
    LOGGER.info("User added: {}!", userObject);
  }
}
