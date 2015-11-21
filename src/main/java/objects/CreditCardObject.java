package objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hans on 21.11.15.
 */
@AllArgsConstructor
public class CreditCardObject {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardObject.class);

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String number;

  @Getter
  @Setter
  private int expMonth;

  @Getter
  @Setter
  private int expYear;

  @Getter
  @Setter
  private String cvc;

  @Getter
  @Setter
  private String name;



  public Map<String, Object> getCreditCardMap() {
    LOGGER.info("Name {}", name);
    Map<String, Object> cardParams = new HashMap<>();
    cardParams.put("number", number.replace("\"", ""));
    cardParams.put("exp_month", expMonth);
    cardParams.put("exp_year", expYear);
    cardParams.put("cvc", cvc.replace("\"", ""));
    cardParams.put("name", name.replace("\"", ""));
    return cardParams;
  }

}
