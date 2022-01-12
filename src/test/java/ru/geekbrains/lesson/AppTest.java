package ru.geekbrains.lesson;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

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
        WebElement enterButton = null;
        enterButton = driver.findElement(By.linkText("Вход"));
        enterButton.click();
        var login = driver.findElement(By.id("loginform-username"));
        login.sendKeys("gb_gunina_test");
        driver.findElement(By.id("loginform-password")).sendKeys("Qw123456");
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        for (WebElement frame : iframes) {
            var frameTitle = frame.getAttribute("title");
            if (frameTitle.equals("reCAPTCHA")) {
                driver.switchTo().frame(frame);
                driver.findElement(By.cssSelector(".recaptcha-checkbox-border")).click();
                try {
                    Thread.sleep(15000); //для прохода капчи
                } catch (InterruptedException e) {
                    fail(e.getMessage());
                }
                driver.switchTo().parentFrame();
                break;
            }
        }

        driver.findElement(By.id("login_btn")).click();
    }

    private static void createNewRecord(WebDriver driver) {
        var newRecordBtn = driver.findElement(By.linkText("Новая запись"));
        newRecordBtn.click();
        var titleBox = driver.findElement(By.id("postTitle"));
        titleBox.sendKeys("test_12");
        driver.switchTo().frame("message_ifr");
        var descrBox = driver.findElement(By.id("tinymce"));
        descrBox.sendKeys("test_12");
        driver.switchTo().parentFrame();
        var rewriteBtn = driver.findElement(By.id("rewrite"));
        rewriteBtn.click();
    }

    @Test
    @Order(4)
    public void deleteRecord() {
        webDriver.findElement(By.cssSelector("a > .i-menu-diary")).click();
        List<WebElement> items = webDriver.findElements(By.xpath("//div[@class='item  first']"));
        assertNotEquals(0, items.size());
        var elem = items.get(0);
        String postId = elem.getAttribute("id");
        String id = postId.substring(4);
        var rmvBtn = webDriver.findElement(By.cssSelector("#post" + id + " .i-cross"));
        rmvBtn.click();
        var confirmBtn = webDriver.findElement(By.cssSelector("#modal_confirm_delete_post_" + id + " .btn-primary"));
        confirmBtn.click();
    }

    @Test
    @Order(3)
    public void editFirstRecord() {
        webDriver.findElement(By.cssSelector("a > .i-menu-diary")).click();
        List<WebElement> items = webDriver.findElements(By.xpath("//div[@class='item  first']"));
        assertNotEquals(0, items.size());
        var elem = items.get(0);
        String postId = elem.getAttribute("id");
        String id = postId.substring(4);
        var editBtn = webDriver.findElement(By.cssSelector("#post" + id + " .i-edit"));
        editBtn.click();
        webDriver.switchTo().frame("message_ifr");
        var descrBox = webDriver.findElement(By.id("tinymce"));
        descrBox.sendKeys(java.util.UUID.randomUUID().toString());
        String newDescr = descrBox.getText();
        webDriver.switchTo().parentFrame();
        var rewriteBtn = webDriver.findElement(By.id("rewrite"));
        rewriteBtn.click();
        webDriver.findElement(By.xpath("//*[contains(text(),'" + newDescr + "')]"));
    }
}
