/*
 * File: proj4CorrelEnglishBogatyrev.Main.java
 * Names: Cassidy Correl, Nick English, Philipp Bogatyrev
 * Class: CS361
 * Project 4
 * Date: 2/28/2022
 */

package proj4CorrelEnglishBogatyrev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main class initializes the base Application.
 *
 */
public class Main extends Application {
    /**
     * Constructs the base elements on the stage.
     *
     * @param stage the stage on which to build the Application.
     * @throws IOException signals a disruption in IO.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        scene.getStylesheets().add(JavaKeywordsAsync.class.getResource("java-keywords.css").toExternalForm());
        stage.setTitle("Project 4 Cassidy, Nick, Philipp");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(E -> {
            ((Controller) fxmlLoader.getController()).handleExit();
        });
    }

    /**
     * Initializes the Application.
     *
     * @param args args passed on run.
     */
    public static void main(String[] args) {
        launch();
    }
}