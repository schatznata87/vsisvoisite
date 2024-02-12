package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;


public class FavoriteListPage extends Page {

    @FindBy(xpath = "//*[contains(@class, 'favorites__list')]/li")
    private List<WebElement> favoritesList;

    public FavoriteListPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public String getRelativeUrl() {
        return "/shop/favorite";
    }

    public FavoriteListPage checkRedirectToFavoriteListPage() {

        checkUrlWithPattern();

        return this;

    }

    private String getProductName(int productIndex) {
        return favoritesList.get(productIndex).findElement(By.className("favorite-card__info-text")).getText();
    }

    public FavoriteListPage checkFavoritesList(List<String> expectedFavoritesList) {

        List<String> actualFavoritesList = new ArrayList<String>();

        for (int i = 0; i < favoritesList.size(); i++) {
            actualFavoritesList.add(getProductName(i));
        }

        Assert.assertTrue("Not all products added to favorite list", actualFavoritesList.containsAll(expectedFavoritesList));

        actualFavoritesList.removeAll(expectedFavoritesList);

        Assert.assertTrue("Extra products added to favorite list", actualFavoritesList.isEmpty());

        logger.info("All products are correctly added to favorite list");

        return this;

    }

}
