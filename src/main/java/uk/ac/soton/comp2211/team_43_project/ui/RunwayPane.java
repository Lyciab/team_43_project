package uk.ac.soton.comp2211.team_43_project.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.Data;
import uk.ac.soton.comp2211.team_43_project.model.Runway;


/**
 * Basic pane to display a runway.
 */
public class RunwayPane extends Pane implements Initializable {

  public Logger logger = LogManager.getLogger(this.getClass());

  @FXML protected Pane instrumentStrip;
  @FXML protected BorderPane runwayPane;
  @FXML protected HBox visualStrip;
  @FXML protected Label runwayName1;
  @FXML protected Label runwayName2;
  @FXML protected Label toraText;
  @FXML protected Label todaText;
  @FXML protected Label asdaText;
  @FXML protected Label ldaText;
  @FXML protected Line toraIndicator;
  @FXML protected Line todaIndicator;
  @FXML protected Line asdaIndicator;
  @FXML protected Line ldaIndicator;
  @FXML protected HBox toraBox;
  @FXML protected HBox todaBox;
  @FXML protected HBox asdaBox;
  @FXML protected HBox ldaBox;
  @FXML protected VBox unStacked;
  @FXML protected StackPane stacked;
  @FXML protected StackPane indicatorPane;


  protected Float tora;
  protected Float toda;
  protected Float asda;
  protected Float lda;
  protected Float stopway;
  protected Float clearway;
  protected Float displacedThreshold;
  protected Integer thresholdLength = Data.getThresholdLength();
  protected Boolean rotated = false;
  protected Boolean away;
  protected Boolean isStacked = false;
  protected final float scaling = Data.getScaling();

  Parent root;
  protected Runway runway;

  /**
   * Create new Runway Pane.
   *
   * @param runway runway to display
   * @param fxml fxml file for the runway
   */
  public RunwayPane(Runway runway, String fxml) {

    logger.info("Building " + this.getClass().getName());

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
    fxmlLoader.setController(this);

    try {
      root = fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    this.getChildren().add(root);
    this.runway = runway;

    runwaySetup();

  }

  /**
   * Setup runway values and indicators.
   */
  public void runwaySetup() {
    logger.info("setting up runway");

    tora = runway.getTora();
    toda = runway.getToda();
    asda = runway.getAsda();
    lda  = runway.getLda();
    displacedThreshold = runway.getDisplacedThreshold();
    stopway = runway.getStopway();
    clearway = runway.getClearway();
    away = runway.getAway();

    setRunwayName(runway.getDesignator());
    updateIndicators();
  }

  /**
   * Toggles on/off stacking indicators on top of each other.
   */
  public void toggleStackIndicators() {
    logger.info("stacking indicators set to: {}", !isStacked);
    if (isStacked) {
      isStacked = false;
      stacked.getChildren().clear();
      unStacked.getChildren().addAll(toraBox, todaBox, asdaBox, ldaBox);
      indicatorPane.setPrefHeight(85);
    } else {
      isStacked = true;
      unStacked.getChildren().clear();
      stacked.getChildren().addAll(ldaBox, asdaBox, todaBox, toraBox);
      indicatorPane.setPrefHeight(23);
    }
  }

  /**
   * Change the name of the runway.
   *
   * @param name new name of runway
   */
  public void setRunwayName(String name) {
    logger.info("updating runway name to {}", name);
    //Parallel runways
    if (name.length() == 3) {

      String dir = name.substring(0, 2);

      var newName =  dir + "\n" + name.charAt(2);
      int runwayNum = Integer.parseInt(dir);

      Label next;

      if (runwayNum <= 18) {
        runwayName1.setText(newName);
        next = runwayName2;
        runwayNum += 18;
      } else {
        runwayName2.setText(newName);
        next = runwayName1;
        runwayNum -= 18;
      }

      String name2 = switch (name.charAt(2)) {
        case 'L' -> runwayNum + "\n" + "R";
        case 'R' -> runwayNum + "\n" + "L";
        case 'C' -> runwayNum + "\n" + "C";
        default -> "";
      };

      next.setText(name2);
    } else { //Single runway
      runwayName1.setText(name);
      runwayName2.setText("");
    }
  }

  /**
   * Update all indicators.
   */
  public void updateIndicators() {
    logger.info("updating indicators");

    float[] stopwayArray = {stopway};
    float[] clearwayArray = {clearway};
    float[] emptyArray = {};

    updateParameter(toraText, toraIndicator, toraBox, tora, "TORA", stopwayArray, clearwayArray);
    updateParameter(todaText, todaIndicator, todaBox, toda, "TODA", stopwayArray, emptyArray);
    updateParameter(asdaText, asdaIndicator, asdaBox, asda, "ASDA", stopwayArray, clearwayArray);

    float[] ldaArray = {stopway, displacedThreshold};
    float[] ldaAwayArray = {clearway, -displacedThreshold};

    updateParameter(ldaText, ldaIndicator, ldaBox, lda, "LDA   ", ldaArray, ldaAwayArray);


  }

  /**
   * Update a given parameter's indicator.
   *
   * @param text label
   * @param indicator indicator line
   * @param box indicator box
   * @param parameter parameter to update
   * @param parameterName name of parameter
   * @param paramArray array of values to add
   * @param ifAway array of values to add if taking off away
   */
  private void updateParameter(Label text,
                               Line indicator,
                               HBox box,
                               Float parameter,
                               String parameterName,
                               float[] paramArray,
                               float[] ifAway) {
    text.setText(parameterName + " " + parameter + "m");
    indicator.setEndX(parameter  * scaling);
    for (float v : paramArray) {
      box.setTranslateX(box.getTranslateX() + (v * scaling));
    }
    if (!away) {
      for (float v : ifAway) {
        box.setTranslateX(box.getTranslateX() + (v * scaling));
      }
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    logger.info("initialising");
  }

  public Boolean getIsStacked() {
    return isStacked;
  }

  public void setIsStacked(Boolean isStacked) {
    this.isStacked = isStacked;
  }

}