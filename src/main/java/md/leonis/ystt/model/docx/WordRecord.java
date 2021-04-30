package md.leonis.ystt.model.docx;

import java.util.List;

public class WordRecord {

    private List<String> strings;
    private List<StringRecord> stringRecords;

    public WordRecord(List<String> strings, List<StringRecord> stringRecords) {
        this.strings = strings;
        this.stringRecords = stringRecords;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<StringRecord> getStringRecords() {
        return stringRecords;
    }

    public void setStringRecords(List<StringRecord> stringRecords) {
        this.stringRecords = stringRecords;
    }
}
