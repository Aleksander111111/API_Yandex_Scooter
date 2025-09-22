import ClientApi.CourierClient;
import Models.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = TestDataGenerator.createRandomCourier();
        courierClient.createCourier(courier);
        Response loginResponse = courierClient.loginCourier(
                new Courier(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.then().extract().path("id");
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешный логин курьера")
    public void testSuccessfulCourierLogin() {
        Response response = courierClient.loginCourier(
                new Courier(courier.getLogin(), courier.getPassword()));

        response.then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void testLoginWithWrongPassword() {
        Response response = courierClient.loginCourier(
                new Courier(courier.getLogin(), "wrong_password"));

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин с несуществующим логином")
    public void testLoginWithNonExistentLogin() {
        Response response = courierClient.loginCourier(
                new Courier("non_existent_login", "password"));

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин без логина")
    public void testLoginWithoutLogin() {
        Response response = courierClient.loginCourier(
                new Courier(null, courier.getPassword()));

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин без пароля")
    public void testLoginWithoutPassword() {
        Response response = courierClient.loginCourier(
                new Courier(courier.getLogin(), null));

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}