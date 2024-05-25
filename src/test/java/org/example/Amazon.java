package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;


public class Amazon {

    private final String URL = "https://www.amazon.in/";

    private final String clickCategories = "//a[@data-csa-c-content-id = 'nav_cs_electronics']";

    private final String clickLaptops = "(//img[@alt = 'Laptops'])[1]";

    private final String getLaptops = "//*[@data-cy = 'title-recipe']/h2/a/span";

    private final String seeAll = "apb-desktop-browse-search-see-all";

    private final  String next = "//*[text() = 'Next']";

    private WebDriver driver;


    @BeforeClass
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
    }

    @Test
    public void test() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Clicking on categories
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(clickCategories))).click();

        // Clicking on laptops
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(clickLaptops))).click();

        // Clicking on see all
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(seeAll))).click();

        while (true) {
            // Waiting for laptops to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getLaptops)));

            // Getting all products
            List<WebElement> products = driver.findElements(By.xpath(getLaptops));

            for (int i = 0; i < products.size(); i++) {
                WebElement product = products.get(i);
                String text = product.getText();
                System.out.println((i + 1) + " " + text);
            }

            try {
                WebElement nextPageButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(next)));
                if (!nextPageButton.isEnabled()) {
                    // If the "Next" button is disabled, we've reached the last page
                    break;
                }
                nextPageButton.click();
            } catch (NoSuchElementException e) {
                // If the "Next" button is not found, we've reached the last page
                break;
            }
        }
    }


    //@AfterClass()
    public void tearDown(){
        if (driver !=null){
            driver.quit();
        }
    }
}
