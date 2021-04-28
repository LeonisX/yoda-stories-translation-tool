package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.characters.CharacterAuxiliaries;
import md.leonis.ystt.model.characters.CharacterWeapons;
import md.leonis.ystt.model.zones.Zone;
import md.leonis.ystt.oldmodel.Section;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static md.leonis.ystt.oldmodel.Section.VERS;
import static md.leonis.ystt.oldmodel.Section.ZONE;

public class CatalogEntry extends KaitaiStruct {

    private String type;
    private Section section;
    private long size;
    private int position;
    private int dataPosition;
    private byte[] bytes;
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

        try {
            position = this._io.pos();

            this.type = new String(this._io.readBytes(4), StandardCharsets.US_ASCII);
            section = Section.valueOf(type);

            if (section.equals(VERS)) {
                this.size = 4;                                  // 4 bytes of value
            } else if (section.equals(ZONE)) {
                this.size = this._io.size() - this._io.pos();   // Will be calculated later
            } else {
                this.size = this._io.readU4le();
            }

            dataPosition = this._io.pos();
            this.bytes = this._io.readBytes(size);
            KaitaiStream stream = new ByteBufferKaitaiStream(bytes);

            switch (section) {
                case VERS:
                    Version version = new Version(stream, this, _root);
                    this.content = version;
                    _parent.setVersion(version);
                    break;
                case STUP:
                    StartupImage startupImage = new StartupImage(stream, this, _root);
                    this.content = startupImage;
                    _parent.setStartupImage(startupImage);
                    break;
                case SNDS:
                    Sounds sounds = new Sounds(stream, this, _root);
                    this.content = sounds;
                    _parent.setSounds(sounds);
                    break;
                case TILE:
                    Tiles tiles = new Tiles(stream, this, _root);
                    this.content = tiles;
                    _parent.setTiles(tiles);
                    break;
                case ZONE:
                    Zones zones = new Zones(stream, this, _root);
                    this.content = zones;
                    _parent.setZones(zones);

                    this.size = zones.getZones().stream().map(Zone::getSize).reduce(0L, Long::sum)
                            + 2                                 // numZones
                            + zones.getZones().size() * (2 + 4);   // planet + size of every zone

                    _io.seek(position);
                    this.bytes = _io.readBytes(size);
                    break;
                case PUZ2:
                    Puzzles puzzles = new Puzzles(stream, this, _root);
                    this.content = puzzles;
                    _parent.setPuzzles(puzzles);
                    break;
                case CHAR:
                    Characters characters = new Characters(stream, this, _root);
                    this.content = characters;
                    _parent.setCharacters(characters);
                    break;
                case CHWP:
                    CharacterWeapons characterWeapons = new CharacterWeapons(stream, this, _root);
                    this.content = characterWeapons;
                    _parent.setCharacterWeapons(characterWeapons);
                    break;
                case CAUX:
                    CharacterAuxiliaries characterAuxiliaries = new CharacterAuxiliaries(stream, this, _root);
                    this.content = characterAuxiliaries;
                    _parent.setCharacterAuxiliaries(characterAuxiliaries);
                    break;
                case TNAM:
                    TileNames tileNames = new TileNames(stream, this, _root);
                    this.content = tileNames;
                    _parent.setTileNames(tileNames);
                    break;
                case TGEN:
                    Tgen tgen = new Tgen(stream, this, _root);
                    this.content = tgen;
                    _parent.setTgen(tgen);
                    break;
                case ENDF:
                    Endf endf = new Endf(stream, this, _root);
                    this.content = endf;
                    _parent.setEndf(endf);
                    break;
            }

            size += 4 + (section.isWithSize() ? 4 : 0);             // 4 bytes of marker + optional 4 bytes of size

            this._io.seek(position);
            this.bytes = this._io.readBytes(size);

        } catch (IllegalArgumentException e) {
            //TODO showMessage("Unknown section: 0x" + intToHex(GetPosition(), 4) + ": \"" + s + '"');
            section = Section.UNKN;
            this.size = this._io.readU4le();
            this.bytes = this._io.readBytes(size);
            KaitaiStream stream = new ByteBufferKaitaiStream(bytes);
            UnknownCatalogEntry unknownCatalogEntry = new UnknownCatalogEntry(stream, this, _root);
            this.content = unknownCatalogEntry;
            _parent.setUnknownCatalogEntry(unknownCatalogEntry);
        }
    }

    public String getType() {
        return type;
    }

    public Section getSection() {
        return section;
    }

    public long getSize() {
        return size;
    }

    public int getPosition() {
        return position;
    }

    public int getDataPosition() {
        return dataPosition;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public KaitaiStruct getContent() {
        return content;
    }

    public Yodesk get_root() {
        return _root;
    }

    public Yodesk get_parent() {
        return _parent;
    }
}