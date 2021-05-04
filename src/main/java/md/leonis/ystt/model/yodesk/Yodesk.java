package md.leonis.ystt.model.yodesk;
// This is a generated file! Please edit source .ksy file and use kaitai-struct-compiler to rebuild

import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import io.kaitai.struct.RandomAccessFileKaitaiInputStream;
import md.leonis.ystt.model.yodesk.characters.CharacterAuxiliaries;
import md.leonis.ystt.model.yodesk.characters.CharacterWeapons;
import md.leonis.ystt.model.Section;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * [Star Wars: Yoda Stories](https://en.wikipedia.org/wiki/Star_Wars:_Yoda_Stories) is a unique tile based game with procedurally
 * generated worlds.
 * This spec describes the file format used for all assets of the Windows
 * version of the game.
 * <p>
 * The file format follows the TLV (type-length-value) pattern to build a
 * central catalog containing the most important (and globally accessible)
 * assets of the game (e.g. puzzles, zones, tiles, etc.). The same pattern is
 * also found in some catalog entries to encode arrays of variable-length
 * structures.
 * <p>
 * With every new game, Yoda Stories generates a new world. This is done by
 * picking a random sample of puzzles from `PUZ2`. One of the chosen puzzles
 * will be the goal, which when solved wins the game.
 * Each puzzle provides an item when solved and some require one to be completed.
 * During world generation a global world map of 10x10 sectors is filled with
 * zones based on the selected puzzles.
 * <p>
 * To add variety and interactivity to each zone the game includes a simple
 * scripting engine. Zones can declare actions that when executed can for
 * example set, move or delete tiles, drop items or activate enemies.
 */
public class Yodesk extends KaitaiStruct {

    private ArrayList<CatalogEntry> catalog;
    private final Map<Section, CatalogEntry> sections = new LinkedHashMap<>();

    private Version version;
    private StartupImage startupImage;
    private Sounds sounds;
    private Tiles tiles;
    private Zones zones;
    private Puzzles puzzles;
    private Characters characters;
    private CharacterAuxiliaries characterAuxiliaries;
    private CharacterWeapons characterWeapons;
    private TileNames tileNames;
    private Tgen tgen;
    private Endf endf;
    private UnknownCatalogEntry unknownCatalogEntry;

    private final Yodesk root;
    private final KaitaiStruct parent;

    private static String charset = "Cp1252";

    public static Yodesk fromFile(String fileName, String charset) throws IOException {
        Yodesk.charset = charset;
        return new Yodesk(new RandomAccessFileKaitaiInputStream(fileName));
    }

    public Yodesk(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Yodesk(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public Yodesk(KaitaiInputStream io, KaitaiStruct parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root == null ? this : root;
        _read();
    }

    private void _read() {
        this.catalog = new ArrayList<>();

        CatalogEntry _it;
        do {
            _it = new CatalogEntry(this.io, this, root);
            this.catalog.add(_it);
            sections.put(_it.getSection(), _it);
        } while (!_it.getType().equals("ENDF"));

        assert characters.getCharacters().size() == characterAuxiliaries.getAuxiliaries().size();
        assert characters.getCharacters().size() == characterWeapons.getWeapons().size();
    }

    public void write(KaitaiOutputStream os) {

        try {
            catalog.forEach(c -> c.write(os));
            os.saveAndClose();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<CatalogEntry> getCatalog() {
        return catalog;
    }

    public void setCatalog(ArrayList<CatalogEntry> catalog) {
        this.catalog = catalog;
    }

    public Map<Section, CatalogEntry> getSections() {
        return sections;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public StartupImage getStartupImage() {
        return startupImage;
    }

    public void setStartupImage(StartupImage startupImage) {
        this.startupImage = startupImage;
    }

    public Sounds getSounds() {
        return sounds;
    }

    public void setSounds(Sounds sounds) {
        this.sounds = sounds;
    }

    public Tiles getTiles() {
        return tiles;
    }

    public void setTiles(Tiles tiles) {
        this.tiles = tiles;
    }

    public Zones getZones() {
        return zones;
    }

    public void setZones(Zones zones) {
        this.zones = zones;
    }

    public Puzzles getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(Puzzles puzzles) {
        this.puzzles = puzzles;
    }

    public Characters getCharacters() {
        return characters;
    }

    public void setCharacters(Characters characters) {
        this.characters = characters;
    }

    public CharacterAuxiliaries getCharacterAuxiliaries() {
        return characterAuxiliaries;
    }

    public void setCharacterAuxiliaries(CharacterAuxiliaries characterAuxiliaries) {
        this.characterAuxiliaries = characterAuxiliaries;
    }

    public CharacterWeapons getCharacterWeapons() {
        return characterWeapons;
    }

    public void setCharacterWeapons(CharacterWeapons characterWeapons) {
        this.characterWeapons = characterWeapons;
    }

    public TileNames getTileNames() {
        return tileNames;
    }

    public void setTileNames(TileNames tileNames) {
        this.tileNames = tileNames;
    }

    public Tgen getTgen() {
        return tgen;
    }

    public void setTgen(Tgen tgen) {
        this.tgen = tgen;
    }

    public Endf getEndf() {
        return endf;
    }

    public void setEndf(Endf endf) {
        this.endf = endf;
    }

    public UnknownCatalogEntry getUnknownCatalogEntry() {
        return unknownCatalogEntry;
    }

    public void setUnknownCatalogEntry(UnknownCatalogEntry unknownCatalogEntry) {
        this.unknownCatalogEntry = unknownCatalogEntry;
    }

    public Yodesk getRoot() {
        return root;
    }

    public KaitaiStruct getParent() {
        return parent;
    }

    public static String getCharset() {
        return charset;
    }

    public static void setCharset(String charset) {
        Yodesk.charset = charset;
    }
}
