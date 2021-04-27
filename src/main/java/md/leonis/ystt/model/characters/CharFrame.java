package md.leonis.ystt.model.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

public class CharFrame extends KaitaiStruct {

    private ArrayList<Integer> tiles;

    private final Yodesk _root;
    private final Character _parent;

    public static CharFrame fromFile(String fileName) throws IOException {
        return new CharFrame(new ByteBufferKaitaiStream(fileName));
    }

    public CharFrame(KaitaiStream _io) {
        this(_io, null, null);
    }

    public CharFrame(KaitaiStream _io, Character _parent) {
        this(_io, _parent, null);
    }

    public CharFrame(KaitaiStream _io, Character _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        tiles = new ArrayList<>(((Number) (8)).intValue());
        for (int i = 0; i < 8; i++) {
            this.tiles.add(this._io.readU2le());
        }
    }

    public ArrayList<Integer> tiles() { return tiles; }
    public Yodesk _root() { return _root; }
    public Character _parent() { return _parent; }
}
