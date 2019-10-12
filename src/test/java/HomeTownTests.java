import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.util.concurrent.TimeUnit;

public class HomeTownTests {

    private WebDriver driver;

    @Test
    public void homeTownTest(){

        WebDriverManager.firefoxdriver().setup();

        driver = new FirefoxDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.get("https://www.hometown.in/");

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, 30);

        WebElement notification_button = driver.findElement(By.id("onesignal-popover-cancel-button"));

        wait.until(ExpectedConditions.visibilityOf(notification_button));

        notification_button.click();

        Actions actions = new Actions(driver);

        WebElement electronics_menu = driver.findElement(By.xpath("//a[contains(text(),'Electronics')]"));

        wait.until(ExpectedConditions.elementToBeClickable(electronics_menu));

        //*************** clicking the electronics menu directly, as there is no option under More menu ***********//
        actions.moveToElement(electronics_menu).click().build().perform();

        WebElement color_dropdown = driver.findElement(By.xpath("//button[contains(text(),'Color')]"));

        actions.moveToElement(color_dropdown).build().perform();

        //***************** Selecting the block color ************************//
        WebElement black_color_chkbox = driver.findElement(By.xpath("//label[contains(text(),'Black')]"));

        wait.until(ExpectedConditions.visibilityOf(black_color_chkbox));

        black_color_chkbox.click();

        WebElement first_product = driver.findElement(By.xpath("(//img[starts-with(@class, 'Product__ProductImg-sc')])[1]"));

        actions.moveToElement(first_product).perform();


        //*********************** Clicking on the QUICK VIEW of first product ****************//
        driver.findElement(By.xpath("(//button[text()='QUICK VIEW'])[1]")).click();

        String product_text = driver.findElement(By.xpath("//h1/a")).getText();

        //****************** checking whether Black is present in the product text ***************//
        if(product_text.contains("Black")){

            Assert.assertTrue(true);
        }else {
            Assert.fail("Product text doesn't contain Black");
        }

        WebElement close_button = driver.findElement(By.xpath("//button[@class='styles_closeButton__20ID4']"));

        wait.until(ExpectedConditions.visibilityOf(close_button));

        //**************closing the Quick View***************//
        close_button.click();

        WebElement color_dropdown_again = waitForStaleElement(By.xpath("//button[contains(text(),'Color')]"));

        actions.moveToElement(color_dropdown_again).build().perform();

        //*************** checking the black color checkbox selected or not after Quick View close ************//
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),'Black')]/preceding-sibling::div/input")).isSelected());

        System.out.println("In applied filter - color, the Black is still selected ");

        driver.quit();


    }

    private WebElement waitForStaleElement(By locator) {
        System.out.println("In stale element");
        try {

            return driver.findElement(locator); // Returns when the object identified

        } catch (StaleElementReferenceException e) {

            return waitForStaleElement(locator); //recalls the function till the element on the page identified
        }
    }
}
