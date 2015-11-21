package objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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

  @Getter
  @Setter
  private BigDecimal balance;

}
