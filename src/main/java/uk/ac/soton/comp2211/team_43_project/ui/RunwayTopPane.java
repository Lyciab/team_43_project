package uk.ac.soton.comp2211.team_43_project.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.Data;
import uk.ac.soton.comp2211.team_43_project.model.Runway;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RunwayTopPane extends Pane implements Initializable {

  private static final Logger logger = LogManager.getLogger(RunwayTopPane.class);

  Parent root = null;
  private Runway runway;


  @FXML private Pane instrumentStrip;
  @FXML private BorderPane runwayPane;
  @FXML private HBox visualStrip;
  @FXML private Label runwayName1;
  @FXML private Label runwayName2;
  @FXML private Rectangle stopwayL;
  @FXML private Rectangle stopwayR;
  @FXML private Rectangle displacedThresholdL;
  @FXML private Rectangle displacedThresholdR;
  @FXML private Line centreLine;
  @FXML private Label toraText;
  @FXML private Label todaText;
  @FXML private Label asdaText;
  @FXML private Label ldaText;
  @FXML private Line toraIndicator;
  @FXML private Line todaIndicator;
  @FXML private Line asdaIndicator;
  @FXML private Line ldaIndicator;
  @FXML private HBox toraBox;
  @FXML private HBox todaBox;
  @FXML private HBox asdaBox;
  @FXML private HBox ldaBox;
  @FXML private Rectangle clearwayL;
  @FXML private Rectangle clearwayR;
  @FXML private Polygon direction;


  private Integer TORA, TODA, ASDA, LDA, stopway, clearway, displacedThreshold;
  private Boolean rotated = false;
  private Boolean away;

  public RunwayTopPane(Runway runway) {

    logger.info("Building " + this.getClass().getName());

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/top-down-view.fxml"));
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


  public void runwaySetup() {
    this.TORA = runway.getTORA();
    this.TODA = runway.getTODA();
    this.ASDA = runway.getASDA();
    this.LDA = runway.getLDA();
    this.displacedThreshold = runway.getDisplacedThreshold();
    this.stopway = runway.getStopway();
    this.clearway = runway.getClearway();
    this.away = runway.getAway();
    setRunwayName(runway.getDesignator());
    setRunwayWidth(TORA);
    updateIndicators();
  }

  /**
   * Change runway display length.
   * @param widthMetres Length of runway in metres
   */
  public void setRunwayWidth(float widthMetres) {
    logger.info("Setting TODA to " + widthMetres);
    centreLine.setEndX((widthMetres * Data.getScaling()) - (2 * Data.getThresholdLength()) - (displacedThreshold * Data.getScaling())) ;
  }


  /**
   * Toggle runway rotation.
   */
  public void toggleRotateRunway() {
    logger.info("Match runway rotation to compass: " + !rotated);

    if (rotated) {
      rotateAll(0);
      rotated = false;
    } else {
      var dir = runway.getDesignator().substring(0,2);
      int angle = (Integer.parseInt(dir) * 10) - 90;
      rotateAll(angle);
      rotated = true;
    }
  }

  private void rotateAll(int angle) {
    instrumentStrip.setRotate(angle);
    runwayName1.setRotate(-angle);
    runwayName2.setRotate(-angle);
    toraText.setRotate(-angle);
    todaText.setRotate(-angle);
    asdaText.setRotate(-angle);
    ldaText.setRotate(-angle);
  }

  /**
   * Given a runway name, assign that name to the runway and create the other logical runway
   * if appropriate.
   * @param name name of runway
   */
  public void setRunwayName (String name) {
    //Parallel runways
    if (name.length() == 3) {

      String dir = name.substring(0,2);

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

  public Boolean getRotated() {
    return rotated;
  }

  public void setRotated(Boolean rotated) {
    this.rotated = rotated;
  }

  //TODO: THIS \/\/\/
  void updateIndicators() {
    updateTORA(TORA, toraText, toraIndicator, toraBox);
    updateTODA(TODA, todaText, todaIndicator, todaBox);
    updateASDA(ASDA, asdaText, asdaIndicator, asdaBox);
    updateLDA(LDA, ldaText, ldaIndicator, ldaBox);
    stopwayL.setWidth(stopway * Data.getScaling());
    stopwayR.setWidth(stopway * Data.getScaling());

    logger.info(clearway);
    if(away) {
      clearwayR.setWidth(clearway * Data.getScaling());
      clearwayL.setWidth(0);

      direction.setRotate(90);

      displacedThresholdL.setWidth((displacedThreshold * Data.getScaling()));
      displacedThresholdR.setWidth(0);
    } else {
      clearwayL.setWidth(clearway * Data.getScaling());
      clearwayR.setWidth(0);

      direction.setRotate(-90);

      displacedThresholdL.setWidth(0);
      displacedThresholdR.setWidth((displacedThreshold * Data.getScaling()));

    }
  }

  public void updateIndicator(String name, Integer parameter,Label text, Line indicator, HBox box) {
    text.setText(name + " = " + parameter + "m");
    indicator.setEndX(parameter * Data.getScaling());
    box.setTranslateX(box.getTranslateX() - (displacedThreshold * Data.getScaling()));
    if (!away) {
      box.setTranslateX(box.getTranslateX() + (clearway * Data.getScaling()));
    }
  }

  public void updateTORA(Integer parameter,Label text, Line indicator, HBox box) {
    text.setText("TORA = " + parameter + "m");
    indicator.setEndX(parameter * Data.getScaling());
    box.setTranslateX(box.getTranslateX() + ((stopway) * Data.getScaling()));
    if (!away) {
      box.setTranslateX(box.getTranslateX() + (clearway * Data.getScaling()));
    }
  }

  public void updateTODA(Integer parameter,Label text, Line indicator, HBox box) {
    text.setText("TODA = " + parameter + "m");
    indicator.setEndX(parameter * Data.getScaling());
    box.setTranslateX(box.getTranslateX() + ((stopway * Data.getScaling())));
  }

  public void updateASDA(Integer parameter,Label text, Line indicator, HBox box) {
    text.setText("ASDA = " + parameter + "m");
    indicator.setEndX(parameter * Data.getScaling());
    box.setTranslateX(box.getTranslateX() + (( stopway)) * Data.getScaling());
    if (!away) {
      box.setTranslateX(box.getTranslateX() + (clearway * Data.getScaling()));
    }
  }

  public void updateLDA(Integer parameter,Label text, Line indicator, HBox box) {
    text.setText("LDA    = " + parameter + "m");
    indicator.setEndX(parameter * Data.getScaling());
    box.setTranslateX(box.getTranslateX() + ((displacedThreshold) * Data.getScaling()) + ((stopway) * Data.getScaling()));
    if (!away) {
      box.setTranslateX(box.getTranslateX() + (clearway * Data.getScaling()));
    } else {

    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    logger.info("initialising");
  }
}
