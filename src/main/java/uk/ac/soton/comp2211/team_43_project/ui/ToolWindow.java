package uk.ac.soton.comp2211.team_43_project.ui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.App;
import uk.ac.soton.comp2211.team_43_project.scene.BaseScene;
import uk.ac.soton.comp2211.team_43_project.scene.ToolScene;

public class ToolWindow {

  private static final Logger logger = LogManager.getLogger(ToolWindow.class);
  private final int width;
  private final int height;
  private final Stage stage;
  private BaseScene currentScene;
  private Scene scene;

  public ToolWindow(Stage stage, int width, int height) {
    this.width = width;
    this.height = height;

    this.stage = stage;

    setupStage();

    setupResources();

    setupDefaultScene();

    startTool();
  }

  private void setupResources() {
    logger.info("Loading Resources");
  }

  public void startTool() {
    loadScene(new ToolScene(this));
  }

  public void setupStage() {
    stage.setTitle("Runway Redeclaration Tool");
    stage.setMinWidth(width);
    stage.setMinHeight(height + 20);
    stage.setOnCloseRequest(ev -> App.getInstance().shutdown());
  }

  public void loadScene(BaseScene newScene) {
    cleanup();

    newScene.build();
    currentScene = newScene;
    scene = newScene.setScene();
    stage.setScene(scene);

    Platform.runLater(() -> currentScene.initialise());
  }

  public void setupDefaultScene() {
    this.scene = new Scene(new Pane(), width, height, Color.BLACK);
    stage.setScene(this.scene);
  }

  public void cleanup() {
    logger.info("Clearing up previous scene");
//        communicator.clearListeners();
  }

  public Scene getScene() {
    return scene;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

//    getCommunicator() {return communicator;}
}
