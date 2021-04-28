package md.leonis.ystt.model.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.CatalogEntry;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

public class CharacterWeapons extends KaitaiStruct {

    private ArrayList<CharacterWeapon> weapons;

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static CharacterWeapons fromFile(String fileName) throws IOException {
        return new CharacterWeapons(new ByteBufferKaitaiStream(fileName));
    }

    public CharacterWeapons(KaitaiStream _io) {
        this(_io, null, null);
    }

    public CharacterWeapons(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public CharacterWeapons(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.weapons = new ArrayList<>();
        CharacterWeapon _it;
        do {
            _it = new CharacterWeapon(this._io, this, _root);
            this.weapons.add(_it);
        } while (_it.getIndex() != 65535);
    }

    public ArrayList<CharacterWeapon> getWeapons() {
        return weapons;
    }

    public Yodesk get_root() {
        return _root;
    }

    public CatalogEntry get_parent() {
        return _parent;
    }
}
