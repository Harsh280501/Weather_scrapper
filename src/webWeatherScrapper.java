import java.io.FileWriter;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import java.util.List;
import java.io.IOException;

public class webWeatherScrapper {
    public static void main(String[] args) {
        // Array of city names to search for.
        String[] Nsh = { "Vaughan, ON", "Brampton, ON", "Barrie, ON", "Cambridge, ON",
                "Peterborough, ON", "Sudbury, ON"};

        // Set the system property for ChromeDriver. This tells Selenium which driver to use for Chrome.
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\harsh\\OneDrive\\Desktop\\chromedriver-win64\\chromedriver.exe");

        // Initialize the Chrome WebDriver.
        WebDriver ABCD = new ChromeDriver();

        try (FileWriter QWE = new FileWriter("report.csv")) {
            // Write the CSV header line.
            QWE.write("Name of the City,City Temperature,Additional Information\n");

            // Loop through each city in the array.
            for (String city : Nsh) {
                System.out.println("City: " + city);

                // Set up a wait to use later for elements to become visible.
                WebDriverWait BCV = new WebDriverWait(ABCD, Duration.ofSeconds(8));

                // Navigate to the weather network website.
                ABCD.get("https://www.theweathernetwork.com/");

                // Find the search box and enter the city name.
                WebElement spacetoenter = ABCD.findElement(By.id("search"));
                spacetoenter.sendKeys(city);

                // Wait until the suggestions list becomes visible.
                BCV.until(ExpectedConditions.visibilityOfElementLocated(By.id("suggestionlist")));

                // Find the suggestion list and all its 'a' elements (links).
                WebElement queue = ABCD.findElement(By.id("suggestionlist"));
                List<WebElement> alter = queue.findElements(By.tagName("a"));

                // Loop through all suggestions and click the one that matches our city.
                for (WebElement alternate : alter) {
                    if (alternate.getText().contains(city)) {
                        alternate.click();
                        break;
                    }
                }

                // Wait until the weather details become visible.
                BCV.until(ExpectedConditions.visibilityOfElementLocated(By.id("obs_data")));

                // Find and extract the temperature.
                WebElement deatiltem = ABCD.findElement(By.cssSelector(".temp"));
                String merc = deatiltem.getText();

                // Find and extract the additional weather details, replacing newlines with semicolons.
                WebElement finalel = ABCD.findElement(By.className("secondary-obs"));
                String overallDetails = finalel.getText().replace("\n", "; ");

                // Print the details to the console.
                System.out.println("City Temperature: " + merc + " Celsius");
                System.out.println(overallDetails);
                System.out.println("*********************");

                // Write the city details to the CSV file.
                QWE.write(city + "," + merc + ",\"" + overallDetails + "\"\n");
            }
        } catch (IOException e) {
            // Handle any IO exceptions.
            e.printStackTrace();
        } finally {
            // Ensure the WebDriver is quit at the end to close the browser window.
            ABCD.quit();
        }
    }
}
