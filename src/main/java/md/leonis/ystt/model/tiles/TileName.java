package md.leonis.ystt.model.tiles;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.TileNames;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TileName extends KaitaiStruct {

    private int tileId;
    private String name;

    private final Yodesk _root;
    private final TileNames _parent;

    public static TileName fromFile(String fileName) throws IOException {
        return new TileName(new ByteBufferKaitaiStream(fileName));
    }

    public TileName(KaitaiStream _io) {
        this(_io, null, null);
    }

    public TileName(KaitaiStream _io, TileNames _parent) {
        this(_io, _parent, null);
    }

    public TileName(KaitaiStream _io, TileNames _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.tileId = this._io.readU2le();
        if (tileId != 65535) {
            this.name = new String(KaitaiStream.bytesTerminate(this._io.readBytes(24), (byte) 0, false), Charset.forName("Cp1252"));
        }
    }

    public int getTileId() {
        return tileId;
    }

    public String getName() {
        return name;
    }

    public Yodesk get_root() {
        return _root;
    }

    public TileNames get_parent() {
        return _parent;
    }
}
