package md.leonis.ystt.model.docx;

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
