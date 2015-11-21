package handlers;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import objects.CreditCardObject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MongoConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hans on 21.11.15.
 */

public class CreditCardHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardHandler.class);

  public static void addCreditCard(CreditCardObject creditCardObject) {
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("creditcards");
    //Because ORM is for the weak. Not really, needs refac maybe someday
    Document document = new Document().append("email", creditCardObject.getEmail()).append("number", creditCardObject.getNumber())
        .append("expMonth", creditCardObject.getExpMonth()).append("expYear", creditCardObject.getExpYear()).append("cvc", creditCardObject.getCvc())
        .append("name", creditCardObject.getName());
    collection.insertOne(document);
  }

  public static List<CreditCardObject> getCardsForUser(String email) {
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("creditcards");
    List<CreditCardObject> resp = new ArrayList<>();
    Block<Document> mapToObject = document -> resp.add(new CreditCardObject(document.getString("email"), document.getString("number"), document.getInteger("expMonth"),
        document.getInteger("expYear"), document.getString("cvc"), document.getString("name")));
    collection.find(Filters.eq("email", email)).forEach(mapToObject);
    return resp;
  }

  public static CreditCardObject getCard(String email, String cardNumber) {
    LOGGER.info("getting credit card with email {} and cardnumber {}", email, cardNumber);
    List<CreditCardObject> creditCardObjects = getCardsForUser(email);
    for (CreditCardObject creditCardObject : creditCardObjects) {
      if (creditCardObject.getName() != null && creditCardObject.getNumber().equals(cardNumber)) {
        return creditCardObject;
      }
    }
    throw new IllegalArgumentException("No credit card with user " + email + " and cardnumber " + cardNumber);
  }
}
