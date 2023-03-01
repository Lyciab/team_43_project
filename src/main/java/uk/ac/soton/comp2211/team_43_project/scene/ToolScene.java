package uk.ac.soton.comp2211.team_43_project.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.ui.ToolPane;
import uk.ac.soton.comp2211.team_43_project.ui.ToolWindow;

public class ToolScene extends BaseScene {

    private static final Logger logger = LogManager.getLogger(ToolScene.class);

    public ToolScene(ToolWindow toolWindow) {
        super(toolWindow);
        logger.info("Creating Tool Scene");
    }

    @Override
    public void build() {
        logger.info("Building " + this.getClass().getName());

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
//        //Setup
//        root = new ToolPane(toolWindow.getWidth(), toolWindow.getHeight());
//
//        var mainPane = new StackPane();
//        mainPane.setMaxWidth(toolWindow.getWidth());
//        mainPane.setMaxHeight(toolWindow.getHeight());
//        mainPane.getStyleClass().add("tool-background");
//        root.getChildren().add(mainPane);
//
//        var subPane = new BorderPane();
//        mainPane.getChildren().add(subPane);
//
//        //Options Pane
//        TabPane options = new TabPane();
//        options.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
//        options.setTabDragPolicy(TabPane.TabDragPolicy.FIXED);
//
//        Tab calculationsTab = new Tab("Calculations");
//        Tab obstaclesTab = new Tab("Edit Obstacles");
//
//
//        options.getTabs().addAll(calculationsTab, obstaclesTab);
//
//        //Calculations Tab
//        var calculationsBox = new VBox();
//        calculationsBox.getStyleClass().add("tab-generic");
//
//        //Obstacles Tab
//        var obstaclesBox = new VBox();
//        obstaclesBox.getStyleClass().add("tab-generic");
//
//
//        //Assign pos
//        calculationsTab.setContent(calculationsBox);
//        obstaclesTab.setContent(obstaclesBox);
//        subPane.setRight(options);


    }

    @Override
    public void initialise() {

    }
}
