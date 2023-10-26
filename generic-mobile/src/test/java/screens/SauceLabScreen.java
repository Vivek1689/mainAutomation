package screens;


import com.codeborne.selenide.appium.AppiumSelectors;
import com.codeborne.selenide.appium.selector.CombinedBy;
import generic.AppiumFunctions;
import io.appium.java_client.AppiumBy;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static generic.AppiumFunctions.*;

public class SauceLabScreen {

    By menu = CombinedBy
            .android(AppiumSelectors.byContentDescription("open menu"))
            .ios(AppiumBy.accessibilityId("tab bar option menu"));

    // Menu items
    By login_menuItem = AppiumBy.accessibilityId("menu item log in");

    // Login
    By login_label = CombinedBy
            .android(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"container header\"]/android.widget.TextView"))
            .ios(AppiumBy.xpath("//XCUIElementTypeStaticText[@name=\"Login\"]"));
    By email_txt = AppiumBy.accessibilityId("Username input field");
    By password_txt = AppiumBy.accessibilityId("Password input field");
    By login_btn = AppiumBy.accessibilityId("Login button");

    // Cart screen
    By goShopping_btn = AppiumBy.accessibilityId("Go Shopping button");

    By goshoppingcart = AppiumBy.accessibilityId("Go Shopping button");

    By itemslists = CombinedBy
            .android(AppiumSelectors.withTagAndText("android.widget.TextView","Sauce Labs Backpack"))//.android(AppiumBy.xpath("(//android.widget.TextView[@content-desc=\"store item text\"])[1]"))
            .ios(AppiumBy.accessibilityId("Sauce Labs Backpack"));

    By AddToCart = AppiumBy.accessibilityId("Add To Cart button");

    By cartBadge = CombinedBy
            .android(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"cart badge\"]/android.widget.ImageView"))
            .ios(AppiumBy.accessibilityId("tab bar option cart"));

    By ProceedCheckoutBtn = AppiumBy.accessibilityId("Proceed To Checkout button");


    By logout_menuItem =
            AppiumBy.accessibilityId("menu item log out");
    By logout_btn = CombinedBy
            .android(AppiumSelectors.byText("LOG OUT")) //.android(AppiumBy.id("android:id/button1"))
            .ios(AppiumSelectors.byTagAndName("XCUIElementTypeButton","Log Out")); //.ios(AppiumBy.xpath("//XCUIElementTypeButton[@name=\"Log Out\"]"));

    By LogoutTitle = CombinedBy
            .android(AppiumBy.id("android:id/alertTitle"));
    By okBtn = CombinedBy
            .android(AppiumSelectors.withTagAndText("android.widget.Button", "OK"))//.android(AppiumBy.id("android:id/button1"))
            .ios(AppiumSelectors.byTagAndName("XCUIElementTypeButton","OK"));

    By ValidateLogoutText = CombinedBy
            .android(AppiumSelectors.withTagAndText("android.widget.TextView", "logged out."))//.android(AppiumBy.id("android:id/alertTitle"))
            //.ios(AppiumBy.xpath("//XCUIElementTypeStaticText[@name=\"You are successfully logged out.\"]"));
            .ios(AppiumSelectors.withTagAndName("XCUIElementTypeStaticText", "logged out."));

    // Footer
    By footer_txt = CombinedBy.android(AppiumSelectors.byText("© 2023 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy."));


    public void navigateToLogin() {
        _tap(menu, "Menu");
        _click(login_menuItem, "Login Menu");
        System.out.println(_getText(login_label, "login header"));
    }

    public void login() {
        _sendKeys(email_txt,"bob@example.com", "email field");
        _sendKeysPassword(password_txt,"10203040", "password field");
        _tap(login_btn, "login btn");
    }

    public void login(String username, String password) {
        _sendKeys(email_txt,username, "email field");
        _sendKeysPassword(password_txt,password, "password field");
        _tap(login_btn, "login btn");
    }

    public void validateLogin(){
        _tap(menu, "Menu");
        _click(login_menuItem, "Login Menu");
        _getSelenideAppiumElement(goShopping_btn).shouldBe(visible);
        _hardWait(3);
    }

    public void navigateToCatalystScreenFromGoshopBtn() {
        _tap(goshoppingcart, "Shopping Button");
    }

    public void navigateToProductSelection() {
        _tap(itemslists, "Item Selection");
    }

    public void navigateToCartBadge() {
        _tap(cartBadge,"Cart Badge");
    }

    public void navigateToAddToCartButton() {
        _tap(AddToCart,"Add To Cart Button");
    }

    public void navigateToProceedToCheckoutButton() {
        _tap(ProceedCheckoutBtn, "Proceed To Checkout Button");
    }

    public void navigateToLogout() {
        _click(menu, "Menu");
        _tap(logout_menuItem, "Logout From Menu Option");
        _tap(logout_btn, "Yes Option");
        _getSelenideAppiumElement(ValidateLogoutText).shouldBe(visible);
        _tap(okBtn, "Ok Button");
        //_hardWait(2);
    }

    public void navigateToLogoutFailed() {
        _tap(menu, "Menu");
        _tap(logout_menuItem, "Logout From Menu Option");
        _tap(logout_btn, "Yes Option");
        Assertions.assertThat(_getText(ValidateLogoutText, "Logout success confirm dialog"))
                .as("Logout success confirm dialog").isEqualToIgnoringCase("You are successfully logged-out.")   ;
        _tap(okBtn, "Ok Button");
        // _hardWait(1);
    }

    public void AddItemToCart() {
        _click(itemslists, "Item Selection");
        _tap(AddToCart,"Cart Button");
        _tap(cartBadge,"Cart Badge");
        _tap(ProceedCheckoutBtn,"Checkout Button");
        _tap(menu, "Menu");
        _tap(logout_menuItem, "Logout From Menu Option");
        _tap(logout_btn, "Yes Option");
        _getSelenideAppiumElement(ValidateLogoutText).shouldBe(visible);
        _tap(okBtn, "Ok Button");

    }



}
