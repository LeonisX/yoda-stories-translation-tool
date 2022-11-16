package md.leonis.ystt;

import com.google.gson.Gson;
import md.leonis.ystt.model.Encoding;
import md.leonis.ystt.model.yodesk.Yodesk;
import md.leonis.ystt.model.yodesk.zones.*;
import md.leonis.ystt.utils.IOUtils;
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

    public static void main(String[] args) throws IOException {

        Yodesk yodesk = Yodesk.fromFile("C:\\Users\\user\\Downloads\\Games_YS\\my experiments\\Star Wars - Yoda Stories (USA) (10.08.1998) (The LucasArts Archives Vol. IV) (Leonis)\\yodesk.dta", "windows-1252");
        //Yodesk yodesk = Yodesk.fromFile("C:\\Users\\user\\Downloads\\Games_YS\\my experiments\\Star Wars - Yoda Stories (Spain) (22.05.1997) (Installed)\\yodesk.dta", "windows-1252");

        showZonesTypeVsUnk2(yodesk);
        //showZonesTypeVsProvidedItems(yodesk);

        //showGarbage(yodesk);
        //showInstructionTitles(yodesk);
        //showConditionsTitles(yodesk);
        //showSoundsUsage();
        //encodingsToJson();
        //docExcelExperiments();
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
