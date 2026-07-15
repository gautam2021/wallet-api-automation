package utils;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.TransferRequest;

public class WalletClient {

    public Response transferMoney(TransferRequest request) {

        return given()
                .contentType(ContentType.JSON)
                .body(request)
        .when()
                .post("/transfers")
        .then()
                .extract()
                .response();
    }

    public Response getWallets() {

        return given()
        .when()
                .get("/wallet")
        .then()
                .extract()
                .response();
    }
}