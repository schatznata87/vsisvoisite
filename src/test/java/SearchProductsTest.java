import org.junit.Test;
import pages.WomanProductListPage;


public class SearchProductsTest extends BaseTest {

    @Test
    public void searchProductsTest() {

        String searchQuery = "куртка";

        WomanProductListPage womanProductListPage = (WomanProductListPage)
                pageProvider.getWomanProductListPage().openPage();

        womanProductListPage.searchProduct(searchQuery).checkProductSearch(searchQuery);

    }

}
