package md.leonis.ystt.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import md.leonis.config.Config;
import md.leonis.ystt.MainApp;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JavaFxUtils {

    private static Stage stage;

    private static BorderPane rootLayout;

    private static final int SCENE_WIDTH = 900;
    private static final int SCENE_HEIGHT = 720;

    public static Object currentController;

    public static void showMainPane(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Yoda Stories Translation Tool");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(Config.resourcePath + "MainStage.fxml"));
            rootLayout = loader.load();
            //MainStageController controller = loader.getController();
            Scene scene = new Scene(rootLayout);
            //Scene scene = new Scene(rootLayout, SCENE_WIDTH, SCENE_HEIGHT);
            primaryStage.setScene(scene);

            showPrimaryPanel();

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showPrimaryPanel() {
        showPane("PrimaryPane.fxml");
    }

    public static void showMainPanel() {
        showPane("MainPane.fxml");
    }

    public static void showPane(String resource) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(Config.resourcePath + resource));
            Region innerPanel = loader.load();
            //innerPanel.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
            innerPanel.prefHeightProperty().bind(rootLayout.heightProperty());
            innerPanel.prefWidthProperty().bind(rootLayout.widthProperty());
            currentController = loader.getController();
            //if (controller instanceof SubPane) ((SubPane) controller).init();
            rootLayout.setCenter(innerPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(String title, String header, String text, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);

        TextArea textArea = new TextArea(text);
        //textArea.setEditable(false);
        //textArea.setWrapText(true);

        /*textArea.setMinWidth(720);
        textArea.setMinHeight(600);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);*/

        //alert.getDialogPane().setContent(textArea);

        alert.setResizable(true);
        alert.setContentText(text);
        //alert.setWidth(800);
        alert.getDialogPane().setPrefSize(880, 320);
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        alert.showAndWait();
    }

    public static void showAlert(String title, String header, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    public static void showAlert(String title, Exception exception) {
        showAlert(title, exception.getClass().getName() + ": " + exception.getMessage(), Alert.AlertType.ERROR);
        exception.printStackTrace();
    }

    public static File showBMPLoadDialog(String title, String initialDir, String initialFile) {
        return getFileChooser(title, getBMPExtensionFilters(), initialDir, initialFile).showOpenDialog(JavaFxUtils.getStage());
    }

    public static File showBMPSaveDialog(String title, String initialDir, String initialFile) {
        return getFileChooser(title, getBMPExtensionFilters(), initialDir, initialFile).showSaveDialog(JavaFxUtils.getStage());
    }

    private static List<FileChooser.ExtensionFilter> getBMPExtensionFilters() {
        return Collections.singletonList(new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp"));
    }

    private static FileChooser getFileChooser(String title, List<FileChooser.ExtensionFilter> extensionFilters, String initialDir, String initialFile) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(extensionFilters);
        if (initialDir != null) {
            fileChooser.setInitialDirectory(new File(initialDir));
        }
        fileChooser.setInitialFileName(initialFile);
        fileChooser.setTitle(title);

        return fileChooser;
    }

    public static Stage getStage() {
        return stage;
    }
}
