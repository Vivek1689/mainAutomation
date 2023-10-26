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

    @DefaultValue("http://localhost:4723")
    @ConverterClass(StringToURLConverter.class)
    URL appiumLocalURL();

    @Key("wait_timeout")
    long waitTimeout();

    @DefaultValue("stage")
    String environment();

    String platform();

    @DefaultValue("")
    @Key("${environment}.ui_username")
    String ui_userName();

    @DefaultValue("")
    @Key("${environment}.ui_password")
    String ui_password();

    @Key("passedstepsscreenshot")
    String passedStepsScreenshot();

    @DefaultValue("yes")
    @Key("failedstepsscreenshot")
    String failedStepsScreenshot();

    @DefaultValue("false")
    @Key("eachstepsscreenshot")
    boolean eachStepsScreenshot();

    @DefaultValue("false")
    @Key("retryfailedtests")
    boolean retryFailedTests();

    @Key("retry_count")
    int retryCount();


    @Key("runmode_mobile")
    String RunModeMobile();


    @DefaultValue("false")
    @Key("terminateApp")
    boolean terminateApp();

    @Key("remote_mobile_provider")
    String RemoteMobileProvider();

    @Key("remote_appium_version")
    String appiumVersion();

    @Key("${environment}.project_name")
    String projectName();

    @Key("${environment}.build_name")
    String buildName();
    @Key("${environment}.build_no")
    String buildNumber();

    @Key("${environment}.${platform}.app")
    String appLocal();

    @Key("${environment}.${remote_mobile_provider}.${platform}.app")
    String appRemote();

    @Key("${environment}.${remote_mobile_provider}.${platform}.deviceName")
    String deviceNameRemote();

    @Key("${environment}.${remote_mobile_provider}.${platform}.platformVersion")
    String platformVersionRemote();


    // Browser Stack Configuration
    String bs_username();
    String bs_key();
    String bs_url();
    @DefaultValue("https://${bs_username}:${bs_key}@${bs_url}")
    @ConverterClass(StringToURLConverter.class)
    URL browserStackURL();


}
