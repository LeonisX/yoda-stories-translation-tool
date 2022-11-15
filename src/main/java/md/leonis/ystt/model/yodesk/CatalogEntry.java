package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Section;
import md.leonis.ystt.model.yodesk.characters.CharacterAuxiliaries;
import md.leonis.ystt.model.yodesk.characters.CharacterWeapons;
import md.leonis.ystt.model.yodesk.zones.Zone;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static md.leonis.ystt.model.Section.VERS;
import static md.leonis.ystt.model.Section.ZONE;

public class CatalogEntry extends KaitaiStruct {

    private String type;
    private Section section;
    private long size;
    private long fullSize;
    private int position;
    private int dataPosition;
    private byte[] bytes;
    private KaitaiStruct content;

    private final transient Yodesk root;
    private final transient Yodesk parent;

    public static CatalogEntry fromFile(String fileName) throws IOException {
        return new CatalogEntry(new ByteBufferKaitaiInputStream(fileName));
    }

    public CatalogEntry(KaitaiInputStream io) {
        this(io, null, null);
    }

    public CatalogEntry(KaitaiInputStream io, Yodesk parent) {
        this(io, parent, null);
    }

    public CatalogEntry(KaitaiInputStream io, Yodesk parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        try {
            position = io.pos();

            type = new String(io.readBytes(4), StandardCharsets.US_ASCII);
            section = Section.valueOf(type);

            if (section.equals(VERS)) {
                size = 4;                       // 4 bytes of value
            } else if (section.equals(ZONE)) {
                size = io.size() - io.pos();    // Will be calculated later
            } else {
                size = io.readU4le();
            }

            dataPosition = io.pos();
            bytes = io.readBytes(size);
            KaitaiInputStream stream = new ByteBufferKaitaiInputStream(bytes);

            switch (section) {
                case VERS:
                    Version version = new Version(stream, this, root);
                    content = version;
                    parent.setVersion(version);
                    break;
                case STUP:
                    StartupImage startupImage = new StartupImage(stream, this, root);
                    content = startupImage;
                    parent.setStartupImage(startupImage);
                    break;
                case SNDS:
                    Sounds sounds = new Sounds(stream, this, root);
                    content = sounds;
                    parent.setSounds(sounds);
                    break;
                case TILE:
                    Tiles tiles = new Tiles(stream, this, root);
                    content = tiles;
                    parent.setTiles(tiles);
                    break;
                case ZONE:
                    Zones zones = new Zones(stream, this, root);
                    content = zones;
                    parent.setZones(zones);

                    size = zones.getZones().stream().map(Zone::getSize).reduce(0L, Long::sum)
                            + 2                                             // numZones
                            + (long) zones.getZones().size() * (2 + 4);     // planet + size of every zone

                    io.seek(position);
                    bytes = io.readBytes(size);
                    break;
                case PUZ2:
                    Puzzles puzzles = new Puzzles(stream, this, root);
                    content = puzzles;
                    parent.setPuzzles(puzzles);
                    break;
                case CHAR:
                    Characters characters = new Characters(stream, this, root);
                    content = characters;
                    parent.setCharacters(characters);
                    break;
                case CHWP:
                    CharacterWeapons characterWeapons = new CharacterWeapons(stream, this, root);
                    content = characterWeapons;
                    parent.setCharacterWeapons(characterWeapons);
                    break;
                case CAUX:
                    CharacterAuxiliaries characterAuxiliaries = new CharacterAuxiliaries(stream, this, root);
                    content = characterAuxiliaries;
                    parent.setCharacterAuxiliaries(characterAuxiliaries);
                    break;
                case TNAM:
                    TileNames tileNames = new TileNames(stream, this, root);
                    content = tileNames;
                    parent.setTileNames(tileNames);
                    break;
                case TGEN:
                    TileGenders tileGenders = new TileGenders(stream, this, root);
                    content = tileGenders;
                    parent.setTileGenders(tileGenders);
                    break;
                case ENDF:
                    Endf endf = new Endf(stream, this, root);
                    content = endf;
                    parent.setEndf(endf);
                    break;
            }

            io.seek(position);
            fullSize = size + 4 + (section.isWithSize() ? 4 : 0);             // 4 bytes of marker + optional 4 bytes of size
            bytes = io.readBytes(fullSize);

        } catch (IllegalArgumentException e) {
            section = Section.UNKN;
            size = io.readU4le();
            bytes = io.readBytes(fullSize);
            fullSize = size + 4 + (section.isWithSize() ? 4 : 0);             // 4 bytes of marker + optional 4 bytes of size
            KaitaiInputStream stream = new ByteBufferKaitaiInputStream(bytes);
            UnknownCatalogEntry unknownCatalogEntry = new UnknownCatalogEntry(stream, this, root);
            content = unknownCatalogEntry;
            parent.setUnknownCatalogEntry(unknownCatalogEntry);
        }

        fullSize = size + 4 + (section.isWithSize() ? 4 : 0);             // 4 bytes of marker + optional 4 bytes of size
    }

    public void write(KaitaiOutputStream os) {

        os.writeString(section.name());

        if (!section.equals(VERS) && !section.equals(ZONE)) {
            os.writeU4le(size);
        }

        content.write(os);
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

    public void setSize(long size) {
        long diff = fullSize - size;
        this.size = size;
        this.fullSize = fullSize + diff;
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
