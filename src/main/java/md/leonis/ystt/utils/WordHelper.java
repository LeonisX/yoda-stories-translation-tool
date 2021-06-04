package md.leonis.ystt.utils;

import md.leonis.ystt.model.docx.ImageRecord;
import md.leonis.ystt.model.docx.StringImagesRecord;
import md.leonis.ystt.model.docx.StringRecord;
import md.leonis.ystt.model.yodesk.puzzles.StringMeaning;
import md.leonis.ystt.model.yodesk.tiles.TileName;
import md.leonis.ystt.model.yodesk.zones.Action;
import md.leonis.ystt.model.yodesk.zones.Condition;
import md.leonis.ystt.model.yodesk.zones.Instruction;
import org.apache.commons.lang3.StringUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static md.leonis.config.Config.icmw;
import static md.leonis.config.Config.yodesk;
import static md.leonis.ystt.utils.ImageUtils.getTile;

public class WordHelper {

    public static List<StringRecord> getActionsTexts() {

        List<StringRecord> zoneRecords = new ArrayList<>();

        yodesk.getZones().getZones().forEach(zone -> {
            List<StringRecord> zr = new ArrayList<>();

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

            zoneRecords.addAll(zr);
        });

        return zoneRecords;
    }

    public static List<StringImagesRecord> getPuzzlesTexts() {

        List<StringImagesRecord> puzzleRecords = new ArrayList<>();

        yodesk.getPuzzles().getFilteredPuzzles().forEach(p -> {
            List<StringImagesRecord> pz = new ArrayList<>();

            for (int i = 0; i < StringMeaning.values().length; i++) {
                if (StringUtils.isNotBlank(p.getPrefixesStrings().get(i).getContent())) {
                    pz.add(new StringImagesRecord(p.getIndex() + "." + StringMeaning.byId(i), Collections.emptyList(), p.getPrefixesStrings().get(i).getContent()));
                }
            }

            pz.get(0).setImages(Stream.of(p.getItem1()).map(tileId -> ImageUtils.getTile(tileId, icmw)).collect(Collectors.toList()));
            pz.get(0).getTileIds().add(p.getItem1());

            List<BufferedImage> image2 = Stream.of(p.getItem2()).filter(i -> i <= yodesk.getTiles().getTiles().size() && i >= 418)
                    .map(tileId -> ImageUtils.getTile(tileId, icmw)).collect(Collectors.toList());
            if (!image2.isEmpty()) {
                pz.get(0).getTileIds().add(p.getItem2());
            }

            if (pz.size() < 2) {
                pz.get(0).getImages().addAll(image2);
            } else {
                pz.get(1).setImages(image2);
            }

            puzzleRecords.addAll(pz);
        });

        return puzzleRecords;
    }

    public static List<StringImagesRecord> getNamesTexts() {

        List<StringImagesRecord> list = new ArrayList<>();

        List<TileName> filteredNames = yodesk.getTileNames().getFilteredNames();

        for (int i = 0; i < filteredNames.size(); i++) {
            TileName n = filteredNames.get(i);
            StringImagesRecord puzzleRecord = new StringImagesRecord(i, Collections.singletonList(getTile(n.getTileId(), icmw)), n.getName());
            list.add(puzzleRecord);
        }
        return list;
    }

    public static List<ImageRecord> getCharacters() {
        return yodesk.getCharacters().getFilteredCharacters().stream()
                .map(c -> new ImageRecord(c.getTileIds().stream().map(t -> getTile(t, icmw)).collect(Collectors.toList()), c.getName())).collect(Collectors.toList());
    }

    public static List<StringImagesRecord> getCharactersImages() {
        return yodesk.getCharacters().getFilteredCharacters().stream()
                .map(c -> new StringImagesRecord(c.getIndex(), c.getTileIds().stream().map(t -> getTile(t, icmw)).collect(Collectors.toList()), c.getName())).collect(Collectors.toList());
    }
}
