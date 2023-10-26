package generic;

import com.codeborne.selenide.*;
import com.codeborne.selenide.appium.*;
import constants.Constants;
import io.appium.java_client.AppiumDriver;
import logger.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static com.codeborne.selenide.Selenide.*;

public class AppiumFunctions {


    public static WebElement _getWebElement( By by) {
        return $(by).getWrappedElement();
    }

    public static List<WebElement> _getWebElements(By by) {
        return $$(by).stream().map(e -> e.getWrappedElement()).collect(Collectors.toList());
    }

    /**
     * Perform clicking the back button of the navigation
     */
    public static void _back() {
        SelenideAppium.back();
    }

    /**
     * Enter the text to the text field
     * @param by
     * @param txt
     * @param elementName
     */
    public static void _sendKeys(By by, String txt, String elementName) {
        $(by).shouldBe(interactable).sendKeys(txt);
        if (!elementName.isEmpty()) Logger.info("'" + txt + "' is entered into " + elementName);
    }

    /**
     * Clears the text(if any) from input text field and Enter the text
     * @param by
     * @param txt
     * @param elementName
     */
    public static void _sendKeys_clear(By by, String txt, String elementName) {
        $(by).shouldBe(interactable).setValue(txt);
        if (!elementName.isEmpty()) Logger.info("'" + txt + "' is entered into " + elementName);
    }

    /**
     * Enter the password to the text field and hides the password to be displayed in the logs
     * @param by
     * @param txt
     * @param elementName
     */
    public static void _sendKeysPassword(By by, String txt, String elementName) {
        $(by).shouldBe(interactable).sendKeys(txt);
        if (!elementName.isEmpty()) Logger.info("'*******' password is entered into " + elementName);
    }

    /**
     * Enter the text to the text field and hides the keyboard
     * @param by
     * @param txt
     * @param elementName
     */
    public static void _sendKeysHideKeyboard(By by, String txt, String elementName ) {
        $(by).shouldBe(interactable).sendKeys(txt);
        $(by).hideKeyboard();
        if (!elementName.isEmpty()) Logger.info("'" + txt + "' is entered into " + elementName);
    }

    /**
     * ScrollUp to element, if element not founds scrolls up 30(default) times to find the element
     * @param by
     * @param elementName
     */
    public static void _scrollOrSwipeUp(By by, String elementName){
        $(by).scroll(AppiumScrollOptions.up());
        if (!elementName.isEmpty()) Logger.info("Scroll Up to " + elementName);
    }

    /**
     * ScrollUp to element, if element not founds scrolls up 'noOfSwipe' times to find the element
     * @param by
     * @param noOfSwipe
     * @param elementName
     */
    public static void _scrollOrSwipeUp(By by, int noOfSwipe, String elementName){
        $(by).scroll(AppiumScrollOptions.up(noOfSwipe));
        if (!elementName.isEmpty()) Logger.info("Scroll Up "+noOfSwipe+" times to " + elementName);
    }

    /**
     * ScrollDown to element, if element not founds scrolls down 'noOfSwipe' times to find the element
     * @param by
     * @param noOfSwipe
     * @param elementName
     */
    public static void _scrollOrSwipeDown(By by, int noOfSwipe, String elementName){
        $(by).scroll(AppiumScrollOptions.down(noOfSwipe));
        if (!elementName.isEmpty()) Logger.info("Scroll Down "+noOfSwipe+" times to " + elementName);
    }

    /**
     * Swipe right from the element(as reference)
     * @param by
     * @param noOfSwipe
     * @param elementName
     */
    public static void _scrollOrSwipeRight(By by, int noOfSwipe, String elementName){
        $(by).swipe(AppiumSwipeOptions.right(noOfSwipe));
        if (!elementName.isEmpty()) Logger.info("Swipe Right "+noOfSwipe+" times to " + elementName);
    }

    /**
     * Swipe Right from the element(as reference), scrolls 500 pixels of the screen
     * @param by
     * @param elementName
     */
    public static void _scrollOrSwipeRight(By by, String elementName){
        WebElement scrollElement = $(by).scroll(AppiumScrollOptions.down(5)).shouldBe(exist).getWrappedElement();
        Point location = scrollElement.getLocation();
        Dimension size = scrollElement.getSize();
        int startX = location.getX() + size.getWidth() / 2;
        int startY = location.getY() + size.getHeight() / 2;

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(input, 0);
        sequence.addAction(input.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(input.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX + 500, startY));
        sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        AppiumDriverRunner.getMobileDriver().perform(Arrays.asList(sequence));
        if (!elementName.isEmpty()) Logger.info("Swipe Right from " + elementName);
    }

    /**
     * Swipe left from the element(as reference)
     * @param by
     * @param noOfSwipe
     * @param elementName
     */
    public static void _scrollOrSwipeLeft(By by, int noOfSwipe, String elementName){
        $(by).swipe(AppiumSwipeOptions.left(noOfSwipe));
        if (!elementName.isEmpty()) Logger.info("Swipe Left "+noOfSwipe+" times to " + elementName);
    }
    /**
     * Swipe Left from the element(as reference), scrolls 500 pixels of the screen
     * @param by
     * @param elementName
     */
    public static void _scrollOrSwipeLeft(By by, String elementName){
        WebElement scrollElement = $(by).scroll(AppiumScrollOptions.down(5)).shouldBe(exist).getWrappedElement();
        Point location = scrollElement.getLocation();
        Dimension size = scrollElement.getSize();
        int startX = location.getX() + size.getWidth() / 2;
        int startY = location.getY() + size.getHeight() / 2;

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(input, 0);
        sequence.addAction(input.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(input.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX - 500, startY));
        sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        AppiumDriverRunner.getMobileDriver().perform(Arrays.asList(sequence));
        if (!elementName.isEmpty()) Logger.info("Swipe Left from " + elementName);
    }

    /**
     * Swipe Left from the middle of the screen
     */
    public static void _scrollOrSwipeLeft() {
        AppiumDriver driver = AppiumDriverRunner.getMobileDriver();
        // Get the device screen dimensions
        Dimension size = driver.manage().window().getSize();
        // Get coordinate at the center of the screen for the pinch gesture
        int startX = (int) (size.getWidth() * 0.5);
        int startY = (int) (size.getHeight() * 0.5);
        int endX = (int) (size.getHeight() * 0.25);

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(input, 0);
        sequence.addAction(input.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(input.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX - endX, startY));
        sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(sequence));
    }

    public static void _scrollOrSwipeLeft(int noOfSwipe) {
        for (int i = 0; i < noOfSwipe; i++) {
            _scrollOrSwipeLeft();
        }
    }

    /**
     * Swipe Right from the middle of the screen
     */
    public static void _scrollOrSwipeRight() {
        AppiumDriver driver = AppiumDriverRunner.getMobileDriver();
        // Get the device screen dimensions
        Dimension size = driver.manage().window().getSize();
        // Get coordinate at the center of the screen for the pinch gesture
        int startX = (int) (size.getWidth() * 0.5);
        int startY = (int) (size.getHeight() * 0.5);
        int endX = (int) (size.getHeight() * 0.25);

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(input, 0);
        sequence.addAction(input.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(input.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX + endX, startY));
        sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(sequence));
    }

    public static void _scrollOrSwipeRight(int noOfSwipe) {
        for (int i = 0; i < noOfSwipe; i++) {
            _scrollOrSwipeRight();
        }
    }

    /**
     * Swipe Up from the middle of the screen
     */
    public static void _scrollOrSwipeUp() {
        AppiumDriver driver = AppiumDriverRunner.getMobileDriver();
        // Get the device screen dimensions
        Dimension size = driver.manage().window().getSize();
        // Get coordinate at the center of the screen for the pinch gesture
        int startX = (int) (size.getWidth() * 0.5);
        int startY = (int) (size.getHeight() * 0.5);
        int endY = (int) (size.getHeight() * 0.25);

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(input, 0);
        sequence.addAction(input.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(input.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX , startY - endY));
        sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(sequence));
    }

    public static void _scrollOrSwipeUp(int noOfSwipe) {
        for (int i = 0; i < noOfSwipe; i++) {
            _scrollOrSwipeUp();
        }
    }

    /**
     * Swipe Down from the middle of the screen
     */
    public static void _scrollOrSwipeDown() {
        AppiumDriver driver = AppiumDriverRunner.getMobileDriver();
        // Get the device screen dimensions
        Dimension size = driver.manage().window().getSize();
        // Get coordinate at the center of the screen for the pinch gesture
        int startX = (int) (size.getWidth() * 0.5);
        int startY = (int) (size.getHeight() * 0.5);
        int endY = (int) (size.getHeight() * 0.25);

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(input, 0);
        sequence.addAction(input.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        sequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(input.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX , startY + endY));
        sequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(sequence));
    }

    public static void _scrollOrSwipeDown(int noOfSwipe) {
        for (int i = 0; i < noOfSwipe; i++) {
            _scrollOrSwipeDown();
        }
    }

    /**
     * Drag and drop
     * @param byFrom : Element to be dragged
     * @param byTo : Element on which the dragged to be dropped
     * @param elementNameFrom
     * @param elementNameTo
     */
    public static void _dragDrop(By byFrom, By byTo, String elementNameFrom, String elementNameTo){
        $(byFrom).scroll(AppiumScrollOptions.down(5)).shouldBe(interactable).dragAndDrop(DragAndDropOptions.to($(byTo).shouldBe(visible)));
        if (!elementNameFrom.isEmpty()) Logger.info("Drag from element: "+elementNameFrom+" to element: " + elementNameTo);
    }

    /**
     * Tap on element, if element not founds scrolls down 5 times to find the element
     * @param by
     * @param elementName
     */
    public static void _tap(By by, String elementName){
        $(by).scroll(AppiumScrollOptions.down(5)).shouldBe(interactable).click(AppiumClickOptions.tap());
        if (!elementName.isEmpty()) Logger.info("Tap on " + elementName);
    }

    /**
     * DoubleTap on element, if element not founds scrolls down 5 times to find the element
     * @param by
     * @param elementName
     */
    public static void _doubleTap(By by, String elementName){
        $(by).scroll(AppiumScrollOptions.down(5)).shouldBe(interactable).click(AppiumClickOptions.doubleTap());
        if (!elementName.isEmpty()) Logger.info("DoubleTap on " + elementName);
    }

    /**
     * LongPress on element, if element not founds scrolls down 5 times to find the element
     * @param by
     * @param elementName
     */
    public static void _longPress(By by, String elementName){
        $(by).scroll(AppiumScrollOptions.down(5)).shouldBe(interactable).click(AppiumClickOptions.longPress());
        if (!elementName.isEmpty()) Logger.info("LongPress on " + elementName);
    }

    /**
     * Click on element, if element not founds scrolls down 5 times to find the element
     * @param by
     * @param elementName
     */
    public static void _click(By by, String elementName) {
        $(by).scroll(AppiumScrollOptions.down(5)).shouldBe(interactable).click();
        if (!elementName.isEmpty()) Logger.info("Tap on " + elementName);
    }

    /**
     * Returns the visible text of the element
     * @param by
     * @param elementName
     */
    public static String _getText(By by, String elementName) {
        String sText = $(by).scroll(AppiumScrollOptions.down(5)).shouldBe(exist).getText();
        if (!elementName.isEmpty()) Logger.info("'" + sText + "' is returned from " + elementName);
        return sText;
    }

    /**
     * Returns the attribute value  for the given attribute
     * @param by
     * @param attribute
     * @param elementName
     * @return : String attribute value
     */
    public static String _getAttributeValue(By by, String attribute, String elementName) {
        String sText = $(by).scroll(AppiumScrollOptions.down(5)).shouldBe(exist).getAttribute(attribute);
        if (!elementName.isEmpty()) Logger.info("'" + sText + "' attribute value is returned from " + elementName);
        return sText;
    }

    /**
     * Upload the given file
     * @param by Applicable for: <input type="file">.
     * @param filePath : full file path with extension
     * @param elementName
     */
    public static void _uploadFile(By by, String filePath, String elementName) {
        File file = new File(filePath);
        $(by).scroll(AppiumScrollOptions.down(5)).shouldBe(interactable).uploadFile(file);
        if (!elementName.isEmpty()) Logger.info("'" + filePath + "' is uploaded from element " + elementName);
    }

    /**
     * Returns Appium wrapper web element
     * @param by
     * @return SelenideAppiumElement
     */
    public static SelenideAppiumElement _getSelenideAppiumElement(By by){
        return $(by);
    }

    /**
     * Waits for visibility of element, un till the timeout is reached
     * @param by
     * @param timeOut
     */
    public static void _waitForVisibilityOfElement(By by, int timeOut) {
        $(by).shouldBe(visible, Duration.ofSeconds(timeOut) );
    }

    public static void _hardWait(int sec) {
        Selenide.sleep(sec * 1000);
    }

    public static void _hideKeyboard() {
        $(By.xpath("//")).hideKeyboard();
    }

    /**
     * Zooms out(if allowed to zoom) the screen for 200 pixels from the center
     */
    public static void _zoom() {
        AppiumDriver driver = AppiumDriverRunner.getMobileDriver();
        // Get the device screen dimensions
        Dimension size = driver.manage().window().getSize();
        // Get coordinate at the center of the screen for the pinch gesture
        int startX = size.getWidth() / 2;
        int startY = size.getHeight() / 2;
        // Define the pinch-in and pinch-out coordinates
        int pinchInX = startX;
        int pinchInY = startY;
        int pinchOutX = startX;
        int pinchOutY = startY;

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

        Sequence sequence1 = new Sequence(finger1, 0)
                .addAction(finger1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), pinchInX, pinchInY))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger1.createPointerMove(Duration.ofMillis(200), PointerInput.Origin.viewport(), pinchInX, pinchInY - 200))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        Sequence sequence2 = new Sequence(finger2, 0)
                .addAction(finger2.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), pinchOutX, pinchOutY))
                .addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger2.createPointerMove(Duration.ofMillis(200), PointerInput.Origin.viewport(), pinchOutX, pinchOutY + 200))
                .addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(sequence1, sequence2));
    }

    /**
     * Zooms out(if allowed to zoom) the screen to the specified zoom percentage
     * Lets say zoomPercentage value is 30, only 30% of the screen will be zoomed
     * @param zoomPercentage: double value to zoom
     */
    public static void _zoom(double zoomPercentage) {
        AppiumDriver driver = AppiumDriverRunner.getMobileDriver();
        // Get the device screen dimensions
        Dimension size = driver.manage().window().getSize();
        // Get coordinate at the center of the screen for the pinch gesture
        int startX = size.getWidth() / 2;
        int startY = size.getHeight() / 2;
        // Define the pinch-in and pinch-out coordinates
        int pinchInX = startX;
        int pinchInY = startY;
        int pinchOutX = startX;
        int pinchOutY = startY;

        if(zoomPercentage < 5) zoomPercentage = 5;
        if(zoomPercentage > 80) zoomPercentage = 80;
        int inPercentage = (int) (size.getHeight() * (zoomPercentage/100));

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

        Sequence sequence1 = new Sequence(finger1, 0)
                .addAction(finger1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), pinchInX, pinchInY))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger1.createPointerMove(Duration.ofMillis(200), PointerInput.Origin.viewport(), pinchInX, pinchInY - inPercentage))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        Sequence sequence2 = new Sequence(finger2, 0)
                .addAction(finger2.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), pinchOutX, pinchOutY))
                .addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger2.createPointerMove(Duration.ofMillis(200), PointerInput.Origin.viewport(), pinchOutX, pinchOutY + inPercentage))
                .addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(sequence1, sequence2));
    }


    /**
     * Returns the Display status of the element
     * @param by
     * @param elementName
     * @return true if the element is displayed, false if element does not exist
     */
    public static boolean _isDisplayed(By by, String elementName) {
        Boolean flag = $(by).isDisplayed();
        Logger.info(String.format("Display status of %s, is %s", elementName, flag));
        return flag;
    }

    /**
     * Determine whether this element is selected or not. This operation only applies to input elements such as checkboxes, options in a select and radio buttons
     * @param by
     * @param elementName
     * @return True if the element is currently selected or checked, false otherwise.
     */
    public static boolean _isSelected(By by, String elementName) {
        Boolean flag = $(by).isSelected();
        Logger.info(String.format("Selected status of %s, is %s", elementName, flag));
        return flag;
    }

    /**
     * Is the element currently enabled or not? This will generally return true for everything but disabled input elements.
     * @param by
     * @param elementName
     * @return True if the element is enabled, false otherwise.
     */
    public static boolean _isEnabled(By by, String elementName) {
        Boolean flag = $(by).isEnabled();
        Logger.info(String.format("enabled status of %s, is %s", elementName, flag));
        return flag;
    }

    /**
     * Select visible text from drop down
     * @param by
     * @param txt
     * @param elementName
     */
    public static void _selectVisibleText(By by, String txt, String elementName){
        $(by).shouldBe(interactable).selectOption(txt);
        if (!elementName.isEmpty()) Logger.info("'" + txt + "' is selected from dropdown " + elementName);
    }


    public static Dimension _getScreenSize() {
        return AppiumDriverRunner.getMobileDriver().manage().window().getSize();
    }

    /**
     * Capture the element screenshot if it exists
     * @param by
     * @param filenameWithoutExtension
     * @return : screenshot saved location full file path
     */
    public String elementScreenshot(By by, String filenameWithoutExtension) {
        String filePath = Constants.DEFAULT_SCREENSHOT_PATH + filenameWithoutExtension+".png";
        try {
            File scrFile = $(by).scrollTo().screenshot();
            FileUtils.copyFile(scrFile, new File(filePath));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return filePath;

    }



//    public void verticalScroll(WebDriver driver, By scrollableScreenElement) {
//        Logger.info("Vertical scrolling till element visibility: "+scrollableScreenElement);
//        //Creating Vertical Scroll Event
//        //Scrollable Element
//        WebElement ele01 = findElement(driver, scrollableScreenElement);
//        int centerX = ele01.getRect().x + (ele01.getSize().width / 2);
//
//        double startY = ele01.getRect().y + (ele01.getSize().height * 0.9);
//
//        double endY = ele01.getRect().y + (ele01.getSize().height * 0.1);
//        //Type of Pointer Input
//        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
//        //Creating Sequence object to add actions
//        Sequence swipe = new Sequence(finger, 1);
//        //Move finger into starting position
//        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), centerX, (int) startY));
//        //Finger comes down into contact with screen
//        swipe.addAction(finger.createPointerDown(0));
//        //Finger moves to end position
//        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
//                PointerInput.Origin.viewport(), centerX, (int) endY));
//        //Get up Finger from Srceen
//        swipe.addAction(finger.createPointerUp(0));
//
//        //Perform the actions
//        //driver.perform(Arrays.asList(swipe));
//        _hardWait(1);
//
//    }


//    public void horizontalScroll(WebDriver driver, By scrollableScreenElement) {
//        Logger.info("horizontal scrolling till element visibility: "+scrollableScreenElement);
//        //Creating Horizontal Scroll Event
//        //Scrollable Element
//        WebElement ele01 = findElement(driver, scrollableScreenElement);
//
//        int centerY = ele01.getRect().y + (ele01.getSize().height / 2);
//
//        double startX = ele01.getRect().x + (ele01.getSize().width * 0.9);
//
//        double endX = ele01.getRect().x + (ele01.getSize().width * 0.1);
//        //Type of Pointer Input
//        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
//        //Creating Sequence object to add actions
//        Sequence swipe = new Sequence(finger, 1);
//        //Move finger into starting position
//        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), (int) startX, centerY));
//        //Finger comes down into contact with screen
//        swipe.addAction(finger.createPointerDown(0));
//        //Finger moves to end position
//        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
//                PointerInput.Origin.viewport(), (int) endX, centerY));
//        //Get up Finger from Srceen
//        swipe.addAction(finger.createPointerUp(0));
//
//        //Perform the actions
//        //driver.perform(Arrays.asList(swipe));
//
//        _hardWait(1);
//
//    }


//    public void verticalScrollUp(WebDriver driver) {
//        //Creating Vertical Scroll Event
//        //Scrollable Element
//        int[] size = getScreenSize(driver);
//
//        int centerX = size[1] / 2;
//
//        double startY = size[0] * 0.8;
//
//        double endY = size[0] * 0.2;
//        //Type of Pointer Input
//        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
//        //Creating Sequence object to add actions
//        Sequence swipe = new Sequence(finger, 1);
//        //Move finger into starting position
//        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), centerX, (int) startY));
//        //Finger comes down into contact with screen
//        swipe.addAction(finger.createPointerDown(0));
//        //Finger moves to end position
//        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
//                PointerInput.Origin.viewport(), centerX, (int) endY));
//        //Get up Finger from Srceen
//        swipe.addAction(finger.createPointerUp(0));
//
//        //Perform the actions
//        //driver.perform(Arrays.asList(swipe));
//        _hardWait(1);
//
//    }

//    public void verticalScrollDown(AppiumDriver driver) {
//        //Creating Vertical Scroll Event
//        //Scrollable Element
//        int[] size = getScreenSize(driver);
//
//        int centerX = size[1] / 2;
//
//        double startY = size[0] * 0.2;
//
//        double endY = size[0] * 0.8;
//        //Type of Pointer Input
//        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
//        //Creating Sequence object to add actions
//        Sequence swipe = new Sequence(finger, 1);
//        //Move finger into starting position
//        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), centerX, (int) startY));
//        //Finger comes down into contact with screen
//        swipe.addAction(finger.createPointerDown(0));
//        //Finger moves to end position
//        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
//                PointerInput.Origin.viewport(), centerX, (int) endY));
//        //Get up Finger from Srceen
//        swipe.addAction(finger.createPointerUp(0));
//
//        //Perform the actions
//        driver.perform(Arrays.asList(swipe));
//        //hardWait(1);
//
//    }

//
//    public void horizontalScrollRight(WebDriver driver) {
//        //Creating Horizontal Scroll Event
//        //Scrollable Element
//        int[] size = getScreenSize(driver);
//
//        int centerY = size[0] / 2;
//
//        double startX = size[1] * 0.8;
//
//        double endX = size[1] * 0.2;
//
//        //Type of Pointer Input
//        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
//        //Creating Sequence object to add actions
//        Sequence swipe = new Sequence(finger, 1);
//        //Move finger into starting position
//        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), (int) startX, centerY));
//        //Finger comes down into contact with screen
//        swipe.addAction(finger.createPointerDown(0));
//        //Finger moves to end position
//        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
//                PointerInput.Origin.viewport(), (int) endX, centerY));
//        //Get up Finger from Srceen
//        swipe.addAction(finger.createPointerUp(0));
//
//        //Perform the actions
//        //driver.perform(Arrays.asList(swipe));
//
//        //hardWait(1);
//
//    }

//    public void horizontalScrollLeft(AppiumDriver driver) {
//        //Creating Horizontal Scroll Event
//        //Scrollable Element
//        int[] size = getScreenSize(driver);
//
//        int centerY = size[0] / 2;
//
//        double startX = size[1] * 0.2;
//
//        double endX = size[1] * 0.8;
//
//        //Type of Pointer Input
//        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
//        //Creating Sequence object to add actions
//        Sequence swipe = new Sequence(finger, 1);
//        //Move finger into starting position
//        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), (int) startX, centerY));
//        //Finger comes down into contact with screen
//        swipe.addAction(finger.createPointerDown(0));
//        //Finger moves to end position
//        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
//                PointerInput.Origin.viewport(), (int) endX, centerY));
//        //Get up Finger from Srceen
//        swipe.addAction(finger.createPointerUp(0));
//
//        //Perform the actions
//        driver.perform(Arrays.asList(swipe));
//
//        //hardWait(1);
//
//    }


//
//    public boolean horizontalScrollUntilElementIsVisible(WebDriver driver, By by, int scrollingIteration) {
//        int i = 0;
//        while ((!isDisplayed(driver, by)) && i < scrollingIteration) {
//            horizontalScrollRight(driver);
//        }
//        return isDisplayed(driver, by);
//
//    }

    public static FluentWait<WebDriver> _getFluentWait(int i) {
        return Wait().withTimeout(Duration.ofSeconds(i));
    }

    // ****************** Alerts ****************
    public static String _alertGetText(){
        Alert alert = _getFluentWait(10).until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Logger.info("'" + alertText + "' txt is returned from alert");
        return alertText;
    }

    public static void _alertAccept(){
        Alert alert = _getFluentWait(10).until(ExpectedConditions.alertIsPresent());
        alert.accept();
        Logger.info("Accepted the alert");
    }

    public static void _alertDismiss(){
        Alert alert = _getFluentWait(10).until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
        Logger.info("Dismissed the alert");
    }

    public static void _alertEnterText(String text){
        Alert alert = _getFluentWait(10).until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);
        Logger.info("'" + text + "' txt is entered to alert");
    }

    public static void _contextToWebView(){
        _hardWait(2000);
        //SupportsContextSwitching supportsContext = (SupportsContextSwitching) AppiumDriverRunner.getMobileDriver();
        Set<String> allContext = SelenideAppium.getContextHandles();
        System.out.println("Before Context: "+ SelenideAppium.getCurrentContext());
        System.out.println("ALL CONTEXT:"+allContext);
        for (String context : allContext) {
            if (context.contains("WEBVIEW")) {
                SelenideAppium.switchTo().context(context);
                break;
            }
        }
        Logger.info("Switched to Web View context: " + SelenideAppium.getCurrentContext());
        System.out.println("After Context: "+ SelenideAppium.getCurrentContext());
    }

    public static void _contextToNativeApp(){
        _hardWait(2000);
        Set<String> allContext = SelenideAppium.getContextHandles();
        System.out.println("Before Context: "+ SelenideAppium.getCurrentContext());
        System.out.println("ALL CONTEXT:"+allContext);
        for (String context : allContext) {
            if (context.contains("NATIVE_APP")) {
                SelenideAppium.switchTo().context(context);
                break;
            }
        }
        Logger.info("Switched to Native APP context: " + SelenideAppium.getCurrentContext());
        System.out.println("After Context: "+ SelenideAppium.getCurrentContext());
    }

    public static String _contextGetCurrent() {
        _hardWait(2000);
        String context = SelenideAppium.getCurrentContext();
        Logger.info("Current Context returned: " + context);
        return context;
    }

    public static void _contextSetContext(String context){
        SelenideAppium.switchTo().context(context);
        Logger.info("Set Context to: " + SelenideAppium.getCurrentContext());
    }
}
