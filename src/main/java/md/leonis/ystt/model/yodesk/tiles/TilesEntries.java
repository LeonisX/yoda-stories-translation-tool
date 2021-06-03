package md.leonis.ystt.model.yodesk.tiles;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Tiles;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TilesEntries extends KaitaiStruct {

    private List<Tile> tiles;

    private final transient Yodesk root;
    private final transient Tiles parent;

    public static TilesEntries fromFile(String fileName) throws IOException {
        return new TilesEntries(new ByteBufferKaitaiInputStream(fileName));
    }

    public TilesEntries(KaitaiInputStream io) {
        this(io, null, null);
    }

    public TilesEntries(KaitaiInputStream io, Tiles parent) {
        this(io, parent, null);
    }

    public TilesEntries(KaitaiInputStream io, Tiles parent, Yodesk root) {
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

    @Override
    public void write(KaitaiOutputStream os) {
        // unneeded, we write raw bytes in Tiles class
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Tiles getParent() {
        return parent;
    }
}
