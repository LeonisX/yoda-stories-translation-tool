package md.leonis.ystt.model.yodesk.characters;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharFrame extends KaitaiStruct {

    private List<Integer> tiles;

    private final Yodesk root;
    private final Character parent;

    public static CharFrame fromFile(String fileName) throws IOException {
        return new CharFrame(new ByteBufferKaitaiInputStream(fileName));
    }

    public CharFrame(KaitaiInputStream io) {
        this(io, null, null);
    }

    public CharFrame(KaitaiInputStream io, Character parent) {
        this(io, parent, null);
    }

    public CharFrame(KaitaiInputStream io, Character parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        tiles = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            tiles.add(io.readU2le());
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        for (Integer tile : tiles) {
            os.writeU2le(tile);
        }
    }

    public int byteSize() {
        return 2 * 8; // 8 tiles x 2 bytes
    }

    public List<Integer> getTiles() {
        return tiles;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Character getParent() {
        return parent;
    }
}
