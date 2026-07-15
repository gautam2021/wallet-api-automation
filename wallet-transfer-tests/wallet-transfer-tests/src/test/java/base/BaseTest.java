package base;

import org.junit.jupiter.api.BeforeEach;

import io.restassured.RestAssured;

public class BaseTest {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }
}