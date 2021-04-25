package md.leonis.ystt.model;

public class Release {

    private String id;
    private String title;
    private String exeCrc32;
    private String dtaCrc32;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExeCrc32() {
        return exeCrc32;
    }

    public void setExeCrc32(String exeCrc32) {
        this.exeCrc32 = exeCrc32;
    }

    public String getDtaCrc32() {
        return dtaCrc32;
    }

    public void setDtaCrc32(String dtaCrc32) {
        this.dtaCrc32 = dtaCrc32;
    }
}
