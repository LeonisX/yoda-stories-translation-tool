package md.leonis.ystt.model.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;

public class CharacterWeapon extends KaitaiStruct {

    private int index;
    /**
     * If the character referenced by index is a monster, this is a
     * reference to their weapon, otherwise this is the index of the
     * weapon's sound
     */
    private Integer reference;
    private Integer health;
    private final Yodesk _root;
    private final CharacterWeapons _parent;

    public static CharacterWeapon fromFile(String fileName) throws IOException {
        return new CharacterWeapon(new ByteBufferKaitaiStream(fileName));
    }

    public CharacterWeapon(KaitaiStream _io) {
        this(_io, null, null);
    }

    public CharacterWeapon(KaitaiStream _io, CharacterWeapons _parent) {
        this(_io, _parent, null);
    }

    public CharacterWeapon(KaitaiStream _io, CharacterWeapons _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.index = this._io.readU2le();
        if (index() != 65535) {
            this.reference = this._io.readU2le();
        }
        if (index() != 65535) {
            this.health = this._io.readU2le();
        }
    }

    public int index() { return index; }
    public Integer reference() { return reference; }
    public Integer health() { return health; }
    public Yodesk _root() { return _root; }
    public CharacterWeapons _parent() { return _parent; }
}
