package objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by Hans on 20/11/2015.
 */
@AllArgsConstructor
public class PaymentObject {

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String message;

}
