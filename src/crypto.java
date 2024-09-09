import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class crypto {

    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\harsh\\OneDrive\\Desktop\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://coinmarketcap.com/");

        // Correct the CSS selector for the search button
        WebElement svgSearchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("svg.fKbUaI")));
        svgSearchButton.click();

        // Correct the CSS selector for the search bar
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sc-d565189d-5 jmWcnc .sc-d565189d-3 kKevNe"))); // This uses a part of the class name that we can see in the image
        searchBar.sendKeys("Bitcoin");
        searchBar.sendKeys(Keys.RETURN);

        // Wait for the Bitcoin stats section to load
        // Adjust the selector to match the section that confirms the page has loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='sc-aef7b723-0'][class*='sc-55349342-0']"))); // Use CSS selector to target the element correctly

        // Retrieve the Bitcoin price
        WebElement bitcoinPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class='sc-f70bb44c-0 jxpCgO base-text']"))); // Correct the classes
        String bitcoinPrice = bitcoinPriceElement.getText();
        System.out.println("Bitcoin Price: " + bitcoinPrice);

        driver.quit();
    }
}
