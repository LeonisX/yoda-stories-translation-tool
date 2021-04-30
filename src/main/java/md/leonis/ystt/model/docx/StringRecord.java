package md.leonis.ystt.model.docx;

public class StringRecord {

    private String id;
    private String original;
    private String translation;

    public StringRecord(String id, String original, String translation) {
        this.id = (id == null) ? "" : id;
        this.original = (original == null) ? "" : original;
        this.translation = (translation == null) ? "" : translation;
    }

    public StringRecord(Integer id, String original) {
        this(id.toString(), original, "");
    }

    public StringRecord(String id, String original) {
        this(id, original, "");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
