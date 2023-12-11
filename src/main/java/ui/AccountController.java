package ui;

import bank.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Controller for accountview
 */
public class AccountController extends Controller implements Initializable {

    /**
     * Observable list for all transactions
     */
    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    /**
     * JavaFX ListView element
     */
    @FXML
    private ListView<Transaction> transactionListView;

    /**
     * Currently selected transaction
     */
    AtomicReference<Transaction> selectedTransaction = new AtomicReference<>();

    /**
     * Text element that shows account balance
     */
    @FXML
    public Text balanceText;

    /**
     * Enum for sorting state
     */
    enum SortType {
        ASCENDING,
        DESCENDING,
        POSITIVE,
        NEGATIVE
    }

    /**
     * Current sorting state
     */
    SortType currentSort = SortType.ASCENDING;

    /**
     * Initializes the controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        try {
            bank = new PrivateBank("Testbank", 0.1, 0.2, PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateTransactions();
        transactionListView.setItems(transactionList);
        transactionListView.setOnMouseClicked(this::onClickedTransactionsListView);
    }

    /**
     * Updates the transaction list and balance with the current information from the bank
     */
    public void updateTransactions() {
        transactionList.clear();
        List<Transaction> transactions = new ArrayList<>();
        if (bank.getTransactions(FxApplication.currentAccount) == null) {
            balanceText.setText("Account balance: 0");
            return;
        }
        switch (currentSort) {
            case ASCENDING:
                transactions = bank.getTransactionsSorted(FxApplication.currentAccount, true);
                break;
            case DESCENDING:
                transactions = bank.getTransactionsSorted(FxApplication.currentAccount, false);
                break;
            case POSITIVE:
                transactions = bank.getTransactionsByType(FxApplication.currentAccount, true);
                break;
            case NEGATIVE:
                transactions = bank.getTransactionsByType(FxApplication.currentAccount, false);
                break;
        }
        transactionList.addAll(transactions);
        balanceText.setText("Account balance: " + String.valueOf(bank.getAccountBalance(FxApplication.currentAccount)));
    }

    /**
     * Sets selectedTransaction to the selected item in the ListView
     * @param event
     */
    public void onClickedTransactionsListView(Event event) {
        if (transactionListView.getSelectionModel().getSelectedItem() == null)
            return;
        selectedTransaction.set(transactionListView.getSelectionModel().getSelectedItem());
    }

    /**
     * Deletes transaction after confirmation through alert
     * @param event
     */
    @FXML
    public void deleteTransactionEvent(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Transaction");
        alert.setHeaderText("Are you sure you want to delete this transaction?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                bank.removeTransaction(FxApplication.currentAccount, selectedTransaction.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateTransactions();
        }
    }

    /**
     * Sorts transactions ascending
     * @param event
     */
    @FXML
    public void getAscendingTransactions(Event event) {
        currentSort = SortType.ASCENDING;
        updateTransactions();
    }

    /**
     * Sorts transactions descending
     * @param event
     */
    @FXML
    public void getDescendingTransactions(Event event) {
        currentSort = SortType.DESCENDING;
        updateTransactions();
    }

    /**
     * Shows only positive transactions (calculated amount)
     * @param event
     */
    @FXML
    public void getPositiveTransactions(Event event) {
        currentSort = SortType.POSITIVE;
        updateTransactions();
    }

    /**
     * Shows only negative transactions (calculated amount)
     * @param event
     */
    @FXML
    public void getNegativeTransactions(Event event) {
        currentSort = SortType.NEGATIVE;
        updateTransactions();
    }

    /**
     * Changes scene to Mainview
     * @param event
     * @throws IOException
     */
    @FXML
    public void setMainView(Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FxApplication.class.getResource("Mainview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FxApplication.primaryStage.setScene(scene);
        FxApplication.primaryStage.show();
        return;
    }

    /**
     * Adds transaction trough user input. Asks for payment or transfer.
     * @param event
     */
    @FXML
    public void addTransaction(Event event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Transaction");
        dialog.setHeaderText("Add a new transaction");
        ButtonType paymentButton = new ButtonType("Payment", ButtonBar.ButtonData.LEFT);
        ButtonType transferButton = new ButtonType("Transfer", ButtonBar.ButtonData.RIGHT);
        dialog.getDialogPane().getButtonTypes().addAll(paymentButton, transferButton);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == paymentButton) {
            addPayment();
        } else if (result.get() == transferButton) {
            addTransfer();
        }
    }

    /**
     * Adds payment through user input
     */
    public void addPayment() {
        Dialog<Payment> dialog = new Dialog<>();
        dialog.getDialogPane().setMinWidth(350);
        dialog.getDialogPane().setMinHeight(250);
        dialog.setTitle("Add Payment");
        dialog.setHeaderText("Add a new payment");
        GridPane grid = new GridPane();
        Label dateLabel = new Label("Date:");
        Label amountLabel = new Label("Amount:");
        Label descriptionLabel = new Label("Description:");
        TextField dateField = new TextField();
        TextField amountField = new TextField();
        TextField descriptionField = new TextField();
        grid.add(dateLabel, 0, 0);
        grid.add(amountLabel, 0, 1);
        grid.add(descriptionLabel, 0, 2);
        grid.add(dateField, 1, 0);
        grid.add(amountField, 1, 1);
        grid.add(descriptionField, 1, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType okButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                tryAddPayment(dateField.getText(), amountField.getText(), descriptionField.getText());
                updateTransactions();
            }
            return null;
        });
        dialog.show();
    }

    /**
     * Adds transfer through user input
     */
    public void addTransfer() {
        Dialog<Transfer> dialog = new Dialog<>();
        dialog.getDialogPane().setMinWidth(350);
        dialog.getDialogPane().setMinHeight(250);
        dialog.setTitle("Add Transfer");
        dialog.setHeaderText("Add a new Transfer");
        GridPane grid = new GridPane();
        Label dateLabel = new Label("Date:");
        Label amountLabel = new Label("Amount:");
        Label descriptionLabel = new Label("Description:");
        Label senderLabel = new Label("Sender:");
        Label recipientLabel = new Label("Recipient:");
        TextField dateField = new TextField();
        TextField amountField = new TextField();
        TextField descriptionField = new TextField();
        TextField senderField = new TextField();
        TextField recipientField = new TextField();
        grid.add(dateLabel, 0, 0);
        grid.add(amountLabel, 0, 1);
        grid.add(descriptionLabel, 0, 2);
        grid.add(senderLabel, 0, 3);
        grid.add(recipientLabel, 0, 4);
        grid.add(dateField, 1, 0);
        grid.add(amountField, 1, 1);
        grid.add(descriptionField, 1, 2);
        grid.add(senderField, 1, 3);
        grid.add(recipientField, 1, 4);
        dialog.getDialogPane().setContent(grid);

        ButtonType okButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                tryAddTransfer(dateField.getText(), amountField.getText(), descriptionField.getText(), senderField.getText(), recipientField.getText());
                updateTransactions();
            }
            return null;
        });
        dialog.show();
    }

    /**
     * Checks user input for payment for errors and adds payment if no errors are found
     * @param date Date of payment
     * @param amount Amount of payment
     * @param description Description of payment
     */
    public void tryAddPayment(String date, String amount, String description) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (date == null || date.isEmpty()) {
            alert.setContentText("Date cannot be empty");
            alert.showAndWait();
            return;
        }
        if (amount == null || amount.isEmpty()) {
            alert.setContentText("Amount cannot be empty");
            alert.showAndWait();
            return;
        }
        if (description == null || description.isEmpty()) {
            alert.setContentText("Description cannot be empty");
            alert.showAndWait();
            return;
        }
        try {
            bank.addTransaction(FxApplication.currentAccount, new Payment(date, Double.parseDouble(amount), description));
        } catch (Exception e) {
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
    }

    /**
     * Checks user input for transfer for errors and adds transfer if no errors are found
     * @param date Date of transfer
     * @param amount Amount of transfer
     * @param description Description of transfer
     * @param sender Sender of transfer
     * @param recipient Recipient of transfer
     */
    public void tryAddTransfer(String date, String amount, String description, String sender, String recipient) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (date == null || date.isEmpty()) {
            alert.setContentText("Date cannot be empty");
            alert.showAndWait();
            return;
        }
        if (amount == null || amount.isEmpty()) {
            alert.setContentText("Amount cannot be empty");
            alert.showAndWait();
            return;
        }
        if (Double.parseDouble(amount) < 0) {
            alert.setContentText("Amount cannot be negative");
            alert.showAndWait();
            return;
        }
        if (description == null || description.isEmpty()) {
            alert.setContentText("Description cannot be empty");
            alert.showAndWait();
            return;
        }
        if (sender == null || sender.isEmpty()) {
            alert.setContentText("Sender cannot be empty");
            alert.showAndWait();
            return;
        }
        if (recipient == null || recipient.isEmpty()) {
            alert.setContentText("Recipient cannot be empty");
            alert.showAndWait();
            return;
        }
        if (sender.equals(recipient)) {
            alert.setContentText("Sender and recipient cannot be the same");
            alert.showAndWait();
            return;
        }
        if (sender.equals(FxApplication.currentAccount)) {
            try {
                bank.addTransaction(FxApplication.currentAccount, new OutgoingTransfer(date, Double.parseDouble(amount), description, sender, recipient));
            } catch (Exception e) {
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else if (recipient.equals(FxApplication.currentAccount)) {
            try {
                bank.addTransaction(FxApplication.currentAccount, new IncomingTransfer(date, Double.parseDouble(amount), description, sender, recipient));
            } catch (Exception e) {
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            alert.setContentText("Sender or recipient must be the current account");
            alert.showAndWait();
            return;
        }
    }
}



