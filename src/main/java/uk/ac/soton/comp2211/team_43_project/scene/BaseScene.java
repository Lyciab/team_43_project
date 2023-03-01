package uk.ac.soton.comp2211.team_43_project.scene;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import uk.ac.soton.comp2211.team_43_project.ui.ToolPane;
import uk.ac.soton.comp2211.team_43_project.ui.ToolWindow;

public abstract class BaseScene {

    protected final ToolWindow toolWindow;

    protected ToolPane root;
    protected Scene scene;

    public BaseScene(ToolWindow toolWindow) {this.toolWindow = toolWindow;}

    public abstract void initialise();

    public abstract void build();

    public Scene setScene() {
        var previous = toolWindow.getScene();
        Scene scene = new Scene(root, previous.getWidth(), previous.getHeight(), Color.BLACK);
        scene.getStylesheets().add(getClass().getResource("/style/tool.css").toExternalForm());
        this.scene = scene;
        return scene;
    }

    public Scene getScene() {
        return this.scene;
    }
}
