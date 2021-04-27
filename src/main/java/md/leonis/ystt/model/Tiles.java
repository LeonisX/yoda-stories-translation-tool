package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.tiles.TilesEntries;

import java.io.IOException;

public class Tiles extends KaitaiStruct {

    private TilesEntries tiles;

    private final Yodesk _root;
    private final CatalogEntry _parent;
    private byte[] _raw_tiles;

    public static Tiles fromFile(String fileName) throws IOException {
        return new Tiles(new ByteBufferKaitaiStream(fileName));
    }

    public Tiles(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Tiles(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public Tiles(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this._raw_tiles = this._io.readBytes(_parent().size());
        KaitaiStream _io__raw_tiles = new ByteBufferKaitaiStream(_raw_tiles);
        this.tiles = new TilesEntries(_io__raw_tiles, this, _root);
    }

    public TilesEntries tiles() { return tiles; }
    public Yodesk _root() { return _root; }
    public CatalogEntry _parent() { return _parent; }
    public byte[] _raw_tiles() { return _raw_tiles; }
}
