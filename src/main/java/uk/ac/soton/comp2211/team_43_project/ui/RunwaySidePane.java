package uk.ac.soton.comp2211.team_43_project.ui;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.Data;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RunwaySidePane extends Pane implements Initializable {
  private final Logger logger = LogManager.getLogger(RunwaySidePane.class);

  Parent root = null;

  public RunwaySidePane(Data data) {
    logger.info("Building " + this.getClass().getName());

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/side-view.fxml"));
    fxmlLoader.setController(this);

    try {
      root = fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    this.getChildren().add(root);

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
