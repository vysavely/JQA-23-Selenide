package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    private String deliveryDate(int days, String date) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(date));
    }

    @Test
    void cardOrderAndDeliveryTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        String currentDate = deliveryDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Трутнев Олег");
        $("[data-test-id='phone'] input").setValue("+79200705871");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));

    }

}