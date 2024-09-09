
// Import necessary Java and Selenium WebDriver classes
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.FileWriter;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;

// Defining the WeatherWebScrapper class

public class WeatherWebScrapper {

    // Main method for the code

    public static void main(String[] args) {

        // List of cities that will be scrapped

        String[] cityName = { "Toronto, ON", "Hamilton, ON", "Kitchener, ON", "London, ON",
                "Windsor, ON", "Oshawa, ON", "Ottawa, ON" };

        // Setting the system property to the path of the ChromeDriver executable

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\harsh\\OneDrive\\Desktop\\chromedriver-win64\\chromedriver.exe");

        // Creating a new instance of the ChromeDriver, opening a Chrome browser

        WebDriver driver = new ChromeDriver();

        // Try block to ensure resources are freed after use

        try (FileWriter writer = new FileWriter("weather_data.csv")) {

            // Writing the CSV file header

            writer.write("City,Temperature,Overall Details\n");

            // Looping through each city in the cityName array

            for (String city : cityName) {
                // Printing the current city to the console

                System.out.println("City: " + city);
                // Creating a new WebDriverWait instance for waiting up to 10 seconds

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                // Navigating to the weather network website

                driver.get("https://www.theweathernetwork.com/");

                // Finding the search box element by its HTML ID and enter the city name
                WebElement searchBox = driver.findElement(By.id("search"));

                searchBox.sendKeys(city);
                // Waiting until the suggestions box is visible

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("suggestionlist")));

                // Finding the suggestion list box and all suggestion elements within it
                WebElement suggestionList = driver.findElement(By.id("suggestionlist"));

                List<WebElement> suggestions = suggestionList.findElements(By.tagName("a"));
                // Looping through each suggestion and click the one that matches our city

                for (WebElement suggestion : suggestions) {

                    if (suggestion.getText().contains(city)) {
                        suggestion.click();
                        break;
                    }
                }

                // Waiting until the page with the weather observation data is visible
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("obs_data")));
                // Finding the temperature element by its CSS selector and retrieve its text
                WebElement temperatureElement = driver.findElement(By.cssSelector(".temp"));
                String temperature = temperatureElement.getText();
                // Finding the element containing overall weather details
                WebElement overallElement = driver.findElement(By.className("secondary-obs"));
                // Getting the text of the overall details, replacing newlines with semicolons
                String overallDetails = overallElement.getText().replace("\n", "; ");

                // Printting the temperature and overall details to the console
                System.out.println("Temperature: " + temperature + "C");
                System.out.println(overallDetails);
                System.out.println("=======================================");

                // Writing the city, temperature, and overall details to the CSV file
                writer.write(city + "," + temperature + ",\"" + overallDetails + "\"\n");
            }
        } catch (IOException e) {
            // Catching any IOExceptions and print the stack trace
            e.printStackTrace();
        } finally {
            // Ensuring the WebDriver is closed after execution
            driver.quit();
        }
    }
}
