package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppTest{

    protected WebDriver driver;

    private final String URL = "https://rahulshettyacademy.com/seleniumPractise/#/";
    private final String addProducts = "h4.product-name";

    private final String addToCart = "//div[@class = 'product-action']/button";

    int j =0;

    @BeforeClass
    public void setUp() throws InterruptedException{
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
        Thread.sleep(5000);

    }

    @Test
    public void addProducts() {

        String[] abc = {"Brocolli", "Beetroot", "Tomato"};
        List<String> productList = Arrays.asList(abc);
        List<WebElement> products = driver.findElements(By.cssSelector(addProducts));
        System.out.println(products);

        for(int i = 0; i<products.size(); i++){
            String [] name = products.get(i).getText().split("-");
            String productNeeded = name[0].trim();
            List addProducts = Arrays.asList(abc);
            if (addProducts.contains(productNeeded)) {
                j++;
                driver.findElements(By.xpath(addToCart)).get(i).click();
                if (j == 3) {
                    break;
                }
            }
        }
    }


    //@AfterClass
    public void tearDown(){
        if (driver !=null){
            driver.quit();
        }
    }

}
