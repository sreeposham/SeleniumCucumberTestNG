package anhtester.com.hubpay.corp.pages;

import anhtester.com.common.CommonPage;
import anhtester.com.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PaymentsPage extends CommonPage {
	private By sendBtn=By.xpath("//a[text()='Send']");
	private By sendInternationalBtn=By.xpath("//a[text()='Send international']");
	private By exchangeBtn=By.xpath("//a[text()='Exchange']");
	private By addFundsBtn=By.xpath("//button[text()='Add funds']");
	private By withdrawBtn=By.xpath("//button[text()='Withdraw']");
	private By exchangeHdr = By.xpath("//h1[text()='Exchange']");
	private By paymentsHdr = By.xpath("//h1[text()='Payments']");
	private By internaltionalPaymentsHdr = By.xpath("//h1[text()='International payment']");
	private By addFundsHdr = By.xpath("//h2[text()='Add funds']");
	private By withdrawFundsHdr = By.xpath("//h2[text()='Withdraw funds']");
	private By transactionsTabLink=By.xpath("//button[text()='Transactions']");
	private By walletsTabLink=By.xpath("//button[text()='Wallets']");
	private By gbpWalletCard=By.xpath("//p[text()='British Pound Sterling']/parent::div");

	private By leftmenu_signout=By.xpath("//li[text()=' Sign out']");
	private By corp_signoutBtn = By.xpath("//li[contains(.,'Sign out')]");

	private By paymentsTitle=By.xpath("//h1[text()='Payments']");
	private By defaultSelectWallet=By.xpath("//span[text()='Select wallet']/parent::button");
	private By selectWallet=By.xpath("//div[contains(.,'Select the wallet you want to send from')]/following-sibling::div/button");
	private By selectWalletTitle=By.xpath("//h1[text()='Select the wallet you want to send from']");
	private By selectWalletSubTitle=By.xpath("//p[text()='E.g. If you wish to send GBP, select your GBP wallet']");
	private By processingTimeSection=By.xpath("//div[text()='Processing time']/parent::div");
	private By selectedWalletName=By.xpath("//div[contains(@class,'flex-col') and contains(@class,'items-start')]/span[@class='truncate']");
	private By selectedWalletCode=By.xpath("//div[contains(@class,'flex-col') and contains(@class,'items-start')]/span[2]");
	private By selectedWalletBalance=By.xpath("//span[text()='Balance']/following-sibling::span");
	private By recipientForm=By.xpath("//div[.='Add new recipient']/parent::div[@role='dialog']");
	private By individualTabOnForm=By.xpath("//button[text()='Individual'][@type='button']");
	private By businessTabOnForm=By.xpath("//button[text()='Business'][@type='button']");
	private By recipientRow=By.xpath("//div[contains(@class,'grid gap-2 grid-flow-col')]/div");
	private By chkout_totalPayments= By.xpath("//div[text()='Total payments:']/following-sibling::div");
	private By chkout_amount= By.xpath("//div[text()='Amount:']/following-sibling::div");
	private By review_feePerPayment= By.xpath("//div[text()='Fee per payment:']/following-sibling::div");
	private By chkout_totalFees= By.xpath("//div[contains(text(),'Total fees') and contains(text(),'incl. 5% VAT')]/following-sibling::div");
	private By chkout_totalPayable= By.xpath("//div[text()='Total payable:']/following-sibling::div");
	private By paymentSubmitted_totalAmount= By.xpath("//p[text()='Total amount (incl. fee)']/following-sibling::p");
	private By paymentSubmitted_ExpPayDate= By.xpath("//p[text()='Expected payment arrival date']/following-sibling::p");
	private By paymentSubmitted_BalanceDue= By.xpath("//p[text()='Balance due']/following-sibling::p");
	public PaymentsPage() {

	}


	public void verifyPaymentsPageIsDisplayed() {
			WebUI.waitForElementVisible(paymentsTitle);
			WebUI.verifyElementVisible(paymentsTitle,"Payments page is not displayed");
	}
	public void verifyDefaultDetails(String paymentType){
		switch(paymentType.toUpperCase()){
			case "PAYMENTS":
				WebUI.verifyElementVisible(selectWalletTitle);
				WebUI.verifyElementVisible(selectWalletSubTitle);
				WebUI.verifyElementVisible(defaultSelectWallet);
				WebUI.verifyElementNotVisible(processingTimeSection,"Processing time is displayed");
				break;
			case "INTERNATIONAL PAYMENTS": break;
		}
	}
	public void selectPaymentCurrency(String currency){
		WebUI.clickElement(selectWallet);
		WebUI.clickElement(By.xpath("//*[text()='"+currency+"']"));
		WebUI.verifyElementVisible(By.xpath("//button/descendant::span[text()='"+currency+"']"));
	}
	public void verifySelectedCorridorDetails(Map<String, String> paymentCommonData){
		verifyFromCorridorNameCode(paymentCommonData);
		WebUI.verifyElementText(selectedWalletBalance,paymentCommonData.get("buyCurrencyWalletBalance"));
	}
	public void verifyFromCorridorNameCode(Map<String, String> paymentCommonData){
		WebUI.verifyElementText(selectedWalletName,paymentCommonData.get("toCurrencyName"));
		WebUI.verifyElementText(selectedWalletCode,paymentCommonData.get("toCurrencyCode"));
	}
	public String getWalletBalanceOnDashboardPage(String corridor) {
		By by=By.xpath("//p[contains(text(),'"+corridor+"')]");
		return WebUI.getTextElement(by);
	}

	public void verifyRecipientFormOpened() {
		WebUI.waitForElementVisible(recipientForm);
		WebUI.verifyElementVisible(recipientForm,"Add a new Recipient form is not displayed");
	}
	public void chooseRecipientTypeOnForm(String recipientType) {
		switch(recipientType.toUpperCase()){
			case "INDIVIDUAL": WebUI.clickElement(individualTabOnForm);
			WebUI.verifyElementAttributeValue(individualTabOnForm,"data-state","active"); break;
			case "BUSINESS": WebUI.clickElement(businessTabOnForm);
				WebUI.verifyElementAttributeValue(businessTabOnForm,"data-state","active");break;
		}
	}
	public void enterDataOnRecipientForm(Map<String,String> recipient_data_map,String corridor,String recipientType) {
		enterFieldValueOnRecipientForm("Enter payment amount",recipient_data_map.get("sendAmount").split(" ")[1]);
		if(recipientType.equalsIgnoreCase("Individual")){
		enterFieldValueOnRecipientForm("Full legal name",recipient_data_map.get("recipientName"));}else if(recipientType.equalsIgnoreCase("Business")){
			enterFieldValueOnRecipientForm("Full Registered business name",recipient_data_map.get("recipientName"));
		}
		enterFieldValueOnRecipientForm("First line of address",recipient_data_map.get("addressLineOne"));
		enterFieldValueOnRecipientForm("Second line of address",recipient_data_map.get("addressLineTwo"));
		selectFieldValueOnRecipientForm("Country",recipient_data_map.get("country"));
		enterFieldValueOnRecipientForm("Province",recipient_data_map.get("province"));
		enterFieldValueOnRecipientForm("City",recipient_data_map.get("city"));
		enterFieldValueOnRecipientForm("Postal/Zip code",recipient_data_map.get("postalCode"));
		enterFieldValueOnRecipientForm("SWIFT/BIC code",recipient_data_map.get("bicCode"));
		enterFieldValueOnRecipientForm("Invoice/Document ID",recipient_data_map.get("documentNumber"));
		enterFieldValueOnRecipientForm("Third party reference",recipient_data_map.get("thirdPartyReference"));
		selectFieldValueOnRecipientForm("Purpose of payment",recipient_data_map.get("purposeOfPayment"));
		if(corridor.equalsIgnoreCase("AUD")|corridor.equalsIgnoreCase("CAD")){
		enterFieldValueOnRecipientForm("Account Number",recipient_data_map.get("accountNumber"));}else if(corridor.equalsIgnoreCase("EUR")|corridor.equalsIgnoreCase("GBP")) {
			enterFieldValueOnRecipientForm("IBAN", recipient_data_map.get("accountNumber"));
		}
		switch(corridor.toUpperCase()){
			case "AUD":
				enterFieldValueOnRecipientForm("BSB number",recipient_data_map.get("bankCode"));
				break;
			case "CAD":
				enterFieldValueOnRecipientForm("CAD clearing code",recipient_data_map.get("bankCode"));
				break;
		}
	}
	public void verifydetailsOnRecipientsGrid(Map<String,String>recipient_data_map,int index){
		WebUI.smartWait();
		WebUI.sleep(5);
		List<WebElement> ls_recipients=WebUI.getWebElements(recipientRow);
		WebUI.verifyTrue(ls_recipients.size()>0,"Recipient are not available in the Recipient & payments step");
		String actValue="";
		actValue=ls_recipients.get(index-1).findElement(By.xpath("./p[2]")).getText();
		WebUI.verifyTrue(recipient_data_map.get("recipientName").contentEquals(actValue));
		actValue=ls_recipients.get(index-1).findElement(By.xpath("./p[3]")).getText();
		WebUI.verifyTrue(recipient_data_map.get("accountNumber").contentEquals(actValue));
		actValue=ls_recipients.get(index-1).findElement(By.xpath("./p[4]")).getText();
		WebUI.verifyTrue(recipient_data_map.get("sendAmount").contentEquals(actValue));
	}
	public void enterFieldValueOnRecipientForm(String fieldName,String value){
		By fld=By.xpath("//label[text()='"+fieldName+"']/preceding-sibling::input");
		WebUI.setText(fld,value);
	}
	public void selectFieldValueOnRecipientForm(String fieldName,String value){
		By fld=By.xpath("//button[text()='"+fieldName+"']");
		WebUI.clickElement(fld);
		By searchInput=By.xpath("//button[text()='"+fieldName+"']/following-sibling::div//input");
		WebUI.setText(searchInput,value);
		WebUI.clickElement(By.xpath("//span[text()='"+value+"']"));
	}
	/*######################TRANSACTIONS TAB#########################*/

	By title_TransactionDetail=By.xpath("//h2[contains(@id,'radix-')]/h1");

	By title_TransactionStatus=By.xpath("//h2[contains(@id,'radix-')]/div");

	By title_CreatedDate=By.xpath("//p[contains(@id,'radix-')]");


	By iconClose=By.xpath("//span[text()='Close']/parent::button");

	By btnDownload=By.xpath("//button[text()='Download']");

	public void openTransactionDetailsPage(Map<String,String> exchange_details, String status) {
		String amount=exchange_details.get("buyCurrencyCode")+" "+exchange_details.get("buyAmount");
		By record=By.xpath("//td[.='"+exchange_details.get("transactionReference")+"']/parent::tr");
		WebUI.verifyTrue(WebUI.verifyElementVisible(record),"Transaction is not available in the table:"+exchange_details.get("transactionReference"));
		List<WebElement> lsTableCols=WebUI.getWebElements(By.xpath("//td[.='"+exchange_details.get("transactionReference")+"']/parent::tr/td[contains(@class,'text-sm')]"));
		System.out.println(lsTableCols.size());
		WebUI.verifyTrue(lsTableCols.get(2).getText().contentEquals(amount));
		WebUI.verifyTrue(lsTableCols.get(3).getText().contentEquals(exchange_details.get("type")));
		WebUI.verifyTrue(lsTableCols.get(4).getText().equalsIgnoreCase(status));
		if(exchange_details.get("type").equalsIgnoreCase("Foreign Exchange")){
			WebUI.verifyTrue(lsTableCols.get(5).getText().contentEquals("-"));
		} else if (exchange_details.get("type").equalsIgnoreCase("Payment")) {
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

	public void verifyOnlyExchangeTransactionDetails(Map<String,String> exchange_details) {
		String settlementDt="";
		By creationDate=By.xpath("//h1[text()='Exchange receipt']/parent::h2/following-sibling::p");
//		String expCreateDt=(new SimpleDateFormat("EEEE dd MMMM yyyy").format(new Date())).toString();
		WebUI.verifyElementTextContains(creationDate,exchange_details.get("createdAt"));
		String amount=exchange_details.get("sellCurrencyCode")+" "+exchange_details.get("sellAmount");
		try {
			Date formatDt=new SimpleDateFormat("d MMMM yyyy").parse(exchange_details.get("formatted_settlementDate"));
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
		amount=exchange_details.get("buyCurrencyCode")+" "+exchange_details.get("buyAmount");
		verifyFieldValue("To",amount);
		verifyFieldValue("Exchange rate",exchange_details.get("liveRate"));
		verifyFieldValue("Transaction reference",exchange_details.get("transactionReference"));
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

	public Map<String, Map<String, String>> calculatePaymentDetails(Map<String, Map<String, String>> payments_data_map) {
		int recipients=WebUI.getWebElements(recipientRow).size();
		double totalAmt=0;
		double totalFee=0;
		double totalPayable=0;
		double balValue=0;
		payments_data_map.get("COMMON").put("noOfPayments",Integer.toString(recipients));
		for (Map.Entry<String, Map<String, String>> recipients_map : payments_data_map.entrySet()) {
			String recipientType = recipients_map.getKey();
			if(!(recipientType.equalsIgnoreCase("COMMON")|recipientType.equalsIgnoreCase("Exchange")|recipientType.equalsIgnoreCase("Foreign Exchange"))){
					totalAmt=totalAmt+Double.valueOf(payments_data_map.get(recipientType).get("sendAmount").toString().split(" ")[1]);
			}
		}
		totalFee=Double.valueOf(recipients)*Double.parseDouble(payments_data_map.get("COMMON").get("feePerPayment").split(" ")[1]);
		totalPayable=totalFee+totalAmt;
		balValue=Double.valueOf((payments_data_map.get("COMMON").get("buyCurrencyWalletBalance").toString().split(" ")[1]).replace(",",""));
		payments_data_map.get("COMMON").put("totalReceiveAmount", payments_data_map.get("COMMON").get("toCurrencyCode")+" "+String.format("%.2f",totalAmt));
		payments_data_map.get("COMMON").put("totalFees", payments_data_map.get("COMMON").get("fromCurrencyCode")+" "+String.format("%.2f",totalFee));
		payments_data_map.get("COMMON").put("totalPayable", payments_data_map.get("COMMON").get("fromCurrencyCode")+" "+String.format("%.2f",totalPayable));
		if(totalPayable < Double.valueOf(balValue)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			payments_data_map.get("COMMON").put("balanceDue", payments_data_map.get("COMMON").get("fromCurrencyCode")+" 0.00");
		}else if(totalPayable > Double.valueOf(balValue)) {
			Double balDue=totalPayable - balValue;
			payments_data_map.get("COMMON").put("balanceDue", payments_data_map.get("COMMON").get("fromCurrencyCode")+" "+String.format("%.2f",balDue));
		}
		return payments_data_map;
	}

	public void verifyPaymentDetailsReviewConfirm(Map<String, String> paymentsData) {
		verifyPaymentDetailsCheckout("",paymentsData);
		WebUI.verifyElementText(review_feePerPayment,paymentsData.get("feePerPayment"));
		getExchangePage().calculateAvailableBalanceLogic(paymentsData.get("buyCurrencyWalletBalance"));
	}
	public void verifyPaymentDetailsCheckout(String paymentType,Map<String, String> paymentsData){
		WebUI.verifyElementText(chkout_totalPayments,paymentsData.get("noOfPayments"));
		WebUI.verifyElementText(chkout_amount,paymentsData.get("totalReceiveAmount"));
		WebUI.verifyElementText(chkout_totalFees,paymentsData.get("totalFees"));
		WebUI.verifyElementText(chkout_totalPayable,paymentsData.get("totalPayable"));
	}

	public void verifyDetailsOnPaymentSubmittedPage(Map<String, String> paymentsData) {
		WebUI.verifyElementText(paymentSubmitted_totalAmount,paymentsData.get("totalPayable"));
		WebUI.verifyElementText(paymentSubmitted_BalanceDue,paymentsData.get("balanceDue"));
	}

	public String verifyExpPaymentDate() {
	return WebUI.getTextElement(paymentSubmitted_ExpPayDate);
	}
}
