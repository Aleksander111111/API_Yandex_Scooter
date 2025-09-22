import ClientApi.CourierClient;
import Models.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class CourierCreationTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = TestDataGenerator.createRandomCourier();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void testSuccessfulCourierCreation() {
        Response response = courierClient.createCourier(courier);

        response.then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Response loginResponse = courierClient.loginCourier(
                new Courier(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.then().extract().path("id");
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    public void testCreateCourierWithExistingLogin() {
        courierClient.createCourier(courier);
        Response loginResponse = courierClient.loginCourier(
                new Courier(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.then().extract().path("id");
        Courier duplicateCourier = new Courier(
                courier.getLogin(),
                "different_password",
                "different_name");

        Response response = courierClient.createCourier(duplicateCourier);

        response.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void testCreateCourierWithoutLogin() {
        Courier courierWithoutLogin = new Courier(null, "password", "name");

        Response response = courierClient.createCourier(courierWithoutLogin);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void testCreateCourierWithoutPassword() {
        Courier courierWithoutPassword = new Courier("login", null, "name");

        Response response = courierClient.createCourier(courierWithoutPassword);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}