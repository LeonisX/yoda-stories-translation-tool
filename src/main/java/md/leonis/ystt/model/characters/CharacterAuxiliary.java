package md.leonis.ystt.model.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;

public class CharacterAuxiliary extends KaitaiStruct {

    private int index;
    private Short damage;

    private final Yodesk _root;
    private final CharacterAuxiliaries _parent;

    public static CharacterAuxiliary fromFile(String fileName) throws IOException {
        return new CharacterAuxiliary(new ByteBufferKaitaiStream(fileName));
    }

    public CharacterAuxiliary(KaitaiStream _io) {
        this(_io, null, null);
    }

    public CharacterAuxiliary(KaitaiStream _io, CharacterAuxiliaries _parent) {
        this(_io, _parent, null);
    }

    public CharacterAuxiliary(KaitaiStream _io, CharacterAuxiliaries _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.index = this._io.readU2le();
        if (index() != 65535) {
            this.damage = this._io.readS2le();
        }
    }

    public int index() { return index; }
    public Short damage() { return damage; }
    public Yodesk _root() { return _root; }
    public CharacterAuxiliaries _parent() { return _parent; }
}
