package md.leonis.ystt;

import javafx.application.Application;
import javafx.stage.Stage;
import md.leonis.config.Config;
import md.leonis.ystt.utils.JavaFxUtils;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Config.loadCRCs();
        Config.loadCharsets();
        //Config.loadLevels();
        //Config.loadProperties();
        Config.loadLanguageTable();
        JavaFxUtils.showMainPane(primaryStage, this);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
