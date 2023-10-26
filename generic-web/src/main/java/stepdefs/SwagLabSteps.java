package stepdefs;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;

import java.io.IOException;
import java.util.Map;

import static config.factory.ConfigFactory.getConfig;

public class SwagLabSteps extends BaseSteps{


    @Given("user navigates to Swag Lab page")
    public void user_navigates_to_swag_lab_page() {
        swagLabPage.navigate();
    }
    @Given("user login using {string} and {string}")
    public void user_login_using_and(String string, String string2) {
        swagLabPage.login(string, string2);
    }
    @Then("user should login successfully")
    public void user_should_login_successfully() {
        Assertions.assertThat(swagLabPage.cartIsVisible()).as("User is in home page").isTrue();
    }

    @Given("I am logged in")
    public void i_am_logged_in() {
        swagLabPage.navigate();
        swagLabPage.take_screenshot_visualTest("Login Page");
        swagLabPage.login(getConfig().ui_userName(), getConfig().ui_password());
        swagLabPage.take_screenshot_visualTest("Home Page");
    }

    @Given("I am on a product detail page")
    public void i_am_on_a_product_detail_page() {
        swagLabPage.take_screenshot_visualTest("Product Details Page");
    }
    @When("I select a product {string}")
    public void i_select_a_product(String string) {
        swagLabPage.selectProduct(string);
    }
    @When("I click the add to cart button")
    public void i_click_the_add_to_cart_button() {
        swagLabPage.clickAddToCart();
    }

    @Then("the product {string} is added to cart")
    public void the_product_is_added_to_cart(String string) {
        swagLabPage.validateProductCountInCart("1");
        swagLabPage.clickCartLink();
        swagLabPage.validateProductInCart(string);
    }

    @When("I select multiple products")
    public void i_select_multiple_products(DataTable dataTable) {
        for(Map<String, String> product : dataTable.asMaps()) {
            swagLabPage.selectProduct(product.get("ProductName"));
            swagLabPage.clickAddToCart();
            swagLabPage.clickBackToProduct();
        }
    }

    @Then("validate multiple products is added to cart")
    public void validate_multiple_products_is_added_to_cart() throws IOException {
        swagLabPage.clickCartLink();
        swagLabPage.validateProductsInCart();
    }

    @Given("user opens a page {string}")
    public void userOpensAPage(String arg0) {
        swagLabPage.open(arg0);
    }

    @And("user should logout")
    public void userShouldLogout() {
        swagLabPage.logOut();
    }

    @And("I click any add to cart button")
    public void iClickAnyAddToCartButton() {
        swagLabPage.clickAddToCart();
    }

    @When("I reset the application")
    public void iResetTheApplication() {
        swagLabPage.resetApplication();
    }

    @Then("I validate the application is reset")
    public void iValidateTheApplicationIsReset() {
        swagLabPage.validateApplicationReset();
    }
}
