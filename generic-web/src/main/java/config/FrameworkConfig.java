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

    @DefaultValue("CHROME")
//    @ConverterClass(StringToBrowserTypeConverter.class)
//    BrowserType browser();
    String browser();

    @DefaultValue("false")
    @Key("reuse_driver_instance")
    boolean reuseDriverInstance();

    @Key("${environment}.url")
    String ui_URL();


    @DefaultValue("")
    @Key("${environment}.ui_username")
    String ui_userName();

    @DefaultValue("")
    @Key("${environment}.ui_password")
    String ui_password();


    @Key("${environment}.api_email")
    String api_email();
    @Key("${environment}.api_password")
    String api_password();
    @Key("${environment}.api_base_uri")
    String api_baseURI();



    @Key("${environment}.db_connection")
    String dbConnection();


    @Key("passedstepsscreenshot")
    String passedStepsScreenshot();

    @DefaultValue("yes")
    @Key("failedstepsscreenshot")
    String failedStepsScreenshot();

    @DefaultValue("no")
    @Key("failedtestscreenrec")
    String failedTestScreenRec();

    @Key("eachstepsscreenshot")
    boolean eachStepsScreenshot();

    @DefaultValue("false")
    @Key("retryfailedtests")
    boolean retryFailedTests();

    @Key("retry_count")
    int retryCount();

    @Key("debuggeraddress")
    String debuggerAddress();

    @Key("runmode_browser")
    String runModeBrowser();

    @Key("remote_browser_provider")
    String remoteBrowserProvider();

    @Key("docker_enableVNC")
    boolean dockerEnableVNC();

    @Key("docker_enableVNC_view")
    boolean dockerEnableVNC_View();

    @DefaultValue("latest")
    @Key("${environment}.docker_browser_version")
    String dockerBrowserVersion();

    @Key("network_call_log_capture")
    boolean networkCallLogCapture();

    @Key("network_log_ignore_status_code_min")
    int networkCallLogIgnoreCodeMin();
    @Key("network_log_ignore_status_code_max")
    int networkCallLogIgnoreCodeMax();

//    @Key("browserRemoteMode")
//    @ConverterClass(StringToRemoteModeBrowserTypeConverter.class)
//    BrowserRemoteModeType browserRemoteMode();

//    @Key("runModeMobile")
//    @ConverterClass(StringToRunModeBrowserTypeConverter.class)
//    RunModeType mobileRunMode();

//    @Key("mobileRemoteMode")
//    @ConverterClass(StringToMobileRemoteModeTypeConverter.class)
//    MobileRemoteModeType mobileRemoteMode();


    // Browser Stack Configuration
    String bs_username();
    String bs_key();
    String bs_url();
    @Key("${environment}.${remote_browser_provider}.os")
    String browserStack_OS();
    @Key("${environment}.${remote_browser_provider}.os_version")
    String browserStack_OS_Ver();
    @Key("${environment}.${remote_browser_provider}.browser_version")
    String browserStack_Browser_Ver();
    @DefaultValue("${browser}")
    @Key("${environment}.${remote_browser_provider}.browser_name")
    String browserStack_Browser_Name();


    @DefaultValue("https://${bs_username}:${bs_key}@${bs_url}")
    @ConverterClass(StringToURLConverter.class)
    URL browserStackURL();

    @Key("${environment}.project_name")
    String projectName();

    @Key("${environment}.build_name")
    String buildName();
    @Key("${environment}.build_no")
    String buildNumber();


}
