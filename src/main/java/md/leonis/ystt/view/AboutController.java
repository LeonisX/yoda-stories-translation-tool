package md.leonis.ystt.view;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import md.leonis.ystt.utils.JavaFxUtils;

public class AboutController {

    public Button okButton;

    public void okButtonClick() {
        ((Stage) okButton.getScene().getWindow()).close();
    }

    public void emailMouseClicked() {
        JavaFxUtils.openUrl("mailto:tv-games@mail.ru");
    }

    public void siteMouseClicked() {
        JavaFxUtils.openUrl("http://tv-games.ru");
    }
}
