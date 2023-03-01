package uk.ac.soton.comp2211.team_43_project.ui;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.App;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

  private static final Logger logger = LogManager.getLogger(MainWindow.class);
  private final App app;

  Scene scene = null;
  Parent root = null;

  public MainWindow (App app) {
    logger.info("Building " + this.getClass().getName());

    this.app = app;

    try {
      var loader = new FXMLLoader(getClass().getResource("/layout.fxml"));

      loader.setController(this);
      root = loader.load();
    } catch (Exception e) {
      logger.error("Unable to read file: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }

    scene = new Scene(root);
  }

  public Scene getScene() {
    return scene;
  }

  /**
   * Initialise the Login Window
   *
   * @param url
   * @param bundle
   */
  @Override
  public void initialize(URL url, ResourceBundle bundle) {
    // TODO: Any setting up of the window when it is initialised
  }
}
