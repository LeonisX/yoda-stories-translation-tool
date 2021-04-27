package md.leonis.ystt.model.tiles;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Tiles;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

public class TilesEntries extends KaitaiStruct {

    private ArrayList<Tile> tiles;
    private final Yodesk _root;
    private final Tiles _parent;

    public static TilesEntries fromFile(String fileName) throws IOException {
        return new TilesEntries(new ByteBufferKaitaiStream(fileName));
    }

    public TilesEntries(KaitaiStream _io) {
        this(_io, null, null);
    }

    public TilesEntries(KaitaiStream _io, Tiles _parent) {
        this(_io, _parent, null);
    }

    public TilesEntries(KaitaiStream _io, Tiles _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.tiles = new ArrayList<>();

        while (!this._io.isEof()) {
            this.tiles.add(new Tile(this._io, this, _root));
        }
    }

    public ArrayList<Tile> tiles() {
        return tiles;
    }

    public Yodesk _root() {
        return _root;
    }

    public Tiles _parent() {
        return _parent;
    }
}
