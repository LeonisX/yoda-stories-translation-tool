package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
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
        return new Version(new ByteBufferKaitaiInputStream(fileName));
    }

    public Version(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Version(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Version(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
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

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU4le(ver);
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
