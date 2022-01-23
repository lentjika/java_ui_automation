package ru.geekbrains.lesson;

import io.qameta.allure.Epic;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.List;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Тесты дневника")
public class AppTest {

    static WebDriver webDriver;

    @BeforeAll
    public static void init() {
        System.setProperty(
                "webdriver.chrome.driver", "src/main/resources/chromedriver.exe"
        );
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        webDriver.get("https://diary.ru/");
    }

    @AfterAll
    private static void finish() {
        webDriver.close();
    }

    @AfterEach
    public void tearDown() {
        LogEntries browserLogs = webDriver.manage().logs().get(LogType.BROWSER);
        List<LogEntry> allLogRows = browserLogs.getAll();

        System.out.println("-------------логи теста-----------------");
        if (allLogRows.size() > 0) {
            allLogRows.forEach((a) -> System.out.println(a.getMessage()));
        }
        System.out.println("-------------логи теста закончились-----------------");
    }

    @Test
    @Order(1)
    @Epic("Авторизация")
    @DisplayName("Процедура входа в дневник")
    public void login() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.clickLogin();

        Assertions.assertNotNull(webDriver.findElement(By.id("login-form")));

        LoginPage loginPage = new LoginPage(webDriver);

        try {
            loginPage
                    .enterLogin("gb_gunina_test")
                    .enterPassword("Qw123456")
                    .enterCaptcha(15000)
                    .clickLoginBtn();
        } catch (InterruptedException e) {
            Assertions.fail(e.getMessage());
        }
        Assertions.assertNotNull(webDriver.findElement(By.linkText("Мой дневник")));
    }

    @Test
    @Order(2)
    @Epic("Работа с дневником")
    @DisplayName("Создание новой записи")
    public void createNewRecord() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.newRecord();
        Assertions.assertEquals("НОВАЯ ЗАПИСЬ", webDriver.findElement(By.id("new_post_title")).getText());

        PostPage newPostPage = new PostPage(webDriver);
        newPostPage.enterTitle("test_12").enterDescription("test_descr_12").savePost();
        String text = newPostPage.getDescription();
        Assertions.assertNotNull(findElementByText(text));
    }

    @Test
    @Order(4)
    @Epic("Работа с дневником")
    @DisplayName("Удаление записи")
    public void deleteRecord() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.openList();
        Assertions.assertNotNull(webDriver.findElement(By.className("day-header")));

        PostList postList = new PostList(webDriver);
        postList.deleteFirst();
        Assertions.assertNotNull(webDriver.findElement(By.className("day-header")));
    }

    @Test
    @Order(3)
    @Epic("Работа с дневником")
    @DisplayName("Редактирование первоц записи")
    public void editFirstRecord() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.openList();
        Assertions.assertNotNull(webDriver.findElement(By.className("day-header")));

        PostList postList = new PostList(webDriver);
        postList.editFirst();

        PostPage postPage = new PostPage(webDriver);
        postPage.enterDescription(java.util.UUID.randomUUID().toString()).savePost();
        String text = postPage.getDescription();
        Assertions.assertNotNull(findElementByText(text));
    }

    private static WebElement findElementByText(String text) {
        return webDriver.findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
    }
}
