package md.leonis.ystt;

import com.google.gson.Gson;
import md.leonis.ystt.model.Encoding;
import md.leonis.ystt.utils.IOUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainTest {

    public static void main(String[] args) {

        // Sounds usage
        showSounds("eng-1.2");
        showSounds("eng-1.1");
        showSounds("eng-1");
        showSounds("eng-demo");
        showSounds("spa-1");

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


        //ExcelUtils.saveCharacters(new Yodesk(new ByteBufferKaitaiInputStream("D:\\Working\\_Yoda\\YExplorer\\other\\Yoda Stories (14.02.1997)\\Yodesk.dta")));

        /*XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("The list:");

        ArrayList<String> items = new ArrayList<>(Arrays.asList("One", "Two", "Three"));

        addBulletsList(document, items);

        FileOutputStream out = new FileOutputStream("CreateWordSimplestNumberingList.docx");
        document.write(out);
        out.close();
        document.close();*/

        /*FileInputStream fis = new FileInputStream("CreateWordSimplestNumberingList.docx");
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
        System.out.println(strings);*/
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
}
