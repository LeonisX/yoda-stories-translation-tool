package md.leonis.ystt.view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import md.leonis.bin.ByteOrder;
import md.leonis.bin.Dump;
import md.leonis.config.Config;
import md.leonis.ystt.model.*;
import md.leonis.ystt.model.yodesk.CatalogEntry;
import md.leonis.ystt.model.yodesk.Yodesk;
import md.leonis.ystt.model.yodesk.characters.Character;
import md.leonis.ystt.model.yodesk.characters.CharacterAuxiliary;
import md.leonis.ystt.model.yodesk.characters.CharacterWeapon;
import md.leonis.ystt.model.yodesk.puzzles.Puzzle;
import md.leonis.ystt.model.yodesk.tiles.TileName;
import md.leonis.ystt.model.yodesk.zones.Action;
import md.leonis.ystt.model.yodesk.zones.Condition;
import md.leonis.ystt.model.yodesk.zones.Instruction;
import md.leonis.ystt.model.yodesk.zones.Zone;
import md.leonis.ystt.utils.*;
import net.sf.image4j.codec.bmp.BMPImage;
import net.sf.image4j.codec.bmp.BMPReader;
import net.sf.image4j.codec.bmp.BMPWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static md.leonis.config.Config.*;
import static md.leonis.ystt.utils.ImageUtils.readWPicture;

public class MainPaneController {

    public MenuItem openMenuItem;
    public MenuItem saveMenuItem;
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
    public Label sizeLabel;
    public Label crc32Label;
    public Label nameLabel;
    public Label charsetLabel;
    public TableView<CatalogEntry> commonInformationTableView;
    public Button dumpAllSectionsButton;

    public Button dumpPETextToDocx;
    public Button loadPETranslatedText;
    public Button replacePETextInDta;
    public CheckBox trimPESpacesCheckBox;

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
    public ImageView clipboardImageView;
    public Rectangle clipboardRectangle;
    public Button loadClipboardImage;
    public Button saveClipboardImage;
    public Button clearClipboardImage;
    public ContextMenu tilesContextMenu;
    public ToggleGroup tilesToggleGroup;
    private final Map<String, RadioMenuItem> tileFlagsMap = new HashMap<>();

    private Node currentTile;

    public Label mapsCountLabel;
    public Button saveMapsToFilesButton;
    public CheckBox normalSaveCheckBox;
    public CheckBox groupByFlagsCheckBox;
    public CheckBox groupByPlanetTypeCheckBox;
    public CheckBox dumpActionsCheckBox;
    public CheckBox dumpTextCheckBox;
    public CheckBox saveUnusedTilesCheckBox;
    public ListView<String> mapsListView;
    public Canvas mapCanvas;
    public TableView<Zone> mapsTableView;

    public RadioButton topRadioButton;
    public RadioButton middleRadioButton;
    public RadioButton bottomRadioButton;
    public ToggleGroup mapLayerToggleGroup;
    public ListView<String> mapEditorListView;
    public Canvas mapEditorCanvas;
    public FlowPane mapEditorTilesFlowPane;
    public Button undoMapEdit;

    public Button dumpTextToDocx;
    public Button loadTranslatedText;
    public Button replaceTextInDta;
    public CheckBox trimSpacesCheckBox;


    public Label puzzlesCountLabel;
    public Button savePuzzlesToFilesButton;
    public Button dumpPuzzlesTextToDocx;
    public Button loadPuzzlesTranslatedText;
    public Button replacePuzzlesTextInDta;
    public CheckBox trimPuzzlesSpacesCheckBox;

    public Label charactersCountLabel;
    public Button saveCharactersToFilesButton;
    public TableView<Character> charactesTableView;

    public Label namesCountLabel;
    public Button saveNamesToFilesButton;
    public TableView<TileName> namesTableView;
    public Button dumpNamesTextToDocx;
    public Button loadNamesTranslatedText;
    public Button replaceNamesTextInDta;
    public CheckBox trimNamesSpacesCheckBox;

    public TextArea logsTextArea;
    public TextArea hexViewerTextArea;

    public Label statusLabel;

    private static final String OUTPUT = "output";
    private static final String E_BMP = ".bmp";
    private static final String E_DOCX = ".docx";
    private static final int TILE_SIZE = 32;

    Path spath, opath;
    int selectedCell, selectedTileX, selectedTileY;


    //TODO need to clear after DTA load
    public boolean[] usedTiles;

    int prevTile = -1;
    long prevTileAddress;

    private File clipboardFile;

    public static Dump dtaDump;
    public static Dump exeDump;
    public static String exeCrc32;
    public static String dtaCrc32;


    public MainPaneController() {

        spath = Paths.get(".");

        /*var k: Word;

        ZeroColorRGDo(false);
        OpenDTADialog.InitialDir := '.\';
        texts := TStringList.Create;
        log.SetOutput(LogMemo.lines);

        // insert text
        ts := TStringList.Create;
        ts2 := TStringList.Create;

        StringGrid1.Cells[0,0]:='#';
        StringGrid1.Cells[1,0]:='Original';
        StringGrid1.Cells[2,0]:='Translated';
        k:=(StringGrid1.Width - 96) div 2 - StringGrid1.GridLineWidth * StringGrid1.ColCount - 8;
        StringGrid1.ColWidths[1]:=k;
        StringGrid1.ColWidths[2]:=k;
        StringGrid4.Cells[0,0]:='#';
        StringGrid4.Cells[1,0]:='Original';
        StringGrid4.Cells[2,0]:='Translated';
        StringGrid4.ColWidths[1]:=k;
        StringGrid4.ColWidths[2]:=k;
        StringGrid2.Cells[0,0]:='#';
        StringGrid2.Cells[1,0]:='Original';
        StringGrid2.Cells[2,0]:='Translated';
        StringGrid2.ColWidths[1]:=k;
        StringGrid2.ColWidths[2]:=k;

        Opendialog1.InitialDir := ExtractFilePath(ParamStr(0))+'\';*/
    }

    @FXML
    void initialize() {

        usedTiles = new boolean[yodesk.getTiles().getTiles().size()];

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
        sizeLabel.setText(dtaDump.size() + " / " + exeDump.size());
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
        tilesCountLabel.setText(Integer.toString(yodesk.getTiles().getTiles().size()));
        drawTiles();
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

        // Puzzles
        puzzlesCountLabel.setText(Integer.toString(yodesk.getPuzzles().getPuzzles().size()));

        // Characters
        charactersCountLabel.setText(Integer.toString(yodesk.getCharacters().getCharacters().size()));
        @SuppressWarnings("all")
        TableColumn<Character, List<Integer>> charsColumn = (TableColumn<Character, List<Integer>>) charactesTableView.getColumns().get(1);
        charsColumn.setCellFactory(c -> {

            Canvas canvas = new Canvas();

            TableCell<Character, List<Integer>> cell = new TableCell<Character, List<Integer>>() {
                public void updateItem(List<Integer> tileIds, boolean empty) {
                    if (tileIds != null) {
                        canvas.setWidth(tileIds.size() * TILE_SIZE);
                        canvas.setHeight(TILE_SIZE);
                        for (int i = 0; i < tileIds.size(); i++) {
                            GetWTile(tileIds.get(i), canvas, i * TILE_SIZE, 0, null);
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
        charactesTableView.setItems(FXCollections.observableList(yodesk.getCharacters().getFilteredCharacters()));

        // Names
        namesCountLabel.setText(Integer.toString(yodesk.getTileNames().getNames().size()));
        @SuppressWarnings("all")
        TableColumn<TileName, Integer> namesColumn = (TableColumn<TileName, Integer>) namesTableView.getColumns().get(0);
        namesColumn.setCellFactory(c -> {

            final ImageView imageView = new ImageView();

            TableCell<TileName, Integer> cell = new TableCell<TileName, Integer>() {
                public void updateItem(Integer tileId, boolean empty) {
                    if (tileId != null && !tileId.equals(0xFFFF)) {
                        imageView.setImage(GetWTile(tileId));
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
    }

    private void drawTitleImage() {

        try {
            WritableImage image = readWPicture(yodesk.getStartupImage().getPixels(), 0, 288, 288, Color.BLACK);
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
            for (int i = 0; i < yodesk.getTiles().getTiles().size(); i++) {
                ImageView image = new ImageView(GetWTile(i));
                image.setUserData(i);
                image.setOnMouseEntered(mouseEnteredHandler);
                image.setOnMouseExited(mouseExitedHandler);
                image.setOnContextMenuRequested(e -> tilesContextMenu.show((Node) e.getSource(), e.getScreenX(), e.getScreenY()));
                tilesFlowPane.getChildren().add(image);
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("Tiles display error", e);
        }
    }

    private void drawMapEditorTiles() {

        try {
            for (int i = 0; i < yodesk.getTiles().getTiles().size(); i++) {
                ImageView image = new ImageView(GetWTile(i));
                image.setUserData(i);
                image.setOnMouseEntered(mouseEnteredHandler);
                image.setOnMouseExited(mouseExitedHandler);
                image.setOnContextMenuRequested(e -> tilesContextMenu.show((Node) e.getSource(), e.getScreenX(), e.getScreenY()));
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
                drawViewMap(mapsListView.getSelectionModel().getSelectedIndex());
            }
        }
    };

    private void drawViewMap(int id) {
        try {
            //ReadIZON(id, false);
            ReadMap(mapCanvas, id, true, normalSaveCheckBox.isSelected() || groupByFlagsCheckBox.isSelected() || groupByPlanetTypeCheckBox.isSelected());
        } catch (Exception e) {
            JavaFxUtils.showAlert(String.format("Map %s display error", id), e);
        }
    }

    ChangeListener<String> mapsEditorListViewChangeListener = new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (mapEditorListView.getSelectionModel().getSelectedIndex() >= 0) {
                drawEditorMap(mapEditorListView.getSelectionModel().getSelectedIndex());
                //TODO move map editor tiles, undo button
            }
        }
    };

    private void drawEditorMap(int id) {
        try {
            //ReadIZON(id, false);
            ReadMap(mapEditorCanvas, id, true, normalSaveCheckBox.isSelected() || groupByFlagsCheckBox.isSelected() || groupByPlanetTypeCheckBox.isSelected());
        } catch (Exception e) {
            JavaFxUtils.showAlert(String.format("Map %s display error", id), e);
        }
    }

    //TODO
    public void undoMapEditClick(ActionEvent actionEvent) {
    }

    //TODO refactor this
    private void ReadMap(Canvas canvas, int zoneId, boolean show, boolean save) throws IOException {

        Zone zone = yodesk.getZones().getZones().get(zoneId);

        //TODO offset if need
        Log.debug("Map #" + zoneId + " offset: " + "section.intToHex(section.GetPosition(), 8)");
        statusLabel.setText("Map: " + zoneId + " (" + zone.getPlanet().name() + ")");

        if (show) {
            canvas.setWidth(zone.getWidth() * TILE_SIZE);
            canvas.setHeight(zone.getHeight() * TILE_SIZE);

            canvas.getGraphicsContext2D().setFill(Color.rgb(7, 11, 0));
            canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            for (int y = 0; y < zone.getHeight(); y++) {
                for (int x = 0; x < zone.getWidth(); x++) {
                    //W*H*6 bytes: map data
                    drawTileOnMap(canvas, x, y, zone, 0);
                    drawTileOnMap(canvas, x, y, zone, 1);
                    drawTileOnMap(canvas, x, y, zone, 2);
                }
            }
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

    private void drawTileOnMap(Canvas canvas, int x, int y, Zone zone, int layer) {

        int tileId = zone.getTileIds().get(y * zone.getWidth() + x).getColumn().get(layer);
        if (tileId < yodesk.getTiles().getTiles().size()) {
            usedTiles[tileId] = true;
            GetWTile(tileId, canvas, x * TILE_SIZE, y * TILE_SIZE, null);
        }
    }

    private void ReadIZON(int id, boolean save) throws IOException {

        ReadMap(mapCanvas, id, normalSaveCheckBox.isSelected() || groupByFlagsCheckBox.isSelected() || groupByPlanetTypeCheckBox.isSelected(), save);

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
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Executable File");
            //TODO remove
            fileChooser.setInitialDirectory(new File("D:\\Working\\_Yoda\\YExplorer\\other\\Yoda Stories (14.02.1997)"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Executable Files", "*.exe"));
            File selectedFile = fileChooser.showOpenDialog(JavaFxUtils.getStage());

            if (selectedFile != null) {
                loadFileToArray(selectedFile);
                yodesk = Yodesk.fromFile(IOUtils.getDtaPath(selectedFile.toPath().getParent()).toString(), release.getCharset());

                JavaFxUtils.showMainPanel();
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("EXE file loading error", e);
        }
    }

    public void saveMenuItemClick() {
        //TODO
        /*
    procedure TMainForm.Button6Click(Sender: TObject);
    begin
  if SaveDTADialog.Execute then DTA.SaveToFile(SaveDTADialog.FileName);
    end;
        */
        /*// Save
        procedure TMainForm.Save1Click(Sender: TObject);
        begin
        Hex.LoadFromStream(DTA.data);
        Hex.Save;
        ShowMessage('OK');
        end;

// Save...
        procedure TMainForm.Save2Click(Sender: TObject);
        begin
        if SaveDTADialog.Execute then
        begin
        Hex.LoadFromStream(DTA.data);
        Hex.SaveToFile(SaveDTADialog.FileName, true);
        end;
        end;*/
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
        for (int i = 0; i < children.size(); i++) {
            ImageView imageView = (ImageView) children.get(i);
            imageView.setImage(GetWTile(i));
        }
        children = mapEditorTilesFlowPane.getChildren();
        for (int i = 0; i < children.size(); i++) {
            ImageView imageView = (ImageView) children.get(i);
            imageView.setImage(GetWTile(i));
        }
        //TODO redraw clipboard,  other images//TODO redraw clipboard,  other images
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

    //TODO
    public void dumpPETextToDocxCLick(ActionEvent actionEvent) {
    }

    //TODO
    public void loadPETranslatedTextClick(ActionEvent actionEvent) {
    }

    //TODO
    public void replacePETextInDtaClick() {
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
                    BMPWriter.write(GetTile(i, icm), path.toFile());

                    //TODO may be use PNG. Sample code
                    ImageIO.write(GetTile(i, icm), "PNG", tilesPath.resolve(StringUtils.leftPad(Integer.toString(i), 4, "0") + ".png").toFile());
                }

                if (groupByAttributesFilenamesCheckBox.isSelected()) {
                    String binaryAttr = StringUtils.right(StringUtils.leftPad(attrHex, 32, '0'), 32);
                    Path path = attrPath.resolve(binaryAttr + " (" + attrHex + ")");
                    IOUtils.createDirectories(path);
                    BMPWriter.write(GetTile(i, icm), path.resolve(StringUtils.leftPad(Integer.toString(i), 4, "0") + E_BMP).toFile());
                }

                if (hexFilenamesCheckBox.isSelected()) {
                    BMPWriter.write(GetTile(i, icm), hexPath.resolve(intToHex(i, 4) + E_BMP).toFile());
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
                        GetWTile(k, canvas, x * TILE_SIZE, y * TILE_SIZE, Config.transparentColor);
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

    public void clipboardImageViewMouseEntered(MouseEvent mouseEvent) {
        moveClipboardRectangle(mouseEvent.getX(), mouseEvent.getY());
        clipboardRectangle.setVisible(true);
    }

    public void clipboardImageViewMouseMoved(MouseEvent mouseEvent) {
        moveClipboardRectangle(mouseEvent.getX(), mouseEvent.getY());
    }

    private void moveClipboardRectangle(double mx, double my) {
        double x = clipboardImageView.getLayoutX() + (((int) mx) & 0xFFFFFFE0);
        double y = clipboardImageView.getLayoutY() + (((int) my) & 0xFFFFFFE0);
        if (x != clipboardRectangle.getLayoutX() || y != clipboardRectangle.getLayoutY()) {
            clipboardRectangle.setLayoutX(x);
            clipboardRectangle.setLayoutY(y);
        }
    }

    public void clipboardImageViewMouseExited() {
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
                Image image = BMPReader.readWI(file);
                clipboardImageView.setImage(image);
                clipboardImageView.setFitHeight(image.getHeight());
                clipboardImageView.setFitWidth(image.getWidth());
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("Clipboard image loading error", e);
        }
    }

    public void saveClipboardImageClick() {
        try {
            if (null != clipboardImageView.getImage()) {
                String initialFile = (null == clipboardFile) ? "clipboard.bmp" : clipboardFile.getName();
                String initialDir = (null == clipboardFile) ? null : clipboardFile.getParent();
                File file = JavaFxUtils.showBMPSaveDialog("Save Clipboard image", initialDir, initialFile);
                if (file != null) {
                    BMPWriter.write8bit(clipboardImageView, file);
                }
            }
        } catch (Exception e) {
            JavaFxUtils.showAlert("Clipboard image saving error", e);
        }
    }

    public void clearClipboardImageClick() {
        clipboardImageView.setImage(null);
    }

    public void saveMapsToFilesButtonClick() throws IOException, InvalidFormatException {

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
                if (!usedTiles[i]) {
                    Log.debug(i);
                    BMPWriter.write8bit(GetTile(i), unusedTilesPath.resolve(StringUtils.leftPad(Integer.toString(i), 4, '0') + E_BMP));
                }
            }
        }

        if (dumpTextCheckBox.isSelected()) {
            List<String> phrases = yodesk.getZones().getZones().stream().flatMap(z -> {
                List<String> conditions = z.getActions().stream().flatMap(a -> a.getConditions().stream().map(Condition::getText)).collect(Collectors.toList());
                List<String> instructions = z.getActions().stream().flatMap(a -> a.getInstructions().stream().map(Instruction::getText)).collect(Collectors.toList());
                conditions.addAll(instructions);
                return conditions.stream().filter(StringUtils::isNotBlank).map(s -> s.replace("\r\n", "[CR]").replace("[CR][CR]", "[CR2]"));
            }).collect(Collectors.toList());
            IOUtils.saveTextFile(phrases, opath.resolve("iact.txt"));


            // TODO group by all zone auxiliary

            List<PuzzleRecord> zoneRecords = new ArrayList<>();

            //TODO filtered?
            yodesk.getZones().getZones().forEach(zone -> {
                List<PuzzleRecord> zr = new ArrayList<>();

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
                            zr.add(new PuzzleRecord(zone.getIndex() + "." + ax + ".c" + cx, Collections.emptyList(), "", c.getText()));
                        }
                    }
                    for (int ix = 0; ix < a.getInstructions().size(); ix++) {
                        Instruction i = a.getInstructions().get(ix);
                        if (!i.getText().isEmpty()) {
                            zr.add(new PuzzleRecord(zone.getIndex() + "." + ax + ".i" + ix, Collections.emptyList(), "", i.getText()));
                        }
                    }
                }

                /*if (zr.size() == 0) {
                    zr.add(new PuzzleRecord(Integer.toString(zone.getIndex()), Collections.emptyList(), "", ""));
                }
                zr.get(0).setType(zone.getPlanet().name());*/

                zoneRecords.addAll(zr);
            });
            WordUtils.saveZones(zoneRecords, opath.resolve("iact2" + E_DOCX));
        }
        //TODO uncomment, if need
        //ViewMap(MapsListStringGrid.Row);
    }


    //TODO need to test signed
    private String IntToBin(long value) {
        return Long.toBinaryString(value);
    }

    public void dumpTextToDocxCLick(ActionEvent actionEvent) {

    }

    public void loadTranslatedTextClick(ActionEvent actionEvent) {
    }

    public void replaceTextInDtaClick(ActionEvent actionEvent) {
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

            List<PuzzleRecord> puzzleRecords = new ArrayList<>();


            Map<Long, List<String>> byType = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(p -> p.getType() != null).collect(Collectors.groupingBy(Puzzle::getType, Collectors.mapping(p -> Long.toHexString(p.getIndex()), Collectors.toList())));

            Map<Long, List<String>> byUnnamed4 = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(p -> p.get_unnamed4() != null).collect(Collectors.groupingBy(Puzzle::get_unnamed4, Collectors.mapping(p -> Long.toHexString(p.getIndex()), Collectors.toList())));

            Map<Long, List<String>> byUnnamed5 = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(p -> p.get_unnamed5() != null && p.getItem2() != 0).collect(Collectors.groupingBy(Puzzle::get_unnamed5, Collectors.mapping(p -> Long.toHexString(p.getIndex()), Collectors.toList())));

            Map<Integer, List<String>> byUnnamed6 = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(p -> p.get_unnamed6() != null).collect(Collectors.groupingBy(Puzzle::get_unnamed6, Collectors.mapping(p -> Integer.toHexString(p.getIndex()), Collectors.toList())));

            //TODO dump groups
            System.out.println(byType);
            System.out.println(byUnnamed4); // Item1 type
            System.out.println(byUnnamed5); // Item2 type
            System.out.println(byUnnamed6); // Flag??

            List<Integer> items1 = yodesk.getPuzzles().getFilteredPuzzles().stream().map(Puzzle::getItem1).distinct().filter(i -> i <= yodesk.getTiles().getTiles().size()).sorted().collect(Collectors.toList());
            List<Integer> items2 = yodesk.getPuzzles().getFilteredPuzzles().stream().map(Puzzle::getItem2).distinct().filter(i -> i <= yodesk.getTiles().getTiles().size()).sorted().collect(Collectors.toList());

            List<Puzzle> puzzles1 = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(i -> i.getItem1() > yodesk.getTiles().getTiles().size()).sorted(Comparator.comparing(Puzzle::getItem1)).collect(Collectors.toList());
            List<Puzzle> puzzles2 = yodesk.getPuzzles().getFilteredPuzzles().stream().filter(i -> i.getItem2() > yodesk.getTiles().getTiles().size()).sorted(Comparator.comparing(Puzzle::getItem2)).collect(Collectors.toList());


            System.out.println(items1);
            System.out.println(items2);

            System.out.println(puzzles1);
            System.out.println(puzzles2);

            yodesk.getPuzzles().getFilteredPuzzles().forEach(p -> {
                List<PuzzleRecord> pz = new ArrayList<>();


                if (StringUtils.isNotBlank(p.getPrefixesStrings().get(0).getContent())) {
                    pz.add(new PuzzleRecord(p.getIndex() + ".REQUEST", Stream.of(p.getItem1(), p.getItem2()).filter(i -> i <= yodesk.getTiles().getTiles().size() && i < 112)
                            .map(tileId -> GetTile(tileId, icmw)).collect(Collectors.toList()), "REQUEST", p.getPrefixesStrings().get(0).getContent()));
                }
                if (StringUtils.isNotBlank(p.getPrefixesStrings().get(1).getContent())) {
                    pz.add(new PuzzleRecord(p.getIndex() + ".THANK", Collections.emptyList(), "THANKS", p.getPrefixesStrings().get(1).getContent()));
                }
                if (StringUtils.isNotBlank(p.getPrefixesStrings().get(2).getContent())) {
                    pz.add(new PuzzleRecord(p.getIndex() + ".OFFER", Collections.emptyList(), "OFFER", p.getPrefixesStrings().get(2).getContent()));
                }
                if (StringUtils.isNotBlank(p.getPrefixesStrings().get(3).getContent())) {
                    pz.add(new PuzzleRecord(p.getIndex() + ".MISSION", Collections.emptyList(), "MISSION", p.getPrefixesStrings().get(3).getContent()));
                }
                if (StringUtils.isNotBlank(p.getPrefixesStrings().get(4).getContent())) {
                    pz.add(new PuzzleRecord(p.getIndex() + ".UNUSED", Collections.emptyList(), "UNUSED", p.getPrefixesStrings().get(4).getContent()));
                }

                pz.get(0).setImages(Stream.of(p.getItem1()).map(tileId -> GetTile(tileId, icmw)).collect(Collectors.toList()));
                if (pz.size() < 2) {
                    List<BufferedImage> bufferedImages = pz.get(0).getImages();
                    bufferedImages.addAll(Stream.of(p.getItem2()).filter(i -> i <= yodesk.getTiles().getTiles().size() && i >= 418)
                            .map(tileId -> GetTile(tileId, icmw)).collect(Collectors.toList()));
                    //pz.get(0).setImages();
                } else {
                    pz.get(1).setImages(Stream.of(p.getItem2()).filter(i -> i <= yodesk.getTiles().getTiles().size() && i >= 418)
                            .map(tileId -> GetTile(tileId, icmw)).collect(Collectors.toList()));
                }

                puzzleRecords.addAll(pz);
            });
            WordUtils.savePuzzles(puzzleRecords, opath.resolve("puz2" + E_DOCX));

        } catch (Exception e) {
            JavaFxUtils.showAlert("Error saving puzzles to the files", e);
        }
    }

    private void ReadPUZ2(Puzzle puzzle, int position) throws IOException {

        Log.debug("Puzzle #" + puzzle.getIndex() + "; Size: 0x" + longToHex(puzzle.byteSize(), 4));
        DumpData(opath.resolve("PUZ2").resolve(StringUtils.leftPad(Integer.toString(puzzle.getIndex()), 4, '0')), position, puzzle.byteSize());
    }

    public void dumpPuzzlesTextToDocxCLick(ActionEvent actionEvent) {
    }

    public void loadPuzzlesTranslatedTextClick(ActionEvent actionEvent) {
    }

    public void replacePuzzlesTextInDtaClick(ActionEvent actionEvent) {
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
            WordUtils.saveRecords("Characters", records, opath.resolve("chars" + E_DOCX));

            List<ImageRecord> imageRecords = yodesk.getCharacters().getFilteredCharacters().stream()
                    .map(c -> new ImageRecord(c.getTileIds().stream().map(t -> GetTile(t, icmw)).collect(Collectors.toList()), c.getName())).collect(Collectors.toList());
            WordUtils.saveCharacters(imageRecords, opath.resolve("chars2" + E_DOCX));

            IOUtils.saveTextFile(yodesk.getCharacters().getFilteredCharacters().stream().map(Character::getName).collect(Collectors.toList()), opath.resolve("chars.txt"));
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error saving characters to the files", e);
        }
    }

    private void ReadCHAR(Character c, int position) throws IOException {

        for (Integer integer : c.getTileIds()) {
            Path path = opath.resolve("Characters").resolve(c.getName());
            IOUtils.createDirectories(path);
            BMPWriter.write(GetTile(integer), path.resolve(StringUtils.leftPad(Integer.toString(integer), 4, '0') + E_BMP));
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
            ReadTNAM();
        } catch (Exception e) {
            JavaFxUtils.showAlert("Error saving names to a file", e);
        }
    }

    private void ReadTNAM() throws IOException, InvalidFormatException {

        Log.clear();
        Log.debug("Names:");
        Log.newLine();
        Log.debug("Total count: " + yodesk.getTileNames().getNames().size());
        Log.newLine();
        IOUtils.createDirectories(opath.resolve("Names"));
        for (TileName n : yodesk.getTileNames().getNames()) {
            if (null != n.getName()) {
                BMPWriter.write(GetTile(n.getTileId()), IOUtils.findUnusedFileName(opath.resolve("Names"), n.getName(), E_BMP));
            }
        }
        IOUtils.saveTextFile(yodesk.getTileNames().getFilteredNames().stream().map(TileName::getName).filter(Objects::nonNull)
                .collect(Collectors.toList()), opath.resolve("tilenames.txt"));

        List<ImageRecord> imageRecords = yodesk.getTileNames().getFilteredNames().stream()
                .map(n -> new ImageRecord(Collections.singletonList(GetTile(n.getTileId(), icmw)), n.getName())).collect(Collectors.toList());
        WordUtils.saveNames(imageRecords, opath.resolve("tilenames2" + E_DOCX));
    }

    public void dumpNamesTextToDocxCLick(ActionEvent actionEvent) {
    }

    public void loadNamesTranslatedTextClick(ActionEvent actionEvent) {
    }

    public void replaceNamesTextInDtaClick(ActionEvent actionEvent) {
    }

    /*

    procedure TMainForm.SectionsStringGridSelectCell(Sender: TObject; ACol, ARow: Integer; var CanSelect: Boolean);
    begin
  if ACol <> 3 then
            begin
    HEX.SetSelStart(StrToInt(SectionsStringGrid.Cells[4, ARow]));
    HEX.SetSelEnd(StrToInt(SectionsStringGrid.Cells[4, ARow]) + 3);
    end else
    begin
    HEX.SetSelStart(StrToInt(SectionsStringGrid.Cells[ACol, ARow]));
    HEX.SetSelEnd(StrToInt(SectionsStringGrid.Cells[ACol, ARow]) + StrToInt(SectionsStringGrid.Cells[1, ARow]) - 1);
    end;
    end;

    procedure TMainForm.MapsStringGridSelectCell(Sender: TObject; ACol, ARow: Integer; var CanSelect: Boolean);
    var pos: Integer;
    begin
  case ACol of
    0..2: begin
    pos := StrToInt(MapsStringGrid.Cells[1, ARow]);
            HEX.SetSelStart(pos);
            HEX.SetSelEnd(pos + StrToInt(MapsStringGrid.Cells[2, ARow]) - 1);
    end;
    3, 4: begin
    pos := StrToInt(MapsStringGrid.Cells[3, ARow]);
            HEX.SetSelStart(pos);
            HEX.SetSelEnd(pos + StrToInt(MapsStringGrid.Cells[4, ARow]) - 1);
    end;
    5..7: begin
    pos := StrToInt(MapsStringGrid.Cells[5, ARow]);
            HEX.SetSelStart(pos);
            HEX.SetSelEnd(pos + StrToInt(MapsStringGrid.Cells[6, ARow]) - 1);
    end;
    8, 9: begin
    pos := StrToInt(MapsStringGrid.Cells[8, ARow]);
            HEX.SetSelStart(pos);
            HEX.SetSelEnd(pos + StrToInt(MapsStringGrid.Cells[9, ARow]) - 1);
    end;
    10, 11: begin
    pos := StrToInt(MapsStringGrid.Cells[10, ARow]);
            HEX.SetSelStart(pos);
            HEX.SetSelEnd(pos + StrToInt(MapsStringGrid.Cells[11, ARow]) - 1);
    end;
    12, 13: begin
    pos := StrToInt(MapsStringGrid.Cells[12, ARow]);
            HEX.SetSelStart(pos);
            HEX.SetSelEnd(pos + StrToInt(MapsStringGrid.Cells[13, ARow]) - 1);
    end;
    14, 15: begin
    pos := StrToInt(MapsStringGrid.Cells[14, ARow]);
            HEX.SetSelStart(pos);
            HEX.SetSelEnd(pos + StrToInt(MapsStringGrid.Cells[15, ARow]) - 1);
    end;
    16, 17: begin
    pos := StrToInt(MapsStringGrid.Cells[16, ARow]);
            HEX.SetSelStart(pos);
            HEX.SetSelEnd(pos + StrToInt(MapsStringGrid.Cells[17, ARow]) - 1);
    end;
    end;
    HEX.CenterCursorPosition;

    bmp.PixelFormat := pf8bit;
    bmp.Width := TileSize;
    bmp.Height := TileSize;
    FillInternalPalette(BMP, $FE00FE);

    ReadIZON(StrToInt(MapsStringGrid.Cells[0, ARow]), false);
    end;




    procedure TMainForm.ClipboardImageDragDrop(Sender, Source: TObject; X, Y: Integer);
    var left, top: Word;
    begin
  if (Source is TDrawGrid) then
            begin
    bmp.PixelFormat := pf8bit;
    bmp.Width := TileSize;
    bmp.Height := TileSize;
    FillInternalPalette(BMP, currentFillColor);

    FillInternalPalette(ClipboardImage.Picture.Bitmap, currentFillColor);
    left := x div 32 * 32;
    top := y div 32 * 32;
    GetTile(dta, selectedCell, bmp);
    DrawBMP(ClipboardImage.Picture.Bitmap, left, top, BMP);
    ClipboardImage.Repaint;
    end;
    end;

    procedure TMainForm.ClipboardImageDragOver(Sender, Source: TObject; X, Y: Integer; State: TDragState; var Accept: Boolean);
    begin
    Accept := (Source is TDrawGrid);
    end;

    procedure TMainForm.TilesDrawGridMouseDown(Sender: TObject; Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
    var ACol, ARow: Integer;
    canSel: Boolean;
    begin
  TilesDrawGrid.MouseToCell(x, y, ACol, ARow);
    selectedCell := ACol + ARow * 16;
  if selectedCell < DTA.tilesCount then
    begin
    if Button = mbLeft then TilesDrawGrid.BeginDrag(false, 8) else
    begin
    TilesDrawGrid.Row := ARow;
    TilesDrawGrid.Col := ACol;
    TilesDrawGridSelectCell(Sender, ARow, ACol, canSel);
    TilesDrawGrid.SetFocus;
    ShowTileStatus;
    end;
    end;
    end;


// Clear
    procedure TMainForm.Button5Click(Sender: TObject);
    var r: TRect;
    begin
    r := Rect(0, 0, ClipboardImage.Width, ClipboardImage.Height);
    ClipboardImage.Canvas.Brush.Color := currentFillColor;
    ClipboardImage.Canvas.Brush.Style := bsSolid;
  ClipboardImage.Canvas.FillRect(r);
    end;


    procedure TMainForm.ClipboardImageMouseDown(Sender: TObject; Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
    begin
    selectedTileX := x div 32 * 32;
    selectedTileY := y div 32 * 32;
  ClipboardImage.BeginDrag(false, 8);
    end;


    procedure TMainForm.TilesDrawGridDragOver(Sender, Source: TObject; X,
                                              Y: Integer; State: TDragState; var Accept: Boolean);
    begin
    Accept := (Source is TImage);
    end;

    procedure TMainForm.TilesDrawGridDragDrop(Sender, Source: TObject; X, Y: Integer);
    var ACol, ARow: Integer;
    p: PByteArray;
    i, j: Word;
    begin
  if (Source is TImage) then
            begin
    TilesDrawGrid.MouseToCell(x, y, ACol, ARow);
    selectedCell := ACol + ARow * 16;
    for i := 0 to 31 do
    begin
    p := ClipboardImage.Picture.Bitmap.ScanLine[i + selectedTileY];
      for j := 0 to 31 do
    begin
          DTA.SetPosition(DTA.GetDataOffset(knownSections[4]) + selectedCell * $404 + 4 + i * 32 + j);
          DTA.WriteByte(p[j + selectedTileX]);
    end;
    end;
    TilesDrawGrid.Repaint;
    end;
    end;



    procedure TMainForm.ReadTGEN;
    var size: Integer;
    begin
    size := DTA.ReadLongWord;
  LOG.debug('TGEN: '+inttohex(size,4)); //4 байта - длина блока TGEN
    //надо расшифровать
    showmessage('Обработка tGEN пока не поддерживается!!!');
    seek(SrcFile,filepos(SrcFile) + size);
    end;


    procedure TMainForm.WhiteMenuItemClick(Sender: TObject);
    begin
    currentFillColor := TMenuItem(Sender).Tag;
    ZeroColorRGDo(true);
    end;

    procedure TMainForm.ZeroColorRGDo(remember: Boolean);
    var
    i,j: word;
    arr: Array[0..287, 0..287] of Byte;
    p: PByteArray;
    begin
  for i := 0 to ClipboardImage.Height - 1 do
    begin
    p := ClipboardImage.Picture.Bitmap.ScanLine[i];
    for j := 0 to ClipboardImage.Width - 1 do arr[i][j] := p[j];
    end;

    FillInternalPalette(TileImage.Picture.Bitmap, currentFillColor);
    FillInternalPalette(ClipboardImage.Picture.Bitmap, currentFillColor);

    // Clear ClipboardImage
    Button5.Click;

  if remember then
    for i := 0 to ClipboardImage.Height - 1 do
    begin
    p := ClipboardImage.Picture.Bitmap.ScanLine[i];
      for j := 0 to ClipboardImage.Width - 1 do p[j] := arr[i][j];
    end;

    TileImage.Repaint;
    ClipboardImage.Repaint;
    TilesDrawGrid.Repaint;
    end;


    procedure TMainForm.TilesDrawGridMouseUp(Sender: TObject; Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
    var pnt: TPoint;
    begin
  if selectedCell < DTA.tilesCount then
    if (Button = mbRight) and GetCursorPos(pnt) then TilesPopupMenu.Popup(pnt.X - 7, pnt.Y - 10)
    else ShowTileStatus;
    end;


// Tiles menu item click

    procedure TMainForm.Bottomlayer1Click(Sender: TObject);
    var flag: Cardinal;
    begin
    flag := TMenuItem(Sender).Tag;
  if (flag = 2000000000) then flag := 2147483680;
  DTA.SetTileFlag(selectedCell, flag);
    ShowTileStatus;
    //flag := DTA.GetTileFlag(selectedCell);
    //Showmessage(inttohex(flag,4));
    end;


    procedure TMainForm.Adddtiles1Click(Sender: TObject);
var count: Byte;
  size: Cardinal;
begin
  count := StrToInt(InputBox('Add tile(s)', 'How many tiles to add?', '0'));
  if count > 0 then
  begin
    DTA.SetPosition(DTA.GetDataOffset(knownSections[4]) + DTA.tilesCount * $404);
    size := $404 * count;
    DTA.InsertEmptyArea(size);
    DTA.SetPosition(DTA.GetDataOffset(knownSections[4]) - 4);
    DTA.WriteLongWord(DTA.GetDataSize(knownSections[4]) + size);
    ScanFileAndUpdate;
    prevTile := -1;
  end;
end;

procedure TMainForm.Deletetile1Click(Sender: TObject);
begin
  if MessageDlg('Are you sure to delete current tile?', mtConfirmation, [mbYes, mbNo], 0) = mrYes then
  begin
    DTA.SetPosition(DTA.GetDataOffset(knownSections[4]) + selectedCell * $404);
    DTA.DeleteArea($404);
    DTA.SetPosition(DTA.GetDataOffset(knownSections[4]) - 4);
    DTA.WriteLongWord(DTA.GetDataSize(knownSections[4]) - $404);
    ScanFileAndUpdate;
    prevTile := -1;
  end;
end;


    procedure TMainForm.MapsListStringGridSelectCell(Sender: TObject; ACol, ARow: Integer; var CanSelect: Boolean);
    begin
  if ARow < DTA.mapsCount then ViewMap(ARow);
    end;


    procedure TMainForm.ViewMap(id: Word);
    begin
    bmp.PixelFormat := pf8bit;
    bmp.Width := TileSize;
    bmp.Height := TileSize;
    FillInternalPalette(BMP, $FE00FE);
    ReadMap(id, true, false);
    end;


    procedure TMainForm.ReadColumn(col: Byte; StringGrid: TStringGrid);
    var f: textfile;
    s: String;
    i, c1, c2: Word;
    begin
    s := StringGrid.Cells[0, 0];
    StringGrid.Cols[0].Clear;
    StringGrid.Cells[0, 0] := s;
    s := StringGrid.Cells[col, 0];
    StringGrid.Cols[col].Clear;
    StringGrid.Cells[col, 0] := s;

    i := 1; //строка
    AssignFile(f, Opendialog1.FileName);
    Reset(f);
  while not Eof(f) do
    begin
    ReadLn(f, s);
    if s <> '' then
            begin
      if StringGrid.RowCount = i then StringGrid.RowCount := StringGrid.RowCount + 300;
    StringGrid.Cells[0, i] := IntToStr(i);
    StringGrid.Cells[col, i] := s;
    inc(i);
    end;
    end;
    CloseFile(f);

    c1 := 1;
    c2 := 1;
  for i := 1 to StringGrid.RowCount - 1 do
    begin
    if StringGrid.Cells[1, i] <> '' then inc(c1);
    if StringGrid.Cells[2, i] <> '' then inc(c2);
    end;

  if (c1 > 1) or (c2 > 1) then StringGrid.RowCount := Max(c1, c2);
  if (c1 > 1) and (c2 > 1) and (c1 <> c2) then Showmessage('Number of phrases does not match!');
    end;


    function TMainForm.FindStringPosition(searchString: String): Cardinal;
    var
    startPosition: Cardinal;
    searchLength, currentLength, i: Word;
    begin
    Result := 0;
    searchLength := Length(searchString);
    startPosition := DTA.GetPosition;
  while (DTA.GetPosition + searchLength < DTA.GetSize) do
    begin
    DTA.SetPosition(startPosition);
    currentLength := 0;
    for i := 1 to searchLength do
    begin
      if DTA.ReadChar <> searchString[i] then Break;
    Inc(currentLength);
    end;
    if currentLength = searchLength then
    begin
    Result := startPosition;
    Break;
    end else Inc(startPosition);
    end; // while
    end;


    function TMainForm.FindStringPositionReverse(searchString: String): Cardinal;
    var
    startPosition: Cardinal;
    searchLength, currentLength, i: Word;
    begin
    Result := 0;
    searchLength := Length(searchString);
    startPosition := DTA.GetPosition;
  while (DTA.GetPosition > 0) do
    begin
    DTA.SetPosition(startPosition);
    currentLength := 0;
    for i := 1 to searchLength do
    begin
      if DTA.ReadChar <> searchString[i] then Break;
    Inc(currentLength);
    end;
    if currentLength = searchLength then
    begin
    Result := startPosition;
    Break;
    end else Dec(startPosition);
    end; // while
    end;


// Get original
    procedure TMainForm.Button1Click(Sender: TObject);
    begin
  if Opendialog1.Execute then ReadColumn(1, StringGrid1);
    end;


// Get translated
    procedure TMainForm.Button12Click(Sender: TObject);
    begin
 if Opendialog1.Execute then ReadColumn(2, StringGrid1);
    end;


    function TMainForm.DecompressString(s: String): String;
    begin
    result := AnsiReplaceStr(s, '[CR2]', '[CR][CR]');
    result := AnsiReplaceStr(result, '[CR]', chr($0d) + chr($0a));
    result := AnsiReplaceStr(result, chr($5F), chr($A5));
    end;



// all StringGridDrawCell, multiline output

    procedure TMainForm.StringGridDrawCell(Sender: TObject; ACol, ARow: Integer; Rect: TRect; State: TGridDrawState);
    var
    Sentence, { Выводимый текст }
    CurWord: string; { Текущее выводимое слово }
    SpacePos, { Позиция первого пробела }
    CurX, { Х-координата 'курсора' }
    CurY: Integer; { Y-координата 'курсора' }
    EndOfSentence: Boolean; { Величина, указывающая на заполненность ячейки }
    hGrid: TStringGrid;
    n: Integer;
    begin
    { Инициализируем шрифт, чтобы он был управляющим шрифтом }
    hGrid := (Sender as TStringGrid);
    hGrid.Canvas.Font := Font;

    with hGrid.Canvas do
    begin
    { Если это фиксированная ячейка, тогда используем фиксированный цвет }
    if gdFixed in State then
    begin
    Pen.Color := hGrid.FixedColor;
    Brush.Color := hGrid.FixedColor;
    end
    { в противном случае используем нормальный цвет }
    else
    begin
    Pen.Color := hGrid.Color;
    Brush.Color := hGrid.Color;
    end;
    { Рисуем подложку цветом ячейки }
    Rectangle(Rect.Left, Rect.Top, Rect.Right, Rect.Bottom);
    end;

    { Начинаем рисование с верхнего левого угла ячейки }
    CurX := Rect.Left;
    CurY := Rect.Top;

    { Здесь мы получаем содержание ячейки }
    Sentence := hGrid.Cells[ACol, ARow];
    n:=0;

    { для каждого слова ячейки }
    EndOfSentence := FALSE;
  while (not EndOfSentence) do
    begin
    { для получения следующего слова ищем пробел }
    SpacePos := Pos(' ', Sentence);
    if SpacePos > 0 then
            begin
    { получаем текущее слово плюс пробел }
    CurWord := Copy(Sentence, 0, SpacePos);

    { получаем остальную часть предложения }
    Sentence := Copy(Sentence, SpacePos + 1, Length(Sentence) - SpacePos);
    end
    else
    begin
    { это - последнее слово в предложении }
    EndOfSentence := TRUE;
    CurWord := Sentence;
    end;

    with hGrid.Canvas do
    begin
    { если текст выходит за границы ячейки }
      if (TextWidth(CurWord) + CurX) > Rect.Right then
    begin
    { переносим на следующую строку }
    CurY := CurY + TextHeight(CurWord);
    CurX := Rect.Left;
    n:=n + TextHeight(CurWord);
    end;

    { выводим слово }
    TextOut(CurX, CurY, CurWord);
    { увеличиваем X-координату курсора }
    CurX := CurX + TextWidth(CurWord);
    end;
    end;
    n := n + hGrid.Canvas.TextHeight(CurWord);
    hGrid.RowHeights[Arow] := Max(n + 1, hGrid.RowHeights[Arow]);
    end;


    procedure TMainForm.Highlight(offset, size: Cardinal);
    begin
  HEX.LoadFromStream(DTA.data);
  HEX.SetSelStart(offset);
  HEX.SetSelEnd(offset + size - 1);
    HEX.CenterCursorPosition;
    Application.ProcessMessages;
    end;


// Maps Replace text

    procedure TMainForm.Button14Click(Sender: TObject);
    var
    i, tw, oldSize, newSize: Word;
    oldPhrase, newPhrase: String;
    size, phaseStart, phaseEnd, iactOffset, izonOffset: Cardinal;
    delta: Integer;
    begin
    Log.Clear;
    ProgressBar1.Position := 0;
    ProgressBar1.Max := StringGrid1.RowCount;
    Label3.Caption := '';

  DTA.SetPosition(TMap(DTA.maps.Objects[0]).mapOffset);
    phaseEnd := DTA.GetPosition;

  for i := 1 to StringGrid1.RowCount - 1 do
    begin
    oldPhrase := DecompressString(StringGrid1.Cells[1, i]);
    oldSize := Length(oldPhrase);
    if CheckBox3.Checked then newPhrase := DecompressString(Trim(StringGrid1.Cells[2, i]))
            else newPhrase := DecompressString(StringGrid1.Cells[2, i]);
    newSize := Length(newPhrase);
    delta := newSize - oldSize;
//    Log.Debug('======================================================================== #' + IntToStr(i));
//    Log.Debug('Search phase: ' + IntToHex(phaseEnd, 2));
    DTA.SetPosition(phaseEnd);
    phaseStart := FindStringPosition(oldPhrase);

//    Log.Debug('oldPhrase: ' + oldPhrase + ': oldSize: ' + IntToHex(oldSize, 2));
//    Log.Debug('newPhrase: ' + newPhrase + ': newSize: ' + IntToHex(newSize, 2));
//    Log.Debug('Delta: ' + IntToStr(delta));
//    Log.Debug('Phase start: ' + IntToHex(phaseStart, 2));

    DTA.SetPosition(phaseStart - 2);
    tw := DTA.ReadWord;
    if tw <> oldSize then
            begin
    Highlight(phaseStart - 2, 2);
    ShowMessage('Readed size:' + Inttohex(tw, 4) + '(need ' + inttostr (oldSize) + ')');
      break;
    end;

    DTA.SetPosition(phaseStart - 2);
    DTA.WriteWord(newSize);

    //Highlight(phaseStart, oldSize);
    //ShowMessage('Before writing text. Size: ' + Inttohex(hex.SelCount, 4));

    DTA.ReplaceArea(oldPhrase, newPhrase);
    phaseEnd := phaseStart + newSize - 2;

    //HEX.LoadFromStream(DTA.data);
    //Highlight(phaseStart, newSize);
    //ShowMessage('After writing text. Size: ' + Inttohex(hex.SelCount, 4));

    if delta <> 0 then
            begin
      DTA.SetPosition(phaseStart - 2);
    iactOffset := FindStringPositionReverse('IACT');
    //Showmessage(inttohex(iactOffset,4));

      DTA.SetPosition(iactOffset);
      if DTA.ReadString(4) <> 'IACT' then
            begin
    Highlight(iactOffset, 4);
    ShowMessage('IACT not found!!!');
    Break;
    end;

    size := DTA.ReadLongWord;
      DTA.MovePosition(-4);
      DTA.WriteLongWord(size + delta);

    //Highlight(iactOffset + 4, 4);
    //ShowMessage('IACT size (old:new): ' + Inttohex(size, 4) + ':' + inttohex(size + delta, 4));


      DTA.SetPosition(iactOffset);
    izonOffset := FindStringPositionReverse('IZON');
    //Showmessage(inttohex(izonOffset,4));
      DTA.SetPosition(izonOffset);
      if DTA.ReadString(4) <> 'IZON' then ShowMessage('IZON not found!!!');
      DTA.MovePosition(-10);
    size := DTA.ReadLongWord;
      DTA.MovePosition(-4);
      DTA.WriteLongWord(size + delta);

    //Highlight(izonOffset - 6, 4);
    //ShowMessage('IZON size (old:new): ' + Inttohex(size, 4) + ':' + inttohex(size + delta, 4));
    end;

    ProgressBar1.Position := i;
    Label3.Caption := Format('%.2f %%', [((i + 1)/ StringGrid1.RowCount) * 100]);

    Application.ProcessMessages;
    end;

  Hex.LoadFromStream(DTA.data);
  DTA.data.SaveToFile('d:\YExplorer\test\test2\Yodesk.dta');
    showmessage('OK');
    ScanFileAndUpdate;
    end;


// puzzles
// Get original
    procedure TMainForm.Button16Click(Sender: TObject);
    begin
  if Opendialog1.Execute then ReadColumn(1, StringGrid4);
    end;


// Get translated
    procedure TMainForm.Button17Click(Sender: TObject);
    begin
  if Opendialog1.Execute then ReadColumn(2, StringGrid4);
    end;


// Replace text
    procedure TMainForm.Button19Click(Sender: TObject);
    var
    i, tw, oldSize, newSize: Word;
    oldPhrase, newPhrase: String;
    size, phaseStart, phaseEnd, ipuzOffset, previousSize: Cardinal;
    delta: Integer;
    begin
    Log.Clear;
    ProgressBar3.Position := 0;
    ProgressBar3.Max := StringGrid4.RowCount;
    Label7.Caption := '';

    previousSize := DTA.GetSize;
 // PUZ2
  DTA.SetPosition(DTA.GetDataOffset(knownSections[6]));
    phaseEnd := DTA.GetPosition;

  for i := 1 to StringGrid4.RowCount - 1 do
    begin
    oldPhrase := DecompressString(StringGrid4.Cells[1, i]);
    oldSize := Length(oldPhrase);
    if CheckBox10.Checked then newPhrase := DecompressString(Trim(StringGrid4.Cells[2, i]))
            else newPhrase := DecompressString(StringGrid4.Cells[2, i]);
    newSize := Length(newPhrase);
    delta := newSize - oldSize;
//    Log.Debug('======================================================================== #' + IntToStr(i));
//    Log.Debug('Search phase: ' + IntToHex(phaseEnd, 2));
    DTA.SetPosition(phaseEnd);
    phaseStart := FindStringPosition(oldPhrase);

//    Log.Debug('oldPhrase: ' + oldPhrase + ': oldSize: ' + IntToHex(oldSize, 2));
//    Log.Debug('newPhrase: ' + newPhrase + ': newSize: ' + IntToHex(newSize, 2));
//    Log.Debug('Delta: ' + IntToStr(delta));
//    Log.Debug('Phase start: ' + IntToHex(phaseStart, 2));

    DTA.SetPosition(phaseStart - 2);
    tw := DTA.ReadWord;
    if tw <> oldSize then
            begin
    Highlight(phaseStart - 2, 2);
    ShowMessage('Readed size:' + Inttohex(tw, 4) + '(need ' + inttostr (oldSize) + ')');
      break;
    end;

    DTA.SetPosition(phaseStart - 2);
    DTA.WriteWord(newSize);

    //Highlight(phaseStart, oldSize);
    //ShowMessage('Before writing text. Size: ' + Inttohex(hex.SelCount, 4));

    DTA.ReplaceArea(oldPhrase, newPhrase);
    phaseEnd := phaseStart + newSize - 2;

    //HEX.LoadFromStream(DTA.data);
    //Highlight(phaseStart, newSize);
    //ShowMessage('After writing text. Size: ' + Inttohex(hex.SelCount, 4));

    if delta <> 0 then
            begin
      DTA.SetPosition(phaseStart - 2);
    ipuzOffset := FindStringPositionReverse('IPUZ');
    //Showmessage(inttohex(ipuzOffset,4));
      DTA.SetPosition(ipuzOffset);
      if DTA.ReadString(4) <> 'IPUZ' then ShowMessage('IPUZ not found!!!');
    size := DTA.ReadLongWord;
      DTA.MovePosition(-4);
      DTA.WriteLongWord(size + delta);

    //Highlight(izonOffset - 6, 4);
    //ShowMessage('IZON size (old:new): ' + Inttohex(size, 4) + ':' + inttohex(size + delta, 4));
    end;

    ProgressBar3.Position := i;
    Label7.Caption := Format('%.2f %%', [((i + 1)/ StringGrid4.RowCount) * 100]);

    Application.ProcessMessages;
    end;

    // поменять размер PUZ2
  DTA.SetPosition(DTA.GetStartOffset(knownSections[6]));
  if DTA.ReadString(4) <> 'PUZ2' then ShowMessage('PUZ2 not found!!!');
    size := DTA.ReadLongWord;
  if size <> DTA.GetDataSize((knownSections[6])) then ShowMessage('PUZ2 size don''t match!');
  DTA.MovePosition(-4);
  DTA.WriteLongWord(size - previousSize + DTA.GetSize);

  Hex.LoadFromStream(DTA.data);
  DTA.data.SaveToFile('d:\YExplorer\test\test2\Yodesk.dta');
    showmessage('OK');
    ScanFileAndUpdate;
    end;


    procedure TMainForm.StringGrid1DrawCell(Sender: TObject; ACol, ARow: Integer; Rect: TRect; State: TGridDrawState);
    begin
    StringGridDrawCell(Sender, ACol, ARow, Rect, State);
    end;


    procedure TMainForm.StringGrid4DrawCell(Sender: TObject; ACol, ARow: Integer; Rect: TRect; State: TGridDrawState);
    begin
    StringGridDrawCell(Sender, ACol, ARow, Rect, State);
    end;


// Names
// Get original
    procedure TMainForm.Button20Click(Sender: TObject);
    begin
  if Opendialog1.Execute then ReadColumn(1, StringGrid2);
    end;

// Get translated
    procedure TMainForm.Button21Click(Sender: TObject);
    begin
  if Opendialog1.Execute then ReadColumn(2, StringGrid2);
    end;


//Check phase positions (may be not need)
    procedure TMainForm.Button22Click(Sender: TObject);
    var i: Word;
    s, msg: String;
    len: Byte;
    begin
// TNAM
  DTA.SetPosition(DTA.GetDataOffset(knownSections[10]));
    msg := '';
  for i := 1 to DTA.namesCount do
    begin
    DTA.ReadWord;
    len := Length(StringGrid2.Cells[1, i]);
    s := DTA.ReadString(len);
    if s <> StringGrid2.Cells[1, i] then
            begin
    msg := 'Readed name "' + s + '" must be: "' + StringGrid2.Cells[1, i] + '"';
    Break;
    end;
    DTA.ReadString(24 - len);
    end;
  if msg <> '' then
            begin
    StringGrid2.Row := i;
    StringGrid2.Selection := TGridRect(Rect(StringGrid2.FixedCols, i, StringGrid2.FixedCols + 1, i));
    Application.ProcessMessages;
    Showmessage(msg);
    end else ShowMessage('OK, founded all names.');
    end;


//Replace text (Names)
    procedure TMainForm.Button23Click(Sender: TObject);
    var currentPos: Cardinal;
    i: Word;
    j, len: Byte;
    str: String;
    begin
    Log.Clear;
    ProgressBar2.Position := 0;
    ProgressBar2.Max := StringGrid2.RowCount;
    Label9.Caption := '';

// TNAM
  DTA.SetPosition(DTA.GetDataOffset(knownSections[10]));

  for i := 1 to DTA.namesCount do
    begin
    DTA.ReadWord;
    currentPos := DTA.GetPosition;
    // Check again
    //if DTA.ReadString(Length(StringGrid2.Cells[1, i])) <> StringGrid2.Cells[1, i] then showmessage(inttohex(currentPos,4) + ' !! ' + StringGrid2.Cells[1, i]);
    // Clear with #0000000000000000
    //DTA.SetPosition(currentPos);
    for j := 1 to 6 do DTA.WriteLongWord(0);

    DTA.SetPosition(currentPos);

    str := StringGrid2.Cells[2, i];
    if CheckBox4.Checked then str := Trim(str);
    DTA.WriteString(str);
    DTA.ReadString(24 - Length(str));

    ProgressBar2.Position := i;
    Label9.Caption := Format('%.2f %%', [((i + 1)/ StringGrid2.RowCount) * 100]);

    Application.ProcessMessages;
    end;

  Hex.LoadFromStream(DTA.data);
  DTA.data.SaveToFile('d:\YExplorer\test\test2\Yodesk.dta');
    showmessage('OK');
    ScanFileAndUpdate;
    end;

    procedure TMainForm.TabSheet18Show(Sender: TObject);
    begin
    MapsListStringGrid.Parent := TabSheet18;
    MapsListStringGrid.Left := 8;
    TilesDrawGrid.Parent := TabSheet18;
    TilesDrawGrid.Left := MapsListStringGrid.Left + MapsListStringGrid.Width + 8;
    MapImage.Parent := TabSheet18;
    MapImage.Left := TilesDrawGrid.Left + TilesDrawGrid.Width + 8;
    MapsListStringGrid.Top := TilesDrawGrid.Top;
    MapImage.Top := TilesDrawGrid.Top;
    end;

    procedure TMainForm.TabSheet18Hide(Sender: TObject);
    begin
    MapsListStringGrid.Parent := TabSheet11;
    MapsListStringGrid.Left := 224;
    TilesDrawGrid.Parent := TabSheet4;
    TilesDrawGrid.Left := 224;
    MapImage.Parent := TabSheet11;
    MapImage.Left := 320;
    MapsListStringGrid.Top := 7;
    MapImage.Top := 7;
    end;


    procedure TMainForm.MapImageDragOver(Sender, Source: TObject; X, Y: Integer; State: TDragState; var Accept: Boolean);
    begin
    Accept := (Source is TDrawGrid);
    end;


    procedure TMainForm.SelectMapTile(X, Y: Integer; concrete: Boolean);
    var left, top, w, h: Word;
    begin
    left := x div 32;
    top := y div 32;

  DTA.SetPosition(TMap(DTA.maps.Objects[MapsListStringGrid.Row]).mapOffset);   // go to map data
  DTA.ReadString(10);
    w := DTA.ReadWord;                // width:word; //2 bytes: map width (W)
    //h := DTA.ReadWord;                // height:word; //2 bytes: map height (H)
  //DTA.ReadString(8);
  DTA.ReadString(10);
  DTA.MovePosition((top * w + left) * 6);
  if concrete then DTA.MovePosition(RadioGroup1.ItemIndex * 2);
    end;


    procedure TMainForm.MapImageDragDrop(Sender, Source: TObject; X, Y: Integer);
begin
  SelectMapTile(x, y, true);
  prevTileAddress := DTA.GetPosition;
  prevTile := DTA.ReadWord;
  DTA.MovePosition(-2);
  DTA.WriteWord(selectedCell);
  Highlight(DTA.GetPosition - 2, 2);
  ViewMap(MapsListStringGrid.Row);
end;


    procedure TMainForm.MapImageMouseUp(Sender: TObject; Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
var pnt: TPoint;
begin
  SelectMapTile(x, y, false);
  Highlight(DTA.GetPosition, 6);
  if (Button = mbRight) and GetCursorPos(pnt) and TabSheet18.Visible then
  begin
    Undo1.Enabled := prevTile <> -1;
    MapPopupMenu.Popup(pnt.X - 7, pnt.Y - 10);
  end;
end;

    StatusBar.Panels[0].Text := '$' + IntToHex(t1, 4) + ' $' + IntToHex(t2, 4) + ' $' + IntToHex(t3, 4) + ' (bottom, middle, top)';

  if (Button = mbRight) and GetCursorPos(pnt) and TabSheet18.Visible then MapPopupMenu.Popup(pnt.X - 7, pnt.Y - 10);
    end;


    procedure TMainForm.RadioGroup1Click(Sender: TObject);
    begin
    prevTile := -1;
    end;


    procedure TMainForm.Undo1Click(Sender: TObject);
begin
  if prevTile <> -1 then
  begin
    DTA.SetPosition(prevTileAddress);
    DTA.WriteWord(prevTile);
    Highlight(DTA.GetPosition - 2, 2);
    prevTile := -1;
    ViewMap(MapsListStringGrid.Row);
  end;
end;


    procedure TMainForm.Empty1Click(Sender: TObject);
var pt : tPoint;
begin
  pt := ScreenToClient(Mouse.CursorPos);
  SelectMapTile(pt.x div 32, pt.y div 32, true);
  prevTileAddress := DTA.GetPosition;
  prevTile := DTA.ReadWord;
  DTA.MovePosition(-2);
  DTA.WriteWord($FFFF);
  Highlight(DTA.GetPosition - 2, 2);
  ViewMap(MapsListStringGrid.Row);
end;

*/


    /*procedure TMainForm.HEXMouseUp(Sender: TObject; Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
    begin
            ShowHEXCaretIndex;
    end;

    procedure TMainForm.HEXKeyUp(Sender: TObject; var Key: Word; Shift: TShiftState);
    begin
            ShowHEXCaretIndex;
    end;

    procedure TMainForm.HEXSelectionChanged(Sender: TObject);
    begin
            ShowHEXCaretIndex;
    end;

    procedure TMainForm.ShowHEXCaretIndex;
    begin
  if HEX.HasFile then StatusBar.Panels[0].Text := 'Offset: 0x' + IntToHex(HEX.GetSelStart, 8);
    end;*/

    private BufferedImage GetTile(int tileId) {
        return GetTile(tileId, Config.icm0);
    }

    private BufferedImage GetTile(int tileId, IndexColorModel icm) {

        int position = yodesk.getTiles().tilePixelsPosition(tileId);
        return ImageUtils.readBPicture(yodesk.getTiles().getRawTiles(), position, TILE_SIZE, TILE_SIZE, icm);
    }

    private WritableImage GetWTile(int tileId) {

        int position = yodesk.getTiles().tilePixelsPosition(tileId);
        return ImageUtils.readWPicture(yodesk.getTiles().getRawTiles(), position, TILE_SIZE, TILE_SIZE, Config.transparentColor);
    }

    private void GetWTile(int tileId, Canvas canvas, int xOffset, int yOffset, Color transparentColor) {

        int position = yodesk.getTiles().tilePixelsPosition(tileId);
        ImageUtils.drawOnCanvas(yodesk.getTiles().getRawTiles(), position, canvas, xOffset, yOffset, transparentColor);
    }

    private void fillMapTile(Canvas canvas, int xOffset, int yOffset, Color color) {
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
        return /*"$" +*/ StringUtils.leftPad(result, size, '0');
    }

    public String longToHex(long value, int size) {

        String result = Long.toHexString(value);
        return StringUtils.leftPad(result, size, '0');
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
