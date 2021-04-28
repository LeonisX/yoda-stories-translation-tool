package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class Tgen extends KaitaiStruct {

    private byte[] _raw_data;

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static Tgen fromFile(String fileName) throws IOException {
        return new Tgen(new ByteBufferKaitaiStream(fileName));
    }

    public Tgen(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Tgen(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public Tgen(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this._raw_data = this._io.readBytes(_parent().getSize());
    }

    public byte[] pixels() { return _raw_data; }
    public Yodesk _root() { return _root; }
    public CatalogEntry _parent() { return _parent; }
}
