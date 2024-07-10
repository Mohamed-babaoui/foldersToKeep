package Tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class StandaloneTestProject {
    public WebDriver driver;
    public List<WebElement> sitesElements;
    @Test
    public void testMethod() throws InterruptedException {
        String countyCode="FR";
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://www.centerparcs.fr/");
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement acceptElement =driver.findElement(By.id("didomi-notice-agree-button"));
        wait.until(ExpectedConditions.elementToBeClickable(acceptElement));
        acceptElement.click();
        driver.findElement(By.cssSelector(".searchForm-parkSelectContainer")).click();
        //locatorOf Parc css selector
        WebElement countryElement = getChooseCountry(countyCode);
        Actions actions = new Actions(driver);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOf(countryElement));

        // Perform the mouse hover action
        actions.moveToElement(countryElement).build().perform();
        Thread.sleep(5000);
        //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".searchForm-siteLabel"))));
        WebElement elementOfSite=driver.findElement(By.cssSelector(".searchForm-siteLabel"));
        sitesElements = driver.findElements(By.cssSelector(".searchForm-siteLabel"));
        List<String> textList = sitesElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        textList.forEach(System.out::println);
        String selecteSite= textList.get(0);

        //sitesElements.get(0).click();
        sitesElements.stream().filter(s->s.getText().equalsIgnoreCase(selecteSite)).findAny().get().click();
        //TODO //button[@data-modeldata='capacity']
        driver.findElement(By.xpath("//button[@data-modeldata='capacity']")).click();
        //div[@id='searchEngine-children']/div[2]/span[@data-action="substract"]
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@id='searchEngine-children']/div[2]/span[@data-action=\"substract\"]"))));
        driver.findElement(By.xpath("//div[@id='searchEngine-children']/div[2]/span[@data-action=\"substract\"]")).click();

        driver.findElement(By.cssSelector(".date-start")).click();

        Thread.sleep(3000);
        //WebElement calendar=driver.findElement(By.cssSelector(".calendarBlock.u-Hidden-mobile"));
        //WebElement monthOfStart = driver.findElement(By.xpath("//div[@id='pika-title-hg']/div[@class='pika-label'][1]"));
        //wait.until(ExpectedConditions.elementToBeClickable(monthOfStart));
       /* calendar.findElement(By.cssSelector(".pika-select-month")).click();
        //monthOfStart.click();

        // Create a Select object
        Select options = new Select(driver.findElement(By.cssSelector(".pika-select-month")));

        // Get all options in the dropdown and print them
        List<WebElement> listOfOptions = options.getOptions().stream().collect(Collectors.toList());
        System.out.println(listOfOptions.stream().map(WebElement::getText).collect(Collectors.toList()));

        // Select "Avril" from the dropdown
       // options.selectByIndex(5);

        //calendar.findElement(By.xpath("//option[text()=\"Juillet\"]")).click();*/
        driver.findElement(By.xpath("//button[text()=\"13\"]")).click();
        Thread.sleep(3000);
        WebElement departure=driver.findElement(By.cssSelector(".pika-single.is-bound.departure"));
        //wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[text()=\"20\"]"))));
       departure.findElement(By.xpath("//button[text()=\"20\"]")).click();
        driver.quit();











    }

    /**
     *
     * @param siteName
     * @return
     */
    private  WebElement getChoosenSite(String siteName) {
        return driver.findElement(By.xpath("//input[@data-label="+siteName+"\""));
    }


    private  WebElement getChooseCountry(String countryCode) {
        List<String> countriesCodes = Arrays.asList("FR", "DE", "BE", "NL");
        if (!(countriesCodes.contains(countryCode))){
            throw new RuntimeException("Invalid country code");
        }

        return driver.findElement(By.xpath("//a[@data-key='"+countryCode+"']"));

    }
}
