package md.leonis.ystt.view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import md.leonis.ystt.oldmodel.SaveGameStatus;
import md.leonis.ystt.utils.Config;
import md.leonis.ystt.utils.JavaFxUtils;
import md.leonis.ystt.oldmodel.SaveState;

import java.io.IOException;
import java.util.Optional;


//TODO после чтения проверять равенство x, y ,x2, y2

public class SecondaryPaneController {

    @FXML
    public ComboBox languageComboBox;

    @FXML
    public VBox vBox;
    @FXML
    public TextArea textArea;
    @FXML
    public Button createButton;
    @FXML
    public Button editButton;
    @FXML
    public Button restoreButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button eraseButton;
    @FXML
    public Button renameButton;

    private int saveSlotIndex;

    @FXML
    private void initialize() {
        //TODO show data (%, mesetas, roaster, map).
        // if error - show alert

        vBox.getChildren().clear();

        showButtons(SaveGameStatus.DEFAULT);

        createButton.managedProperty().bind(createButton.visibleProperty());
        editButton.managedProperty().bind(editButton.visibleProperty());
        renameButton.managedProperty().bind(renameButton.visibleProperty());
        restoreButton.managedProperty().bind(restoreButton.visibleProperty());
        deleteButton.managedProperty().bind(deleteButton.visibleProperty());
        eraseButton.managedProperty().bind(eraseButton.visibleProperty());

        for (int i = 0; i < 5; i++) {
            Button button = new Button(Config.saveState.getSaveGames()[i].getName());
            button.setUserData(i);
            button.setMinWidth(120);
            button.setOnAction(e -> {
                //TODO %%%%%
                saveSlotIndex = (int) ((Node) e.getSource()).getUserData();
                Config.currentSaveGame = Config.saveState.getSaveGames()[saveSlotIndex];
                String text = "";
                text += Config.currentSaveGame.getName() + " (" + Config.currentSaveGame.getStatus() + ")\n";
                text += "Teammates: " + (Config.currentSaveGame.getCompanionsCount() + 1) + "\n";
                text += "Level: " + Config.currentSaveGame.getHeroes()[0].getLevel() + "\n";
                text += "Mesetas: " + Config.currentSaveGame.getMesetas() + "\n";
                text += "Items: " + Config.currentSaveGame.getItemsCount() + "\n";
                if (Config.currentSaveGame.getGeo().getType() == 0x0D) {
                    text += "Map Id: 0x" + String.format("%02X", Config.currentSaveGame.getGeo().getMapLayer()) + " ";
                    text += String.format("%02X", Config.currentSaveGame.getGeo().getMapId()) + "\n";
                } else {
                    text += "Dungeon Id: 0x" + Integer.toHexString(Config.currentSaveGame.getGeo().getDungeon()) + "\n";
                    text += "Room Id: 0x" + Integer.toHexString(Config.currentSaveGame.getGeo().getRoom()) + "\n";
                }
                textArea.setText(text);
                showButtons(Config.saveState.getSaveGames()[saveSlotIndex].getStatus());
            });
            HBox hBox = new HBox(new Label("Slot #" + (i + 1)), button);
            hBox.setSpacing(5);
            hBox.setMinHeight(30);
            hBox.setAlignment(Pos.CENTER_LEFT);
            switch (Config.saveState.getSaveGames()[i].getStatus()) {
                case DELETED:
                    Font font = button.getFont();
                    //System.out.println(font);
                    button.setStyle("-fx-font: " + (font.getSize() - 1) + " " + font.getFamily() + "; -fx-text-fill: gray");
                    button.setText("deleted");
                    break;
                case ACTIVE:
                    button.setStyle("-fx-font-weight: bold;");
                    break;
                case EMPTY:
                    Font font2 = button.getFont();
                    //System.out.println(font2);
                    button.setStyle("-fx-font: " + (font2.getSize() - 1) + " " + font2.getFamily() + "; -fx-text-fill: gray");
                    button.setText("empty");
                    break;
            }
            vBox.getChildren().add(hBox);
        }
    }

    private void showButtons(SaveGameStatus status) {
        switch (status) {
            case DELETED:
                createButton.setVisible(false);
                editButton.setVisible(false);
                renameButton.setVisible(false);
                restoreButton.setVisible(true);
                deleteButton.setVisible(false);
                eraseButton.setVisible(true);
                break;
            case ACTIVE:
                createButton.setVisible(false);
                editButton.setVisible(true);
                renameButton.setVisible(true);
                restoreButton.setVisible(false);
                deleteButton.setVisible(true);
                eraseButton.setVisible(true);
                break;
            case EMPTY:
                createButton.setVisible(true);
                editButton.setVisible(false);
                renameButton.setVisible(false);
                restoreButton.setVisible(false);
                deleteButton.setVisible(false);
                eraseButton.setVisible(false);
                break;
            case DEFAULT:
                createButton.setVisible(false);
                editButton.setVisible(false);
                renameButton.setVisible(false);
                restoreButton.setVisible(false);
                deleteButton.setVisible(false);
                eraseButton.setVisible(false);
        }
    }

    @FXML
    public void languageComboBoxChange() {
    }

    @FXML
    public void backButtonClick() {
        try {
            Config.saveState.save();
        } catch (IOException e) {
            JavaFxUtils.showAlert("Unable save to file :(", Config.saveStateFile.getAbsolutePath(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void createClick() {
        // Для Гео уже есть метод - дефолтные данные в 0-м гео
    }

    @FXML
    public void editClick() {
        JavaFxUtils.showPane("SaveGamePane.fxml");
    }

    @FXML
    public void restoreClick() {
        showTextInputDialog("Restore save slot",
                "Enter title for save slot",
                "Up to 5 symbols:",
                ""
        ).ifPresent(name -> {
            //write name, reread ROM
            Config.saveState.writeName(saveSlotIndex, name.toUpperCase());
            Config.saveState.getRomData().setBoolean(SaveState.SAVE_GAME_STATUS_OFFSET + saveSlotIndex, true);
            Config.saveState.updateObject();
            initialize();
        });
    }

    @FXML
    public void deleteClick() {
        //erase name, reread ROM
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete " + Config.saveState.getSaveGames()[saveSlotIndex].getName() + " slot?");
        //alert.setContentText();

        if (alert.showAndWait().get() == ButtonType.OK){
            Config.saveState.eraseName(saveSlotIndex);
            Config.saveState.getRomData().setBoolean(SaveState.SAVE_GAME_STATUS_OFFSET + saveSlotIndex, false);
            Config.saveState.updateObject();
            initialize();
        }
    }

    @FXML
    public void eraseClick() {
        //clear name, delete, fill data with zeroes
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Completely erase " + Config.saveState.getSaveGames()[saveSlotIndex].getName() + " slot?");
        //alert.setContentText();

        if (alert.showAndWait().get() == ButtonType.OK){
            Config.saveState.writeName(saveSlotIndex, "");
            Config.saveState.getRomData().setBoolean(SaveState.SAVE_GAME_STATUS_OFFSET + saveSlotIndex, false);
            Config.saveState.clearArea(saveSlotIndex);
            Config.saveState.updateObject();
            initialize();
        }

    }

    @FXML
    public void renameClick() {
        showTextInputDialog("Rename save slot",
                "Enter new save slot title",
                "Up to 5 symbols:",
                Config.saveState.getSaveGames()[saveSlotIndex].getName()
        ).ifPresent(name -> {
            //write name, reread ROM, change flag (for save)
            Config.saveState.writeName(saveSlotIndex, name.toUpperCase());
            Config.saveState.updateObject();
            initialize();
        });
    }

    private Optional<String> showTextInputDialog(String title, String header, String content, String initialText) {
        TextInputDialog dialog = new TextInputDialog(initialText);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        dialog.getEditor().setPrefColumnCount(5);
        dialog.getEditor().addEventFilter(KeyEvent.KEY_TYPED, e -> {
                    String text = e.getCharacter().toUpperCase();
                    boolean result = false;
                    for (int i = 0; i < text.length(); i++) {
                        if (Config.getKeyByValue(text.charAt(i)).isEmpty()) result = true;
                    }
                    if (result || ((TextField) e.getSource()).getText().length() >= 5) {
                        e.consume();
                    }
                }
        );
        return dialog.showAndWait();
    }
}
