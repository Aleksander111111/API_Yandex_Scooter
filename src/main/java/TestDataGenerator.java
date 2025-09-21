import Models.Courier;
import Models.Order;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestDataGenerator {
    public static Courier createRandomCourier() {
        String login = "courier_" + UUID.randomUUID().toString().substring(0, 8);
        String password = "password_" + UUID.randomUUID().toString().substring(0, 8);
        String firstName = "firstName_" + UUID.randomUUID().toString().substring(0, 8);
        return new Courier(login, password, firstName);
    }

    public static Order createOrderWithColor(List<String> colors) {
        return new Order(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2024-06-06",
                "Saske, come back to Konoha",
                colors
        );
    }

    public static Order createDefaultOrder() {
        return createOrderWithColor(Arrays.asList("BLACK"));
    }
}
