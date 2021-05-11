package md.leonis.ystt.utils;

import md.leonis.ystt.model.yodesk.characters.Character;
import md.leonis.ystt.model.yodesk.characters.CharacterAuxiliary;
import md.leonis.ystt.model.yodesk.characters.CharacterWeapon;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static md.leonis.config.Config.icm;
import static md.leonis.config.Config.yodesk;
import static md.leonis.ystt.utils.ImageUtils.getTile;

public class ExcelUtils {

    public static void saveCharacters(Path path) throws IOException {

        List<Character> characters = yodesk.getCharacters().getCharacters();
        List<CharacterAuxiliary> auxiliaries = yodesk.getCharacterAuxiliaries().getAuxiliaries();
        List<CharacterWeapon> weapons = yodesk.getCharacterWeapons().getWeapons();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Characters");

        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("ID    ");
        cell = row.createCell(1);
        cell.setCellValue("Name");
        cell = row.createCell(2);
        cell.setCellValue("Type    ");
        cell = row.createCell(3);
        cell.setCellValue("Movement Type  ");

        cell = row.createCell(4);
        cell.setCellValue("Damage   ");

        cell = row.createCell(5);
        cell.setCellValue("Reference   ");
        cell = row.createCell(6);
        cell.setCellValue("Health   ");

        cell = row.createCell(7);
        cell.setCellValue("Garbage 1  ");
        cell = row.createCell(8);
        cell.setCellValue("Garbage 2  ");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                cell = row.createCell(9 + i * 8 + j);
                cell.setCellValue(String.format("Fr%s%s", i + 1, j + 1));
            }
        }

        Map<Integer, Integer> imagesMap = new HashMap<>();

        for (int r = 0; r < characters.size() - 1; r++) {
            row = sheet.createRow(r + 1);
            row.setHeight((short) (32 * 15));
            cell = row.createCell(0);
            cell.setCellValue(characters.get(r).getIndex());
            cell = row.createCell(1);
            cell.setCellValue(characters.get(r).getName());
            cell = row.createCell(2);
            cell.setCellValue(characters.get(r).getType().name());
            cell = row.createCell(3);
            cell.setCellValue(characters.get(r).getMovementType().name());

            cell = row.createCell(4);
            cell.setCellValue(auxiliaries.get(r).getDamage());

            cell = row.createCell(5);
            cell.setCellValue(weapons.get(r).getReference());
            cell = row.createCell(6);
            cell.setCellValue(weapons.get(r).getHealth());

            cell = row.createCell(7);
            cell.setCellValue(characters.get(r).getProbablyGarbage1());
            cell = row.createCell(8);
            cell.setCellValue(characters.get(r).getProbablyGarbage2());

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 8; j++) {
                    cell = row.createCell(9 + i * 8 + j);
                    int tileId = characters.get(r).getFrame1().getTiles().get(j);

                    if (tileId != 65535) {
                        //cell.setCellValue(tileId);
                        Integer pictureId = imagesMap.get(tileId);

                        if (null == pictureId) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(getTile(tileId, icm), "PNG", baos);

                            pictureId = workbook.addPicture(baos.toByteArray(), Workbook.PICTURE_TYPE_PNG);
                            imagesMap.put(tileId, pictureId);
                        }

                        XSSFDrawing drawing = sheet.createDrawingPatriarch();
                        XSSFClientAnchor anchor = new XSSFClientAnchor();
                        anchor.setCol1(9 + i * 8 + j);
                        anchor.setRow1(r + 1);
                        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);
                        drawing.createPicture(anchor, pictureId).resize();
                    }
                }
            }
        }

        CellReference topLeft = new CellReference(sheet.getRow(0).getCell(0));
        CellReference bottomRight = new CellReference(sheet.getRow(characters.size() - 1).getCell(32));
        AreaReference tableArea = workbook.getCreationHelper().createAreaReference(topLeft, bottomRight);
        XSSFTable dataTable = sheet.createTable(tableArea);
        //dataTable.setName("Table1");
        dataTable.setDisplayName("Table1");

        //this styles the table as Excel would do per default
        dataTable.getCTTable().addNewTableStyleInfo();
        XSSFTableStyleInfo style = (XSSFTableStyleInfo) dataTable.getStyle();
        style.setName("TableStyleMedium2");
        style.setShowColumnStripes(false);
        style.setShowRowStripes(true);

        //this sets auto filters
        dataTable.getCTTable().addNewAutoFilter().setRef(tableArea.formatAsString());

        for (int x = 0; x < sheet.getRow(0).getPhysicalNumberOfCells(); x++) {
            sheet.autoSizeColumn(x);
        }

        try (FileOutputStream outputStream = new FileOutputStream(path.toFile())) {
            workbook.write(outputStream);
        }
    }

    public static String sizeInt(int value, int size) {
        return StringUtils.leftPad(String.valueOf(value), size, '0');
    }
}
