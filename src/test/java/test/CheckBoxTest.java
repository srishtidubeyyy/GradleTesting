package test;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CheckBoxTest {

    WebDriver webDriver;

    @BeforeTest
    public void setWebDriver() {
        webDriver = new ChromeDriver();
    }

    @Test
    public void testCheckBox() {

        webDriver.navigate().to("http://the-internet.herokuapp.com/checkboxes");
        WebElement webElement = webDriver.findElement(By.xpath("//input[@type='checkbox']"));
        webElement.click();
        Assert.assertTrue(webElement.isSelected());
    }

    @Test

    public void testDropDown() {
        webDriver.navigate().to("http://the-internet.herokuapp.com/dropdown");
        WebElement webElement = webDriver.findElement(By.id("dropdown"));
        Select dropDown = new Select(webElement);
        dropDown.selectByVisibleText("Option 2");
        Assert.assertFalse(webElement.isDisplayed());
    }

    @AfterMethod
    public void AfterMethod(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            File screenshotFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

            String destinationDirectory = "C:/Users/srishti_dubey/GradleGroovyTest/Screenshots";

            FileUtils.copyFile(screenshotFile, new File(destinationDirectory + "failure" + ".jpeg"));
            String screenshotFilePath = destinationDirectory + "failure.jpeg";

            try (InputStream inputStream = Files.newInputStream(Paths.get(screenshotFilePath))) {
                Allure.addAttachment("Screenshot", inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @AfterSuite
    public void quitServer() {
        webDriver.quit();
    }


}