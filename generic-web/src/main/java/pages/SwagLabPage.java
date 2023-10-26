package pages;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.locators.RelativeLocator;

import static generic.PageFunctions.*;
import static org.assertj.core.api.Assertions.*;

public class SwagLabPage extends BasePage{


    By userName_edit = By.id("user-name");
    By password_edit = By.id("password");
    By login_btn = By.id("login-button");

    By app_logo = By.xpath("//div[text()='Swag Labs']");
    By burger_menu = By.id("react-burger-menu-btn");

    By returnProductLink(String productName){
        return By.xpath(String.format("//a/div[contains(.,'%s')]", productName));
    }
    By addToCart_btn = By.xpath("//button[text()='Add to cart']");
    By backToProducts = By.xpath("//button[text()='Back to products']");
    By cart_link = By.className("shopping_cart_link");

    By cart_icon = By.xpath("//span[@class='shopping_cart_badge']");
            //RelativeLocator.with(By.tagName("span")).above(By.className("product_sort_container")).toRightOf(By.className("app_logo"));


    public void navigate(){
        load_user_ui("");
    }

    public void login(String sUsername, String sPassword){
        _sendKeys(userName_edit, sUsername, "UserName");
        _sendKeys_password(password_edit, sPassword, "Password");
        _click(login_btn, "Login");
        _waitForDisplayed(app_logo, "App logo label");
    }

    public void selectProduct(String productName){
        _click(returnProductLink(productName), "Product Link");
    }

    public void clickAddToCart(){
        _click(addToCart_btn, "Add to Cart");
        _hardWait(1);
    }

    public void clickCartLink(){
        _click(cart_link, "Cart Link");
        _hardWait(1);
    }

    public void clickBackToProduct(){
        _click(backToProducts, "Back to Product");
    }

    public void validateProductInCart(String product){
        assertThat(_waitForPresent(returnProductLink(product), "Product Link"))
                .as("Validate presence of Product: " + product).isTrue();
    }

    public void validateProductsInCart(){
        assertThat( _getElements(returnProductLink(""), "").size())
                .as("Validate presence of Products").isGreaterThan(1);
    }

    public boolean cartIsVisible(){
        return _isVisible(cart_link, "Cart");
    }

    public void logOut() {
        _click(burger_menu, "Burger menu");
        _click(By.id("logout_sidebar_link"), "Logout Link");
    }

    public void validateProductCountInCart(String number) {
        assertThat(_getText(cart_icon, "Cart Icon")).as("Validate product count")
                .isEqualTo(number);
    }

    public void resetApplication() {
        _click(burger_menu, "Burger menu");
        _click(By.id("reset_sidebar_link"), "Reset App State");
        _refreshPage();
    }

    public void validateApplicationReset() {
        assertThat(_waitForInvisibility(cart_icon)).as("Validate app reset")
                .isTrue();
    }
}
