package ru.geekbrains.lesson;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage {
    private WebDriver driver;

    @FindBy(id = "loginform-username")
    private WebElement loginBox;

    @FindBy(id = "loginform-password")
    private WebElement passwordBox;

    @FindBy(tagName = "iframe")
    private List<WebElement> frames;

    @FindBy(css = ".recaptcha-checkbox-border")
    private WebElement captchaButton;

    @FindBy(id = "login_btn")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage enterLogin(String login) {
        loginBox.sendKeys(login);
        return this;
    }

    public LoginPage enterPassword(String pwd) {
        passwordBox.sendKeys(pwd);
        return this;
    }

    public LoginPage enterCaptcha(int millis) throws InterruptedException {
        for (WebElement frame : frames) {
            String frameTitle = frame.getAttribute("title");
            if (frameTitle.equals("reCAPTCHA")) {
                driver.switchTo().frame(frame);
                captchaButton.click();
                Thread.sleep(millis); //для прохода капчи
                driver.switchTo().parentFrame();
                break;
            }
        }
        return this;
    }

    public LoginPage clickLoginBtn() {
        loginButton.click();
        return this;
    }
}
