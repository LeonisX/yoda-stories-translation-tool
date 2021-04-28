package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class UnknownCatalogEntry extends KaitaiStruct {

    private byte[] data;

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static UnknownCatalogEntry fromFile(String fileName) throws IOException {
        return new UnknownCatalogEntry(new ByteBufferKaitaiStream(fileName));
    }

    public UnknownCatalogEntry(KaitaiStream _io) {
        this(_io, null, null);
    }

    public UnknownCatalogEntry(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public UnknownCatalogEntry(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.data = this._io.readBytes(get_parent().getSize());
    }

    public byte[] getData() {
        return data;
    }

    public Yodesk get_root() {
        return _root;
    }

    public CatalogEntry get_parent() {
        return _parent;
    }
}
