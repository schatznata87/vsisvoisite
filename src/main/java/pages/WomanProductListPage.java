package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class WomanProductListPage extends Page {

    @FindBy(xpath = "//*[contains(@class, 'products__items')]/div[1]/div")
    private List<WebElement> productList;

    @FindBy(xpath = "//*[@id=\"categoryFormFiltr\"]/ul")
    private List<WebElement> categoriesAndFiltersList;

    public WomanProductListPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getRelativeUrl() {
        return "/zhinkam";
    }

    public String getProductName(int productIndex) {

        if (productIndex >= 0 && productIndex < productList.size()) {
            WebElement listItem = productList.get(productIndex);
            WebElement productTitle = listItem.findElement(By.className("card__title"));
            return productTitle.getText();
        } else {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }

    }

    public String getProductSlug(int productIndex) {

        if (productIndex >= 0 && productIndex < productList.size()) {

            WebElement listItem = productList.get(productIndex);
            WebElement productLink = listItem.findElement(By.className("card__link"));

            String[] relativeUrlParts = productLink.getAttribute("href").split("/");

            return relativeUrlParts[relativeUrlParts.length - 1];

        } else {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }

    }

    public ProductPage clickOnProduct(int productIndex) {

        String productSlug = getProductSlug(productIndex);

        logger.info(String.format("Product '%s' was opened", getProductName(productIndex)));

        clickOnElement(productList.get(productIndex));

        return new ProductPage(webDriver, productSlug);

    }

    public WomanProductListPage searchProduct(String searchQuery) {

        header.clickOnSearch();

        WebElement searchForm = webDriver.findElement(By.id("w1"));

        WebElement searchInput = searchForm.findElement(By.tagName("input"));

        enterTextIntoInput(searchInput, searchQuery);

        searchInput.sendKeys(Keys.ENTER);

        webDriver.get(webDriver.getCurrentUrl());

        logger.info(String.format("Found products for search query \"%s\"", searchQuery));

        return this;

    }

    public WomanProductListPage checkProductSearch(String searchQuery) {

        String productName;

        for (int i = 0; i < productList.size(); i++) {

            productName = getProductName(i);

            Assert.assertTrue(
                    String.format("Product name \"%s\" does not contain \"%s\"", productName, searchQuery),
                    productName.toLowerCase().contains(searchQuery.toLowerCase())
            );

        }

        logger.info(String.format("All products fit the search query \"%s\"", searchQuery));

        return this;

    }

    private List<WebElement> getFiltersList() {
        return categoriesAndFiltersList.subList(1, categoriesAndFiltersList.size());
    }

    private boolean isFiltersListItemsVisible() {

        boolean allVisible = true;

        for (WebElement filter : getFiltersList()) {
            if (!filter.isDisplayed()) {
                allVisible = false;
                break;
            }
        }
        return allVisible;

    }

    public WomanProductListPage checkIsFiltersListItemsVisible() {

        Assert.assertTrue("Not all filters are visible", isFiltersListItemsVisible());
        logger.info("All filters are visible");

        return this;

    }

    public WomanProductListPage checkNumberOfFilters(int expectedNumberOfFilters) {

        Assert.assertEquals("Number of filters is not as expected", expectedNumberOfFilters, getFiltersList().size());
        logger.info(String.format("Number of filters is %d", expectedNumberOfFilters));

        return this;

    }

    public String addProductToFavoriteList(int productIndex) {

        clickOnElement(productList.get(productIndex).findElement(By.className("js-favorite-button")));

        String productName = getProductName(productIndex);

        logger.info(String.format("Product \"%s\" added to favorite list", productName));

        return productName;

    }

    public FavoriteListPage openFavoriteListPage() {
        return ((FavoriteListPage) new FavoriteListPage(webDriver).openPage()).checkRedirectToFavoriteListPage();
    }

}
