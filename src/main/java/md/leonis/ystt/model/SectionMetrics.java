package md.leonis.ystt.model;

//TODO movable too
public class SectionMetrics {

    private final KnownSections section;
    private final int dataSize;
    private final int fullSize;
    private final int startOffset;
    private final int dataOffset;

    public SectionMetrics(KnownSections section, int dataSize, int fullSize, int startOffset, int dataOffset) {

        this.section = section;
        this.dataSize = dataSize;
        this.fullSize = fullSize;
        this.startOffset = startOffset;
        this.dataOffset = dataOffset;
    }

    public KnownSections getSection() {
        return section;
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
