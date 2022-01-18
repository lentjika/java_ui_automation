package ru.geekbrains.lesson;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @Test
    @Order(1)
    public void login() {
        login(webDriver);
    }

    @Test
    @Order(2)
    public void createNewRecord() {
        createNewRecord(webDriver);
    }

    private static void login(WebDriver driver) {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .enterLogin("gb_gunina_test")
                .enterPassword("Qw123456")
                .enterCaptcha(15000)
                .clickLoginBtn();
    }

    private static void createNewRecord(WebDriver driver) {
        MainPage mainPage = new MainPage(driver);
        mainPage.newRecord();

        PostPage newPostPage = new PostPage(driver);
        newPostPage.enterTitle("test_12").enterDescription("test_descr_12").savePost();
    }

    @Test
    @Order(4)
    public void deleteRecord() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.openList();

        PostList postList = new PostList(webDriver);
        postList.deleteFirst();
    }

    @Test
    @Order(3)
    public void editFirstRecord() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.openList();

        PostList postList = new PostList(webDriver);
        postList.editFirst();

        PostPage postPage = new PostPage(webDriver);
        postPage.enterDescription(java.util.UUID.randomUUID().toString()).savePost();
        String newDescr = postPage.getDescription();

        postList.findPost(newDescr);
    }
}
