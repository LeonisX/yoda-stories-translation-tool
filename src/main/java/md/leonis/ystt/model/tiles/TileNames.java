package md.leonis.ystt.model.tiles;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.CatalogEntry;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

public class TileNames extends KaitaiStruct {

    /**
     * List of tile ids and their corresponding names. These are shown in the
     * inventory or used in dialogs (see `speak_hero` and `speak_npc` opcodes).
     */
    private ArrayList<TileName> names;

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static TileNames fromFile(String fileName) throws IOException {
        return new TileNames(new ByteBufferKaitaiStream(fileName));
    }

    public TileNames(KaitaiStream _io) {
        this(_io, null, null);
    }

    public TileNames(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public TileNames(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.names = new ArrayList<>();

        TileName _it;
        do {
            _it = new TileName(this._io, this, _root);
            this.names.add(_it);

        } while (_it.tileId() != 65535);
    }

    public ArrayList<TileName> names() {
        return names;
    }

    public Yodesk _root() {
        return _root;
    }

    public CatalogEntry _parent() {
        return _parent;
    }
}
