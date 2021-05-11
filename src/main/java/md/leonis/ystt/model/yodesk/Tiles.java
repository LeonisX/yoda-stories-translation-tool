package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
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
        return new Tiles(new ByteBufferKaitaiInputStream(fileName));
    }

    public Tiles(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Tiles(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Tiles(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        rawTiles = io.readBytes(getParent().getSize());
        KaitaiInputStream kis = new ByteBufferKaitaiInputStream(rawTiles);
        tilesEntries = new TilesEntries(kis, this, root);
        tiles = tilesEntries.getTiles();
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeBytesFull(rawTiles);
    }

    public int tilePosition(int tileId) {

        int position = 0;
        for (int i = 0; i < tileId; i++) {
            position += tiles.get(i).byteSize();
        }
        return position;
    }

    public int getTilePixelsPosition(int tileId) {
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
        parent.setSize(rawTiles.length);
    }

    public void deleteTile() {

        rawTiles = Arrays.copyOf(rawTiles, rawTiles.length - tiles.get(0).byteSize());
        tilesEntries.getTiles().remove(tilesEntries.getTiles().size() - 1);
        parent.setSize(rawTiles.length);
    }

    public void replaceTile(int tileId, byte[] pixels) {

        getTiles().get(tileId).setPixels(pixels);

        int offset = getTilePixelsPosition(tileId);
        System.arraycopy(pixels, 0, rawTiles, offset, pixels.length);
    }
}
