package md.leonis.ystt.utils;

import md.leonis.ystt.model.docx.PropertyName;
import md.leonis.ystt.model.docx.StringRecord;
import md.leonis.ystt.model.docx.WordRecord;
import md.leonis.ystt.model.yodesk.Yodesk;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class WordUtils {

    public static void saveZones(String crc32, Path path) throws IOException, InvalidFormatException {

        XWPFDocument document = new XWPFDocument();
        addHeader(document, "Zone actions", crc32);

        List<List<Object>> matrix = WordHelper.getActionsTexts().stream().map(r -> {
            List<Object> objects = new ArrayList<>(new ArrayList<>(Collections.singletonList(r.getId())));
            /*if (!r.getType().isEmpty()) {
                objects.add("\n");
                objects.add(r.getType());
            }*/

            List<String> lines = Arrays.stream(r.getOriginal().replace("\r\n", "\t\r\n\t").split("\t")).collect(Collectors.toList());

            return Arrays.asList(objects, lines, r.getTranslation());
        }).collect(Collectors.toList());

        createTable(document, Arrays.asList("Info", "Original", "Translated"), matrix);

        FileOutputStream out = new FileOutputStream(path.toFile());
        document.write(out);
        out.close();
        System.out.println(path.toString() + " written successfully");
    }

    public static void savePuzzles(String crc32, Path path) throws IOException, InvalidFormatException {

        XWPFDocument document = new XWPFDocument();
        addHeader(document, "Puzzles", crc32);

        List<List<Object>> matrix = WordHelper.getPuzzlesTexts().stream().map(r -> {
            List<Object> objects = new ArrayList<>(new ArrayList<>(Collections.singletonList(r.getId())));
            if (!r.getImages().isEmpty()) {
                objects.add("\n");
                objects.add(r.getImages().get(0));
            }

            List<String> lines = Arrays.stream(r.getOriginal().replace("\r\n", "\t\r\n\t").split("\t")).collect(Collectors.toList());

            return Arrays.asList(objects, lines, r.getTranslation());
        }).collect(Collectors.toList());

        createTable(document, Arrays.asList("Info", "Original", "Translated"), matrix);

        FileOutputStream out = new FileOutputStream(path.toFile());
        document.write(out);
        out.close();
        System.out.println(path.toString() + " written successfully");
    }

    public static void saveNames(String crc32, Path path) throws IOException, InvalidFormatException {

        XWPFDocument document = new XWPFDocument();
        addHeader(document, "Tile names", crc32);

        List<List<Object>> matrix = WordHelper.getNamesTexts().stream().map(r -> Arrays.asList(r.getId(), r.getImages(), r.getOriginal(), r.getTranslation())).collect(Collectors.toList());

        createTable(document, Arrays.asList("ID", "Tile", "Original text", "Translated text"), matrix);

        FileOutputStream out = new FileOutputStream(path.toFile());
        document.write(out);
        out.close();
        System.out.println(path.toString() + " written successully");
    }

    public static void saveCharacters(String title, String crc32, Path path) throws IOException, InvalidFormatException {

        XWPFDocument document = new XWPFDocument();
        addNoTranslationHeader(document, title, crc32);

        List<List<Object>> matrix = WordHelper.getCharactersImages().stream()
                .map(r -> Arrays.asList(Collections.singletonList(r.getId()), r.getImages(), r.getOriginal())).collect(Collectors.toList());

        createTable(document, Arrays.asList("ID", "Tiles", "Name"), matrix);

        FileOutputStream out = new FileOutputStream(path.toFile());
        document.write(out);
        out.close();
        System.out.println(path.toString() + " written successully");
    }

    private static void addNoTranslationHeader(XWPFDocument document, String title, String crc32) {

        addTitle(document, title);
        addBulletsList(document, Collections.singletonList(PropertyName.CRC32.getTextHeader() + crc32));
        addItalicParagraph(document, "//TODO not need to translate"); // TODO
    }

    private static void addHeader(XWPFDocument document, String title, String crc32) {

        addTitle(document, title);
        addBullets(document, crc32);
        addItalicParagraph(document, "//TODO about available charsets"); // TODO
    }

    private static void addTitle(XWPFDocument document, String title) {

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        run.setFontSize(18);
        run.setBold(true);
        run.setText(title);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
    }

    private static void addItalicParagraph(XWPFDocument document, String text) {

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        run.setItalic(true);
        run.setText(text);
    }

    private static void addBullets(XWPFDocument document, String crc32) {

        List<String> items = Arrays.asList(
                PropertyName.CRC32.getTextHeader() + crc32,
                PropertyName.SRC_CHARSET.getTextHeader() + Yodesk.getInputCharset(), // Cp1252
                PropertyName.DST_CHARSET.getTextHeader() + Yodesk.getOutputCharset() // Cp1251
        );

        addBulletsList(document, items);
    }

    private static void addBulletsList(XWPFDocument document, List<String> items) {

        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        //Next we set the AbstractNumId. This requires care.
        //Since we are in a new document we can start numbering from 0.
        //But if we have an existing document, we must determine the next free number first.
        cTAbstractNum.setAbstractNumId(BigInteger.valueOf(0));

        // Bullet list
        CTLvl cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.addNewNumFmt().setVal(STNumberFormat.BULLET);
        cTLvl.addNewLvlText().setVal("•");

        // Decimal list
        /*CTLvl cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl.addNewLvlText().setVal("%1.");
        cTLvl.addNewStart().setVal(BigInteger.valueOf(1));*/

        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);

        XWPFNumbering numbering = document.createNumbering();

        BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);

        BigInteger numID = numbering.addNum(abstractNumID);

        for (String string : items) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setNumID(numID);
            XWPFRun run = paragraph.createRun();
            run.setText(string);
        }
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

    public static WordRecord loadRecords(Path path) throws IOException {

        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            XWPFDocument doc = new XWPFDocument(fis);
            XWPFTable table = doc.getTables().get(0);

            List<StringRecord> records = new ArrayList<>();

            for (int i = 1; i < table.getRows().size(); i++) {
                XWPFTableRow row = table.getRows().get(i);
                records.add(new StringRecord(row.getCell(0).getText(), row.getCell(1).getText(), row.getCell(2).getText()));
            }

            return formatRecord(doc, records);
        }
    }

    public static WordRecord loadNames(Path path) throws IOException {

        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            XWPFDocument doc = new XWPFDocument(fis);
            XWPFTable table = doc.getTables().get(0);

            List<StringRecord> records = new ArrayList<>();

            for (int i = 1; i < table.getRows().size(); i++) {
                XWPFTableRow row = table.getRows().get(i);
                records.add(new StringRecord(row.getCell(0).getText(), row.getCell(2).getText(), row.getCell(3).getText()));
            }

            return formatRecord(doc, records);
        }
    }

    private static WordRecord formatRecord(XWPFDocument doc, List<StringRecord> records) {

        List<String> numbers = doc.getParagraphs().stream().filter(p -> p.getNumID() != null).map(XWPFParagraph::getText).collect(Collectors.toList());
        List<String> strings = doc.getParagraphs().stream().filter(p -> p.getNumID() == null).map(p -> p.getNumID() + p.getText()).collect(Collectors.toList());

        Map<PropertyName, String> props = new HashMap<>();

        Arrays.asList(PropertyName.values()).forEach(p -> numbers.stream().filter(n -> n.startsWith(p.getTextHeader())).findFirst()
                .ifPresent(value -> props.put(p, value.replace(p.getTextHeader(), "").trim())));

        return new WordRecord(strings, numbers, props, records);
    }
}
