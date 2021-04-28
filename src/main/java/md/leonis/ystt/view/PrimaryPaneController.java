package md.leonis.ystt.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryPaneController {

    @FXML
    public Button openExeFileButton;

    @FXML
    private void initialize() {

    }

    //TODO center button when resize main panel
    public void openExeFileButtonClick() {
        MainPaneController.openFile();
    }
}
