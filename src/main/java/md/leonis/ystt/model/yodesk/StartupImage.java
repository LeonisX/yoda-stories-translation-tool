package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

/**
 * A 288x288 bitmap to be shown while other assets are loaded and a new
 * world is generated.
 */
public class StartupImage extends KaitaiStruct {

    private byte[] pixels;

    private final Yodesk root;
    private final CatalogEntry parent;

    public static StartupImage fromFile(String fileName) throws IOException {
        return new StartupImage(new ByteBufferKaitaiStream(fileName));
    }

    public StartupImage(KaitaiStream io) {
        this(io, null, null);
    }

    public StartupImage(KaitaiStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public StartupImage(KaitaiStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.pixels = this.io.readBytes(getParent().getSize());
    }

    public byte[] getPixels() {
        return pixels;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}