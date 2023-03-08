package uk.ac.soton.comp2211.team_43_project.ui;

import java.net.URL;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.model.Runway;

/**
 * RunwayTopPane displays a side-on view of a runway.
 */
public class RunwaySidePane extends RunwayPane {
  private final Logger logger = LogManager.getLogger(RunwaySidePane.class);

  public RunwaySidePane(Runway runway) {
    super(runway, "/side-view.fxml");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
