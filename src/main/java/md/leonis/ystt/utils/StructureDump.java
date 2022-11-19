package md.leonis.ystt.utils;

import md.leonis.ystt.model.yodesk.Yodesk;
import md.leonis.ystt.model.yodesk.characters.Character;
import md.leonis.ystt.model.yodesk.characters.CharacterAuxiliary;
import md.leonis.ystt.model.yodesk.characters.CharacterWeapon;
import md.leonis.ystt.model.yodesk.puzzles.Puzzle;
import md.leonis.ystt.model.yodesk.puzzles.StringMeaning;
import md.leonis.ystt.model.yodesk.tiles.Tile;
import md.leonis.ystt.model.yodesk.tiles.TileGender;
import md.leonis.ystt.model.yodesk.zones.Action;
import md.leonis.ystt.model.yodesk.zones.Condition;
import md.leonis.ystt.model.yodesk.zones.Instruction;
import md.leonis.ystt.model.yodesk.zones.Zone;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.HexDump;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static md.leonis.config.Config.release;
import static md.leonis.ystt.utils.BinaryUtils.bytesToBinary;
import static md.leonis.ystt.utils.BinaryUtils.crc32hex;

public class StructureDump extends ArrayList<String> {

    private final Yodesk yodesk;

    public StructureDump(Yodesk yodesk) {
        this.yodesk = yodesk;
    }

    public StructureDump dumpStructure() {

        addH1(release.getTitle());
        addBr();

        add("* CRC32: " + release.getDtaCrc32());
        addBr();

        dumpHighLevelStructure();
        dumpMidLevelStructure();

        return this;
    }

    public List<String> dumpActionsPhrases() {

        addH1("Zone actions phrases");

        List<Zone> zones = yodesk.getZones().getZones();

        for (int i = 0; i < zones.size(); i++) {
            addH4("Zone " + i);
            Zone zone = zones.get(i);

            for (int a = 0; a < zone.getActions().size(); a++) {

                Action action = zone.getActions().get(a);
                List<String> conditions = action.getConditions().stream().map(Condition::getText).filter(StringUtils::isNotBlank).collect(Collectors.toList());
                List<String> instructions = action.getInstructions().stream().map(Instruction::getText).filter(StringUtils::isNotBlank).collect(Collectors.toList());

                if (!conditions.isEmpty() || !instructions.isEmpty()) {
                    addBr();
                    addH5("Action " + a);
                    addBr();
                }

                if (!conditions.isEmpty()) {
                    for (int x = 0; x < conditions.size(); x++) {
                        add(String.format("* Condition %s: %s", x, conditions.get(x)));
                    }
                }
                if (!instructions.isEmpty()) {
                    for (int x = 0; x < instructions.size(); x++) {
                        add(String.format("* Instruction %s: %s", x, instructions.get(x)));
                    }
                }
            }
            addBr();
        }
        return this;
    }

    public List<String> dumpPuzzlesPhrases() {

        addH1("Puzzle phrases");

        List<Puzzle> puzzles = yodesk.getPuzzles().getFilteredPuzzles();

        for (int i = 0; i < puzzles.size(); i++) {
            addH4("Puzzle " + i);
            addBr();
            Puzzle puzzle = puzzles.get(i);

            for (int j = 0; j < StringMeaning.values().length; j++) {
                if (StringUtils.isNotBlank(puzzle.getPrefixesStrings().get(j).getContent())) {
                    add(String.format("* %-8s %s", StringMeaning.byId(j) + ":", puzzle.getPrefixesStrings().get(j).getContent()));
                }
            }
            addBr();
        }
        return this;
    }

    public List<String> dumpZoneTilesStructure() {

        addH1("Zone tiles");
        List<Zone> zones = yodesk.getZones().getZones();
        for (int i = 0; i < zones.size(); i++) {
            addH5("Zone " + i);
            Zone zone = zones.get(i);
            for (int y = 0; y < zone.getHeight(); y++) {
                for (int x = 0; x < zone.getWidth(); x++) {
                    add(String.format("* [%s; %s]: %s", x, y, zone.getTileIds().get(y * zone.getHeight() + x).getColumn().toString()));
                }
            }
        }
        return this;
    }

    private void dumpMidLevelStructure() {

        addH2("Middle level structure");

        addH4("VERS:");
        add("* Value: " + HexDump.intToHex((int) yodesk.getVersion().getVer()));
        addBr();

        addH4("STUP:");
        addSizeCrc32(yodesk.getStartupImage().getParent().getBytes());
        addBr();

        addH4("SNDS:");
        add("* Count: " + yodesk.getSounds().getSounds().size());
        yodesk.getSounds().getSounds().forEach(s -> add("    * " + s));
        addBr();

        addH4("TILE:");
        add("* Count: " + yodesk.getTiles().getTiles().size());
        List<Tile> tiles = yodesk.getTiles().getTiles();
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            add("* Tile " + i + ": CRC32: " + crc32hex(tile.getPixels()) + "; Attribyte: " + bytesToBinary(tile.getRawAttributes()));
        }
        addBr();

        addH4("ZONE:");
        List<Zone> zones = yodesk.getZones().getZones();
        add("* Count: " + zones.size());
        for (int i = 0; i < zones.size(); i++) {
            addH5("Zone " + i);
            Zone zone = zones.get(i);
            add("* planet: " + zone.getPlanet().name());
            add("* width: " + zone.getWidth());
            add("* height: " + zone.getHeight());
            add("* type: " + zone.getType().name());
            add("* sharedCounter: " + zone.getSharedCounter());
            add("* planetAgain: " + zone.getPlanetAgain());
            add("* hotspots: ");
            zone.getHotspots().forEach(h -> add(String.format("    * %s [%s; %s] enabled: %s; argument: %s",
                    h.getType().name(), h.getX(), h.getY(), h.getEnabled(), h.getArgument())));
            add("* izax: ");
            add("    * size: " + zone.getIzax().getSize());
            add("    * _unnamed2: " + zone.getIzax().get_unnamed2());
            zone.getIzax().getMonsters().forEach(m ->
                    add(String.format("    * monster %s [%s; %s] loot: %s; dropsLoot: %s; waypoints: %s", m.getCharacterId(), m.getX(), m.getY(),
                            m.getLoot(), m.getDropsLoot(), m.getWaypoints().stream().map(w -> String.format("[%s; %s]", w.getX(), w.getY())).collect(Collectors.joining(", ")))));
            add("    * requiredItems: " + zone.getIzax().getRequiredItems());
            add("    * goalItems: " + zone.getIzax().getGoalItems());
            add("* izx2: ");
            add("    * size: " + zone.getIzx2().getSize());
            add("    * providedItems: " + zone.getIzx2().getProvidedItems());
            add("* izx3: ");
            add("    * size: " + zone.getIzx3().getSize());
            add("    * providedItems: " + zone.getIzx3().getNpcs());
            add("* izx4: ");
            add("    * size: " + zone.getIzx4().getSize());
            add("    * _unnamed2: " + zone.getIzx4().get_unnamed2());
            add("* actions: ");
            add("    * count: " + zone.getActions().size());
            List<Action> actions = zone.getActions();
            for (int i1 = 0; i1 < actions.size(); i1++) {
                Action a = actions.get(i1);
                add(String.format("    * Action %s conditions: %s; instructions: %s", i1, a.getConditions().size(), a.getInstructions().size()));
            }
        }
        addBr();

        addH4("PUZ2:");
        List<Puzzle> puzzles = yodesk.getPuzzles().getPuzzles();
        add("* Count: " + puzzles.size());
        for (int i = 0; i < puzzles.size(); i++) {
            addH5("Puzzle " + i);
            Puzzle puzzle = puzzles.get(i);
            add("* item1Class: " + puzzle.getItem1Class());
            add("* item2Class: " + puzzle.getItem2Class());
            add("* _unnamed6: " + puzzle.get_unnamed6());
            add("* item1: " + puzzle.getItem1());
            add("* item2: " + puzzle.getItem2());
        }
        addBr();

        addH4("CHAR:");
        List<Character> characters = yodesk.getCharacters().getCharacters();
        add("* Count: " + characters.size());
        for (int i = 0; i < characters.size(); i++) {
            addH5("Character " + i);
            Character character = characters.get(i);
            add("* size: " + character.getSize());
            add("* name: " + character.getName());
            add("* type: " + character.getType());
            add("* movementType: " + character.getMovementType());
            add("* probablyGarbage1: " + character.getProbablyGarbage1());
            add("* probablyGarbage2: " + character.getProbablyGarbage2());
            add("* frame1: " + (character.getFrame1() == null ? "[]" : character.getFrame1().getTiles()));
            add("* frame2: " + (character.getFrame2() == null ? "[]" : character.getFrame2().getTiles()));
            add("* frame3: " + (character.getFrame3() == null ? "[]" : character.getFrame3().getTiles()));
        }
        addBr();

        addH4("CHWP:");
        List<CharacterWeapon> weapons = yodesk.getCharacterWeapons().getWeapons();
        add("* Count: " + weapons.size());
        for (int i = 0; i < weapons.size(); i++) {
            addH5("Character Weapon " + i);
            CharacterWeapon weapon = weapons.get(i);
            add("* reference: " + weapon.getReference());
            add("* health: " + weapon.getHealth());
        }
        addBr();

        addH4("CAUX:");
        List<CharacterAuxiliary> auxiliaries = yodesk.getCharacterAuxiliaries().getAuxiliaries();
        add("* Count: " + auxiliaries.size());
        for (int i = 0; i < auxiliaries.size(); i++) {
            addH5("Character Auxiliaries " + i);
            CharacterAuxiliary auxiliary = auxiliaries.get(i);
            add("* damage: " + auxiliary.getDamage());
        }
        addBr();

        addH4("TNAM:");
        add("* Count: " + yodesk.getTileNames().getNames().size());
        yodesk.getTileNames().getNames().stream().map(tileName -> "* " + tileName.getName()).forEach(this::add);
        addBr();

        if (null != yodesk.getTileGenders()) {
            addH4("TGEN:");
            add("* Count: " + yodesk.getTileGenders().getGenders().size());
            List<TileGender> genders = yodesk.getTileGenders().getGenders();
            for (int i = 0; i < genders.size(); i++) {
                TileGender gender = genders.get(i);
                add("* Tile Gender " + i + ": " + gender.name());
            }
            addBr();
        }
    }

    private void dumpHighLevelStructure() {

        addH2("High level structure");

        addH4("VERS:");
        addSizeCrc32(yodesk.getVersion().getParent().getBytes());
        addBr();

        addH4("STUP:");
        addSizeCrc32(yodesk.getStartupImage().getParent().getBytes());
        addBr();

        addH4("SNDS:");
        addSizeCrc32(yodesk.getSounds().getParent().getBytes());
        addBr();

        addH4("TILE:");
        addSizeCrc32(yodesk.getTiles().getParent().getBytes());
        addBr();

        addH4("ZONE:");
        addSizeCrc32(yodesk.getZones().getParent().getBytes());
        addBr();

        addH4("PUZ2:");
        addSizeCrc32(yodesk.getPuzzles().getParent().getBytes());
        addBr();

        addH4("CHAR:");
        addSizeCrc32(yodesk.getCharacters().getParent().getBytes());
        addBr();

        addH4("CHWP:");
        addSizeCrc32(yodesk.getCharacterWeapons().getParent().getBytes());
        addBr();

        addH4("CAUX:");
        addSizeCrc32(yodesk.getCharacterAuxiliaries().getParent().getBytes());
        addBr();

        addH4("TNAM:");
        addSizeCrc32(yodesk.getTileNames().getParent().getBytes());
        addBr();

        if (null != yodesk.getTileGenders()) {
            addH4("TGEN:");
            addSizeCrc32(yodesk.getTileGenders().getParent().getBytes());
            addBr();
        }

        addH4("ENDF:");
        addSizeCrc32(yodesk.getEndf().getParent().getBytes());
        addBr();
    }

    private void addSizeCrc32(byte[] bytes) {
        add("* Size: " + bytes.length);
        add("* CRC32: " + BinaryUtils.crc32hex(bytes));
    }

    private void addBr() {
        add("");
    }

    private void addH1(String line) {
        add(line);
        add(StringUtils.repeat('=', line.length()));
        addBr();
    }

    private void addH2(String line) {
        add(line);
        add(StringUtils.repeat('-', line.length()));
        addBr();
    }

    private void addH4(String line) {
        add("#### " + line);
    }

    private void addH5(String line) {
        add("##### " + line);
    }

    @Override
    public String toString() {
        return String.join("\n", this);
    }
}
