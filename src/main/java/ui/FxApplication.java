package ui;

import bank.PrivateBank;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FxApplication extends Application {

    public static Stage primaryStage;

    public static String currentAccount;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainview.fxml"));
        primaryStage = stage;
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}
