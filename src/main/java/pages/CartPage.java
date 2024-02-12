package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;


public class CartPage extends Page {

    @FindBy(xpath = "//*[contains(@class, 'js_cart_product_box')]/ul")
    private List<WebElement> cartItems;

    private List<String> currentProductNames = null;

    public CartPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public String getRelativeUrl() {
        return "/shop/cart";
    }

    public CartPage checkRedirectToCardPage() {

        checkUrlWithPattern();

        return this;

    }

    public CartPage checkNumberOfCartItems(int expectedNumberOfCartItems) {

        Assert.assertEquals("Number of cart items is not as expected", expectedNumberOfCartItems, cartItems.size());

        logger.info(String.format("Number of cart items is %d", expectedNumberOfCartItems));

        return this;

    }

    public CartPage checkProductName(int productIndex, String expectedProductName) {

        String actualProductName = getProductName(productIndex);

        Assert.assertEquals("Product name is not as expected", expectedProductName, actualProductName);

        logger.info(String.format("Product name is %s", expectedProductName));

        return this;

    }

    public CartPage saveProducts() {

        currentProductNames = new ArrayList<String>();

        for (int i = 0; i < cartItems.size(); i++) {
            currentProductNames.add(getProductName(i));
        }

        return this;

    }

    public String deleteProduct(int productIndex) {

        String productName = getProductName(productIndex);

        clickOnElement(cartItems.get(productIndex).findElement(By.className("icon-close-circle")));

        logger.info(String.format("Product \"%s\" deleted from the cart", getProductName(productIndex)));

        return productName;

    }

    public CartPage checkProductsAfterDelete(List<String> deletedProducts) {

        List<String> remainingProducts = new ArrayList<String>();

        int numberOfRemainingProducts = remainingProducts.size();

        for (int i = 0; i < cartItems.size(); i++) {
            remainingProducts.add(getProductName(i));
        }

        remainingProducts.addAll(deletedProducts);

        Assert.assertEquals(
                String.format(
                        "Number of products before deleting: %d. Number of deleted products: %d. Number of remaining products: %d",
                        currentProductNames.size(), deletedProducts.size(), numberOfRemainingProducts
                ),
                currentProductNames.size(), remainingProducts.size()
        );

        for (String currentProductName : currentProductNames) {

            Assert.assertTrue(
                    String.format("There is no product \"%s\" in the list of remaining or deleted products", currentProductName),
                    remainingProducts.contains(currentProductName)
            );

            remainingProducts.remove(remainingProducts.indexOf(currentProductName));

        }

        Assert.assertTrue(remainingProducts.isEmpty());

        logger.info("Products are correctly deleted from the cart");

        return this;

    }

    private String getProductName(int productIndex) {

        return cartItems.get(productIndex)
                .findElement(By.className("content-right__info-top"))
                .findElement(By.className("content-right__info-text"))
                .getText();

    }

}
