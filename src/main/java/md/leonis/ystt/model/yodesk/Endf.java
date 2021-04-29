package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class Endf extends KaitaiStruct {

    private final Yodesk root;
    private final CatalogEntry parent;

    public static Endf fromFile(String fileName) throws IOException {
        return new Endf(new ByteBufferKaitaiStream(fileName));
    }

    public Endf(KaitaiStream io) {
        this(io, null, null);
    }

    public Endf(KaitaiStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Endf(KaitaiStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
