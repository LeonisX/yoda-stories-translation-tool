package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class Endf extends KaitaiStruct {

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static Endf fromFile(String fileName) throws IOException {
        return new Endf(new ByteBufferKaitaiStream(fileName));
    }

    public Endf(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Endf(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public Endf(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
    }

    public Yodesk _root() { return _root; }
    public CatalogEntry _parent() { return _parent; }
}
