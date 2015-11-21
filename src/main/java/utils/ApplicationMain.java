package utils;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import controllers.BasicJsonController;
import controllers.BasicMongoController;
import controllers.StripeController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hans on 16/11/2015.
 */
@ApplicationPath("/rest")
public class ApplicationMain extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> classes = new HashSet<>();
    classes.add(BasicJsonController.class);
    classes.add(BasicMongoController.class);
    classes.add(StripeController.class);
    //This class gives us JSON bindings by Jackson
    classes.add(JacksonJsonProvider.class);
    return classes;
  }

}