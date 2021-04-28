package md.leonis.ystt.model.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;

public class CharacterWeapon extends KaitaiStruct {

    private int index;
    // If the character referenced by index is a monster, this is a reference to their weapon,
    // otherwise this is the index of the weapon's sound
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
        if (index != 65535) {
            this.reference = this._io.readU2le();
            this.health = this._io.readU2le();
        }
    }

    public int byteSize() {
        return (index == 65535) ? 2 :   // if index == 65535, then we have last CharacterWeapon with FFFF index only
                2 +                     // index
                        2 +             // reference
                        2;              // health
    }

    public int getIndex() {
        return index;
    }

    public Integer getReference() {
        return reference;
    }

    public Integer getHealth() {
        return health;
    }

    public Yodesk get_root() {
        return _root;
    }

    public CharacterWeapons get_parent() {
        return _parent;
    }
}
