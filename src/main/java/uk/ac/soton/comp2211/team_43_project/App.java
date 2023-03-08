package uk.ac.soton.comp2211.team_43_project;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.ui.MainWindow;

/**
 * The application class for the tool.
 */
public class App extends Application {

  private static final Logger logger = LogManager.getLogger(App.class);
  private static App instance;

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
    Data data = new Data();

    stage.setTitle("Runway Redeclaration Tool");
    stage.setOnCloseRequest(ev -> shutdown());

    openTool();
  }

  /**
   * Open the main window.
   */
  public void openTool() {
    logger.info("Opening main window");

    var window = new MainWindow();
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