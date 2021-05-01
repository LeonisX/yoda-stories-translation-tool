package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.tiles.Tile;
import md.leonis.ystt.model.yodesk.tiles.TilesEntries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Tiles extends KaitaiStruct {

    private TilesEntries tilesEntries;
    private ArrayList<Tile> tiles;
    private byte[] rawTiles;

    private final Yodesk root;
    private final CatalogEntry parent;

    public static Tiles fromFile(String fileName) throws IOException {
        return new Tiles(new ByteBufferKaitaiStream(fileName));
    }

    public Tiles(KaitaiStream io) {
        this(io, null, null);
    }

    public Tiles(KaitaiStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Tiles(KaitaiStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.rawTiles = this.io.readBytes(getParent().getSize());
        KaitaiStream io_raw_tiles = new ByteBufferKaitaiStream(rawTiles);
        this.tilesEntries = new TilesEntries(io_raw_tiles, this, root);
        this.tiles = tilesEntries.getTiles();
    }

    public int tilePosition(int tileId) {

        int position = 0;
        for (int i = 0; i < tileId; i++) {
            position += tiles.get(i).byteSize();
        }
        return position;
    }

    public int tilePixelsPosition(int tileId) {
        return tilePosition(tileId) + tiles.get(tileId).get_raw_attributes().length;
    }

    public TilesEntries getTilesEntries() {
        return tilesEntries;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public byte[] getRawTiles() {
        return rawTiles;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }

    public void addTile() {

        rawTiles = Arrays.copyOf(rawTiles, rawTiles.length + tiles.get(0).byteSize());
        tilesEntries.getTiles().add(new Tile(tilesEntries, root));
    }
}
