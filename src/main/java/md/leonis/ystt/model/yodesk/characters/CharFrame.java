package md.leonis.ystt.model.yodesk.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

public class CharFrame extends KaitaiStruct {

    private ArrayList<Integer> tiles;

    private final Yodesk root;
    private final Character parent;

    public static CharFrame fromFile(String fileName) throws IOException {
        return new CharFrame(new ByteBufferKaitaiStream(fileName));
    }

    public CharFrame(KaitaiStream io) {
        this(io, null, null);
    }

    public CharFrame(KaitaiStream io, Character parent) {
        this(io, parent, null);
    }

    public CharFrame(KaitaiStream io, Character parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        tiles = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            this.tiles.add(this.io.readU2le());
        }
    }

    public int byteSize() {
        return 2 * 8; // 8 tiles x 2 bytes
    }

    public ArrayList<Integer> getTiles() {
        return tiles;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Character getParent() {
        return parent;
    }
}
