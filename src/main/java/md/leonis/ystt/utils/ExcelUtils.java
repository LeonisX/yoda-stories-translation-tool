package md.leonis.ystt.utils;

import md.leonis.ystt.model.yodesk.characters.Character;
import md.leonis.ystt.model.yodesk.characters.CharacterAuxiliary;
import md.leonis.ystt.model.yodesk.characters.CharacterWeapon;
import md.leonis.ystt.model.yodesk.zones.*;
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
import java.util.stream.Collectors;

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

        int columnIndex = 0;

        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(columnIndex++);
        cell.setCellValue("ID    ");
        cell = row.createCell(columnIndex++);
        cell.setCellValue("Name");
        cell = row.createCell(columnIndex++);
        cell.setCellValue("Type    ");
        cell = row.createCell(columnIndex++);
        cell.setCellValue("Movement Type  ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Damage   ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Reference   ");
        cell = row.createCell(columnIndex++);
        cell.setCellValue("Health   ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Garbage 1  ");
        cell = row.createCell(columnIndex);
        cell.setCellValue("Garbage 2  ");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                cell = row.createCell(9 + i * 8 + j);
                cell.setCellValue(String.format("Fr%s%s", i + 1, j + 1));
            }
        }

        Map<Integer, Integer> imagesMap = new HashMap<>();

        for (int r = 0; r < characters.size() - 1; r++) {
            columnIndex = 0;
            row = sheet.createRow(r + 1);
            row.setHeight((short) (32 * 15));
            cell = row.createCell(columnIndex++);
            cell.setCellValue(characters.get(r).getIndex());
            cell = row.createCell(columnIndex++);
            cell.setCellValue(characters.get(r).getName());
            cell = row.createCell(columnIndex++);
            cell.setCellValue(characters.get(r).getType().name());
            cell = row.createCell(columnIndex++);
            cell.setCellValue(characters.get(r).getMovementType().name());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(auxiliaries.get(r).getDamage());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(weapons.get(r).getReference());
            cell = row.createCell(columnIndex++);
            cell.setCellValue(weapons.get(r).getHealth());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(characters.get(r).getProbablyGarbage1());
            cell = row.createCell(columnIndex++);
            cell.setCellValue(characters.get(r).getProbablyGarbage2());

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 8; j++) {
                    row.createCell(columnIndex + i * 8 + j);
                    int tileId = characters.get(r).getFrame1().getTiles().get(j);
                    if (tileId != 65535) {
                        addPicture(workbook, sheet, imagesMap, tileId, 9 + i * 8 + j, r + 1);
                    }
                }
            }
        }

        CellReference topLeft = new CellReference(sheet.getRow(0).getCell(0));
        CellReference bottomRight = new CellReference(sheet.getRow(characters.size() - 1).getCell(32));
        formatTable(workbook, sheet, topLeft, bottomRight);

        try (FileOutputStream outputStream = new FileOutputStream(path.toFile())) {
            workbook.write(outputStream);
        }
    }

    public static void saveMonsters(Path path) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Monsters");

        int columnIndex = 0;
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(columnIndex++);
        cell.setCellValue("ZoneId    ");
        cell = row.createCell(columnIndex++);
        cell.setCellValue("MonsterId    ");
        cell = row.createCell(columnIndex++);
        cell.setCellValue("Character    ");
        cell = row.createCell(columnIndex++);
        cell.setCellValue("Loot      ");
        cell = row.createCell(columnIndex++);
        cell.setCellValue("DropsLoot   ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("X   ");
        cell = row.createCell(columnIndex++);
        cell.setCellValue("Y    ");

        cell = row.createCell(columnIndex);
        cell.setCellValue("Waypoints");

        Map<Integer, Integer> imagesMap = new HashMap<>();


        List<Character> characters = yodesk.getCharacters().getCharacters();
        List<Zone> zones = yodesk.getZones().getZones();

        int monsterLine = 1;
        for (int zoneId = 0; zoneId < zones.size() - 1; zoneId++) {
            ZoneAuxiliary za = zones.get(zoneId).getIzax();
            List<Monster> monsters = za.getMonsters();

            for (int monsterId = 0; monsterId < monsters.size(); monsterId++) {

                Monster m = monsters.get(monsterId);

                columnIndex = 0;
                row = sheet.createRow(monsterLine);
                row.setHeight((short) (32 * 15));
                cell = row.createCell(columnIndex++);
                cell.setCellValue(zoneId);
                cell = row.createCell(columnIndex++);
                cell.setCellValue(monsterId);

                cell = row.createCell(columnIndex++);
                cell.setCellValue(m.getCharacter());

                int tileId = characters.get(m.getCharacter()).getTileIds().get(0);
                addPicture(workbook, sheet, imagesMap, tileId, 2, monsterLine);

                cell = row.createCell(columnIndex++);
                cell.setCellValue(m.getLoot());

                int loot = m.getLoot();
                if (loot != 65535 && loot > 0) {
                    addPicture(workbook, sheet, imagesMap, loot - 1, 3, monsterLine);
                }

                cell = row.createCell(columnIndex++);
                cell.setCellValue(m.getDropsLoot());

                cell = row.createCell(columnIndex++);
                cell.setCellValue(m.getX());

                cell = row.createCell(columnIndex++);
                cell.setCellValue(m.getY());

                cell = row.createCell(columnIndex);
                String waypoints = m.getWaypoints().stream().map(w -> "[" + Long.toHexString(w.getX()).toUpperCase()
                        + ";" + Long.toHexString(w.getY()).toUpperCase() + "]").collect(Collectors.joining(", "));
                cell.setCellValue(waypoints);

                monsterLine++;
            }
        }

        CellReference topLeft = new CellReference(sheet.getRow(0).getCell(0));
        CellReference bottomRight = new CellReference(sheet.getRow(monsterLine - 1).getCell(columnIndex - 1));
        formatTable(workbook, sheet, topLeft, bottomRight);

        try (FileOutputStream outputStream = new FileOutputStream(path.toFile())) {
            workbook.write(outputStream);
        }
    }

    public static void saveZones(Path path) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Zones");

        int columnIndex = 0;
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(columnIndex++);
        cell.setCellValue("ZoneId    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Planet    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("PlanetAgain    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Dimensions    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Type    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("SharedCounter    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Hotspots    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("ZA1 Unnamed2    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Monsters    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("RequiredItems    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("GoalItems    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("ProvidedItems    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Npcs    ");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("ZA4 Unnamed2    ");

        columnIndex = columnIndex / 1000;

        List<Zone> zones = yodesk.getZones().getZones();

        for (int zoneId = 0; zoneId < zones.size() - 1; zoneId++) {
            Zone zone = zones.get(zoneId);
            ZoneAuxiliary za1 = zone.getIzax();
            ZoneAuxiliary2 za2 = zone.getIzx2();
            ZoneAuxiliary3 za3 = zone.getIzx3();
            ZoneAuxiliary4 za4 = zone.getIzx4();

            columnIndex = 0;
            row = sheet.createRow(zoneId + 1);
            row.setHeight((short) (32 * 15));

            cell = row.createCell(columnIndex++);
            cell.setCellValue(zoneId);

            cell = row.createCell(columnIndex++);
            cell.setCellValue(zone.getPlanet().name());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(zone.getPlanetAgain());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(String.format("%sx%s", zone.getWidth(), zone.getHeight()));

            cell = row.createCell(columnIndex++);
            cell.setCellValue(zone.getType().name());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(zone.getSharedCounter());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(zone.getHotspots().size());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(za1.get_unnamed2());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(za1.getMonsters().size());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(za1.getRequiredItems().size());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(za1.getGoalItems().size());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(za2.getProvidedItems().size());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(za3.getNpcs().size());

            cell = row.createCell(columnIndex++);
            cell.setCellValue(za4.get_unnamed2());
        }

        CellReference topLeft = new CellReference(sheet.getRow(0).getCell(0));
        CellReference bottomRight = new CellReference(sheet.getRow(zones.size() - 1).getCell(columnIndex - 1));
        formatTable(workbook, sheet, topLeft, bottomRight);

        try (FileOutputStream outputStream = new FileOutputStream(path.toFile())) {
            workbook.write(outputStream);
        }
    }

    private static void addPicture(Workbook workbook, XSSFSheet sheet, Map<Integer, Integer> imagesMap, int tileId, int col, int row) throws IOException {

        Integer pictureId = imagesMap.get(tileId);

        if (null == pictureId) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(getTile(tileId, icm), "PNG", baos);

            pictureId = workbook.addPicture(baos.toByteArray(), Workbook.PICTURE_TYPE_PNG);
            imagesMap.put(tileId, pictureId);
        }

        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = new XSSFClientAnchor();
        anchor.setCol1(col);
        anchor.setRow1(row);
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);
        drawing.createPicture(anchor, pictureId).resize();
    }

    private static void formatTable(Workbook workbook, XSSFSheet sheet, CellReference topLeft, CellReference bottomRight) {

        AreaReference tableArea = workbook.getCreationHelper().createAreaReference(topLeft, bottomRight);
        XSSFTable dataTable = sheet.createTable(tableArea);
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
    }
}
