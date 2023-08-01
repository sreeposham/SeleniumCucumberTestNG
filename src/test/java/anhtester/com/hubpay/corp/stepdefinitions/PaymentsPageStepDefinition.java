package anhtester.com.hubpay.corp.stepdefinitions;

import anhtester.com.common.CommonPage;
import anhtester.com.helpers.JsonHelpers;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class PaymentsPageStepDefinition extends CommonPage {

//	private String email="sreekanth@hubpay.ae";
//	private String password="Hubpass@1";
//	public String temp_exchange_sellcurrencywalletbalance="";
//	public String temp_exchange_buycurrencywalletbalance="";
	public String temp_exchange_liverate="";
	public String temp_exchange_sellamount="";
	public String temp_exchange_settlmentdate="";
//	public Map<String, String> temp_exchange_map;
	public Map<String, String> individualMap;
	public Map<String, String> dynamicDataMap;
	//	public ProductCatalogue productCatalogue;
	private Map<String, Map<String, String>> payments_data_map=new HashMap<>();
	private Map<String, String> individual_data_map=new HashMap<>();
	private Map<String, String> business_data_map=new HashMap<>();
	String fileName="";
	public static String tempfile="";

//	public ConfirmationPage confirmationPage;

	public PaymentsPageStepDefinition() throws IOException {
		fileName=getTestDataFile();
//		System.out.println("DashboardPageStepDefinition:"+CommonPage.checkfilenameaccess);
		try {
			payments_data_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		individualMap=new HashMap<String, String>();
		dynamicDataMap=new HashMap<String, String>();
		individualMap.put("type", "Individual");
		individualMap.put("Payment amount", "50.10");
		individualMap.put("Full name", "Individual Recipient Auto");
		individualMap.put("First line of address", "Office 15-123, WeWork Hub71");
		individualMap.put("Second line of address", "Al Khatem Tower, ADGM Square");
		individualMap.put("Country", "United Arab Emirates");
		individualMap.put("Province", "Abu Dhabi");
		individualMap.put("City", "Dubai");
		individualMap.put("Postal/Zip code", "00000");
		individualMap.put("IBAN", "GB12345678901234567890");
		individualMap.put("SWIFT/BIC code", "12345678");
		individualMap.put("Invoice/Document ID", "GBP-INV-1234");
		individualMap.put("Third party reference", "SWIFT-GBP-1234");
		individualMap.put("Purpose of payment", "Education");
//		individualMap.put("Fees", "12.00");
		
	}
//	public void getTestDataFileName(){
//	fileName="src/test/resources/testdata/" + scenario.getName().replace(" ","_")+".json";
//	System.out.println(fileName);
////	return fileName;
//	}
//@Given("I create test data file {string}")
//public void I_create_test_data_file(String testDataFile) throws IOException {
//	tempfile="src/test/resources/testdata/"+testDataFile;
//		createTestDataFile(tempfile);
//}
	@Then("I verify Payments page is displayed")
	public void I_verify_dashboard_page_is_displayed() throws IOException {
		getDashboardPage().verifyDashboardIsDisplayed();
	}

	@Then("I verify default details on Payments page")
	public void I_verify_defaultdetails_payments_page() throws IOException {
		getPaymentsPage().verifyDefaultDetails("Payments");
	}
	@Then("I select {string} corridor on Payments page")
	public void I_select_corridor_on_payments_page(String corridor) throws IOException {
		getPaymentsPage().selectPaymentCurrency(corridor);
		payments_data_map.get("COMMON").put("toCurrencyCode",corridor);
		payments_data_map.get("COMMON").put("currencyName",getCurrencyName(corridor));
	}

	@Then("I verify {string} corridor details on Payments page")
	public void I_verify_corridor_details_on_payments_page(String corridor) throws IOException {
		getPaymentsPage().verifySelectedCorridorDetails(payments_data_map.get("COMMON"));
	}
	@Then("I verify {string} for the {string} Payments")
	public void I_verify_processingtime_for_the_payments(String procTime,String corridor) throws Exception {
		getExchangePage().verifyProcessingTime(payments_data_map.get("COMMON").get("processingTime"),corridor);
	}
	@Then("I verify Add a new recipient form is displayed")
	public void I_verify_add_new_recipient_form_displayed() throws Exception {
		getPaymentsPage().verifyRecipientFormOpened();
	}
	@When("I choose {string} on Add a new recipient form")
	public void I_choose_on_Add_new_recipient_form(String recipientType) throws IOException {
		getPaymentsPage().chooseRecipientTypeOnForm(recipientType);
	}
	@When("I enter {string} recipient details on Add a new recipient form")
	public void I_enter_on_recipientdetails_Add_new_recipient_form(String recipientType) throws IOException {
		String payCorridor="";
		payCorridor=payments_data_map.get("COMMON").get("toCurrencyCode");
		if(recipientType.equalsIgnoreCase("INDIVIDUAL"))
			individual_data_map=payments_data_map.get("INDIVIDUAL");
		else if(recipientType.equalsIgnoreCase("BUSINESS"))
			individual_data_map=payments_data_map.get("BUSINESS");
		getPaymentsPage().enterDataOnRecipientForm(individual_data_map,payCorridor,recipientType);
	}
	@When("I click {string} button on Add a new recipient form")
	public void I_click_button_on_Add_new_recipient_form(String btnName) throws IOException {
		getExchangePage().clickBtn(btnName,"Payments");
	}

	@When("I verify {string} details in row {string} in Recipients grid on Payments & Recipients step")
	public void I_verify_details_in_row_in_recipientsgrid_on_paymentsrecipients_step(String recipientType,String index) throws IOException {
		String payCorridor="";
		payCorridor=payments_data_map.get("COMMON").get("toCurrencyCode");
		if(recipientType.equalsIgnoreCase("INDIVIDUAL"))
			individual_data_map=payments_data_map.get("INDIVIDUAL");
		else if(recipientType.equalsIgnoreCase("BUSINESS"))
			individual_data_map=payments_data_map.get("BUSINESS");
		getPaymentsPage().verifydetailsOnRecipientsGrid(individual_data_map,Integer.valueOf(index));
	}
	@Then("I calculate the payment details for the recipients added")
	public void I_calculate_the_paymentdetails_for_the_recipients_added() throws IOException {
		try {
			payments_data_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		JsonHelpers.writeToJsonFile(fileName,getPaymentsPage().calculatePaymentDetails(payments_data_map));
	}
	@When("I verify payment details under checkout section in Payments & Recipients step on {string} page")
	public void I_verify_paymentdetails_under_checkout_payments_recipients_step(String flow) throws IOException {
		getPaymentsPage().verifySelectedCorridorDetails(payments_data_map.get("COMMON"));
		if(flow.equalsIgnoreCase("Payments")){
			getPaymentsPage().verifyPaymentDetailsCheckout(flow,payments_data_map.get("COMMON"));
		}else if(flow.equalsIgnoreCase("International Payments")){
		}
	}
	@When("I verify payment details in Review & confirm step on {string} page")
	public void I_verify_paymentdetails_in_review_confirm_payments_page(String flow) throws IOException {
		getPaymentsPage().verifyFromCorridorNameCode(payments_data_map.get("COMMON"));
		if(flow.equalsIgnoreCase("Payments")){
			getPaymentsPage().verifyPaymentDetailsReviewConfirm(payments_data_map.get("COMMON"));
		}else if(flow.equalsIgnoreCase("International Payments")){
		}
	}
	@Then("I verify details on the Payment submitted page")
	public void I_verify_details_on_payment_submitted_page() throws IOException {
		getPaymentsPage().verifyDetailsOnPaymentSubmittedPage(payments_data_map.get("COMMON"));
		payments_data_map.get("COMMON").put("expectedPaymentDate",getPaymentsPage().verifyExpPaymentDate());
		JsonHelpers.writeToJsonFile(fileName,getPaymentsPage().calculatePaymentDetails(payments_data_map));
	}
//	@Then("I verify available funds for the {string} corridor")
//	public void I_verify_available_funds_for_corridor(String corridor){
//		String funds="";
//		if(exchange_data_map.get("buyCurrencyCode").contentEquals(corridor)){
//			funds=exchange_data_map.get("buyCurrencyWalletBalance");
//		}else if(exchange_data_map.get("sellCurrencyCode").contentEquals(corridor)){
//			funds=exchange_data_map.get("sellCurrencyWalletBalance");
//		}
//		getDashboardPage().verifyAvailableFundsInCorridor(corridor,funds);
//	}
//	@Then("I verify available funds for the {string} corridor after submitting Foreign Exchange to CAB")
//	public void I_verify_available_funds_for_corridor_after_exchange_submit_cab(String corridor){
//		String funds="";
//		try {
//			exchange_data_map=JsonHelpers.readFromJsonFile(fileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if(exchange_data_map.get("buyCurrencyCode").contentEquals(corridor)){
//			funds=exchange_data_map.get("buyCurrencyWalletBalanceAfterSettlementReceived");
//		}else if(exchange_data_map.get("sellCurrencyCode").contentEquals(corridor)){
//			funds=exchange_data_map.get("sellCurrencyWalletBalanceAfterSettlementReceived");
//		}
//		getDashboardPage().verifyAvailableFundsInCorridor(corridor,funds);
//	}
//	@When("I navigate to Transactions tab page")
//	public void I_navigate_to_transactions_page() throws IOException {
//		getDashboardPage().navigateToTabPageOnDashboard("Transactions");
//	}
//	@When("I navigate to Wallets tab page")
//	public void I_navigate_to_wallets_tab_page() throws IOException {
//		getDashboardPage().navigateToTabPageOnDashboard("Wallets");
//	}
//	@Then("I verify transaction status {string} in table and open transaction details view")
//	public void I_verify_transactions_status_open_details_view(String status) throws IOException {
//		try {
//			exchange_data_map=JsonHelpers.readFromJsonFile(fileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("Before openTransactionDetailsPage:"+exchange_data_map);
//		getDashboardPage().openTransactionDetailsPage(exchange_data_map,status);
//	}
//	@Then("I verify exchange transaction status is {string} on transaction details view")
//	public void I_verify_transactions_status_on_details_view(String status) throws IOException {
//		getDashboardPage().verifyStatusTransactionDetails(status);
//	}
//	@Then("I verify details of exchange transaction on transaction details view")
//	public void I_verify_transactions_status_open_details_view() throws IOException {
//		getDashboardPage().verifyOnlyExchangeTransactionDetails(exchange_data_map);
//	}
//
//	@When("I close the exchange transaction details view")
//	public void I_close_the_exchange_transaction_details_view() throws IOException {
//		getDashboardPage().clickCloseBtn();
//	}
////	@Then("I navigate to Payments page and select {string} corridor")
////	public void I_navigate_to_payments_page(String corridor) throws IOException {
//////		dynamicDataMap.put("corridor", email)
////		paymentsPage=dashboardPage.navigateToPaymentsPage("payments");
////		paymentsPage.selectWallet(corridor);
////	}
////
////	@Then("I navigate to Exchange page")
////	public void I_navigate_to_exchange_page() throws IOException {
////		exchangePage=dashboardPage.navigateToExchangePage("Exchange");
////	}
////
////	@Then("I verify default details on Exchange page")
////	public void I_verify_default_details_on_exchange_page() throws IOException {
////		exchangePage.defaultDetailsExchangePage();
////	}
////
////	@Then("I verify {string} company name and {string} full name on Exchange page")
////	public void I_verify_default_details_on_exchange_page(String company, String user) throws IOException {
////		exchangePage.verifyCompanyUserDetails(company,user);
////	}
////
////	@Then("I verify {string} step is {string} on Exchange page")
////	public void I_verify_step_status_on_exchange_page(String stepName,String stepStatus) throws IOException {
////		exchangePage.verifyStepStatusExchangePage(stepName,stepStatus);
////	}
////
////	@When("I select {string} corridor and input {string} amount on Exchange page")
////	public void I_select_corridor_and_input_amount_on_exchange_page(String corridor,String amount) throws IOException {
////		exchangePage.selectAndEnterBuyAmount(corridor,amount);
////	}
////	@Then("I verify Live rate is displayed for {string} corridor")
////	public void I_verify_live_rate_is_displayed_exchange_page(String corridor) throws IOException {
////		exchangePage.verifyLiveRateDisplayed(corridor);
////	}
////	@Then("I verify {string} for the {string} corridor")
////	public void I_verify_processingtime_for_the_corridor(String procTime,String corridor) throws IOException {
////		exchangePage.verifyProcessingTime(procTime,corridor);
////		temp_exchange_map=exchangePage.getDetailsForReview();
////		temp_exchange_map.put("buyCurrencyWalletBalance", temp_exchange_buycurrencywalletbalance);
////		temp_exchange_map.put("sellCurrencyWalletBalance", temp_exchange_sellcurrencywalletbalance);
////		System.out.println("In Amount step:");
////		System.out.println(temp_exchange_map);
////	}
////	@Then("I review Exchange summary for {string} corridor and {string} amount")
////	public void I_review_exchangesummary_for_corridor_and_amount(String corridor,String buyAmount) throws IOException {
//////		exchangePage.verifyProcessingTime(procTime,corridor);
//////		temp_exchange_map=exchangePage.getDetailsForReview();
////		if(corridor.contentEquals("EUR")) {
////			temp_exchange_map.put("buyAmount", buyAmount+".00");
////		}
////		exchangePage.reviewExchangeSummaryBeforeExpired(temp_exchange_map);
////	}
////	@When("I Confirm the Exchange transaction")
////	public void I_confirm_the_exchange_transaction() throws IOException {
////		String settlementDate=temp_exchange_map.get("settlementDate");
////		exchangePage.clickReviewConfirmCheckbox(settlementDate);
////		exchangePage.clickBtn("Confirm Exchange");
////	}
////
//////	@When("I create test data file with Exchange transaction details")
//////	public void I_create_testdata_file_with_exchange_transaction_details() throws IOException {
//////		exchangePage.createExchangeTransactionDataFile();
//////	}
////	@When("I verify details on Exchange Success modal popup")
////	public void I_verify_details_exchange_success_mmodal() throws IOException {
////		String trxnReference=exchangePage.verifyDetailsOnSuccessModal(temp_exchange_map);
////		temp_exchange_map.put("type", "Foreign Exchange");
////		temp_exchange_map.put("createdAt", "Foreign Exchange");
////		temp_exchange_map.put("transactionReference", trxnReference);
////	}
////	//	@When("I select {string} corridor on Exchange page")
//////	public void I_select_corridor_exchange_page(String wallet) throws IOException{
//////		exchangePage.selectWallet(wallet);
////
////	@When("I click {string} button on Exchange page")
////	public void I_click_button_on_exchange_page(String btn) throws IOException{
////		exchangePage.clickBtn(btn);
////	}
////
////	@When("I click {string} button on Exchange Success modal popup")
////	public void I_click_button_exchange_success_modal(String btn) throws IOException{
////		dashboardPage=exchangePage.modal_ClickBtn();
////	}
////
////	@Then("I navigate to Transactions page")
////	public void I_navigate_to_transactions_page() throws IOException {
////		transactionsPage=dashboardPage.navigateToTransactionsPage();
////	}
////	@Then("I verify transaction status {string} in table and open transaction details view")
////	public void I_verify_transactions_status_open_details_view(String status) throws IOException {
////		transactionsPage.openTransactionDetailsPage(temp_exchange_map,status);
////	}
////	@Then("I verify exchange transaction status is {string} on transaction details view")
////	public void I_verify_transactions_status_on_details_view(String status) throws IOException {
////		transactionsPage.verifyStatusTransactionDetails(status);
////	}
////	@Then("I verify details of exchange transaction on transaction details view")
////	public void I_verify_transactions_status_open_details_view() throws IOException {
////		transactionsPage.verifyOnlyExchangeTransactionDetails(temp_exchange_map);
////	}
////
////	@When("I close the exchange transaction details view")
////	public void I_close_the_exchange_transaction_details_view() throws IOException {
////		transactionsPage.clickCloseBtn();
////	}
////
////	@When("I sign out from Corp portal")
////	public void I_sign_out_from_corp_portal() throws IOException {
////		dashboardPage.signoutCorpPortal();
////	}
////
////
////
////	@When("I select {string} corridor on Payments page")
////	public void I_select_corridor_payments_page(String wallet) throws IOException{
////		paymentsPage.selectWallet(wallet);
////	}
////	@Then("I verify {string} for the selected {string} corridor")
////	public void I_verify_proctime_for_the_selected_corridor(String procTime,String corridor) throws IOException{
////		paymentsPage.verifyProcessingTime(procTime, corridor);
////	}
////	@Then("I verify the Payment summary for {string} corridor before recipients added")
////	public void I_verify_the_paymentsummary_before_recipients_added(String currency) throws IOException{
////		paymentsPage.verifyCheckoutBeforeRecipientAdded(currency,"PAYMENTS");
////	}
////	@When("I click {string} button on Payments page")
////	public void I_click_button_on_payments_page(String btn) throws IOException{
////		paymentsPage.clickBtn(btn);
////	}
////	@When("I open Add new recipient form")
////	public void I_open_add_new_recipient_form() throws IOException {
////		paymentsPage.clickBtn("Add individual or business");
////	}
////	@When("I fill data for the {string} recipient")
////	public void I_fill_data_for_the_recipient(String recipientType) throws IOException{
////		paymentsPage.enterRecipientFormData(individualMap);
////	}
////	@When("I save the recipient details")
////	public void I_save_the_recipient_details() throws IOException{
////		paymentsPage.saveRecipientDetails("Recipient added");
////	}
}
