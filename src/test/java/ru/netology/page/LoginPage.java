package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement blockMessage = $("[data-test-id='block-notification']");
    private SelenideElement errorMessage = $("[data-test-id='error-notification']");
    private SelenideElement loginEmptyNotification = $("[data-test-id=login] .input__sub");
    private SelenideElement passwordEmptyNotification = $("[data-test-id=password] .input__sub");


    public VerificationPage validLogIn(DataHelper.AuthInfo authInfo){
        var login = authInfo.getLogin();
        var password = authInfo.getPassword();
        logIn(login, password);
        return new VerificationPage();
    }
    public void logIn(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }

    public void blockNotification() {
        blockMessage.shouldHave(Condition.text("Учетная запись заблокирована"));
    }

    public void invalidDataNotification() {
        errorMessage.should(visible);
        errorMessage.$(".notification__content").shouldHave(text("Неверно указан"));
    }
    public void emptyLoginField() {
        loginEmptyNotification.shouldHave(text("обязательно для заполнения"));
    }

    public void emptyPasswordField() {
        passwordEmptyNotification.shouldHave(text("обязательно для заполнения"));
    }
}