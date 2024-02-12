import org.junit.Test;
import pages.WomanProductListPage;

import java.util.ArrayList;
import java.util.List;


public class AddToFavoriteListTest extends BaseTest {

    @Test
    public void addToFavoriteListTest() {

        int numberOfProducts = 5;

        WomanProductListPage womanProductListPage = (WomanProductListPage)
                pageProvider.getWomanProductListPage().openPage();

        List<String> addedProducts = new ArrayList<String>();

        for (int i = 0; i < numberOfProducts; i++)
            addedProducts.add(womanProductListPage.addProductToFavoriteList(i));

        womanProductListPage.openFavoriteListPage().checkFavoritesList(addedProducts);

    }

}
