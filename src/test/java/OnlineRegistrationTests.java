import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class OnlineRegistrationTests {

    @Test
    public void registrationTests(){

        WebDriverManager.firefoxdriver().setup();

        WebDriver driver = new FirefoxDriver();

        driver.manage().window().maximize();

        driver.manage().deleteAllCookies();

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.get(" https://www.cii.in/OnlineRegistration.aspx");

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, 30);

        WebElement attendee_dropdown = driver.findElement(By.id("drpAttendee"));

        Select attendees = new Select(attendee_dropdown);

        attendees.selectByVisibleText("3");

        List<WebElement> total_rows = driver.findElements(By.xpath("//table[@id='Gridview1']/tbody/tr"));

        System.out.println("Total rows is: "+total_rows.size());

        int attendee_row_count = total_rows.size()-1;

        System.out.println("Only attendee's row count: "+attendee_row_count);

        assertEquals(3,attendee_row_count);

        WebElement first_title_dropdown = driver.findElement(By.id("Gridview1_ctl02_drpTitle"));

        new Select(first_title_dropdown).selectByIndex(1);

        WebElement second_title_dropdown = driver.findElement(By.id("Gridview1_ctl03_drpTitle"));

        new Select(second_title_dropdown).selectByValue("CA");

        WebElement third_title_dropdown = driver.findElement(By.id("Gridview1_ctl04_drpTitle"));

        new Select(third_title_dropdown).selectByVisibleText("CS");

        List<WebElement> option_elements = new Select(first_title_dropdown).getOptions();

        System.out.println("All the options in the Title dropdown are: ");

        for (WebElement element : option_elements) {

            System.out.println(element.getText().trim());
        }

        driver.quit();


    }

}
