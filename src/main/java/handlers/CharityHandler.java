package handlers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import objects.CharityObject;
import org.bson.Document;
import utils.MongoConnection;

/**
 * Created by hans on 21.11.15.
 */
public class CharityHandler {

  public static CharityObject getCharity(String name) {
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("charity");
    FindIterable<Document> iterable = collection.find(Filters.eq("name", name));
    Document document = iterable.first();
    return new CharityObject(document.getString("name"), document.getString("message"));
  }
}
