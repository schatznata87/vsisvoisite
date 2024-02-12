package pages;

import config.ConfigProvider;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.elements.Header;


abstract public class Page extends CommonActionWithElements {

    private final String baseUrl = ConfigProvider.configProperties.BASE_URL();

    protected final Header header = new Header(webDriver);

    public Page(WebDriver webDriver) {
        super(webDriver);
    }

    protected void checkCurrentUrl(String relativeUrl) {
        Assert.assertEquals("Url is not expected", baseUrl + relativeUrl, webDriver.getCurrentUrl());
    }

    abstract String getRelativeUrl();

    public void checkUrl() {
        Assert.assertEquals("Invalid page",
                baseUrl + getRelativeUrl(),
                webDriver.getCurrentUrl());
    }

    protected void checkUrlWithPattern() {
        Assert.assertTrue("Invalid page \n" +
                        "Expected: " + baseUrl + getRelativeUrl() +"\n" +
                        "Actual: " + webDriver.getCurrentUrl(),
                webDriver.getCurrentUrl().matches(baseUrl + getRelativeUrl()));
    }

    public Page openPage() {

        String relativeUrl = getRelativeUrl();

        try {
            webDriver.get(baseUrl + relativeUrl);
            logger.info("Page " + relativeUrl + " was opened");
        } catch (Exception e) {
            logger.error("Can not open " + relativeUrl);
            Assert.fail("Can not open " + relativeUrl);
        }

        return  this;

    }

}
