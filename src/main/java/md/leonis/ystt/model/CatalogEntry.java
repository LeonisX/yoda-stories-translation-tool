package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.characters.CharacterAuxiliaries;
import md.leonis.ystt.model.characters.CharacterWeapons;
import md.leonis.ystt.model.tiles.TileNames;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CatalogEntry extends KaitaiStruct {

    private String type;
    private Long size;
    private KaitaiStruct content;

    private final Yodesk _root;
    private final Yodesk _parent;

    public static CatalogEntry fromFile(String fileName) throws IOException {
        return new CatalogEntry(new ByteBufferKaitaiStream(fileName));
    }

    public CatalogEntry(KaitaiStream _io) {
        this(_io, null, null);
    }

    public CatalogEntry(KaitaiStream _io, Yodesk _parent) {
        this(_io, _parent, null);
    }

    public CatalogEntry(KaitaiStream _io, Yodesk _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {

        this.type = new String(this._io.readBytes(4), StandardCharsets.US_ASCII);
        if ( ((!(type()).equals("VERS")) && (!(type()).equals("ZONE"))) ) {
            this.size = this._io.readU4le();
        }
        switch (type()) {
            case "SNDS": {
                this.content = new Sounds(this._io, this, _root);
                break;
            }
            case "PUZ2": {
                this.content = new Puzzles(this._io, this, _root);
                break;
            }
            case "STUP": {
                this.content = new SetupImage(this._io, this, _root);
                break;
            }
            case "ENDF": {
                this.content = new Endf(this._io, this, _root);
                break;
            }
            case "ZONE": {
                this.content = new Zones(this._io, this, _root);
                break;
            }
            case "VERS": {
                this.content = new Version(this._io, this, _root);
                break;
            }
            case "CAUX": {
                this.content = new CharacterAuxiliaries(this._io, this, _root);
                break;
            }
            case "TNAM": {
                this.content = new TileNames(this._io, this, _root);
                break;
            }
            case "TILE": {
                this.content = new Tiles(this._io, this, _root);
                break;
            }
            case "CHWP": {
                this.content = new CharacterWeapons(this._io, this, _root);
                break;
            }
            case "CHAR": {
                this.content = new Characters(this._io, this, _root);
                break;
            }
            default: {
                this.content = new UnknownCatalogEntry(this._io, this, _root);
                break;
            }
        }
    }

    public String type() { return type; }
    public Long size() { return size; }
    public KaitaiStruct content() { return content; }
    public Yodesk _root() { return _root; }
    public Yodesk _parent() { return _parent; }
}
