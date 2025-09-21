import ClientApi.OrderClient;
import Models.Order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private final OrderClient orderClient = new OrderClient();
    private final List<String> colors;
    private final String description;

    public OrderCreationTest(List<String> colors, String description) {
        this.colors = colors;
        this.description = description;
    }

    @Parameterized.Parameters(name = "Цвета: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {Arrays.asList("BLACK"), "Только BLACK"},
                {Arrays.asList("GREY"), "Только GREY"},
                {Arrays.asList("BLACK", "GREY"), "Оба цвета"},
                {null, "Без указания цвета"}
        });
    }

    @Test
    @DisplayName("Создание заказа с разными цветами")
    public void testOrderCreationWithDifferentColors() {
        Order order = TestDataGenerator.createOrderWithColor(colors);

        Response response = orderClient.createOrder(order);

        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}