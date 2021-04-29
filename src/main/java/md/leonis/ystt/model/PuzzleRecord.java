package md.leonis.ystt.model;

import java.awt.image.BufferedImage;
import java.util.List;

public class PuzzleRecord {

    private String id;
    private List<BufferedImage> images;
    private String type;
    private String original;
    private String translation;

    public PuzzleRecord(String id, List<BufferedImage> images, String type, String original, String translation) {
        this.id = (id == null) ? "" : id;
        this.images = images;
        this.type = type;
        this.original = (original == null) ? "" : original;
        this.translation = (translation == null) ? "" : translation;
    }

    public PuzzleRecord(String id, List<BufferedImage> images, String type, String original) {
        this(id, images, type, original, "");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BufferedImage> getImages() {
        return images;
    }

    public void setImages(List<BufferedImage> images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
