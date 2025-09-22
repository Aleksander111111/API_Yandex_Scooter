package ClientApi;

import Models.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String CREATE_COURIER_ENDPOINT = "/api/v1/courier";
    private static final String LOGIN_COURIER_ENDPOINT = "/api/v1/courier/login";

    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(CREATE_COURIER_ENDPOINT);
    }

    @Step("Логин курьера")
    public Response loginCourier(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(LOGIN_COURIER_ENDPOINT);
    }

    @Step("Удаление курьера")
    public void deleteCourier(int courierId) {
        given()
                .baseUri(BASE_URL)
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then()
                .statusCode(200);
    }
}
