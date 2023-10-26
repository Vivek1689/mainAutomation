package generic;

import constants.Constants;
import driverManager.DriverManager;
import enums.WaitStrategy;
import logger.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static enums.WaitStrategy.*;

/**
 * This Class contains all basic selenium functionalities and behaves as the
 * parent class of PageObjectWrapper.
 */
public class PageFunctions {

    ///////////////////////////////////////////////////////////////////////////
    private static Keys get_CTRL_CMD() {
        Platform platformName = ((HasCapabilities) DriverManager.getDriver()).getCapabilities().getPlatformName();
        return platformName.is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
    }

    public static WebDriverWait _getWebDriverWait(long seconds) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds));
    }

    public static WebElement _performExplicitWait(By by, WaitStrategy waitStrategy) {
        WebElement e = null;
        WebDriverWait wait = _getWebDriverWait(Constants.DEFAULT_WEBDRIVERWAIT);
        try {
            switch (waitStrategy) {
                case NONE:
                    e = DriverManager.getDriver().findElement(by);
                    break;
                case CLICKABLE:
                    e = wait.until(ExpectedConditions.elementToBeClickable(by));
                    break;
                case VISIBLE:
                    e = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
                    break;
                case PRESENCE:
                    e = wait.until(ExpectedConditions.presenceOfElementLocated(by));
                    break;
                case HANDLESTALE:
                    e = wait.until(d -> {
                        d.navigate().refresh();
                        return d.findElement(by);
                    });
                    break;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return e;
    }

    public static List<WebElement> _performExplicitWaitForElements(By by, WaitStrategy waitStrategy) {
        List<WebElement> e = null;
        WebDriverWait wait = _getWebDriverWait(Constants.DEFAULT_WEBDRIVERWAIT);
        try {
            switch (waitStrategy) {
                case NONE:
                    e = DriverManager.getDriver().findElements(by);
                    break;
                case CLICKABLE:
                    e = wait.until(d -> d.findElements(by).stream().filter(WebElement::isEnabled).collect(Collectors.toList()));
                    break;
                case VISIBLE:
                    e = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
                    break;
                case PRESENCE:
                    e = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
                    break;
                case HANDLESTALE:
                    e = wait.until(d -> {
                        d.navigate().refresh();
                        return d.findElements(by);
                    });
                    break;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return e;
    }

    /////////////////////////////////////////////////////////////////////////

    public static void _open(String url) {
        DriverManager.getDriver().get(url);
        Logger.info("Opened: '" + url + "'");
    }

    public static void _navigateBack() {
        DriverManager.getDriver().navigate().back();
        Logger.info("Navigated to previous page");
    }

    /**
     * Used to refresh the page
     */
    public static void _refreshPage() {
        DriverManager.getDriver().navigate().refresh();
        _waitForPageToLoadCompletely();
    }

    /**
     * Capture the screenshot with the specified filename
     *
     * @param filenameWithoutExtension : ".png" filename extension is used
     */
    public static void _takeScreenshot(String filenameWithoutExtension) {
        String tmpPath;
        // If file path is passed with filename, then this file path will be used
        // else DEFAULT_SCREENSHOT_PATH will be used
        if (filenameWithoutExtension.contains("/") || filenameWithoutExtension.contains("\\"))
            tmpPath = filenameWithoutExtension;
        else tmpPath = Constants.DEFAULT_SCREENSHOT_PATH + filenameWithoutExtension;
        File scrFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(tmpPath + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
            // throw new RuntimeException("Could not capture screenshot" + e.getLocalizedMessage());
        }
    }

    /**
     * Returns the List of WebElements
     * @param by
     * @return List<WebElement>
     */
    public static List<WebElement> _getElements(By by, String eleListName) {
        List<WebElement> elements = _performExplicitWaitForElements(by, PRESENCE);
        if (!eleListName.isEmpty()) Logger.info(String.format("Elements: %s, found: %s", eleListName, elements.size()));
        return elements;
    }

    /**
     * Returns the WebElement
     * @param by
     * @param eleName
     * @return WebElement
     */
    public static WebElement _getElement(By by, String eleName) {
        WebElement element;
        try {
            element = _performExplicitWait(by, PRESENCE);
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            element = _performExplicitWait(by, HANDLESTALE);
        }
        if (!eleName.isEmpty()) Logger.info(String.format("Element: %s, found", eleName));
        return element;
    }

    /**
     * Returns the element w r t index
     * @param by
     * @param elementIndex: 1 = first element
     * @param eleName
     * @return
     */
    public static WebElement _getElement(By by, int elementIndex, String eleName) {
        WebElement element;
        List<WebElement> elements = _performExplicitWaitForElements(by, PRESENCE);
        if(!elements.isEmpty()){
            if(elements.size()>= elementIndex){
                element = elements.get(elementIndex-1);
                if (!eleName.isEmpty()) Logger.info(String.format("Element: %s, found at index: %s", eleName, elementIndex));
            }else {
                element = elements.get(0);
                if (!eleName.isEmpty()) Logger.info(String.format("Element: %s, couldn't find at index: %s, Returned 1st element", eleName, elementIndex));
            }
        }else return null;
        return element;
    }


    /**
     * File upload - Make sure that the locator element has <input> tag and type="file"
     * @param by : Make sure that the element has <input> tag and type="file"
     * @param fullFilePathWithExtension
     * @param elementName
     */
    public static void _upload(By by, String fullFilePathWithExtension, String elementName) {
        WebElement e = _performExplicitWait(by, PRESENCE);
        e.sendKeys(fullFilePathWithExtension);
        if (!elementName.isEmpty()) Logger.info("'" + fullFilePathWithExtension + "' is uploaded to " + elementName);
    }


    /**
     * This method returns Locator in By form by using locator type and locator value.
     * @param locatorType
     * @param locatorValue
     * @return By
     */
/*
    protected By getBy(String locatorType, String locatorValue) {
        switch (locatorType) {
            case "id":
                return By.id(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "css":
            case "cssSelector":
                return By.cssSelector(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "classname":
                return By.className(locatorValue);
            case "linktext":
                return By.linkText(locatorValue);
            default:
                return By.id(locatorValue);
        }
    }
*/

    /**
     * Waits for the element to be clickable and clicks      *
     * @param by
     */
    public static void _click(By by, String elementName) {
        // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
        WebElement e = _performExplicitWait(by, CLICKABLE);
        e.click();
        if (!elementName.isEmpty()) Logger.info("Clicked on " + elementName);
    }

    /**
     * Looks for the specified element, if it exists then Click
     * @param by
     * @param elementName
     */
    public static void _click_IfExists(By by, String elementName) {
        List<WebElement> els = _performExplicitWaitForElements(by, CLICKABLE);
        if (!els.isEmpty()) els.get(0).click();
        if (!elementName.isEmpty()) Logger.info("Clicked on " + elementName);
    }

    /**
     * Scrolls into View, then Click
     * @param by
     * @param elementName
     */
    public static void _click_js_scroll(By by, String elementName) {
        WebElement e = _performExplicitWait(by, CLICKABLE);
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", e);
        e.click();
        if (!elementName.isEmpty()) Logger.info("Clicked(Scrolled to view) on " + elementName);
    }

    /**
     * Click using JavascriptExecutor
     * @param by
     * @param elementName
     */
    public static void _click_JS(By by, String elementName) {
        WebElement e = _performExplicitWait(by, PRESENCE);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", e);
        js.executeScript("arguments[0].click();", e);
        if (!elementName.isEmpty()) Logger.info("Clicked on " + elementName);
    }

    public static void _click_exactText(String textToBeClicked, String elementName) {
        _click_js_scroll(By.xpath("//*[text()='"+textToBeClicked+"']"), elementName);
    }

    public static void _click_partialText(String textToBeClicked, String elementName) {
        _click_js_scroll(By.xpath("//*[contains(text(),'"+textToBeClicked+"')]"), elementName);
    }

    public static void _checkBox_check(By by, WaitStrategy waitStrategy, String elementName) {
        WebElement e = _performExplicitWait(by, waitStrategy);
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", e);
        if (!e.isSelected()) e.click();
        if (!elementName.isEmpty()) Logger.info("Checked the check box: " + elementName);
    }

    public static void _checkBox_uncheck(By by, WaitStrategy waitStrategy, String elementName) {
        WebElement e = _performExplicitWait(by, waitStrategy);
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", e);
        if (e.isSelected()) e.click();
        if (!elementName.isEmpty()) Logger.info("UnChecked the check box: " + elementName);
    }

    /**
     * Click on the 1st element with matching text
     * @param by
     * @param text
     * @param elementName
     */
    public static void _clickElementByText(By by, String text, String elementName) {
        for (WebElement ele : _performExplicitWaitForElements(by, CLICKABLE)) {
            String val = ele.getText();
            if (val.trim().equalsIgnoreCase(text)) {
                ele.click();
                if (!elementName.isEmpty()) Logger.info("Clicked on " + elementName + ", with text: " + text);
                break;
            }
        }
    }

    /**
     * Clears the WebElement Field and Fill the specified value
     * @param by
     * @param stringText
     */
    public static void _clearAndSendKeys(By by, String stringText, String eleName) {
        WebElement webelement;
        try {
            webelement = _performExplicitWait(by, VISIBLE);
            webelement.click();
            String os = System.getProperty("os.name");
            if (os.equals("WINDOWS")) {
                webelement.sendKeys(Keys.chord(get_CTRL_CMD(), "a"), Keys.DELETE);
                webelement.sendKeys(Keys.chord(Keys.BACK_SPACE));
            } else {
                webelement.sendKeys(Keys.chord(get_CTRL_CMD(), "a"));
                webelement.sendKeys(Keys.chord(Keys.BACK_SPACE));
            }
            webelement.sendKeys(stringText);
            if (!eleName.isEmpty()) Logger.info("'" + stringText + "' is entered into " + eleName);
        } catch (StaleElementReferenceException se) {
            webelement = _performExplicitWait(by, HANDLESTALE);
            webelement.click();
            webelement.clear();
            webelement.sendKeys(stringText);
            if (!eleName.isEmpty()) Logger.info("'" + stringText + "' is entered into " + eleName);
        }
    }

    public static void _sendKeys(By by, String txt, String elementName) {
        WebElement e = _performExplicitWait(by, CLICKABLE);
        e.sendKeys(txt);
        if (!elementName.isEmpty()) Logger.info("'" + txt + "' is entered into " + elementName);
    }

    /**
     * Hides the text entered into report/log
     * @param by
     * @param txt
     * @param elementName
     */
    public static void _sendKeys_password(By by, String txt, String elementName) {
        WebElement e = _performExplicitWait(by, CLICKABLE);
        e.sendKeys(txt);
        if (!elementName.isEmpty()) Logger.info("'*******' password is entered into " + elementName);
    }

    public static void _sendKeys(By by, Keys key, String eleName) {
        WebElement e = _performExplicitWait(by, CLICKABLE);
        e.sendKeys(key);
        if (!eleName.isEmpty()) Logger.info("'" + key + "' is entered into " + eleName);
    }

    public static void _sendKeys_JS(By by, String txt, String elementName) {
        WebElement e = _performExplicitWait(by, PRESENCE);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript(String.format("arguments[0].value='%s'", txt), e);
        if (!elementName.isEmpty()) Logger.info("'" + txt + "' is entered(using JS) into " + elementName);
    }

    /**
     * Get Current page url
     * @return : String - page Url
     */
    public static String _getCurrentUrl() {
        _waitForPageToLoadCompletely();
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Logger.info("Current url :" + currentUrl);
        return currentUrl;
    }

    /**
     * Returns the current page title
     * @return : String title
     */
    public static String _getPageTitle() {
        _waitForPageToLoadCompletely();
        String title = DriverManager.getDriver().getTitle();
        Logger.info("'" + title + "' Title is returned");
        return title;
    }

    /**
     * Returns the page source
     * @return
     */
    public static String _getPageSource() {
        String src = DriverManager.getDriver().getPageSource();
        Logger.info("Returned page source");
        return src;
    }

    /**
     * Returns the text of the specified WebElement
     * @param by
     * @param eleName
     * @return String - Text of WebElement
     */
    public static String _getText(By by, String eleName) {
        String value = null;
        WebElement element;
        try {
            element = _performExplicitWait(by, PRESENCE);
            value = element.getText();
        } catch (StaleElementReferenceException e) {
            element = _performExplicitWait(by, HANDLESTALE);
            value = element.getText();
        }
        if (!eleName.isEmpty()) Logger.info("'" + value + "' text returned for element " + eleName);
        return value;
    }

    /**
     * Returns the value of the input element
     * @param by
     * @param waitTillSomeValuePresent
     * @param elementName
     * @return
     */
    public static String _getValue(By by, boolean waitTillSomeValuePresent, String elementName) {
        WebElement e = _performExplicitWait(by, VISIBLE);
        String txt = e.getAttribute("value");
        if (waitTillSomeValuePresent) {
            for (int i = 0; i < 3; i++) {
                if (txt.isEmpty()) {
                    _hardWait(2);
                    txt = e.getAttribute("value");
                }
            }
        }
        if (!elementName.isEmpty()) Logger.info("'" + txt + "' is returned from " + elementName);
        return txt;
    }

    /**
     * Returns attribute value as String for the specified WebElement
     * @param by:
     * @param attribute:
     * @param eleName:
     * @return : String : Attribute Value
     */
    public static String _getAttribute(By by, String attribute, String eleName) {
        String value = null;
        WebElement element;
        try {
            element = _performExplicitWait(by, PRESENCE);
            value = element.getAttribute(attribute);
        } catch (StaleElementReferenceException e) {
            element = _performExplicitWait(by, HANDLESTALE);
            value = element.getAttribute(attribute);
        }
        if (!eleName.isEmpty())
            Logger.info(String.format("'%s' is returned for attribute '%s' for element %s", value, attribute, eleName));
        return value;
    }

    /**
     * This method sets attribute value as String for the specified WebElement
     * @param by:
     * @param attribute:
     * @param val:
     */
    public static void _setAttribute(By by, String attribute, String val, String eleName) {
        WebElement element;
        WebDriver driver = DriverManager.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            element = _performExplicitWait(by, PRESENCE);
            js.executeScript(element + ".setAttribute(" + attribute + ", '" + val + "')");

        } catch (StaleElementReferenceException e) {
            element = _performExplicitWait(by, HANDLESTALE);
            js.executeScript(element + ".setAttribute(" + attribute + ", '" + val + "')");
        }
    }

    /**
     * Waits for the element to be Visible and returns TRUE/FALSE as boolean
     * @param by
     * @param elementName
     * @return boolean - Visibility
     */
    public static boolean _isVisible(By by, String elementName) {
        if (_performExplicitWaitForElements(by, VISIBLE).size() > 0) {
            if (!elementName.isEmpty()) Logger.info(String.format("Element: %s, is Visible", elementName));
            return true;
        } else {
            if (!elementName.isEmpty()) Logger.info(String.format("Element: %s, is Not Visible", elementName));
            return false;
        }
    }

    /**
     * Returns TRUE/FALSE as boolean for the visibility of the WebElement(Doesn't wait)
     *
     * @param by
     * @param eleName
     * @return boolean - Visibility
     */
    public static boolean _isDisplayed(By by, String eleName) {
        boolean flag = false;
        try {
            flag = _performExplicitWait(by, NONE).isDisplayed();
        } catch (Exception e) {
            flag = false;
        }
        if (!eleName.isEmpty()) Logger.info(eleName + " element is displayed : " + flag);
        return flag;
    }


    /**
     * Waits for the visibility of the WebElement & Returns TRUE/FALSE as boolean
     *
     * @param by
     * @param eleName
     * @return boolean - Visibility
     */
    public static boolean _waitForDisplayed(By by, String eleName) {
        boolean flag = false;
        try {
            flag = _performExplicitWait(by, VISIBLE).isDisplayed();
        } catch (StaleElementReferenceException e) {
            flag = _performExplicitWait(by, HANDLESTALE).isDisplayed();
        } catch (Exception e) {
            flag = false;
        }
        if (!eleName.isEmpty()) Logger.info(eleName + " element is displayed : " + flag);
        return flag;
    }

    public static Boolean _waitForInvisibility(By by) {
        return _getWebDriverWait(Constants.DEFAULT_WEBDRIVERWAIT).until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static Boolean _waitForInvisibility(By by, long timeOut) {
        return _getWebDriverWait(timeOut).until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static boolean _waitForTextPresentOnPage(String txtToBePresent, boolean ignoreCase) {
        boolean status = false;
        _waitForPageToLoadCompletely();
        for (long i = 0; i < 5; i++) {
            if (ignoreCase) {
                status = DriverManager.getDriver().getPageSource().toLowerCase().contains(txtToBePresent.toLowerCase());
            } else {
                status = DriverManager.getDriver().getPageSource().contains(txtToBePresent);
            }
            if (status) return true;
            _hardWait(2);
        }
        return status;
    }

    /**
     * Returns whether this element is selected or not.
     * This operation only applies to input elements such as checkboxes, options in a select and radio buttons.
     *
     * @param by
     * @return : boolean
     */
    public static boolean _isSelected(By by, String eleName) {
        boolean flag = false;
        flag = _performExplicitWait(by, PRESENCE).isSelected();
        if (!eleName.isEmpty()) Logger.info(eleName + " element is selected : " + flag);
        return flag;
    }


    /**
     * Selects the values from dropdown
     *
     * @param by:
     * @param value:
     */
    public static void _select_ByValue(By by, String value, String eleName) {
        WebElement element;
        try {
            element = _performExplicitWait(by, CLICKABLE);
            new Select(element).selectByValue(value);
        } catch (StaleElementReferenceException e) {
            element = _performExplicitWait(by, HANDLESTALE);
            new Select(element).selectByValue(value);
        }
        if (!eleName.isEmpty()) Logger.info("'" + value + "' is selected value from dropdown " + eleName);
    }

    /**
     * Selects the visible text option from dropdown
     *
     * @param by:
     * @param value:
     */
    public static void _select_ByVisibleText(By by, String value, String eleName) {
        WebElement element;
        try {
            element = _performExplicitWait(by, CLICKABLE);
            new Select(element).selectByVisibleText(value);
        } catch (StaleElementReferenceException e) {
            element = _performExplicitWait(by, HANDLESTALE);
            new Select(element).selectByVisibleText(value);
        }
        if (!eleName.isEmpty()) Logger.info("'" + value + "' option is selected from dropdown " + eleName);
    }

    public static String _get_select_OptionText(By by, WaitStrategy waitStrategy, String elementName) {
        WebElement e = _performExplicitWait(by, waitStrategy);
        String value = new Select(e).getFirstSelectedOption().getText();
        if (!elementName.isEmpty()) Logger.info("'" + value + "' option txt is returned from dropdown " + elementName);
        return value;
    }

    // ****************** Alerts ****************
    public static String _alertGetText() {
        Alert alert = _getWebDriverWait(10).until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Logger.info("'" + alertText + "' txt is returned from alert");
        return alertText;
    }

    public static void _alertAccept() {
        Alert alert = _getWebDriverWait(10).until(ExpectedConditions.alertIsPresent());
        alert.accept();
        Logger.info("Accepted the alert");
    }

    public static void _alertDismiss() {
        Alert alert = _getWebDriverWait(10).until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
        Logger.info("Dismissed the alert");
    }

    public static void _alertEnterText(String text) {
        Alert alert = _getWebDriverWait(10).until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);
        Logger.info("'" + text + "' txt is entered to alert");
    }

    // ****************** Window handling ****************

    /**
     * Selects either the first frame on the page, or the main document when a page contains iframes.
     * Returns:
     * This driver focused on the top window/first frame.
     */
    public static void _switchToDefaultContent() {
        DriverManager.getDriver().switchTo().defaultContent();
    }

    /**
     * @return the current window handle as a string
     */
    public static String _getWindowHandle() {
        return DriverManager.getDriver().getWindowHandle();
    }

    public static void _switchToWindow(String windowId) {
        DriverManager.getDriver().switchTo().window(windowId);
    }

    public static Set<String> _getWindowHandles() {
        return _getWebDriverWait(10).until(d -> {
            boolean b = d.getWindowHandles().size() > 1;
            return d.getWindowHandles();
        });
        //return DriverManager.getDriver().getWindowHandles();
    }


/*
    public static boolean selectListValues(By traverseList, String dataVal, WebDriver driver, String listName) {
        boolean flag = false;
        for (WebElement e : getElements(traverseList, driver, listName)) {
            System.out.println(e.getText());
            if (e.getText().trim().equalsIgnoreCase(dataVal.trim())) {
                scrollDown(e, listName);
                e.click();
                //LoggerUtil.info(myLogger, "Selected  value = "+dataVal+" in List "+listName);

                return true;
            }
        }
        System.out.println("value  = " + dataVal + " Not Found in List " + listName);
        //LoggerUtil.info(myLogger, "  value  = "+dataVal+" Not Found in List "+listName);

        return flag;
    }
*/

    /**
     * Returns the Texts as List for All displayed Elements
     * @param by
     * @return String list
     */
    public ArrayList<String> _getAllDisplayedElementsText(By by) {
        ArrayList<String> allDisplayedEleText = new ArrayList<>();
        for (WebElement webElement : _getElements(by, "")) {
            if (webElement.isDisplayed()) allDisplayedEleText.add(webElement.getText().trim());
        }
        return allDisplayedEleText;
    }


    /**
     * Used to select partial List Value
     * @param traverseList
     * @param partialVal
     * @param listName
     * @return
     */
    /*
    public static boolean selectListWithPartialValues(By traverseList, String partialVal, WebDriver driver, String listName) {
        boolean fnselectlistFlag = false;
        for (WebElement e : getElements(traverseList, listName)) {
            String val = e.getText();
            if (val.contains(partialVal)) {
//clickByJS(e, driver, listName);

                e.click();
                fnselectlistFlag = true;
                //LoggerUtil.info(myLogger, "Selected  value = "+val+" in list "+listName);

                break;
            }
        }

        //LoggerUtil.info(myLogger, "  value  = "+partialVal+"  not foud in list "+listName);

        return fnselectlistFlag;
    }
*/

/***************************** Waits Handling **********************************/

    /**
     * Wait for Visibility of Element
     *
     * @return: WebElement
     */
    /*
    public static WebElement _waitForVisibilityOfElement(By by, String eleName) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));

        WebElement element;
        try {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            element = wait.until(ExpectedConditions.visibilityOf(element));
            //LoggerUtil.info(myLogger, "waiting for Element == "+eleName);
        } catch (TimeoutException | NoSuchElementException se) {
            //LoggerUtil.info(myLogger, eleName+"  Element Not found after wait  ");


            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));

        }
        return element;
    }

    /**
     * Wait for Element to be clickable
     *
     * @param locator
     * @param driver: WebDriver
     * @return : WebElement
     */
    /*
    public static WebElement _waitForElementToBeClickable(By locator, WebDriver driver, String eleName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //LoggerUtil.info(myLogger, "Waiting for  "+eleName+"  Element to be clickable  ");
//        waitForVisibilityOfElement(locator, driver, eleName);
        WebElement element;
        element = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
        return element;
    }

    /**
     * Wait for Page to load completely
     */
    public static void _waitForPageToLoadCompletely() {
        _performExplicitWaitForElements(By.xpath("//*"), PRESENCE);
    }

    /**
     * Reset Implicit wait
     *
     * @param newTimeOut

    public static void resetImplicitTimeout(int newTimeOut) {
    DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(newTimeOut));
    }
     */

    /**
     * Hard wait
     * @param seconds:
     */
    public static void _hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

/************************ Misc Browser Actions ********************/

public static Actions _getAction() {
    return new Actions(DriverManager.getDriver());
}
    /**
     * Mouse hover on any Element
     * @param by
     */
    public static void _mouseHover(By by, String eleName) {
        WebElement ele = _performExplicitWait(by, VISIBLE);
        _getAction().moveToElement(ele).build().perform();
        if (!eleName.isEmpty()) Logger.info("Mouse Hovered - " + eleName);
    }

    /**
     * Enter text using Action class
     * @param by
     * @param text
     */
    public static void _sendKeysByAction(By by, String text, String eleName) {
        Actions actions = _getAction();
        WebElement element = _performExplicitWait(by, VISIBLE);
        actions.moveToElement(element);
        actions.click();
        actions.sendKeys(text);
        actions.build().perform();
        if (!eleName.isEmpty()) Logger.info("'" + text + "' is enter(using actions) into  " + eleName);
    }

    /**
     * Click using Action class
     * @param by
     */
    public static void _clickByAction(By by, String eleName) {
        WebElement element = _performExplicitWait(by, VISIBLE);
        _getAction().moveToElement(element).click(element).build().perform();
        if (!eleName.isEmpty()) Logger.info("Clicked(using actions) on " + eleName);
    }

    /**
     * Double Click using Action class
     * @param by
     */
    public static void _doubleClick(By by, String eleName) {
        WebElement element = _performExplicitWait(by, VISIBLE);
        _getAction().moveToElement(element).doubleClick().build().perform();
        if (!eleName.isEmpty()) Logger.info("DoubleClick(using actions) on " + eleName);
    }

    public static void _dragDrop(By sourceBy, By targetBy, String eleName1, String eleName2) {
        WebElement element1 = _performExplicitWait(sourceBy, VISIBLE);
        WebElement element2 = _performExplicitWait(targetBy, VISIBLE);
        _getAction().moveToElement(element1).dragAndDrop(element1, element2).build().perform();
        if (!eleName1.isEmpty()) Logger.info("Dragged: " + eleName1 + " over target: " + eleName2);
    }

/*********************Java scripts functions***********************/

    /**
     * To Execute javascript
     */
    public static void _executeJavascript(String script, WebDriver driver, String jsFor) {
        ((JavascriptExecutor) driver).executeScript(script);
        //LoggerUtil.info(myLogger, "Executed javascript for "+jsFor);
    }

    /**
     * Waits for 5 secs
     * @return
     */
    public static Boolean _untilJqueryIsDone(){
        return _untilJqueryIsDone(5);
    }
    public static Boolean _untilJqueryIsDone(long timeoutInSeconds) {
        return _getWebDriverWait(timeoutInSeconds).until(d -> {
            Boolean isJqueryCallDone = (Boolean) ((JavascriptExecutor) d).executeScript("return jQuery.active==0");
            //if (!isJqueryCallDone) System.out.println("JQuery call is in Progress....");
            return isJqueryCallDone;
        });
    }

    /**
     * This method waits for the element to be clickable and clicks using javascript
     *
     * @param by: locator
     */
    public static void _clickByJS(By by, String eleName) {
        JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
        try {
            _performExplicitWait(by, CLICKABLE);
            executor.executeScript("arguments[0].click();", _getElement(by, eleName));
        } catch (StaleElementReferenceException se) {
            _performExplicitWait(by, HANDLESTALE);
            executor.executeScript("arguments[0].click();", _getElement(by, eleName));
        }
        if (!eleName.isEmpty()) Logger.info("Clicked(using JS) on " + eleName);
    }

    /**
     * This method waits for the element to be clickable and clicks using javascript
     */
    /*
    public static void clickByJS(WebElement ele, WebDriver driver, String eleName) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        try {
            executor.executeScript("arguments[0].click();", ele);
            //LoggerUtil.info(myLogger, "js Clicked On Element  "+eleName);

        } catch (StaleElementReferenceException se) {
            scrollDown(ele, eleName);
            executor.executeScript("arguments[0].click();", ele);
            //LoggerUtil.info(myLogger, "js Clicked On Element  "+eleName);

        }
    }
/*
    /**
     * Scroll the element to center
     * @param by: locator
     */
    public static void _scrollIntoView(By by) {
        WebElement element = _performExplicitWait(by, VISIBLE);
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView({block: \"center\"});", element);
        //if (!eleName.isEmpty()) Logger.info("ScrollDown  by js till " + eleName);
        _hardWait(1);
    }

    /**
     * This method is used to scroll Down window
     */
    public static void _scrollDownByText(By listOptions, String text, String eleName) {
        for (WebElement e : _getElements(listOptions, eleName)) {
            if (e.getText().trim().contains(text.trim())) {
                ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", e);
            }
        }
        if (!eleName.isEmpty()) Logger.info("ScrollDown  by js till " + eleName);

    }

    /**
     * Scroll the element to center
     */
    public static void _scrollIntoView(WebElement ele, String eleName) {
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView({block: \"center\"});", ele);
        if (!eleName.isEmpty()) Logger.info("ScrollDown  by js till " + eleName);

    }


    /**
     * Scroll the element to Top
     */
    public static void _scrollToTop() {
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("window.scrollTo(0, 0);");
        Logger.info("Scrolled to top");

    }

    /**
     * Scroll the element to Down
     */
    public static void _scrollToDown(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Logger.info("Scrolled to Bottom");
    }


    /**
     * Waits for the element to be present & return boolean presence status
     *
     * @param by
     * @return
     */
    public static boolean _waitForPresent(By by, String eleName) {
        if (_performExplicitWaitForElements(by, PRESENCE).size() > 0) {
            if (!eleName.isEmpty()) Logger.info(String.format("Element: %s, is Present", eleName));
            return true;
        } else {
            if (!eleName.isEmpty()) Logger.info(String.format("Element: %s, is Not Present", eleName));
            return false;
        }
    }

/*
    public static boolean _selectListValuesWithoutScrolling(By traverseList, String dataVal, String listName) {
        boolean flag = false;
        for (WebElement e : getElements(traverseList, listName)) {
            if (e.getText().trim().equalsIgnoreCase(dataVal.trim())) {
                scrollDown(e, listName);
                e.click();
                clickByJS(traverseList,listName);
                ;
                //LoggerUtil.info(myLogger, "Selected  value = "+dataVal+" in List "+listName);
                return true;
            }
        }
        //LoggerUtil.info(myLogger, "  value  = "+dataVal+" Not Found in List "+listName);

        return flag;
    }
*/

    public static boolean _selectListValuesUsingJS(By traverseList, String dataVal, String listName) {
        boolean flag = false;
        for (WebElement e : _getElements(traverseList, listName)) {
            System.out.println(e.getText());
            if (e.getText().trim().contains(dataVal.trim())) {
                _scrollIntoView(e, listName);
                _clickByJS(traverseList, listName);
//e.click();
                //LoggerUtil.info(myLogger, "Selected  value = "+dataVal+" in List "+listName);

                return true;
            }
        }
        //LoggerUtil.info(myLogger, "  value  = "+dataVal+" Not Found in List "+listName);

        return flag;
    }
/*
    public static boolean isValuePresentInListUsingJS(By traverseList, String dataVal, WebDriver driver, String listName) {
        boolean flag = false;
        for (WebElement e : getElements(traverseList, driver, listName)) {
            System.out.println(e.getText());
            if (e.getText().trim().contains(dataVal.trim())) {
                return true;
            }
        }
        //LoggerUtil.info(myLogger, "  value  = "+dataVal+" Not Found in List "+listName);

        return flag;
    }
*/


    public static void _scrollBy(String horizontalCoordinates, String verticalCoordinates) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollBy(" + horizontalCoordinates + "," + verticalCoordinates + ")");
        Logger.info("Scroll by : " + horizontalCoordinates + "," + verticalCoordinates);

    }


}
