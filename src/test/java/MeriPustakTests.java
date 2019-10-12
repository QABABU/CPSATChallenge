import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class MeriPustakTests {

    private WebDriver driver;

    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().deleteAllCookies();

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.get("https://www.meripustak.com/");

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 30);


    }

    /**
     * printing the width and height of the logo
     */
    @Test(priority = 1)
    public void getHeightAndWidthTest() {

        WebElement logo = driver.findElement(By.xpath("//a[@id='mpstkLogo']/img"));

        wait.until(ExpectedConditions.visibilityOf(logo));

        System.out.println("The height of the logo is: " + logo.getSize().height);

        System.out.println("The width of the logo is: " + logo.getSize().width);

        System.out.println("The height of the logo is: " + logo.getAttribute("naturalHeight"));

        System.out.println("The width of the logo is: " + logo.getAttribute("naturalWidth"));

    }

    /**
     *  printing the href of ‘twitter’ social media icon
     */
    @Test(priority = 2)
    public void getTwitterHrefTest(){

        WebElement twitter_icon = driver.findElement(By.xpath("//a[contains(@href, 'twitter.com')]"));

        System.out.println("The twitter social media link (href) is: "+twitter_icon.getAttribute("href"));
    }

    /**
     * Clicking on the Shopping cart when items are zero in the cart
     */
    @Test(priority = 3)
    public void clickShoppingCartTest(){

        WebElement zero_item = driver.findElement(By.id("lblNoCartItem"));

        WebElement shopping_cart = driver.findElement(By.xpath("//div[@class='shopping_cart']//a[text()='Shopping Cart']"));

        if(zero_item.isDisplayed()){

            System.out.println("Shopping cart has zero item");

            shopping_cart.click();
        }else {
            System.out.println("Shopping cart is 1 or more items");
        }
    }

    /**
     * Asserting the shopping cart message
     */
    @Test(priority = 4)
    public void assertShoppingCartMessageTest(){

        WebElement no_items_in_cart_text = driver.findElement(By.xpath("//table[@id='ContentPlaceHolder1_gvCartTable']//h4"));

        System.out.println("The text: "+no_items_in_cart_text.getText());

        wait.until(ExpectedConditions.visibilityOf(no_items_in_cart_text));

        Assert.assertEquals("No Item is Added In Cart yet.Cart is Empty!!!", no_items_in_cart_text.getText().trim());

    }

    /**
     * Adding java book to the cart and verifying the message
     */
    @Test(priority = 5)
    public void addJavaBookTest(){

        WebElement search_box = driver.findElement(By.id("txtsearch"));

        WebElement search_btn = driver.findElement(By.id("btnsearch"));

        search_box.sendKeys("Core Java");

        search_btn.click();

        WebElement first_book = driver.findElement(By.xpath("(//div[@id='book_list']/ul/li/a/img)[1]"));

        wait.until(ExpectedConditions.visibilityOf(first_book));

        first_book.click();

        WebElement add_cart_btn = driver.findElement(By.id("ContentPlaceHolder1_AddtoCart"));

        wait.until(ExpectedConditions.visibilityOf(add_cart_btn));

        add_cart_btn.click();

        try{
            //trying to find the no items cart message text area
            WebElement no_items_in_cart_text = driver.findElement(By.xpath("//table[@id='ContentPlaceHolder1_gvCartTable']//h4"));
            System.out.println("The message is displayed even after adding the java book");
            Assert.fail();
        }catch (NoSuchElementException e){
            System.out.println("Message is not displayed after adding the java book to cart as expected");
            Assert.assertTrue(true);
        }
    }


    @AfterClass
    public void closeBrowser(){
       driver.quit();
        System.out.println("Test 1/6 of Challenge 1 is completed successfully");
    }
}
