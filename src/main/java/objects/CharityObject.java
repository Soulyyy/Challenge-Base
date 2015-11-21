package objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hans on 21.11.15.
 */
@AllArgsConstructor
public class CharityObject {

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String message;

}
