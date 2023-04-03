package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;
import ru.netology.data.DbUtils;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorMessage = $("[data-test-id='error-notification']");
    private SelenideElement codeEmptyNotification = $("[data-test-id=code] .input__sub");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(String code) {
        codeField.setValue(code);
        verifyButton.click();
        return new DashboardPage();
    }

    public void failedCode(String code) {
        codeField.setValue(code);
        verifyButton.click();
    }

    public void failedVerify() {
        errorMessage.shouldBe(visible);
        errorMessage.$(".notification__content").shouldHave(text("Неверно указан код"));
    }

    public void emptyCodeField() {
        codeEmptyNotification.shouldHave(text("обязательно для заполнения"));
    }
}
