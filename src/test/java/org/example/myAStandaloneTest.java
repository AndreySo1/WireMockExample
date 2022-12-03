package org.example;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static io.restassured.RestAssured.given;

public class myAStandaloneTest {

    /**
     * 1 - Comment @Bfore, @ After
     * 2 - start java -jar wiremock-jre8-standalone-2.35.0.jar
     * 3 - create mapping:
     * {
     *     "request": {
     *         "method": "GET",
     *         "url": "/users/1"
     *     },
     *     "response": {
     *         "status": 222,
     *         "body": "Hello World, User 1"
     *     }
     * }
     * 4 - test success
     * */

    @Test
    public void threeTest(){

//        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder().withStatus(222);
//        stubFor(get("/users/1").willReturn(mockResponse));

        Response response =given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users/1")
                .then()
                .log().all()
                .extract().response();

        System.out.println(response.body().asString());
        Assert.assertEquals( response.body().asString() , "Hello World, User 1");
    }
}
