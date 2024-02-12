import org.junit.Test;
import pages.WomanProductListPage;


public class WomanProductPageListFiltersTest extends BaseTest {

    @Test
    public final void womanProductPageListFiltersTest() {

        int expectedNumberOfFilters = 8;

        WomanProductListPage womanProductListPage = (WomanProductListPage) pageProvider.getWomanProductListPage().openPage();

        womanProductListPage.checkIsFiltersListItemsVisible()
                .checkNumberOfFilters(expectedNumberOfFilters);

    }

}
