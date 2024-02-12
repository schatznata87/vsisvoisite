package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class ProductPage extends Page {

    private final String productSlug;

    @FindBy(xpath = "//*[contains(@class, 'product-size__btn-desc')]")
    private WebElement productSizeSelect;

    @FindBy(xpath = "//*[contains(@class, 'jst_add_to_cart')]")
    private WebElement addToCartButton;

    public ProductPage(WebDriver webDriver, String productSlug) {

        super(webDriver);

        this.productSlug = productSlug;

    }

    @Override
    protected String getRelativeUrl() {
        return "/store/" + productSlug;
    }

    public ProductPage checkRedirectToProductPage() {

        checkUrlWithPattern();

        return this;

    }

    public boolean hasMultipleSizes() {
        return productSizeSelect.findElement(By.tagName("ul")).findElements(By.tagName("li")).size() > 1;
    }

    public ProductPage addToCart() {

        if (hasMultipleSizes()) {

            clickOnElement(productSizeSelect);

            List<WebElement> productSizes = productSizeSelect.findElement(By.tagName("ul")).findElements(By.tagName("li"));

            clickOnElement(productSizes.get(0));

        }

        clickOnElement(addToCartButton);

        WebElement closeModalButton = webDriver.findElement(By.xpath("//*[contains(@class, 'js-close-modal')]"));

        clickOnElement(closeModalButton);

        logger.info("Product added to cart");

        return this;

    }

    public ProductPage addToCart(String sizeValue) {

        clickOnElement(productSizeSelect);

        List<WebElement> productSizes = productSizeSelect.findElement(By.tagName("ul")).findElements(By.tagName("li"));

        int productSizeIndex = -1;

        for (int i = 0; i < productSizes.size(); i++) {
            if (productSizes.get(i).getText().equals(sizeValue)) {
                productSizeIndex = i;
                break;
            }
        }

        if (productSizeIndex == -1)
            throw new RuntimeException(String.format("No size %s for the product.", sizeValue));

        clickOnElement(productSizes.get(productSizeIndex));

        clickOnElement(addToCartButton);

        WebElement closeModalButton = webDriver.findElement(By.xpath("//*[contains(@class, 'js-close-modal')]"));

        clickOnElement(closeModalButton);

        logger.info("Product added to cart");

        return this;

    }

    public CartPage openCart() {
        return ((CartPage) new CartPage(webDriver).openPage()).checkRedirectToCardPage();
    }

}
