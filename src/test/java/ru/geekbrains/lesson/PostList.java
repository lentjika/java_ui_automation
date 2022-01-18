package ru.geekbrains.lesson;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PostList {
    private WebDriver driver;

    @FindBy(xpath = "//div[@class='item  first']")
    private List<WebElement> postList;

    public PostList(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private String getFirstId(){
        var elem = postList.get(0);
        String postId = elem.getAttribute("id");
        String id = postId.substring(4);
        return id;
    }

    public PostList deleteFirst() {
        String id = getFirstId();
        var rmvBtn = driver.findElement(By.cssSelector("#post" + id + " .i-cross"));
        rmvBtn.click();
        var confirmBtn = driver.findElement(By.cssSelector("#modal_confirm_delete_post_" + id + " .btn-primary"));
        confirmBtn.click();
        return this;
    }

    public PostList editFirst(){
        String id = getFirstId();
        var editBtn = driver.findElement(By.cssSelector("#post" + id + " .i-edit"));
        editBtn.click();
        return this;
    }

    public PostList findPost(String text){
        driver.findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
        return this;
    }
}
