package md.leonis.ystt.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import md.leonis.ystt.oldmodel.Geo;
import md.leonis.ystt.utils.Config;
import md.leonis.ystt.utils.JavaFxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SaveGamePaneController {

    @FXML
    public TextField x;
    @FXML
    public TextField y;
    @FXML
    public TextField map;
    @FXML
    public ComboBox<String> transport;
    @FXML
    public TextField animation1;
    @FXML
    public TextField animation2;
    @FXML
    public TextField mesetas;
    @FXML
    public TextField dungeon;
    @FXML
    public TextField room;
    @FXML
    public ComboBox<String> direction;
    @FXML
    public VBox dungeonVBox;
    @FXML
    public VBox cityVBox;
    @FXML
    public ToggleGroup toggleGroup;
    @FXML
    public RadioButton dungeonRadioButton;
    @FXML
    public RadioButton cityRadioButton;
    @FXML
    public GridPane gridPane;
    @FXML
    public Slider colorSlider;
    @FXML
    public Pane colorPane1;
    @FXML
    public Pane colorPane2;
    @FXML
    public ComboBox<String> geoComboBox;
    @FXML
    public FlowPane allItemsPane;
    @FXML
    public FlowPane itemsPane;
    @FXML
    public VBox itemsVBox;
    @FXML
    public ComboBox<String> churchComboBox;
    @FXML
    public VBox heroesVBox;

    private Button plusItemButton;

    private List<String> directionsList = new ArrayList<>(Arrays.asList("North", "East", "South", "West"));
    private List<String> transportList = new ArrayList<>(Arrays.asList("No", "Landrover", "Hovercraft", "Ice Digger"));
    private int[] transportIds = new int[]{0x00, 0x04, 0x08, 0x0C};

    private ObservableList<String> observableDirectionsList = FXCollections.observableList(directionsList);
    private ObservableList<String> observableTransportList = FXCollections.observableList(transportList);
    private ObservableList<String> observableGeosList = FXCollections.observableList(Config.geos.stream().map(Geo::getName).collect(Collectors.toList()));


    @FXML
    private void initialize() {

        //
        // Alisa                            revive heal hire retire/dismiss
        for (int i = 0; i < 4; i++) {
            //System.out.println(currentSaveGame.getHeroes()[i].getBorderPane());
            heroesVBox.getChildren().add(Config.currentSaveGame.getHeroes()[i].getBorderPane());
        }


        //geoComboBox.getScene().lookup()


        //TODO pretty show current map

        geoComboBox.setItems(observableGeosList);
        // TODO Geo - equals()
        //System.out.println(Config.geos.size());
        for (int i = 0; i < Config.geos.size(); i++) {
            boolean flag;
            //System.out.println(i);
            //System.out.println(currentSaveGame.getGeo());
            //System.out.println(Config.geos.get(i));
            //System.out.println(String.format("%02X:%02X", Config.geos.get(i).getMapLayer(), Config.geos.get(i).getMapId()));
            //System.out.println(String.format("%02X-%02X", currentSaveGame.getGeo().getMapLayer(), currentSaveGame.getGeo().getMapId()));
            //System.out.println(String.format("%02X:%02X", Config.geos.get(i).getX(), Config.geos.get(i).getY()));
            //System.out.println(String.format("%02X-%02X", currentSaveGame.getGeo().getX(), currentSaveGame.getGeo().getY()));
            if (Config.currentSaveGame.getGeo().getType() == 0x0B) {
                //System.out.println("OB");
                flag = ((Config.geos.get(i).getDungeon() == Config.currentSaveGame.getGeo().getDungeon())
                        & (Config.geos.get(i).getMapLayer() == Config.currentSaveGame.getGeo().getMapLayer())
                        & (Config.geos.get(i).getMapId() == Config.currentSaveGame.getGeo().getMapId())
                        & (Config.geos.get(i).getX() == Config.currentSaveGame.getGeo().getX())
                        & (Config.geos.get(i).getY() == Config.currentSaveGame.getGeo().getY()));
            } else {
                //System.out.println("OD");
                flag = (Config.geos.get(i).getMapLayer() == Config.currentSaveGame.getGeo().getMapLayer())
                       & (Config.geos.get(i).getMapId() == Config.currentSaveGame.getGeo().getMapId());
            }
            if (flag) {
                geoComboBox.getSelectionModel().select(i);
                System.out.println(i);
                break;
            }
        }

        plusItemButton = new Button("+");
        plusItemButton.setOnAction(e -> addItemsButtonClick());

        plusItemButton.managedProperty().bind(plusItemButton.visibleProperty());
        allItemsPane.managedProperty().bind(allItemsPane.visibleProperty());

        allItemsPane.setVisible(false);

        Font font = plusItemButton.getFont();
        System.out.println(font);
        for (int i = 1; i < Config.items.size(); i++) {
            Button button = new Button(Config.items.get(i));
            button.setUserData(i);
            button.setStyle("-fx-font: " + (font.getSize() - 2) + " " + font.getFamily());
            button.setOnAction(this::addItem);
            allItemsPane.getChildren().add(button);
        }

        Button button = new Button("OK");
        button.setOnAction(e -> okAddItemsButtonClick());
        button.setStyle("-fx-font: " + (font.getSize() - 2) + " " + font.getFamily());
        allItemsPane.getChildren().add(button);


        colorSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return object * 5 + "'";
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        });
        colorPane1.setStyle("-fx-background-color: #00FFFF");
        colorPane2.setStyle("-fx-background-color: #000000");

        cityVBox.managedProperty().bind(cityVBox.visibleProperty());
        dungeonVBox.managedProperty().bind(dungeonVBox.visibleProperty());

        churchComboBox.setItems(FXCollections.observableList(Config.churchs));
        transport.setItems(observableTransportList);

        updateControls();
    }


    private void updateControls() {
        x.setText(String.format("%04X", Config.currentSaveGame.getGeo().getX()));
        y.setText(String.format("%04X", Config.currentSaveGame.getGeo().getY()));
        map.setText(String.format("%02X%02X", Config.currentSaveGame.getGeo().getMapLayer(), Config.currentSaveGame.getGeo().getMapId()));

        for (int i = 0; i < transportIds.length; i++) {
            if (transportIds[i] == Config.currentSaveGame.getGeo().getTransport()) {
                transport.getSelectionModel().select(i);
                break;
            }
        }
        animation1.setText(String.format("%02X", Config.currentSaveGame.getGeo().getAnimation1()));
        animation2.setText(String.format("%02X", Config.currentSaveGame.getGeo().getAnimation2()));

        mesetas.setText(String.valueOf(Config.currentSaveGame.getMesetas()));

        itemsPane.getChildren().clear();
        for (int i = 0; i < Config.currentSaveGame.getItemsCount(); i++) {
            Button button = new Button(Config.items.get(Config.currentSaveGame.getItems()[i]));
            button.setUserData(i);
            button.setOnAction(this::deleteItem);
            itemsPane.getChildren().add(button);
        }
        plusItemButton.setVisible(Config.currentSaveGame.getItemsCount() != Config.currentSaveGame.getItems().length
        && !allItemsPane.isVisible());
        itemsPane.getChildren().add(plusItemButton);

        dungeon.setText(String.format("%02X", Config.currentSaveGame.getGeo().getDungeon()));
        room.setText(String.format("%02X", Config.currentSaveGame.getGeo().getRoom()));

        direction.setItems(observableDirectionsList);
        direction.getSelectionModel().select(Config.currentSaveGame.getGeo().getDirection());

        churchComboBox.getSelectionModel().select(Config.currentSaveGame.getGeo().getChurch());


        if (Config.currentSaveGame.getGeo().getType() == 0x0B) {
            cityRadioButton.setSelected(true);
            cityVBox.setVisible(true);
            //dungeonVBox.setVisible(false);
        } else {
            dungeonRadioButton.setSelected(true);
            //cityVBox.setVisible(false);
            dungeonVBox.setVisible(true);
        }
        //heroes.setText(Arrays.toString(currentSaveGame.getHeroes()));
        //bosses.setText(Arrays.toString(currentSaveGame.getBosses()));
        //events.setText(Arrays.toString(currentSaveGame.getEvents()));
        //chests.setText(Arrays.toString(currentSaveGame.getChests()));
    }

    public void geoComboBoxAction() {
        int index = geoComboBox.getSelectionModel().getSelectedIndex();
        Geo srcGeo = Config.geos.get(index);
        srcGeo.copyDataTo(Config.currentSaveGame.getGeo());
        saveMesetas();
        updateControls();
    }

    public void okButtonClick() {
        save();
        JavaFxUtils.showPane("SecondaryPane.fxml");
    }

    public void saveMesetas() {
        Config.currentSaveGame.setMesetas(Integer.parseInt(mesetas.getText()));
        //System.out.println(currentSaveGame.getMesetas());
        //Config.saveState.updateDump();
    }

    public void save() {
        saveMesetas();
        Config.currentSaveGame.getGeo().setX(Integer.parseInt(x.getText(), 16));
        Config.currentSaveGame.getGeo().setY(Integer.parseInt(y.getText(), 16));
        Config.currentSaveGame.getGeo().setRoom(Integer.parseInt(room.getText(), 16));
        Config.currentSaveGame.getGeo().setDungeon(Integer.parseInt(dungeon.getText(), 16));
        Config.saveState.updateDump();
    }

    public void cancelButtonClick() {
        JavaFxUtils.showPane("SecondaryPane.fxml");
    }


    private void addItem(ActionEvent actionEvent) {
        if (Config.currentSaveGame.getItemsCount() < Config.currentSaveGame.getItems().length) {
            Button button = (Button) actionEvent.getSource();
            Config.currentSaveGame.getItems()[Config.currentSaveGame.getItemsCount()] = (int) button.getUserData();
            Config.currentSaveGame.setItemsCount(Config.currentSaveGame.getItemsCount() + 1);
            updateControls();
        }
    }


    private void deleteItem(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        int index = (int) button.getUserData();
        System.out.println(index);
        Config.currentSaveGame.getItems()[index] = 0;
        System.arraycopy(Config.currentSaveGame.getItems(), index + 1, Config.currentSaveGame.getItems(),
                index, Config.currentSaveGame.getItems().length - 1 - index);
        Config.currentSaveGame.setItemsCount(Config.currentSaveGame.getItemsCount() - 1);
        updateControls();
    }


    private void addItemsButtonClick() {
        allItemsPane.setVisible(true);
        plusItemButton.setVisible(false);
    }

    private void okAddItemsButtonClick() {
        allItemsPane.setVisible(false);
        plusItemButton.setVisible(true);
    }

    public void directionChange() {
        Config.currentSaveGame.getGeo().setDirection(direction.getSelectionModel().getSelectedIndex());
    }

    public void transportChange() {
        Config.currentSaveGame.getGeo().setTransport(transportIds[transport.getSelectionModel().getSelectedIndex()]);
    }

    public void churchComboBoxAction() {
        Config.currentSaveGame.getGeo().setChurch(churchComboBox.getSelectionModel().getSelectedIndex());
    }
}
