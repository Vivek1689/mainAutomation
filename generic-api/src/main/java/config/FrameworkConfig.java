package config;


import config.converters.StringToURLConverter;
import org.aeonbits.owner.Config;

import java.net.URL;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:${user.dir}/src/test/resources/config/config.properties",
        "file:${user.dir}/src/test/resources/config/stage-config.properties"
})
public interface FrameworkConfig extends Config {

    @DefaultValue("stage")
    String environment();


    @Key("${environment}.baseuri")
    String api_baseURI();


    @DefaultValue("")
    @Key("${environment}.api_username")
    String api_userName();

    @DefaultValue("")
    @Key("${environment}.api_password")
    String api_password();


//
//    @Key("${environment}.db_connection")
//    String dbConnection();


    @DefaultValue("no")
    @Key("retryfailedtests")
    String retryFailedTests();

    @Key("retry_count")
    int retryCount();


}
