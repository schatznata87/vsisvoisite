package pages;

import org.openqa.selenium.WebDriver;


public class PageProvider {

    private WebDriver webDriver;

    public  PageProvider (WebDriver webDriver) {this.webDriver = webDriver;}

    public WomanProductListPage getWomanProductListPage() {
        return new WomanProductListPage(webDriver);
    }

    public ProductPage getProductPage(String productSlug) {
        return new ProductPage(webDriver, productSlug);
    }

    public CartPage getCartPage() {
        return new CartPage(webDriver);
    }

    public FavoriteListPage getFavoriteListPage() {
        return new FavoriteListPage(webDriver);
    }

}
