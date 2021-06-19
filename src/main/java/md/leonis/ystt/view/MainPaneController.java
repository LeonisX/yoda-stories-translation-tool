package md.leonis.ystt.view;

import io.kaitai.struct.ByteBufferKaitaiOutputStream;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import md.leonis.bin.ByteOrder;
import md.leonis.bin.Dump;
import md.leonis.config.Config;
import md.leonis.ystt.model.*;
import md.leonis.ystt.model.docx.PropertyName;
import md.leonis.ystt.model.docx.StringImagesRecord;
import md.leonis.ystt.model.docx.StringRecord;
import md.leonis.ystt.model.docx.WordRecord;
import md.leonis.ystt.model.yodesk.CatalogEntry;
import md.leonis.ystt.model.yodesk.Yodesk;
import md.leonis.ystt.model.yodesk.characters.Character;
import md.leonis.ystt.model.yodesk.characters.CharacterType;
import md.leonis.ystt.model.yodesk.puzzles.PrefixedStr;
import md.leonis.ystt.model.yodesk.puzzles.Puzzle;
import md.leonis.ystt.model.yodesk.puzzles.StringMeaning;
import md.leonis.ystt.model.yodesk.tiles.TileGender;
import md.leonis.ystt.model.yodesk.tiles.TileName;
import md.leonis.ystt.model.yodesk.zones.*;
import md.leonis.ystt.utils.*;
import net.sf.image4j.codec.bmp.BMPImage;
import net.sf.image4j.codec.bmp.BMPReader;
import net.sf.image4j.codec.bmp.BMPWriter;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static md.leonis.config.Config.*;
import static md.leonis.ystt.utils.BinaryUtils.intToHex;
import static md.leonis.ystt.utils.BinaryUtils.longToHex;
import static md.leonis.ystt.utils.ImageUtils.getTile;
import static md.leonis.ystt.utils.ImageUtils.readWPicture;
import static md.leonis.ystt.utils.JavaFxUtils.runInBackground;
import static md.leonis.ystt.utils.JavaFxUtils.showAlert;
import static md.leonis.ystt.utils.StringUtils.splitCamelCase;
import static md.leonis.ystt.utils.WordHelper.getActionsTexts;

public class MainPaneController {

    private static final int STANDARD_PALETTE_LOCATION = 0x550F0;

    public MenuItem openMenuItem;
    public MenuItem dumpAllMenuItem;
    public MenuItem saveMenuItem;
    public MenuItem saveAsMenuItem;
    public MenuItem closeMenuItem;
    public MenuItem exitMenuItem;

    public ToggleGroup transparentColorToggleGroup;
    public RadioMenuItem noColorMenuItem;
    public RadioMenuItem fuchsiaMenuItem;
    public RadioMenuItem blackMenuItem;
    public RadioMenuItem whiteMenuItem;
    public CheckMenuItem disableNonTranslationMenuItem;

    public MenuItem howToMenuItem;
    public MenuItem aboutMenuItem;

    public Label versionLabel;
    public Label fileSizeLabel;
    public Label crc32Label;
    public Label nameLabel;
    public TableView<CatalogEntry> commonInformationTableView;
    public Button dumpAllSectionsButton;
    public Button saveHighMidStructureButton;
    public Label sourceCharsetLabel;
    public Button changeSourceCharsetButton;
    public Label destinationCharsetLabel;
    public Button changeDestinationCharsetButton;
    public Label descriptionLabel;

    public TextField windowSizeTextField;
    public TextField inventoryTextField;
    public Label inventoryErrorLabel;
    public Label inventoryLabel;

    public TextField toolTipSizeTextField;
    public TextField dialogTextField;
    public TextField toolTipSizeTextFont;
    public Label dialogErrorLabel;
    public Label dialogLabel;
    public Label dialogFontErrorLabel;

    public ImageView gameImageView;
    public ImageView dialogButtonImageView;
    public Rectangle dialogRectangle;
    public Rectangle shapeRectangle;
    public ImageView inventoryImageView;

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
    public CheckBox groupByGenderCheckBox;
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
    public Menu setGenderMenu;
    public ToggleGroup tilesGenderToggleGroup;
    private final Map<String, RadioMenuItem> tileAttributesMap = new HashMap<>();
    public Button addNewTile;
    public Button deleteTile;

    private Node currentTile;

    public Label zonesCountLabel;
    public Label phrasesCountLabel;
    public Tab dumpTab;
    public Button saveZonesToFilesButton;
    public CheckBox normalSaveCheckBox;
    public CheckBox groupByAttributesCheckBox;
    public CheckBox groupByPlanetTypeCheckBox;
    public CheckBox groupByUnknownCheckBox;
    public CheckBox dumpActionsCheckBox;
    public CheckBox saveUnusedTilesCheckBox;
    public Button showActionsButton;
    public Canvas zoneTypeCanvas;
    public CheckBox showZoneMonstersCheckBox;
    public CheckBox showZoneHotspotsCheckBox;
    public Label zoneSizeLabel;
    public Label zoneTypeLabel;
    public Label zonePlanetLabel;
    public Label zoneActionsLabel;
    public Label zoneIzaxUnnamedLabel;
    public Label zoneZax4Unnamed2Label;
    public FlowPane zoneNpcsTilesFlowPane;
    public FlowPane zoneGoalTilesFlowPane;
    public FlowPane zoneProvidedItemsTilesFlowPane;
    public FlowPane zoneRequiredItemsTilesFlowPane;
    public FlowPane lootTilesFlowPane;

    public RadioButton topRadioButton;
    public RadioButton middleRadioButton;
    public RadioButton bottomRadioButton;
    public CheckBox topCheckBox;
    public CheckBox middleCheckBox;
    public CheckBox bottomCheckBox;
    public ToggleGroup zoneLayerToggleGroup;
    public ListView<String> zoneEditorListView;
    public Canvas zoneEditorCanvas;
    public ScrollPane zoneEditorTilesScrollPane;
    public FlowPane zoneEditorTilesFlowPane;
    public Button undoZoneEdit;
    public ContextMenu zoneEditorContextMenu;
    public TabPane zoneOptionsTabPane;
    public Label zoneSpotStatusLabel;

    private int mapX;
    private int mapY;

    public Button dumpActionsTextToDocx;
    public Button loadTranslatedActionsText;
    public Button replaceActionText;
    public CheckBox trimActionsTrailSpacesCheckBox;
    public CheckBox strictActionsReplacingRulesCheckBox;
    public CheckBox generateMapsReports;
    public TableView<StringRecord> actionsTextTableView;

    public Label puzzlesCountLabel;
    public TableView<StringImagesRecord> puzzlesTableView;
    public Button dumpPuzzlesTextToDocx;
    public Button loadTranslatedPuzzlesText;
    public Button replacePuzzlesText;
    public CheckBox trimPuzzlesTrailSpacesCheckBox;
    public CheckBox strictPuzzlesReplacingRulesCheckBox;
    public TableView<StringImagesRecord> puzzlesTextTableView;

    public Tab charactersTab;
    public Label charactersCountLabel;
    public Button saveCharactersToFilesButton;
    public Button generateCharactersReportButton;
    public TableView<Character> charactersTableView;

    public Label namesCountLabel;
    public TableView<TileName> namesTableView;
    public Button saveNamesToFilesButton;
    public Button dumpNamesTextToDocx;
    public Button loadTranslatedNamesText;
    public Button replaceNamesText;
    public CheckBox trimNamesSpacesCheckBox;
    public CheckBox strictNamesReplacingRulesCheckBox;
    public TableView<StringImagesRecord> namesTextTableView;

    public TextArea logsTextArea;

    public Label statusLabel;
    public Canvas statusCanvas;

    private static final String OUTPUT = "output";
    private static final String E_BMP = ".bmp";
    private static final String E_DOCX = ".docx";
    private static final String E_XLSX = ".xlsx";
    private static final String E_TXT = ".txt";
    private static final String E_MD = ".md";
    private static final int TILE_SIZE = 32;

    Path rootPath, releasePath, resourcesPath, translatePath, dumpsPath;

    public static List<Boolean> usedTiles = new ArrayList<>();

    private File clipboardFile;
    private BufferedImage clipboardBufferedImage = new BufferedImage(288, 288, BufferedImage.TYPE_BYTE_INDEXED, icm);

    private static long dtaSize;
    public static Dump exeDump;
    public static String exeCrc32;
    public static String dtaCrc32;

    private List<StringRecord> actionTexts;
    private List<StringImagesRecord> puzzlesTexts;
    private List<StringImagesRecord> namesTexts;

    private final Map<Integer, ArrayDeque<ZoneHistory>> zoneHistoryMap = new HashMap<>();

    // Offsets for EXE hacking
    private static Map<Integer, Long> windowMap; // 525
    private static Map<Integer, Long> window2Map; // 525
    private static Map<Integer, Long> toolTipMap; // C2039B148D     833284150413
    private static Map<Integer, Long> gridViewMap; // 489
    private static Map<Integer, Long> scrollBarLeftMap; // 496
    private static Map<Integer, Long> scrollBarRightMap; // 512
    private static Map<Integer, Long> toolTipFontMap; // -8
    private static Map<Integer, Long> toolTipFont2Map; // -8

    boolean firstRun = true;

    private int changesCount;
    private int tilesCount;

    double dialogButtonLayoutX;
    double dialogRectangleLayoutX;
    double dialogRectangleLayoutY;
    double dialogRectangleWidth;
    double dialogLabelLayoutX;
    double dialogLabelLayoutY;

    double inventoryImageViewOffset;

    public MainPaneController() {
        rootPath = Paths.get(".");
    }

    @FXML
    void initialize() {

        changesCount = 0;
        tilesCount = yodesk.getTiles().getTiles().size();
        usedTiles = new ArrayList<>(Collections.nCopies(yodesk.getTiles().getTiles().size(), false));

        noColorMenuItem.setUserData(transparentColor);
        fuchsiaMenuItem.setUserData(Color.FUCHSIA);
        blackMenuItem.setUserData(Color.BLACK);
        whiteMenuItem.setUserData(Color.WHITE);

        Log.setOutput(logsTextArea);

        toolTipSizeTextField.setDisable(toolTipMap.isEmpty());
        toolTipSizeTextFont.setDisable(toolTipFontMap.isEmpty() && toolTipFont2Map.isEmpty());
        windowSizeTextField.setDisable(windowMap.isEmpty() || window2Map.isEmpty() || gridViewMap.isEmpty() ||
                scrollBarLeftMap.isEmpty() || scrollBarRightMap.isEmpty());

        if (toolTipSizeTextField.isDisabled() || windowSizeTextField.isDisabled() || toolTipSizeTextFont.isDisabled()) {
            JavaFxUtils.showAlert("Error reading YODESK.EXE file!",
                    "Could not find all addresses in the executable file.",
                    "Perhaps this is some kind of exclusive version of the game.\n" +
                            "Please send it to us so that we can analyze it and fix our code.", Alert.AlertType.WARNING);

        }

        releasePath = rootPath.resolve(OUTPUT + '-' + release.getId());
        resourcesPath = releasePath.resolve("resources"); // all images
        translatePath = releasePath.resolve("translate"); // all translation images & docs
        dumpsPath = releasePath.resolve("dumps");

        try {
            tilesToggleGroup.getToggles().forEach(t -> tileAttributesMap.put(t.getUserData().toString(), (RadioMenuItem) t));
            Platform.runLater(this::updateUI);
        } catch (Exception e) {
            JavaFxUtils.showAlert("UI update error", e);
        }

        disableNonTranslationFeatures(disableNonTranslationMenuItem.isSelected());
    }

    private void updateUI() {

        if (firstRun) {
            setGenderMenu.setDisable(null == yodesk.getTileGenders());
            dialogButtonLayoutX = dialogButtonImageView.getLayoutX();
            dialogRectangleLayoutX = dialogRectangle.getLayoutX();
            dialogRectangleLayoutY = dialogRectangle.getLayoutY();
            dialogRectangleWidth = dialogRectangle.getWidth();
            dialogLabelLayoutX = dialogLabel.getLayoutX();
            dialogLabelLayoutY = dialogLabel.getLayoutY();

            inventoryImageViewOffset = inventoryImageView.getLayoutX();
            firstRun = false;
        }

        // Common information, sections
        versionLabel.setText(yodesk.getVersion().getVersion());
        nameLabel.setText(release.getTitle());
        fileSizeLabel.setText(dtaSize + " / " + exeDump.size());
        crc32Label.setText(dtaCrc32 + " / " + exeCrc32);
        sourceCharsetLabel.setText(sourceCharset.getDescription());
        updateDestinationCharset();

        String note = (release.getId().equals("eng-1.2"))
                ? "This is the best basis for future translation. Good luck!"
                : "English version 1.2 is currently recommended for translation.\n" +
                "It can be found in Star Wars - Yoda Stories & Behind The Magic - Vehicles Special Edition.\n" +
                "But it's better to search for the name LucasArts Archives Vol. IV: Star Wars Collection II.";

        descriptionLabel.setText(release.getDescription() + "\n\n" + note);
        commonInformationTableView.setItems(FXCollections.observableList(yodesk.getCatalog()));

        //EXE
        if (windowMap.size() == 1) {
            int value = (int) getValue(windowMap) - 525;
            windowSizeTextField.setText(String.valueOf(value));
            resizeInventory(value);
        }
        if (toolTipMap.size() == 1) {
            long longValue = getValue(toolTipMap);
            String hex = longToHex(longValue, 10);
            if (hex.equals("C2039B148D") || !hex.startsWith("000000") || !hex.endsWith("B8")) {
                toolTipSizeTextField.setText("0");
            } else { // 000000a2b8
                int value = Integer.parseInt(hex.substring(6, 8), 16) - 130;
                toolTipSizeTextField.setText(String.valueOf(value));
                resizeToolTip(value);
            }
        }
        if (toolTipMap.size() == 1) {
            int value = Math.abs((int) getValue(toolTipFontMap) + 8 - 256);
            toolTipSizeTextFont.setText(String.valueOf(value));
            resizeToolTipFont(value);
        }

        windowSizeTextField.textProperty().addListener(windowSizeChangeListener());
        inventoryTextField.textProperty().addListener((observable, oldValue, newValue) -> inventoryLabel.setText(newValue));

        toolTipSizeTextField.textProperty().addListener(toolTipSizeChangeListener());
        toolTipSizeTextFont.textProperty().addListener(toolTipFontSizeChangeListener());
        dialogTextField.textProperty().addListener((observable, oldValue, newValue) -> dialogLabel.setText(newValue));

        // Title image, palette
        drawTitleImage();
        drawPalette();

        // Sounds
        soundsCountLabel.setText(Integer.toString(yodesk.getSounds().getSounds().size()));
        soundsTextArea.setText(String.join("\n", yodesk.getSounds().getSounds()));

        // Tiles, sprites
        drawTiles();
        tilesCountLabel.setText(Integer.toString(yodesk.getTiles().getTiles().size()));
        tilesContextMenu.setOnShown(this::selectTileMenuItem);
        tilesToggleGroup.selectedToggleProperty().addListener(tilesAttributesToggleGroupListener());
        tilesGenderToggleGroup.selectedToggleProperty().addListener(tilesGenderToggleGroupListener());

        // Zones
        zonesCountLabel.setText(String.valueOf(yodesk.getZones().getZones().size()));
        phrasesCountLabel.setText(
                String.valueOf(yodesk.getZones().getZones().stream().mapToLong(z -> z.getActions().stream()
                        .mapToLong(a -> a.getInstructions().stream().map(Instruction::getText).filter(StringUtils::isNotBlank).count() +
                                a.getConditions().stream().map(Condition::getText).filter(StringUtils::isNotBlank).count()).sum()).sum()
                )
        );
        zoneLayerToggleGroup.selectedToggleProperty().addListener(zoneLayerToggleGroupListener());
        zoneEditorListView.setItems(FXCollections.observableList(yodesk.getZones().getZones().stream().map(m -> "Zone #" + m.getIndex()).collect(Collectors.toList())));
        zoneEditorListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> drawEditZone());
        zoneEditorListView.getSelectionModel().select(0);
        zoneEditorCanvas.setOnContextMenuRequested(e -> zoneEditorContextMenu.show((Node) e.getSource(), e.getScreenX(), e.getScreenY()));
        zoneOptionsTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> positionZoneOptionsTabPane());
        drawMapEditorTiles();

        actionTexts = getActionsTexts();
        actionsTextTableView.setItems(FXCollections.observableList(actionTexts));

        // Puzzles
        puzzlesCountLabel.setText(Integer.toString(yodesk.getPuzzles().getPuzzles().size()));
        puzzlesTexts = WordHelper.getPuzzlesTexts();
        puzzlesTextTableView.setItems(FXCollections.observableList(puzzlesTexts));
        @SuppressWarnings("all")
        TableColumn<StringImagesRecord, List<Integer>> puzzlesColumn = (TableColumn<StringImagesRecord, List<Integer>>) puzzlesTableView.getColumns().get(1);
        puzzlesColumn.setCellFactory(c -> {

            Canvas canvas = new Canvas();

            TableCell<StringImagesRecord, List<Integer>> cell = new TableCell<StringImagesRecord, List<Integer>>() {
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
        puzzlesTableView.setItems(FXCollections.observableList(puzzlesTexts));

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

        namesTexts = WordHelper.getNamesTexts();
        namesTextTableView.setItems(FXCollections.observableList(namesTexts));

        logsTextArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) ->
                logsTextArea.setScrollTop(Double.MIN_VALUE));
    }

    private ChangeListener<? super Toggle> zoneLayerToggleGroupListener() {
        return (ov, oldToggle, newToggle) -> {
            if (newToggle != null) {
                Log.debug("Select zone layer: " + ZoneLayer.byId(newToggle.getUserData().toString()));
            }
        };
    }

    private void disableNonTranslationFeatures(boolean status) {

        dumpAllMenuItem.setDisable(status);
        dumpAllSectionsButton.setDisable(status);
        saveHighMidStructureButton.setDisable(status);
        saveSoundsListToFileButton.setDisable(status);
        saveTilesToSeparateFiles.setDisable(status);
        decimalFilenamesCheckBox.setDisable(status);
        hexFilenamesCheckBox.setDisable(status);
        groupByAttributesFilenamesCheckBox.setDisable(status);
        saveTilesToOneFile.setDisable(status);
        tilesInARowTextField.setDisable(status);
        dumpTab.setDisable(status);
        charactersTab.setDisable(status);
        Log.debug("Show all features: " + (status ? "OFF" : "ON"));
    }

    private void updateDestinationCharset() {
        destinationCharsetLabel.setText(destinationCharset.getDescription());
    }

    private void drawTitleImage() {

        try {
            WritableImage image = readWPicture(yodesk.getStartupImage().getPixels(), 0, 288, 288, transparentColor);
            titleScreenImageView.setImage(image);
        } catch (Exception e) {
            JavaFxUtils.showAlert("Title screen display error", e);
        }
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
            tilesFlowPane.getChildren().add(deleteTile);
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

    private ChangeListener<String> windowSizeChangeListener() {

        return (observable, oldValue, newValue) -> {
            int value = 0;
            inventoryErrorLabel.setVisible(true);
            try {
                value = Integer.parseInt(newValue);
                if (value >= -120 && value <= 170) {
                    exeDump.writeHexDump(getIndex(windowMap), intToHex(525 + value, 4));
                    exeDump.writeHexDump(getIndex(window2Map), intToHex(525 + value, 4));

                    exeDump.writeHexDump(getIndex(gridViewMap), intToHex(489 + value, 4));
                    exeDump.writeHexDump(getIndex(scrollBarLeftMap), intToHex(496 + value, 4));
                    exeDump.writeHexDump(getIndex(scrollBarRightMap), intToHex(512 + value, 4));

                    inventoryErrorLabel.setVisible(false);
                    changesCount++;
                } else {
                    value = 0;
                }
            } catch (Exception ignored) {
                //
            }

            resizeInventory(value);
        };
    }

    private void resizeInventory(int value) {
        inventoryImageView.setLayoutX(inventoryImageViewOffset + value);
    }

    private ChangeListener<? super String> toolTipSizeChangeListener() {
        return (observable, oldValue, newValue) -> {
            int value = 0;
            dialogErrorLabel.setVisible(true);
            try {
                value = Integer.parseInt(toolTipSizeTextField.getText());
                if (value == 0) {
                    exeDump.writeHexDump(getIndex(toolTipMap), "C2039B148D");
                    changesCount++;
                    dialogErrorLabel.setVisible(false);
                } else if (value >= -78 && value <= 125) {
                    exeDump.writeHexDump(getIndex(toolTipMap), "000000" + intToHex(130 + value, 2) + "B8"); // 000000a2b8
                    changesCount++;
                    dialogErrorLabel.setVisible(false);
                } else {
                    value = 0;
                }
            } catch (Exception ignored) {
                //
            }

            resizeToolTip(value);
        };
    }

    private void resizeToolTip(int value) {

        int moveLeft = value > 53 ? value - 53 : 0;
        int right = Math.min(value, 53);

        if (moveLeft > 55) {
            right += moveLeft - 55;
            moveLeft = 55;
        }

        dialogButtonImageView.setLayoutX(dialogButtonLayoutX + right);
        dialogRectangle.setWidth(dialogRectangleWidth + value);

        dialogRectangle.setLayoutX(dialogRectangleLayoutX - moveLeft);
        dialogLabel.setLayoutX(dialogLabelLayoutX - moveLeft);
    }

    private ChangeListener<? super String> toolTipFontSizeChangeListener() {
        return (observable, oldValue, newValue) -> {
            int value = 0;
            dialogFontErrorLabel.setVisible(true);
            try {
                value = Byte.parseByte(toolTipSizeTextFont.getText());
                if (value >= 0 && value <= 20) {
                    exeDump.setByte(getIndex(toolTipFontMap), -value - 8);
                    exeDump.setByte(getIndex(toolTipFont2Map), -value - 8);
                    changesCount++;
                    dialogFontErrorLabel.setVisible(false);
                } else {
                    value = 0;
                }
            } catch (Exception ignored) {
                //
            }

            resizeToolTipFont(value);
        };
    }

    private void resizeToolTipFont(int value) {

        // 10.8
        Font of = dialogLabel.getFont();
        Font font = new Font(of.getName(), 10.8 + value);
        dialogLabel.setFont(font);

        double moveTop = Math.abs(10.8 - dialogLabel.getFont().getSize());

        dialogRectangle.setHeight(24 + moveTop);
        dialogRectangle.setLayoutY(dialogRectangleLayoutY - moveTop);
        dialogLabel.setLayoutY(dialogLabelLayoutY - moveTop);
    }

    public void addNewTileClick() {

        try {
            yodesk.getTiles().addTile();
            if (null != yodesk.getTileGenders()) {
                yodesk.getTileGenders().addTileGender();
            }
            int tileId = yodesk.getTiles().getTiles().size() - 1;
            tilesFlowPane.getChildren().add(tileId, newTile(tileId, true));
            zoneEditorTilesFlowPane.getChildren().add(tileId, newTile(tileId, false));
            usedTiles.add(false);
            Log.debug("Added new tile");
            drawEditZone();
            changesCount++;
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error adding tile", e);
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
                ((ImageView) zoneEditorTilesFlowPane.getChildren().get(tileId)).setImage(wi);
                yodesk.getTiles().replaceTile(tileId, ImageUtils.getBytes(wi, transparentColor));
                changesCount++;
            }
            event.setDropCompleted(true);

            event.consume();
        });
        return image;
    }

    public void deleteTileClick() {

        try {
            int tileId = yodesk.getTiles().getTiles().size() - 1;
            List<Integer> zoneIds = yodesk.getZones().getZones().stream().filter(z -> z.getTileIds().stream()
                    .flatMap(l -> l.getColumn().stream()).anyMatch(i -> i.equals(tileId))).map(Zone::getIndex).collect(Collectors.toList());
            zoneIds.addAll(yodesk.getZones().getZones().stream().filter(z -> z.getIzax().getGoalItems().stream()
                    .anyMatch(i -> i.equals(tileId))).map(Zone::getIndex).collect(Collectors.toList()));
            zoneIds.addAll(yodesk.getZones().getZones().stream().filter(z -> z.getIzax().getRequiredItems().stream()
                    .anyMatch(i -> i.equals(tileId))).map(Zone::getIndex).collect(Collectors.toList()));
            zoneIds.addAll(yodesk.getZones().getZones().stream().filter(z -> z.getIzx2().getProvidedItems().stream()
                    .anyMatch(i -> i.equals(tileId))).map(Zone::getIndex).collect(Collectors.toList()));
            zoneIds = zoneIds.stream().distinct().sorted().collect(Collectors.toList());
            List<Integer> puzzleIds = yodesk.getPuzzles().getPuzzles().stream()
                    .filter(p -> (p.getItem2() != null && p.getItem2().equals(tileId)) || p.getItem1() != null && p.getItem1().equals(tileId))
                    .map(Puzzle::getIndex).collect(Collectors.toList());
            List<Integer> charactersIds = yodesk.getCharacters().getCharacters().stream().filter(c -> c.getTileIds().stream()
                    .anyMatch(i -> i.equals(tileId))).map(Character::getIndex).collect(Collectors.toList());
            List<String> tileNameIds = yodesk.getTileNames().getNames().stream().filter(t -> t.getTileId() == tileId).map(TileName::getName).collect(Collectors.toList());

            boolean isSafe = (zoneIds.size() + puzzleIds.size() + charactersIds.size() + tileNameIds.size()) == 0;
            if (isSafe) {
                deleteTile(tileId, -1);
            } else {
                int newTileId = JavaFxUtils.showDeleteTileConfirmation(tileId, zoneIds, puzzleIds, charactersIds, tileNameIds);
                if (newTileId > 0) {
                    deleteTile(tileId, newTileId);
                }
            }
            if (yodesk.getTiles().getTiles().size() >= tilesCount) {
                changesCount--;
            } else {
                changesCount++;
            }
            Log.debug("Deleted last tile");
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error deleting tile", e);
        }
    }

    private void deleteTile(int tileId, int newTileId) {

        yodesk.getTiles().deleteTile();
        if (null != yodesk.getTileGenders()) {
            yodesk.getTileGenders().deleteTileGender();
        }
        tilesFlowPane.getChildren().remove(tileId);
        zoneEditorTilesFlowPane.getChildren().remove(tileId);
        yodesk.getZones().getZones().forEach(z -> z.replaceTile(tileId, newTileId));
        yodesk.getPuzzles().getPuzzles().forEach(p -> p.replaceTile(tileId, newTileId));
        yodesk.getCharacters().getCharacters().forEach(c -> c.replaceTile(tileId, newTileId));
        yodesk.getTileNames().getNames().forEach(t -> t.replaceTile(tileId, newTileId));

        usedTiles.remove(usedTiles.size() - 1);
        drawEditZone();
    }

    private ChangeListener<Toggle> tilesAttributesToggleGroupListener() {
        return (ov, oldToggle, newToggle) -> {
            int tileId = ((Integer) (currentTile).getUserData());
            if (newToggle != null) {
                String binaryTileName = yodesk.getTiles().getTiles().get(tileId).getAttributesBinaryString();
                String newBinaryTileName = newToggle.getUserData().toString();
                if (!binaryTileName.equals(newBinaryTileName)) {
                    yodesk.getTiles().setAttributes(tileId, newBinaryTileName);
                    Log.debug(String.format("Set new attribute %s for tile #%s", newBinaryTileName, tileId));
                    changesCount++;
                }
            }
        };
    }

    private ChangeListener<? super Toggle> tilesGenderToggleGroupListener() {
        return (ov, oldToggle, newToggle) -> {
            int tileId = ((Integer) (currentTile).getUserData());
            if (newToggle != null) {
                String gender = yodesk.getTiles().getTiles().get(tileId).getAttributesBinaryString();
                String newGender = newToggle.getUserData().toString();
                if (!gender.equals(newGender)) {
                    yodesk.getTileGenders().getGenders().set(tileId, TileGender.byId(Integer.parseInt(newGender)));
                    Log.debug(String.format("Set new gender %s for tile #%s", newGender, tileId));
                    changesCount++;
                }
            }
        };
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
            drawTileOnBufferedImage(tileId, clipboardBufferedImage, x, y, false);
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
                image.setOnContextMenuRequested(e -> tilesContextMenu.show((Node) e.getSource(), e.getScreenX(), e.getScreenY()));

                int finalI = i;
                image.setOnDragDetected(event -> {
                    Dragboard db = image.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(Integer.toString(finalI));
                    db.setContent(content);
                    event.consume();
                });

                zoneEditorTilesFlowPane.getChildren().add(image);
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("Tiles display error", e);
        }
    }

    EventHandler<MouseEvent> mouseEnteredHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {

            currentTile = (Node) mouseEvent.getSource();
            int tileId = ((Integer) (currentTile).getUserData());
            String binaryTileName = yodesk.getTiles().getTiles().get(tileId).getAttributesBinaryString();
            String gender = "";
            if (null != yodesk.getTileGenders()) {
                gender = "; Gender: " + yodesk.getTileGenders().getGenders().get(tileId).name().toLowerCase();
            }
            if (tileAttributesMap.get(binaryTileName) != null) {
                statusLabel.setText("Tile #" + tileId + ": " + tileAttributesMap.get(binaryTileName).getText() + gender);
            }
        }
    };

    EventHandler<MouseEvent> mouseExitedHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            statusLabel.setText("");
        }
    };

    private void selectTileMenuItem(WindowEvent event) {
        int tileId = ((Integer) (currentTile).getUserData());
        String binaryTileName = yodesk.getTiles().getTiles().get(tileId).getAttributesBinaryString();
        tilesToggleGroup.getToggles().forEach(t -> t.setSelected(t.getUserData().toString().equals(binaryTileName)));
        if (null != yodesk.getTileGenders()) {
            String gender = String.valueOf(yodesk.getTileGenders().getGenders().get(tileId).getId());
            tilesGenderToggleGroup.getToggles().forEach(t -> t.setSelected(t.getUserData().toString().equals(gender)));
        }
    }

    @FXML
    private void drawEditZone() {

        if (getEditorZoneId() >= 0) {
            int zoneId = getEditorZoneId();
            drawEditorMap(zoneId);
            showMapInfo(zoneId);
            positionZoneOptionsTabPane();
            undoZoneEdit.setVisible(null != zoneHistoryMap.get(zoneId));
        }
    }

    private void showMapInfo(int zoneId) {

        Zone zone = yodesk.getZones().getZones().get(zoneId);

        boolean hasActiveTeleporter = zone.getType().equals(ZoneType.EMPTY) &&
                zone.getTileIds().stream().flatMap(l -> l.getColumn().stream()).anyMatch(t -> t.equals(537));
        boolean hasInActiveTeleporter = zone.getType().equals(ZoneType.EMPTY) &&
                zone.getTileIds().stream().flatMap(l -> l.getColumn().stream()).anyMatch(t -> t.equals(536));

        zoneSizeLabel.setText(zone.getWidth() + "x" + zone.getHeight());

        if (zone.getIzx2().getProvidedItems().size() == 1 && zone.getIzx2().getProvidedItems().get(0).equals(511)) {
            zoneTypeLabel.setText("Find Force");
        } else if (zone.getIzx2().getProvidedItems().size() == 1 && zone.getIzx2().getProvidedItems().get(0).equals(421)) {
            zoneTypeLabel.setText("Find Locator");
        } else {
            zoneTypeLabel.setText(StringUtils.capitalize(zone.getType().name().toLowerCase().replace("_", " ")));
        }

        if (hasActiveTeleporter || hasInActiveTeleporter) {
            zoneTypeLabel.setText(zoneTypeLabel.getText() + " with Teleporter");
        }

        zonePlanetLabel.setText(StringUtils.capitalize(zone.getPlanet().name().toLowerCase()));
        zoneActionsLabel.setText(String.valueOf(zone.getActions().size()));

        zoneIzaxUnnamedLabel.setText(String.valueOf(zone.getIzax().get_unnamed2()));
        zoneZax4Unnamed2Label.setText(String.valueOf(zone.getIzx4().get_unnamed2()));

        if (hasActiveTeleporter) {
            drawTileOnCanvas(834, zoneTypeCanvas, 0, 0, Color.valueOf("#F4F4F4"));
        } else if (hasInActiveTeleporter) {
            drawTileOnCanvas(833, zoneTypeCanvas, 0, 0, Color.valueOf("#F4F4F4"));
        } else {
            drawTileOnCanvas(zone.getType().getTileId(), zoneTypeCanvas, 0, 0, Color.valueOf("#F4F4F4"));
        }

        drawTilesOnFlowPane(zoneNpcsTilesFlowPane, zone.getIzx3().getNpcs());
        drawTilesOnFlowPane(zoneGoalTilesFlowPane, zone.getIzax().getGoalItems());
        drawTilesOnFlowPane(zoneProvidedItemsTilesFlowPane, zone.getIzx2().getProvidedItems());
        drawTilesOnFlowPane(zoneRequiredItemsTilesFlowPane, zone.getIzax().getRequiredItems());

        List<Integer> lootTiles = new ArrayList<>();

        zone.getIzax().getMonsters().forEach(monster -> {
            if (monster.getDropsLoot() > 0 && monster.getLoot() > 0) {
                lootTiles.add(monster.getLoot() - 1);
            }
        });

        drawTilesOnFlowPane(lootTilesFlowPane, lootTiles);
    }

    private void positionZoneOptionsTabPane() {

        if (zoneOptionsTabPane.getSelectionModel().getSelectedIndex() == 1) {
            zoneOptionsTabPane.setLayoutX(zoneEditorCanvas.getLayoutX() + zoneEditorCanvas.getWidth() + 8);
        } else {
            zoneOptionsTabPane.setLayoutX(zoneEditorCanvas.getLayoutX() + 18 * 32 + 8);
        }
    }

    public void showActionsButtonClick() {

        Zone zone = yodesk.getZones().getZones().get(zoneEditorListView.getSelectionModel().getSelectedIndex());
        List<Action> actions = zone.getActions();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);
            sb.append(getActionScript(action, i, false));
        }

        JavaFxUtils.showActionsWindow("Zone #" + zone.getIndex() + " actions", "Total actions count: " + zone.getActions().size(), sb.toString());
    }

    private String getActionScript(Action action, int id, boolean noText) {

        StringBuilder sb = new StringBuilder();

        sb.append("Action ").append(id).append("\n");
        sb.append("if").append("\n");
        action.getConditions().forEach(c -> {
            sb.append("    ").append(c.getOpcode().name().toLowerCase().replace("_", "-")).append(": ");
            sb.append(c.getArguments().stream().map(Object::toString).collect(Collectors.joining(" ")));
            if (!c.getText().isEmpty()) {
                sb.append(" \"").append(c.getText()).append("\"");
            }
            sb.append("\n");
        });
        sb.append("then").append("\n");
        action.getInstructions().forEach(c -> {
            sb.append("    ").append(c.getOpcode().name().toLowerCase().replace("_", "-")).append(": ");
            sb.append(c.getArguments().stream().map(Object::toString).collect(Collectors.joining(" ")));
            String text = noText ? "" : c.getText();
            sb.append(" \"").append(text).append("\"").append("\n");
        });
        sb.append("\n");
        return sb.toString();
    }

    private void drawEditorMap(int zoneId) {

        Platform.runLater(() -> {
            try {
                Zone zone = yodesk.getZones().getZones().get(zoneId);

                Log.debug("Show zone #" + zoneId);
                showZoneStatus(zone);

                zoneEditorCanvas.setWidth(zone.getWidth() * TILE_SIZE);
                zoneEditorCanvas.setHeight(zone.getHeight() * TILE_SIZE);

                drawZone(zoneEditorCanvas, zone);

            } catch (Exception e) {
                JavaFxUtils.showAlert(String.format("Map %s display error", zoneId), e);
            }
        });
    }

    public void zoneEditorCanvasDragDetected(MouseEvent event) {

        Dragboard db = zoneEditorCanvas.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();

        int x = ((int) event.getX()) & 0xFFFFFFE0;
        int y = ((int) event.getY()) & 0xFFFFFFE0;

        content.putString(String.format("%s|%s", x, y));
        db.setContent(content);
        event.consume();
    }

    public void zoneEditorCanvasDragOver(DragEvent event) {

        if (event.getGestureSource() != zoneEditorCanvas && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    public void zoneEditorCanvasDragEntered(DragEvent event) {

        if (event.getGestureSource() != zoneEditorCanvas && event.getDragboard().hasString()) {
            moveClipboardRectangle(event.getX(), event.getY());
            clipboardRectangle.setVisible(false);
        }
        event.consume();
    }

    public void zoneEditorCanvasDragExited(DragEvent event) {

        moveClipboardRectangle(event.getX(), event.getY());
        event.consume();
    }

    public void zoneEditorCanvasDragDropped(DragEvent event) {

        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            int tileId = Integer.parseInt(db.getString());
            int x = ((int) event.getX()) / TILE_SIZE;
            int y = ((int) event.getY()) / TILE_SIZE;
            int layerId = Integer.parseInt((String) zoneLayerToggleGroup.getSelectedToggle().getUserData());

            changeZoneSpot(x, y, layerId, tileId);
        }
        event.setDropCompleted(true);

        event.consume();
    }

    public void zoneEditorCanvasMouseMoved(MouseEvent event) {

        mapX = (int) (event.getX() / TILE_SIZE);
        mapY = (int) (event.getY() / TILE_SIZE);

        Zone zone = getEditorZone();
        List<Integer> column = zone.getTileIds().get(mapY * zone.getWidth() + mapX).getColumn();
        showZoneStatus(zone);

        List<String> lines = new ArrayList<>();
        lines.add(String.format("Top tileId: %s\nMiddle tileId: %s\nBottom tileId: %s", column.get(2), column.get(1), column.get(0)));

        List<Monster> monsters = zone.getIzax().getMonsters().stream().filter(m -> m.getX() == mapX && m.getY() == mapY).collect(Collectors.toList());
        List<Hotspot> hotspots = zone.getHotspots().stream().filter(m -> m.getX() == mapX && m.getY() == mapY).collect(Collectors.toList());

        if (!monsters.isEmpty()) {
            Monster monster = monsters.get(0);

            Character character = yodesk.getCharacters().getCharacters().get(monster.getCharacter());


            lines.add("Monster: " + character.getName());
            lines.add("Movement type: " + StringUtils.capitalize(character.getMovementType().name().toLowerCase().replace("_", " ")));

            int weapon = yodesk.getCharacterWeapons().getWeapons().get(monster.getCharacter()).getReference();

            if (weapon == 65535) {
                lines.add("Weapon: none");
            } else if (character.getType() == CharacterType.ENEMY) {
                lines.add("Weapon: " + yodesk.getCharacters().getCharacters().get(weapon).getName());
            } else {
                lines.add("Sound: " + yodesk.getSounds().getSounds().get(weapon));
            }
            lines.add("Damage: " + yodesk.getCharacterAuxiliaries().getAuxiliaries().get(monster.getCharacter()).getDamage());
            lines.add("Health: " + yodesk.getCharacterWeapons().getWeapons().get(monster.getCharacter()).getHealth());
            if (monster.getDropsLoot() > 0) {
                if (monster.getLoot() == 0xFFFF) {
                    lines.add("Loot: Zone's quest item");
                } else if (monster.getLoot() > 0) {
                    lines.add("Loot: " + yodesk.getTileNames().getNames().stream().filter(t -> t.getTileId() == monster.getLoot() - 1)
                            .map(TileName::getName).findFirst().orElse(String.valueOf(monster.getLoot() - 1)));
                }
            }
            if (monster.getWaypoints().get(0).getX() != 4294967295L) {
                lines.add("Waypoints: " + String.format("[%s;%s],", Long.toHexString(monster.getWaypoints().get(0).getX()), Long.toHexString(monster.getWaypoints().get(0).getY())));
                lines.add(monster.getWaypoints().subList(1, monster.getWaypoints().size()).stream().map(w -> String.format("[%s;%s]", Long.toHexString(w.getX()), Long.toHexString(w.getY()))).collect(Collectors.joining(", ")));
            }
        }

        if (!hotspots.isEmpty()) {
            lines.add("Hotspot: " + StringUtils.capitalize(hotspots.get(0).getType().name().toLowerCase().replace("_", " ")));
            lines.add("Argument: " + hotspots.get(0).getArgument());
            lines.add("Enabled: " + hotspots.get(0).getEnabled());
        }

        zoneSpotStatusLabel.setText(String.join("\n", lines));
        zoneSpotStatusLabel.setLayoutX(event.getX() + zoneSpotStatusLabel.getWidth() - 20);
        if (event.getY() + zoneSpotStatusLabel.getHeight() + 20 > zoneEditorCanvas.getLayoutY() + 18 * TILE_SIZE) {
            zoneSpotStatusLabel.setLayoutY(event.getY() - zoneSpotStatusLabel.getHeight() - 20);
        } else {
            zoneSpotStatusLabel.setLayoutY(event.getY() + 20);
        }

        statusCanvas.setHeight(TILE_SIZE);
        statusCanvas.setWidth(TILE_SIZE * 3);
        statusCanvas.getGraphicsContext2D().clearRect(0, 0, statusCanvas.getWidth(), statusCanvas.getHeight());
        drawBorderOnCanvas(statusCanvas, 0, 0, Color.BLACK);
        drawBorderOnCanvas(statusCanvas, TILE_SIZE, 0, Color.BLACK);
        drawBorderOnCanvas(statusCanvas, TILE_SIZE * 2, 0, Color.BLACK);
        if (column.get(2) < yodesk.getTiles().getTiles().size()) {
            drawTileOnCanvas(column.get(2), statusCanvas, 0, 0, Color.rgb(0xF4, 0xF4, 0xF4));
        }
        if (column.get(1) < yodesk.getTiles().getTiles().size()) {
            drawTileOnCanvas(column.get(1), statusCanvas, TILE_SIZE, 0, Color.rgb(0xF4, 0xF4, 0xF4));
        }
        if (column.get(0) < yodesk.getTiles().getTiles().size()) {
            drawTileOnCanvas(column.get(0), statusCanvas, TILE_SIZE * 2, 0, Color.rgb(0xF4, 0xF4, 0xF4));
        }

        zoneSpotStatusLabel.setVisible(true);
        statusCanvas.setVisible(true);
    }

    public void zoneEditorCanvasMouseExited() {
        showZoneStatus(getEditorZone());
        zoneSpotStatusLabel.setVisible(false);
        statusCanvas.setVisible(false);
    }

    public void clearBottomLayer() {
        changeZoneSpot(mapX, mapY, 0, 0xFFFF);
    }

    public void clearMiddleLayer() {
        changeZoneSpot(mapX, mapY, 1, 0xFFFF);
    }

    public void clearTopLayer() {
        changeZoneSpot(mapX, mapY, 2, 0xFFFF);
    }

    private void changeZoneSpot(int x, int y, int layerId, int tileId) {

        Zone zone = getEditorZone();
        int oldValue = zone.getTileIds().get(y * zone.getWidth() + x).getColumn().get(layerId);

        modifyZoneSpot(zone.getIndex(), x, y, layerId, tileId);

        addToZonesHistory(zone.getIndex(), x, y, layerId, oldValue, tileId);

        undoZoneEdit.setVisible(true);
        changesCount++;

        Log.debug(String.format("Changed tile [%s, %s] on layer #%s to %s", tileId, x, y, layerId));
    }

    private void addToZonesHistory(int zoneId, int x, int y, int layerId, int oldValue, int newValue) {

        ArrayDeque<ZoneHistory> histories = zoneHistoryMap.get(zoneId);
        if (null == histories) {
            histories = new ArrayDeque<>();
        }

        histories.add(new ZoneHistory(zoneId, x, y, layerId, oldValue, newValue));
        zoneHistoryMap.put(zoneId, histories);
    }

    public void undoZoneEditClick() {

        ArrayDeque<ZoneHistory> histories = zoneHistoryMap.get(getEditorZoneId());

        int zoneId = getEditorZoneId();
        ZoneHistory history = zoneHistoryMap.get(zoneId).pollLast();

        if (history != null) {
            modifyZoneSpot(zoneId, history.getX(), history.getY(), history.getLayerId(), history.getOldValue());
            changesCount--;

            if (histories.isEmpty()) {
                zoneHistoryMap.remove(zoneId);
                undoZoneEdit.setVisible(false);
            }

            Log.debug("Undo last zone change");
        }
    }

    private void modifyZoneSpot(int zoneId, int x, int y, int layerId, int tileId) {

        Zone zone = yodesk.getZones().getZones().get(zoneId);
        List<Integer> column = zone.getTileIds().get(y * zone.getWidth() + x).getColumn();
        column.set(layerId, tileId);

        drawZoneSpot(zoneEditorCanvas, zone, x, y);

        if (zone.getIndex() == zoneEditorListView.getSelectionModel().getSelectedIndex()) {
            drawZoneSpot(zoneEditorCanvas, zone, x, y);
        }
    }

    private void showZoneStatus(Zone zone) {
        statusLabel.setText("Zone: " + zone.getIndex() + " (" + zone.getPlanet().name() + ")");
    }

    private void drawZone(Canvas canvas, int zoneId) {
        drawZone(canvas, yodesk.getZones().getZones().get(zoneId));
    }

    private void drawZone(Canvas canvas, Zone zone) {

        fillZone(canvas, transparentColor);

        if (bottomCheckBox.isSelected()) {
            drawZoneLayer(canvas, zone, 0);
        }
        if (middleCheckBox.isSelected()) {
            drawZoneLayer(canvas, zone, 1);
        }
        if (showZoneMonstersCheckBox.isSelected()) {
            zone.getIzax().getMonsters().forEach(m -> {
                int tileId = yodesk.getCharacters().getCharacters().get(m.getCharacter()).getTileIds().get(0);
                drawTileOnCanvas(tileId, canvas, m.getX() * TILE_SIZE, m.getY() * TILE_SIZE, null);
                drawBorderOnCanvas(canvas, m.getX() * TILE_SIZE, m.getY() * TILE_SIZE, Color.rgb(127, 255, 255));
            });
        }
        if (topCheckBox.isSelected()) {
            drawZoneLayer(canvas, zone, 2);
        }
        if (showZoneHotspotsCheckBox.isSelected()) {
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
        if (showZoneMonstersCheckBox.isSelected()) {
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
        if (showZoneHotspotsCheckBox.isSelected()) {
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

    public void layerCheckBoxClick(ActionEvent event) {

        Platform.runLater(() -> {
            CheckBox checkBox = (CheckBox) event.getSource();
            String layerName = ZoneLayer.byId(checkBox.getUserData().toString());
            if (checkBox.isSelected()) {
                Log.debug(String.format("Show layer: %s", layerName));
            } else {
                Log.debug(String.format("Hide layer: %s", layerName));
            }
            drawZone(zoneEditorCanvas, getEditorZone());
        });
    }

    private Zone getEditorZone() {
        return yodesk.getZones().getZones().get(getEditorZoneId());
    }

    private int getEditorZoneId() {
        return zoneEditorListView.getSelectionModel().getSelectedIndex();
    }

    public void openMenuItemClick() {

        if (changesCount == 0 || JavaFxUtils.showModifiedConfirmation()) {
            changesCount = 0;
            openFile();
        }
    }

    public static void openFile() {

        try {
            File file = JavaFxUtils.showEXELoadDialog("Open Executable File", lastVisitedDirectory, "yodesk.dta");

            if (file != null) {
                exeFile = file;
                lastVisitedDirectory = exeFile.toPath().getParent().toString();
                dtaFile = IOUtils.getDtaPath(file.toPath().getParent());
                loadFileToArray(file);
                yodesk = Yodesk.fromFile(dtaFile.toString(), sourceCharset.getCode());

                usedTiles.clear();
                JavaFxUtils.showMainPanel();
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("Executable file loading error", e);
        }
    }

    public static void loadFileToArray(File file) throws IOException {

        Log.clear();
        Path exePath = file.toPath();
        Path dtaPath = IOUtils.getDtaPath(exePath.getParent());

        loadExeFile(exePath);
        loadDtaFile(dtaPath);

        release = Config.releases.stream().filter(r -> r.getExeCrc32().equals(exeCrc32) && r.getDtaCrc32().equals(dtaCrc32)).findFirst().orElse(null);

        if (null == release) {
            release = new Release();
            release.setCharset("");
            release.setId("unk");
            release.setTitle("Unknown combination of files");
            release.setDtaCrc32("????????");
            release.setExeCrc32("????????");
            release.setDescription("This is an unknown combination of DTA/EXE files.\n" +
                    "Perhaps this is your new translation of the game, which has not yet been included in our database.\n" +
                    "If so, then you should copy all translation resources to the directory output-unk.\n" +
                    "Or maybe you found a new release of the game.\n" +
                    "Be sure to send it to us so we can analyze it and add to our database. E-mail: tv-games@mail.ru");
        }

        sourceCharset = getCharset(release.getCharset());
        destinationCharset = sourceCharset;
        exeDump.setCharset(sourceCharset.getCode());

        Log.debug("DTA revision: " + release.getTitle());
        Log.appendOk("Load");
    }

    private static void loadDtaFile(Path dtaPath) throws IOException {

        Log.debug(String.format("Loading %s...", dtaPath));

        dtaCrc32 = intToHex(BinaryUtils.crc32(dtaPath), 8);

        dtaSize = Files.size(dtaFile);

        Log.debug("Size: " + dtaSize);
        Log.debug("CRC-32: " + dtaCrc32);
    }

    private static void loadExeFile(Path exePath) throws IOException {

        Log.debug(String.format("Loading %s...", exePath));
        exeDump = new Dump(exePath);
        exeDump.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        exeDump.setIndex(0);

        exeCrc32 = BinaryUtils.crc32hex(exeDump.getDump());

        getControlsBounds();

        int paletteStartIndex = exeDump.findAddress(PaletteUtils.getBGRASample(gamePalette));

        if (paletteStartIndex < 0) {
            throw new RuntimeException("Palette isn't found in EXE file");
        }

        boolean otherPaletteLocation = (STANDARD_PALETTE_LOCATION != paletteStartIndex);

        dumpPalette(paletteStartIndex);

        Log.debug("Size: " + exeDump.size());
        Log.debug("CRC-32: " + exeCrc32);
        Log.debug("Palette address: " + intToHex(paletteStartIndex, 6));

        if (otherPaletteLocation) {
            Log.debug("Unknown palette location. Standard is: 0x550F0");
        }
    }

    private static void getControlsBounds() {

        windowMap = exeDump.findValueAddressByMask("????0000 6A08FF"); // 525
        window2Map = exeDump.findValueAddressByMask("C74708 ????0000 C7470C"); // 525
        gridViewMap = exeDump.findValueAddressByMask("C7828C320000 ????0000 C78294"); // 489
        scrollBarLeftMap = exeDump.findValueAddressByMask("C78294320000 ????0000 B8FC00"); // 496
        scrollBarRightMap = exeDump.findValueAddressByMask("C7829C320000 ????0000 C782A4"); // 512

        toolTipMap = exeDump.findValueAddressByMask("4F4433F6 ?????????? 8BCF8947"); // C2039B148D     833284150413

        toolTipFontMap = exeDump.findValueAddressByMask("5668900100005656566A ?? FF15"); // -8 FFFFFFF8
        toolTipFont2Map = exeDump.findValueAddressByMask("0100006A006A006A006A ?? FF15"); // -8 FFFFFFF8

        if (toolTipMap.isEmpty()) { // Spanish, German versions                                         vvvvvv vvvv
            toolTipMap = exeDump.findValueAddressByMask("4E4433FF ?????????? 8BCE89"); // 4e4433ff 8D149B 03C2 8BCE89
        }
    }

    private static void dumpPalette(int paletteStartIndex) {

        gamePalette = PaletteUtils.dumpBGRAPalette(exeDump.getDump(), paletteStartIndex);
        Config.updatePalette();
    }

    public void dumpAllMenuItemClick() {

        Log.debug("Dump all resources...");
        IOUtils.createDirectories(resourcesPath);
        IOUtils.createDirectories(dumpsPath);
        IOUtils.createDirectories(translatePath);
        dumpAllSectionsButtonClick();
        saveHighMidStructureButtonClick();
        saveStartupScreenButtonClick();
        savePaletteButtonClick();
        dumpPaletteButtonButtonClick();
        saveSoundsListToFileButtonClick();
        saveTilesToSeparateFilesClick();
        saveTilesToOneFileClick();
        saveZonesToFilesButtonClick();
        dumpActionsTextToDocxClick();
        dumpPuzzlesTextToDocxClick();
        saveCharactersToFilesButtonClick();
        generateCharactersReportButtonClick();
        saveNamesToFileButtonClick();
        saveNamesTextToDocxCLick();
    }

    public void saveMenuItemClick() {

        if (changesCount == 0) {
            showAlert("Files have not been changed!", "There is nothing to save.", Alert.AlertType.INFORMATION);
        } else if (JavaFxUtils.showOverwriteConfirmation()) {
            Platform.runLater(() -> {
                try {
                    String[] chunks = dtaFile.getFileName().toString().split("\\.");
                    Path backupPath = exeFile.toPath().getParent().resolve(chunks[0] + ".exe.bak");
                    Log.debug("Create backup: " + backupPath);
                    IOUtils.copyFile(exeFile.toPath(), backupPath);
                    Log.debug("Save EXE file");
                    exeDump.saveToFile(exeFile);
                    Log.appendOk("Save EXE file");

                    chunks = dtaFile.getFileName().toString().split("\\.");
                    backupPath = dtaFile.getParent().resolve(chunks[0] + ".dta.bak");
                    Log.debug("Create backup: " + backupPath);
                    IOUtils.copyFile(dtaFile, backupPath);
                    Log.debug("Save DTA file");
                    yodesk.write(new ByteBufferKaitaiOutputStream(dtaFile));

                    changesCount = 0;

                } catch (Exception e) {
                    JavaFxUtils.showAlert("DTA file saving error", e);
                }
            });
        }
    }

    public void saveAsMenuItemClick() {

        if (changesCount == 0) {
            showAlert("Files have not been changed!", "There is nothing to save.", Alert.AlertType.INFORMATION);
        } else if (JavaFxUtils.showOverwriteConfirmation()) {
            Platform.runLater(() -> {
                try {
                    File file = JavaFxUtils.showDTASaveDialog("Save DTA File", lastVisitedDirectory, "yodesk.dta");

                    if (file != null) {
                        Log.debug("Save DTA file: " + file.getName());
                        lastVisitedDirectory = file.toPath().getParent().toString();
                        yodesk.write(new ByteBufferKaitaiOutputStream(file));

                        changesCount = 0; // if ok

                        Log.appendOk("Save DTA file");
                    }
                } catch (Exception e) {
                    JavaFxUtils.showAlert("DTA file saving error", e);
                }
            });
        }
    }

    public void closeMenuItemClick() {
        changesCount = 0;
        JavaFxUtils.showPrimaryPanel();
    }

    public void exitMenuItemClick() {

        if (changesCount == 0 || JavaFxUtils.showModifiedConfirmation()) {
            Platform.exit();
        }
    }

    public void transparentColorMenuItemClick() {

        Platform.runLater(() -> {
            transparentColor = (Color) transparentColorToggleGroup.getSelectedToggle().getUserData();
            updatePalette();
            Log.debug("Selected new transparent color: " + transparentColor.toString());
            ObservableList<Node> children = tilesFlowPane.getChildren();
            for (int i = 0; i < children.size() - 2; i++) {
                ImageView imageView = (ImageView) children.get(i);
                imageView.setImage(drawTileOnImage(i));
            }
            children = zoneEditorTilesFlowPane.getChildren();
            for (int i = 0; i < children.size() - 1; i++) {
                ImageView imageView = (ImageView) children.get(i);
                imageView.setImage(drawTileOnImage(i));
            }
            drawTitleImage();
            clipboardBufferedImage = ImageUtils.replaceIcm(clipboardBufferedImage, icm);
            ImageUtils.drawOnCanvas(clipboardBufferedImage, clipboardCanvas, transparentColor);
            drawZone(zoneEditorCanvas, zoneEditorListView.getSelectionModel().getSelectedIndex());
            namesTableView.refresh();
        });
    }

    public void howToMenuItemClick() {
        JavaFxUtils.showWindow("How to translate Yoda Stories...", "HowTo.fxml");
    }

    public void aboutMenuItemClick() {
        JavaFxUtils.showWindow("About...", "About.fxml");
    }

    public void dumpAllSectionsButtonClick() {

        Platform.runLater(() -> {
            try {
                Path path = dumpsPath.resolve("raw");
                IOUtils.createDirectories(path);
                for (CatalogEntry entry : yodesk.getCatalog()) {
                    Section name = entry.getSection();
                    IOUtils.saveBytes(path.resolve(name + ".bin"), entry.getBytes());
                    Log.debug(name + " is saved");
                }
                Log.appendOk("Dump all sections");
            } catch (Exception e) {
                JavaFxUtils.showAlert("Sections dumping error", e);
            }
        });
    }

    public void saveHighMidStructureButtonClick() {

        Platform.runLater(() -> {
            try {
                String name = "structure" + E_MD;
                IOUtils.createDirectories(dumpsPath);
                IOUtils.saveTextFile(new StructureDump(yodesk).dumpStructure(), dumpsPath.resolve(name));
                Log.debug(name + " is saved");
            } catch (Exception e) {
                JavaFxUtils.showAlert("Data file structure dumping error", e);
            }
        });
    }

    public void saveStartupScreenButtonClick() {

        Platform.runLater(() -> {
            try {
                Path path = translatePath.resolve("startup" + E_BMP);
                Path path2 = resourcesPath.resolve("startup" + E_BMP);
                Log.debug("Saving startup screen: " + path);
                IOUtils.createDirectories(translatePath);
                IOUtils.createDirectories(resourcesPath);
                if (titleScreenImageView.getUserData() != null) {
                    BMPWriter.write((BMPImage) titleScreenImageView.getUserData(), path);
                    BMPWriter.write((BMPImage) titleScreenImageView.getUserData(), path2);
                } else {
                    BMPWriter.write8bit(titleScreenImageView, path);
                    BMPWriter.write8bit(titleScreenImageView, path2);
                }
                Log.appendOk("Save startup screen");

            } catch (Exception e) {
                JavaFxUtils.showAlert("Startup screen saving error", e);
            }
        });
    }

    public void loadStartupScreenButtonClick() {

        Platform.runLater(() -> {
            try {
                Path path = translatePath.resolve("startup" + E_BMP);
                Log.debug("Loading startup screen: " + path);
                BMPImage titleImage = BMPReader.readExt(path);

                // Update in memory
                for (int y = 0; y < titleImage.getHeight(); y++) {
                    for (int x = 0; x < titleImage.getWidth(); x++) {

                        int sample = titleImage.getImage().getRaster().getSample(x, y, 0);
                        yodesk.getStartupImage().getPixels()[y * titleImage.getWidth() + x] = new Integer(sample).byteValue();
                    }
                }

                changesCount = 0;

                WritableImage image = new WritableImage(titleImage.getWidth(), titleImage.getHeight());
                SwingFXUtils.toFXImage(titleImage.getImage(), image);
                titleScreenImageView.setImage(image);
                titleScreenImageView.setUserData(titleImage);
                Log.appendOk("Load startup screen");

            } catch (Exception e) {
                JavaFxUtils.showAlert("Startup screen loading error", e);
            }
        });
    }

    public void savePaletteButtonClick() {

        Platform.runLater(() -> {
            try {
                IOUtils.createDirectories(resourcesPath);
                Path path = resourcesPath.resolve("palette" + E_BMP);
                Log.debug("Saving palette image: " + path);
                BMPWriter.write8bit(paletteCanvas, path);
                Log.appendOk("Save palette image");
            } catch (Exception e) {
                JavaFxUtils.showAlert("Palette loading error", e);
            }
        });
    }

    public void dumpPaletteButtonButtonClick() {

        Platform.runLater(() -> {
            try {
                Log.debug("Saving palettes...");
                IOUtils.createDirectories(translatePath);
                PaletteUtils.saveToFile(gamePalette, translatePath.resolve("palette.pal"));
                PaletteUtils.saveSafeToFile(gamePalette, translatePath.resolve("palette-safe.pal"));
                PaletteUtils.saveToFile(fuchsiaPalette, translatePath.resolve("palette-fuchsia.pal"));
                PaletteUtils.saveSafeToFile(fuchsiaPalette, translatePath.resolve("palette-fuchsia-safe.pal"));
                Log.appendOk("Dump palettes");
            } catch (Exception e) {
                JavaFxUtils.showAlert("Palette saving error", e);
            }
        });
    }

    public void saveSoundsListToFileButtonClick() {

        Platform.runLater(() -> {
            try {
                Path path = dumpsPath.resolve("sounds" + E_TXT);
                Log.debug("Saving sounds to file: " + path);
                IOUtils.saveTextFile(yodesk.getSounds().getSounds(), path);
                Log.appendOk("Save sounds");
            } catch (Exception e) {
                JavaFxUtils.showAlert("Sounds list saving error", e);
            }
        });
    }

    public void saveTilesToSeparateFilesClick() {

        runInBackground(() -> {
            try {
                Log.debug("Saving tiles...");
                IOUtils.createDirectories(resourcesPath);

                if (decimalFilenamesCheckBox.isSelected()) {
                    saveTiles();
                }
                if (hexFilenamesCheckBox.isSelected()) {
                    saveTilesHex();
                }
                if (groupByAttributesFilenamesCheckBox.isSelected()) {
                    saveTilesByAttr();
                }
                if (groupByGenderCheckBox.isSelected()) {
                    saveTilesByGender();
                }
            } catch (Exception e) {
                Log.error("Error saving tiles to separate files: " + e.getMessage());
            }
        }, () -> Log.appendOk("Save tiles to separate files"));
    }

    private void saveTiles() throws IOException {

        Path tilesPath = resourcesPath.resolve("Tiles");
        IOUtils.createDirectories(tilesPath);
        for (int i = 0; i < yodesk.getTiles().getTiles().size(); i++) {
            Path path = tilesPath.resolve(String.format("%04d", i) + E_BMP);
            BMPWriter.write(getTile(i, icm), path);

            //TODO may be use PNG. Sample code. Now in used in ExcelUtils
            ImageIO.write(getTile(i, icm), "PNG", tilesPath.resolve(String.format("%04d", i) + ".png").toFile());
        }
    }

    private void saveTilesHex() throws IOException {

        Path hexPath = resourcesPath.resolve("TilesHex");
        IOUtils.createDirectories(hexPath);
        for (int i = 0; i < yodesk.getTiles().getTiles().size(); i++) {
            BMPWriter.write(getTile(i, icm), hexPath.resolve(String.format("%04X", i) + E_BMP));
        }
    }

    private void saveTilesByAttr() throws IOException {

        Path attrPath = resourcesPath.resolve("TilesByAttr");
        Path attrNamesPath = resourcesPath.resolve("TilesByAttrName");
        IOUtils.createDirectories(attrPath);
        IOUtils.createDirectories(attrNamesPath);
        Map<String, List<Integer>> byAttr = new HashMap<>();
        for (int i = 0; i < yodesk.getTiles().getTiles().size(); i++) {
            String attrHex = yodesk.getTiles().getTiles().get(i).getAttributesBinaryString();
            List<Integer> value = byAttr.computeIfAbsent(attrHex, k -> new ArrayList<>());
            value.add(i);
        }
        for (Map.Entry<String, List<Integer>> e : byAttr.entrySet()) {
            Path path = attrPath.resolve(e.getKey() + " - " + getTileName(e.getKey()) + " (" + e.getValue().size() + ")");
            IOUtils.createDirectories(path);
            for (Integer i : e.getValue()) {
                BMPWriter.write(getTile(i, icm), path.resolve(String.format("%04d", i) + E_BMP));
            }
            path = attrNamesPath.resolve(getTileName(e.getKey()) + " (" + e.getValue().size() + ")");
            IOUtils.createDirectories(path);
            for (Integer i : e.getValue()) {
                BMPWriter.write(getTile(i, icm), path.resolve(String.format("%04d", i) + E_BMP));
            }
        }
    }

    private void saveTilesByGender() throws IOException {

        Path genPath = resourcesPath.resolve("TilesByGen");

        if (null != yodesk.getTileGenders()) {
            IOUtils.createDirectories(genPath);
        }

        for (int i = 0; i < yodesk.getTiles().getTiles().size(); i++) {

            //TGEN
            if (null != yodesk.getTileGenders()) {
                int ii = i;
                String hex = String.format("%04X", yodesk.getTileGenders().getGenders().get(i).getId());
                String name = yodesk.getTileNames().getFilteredNames().stream().filter(n -> n.getTileId() == ii).map(TileName::getName).findFirst().orElse("");
                name = name.isEmpty() ? name : " " + name;
                IOUtils.createDirectories(genPath.resolve(hex));
                BMPWriter.write(getTile(i, icm), genPath.resolve(hex).resolve(String.format("%04d", i) + name + E_BMP));
            }
        }
    }

    private String getTileName(String byteString) {

        StringBuilder title = new StringBuilder();
        appendIfSet(title, byteString, 1, "Floor");
        appendIfSetAndSet(title, byteString, 1, 16, "Doorway");
        appendIfSet(title, byteString, 2, "Object");
        appendIfSet(title, byteString, 3, "Draggable");
        appendIfSet(title, byteString, 4, "Roof");
        appendIfSet(title, byteString, 5, "Map");
        appendIfSetAndSet(title, byteString, 5, 17, "Town");
        appendIfSetAndSet(title, byteString, 5, 18, "PuzzleUnsolved");
        appendIfSetAndSet(title, byteString, 5, 19, "PuzzleSolved");
        appendIfSetAndSet(title, byteString, 5, 20, "TravelUnsolved");
        appendIfSetAndSet(title, byteString, 5, 21, "TravelSolved");
        appendIfSetAndSet(title, byteString, 5, 22, "BlockadeNorthUnsolved");
        appendIfSetAndSet(title, byteString, 5, 23, "BlockadeSouthUnsolved");
        appendIfSetAndSet(title, byteString, 5, 24, "BlockadeWestUnsolved");
        appendIfSetAndSet(title, byteString, 5, 25, "BlockadeEastUnsolved");
        appendIfSetAndSet(title, byteString, 5, 26, "BlockadeNorthSolved");
        appendIfSetAndSet(title, byteString, 5, 27, "BlockadeSouthSolved");
        appendIfSetAndSet(title, byteString, 5, 28, "BlockadeWestSolved");
        appendIfSetAndSet(title, byteString, 5, 29, "BlockadeEastSolved");
        appendIfSetAndSet(title, byteString, 5, 30, "GoalUnsolved");
        appendIfSetAndSet(title, byteString, 5, 31, "YouAreHere");
        appendIfSet(title, byteString, 6, "Weapon");
        appendIfSetAndSet(title, byteString, 6, 16, "BlasterLow");
        appendIfSetAndSet(title, byteString, 6, 17, "BlasterHigh");
        appendIfSetAndSet(title, byteString, 6, 18, "Lightsaber");
        appendIfSetAndSet(title, byteString, 6, 19, "TheForce");
        appendIfSet(title, byteString, 7, "Item");
        appendIfSetAndSet(title, byteString, 7, 16, "Keycard");
        appendIfSetAndSet(title, byteString, 7, 17, "Tool");
        appendIfSetAndSet(title, byteString, 7, 18, "Part");
        appendIfSetAndSet(title, byteString, 7, 19, "Valuable");
        appendIfSetAndSet(title, byteString, 7, 20, "Locator");
        appendIfSetAndSet(title, byteString, 7, 22, "Edible");
        appendIfSet(title, byteString, 8, "Character");
        appendIfSetAndSet(title, byteString, 8, 16, "Hero");
        appendIfSetAndSet(title, byteString, 8, 17, "Enemy");
        appendIfSetAndSet(title, byteString, 8, 18, "NPC");

        appendIfSet(title, byteString, 0, "Transparent");

        return title.toString().trim();
    }

    private void appendIfSet(StringBuilder title, String byteString, int index, String name) {
        if (isSet(byteString, index)) {
            title.append(splitCamelCase(name)).append(" ");
        }
    }

    private void appendIfSetAndSet(StringBuilder title, String byteString, int index, int index2, String name) {
        if (isSet(byteString, index) && isSet(byteString, index2)) {
            title.append(splitCamelCase(name)).append(" ");
        }
    }

    private boolean isSet(String byteString, int index) {
        return byteString.charAt(byteString.length() - index - 1) == '1';
    }

    public void saveTilesToOneFileClick() {

        runInBackground(() -> {
            try {
                IOUtils.createDirectories(resourcesPath);
                int width = Integer.parseInt(tilesInARowTextField.getText());
                int height = (int) Math.ceil(yodesk.getTiles().getTiles().size() * 1.0 / width);
                Path path = resourcesPath.resolve(String.format("tiles%sx%s%s", width, height, E_BMP));
                Log.debug("Saving tiles to one file: " + path);

                BufferedImage canvas = new BufferedImage(width * TILE_SIZE, height * TILE_SIZE, BufferedImage.TYPE_BYTE_INDEXED, icm);

                int k = 0;
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if ((y * width + x) < yodesk.getTiles().getTiles().size()) {
                            drawTileOnBufferedImage(k, canvas, x * TILE_SIZE, y * TILE_SIZE, true);
                            k++;
                        }
                    }
                }
                BMPWriter.write(canvas, path);

            } catch (Exception e) {
                Log.error("Error saving tiles to a single file: " + e.getMessage());
            }
        }, () -> Log.appendOk("Save tiles to single files"));
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

    public void loadClipboardImageClick() {

        Platform.runLater(() -> {
            try {

                String initialFile = (null == clipboardFile) ? "clipboard.bmp" : clipboardFile.getName();
                String initialDir = (null == clipboardFile) ? translatePath.toString() : clipboardFile.getParent();
                File file = JavaFxUtils.showBMPLoadDialog("Load clipboard image", initialDir, initialFile);
                if (file != null) {
                    Log.debug("Loading clipboard file: " + file.getName());
                    clipboardFile = file;
                    clipboardBufferedImage = BMPReader.read(file);
                    ImageUtils.drawOnCanvas(clipboardBufferedImage, clipboardCanvas, transparentColor);
                    Log.appendOk("Save clipboard image");
                }
            } catch (Exception e) {
                JavaFxUtils.showAlert("Clipboard image loading error", e);
            }
        });
    }

    public void saveClipboardImageClick() {

        Platform.runLater(() -> {
            try {
                String initialFile = (null == clipboardFile) ? "clipboard.bmp" : clipboardFile.getName();
                String initialDir = (null == clipboardFile) ? translatePath.toString() : clipboardFile.getParent();
                File file = JavaFxUtils.showBMPSaveDialog("Save Clipboard image", initialDir, initialFile);
                if (file != null) {
                    Log.debug("Saving clipboard file: " + file.getName());
                    BMPWriter.write(clipboardBufferedImage, file);
                    Log.appendOk("Save clipboard image");
                }
            } catch (Exception e) {
                JavaFxUtils.showAlert("Clipboard image saving error", e);
            }
        });
    }

    public void clearClipboardImageClick() {
        clipboardBufferedImage = ImageUtils.clearBufferedImage(clipboardBufferedImage);
        ImageUtils.fillCanvas(clipboardCanvas, transparentColor);
        Log.debug("Clean clipboard image");
    }

    public void saveZonesToFilesButtonClick() {

        Log.debug("Save zones...");
        Log.debug("Total count: " + yodesk.getZones().getZones().size());

        runInBackground(() -> {
            try {
                IOUtils.createDirectories(resourcesPath);
                IOUtils.createDirectories(dumpsPath);

                /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonElement element = gson.toJsonTree(yodesk.getZones().getZones());
                element.getAsJsonArray().forEach(zone -> zone.getAsJsonObject().remove("actions"));
                Path path = dumpsPath.resolve("zonesNoActions" + E_JSON);
                FileWriter writer = new FileWriter(path.toFile());
                gson.toJson(element, writer);
                writer.flush();*/

                IOUtils.saveTextFile(new StructureDump(yodesk).dumpZoneTilesStructure(), dumpsPath.resolve("zoneTiles" + E_MD));

                if (normalSaveCheckBox.isSelected() || groupByAttributesCheckBox.isSelected() || groupByPlanetTypeCheckBox.isSelected()) {

                    for (int i = 0; i < yodesk.getZones().getZones().size(); i++) {

                        Zone zone = yodesk.getZones().getZones().get(i);
                        BufferedImage canvas = new BufferedImage(zone.getWidth() * TILE_SIZE, zone.getHeight() * TILE_SIZE, BufferedImage.TYPE_BYTE_INDEXED, icm);
                        drawBIZone(canvas, zone);

                        if (normalSaveCheckBox.isSelected()) {
                            Path path = resourcesPath.resolve("Zones");
                            IOUtils.createDirectories(path);
                            BMPWriter.write(canvas, path.resolve(String.format("%03d", zone.getIndex()) + E_BMP));
                        }

                        if (groupByAttributesCheckBox.isSelected()) {
                            Path path = resourcesPath.resolve("ZonesByType").resolve(zone.getType().name());
                            IOUtils.createDirectories(path);
                            BMPWriter.write(canvas, path.resolve(String.format("%03d", zone.getIndex()) + E_BMP));
                        }

                        if (groupByPlanetTypeCheckBox.isSelected()) {
                            Path path = resourcesPath.resolve("ZonesByPlanetType").resolve(zone.getPlanet().name());
                            IOUtils.createDirectories(path);
                            BMPWriter.write(canvas, path.resolve(String.format("%03d", zone.getIndex()) + E_BMP));
                        }

                        if (groupByUnknownCheckBox.isSelected()) {
                            Path path = resourcesPath.resolve("ZonesByIzaxUnk2").resolve(String.valueOf(zone.getIzax().get_unnamed2()));
                            IOUtils.createDirectories(path);
                            BMPWriter.write(canvas, path.resolve(String.format("%03d", zone.getIndex()) + E_BMP));

                            path = resourcesPath.resolve("ZonesByIzx4Unk2").resolve(String.valueOf(zone.getIzx4().get_unnamed2()));
                            IOUtils.createDirectories(path);
                            BMPWriter.write(canvas, path.resolve(String.format("%03d", zone.getIndex()) + E_BMP));

                            path = resourcesPath.resolve("ZonesByIzaxIzx4Unk2").resolve(String.valueOf(zone.getIzax().get_unnamed2()) + zone.getIzx4().get_unnamed2());
                            IOUtils.createDirectories(path);
                            BMPWriter.write(canvas, path.resolve(String.format("%03d", zone.getIndex()) + E_BMP));
                        }
                    }
                }

                if (saveUnusedTilesCheckBox.isSelected()) {
                    Path unusedTilesPath = resourcesPath.resolve("ZonesTilesUnused");
                    IOUtils.createDirectories(unusedTilesPath);
                    for (int i = 0; i < yodesk.getTiles().getTiles().size(); i++) {
                        if (!usedTiles.get(i)) {
                            BMPWriter.write(getTile(i, icm), unusedTilesPath.resolve(String.format("%04d", i) + E_BMP));
                        }
                    }
                }

                if (generateMapsReports.isSelected()) {
                    Path path = dumpsPath.resolve("monsters" + E_XLSX);
                    Log.debug("Generate monsters report: " + path);
                    ExcelUtils.saveMonsters(path);
                    path = dumpsPath.resolve("zones" + E_XLSX);
                    Log.debug("Generate zones report: " + path);
                    ExcelUtils.saveZones(path);
                }

                if (dumpActionsCheckBox.isSelected()) {
                    dumpActionsScripts();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, () -> Log.appendOk("Save zones to files"));
    }

    private void drawBIZone(BufferedImage canvas, Zone zone) {

        ImageUtils.clearBufferedImage(canvas);

        drawBIZoneLayer(canvas, zone, 0);
        drawBIZoneLayer(canvas, zone, 1);
        drawBIZoneLayer(canvas, zone, 2);
    }

    private void drawBIZoneLayer(BufferedImage canvas, Zone zone, int layerId) {

        for (int y = 0; y < zone.getHeight(); y++) {
            for (int x = 0; x < zone.getWidth(); x++) {
                drawBITileOnMap(canvas, zone, x, y, layerId);
            }
        }
    }

    private void drawBITileOnMap(BufferedImage canvas, Zone zone, int x, int y, int layerId) {

        int tileId = zone.getTileIds().get(y * zone.getWidth() + x).getColumn().get(layerId);
        if (tileId < yodesk.getTiles().getTiles().size()) {
            usedTiles.set(tileId, true);
            drawTileOnBufferedImage(tileId, canvas, x * TILE_SIZE, y * TILE_SIZE, true);
        }
    }

    private void dumpActionsScripts() throws IOException {

        StringBuilder sb = new StringBuilder();
        StringBuilder sbn = new StringBuilder();

        List<Zone> zones = yodesk.getZones().getZones();
        for (int zoneId = 0; zoneId < zones.size(); zoneId++) {
            Zone zone = zones.get(zoneId);

            StringBuilder ssb = new StringBuilder();
            StringBuilder ssbn = new StringBuilder();

            for (int i = 0; i < zone.getActions().size(); i++) {
                String action = getActionScript(zone.getActions().get(i), i, false);
                ssb.append(action).append("\n");

                action = getActionScript(zone.getActions().get(i), i, true);
                ssbn.append(action).append("\n");
            }

            sb.append("Zone ").append(zoneId).append("\n").append("\n").append(ssb).append("\n");
            sbn.append("Zone ").append(zoneId).append("\n").append("\n").append(ssbn).append("\n");
        }

        IOUtils.saveTextFile(Collections.singletonList(sb.toString()), dumpsPath.resolve("actionsScripts" + E_TXT));
        IOUtils.saveTextFile(Collections.singletonList(sbn.toString()), dumpsPath.resolve("actionsScriptsNoText" + E_TXT));

        IOUtils.saveTextFile(new StructureDump(yodesk).dumpActionsPhrases(), dumpsPath.resolve("actionsText" + E_MD));
    }

    public void dumpActionsTextToDocxClick() {

        Platform.runLater(() -> {
            try {
                Path path = translatePath.resolve("actions" + E_DOCX);
                Log.debug("Save actions text to file: " + path);
                WordUtils.saveZones(dtaCrc32, path);
                Log.appendOk("Save actions text");
            } catch (Exception e) {
                JavaFxUtils.showAlert("Error saving Actions text to the DOCX file", e);
            }
        });
    }

    public void loadTranslatedActionsTextClick() {

        Platform.runLater(() -> {
            try {
                Path path = translatePath.resolve("actions" + E_DOCX);
                Log.debug("Load actions text from file: " + path);
                WordRecord wordRecord = WordUtils.loadRecords(path);
                validateProps(wordRecord.getProps());
                actionTexts = wordRecord.getStringRecords();
                actionsTextTableView.setItems(FXCollections.observableList(actionTexts));
                Log.appendOk("Load actions text");
            } catch (Exception e) {
                JavaFxUtils.showAlert("Error loading Actions text from file", e);
            }
        });
    }

    private void validateProps(Map<PropertyName, String> props) {

        if (!dtaCrc32.equals(props.get(PropertyName.CRC32))) {
            JavaFxUtils.showAlert("Wrong or absent: " + PropertyName.SRC_CHARSET, "Perhaps this translation is for another version of the game.", Alert.AlertType.WARNING);
        }
        validateCharset(props, PropertyName.SRC_CHARSET);
        if (validateCharset(props, PropertyName.DST_CHARSET) && !destinationCharset.getCode().equals(props.get(PropertyName.DST_CHARSET))) {
            destinationCharset = Config.getCharset(props.get(PropertyName.DST_CHARSET));
            Yodesk.setOutputCharset(destinationCharset.getCode());
            JavaFxUtils.showAlert("Destination charset has changed", "New charset is: " + destinationCharset.getDescription(), Alert.AlertType.INFORMATION);
        }
    }

    private boolean validateCharset(Map<PropertyName, String> props, PropertyName propertyName) {

        String charset = props.get(propertyName);

        if (null == charset) {
            JavaFxUtils.showAlert("Absent property: " + propertyName.getText(), "Please do not remove this data from the translation file.", Alert.AlertType.WARNING);
            return false;
        }

        try {
            Charset.forName(charset);
        } catch (Exception e) {
            JavaFxUtils.showAlert("Wrong property: " + propertyName.getText(), e.getClass().getSimpleName() + ": " + e.getMessage(), Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    Pattern actionIdPattern = Pattern.compile("^[0-9]{1,4}\\.[0-9]{1,3}\\.[ic][0-9]{1,3}$");

    public void replaceActionTextClick() {

        Platform.runLater(() -> {
            try {
                Log.debug("Replacing actions text...");
                actionTexts.forEach(a -> a.setTranslation(a.getTranslation()
                                .replace("\r\n", "\n")
                                .replace("\n", "\r\n")
                                .replace("[CR2]", "[CR][CR]")
                                .replace("[CR]", "\r\n")
                                .replace("[CR]", "\r\n")
                                .replace("¥", var2(Yodesk.getOutputCharset()))
                                .replace("¢", var1(Yodesk.getOutputCharset()))
                                .replace("…", "...")
                        )
                );

                if (trimActionsTrailSpacesCheckBox.isSelected()) {
                    actionTexts.forEach(a -> a.setTranslation(a.getTranslation().trim()));
                }

                if (isBadTranslation(actionTexts, actionIdPattern, WordHelper.getActionsTexts(), strictActionsReplacingRulesCheckBox.isSelected())) {
                    return;
                }

                actionTexts.forEach(s -> getActionTextContainer(s.getId()).setText(s.getTranslation()));

                changesCount++;

                Log.appendOk("Replace actions text");
                JavaFxUtils.showAlert("The text has been successfully replaced", "No errors were found during the replacement ", Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                JavaFxUtils.showAlert("Error replacing Actions text", e);
            }
        });
    }

    private String var1(String charset) {
        return new String(new byte[]{(byte) 0xA2}, Charset.forName(charset));
    }

    private String var2(String charset) {
        return new String(new byte[]{(byte) 0xA5}, Charset.forName(charset));
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
                .allMatch(r -> r.getOriginal().trim().equals(tr.getOriginal().trim()) && r.getId().trim().equals(tr.getId().trim())))) {
            strings.add("Original text does not match IDs");
        }

        String sourceVar1 = var1(Yodesk.getInputCharset());
        String sourceVar2 = var2(Yodesk.getInputCharset());
        String destinationVar1 = var1(Yodesk.getOutputCharset());
        String destinationVar2 = var2(Yodesk.getOutputCharset());

        translatedRecords.forEach(r -> {
            if (StringUtils.countMatches(r.getOriginal(), sourceVar1) != StringUtils.countMatches(r.getTranslation(), destinationVar1)) {
                strings.add(String.format("Missing variable(s) %s: %s", sourceVar1, r.getId()));
            }
            if (StringUtils.countMatches(r.getOriginal(), sourceVar2) != StringUtils.countMatches(r.getTranslation(), destinationVar2)) {
                strings.add(String.format("Missing variable(s) %s: %s", sourceVar2, r.getId()));
            }
        });

        if (!strings.isEmpty()) {

            List<String> strings2 = strings;

            int size = strings2.size();

            if (size > 40) {
                strings2 = strings.subList(0, 40);
                strings2.add("... and " + (size - 40) + " other exceptions...");
            }

            JavaFxUtils.showAlert(strict ? "The text has not been replaced" : "Replacement of text was done with remarks",
                    "During the check, the following errors were identified:",
                    String.join("\n", strings2), strict ? Alert.AlertType.ERROR : Alert.AlertType.WARNING
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

    public void dumpPuzzlesTextToDocxClick() {

        runInBackground(() -> {
            try {
                IOUtils.createDirectories(translatePath);
                IOUtils.createDirectories(dumpsPath);
                Path path = translatePath.resolve("puzzles" + E_DOCX);
                Log.debug("Save puzzles text to file: " + path);
                Log.debug("Total count: " + yodesk.getPuzzles().getPuzzles().size());

                if (!disableNonTranslationMenuItem.isSelected()) {
                    IOUtils.saveTextFile(new StructureDump(yodesk).dumpPuzzlesPhrases(), dumpsPath.resolve("puzzlesText" + E_MD));
                }

                WordUtils.savePuzzles(dtaCrc32, path);
                Log.appendOk("Save puzzles text to file");

            } catch (Exception e) {
                JavaFxUtils.showAlert("Error saving puzzles to the files", e);
            }
        });
    }

    public void loadTranslatedPuzzlesTextClick() {

        Platform.runLater(() -> {
            try {
                Path path = translatePath.resolve("puzzles" + E_DOCX);
                Log.debug("Load puzzles text from file: " + path);
                WordRecord wordRecord = WordUtils.loadRecords(path);
                validateProps(wordRecord.getProps());
                puzzlesTexts = wordRecord.getStringRecords().stream().map(s -> new StringImagesRecord(s.getId(), Collections.emptyList(), s.getOriginal(), s.getTranslation())).collect(Collectors.toList());
                puzzlesTextTableView.setItems(FXCollections.observableList(puzzlesTexts));
                Log.appendOk("Load puzzles text");
            } catch (Exception e) {
                JavaFxUtils.showAlert("Error loading Puzzles text from file", e);
            }
        });
    }

    Pattern puzzleIdPattern = Pattern.compile(String.format("^[0-9]{1,4}\\.(%s|%s|%s|%s|%s)$",
            StringMeaning.REQUEST, StringMeaning.THANK, StringMeaning.OFFER, StringMeaning.MISSION, StringMeaning.UNUSED));

    public void replacePuzzlesTextClick() {

        Platform.runLater(() -> {
            try {
                Log.debug("Replacing puzzles text...");
                puzzlesTexts.forEach(t -> t.setTranslation(t.getTranslation()
                                .replace("\r\n", "\n")
                                .replace("\n", "\r\n")
                                .replace("¥", var2(Yodesk.getOutputCharset()))
                                .replace("¢", var1(Yodesk.getOutputCharset()))
                                .replace("…", "...")
                        )
                );

                if (trimPuzzlesTrailSpacesCheckBox.isSelected()) {
                    puzzlesTexts.forEach(t -> t.setTranslation(t.getTranslation().trim()));
                }

                if (isBadTranslation(puzzlesTexts, puzzleIdPattern, WordHelper.getPuzzlesTexts(), strictPuzzlesReplacingRulesCheckBox.isSelected())) {
                    return;
                }

                puzzlesTexts.forEach(s -> getPuzzlesPrefixedStr(s.getId()).setContent(s.getTranslation()));

                changesCount++;

                Log.appendOk("Replace puzzles text");
                JavaFxUtils.showAlert("The text has been successfully replaced", "No errors were found during the replacement ", Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                JavaFxUtils.showAlert("Error replacing Puzzles text", e);
            }
        });
    }

    private PrefixedStr getPuzzlesPrefixedStr(String id) {

        String[] chunks = id.trim().split("\\.");
        int puzzleId = Integer.parseInt(chunks[0]);
        int stringId = StringMeaning.valueOf(chunks[1]).getId();

        return yodesk.getPuzzles().getPuzzles().get(puzzleId).getPrefixesStrings().get(stringId);
    }

    public void saveCharactersToFilesButtonClick() {

        runInBackground(() -> {
            try {
                Path path = resourcesPath.resolve("Characters");
                IOUtils.createDirectories(path);
                IOUtils.createDirectories(dumpsPath);
                Log.debug("Saving characters...");
                Log.debug("Total count: " + yodesk.getCharacters().getCharacters().size());

                for (int i = 0; i < yodesk.getCharacters().getCharacters().size(); i++) {
                    Character character = yodesk.getCharacters().getCharacters().get(i);
                    for (Integer integer : character.getTileIds()) {
                        Path charPath = path.resolve(character.getName());
                        IOUtils.createDirectories(charPath);
                        BMPWriter.write(getTile(integer), charPath.resolve(String.format("%04d", integer) + E_BMP));
                    }
                }

                path = dumpsPath.resolve("characters" + E_DOCX);
                Log.debug("Saving characters text to file: " + path);
                WordUtils.saveCharacters("Characters", dtaCrc32, path);

                IOUtils.saveTextFile(yodesk.getCharacters().getFilteredCharacters().stream().map(Character::getName).collect(Collectors.toList()), dumpsPath.resolve("characters" + E_TXT));
                Log.appendOk("Save characters");

            } catch (Exception e) {
                JavaFxUtils.showAlert("Error saving characters to the files", e);
            }
        });
    }

    public void generateCharactersReportButtonClick() {

        runInBackground(() -> {
            try {
                Path path = dumpsPath.resolve("characters" + E_XLSX);
                Log.debug("Generate characters report: " + path);
                ExcelUtils.saveCharacters(path);
                Log.appendOk("Generate characters report");
            } catch (Exception e) {
                JavaFxUtils.showAlert("Error saving characters to the files", e);
            }
        });
    }

    public void saveNamesToFileButtonClick() {

        runInBackground(() -> {
            try {
                Log.debug("Saving tile names...");
                Log.debug("Total count: " + yodesk.getTileNames().getNames().size());

                IOUtils.createDirectories(dumpsPath);
                IOUtils.createDirectories(resourcesPath.resolve("TileNames"));
                for (TileName n : yodesk.getTileNames().getNames()) {
                    if (null != n.getName()) {
                        BMPWriter.write(getTile(n.getTileId(), icm), IOUtils.findUnusedFileName(resourcesPath.resolve("TileNames"), n.getName(), E_BMP));
                    }
                }
                IOUtils.saveTextFile(yodesk.getTileNames().getFilteredNames().stream().map(TileName::getName).filter(Objects::nonNull)
                        .collect(Collectors.toList()), dumpsPath.resolve("tilenames" + E_TXT));
                Log.appendOk("Save tile names");
            } catch (Exception e) {
                JavaFxUtils.showAlert("Error saving TileNames to the files", e);
            }
        });
    }

    public void saveNamesTextToDocxCLick() {

        Platform.runLater(() -> {
            try {
                IOUtils.createDirectories(translatePath);
                Path path = translatePath.resolve("tilenames" + E_DOCX);
                Log.debug("Saving tile names to file: " + path);
                WordUtils.saveNames(dtaCrc32, path);
                Log.appendOk("Save tile names text");
            } catch (Exception e) {
                JavaFxUtils.showAlert("Error saving TileNames text to the DOCX file", e);
            }
        });
    }

    public void loadTranslatedNamesTextClick() {

        Platform.runLater(() -> {
            try {
                Path path = translatePath.resolve("tilenames" + E_DOCX);
                Log.debug("Loading tile names from file: " + path);
                WordRecord wordRecord = WordUtils.loadNames(path);
                validateProps(wordRecord.getProps());
                namesTexts = wordRecord.getStringRecords().stream().map(s -> new StringImagesRecord(s.getId(), Collections.emptyList(), s.getOriginal(), s.getTranslation())).collect(Collectors.toList());
                namesTextTableView.setItems(FXCollections.observableList(namesTexts));

                double maxWidth = 0;

                for (StringImagesRecord n : namesTexts) {
                    final Text text = new Text(n.getTranslation());
                    Font font = Font.font("Microsoft Sans Serif", 12.7); // Why not 10???
                    text.setFont(font);
                    maxWidth = Math.max(maxWidth, text.getLayoutBounds().getWidth());
                }

                //maxWidth = maxWidth * 179 / 130;
                Log.appendOk("Load tile names");
                if (maxWidth > 141) {
                    JavaFxUtils.showAlert("Too long tile name(s)", "Please consider increasing the window width by at least " + (int) Math.ceil(maxWidth - 141) + "px", Alert.AlertType.INFORMATION);
                }
            } catch (Exception e) {
                JavaFxUtils.showAlert("Error loading TileNames text from file", e);
            }
        });
    }

    Pattern nameIdPattern = Pattern.compile("^[0-9]{1,4}$");

    public void replaceNamesTextClick() {

        Platform.runLater(() -> {
            try {
                Log.debug("Replacing tile names text...");
                if (trimNamesSpacesCheckBox.isSelected()) {
                    namesTexts.forEach(a -> a.setTranslation(a.getTranslation().trim()));
                }

                if (isBadTranslation(namesTexts, nameIdPattern, WordHelper.getNamesTexts(), strictNamesReplacingRulesCheckBox.isSelected())) {
                    return;
                }

                List<TileName> filteredNames = yodesk.getTileNames().getFilteredNames();

                namesTexts.forEach(n -> filteredNames.get(Integer.parseInt(n.getId())).setName(n.getTranslation()));

                changesCount++;

                Log.appendOk("Replace tile names");
                JavaFxUtils.showAlert("The text has been successfully replaced", "No errors were found during the replacement ", Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                JavaFxUtils.showAlert("Error replacing Tile Names text", e);
            }
        });
    }

    private WritableImage drawTileOnImage(int tileId) {

        int position = yodesk.getTiles().getTilePixelsPosition(tileId);
        return ImageUtils.readWPicture(yodesk.getTiles().getRawTiles(), position, TILE_SIZE, TILE_SIZE, transparentColor);
    }

    private void drawTileOnBufferedImage(int tileId, BufferedImage bi, int xOffset, int yOffset, boolean transparent) {

        int position = yodesk.getTiles().getTilePixelsPosition(tileId);
        ImageUtils.drawOnBufferedImage(yodesk.getTiles().getRawTiles(), position, bi, xOffset, yOffset, transparent);
    }

    private void drawTileOnCanvas(int tileId, Canvas canvas, int xOffset, int yOffset, Color transparentColor) {

        int position = yodesk.getTiles().getTilePixelsPosition(tileId);
        ImageUtils.drawOnCanvas(yodesk.getTiles().getRawTiles(), position, canvas, xOffset, yOffset, transparentColor);
    }

    private void drawBorderOnCanvas(Canvas canvas, int xOffset, int yOffset, Color borderColor) {
        ImageUtils.drawBorderOnCanvas(canvas, xOffset, yOffset, borderColor);
    }

    private void fillZone(Canvas canvas, Color color) {
        ImageUtils.fillCanvas(canvas, color);
    }

    private void fillZoneTile(Canvas canvas, int xOffset, int yOffset, Color color) {
        ImageUtils.fillCanvas(canvas, xOffset, yOffset, color);
    }

    private static int getIndex(Map<Integer, Long> map) {

        Map.Entry<Integer, Long> entry = map.entrySet().iterator().next();
        return entry.getKey();
    }

    private static long getValue(Map<Integer, Long> map) {

        Map.Entry<Integer, Long> entry = map.entrySet().iterator().next();
        return entry.getValue();
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

    public void changeSourceCharsetButtonClick() {

        Platform.runLater(() -> {
            try {
                ChoiceDialog<Encoding> dialog = new ChoiceDialog<>(sourceCharset, charsets);
                dialog.setTitle("Choose charset");
                dialog.setHeaderText("Please, select new charset");
                dialog.setContentText("Source charset:");

                Optional<Encoding> result = dialog.showAndWait();
                if (result.isPresent()) {
                    sourceCharset = result.get();
                    Log.debug("Change source charset to: " + sourceCharset.getDescription());
                    yodesk = Yodesk.fromFile(dtaFile.toString(), sourceCharset.getCode());
                    updateUI();
                }
            } catch (Exception e) {
                JavaFxUtils.showAlert("Error of changing the source charset", e);
            }
        });
    }

    public void changeDestinationCharsetButtonClick() {

        Platform.runLater(() -> {
            try {
                ChoiceDialog<Encoding> dialog = new ChoiceDialog<>(destinationCharset, charsets);
                dialog.setTitle("Choose charset");
                dialog.setHeaderText("Please, select new charset");
                dialog.setContentText("Destination charset:");

                dialog.showAndWait().ifPresent(e -> {
                    destinationCharset = e;
                    Log.debug("Change destination charset to: " + destinationCharset.getDescription());
                    Yodesk.setOutputCharset(e.getCode());
                    updateDestinationCharset();
                });
            } catch (Exception e) {
                JavaFxUtils.showAlert("Error of changing the destination charset", e);
            }
        });
    }

    public void disableNonTranslationMenuItemClick() {
        disableNonTranslationFeatures(disableNonTranslationMenuItem.isSelected());
    }

    public static class Log {

        private static final List<String> lines = new ArrayList<>();
        private static TextArea textArea;

        public static void setOutput(TextArea textArea) {
            Log.textArea = textArea;
            textArea.setText(String.join("\n", lines));
            textArea.appendText("");
            lines.clear();
        }

        public static void error(String message) {
            appendText("ERROR: " + message);
        }

        public static void debug(String message) {
            appendText("DEBUG: " + message);
        }

        public static void message(String message) {
            appendText("MESSAGE: " + message);
        }

        public static void appendOk(String text) {
            appendText(text + ": OK");
        }

        private static void appendText(String text) {

            if (textArea == null) {
                lines.add(text);
            } else {
                textArea.appendText(text + "\n");
            }
        }

        public static void clear() {

            if (textArea == null) {
                lines.clear();
            } else {
                textArea.clear();
            }
        }
    }
}
