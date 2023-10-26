package stepdefs;

import config.factory.ConfigFactory;

public class BaseSteps {

    String baseURI = ConfigFactory.getConfig().api_baseURI();


}
