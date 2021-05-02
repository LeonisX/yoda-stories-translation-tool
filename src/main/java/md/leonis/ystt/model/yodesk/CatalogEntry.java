package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.characters.CharacterAuxiliaries;
import md.leonis.ystt.model.yodesk.characters.CharacterWeapons;
import md.leonis.ystt.model.yodesk.zones.Zone;
import md.leonis.ystt.model.Section;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static md.leonis.ystt.model.Section.VERS;
import static md.leonis.ystt.model.Section.ZONE;

public class CatalogEntry extends KaitaiStruct {

    private String type;
    private Section section;
    private long size;
    private int position;
    private int dataPosition;
    private byte[] bytes;
    private KaitaiStruct content;

    private final Yodesk root;
    private final Yodesk parent;

    public static CatalogEntry fromFile(String fileName) throws IOException {
        return new CatalogEntry(new ByteBufferKaitaiStream(fileName));
    }

    public CatalogEntry(KaitaiStream io) {
        this(io, null, null);
    }

    public CatalogEntry(KaitaiStream io, Yodesk parent) {
        this(io, parent, null);
    }

    public CatalogEntry(KaitaiStream io, Yodesk parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        try {
            position = this.io.pos();

            this.type = new String(this.io.readBytes(4), StandardCharsets.US_ASCII);
            section = Section.valueOf(type);

            if (section.equals(VERS)) {
                this.size = 4;                                  // 4 bytes of value
            } else if (section.equals(ZONE)) {
                this.size = this.io.size() - this.io.pos();   // Will be calculated later
            } else {
                this.size = this.io.readU4le();
            }

            dataPosition = this.io.pos();
            this.bytes = this.io.readBytes(size);
            KaitaiStream stream = new ByteBufferKaitaiStream(bytes);

            switch (section) {
                case VERS:
                    Version version = new Version(stream, this, root);
                    this.content = version;
                    parent.setVersion(version);
                    break;
                case STUP:
                    StartupImage startupImage = new StartupImage(stream, this, root);
                    this.content = startupImage;
                    parent.setStartupImage(startupImage);
                    break;
                case SNDS:
                    Sounds sounds = new Sounds(stream, this, root);
                    this.content = sounds;
                    parent.setSounds(sounds);
                    break;
                case TILE:
                    Tiles tiles = new Tiles(stream, this, root);
                    this.content = tiles;
                    parent.setTiles(tiles);
                    break;
                case ZONE:
                    Zones zones = new Zones(stream, this, root);
                    this.content = zones;
                    parent.setZones(zones);

                    this.size = zones.getZones().stream().map(Zone::getSize).reduce(0L, Long::sum)
                            + 2                                 // numZones
                            + zones.getZones().size() * (2 + 4);   // planet + size of every zone

                    io.seek(position);
                    this.bytes = io.readBytes(size);
                    break;
                case PUZ2:
                    Puzzles puzzles = new Puzzles(stream, this, root);
                    this.content = puzzles;
                    parent.setPuzzles(puzzles);
                    break;
                case CHAR:
                    Characters characters = new Characters(stream, this, root);
                    this.content = characters;
                    parent.setCharacters(characters);
                    break;
                case CHWP:
                    CharacterWeapons characterWeapons = new CharacterWeapons(stream, this, root);
                    this.content = characterWeapons;
                    parent.setCharacterWeapons(characterWeapons);
                    break;
                case CAUX:
                    CharacterAuxiliaries characterAuxiliaries = new CharacterAuxiliaries(stream, this, root);
                    this.content = characterAuxiliaries;
                    parent.setCharacterAuxiliaries(characterAuxiliaries);
                    break;
                case TNAM:
                    TileNames tileNames = new TileNames(stream, this, root);
                    this.content = tileNames;
                    parent.setTileNames(tileNames);
                    break;
                case TGEN:
                    Tgen tgen = new Tgen(stream, this, root);
                    this.content = tgen;
                    parent.setTgen(tgen);
                    break;
                case ENDF:
                    Endf endf = new Endf(stream, this, root);
                    this.content = endf;
                    parent.setEndf(endf);
                    break;
            }

            size += 4 + (section.isWithSize() ? 4 : 0);             // 4 bytes of marker + optional 4 bytes of size

            this.io.seek(position);
            this.bytes = this.io.readBytes(size);

        } catch (IllegalArgumentException e) {
            //TODO showMessage("Unknown section: 0x" + intToHex(GetPosition(), 4) + ": \"" + s + '"');
            section = Section.UNKN;
            this.size = this.io.readU4le();
            this.bytes = this.io.readBytes(size);
            KaitaiStream stream = new ByteBufferKaitaiStream(bytes);
            UnknownCatalogEntry unknownCatalogEntry = new UnknownCatalogEntry(stream, this, root);
            this.content = unknownCatalogEntry;
            parent.setUnknownCatalogEntry(unknownCatalogEntry);
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

    public Yodesk getRoot() {
        return root;
    }

    public Yodesk getParent() {
        return parent;
    }
}