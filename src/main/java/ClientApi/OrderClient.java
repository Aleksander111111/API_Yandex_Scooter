package ClientApi;

import Models.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String CREATE_ORDER_ENDPOINT = "/api/v1/orders";
    private static final String GET_ORDERS_ENDPOINT = "/api/v1/orders";

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER_ENDPOINT);
    }

    @Step("Получение списка заказов")
    public Response getOrders(Integer courierId, String[] nearestStations, Integer limit, Integer page) {
        return given()
                .baseUri(BASE_URL)
                .queryParam("courierId", courierId)
                .queryParam("nearestStation", nearestStations != null ? String.join(",", nearestStations) : null)
                .queryParam("limit", limit)
                .queryParam("page", page)
                .when()
                .get(GET_ORDERS_ENDPOINT);
    }
}
