package utils;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hans on 21.11.15.
 */

//Not really optimal, but looks better
public enum ActionType {

  BLACKLISTCLICK("blacklistclick"),
  CUSTOM("custom");

  private static final Logger LOGGER = LoggerFactory.getLogger(ActionType.class);

  @Getter
  private String action;

  ActionType(String action) {
    this.action = action;
  }

  public static ActionType fromString(String action) {
    if (action != null) {
      for (ActionType actionType : ActionType.values()) {
        if (action.equalsIgnoreCase(actionType.action)) {
          return actionType;
        }
      }
    }
    throw new IllegalArgumentException("Request made with unknown actiontype " + action);
  }

  @Override
  public String toString() {
    return action;
  }
}
