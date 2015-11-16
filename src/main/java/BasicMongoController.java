import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hans on 16/11/2015.
 */
@Path("/mongo")
public class BasicMongoController {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<MongoObject> getMongoExample(@QueryParam("name") String name, @QueryParam("age") int age) {
    MongoDatabase database = MongoConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("test");
    BasicDBObject query = new BasicDBObject("name", name).append("age", age);
    final List<MongoObject> mongoList = new ArrayList<>();
    Block<Document> mapToMongoObject = document ->
        mongoList.add(new MongoObject(document.getString("name"), document.getInteger("age")));
    //Block<Document> printBlock = document -> System.out.println(document.toJson());
    collection.find(query).forEach(mapToMongoObject);
    return mongoList;
  }
}
