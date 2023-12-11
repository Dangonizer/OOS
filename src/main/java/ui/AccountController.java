package ui;

import bank.PrivateBank;
import bank.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AccountController extends Controller implements Initializable {
    private ObservableList<String> transactionList = FXCollections.observableArrayList();

    @FXML
    private ListView<String> transactionListView;

    AtomicReference<String> selectedTransaction = new AtomicReference<>();

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        try {
            bank = new PrivateBank("Testbank", 0.1, 0.2, PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        transactionList.clear();
        List<Transaction> transactions = bank.getTransactions(FxApplication.currentAccount);
        List<String> transactionStrings = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionStrings.add(transaction.toString());
        }
        transactionList.addAll(transactionStrings);
        transactionListView.setItems(transactionList);
    }

}
