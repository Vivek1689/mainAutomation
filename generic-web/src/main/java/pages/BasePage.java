package pages;

import config.factory.ConfigFactory;

import static generic.PageFunctions.*;

public class BasePage {
    protected void load_user_ui(String endPoint){
        _open(ConfigFactory.getConfig().ui_URL() + endPoint);
    }

    public void take_screenshot_visualTest(String filenameWithoutExtension) {
        _takeScreenshot(filenameWithoutExtension);
    }

    public void open(String arg0) {
        _open(arg0);
    }
}
