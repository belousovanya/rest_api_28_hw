package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static org.apache.http.HttpStatus.*;

public class ResponseSpecs {

    public static ResponseSpecification successResponseSpec200 = new ResponseSpecBuilder()
            .expectStatusCode(SC_OK)
            .log(ALL)
            .build();

    public static ResponseSpecification createdResponseSpec201 = new ResponseSpecBuilder()
            .expectStatusCode(SC_CREATED)
            .log(ALL)
            .build();

    public static ResponseSpecification badRequestResponseSpec400 = new ResponseSpecBuilder()
            .expectStatusCode(SC_BAD_REQUEST)
            .log(ALL)
            .build();
}