package md.leonis.ystt.utils;

import md.leonis.ystt.model.ImageRecord;
import md.leonis.ystt.model.PuzzleRecord;
import md.leonis.ystt.model.StringRecord;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class WordUtils {

    public static void saveZones(List<PuzzleRecord> records, Path path) throws IOException, InvalidFormatException {

        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        run.setFontSize(18);
        run.setBold(true);
        run.setText("Zone actions");
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        List<List<Object>> matrix = records.stream().map(r -> {
            List<Object> objects = new ArrayList<>(new ArrayList<>(Collections.singletonList(r.getId())));
            if (!r.getType().isEmpty()) {
                objects.add("\n");
                objects.add(r.getType());
            }

            List<String> lines = Arrays.stream(r.getOriginal().replace("\r\n", "\t\r\n\t").split("\t")).collect(Collectors.toList());

            return Arrays.asList(objects, lines, "");
        }).collect(Collectors.toList());

        createTable(document, Arrays.asList("Info", "Original", "Translated"), matrix);

        FileOutputStream out = new FileOutputStream(path.toFile());
        document.write(out);
        out.close();
        System.out.println(path.toString() + " written successfully");
    }

    public static void savePuzzles(List<PuzzleRecord> records, Path path) throws IOException, InvalidFormatException {

        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        run.setFontSize(18);
        run.setBold(true);
        run.setText("Puzzles");
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        List<List<Object>> matrix = records.stream().map(r -> {
            List<Object> objects = new ArrayList<>(new ArrayList<>(Collections.singletonList(r.getId())));
            if (!r.getImages().isEmpty()) {
                objects.add("\n");
                objects.add(r.getImages().get(0));
            }

            return Arrays.asList(objects, r.getOriginal(), "");
        }).collect(Collectors.toList());

        createTable(document, Arrays.asList("Info", "Original", "Translated"), matrix);

        FileOutputStream out = new FileOutputStream(path.toFile());
        document.write(out);
        out.close();
        System.out.println(path.toString() + " written successfully");
    }

    public static void saveCharacters(List<ImageRecord> records, Path path) throws IOException, InvalidFormatException {

        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        run.setFontSize(18);
        run.setBold(true);
        run.setText("Characters");
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        List<List<Object>> matrix = records.stream().map(r -> Arrays.asList(r.getOriginal(), r.getImages())).collect(Collectors.toList());

        createTable(document, Arrays.asList("Name", "Sprites"), matrix);

        FileOutputStream out = new FileOutputStream(path.toFile());
        document.write(out);
        out.close();
        System.out.println(path.toString() + " written successully");
    }

    public static void saveNames(List<ImageRecord> records, Path path) throws IOException, InvalidFormatException {

        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        run.setFontSize(18);
        run.setBold(true);
        run.setText("Tile names");
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        List<List<Object>> matrix = records.stream().map(r -> Arrays.asList(r.getImages(), r.getOriginal(), r.getTranslation())).collect(Collectors.toList());

        createTable(document, Arrays.asList("Tiles", "Original text", "Translated text"), matrix);

        FileOutputStream out = new FileOutputStream(path.toFile());
        document.write(out);
        out.close();
        System.out.println(path.toString() + " written successully");
    }

    //TODO add release metrics, comments
    public static void saveRecords(String title, List<StringRecord> records, Path path) throws IOException {

        XWPFDocument document = new XWPFDocument();

        document.createParagraph().createRun().setText(title);

        XWPFTable table = document.createTable();

        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("ID");
        header.getCell(0).setWidth("20%");
        header.addNewTableCell().setText("Original text");
        header.addNewTableCell().setText("Translated text");

        records.forEach(r -> {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(r.getId());
            row.addNewTableCell().setText(r.getOriginal());
            row.addNewTableCell().setText(r.getTranslation());
        });

        // Customize cell
        /*int twipsPerInch = 1440;
        tableRowTwo.setHeight((int) (twipsPerInch * 1 / 10)); //set height 1/10 inch.
        tableRowTwo.getCtRow().getTrPr().getTrHeightArray(0).setHRule(STHeightRule.AT_LEAST); //set w:hRule="exact"

        //create third row
        XWPFTableRow tableRowThree = table.createRow();
        tableRowThree.getCell(0).setText("col one, row three");
        tableRowThree.getCell(1).setText("col two, row three");
        tableRowThree.getCell(2).setText("col three, row three");

        twipsPerInch = 1440;
        tableRowThree.setHeight(twipsPerInch * 1); //set height 1 inch.

        XWPFTableRow rowOne = table.getRow(0);
        rowOne.getCell(0).removeParagraph(0);
        XWPFParagraph paragraph = rowOne.getCell(0).addParagraph();
        setRun(paragraph.createRun(), "Calibre Light", 10, "2b5079", "Some string", false, false);*/

        /*XWPFRun run = table.getRow(1).addNewTableCell().addParagraph().createRun();
        run.setBold(true);run.setText("Your Text");*/


        // Add image
        /*XWPFParagraph title = document.createParagraph();
        XWPFRun run = title.createRun();
        run.setText("Fig.1 A Natural Scene");
        run.setBold(true);
        title.setAlignment(ParagraphAlignment.CENTER);

        String imgFile = "0354.bmp";
        FileInputStream is = new FileInputStream(imgFile);
        run.addBreak();
        //run.addPicture(is, XWPFDocument.PICTURE_TYPE_DIB, imgFile, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
        run.addPicture(is, XWPFDocument.PICTURE_TYPE_DIB, imgFile, Units.toEMU(32), Units.toEMU(32)); // 200x200 pixels
        is.close();*/

//auto-size picture relative to its top-left corner
        //pict.resize();


        FileOutputStream out = new FileOutputStream(path.toFile());
        document.write(out);
        out.close();
        System.out.println(path.toString() + " written successully");
    }

    private static void createTable(XWPFDocument document, List<String> titles, List<List<Object>> objectsMatrix) throws IOException, InvalidFormatException {

        XWPFTable table = document.createTable();

        XWPFTableRow header = table.getRow(0);
        header.removeCell(0);
        titles.forEach(title -> header.addNewTableCell().setText(title));

        for (List<Object> objectsList : objectsMatrix) {
            fillRow(table.createRow(), objectsList);
        }
    }

    @SuppressWarnings("all")
    private static void fillRow(XWPFTableRow row, List<Object> objectsList) throws IOException, InvalidFormatException {

        for (int i = 0; i < objectsList.size(); i++) {
            Object o = objectsList.get(i);
            if (o instanceof List) {
                XWPFRun run = row.getCell(i).addParagraph().createRun();
                for (Object e : ((List) o)) {
                    if (e instanceof BufferedImage) {
                        addImage(run, Collections.singletonList((BufferedImage) e));
                    } else {
                        String s = (String) e;
                        if (s.equals("\n") || s.equals("\r\n")) {
                            run.addBreak();
                        } else {
                            run.setText((String) e);
                        }
                    }
                }
            } else {
                XWPFTableCell cell = (row.getCell(i) == null) ? row.createCell() : row.getCell(i);
                cell.setText((String) o);
            }
        }
    }

    private static void imagesParagraph(XWPFDocument document, List<BufferedImage> images) throws IOException, InvalidFormatException {

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        for (BufferedImage image : images) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "bmp", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            run.addPicture(is, XWPFDocument.PICTURE_TYPE_DIB, "", Units.toEMU(24), Units.toEMU(24));
            is.close();
        }
    }

    private static void addImage(XWPFParagraph paragraph, List<BufferedImage> images) throws IOException, InvalidFormatException {
        addImage(paragraph.createRun(), images);
    }

    private static void addImage(XWPFRun run, List<BufferedImage> images) throws IOException, InvalidFormatException {

        for (BufferedImage image : images) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "bmp", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            run.addPicture(is, XWPFDocument.PICTURE_TYPE_DIB, "", Units.toEMU(24), Units.toEMU(24));
            is.close();
        }
    }

    private static void setRun(XWPFRun run, String fontFamily, int fontSize, String colorRGB, String text, boolean bold, boolean addBreak) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setColor(colorRGB);
        run.setText(text);
        run.setBold(bold);
        if (addBreak) run.addBreak();
    }

    public static List<StringRecord> loadRecords(Path path) throws IOException {

        List<StringRecord> records = new ArrayList<>();

        FileInputStream fis = new FileInputStream(path.toFile());
        XWPFDocument doc = new XWPFDocument(fis);
        XWPFTable table = doc.getTables().get(0);

        for (int i = 1; i < table.getRows().size(); i++) {
            XWPFTableRow row = table.getRows().get(i);
            records.add(new StringRecord(row.getCell(0).getText(), row.getCell(1).getText(), row.getCell(2).getText()));
        }

        /*
        List<XWPFTable> tables = doc.getTables();

        for (XWPFTable tbl : tables) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    System.out.println(cell.getText());
                    String sFieldValue = cell.getText();
                    if (sFieldValue.matches("Whatever you want to match with the string") || sFieldValue.matches("col two, row three")) {
                        System.out.println("The match as per the Document is True");
                    }
                }
                System.out.println(" ");
            }
        }*/

        return records;
    }
}
