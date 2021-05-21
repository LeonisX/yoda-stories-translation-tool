package md.leonis.ystt.model.yodesk.tiles;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class Tile extends KaitaiStruct {

    private TileAttributes attributes;
    private byte[] pixels;

    private final Yodesk root;
    private final TilesEntries parent;
    private byte[] rawAttributes;

    public Tile(TilesEntries parent, Yodesk root) {
        super(null);
        this.parent = parent;
        this.root = root;
        this.rawAttributes = "\u0000\u0000\u0000\u0000".getBytes();
        this.attributes = new TileAttributes(this, root);
        this.pixels = new byte[32 * 32];
    }

    public Tile(byte[] pixels, TilesEntries parent, Yodesk root) {
        this(parent, root);
        this.pixels = pixels;
    }

    public static Tile fromFile(String fileName) throws IOException {
        return new Tile(new ByteBufferKaitaiInputStream(fileName));
    }

    public Tile(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Tile(KaitaiInputStream io, TilesEntries parent) {
        this(io, parent, null);
    }

    public Tile(KaitaiInputStream io, TilesEntries parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        rawAttributes = io.readBytes(4);
        processRawAttributes();
        pixels = io.readBytes(32 * 32);
    }

    public void processRawAttributes() {
        KaitaiInputStream inputStream = new ByteBufferKaitaiInputStream(rawAttributes);
        attributes = new TileAttributes(inputStream, this, root);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        // unneeded, we write raw bytes in Tiles class
    }

    public int byteSize() {
        return rawAttributes.length +     // _raw_attributes
                pixels.length;              // pixels
    }

    public TileAttributes getAttributes() {
        return attributes;
    }

    public byte[] getPixels() {
        return pixels;
    }

    public void setPixels(byte[] pixels) {
        this.pixels = pixels;
    }

    public Yodesk getRoot() {
        return root;
    }

    public TilesEntries getParent() {
        return parent;
    }

    public byte[] getRawAttributes() {
        return rawAttributes;
    }

    public String getAttributesBinaryString() {

        StringBuilder sb = new StringBuilder();

        for (byte b : rawAttributes) {
            sb.insert(0, StringUtils.right(StringUtils.leftPad(Integer.toBinaryString((int) b & 0xFF), 8, '0'), 8));
        }

        return sb.toString();
    }
}
