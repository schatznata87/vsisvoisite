import org.junit.Test;
import pages.CartPage;
import pages.ProductPage;
import pages.WomanProductListPage;

import java.util.ArrayList;
import java.util.List;


public class DeleteProductFromCartTest extends BaseTest {

    @Test
    public void deleteProductFromCartTest() {

        int numberOfProductsToAdd = 3;

        List<String> deletedProducts = new ArrayList<String>();

        WomanProductListPage womanProductListPage = pageProvider.getWomanProductListPage();

        ProductPage productPage = null;

        for (int i = 0; i < numberOfProductsToAdd; i++) {

            productPage = ((WomanProductListPage) womanProductListPage.openPage())
                    .clickOnProduct(i)
                    .checkRedirectToProductPage()
                    .addToCart();

        }

        CartPage cartPage = productPage.openCart();

        deletedProducts.add(
                cartPage.saveProducts()
                    .checkNumberOfCartItems(numberOfProductsToAdd)
                    .deleteProduct(0)
        );

        ((CartPage) cartPage.openPage())
                .checkNumberOfCartItems(numberOfProductsToAdd - 1)
                .checkProductsAfterDelete(deletedProducts);

    }

}
