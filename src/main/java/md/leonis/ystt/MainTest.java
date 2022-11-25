package md.leonis.ystt;

import com.google.gson.Gson;
import javafx.util.Pair;
import md.leonis.ystt.model.Encoding;
import md.leonis.ystt.model.yodesk.Yodesk;
import md.leonis.ystt.model.yodesk.characters.Character;
import md.leonis.ystt.model.yodesk.characters.CharacterType;
import md.leonis.ystt.model.yodesk.characters.CharacterWeapon;
import md.leonis.ystt.model.yodesk.characters.MovementType;
import md.leonis.ystt.model.yodesk.puzzles.Puzzle;
import md.leonis.ystt.model.yodesk.tiles.TileName;
import md.leonis.ystt.model.yodesk.zones.*;
import md.leonis.ystt.utils.IOUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static md.leonis.ystt.utils.WordUtils.addBulletsList;

public class MainTest {

    //TODO puzzles
    //TODO puzzles report
    public static void main(String[] args) throws IOException {

        Yodesk yodesk = Yodesk.fromFile("C:\\Users\\user\\Downloads\\Games_YS\\my experiments\\Star Wars - Yoda Stories (USA) (10.08.1998) (The LucasArts Archives Vol. IV) (Leonis)\\yodesk.dta", "windows-1252");
        //Yodesk yodesk = Yodesk.fromFile("C:\\Users\\user\\Downloads\\Games_YS\\my experiments\\Star Wars - Yoda Stories (Spain) (22.05.1997) (Installed)\\yodesk.dta", "windows-1252");

        //showPuzzles(yodesk);

        showCharactersMovementType(yodesk);
        //showRarestCharacters(yodesk);
        //showCharactersEnemies(yodesk);
        //showCharactersWeapons(yodesk);
        //showActionsOnlyTiles(yodesk);
        //showNPCs(yodesk);
        //showProvidedItems(yodesk);
        //showRequiredItems(yodesk);
        //showGoalItems(yodesk);
        //showLoot(yodesk);
        //showUnreachableLoot(yodesk);
        //showZonesTypeVsUnk2(yodesk);
        //showZonesTypeVsProvidedItems(yodesk);

        //showGarbage(yodesk);
        //showInstructionTitles(yodesk);
        //showConditionsTitles(yodesk);
        //showSoundsUsage();
        //encodingsToJson();
        //docExcelExperiments();
    }

    private static void showCharactersMovementType(Yodesk yodesk) {
        List<Pair<MovementType, Character>> pairs = new ArrayList<>();
        List<Character> characters = yodesk.getCharacters().getFilteredCharacters().stream().filter(c -> c.getType().equals(CharacterType.ENEMY)).collect(Collectors.toList());
        for (Character enemy : characters) {
            pairs.add(new Pair<>(enemy.getMovementType(), enemy));
        }
        // id   name    tiles
        pairs.stream().collect(Collectors.groupingBy(Pair::getKey))
                .entrySet().stream()
                .sorted(Comparator.comparingLong(e -> e.getKey().getId()))
                .forEach((e) -> System.out.println(String.format("| %d | %s | %s |",
                        e.getKey().getId(), e.getKey().name().charAt(0) + e.getKey().name().toLowerCase().substring(1),
                        e.getValue().stream().map(c -> c.getValue().getFrame1().getTiles().get(1)).distinct().sorted()
                                .map(t -> String.format("![](images/tiles/%04d.png)", t)).collect(Collectors.joining(" "))))
                );
    }

    private static void showRarestCharacters(Yodesk yodesk) {
        List<Pair<Character, Integer>> pairs = new ArrayList<>();
        for (Zone z : yodesk.getZones().getZones()) {
            for (Monster m : z.getIzax().getMonsters()) {
                Character character = yodesk.getCharacters().getFilteredCharacters().get(m.getCharacterId());
                pairs.add(new Pair<>(character, z.getIndex()));
            }
        }
        pairs.stream().collect(Collectors.groupingBy(Pair::getKey))
                .entrySet().stream()
                .filter(e -> e.getValue().stream().map(Pair::getValue).distinct().count() <= 5)
                .sorted(Comparator.comparingLong(e -> e.getValue().stream().map(Pair::getValue).distinct().count()))
                .forEach((e) -> System.out.println(String.format("| %d | ![](images/tiles/%04d.png) | %s | %s | %s | %s |",
                        e.getKey().getIndex(), e.getKey().getFrame1().getTiles().get(1), e.getKey().getName(),
                        yodesk.getCharacterWeapons().getWeapons().get(e.getKey().getIndex()).getHealth().shortValue(),
                        yodesk.getCharacterAuxiliaries().getAuxiliaries().get(e.getKey().getIndex()).getDamage(),
                        e.getValue().stream().map(Pair::getValue).distinct().map(z -> "" + z).collect(Collectors.joining(", ")))));
    }

    private static void showCharactersEnemies(Yodesk yodesk) {
        List<Pair<Character, Character>> pairs = new ArrayList<>();
        List<Character> characters = yodesk.getCharacters().getFilteredCharacters().stream().filter(c -> c.getType().equals(CharacterType.ENEMY)).collect(Collectors.toList());
        for (Character enemy : characters) {
            CharacterWeapon enemyWeapon = yodesk.getCharacterWeapons().getWeapons().get(enemy.getIndex());
            Character weapon = enemyWeapon.getReference() == 65535 ? null : yodesk.getCharacters().getFilteredCharacters().get(enemyWeapon.getReference());
            pairs.add(new Pair<>(enemy, weapon));
        }
        // ID   Tile 	Name	Movement Type  	Health      Melee Damage     Shoot Damage
        pairs.forEach(p -> System.out.println(String.format("| %d | ![](images/tiles/%04d.png) | %s | %s | %s | %s | %s |",
                p.getKey().getIndex(), p.getKey().getFrame1().getTiles().get(1), p.getKey().getName(), p.getKey().getMovementType().name().charAt(0) + p.getKey().getMovementType().name().toLowerCase().substring(1),
                yodesk.getCharacterWeapons().getWeapons().get(p.getKey().getIndex()).getHealth().shortValue(),
                yodesk.getCharacterAuxiliaries().getAuxiliaries().get(p.getKey().getIndex()).getDamage(),
                p.getValue() == null ? "-" : yodesk.getCharacterAuxiliaries().getAuxiliaries().get(p.getValue().getIndex()).getDamage())));
    }

    private static void showCharactersWeapons(Yodesk yodesk) {
        List<Pair<Character, List<Character>>> pairs = new ArrayList<>();
        List<Character> characters = yodesk.getCharacters().getFilteredCharacters().stream().filter(c -> c.getType().equals(CharacterType.WEAPON)).collect(Collectors.toList());
        for (Character weapon : characters) {
            List<Character> enemies = yodesk.getCharacters().getFilteredCharacters().stream().filter(c -> !c.getType().equals(CharacterType.WEAPON))
                    .filter(c -> yodesk.getCharacterWeapons().getWeapons().get(c.getIndex()).getReference() == weapon.getIndex()).collect(Collectors.toList());
            pairs.add(new Pair<>(weapon, enemies));
        }
        pairs.forEach(p -> System.out.println(String.format("| %d | ![](images/tiles/%04d.png) | %s | %s | %s |",
                p.getKey().getIndex(), p.getKey().getFrame1().getTiles().get(7), p.getKey().getName(), yodesk.getCharacterAuxiliaries().getAuxiliaries().get(p.getKey().getIndex()).getDamage(),
                p.getValue().stream().map(c -> c.getFrame1().getTiles().get(1)).distinct().sorted().map(t -> String.format("![](images/tiles/%04d.png)", t)).collect(Collectors.joining(" ")))));
    }

    //TODO
    private static void showPuzzles(Yodesk yodesk) {
        List<Triple<Integer, String, Integer>> triples = new ArrayList<>();
        for (Zone z : yodesk.getZones().getZones()) {
            for (Integer m : z.getIzx3().getNpcs()) {
                triples.add(new ImmutableTriple<>(z.getIndex(), getTileName(yodesk, m), m));
            }
        }
        triples.stream().collect(Collectors.groupingBy(Triple::getRight))
                .entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .forEach((e) -> System.out.println(String.format("| %d | ![](images/tiles/%04d.png) | %s | %s | %s |", e.getKey(), e.getKey(), e.getValue().get(0).getMiddle(), e.getValue().size(),
                        e.getValue().size() > 16 ? e.getValue().subList(0, 16).stream().map(z -> "" + z.getLeft()).collect(Collectors.joining(", ")) + ", ..."
                                : e.getValue().stream().map(z -> "" + z.getLeft()).collect(Collectors.joining(", ")))));
    }

    private static void showActionsOnlyTiles(Yodesk yodesk) {
        Map<Integer, List<Integer>> zoneTiles = yodesk.getZones().getZones().stream()
                .flatMap(z -> z.getTileIds().stream()).flatMap(t -> t.getColumn().stream()).collect(Collectors.groupingBy(t -> t));

        List<Pair<Integer, Integer>> actionZones = new ArrayList<>();
        for (Zone z : yodesk.getZones().getZones()) {
            z.getActions().stream().flatMap(t -> t.getInstructions().stream()).forEach(i -> {
                switch (i.getOpcode()) {
                    case PLACE_TILE:
                        actionZones.add(new Pair<>((int) i.getArguments().get(3), z.getIndex()));
                        break;
                    case DRAW_TILE:
                        actionZones.add(new Pair<>((int) i.getArguments().get(2), z.getIndex()));
                        break;
                    case SET_VARIABLE:
                        actionZones.add(new Pair<>((int) i.getArguments().get(3), z.getIndex()));
                        break;
                    case DROP_ITEM:
                        actionZones.add(new Pair<>((int) i.getArguments().get(0), z.getIndex()));
                        break;
                    case ADD_ITEM:
                        actionZones.add(new Pair<>((int) i.getArguments().get(0), z.getIndex()));
                        break;
                    case REMOVE_ITEM:
                        actionZones.add(new Pair<>((int) i.getArguments().get(0), z.getIndex()));
                        break;
                }
            });
        }

        Map<Integer, List<Pair<Integer, Integer>>> actions = actionZones.stream().collect(Collectors.groupingBy(Pair::getKey));

        actions.keySet().removeAll(zoneTiles.keySet());
        actions.keySet().removeAll(yodesk.getZones().getZones().stream().flatMap(z -> z.getIzax().getRequiredItems().stream()).distinct().collect(Collectors.toList()));
        actions.keySet().removeAll(yodesk.getZones().getZones().stream().flatMap(z -> z.getIzax().getGoalItems().stream()).distinct().collect(Collectors.toList()));
        actions.keySet().removeAll(yodesk.getZones().getZones().stream().flatMap(z -> z.getIzx2().getProvidedItems().stream()).distinct().collect(Collectors.toList()));
        actions.keySet().removeAll(yodesk.getZones().getZones().stream().flatMap(z -> z.getIzx3().getNpcs().stream()).distinct().collect(Collectors.toList()));
        actions.keySet().removeAll(yodesk.getZones().getZones().stream().flatMap(z -> z.getIzax().getMonsters().stream().map(m -> m.getLoot() - 1)).distinct().collect(Collectors.toList()));
        actions.keySet().removeAll(yodesk.getCharacters().getFilteredCharacters().stream().flatMap(c -> c.getTileIds().stream()).distinct().collect(Collectors.toList()));
        actions.keySet().removeAll(yodesk.getPuzzles().getPuzzles().stream().map(Puzzle::getItem1).distinct().collect(Collectors.toList()));
        actions.keySet().removeAll(yodesk.getPuzzles().getPuzzles().stream().map(Puzzle::getItem2).distinct().collect(Collectors.toList()));

        actions.entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .forEach((e) -> System.out.println(String.format("| %d | ![](images/tiles/%04d.png) | %s | %s | %s |", e.getKey(), e.getKey(), getTileName2(yodesk, e.getKey()), e.getValue().size(),
                        distinctZones(e.getValue()).stream().map(z -> "" + z).collect(Collectors.joining(", ")))));
    }

    private static List<Integer> distinctZones(List<Pair<Integer, Integer>> pairs) {
        return pairs.stream().map(Pair::getValue).distinct().sorted().collect(Collectors.toList());
    }


    private static String getTileName2(Yodesk yodesk, int tileId) {
        String tileName = yodesk.getTileNames().getFilteredNames().stream().filter(t -> t.getTileId() == tileId)
                .findFirst().map(TileName::getName).orElse(null);
        if (tileName == null) {
            tileName = "";
        }
        return tileName;
    }

    private static void showNPCs(Yodesk yodesk) {
        List<Triple<Integer, String, Integer>> triples = new ArrayList<>();
        for (Zone z : yodesk.getZones().getZones()) {
            for (Integer m : z.getIzx3().getNpcs()) {
                triples.add(new ImmutableTriple<>(z.getIndex(), getTileName(yodesk, m), m));
            }
        }
        triples.stream().collect(Collectors.groupingBy(Triple::getRight))
                .entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .forEach((e) -> System.out.println(String.format("| %d | ![](images/tiles/%04d.png) | %s | %s | %s |", e.getKey(), e.getKey(), e.getValue().get(0).getMiddle(), e.getValue().size(),
                        e.getValue().size() > 16 ? e.getValue().subList(0, 16).stream().map(z -> "" + z.getLeft()).collect(Collectors.joining(", ")) + ", ..."
                                : e.getValue().stream().map(z -> "" + z.getLeft()).collect(Collectors.joining(", ")))));
    }

    private static void showProvidedItems(Yodesk yodesk) {
        List<Triple<Integer, String, Integer>> triples = new ArrayList<>();
        for (Zone z : yodesk.getZones().getZones()) {
            for (Integer m : z.getIzax().getRequiredItems()) {
                triples.add(new ImmutableTriple<>(z.getIndex(), getTileName(yodesk, m), m));
            }
        }
        triples.stream().collect(Collectors.groupingBy(Triple::getRight))
                .entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .forEach((e) -> System.out.println(String.format("| %d | ![](images/tiles/%04d.png) | %s | %s | %s |", e.getKey(), e.getKey(), e.getValue().get(0).getMiddle(), e.getValue().size(),
                        e.getValue().size() > 16 ? e.getValue().subList(0, 16).stream().map(z -> "" + z.getLeft()).collect(Collectors.joining(", ")) + ", ..."
                                : e.getValue().stream().map(z -> "" + z.getLeft()).collect(Collectors.joining(", ")))));
    }

    private static void showRequiredItems(Yodesk yodesk) {
        List<Triple<Integer, String, Integer>> triples = new ArrayList<>();
        for (Zone z : yodesk.getZones().getZones()) {
            for (Integer m : z.getIzax().getRequiredItems()) {
                triples.add(new ImmutableTriple<>(z.getIndex(), getTileName(yodesk, m), m));
            }
        }
        triples.stream().collect(Collectors.groupingBy(Triple::getRight))
                .entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .forEach((e) -> System.out.println(String.format("| %d | ![](images/tiles/%04d.png) | %s | %s | %s |", e.getKey(), e.getKey(), e.getValue().get(0).getMiddle(), e.getValue().size(),
                        e.getValue().size() > 16 ? e.getValue().subList(0, 16).stream().map(z -> "" + z.getLeft()).collect(Collectors.joining(", ")) + ", ..."
                                : e.getValue().stream().map(z -> "" + z.getLeft()).collect(Collectors.joining(", ")))));
    }

    private static void showGoalItems(Yodesk yodesk) {
        List<Triple<Integer, String, Integer>> triples = new ArrayList<>();
        for (Zone z : yodesk.getZones().getZones()) {
            for (Integer m : z.getIzax().getGoalItems()) {
                triples.add(new ImmutableTriple<>(z.getIndex(), getTileName(yodesk, m), m));
            }
        }
        triples.stream().collect(Collectors.groupingBy(Triple::getRight))
                .entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().get(0).getMiddle()))
                .forEach((e) -> System.out.println(String.format("| %d | ![](images/tiles/%04d.png) | %s | %s | %s |", e.getKey(), e.getKey(), e.getValue().get(0).getMiddle(), e.getValue().size(),
                        e.getValue().stream().map(z -> "" + z.getLeft()).collect(Collectors.joining(", ")))));
    }

    private static void showLoot(Yodesk yodesk) {
        List<Triple<Integer, String, Integer>> triples = new ArrayList<>();
        for (Zone z : yodesk.getZones().getZones()) {
            for (Monster m : z.getIzax().getMonsters()) {
                if (m.getLoot() != 0 && m.getLoot() != 65535 && m.getDropsLoot() != 0) {
                    triples.add(new ImmutableTriple<>(z.getIndex(), getTileName(yodesk, m.getLoot() - 1), m.getLoot() - 1));
                }
            }
        }
        triples.stream().collect(Collectors.groupingBy(Triple::getRight))
                .entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .forEach((e) -> System.out.println(String.format("| %d | ![](images/tiles/%04d.png) | %s | %s | %s |", e.getKey(), e.getKey(), e.getValue().get(0).getMiddle(), e.getValue().size(),
                        e.getValue().stream().map(z -> "" + z.getLeft()).collect(Collectors.joining(", ")))));
    }

    private static String getTileName(Yodesk yodesk, int tileId) {
        String tileName = yodesk.getTileNames().getFilteredNames().stream().filter(t -> t.getTileId() == tileId)
                .findFirst().map(TileName::getName).orElse(null);
        if (tileName == null) {
            tileName = "??? #" + (tileId);
        }
        return tileName;
    }

    private static void showUnreachableLoot(Yodesk yodesk) {
        for (Zone z : yodesk.getZones().getZones()) {
            for (Monster m : z.getIzax().getMonsters()) {
                if (m.getLoot() != 0 && m.getLoot() != 65535 && m.getDropsLoot() == 0) {
                    String character = yodesk.getCharacters().getCharacters().get(m.getCharacterId()).getName();
                    String tileName = getTileName(yodesk, m.getLoot() - 1);
                    System.out.println(String.format("* Zone #%s: %s: %s", z.getIndex(), character, tileName));
                }
            }
        }
    }

    private static void showZonesTypeVsUnk2(Yodesk yodesk) {
        yodesk.getZones().getZones().stream().map(z -> z.getType() + ":" + z.getIzax().get_unnamed2())
                .collect(Collectors.groupingBy(Function.identity())).entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue().size()));
    }

    private static void showZonesTypeVsProvidedItems(Yodesk yodesk) {
        yodesk.getZones().getZones().stream().filter(z -> z.getIzx2().getNumProvidedItems() > 0)
                .collect(Collectors.groupingBy(Zone::getType)).forEach((key, value) -> System.out.println(key + ": " + value.size()));
    }

    private static void showGarbage(Yodesk yodesk) {
        yodesk.getZones().getZones().forEach(zone -> {
            System.out.println("Zone #" + zone.getIndex());
            List<Action> actions = zone.getActions();
            for (int i = 0; i < actions.size(); i++) {
                Action action = actions.get(i);
                System.out.println("Action #" + i);

                System.out.println(getGarbage(action));
                System.out.println(getActionScript(action));
            }
        });

    }

    private static String getGarbage(Action action) {
        StringBuilder sb = new StringBuilder();

        action.getConditions().forEach(c -> {
            for (int j = c.getOpcode().getArgsCount(); j < 5; j++) {
                sb.append(" ").append(getUnusedVariableAsText(c.getArguments().get(j)));
            }
        });

        action.getInstructions().forEach(i -> {
            for (int j = i.getOpcode().getArgsCount(); j < 5; j++) {
                sb.append(" ").append(getUnusedVariableAsText(i.getArguments().get(j)));
            }
        });

        return sb.toString();
    }

    private static String getActionScript(Action action) {
        StringBuilder sb = new StringBuilder();

        action.getConditions().forEach(c -> {
            String args = printArguments(c);
            sb.append("    ").append(c.getOpcode().getOpcode());
            if (!args.isEmpty()) {
                sb.append(": ");
            }
            sb.append(args);
            sb.append("; ");
        });
        action.getInstructions().forEach(i -> {
            String args = printArguments(i);
            sb.append("    ").append(i.getOpcode().getOpcode());
            if (!args.isEmpty()) {
                sb.append(": ");
            }
            sb.append(printArguments(i));
            sb.append("; ");
        });
        return sb.toString().replace(":  \"", ": \"");
    }

    private static String printArguments(Condition condition) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < condition.getOpcode().getArgsCount(); i++) {
            sb.append(condition.getArguments().get(i)).append(" ");
        }
        return sb.toString().trim();
    }

    private static String printArguments(Instruction instruction) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < instruction.getOpcode().getArgsCount(); i++) {
            sb.append(instruction.getArguments().get(i)).append(" ");
        }
        return sb.toString().trim();
    }

    private static void showConditionsTitles(Yodesk yodesk) {
        LinkedHashMap<ConditionOpcode, List<String>> map = Arrays.stream(ConditionOpcode.values())
                .collect(Collectors.toMap(Function.identity(), i -> new ArrayList<>(), (x, y) -> y, LinkedHashMap::new));
        yodesk.getZones().getZones().forEach(zone -> zone.getActions().forEach(action -> {
            action.getConditions().forEach(c -> {
                StringBuilder result = new StringBuilder();
                for (int j = c.getOpcode().getArgsCount(); j < 5; j++) {
                    result.append(getUnusedVariableAsText(c.getArguments().get(j)));
                }
                map.get(c.getOpcode()).add(result.toString().trim());
            });
        }));
        System.out.println("Conditions usage:");
        map.forEach((key, value) -> System.out.println(key.getId() + ": " + key.getOpcode() + ": " + value.size()));
        System.out.println("\nConditions garbage:");
        map.forEach((key, value) -> System.out.println(key.getOpcode() + ": " + value.stream()
                .collect(Collectors.groupingBy(Function.identity())).entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .map(Map.Entry::getKey).collect(Collectors.joining(", "))));
    }

    private static void showInstructionTitles(Yodesk yodesk) {
        LinkedHashMap<InstructionOpcode, List<String>> map = Arrays.stream(InstructionOpcode.values())
                .collect(Collectors.toMap(Function.identity(), i -> new ArrayList<>(), (x, y) -> y, LinkedHashMap::new));
        yodesk.getZones().getZones().forEach(zone -> zone.getActions().forEach(action -> {
            action.getInstructions().forEach(i -> {
                StringBuilder result = new StringBuilder();
                for (int j = i.getOpcode().getArgsCount(); j < 5; j++) {
                    result.append(getUnusedVariableAsText(i.getArguments().get(j)));
                }
                map.get(i.getOpcode()).add(result.toString().trim());
            });
        }));
        System.out.println("Instructions usage:");
        map.forEach((key, value) -> System.out.println(key.getId() + ": " + key.getOpcode() + ": " + value.size()));
        System.out.println("\nInstructions garbage:");
        map.forEach((key, value) -> System.out.println(key.getOpcode() + ": " + value.stream()
                .collect(Collectors.groupingBy(Function.identity())).entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .map(Map.Entry::getKey).collect(Collectors.joining(", "))));
    }

    private static String getUnusedVariableAsText(Short variable) {
        int var = Short.toUnsignedInt(variable);
        char char1 = (char) ((var & 0xff00) >>> 8);
        char char2 = (char) (var & 0xff);
        return "" + char2 + char1;
    }

    private static void showSoundsUsage() {
        // Sounds usage
        showSounds("eng-1.2");
        showSounds("eng-1.1");
        showSounds("eng-1");
        showSounds("eng-demo");
        showSounds("spa-1");
    }

    private static void showSounds(String version) {
        List<String> lines = IOUtils.loadTextFile(Paths.get(String.format("D:\\yoda-stories-translation-tool\\output-%s\\dumps\\actionsScripts.txt", version)));
        List<Integer> sounds = lines.stream().filter(l -> l.contains("play-sound"))
                .map(l -> Integer.valueOf(l.split(":")[1].trim())).collect(Collectors.toList());

        System.out.println();
        System.out.println(version);
        System.out.println();
        sounds.stream().collect(Collectors.groupingBy(Function.identity())).entrySet().stream()
                .sorted(Map.Entry.comparingByKey()).forEach(e -> System.out.println(e.getKey() + ": " + e.getValue().size()));
    }

    private static void encodingsToJson() {
        // Encodings
        List<Encoding> encodings = new ArrayList<>();
        encodings.add(new Encoding("windows-1250", "Eastern European (Latin 2)"));
        encodings.add(new Encoding("windows-1251", "Cyrillic (Slavic)"));
        encodings.add(new Encoding("windows-1252", "Western European (Latin-1, ANSI)"));
        encodings.add(new Encoding("windows-1253", "Greek"));
        encodings.add(new Encoding("windows-1254", "Turkish (Latin 5)"));
        encodings.add(new Encoding("windows-1255", "Hebrew"));
        encodings.add(new Encoding("windows-1256", "Arabic"));
        encodings.add(new Encoding("windows-1257", "Baltic"));
        encodings.add(new Encoding("windows-1258", "Vietnamese"));
        encodings.add(new Encoding("x-windows-874", "Thai"));
        encodings.add(new Encoding("windows-31j", "Japanese"));
        encodings.add(new Encoding("x-windows-iso2022jp", "Japanese ISO-2022"));
        encodings.add(new Encoding("x-mswin-936", "Chinese Simplified"));
        encodings.add(new Encoding("x-windows-950", "Chinese Traditional"));
        encodings.add(new Encoding("x-MS950-HKSCS", "Chinese Traditional + Hong Kong"));
        encodings.add(new Encoding("x-windows-949", "Korean"));
        encodings.add(new Encoding("x-Johab", "Korean (Johab)"));

        System.out.println(new Gson().toJson(encodings));


        Charset charset = Charset.forName("windows-1252");

        System.out.println(charset);

        byte[] bytes = new byte[256];

        for (int i = 128; i < 256; i++) {
            bytes[i] = (byte) i;
        }

        System.out.println("ASCII:  " + new String(bytes, StandardCharsets.US_ASCII));
        System.out.println("windows-1252: " + new String(bytes, Charset.forName("windows-1252")));
        System.out.println("windows-1250: " + new String(bytes, Charset.forName("windows-1250")));
        System.out.println("windows-1251: " + new String(bytes, Charset.forName("windows-1251")));
    }

    private static void docExcelExperiments() throws IOException {
        //ExcelUtils.saveCharacters(new Yodesk(new ByteBufferKaitaiInputStream("D:\\Working\\_Yoda\\YExplorer\\other\\Yoda Stories (14.02.1997)\\Yodesk.dta")));

        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("The list:");

        ArrayList<String> items = new ArrayList<>(Arrays.asList("One", "Two", "Three"));

        addBulletsList(document, items);

        FileOutputStream out = new FileOutputStream("CreateWordSimplestNumberingList.docx");
        document.write(out);
        out.close();
        document.close();

        FileInputStream fis = new FileInputStream("CreateWordSimplestNumberingList.docx");
        XWPFDocument doc = new XWPFDocument(fis);
        XWPFNumbering numbering = doc.getNumbering();

        //System.out.println(numbering);

        for (int i = 0; i < numbering.getNums().size(); i++) {
            XWPFNum num = numbering.getNums().get(i);
            //Arrays.stream(num.getCTNum().getLvlOverrideArray()).forEach(n -> System.out.println(n.));
        }

        List<String> numbers = doc.getParagraphs().stream().filter(p -> p.getNumID() != null).map(XWPFParagraph::getText).collect(Collectors.toList());

        List<String> strings = doc.getParagraphs().stream().filter(p -> p.getNumID() == null).map(p -> p.getNumID() + p.getText()).collect(Collectors.toList());

        System.out.println(numbers);
        System.out.println(strings);
    }
}
