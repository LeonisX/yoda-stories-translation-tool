package md.leonis.ystt.model.yodesk.characters;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;

public class CharacterWeapon extends KaitaiStruct {

    private int index;
    // If the character referenced by index is a monster,
    // this is a reference to their weapon (Darth Vader: 69 Evil Force, 9 Blaster, 11 Imperial Blaster),
    // otherwise this is the index of the weapon's sound
    private Integer reference;
    private Integer health;

    private final Yodesk root;
    private final CharacterWeapons parent;

    public static CharacterWeapon fromFile(String fileName) throws IOException {
        return new CharacterWeapon(new ByteBufferKaitaiInputStream(fileName));
    }

    public CharacterWeapon(KaitaiInputStream io) {
        this(io, null, null);
    }

    public CharacterWeapon(KaitaiInputStream io, CharacterWeapons parent) {
        this(io, parent, null);
    }

    public CharacterWeapon(KaitaiInputStream io, CharacterWeapons parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        index = io.readU2le();
        if (index != 65535) {
            reference = io.readU2le();
            health = io.readU2le();
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU2le(index);
        if (index != 65535) {
            os.writeU2le(reference);
            os.writeU2le(health);
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

    public Yodesk getRoot() {
        return root;
    }

    public CharacterWeapons getParent() {
        return parent;
    }
}
