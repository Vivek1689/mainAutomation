package stepdefs;

import io.cucumber.java.en.*;

public class SauceLabSteps extends BaseSteps{


    @Given("User launches sauce lab app")
    public void user_launches_sauce_lab_app() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Given("User navigates to login screen")
    public void user_navigates_to_login_screen() {
        sauceLabScreen.navigateToLogin();
    }

    @Given("User enters credentials {string} and {string}")
    public void user_enters_credentials_and(String username, String password) {
        sauceLabScreen.login(username, password);

    }

    @Given("User enters credentials")
    public void user_enters_credentials() {
        sauceLabScreen.login();
    }

    @Then("User logs in successfully")
    public void user_logs_in_successfully() {
        sauceLabScreen.validateLogin();
    }

    @When("User clicks on cart badge")
    public void userClicksOnCartBadge() {
        sauceLabScreen.navigateToCartBadge();

    }

    @And("User lands into cart screen")
    public void userLandsIntoCartScreen() {

    }

    @When("User clicks on go shopping button")
    public void userClicksOnGoShoppingButton() {
        sauceLabScreen.navigateToCatalystScreenFromGoshopBtn();

    }

    @Then("User lands into products Lists screen")
    public void userLandsIntoProductsListsScreen() {

    }

    @And("User selects the product")
    public void userSelectsTheProduct() {
        sauceLabScreen.navigateToProductSelection();
    }

    @And("User add the selected product to the cart")
    public void userAddTheSelectedProductToTheCart() {
        sauceLabScreen.navigateToAddToCartButton();
    }

    @And("User clicks on the Proceed to checkout button")
    public void userClicksOnTheProceedToCheckoutButton() {
        sauceLabScreen.navigateToProceedToCheckoutButton();

    }

    @Then("User Proceeds to Login screen")
    public void userProceedsToLoginScreen() {
        //sauceLabScreen.login();

    }

    @And("User is entered into Payment screen")
    public void userIsEnteredIntoPaymentScreen() {

    }

    @And("User logs out successfully")
    public void userLogsOutSuccessfully() {
        sauceLabScreen.navigateToLogout();
    }

    @And("User logs out successfully and validate the text")
    public void userLogsOutSuccessfullyAndValidateTheText() {
        sauceLabScreen.navigateToLogoutFailed();
    }
}
