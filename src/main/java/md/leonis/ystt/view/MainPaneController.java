package md.leonis.ystt.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import md.leonis.ystt.model.KnownSections;
import md.leonis.ystt.model.MapEntry;
import md.leonis.ystt.model.SectionMetrics;
import md.leonis.ystt.utils.Config;
import md.leonis.ystt.utils.IOUtils;
import md.leonis.ystt.utils.JavaFxUtils;
import md.leonis.ystt.utils.PaletteUtils;
import net.sf.image4j.codec.bmp.BMPDecoder;
import net.sf.image4j.codec.bmp.BMPEncoder;
import net.sf.image4j.codec.bmp.BMPImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static md.leonis.ystt.utils.Config.gamePalette;
import static md.leonis.ystt.utils.Config.section;

public class MainPaneController {

    public MenuItem openMenuItem;
    public MenuItem saveMenuItem;
    public MenuItem closeMenuItem;
    public MenuItem exitMenuItem;

    public ToggleGroup transparentColorToggleGroup;
    public RadioMenuItem fuchsiaMenuItem;
    public RadioMenuItem blackMenuItem;
    public RadioMenuItem whiteMenuItem;

    public MenuItem addTilesMenuItem;

    public MenuItem howToMenuItem;
    public MenuItem aboutMenuItem;

    public Button openExeFileButton;

    public Label internalVersionLabel;
    public Label sizeLabel;
    public Label crc32Label;
    public Label nameLabel;
    public TableView<SectionMetrics> commonInformationTableView;
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

    public GridPane tilesGridPane;
    public Label tilesCountLabel;
    public Button saveTilesToSeparateFiles;
    public CheckBox decimalFilenamesCheckBox;
    public CheckBox hexFilenamesCheckBox;
    public CheckBox groupByAttributesFilenamesCheckBox;
    public Button saveTilesToOneFile;
    public FlowPane tilesFlowPane;
    public TextField tilesInARowTextField;
    public ImageView clipboardImageView;
    public Button loadClipboardImage;
    public Button saveClipboardImage;
    public Button clearClipboardImage;

    public Label mapsCountLabel;
    public Button saveMapsToFilesButton;
    public CheckBox normalSaveCheckBox;
    public CheckBox groupByFlagsCheckBox;
    public CheckBox groupByPlanetTypeCheckBox;
    public CheckBox dumpActionsCheckBox;
    public CheckBox dumpTextCheckBox;
    public CheckBox saveUnusedTilesCheckBox;
    public TableView<MapEntry> mapsTableView;

    public ImageView mapEditorImageView;
    public GridPane mapEditorGridPane;
    public FlowPane mapEditorFlowPane;
    public ListView mapEditorListView;
    public RadioButton topRadioButton;
    public ToggleGroup mapLayerToggleGroup;
    public RadioButton middleRadioButton;
    public RadioButton bottomRadioButton;
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
    public Label namesCountLabel;

    public Button saveNamesToFilesButton;
    public Button dumpNamesTextToDocx;
    public Button loadNamesTranslatedText;
    public Button replaceNamesTextInDta;
    public CheckBox trimNamesSpacesCheckBox;

    public TextArea logsTextArea;
    public TextArea hexViewerTextArea;

    public Label statusLabel;

    private static final String OUTPUT = "output";
    private static final String E_BMP = ".bmp";
    private static final int TILE_SIZE = 32;

    private List<String> texts;

    int pn;
    String spath, opath;
    int selectedCell, selectedTileX, selectedTileY;
    // TODO
    Color currentFillColor = Color.FUCHSIA;

    // Insert text
    //TODO
    List<String> ts, ts2;

    int prevTile = -1;
    int prevTileX, prevTileY;

    public MainPaneController() {

        //TODO
        /*OpenDialog1.FileName :='D:\YExplorer\iact-e.txt';
        ReadColumn(1, StringGrid1);
        OpenDialog1.FileName :='D:\YExplorer\iact-d.txt';
        ReadColumn(2, StringGrid1);
        Button13Click(Self);
        OpenDialog1.FileName :='D:\YExplorer\puz2-e.txt';
        ReadColumn(1, StringGrid4);
        OpenDialog1.FileName :='D:\YExplorer\puz2-d.txt';
        ReadColumn(2, StringGrid4);
        Button18Click(Self);*/

        /*var k: Word;
        begin
        spath := ExtractFilePath(paramstr(0));
        opath := spath + OUTPUT + '\';
        BMP := TBitmap.Create;
        BMP.PixelFormat := pf8bit;
        TileImage.Picture.Bitmap.PixelFormat := pf8bit;
        TitleImage.Picture.Bitmap.PixelFormat := pf8bit;
        MapImage.Picture.Bitmap.PixelFormat := pf8bit;
        ClipboardImage.Picture.Bitmap.Width := ClipboardImage.Width;
        ClipboardImage.Picture.Bitmap.Height := ClipboardImage.Height;
        ClipboardImage.Picture.Bitmap.PixelFormat := pf8bit;

        ZeroColorRGDo(false);
        FillInternalPalette(TitleImage.Picture.Bitmap, 0);
        FillInternalPalette(MapImage.Picture.Bitmap, 0);
        OpenDTADialog.InitialDir := '.\';
        texts := TStringList.Create;
        log.SetOutput(LogMemo.lines);
        FuchsiaMenuItem.Bitmap.Transparent := false;
        BlackMenuItem.Bitmap.Transparent := false;
        WhiteMenuItem.Bitmap.Transparent := false;

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

        fuchsiaMenuItem.setUserData(Color.FUCHSIA);
        blackMenuItem.setUserData(Color.BLACK);
        whiteMenuItem.setUserData(Color.WHITE);

        //TODO log.Clear;

        ScanFileAndUpdate();

        //TODO Log.SaveToFile(opath, 'Structure');
    }

    public void openMenuItemClick() {

        openFile();
    }

    public static void openFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Executable File");
        //TODO remove
        fileChooser.setInitialDirectory(new File("D:\\Working\\_Yoda\\YExplorer\\other\\Yoda Stories (14.02.1997)"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Executable Files", "*.exe"));
        File selectedFile = fileChooser.showOpenDialog(JavaFxUtils.getStage());
        if (selectedFile != null) {
            try {
                Config.loadGameFiles(selectedFile);
                JavaFxUtils.showMainPanel();
            } catch (Exception e) {
                JavaFxUtils.showAlert("Loading error", e.getClass().getName() + " " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    private void ScanFileAndUpdate() {

        section.clear();
        section.readDTAMetricks();

        // Common information, sections
        internalVersionLabel.setText(section.version);
        nameLabel.setText(section.dtaRevision);
        sizeLabel.setText(section.dump.size() + " / " + section.exeDump.size());
        crc32Label.setText(section.dtaCrc32 + " / " + section.exeCrc32);

        List<SectionMetrics> metrics = section.sections.values().stream()
                .sorted(Comparator.comparing(SectionMetrics::getStartOffset)).collect(Collectors.toList());
        commonInformationTableView.setItems(FXCollections.observableList(metrics));


        // Title image, palette
        DrawTitleImage();
        DrawPalette();

        // Sounds
        soundsCountLabel.setText(Integer.toString(section.soundsCount));
        soundsTextArea.setText(String.join("\n", section.sounds));


        // Tiles, sprites
        tilesCountLabel.setText(Integer.toString(section.tilesCount));

        // Maps
        mapsCountLabel.setText(Integer.toString(section.mapsCount));

        List<MapEntry> mapEntries = section.maps.values().stream()
                .sorted(Comparator.comparing(MapEntry::getId)).collect(Collectors.toList());
        mapsTableView.setItems(FXCollections.observableList(mapEntries));


        // Puzzles
        puzzlesCountLabel.setText(Integer.toString(section.puzzlesCount));

        charactersCountLabel.setText(Integer.toString(section.charsCount));

        namesCountLabel.setText(Integer.toString(section.namesCount));


        // refactor
        /*
        MapsStringGrid.RowCount := DTA.mapsCount + 1;
        MapsStringGrid.Cells[0, 0] := 'Map #';
        MapsStringGrid.Cells[1, 0] := 'Map offset';
        MapsStringGrid.Cells[2, 0] := 'Map size';
        MapsStringGrid.Cells[3, 0] := 'IZON offset';
        MapsStringGrid.Cells[4, 0] := 'IZON size';
        MapsStringGrid.Cells[5, 0] := 'OIE offset';
        MapsStringGrid.Cells[6, 0] := 'OIE size';
        MapsStringGrid.Cells[7, 0] := 'OIE count';

        MapsStringGrid.Cells[8, 0] := 'IZAX offset';
        MapsStringGrid.Cells[9, 0] := 'IZAX size';
        MapsStringGrid.Cells[10, 0] := 'IZX2 offset';
        MapsStringGrid.Cells[11, 0] := 'IZX2 size';
        MapsStringGrid.Cells[12, 0] := 'IZX3 offset';
        MapsStringGrid.Cells[13, 0] := 'IZX3 size';
        MapsStringGrid.Cells[14, 0] := 'IZX4 offset';
        MapsStringGrid.Cells[15, 0] := 'IZX4 size';
        MapsStringGrid.Cells[16, 0] := 'IACT offset';
        MapsStringGrid.Cells[17, 0] := 'IACT size';

        for i := 0 to DTA.mapsCount - 1 do
            begin
        MapsStringGrid.Cells[0, i + 1] := IntToStr(i);
        MapsStringGrid.Cells[1, i + 1] := '$' + IntToHex(TMap(DTA.maps.Objects[i]).mapOffset, 6);
        MapsStringGrid.Cells[2, i + 1] := IntToStr(TMap(DTA.maps.Objects[i]).mapSize);
        MapsStringGrid.Cells[3, i + 1] := '$' + IntToHex(TMap(DTA.maps.Objects[i]).izonOffset, 6);
        MapsStringGrid.Cells[4, i + 1] := IntToStr(TMap(DTA.maps.Objects[i]).izonSize);
        MapsStringGrid.Cells[5, i + 1] := '$' + IntToHex(TMap(DTA.maps.Objects[i]).oieOffset, 6);
        MapsStringGrid.Cells[6, i + 1] := IntToStr(TMap(DTA.maps.Objects[i]).oieSize);
        MapsStringGrid.Cells[7, i + 1] := IntToStr(TMap(DTA.maps.Objects[i]).oieCount);
        MapsStringGrid.Cells[8, i + 1] := '$' + IntToHex(TMap(DTA.maps.Objects[i]).izaxOffset, 6);
        MapsStringGrid.Cells[9, i + 1] := IntToStr(TMap(DTA.maps.Objects[i]).izaxSize);
        MapsStringGrid.Cells[10, i + 1] := '$' + IntToHex(TMap(DTA.maps.Objects[i]).izx2Offset, 6);
        MapsStringGrid.Cells[11, i + 1] := IntToStr(TMap(DTA.maps.Objects[i]).izx2Size);
        MapsStringGrid.Cells[12, i + 1] := '$' + IntToHex(TMap(DTA.maps.Objects[i]).izx3Offset, 6);
        MapsStringGrid.Cells[13, i + 1] := IntToStr(TMap(DTA.maps.Objects[i]).izx3Size);
        MapsStringGrid.Cells[14, i + 1] := '$' + IntToHex(TMap(DTA.maps.Objects[i]).izx4Offset, 6);
        MapsStringGrid.Cells[15, i + 1] := IntToStr(TMap(DTA.maps.Objects[i]).izx4Size);
        MapsStringGrid.Cells[16, i + 1] := '$' + IntToHex(TMap(DTA.maps.Objects[i]).iactOffset, 6);
        MapsStringGrid.Cells[17, i + 1] := IntToStr(TMap(DTA.maps.Objects[i]).iactSize);
        end;

        MapsListStringGrid.RowCount := DTA.mapsCount;
        for i := 0 to DTA.mapsCount - 1 do
            begin
        MapsListStringGrid.Cells[0, i] := 'Map #' + IntToStr(i);
        end;
        MapsListStringGrid.Row := 0;
        ViewMap(0);

        TilesDrawGrid.RowCount := DTA.tilesCount div 16 + 1;
        TilesDrawGrid.Repaint;*/

        //PageControl.Visible := true;
    }

    //TODO error processing
    private void DrawTitleImage() {

        ReadPicture(section.GetDataOffset(KnownSections.STUP), Color.BLACK);
    }

    private void ReadPicture(int offset, Color transparentColor) {

        if (offset > 0) {
            section.SetPosition(offset);
        }

        WritableImage image = new WritableImage(288, 288);

        for (int y = 0; y < 288; y++) {
            for (int x = 0; x < 288; x++) {
                int index = section.ReadByte();
                Color color = (index == 0) ? transparentColor : Config.palette[index];
                image.getPixelWriter().setColor(x, y, color);
                /*titleScreenCanvas.getGraphicsContext2D().getPixelWriter().setArgb(x, y, palette[index]);
                image.getPixelWriter().setArgb(x, y, palette[index]);*/
            }
        }
        titleScreenImageView.setImage(image);
    }

    //TODO error processing
    private void DrawPalette() {

        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                paletteCanvas.getGraphicsContext2D().setFill(Config.palette[y * 16 + x]);
                paletteCanvas.getGraphicsContext2D().fillRect(x * 18, y * 18, 18, 18);
            }
        }
    }


    public void saveMenuItemClick() {
        //TODO
    }

    public void closeMenuItemClick() {

        section = null;
        JavaFxUtils.showPrimaryPanel();
    }

    public void exitMenuItemClick() {

        //TODO warning if modified
        Platform.exit();
    }

    public void transparentColorMenuItemClick() {
        currentFillColor = (Color) transparentColorToggleGroup.getSelectedToggle().getUserData();
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

    public void openExeFileButtonClick() {
        //TODO notify if changed
        //TODO clear if need
        openFile();
    }

    //TODO
    public void dumpAllSectionsButtonClick(ActionEvent actionEvent) {
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


    //TODO error processing
    public void saveToBitmapButtonClick() throws IOException {

        // TODO Log.Clear;
        // TODO IOUtils.createDirectories("");
        // TODO Log.Debug('Title screen saved')

        if (titleScreenImageView.getUserData() != null) {
            BMPEncoder.write((BMPImage) titleScreenImageView.getUserData(), new File("D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\STUPti.bmp"));
        } else {
            BMPEncoder.write8bit(titleScreenImageView, new File("D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\STUPi.bmp"));
        }
    }

    //TODO error processing
    public void loadFromBitmapButtonClick(ActionEvent actionEvent) throws IOException {

        //TODO LoadBMP('output/STUP.bmp',bmp);

        BMPImage titleImage = BMPDecoder.readExt(new File("D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\STUP.bmp"));

        //TODO notify if not indexed
        WritableImage image = new WritableImage(titleImage.getWidth(), titleImage.getHeight());
        SwingFXUtils.toFXImage(titleImage.getImage(), image);

        for (int y = 0; y < image.getWidth(); y++) {
            for (int x = 0; x < image.getHeight(); x++) {
                Color color = image.getPixelReader().getColor(x, y);
                //titleScreenCanvas.getGraphicsContext2D().getPixelWriter().setColor(x, y, color);
            }
        }
        titleScreenImageView.setImage(image);
        titleScreenImageView.setUserData(titleImage);
    }

    //TODO error processing
    public void savePaletteButtonButtonClick() throws IOException {
        BMPEncoder.write8bit(paletteCanvas, new File("D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\palette.bmp"));
    }

    //TODO error processing
    public void dumpPaletteButtonButtonClick() throws IOException {
        PaletteUtils.saveToFile(gamePalette, "D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\palette.pal");
    }

    //TODO error processing
    public void saveSoundsListToFileButtonClick() throws IOException {

        IOUtils.saveTextFile(section.sounds, Paths.get("D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\SNDSn.txt"));
    }

    public void saveTilesToSeparateFilesClick(ActionEvent actionEvent) {
        /*var tilesPath, hexPath, attrPath, attrFullPath, title: String;
        attr, i: Cardinal;
        begin
        TilesProgressBar.Position := 0;
        TilesProgressBar.Max := DTA.tilesCount;
        tilesPath := opath +'Tiles';
        hexPath := opath +'TilesHex';
        attrPath := opath +'TilesByAttr';
        Log.Clear;
        CreateDir(opath);
        if DecimalCheckBox.Checked then CreateDir(tilesPath);
        if HexCheckBox.Checked then CreateDir(hexPath);
        if AttrCheckBox.Checked then CreateDir(attrPath);

        FillInternalPalette(BMP, currentFillColor);
        BMP.Width := TileSize;
        BMP.Height := TileSize;
        title := knownSections[4]; // TILE
        DTA.SetPosition(title);
        for i := 0 to DTA.tilesCount - 1 do
            begin
        attr := DTA.ReadLongWord; // attributes
        ReadPicture(DTA, 0);
        CopyPicture(TileImage, 0, 0);
        Application.ProcessMessages;
        //  SaveBMP('output/Tiles/'+rightstr('000'+inttostr(i),4)+' - '+inttohex(k,6)+'.bmp',bmp);
        if DecimalCheckBox.Checked then
        SaveBMP(tilesPath + '\' + rightstr('000' + inttostr(i) ,4) + eBMP, bmp);
        if AttrCheckBox.Checked then
        begin
        attrFullPath := attrPath + '\' + IntToBin(attr) + ' (' + IntToStr(attr) + ')';
        CreateDir(attrFullPath);
        SaveBMP(attrFullPath + '\' + rightstr('000' + inttostr(i) ,4) + eBMP, bmp);
        end;
        if HexCheckBox.Checked then
        SaveBMP(hexPath + '\' + inttohex(i,4) + eBMP, bmp);
                TilesProgressBar.Position := i;
        TilesProgressLabel.Caption := Format('%.2f %%', [((i + 1) / DTA.tilesCount) * 100]);
        Application.ProcessMessages;
        end;
        Log.Debug('OK');
        end;*/
    }

    public void saveTilesToOneFileClick(ActionEvent actionEvent) {
    }

    public void loadClipboardImageClick(ActionEvent actionEvent) {
    }

    public void saveClipboardImageClick(ActionEvent actionEvent) {
    }

    public void clearClipboardImageClick(ActionEvent actionEvent) {
    }

    public void saveMapsToFilesButtonClick(ActionEvent actionEvent) {
        /*var i: Word;
        begin
        CreateDir(opath);
        if MapSaveCheckBox.Checked then CreateDir(opath + 'Maps');
        if MapFlagSaveCheckBox.Checked then CreateDir(opath + 'MapsByFlags');
        if MapPlanetSaveCheckBox.Checked then CreateDir(opath + 'MapsByPlanetType');
        if ActionsCheckBox.Checked then
        begin
        CreateDir(opath + 'IZAX');
        CreateDir(opath + 'IZX2');
        CreateDir(opath + 'IZX3');
        CreateDir(opath + 'IZX4');
        CreateDir(opath + 'IACT');
        CreateDir(opath + 'OIE');
        end;

        bmp.PixelFormat := pf8bit;
        bmp.Width := TileSize;
        bmp.Height := TileSize;
        FillInternalPalette(BMP, $FE00FE);

        MapProgressBar.Position := 0;
        MapProgressBar.Max := DTA.mapsCount;

        if CheckBox2.Checked then texts.Clear;

        Log.Clear;
        Log.Debug('Maps (zones):');
        Log.NewLine;
        Log.Debug('Total count: ' + IntToStr(DTA.mapsCount));
        Log.NewLine;
        //DTA.SetIndex(knownSections[5]);          // ZONE
        for i:=0 to DTA.mapsCount - 1 do ReadIZON(i, true);

        if CheckBox1.Checked then
        begin
        Log.NewLine;
        Log.Debug('Unused tiles:');
        Log.NewLine;
        CreateDir(opath + 'TilesUnused');
        for i:=0 to DTA.tilesCount -1 do
            if not DTA.tiles[i] then
                begin
        Log.Debug(inttostr(i));
        GetTile(dta, i, bmp);
        SaveBMP(opath + 'TilesUnused\' + rightstr('000' + inttostr(i) ,4) + eBMP, bmp);
        end;
        end;

        if CheckBox2.Checked then texts.SaveToFile(opath + 'iact.txt');
        end;*/
    }


    /*
        function IntToBin(Value: LongWord): string;
        var i: Integer;
        begin
        SetLength(Result, 32);
        for i := 1 to 32 do begin
        if ((Value shl (i-1)) shr 31) = 0 then begin
        Result[i] := '0'
        end else begin
        Result[i] := '1';
        end;
        end;
        end;*/


    /*procedure TMainForm.ReadMap(id: Word; show, save: Boolean);
    var s: String;
    k, w, h, i, j, flag, planet: Word;
    begin
  DTA.SetPosition(TMap(DTA.maps.Objects[id]).mapOffset);   // go to map data
    pn := DTA.ReadWord;               // number:word; //2 bytes - serial number of the map starting with 0
  if pn <> id then ShowMessage(IntToStr(pn) + ' <> ' + IntToStr(id));
  DTA.ReadString(4);                // izon:string[4]; //4 bytes: "IZON"
    DTA.ReadLongWord;                 // longword; //4 bytes - size of block IZON (include 'IZON') until object info entry count

    Application.ProcessMessages;

    w := DTA.ReadWord;                // width:word; //2 bytes: map width (W)
    h := DTA.ReadWord;                // height:word; //2 bytes: map height (H)
    flag := DTA.ReadWord;             // flags:word; //2 byte: map flags (unknown meanings)* добавил байт снизу
    DTA.ReadLongWord;                 // unused:longword; //5 bytes: unused (same values for every map)
    planet := DTA.ReadWord;           // planet:word; //1 byte: planet (0x01 = desert, 0x02 = snow, 0x03 = forest, 0x05 = swamp)* добавил следующий байт
  Log.Debug('Map #' + inttostr(pn) + ' offset: ' + inttohex(DTA.GetPosition, 8));
    StatusBar.Panels[0].Text := 'Map: ' + IntToStr(pn) + ' (' + planets[planet] +')';

  if show then
    begin
    MapImage.Width := w * 32;
    MapImage.Height := h * 32;
    MapImage.Picture.Bitmap.Width := w * 32;
    MapImage.Picture.Bitmap.Height := h * 32;

    MapImage.Picture.bitmap.Canvas.Pen.Color := $010101;
    MapImage.Picture.bitmap.Canvas.Brush.Color := $010101;
    MapImage.picture.Bitmap.canvas.Rectangle(0, 0, MapImage.picture.bitmap.width, MapImage.picture.bitmap.height);
    MapImage.Canvas.Brush.Style := bsClear;

    for i:=0 to h-1 do
            for j:=0 to w-1 do
    begin          //W*H*6 bytes: map data
    k := DTA.ReadWord;
        if k <> $FFFF then
            begin
    DTA.tiles[k] := true;
    GetTile(dta, k, bmp);
    CopyFrame(MapImage.Canvas, j * 32, i * 32);
    end;
    k := DTA.ReadWord;
        if k <> $FFFF then
            begin
    DTA.tiles[k] := true;
    GetTile(dta, k, bmp);
    CopyFrame(MapImage.Canvas, j * 32, i * 32);
    end;
    k := DTA.ReadWord;
        if k <> $FFFF then
            begin
    DTA.tiles[k] := true;
    GetTile(dta, k, bmp);
    CopyFrame(MapImage.Canvas, j * 32, i * 32);
    end;
    end;
    Application.ProcessMessages;

      if MapSaveCheckBox.Checked and save then
        MapImage.Picture.SaveToFile(opath + 'Maps\' + rightstr('000' + inttostr(id), 3) + '.bmp');

            if MapFlagSaveCheckBox.Checked and save then
    begin
    s := opath + 'MapsByFlags\' + IntToBin(flag);
    CreateDir(s);
        MapImage.Picture.SaveToFile(s + '\' + rightstr('000' + inttostr(id), 3) + '.bmp');
    end;

      if MapPlanetSaveCheckBox.Checked and save then
    begin
    s := opath + 'MapsByPlanetType\' + planets[planet];
    CreateDir(s);
        MapImage.Picture.SaveToFile(s + '\' + rightstr('000' + inttostr(id), 3) + '.bmp');
    end;
    end;
    end;

    procedure TMainForm.ReadIZON(id: Word; save: Boolean);
    begin
    ReadMap(id, MapSaveCheckBox.Checked or MapFlagSaveCheckBox.Checked or MapPlanetSaveCheckBox.Checked, save);

    MapProgressBar.Position := id;
    MapProgressLabel.Caption := Format('%.2f %%', [((id + 1)/ DTA.mapsCount) * 100]);
    Application.ProcessMessages;

    //k:=DTA.ReadWord;                             //2 bytes: object info entry count (X)
    //DTA.MoveIndex(k * 12);                       //X*12 bytes: object info data

  if ActionsCheckBox.Checked or CheckBox2.Checked then
    begin
        //ReadOIE(id);
    ReadIZAX(id);
    ReadIZX2(id);
    ReadIZX3(id);
    ReadIZX4(id);
    ReadIACT(id);
    ReadOIE(id);
    end;
    end;*/

    /*procedure TMainForm.DumpData(fileName: String; offset, size: Cardinal);
    var f: File of Byte;
    i: Cardinal;
    b: Byte;
    begin
        DTA.SetPosition(offset);
    AssignFile(f, fileName);
    Rewrite(f);
        if size > 0 then
        for i := 0 to size - 1 do
    begin
    b := DTA.ReadByte;
    Write(f, b);
    end;
    CloseFile(f);
    end;

    procedure TMainForm.ReadOIE(id: Word);
    begin
    DumpData(opath + 'OIE\' + IntToStr(id), TMap(DTA.maps.Objects[id]).oieOffset, TMap(DTA.maps.Objects[id]).oieSize);
            end;

            procedure TMainForm.ReadIZAX(id: Word);
    begin
    DumpData(opath + 'IZAX\' + IntToStr(id), TMap(DTA.maps.Objects[id]).izaxOffset, TMap(DTA.maps.Objects[id]).izaxSize);
            end;

            procedure TMainForm.ReadIZX2(id: Word);
    begin
    DumpData(opath + 'IZX2\' + IntToStr(id), TMap(DTA.maps.Objects[id]).izx2Offset, TMap(DTA.maps.Objects[id]).izx2Size);
            end;

            procedure TMainForm.ReadIZX3(id: Word);
    begin
    DumpData(opath + 'IZX3\' + IntToStr(id), TMap(DTA.maps.Objects[id]).izx3Offset, TMap(DTA.maps.Objects[id]).izx3Size);
            end;

            procedure TMainForm.ReadIZX4;
            begin
            DumpData(opath + 'IZX4\' + IntToStr(id), TMap(DTA.maps.Objects[id]).izx4Offset, TMap(DTA.maps.Objects[id]).izx4Size);
            end;

            procedure TMainForm.ReadIACT(id: Word);
    var size: Longword;
    k: Byte;
    begin
    k := 0;
        DTA.SetPosition(TMap(DTA.maps.Objects[id]).iactOffset);

        while DTA.ReadString(4) = 'IACT' do
    begin
//    HEX.SetSelStart(DTA.GetIndex);
//    HEX.SetSelEnd(DTA.GetIndex + 3);
//    HEX.CenterCursorPosition;
//    Application.ProcessMessages;
    size := DTA.ReadLongWord;  //4 bytes: length (X)
        if CheckBox2.Checked then DumpText(DTA.GetPosition, size);
        if ActionsCheckBox.Checked then
    DumpData(opath + 'IACT\' + rightstr('000' + inttostr(id), 3) + '-'+rightstr('000'+inttostr(k), 3), DTA.GetPosition, size)
            else DTA.MovePosition(size);
    inc(k);
    end;
    end;

    function idDeprecatedWords(text: String): Boolean;
const arr: Array[1..100] of String = (
            'el:', 'ckup L', 'MS S', 'ЂОЅ', 'n 2', 'Redra', 'Remov', 'Redr', 'plac', 'Sho',
            'Show', 'opI', 'ne ', 'Red', 'Set', 'wRandN', 'up L', 'Remove~', 'ng, ', 'opIt',
            'Redraw', 'ckup', 'Y: 12 ', 'Bump', 'Pick', 'Bum', 'Has', 'ђ0n', 'Pickup L', 'SetHer',
            'BumpTi', 'Repla', 'MS San', 'Remove,', 'd my j', 'WaitF', 'SetH', 'SetHe', 'RandNu', 'eIte',
            'n 1 an', 'њњћ', 'LчЅ', '6,12', '0, 1', 'Door', 'aySo', 'c X:', 'Game', 'Р©µ',
            'Remove', 'a l', 'ndNu', 'PlayS', 'Force ', ' Lev', 'ter:', 'ter:F', 'ЂЫ№', 'o Na',
            't•±', '4kЯ', 'w on o', 'ndN', 'HotS', 'ЂУґ', 'perial', '12 Y', 'Name', 'sta',
            'Wait', 'securi', '''s don', 'cku', 'ДFВ', 'rTim', 'Wai', 'шн±', 'Backgr', 'e''s',
            'MS ', ' ’µ', 'h th', 'n''t th', 'u ta', 's! What ', 'Pic', '¤K‡', 'Wait', 'Coun',
            '#####', 'ґъm', 'up ', 'Sav', 'яяяяяяяя', 'Rep', 'ёDґ', '!!!', 'ф$‚', 'Coun'
            );
    var i: byte;
    begin
    result := false;
        for i := 1 to Length(arr) do
            if arr[i] = text then result := true;
        if not result then result := AnsiStartsStr('ShowT', text);
        if not result then result := AnsiStartsStr('awArea', text);
        if not result then result := AnsiStartsStr('HasI', text);
        if not result then result := AnsiStartsStr('ZX', text);
        if not result then result := AnsiStartsStr(' X:', text);
        if not result then result := AnsiStartsStr(',', text);

        if text[1] = ',' then result := true;
        if text[1] = ':' then result := true;
    end;

    procedure TMainForm.DumpText(index: Cardinal; size: Word);
    var idx, tempIndex: Cardinal;
    sz, j: Word;
    phase, b: Byte;
    s: String;
    begin
    idx := index;
    //phase := 1;
        while DTA.GetPosition < idx + size - 2 do
    begin
    //Log.Debug('Start new scan: ' + IntToHex(DTA.GetIndex, 6));
    tempIndex := DTA.GetPosition;
    sz := DTA.ReadWord;
    //Log.Debug('Size: ' + IntToHex(sz, 4));
        if (sz < $0300) and (sz > $0002) then            // correct max size
    begin
    phase := 2;                 // size correct, maybe text?
    Application.ProcessMessages;
    s := '';
        if DTA.GetPosition + sz <= idx + size + 4 then
        for j := 1 to sz do
    begin
    b := DTA.ReadByte;
    s := s + inttohex(b,2) + ' ';
        if (b < $20) and (b <> $0D) and (b <> $0A) then phase := 1;
        if (b = ord('\')) or (b = ord('@')) or (b = ord('^')) or (b = ord('¶')) or (b = ord('<')) or (b = ord('»'))  or (b = ord('(')) or (b = ord(')')) then phase := 1;
    end
      else phase := 1;
        if phase = 2 then
            begin
    s := '';
        DTA.SetPosition(tempIndex + 2);
        for j := 1 to sz do
    begin
    b := DTA.ReadByte;
    s := s + chr(b);
    end;
    s := AnsiReplaceStr(s, chr($0D) + chr($0A), '[CR]');
    s := AnsiReplaceStr(s, '[CR][CR]', '[CR2]');
    s := AnsiReplaceStr(s, chr($A5), '_');
        if not idDeprecatedWords(s) then
    begin
        texts.Add(s);
    tempIndex := DTA.GetPosition - 1;
    end;
    //phase := 1;
    end;
    end;
        DTA.SetPosition(tempIndex + 1);
    end;

        DTA.SetPosition(idx);
    end;*/

    public void dumpTextToDocxCLick(ActionEvent actionEvent) {

    }

    public void loadTranslatedTextClick(ActionEvent actionEvent) {
    }

    public void replaceTextInDtaClick(ActionEvent actionEvent) {
    }

    public void savePuzzlesToFilesButtonClick(ActionEvent actionEvent) {
    }

    public void dumpPuzzlesTextToDocxCLick(ActionEvent actionEvent) {
    }

    public void loadPuzzlesTranslatedTextClick(ActionEvent actionEvent) {
    }

    public void replacePuzzlesTextInDtaClick(ActionEvent actionEvent) {
    }

    public void saveCharactersToFilesButtonClick(ActionEvent actionEvent) {
    }

    public void saveNamesToFilesButtonClick(ActionEvent actionEvent) {
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

    procedure TMainForm.TilesDrawGridDrawCell(Sender: TObject; ACol, ARow: Integer; Rect: TRect; State: TGridDrawState);
    var id: Word;
    begin
    id := ACol + ARow * 16;
  if ((ACol < TilesDrawGrid.ColCount) and (ARow < TilesDrawGrid.RowCount)) and (id < DTA.tilesCount) then
    begin
    bmp.PixelFormat := pf8bit;
    bmp.Width := TileSize;
    bmp.Height := TileSize;
    FillInternalPalette(BMP, currentFillColor);

    TilesDrawGrid.Canvas.Brush.Color := currentFillColor;
    TilesDrawGrid.Canvas.Brush.Style := bsSolid;
    GetTile(dta, id, bmp);
    CopyFrame(TilesDrawGrid.Canvas, Rect.Left, Rect.Top);
    end else
    begin
    TilesDrawGrid.Canvas.Brush.Color := clBtnFace;
    TilesDrawGrid.Canvas.FillRect(Rect);
    end;
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

    procedure TMainForm.Button3Click(Sender: TObject);
    begin
  if SaveClipboardDialog.Execute then ClipboardImage.Picture.SaveToFile(SaveClipboardDialog.FileName);
    end;

    procedure TMainForm.Button4Click(Sender: TObject);
    begin
    if OpenClipboardDialog.Execute then
    begin
      ClipboardImage.Picture.LoadFromFile(OpenClipboardDialog.FileName);
    ZeroColorRGDo(true);
    end;
    end;

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

    procedure TMainForm.Button6Click(Sender: TObject);
    begin
  if SaveDTADialog.Execute then DTA.SaveToFile(SaveDTADialog.FileName);
    end;

    procedure TMainForm.Button7Click(Sender: TObject);
    var p: PByteArray;
    i, j: Word;
    begin
  if OpenClipboardDialog.Execute then
    begin
    TitleImage.Picture.LoadFromFile(OpenClipboardDialog.FileName);
    // Update in memory
    for i := 0 to TitleImage.Height - 1 do
    begin
    p := TitleImage.Picture.Bitmap.ScanLine[i];
      for j := 0 to TitleImage.Width - 1 do
    begin
        DTA.SetPosition(DTA.GetDataOffset(knownSections[2]) + i * TitleImage.Width + j);
        DTA.WriteByte(p[j]);
    end;
    end;
    TilesDrawGrid.Repaint;
    end;
    end;

    procedure TMainForm.Button8Click(Sender: TObject);
    var w, h, i, j: Word;
    begin
    w := StrToInt(Edit1.text);
    h := DTA.tilesCount div w + 1;
    BMP2 := TBitmap.Create;
    BMP2.PixelFormat := pf8bit;
    FillInternalPalette(BMP2, currentFillColor);
    BMP2.Width := w * 32;
    BMP2.Height := h * 32;

  for i := 0 to h - 1 do
            for j := 0 to w - 1 do
            if i * w + j < DTA.tilesCount then
    begin
    GetTile(dta, i * w + j, bmp);
    DrawBMP(BMP2, j * 32, i * 32, BMP);
    end;

  BMP2.SaveToFile(opath + 'tiles' + inttostr(w) +'x' + inttostr(h) + '.bmp');
    end;

    procedure TMainForm.Button9Click(Sender: TObject);
    var i: Word;
    begin
    CreateDir(opath);
    CreateDir(opath + 'PUZ2');
    Log.Clear;
  Log.Debug('Puzzles (2):');
    Log.NewLine;
  Log.Debug('Total count: ' + IntToStr(DTA.puzzlesCount));
    Log.NewLine;
    texts.Clear;
  DTA.SetPosition(DTA.GetDataOffset(knownSections[6]));            // PUZ2
  for i:=0 to DTA.puzzlesCount - 1 do ReadPUZ2;
  texts.SaveToFile(opath + 'puz2.txt');
    end;


    procedure TMainForm.ReadPUZ2;
    var pz: Word;
    psz: LongWord;
    begin
    pz := DTA.ReadWord;             //2 bytes - index of puzzle (from 0)
  DTA.ReadString(4);              //4 bytes - 'IPUZ'
    psz := DTA.ReadLongWord;        //4 bytes - rest of current puzzle length
    DumpText(DTA.GetPosition, psz);
  Log.Debug('Puzzle #' + IntToStr(pz) + '; Size: $' + IntToHex(psz, 4));
    DumpData(opath + 'PUZ2\' + rightstr('000' + inttostr(pz), 4), DTA.GetPosition, psz);
            end;


            procedure TMainForm.Button10Click(Sender: TObject);
    var i: Word;
    begin
    CreateDir(opath);
    CreateDir(opath + 'CHAR');
    CreateDir(opath + 'Characters');
    CreateDir(opath + 'CAUX');
    CreateDir(opath + 'CHWP');
    Log.Clear;
  Log.Debug('Characters:');
    Log.NewLine;
  Log.Debug('Total count: ' + IntToStr(DTA.charsCount));
    Log.NewLine;
    texts.Clear;
  DTA.SetPosition(DTA.GetDataOffset(knownSections[7]));            // CHAR
  for i:=0 to DTA.charsCount - 1 do ReadCHAR;
  DTA.SetPosition(DTA.GetDataOffset(knownSections[8]));            // CHWP
  for i:=0 to DTA.charsCount - 1 do ReadCHWP;
  DTA.SetPosition(DTA.GetDataOffset(knownSections[9]));            // CAUX
    //incorrect CAUX offset!!!!!!!!!!!!!!
    //Showmessage(inttohex(DTA.GetIndex,6));
  for i:=0 to DTA.charsCount - 1 do ReadCAUX;
  texts.SaveToFile(opath + 'chars.txt');
    end;


    procedure TMainForm.ReadCHAR;
    var csz, idx: Longword;
    ch, tl: Word;
    name, seq: String;
    k, n: Byte;
    begin
    ch := DTA.ReadWord;             //2 bytes - index of character
  DTA.ReadString(4);              //4 bytes - 'ICHA'
    csz := DTA.ReadLongWord;        //4 bytes - rest of current character length; always 74
    idx := DTA.GetPosition;
    // тут сделать обработку текста, спрайтиков
    name := '';
    k := DTA.ReadByte;
  while k <> 0 do
    begin
    name := name + chr(k);              // Character name, ended with $00 <= 16
    k := DTA.ReadByte;
    end;
    seq := '';
  if Length(name) mod 2 = 0 then seq := seq + IntToHex(DTA.ReadByte, 2) + ' ';
    k := DTA.ReadByte;
    n := DTA.ReadByte;
  while (k <> $FF) and (n <> $FF) do    // unknown data 2 bytes * x, ended with $FF FF
    begin
    seq := seq + IntToHex(k, 2) + ' ' + IntToHex(n, 2) + ' ';
    k := DTA.ReadByte;
    n := DTA.ReadByte;
    end;
    DTA.ReadLongWord;                     // 4 bytes 00 00 00 00
  while DTA.GetPosition < idx + csz do
    begin
    tl := DTA.ReadWord;                 // REST - sequence of tiles # (2 bytes), or $FF FF
    if tl <> $FFFF then
            begin
    CreateDir(opath + 'Characters\' + name);
            GetTile(dta, tl, bmp);
      bmp.SaveToFile(opath + 'Characters\' + name + '\' + rightstr('000' + inttostr(tl), 4) + eBMP);
    end;
    end;
    //ReadString(csz);
  Log.Debug(seq + '      : ' + name);
  DTA.SetPosition(idx);
    DumpData(opath + 'CHAR\' + rightstr('00' + inttostr(ch), 3), DTA.GetPosition, csz);
            end;

            procedure TMainForm.ReadCHWP;
            var //size:longword;
            ch: Word;
            begin
//  size := DTA.ReadLongWord;
            ch := DTA.ReadWord;
            DumpData(opath + 'CHWP\' + rightstr('00' + inttostr(ch), 3), DTA.GetPosition, 4);
            end;

            procedure TMainForm.ReadCAUX;
            var //size:longword;
            ch: Word;
            begin
//  size:=DTA.ReadLongWord;
            ch := DTA.ReadWord;
            DumpData(opath + 'CAUX\' + rightstr('00' + inttostr(ch), 3), DTA.GetPosition, 2);
            end;

            function GetFileName(name: String): String;
    var s: String;
    b: Byte;
    begin
  if not FileExists(opath + 'Names\' + name  + eBMP) then  result := ''
            else begin
            b := 2;
            while FileExists(opath + 'Names\' + name + ' (' + inttostr(b) + ')' + eBMP) do inc(b);
    s := ' (' + inttostr(b) + ')';
    end;
    result := opath + 'Names\' + name + s + eBMP;
    end;


    procedure TMainForm.ReadTNAM;
    var k: Word;
    s: String;
    begin
    Log.Clear;
  Log.Debug('Names:');
    Log.NewLine;
  Log.Debug('Total count: ' + IntToStr(DTA.namesCount));
    Log.NewLine;
    texts.Clear;
  DTA.SetPosition(DTA.GetDataOffset(knownSections[10]));            // TNAM
    BMP.Width := TileSize;
    BMP.Height := TileSize;
    CreateDir(opath);
    CreateDir(opath + 'Names');
    repeat
    k := DTA.ReadWord; //2 байта - номер персонажа (тайла)
    if k = $FFFF then break;
    s := DTA.ReadString(24); //24 байта - длина до конца текущего имени
    s := leftstr(s, pos(chr(0), s) - 1);
    texts.Add(s);
    GetTile(dta, k, bmp);
    bmp.SaveToFile(GetFileName(s));
//        bmp.SaveToFile(opath + 'Names\' + s + eBMP);
    until false;
  texts.SaveToFile(opath + 'names.txt');
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


    procedure TMainForm.Button11Click(Sender: TObject);
    begin
            ReadTNAM;
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

    function TMainForm.GetFlagDescription(flag: Cardinal): String;
    var i: Word;
    m: TMenuItem;
    begin
    result := '';
  for i := 0 to ComponentCount - 1 do
            if Components[i] is TMenuItem then
            begin
    m := Components[i] as TMenuItem;
      if m.GroupIndex = 7 then
            begin
        if m.Tag = flag then
    begin
    result := AnsiReplaceStr(m.Caption, '&', '');
    Break;
    end;
        if (flag = 2147483680) and (m.Tag = 2000000000) then
    begin
    result := AnsiReplaceStr(m.Caption, '&', '');
    Break;
    end;
    end;
    end;
    end;

    procedure TMainForm.SetFlagMenuItem(flag: Cardinal);
    var i: Word;
    m: TMenuItem;
    begin
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
    end;
    end;

    procedure TMainForm.ShowTileStatus;
    var flag: Cardinal;
    begin
    selectedCell := TilesDrawGrid.Col + TilesDrawGrid.Row * 16;
    flag := DTA.GetTileFlag(selectedCell);
    SetFlagMenuItem(flag);
    StatusBar.Panels[0].Text := 'Tile #' + IntToStr(selectedCell) + ': ' + GetFlagDescription(flag);
    end;

    procedure TMainForm.TilesDrawGridMouseWheelDown(Sender: TObject; Shift: TShiftState; MousePos: TPoint; var Handled: Boolean);
    begin
            ShowTileStatus;
    Handled:= false;
    end;

    procedure TMainForm.TilesDrawGridKeyUp(Sender: TObject; var Key: Word; Shift: TShiftState);
    begin
            ShowTileStatus;
    end;

    procedure TMainForm.TilesDrawGridMouseWheelUp(Sender: TObject; Shift: TShiftState; MousePos: TPoint; var Handled: Boolean);
    begin
            ShowTileStatus;
    end;

    procedure TMainForm.TilesDrawGridSelectCell(Sender: TObject; ACol, ARow: Integer; var CanSelect: Boolean);
    begin
            //break;
            end;

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

    procedure TMainForm.Button1Click(Sender: TObject);
    begin
  if Opendialog1.Execute then ReadColumn(1, StringGrid1);
    end;

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

    procedure TMainForm.Button16Click(Sender: TObject);
    begin
  if Opendialog1.Execute then ReadColumn(1, StringGrid4);
    end;

    procedure TMainForm.Button17Click(Sender: TObject);
    begin
  if Opendialog1.Execute then ReadColumn(2, StringGrid4);
    end;



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

    procedure TMainForm.Button20Click(Sender: TObject);
    begin
  if Opendialog1.Execute then ReadColumn(1, StringGrid2);
    end;

    procedure TMainForm.Button21Click(Sender: TObject);
    begin
  if Opendialog1.Execute then ReadColumn(2, StringGrid2);
    end;

    procedure TMainForm.Button22Click(Sender: TObject);
    var i: Word;
    s, msg: String;
    len: Byte;
    begin
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
    h := DTA.ReadWord;                // height:word; //2 bytes: map height (H)
  DTA.ReadString(8);
  DTA.MovePosition((top * w + left) * 6);
  if concrete then DTA.MovePosition(RadioGroup1.ItemIndex * 2);
    end;


    procedure TMainForm.MapImageDragDrop(Sender, Source: TObject; X, Y: Integer);
    begin
    prevTileX := x div 32;
    prevTileY := y div 32;
    SelectMapTile(x, y, true);
    prevTile := DTA.ReadWord;
  DTA.MovePosition(-2);
  DTA.WriteWord(selectedCell);
    ViewMap(MapsListStringGrid.Row);
    end;

    procedure TMainForm.MapImageMouseUp(Sender: TObject; Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
    var pnt: TPoint;
    t1, t2, t3: Word;
    begin
    SelectMapTile(x, y, false);
    t1 := DTA.ReadWord;
    t2 := DTA.ReadWord;
    t3 := DTA.ReadWord;

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
    SelectMapTile(prevTileX, prevTileY, true);
    DTA.WriteWord(prevTile);
    prevTile := -1;
    ViewMap(MapsListStringGrid.Row);
    end;
    end;

    procedure TMainForm.Empty1Click(Sender: TObject);
    var
    pt : tPoint;
    begin
    pt := ScreenToClient(Mouse.CursorPos);
    prevTileX := pt.x div 32;
    prevTileY := pt.y div 32;
    SelectMapTile(prevTileX, prevTileY, true);
    prevTile := DTA.ReadWord;
  DTA.MovePosition(-2);
  DTA.WriteWord($FFFF);
    ViewMap(MapsListStringGrid.Row);
    end;*/


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
}
