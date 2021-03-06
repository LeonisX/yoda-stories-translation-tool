package md.leonis.ystt.model.docx;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class StringImagesRecord extends StringRecord {

    private List<Integer> tileIds = new ArrayList<>();
    private List<BufferedImage> images;

    public StringImagesRecord(String id, List<BufferedImage> images, String original, String translation) {
        super(id, original, translation);
        this.images = images;
    }

    public StringImagesRecord(String id, List<BufferedImage> images, String original) {
        this(id, images, original, "");
    }

    public StringImagesRecord(int id, List<BufferedImage> images, String original) {
        this(String.valueOf(id), images, original, "");
    }

    public List<BufferedImage> getImages() {
        return images;
    }

    public void setImages(List<BufferedImage> images) {
        this.images = images;
    }

    public List<Integer> getTileIds() {
        return tileIds;
    }

    public void setTileIds(List<Integer> tileIds) {
        this.tileIds = tileIds;
    }
}
