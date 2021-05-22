package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

/**
 * The TGEN section is only present in non-english versions of the game.
 * It's purpose or internal structure is unknown.
 */
public class Tgen extends KaitaiStruct {

    private byte[] rawData;

    private final transient Yodesk root;
    private final transient CatalogEntry parent;

    public static Tgen fromFile(String fileName) throws IOException {
        return new Tgen(new ByteBufferKaitaiInputStream(fileName));
    }

    public Tgen(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Tgen(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Tgen(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
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
