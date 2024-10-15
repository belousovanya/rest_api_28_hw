package tests;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;

public class TestBase {
    @BeforeAll
    public static void setupTests() {
        baseURI = "https://reqres.in/";
        basePath = "/api";
    }
}