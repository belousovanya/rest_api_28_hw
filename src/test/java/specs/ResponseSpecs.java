package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static org.apache.http.HttpStatus.*;

public class ResponseSpecs {

    public static ResponseSpecification successResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(SC_OK)
            .build();

    public static ResponseSpecification createdResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(SC_CREATED)
            .build();

    public static ResponseSpecification badRequestResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(SC_BAD_REQUEST)
            .build();
}