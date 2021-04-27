package md.leonis.ystt.model.puzzles;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Puzzles;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Puzzle extends KaitaiStruct {

    private int index;
    private byte[] marker;
    private Long size;
    private Long type;
    private Long _unnamed4;
    private Long _unnamed5;
    private Integer _unnamed6;
    private ArrayList<PrefixedStr> strings;
    private Integer item1;
    private Integer item2;

    private final Yodesk _root;
    private final Puzzles _parent;

    public static Puzzle fromFile(String fileName) throws IOException {
        return new Puzzle(new ByteBufferKaitaiStream(fileName));
    }

    public Puzzle(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Puzzle(KaitaiStream _io, Puzzles _parent) {
        this(_io, _parent, null);
    }

    public Puzzle(KaitaiStream _io, Puzzles _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {

        this.index = this._io.readU2le();

        if (index() != 65535) {
            this.marker = this._io.readBytes(4);

            if (!(Arrays.equals(marker(), new byte[]{73, 80, 85, 90}))) { // IPUZ
                throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 80, 85, 90}, marker(), _io(), "/types/puzzle/seq/1");
            }

            this.size = this._io.readU4le();
            this.type = this._io.readU4le();
            this._unnamed4 = this._io.readU4le();
            this._unnamed5 = this._io.readU4le();
            this._unnamed6 = this._io.readU2le();

            strings = new ArrayList<>(5);
            for (int i = 0; i < 5; i++) {
                this.strings.add(new PrefixedStr(this._io, this, _root));
            }

            this.item1 = this._io.readU2le();
            this.item2 = this._io.readU2le();
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

    public Long type() {
        return type;
    }

    public Long _unnamed4() {
        return _unnamed4;
    }

    public Long _unnamed5() {
        return _unnamed5;
    }

    public Integer _unnamed6() {
        return _unnamed6;
    }

    public ArrayList<PrefixedStr> strings() {
        return strings;
    }

    public Integer item1() {
        return item1;
    }

    public Integer item2() {
        return item2;
    }

    public Yodesk _root() {
        return _root;
    }

    public Puzzles _parent() {
        return _parent;
    }
}
