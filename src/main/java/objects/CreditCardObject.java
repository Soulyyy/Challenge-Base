package objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hans on 21.11.15.
 */
@AllArgsConstructor
public class CreditCardObject {

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
    Map<String, Object> cardParams = new HashMap<>();
    cardParams.put("number", number);
    cardParams.put("exp_month", expMonth);
    cardParams.put("exp_year", expYear);
    cardParams.put("cvc", cvc);
    cardParams.put("name", name);
    return cardParams;
  }

}
