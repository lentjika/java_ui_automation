package ru.geekbrains.lesson;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PostPage {
    private WebDriver driver;
    private String description;

    @FindBy(id = "postTitle")
    private WebElement titleBox;

    @FindBy(id = "tinymce")
    private WebElement descriptionBox;

    @FindBy(id = "rewrite")
    private WebElement rewriteBox;

    public PostPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public PostPage enterTitle(String text) {
        titleBox.sendKeys(text);
        return this;
    }

    public PostPage enterDescription(String text) {
        driver.switchTo().frame("message_ifr");
        descriptionBox.sendKeys(text);
        description = descriptionBox.getText();
        driver.switchTo().parentFrame();
        return this;
    }

    public PostPage savePost(){
        rewriteBox.click();
        return this;
    }

    public String getDescription(){
        return description;
    }
}
