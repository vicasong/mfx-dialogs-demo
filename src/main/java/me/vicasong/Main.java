package me.vicasong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * demo application
 *
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        CurrentStage.stage = primaryStage;
        @SuppressWarnings("DataFlowIssue")
        Pane root = FXMLLoader.load(this.getClass().getResource("/views/demo.fxml"));
        if (primaryStage.getScene() == null) {
            primaryStage.setScene(new Scene(root));
        } else {
            primaryStage.getScene().setRoot(root);
        }
        primaryStage.show();
    }
}