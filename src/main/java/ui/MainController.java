package ui;

import bank.PrivateBank;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for mainview
 */
public class MainController extends Controller implements Initializable {

    /**
     * Observable list for all accounts
     */
    private ObservableList<String> accountList = FXCollections.observableArrayList();

    /**
     * JavaFX ListView element
     */
    @FXML
    private ListView<String> accountListView;

    /**
     * Currently selected account
     */
    String selectedAccount;

    //@FXML
    //private Button addAccountButton;

    /**
     * Initializes the controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            bank = new PrivateBank("Testbank", 0.1, 0.2, PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        accountList.clear();
        accountList.addAll(bank.getAllAccounts());
        accountList.sort(Comparator.naturalOrder());
        accountListView.setItems(accountList);
        accountListView.setOnMouseClicked(this::onClickedAccountsListView);
    }

    /**
     * Sets selectedAccount to the selected item in the ListView
     * @param mouseEvent
     */
    private void onClickedAccountsListView(MouseEvent mouseEvent) {
        if (accountListView.getSelectionModel().getSelectedItem() == null)
            return;
        selectedAccount =String.valueOf(
                accountListView.getSelectionModel().getSelectedItem());
    }

    /**
     * Updates observable list with current accounts
     */
    public void updateAccountList() {
        accountList.clear();
        accountList.addAll(bank.getAllAccounts());
        accountList.sort(Comparator.naturalOrder());
    }

    /**
     * Opens a dialog to add a new account
     * @param event
     */
    @FXML
    public void addAccount(Event event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add Account");
        dialog.setHeaderText("Add a new account to " + bank.getName());
        dialog.getDialogPane().setMinWidth(300);

        Label lName = new Label("Name: ");
        TextField tName = new TextField();

        GridPane grid = new GridPane();
        grid.add(lName, 2, 1);
        grid.add(tName, 3, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResizable(true);

        ButtonType buttonTypeOk = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                tryAddAccount(tName.getText());
                //return tName.getText();
            }
            return null;
        });
        dialog.show();
    }

    /**
     * Checks user input for account for errors and adds account if no errors are found
     * @param name Account name to be added
     */
    public void tryAddAccount(String name) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (name == null || name.isEmpty()) {
            alert.setContentText("Name cannot be empty");
            alert.showAndWait();
            return;
        }
        try {
            bank.createAccount(name);
            updateAccountList();
        } catch (Exception e) {
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
    }

    /**
     * Sets current account in FxApplication class and changes scene to AccountView
     * @param event
     * @throws IOException
     */
    @FXML
    public void viewAccountEvent(Event event) throws IOException {
        FxApplication.currentAccount = selectedAccount.replace("[", "").replace("]", "");
        FXMLLoader fxmlLoader = new FXMLLoader(FxApplication.class.getResource("Accountview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FxApplication.primaryStage.setScene(scene);
        FxApplication.primaryStage.show();
        return;
    }

    /**
     * Deletes selected account
     * @param event
     */
    @FXML
    public void deleteAccountEvent(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Delete Account");
        alert.setContentText("Delete account " + selectedAccount.replace("[", "").replace("]", ""));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            return;
        } else {
            try {
                bank.deleteAccount(selectedAccount.replace("[", "").replace("]", ""));
                updateAccountList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
