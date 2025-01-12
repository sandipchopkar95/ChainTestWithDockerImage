package reporting;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import java.time.Duration;

@Listeners(ChainTestListener.class)
public class RootTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void attachScreenShot(final ITestResult result) {
        try {
            final byte[] bytes = new byte[]{};
            final String qualifiedName = result.getMethod().getQualifiedName();
            ChainPluginService.getInstance().embed(qualifiedName, bytes, "image/png");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod(dependsOnMethods = "attachScreenShot")
    public void tearDown() {
        driver.quit();
    }

}


