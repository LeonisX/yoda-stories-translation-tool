package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class Version extends KaitaiStruct {

    /**
     * Version of the file. This value is always set to 512.
     */
    private long ver;
    private String version;

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
        this.ver = this._io.readU4le();
        if (ver == 0x0200) {
            version = "2.0";
        } else {
            version = "x.x";
        }
    }

    public long getVer() {
        return ver;
    }

    public String getVersion() {
        return version;
    }

    public Yodesk get_root() {
        return _root;
    }

    public CatalogEntry get_parent() {
        return _parent;
    }
}
