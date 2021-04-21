package md.leonis.ystt.model;

public class SectionMetrics {

    private final int dataSize;
    private final int fullSize;
    private final int dataOffset;
    private final int startOffset;

    public SectionMetrics(int dataSize, int fullSize, int dataOffset, int startOffset) {
        this.dataSize = dataSize;
        this.fullSize = fullSize;
        this.dataOffset = dataOffset;
        this.startOffset = startOffset;
    }

    public int getDataSize() {
        return dataSize;
    }

    public int getFullSize() {
        return fullSize;
    }

    public int getDataOffset() {
        return dataOffset;
    }

    public int getStartOffset() {
        return startOffset;
    }
}
