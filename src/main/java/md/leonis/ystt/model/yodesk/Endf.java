package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class Endf extends KaitaiStruct {

    private final Yodesk root;
    private final CatalogEntry parent;

    public static Endf fromFile(String fileName) throws IOException {
        return new Endf(new ByteBufferKaitaiInputStream(fileName));
    }

    public Endf(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Endf(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Endf(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
    }

    @Override
    public void write(KaitaiOutputStream os) {
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
