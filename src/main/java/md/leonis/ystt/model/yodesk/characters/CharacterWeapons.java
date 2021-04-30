package md.leonis.ystt.model.yodesk.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.CatalogEntry;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

public class CharacterWeapons extends KaitaiStruct {

    private ArrayList<CharacterWeapon> weapons;

    private final Yodesk root;
    private final CatalogEntry parent;

    public static CharacterWeapons fromFile(String fileName) throws IOException {
        return new CharacterWeapons(new ByteBufferKaitaiStream(fileName));
    }

    public CharacterWeapons(KaitaiStream io) {
        this(io, null, null);
    }

    public CharacterWeapons(KaitaiStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public CharacterWeapons(KaitaiStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.weapons = new ArrayList<>();
        CharacterWeapon _it;
        do {
            _it = new CharacterWeapon(this.io, this, root);
            this.weapons.add(_it);
        } while (_it.getIndex() != 65535);
    }

    public ArrayList<CharacterWeapon> getWeapons() {
        return weapons;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
