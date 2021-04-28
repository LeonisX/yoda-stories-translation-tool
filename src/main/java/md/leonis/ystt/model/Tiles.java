package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.tiles.Tile;
import md.leonis.ystt.model.tiles.TilesEntries;

import java.io.IOException;
import java.util.ArrayList;

public class Tiles extends KaitaiStruct {

    private TilesEntries tilesEntries;
    private ArrayList<Tile> tiles;
    private byte[] rawTiles;

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static Tiles fromFile(String fileName) throws IOException {
        return new Tiles(new ByteBufferKaitaiStream(fileName));
    }

    public Tiles(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Tiles(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public Tiles(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.rawTiles = this._io.readBytes(get_parent().getSize());
        KaitaiStream _io_raw_tiles = new ByteBufferKaitaiStream(rawTiles);
        this.tilesEntries = new TilesEntries(_io_raw_tiles, this, _root);
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

    public Yodesk get_root() {
        return _root;
    }

    public CatalogEntry get_parent() {
        return _parent;
    }
}
