package uk.ac.soton.comp2211.team_43_project.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import uk.ac.soton.comp2211.team_43_project.model.Runway;

/**
 * Runway top pane displays a top-down view of a runway.
 */
public class RunwayTopPane extends RunwayPane {

  @FXML protected Rectangle clearwayL;
  @FXML protected Rectangle clearwayR;
  @FXML protected Polygon direction;
  @FXML protected Rectangle stopwayL;
  @FXML protected Rectangle stopwayR;
  @FXML protected Rectangle displacedThresholdL;
  @FXML protected Rectangle displacedThresholdR;
  @FXML protected Line centreLine;
  @FXML protected VBox textBox;

  public RunwayTopPane(Runway runway) {
    super(runway, "/top-down-view.fxml");
  }

  @Override
  public void runwaySetup() {
    super.runwaySetup();
    setRunwayWidth(tora);
  }

  /**
   * Toggle runway rotation.
   */
  public void toggleRotateRunway() {
    logger.info("rotate runway set to: {}", !rotated);
    if (rotated) {
      rotateAll(0);
      rotated = false;
    } else {
      var dir = runway.getDesignator().substring(0, 2);
      int angle = (Integer.parseInt(dir) * 10) - 90;
      rotateAll(angle);
      rotated = true;
    }
  }

  private void rotateAll(int angle) {
    instrumentStrip.setRotate(angle);
    runwayName1.setRotate(-angle);
    runwayName2.setRotate(-angle);
    todaText.setRotate(-angle);
    asdaText.setRotate(-angle);
    toraText.setRotate(-angle);
    ldaText.setRotate(-angle);
  }

  /**
   * Change runway display length.
   *
   * @param widthMetres length of runway in metres
   */
  public void setRunwayWidth(float widthMetres) {
    logger.info("Setting TORA to " + widthMetres);
    centreLine.setEndX((widthMetres * scaling)
        - (2 * thresholdLength)
        - (displacedThreshold * scaling));
  }


  @Override
  public void updateIndicators() {
    super.updateIndicators();
    stopwayL.setWidth(stopway * scaling);
    stopwayR.setWidth(stopway * scaling);

    logger.info(clearway);
    if (away) {
      clearwayR.setWidth(clearway * scaling);
      clearwayL.setWidth(0);

      direction.setRotate(90);

      displacedThresholdL.setWidth(displacedThreshold * scaling);
      displacedThresholdR.setWidth(0);
    } else {
      clearwayL.setWidth(clearway * scaling);
      clearwayR.setWidth(0);

      direction.setRotate(-90);

      displacedThresholdL.setWidth(0);
      displacedThresholdR.setWidth(displacedThreshold * scaling);

    }
  }

  public Boolean getRotated() {
    return rotated;
  }

  public void setRotated(Boolean rotated) {
    this.rotated = rotated;
  }

}
