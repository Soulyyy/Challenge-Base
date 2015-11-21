package handlers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import objects.CharityObject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MongoConnection;

import java.math.BigDecimal;

/**
 * Created by hans on 21.11.15.
 */
public class CharityHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(CharityHandler.class);

  public static CharityObject getCharity(String name) {
    LOGGER.info("Getting charity with name: {}", name);
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("charity");
    FindIterable<Document> iterable = collection.find(Filters.eq("name", name));
    Document document = iterable.first();
    return new CharityObject(document.getString("name"), document.getString("message"), new BigDecimal(document.getDouble("balance")));
  }

  public static void updateCharityBalance(CharityObject charityObject, double change) {
    LOGGER.info("Updating charity balance of {} with {}", charityObject, change);
    double balance = charityObject.getBalance().add(new BigDecimal(change)).doubleValue();
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("charity");

    Document document = new Document().append("name", charityObject.getName()).append("message", charityObject.getMessage()).append("balance", balance);
    LOGGER.info("Name is: {}", charityObject.getName());
    LOGGER.info("Document is : {} ", document);
    collection.updateOne(Filters.eq("name", charityObject.getName()), new Document("$set", new Document("balance", charityObject.getBalance().add(new BigDecimal(balance)).doubleValue())));

  }
}
