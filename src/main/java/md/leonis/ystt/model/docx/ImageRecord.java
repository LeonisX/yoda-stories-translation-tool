package md.leonis.ystt.model.docx;

import java.awt.image.BufferedImage;
import java.util.List;

//TODO may be merge with StringImageRecord
public class ImageRecord {

    private List<BufferedImage> images;
    private String original;
    private String translation;

    public ImageRecord(List<BufferedImage> images, String original, String translation) {
        this.images = images;
        this.original = (original == null) ? "" : original;
        this.translation = (translation == null) ? "" : translation;
    }

    public ImageRecord(List<BufferedImage> images, String original) {
        this(images, original, "");
    }

    public List<BufferedImage> getImages() {
        return images;
    }

    public void setImages(List<BufferedImage> images) {
        this.images = images;
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
