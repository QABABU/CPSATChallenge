import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PremierLeagueTests {

    private WebDriver driver;

    @Test
    public void premierLeagueTest() throws InterruptedException, AWTException {

        WebDriverManager.firefoxdriver().setup();

        driver = new FirefoxDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.get("https://www.premierleague.com/");

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, 15);

        // The add screen will be displayed sometimes only

        WebElement ad_close = driver.findElement(By.id("advertClose"));

        closeAddScreen(wait, ad_close);

        WebElement cookies = driver.findElement(By.xpath("//div[@class='btn-primary cookies-notice-accept']"));

        wait.until(ExpectedConditions.visibilityOf(cookies));

        cookies.click();

        WebElement tables_menu = driver.findElement(By.xpath("//ul[contains(@class,'showMoreEnabled')]//a[contains(@class,'')][contains(text(),'Tables')]"));

        wait.until(ExpectedConditions.visibilityOf(tables_menu));

        tables_menu.click();

        WebElement ad_close_2 = waitForStaleElement(By.id("advertClose"));

        closeAddScreen(wait, ad_close_2);

       // Thread.sleep(3000);

        WebElement arsenal_team = driver.findElement(By.xpath("//tbody[@class='tableBodyContainer isPL']//span[@class='long' and text()='Arsenal']"));

        JavascriptExecutor executor = (JavascriptExecutor)driver;

        //executor.executeScript("arguments[0].scrollIntoView();", arsenal_team);

        executor.executeScript("window.scrollBy(0, 300)");

        String parent_window = driver.getWindowHandle();

        Actions actions = new Actions(driver);

        actions.contextClick(arsenal_team).build().perform();

        Thread.sleep(2000);

        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_W);

        robot.keyRelease(KeyEvent.VK_W);

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        Set<String> all_windows = driver.getWindowHandles();

        for (String window:all_windows) {

            if(!window.equals(parent_window)){
                driver.switchTo().window(window);
                WebElement official_website_url = driver.findElement(By.xpath("//div[@class='website']//a"));
                System.out.println("The official website URL "+official_website_url.getText());
                System.out.println("The page title of the newly opened window "+driver.getTitle());
            }
        }       
        driver.switchTo().defaultContent();

        System.out.println("The main window title "+driver.getTitle());

        driver.quit();


    }

    /**
     * closing the add screen when displayed
     */
    private void closeAddScreen(WebDriverWait wait, WebElement closeButton){
        try{
            wait.until(ExpectedConditions.visibilityOf(closeButton));
            closeButton.click();
            System.out.println("The close button is displayed and closed");
        }catch (TimeoutException e){
            System.out.println("Now the add screen is not displayed");
        }
    }

    /**
     * Handling the stale element
     */
    private WebElement waitForStaleElement(By locator) {
        System.out.println("In stale element");
        try {
            return driver.findElement(locator); // Returns when the object identified

        } catch (StaleElementReferenceException e) {

            return waitForStaleElement(locator); //recalls the function till the element on the page identified
        }
    }

    @After
    public void closeBrowser(){
        driver.quit();
    }
}
