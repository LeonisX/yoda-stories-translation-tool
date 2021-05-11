package md.leonis.ystt.view;

import io.kaitai.struct.ByteBufferKaitaiOutputStream;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
import md.leonis.bin.ByteOrder;
import md.leonis.bin.Dump;
import md.leonis.config.Config;
import md.leonis.ystt.model.Release;
import md.leonis.ystt.model.Section;
import md.leonis.ystt.model.ZoneHistory;
import md.leonis.ystt.model.docx.ImageRecord;
import md.leonis.ystt.model.docx.StringImagesRecord;
import md.leonis.ystt.model.docx.StringRecord;
import md.leonis.ystt.model.docx.WordRecord;
import md.leonis.ystt.model.yodesk.CatalogEntry;
import md.leonis.ystt.model.yodesk.Yodesk;
import md.leonis.ystt.model.yodesk.characters.Character;
import md.leonis.ystt.model.yodesk.characters.CharacterAuxiliary;
import md.leonis.ystt.model.yodesk.characters.CharacterWeapon;
import md.leonis.ystt.model.yodesk.puzzles.PrefixedStr;
import md.leonis.ystt.model.yodesk.puzzles.Puzzle;
import md.leonis.ystt.model.yodesk.puzzles.PuzzleItemClass;
import md.leonis.ystt.model.yodesk.puzzles.StringMeaning;
import md.leonis.ystt.model.yodesk.tiles.TileName;
import md.leonis.ystt.model.yodesk.zones.*;
import md.leonis.ystt.utils.*;
import net.sf.image4j.codec.bmp.BMPImage;
import net.sf.image4j.codec.bmp.BMPReader;
import net.sf.image4j.codec.bmp.BMPWriter;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static md.leonis.config.Config.*;
import static md.leonis.ystt.utils.ImageUtils.readWPicture;

public class MainPaneController {

    public MenuItem openMenuItem;
    public MenuItem saveMenuItem;
    public MenuItem saveAsMenuItem;
    public MenuItem closeMenuItem;
    public MenuItem exitMenuItem;

    public ToggleGroup transparentColorToggleGroup;
    public RadioMenuItem noColorMenuItem;
    public RadioMenuItem fuchsiaMenuItem;
    public RadioMenuItem blackMenuItem;
    public RadioMenuItem whiteMenuItem;

    public MenuItem addTilesMenuItem;

    public MenuItem howToMenuItem;
    public MenuItem aboutMenuItem;

    public Label versionLabel;
    public Label fileSizeLabel;
    public Label crc32Label;
    public Label nameLabel;
    public Label charsetLabel;
    public TableView<CatalogEntry> commonInformationTableView;
    public Button dumpAllSectionsButton;

    public Button saveToBitmapButton;
    public Button loadFromBitmapButton;
    public ImageView titleScreenImageView;
    public Canvas paletteCanvas;
    public Button savePaletteButton;
    public Button dumpPaletteButton;

    public Label soundsCountLabel;
    public Button saveSoundsListToFileButton;
    public TextArea soundsTextArea;

    public Label tilesCountLabel;
    public Button saveTilesToSeparateFiles;
    public CheckBox decimalFilenamesCheckBox;
    public CheckBox hexFilenamesCheckBox;
    public CheckBox groupByAttributesFilenamesCheckBox;
    public Button saveTilesToOneFile;
    public FlowPane tilesFlowPane;
    public TextField tilesInARowTextField;
    public Canvas clipboardCanvas;
    public Rectangle clipboardRectangle;
    public Button loadClipboardImage;
    public Button saveClipboardImage;
    public Button clearClipboardImage;
    public ContextMenu tilesContextMenu;
    public ToggleGroup tilesToggleGroup;
    private final Map<String, RadioMenuItem> tileFlagsMap = new HashMap<>();
    public Button addNewTile;

    private Node currentTile;

    public Label mapsCountLabel;
    public Button saveMapsToFilesButton;
    public CheckBox normalSaveCheckBox;
    public CheckBox groupByFlagsCheckBox;
    public CheckBox groupByPlanetTypeCheckBox;
    public CheckBox dumpActionsCheckBox;
    public CheckBox dumpActionsTextCheckBox;
    public CheckBox saveUnusedTilesCheckBox;
    public ListView<String> mapsListView;
    public Canvas mapCanvas;
    public Button showActionsButton;
    public CheckBox showMapMonstersCheckBox;
    public CheckBox showMapHotspotsCheckBox;
    public Label mapSizeLabel;
    public Label mapTypeLabel;
    public Label mapPlanetLabel;
    public Label mapActionsLabel;
    public Label mapIzaxUnnamedLabel;
    public Label mapZax4Unnamed2Label;
    public FlowPane mapNpcsTilesFlowPane;
    public FlowPane mapGoalTilesFlowPane;
    public FlowPane mapProvidedItemsTilesFlowPane;
    public FlowPane mapRequiredItemsTilesFlowPane;

    public TableView<Zone> mapsTableView;

    public RadioButton topRadioButton;
    public RadioButton middleRadioButton;
    public RadioButton bottomRadioButton;
    public CheckBox topCheckBox;
    public CheckBox middleCheckBox;
    public CheckBox bottomCheckBox;
    public ToggleGroup mapLayerToggleGroup;
    public ListView<String> mapEditorListView;
    public Canvas mapEditorCanvas;
    public ScrollPane mapEditorTilesScrollPane;
    public FlowPane mapEditorTilesFlowPane;
    public Button undoMapEdit;

    public Button dumpActionsTextToDocx;
    public Button loadTranslatedActionsText;
    public Button replaceActionText;
    public CheckBox trimActionsTrailSpacesCheckBox;
    public CheckBox strictActionsReplacingRulesCheckBox;
    public TableView<StringRecord> actionsTextTableView;

    public Label puzzlesCountLabel;
    public Button savePuzzlesToFilesButton;
    public Button dumpPuzzlesTextToDocx;
    public Button loadTranslatedPuzzlesText;
    public Button replacePuzzlesText;

    public CheckBox trimPuzzlesTrailSpacesCheckBox;
    public CheckBox strictPuzzlesReplacingRulesCheckBox;
    public TableView<StringImagesRecord> puzzlesTextTableView;

    public Label charactersCountLabel;
    public Button saveCharactersToFilesButton;
    public Button generateCharactersReportButton;
    public TableView<Character> charactersTableView;

    public Label namesCountLabel;
    public Button saveNamesToFilesButton;
    public TableView<TileName> namesTableView;
    public Button dumpNamesTextToDocx;
    public Button loadTranslatedNamesText;
    public Button replaceNamesText;
    public CheckBox trimNamesSpacesCheckBox;
    public CheckBox strictNamesReplacingRulesCheckBox;
    public TableView<StringImagesRecord> namesTextTableView;

    public TextArea logsTextArea;
    public TextArea hexViewerTextArea;

    public Label statusLabel;

    private static final String OUTPUT = "output";
    private static final String E_BMP = ".bmp";
    private static final String E_DOCX = ".docx";
    private static final String E_XLSX = ".xlsx";
    private static final int TILE_SIZE = 32;

    Path spath, opath;

    //TODO need to clear after DTA load
    public List<Boolean> usedTiles;

    private File clipboardFile;
    private BufferedImage clipboardBufferedImage = new BufferedImage(288, 288, BufferedImage.TYPE_BYTE_INDEXED, icm);

    public static Dump dtaDump;
    public static Dump exeDump;
    public static String exeCrc32;
    public static String dtaCrc32;

    private List<StringRecord> actionTexts;
    private List<StringImagesRecord> puzzlesTexts;
    private List<StringImagesRecord> namesTexts;

    private final Map<Integer, ArrayDeque<ZoneHistory>> zoneHistoryMap = new HashMap<>();

    public MainPaneController() {
        spath = Paths.get(".");
    }

    @FXML
    void initialize() {

        usedTiles = new ArrayList<>(Collections.nCopies(yodesk.getTiles().getTiles().size(), false));

        noColorMenuItem.setUserData(Config.transparentColor);
        fuchsiaMenuItem.setUserData(Color.FUCHSIA);
        blackMenuItem.setUserData(Color.BLACK);
        whiteMenuItem.setUserData(Color.WHITE);

        //TODO log.Clear;

        opath = spath.resolve(OUTPUT + '-' + release.getId());

        try {
            mapTileFlags(tilesContextMenu.getItems());
            updateUI();
        } catch (Exception e) {
            JavaFxUtils.showAlert("UI update error", e);
        }

        //TODO Log.SaveToFile(opath, 'Structure');
    }

    private void mapTileFlags(ObservableList<MenuItem> items) {
        items.forEach(m -> {
            if (m instanceof Menu) {
                mapTileFlags(((Menu) m).getItems());
            } else {
                String userData = (String) m.getUserData();
                if (StringUtils.isNotBlank(userData)) {
                    RadioMenuItem menuItem = (RadioMenuItem) m;
                    //menuItem.setToggleGroup(tilesToggleGroup);
                    tileFlagsMap.put(userData, menuItem);
                }
            }
        });
    }

    private void updateUI() {

        // Common information, sections
        versionLabel.setText(yodesk.getVersion().getVersion());
        nameLabel.setText(release.getTitle());
        fileSizeLabel.setText(dtaDump.size() + " / " + exeDump.size());
        crc32Label.setText(dtaCrc32 + " / " + exeCrc32);
        charsetLabel.setText(release.getCharset());

        commonInformationTableView.setItems(FXCollections.observableList(yodesk.getCatalog()));

        // Title image, palette
        drawTitleImage();
        drawPalette();

        // Sounds
        soundsCountLabel.setText(Integer.toString(yodesk.getSounds().getSounds().size()));
        soundsTextArea.setText(String.join("\n", yodesk.getSounds().getSounds()));

        // Tiles, sprites
        drawTiles();
        updateTilesCount();
        tilesContextMenu.setOnShown(this::selectTileMenuItem);

        // Maps
        mapsCountLabel.setText(Integer.toString(yodesk.getZones().getZones().size()));
        mapsListView.setItems(FXCollections.observableList(yodesk.getZones().getZones().stream().map(m -> "Map #" + m.getIndex()).collect(Collectors.toList())));
        mapsListView.getSelectionModel().selectedItemProperty().addListener(mapsListViewChangeListener);
        mapsListView.getSelectionModel().select(0);
        mapsTableView.setItems(FXCollections.observableList(yodesk.getZones().getZones()));
        mapEditorListView.setItems(FXCollections.observableList(yodesk.getZones().getZones().stream().map(m -> "Map #" + m.getIndex()).collect(Collectors.toList())));
        mapEditorListView.getSelectionModel().selectedItemProperty().addListener(mapsEditorListViewChangeListener);
        mapEditorListView.getSelectionModel().select(0);
        drawMapEditorTiles();

        actionTexts = getActionsTexts();
        actionsTextTableView.setItems(FXCollections.observableList(actionTexts));

        // Puzzles
        puzzlesCountLabel.setText(Integer.toString(yodesk.getPuzzles().getPuzzles().size()));
        puzzlesTexts = getPuzzlesTexts();
        puzzlesTextTableView.setItems(FXCollections.observableList(puzzlesTexts));

        // Characters
        charactersCountLabel.setText(Integer.toString(yodesk.getCharacters().getCharacters().size()));
        @SuppressWarnings("all")
        TableColumn<Character, List<Integer>> charsColumn = (TableColumn<Character, List<Integer>>) charactersTableView.getColumns().get(1);
        charsColumn.setCellFactory(c -> {

            Canvas canvas = new Canvas();

            TableCell<Character, List<Integer>> cell = new TableCell<Character, List<Integer>>() {
                public void updateItem(List<Integer> tileIds, boolean empty) {
                    if (tileIds != null) {
                        canvas.setWidth(tileIds.size() * TILE_SIZE);
                        canvas.setHeight(TILE_SIZE);
                        for (int i = 0; i < tileIds.size(); i++) {
                            drawTileOnCanvas(tileIds.get(i), canvas, i * TILE_SIZE, 0, null);
                        }
                    } else {
                        canvas.setWidth(0);
                    }
                }
            };
            cell.setGraphic(canvas);
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        charactersTableView.setItems(FXCollections.observableList(yodesk.getCharacters().getFilteredCharacters()));

        // Names
        namesCountLabel.setText(Integer.toString(yodesk.getTileNames().getNames().size()));
        @SuppressWarnings("all")
        TableColumn<TileName, Integer> namesColumn = (TableColumn<TileName, Integer>) namesTableView.getColumns().get(0);
        namesColumn.setCellFactory(c -> {

            final ImageView imageView = new ImageView();

            TableCell<TileName, Integer> cell = new TableCell<TileName, Integer>() {
                public void updateItem(Integer tileId, boolean empty) {
                    if (tileId != null && !tileId.equals(0xFFFF)) {
                        imageView.setImage(drawTileOnImage(tileId));
                    } else {
                        imageView.setImage(null);
                    }
                }
            };
            cell.setGraphic(imageView);
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        namesTableView.setItems(FXCollections.observableList(yodesk.getTileNames().getFilteredNames()));

        namesTexts = getNamesTexts();
        namesTextTableView.setItems(FXCollections.observableList(namesTexts));
    }

    private void updateTilesCount() {
        tilesCountLabel.setText(Integer.toString(yodesk.getTiles().getTiles().size()));
    }

    private void drawTitleImage() {

        try {
            WritableImage image = readWPicture(yodesk.getStartupImage().getPixels(), 0, 288, 288, transparentColor);
            titleScreenImageView.setImage(image);
        } catch (Exception e) {
            JavaFxUtils.showAlert("Title screen display error", e);
        }
    }

    private void selectTileMenuItem(WindowEvent event) {
        String flag = getTileFlag(currentTile);
        tileFlagsMap.get(flag).setSelected(true);
    }

    private void drawPalette() {

        try {
            ImageUtils.drawPalette(paletteCanvas);
        } catch (Exception e) {
            JavaFxUtils.showAlert("Palette display error", e);
        }
    }

    private void drawTiles() {

        try {
            tilesFlowPane.getChildren().clear();
            for (int i = 0; i < yodesk.getTiles().getTiles().size(); i++) {
                tilesFlowPane.getChildren().add(newTile(i, true));
            }
            tilesFlowPane.getChildren().add(addNewTile);
        } catch (Exception e) {
            JavaFxUtils.showAlert("Tiles display error", e);
        }
    }

    private void drawTilesOnFlowPane(FlowPane flowPane, List<Integer> tileIds) {

        flowPane.getChildren().clear();
        for (Integer tileId : tileIds) {
            flowPane.getChildren().add(newTile(tileId, true));
        }
    }

    public void addNewTileClick() {

        try {
            yodesk.getTiles().addTile();
            int tileId = yodesk.getTiles().getTiles().size() - 1;
            tilesFlowPane.getChildren().add(tileId, newTile(tileId, true));
            mapEditorTilesFlowPane.getChildren().add(tileId, newTile(tileId, false));
            usedTiles.add(false);
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error adding tiles", e);
        }
    }

    private ImageView newTile(int tileId, boolean contextMenu) {

        ImageView image = new ImageView(drawTileOnImage(tileId));
        image.setUserData(tileId);
        image.setOnMouseEntered(mouseEnteredHandler);
        image.setOnMouseExited(mouseExitedHandler);
        if (contextMenu) {
            image.setOnContextMenuRequested(e -> tilesContextMenu.show((Node) e.getSource(), e.getScreenX(), e.getScreenY()));
        }

        image.setOnDragDetected(event -> {
            Dragboard db = image.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(Integer.toString(tileId));
            db.setContent(content);
            event.consume();
        });
        image.setOnDragOver(event -> {
            if (event.getGestureSource() != image && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        image.setOnDragEntered(event -> {
            if (event.getGestureSource() != image && event.getDragboard().hasString()) {
                moveClipboardRectangle(event.getX(), event.getY());
                clipboardRectangle.setVisible(false);
            }
            event.consume();
        });
        image.setOnDragExited(event -> {
            moveClipboardRectangle(event.getX(), event.getY());
            event.consume();
        });
        image.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                String[] chunks = db.getString().split("\\|");
                int x = Integer.parseInt(chunks[0]);
                int y = Integer.parseInt(chunks[1]);
                WritableImage wi = ImageUtils.snapshot(clipboardCanvas, x, y, TILE_SIZE, TILE_SIZE);
                image.setImage(wi);
                ((ImageView) mapEditorTilesFlowPane.getChildren().get(tileId)).setImage(wi);
                yodesk.getTiles().replaceTile(tileId, ImageUtils.getBytes(wi, transparentColor));
            }
            event.setDropCompleted(true);

            event.consume();
        });
        return image;
    }

    public void clipboardCanvasDragDetected(MouseEvent event) {

        Dragboard db = clipboardCanvas.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();

        int x = ((int) event.getX()) & 0xFFFFFFE0;
        int y = ((int) event.getY()) & 0xFFFFFFE0;

        content.putString(String.format("%s|%s", x, y));
        db.setContent(content);
        event.consume();
    }

    public void clipboardCanvasDragOver(DragEvent event) {

        if (event.getGestureSource() != clipboardCanvas && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    public void clipboardCanvasDragEntered(DragEvent event) {

        if (event.getGestureSource() != clipboardCanvas && event.getDragboard().hasString()) {
            moveClipboardRectangle(event.getX(), event.getY());
            clipboardRectangle.setVisible(false);
        }
        event.consume();
    }

    public void clipboardCanvasDragExited(DragEvent event) {

        moveClipboardRectangle(event.getX(), event.getY());
        event.consume();
    }

    public void clipboardCanvasDragDropped(DragEvent event) {

        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            int tileId = Integer.parseInt(db.getString());
            int x = ((int) event.getX()) & 0xFFFFFFE0;
            int y = ((int) event.getY()) & 0xFFFFFFE0;
            drawTileOnBufferedImage(tileId, clipboardBufferedImage, x, y);
            ImageUtils.drawOnCanvas(clipboardBufferedImage, clipboardCanvas, transparentColor);
            success = true;
        }
        event.setDropCompleted(success);

        event.consume();
    }

    private void drawMapEditorTiles() {

        try {
            for (int i = 0; i < yodesk.getTiles().getTiles().size(); i++) {
                ImageView image = new ImageView(drawTileOnImage(i));
                image.setUserData(i);
                image.setOnMouseEntered(mouseEnteredHandler);
                image.setOnMouseExited(mouseExitedHandler);
                //TODO need here????
                image.setOnContextMenuRequested(e -> tilesContextMenu.show((Node) e.getSource(), e.getScreenX(), e.getScreenY()));

                int finalI = i;
                image.setOnDragDetected(event -> {
                    Dragboard db = image.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(Integer.toString(finalI));
                    db.setContent(content);
                    event.consume();
                });

                mapEditorTilesFlowPane.getChildren().add(image);
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("Tiles display error", e);
        }
    }

    EventHandler<MouseEvent> mouseEnteredHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {

            currentTile = (Node) mouseEvent.getSource();
            int id = ((Integer) (currentTile).getUserData());
            String flag = getTileFlag(currentTile);
            SetFlagMenuItem(flag);
            statusLabel.setText("Tile #" + id + ": " + GetFlagDescription(flag));
        }
    };

    //TODO repair this
    private String getTileFlag(Node tile) {
        int id = ((Integer) (tile).getUserData());
        //return section.GetTileFlagS(id);
        return "TODO repair me";
    }

    EventHandler<MouseEvent> mouseExitedHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            currentTile = null;
            statusLabel.setText("");
        }
    };

    //TODO repair me
    private String GetFlagDescription(String flag) {
        //return tileFlagsMap.get(flag).getText();
        return "TODO repair me";
    }

    //TODO select flag in context menu; may be not need
    private void SetFlagMenuItem(String flag) {
    /*
    var i: Word;
    m: TMenuItem;

  for i := 0 to ComponentCount - 1 do
            if Components[i] is TMenuItem then
            begin
    m := Components[i] as TMenuItem;
      if m.GroupIndex = 7 then
            begin
    m.Checked := false;
        if m.Tag = flag then m.Checked := true;
        if (flag = 2147483680) and (m.Tag = 2000000000) then m.Checked := true;
    end;
    end;*/
    }

    ChangeListener<String> mapsListViewChangeListener = new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (mapsListView.getSelectionModel().getSelectedIndex() >= 0) {
                undoMapEdit.setVisible(null != zoneHistoryMap.get(drawViewZone()));
            }
        }
    };

    public int drawViewZone() {

        int zoneId = mapsListView.getSelectionModel().getSelectedIndex();

        drawViewMap(zoneId);
        showMapInfo(zoneId);

        return zoneId;
    }


    private void showMapInfo(int zoneId) {

        Zone zone = yodesk.getZones().getZones().get(zoneId);

        mapSizeLabel.setText(zone.getWidth() + "x" + zone.getHeight());
        mapTypeLabel.setText(zone.getType().name());
        mapPlanetLabel.setText(zone.getPlanet().name());
        mapActionsLabel.setText(String.valueOf(zone.getActions().size()));

        mapIzaxUnnamedLabel.setText(String.valueOf(zone.getIzax().get_unnamed2()));
        mapZax4Unnamed2Label.setText(String.valueOf(zone.getIzx4().get_unnamed2()));

        drawTilesOnFlowPane(mapNpcsTilesFlowPane, zone.getIzx3().getNpcs());
        drawTilesOnFlowPane(mapGoalTilesFlowPane, zone.getIzax().getGoalItems());
        drawTilesOnFlowPane(mapProvidedItemsTilesFlowPane, zone.getIzx2().getProvidedItems());
        drawTilesOnFlowPane(mapRequiredItemsTilesFlowPane, zone.getIzax().getRequiredItems());

        //TODO ArrayList<Action> actions;
    }

    private void drawViewMap(int zoneId) {
        try {
            ReadMap(mapCanvas, zoneId, true, normalSaveCheckBox.isSelected() || groupByFlagsCheckBox.isSelected() || groupByPlanetTypeCheckBox.isSelected());
        } catch (Exception e) {
            JavaFxUtils.showAlert(String.format("Map %s display error", zoneId), e);
        }
    }

    ChangeListener<String> mapsEditorListViewChangeListener = new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (getEditorZoneId() >= 0) {
                int zoneId = getEditorZoneId();
                drawEditorMap(zoneId);
                mapEditorTilesScrollPane.setLayoutX(mapEditorCanvas.getLayoutX() + mapEditorCanvas.getWidth() + 8);
                undoMapEdit.setVisible(null != zoneHistoryMap.get(zoneId));
            }
        }
    };

    public void showActionsButtonClick() {

        Zone zone = yodesk.getZones().getZones().get(mapsListView.getSelectionModel().getSelectedIndex());
        List<Action> actions = zone.getActions();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < actions.size(); i++) {
            Action a = actions.get(i);
            sb.append("Action ").append(i).append("\n");
            sb.append("if").append("\n");
            a.getConditions().forEach(c -> {
                sb.append("    ").append(c.getOpcode().name().toLowerCase().replace("_", "-")).append(": ");
                sb.append(c.getArguments().stream().map(Object::toString).collect(Collectors.joining(" "))).append("\n");
            });
            sb.append("then").append("\n");
            a.getInstructions().forEach(c -> {
                sb.append("    ").append(c.getOpcode().name().toLowerCase().replace("_", "-")).append(": ");
                sb.append(c.getArguments().stream().map(Object::toString).collect(Collectors.joining(" ")));
                sb.append(" \"").append(c.getText()).append("\"").append("\n");
            });
            sb.append("\n");
        }

        JavaFxUtils.showActionsWindow("Zone #" + zone.getIndex() + " actions", sb.toString());
    }

    private void drawEditorMap(int zoneId) {
        try {
            //ReadIZON(id, false);
            ReadMap(mapEditorCanvas, zoneId, true, normalSaveCheckBox.isSelected() || groupByFlagsCheckBox.isSelected() || groupByPlanetTypeCheckBox.isSelected());
        } catch (Exception e) {
            JavaFxUtils.showAlert(String.format("Map %s display error", zoneId), e);
        }
    }

    public void mapEditorCanvasDragDetected(MouseEvent event) {

        Dragboard db = mapEditorCanvas.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();

        int x = ((int) event.getX()) & 0xFFFFFFE0;
        int y = ((int) event.getY()) & 0xFFFFFFE0;

        content.putString(String.format("%s|%s", x, y));
        db.setContent(content);
        event.consume();
    }

    public void mapEditorCanvasDragOver(DragEvent event) {

        if (event.getGestureSource() != mapEditorCanvas && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    public void mapEditorCanvasDragEntered(DragEvent event) {

        if (event.getGestureSource() != mapEditorCanvas && event.getDragboard().hasString()) {
            moveClipboardRectangle(event.getX(), event.getY());
            clipboardRectangle.setVisible(false);
        }
        event.consume();
    }

    public void mapEditorCanvasDragExited(DragEvent event) {

        moveClipboardRectangle(event.getX(), event.getY());
        event.consume();
    }

    public void mapEditorCanvasDragDropped(DragEvent event) {

        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            int tileId = Integer.parseInt(db.getString());
            int x = ((int) event.getX()) / TILE_SIZE;
            int y = ((int) event.getY()) / TILE_SIZE;

            int layerId = Integer.parseInt((String) mapLayerToggleGroup.getSelectedToggle().getUserData());

            Zone zone = getEditorZone();
            int oldValue = zone.getTileIds().get(y * zone.getWidth() + x).getColumn().get(layerId);

            modifyZoneSpot(zone.getIndex(), x, y, layerId, tileId);

            addToMapsHistory(zone.getIndex(), x, y, layerId, oldValue, tileId);

            undoMapEdit.setVisible(true);
        }
        event.setDropCompleted(true);

        event.consume();
    }

    private void addToMapsHistory(int zoneId, int x, int y, int layerId, int oldValue, int newValue) {

        ArrayDeque<ZoneHistory> histories = zoneHistoryMap.get(zoneId);
        if (null == histories) {
            histories = new ArrayDeque<>();
        }

        histories.add(new ZoneHistory(zoneId, x, y, layerId, oldValue, newValue));
        zoneHistoryMap.put(zoneId, histories);
    }

    public void undoMapEditClick() {

        ArrayDeque<ZoneHistory> histories = zoneHistoryMap.get(getEditorZoneId());

        int zoneId = getEditorZoneId();
        ZoneHistory history = zoneHistoryMap.get(zoneId).pollLast();

        modifyZoneSpot(zoneId, history.getX(), history.getY(), history.getLayerId(), history.getOldValue());

        if (histories.isEmpty()) {
            zoneHistoryMap.remove(zoneId);
            undoMapEdit.setVisible(false);
        }
    }

    private void modifyZoneSpot(int zoneId, int x, int y, int layerId, int tileId) {

        Zone zone = yodesk.getZones().getZones().get(zoneId);
        List<Integer> column = zone.getTileIds().get(y * zone.getWidth() + x).getColumn();
        column.set(layerId, tileId);

        drawZoneSpot(mapEditorCanvas, zone, x, y);
    }


    //TODO refactor this
    private void ReadMap(Canvas canvas, int zoneId, boolean show, boolean save) throws IOException {

        Zone zone = yodesk.getZones().getZones().get(zoneId);

        //TODO offset if need
        //Log.debug("Map #" + zoneId + " offset: " + "section.intToHex(section.GetPosition(), 8)");
        statusLabel.setText("Map: " + zoneId + " (" + zone.getPlanet().name() + ")");

        if (show) {
            canvas.setWidth(zone.getWidth() * TILE_SIZE);
            canvas.setHeight(zone.getHeight() * TILE_SIZE);

            /*canvas.getGraphicsContext2D().setFill(transparentColor);
            canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());*/

            drawZone(canvas, zone);
        }
        // TODO Application.ProcessMessages;

        if (normalSaveCheckBox.isSelected() && save) {
            Path path = opath.resolve("Maps");
            IOUtils.createDirectories(path);
            BMPWriter.write8bit(canvas, path.resolve(StringUtils.leftPad(Integer.toString(zoneId), 3, '0') + E_BMP));
        }

        if (groupByFlagsCheckBox.isSelected() && save) {
            Path path = opath.resolve("MapsByFlags").resolve(zone.getType().name());
            IOUtils.createDirectories(path);
            BMPWriter.write8bit(canvas, path.resolve(StringUtils.leftPad(Integer.toString(zoneId), 3, '0') + E_BMP));
        }

        if (groupByPlanetTypeCheckBox.isSelected() && save) {
            Path path = opath.resolve("MapsByPlanetType").resolve(zone.getPlanet().name());
            IOUtils.createDirectories(path);
            BMPWriter.write8bit(canvas, path.resolve(StringUtils.leftPad(Integer.toString(zoneId), 3, '0') + E_BMP));
        }
    }

    private void drawZone(Canvas canvas, int zoneId) {
        drawZone(canvas, yodesk.getZones().getZones().get(zoneId));
    }

    private void drawZone(Canvas canvas, Zone zone) {

        if (bottomCheckBox.isSelected()) {
            drawZoneLayer(canvas, zone, 0);
        }
        if (middleCheckBox.isSelected()) {
            drawZoneLayer(canvas, zone, 1);
        }
        if (showMapMonstersCheckBox.isSelected()) {
            zone.getIzax().getMonsters().forEach(m -> {
                    int tileId = yodesk.getCharacters().getCharacters().get(m.getCharacter()).getTileIds().get(0);
                    drawTileOnCanvas(tileId, canvas, m.getX() * TILE_SIZE, m.getY() * TILE_SIZE, null);
                    drawBorderOnCanvas(canvas, m.getX() * TILE_SIZE, m.getY() * TILE_SIZE, Color.rgb(127, 255, 255));
            });
        }
        if (topCheckBox.isSelected()) {
            drawZoneLayer(canvas, zone, 2);
        }
        if (showMapHotspotsCheckBox.isSelected()) {
            zone.getHotspots().forEach(h -> drawBorderOnCanvas(canvas, h.getX() * TILE_SIZE, h.getY() * TILE_SIZE, Color.FUCHSIA));
        }
    }

    private void drawZoneLayer(Canvas canvas, Zone zone, int layerId) {

        for (int y = 0; y < zone.getHeight(); y++) {
            for (int x = 0; x < zone.getWidth(); x++) {
                drawTileOnMap(canvas, zone, x, y, layerId);
            }
        }
    }

    private void drawZoneSpot(Canvas canvas, Zone zone, int x, int y) {

        fillZoneTile(canvas, x * TILE_SIZE, y * TILE_SIZE, transparentColor);

        if (bottomCheckBox.isSelected()) {
            drawTileOnMap(canvas, zone, x, y, 0);
        }
        if (middleCheckBox.isSelected()) {
            drawTileOnMap(canvas, zone, x, y, 1);
        }
        if (showMapMonstersCheckBox.isSelected()) {
            zone.getIzax().getMonsters().forEach(m -> {
                if (m.getX() == x && m.getY() == y) {
                    int tileId = yodesk.getCharacters().getCharacters().get(m.getCharacter()).getTileIds().get(0);
                    drawTileOnCanvas(tileId, canvas, m.getX() * TILE_SIZE, m.getY() * TILE_SIZE, null);
                    drawBorderOnCanvas(canvas, m.getX() * TILE_SIZE, m.getY() * TILE_SIZE, Color.rgb(127, 255, 255));
                }
            });
        }
        if (topCheckBox.isSelected()) {
            drawTileOnMap(canvas, zone, x, y, 2);
        }
        if (showMapHotspotsCheckBox.isSelected()) {
            zone.getHotspots().forEach(h -> {
                if (h.getX() == x && h.getY() == y) {
                    drawBorderOnCanvas(canvas, h.getX() * TILE_SIZE, h.getY() * TILE_SIZE, Color.rgb(255, 0, 255));
                }
            });
        }
    }

    private void drawTileOnMap(Canvas canvas, Zone zone, int x, int y, int layerId) {

        int tileId = zone.getTileIds().get(y * zone.getWidth() + x).getColumn().get(layerId);
        if (tileId < yodesk.getTiles().getTiles().size()) {
            usedTiles.set(tileId, true);
            drawTileOnCanvas(tileId, canvas, x * TILE_SIZE, y * TILE_SIZE, null);
        }
    }

    public void layerCheckBoxClick() {
        drawZone(mapEditorCanvas, getEditorZone());
    }

    private Zone getEditorZone() {
        return yodesk.getZones().getZones().get(getEditorZoneId());
    }

    private int getEditorZoneId() {
        return mapEditorListView.getSelectionModel().getSelectedIndex();
    }

    private void ReadIZON(int zoneId, boolean save) throws IOException {

        ReadMap(mapCanvas, zoneId, normalSaveCheckBox.isSelected() || groupByFlagsCheckBox.isSelected() || groupByPlanetTypeCheckBox.isSelected(), save);

        //TODO
        //MapProgressBar.Position :=id;
        //MapProgressLabel.Caption :=Format('%.2f %%',[((id + 1) / section.mapsCount) * 100]);
        //TODO Application.ProcessMessages;

        //TODO not need, commented in Delphi
        //k:=DTA.ReadWord;                             //2 bytes: object info entry count (X)
        //DTA.MoveIndex(k * 12);                       //X*12 bytes: object info data

        //TODO return after implement write() methods
        /*if (dumpActionsCheckBox.isSelected() || dumpTextCheckBox.isSelected()) {
            ReadIZAX(id);
            ReadIZX2(id);
            ReadIZX3(id);
            ReadIZX4(id);
            ReadIACT(id);
            ReadOIE(id);
        }*/
    }

    /*private void ReadIZAX(int id) throws IOException {
        Path path = opath.resolve("IZAX").resolve(Integer.toString(id));
        DumpData(path, section.maps.get(id).getIzax().getPosition(), section.maps.get(id).getIzax().getSize());
    }

    private void ReadIZX2(int id) throws IOException {
        Path path = opath.resolve("IZX2").resolve(Integer.toString(id));
        DumpData(path, section.maps.get(id).getIzx2().getPosition(), section.maps.get(id).getIzx2().getSize());
    }

    private void ReadIZX3(int id) throws IOException {
        Path path = opath.resolve("IZX3").resolve(Integer.toString(id));
        DumpData(path, section.maps.get(id).getIzx3().getPosition(), section.maps.get(id).getIzx3().getSize());
    }

    private void ReadIZX4(int id) throws IOException {
        Path path = opath.resolve("IZX4").resolve(Integer.toString(id));
        DumpData(path, section.maps.get(id).getIzx4().getPosition(), section.maps.get(id).getIzx4().getSize());
    }*/

    /*private void ReadIACT(int id) throws IOException {
        int k = 0;
        section.SetPosition(section.maps.get(id).getIactPosition());

        while (section.ReadString(4).equals("IACT")) {
//    HEX.SetSelStart(DTA.GetIndex);
//    HEX.SetSelEnd(DTA.GetIndex + 3);
//    HEX.CenterCursorPosition;
//    Application.ProcessMessages;
            int size = (int) section.ReadLongWord();  //4 bytes: length (X)
            if (dumpActionsCheckBox.isSelected()) {
                String fileName = StringUtils.leftPad("" + id, 3, "0") + '-' + StringUtils.leftPad("" + k, 3, "0");
                Path path = opath.resolve("IACT").resolve(fileName);
                DumpData(path, section.GetPosition(), size);
            } else {
                section.MovePosition(size);
            }
            k++;
        }

    }*/

    /*private void ReadOIE(int id) throws IOException {
        Path path = opath.resolve("OIE").resolve(Integer.toString(id));
        DumpData(path, section.maps.get(id).getOie().getPosition(), section.maps.get(id).getOie().getSize());
    }*/

    private void DumpData(Path path, CatalogEntry entry) throws IOException {
        IOUtils.saveBytes(path, entry.getBytes());
    }

    //TODO repair me
    private void DumpData(Path path, int offset, int size) throws IOException {
        /*byte[] array = Arrays.copyOfRange(section.dump.getDump(), offset, offset + size);
        IOUtils.saveBytes(path, array);
        section.SetPosition(offset + size);*/
    }


    public void openMenuItemClick() {
        //TODO notify if changed
        //TODO clear if need
        openFile();
    }

    public static void openFile() {

        try {
            //TODO remove hardcoded
            File file = JavaFxUtils.showEXELoadDialog("Open Executable File", "D:\\Working\\_Yoda\\YExplorer\\other\\Yoda Stories (14.02.1997)", "yodesk.dta");

            if (file != null) {
                exeFile = file;
                dtaFile = IOUtils.getDtaPath(file.toPath().getParent());
                loadFileToArray(file);
                yodesk = Yodesk.fromFile(dtaFile.toString(), release.getCharset());

                JavaFxUtils.showMainPanel();
            } else {
                exeFile = null;
                dtaFile = null;
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("Executable file loading error", e);
        }
    }

    public void saveMenuItemClick() {
        try {
            String[] chunks = dtaFile.getFileName().toString().split("\\.");
            IOUtils.copyFile(dtaFile, dtaFile.getParent().resolve(chunks[0] + ".bak"));
            yodesk.write(new ByteBufferKaitaiOutputStream(dtaFile));
            //TODO confirmation
        } catch (Exception e) {
            JavaFxUtils.showAlert("DTA file saving error", e);
        }
    }

    public void saveAsMenuItemClick() {
        try {
            //TODO remove hardcoded
            File file = JavaFxUtils.showDTASaveDialog("Save DTA File", "D:\\Working\\_Yoda\\YExplorer\\other\\Yoda Stories (14.02.1997)", "yodesk.dta");

            if (file != null) {
                yodesk.write(new ByteBufferKaitaiOutputStream(file));
                //TODO confirmation
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("DTA file saving error", e);
        }
    }

    public void closeMenuItemClick() {
        JavaFxUtils.showPrimaryPanel();
    }

    public void exitMenuItemClick() {

        //TODO warning if modified
        Platform.exit();
    }

    public void transparentColorMenuItemClick() {

        Config.transparentColor = (Color) transparentColorToggleGroup.getSelectedToggle().getUserData();
        Config.updatePalette();
        ObservableList<Node> children = tilesFlowPane.getChildren();
        for (int i = 0; i < children.size() - 1; i++) {
            ImageView imageView = (ImageView) children.get(i);
            imageView.setImage(drawTileOnImage(i));
        }
        children = mapEditorTilesFlowPane.getChildren();
        for (int i = 0; i < children.size() - 1; i++) {
            ImageView imageView = (ImageView) children.get(i);
            imageView.setImage(drawTileOnImage(i));
        }
        drawTitleImage();
        clipboardBufferedImage = ImageUtils.replaceIcm(clipboardBufferedImage, icm);
        ImageUtils.drawOnCanvas(clipboardBufferedImage, clipboardCanvas, transparentColor);
        drawZone(mapCanvas, mapsListView.getSelectionModel().getSelectedIndex());
        drawZone(mapEditorCanvas, mapEditorListView.getSelectionModel().getSelectedIndex());
        namesTableView.refresh();
    }

    //TODO
    public void addTilesMenuItemClick() {
    }

    //TODO
    public void howToMenuItemClick() {
    }

    //TODO
    public void aboutMenuItemClick() {
    }

    public void dumpAllSectionsButtonClick() {

        try {
            Path path = opath.resolve("Dumps");
            IOUtils.createDirectories(path);
            for (CatalogEntry entry : yodesk.getCatalog()) {
                Section name = entry.getSection();
                DumpData(path.resolve(name + ".bin"), entry);
                Log.debug(name + " is saved");
            }
            // TODO Showmessage('OK');
        } catch (Exception e) {
            JavaFxUtils.showAlert("Sections dumping error", e);
        }
    }

    public void saveToBitmapButtonClick() {

        // TODO Log.Clear;
        // TODO IOUtils.createDirectories("");
        // TODO Log.Debug('Title screen saved')

        try {
            Path path = opath.resolve("stup" + E_BMP);
            if (titleScreenImageView.getUserData() != null) {
                BMPWriter.write((BMPImage) titleScreenImageView.getUserData(), path);
            } else {
                BMPWriter.write8bit(titleScreenImageView, path.toFile());
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("Title screen saving error", e);
        }
    }

    public void loadFromBitmapButtonClick() {

        try {
            BMPImage titleImage = BMPReader.readExt(opath.resolve("stup" + E_BMP));

            // Update in memory
            for (int y = 0; y < titleImage.getHeight(); y++) {
                for (int x = 0; x < titleImage.getWidth(); x++) {

                    int sample = titleImage.getImage().getRaster().getSample(x, y, 0);
                    //TODO need to test this
                    yodesk.getStartupImage().getPixels()[y * titleImage.getWidth() + x] = new Integer(sample).byteValue();
                }
            }

            //TODO notify if not indexed
            WritableImage image = new WritableImage(titleImage.getWidth(), titleImage.getHeight());
            SwingFXUtils.toFXImage(titleImage.getImage(), image);
            titleScreenImageView.setImage(image);
            titleScreenImageView.setUserData(titleImage);
        } catch (Exception e) {
            JavaFxUtils.showAlert("Title screen loading error", e);
        }
    }

    public void savePaletteButtonButtonClick() {
        try {
            BMPWriter.write8bit(paletteCanvas, opath.resolve("palette" + E_BMP));
        } catch (Exception e) {
            JavaFxUtils.showAlert("Palette loading error", e);
        }
    }

    public void dumpPaletteButtonButtonClick() {
        try {
            PaletteUtils.saveToFile(gamePalette, opath.resolve("palette.pal"));
        } catch (Exception e) {
            JavaFxUtils.showAlert("Palette saving error", e);
        }
    }

    public void saveSoundsListToFileButtonClick() {
        try {
            IOUtils.saveTextFile(yodesk.getSounds().getSounds(), opath.resolve("snds.txt"));
        } catch (Exception e) {
            JavaFxUtils.showAlert("Sounds list saving error", e);
        }
    }

    //TODO need refactor
    public void saveTilesToSeparateFilesClick() {

        try {
            //TilesProgressBar.Position := 0;
            //TilesProgressBar.Max := DTA.tilesCount;
            Path tilesPath = opath.resolve("Tiles");
            Path hexPath = opath.resolve("TilesHex");
            Path attrPath = opath.resolve("TilesByAttr");
            //Log.clear();
            if (decimalFilenamesCheckBox.isSelected()) {
                IOUtils.createDirectories(tilesPath);
            }
            if (hexFilenamesCheckBox.isSelected()) {
                IOUtils.createDirectories(hexPath);
            }
            if (groupByAttributesFilenamesCheckBox.isSelected()) {
                IOUtils.createDirectories(attrPath);
            }

            for (int i = 0; i < yodesk.getTiles().getTiles().size(); i++) {

                byte[] attr = yodesk.getTiles().getTiles().get(i).get_raw_attributes(); // attributes
                String attrHex = new BigInteger(attr).toString(2);
                //ReadPicture(section, 0);
                //CopyPicture(TileImage, 0, 0);
                //TODO Application.ProcessMessages;

                //  SaveBMP('output/Tiles/'+rightstr('000'+inttostr(i),4)+' - '+inttohex(k,6)+'.bmp',bmp);

                if (decimalFilenamesCheckBox.isSelected()) {
                    Path path = tilesPath.resolve(StringUtils.leftPad(Integer.toString(i), 4, "0") + E_BMP);
                    BMPWriter.write(getTile(i, icm), path.toFile());

                    //TODO may be use PNG. Sample code
                    ImageIO.write(getTile(i, icm), "PNG", tilesPath.resolve(StringUtils.leftPad(Integer.toString(i), 4, "0") + ".png").toFile());
                }

                if (groupByAttributesFilenamesCheckBox.isSelected()) {
                    String binaryAttr = StringUtils.right(StringUtils.leftPad(attrHex, 32, '0'), 32);
                    Path path = attrPath.resolve(binaryAttr + " (" + attrHex + ")");
                    IOUtils.createDirectories(path);
                    BMPWriter.write(getTile(i, icm), path.resolve(StringUtils.leftPad(Integer.toString(i), 4, "0") + E_BMP).toFile());
                }

                if (hexFilenamesCheckBox.isSelected()) {
                    BMPWriter.write(getTile(i, icm), hexPath.resolve(intToHex(i, 4) + E_BMP).toFile());
                }
                //TilesProgressBar.Position := i;
                //TilesProgressLabel.Caption := Format('%.2f %%', [((i + 1) / DTA.tilesCount) * 100]);
                //TODO Application.ProcessMessages;
            }
            Log.debug("OK");
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error saving tiles to separate files", e);
        }
    }

    public void saveTilesToOneFileClick() {

        try {
            int width = Integer.parseInt(tilesInARowTextField.getText());
            int height = (int) Math.ceil(yodesk.getTiles().getTiles().size() * 1.0 / width);

            Canvas canvas = new Canvas(width * TILE_SIZE, height * TILE_SIZE);

            int k = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if ((y * width + x) < yodesk.getTiles().getTiles().size()) {
                        drawTileOnCanvas(k, canvas, x * TILE_SIZE, y * TILE_SIZE, Config.transparentColor);
                        k++;
                    }
                }
            }

            Path path = opath.resolve(String.format("tiles%sx%s%s", width, height, E_BMP));
            BMPWriter.write8bit(canvas, path);
            Log.debug("OK");
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error saving tiles to a single file", e);
        }
    }

    public void clipboardCanvasMouseEntered(MouseEvent mouseEvent) {
        moveClipboardRectangle(mouseEvent.getX(), mouseEvent.getY());
        clipboardRectangle.setVisible(true);
    }

    public void clipboardCanvasMouseMoved(MouseEvent mouseEvent) {
        moveClipboardRectangle(mouseEvent.getX(), mouseEvent.getY());
    }

    private void moveClipboardRectangle(double mx, double my) {
        double x = clipboardCanvas.getLayoutX() + (((int) mx) & 0xFFFFFFE0);
        double y = clipboardCanvas.getLayoutY() + (((int) my) & 0xFFFFFFE0);
        if (x != clipboardRectangle.getLayoutX() || y != clipboardRectangle.getLayoutY()) {
            clipboardRectangle.setLayoutX(x);
            clipboardRectangle.setLayoutY(y);
        }
    }

    public void clipboardCanvasMouseExited() {
        clipboardRectangle.setVisible(false);
    }

    //TODO use workdir here, if no clipboardFile
    public void loadClipboardImageClick() {
        try {
            String initialFile = (null == clipboardFile) ? "clipboard.bmp" : clipboardFile.getName();
            String initialDir = (null == clipboardFile) ? null : clipboardFile.getParent();
            File file = JavaFxUtils.showBMPLoadDialog("Load Clipboard image", initialDir, initialFile);
            if (file != null) {
                clipboardFile = file;
                clipboardBufferedImage = BMPReader.read(file);
                ImageUtils.drawOnCanvas(clipboardBufferedImage, clipboardCanvas, transparentColor);
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("Clipboard image loading error", e);
        }
    }

    public void saveClipboardImageClick() {
        try {
            String initialFile = (null == clipboardFile) ? "clipboard.bmp" : clipboardFile.getName();
            String initialDir = (null == clipboardFile) ? null : clipboardFile.getParent();
            File file = JavaFxUtils.showBMPSaveDialog("Save Clipboard image", initialDir, initialFile);
            if (file != null) {
                BMPWriter.write8bit(clipboardBufferedImage, file);
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("Clipboard image saving error", e);
        }
    }

    public void clearClipboardImageClick() {
        clipboardBufferedImage = ImageUtils.clearBufferedImage(clipboardBufferedImage);
        ImageUtils.fillCanvas(clipboardCanvas, transparentColor);
    }

    public void saveMapsToFilesButtonClick() throws IOException {

        if (normalSaveCheckBox.isSelected()) {
            IOUtils.createDirectories(opath.resolve("Maps"));
        }
        if (groupByFlagsCheckBox.isSelected()) {
            IOUtils.createDirectories(opath.resolve("MapsByFlags"));
        }
        if (groupByPlanetTypeCheckBox.isSelected()) {
            IOUtils.createDirectories(opath.resolve("MapsByPlanetType"));
        }
        if (dumpActionsCheckBox.isSelected()) {
            IOUtils.createDirectories(opath.resolve("IZAX"));
            IOUtils.createDirectories(opath.resolve("IZX2"));
            IOUtils.createDirectories(opath.resolve("IZX3"));
            IOUtils.createDirectories(opath.resolve("IZX4"));
            IOUtils.createDirectories(opath.resolve("IACT"));
            IOUtils.createDirectories(opath.resolve("OIE"));
        }

        //TODO
        //MapProgressBar.Position := 0;
        //MapProgressBar.Max := DTA.mapsCount;

        //Log.Clear;
        Log.debug("Maps (zones):");
        Log.newLine();
        Log.debug("Total count: " + yodesk.getZones().getZones().size());
        Log.newLine();
        //DTA.SetIndex(knownSections[5]);          // ZONE
        for (int i = 0; i < yodesk.getZones().getZones().size(); i++) {
            ReadIZON(i, true);
        }

        if (saveUnusedTilesCheckBox.isSelected()) {
            Log.newLine();
            Log.debug("Unused tiles:");
            Log.newLine();
            Path unusedTilesPath = opath.resolve("TilesUnused");
            IOUtils.createDirectories(unusedTilesPath);
            for (int i = 0; i < yodesk.getTiles().getTiles().size(); i++) {
                if (!usedTiles.get(i)) {
                    Log.debug(i);
                    BMPWriter.write8bit(getTile(i), unusedTilesPath.resolve(StringUtils.leftPad(Integer.toString(i), 4, '0') + E_BMP));
                }
            }
        }

        if (dumpActionsTextCheckBox.isSelected()) {
            dumpActionsTextToDocxClick();
        }
        //TODO uncomment, if need
        //ViewMap(MapsListStringGrid.Row);
    }


    //TODO need to test signed
    private String IntToBin(long value) {
        return Long.toBinaryString(value);
    }

    public void dumpActionsTextToDocxClick() {

        try {
            List<String> phrases = yodesk.getZones().getZones().stream().flatMap(z -> {
                List<String> conditions = z.getActions().stream().flatMap(a -> a.getConditions().stream().map(Condition::getText)).collect(Collectors.toList());
                List<String> instructions = z.getActions().stream().flatMap(a -> a.getInstructions().stream().map(Instruction::getText)).collect(Collectors.toList());
                conditions.addAll(instructions);
                return conditions.stream().filter(StringUtils::isNotBlank).map(s -> s.replace("\r\n", "[CR]").replace("[CR][CR]", "[CR2]"));
            }).collect(Collectors.toList());
            IOUtils.saveTextFile(phrases, opath.resolve("iact.txt"));

            // TODO group by all zone auxiliary

            WordUtils.saveZones(getActionsTexts(), getDtaCrcStrings(), opath.resolve("iact2" + E_DOCX));
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error saving Actions text to file", e);
        }
    }

    private List<StringRecord> getActionsTexts() {

        List<StringRecord> zoneRecords = new ArrayList<>();

        yodesk.getZones().getZones().forEach(zone -> {
            List<StringRecord> zr = new ArrayList<>();

            // planet
            // width
            // height
            // type
            // sharedCounter
            // hotspots
            // ZoneAuxiliary3 npcs
            // ZoneAuxiliary4 unknown
            // ZoneAuxiliary2 providedItems
            // ZoneAuxiliary
            //    private int _unnamed2;
            //    private ArrayList<Monster> monsters;
            //    private ArrayList<Integer> requiredItems;
            //    private ArrayList<Integer> goalItems;

            // Actions
            // Conditions Opcodes, text
            // Instructions Opcodes, text

            for (int ax = 0; ax < zone.getActions().size(); ax++) {
                Action a = zone.getActions().get(ax);
                for (int cx = 0; cx < a.getConditions().size(); cx++) {
                    Condition c = a.getConditions().get(cx);
                    if (!c.getText().isEmpty()) {
                        zr.add(new StringRecord(zone.getIndex() + "." + ax + ".c" + cx, c.getText()));
                    }
                }
                for (int ix = 0; ix < a.getInstructions().size(); ix++) {
                    Instruction i = a.getInstructions().get(ix);
                    if (!i.getText().isEmpty()) {
                        zr.add(new StringRecord(zone.getIndex() + "." + ax + ".i" + ix, i.getText()));
                    }
                }
            }

                /*if (zr.size() == 0) {
                    zr.add(new PuzzleRecord(Integer.toString(zone.getIndex()), Collections.emptyList(), "", ""));
                }
                zr.get(0).setType(zone.getPlanet().name());*/

            zoneRecords.addAll(zr);
        });

        return zoneRecords;
    }

    private List<String> getDtaCrcStrings() {
        return Collections.singletonList(getDtaCrcString());
    }

    private String getDtaCrcString() {
        return "CRC32: " + dtaCrc32;
    }

    private void validateDtaCrc(List<String> strings) {

        if (!strings.contains(getDtaCrcString())) {
            JavaFxUtils.showAlert("Wrong or absent CRC32 data", "Perhaps this translation is for another version of the game ", Alert.AlertType.WARNING);
        }
    }

    public void loadTranslatedActionsTextClick() {

        try {
            WordRecord wordRecord = WordUtils.loadRecords(opath.resolve("iact2" + E_DOCX));
            validateDtaCrc(wordRecord.getStrings());
            actionTexts = wordRecord.getStringRecords();
            actionsTextTableView.setItems(FXCollections.observableList(actionTexts));
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error loading Actions text from file", e);
        }
    }

    Pattern actionIdPattern = Pattern.compile("^[0-9]{1,4}\\.[0-9]{1,3}\\.[ic][0-9]{1,3}$");

    public void replaceActionTextClick() {

        try {
            actionTexts.forEach(a -> a.setTranslation(a.getTranslation().replace("[CR2]", "[CR][CR]").replace("[CR]", "\r\n")));

            if (trimActionsTrailSpacesCheckBox.isSelected()) {
                actionTexts.forEach(a -> a.setTranslation(a.getTranslation().trim()));
            }

            if (isBadTranslation(actionTexts, actionIdPattern, getActionsTexts(), strictActionsReplacingRulesCheckBox.isSelected())) {
                return;
            }

            actionTexts.forEach(s -> getActionTextContainer(s.getId()).setText(s.getTranslation()));

            JavaFxUtils.showAlert("The text has been successfully replaced", "No errors were found during the replacement ", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            JavaFxUtils.showAlert("Error replacing Actions text", e);
        }
    }

    private boolean isBadTranslation(List<? extends StringRecord> translatedRecords, Pattern pattern, List<? extends StringRecord> records, boolean strict) {

        List<String> strings = new ArrayList<>();

        if (translatedRecords.stream().anyMatch(a -> a.getOriginal().trim().isEmpty())) {
            strings.add("Part of the original text is missing");
        }
        if (translatedRecords.stream().anyMatch(a -> a.getTranslation().isEmpty())) {
            strings.add("The text is not fully translated");
        }
        if (translatedRecords.size() != records.size()) {
            strings.add("Invalid number of lines of text");
        }
        if (translatedRecords.stream().anyMatch(a -> !pattern.matcher(a.getId().trim()).matches())) {
            strings.add("Broken or missing identifiers");
        }
        if (translatedRecords.stream().anyMatch(tr -> records.stream()
                .noneMatch(r -> r.getOriginal().trim().equals(tr.getOriginal().trim()) && r.getId().trim().equals(tr.getId().trim())))) {
            strings.add("Original text does not match IDs");
        }

        if (!strings.isEmpty()) {
            JavaFxUtils.showAlert(strict ? "The text has not been replaced" : "Replacement of text was done with remarks",
                    "During the check, the following errors were identified:",
                    String.join("\n", strings), strict ? Alert.AlertType.ERROR : Alert.AlertType.WARNING
            );
        }

        return !strings.isEmpty();
    }

    private TextContainer getActionTextContainer(String id) {

        String[] chunks = id.split("\\.");
        int zoneId = Integer.parseInt(chunks[0]);
        int actionId = Integer.parseInt(chunks[1]);
        int containerId = Integer.parseInt(chunks[2].substring(1));

        if (chunks[2].charAt(0) == 'c') {
            return yodesk.getZones().getZones().get(zoneId).getActions().get(actionId).getConditions().get(containerId);
        } else {
            return yodesk.getZones().getZones().get(zoneId).getActions().get(actionId).getInstructions().get(containerId);
        }
    }

    public void savePuzzlesToFilesButtonClick() {

        try {
            IOUtils.createDirectories(opath.resolve("PUZ2"));
            Log.clear();
            Log.debug("Puzzles (2):");
            Log.newLine();
            Log.debug("Total count: " + yodesk.getPuzzles().getPuzzles().size());
            Log.newLine();
            int position = yodesk.getPuzzles().getParent().getPosition();
            for (int i = 0; i < yodesk.getPuzzles().getPuzzles().size(); i++) {
                ReadPUZ2(yodesk.getPuzzles().getPuzzles().get(i), position);
                position += yodesk.getPuzzles().getPuzzles().get(i).byteSize();
            }
            //TODO rewrite code after tests
            List<String> phrases = yodesk.getPuzzles().getFilteredPuzzles().stream()
                    .flatMap(p -> p.getStrings().stream().filter(s -> !s.isEmpty())).map(s -> s.replace("\r\n", "[CR]").replace("[CR][CR]", "[CR2]")).collect(Collectors.toList());
            IOUtils.saveTextFile(phrases, opath.resolve("puz2.txt"));

            Map<Long, List<String>> byType = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(p -> p.getType() != null).collect(Collectors.groupingBy(Puzzle::getType, Collectors.mapping(p -> Long.toHexString(p.getIndex()), Collectors.toList())));

            Map<PuzzleItemClass, List<String>> byItemClass1 = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(p -> p.getItem1Class() != null).collect(Collectors.groupingBy(Puzzle::getItem1Class, Collectors.mapping(p -> Long.toHexString(p.getIndex()), Collectors.toList())));

            Map<PuzzleItemClass, List<String>> byItemClass2 = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(p -> p.getItem2Class() != null && p.getItem2() != 0).collect(Collectors.groupingBy(Puzzle::getItem2Class, Collectors.mapping(p -> Long.toHexString(p.getIndex()), Collectors.toList())));

            Map<Integer, List<String>> byUnnamed6 = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(p -> p.get_unnamed6() != null).collect(Collectors.groupingBy(Puzzle::get_unnamed6, Collectors.mapping(p -> Integer.toHexString(p.getIndex()), Collectors.toList())));

            //TODO dump groups
            System.out.println(byType);
            System.out.println(byItemClass1); // Item1 type
            System.out.println(byItemClass2); // Item2 type
            System.out.println(byUnnamed6); // Flag??

            List<Integer> items1 = yodesk.getPuzzles().getFilteredPuzzles().stream().map(Puzzle::getItem1).distinct().filter(i -> i <= yodesk.getTiles().getTiles().size()).sorted().collect(Collectors.toList());
            List<Integer> items2 = yodesk.getPuzzles().getFilteredPuzzles().stream().map(Puzzle::getItem2).distinct().filter(i -> i <= yodesk.getTiles().getTiles().size()).sorted().collect(Collectors.toList());

            List<Puzzle> puzzles1 = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(i -> i.getItem1() > yodesk.getTiles().getTiles().size()).sorted(Comparator.comparing(Puzzle::getItem1)).collect(Collectors.toList());
            List<Puzzle> puzzles2 = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(i -> i.getItem2() > yodesk.getTiles().getTiles().size()).sorted(Comparator.comparing(Puzzle::getItem2)).collect(Collectors.toList());


            System.out.println(items1);
            System.out.println(items2);

            System.out.println(puzzles1);
            System.out.println(puzzles2);

            WordUtils.savePuzzles(getPuzzlesTexts(), getDtaCrcStrings(), opath.resolve("puz2" + E_DOCX));

        } catch (Exception e) {
            JavaFxUtils.showAlert("Error saving puzzles to the files", e);
        }
    }

    private void ReadPUZ2(Puzzle puzzle, int position) throws IOException {

        Log.debug("Puzzle #" + puzzle.getIndex() + "; Size: 0x" + longToHex(puzzle.byteSize(), 4));
        DumpData(opath.resolve("PUZ2").resolve(StringUtils.leftPad(Integer.toString(puzzle.getIndex()), 4, '0')), position, puzzle.byteSize());
    }

    public void dumpPuzzlesTextToDocxClick() {
        savePuzzlesToFilesButtonClick();
    }

    private List<StringImagesRecord> getPuzzlesTexts() {

        List<StringImagesRecord> puzzleRecords = new ArrayList<>();

        yodesk.getPuzzles().getFilteredPuzzles().forEach(p -> {
            List<StringImagesRecord> pz = new ArrayList<>();

            for (int i = 0; i < StringMeaning.values().length; i++) {
                if (StringUtils.isNotBlank(p.getPrefixesStrings().get(i).getContent())) {
                    pz.add(new StringImagesRecord(p.getIndex() + "." + StringMeaning.byId(i), Collections.emptyList(), p.getPrefixesStrings().get(i).getContent()));
                }
            }

            pz.get(0).setImages(Stream.of(p.getItem1()).map(tileId -> getTile(tileId, icmw)).collect(Collectors.toList()));

            List<BufferedImage> image2 = Stream.of(p.getItem2()).filter(i -> i <= yodesk.getTiles().getTiles().size() && i >= 418)
                    .map(tileId -> getTile(tileId, icmw)).collect(Collectors.toList());

            if (pz.size() < 2) {
                pz.get(0).getImages().addAll(image2);
            } else {
                pz.get(1).setImages(image2);
            }

            puzzleRecords.addAll(pz);
        });

        return puzzleRecords;
    }

    public void loadTranslatedPuzzlesTextClick() {

        try {
            WordRecord wordRecord = WordUtils.loadRecords(opath.resolve("puz2" + E_DOCX));
            validateDtaCrc(wordRecord.getStrings());
            puzzlesTexts = wordRecord.getStringRecords().stream().map(s -> new StringImagesRecord(s.getId(), Collections.emptyList(), s.getOriginal(), s.getTranslation())).collect(Collectors.toList());
            puzzlesTextTableView.setItems(FXCollections.observableList(puzzlesTexts));
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error loading Puzzles text from file", e);
        }
    }

    Pattern puzzleIdPattern = Pattern.compile(String.format("^[0-9]{1,4}\\.(%s|%s|%s|%s|%s)$",
            StringMeaning.REQUEST, StringMeaning.THANK, StringMeaning.OFFER, StringMeaning.MISSION, StringMeaning.UNUSED));

    public void replacePuzzlesTextClick() {

        try {
            puzzlesTexts.forEach(t -> t.setTranslation(t.getTranslation().replace("[CR2]", "[CR][CR]").replace("[CR]", "\r\n")));

            if (trimPuzzlesTrailSpacesCheckBox.isSelected()) {
                puzzlesTexts.forEach(t -> t.setTranslation(t.getTranslation().trim()));
            }

            if (isBadTranslation(puzzlesTexts, puzzleIdPattern, getPuzzlesTexts(), strictPuzzlesReplacingRulesCheckBox.isSelected())) {
                return;
            }

            puzzlesTexts.forEach(s -> getPuzzlesPrefixedStr(s.getId()).setContent(s.getTranslation()));

            JavaFxUtils.showAlert("The text has been successfully replaced", "No errors were found during the replacement ", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            JavaFxUtils.showAlert("Error replacing Puzzles text", e);
        }
    }

    private PrefixedStr getPuzzlesPrefixedStr(String id) {

        String[] chunks = id.trim().split("\\.");
        int puzzleId = Integer.parseInt(chunks[0]);
        int stringId = StringMeaning.valueOf(chunks[1]).getId();

        return yodesk.getPuzzles().getPuzzles().get(puzzleId).getPrefixesStrings().get(stringId);
    }

    public void saveCharactersToFilesButtonClick() {

        try {
            IOUtils.createDirectories(opath.resolve("CHAR"));
            IOUtils.createDirectories(opath.resolve("Characters"));
            IOUtils.createDirectories(opath.resolve("CAUX"));
            IOUtils.createDirectories(opath.resolve("CHWP"));
            Log.clear();
            Log.debug("Characters:");
            Log.newLine();
            Log.debug("Total count: " + yodesk.getCharacters().getCharacters().size());
            Log.newLine();

            int position = yodesk.getCharacters().getParent().getDataPosition();
            for (int i = 0; i < yodesk.getCharacters().getCharacters().size(); i++) {
                ReadCHAR(yodesk.getCharacters().getCharacters().get(i), position);
                position += yodesk.getCharacters().getCharacters().get(i).byteSize();
            }

            position = yodesk.getCharacterWeapons().getParent().getDataPosition();
            for (int i = 0; i < yodesk.getCharacterWeapons().getWeapons().size(); i++) {
                ReadCHWP(yodesk.getCharacterWeapons().getWeapons().get(i), position);
                position += yodesk.getCharacterWeapons().getWeapons().get(i).byteSize();
            }

            position = yodesk.getCharacterAuxiliaries().getParent().getDataPosition();
            for (int i = 0; i < yodesk.getCharacterAuxiliaries().getAuxiliaries().size(); i++) {
                ReadCAUX(yodesk.getCharacterAuxiliaries().getAuxiliaries().get(i), position);
                position += yodesk.getCharacterAuxiliaries().getAuxiliaries().get(i).byteSize();
            }

            List<StringRecord> records = yodesk.getCharacters().getCharacters().stream()
                    .map(c -> new StringRecord(c.getIndex(), c.getName())).collect(Collectors.toList());
            WordUtils.saveRecords("Characters", records, getDtaCrcStrings(), opath.resolve("chars" + E_DOCX));

            List<ImageRecord> imageRecords = yodesk.getCharacters().getFilteredCharacters().stream()
                    .map(c -> new ImageRecord(c.getTileIds().stream().map(t -> getTile(t, icmw)).collect(Collectors.toList()), c.getName())).collect(Collectors.toList());
            WordUtils.saveCharacters(imageRecords, getDtaCrcStrings(), opath.resolve("chars2" + E_DOCX));

            IOUtils.saveTextFile(yodesk.getCharacters().getFilteredCharacters().stream().map(Character::getName).collect(Collectors.toList()), opath.resolve("chars.txt"));
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error saving characters to the files", e);
        }
    }

    public void generateCharactersReportButton() {

        try {
            List<ImageRecord> imageRecords = yodesk.getCharacters().getFilteredCharacters().stream()
                    .map(c -> new ImageRecord(c.getTileIds().stream().map(t -> getTile(t, icmw)).collect(Collectors.toList()), c.getName())).collect(Collectors.toList());
            WordUtils.saveCharacters(imageRecords, getDtaCrcStrings(), opath.resolve("chars2" + E_DOCX));
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error saving characters to the files", e);
        }
    }

    private void ReadCHAR(Character c, int position) throws IOException {

        for (Integer integer : c.getTileIds()) {
            Path path = opath.resolve("Characters").resolve(c.getName());
            IOUtils.createDirectories(path);
            BMPWriter.write(getTile(integer), path.resolve(StringUtils.leftPad(Integer.toString(integer), 4, '0') + E_BMP));
        }

        DumpData(opath.resolve("CHAR").resolve(StringUtils.leftPad(Integer.toString(c.getIndex()), 3, '0')), position, c.byteSize());
    }

    private void ReadCHWP(CharacterWeapon c, int position) throws IOException {

        //  size := DTA.ReadLongWord;
        //int ch = section.ReadWord();
        DumpData(opath.resolve("CHWP").resolve(StringUtils.leftPad(Integer.toString(c.getIndex()), 3, '0')), position, c.byteSize());
    }

    private void ReadCAUX(CharacterAuxiliary c, int position) throws IOException {

        //  size:=DTA.ReadLongWord;
        //int ch = section.ReadWord();
        DumpData(opath.resolve("CAUX").resolve(StringUtils.leftPad(Integer.toString(c.getIndex()), 3, '0')), position, c.byteSize());
    }

    public void saveNamesToFileButtonClick() {

        try {
            Log.clear();
            Log.debug("Names:");
            Log.newLine();
            Log.debug("Total count: " + yodesk.getTileNames().getNames().size());
            Log.newLine();
            IOUtils.createDirectories(opath.resolve("Names"));
            for (TileName n : yodesk.getTileNames().getNames()) {
                if (null != n.getName()) {
                    BMPWriter.write(getTile(n.getTileId()), IOUtils.findUnusedFileName(opath.resolve("Names"), n.getName(), E_BMP));
                }
            }
            IOUtils.saveTextFile(yodesk.getTileNames().getFilteredNames().stream().map(TileName::getName).filter(Objects::nonNull)
                    .collect(Collectors.toList()), opath.resolve("tilenames.txt"));

            WordUtils.saveNames(getNamesTexts(), getDtaCrcStrings(), opath.resolve("tilenames2" + E_DOCX));
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error saving names to a file", e);
        }
    }

    public void dumpNamesTextToDocxCLick() {
        saveNamesToFileButtonClick();
    }

    private List<StringImagesRecord> getNamesTexts() {

        List<StringImagesRecord> list = new ArrayList<>();

        List<TileName> filteredNames = yodesk.getTileNames().getFilteredNames();

        for (int i = 0; i < filteredNames.size(); i++) {
            TileName n = filteredNames.get(i);
            StringImagesRecord puzzleRecord = new StringImagesRecord(i, Collections.singletonList(getTile(n.getTileId(), icmw)), n.getName());
            list.add(puzzleRecord);
        }
        return list;
    }

    public void loadTranslatedNamesTextClick() {

        try {
            WordRecord wordRecord = WordUtils.loadNames(opath.resolve("tilenames2" + E_DOCX));
            validateDtaCrc(wordRecord.getStrings());
            namesTexts = wordRecord.getStringRecords().stream().map(s -> new StringImagesRecord(s.getId(), Collections.emptyList(), s.getOriginal(), s.getTranslation())).collect(Collectors.toList());
            namesTextTableView.setItems(FXCollections.observableList(namesTexts));
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error loading Puzzles text from file", e);
        }
    }

    Pattern nameIdPattern = Pattern.compile("^[0-9]{1,4}$");

    public void replaceNamesTextClick() {

        try {
            if (trimNamesSpacesCheckBox.isSelected()) {
                namesTexts.forEach(a -> a.setTranslation(a.getTranslation().trim()));
            }

            if (isBadTranslation(namesTexts, nameIdPattern, getNamesTexts(), strictNamesReplacingRulesCheckBox.isSelected())) {
                return;
            }

            List<TileName> filteredNames = yodesk.getTileNames().getFilteredNames();

            namesTexts.forEach(n -> filteredNames.get(Integer.parseInt(n.getId())).setName(n.getTranslation()));

            JavaFxUtils.showAlert("The text has been successfully replaced", "No errors were found during the replacement ", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            JavaFxUtils.showAlert("Error replacing Tile Names text", e);
        }
    }

    private BufferedImage getTile(int tileId) {
        return getTile(tileId, Config.icm0);
    }

    private BufferedImage getTile(int tileId, IndexColorModel icm) {

        int position = yodesk.getTiles().getTilePixelsPosition(tileId);
        return ImageUtils.readBPicture(yodesk.getTiles().getRawTiles(), position, TILE_SIZE, TILE_SIZE, icm);
    }

    private WritableImage drawTileOnImage(int tileId) {

        int position = yodesk.getTiles().getTilePixelsPosition(tileId);
        return ImageUtils.readWPicture(yodesk.getTiles().getRawTiles(), position, TILE_SIZE, TILE_SIZE, Config.transparentColor);
    }

    private void drawTileOnBufferedImage(int tileId, BufferedImage bi, int xOffset, int yOffset) {

        int position = yodesk.getTiles().getTilePixelsPosition(tileId);
        ImageUtils.drawOnBufferedImage(yodesk.getTiles().getRawTiles(), position, bi, xOffset, yOffset);
    }

    private void drawTileOnCanvas(int tileId, Canvas canvas, int xOffset, int yOffset, Color transparentColor) {

        int position = yodesk.getTiles().getTilePixelsPosition(tileId);
        ImageUtils.drawOnCanvas(yodesk.getTiles().getRawTiles(), position, canvas, xOffset, yOffset, transparentColor);
    }

    private void drawBorderOnCanvas(Canvas canvas, int xOffset, int yOffset, Color borderColor) {
        ImageUtils.drawBorderOnCanvas(canvas, xOffset, yOffset, borderColor);
    }

    private void fillZoneTile(Canvas canvas, int xOffset, int yOffset, Color color) {
        ImageUtils.fillCanvas(canvas, xOffset, yOffset, color);
    }


    public static void loadFileToArray(File file) throws IOException {

        Log.debug("DTA file internal structure");
        Log.debug("===========================");
        Log.newLine();

        Path exePath = file.toPath();
        Path dtaPath = IOUtils.getDtaPath(exePath.getParent());

        exeCrc32 = intToHex(BinaryUtils.crc32(exePath), 8);
        dtaCrc32 = intToHex(BinaryUtils.crc32(dtaPath), 8);

        release = Config.releases.stream().filter(r -> r.getExeCrc32().equals(exeCrc32) && r.getDtaCrc32().equals(dtaCrc32)).findFirst().orElse(null);

        if (null == release) {
            release = new Release();
            release.setCharset("Cp1252");
            release.setId("unk");
            release.setTitle("Unknown combination of files");
            release.setDtaCrc32("????????");
            release.setExeCrc32("????????");
        }

        exeDump = new Dump(exePath);
        exeDump.setCharset(release.getCharset());
        exeDump.setByteOrder(ByteOrder.LITTLE_ENDIAN);

        int paletteStartIndex = exeDump.findAddress(PaletteUtils.getBGRASample(gamePalette));

        if (paletteStartIndex < 0) {
            throw new RuntimeException("Palette isn't found in EXE file");
        }

        //TODO const
        boolean otherPaletteLocation = (0x550F0 != paletteStartIndex);

        Log.newLine();
        Log.debug("EXE:");
        Log.debug("---------");
        Log.newLine();
        Log.debug("Size: " + exeDump.size());
        Log.debug("CRC-32: " + exeCrc32);
        Log.debug("DTA revision: " + release.getTitle());
        Log.debug("Palette address: " + intToHex(paletteStartIndex, 6));
        exeDump.setIndex(0);
        if (otherPaletteLocation) {
            Log.debug("Unknown palette location. Standard is: 0x550F0");
        }

        dumpPalette(paletteStartIndex);

        dtaDump = new Dump(dtaPath);
        dtaDump.setByteOrder(ByteOrder.LITTLE_ENDIAN);

        Log.newLine();
        Log.debug("Sections:");
        Log.debug("---------");
        Log.newLine();
        Log.debug("Size: " + dtaDump.size());
        Log.debug("CRC-32: " + dtaCrc32);
        Log.debug("DTA revision: " + release.getTitle());
    }

    private static void dumpPalette(int paletteStartIndex) {

        gamePalette = PaletteUtils.dumpBGRAPalette(exeDump.getDump(), paletteStartIndex);
        Config.updatePalette();
    }


    public void SaveToFile(String fileName) throws IOException {
        dtaDump.saveToFile(fileName);
    }

    public static String intToHex(int value, int size) {

        String result = Integer.toHexString(value).toUpperCase();
        return StringUtils.leftPad(result, size, '0');
    }

    public String longToHex(long value, int size) {

        String result = Long.toHexString(value);
        return StringUtils.leftPad(result, size, '0');
    }

    public void peExplorerHyperlinkClick() {
        JavaFxUtils.openUrl("http://heaventools.com/pe-explorer.htm");
    }

    public void resEditHyperlinkClick() {
        JavaFxUtils.openUrl("http://resedit.net");
    }

    public void resourceBuilderHyperlinkClick() {
        JavaFxUtils.openUrl("https://resource-builder.com");
    }

    public void resourceHackerHyperlinkClick() {
        JavaFxUtils.openUrl("http://angusj.com/resourcehacker");
    }

    public void resourceTunerHyperlinkClick() {
        JavaFxUtils.openUrl("http://restuner.com");
    }

    public void restoratorHyperlinkClick() {
        JavaFxUtils.openUrl("https://bome.com/products/restorator");
    }

    public void xnResourceEditorHyperlinkClick() {
        JavaFxUtils.openUrl("https://stefansundin.github.io/xn_resource_editor");
    }

    //TODO
    public static class Log {

        public static void error(String message) {
            System.out.println("ERROR: " + message);
        }

        public static void debug(String message) {
            System.out.println("DEBUG: " + message);
        }

        public static void debug(int integer) {
            debug(Integer.toString(integer));
        }

        public static void newLine() {
            System.out.println("");
        }

        public static void message(String message) {
            System.out.println("MESSAGE: " + message);
        }

        //TODO
        public static void clear() {
        }
    }
}
