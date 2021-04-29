package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class UnknownCatalogEntry extends KaitaiStruct {

    private byte[] data;

    private final Yodesk root;
    private final CatalogEntry parent;

    public static UnknownCatalogEntry fromFile(String fileName) throws IOException {
        return new UnknownCatalogEntry(new ByteBufferKaitaiStream(fileName));
    }

    public UnknownCatalogEntry(KaitaiStream io) {
        this(io, null, null);
    }

    public UnknownCatalogEntry(KaitaiStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public UnknownCatalogEntry(KaitaiStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.data = this.io.readBytes(getParent().getSize());
    }

    public byte[] getData() {
        return data;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
