package controllers;

import handlers.ActionHandler;
import utils.ActionType;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Created by hans on 21.11.15.
 */
@Path("/action")
public class ActionController {

  @GET
  public void doPost(@QueryParam("key") String key, @QueryParam("type") String type) {
    ActionType queryType = ActionType.fromString(type);
    ActionHandler.handleAction(key, queryType);


  }


}
