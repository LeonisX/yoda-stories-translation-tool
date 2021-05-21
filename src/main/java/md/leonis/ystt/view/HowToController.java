package md.leonis.ystt.view;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import md.leonis.ystt.utils.JavaFxUtils;

public class HowToController {

    public Button okButton;

    public void okButtonClick() {
        ((Stage) okButton.getScene().getWindow()).close();
    }

    public void siteMouseClicked() {
        JavaFxUtils.openUrl("https://github.com/LeonisX/yoda-stories-translation-tool/blob/main/documents/translation-guide.md");
    }
}
