package tests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.RequestSpec.requestSpec;
import static specs.ResponseSpecs.*;

public class ReqresInTests extends TestBase {

    @Test
    @DisplayName("Проверка получения списка пользователей на странице")
    public void getUsersListTest() {
        UserListResponseModel response =
                step("Отправка GET-запроса на получение списка пользователей на странице", () ->
                        given()
                                .spec(requestSpec)
                                .when()
                                .get("users?page=2")
                                .then()
                                .spec(successResponseSpec200)
                                .extract().as(UserListResponseModel.class)
                );

        step("Проверка основных параметров ответа", () -> {
            assertEquals(response.getPage(), 2);
            assertEquals(response.getPerPage(), 6);
            assertEquals(response.getTotal(), 12);
            assertEquals(response.getTotalPages(), 2);
        });

        UserDataModel firstUser = response.getData().get(0);
        step("Проверка данных первого пользователя", () -> {
            assertEquals(firstUser.getId(), 7);
            assertEquals(firstUser.getEmail(), "michael.lawson@reqres.in");
            assertEquals(firstUser.getFirstName(), "Michael");
            assertEquals(firstUser.getLastName(), "Lawson");
            assertEquals(firstUser.getAvatar(), "https://reqres.in/img/faces/7-image.jpg");
        });

        UserDataModel lastUser = response.getData().get(5);
        step("Проверка данных последнего пользователя", () -> {
            assertEquals(lastUser.getId(), 12);
            assertEquals(lastUser.getEmail(), "rachel.howell@reqres.in");
            assertEquals(lastUser.getFirstName(), "Rachel");
            assertEquals(lastUser.getLastName(), "Howell");
            assertEquals(lastUser.getAvatar(), "https://reqres.in/img/faces/12-image.jpg");
        });

        step("Проверка информации о поддержке", () -> {
            assertEquals(response.getSupport().getUrl(), "https://reqres.in/#support-heading");
            assertEquals(response.getSupport().getText(),
                    "To keep ReqRes free, contributions towards server costs are appreciated!");
        });
    }

    @Test
    @DisplayName("Проверка создания пользователя")
    public void createUserTest() {
        step("Создание пользователя с параметрами 'Name' и 'Job'", () -> {
            UserRequestModel userData = new UserRequestModel();
            userData.setName("Anna");
            userData.setJob("AQA");

            UserResponseModel userResponse =
                    step("Отправка POST-запроса на создание пользователя", () ->
                            given()
                                    .spec(requestSpec)
                                    .body(userData)
                                    .when()
                                    .post("users")
                                    .then()
                                    .spec(createdResponseSpec201)
                                    .extract().as(UserResponseModel.class)
                    );

            step("Проверка данных созданного пользователя", () -> {
                assertEquals(userResponse.getName(), "Anna");
                assertEquals(userResponse.getJob(), "AQA");
                assertNotNull(userResponse.getId());
                assertNotNull(userResponse.getCreatedAt());
            });
        });
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    public void updateUserTest() {
        step("Обновление данных 'Name' и 'Job'", () -> {
            UserRequestModel updatedUserData = new UserRequestModel();
            updatedUserData.setName("Hanna");
            updatedUserData.setJob("QA");

            UserResponseModel response =
                    step("Отправка PUT-запроса на обновление пользователя", () ->
                            given()
                                    .spec(requestSpec)
                                    .body(updatedUserData)
                                    .when()
                                    .put("users/2")
                                    .then()
                                    .spec(successResponseSpec200)
                                    .extract().as(UserResponseModel.class)
                    );

            step("Проверка данных обновленного пользователя", () -> {
                assertEquals(response.getName(), "Hanna");
                assertEquals(response.getJob(), "QA");
                assertNotNull(response.getUpdatedAt());
            });
        });
    }

    @Test
    @DisplayName("Проверка неудачной попытки входа без пароля")
    public void unsuccessfulLoginTest() {
        step("Проверка входа без пароля", () -> {
            LoginRequestModel requestLogin = new LoginRequestModel();
            requestLogin.setEmail("qa@guru");

            ErrorResponseModel errorResponse =
                    step("Отправка POST-запроса на вход без пароля", () ->
                            given()
                                    .spec(requestSpec)
                                    .body(requestLogin)
                                    .when()
                                    .post("login")
                                    .then()
                                    .spec(badRequestResponseSpec400)
                                    .extract().as(ErrorResponseModel.class)
                    );

            step("Проверка сообщения об ошибке", () -> {
                assertEquals(errorResponse.getError(), "Missing password");
            });
        });
    }

    @Test
    @DisplayName("Получение списка пользователей с задержкой 3 секунды")
    public void getUsersListWithDelayTest() {
        UserListResponseModel response =
                step("Отправка GET-запроса на получение списка пользователей с задержкой", () ->
                        given()
                                .spec(requestSpec)
                                .queryParam("delay", 3)
                                .when()
                                .get("users")
                                .then()
                                .spec(successResponseSpec200)
                                .extract().as(UserListResponseModel.class)
                );

        step("Проверка основных параметров ответа", () -> {
            assertEquals(response.getPage(), 1);
            assertEquals(response.getPerPage(), 6);
            assertEquals(response.getTotal(), 12);
            assertEquals(response.getTotalPages(), 2);
        });

        UserDataModel firstUser = response.getData().get(0);
        step("Проверка данных первого пользователя", () -> {
            assertEquals(firstUser.getId(), 1);
            assertEquals(firstUser.getEmail(), "george.bluth@reqres.in");
            assertEquals(firstUser.getFirstName(), "George");
            assertEquals(firstUser.getLastName(), "Bluth");
            assertEquals(firstUser.getAvatar(), "https://reqres.in/img/faces/1-image.jpg");
        });

        UserDataModel lastUser = response.getData().get(5);
        step("Проверка данных последнего пользователя", () -> {
            assertEquals(lastUser.getId(), 6);
            assertEquals(lastUser.getEmail(), "tracey.ramos@reqres.in");
            assertEquals(lastUser.getFirstName(), "Tracey");
            assertEquals(lastUser.getLastName(), "Ramos");
            assertEquals(lastUser.getAvatar(), "https://reqres.in/img/faces/6-image.jpg");
        });

        step("Проверка информации о поддержке", () -> {
            assertEquals(response.getSupport().getUrl(), "https://reqres.in/#support-heading");
            assertEquals(response.getSupport().getText(),
                    "To keep ReqRes free, contributions towards server costs are appreciated!");
        });
    }
}