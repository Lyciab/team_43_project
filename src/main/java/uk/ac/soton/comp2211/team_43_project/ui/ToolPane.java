package uk.ac.soton.comp2211.team_43_project.ui;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ToolPane extends StackPane {

    private static final Logger logger = LogManager.getLogger(ToolPane.class);

    private final int width;
    private final int height;
    private final boolean autoScale = true;
    private double scalar = 1;

    public ToolPane(int width, int height) {
        super();
        this.width = width;
        this.height = height;

        getStyleClass().add("toolpane");
        setAlignment(Pos.TOP_LEFT);
    }

    protected void setScalar(double scalar) {
        this.scalar = scalar;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        if (!autoScale) {
            return;
        }

        // Work out the scale factor height and width
        var scaleFactorHeight = getHeight() / height;
        var scaleFactorWidth = getWidth() / width;

        // Work out whether to scale by width or height
        if (scaleFactorHeight > scaleFactorWidth) {
            setScalar(scaleFactorWidth);
        } else {
            setScalar(scaleFactorHeight);
        }

        // Set up the scale
        Scale scale = new Scale(scalar, scalar);

        // Get the parent width and height
        var parentWidth = getWidth();
        var parentHeight = getHeight();

        // Get the padding needed on the top and left
        var paddingLeft = (parentWidth - (width * scalar)) / 2.0;
        var paddingTop = (parentHeight - (height * scalar)) / 2.0;

        // Perform the transformation
        Translate translate = new Translate(paddingLeft, paddingTop);
        scale.setPivotX(0);
        scale.setPivotY(0);
        getTransforms().setAll(translate, scale);
    }
}
