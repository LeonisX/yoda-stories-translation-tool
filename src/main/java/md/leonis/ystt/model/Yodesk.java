package md.leonis.ystt.model;
// This is a generated file! Please edit source .ksy file and use kaitai-struct-compiler to rebuild

import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import io.kaitai.struct.RandomAccessFileKaitaiStream;
import md.leonis.ystt.model.characters.CharacterAuxiliaries;
import md.leonis.ystt.model.characters.CharacterWeapons;
import md.leonis.ystt.oldmodel2.KnownSections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final Map<KnownSections, CatalogEntry> sections = new HashMap<>();

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

    private final Yodesk _root;
    private final KaitaiStruct _parent;

    public static Yodesk fromFile(String fileName) throws IOException {
        return new Yodesk(new RandomAccessFileKaitaiStream(fileName));
    }

    public Yodesk(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Yodesk(KaitaiStream _io, KaitaiStruct _parent) {
        this(_io, _parent, null);
    }

    public Yodesk(KaitaiStream _io, KaitaiStruct _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root == null ? this : _root;
        _read();
    }

    private void _read() {
        this.catalog = new ArrayList<>();

        CatalogEntry _it;
        do {
            _it = new CatalogEntry(this._io, this, _root);
            this.catalog.add(_it);
            sections.put(_it.getSection(), _it);
        } while (!_it.getType().equals("ENDF"));

        assert characters.getCharacters().size() == characterAuxiliaries.getAuxiliaries().size();
        assert characters.getCharacters().size() == characterWeapons.getWeapons().size();
    }

    public ArrayList<CatalogEntry> getCatalog() {
        return catalog;
    }

    public void setCatalog(ArrayList<CatalogEntry> catalog) {
        this.catalog = catalog;
    }

    public Map<KnownSections, CatalogEntry> getSections() {
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

    public Yodesk get_root() {
        return _root;
    }

    public KaitaiStruct get_parent() {
        return _parent;
    }
}
