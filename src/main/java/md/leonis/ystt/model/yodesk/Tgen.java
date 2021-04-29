package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class Tgen extends KaitaiStruct {

    private byte[] _raw_data;

    private final Yodesk root;
    private final CatalogEntry parent;

    public static Tgen fromFile(String fileName) throws IOException {
        return new Tgen(new ByteBufferKaitaiStream(fileName));
    }

    public Tgen(KaitaiStream io) {
        this(io, null, null);
    }

    public Tgen(KaitaiStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Tgen(KaitaiStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this._raw_data = this.io.readBytes(getParent().getSize());
    }

    public byte[] get_raw_data() {
        return _raw_data;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
