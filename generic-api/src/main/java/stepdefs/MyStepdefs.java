package stepdefs;

import com.fasterxml.jackson.databind.ObjectMapper;
import constants.ApiConstants;
import dto.BookingDTO;
import enums.RequestType;
import generic.BaseRequest;
import generic.RestClient;
import generic.RunnerContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import logger.Logger;
import mapper.CreateBookingMapper;
import mapper.LoginMapper;
import model.LoginModel;
import model.requestModels.BookingRequestBody;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

public class MyStepdefs {

    @When("user hits login api using {string} and {string}")
    public void user_hits_login_api_using_and(String string, String string2, DataTable dataTable) {
        LoginModel loginModel=LoginMapper.map(dataTable);
        BaseRequest req=new BaseRequest(RequestType.GET,"url");
        Response res=RestClient.callApi("url",RequestType.GET,RestClient.buildRequest(req,"baseURI"));
        //AssertionUtil.assertTrue(res.getStatusCode()==200,"status code is as per expectation","status code is wrong");
    }


    @When("User hits the create booking api")
    public void userCreatesBookingWithTheBelowData() {
       RequestSpecification spec=RestClient.buildRequest(RunnerContext.baseRequest.get(), ApiConstants.BASE_URI);
       Response response=RestClient.callApi(RunnerContext.baseRequest.get().getUrl(),RequestType.POST,spec);

       Logger.info_asJSON(response.asPrettyString());
       //ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
       RunnerContext.response.set(response);
       System.out.println(response.getBody().asString());
    }

    @Given("User creates booking creation request with the below data")
    public void createBookingApiForUser(DataTable dataTable) {
        List<Map<String,String>> list = dataTable.asMaps();
        ObjectMapper mapper = new ObjectMapper();
        BookingDTO bookingDTO=null;
        for(Map<String,String> map:list){
            bookingDTO = mapper.convertValue(map, BookingDTO.class);
        }
        System.out.println(bookingDTO);
        BaseRequest baseRequest = new BaseRequest(RequestType.POST, ApiConstants.CREATE_BOOKING);
        BookingRequestBody body=CreateBookingMapper.map(bookingDTO);
        baseRequest.setBody(body);
        RunnerContext.baseRequest.set(baseRequest);
    }

    @Then("Verifies the booking got created")
    public void verifiesTheBookingGotCreated() {
        Assertions.assertThat(RunnerContext.response.get().getStatusCode())
                        .as("Status code is 200").isEqualTo(200);
        //AssertionUtil.assertTrue(RunnerContext.response.get().getStatusCode()==200,"Status code is as per the expectation","Status code is not as per the expectation");
    }

    @Given("User hits get booking api for created booking")
    public void userHitsGetBookingApiForId() {
        BaseRequest baseRequest = new BaseRequest(RequestType.POST, ApiConstants.GET_BOOKINGS+"/"+RunnerContext.response.get().getBody().jsonPath().get("bookingid"));
        RequestSpecification requestSpecification=RestClient.buildRequest(baseRequest,ApiConstants.BASE_URI);
        RunnerContext.response.set(RestClient.callApi(ApiConstants.GET_BOOKINGS+"/"+RunnerContext.response.get().getBody().jsonPath().get("bookingid"),RequestType.GET,requestSpecification));
        Logger.info_asJSON(RunnerContext.response.get().asPrettyString());
        //ExtentManager.getTest().log(Status.INFO,MarkupHelper.createCodeBlock(RunnerContext.response.get().asString(), CodeLanguage.JSON));
    }

    @Then("User verifies the first name in the response as {string}")
    public void userVerifiesTheFirstNameInTheResponseAs(String name) {
        Assertions.assertThat(RunnerContext.response.get().getBody().jsonPath().get("firstname").toString())
                .as("Validate firstname from response").isEqualTo(name);
        //AssertionUtil.assertEqual(RunnerContext.response.get().getBody().jsonPath().get("firstname"),name,"Getting expected first name","Didn't received expected first name");
    }
}
