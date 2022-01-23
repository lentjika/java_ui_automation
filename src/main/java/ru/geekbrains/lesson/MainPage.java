package ru.geekbrains.lesson;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    private WebDriver driver;

    @FindBy(linkText = "Вход")
    private WebElement loginButton;

    @FindBy(linkText = "Новая запись")
    private WebElement newRecord;

    @FindBy(css = "a > .i-menu-diary")
    private WebElement listButton;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public MainPage clickLogin(){
        loginButton.click();
        return this;
    }

    public MainPage newRecord(){
        newRecord.click();
        return this;
    }

    public MainPage openList(){
        listButton.click();
        return this;
    }
}
