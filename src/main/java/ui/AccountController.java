package ui;

import bank.PrivateBank;
import bank.Transaction;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionDoesNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class AccountController extends Controller implements Initializable {
    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    @FXML
    private ListView<Transaction> transactionListView;

    AtomicReference<Transaction> selectedTransaction = new AtomicReference<>();

    enum SortType {
        ASCENDING,
        DESCENDING,
        POSITIVE,
        NEGATIVE
    }

    SortType currentSort = SortType.ASCENDING;

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

    public void updateTransactions(){
        transactionList.clear();
        List<Transaction> transactions = new ArrayList<>();
        switch (currentSort){
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
    }

    public void onClickedTransactionsListView(Event event) {
        if (transactionListView.getSelectionModel().getSelectedItem() == null)
            return;
        selectedTransaction.set(transactionListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void deleteTransactionEvent(Event event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Transaction");
        alert.setHeaderText("Are you sure you want to delete this transaction?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                bank.removeTransaction(FxApplication.currentAccount, selectedTransaction.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateTransactions();
        }
    }

    @FXML
    public void getAscendingTransactions(Event event){
        currentSort = SortType.ASCENDING;
        updateTransactions();
    }

    @FXML
    public void getDescendingTransactions(Event event){
        currentSort = SortType.DESCENDING;
        updateTransactions();
    }

    @FXML
    public void getPositiveTransactions(Event event){
        currentSort = SortType.POSITIVE;
        updateTransactions();
    }

    @FXML
    public void getNegativeTransactions(Event event){
        currentSort = SortType.NEGATIVE;
        updateTransactions();
    }

    @FXML
    public void setMainView(Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FxApplication.class.getResource("Mainview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FxApplication.primaryStage.setScene(scene);
        FxApplication.primaryStage.show();
        return;
    }

    @FXML
    public void addTransaction (Event event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Transaction");
        alert.setHeaderText("Are you sure you want to add this transaction?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                bank.addTransaction(FxApplication.currentAccount, selectedTransaction.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateTransactions();
        }
    }

}
