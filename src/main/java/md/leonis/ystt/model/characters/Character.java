package md.leonis.ystt.model.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Characters;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Character extends KaitaiStruct {

    private int index;
    private byte[] marker;
    private Long size;
    private String name;
    private CharacterType type;
    private MovementType movementType;
    private Integer probablyGarbage1;
    private Long probablyGarbage2;
    private CharFrame frame1;
    private CharFrame frame2;
    private CharFrame frame3;

    private final Yodesk _root;
    private final Characters _parent;

    public static Character fromFile(String fileName) throws IOException {
        return new Character(new ByteBufferKaitaiStream(fileName));
    }

    public Character(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Character(KaitaiStream _io, Characters _parent) {
        this(_io, _parent, null);
    }

    public Character(KaitaiStream _io, Characters _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.index = this._io.readU2le();

        if (index() != 65535) {
            this.marker = this._io.readBytes(4);

            if (!(Arrays.equals(marker(), new byte[]{73, 67, 72, 65}))) { // ICHA
                throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 67, 72, 65}, marker(), _io(), "/types/character/seq/1");
            }

            this.size = this._io.readU4le();
            this.name = new String(KaitaiStream.bytesTerminate(this._io.readBytes(16), (byte) 0, false), StandardCharsets.US_ASCII);
            this.type = CharacterType.byId(this._io.readU2le());
            this.movementType = MovementType.byId(this._io.readU2le());
            this.probablyGarbage1 = this._io.readU2le();
            this.probablyGarbage2 = this._io.readU4le();
            this.frame1 = new CharFrame(this._io, this, _root);
            this.frame2 = new CharFrame(this._io, this, _root);
            this.frame3 = new CharFrame(this._io, this, _root);
        }
    }

    public int index() {
        return index;
    }

    public byte[] marker() {
        return marker;
    }

    public Long size() {
        return size;
    }

    public String name() {
        return name;
    }

    public CharacterType type() {
        return type;
    }

    public MovementType movementType() {
        return movementType;
    }

    public Integer probablyGarbage1() {
        return probablyGarbage1;
    }

    public Long probablyGarbage2() {
        return probablyGarbage2;
    }

    public CharFrame frame1() {
        return frame1;
    }

    public CharFrame frame2() {
        return frame2;
    }

    public CharFrame frame3() {
        return frame3;
    }

    public Yodesk _root() {
        return _root;
    }

    public Characters _parent() {
        return _parent;
    }
}
