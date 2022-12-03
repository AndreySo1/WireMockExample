package org.example;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import io.restassured.http.ContentType;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;

public class myTest {

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
    public void firstTest()
    {
        Gson gson = new Gson();
        TickerData dataBody = new TickerData("BY-BLR","BY2-BY2","0.080957","0.081181","0.0404","0.003154","0.081841","0.076903","124039.6902","9798.9455020323","0.081191","0.08017117","0.001","0.001","1","1");
        String myBodyJson = gson.toJson(dataBody);
        System.out.println("myBodyJSon: " + myBodyJson);

        stubFor(get("/api/v1/market/allTickers").willReturn(okJson(myBodyJson)));

        TickerData resp = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/market/allTickers")
                .then()
                .extract().as(TickerData.class);

        System.out.println("sumbolName: " + resp.getSymbol());
        Assert.assertEquals(resp.getSymbol(), "BY-BLR");

    }

    @AfterTest
    public void tearDownTest(){
        wireMockServer.stop();
    }
}
