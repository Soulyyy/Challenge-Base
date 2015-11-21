package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by hans on 21.11.15.
 */
public class PropertiesReader {

  private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);

  public static String readProps() throws IOException {

    String resp = "";
    Properties properties = new Properties();
    String filename = "props.properties";

    try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(filename)) {
      if (inputStream == null) {
        LOGGER.error("Failed to load properties file with name {}, inputstram is null", filename);
        throw new FileNotFoundException("Property file not found!");
      } else {
        properties.load(inputStream);
      }

      Cache.dbname = properties.getProperty("dbname");
      LOGGER.info("Added dbname as {}", Cache.dbname);
      Cache.token = properties.getProperty("token");
      LOGGER.info("Added token as {}", Cache.token);
    } catch (IOException e) {
      throw e;
    }
    return resp;
  }

}
