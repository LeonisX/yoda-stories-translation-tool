package md.leonis.ystt.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import md.leonis.ystt.oldmodel.SaveState;
import md.leonis.ystt.utils.Config;
import md.leonis.ystt.utils.JavaFxUtils;

public class PrimaryPaneController {

    @FXML
    public Button openButton;
    public Button openExeFileButton;

    @FXML
    private void initialize() {

    }

    public void openButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        //fileChooser.setInitialDirectory(new File(workDir));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All saves", "*.sav", "*.ssm"),
                new FileChooser.ExtensionFilter("All files", "*.*")

        );
        Config.saveStateFile = fileChooser.showOpenDialog(openButton.getScene().getWindow());
        //Config.saveStateFile = new File("/home/leonis/ps.sav");
        //TODO check
        //TODO when cancel - error
        try {
            Config.saveState = new SaveState(Config.saveStateFile);
            JavaFxUtils.showPane("SecondaryPane.fxml");
        } catch (Exception e) {
            /*StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));*/
            JavaFxUtils.showAlert("Read Save State Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }

    }

    //TODO create (for concrete emulator),  convert
    public void closeButtonClick() {
        Platform.exit();
    }

    //TODO center button when resize main panel
    public void openExeFileButtonClick() {
        MainPaneController.openFile();
    }
}
