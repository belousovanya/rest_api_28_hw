package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ReqresInTests extends TestBase {
    @Test
    public void getUsersListTest() {
        given()
                .when()
                .get("users?page=2")
                .then()
                .statusCode(SC_OK)
                .body("page", is(2))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2))
                .body("data", hasSize(6))

                .body("data[0].id", is(7))
                .body("data[0].email", is("michael.lawson@reqres.in"))
                .body("data[0].first_name", is("Michael"))
                .body("data[0].last_name", is("Lawson"))
                .body("data[0].avatar", is("https://reqres.in/img/faces/7-image.jpg"))

                .body("data[5].id", is(12))
                .body("data[5].email", is("rachel.howell@reqres.in"))
                .body("data[5].first_name", is("Rachel"))
                .body("data[5].last_name", is("Howell"))
                .body("data[5].avatar", is("https://reqres.in/img/faces/12-image.jpg"))

                .body("support.url", is("https://reqres.in/#support-heading"))
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    public void createUserTest() {

        String userData = "{\"name\": \"Anna\", \"job\": \"AQA\"}";

        given()
                .body(userData)
                .contentType(JSON)
                .when()
                .post("users")
                .then()
                .statusCode(SC_CREATED)
                .body("name", is("Anna"))
                .body("job", is("AQA"));
    }

    @Test
    public void updateUserTest() {
        String updatedUserData = "{\"name\": \"Hanna\", \"job\": \"QA\"}";
        given()
                .body(updatedUserData)
                .contentType(JSON)
                .when()
                .put("users/2")
                .then()
                .statusCode(SC_OK)
                .body("name", is("Hanna"))
                .body("job", is("QA"));
    }

    @Test
    public void unsuccessfulLoginTest() {
        String email = "{\"email\": \"qa@guru\"}";
        given()
                .body(email)
                .contentType(JSON)
                .when()
                .post("login")
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("error", is("Missing password"));
    }

    @Test
    public void getUsersListWithDelayTest() {
        given()
                .queryParam("delay", 3)
                .when()
                .get("users")
                .then()
                .statusCode(SC_OK)
                .body("page", is(1))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2))
                .body("data", hasSize(6))

                .body("data[0].id", is(1))
                .body("data[0].email", is("george.bluth@reqres.in"))
                .body("data[0].first_name", is("George"))
                .body("data[0].last_name", is("Bluth"))
                .body("data[0].avatar", is("https://reqres.in/img/faces/1-image.jpg"))

                .body("data[5].id", is(6))
                .body("data[5].email", is("tracey.ramos@reqres.in"))
                .body("data[5].first_name", is("Tracey"))
                .body("data[5].last_name", is("Ramos"))
                .body("data[5].avatar", is("https://reqres.in/img/faces/6-image.jpg"))

                .body("support.url", is("https://reqres.in/#support-heading"))
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }
}