package md.leonis.ystt.model.docx;

import md.leonis.ystt.model.yodesk.Yodesk;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;

import java.math.BigInteger;
import java.util.List;

public enum PropertyName {

    CRC32("CRC32"),
    SRC_CHARSET("Source character encoding"),
    DST_CHARSET("Destination character encoding");

    private final String text;

    PropertyName(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getTextHeader() {
        return text + ": ";
    }
}
