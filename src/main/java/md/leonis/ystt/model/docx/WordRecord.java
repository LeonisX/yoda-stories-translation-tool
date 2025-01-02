package md.leonis.ystt.model.docx;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordRecord {

    private List<String> strings;
    private List<String> numbers;
    private List<StringRecord> stringRecords;

    private Map<PropertyName, String> props;

    public WordRecord(List<String> strings, List<String> numbers, Map<PropertyName, String> props, List<StringRecord> stringRecords) {
        this.strings = strings;
        this.numbers = numbers;
        this.props = props;
        this.stringRecords = stringRecords;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public List<StringRecord> getStringRecords() {
        return stringRecords;
    }

    public List<StringImagesRecord> getStringImageRecords() {
        return stringRecords.stream().map(s -> new StringImagesRecord(s.getId(), Collections.emptyList(), s.getOriginal(), s.getTranslation())).collect(Collectors.toList());
    }

    public void setStringRecords(List<StringRecord> stringRecords) {
        this.stringRecords = stringRecords;
    }

    public Map<PropertyName, String> getProps() {
        return props;
    }

    public void setProps(Map<PropertyName, String> props) {
        this.props = props;
    }
}
