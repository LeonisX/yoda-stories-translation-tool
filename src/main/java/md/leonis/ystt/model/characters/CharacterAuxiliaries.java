package md.leonis.ystt.model.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.CatalogEntry;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

public class CharacterAuxiliaries extends KaitaiStruct {

    private ArrayList<CharacterAuxiliary> auxiliaries;

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static CharacterAuxiliaries fromFile(String fileName) throws IOException {
        return new CharacterAuxiliaries(new ByteBufferKaitaiStream(fileName));
    }

    public CharacterAuxiliaries(KaitaiStream _io) {
        this(_io, null, null);
    }

    public CharacterAuxiliaries(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public CharacterAuxiliaries(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.auxiliaries = new ArrayList<>();
        {
            CharacterAuxiliary _it;
            int i = 0;
            do {
                _it = new CharacterAuxiliary(this._io, this, _root);
                this.auxiliaries.add(_it);
                i++;
            } while (!(_it.index() == 65535));
        }
    }

    public ArrayList<CharacterAuxiliary> auxiliaries() { return auxiliaries; }
    public Yodesk _root() { return _root; }
    public CatalogEntry _parent() { return _parent; }
}
