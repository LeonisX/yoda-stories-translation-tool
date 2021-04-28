package md.leonis.ystt.model.tiles;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;

public class Tile extends KaitaiStruct {

    private TileAttributes attributes;
    private byte[] pixels;

    private final Yodesk _root;
    private final TilesEntries _parent;
    private byte[] _raw_attributes;

    public static Tile fromFile(String fileName) throws IOException {
        return new Tile(new ByteBufferKaitaiStream(fileName));
    }

    public Tile(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Tile(KaitaiStream _io, TilesEntries _parent) {
        this(_io, _parent, null);
    }

    public Tile(KaitaiStream _io, TilesEntries _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this._raw_attributes = this._io.readBytes(4);
        KaitaiStream _io_raw_attributes = new ByteBufferKaitaiStream(_raw_attributes);
        this.attributes = new TileAttributes(_io_raw_attributes, this, _root);
        this.pixels = this._io.readBytes(32 * 32);
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

    public Yodesk get_root() {
        return _root;
    }

    public TilesEntries get_parent() {
        return _parent;
    }

    public byte[] get_raw_attributes() {
        return _raw_attributes;
    }
}
