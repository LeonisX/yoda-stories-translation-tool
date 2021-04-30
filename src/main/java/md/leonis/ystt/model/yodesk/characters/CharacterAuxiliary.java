package md.leonis.ystt.model.yodesk.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;

public class CharacterAuxiliary extends KaitaiStruct {

    private int index;
    private Short damage;

    private final Yodesk root;
    private final CharacterAuxiliaries parent;

    public static CharacterAuxiliary fromFile(String fileName) throws IOException {
        return new CharacterAuxiliary(new ByteBufferKaitaiStream(fileName));
    }

    public CharacterAuxiliary(KaitaiStream io) {
        this(io, null, null);
    }

    public CharacterAuxiliary(KaitaiStream io, CharacterAuxiliaries parent) {
        this(io, parent, null);
    }

    public CharacterAuxiliary(KaitaiStream io, CharacterAuxiliaries parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.index = this.io.readU2le();
        if (index != 65535) {
            this.damage = this.io.readS2le();
        }
    }

    public int byteSize() {
        return (index == 65535) ? 2 :   // if index == 65535, then we have last CharacterWeapon with FFFF index only
                2 +                     // index
                        2;              // damage
    }

    public int getIndex() {
        return index;
    }

    public Short getDamage() {
        return damage;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CharacterAuxiliaries getParent() {
        return parent;
    }
}
