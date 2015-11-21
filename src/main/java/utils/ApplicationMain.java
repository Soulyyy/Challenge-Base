package utils;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import controllers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hans on 16/11/2015.
 */
@ApplicationPath("/rest")
public class ApplicationMain extends Application {

  private static Logger LOGGER = LoggerFactory.getLogger(ApplicationMain.class);

  //This class is loaded twice. Maybe I don't want to load props here.
  //Just a not to self.
  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> classes = new HashSet<>();
    classes.add(BasicJsonController.class);
    classes.add(BasicMongoController.class);
    classes.add(StripeController.class);
    classes.add(ActionController.class);
    classes.add(BalanceController.class);
    //This class gives us JSON bindings by Jackson
    classes.add(JacksonJsonProvider.class);
    try {
      PropertiesReader.readProps();
    } catch (IOException e) {
      LOGGER.warn("Failed to load properties, {}", e);
      e.printStackTrace();

    }
    return classes;
  }

}
