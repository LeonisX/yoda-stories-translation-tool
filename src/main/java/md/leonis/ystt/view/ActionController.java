package md.leonis.ystt.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ActionController {

    public Label zoneLabel;
    public TextArea textArea;
    public Button okButton;

    public ActionController() {
    }

    @FXML
    void initialize() {

    }

    public void okButtonClick() {
        ((Stage) okButton.getScene().getWindow()).close();
    }

    public void setValues(String title, String text) {

        zoneLabel.setText(title);
        textArea.setText(text);
    }
}
