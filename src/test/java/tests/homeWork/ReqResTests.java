package tests.homeWork;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ReqResTests {

    @Test
    @Description("Проверка доступности списка пользователей")
    void checkAvailabilityTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }

    @Test
    @Description("Проверка наличия пользователя с email 'emma.wong@reqres.in' на 1 странице")
    void checkUsersTest() {
        given()
                .get("https://reqres.in/api/users?page=1")
                .then()
                .log().body()
                .body("data[2].email", equalTo("emma.wong@reqres.in"));
    }

    @Test
    @Description("Проверка создания нового пользователя")
    void checkCreateNewUserTest() {

        String user = "{ \"name\": \"Klara\", " +
                "\"job\": \"janitor\"," +
                "\"id\": \"712\"," +
                "\"createdAt\": \"2022-09-01T19:51:04.094Z\"}";

        given()
                .contentType(JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Klara"));
    }

    @Test
    @Description("Проверка отсутствия пользователя")
    void checkUserNotFoundTest() {
        given()
                .get("https://reqres.in/api/users/230000000")
                .then()
                .log().body()
                .log().all()
                .statusCode(404);
    }

    @Test
    @Description("Проверка удаления пользователя")
    void checkDeletionUserTest() {
        given()
                .delete("https://reqres.in/api/users/712")
                .then()
                .log().body()
                .log().all()
                .statusCode(204);
    }

}
