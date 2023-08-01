@exchange @aud
Feature: End to End Exchange flow - AUD

  Background:
    Given I setup test data file name for "ForeignExchange" "AUD" "30"

  @corp
  Scenario Outline: Verify the end to end flow of Exchange flow for AUD corridor
    Given I'm Logged in to the Corp portal
    Then I verify Dashboard page is displayed
    Then I Note the wallet balance for the "<buyCurrency>" corridor
    Then I navigate to Exchange page
    Then I verify default details on Exchange page
#    Then I verify "ANITHA TEST BUSINESS Solutions Private Limited" company name and "Sreekanth j" full name on the page
    Then I verify "Amount" step is "Active" on "Exchange" page
    Then I verify "Review & confirm" step is "Inactive" on "Exchange" page
    When I select "<buyCurrency>" corridor and input "<buyAmount>" amount on Exchange page
    Then I verify Live rate is displayed for "<buyCurrency>" corridor
    Then I verify "<processingTime>" for the "<buyCurrency>" corridor
##    Then I verify Settlement Date for the "<currency>" corridor
    When I click "Next" button on "Exchange" page
#    Then I verify "Amount" step is "Inactive" on "Exchange" page
#    Then I verify "Review & confirm" step is "Active" on "Exchange" page
    When I "check" consent checkbox on Exchange page
    Then I review Exchange summary for "<buyCurrency>" corridor and "<buyAmount>" amount
    #Then I create test data file with Exchange transaction details
    When I Confirm the Exchange transaction
    Then I verify details on Exchange Success modal popup
    When I click "Done" button on Exchange Success modal popup
    Then I verify Dashboard page is displayed
    Then I navigate to Transactions tab page
    Then I verify transaction status "Ordered" in table and open transaction details view
    Then I verify exchange transaction status is "Ordered" on transaction details view
    Then I verify details of exchange transaction on transaction details view
    When I close the transaction details view
    Then I navigate to Wallets tab page
    Then I verify available funds for the "<buyCurrency>" corridor
    Then I verify available funds for the "AED" corridor
    When I'm Sign out from the Corp portal
#    Given I'm Logged in to the Ops portal
##    Then I navigated to the "Transactions" page using left navigation
##    When I open transaction details view of the "<transactionType>" transaction having "<transactionReference>"
##    Then I navigate to Customer Overview page from the transaction details view
###    Then I search for customer by "Phone number" "<phone>" in the Customers page
#    Then I open customer overview page of customer registered with "Phone number" "<phone>"
#    And I verify "Available" balance of "<buyCurrency>" wallet on the Customer Overview page
#    And I verify "Available" balance of "AED" wallet on the Customer Overview page
#    When I navigate to "Wallet debits" tab page
##    Then I search for the transaction with "<reference>", "<type>" and "<status>"
#    When I open transaction details view of the "<transactionType>" transaction having "<transactionReference>"
#    Then I verify transaction "Ordered" on the transaction details view
#    Then I verify "<transactionType>" details on the transaction details view
#    Then I verify "SETTLEMENT RECEIVED" button "Available" on the transaction details view
#    When I click "SETTLEMENT RECEIVED" button on the transaction details view
#    Then I verify "SETTLE" button "Available" on the transaction details view
#    Then I verify transaction "ORDER_SETTLED" on the transaction details view
#    Then I verify "<transactionType>" details on the transaction details view
#    Then I navigate to Customer Overview page from the transaction details view
#    And I verify "Available" balance of "<buyCurrency>" wallet on the Customer Overview page
#    Then I calculate and update wallet balance in test data file
#    And I verify "Available" balance of "AED" wallet after submitting Foreign Exchange to CAB
#    Given I'm Logged in to the Corp portal
#    Then I verify Dashboard page is displayed
#    Then I verify available funds for the "AED" corridor after submitting Foreign Exchange to CAB
#    And I verify available funds for the "<buyCurrency>" corridor
#    Then I navigate to Transactions tab page
#    Then I verify transaction status "Settled" in table and open transaction details view
#    Then I verify exchange transaction status is "Order Settled" on transaction details view
#    Then I verify details of exchange transaction on transaction details view
#    When I close the transaction details view
#    When I'm Sign out from the Corp portal
#    Given I'm Logged in to the Ops portal
#    Then I navigated to the "Transactions" page using left navigation
#    When I open transaction details view of the "<transactionType>" transaction having "<transactionReference>"
#    When I click "SETTLE" button on the transaction details view
#    Then I verify transaction "COMPLETED" on the transaction details view
#    Then I verify "<transactionType>" details on the transaction details view
#    Then I navigate to Customer Overview page from the transaction details view
#    And I verify "Available" balance of "AED" wallet after submitting Foreign Exchange to CAB
#    And I verify "Available" balance of "<buyCurrency>" wallet after submitting Foreign Exchange to CAB
#    Given I'm Logged in to the Corp portal
#    Then I verify Dashboard page is displayed
#    Then I verify available funds for the "<buyCurrency>" corridor after submitting Foreign Exchange to CAB
#    Then I verify available funds for the "AED" corridor after submitting Foreign Exchange to CAB
#    Then I navigate to Transactions tab page
#    Then I verify transaction status "Completed" in table and open transaction details view
#    Then I verify exchange transaction status is "Completed" on transaction details view
#    Then I verify details of exchange transaction on transaction details view
#    When I close the transaction details view
#    When I'm Sign out from the Corp portal

    Examples:
      | buyCurrency | buyAmount | processingTime | phone          | transactionType  | transactionReference |
      | AUD         | 30.00     | Instant        | +91 7989730020 | Foreign Exchange |                      |