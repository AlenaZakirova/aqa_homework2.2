import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldAcceptRequest() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Владимир");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иван Иванов-Иванович");
        $("[data-test-id=phone] input").setValue("+79610000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));


    }

    @Test
    public void emptyValueInTheCityField() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(" ");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иван Иванов-Иванович");
        $("[data-test-id=phone] input").setValue("+79610000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void checkingTheCityOfLat() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Vladimir");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иван Иванов-Иванович");
        $("[data-test-id=phone] input").setValue("+79610000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void lessThanTwoDaysMeetingDates() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Владимир");
        String planningDate = generateDate(2, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иван Иванов-Иванович");
        $("[data-test-id=phone] input").setValue("+79610000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void surnameGivenNameInLat() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Владимир");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Ivan Ivanov-Ivanovich");
        $("[data-test-id=phone] input").setValue("+79610000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(withText("Имя и Фамилия указаные неверно.")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void emptyValueLastNameFirstName() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Владимир");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue(" ");
        $("[data-test-id=phone] input").setValue("+79610000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void blankValueAndPhone() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Владимир");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иван Иванов-Иванович");
        $("[data-test-id=phone] input").setValue(" ");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void checkboxNotSelected() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Владимир");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иван Иванов-Иванович");
        $("[data-test-id=phone] input").setValue("+79611135891");
        $("button.button").click();
        $(withText("На указанный номер моб. тел. будет отправлен смс-код")).shouldBe(visible, Duration.ofSeconds(15));


    }
}
