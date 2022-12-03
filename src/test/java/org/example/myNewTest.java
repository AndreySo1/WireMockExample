package org.example;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.google.gson.Gson;
import io.restassured.http.ContentType;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;

public class myNewTest {

    private WireMockServer wireMockServer;

    @BeforeTest
    public void setUpTest(){
        wireMockServer = new WireMockServer(options().bindAddress("localhost")); //No-args constructor will start on port 8080, no HTTPS
//        wireMockServer = new WireMockServer(options().bindAddress("localhost").port(8008)); //No-args constructor will start on port 8080, no HTTPS
        wireMockServer.start();
        System.out.println("host: " + wireMockServer.getOptions().bindAddress());
        System.out.println("port: " + wireMockServer.getOptions().portNumber());
    }

    @Test
    public void oneTest()
    {
        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder().withStatus(337);
        stubFor(get("/users/1").willReturn(mockResponse));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users/1")
                .then()
                .statusCode(337);

    }

    @Test
    public void twoTest()
    {
        Gson gson = new Gson();
        UserData dataBody = new UserData(888, "888@gmail.com","NAme888", "LastName888","avatar888");
        String myBodyJson = gson.toJson(dataBody);
        System.out.println("mock response: " + myBodyJson);

        stubFor(get("/users/2").willReturn(okJson(myBodyJson)));

                UserData resp = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/users/2")
                    .then()
                    .extract().as(UserData.class);

        Assert.assertEquals(resp.getFirst_name(), dataBody.getFirst_name());
    }

    @AfterTest
    public void tearDownTest(){
        wireMockServer.stop();
    }
}

