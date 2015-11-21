package handlers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import objects.CreditCardObject;
import org.bson.Document;
import utils.MongoConnection;

/**
 * Created by hans on 21.11.15.
 */

public class CreditCardHandler {

  public static void addCreditCard(CreditCardObject creditCardObject) {
    MongoDatabase database =MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("creditcards");
    //Because ORM is for the weak. Not really, needs refac maybe someday
    Document document = new Document().append("email", creditCardObject.getEmail()).append("number", creditCardObject.getNumber())
        .append("expMonth", creditCardObject.getExpMonth()).append("expYear", creditCardObject.getExpYear()).append("cvc", creditCardObject.getCvc())
        .append("name", creditCardObject.getName());
    collection.insertOne(document);
  }
}
