package objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Hans on 16/11/2015.
 */
@AllArgsConstructor
public class MongoObject {

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private int age;
}
