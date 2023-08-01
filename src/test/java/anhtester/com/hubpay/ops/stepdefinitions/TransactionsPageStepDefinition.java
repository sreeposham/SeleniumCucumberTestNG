package anhtester.com.hubpay.ops.stepdefinitions;

import anhtester.com.common.CommonPage;
import anhtester.com.helpers.JsonHelpers;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class TransactionsPageStepDefinition extends CommonPage {

	private String fileName="";
	private Map<String,Map<String,String>> local_data_map;
	public TransactionsPageStepDefinition() throws IOException {
		fileName=getTestDataFile();
//		System.out.println("DashboardPageStepDefinition:"+CommonPage.checkfilenameaccess);
		try {
			local_data_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@When("I open transaction details view of the {string} transaction having {string}")
	public void i_open_transaction_detailsview_transaction_having(String trxnType,String reference) {
		// Write code here that turns the phrase above into concrete actions
	if(reference.isEmpty()){
		reference=local_data_map.get("COMMON").get("transactionReference");
	}
		getTransactionsPage().openTransactionDetailsViewFromTabPage(trxnType,reference);
	}

	@When("I verify transaction {string} on the transaction details view")
	public void i_verify_transaction_status_transaction_detailsview(String trxnStatus) {
		// Write code here that turns the phrase above into concrete actions
		getTransactionsPage().verifyStatusInTransactionDetailsView(trxnStatus);
	}
	@Then("I verify {string} details on the transaction details view")
	public void i_verify_transaction_details_on_transaction_detailsview(String trxnType) {
		// Write code here that turns the phrase above into concrete actions
		if(trxnType.equalsIgnoreCase("Foreign Exchange")){
			local_data_map.get("COMMON").get("transactionReference");
		}
		try {
			getTransactionsPage().verifyExchangeTransactionDetails(local_data_map.get("EXCHANGE"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Then("I verify {string} button {string} on the transaction details view")
	public void i_verify_button_on_transaction_detailsview(String button,String status) {
		// Write code here that turns the phrase above into concrete actions
		getTransactionsPage().verifyButtonOnTransactionDetailsView(button,status);
	}
	@Then("I click {string} button on the transaction details view")
	public void i_click_button_on_transaction_detailsview(String button) {
		// Write code here that turns the phrase above into concrete actions
		getTransactionsPage().clickButtonOnTransactionDetailsView(button);
		if(button.equalsIgnoreCase("SETTLEMENT RECEIVED")){
		local_data_map.get("EXCHANGE").put("orderSettledAt",(new SimpleDateFormat("dd/MM/yyyy").format(new Date())).toString());}
		else if(button.equalsIgnoreCase("SETTLE")){
			local_data_map.get("EXCHANGE").put("completedAt",(new SimpleDateFormat("dd/MM/yyyy").format(new Date())).toString());}
		JsonHelpers.writeToJsonFile(fileName,local_data_map);
	}
	@Then("I calculate and update wallet balance in test data file")
	public void i_claculate_update_wallet_balances_in_test_data_file() {
		String bugamount=local_data_map.get("COMMON").get("buyAmount");
		String sellamount=local_data_map.get("COMMON").get("sellAmount");
		String buyCurrencyWalletBalance=local_data_map.get("COMMON").get("buyCurrencyWalletBalance").split(" ")[1].replace(",","");
		String sellCurrencyWalletBalance=local_data_map.get("COMMON").get("sellCurrencyWalletBalance").split(" ")[1].replace(",","");;
//		String temp= Double.toString(Double.parseDouble(buyCurrencyWalletBalance)-Double.parseDouble(bugamount));
		String temp1= String.format("%.2f",(Double.parseDouble(buyCurrencyWalletBalance)+Double.parseDouble(bugamount)));
		System.out.println("buyCurrencyWalletBalance: "+buyCurrencyWalletBalance+" and Temp:"+temp1);
		local_data_map.get("COMMON").put("buyCurrencyWalletBalanceAfterSettlementReceived",(local_data_map.get("COMMON").get("buyCurrencyCode")+" "+temp1).toString());
		String temp2=String.format("%.2f",(Double.parseDouble(sellCurrencyWalletBalance)-Double.parseDouble(sellamount)));
		System.out.println("sellCurrencyWalletBalance: "+sellCurrencyWalletBalance+" and Temp:"+temp2);
		local_data_map.get("COMMON").put("sellCurrencyWalletBalanceAfterSettlementReceived",(local_data_map.get("COMMON").get("sellCurrencyCode")+" "+temp2).toString());
		JsonHelpers.writeToJsonFile(fileName,local_data_map);
		try {
			System.out.println("After calculating balances:"+JsonHelpers.readFromJsonFile(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Then("I navigate to Customer Overview page from the transaction details view")
	public void i_navigate_customer_overview_from_transaction_detailsview() {
		// Write code here that turns the phrase above into concrete actions
		getTransactionsPage().clickButtonOnTransactionDetailsView("User Account Id");
		getCustomersPage().verifyLaunchCustomerOverview();
	}

}
