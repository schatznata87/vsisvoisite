import config.ConfigProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import pages.PageProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;


public class BaseTest {

    WebDriver webDriver;
    protected Logger logger = Logger.getLogger(getClass());

    protected PageProvider pageProvider;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() {

        logger.info("---------" + testName.getMethodName() + " was started ----------");
        webDriver = initDriver();
        webDriver.manage().window().maximize();//maximize window
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigProvider.configProperties.IMPLICIT_WAIT_TIME())); //wait 5 sec
        logger.info("Browser was opened");

        pageProvider = new PageProvider(webDriver);

    }

    @After
    public void tearDown() {

        webDriver.quit();
        logger.info("Browser was closed");
        logger.info("----" + testName.getMethodName() + " was ended----");

    }

    private WebDriver initDriver() {

        String browser = System.getProperty("browser");

        if ((browser == null) || "chrome".equalsIgnoreCase(browser)) {
            ChromeOptions ops = new ChromeOptions();
            ops.addArguments("--remote-allow-origins=*");
            WebDriverManager.chromedriver().setup();
            webDriver = new ChromeDriver(ops);
        } else if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            webDriver = new FirefoxDriver();
        } else if ("safari".equalsIgnoreCase(browser)) {
            WebDriverManager.safaridriver().setup();
            webDriver = new SafariDriver();
        } else if ("edge".equalsIgnoreCase(browser)) {
            WebDriverManager.edgedriver().setup();
            webDriver = new EdgeDriver();
        } else if ("ie".equalsIgnoreCase(browser)) {
            WebDriverManager.iedriver().arch32().setup();
            return new InternetExplorerDriver();
        } else if ("remote".equals(browser)) {
            WebDriverManager.chromedriver().setup();
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setBrowserName("chrome");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.merge(cap);
            try {
                webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return webDriver;

    }

}
