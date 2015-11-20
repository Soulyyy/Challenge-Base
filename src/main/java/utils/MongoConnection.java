package utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import javax.inject.Singleton;

/**
 * Created by Hans on 16/11/2015.
 */
@Singleton
public class MongoConnection {

  private static MongoDatabase database = null;

  public static MongoDatabase getDatabase() {
    if (database == null) {
      MongoClient client = new MongoClient();
      database = client.getDatabase("tw");
    }
    return database;
  }
}
