package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.tiles.Tile;
import md.leonis.ystt.model.yodesk.tiles.TilesEntries;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Tiles extends KaitaiStruct {

    private TilesEntries tilesEntries;
    private List<Tile> tiles;
    private byte[] rawTiles;

    private final transient Yodesk root;
    private final transient CatalogEntry parent;

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
        return tilePosition(tileId) + tiles.get(tileId).getRawAttributes().length;
    }

    public TilesEntries getTilesEntries() {
        return tilesEntries;
    }

    public List<Tile> getTiles() {
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

    public void addTile(String binaryString) {
        rawTiles = Arrays.copyOf(rawTiles, rawTiles.length + tiles.get(0).byteSize());
        tilesEntries.getTiles().add(new Tile(tilesEntries, root));
        setAttributes(tiles.size() - 1, binaryString);
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

    public void setAttributes(int tileId, String binaryString) {
        Tile tile = tiles.get(tileId);
        byte[] rawAttributes = tile.getRawAttributes();

        for (int i = 0; i < binaryString.length() / 8; i++) {
            rawAttributes[rawAttributes.length - i - 1] = Integer.valueOf(binaryString.substring(i * 8, i * 8 + 8), 2).byteValue();
        }
        tile.processRawAttributes();

        System.arraycopy(rawAttributes, 0, rawTiles, tileId * tile.byteSize(), tile.getRawAttributes().length);
    }
}
