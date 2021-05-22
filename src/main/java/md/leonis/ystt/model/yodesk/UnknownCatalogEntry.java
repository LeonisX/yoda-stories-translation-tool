package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class UnknownCatalogEntry extends KaitaiStruct {

    private byte[] rawData;

    private final transient Yodesk root;
    private final transient CatalogEntry parent;

    public static UnknownCatalogEntry fromFile(String fileName) throws IOException {
        return new UnknownCatalogEntry(new ByteBufferKaitaiInputStream(fileName));
    }

    public UnknownCatalogEntry(KaitaiInputStream io) {
        this(io, null, null);
    }

    public UnknownCatalogEntry(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public UnknownCatalogEntry(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        rawData = io.readBytes(getParent().getSize());
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeBytesFull(rawData);
    }

    public byte[] getRawData() {
        return rawData;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
