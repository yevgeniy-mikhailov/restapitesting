import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Listeners(LogListener.class)
public class Test_ForCommand_Login {

    @DataProvider(name="Incorrect login data. Incorrect parameters")
    public static Object[][] incorrectCredentials() {
        return new Object[][] {
                {"peter@klaven", "cityslickar"},
                {"peter@klavenr", "cityslickar"},
                {"peter@klavenr", "cityslicka"}
        };
    }
    @DataProvider(name="Incorrect login data. Missing parameters")
    public static Object[][] missingCredentials() {
        return new Object[][] {
                {"", "cityslicka"},
                {"peter@klavenr", ""},
                {"", ""}
        };
    }
    @Test
    @Description("Login with correct email and correct password")
    @Severity(SeverityLevel.BLOCKER)
    public void testLoginWithCorrectEmailAndCorrectPassword() {
        RestAssured.baseURI = Strings.RESOURCE_API_URL;
        RequestSpecification httpRequest = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "peter@klaven");
        requestParams.put("password", "cityslicka");
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParams.toJSONString());
        Response response = httpRequest.post("/login");
        int statusCode = response.getStatusCode();
        assertThat(statusCode).isEqualTo(200).as("Returned code: 200");
    }

    @Test
    @Description("Login with correct data and check token")
    @Severity(SeverityLevel.BLOCKER)
    public void testCheckTokenIsCorrect() {
        RestAssured.baseURI = Strings.RESOURCE_API_URL;
        RequestSpecification httpRequest = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("email", "peter@klaven");
        requestParams.put("password", "cityslicka");
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParams.toJSONString());
        Response response = httpRequest.post("/login");
        JsonPath jsonPathEvaluator = response.jsonPath();
        String token = jsonPathEvaluator.get("token");
        assertThat(token).isEqualTo("QpwL5tke4Pnpja7X");
    }

    @Test(dataProvider = "Incorrect login data. Missing parameters")
    @Description("Login with incorrect data")
    @Severity(SeverityLevel.BLOCKER)
    public void testLoginWithMissingData(String dEmail, String dPassword) {
        RestAssured.baseURI = Strings.RESOURCE_API_URL;
        RequestSpecification httpRequest = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("email", dEmail);
        requestParams.put("password", dPassword);
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParams.toJSONString());
        Response response = httpRequest.post("/login");
        JsonPath jsonPathEvaluator = response.jsonPath();
        String error = jsonPathEvaluator.get("error");
        assertThat(error.toLowerCase()).isEqualTo("Login or password is incorrect".toLowerCase());
    }

    @Test(dataProvider = "Incorrect login data. Incorrect parameters")
    @Description("Login with incorrect data")
    @Severity(SeverityLevel.BLOCKER)
    public void testLoginWithIncorrectData(String dEmail, String dPassword) {
        RestAssured.baseURI = Strings.RESOURCE_API_URL;
        RequestSpecification httpRequest = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("email", dEmail);
        requestParams.put("password", dPassword);
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParams.toJSONString());
        Response response = httpRequest.post("/login");
        JsonPath jsonPathEvaluator = response.jsonPath();
        String error = jsonPathEvaluator.get("error");
        assertThat(error.toLowerCase()).isEqualTo("missing [email or password]".toLowerCase());
    }
}
