package md.leonis.ystt.model;

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

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static StartupImage fromFile(String fileName) throws IOException {
        return new StartupImage(new ByteBufferKaitaiStream(fileName));
    }

    public StartupImage(KaitaiStream _io) {
        this(_io, null, null);
    }

    public StartupImage(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public StartupImage(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.pixels = this._io.readBytes(get_parent().getSize());
    }

    public byte[] getPixels() {
        return pixels;
    }

    public Yodesk get_root() {
        return _root;
    }

    public CatalogEntry get_parent() {
        return _parent;
    }
}
