package md.leonis.ystt.model.yodesk.tiles;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;

public class Tile extends KaitaiStruct {

    private TileAttributes attributes;
    private byte[] pixels;

    private final Yodesk root;
    private final TilesEntries parent;
    private byte[] _raw_attributes;

    public Tile(TilesEntries parent, Yodesk root) {
        super(null);
        this.parent = parent;
        this.root = root;
        this._raw_attributes = "\u0000\u0000\u0000\u0000".getBytes();
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
        this._raw_attributes = this.io.readBytes(4);
        KaitaiInputStream io_raw_attributes = new ByteBufferKaitaiInputStream(_raw_attributes);
        this.attributes = new TileAttributes(io_raw_attributes, this, root);
        this.pixels = this.io.readBytes(32 * 32);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        // unneeded, we write raw bytes in Tiles class
    }

    public int byteSize() {
        return _raw_attributes.length +     // _raw_attributes
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

    public byte[] get_raw_attributes() {
        return _raw_attributes;
    }
}
