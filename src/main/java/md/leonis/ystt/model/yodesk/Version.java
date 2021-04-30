package md.leonis.ystt.model.yodesk;

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

    private final Yodesk root;
    private final CatalogEntry parent;

    public static Version fromFile(String fileName) throws IOException {
        return new Version(new ByteBufferKaitaiStream(fileName));
    }

    public Version(KaitaiStream io) {
        this(io, null, null);
    }

    public Version(KaitaiStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Version(KaitaiStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.ver = this.io.readU4le();
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

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
