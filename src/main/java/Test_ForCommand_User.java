import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import static org.assertj.core.api.Assertions.*;
import org.testng.annotations.*;

@Listeners(LogListener.class)
public class Test_ForCommand_User {

    @Test
    @Description("Check Response code for correct UserId")
    @Severity(SeverityLevel.BLOCKER)
    public void testCheckCorrectRequestStatusWithCorrectData() {
        RestAssured.baseURI = Strings.RESOURCE_API_URL + "/user";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/2");
        int statusCode = response.getStatusCode();
        assertThat(statusCode).isEqualTo(200).as("Returned code: 200");
    }

    @Test
    @Description("Get user by nonexistent UserId")
    @Severity(SeverityLevel.BLOCKER)
    public void testCheckCorrectRequestStatusWithIncorrectData() {
        RestAssured.baseURI = Strings.RESOURCE_API_URL + "/user";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/0");
        int statusCode = response.getStatusCode();
        assertThat(statusCode).isEqualTo(404).as("Returned code: 404");
    }

    @Test
    @Description("Check Response code for correct UserId")
    @Severity(SeverityLevel.BLOCKER)
    public void testCheckCorrectRequestStatusLineWithCorrectData() {
        RestAssured.baseURI = Strings.RESOURCE_API_URL + "/user";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/2");
        String statusLine = response.getStatusLine();
        assertThat(statusLine).isEqualTo("HTTP/1.1 200 OK").as("Returned status line: \"HTTP/1.1 200 OK\"");
    }

    @Test
    @Description("Get user by correct UserId")
    @Severity(SeverityLevel.BLOCKER)
    public void testCheckCorrectRequestId() {
        RestAssured.baseURI = Strings.RESOURCE_API_URL + "/user";
        RequestSpecification httpRequest = RestAssured.given().filter(new AllureRestAssured());
        Response response = httpRequest.request(Method.GET, "/2");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Integer id = jsonPathEvaluator.get("data.id");
        assertThat(id).isEqualTo(2).as("Id = 2");
    }

    @Test
    @Description("Get user by correct UserId. Expected response contains: first_name")
    @Severity(SeverityLevel.BLOCKER)
    public void testBodyContainsFirstName() {
        RestAssured.baseURI = Strings.RESOURCE_API_URL + "/user";
        RequestSpecification httpRequest = RestAssured.given().filter(new AllureRestAssured());
        Response response = httpRequest.request(Method.GET, "/2");
        ResponseBody body =response.getBody();
        String responseAsString = body.asString();
        assertThat(responseAsString.toLowerCase().contains("first_name")).isEqualTo(true);
    }

    @Test
    @Description("Get user by correct UserId. Expected response contains: last_name")
    @Severity(SeverityLevel.BLOCKER)
    public void testBodyContainsLastName() {
        RestAssured.baseURI = Strings.RESOURCE_API_URL + "/user";
        RequestSpecification httpRequest = RestAssured.given().filter(new AllureRestAssured());
        Response response = httpRequest.request(Method.GET, "/2");
        ResponseBody body =response.getBody();
        String responseAsString = body.asString();
        assertThat(responseAsString.toLowerCase().contains("last_name")).isEqualTo(true);
    }
    @Test
    @Description("Get user by correct UserId. Expected response contains: avatar")
    @Severity(SeverityLevel.BLOCKER)
    public void testBodyContainsAvatar() {
        RestAssured.baseURI = Strings.RESOURCE_API_URL + "/user";
        RequestSpecification httpRequest = RestAssured.given().filter(new AllureRestAssured());
        Response response = httpRequest.request(Method.GET, "/2");
        ResponseBody body =response.getBody();
        String responseAsString = body.asString();
        assertThat(responseAsString.toLowerCase().contains("avatar")).isEqualTo(true);
    }

    @Test
    @Description("Get user by nonexistent UserId")
    @Severity(SeverityLevel.BLOCKER)
    public void testGetUserWithoutUserId() {
        RestAssured.baseURI = Strings.RESOURCE_API_URL + "/user";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/");
        int statusCode = response.getStatusCode();
        assertThat(statusCode).isEqualTo(404).as("Returned code: 404");
    }
}
