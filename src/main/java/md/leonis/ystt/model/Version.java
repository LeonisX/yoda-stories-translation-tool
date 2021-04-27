package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class Version extends KaitaiStruct {

    /**
     * Version of the file. This value is always set to 512.
     */
    private long version;

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static Version fromFile(String fileName) throws IOException {
        return new Version(new ByteBufferKaitaiStream(fileName));
    }

    public Version(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Version(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public Version(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.version = this._io.readU4le();
    }

    public long version() {
        return version;
    }

    public Yodesk _root() {
        return _root;
    }

    public CatalogEntry _parent() {
        return _parent;
    }
}
