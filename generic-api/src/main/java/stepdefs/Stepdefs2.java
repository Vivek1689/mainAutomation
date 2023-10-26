package stepdefs;


import config.factory.ConfigFactory;
import generic.API_Functions;
import generic.RunnerContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONObject;
import utils.random.RandomUtil;


import java.util.HashMap;
import java.util.Map;

import static generic.API_Functions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Stepdefs2 extends BaseSteps{

    Response response;

    @Given("User hits get healthCheck api")
    public void userHitsGetHealthCheckApi() {
        response = _get(baseURI+"/ping");
    }

    @Then("User verifies the web service is up")
    public void userVerifiesTheWebServiceIsUp() {
        assertThat(response.getBody().asPrettyString())
                .as("Body contains Created :\n" + response.getBody().asPrettyString())
                .isEqualToIgnoringCase("Created");
    }

    @Given("User hits post to create booking")
    public void userHitsPostToCreateBooking() {
        JSONObject jsonObject = new JSONObject();
        JSONObject innerJSONObject = new JSONObject();

        innerJSONObject.put("checkin", "2018-01-01");
        innerJSONObject.put("checkout", "2019-01-01");

        jsonObject.put("firstname", "Jim1");
        jsonObject.put("lastname", "Brown1");
        jsonObject.put("totalprice", 111);
        jsonObject.put("depositpaid", true);
        jsonObject.put("bookingdates", innerJSONObject);
        jsonObject.put("additionalneeds", "Breakfast");

        RunnerContext.anyData.set(jsonObject);
        RunnerContext.response.set( _post(baseURI+"/booking", jsonObject.toString()));
    }

    @Then("User verifies the new booking")
    public void userVerifiesTheNewBooking() {
        assertThat(RunnerContext.response.get().getBody().asString())
                .as("Body contains Created :\n" + response.getBody().asString())
                .containsIgnoringCase("bookingid");
        //System.out.println(RunnerContext.anyData.get());
    }

    @Given("User hits put to update booking id {string}")
    public void userHitsPutToUpdateBookingId(String id) {
        JSONObject jsonObject = new JSONObject();
        JSONObject innerJSONObject = new JSONObject();

        innerJSONObject.put("checkin", "2018-01-01");
        innerJSONObject.put("checkout", "2019-01-01");

        jsonObject.put("firstname", "Jim1");
        jsonObject.put("lastname", RandomUtil.getFakerName().lastName());
        jsonObject.put("totalprice", 111);
        jsonObject.put("depositpaid", RandomUtil.getFakerObject().random().nextBoolean());
        jsonObject.put("bookingdates", innerJSONObject);
        jsonObject.put("additionalneeds", RandomUtil.getFakerObject().food().dish());

        Map header = new HashMap();
        String token = "token="+getToken();
        header.put("Cookie", token);

        RunnerContext.anyData.set(jsonObject.toMap());
        RunnerContext.response.set( _put(baseURI+"/booking/"+id, header, jsonObject.toString()));
    }

    @Then("User verifies the updated booking")
    public void userVerifiesTheUpdatedBooking() {
        Map payload = (Map) RunnerContext.anyData.get();
        String response = RunnerContext.response.get().asString();
        assertThat(payload)
                .as("Updated data matches :\n" )
                .containsValue(_fetchDataFromJson_asString(response, "$.lastname"))
                .containsValue(Boolean.parseBoolean(_fetchDataFromJson_asString(response, "$.depositpaid")))
                .containsValue(_fetchDataFromJson_asString(response, "$.additionalneeds"));
    }

    private String getToken(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", ConfigFactory.getConfig().api_userName());
        jsonObject.put("password", ConfigFactory.getConfig().api_password());
        Response res = _post(baseURI + "/auth", jsonObject.toString());
        return _fetchDataFromJson_asString(res.getBody().asString(), "$.token");
        //return res.jsonPath().getString("$.token");
    }

}
