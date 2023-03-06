package uk.ac.soton.comp2211.team_43_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.ui.MainWindow;

import java.io.IOException;

public class App extends Application {

    private static final Logger logger = LogManager.getLogger(App.class);
    private static App instance;

    /**Base resolution width. */
    private final int width = 800;
    /**Base resolution height. */
    private final int height = 600;

    private Stage stage;

    /**
     * Start the tool.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        logger.info("Starting client");
        launch();
    }

    /**
     * Get App instance.
     *
     * @return the app
     */
    public static App getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) {

        instance = this;
        this.stage = stage;

        stage.setTitle("Runway Redeclaration Tool");
        stage.setOnCloseRequest(ev -> shutdown());

        openTool();
    }


    public void openTool() {
        logger.info("Opening main window");

        var window = new MainWindow(this);
        stage.setScene(window.getScene());

        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Shutdown the tool.
     */
    public void shutdown() {
        logger.info("Shutting down");
        System.exit(0);
    }

    public Stage getStage() {
        return stage;
    }
}