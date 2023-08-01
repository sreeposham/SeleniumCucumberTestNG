package anhtester.com.projects.website.cms.stepdefinitions;

import anhtester.com.hooks.TestContext;
import anhtester.com.keywords.WebUI;
import anhtester.com.projects.website.cms.pages.CommonPageCMS;
import anhtester.com.projects.website.cms.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DashboardSteps {

    LoginPage loginPage;
    CommonPageCMS commonPageCMS;

    public DashboardSteps(TestContext testContext) {
//        loginPage = testContext.getLoginPage();
//        testContext.
        commonPageCMS = testContext.getCommonPage();
    }

    @Then("user is redirected to the Dashboard page")
    public void userIsRedirectedToTheDashboardPage() {
        commonPageCMS.verifyDashboardPageDisplays();
    }

}
