package md.leonis.ystt;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainTest {

    public static void main(String[] args) throws IOException {

        byte[] bytes = new byte[256];

        for (int i = 128; i < 256; i++) {
            bytes[i] = (byte) i;
        }

        System.out.println("ASCII:  " + new String(bytes, StandardCharsets.US_ASCII));
        System.out.println("Cp1252: " + new String(bytes, Charset.forName("Cp1252")));
        System.out.println("Cp1250: " + new String(bytes, Charset.forName("Cp1250")));
        System.out.println("Cp1251: " + new String(bytes, Charset.forName("Cp1251")));


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


}
