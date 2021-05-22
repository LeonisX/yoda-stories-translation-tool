package md.leonis.ystt.model.yodesk.characters;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.CatalogEntry;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharacterWeapons extends KaitaiStruct {

    private List<CharacterWeapon> weapons;

    private final transient Yodesk root;
    private final transient CatalogEntry parent;

    public static CharacterWeapons fromFile(String fileName) throws IOException {
        return new CharacterWeapons(new ByteBufferKaitaiInputStream(fileName));
    }

    public CharacterWeapons(KaitaiInputStream io) {
        this(io, null, null);
    }

    public CharacterWeapons(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public CharacterWeapons(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        weapons = new ArrayList<>();
        CharacterWeapon _it;
        do {
            _it = new CharacterWeapon(io, this, root);
            weapons.add(_it);
        } while (_it.getIndex() != 65535);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        weapons.forEach(w -> w.write(os));
    }

    public List<CharacterWeapon> getWeapons() {
        return weapons;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
