package objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Created by hans on 21.11.15.
 */
@AllArgsConstructor
@ToString
public class UserObject {

  @Getter
  @Setter
  private String username;

  @Getter
  @Setter
  private String email;

  //TODO plaintext!!!!! change this when time
  @Getter
  @Setter
  private String password;

  @Getter
  @Setter
  private BigDecimal commitment;

  @Getter
  @Setter
  private BigDecimal balance;

  //TODO hashing here as well
  @Getter
  @Setter
  private String key;

}