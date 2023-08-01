package anhtester.com.hubpay.ops.pages;

import anhtester.com.common.CommonPage;
import anhtester.com.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class TransactionsPage extends CommonPage {

	private By settlementreceivedBtn=By.xpath("//button[text()='Settlement Received']");
	private By settleBtn=By.xpath("//button[text()='Settle']");
	private By userAccountID=By.xpath("//a[text()='User Account ID']");
	private By downloadBtn=By.xpath("//label[title='Download']");
	private By emailInputFld=By.cssSelector("input[name='emailAddress']");
	private By ibanInputFld=By.cssSelector("input[name='iban']");
	private By fullNameInputFld=By.cssSelector("input[name='fullName']");
	private By searchBtn=By.xpath("//button[text()='Search']");
	private By flagFld=By.xpath("//div[@class='selected-flag']");
	private By searchFlagInputFld=By.xpath("//input[@class='search-box']");
	@FindBy(xpath = "//img")
	private List<WebElement> images;
	private By profileLbl=By.xpath("//h5[text()='Profile']");
	public TransactionsPage() {

	}
	
	public void verifyFieldValue(String fieldLabel,String fieldValue){
		WebUI.verifyElementVisible(By.xpath("//p[contains(@class,'MuiTypography-root')][text()='"+fieldLabel+"']"));
		WebUI.verifyElementText(By.xpath("//p[contains(@class,'MuiTypography-root')][text()='"+fieldLabel+"']/following-sibling::div/p"),fieldValue);
	}
	public void verifyFieldContainsValue(String fieldLabel,String fieldValue){
		WebUI.verifyElementVisible(By.xpath("//p[contains(@class,'MuiTypography-root')][text()='"+fieldLabel+"']"));
		String actValue=WebUI.getTextElement(By.xpath("//p[contains(@class,'MuiTypography-root')][text()='"+fieldLabel+"']/following-sibling::div/p"));
		WebUI.verifyContains(actValue,fieldValue,"Incorrect value: Actual-'"+actValue+"' Expected-'"+fieldValue+"'");
	}
    public void verifyStatusInTransactionDetailsView(String status){
		verifyFieldValue("Order Status",status);
	}
	public void verifyExchangeTransactionDetails(Map<String,String> trxnDetails) throws ParseException {
		String fieldValue="";
//		verifyFieldValue("Order Status",fieldValue);
		verifyFieldValue("Reference",trxnDetails.get("transactionReference"));
		Date dateEEE=new SimpleDateFormat("EEEE, dd MMMM yyyy").parse(trxnDetails.get("createdAt"));
		Date dateD=new SimpleDateFormat("d MMMM yyyy").parse(trxnDetails.get("formatted_settlementDate"));
		SimpleDateFormat dateDD=new SimpleDateFormat("dd/MM/yyyy");
		verifyFieldContainsValue("Created At",(dateDD.format(dateEEE)).toString());
		verifyFieldContainsValue("Ordered At",(dateDD.format(dateEEE)).toString());
		verifyFieldValue("Order Settled At",trxnDetails.get("orderSettledAt"));
		verifyFieldValue("Rejected At",trxnDetails.get("rejectedAt"));
		verifyFieldContainsValue("Settlment Date",(dateDD.format(dateD)).toString());
		verifyFieldValue("Completed At",trxnDetails.get("completedAt"));
		/*###Amount & Fees###*/
		verifyFieldValue("Total Funds Due",trxnDetails.get("totalFundsDue"));
		String arr[]=trxnDetails.get("liveRate").split(" ");
		verifyFieldValue("Offer Rate",arr[1]+"/"+arr[4]+" "+arr[3]);
		verifyFieldValue("Send Amount",trxnDetails.get("sellCurrencyCode")+" "+trxnDetails.get("sellAmount"));
		verifyFieldValue("Receive Amount",trxnDetails.get("buyCurrencyCode")+" "+trxnDetails.get("buyAmount"));
	}
	public void verifyPaymentsTransactionDetails(Map<String,Map<String,String>> trxnDetails) throws ParseException {
		String fieldValue="";
//		verifyFieldValue("Order Status",fieldValue);
//		verifyFieldValue("Reference",trxnDetails.get("transactionReference"));
//		Date dateEEE=new SimpleDateFormat("EEEE, dd MMMM yyyy").parse(trxnDetails.get("createdAt"));
//		Date dateD=new SimpleDateFormat("d MMMM yyyy").parse(trxnDetails.get("formatted_settlementDate"));
//		SimpleDateFormat dateDD=new SimpleDateFormat("dd/MM/yyyy");
//		verifyFieldContainsValue("Created At",(dateDD.format(dateEEE)).toString());
//		verifyFieldContainsValue("Ordered At",(dateDD.format(dateEEE)).toString());
//		verifyFieldValue("Order Settled At",trxnDetails.get("orderSettledAt"));
//		verifyFieldValue("Rejected At",trxnDetails.get("rejectedAt"));
//		verifyFieldContainsValue("Settlment Date",(dateDD.format(dateD)).toString());
//		verifyFieldValue("Completed At",trxnDetails.get("completedAt"));
//		/*###Amount & Fees###*/
//		verifyFieldValue("Total Funds Due",trxnDetails.get("totalFundsDue"));
//		String arr[]=trxnDetails.get("liveRate").split(" ");
//		verifyFieldValue("Offer Rate",arr[1]+"/"+arr[4]+" "+arr[3]);
//		verifyFieldValue("Send Amount",trxnDetails.get("sellCurrencyCode")+" "+trxnDetails.get("sellAmount"));
//		verifyFieldValue("Receive Amount",trxnDetails.get("buyCurrencyCode")+" "+trxnDetails.get("buyAmount"));
	}
	public void verifyTransactionInTabGrid(Map<String, String> trxnDetails){
//		clickTab(tab);
		String trnreference="AD-K1K5-224C";
		WebUI.verifyElementVisible(getTransactionInTabGrid(trnreference));
	}
	public void openTransactionDetailsViewFromTabPage(String trxnType,String trnreference){
//		clickTab(tab);
//		String trnreference="AD-K1K5-224C";
		WebUI.verifyElementVisible(getTransactionInTabGrid(trnreference),20);
		WebUI.clickElement(getTransactionInTabGrid(trnreference));
		By trxnDetailsHdr=By.xpath("//h2[text()='"+trxnType+"']");
		WebUI.waitForElementVisible(trxnDetailsHdr,10000);
		WebUI.verifyElementVisible(trxnDetailsHdr,20,trxnType+" Transaction details view is not displayed");
	}
	public By getTransactionInTabGrid(String trxnreference){
		By trxnRecord=By.xpath("//div[@data-field='reference'][.='"+trxnreference+"']/parent::div[@role='row']");
		return trxnRecord;
	}
	public void verifyTransactionStatusInTabGrid(String trxnreference,String status){
		By trxnRecord=getTransactionInTabGrid(trxnreference);
		WebElement trxnStatus=WebUI.getWebElement(trxnRecord).findElement(By.xpath("//div[@data-field='status']"));
		WebUI.verifyTrue(trxnStatus.getText().contentEquals(status.toUpperCase()),"Status mismatch: Expected:'"+status+"' & Actual:'"+trxnStatus.getText()+"'");
	}
	public void verifyTransactionSearchInOpsPortal(String trxnreference,String type,String status){
		//yet to work on
	}
	public void verifyButtonOnTransactionDetailsView(String button,String status){
		switch(button.toUpperCase()){
			case "SETTLEMENT RECEIVED":
				boolean flag=false;
				if(status.equalsIgnoreCase("Available")){
					WebUI.verifyElementVisible(settlementreceivedBtn,button+ " is not available");
				}else if(status.equalsIgnoreCase("Not Available")){
					WebUI.verifyTrue(!WebUI.verifyElementVisible(settlementreceivedBtn,button+ " is available"));
				}
				break;
			case "SETTLE":
				if(status.equalsIgnoreCase("Available")){
					WebUI.verifyElementVisible(settleBtn,button+ " is not available");
				}else if(status.equalsIgnoreCase("Not Available")){
					WebUI.verifyTrue(!WebUI.verifyElementVisible(settleBtn,button+ " is available"));
				}
				break;
		}
	}
	public void clickButtonOnTransactionDetailsView(String button) {
		switch (button.toUpperCase()) {
			case "SETTLEMENT RECEIVED":
				WebUI.clickElement(settlementreceivedBtn);
				break;
			case "SETTLE":
				WebUI.clickElement(settleBtn);
				break;
			case "USER ACCOUNT ID":
				WebUI.clickElement(userAccountID);
				break;
			default:
				WebUI.verifyTrue(false, button + " button is not Available");
		}
	}
}
