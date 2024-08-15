package tests;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.filters;

public class TestBase {
    @BeforeAll
    public static void setupTests() {
        filters(new RequestLoggingFilter(LogDetail.BODY), new ResponseLoggingFilter(LogDetail.BODY),
                new ResponseLoggingFilter(LogDetail.STATUS));
        baseURI = "https://reqres.in/api/";
    }
}