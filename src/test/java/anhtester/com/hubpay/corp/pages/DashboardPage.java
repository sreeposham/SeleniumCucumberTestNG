package anhtester.com.hubpay.corp.pages;

import anhtester.com.common.CommonPage;
import anhtester.com.keywords.WebUI;
import net.bytebuddy.build.Plugin;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DashboardPage extends CommonPage {
	private By sendBtn=By.xpath("//a[text()='Send']");
	private By dashboardMenu=By.xpath("//a[text()='Dashboard']");
	private By sendInternationalBtn=By.xpath("//a[text()='Send international']");
	private By exchangeBtn=By.xpath("//a[text()='Exchange']");
	private By addFundsBtn=By.xpath("//button[text()='Add funds']");
	private By withdrawBtn=By.xpath("//button[text()='Withdraw']");
	private By exchangeHdr = By.xpath("//h1[text()='Exchange']");
	private By paymentsHdr = By.xpath("//h1[text()='Payments']");
	private By dashboardHdr = By.xpath("//h1[text()='Dashboard']");
	private By internaltionalPaymentsHdr = By.xpath("//h1[text()='International payment']");
	private By addFundsHdr = By.xpath("//h2[text()='Add funds']");
	private By withdrawFundsHdr = By.xpath("//h2[text()='Withdraw funds']");
	private By transactionsTabLink=By.xpath("//button[text()='Transactions']");
	private By walletsTabLink=By.xpath("//button[text()='Wallets']");
	private By gbpWalletCard=By.xpath("//p[text()='British Pound Sterling']/parent::div");
	private By dashbrdTitle=By.xpath("//h1[text()='Dashboard']");
	private By leftmenu_signout=By.xpath("//li[text()=' Sign out']");
	private By corp_signoutBtn = By.xpath("//li[contains(.,'Sign out')]");
	private By transactions_searchbox=By.xpath("//input[@id='search']");
	public DashboardPage() {

	}

	public void verifyPageLaunch(String page) {

		switch (page.toUpperCase()) {
			case "DASHBOARD":
				WebUI.clickElement(dashboardMenu);
				WebUI.verifyElementVisible(dashboardHdr,30,"Dashboard page is not displayed");
				break;
			case "PAYMENTS":
				WebUI.clickElement(sendBtn);
				WebUI.verifyElementVisible(paymentsHdr,30,"Payments page is not displayed");
				break;
			case "INTERNATIONAL PAYMENTS":
				WebUI.clickElement(sendInternationalBtn);
				WebUI.verifyElementVisible(internaltionalPaymentsHdr,30,"International Payments page is not displayed");
				break;
			case "EXCHANGE":
				WebUI.clickElement(exchangeBtn);
				WebUI.verifyElementVisible(exchangeHdr,30,"Exchange page is not displayed");
				break;
			case "ADD FUNDS":
				WebUI.clickElement(addFundsBtn);
				WebUI.verifyElementVisible(addFundsHdr,30,"Add Funds modal is not displayed");
				break;
			case "WITHDRAW":
				WebUI.clickElement(withdrawBtn);
				WebUI.verifyElementVisible(withdrawFundsHdr,30,"Withdraw funds modal is not displayed");
				break;
			default:
				WebUI.verifyTrue(false, "Incorrect button provided");
		}
	}
	public void verifyDashboardIsDisplayed() {
			WebUI.waitForElementVisible(dashbrdTitle);
			WebUI.verifyElementVisible(dashbrdTitle,"Dashboard page is not displayed");
	}

	public String getWalletBalanceOnDashboardPage(String corridor) {
		By by=By.xpath("//p[contains(text(),'"+corridor+"')]");
		return WebUI.getTextElement(by);
	}
/*######################TRANSACTIONS TAB#########################*/

	By title_TransactionDetail=By.xpath("//h2[contains(@id,'radix-')]/h1");

	By title_TransactionStatus=By.xpath("//h2[contains(@id,'radix-')]/div");

	By title_CreatedDate=By.xpath("//p[contains(@id,'radix-')]");


	By iconClose=By.xpath("//span[text()='Close']/parent::button");

	By btnDownload=By.xpath("//button[text()='Download']");

	public void openTransactionDetailsPage(Map<String,Map<String,String>> exchange_details, String status) {
		String amount=exchange_details.get("COMMON").get("toCurrencyCode")+" "+exchange_details.get("EXCHANGE").get("toAmount");
		By record=By.xpath("//td[.='"+exchange_details.get("EXCHANGE").get("transactionReference")+"']/parent::tr");
		WebUI.verifyTrue(WebUI.verifyElementVisible(record),"Transaction is not available in the table:"+exchange_details.get("EXCHANGE").get("transactionReference"));
		List<WebElement> lsTableCols=WebUI.getWebElements(By.xpath("//td[.='"+exchange_details.get("EXCHANGE").get("transactionReference")+"']/parent::tr/td[contains(@class,'text-sm')]"));
		System.out.println(lsTableCols.size());
		WebUI.verifyTrue(lsTableCols.get(2).getText().contentEquals(amount));
		WebUI.verifyTrue(lsTableCols.get(3).getText().contentEquals(exchange_details.get("EXCHANGE").get("type")));
		WebUI.verifyTrue(lsTableCols.get(4).getText().equalsIgnoreCase(status));
		if(exchange_details.get("EXCHANGE").get("type").equalsIgnoreCase("Foreign Exchange")){
			WebUI.verifyTrue(lsTableCols.get(5).getText().contentEquals("-"));
		} else if (exchange_details.get("EXCHANGE").get("type").equalsIgnoreCase("Payment")) {
			WebUI.verifyTrue(lsTableCols.get(5).getText().contentEquals("Recipient name"));
		}
		WebUI.clickElement(record);
		WebUI.verifyElementVisible(title_TransactionDetail,"Transaction details page is not opened");
	}
	public void verifyAvailableFundsInCorridor(String corridor,String funds){
		By availableFunds=By.xpath("//p[text()='Available funds']/following-sibling::p[contains(text(),'"+corridor+"')]");
		String formatAct="";
		String formatFunds="";
		if(funds.contains(corridor)){
			if(funds.contains(",")){
				formatAct=WebUI.getTextElement(availableFunds).trim();
			}
			else {
				formatAct=WebUI.getTextElement(availableFunds).trim().replace(",","");
			}
		}else{
			if(funds.contains(",")){
				formatAct=WebUI.getTextElement(availableFunds).split(" ")[1].trim();
			}else{
				formatAct=WebUI.getTextElement(availableFunds).split(" ")[1].trim().replace(",","");

			}
		}
		WebUI.verifyTrue(funds.contentEquals(formatAct),"Funds mismatch: Actual:'"+formatAct+" Expected:'"+funds+"'");
	}
	public void verifyFieldValue(String fldName,String value) {
		By by=By.xpath("//span[text()='"+fldName+"']/following-sibling::div");
		String act="";
		String exp="";
		if(fldName.contentEquals("Exchange rate")){
			act=WebUI.getAttributeElement(by,"innerText").trim();
			String[] rate=value.split(" ");
			//1 AUD = 2.46706 AED
			exp=rate[1].trim()+" "+rate[0].trim()+" = "+rate[4].trim()+" "+rate[3].trim();
		}else {
			act=WebUI.getTextElement(by).trim();
			exp=value;
		}

		WebUI.verifyTrue(act.contentEquals(exp),"Actual value:'"+act+" & Expected value:'"+exp+"'");
	}

	public void verifyOnlyExchangeTransactionDetails(Map<String,Map<String,String>> exchange_details) {
		String settlementDt="";
		By creationDate=By.xpath("//h1[text()='Exchange receipt']/parent::h2/following-sibling::p");
//		String expCreateDt=(new SimpleDateFormat("EEEE dd MMMM yyyy").format(new Date())).toString();
		WebUI.verifyElementTextContains(creationDate,exchange_details.get("EXCHANGE").get("createdAt"));
		String amount=exchange_details.get("fromCurrencyCode")+" "+exchange_details.get("EXCHANGE").get("fromAmount");
		try {
			Date formatDt=new SimpleDateFormat("d MMMM yyyy").parse(exchange_details.get("COMMON").get("formatted_settlementDate"));
			System.out.println(settlementDt);
			settlementDt=(new SimpleDateFormat("dd/MM/yyyy").format(formatDt)).toString();
			System.out.println(settlementDt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verifyFieldValue("Settlement due date",settlementDt);
		verifyFieldValue("Total funds due",amount);
		verifyFieldValue("From",amount);
		amount=exchange_details.get("COMMON").get("toCurrencyCode")+" "+exchange_details.get("EXCHANGE").get("toAmount");
		verifyFieldValue("To",amount);
		verifyFieldValue("Exchange rate",exchange_details.get("COMMON").get("liveRate"));
		verifyFieldValue("Transaction reference",exchange_details.get("EXCHANGE").get("transactionReference"));
		WebUI.verifyElementVisible(btnDownload,"Download button is not available");
	}

	public void clickDownloadBtn() {
		WebUI.clickElement(btnDownload);
	}
	public void clickCloseBtn() {
		WebUI.clickElement(iconClose);
	}

	public void verifyStatusTransactionDetails(String stepStatus) {
			WebUI.waitForElementVisible(title_TransactionStatus);
		String act=WebUI.getTextElement(title_TransactionStatus).trim();
		WebUI.verifyTrue(act.equalsIgnoreCase(stepStatus.trim()),"Actual value:'"+act+"' & Expected value:'"+stepStatus+"'");
	}
	public void navigateToTabPageOnDashboard(String tab) {
//		WebUI.sleep(60);
		switch(tab.toUpperCase()){
			case "TRANSACTIONS":
				WebUI.waitForElementClickable(transactionsTabLink,5000);
				WebUI.clickElement(transactionsTabLink);
				break;
			case "WALLETS":
				WebUI.waitForElementClickable(walletsTabLink,5000);
				WebUI.clickElement(walletsTabLink);
		}
	}

	public void searchAndVerifyPaymentInTransactionGrid(String type,String code,Map<String, String> payment_data_map) {
		WebUI.setText(transactions_searchbox,payment_data_map.get("recipientName"));
		WebUI.smartWait();
		List<WebElement> payment_data;
		payment_data = WebUI.getWebElements(By.xpath("//tboday/tr[1]/td"));

	}
}
