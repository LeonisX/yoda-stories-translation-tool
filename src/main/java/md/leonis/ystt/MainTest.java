package md.leonis.ystt;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import md.leonis.ystt.model.yodesk.Yodesk;
import md.leonis.ystt.utils.ExcelUtils;

import java.io.IOException;

public class MainTest {

    public static void main(String[] args) throws IOException {

        ExcelUtils.saveCharacters(new Yodesk(new ByteBufferKaitaiInputStream("D:\\Working\\_Yoda\\YExplorer\\other\\Yoda Stories (14.02.1997)\\Yodesk.dta")));
    }
}
