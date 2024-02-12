package pages;

import config.ConfigProvider;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class CommonActionWithElements {

    Logger logger = Logger.getLogger(getClass());
    protected WebDriver webDriver;

    protected WebDriverWait webDriverExplicitWaitLow, webDriverExplicitWaitHigh;

    public CommonActionWithElements(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);//this - means all elements from this class will be initialized FindBy
        webDriverExplicitWaitLow = new WebDriverWait(webDriver, Duration.ofSeconds(ConfigProvider.configProperties.EXPLICIT_WAIT_TIME_LOW()));
        webDriverExplicitWaitHigh = new WebDriverWait(webDriver, Duration.ofSeconds(ConfigProvider.configProperties.EXPLICIT_WAIT_TIME_HIGH()));
    }

    public void enterTextIntoInput(WebElement input, String text) {
        try {
            input.clear();
            input.sendKeys(text);
            logger.info(text + " was inputted into input " + getElementName(input));
        } catch (Exception e) {
            printErrorAndStopTest(e);
        }
    }

    private String getElementName(WebElement element) {
        try {
            return element.getAccessibleName();
        }catch (Exception e){
            return "";
        }
    }

    private void printErrorAndStopTest(Exception e) {
        logger.error("Can not work with element" + e);
        Assert.fail("Can not work with element" + e);
    }

    public void clickOnElement(WebElement element) {
        try {
            String elementName = getElementName(element);
            webDriverExplicitWaitLow.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logger.info(elementName + " Element was clicked");
        } catch (Exception e) {
            printErrorAndStopTest(e);
        }
    }

    public void clickOnElement(String locator) {
        try {
            clickOnElement(webDriver.findElement(By.xpath(locator)));
            logger.info("Element was clicked");
        } catch (Exception e) {
            printErrorAndStopTest(e);
        }
    }

    public boolean isElementDisplayed(WebElement element) {
        try {

            boolean state = element.isDisplayed();
            if (state) {
                logger.info("Element is displayed");
            } else {
                logger.info("Element is not displayed");
            }
            return state;
        } catch (Exception e) {
            logger.info("Element is not  displayed");
            return false;
        }
    }

    //click on drop down and click on value
    public void selectTextInDropDown  (WebElement dropDown, String text){
        clickOnElement( dropDown);
        clickOnElement(webDriver.findElement(org.openqa.selenium.By.xpath(".//option[text()='" + text + "']")));


    }

    public void selectValueInDropDown(WebElement dropDown, String value) {
        try {
            Select select = new Select(dropDown);
            select.selectByValue(value);
            logger.info(value + " was selected in DropDown");
        } catch (Exception e) {
            printErrorAndStopTest(e);
        }
    }

    public void checkElementDisplayed(WebElement element) {
        Assert.assertTrue("Element is not displayed", isElementDisplayed(element));
    }

    public void selectTextInDropDownByUI(WebElement dropDown, String text) {
        try {
            Select select = new Select(dropDown);
            select.selectByVisibleText(text);
            logger.info(text + " was selected in DropDown");
        } catch (Exception e) {
            printErrorAndStopTest(e);
        }
    }

    public void deactivateCheckBox (WebElement element, boolean state) {
        try {
            if (element.isSelected() == state) {
                element.click();
                logger.info("CheckBox was deactivated");
            } else {
                logger.info("CheckBox is already deactivated");
            }
        } catch (Exception e) {
            printErrorAndStopTest(e);
        }
    }

    public void activateCheckBox(WebElement element, boolean state) {
        try {
            if (element.isSelected() != state) {
                element.click();
                logger.info("CheckBox was activated");
            } else {
                logger.info("CheckBox is already activated");
            }
        } catch (Exception e) {
            printErrorAndStopTest(e);
        }
    }

    public void changeCheckBoxState(WebElement element, boolean state) {
        if (state) {
            activateCheckBox(element, true);
        } else {
            deactivateCheckBox(element, false);
        }
    }
}
