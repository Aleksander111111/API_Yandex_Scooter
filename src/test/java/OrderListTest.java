import ClientApi.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class OrderListTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов без параметров")
    public void testGetOrdersWithoutParameters() {
        Response response = orderClient.getOrders(null, null, null, null);

        response.then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("pageInfo", notNullValue())
                .body("availableStations", notNullValue());
    }

    @Test
    @DisplayName("Получение списка заказов с лимитом")
    public void testGetOrdersWithLimit() {
        Response response = orderClient.getOrders(null, null, 10, null);

        response.then()
                .statusCode(200)
                .body("orders.size()", lessThanOrEqualTo(10))
                .body("pageInfo.limit", equalTo(10));
    }

    @Test
    @DisplayName("Получение списка заказов с несуществующим courierId")
    public void testGetOrdersWithNonExistentCourierId() {
        Response response = orderClient.getOrders(999999, null, null, null);

        response.then()
                .statusCode(404)
                .body("message", containsString("Курьер с идентификатором 999999 не найден"));
    }
}
