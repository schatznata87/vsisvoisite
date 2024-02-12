import org.junit.Test;
import pages.WomanProductListPage;


public class AddToCartTest extends BaseTest {

    @Test
    public void addToCartTest() {

        int productIndex = 1;
        String productSize = "S";

        WomanProductListPage womanProductListPage = (WomanProductListPage) pageProvider.getWomanProductListPage().openPage();

        String productName = womanProductListPage.getProductName(productIndex);

        logger.info("Name of product #" + productIndex + ": " + productName);

        womanProductListPage.clickOnProduct(productIndex)
                .checkRedirectToProductPage()
                .addToCart(productSize)
                .openCart()
                .checkNumberOfCartItems(1) // There should be only one product in the cart
                .checkProductName(0, productName); // Checking the name of first product (Index 0)

    }

}
