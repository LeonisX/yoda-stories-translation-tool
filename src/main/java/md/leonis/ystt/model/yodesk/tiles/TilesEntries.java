package md.leonis.ystt.model.yodesk.tiles;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Tiles;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

public class TilesEntries extends KaitaiStruct {

    private ArrayList<Tile> tiles;

    private final Yodesk root;
    private final Tiles parent;

    public static TilesEntries fromFile(String fileName) throws IOException {
        return new TilesEntries(new ByteBufferKaitaiStream(fileName));
    }

    public TilesEntries(KaitaiStream io) {
        this(io, null, null);
    }

    public TilesEntries(KaitaiStream io, Tiles parent) {
        this(io, parent, null);
    }

    public TilesEntries(KaitaiStream io, Tiles parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.tiles = new ArrayList<>();

        while (!this.io.isEof()) {
            this.tiles.add(new Tile(this.io, this, root));
        }
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Tiles getParent() {
        return parent;
    }
}
