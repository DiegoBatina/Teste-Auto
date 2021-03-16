package support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;




public class Setup {
    public static WebDriver driver;
    private static final long DEFAULT_WAIT_TIMEOUT = 40;
    public static WebDriverWait wait;
    private String parType;


    public void start(String parBrowser) {
        String title;
        try {
            title = driver.getTitle();
        } catch (Exception e) {
            title = "ERROR";
        }
        if (title.equals("ERROR")) {
            switch (parBrowser) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions options;
                    options = new FirefoxOptions();
                    options.addPreference(FirefoxDriver.MARIONETTE, true);
                    driver = new FirefoxDriver(options);
                    driver.manage().window().maximize();
                    break;
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    driver = new ChromeDriver(chromeOptions);
                    driver.manage().window().maximize();
                    break;
                case "edge":
                    System.setProperty("webdriver.edge.driver", "C:\\Users\\dbatina\\OneDrive - Capgemini\\Desktop\\Drivers\\msedgedriver.exe");
                    //WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    driver = new EdgeDriver(edgeOptions);
                    driver.manage().window().maximize();
                    break;
                case "opera":
                    WebDriverManager.operadriver().setup();
                    OperaOptions operaOptions = new OperaOptions();
                    driver = new OperaDriver(operaOptions);
                    driver.manage().window().maximize();
                default:
                    break;

            }
        }
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        int size = driver.manage().window().getSize().getWidth();

        if (size < 1400) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }

        wait = new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT);

    }

    private String getAttributeType(String... parType) {
        String type;
        if (parType.length == 0) {
            type = "id";
        } else {
            type = parType[0];
        }
        return type;
    }

    private By getLocatorBy(String parValue, String... parType) {
        final String selector = getAttributeType(parType);
        switch (selector) {
            case "id":
                return By.id(parValue);
            case "name":
                return By.name(parValue);
            case "css":
                return By.cssSelector(parValue);
            case "xpath":
                return By.xpath(parValue);
            case "link":
                return By.linkText(parValue);
            case "class":
                return By.className(parValue);
            case "tag":
                return By.tagName(parValue);
            default:
                return By.id(parValue);
        }
    }

    public WebElement findElem(String parValue, String... parType) {
        final By locator = getLocatorBy(parValue, parType);
        WebElement element;
        try {
            element = driver.findElement(locator);
        } catch (NoSuchElementException e) {
            element = null;
        }
        return element;
    }

    public void click(String parValue, String... parType) {
        final WebElement element = findElem(parValue, parType);
        element.click();
    }

    public void openURL(String parURL) {
        driver.get(parURL);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void quit() {
        driver.quit();
    }

    public void sendKeys(String parText, String parValue, String parType) {
        final WebElement element = findElem(parValue, parType);

        element.clear();
        element.sendKeys(parText);
    }

    public void scrollTo(String parValue, String... parType) {
        final WebElement element = findElem(parValue, parType);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("");


    }
}

