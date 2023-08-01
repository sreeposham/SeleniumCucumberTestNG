package anhtester.com.runners;

import anhtester.com.hooks.CucumberListener;
import anhtester.com.utils.EmailSendUtils;
import anhtester.com.utils.ZipUtils;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"anhtester.com",
                "anhtester.com.hooks"},
        plugin = {"anhtester.com.hooks.CucumberListener",
                "pretty",
                "html:target/cucumber-reports/SigninCRMTestRunner.html",
                "json:target/cucumber-reports/SigninCRMTestRunner.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
        , monochrome = true,
        tags = "@exchange and @aud"
)

public class OpsTestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("================ AFTER SUITE ================");
        ZipUtils.zipReportFolder();
        EmailSendUtils.sendEmail(CucumberListener.count_totalTCs
                , CucumberListener.count_passedTCs
                , CucumberListener.count_failedTCs
                , CucumberListener.count_skippedTCs);
    }
}
