package pages.elements;

import pages.CommonActionWithElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class Header extends CommonActionWithElements {

    @FindBy(xpath = "//*[contains(@class, 'js_search-open')]")
    private WebElement search;

    @FindBy(xpath = "//*[contains(@class, 'header__link-cart')]")
    private WebElement cart;

    public Header(WebDriver webDriver) {
        super(webDriver);
    }

    public void checkCartVisible() {
        checkElementDisplayed(cart);
    }

    public boolean isCartVisible() {
        return isElementDisplayed(cart);
    }

    public void clickOnCart() {
        clickOnElement(cart);
    }

    public void clickOnSearch() {
        clickOnElement(search);
    }

}
