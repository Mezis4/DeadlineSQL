package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DbUtils;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataHelper.*;

public class LogInTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
    }

    @AfterAll
    static void clear() {
        DbUtils.clearData();
    }

    @Test
    @Order(1)
    @DisplayName("should successfully logIn")
    public void shouldLogIn() {
        var validUser = getValidUser();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogIn(validUser);
        verificationPage.validVerify(DbUtils.getVerificationCode());
    }

    @Test
    @DisplayName("block after invalid 3 attempts")
    public void shouldBlock() {
        var invalidUser = getInvalidUser();
        var loginPage = new LoginPage();
        loginPage.logIn(invalidUser.getLogin(), invalidUser.getPassword());
        open("http://localhost:9999");
        loginPage.logIn(invalidUser.getLogin(), invalidUser.getPassword());
        open("http://localhost:9999");
        loginPage.logIn(invalidUser.getLogin(), invalidUser.getPassword());
        loginPage.blockNotification();
    }

    @Test
    @DisplayName("not log in with invalid login")
    public void shouldNotLogInWithInvalidLogin() {
        var user = getValidUser();
        var invalidLogin = getRandomLogin();
        var loginPage = new LoginPage();
        loginPage.logIn(invalidLogin, user.getPassword());
        loginPage.invalidDataNotification();
    }

    @Test
    @DisplayName("not log in with invalid password")
    public void shouldNotLogInWithInvalidPassword() {
        var user = getValidUser();
        var invalidPassword = getRandomPassword();
        var loginPage = new LoginPage();
        loginPage.logIn(user.getLogin(), invalidPassword);
        loginPage.invalidDataNotification();
    }

    @Test
    @DisplayName("not log in with invalid verify code")
    public void shouldNotLogInWithInvalidCode() {
        var validUser = getValidUser();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogIn(validUser);
        var code = getRandomCode();
        verificationPage.failedCode(code);
        verificationPage.failedVerify();
    }

    @Test
    @DisplayName("not log in with empty login field")
    public void shouldNotLogInLoginEmpty() {
        var user = getValidUser();
        var loginPage = new LoginPage();
        loginPage.logIn(null, user.getPassword());
        loginPage.emptyLoginField();
    }

    @Test
    @DisplayName("not log in with empty password field")
    public void shouldNotLogInPasswordEmpty() {
        var user = getValidUser();
        var loginPage = new LoginPage();
        loginPage.logIn(user.getLogin(), null);
        loginPage.emptyPasswordField();
    }

    @Test
    @DisplayName("not log in with empty code field")
    public void shouldNotLogInCodeEmpty() {
        var validUser = getValidUser();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogIn(validUser);
        verificationPage.failedCode(null);
        verificationPage.emptyCodeField();
    }
}
