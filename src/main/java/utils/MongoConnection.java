package utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * Created by Hans on 16/11/2015.
 */
@Singleton
public class MongoConnection {

  private static final Logger LOGGER = LoggerFactory.getLogger(MongoConnection.class);

  private static MongoDatabase database = null;

  public static MongoDatabase getDatabase() {
    if (database == null) {
      LOGGER.info("using database name {} to connect", Cache.dbname);
      MongoClient client = new MongoClient();
      database = client.getDatabase(Cache.dbname);
    }
    return database;
  }
}
