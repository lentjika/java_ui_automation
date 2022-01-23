package ru.geekbrains.lesson;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.setProperty(
                "webdriver.chrome.driver", "src/main/resources/chromedriver.exe"
        );
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        login(driver);
        createNewRecord(driver);
        driver.close();
    }

    private static void login(WebDriver driver) {
        driver.get("https://diary.ru/");
        WebElement enterButton = null;
        try {
            enterButton = driver.findElement(By.linkText("Вход"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }

        enterButton.click();
        WebElement login = driver.findElement(By.id("loginform-username"));
        login.sendKeys("gb_gunina_test");
        driver.findElement(By.id("loginform-password")).sendKeys("Qw123456");
        driver.switchTo().frame(0);
        driver.findElement(By.cssSelector(".recaptcha-checkbox-border")).click();
        try {
            Thread.sleep(15000); //для прохода капчи
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().parentFrame();
        driver.findElement(By.id("login_btn")).click();
    }

    private static void createNewRecord(WebDriver driver) {
        driver.findElement(By.linkText("Новая запись")).click();
        driver.findElement(By.id("postTitle")).sendKeys("test_12");
        //driver.switchTo().frame(1);
        driver.switchTo().frame("message_ifr");
        driver.findElement(By.id("tinymce")).sendKeys("test_12");
        driver.switchTo().parentFrame();
        driver.findElement(By.id("rewrite")).click();
    }
}
