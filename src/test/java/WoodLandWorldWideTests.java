import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ExcelDataReader;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class WoodLandWorldWideTests {

    private WebDriver driver;

    private WebDriverWait wait;


    @BeforeClass
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        //Add chrome --disable-notifications**"
        options.addArguments("--disable-notifications");

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        driver.manage().deleteAllCookies();

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.get("https://www.woodlandworldwide.com/");

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 30);
    }

    @Test(dataProvider = "get-data", dataProviderClass= ExcelDataReader.class)
    public void Test1(String product) {
        searchProduct(product);
        validateDescendingOrder();
    }


    @Test(dataProvider = "get-data", dataProviderClass= ExcelDataReader.class)
    public void Test2(String product) {
        searchProduct(product);
        validateDescendingOrder();
    }


    @Test(dataProvider = "get-data", dataProviderClass= ExcelDataReader.class)
    public void Test3(String product) {
        searchProduct(product);
        validateDescendingOrder();
    }

    private void searchProduct(String product) {

        WebElement search_icon = driver.findElement(By.xpath("//form[@id='searchForm']/div"));
        search_icon.click();
        WebElement search_box = driver.findElement(By.id("searchkey"));

        search_box.sendKeys(product);
        WebElement go_button = driver.findElement(By.id("searchBtn"));
        go_button.click();

    }


    //validating descending order of the prices
    private void validateDescendingOrder(){

        WebElement high_to_low = driver.findElement(By.xpath("//input[@id='prcHTL_sort']/following-sibling::label"));

        wait.until(ExpectedConditions.visibilityOf(high_to_low));

        high_to_low.click();

        List<WebElement> product_price_elements = driver.findElements(By.xpath("//span[@class='mrp']"));

        ArrayList<Integer> prices = new ArrayList<Integer>();

        for (int i=0; i < 8; i++){

            prices.add(Integer.parseInt(product_price_elements.get(i).getText().replace("R ", "")));
        }

        boolean flag = false;

        //checking whether prices pin the list or in descending order or not
        for (int i=0; i < 7; i++){

            if (prices.get(i) >= prices.get(i + 1)) {
                flag = true;
            }
        }
        Assert.assertTrue(flag);
    }

    @AfterClass
    public void quitBrowser(){
        driver.quit();
    }




}
