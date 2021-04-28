package md.leonis.ystt.oldmodel2;
// This is a generated file! Please edit source .ksy file and use kaitai-struct-compiler to rebuild

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import io.kaitai.struct.KaitaiStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

/**
 * [Star Wars: Yoda Stories](https://en.wikipedia.org/wiki/Star_Wars:_Yoda_Stories) is a unique tile based game with procedurally
 * generated worlds.
 * This spec describes the file format used for all assets of the Windows
 * version of the game.
 *
 * The file format follows the TLV (type-length-value) pattern to build a
 * central catalog containing the most important (and globally accessible)
 * assets of the game (e.g. puzzles, zones, tiles, etc.). The same pattern is
 * also found in some catalog entries to encode arrays of variable-length
 * structures.
 *
 * With every new game, Yoda Stories generates a new world. This is done by
 * picking a random sample of puzzles from `PUZ2`. One of the chosen puzzles
 * will be the goal, which when solved wins the game.
 * Each puzzle provides an item when solved and some require one to be completed.
 * During world generation a global world map of 10x10 sectors is filled with
 * zones based on the selected puzzles.
 *
 * To add variety and interactivity to each zone the game includes a simple
 * scripting engine. Zones can declare actions that when executed can for
 * example set, move or delete tiles, drop items or activate enemies.
 */
public class Yodesk extends KaitaiStruct {
    public static Yodesk fromFile(String fileName) throws IOException {
        return new Yodesk(new ByteBufferKaitaiStream(fileName));
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
        {
            CatalogEntry _it;
            int i = 0;
            do {
                _it = new CatalogEntry(this._io, this, _root);
                this.catalog.add(_it);
                i++;
            } while (!(_it.type().equals("ENDF")));
        }
    }

    public static class TileAttributes extends KaitaiStruct {
        public static TileAttributes fromFile(String fileName) throws IOException {
            return new TileAttributes(new ByteBufferKaitaiStream(fileName));
        }

        public TileAttributes(KaitaiStream _io) {
            this(_io, null, null);
        }

        public TileAttributes(KaitaiStream _io, Yodesk.Tile _parent) {
            this(_io, _parent, null);
        }

        public TileAttributes(KaitaiStream _io, Yodesk.Tile _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.hasTransparency = this._io.readBitsIntLe(1) != 0;
            this.isFloor = this._io.readBitsIntLe(1) != 0;
            this.isObject = this._io.readBitsIntLe(1) != 0;
            this.isDraggable = this._io.readBitsIntLe(1) != 0;
            this.isRoof = this._io.readBitsIntLe(1) != 0;
            this.isLocator = this._io.readBitsIntLe(1) != 0;
            this.isWeapon = this._io.readBitsIntLe(1) != 0;
            this.isItem = this._io.readBitsIntLe(1) != 0;
            this.isCharacter = this._io.readBitsIntLe(1) != 0;
            this.unused = this._io.readBitsIntLe(7);
            if (isFloor()) {
                this.isDoorway = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.unused1 = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isTown = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isUnsolvedPuzzle = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isSolvedPuzzle = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isUnsolvedTravel = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isSolvedTravel = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isUnsolvedBlockadeNorth = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isUnsolvedBlockadeSouth = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isUnsolvedBlockadeWest = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isUnsolvedBlockadeEast = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isSolvedBlockadeNorth = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isSolvedBlockadeSouth = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isSolvedBlockadeWest = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isSolvedBlockadeEast = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isUnsolvedGoal = this._io.readBitsIntLe(1) != 0;
            }
            if (isLocator()) {
                this.isLocationIndicator = this._io.readBitsIntLe(1) != 0;
            }
            if (isItem()) {
                this.isKeycard = this._io.readBitsIntLe(1) != 0;
            }
            if (isItem()) {
                this.isTool = this._io.readBitsIntLe(1) != 0;
            }
            if (isItem()) {
                this.isPart = this._io.readBitsIntLe(1) != 0;
            }
            if (isItem()) {
                this.isValuable = this._io.readBitsIntLe(1) != 0;
            }
            if (isItem()) {
                this.isMap = this._io.readBitsIntLe(1) != 0;
            }
            if (isItem()) {
                this.unused2 = this._io.readBitsIntLe(1) != 0;
            }
            if (isItem()) {
                this.isEdible = this._io.readBitsIntLe(1) != 0;
            }
            if (isWeapon()) {
                this.isLowBlaster = this._io.readBitsIntLe(1) != 0;
            }
            if (isWeapon()) {
                this.isHighBlaster = this._io.readBitsIntLe(1) != 0;
            }
            if (isWeapon()) {
                this.isLightsaber = this._io.readBitsIntLe(1) != 0;
            }
            if (isWeapon()) {
                this.isTheForce = this._io.readBitsIntLe(1) != 0;
            }
            if (isCharacter()) {
                this.isHero = this._io.readBitsIntLe(1) != 0;
            }
            if (isCharacter()) {
                this.isEnemy = this._io.readBitsIntLe(1) != 0;
            }
            if (isCharacter()) {
                this.isNpc = this._io.readBitsIntLe(1) != 0;
            }
        }
        private boolean hasTransparency;
        private boolean isFloor;
        private boolean isObject;
        private boolean isDraggable;
        private boolean isRoof;
        private boolean isLocator;
        private boolean isWeapon;
        private boolean isItem;
        private boolean isCharacter;
        private long unused;
        private Boolean isDoorway;
        private Boolean unused1;
        private Boolean isTown;
        private Boolean isUnsolvedPuzzle;
        private Boolean isSolvedPuzzle;
        private Boolean isUnsolvedTravel;
        private Boolean isSolvedTravel;
        private Boolean isUnsolvedBlockadeNorth;
        private Boolean isUnsolvedBlockadeSouth;
        private Boolean isUnsolvedBlockadeWest;
        private Boolean isUnsolvedBlockadeEast;
        private Boolean isSolvedBlockadeNorth;
        private Boolean isSolvedBlockadeSouth;
        private Boolean isSolvedBlockadeWest;
        private Boolean isSolvedBlockadeEast;
        private Boolean isUnsolvedGoal;
        private Boolean isLocationIndicator;
        private Boolean isKeycard;
        private Boolean isTool;
        private Boolean isPart;
        private Boolean isValuable;
        private Boolean isMap;
        private Boolean unused2;
        private Boolean isEdible;
        private Boolean isLowBlaster;
        private Boolean isHighBlaster;
        private Boolean isLightsaber;
        private Boolean isTheForce;
        private Boolean isHero;
        private Boolean isEnemy;
        private Boolean isNpc;
        private final Yodesk _root;
        private final Yodesk.Tile _parent;

        /**
         * Affects how tile image should be drawn. If set, the
         * value 0 in `pixels` is treated as transparent. Otherwise
         * it is drawn as black.
         */
        public boolean hasTransparency() { return hasTransparency; }

        /**
         * Tile is usually placed on the lowest layer of a zone
         */
        public boolean isFloor() { return isFloor; }

        /**
         * Object, tile is usually placed on the middle layer of a zone
         */
        public boolean isObject() { return isObject; }

        /**
         * If set and the tile is placed on the object layer it can be
         * dragged and pushed around by the hero.
         */
        public boolean isDraggable() { return isDraggable; }

        /**
         * Tile is usually placed on the top layer (roof)
         */
        public boolean isRoof() { return isRoof; }

        /**
         * Locator, tile is used in world map view overview
         */
        public boolean isLocator() { return isLocator; }

        /**
         * Identifies tiles that are mapped to weapons
         */
        public boolean isWeapon() { return isWeapon; }
        public boolean isItem() { return isItem; }

        /**
         * Tile forms part of a character
         */
        public boolean isCharacter() { return isCharacter; }
        public long unused() { return unused; }

        /**
         * These tiles are doorways, monsters can't go
         */
        public Boolean isDoorway() { return isDoorway; }
        public Boolean unused1() { return unused1; }

        /**
         * Marks the spaceport on the map
         */
        public Boolean isTown() { return isTown; }

        /**
         * Marks a discovered, but unsolved puzzle on the map
         */
        public Boolean isUnsolvedPuzzle() { return isUnsolvedPuzzle; }

        /**
         * Marks a solved puzzle on the map
         */
        public Boolean isSolvedPuzzle() { return isSolvedPuzzle; }

        /**
         * Marks a place of travel on the map that has not been solved
         */
        public Boolean isUnsolvedTravel() { return isUnsolvedTravel; }

        /**
         * Marks a solved place of travel on the map
         */
        public Boolean isSolvedTravel() { return isSolvedTravel; }

        /**
         * Marks a sector on the map that blocks access to northern zones
         */
        public Boolean isUnsolvedBlockadeNorth() { return isUnsolvedBlockadeNorth; }

        /**
         * Marks a sector on the map that blocks access to southern zones
         */
        public Boolean isUnsolvedBlockadeSouth() { return isUnsolvedBlockadeSouth; }

        /**
         * Marks a sector on the map that blocks access to western zones
         */
        public Boolean isUnsolvedBlockadeWest() { return isUnsolvedBlockadeWest; }

        /**
         * Marks a sector on the map that blocks access to eastern zones
         */
        public Boolean isUnsolvedBlockadeEast() { return isUnsolvedBlockadeEast; }

        /**
         * Marks a solved sector on the map that used to block access to
         * northern zones
         */
        public Boolean isSolvedBlockadeNorth() { return isSolvedBlockadeNorth; }

        /**
         * Marks a solved sector on the map that used to block access to
         * southern zones
         */
        public Boolean isSolvedBlockadeSouth() { return isSolvedBlockadeSouth; }

        /**
         * Marks a solved sector on the map that used to block access to
         * western zones
         */
        public Boolean isSolvedBlockadeWest() { return isSolvedBlockadeWest; }

        /**
         * Marks a solved sector on the map that used to block access to
         * eastern zones
         */
        public Boolean isSolvedBlockadeEast() { return isSolvedBlockadeEast; }

        /**
         * The final puzzle of the world. Solving this wins the game
         */
        public Boolean isUnsolvedGoal() { return isUnsolvedGoal; }

        /**
         * Overlay to mark the current position on the map
         */
        public Boolean isLocationIndicator() { return isLocationIndicator; }
        public Boolean isKeycard() { return isKeycard; }
        public Boolean isTool() { return isTool; }
        public Boolean isPart() { return isPart; }
        public Boolean isValuable() { return isValuable; }
        public Boolean isMap() { return isMap; }
        public Boolean unused2() { return unused2; }
        public Boolean isEdible() { return isEdible; }

        /**
         * Item is a low intensity blaster (like the blaster pistol)
         */
        public Boolean isLowBlaster() { return isLowBlaster; }

        /**
         * Item is a high intensity blaster (like the blaster rifle)
         */
        public Boolean isHighBlaster() { return isHighBlaster; }
        public Boolean isLightsaber() { return isLightsaber; }
        public Boolean isTheForce() { return isTheForce; }
        public Boolean isHero() { return isHero; }
        public Boolean isEnemy() { return isEnemy; }
        public Boolean isNpc() { return isNpc; }
        public Yodesk _root() { return _root; }
        public Yodesk.Tile _parent() { return _parent; }
    }

    public static class TileName extends KaitaiStruct {
        public static TileName fromFile(String fileName) throws IOException {
            return new TileName(new ByteBufferKaitaiStream(fileName));
        }

        public TileName(KaitaiStream _io) {
            this(_io, null, null);
        }

        public TileName(KaitaiStream _io, Yodesk.TileNames _parent) {
            this(_io, _parent, null);
        }

        public TileName(KaitaiStream _io, Yodesk.TileNames _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.tileId = this._io.readU2le();
            if (tileId() != 65535) {
                this.name = new String(KaitaiStream.bytesTerminate(this._io.readBytes(24), (byte) 0, false), StandardCharsets.US_ASCII);
            }
        }
        private int tileId;
        private String name;
        private final Yodesk _root;
        private final Yodesk.TileNames _parent;
        public int tileId() { return tileId; }
        public String name() { return name; }
        public Yodesk _root() { return _root; }
        public Yodesk.TileNames _parent() { return _parent; }
    }

    public static class PrefixedStr extends KaitaiStruct {

        private int lenContent;
        private String content;

        private final Yodesk _root;
        private final Yodesk.Puzzle _parent;
        public int lenContent() { return lenContent; }
        public String content() { return content; }
        public Yodesk _root() { return _root; }
        public Yodesk.Puzzle _parent() { return _parent; }

        public static PrefixedStr fromFile(String fileName) throws IOException {
            return new PrefixedStr(new ByteBufferKaitaiStream(fileName));
        }

        public PrefixedStr(KaitaiStream _io) {
            this(_io, null, null);
        }

        public PrefixedStr(KaitaiStream _io, Yodesk.Puzzle _parent) {
            this(_io, _parent, null);
        }

        public PrefixedStr(KaitaiStream _io, Yodesk.Puzzle _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        private void _read() {
            this.lenContent = this._io.readU2le();
            this.content = new String(this._io.readBytes(lenContent()), StandardCharsets.US_ASCII);
        }
    }

    public static class ZoneAuxiliary3 extends KaitaiStruct {
        public static ZoneAuxiliary3 fromFile(String fileName) throws IOException {
            return new ZoneAuxiliary3(new ByteBufferKaitaiStream(fileName));
        }

        public ZoneAuxiliary3(KaitaiStream _io) {
            this(_io, null, null);
        }

        public ZoneAuxiliary3(KaitaiStream _io, Yodesk.Zone _parent) {
            this(_io, _parent, null);
        }

        public ZoneAuxiliary3(KaitaiStream _io, Yodesk.Zone _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.marker = this._io.readBytes(4);
            if (!(Arrays.equals(marker(), new byte[] { 73, 90, 88, 51 }))) {
                throw new KaitaiStream.ValidationNotEqualError(new byte[] { 73, 90, 88, 51 }, marker(), _io(), "/types/zone_auxiliary_3/seq/0");
            }
            this.size = this._io.readU4le();
            this.numNpcs = this._io.readU2le();
            npcs = new ArrayList<Integer>(((Number) (numNpcs())).intValue());
            for (int i = 0; i < numNpcs(); i++) {
                this.npcs.add(this._io.readU2le());
            }
        }
        private byte[] marker;
        private long size;
        private int numNpcs;
        private ArrayList<Integer> npcs;
        private final Yodesk _root;
        private final Yodesk.Zone _parent;
        public byte[] marker() { return marker; }
        public long size() { return size; }
        public int numNpcs() { return numNpcs; }

        /**
         * NPCs that can be placed in the zone to trade items with the hero.
         */
        public ArrayList<Integer> npcs() { return npcs; }
        public Yodesk _root() { return _root; }
        public Yodesk.Zone _parent() { return _parent; }
    }

    /**
     * This section declares sounds used in the game. The actual audio data is
     * stored in wav files on the disk (in a directory named `sfx`) so this
     * section contains paths to each sound file.
     * Sounds can be referenced from the scripting language (see `play_sound`
     * instruction opcode below) and from weapon (see `character` structure).
     * Some sound ids (like the one when the hero is hit, or can't leave a
     * zone) are hard coded in the game.
     */
    public static class Sounds extends KaitaiStruct {
        public static Sounds fromFile(String fileName) throws IOException {
            return new Sounds(new ByteBufferKaitaiStream(fileName));
        }

        public Sounds(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Sounds(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public Sounds(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.count = this._io.readS2le();
            sounds = new ArrayList<>(((Number) (-(count()))).intValue());
            for (int i = 0; i < -(count()); i++) {
                this.sounds.add(new PrefixedStrz(this._io, this, _root));
            }
        }
        private short count;
        private ArrayList<PrefixedStrz> sounds;
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;
        public short count() { return count; }
        public ArrayList<PrefixedStrz> sounds() { return sounds; }
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
    }

    public static class CharacterWeapons extends KaitaiStruct {
        public static CharacterWeapons fromFile(String fileName) throws IOException {
            return new CharacterWeapons(new ByteBufferKaitaiStream(fileName));
        }

        public CharacterWeapons(KaitaiStream _io) {
            this(_io, null, null);
        }

        public CharacterWeapons(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public CharacterWeapons(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.weapons = new ArrayList<CharacterWeapon>();
            {
                CharacterWeapon _it;
                int i = 0;
                do {
                    _it = new CharacterWeapon(this._io, this, _root);
                    this.weapons.add(_it);
                    i++;
                } while (!(_it.index() == 65535));
            }
        }
        private ArrayList<CharacterWeapon> weapons;
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;
        public ArrayList<CharacterWeapon> weapons() { return weapons; }
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
    }

    public static class ZoneAuxiliary extends KaitaiStruct {
        public static ZoneAuxiliary fromFile(String fileName) throws IOException {
            return new ZoneAuxiliary(new ByteBufferKaitaiStream(fileName));
        }

        public ZoneAuxiliary(KaitaiStream _io) {
            this(_io, null, null);
        }

        public ZoneAuxiliary(KaitaiStream _io, Yodesk.Zone _parent) {
            this(_io, _parent, null);
        }

        public ZoneAuxiliary(KaitaiStream _io, Yodesk.Zone _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.marker = this._io.readBytes(4);
            if (!(Arrays.equals(marker(), new byte[] { 73, 90, 65, 88 }))) {
                throw new KaitaiStream.ValidationNotEqualError(new byte[] { 73, 90, 65, 88 }, marker(), _io(), "/types/zone_auxiliary/seq/0");
            }
            this.size = this._io.readU4le();
            this._unnamed2 = this._io.readU2le();
            this.numMonsters = this._io.readU2le();
            monsters = new ArrayList<>(((Number) (numMonsters())).intValue());
            for (int i = 0; i < numMonsters(); i++) {
                this.monsters.add(new Monster(this._io, this, _root));
            }
            this.numRequiredItems = this._io.readU2le();
            requiredItems = new ArrayList<>(((Number) (numRequiredItems())).intValue());
            for (int i = 0; i < numRequiredItems(); i++) {
                this.requiredItems.add(this._io.readU2le());
            }
            this.numGoalItems = this._io.readU2le();
            goalItems = new ArrayList<>(((Number) (numGoalItems())).intValue());
            for (int i = 0; i < numGoalItems(); i++) {
                this.goalItems.add(this._io.readU2le());
            }
        }
        private byte[] marker;
        private long size;
        private int _unnamed2;
        private int numMonsters;
        private ArrayList<Monster> monsters;
        private int numRequiredItems;
        private ArrayList<Integer> requiredItems;
        private int numGoalItems;
        private ArrayList<Integer> goalItems;
        private final Yodesk _root;
        private final Yodesk.Zone _parent;
        public byte[] marker() { return marker; }
        public long size() { return size; }
        public int _unnamed2() { return _unnamed2; }
        public int numMonsters() { return numMonsters; }
        public ArrayList<Monster> monsters() { return monsters; }
        public int numRequiredItems() { return numRequiredItems; }

        /**
         * List of items that can be used to solve the zone.
         */
        public ArrayList<Integer> requiredItems() { return requiredItems; }
        public int numGoalItems() { return numGoalItems; }

        /**
         * Additional items that are needed to solve the zone. Only used if the
         * zone type is `goal`.
         */
        public ArrayList<Integer> goalItems() { return goalItems; }
        public Yodesk _root() { return _root; }
        public Yodesk.Zone _parent() { return _parent; }
    }

    public static class CharacterAuxiliaries extends KaitaiStruct {
        public static CharacterAuxiliaries fromFile(String fileName) throws IOException {
            return new CharacterAuxiliaries(new ByteBufferKaitaiStream(fileName));
        }

        public CharacterAuxiliaries(KaitaiStream _io) {
            this(_io, null, null);
        }

        public CharacterAuxiliaries(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public CharacterAuxiliaries(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.auxiliaries = new ArrayList<>();
            {
                CharacterAuxiliary _it;
                int i = 0;
                do {
                    _it = new CharacterAuxiliary(this._io, this, _root);
                    this.auxiliaries.add(_it);
                    i++;
                } while (!(_it.index() == 65535));
            }
        }
        private ArrayList<CharacterAuxiliary> auxiliaries;
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;
        public ArrayList<CharacterAuxiliary> auxiliaries() { return auxiliaries; }
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
    }

    public static class ZoneAuxiliary2 extends KaitaiStruct {
        public static ZoneAuxiliary2 fromFile(String fileName) throws IOException {
            return new ZoneAuxiliary2(new ByteBufferKaitaiStream(fileName));
        }

        public ZoneAuxiliary2(KaitaiStream _io) {
            this(_io, null, null);
        }

        public ZoneAuxiliary2(KaitaiStream _io, Yodesk.Zone _parent) {
            this(_io, _parent, null);
        }

        public ZoneAuxiliary2(KaitaiStream _io, Yodesk.Zone _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.marker = this._io.readBytes(4);
            if (!(Arrays.equals(marker(), new byte[] { 73, 90, 88, 50 }))) {
                throw new KaitaiStream.ValidationNotEqualError(new byte[] { 73, 90, 88, 50 }, marker(), _io(), "/types/zone_auxiliary_2/seq/0");
            }
            this.size = this._io.readU4le();
            this.numProvidedItems = this._io.readU2le();
            providedItems = new ArrayList<>(((Number) (numProvidedItems())).intValue());
            for (int i = 0; i < numProvidedItems(); i++) {
                this.providedItems.add(this._io.readU2le());
            }
        }
        private byte[] marker;
        private long size;
        private int numProvidedItems;
        private ArrayList<Integer> providedItems;
        private final Yodesk _root;
        private final Yodesk.Zone _parent;
        public byte[] marker() { return marker; }
        public long size() { return size; }
        public int numProvidedItems() { return numProvidedItems; }

        /**
         * Items that can be gained when the zone is solved.
         */
        public ArrayList<Integer> providedItems() { return providedItems; }
        public Yodesk _root() { return _root; }
        public Yodesk.Zone _parent() { return _parent; }
    }

    public static class PrefixedStrz extends KaitaiStruct {
        public static PrefixedStrz fromFile(String fileName) throws IOException {
            return new PrefixedStrz(new ByteBufferKaitaiStream(fileName));
        }

        public PrefixedStrz(KaitaiStream _io) {
            this(_io, null, null);
        }

        public PrefixedStrz(KaitaiStream _io, Yodesk.Sounds _parent) {
            this(_io, _parent, null);
        }

        public PrefixedStrz(KaitaiStream _io, Yodesk.Sounds _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.lenContent = this._io.readU2le();
            this.content = new String(KaitaiStream.bytesTerminate(this._io.readBytes(lenContent()), (byte) 0, false), StandardCharsets.US_ASCII);
        }
        private int lenContent;
        private String content;
        private final Yodesk _root;
        private final Yodesk.Sounds _parent;
        public int lenContent() { return lenContent; }
        public String content() { return content; }
        public Yodesk _root() { return _root; }
        public Yodesk.Sounds _parent() { return _parent; }
    }

    /**
     * A monster is a enemy in a zone.
     */
    public static class Monster extends KaitaiStruct {
        public static Monster fromFile(String fileName) throws IOException {
            return new Monster(new ByteBufferKaitaiStream(fileName));
        }

        public Monster(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Monster(KaitaiStream _io, Yodesk.ZoneAuxiliary _parent) {
            this(_io, _parent, null);
        }

        public Monster(KaitaiStream _io, Yodesk.ZoneAuxiliary _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.character = this._io.readU2le();
            this.x = this._io.readU2le();
            this.y = this._io.readU2le();
            this.loot = this._io.readU2le();
            this.dropsLoot = this._io.readU4le();
            waypoints = new ArrayList<>(((Number) (4)).intValue());
            for (int i = 0; i < 4; i++) {
                this.waypoints.add(new Waypoint(this._io, this, _root));
            }
        }
        private int character;
        private int x;
        private int y;
        private int loot;
        private long dropsLoot;
        private ArrayList<Waypoint> waypoints;
        private final Yodesk _root;
        private final Yodesk.ZoneAuxiliary _parent;
        public int character() { return character; }
        public int x() { return x; }
        public int y() { return y; }

        /**
         * References the item (loot - 1) that will be dropped if the monster
         * is killed. If set to `0xFFFF` the current zone's quest item will be
         * dropped.
         */
        public int loot() { return loot; }

        /**
         * If this field is anything other than 0 the monster may drop an item when killed.
         */
        public long dropsLoot() { return dropsLoot; }
        public ArrayList<Waypoint> waypoints() { return waypoints; }
        public Yodesk _root() { return _root; }
        public Yodesk.ZoneAuxiliary _parent() { return _parent; }
    }

    public static class TilesEntries extends KaitaiStruct {
        public static TilesEntries fromFile(String fileName) throws IOException {
            return new TilesEntries(new ByteBufferKaitaiStream(fileName));
        }

        public TilesEntries(KaitaiStream _io) {
            this(_io, null, null);
        }

        public TilesEntries(KaitaiStream _io, Yodesk.Tiles _parent) {
            this(_io, _parent, null);
        }

        public TilesEntries(KaitaiStream _io, Yodesk.Tiles _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.tiles = new ArrayList<Tile>();
            {
                int i = 0;
                while (!this._io.isEof()) {
                    this.tiles.add(new Tile(this._io, this, _root));
                    i++;
                }
            }
        }
        private ArrayList<Tile> tiles;
        private final Yodesk _root;
        private final Yodesk.Tiles _parent;
        public ArrayList<Tile> tiles() { return tiles; }
        public Yodesk _root() { return _root; }
        public Yodesk.Tiles _parent() { return _parent; }
    }

    public static class Tiles extends KaitaiStruct {
        public static Tiles fromFile(String fileName) throws IOException {
            return new Tiles(new ByteBufferKaitaiStream(fileName));
        }

        public Tiles(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Tiles(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public Tiles(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this._raw_tiles = this._io.readBytes(_parent().size());
            KaitaiStream _io__raw_tiles = new ByteBufferKaitaiStream(_raw_tiles);
            this.tiles = new TilesEntries(_io__raw_tiles, this, _root);
        }
        private TilesEntries tiles;
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;
        private byte[] _raw_tiles;
        public TilesEntries tiles() { return tiles; }
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
        public byte[] _raw_tiles() { return _raw_tiles; }
    }

    public static class Instruction extends KaitaiStruct {
        public static Instruction fromFile(String fileName) throws IOException {
            return new Instruction(new ByteBufferKaitaiStream(fileName));
        }

        public enum InstructionOpcode {
            PLACE_TILE(0),
            REMOVE_TILE(1),
            MOVE_TILE(2),
            DRAW_TILE(3),
            SPEAK_HERO(4),
            SPEAK_NPC(5),
            SET_TILE_NEEDS_DISPLAY(6),
            SET_RECT_NEEDS_DISPLAY(7),
            WAIT(8),
            REDRAW(9),
            PLAY_SOUND(10),
            STOP_SOUND(11),
            ROLL_DICE(12),
            SET_COUNTER(13),
            ADD_TO_COUNTER(14),
            SET_VARIABLE(15),
            HIDE_HERO(16),
            SHOW_HERO(17),
            MOVE_HERO_TO(18),
            MOVE_HERO_BY(19),
            DISABLE_ACTION(20),
            ENABLE_HOTSPOT(21),
            DISABLE_HOTSPOT(22),
            ENABLE_MONSTER(23),
            DISABLE_MONSTER(24),
            ENABLE_ALL_MONSTERS(25),
            DISABLE_ALL_MONSTERS(26),
            DROP_ITEM(27),
            ADD_ITEM(28),
            REMOVE_ITEM(29),
            MARK_AS_SOLVED(30),
            WIN_GAME(31),
            LOSE_GAME(32),
            CHANGE_ZONE(33),
            SET_SHARED_COUNTER(34),
            ADD_TO_SHARED_COUNTER(35),
            SET_RANDOM(36),
            ADD_HEALTH(37);

            private final long id;
            InstructionOpcode(long id) { this.id = id; }
            public long id() { return id; }
            private static final Map<Long, InstructionOpcode> byId = new HashMap<>(38);
            static {
                for (InstructionOpcode e : InstructionOpcode.values())
                    byId.put(e.id(), e);
            }
            public static InstructionOpcode byId(long id) { return byId.get(id); }
        }

        public Instruction(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Instruction(KaitaiStream _io, Yodesk.Action _parent) {
            this(_io, _parent, null);
        }

        public Instruction(KaitaiStream _io, Yodesk.Action _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.opcode = InstructionOpcode.byId(this._io.readU2le());
            arguments = new ArrayList<>(((Number) (5)).intValue());
            for (int i = 0; i < 5; i++) {
                this.arguments.add(this._io.readS2le());
            }
            this.lenText = this._io.readU2le();
            this.text = new String(this._io.readBytes(lenText()), StandardCharsets.US_ASCII);
        }
        private InstructionOpcode opcode;
        private ArrayList<Short> arguments;
        private int lenText;
        private String text;
        private final Yodesk _root;
        private final Yodesk.Action _parent;
        public InstructionOpcode opcode() { return opcode; }
        public ArrayList<Short> arguments() { return arguments; }
        public int lenText() { return lenText; }
        public String text() { return text; }
        public Yodesk _root() { return _root; }
        public Yodesk.Action _parent() { return _parent; }
    }

    public static class ZoneSpot extends KaitaiStruct {
        public static ZoneSpot fromFile(String fileName) throws IOException {
            return new ZoneSpot(new ByteBufferKaitaiStream(fileName));
        }

        public ZoneSpot(KaitaiStream _io) {
            this(_io, null, null);
        }

        public ZoneSpot(KaitaiStream _io, Yodesk.Zone _parent) {
            this(_io, _parent, null);
        }

        public ZoneSpot(KaitaiStream _io, Yodesk.Zone _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            column = new ArrayList<Integer>(((Number) (3)).intValue());
            for (int i = 0; i < 3; i++) {
                this.column.add(this._io.readU2le());
            }
        }
        private ArrayList<Integer> column;
        private final Yodesk _root;
        private final Yodesk.Zone _parent;

        /**
         * from bottom to top, 0xFFFF indicates empty tiles
         */
        public ArrayList<Integer> column() { return column; }
        public Yodesk _root() { return _root; }
        public Yodesk.Zone _parent() { return _parent; }
    }

    public static class Waypoint extends KaitaiStruct {
        public static Waypoint fromFile(String fileName) throws IOException {
            return new Waypoint(new ByteBufferKaitaiStream(fileName));
        }

        public Waypoint(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Waypoint(KaitaiStream _io, Yodesk.Monster _parent) {
            this(_io, _parent, null);
        }

        public Waypoint(KaitaiStream _io, Yodesk.Monster _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.x = this._io.readU4le();
            this.y = this._io.readU4le();
        }
        private long x;
        private long y;
        private final Yodesk _root;
        private final Yodesk.Monster _parent;
        public long x() { return x; }
        public long y() { return y; }
        public Yodesk _root() { return _root; }
        public Yodesk.Monster _parent() { return _parent; }
    }

    public static class Characters extends KaitaiStruct {
        public static Characters fromFile(String fileName) throws IOException {
            return new Characters(new ByteBufferKaitaiStream(fileName));
        }

        public Characters(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Characters(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public Characters(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.characters = new ArrayList<>();
            {
                Character _it;
                int i = 0;
                do {
                    _it = new Character(this._io, this, _root);
                    this.characters.add(_it);
                    i++;
                } while (!(_it.index() == 65535));
            }
        }
        private ArrayList<Character> characters;
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;
        public ArrayList<Character> characters() { return characters; }
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
    }

    public static class TileNames extends KaitaiStruct {
        public static TileNames fromFile(String fileName) throws IOException {
            return new TileNames(new ByteBufferKaitaiStream(fileName));
        }

        public TileNames(KaitaiStream _io) {
            this(_io, null, null);
        }

        public TileNames(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public TileNames(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.names = new ArrayList<TileName>();
            {
                TileName _it;
                int i = 0;
                do {
                    _it = new TileName(this._io, this, _root);
                    this.names.add(_it);
                    i++;
                } while (!(_it.tileId() == 65535));
            }
        }
        private ArrayList<TileName> names;
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;

        /**
         * List of tile ids and their corresponding names. These are shown in
         * the inventory or used in dialogs (see `speak_hero` and `speak_npc`
         * opcodes).
         */
        public ArrayList<TileName> names() { return names; }
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
    }

    /**
     * In addition to actions some puzzles and events are triggered by
     * hotspots. These hotspots are triggered when the hero steps on them or
     * places an item at the location. Additionally, hotspots are used during
     * world generation to mark places where NPCs can spawn.
     */
    public static class Hotspot extends KaitaiStruct {
        public static Hotspot fromFile(String fileName) throws IOException {
            return new Hotspot(new ByteBufferKaitaiStream(fileName));
        }

        public enum HotspotType {
            DROP_QUEST_ITEM(0),
            SPAWN_LOCATION(1),
            DROP_UNIQUE_WEAPON(2),
            VEHICLE_TO(3),
            VEHICLE_BACK(4),
            DROP_MAP(5),
            DROP_ITEM(6),
            NPC(7),
            DROP_WEAPON(8),
            DOOR_IN(9),
            DOOR_OUT(10),
            UNUSED(11),
            LOCK(12),
            TELEPORTER(13),
            SHIP_TO_PLANET(14),
            SHIP_FROM_PLANET(15);

            private final long id;
            HotspotType(long id) { this.id = id; }
            public long id() { return id; }
            private static final Map<Long, HotspotType> byId = new HashMap<>(16);
            static {
                for (HotspotType e : HotspotType.values())
                    byId.put(e.id(), e);
            }
            public static HotspotType byId(long id) { return byId.get(id); }
        }

        public Hotspot(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Hotspot(KaitaiStream _io, Yodesk.Zone _parent) {
            this(_io, _parent, null);
        }

        public Hotspot(KaitaiStream _io, Yodesk.Zone _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.type = HotspotType.byId(this._io.readU4le());
            this.x = this._io.readU2le();
            this.y = this._io.readU2le();
            this.enabled = this._io.readU2le();
            this.argument = this._io.readU2le();
        }
        private HotspotType type;
        private int x;
        private int y;
        private int enabled;
        private int argument;
        private final Yodesk _root;
        private final Yodesk.Zone _parent;
        public HotspotType type() { return type; }
        public int x() { return x; }
        public int y() { return y; }

        /**
         * If disabled, hotspots can not be triggered. See instruction opcodes
         * called `enable_hotspot` and `disable_hotspot`.
         */
        public int enabled() { return enabled; }
        public int argument() { return argument; }
        public Yodesk _root() { return _root; }
        public Yodesk.Zone _parent() { return _parent; }
    }

    public static class Puzzle extends KaitaiStruct {

        private int index;
        private byte[] marker;
        private Long size;
        private Long type;
        private Long _unnamed4;
        private Long _unnamed5;
        private Integer _unnamed6;
        private ArrayList<PrefixedStr> strings;
        private Integer item1;
        private Integer item2;

        private final Yodesk _root;
        private final Yodesk.Puzzles _parent;
        public int index() { return index; }
        public byte[] marker() { return marker; }
        public Long size() { return size; }
        public Long type() { return type; }
        public Long _unnamed4() { return _unnamed4; }
        public Long _unnamed5() { return _unnamed5; }
        public Integer _unnamed6() { return _unnamed6; }
        public ArrayList<PrefixedStr> strings() { return strings; }
        public Integer item1() { return item1; }
        public Integer item2() { return item2; }
        public Yodesk _root() { return _root; }
        public Yodesk.Puzzles _parent() { return _parent; }

        public static Puzzle fromFile(String fileName) throws IOException {
            return new Puzzle(new ByteBufferKaitaiStream(fileName));
        }

        public Puzzle(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Puzzle(KaitaiStream _io, Yodesk.Puzzles _parent) {
            this(_io, _parent, null);
        }

        public Puzzle(KaitaiStream _io, Yodesk.Puzzles _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        private void _read() {

            this.index = this._io.readU2le();

            if (index() != 65535) {
                this.marker = this._io.readBytes(4);

                if (!(Arrays.equals(marker(), new byte[] { 73, 80, 85, 90 }))) {
                    throw new KaitaiStream.ValidationNotEqualError(new byte[] { 73, 80, 85, 90 }, marker(), _io(), "/types/puzzle/seq/1");
                }

                this.size = this._io.readU4le();
                this.type = this._io.readU4le();
                this._unnamed4 = this._io.readU4le();
                this._unnamed5 = this._io.readU4le();
                this._unnamed6 = this._io.readU2le();

                strings = new ArrayList<>(5);
                for (int i = 0; i < 5; i++) {
                    this.strings.add(new PrefixedStr(this._io, this, _root));
                }

                this.item1 = this._io.readU2le();
                this.item2 = this._io.readU2le();
            }
        }
    }

    public static class Version extends KaitaiStruct {
        public static Version fromFile(String fileName) throws IOException {
            return new Version(new ByteBufferKaitaiStream(fileName));
        }

        public Version(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Version(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public Version(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.version = this._io.readU4le();
        }
        private long version;
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;

        /**
         * Version of the file. This value is always set to 512.
         */
        public long version() { return version; }
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
    }

    public static class Condition extends KaitaiStruct {
        public static Condition fromFile(String fileName) throws IOException {
            return new Condition(new ByteBufferKaitaiStream(fileName));
        }

        public enum ConditionOpcode {
            ZONE_NOT_INITIALISED(0),
            ZONE_ENTERED(1),
            BUMP(2),
            PLACED_ITEM_IS(3),
            STANDING_ON(4),
            COUNTER_IS(5),
            RANDOM_IS(6),
            RANDOM_IS_GREATER_THAN(7),
            RANDOM_IS_LESS_THAN(8),
            ENTER_BY_PLANE(9),
            TILE_AT_IS(10),
            MONSTER_IS_DEAD(11),
            HAS_NO_ACTIVE_MONSTERS(12),
            HAS_ITEM(13),
            REQUIRED_ITEM_IS(14),
            ENDING_IS(15),
            ZONE_IS_SOLVED(16),
            NO_ITEM_PLACED(17),
            ITEM_PLACED(18),
            HEALTH_IS_LESS_THAN(19),
            HEALTH_IS_GREATER_THAN(20),
            UNUSED(21),
            FIND_ITEM_IS(22),
            PLACED_ITEM_IS_NOT(23),
            HERO_IS_AT(24),
            SHARED_COUNTER_IS(25),
            SHARED_COUNTER_IS_LESS_THAN(26),
            SHARED_COUNTER_IS_GREATER_THAN(27),
            GAMES_WON_IS(28),
            DROPS_QUEST_ITEM_AT(29),
            HAS_ANY_REQUIRED_ITEM(30),
            COUNTER_IS_NOT(31),
            RANDOM_IS_NOT(32),
            SHARED_COUNTER_IS_NOT(33),
            IS_VARIABLE(34),
            GAMES_WON_IS_GREATER_THAN(35);

            private final long id;
            ConditionOpcode(long id) { this.id = id; }
            public long id() { return id; }
            private static final Map<Long, ConditionOpcode> byId = new HashMap<Long, ConditionOpcode>(36);
            static {
                for (ConditionOpcode e : ConditionOpcode.values())
                    byId.put(e.id(), e);
            }
            public static ConditionOpcode byId(long id) { return byId.get(id); }
        }

        public Condition(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Condition(KaitaiStream _io, Yodesk.Action _parent) {
            this(_io, _parent, null);
        }

        public Condition(KaitaiStream _io, Yodesk.Action _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.opcode = ConditionOpcode.byId(this._io.readU2le());
            arguments = new ArrayList<>(((Number) (5)).intValue());
            for (int i = 0; i < 5; i++) {
                this.arguments.add(this._io.readS2le());
            }
            this.lenText = this._io.readU2le();
            this.text = new String(this._io.readBytes(lenText()), StandardCharsets.US_ASCII);
        }
        private ConditionOpcode opcode;
        private ArrayList<Short> arguments;
        private int lenText;
        private String text;
        private final Yodesk _root;
        private final Yodesk.Action _parent;
        public ConditionOpcode opcode() { return opcode; }
        public ArrayList<Short> arguments() { return arguments; }
        public int lenText() { return lenText; }

        /**
         * The `text_attribute` is never used, but seems to be included to
         * shared the type with instructions.
         */
        public String text() { return text; }
        public Yodesk _root() { return _root; }
        public Yodesk.Action _parent() { return _parent; }
    }

    public static class Character extends KaitaiStruct {
        public static Character fromFile(String fileName) throws IOException {
            return new Character(new ByteBufferKaitaiStream(fileName));
        }

        public enum CharacterType {
            HERO(1),
            ENEMY(2),
            WEAPON(4);

            private final long id;
            CharacterType(long id) { this.id = id; }
            public long id() { return id; }
            private static final Map<Long, CharacterType> byId = new HashMap<>(3);
            static {
                for (CharacterType e : CharacterType.values())
                    byId.put(e.id(), e);
            }
            public static CharacterType byId(long id) { return byId.get(id); }
        }

        public enum MovementType {
            NONE(0),
            SIT(4),
            WANDER(9),
            PATROL(10),
            ANIMATION(12);

            private final long id;
            MovementType(long id) { this.id = id; }
            public long id() { return id; }
            private static final Map<Long, MovementType> byId = new HashMap<>(5);
            static {
                for (MovementType e : MovementType.values())
                    byId.put(e.id(), e);
            }
            public static MovementType byId(long id) { return byId.get(id); }
        }

        public Character(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Character(KaitaiStream _io, Yodesk.Characters _parent) {
            this(_io, _parent, null);
        }

        public Character(KaitaiStream _io, Yodesk.Characters _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.index = this._io.readU2le();
            if (index() != 65535) {
                this.marker = this._io.readBytes(4);
                if (!(Arrays.equals(marker(), new byte[] { 73, 67, 72, 65 }))) {
                    throw new KaitaiStream.ValidationNotEqualError(new byte[] { 73, 67, 72, 65 }, marker(), _io(), "/types/character/seq/1");
                }
            }
            if (index() != 65535) {
                this.size = this._io.readU4le();
            }
            if (index() != 65535) {
                this.name = new String(KaitaiStream.bytesTerminate(this._io.readBytes(16), (byte) 0, false), StandardCharsets.US_ASCII);
            }
            if (index() != 65535) {
                this.type = CharacterType.byId(this._io.readU2le());
            }
            if (index() != 65535) {
                this.movementType = MovementType.byId(this._io.readU2le());
            }
            if (index() != 65535) {
                this.probablyGarbage1 = this._io.readU2le();
            }
            if (index() != 65535) {
                this.probablyGarbage2 = this._io.readU4le();
            }
            if (index() != 65535) {
                this.frame1 = new CharFrame(this._io, this, _root);
            }
            if (index() != 65535) {
                this.frame2 = new CharFrame(this._io, this, _root);
            }
            if (index() != 65535) {
                this.frame3 = new CharFrame(this._io, this, _root);
            }
        }
        private int index;
        private byte[] marker;
        private Long size;
        private String name;
        private CharacterType type;
        private MovementType movementType;
        private Integer probablyGarbage1;
        private Long probablyGarbage2;
        private CharFrame frame1;
        private CharFrame frame2;
        private CharFrame frame3;
        private final Yodesk _root;
        private final Yodesk.Characters _parent;
        public int index() { return index; }
        public byte[] marker() { return marker; }
        public Long size() { return size; }
        public String name() { return name; }
        public CharacterType type() { return type; }
        public MovementType movementType() { return movementType; }
        public Integer probablyGarbage1() { return probablyGarbage1; }
        public Long probablyGarbage2() { return probablyGarbage2; }
        public CharFrame frame1() { return frame1; }
        public CharFrame frame2() { return frame2; }
        public CharFrame frame3() { return frame3; }
        public Yodesk _root() { return _root; }
        public Yodesk.Characters _parent() { return _parent; }
    }

    public static class ZoneAuxiliary4 extends KaitaiStruct {
        public static ZoneAuxiliary4 fromFile(String fileName) throws IOException {
            return new ZoneAuxiliary4(new ByteBufferKaitaiStream(fileName));
        }

        public ZoneAuxiliary4(KaitaiStream _io) {
            this(_io, null, null);
        }

        public ZoneAuxiliary4(KaitaiStream _io, Yodesk.Zone _parent) {
            this(_io, _parent, null);
        }

        public ZoneAuxiliary4(KaitaiStream _io, Yodesk.Zone _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.marker = this._io.readBytes(4);
            if (!(Arrays.equals(marker(), new byte[] { 73, 90, 88, 52 }))) {
                throw new KaitaiStream.ValidationNotEqualError(new byte[] { 73, 90, 88, 52 }, marker(), _io(), "/types/zone_auxiliary_4/seq/0");
            }
            this.size = this._io.readU4le();
            this._unnamed2 = this._io.readU2le();
        }
        private byte[] marker;
        private long size;
        private int _unnamed2;
        private final Yodesk _root;
        private final Yodesk.Zone _parent;
        public byte[] marker() { return marker; }
        public long size() { return size; }
        public int _unnamed2() { return _unnamed2; }
        public Yodesk _root() { return _root; }
        public Yodesk.Zone _parent() { return _parent; }
    }

    public static class CharacterAuxiliary extends KaitaiStruct {
        public static CharacterAuxiliary fromFile(String fileName) throws IOException {
            return new CharacterAuxiliary(new ByteBufferKaitaiStream(fileName));
        }

        public CharacterAuxiliary(KaitaiStream _io) {
            this(_io, null, null);
        }

        public CharacterAuxiliary(KaitaiStream _io, Yodesk.CharacterAuxiliaries _parent) {
            this(_io, _parent, null);
        }

        public CharacterAuxiliary(KaitaiStream _io, Yodesk.CharacterAuxiliaries _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.index = this._io.readU2le();
            if (index() != 65535) {
                this.damage = this._io.readS2le();
            }
        }
        private int index;
        private Short damage;
        private final Yodesk _root;
        private final Yodesk.CharacterAuxiliaries _parent;
        public int index() { return index; }
        public Short damage() { return damage; }
        public Yodesk _root() { return _root; }
        public Yodesk.CharacterAuxiliaries _parent() { return _parent; }
    }

    public static class CharacterWeapon extends KaitaiStruct {
        public static CharacterWeapon fromFile(String fileName) throws IOException {
            return new CharacterWeapon(new ByteBufferKaitaiStream(fileName));
        }

        public CharacterWeapon(KaitaiStream _io) {
            this(_io, null, null);
        }

        public CharacterWeapon(KaitaiStream _io, Yodesk.CharacterWeapons _parent) {
            this(_io, _parent, null);
        }

        public CharacterWeapon(KaitaiStream _io, Yodesk.CharacterWeapons _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.index = this._io.readU2le();
            if (index() != 65535) {
                this.reference = this._io.readU2le();
            }
            if (index() != 65535) {
                this.health = this._io.readU2le();
            }
        }
        private int index;
        private Integer reference;
        private Integer health;
        private final Yodesk _root;
        private final Yodesk.CharacterWeapons _parent;
        public int index() { return index; }

        /**
         * If the character referenced by index is a monster, this is a
         * reference to their weapon, otherwise this is the index of the
         * weapon's sound
         */
        public Integer reference() { return reference; }
        public Integer health() { return health; }
        public Yodesk _root() { return _root; }
        public Yodesk.CharacterWeapons _parent() { return _parent; }
    }

    public static class CharFrame extends KaitaiStruct {
        public static CharFrame fromFile(String fileName) throws IOException {
            return new CharFrame(new ByteBufferKaitaiStream(fileName));
        }

        public CharFrame(KaitaiStream _io) {
            this(_io, null, null);
        }

        public CharFrame(KaitaiStream _io, Yodesk.Character _parent) {
            this(_io, _parent, null);
        }

        public CharFrame(KaitaiStream _io, Yodesk.Character _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            tiles = new ArrayList<Integer>(((Number) (8)).intValue());
            for (int i = 0; i < 8; i++) {
                this.tiles.add(this._io.readU2le());
            }
        }
        private ArrayList<Integer> tiles;
        private final Yodesk _root;
        private final Yodesk.Character _parent;
        public ArrayList<Integer> tiles() { return tiles; }
        public Yodesk _root() { return _root; }
        public Yodesk.Character _parent() { return _parent; }
    }

    /**
     * A 288x288 bitmap to be shown while other assets are loaded and a new
     * world is generated.
     */
    public static class SetupImage extends KaitaiStruct {
        public static SetupImage fromFile(String fileName) throws IOException {
            return new SetupImage(new ByteBufferKaitaiStream(fileName));
        }

        public SetupImage(KaitaiStream _io) {
            this(_io, null, null);
        }

        public SetupImage(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public SetupImage(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.pixels = this._io.readBytes(_parent().size());
        }
        private byte[] pixels;
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;
        public byte[] pixels() { return pixels; }
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
    }

    public static class Zones extends KaitaiStruct {
        public static Zones fromFile(String fileName) throws IOException {
            return new Zones(new ByteBufferKaitaiStream(fileName));
        }

        public Zones(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Zones(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public Zones(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.numZones = this._io.readU2le();
            zones = new ArrayList<Zone>(((Number) (numZones())).intValue());
            for (int i = 0; i < numZones(); i++) {
                this.zones.add(new Zone(this._io, this, _root));
            }
        }
        private int numZones;
        private ArrayList<Zone> zones;
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;
        public int numZones() { return numZones; }
        public ArrayList<Zone> zones() { return zones; }
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
    }

    public static class Puzzles extends KaitaiStruct {
        public static Puzzles fromFile(String fileName) throws IOException {
            return new Puzzles(new ByteBufferKaitaiStream(fileName));
        }

        public Puzzles(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Puzzles(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public Puzzles(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.puzzles = new ArrayList<>();
            {
                Puzzle _it;
                int i = 0;
                do {
                    _it = new Puzzle(this._io, this, _root);
                    this.puzzles.add(_it);
                    i++;
                } while (!(_it.index() == 65535));
            }
        }
        private ArrayList<Puzzle> puzzles;
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;
        public ArrayList<Puzzle> puzzles() { return puzzles; }
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
    }

    public static class UnknownCatalogEntry extends KaitaiStruct {
        public static UnknownCatalogEntry fromFile(String fileName) throws IOException {
            return new UnknownCatalogEntry(new ByteBufferKaitaiStream(fileName));
        }

        public UnknownCatalogEntry(KaitaiStream _io) {
            this(_io, null, null);
        }

        public UnknownCatalogEntry(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public UnknownCatalogEntry(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.data = this._io.readBytes(_parent().size());
        }
        private byte[] data;
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;
        public byte[] data() { return data; }
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
    }

    public static class Zone extends KaitaiStruct {
        public static Zone fromFile(String fileName) throws IOException {
            return new Zone(new ByteBufferKaitaiStream(fileName));
        }

        public enum Planet {
            NONE(0),
            DESERT(1),
            SNOW(2),
            FOREST(3),
            SWAMP(5);

            private final long id;
            Planet(long id) { this.id = id; }
            public long id() { return id; }
            private static final Map<Long, Planet> byId = new HashMap<Long, Planet>(5);
            static {
                for (Planet e : Planet.values())
                    byId.put(e.id(), e);
            }
            public static Planet byId(long id) { return byId.get(id); }
        }

        public enum ZoneType {
            NONE(0),
            EMPTY(1),
            BLOCKADE_NORTH(2),
            BLOCKADE_SOUTH(3),
            BLOCKADE_EAST(4),
            BLOCKADE_WEST(5),
            TRAVEL_START(6),
            TRAVEL_END(7),
            ROOM(8),
            LOAD(9),
            GOAL(10),
            TOWN(11),
            WIN(13),
            LOSE(14),
            TRADE(15),
            USE(16),
            FIND(17),
            FIND_UNIQUE_WEAPON(18);

            private final long id;
            ZoneType(long id) { this.id = id; }
            public long id() { return id; }
            private static final Map<Long, ZoneType> byId = new HashMap<Long, ZoneType>(18);
            static {
                for (ZoneType e : ZoneType.values())
                    byId.put(e.id(), e);
            }
            public static ZoneType byId(long id) { return byId.get(id); }
        }

        public Zone(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Zone(KaitaiStream _io, Yodesk.Zones _parent) {
            this(_io, _parent, null);
        }

        public Zone(KaitaiStream _io, Yodesk.Zones _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.planet = Planet.byId(this._io.readU2le());
            this.size = this._io.readU4le();
            this.index = this._io.readU2le();
            this.marker = this._io.readBytes(4);
            if (!(Arrays.equals(marker(), new byte[] { 73, 90, 79, 78 }))) {
                throw new KaitaiStream.ValidationNotEqualError(new byte[] { 73, 90, 79, 78 }, marker(), _io(), "/types/zone/seq/3");
            }
            this.size2 = this._io.readU4le();
            this.width = this._io.readU2le();
            this.height = this._io.readU2le();
            this.type = ZoneType.byId(this._io.readU4le());
            this.sharedCounter = this._io.readU2le();
            if (!(sharedCounter() == 65535)) {
                throw new KaitaiStream.ValidationNotEqualError(65535, sharedCounter(), _io(), "/types/zone/seq/8");
            }
            this.planetAgain = this._io.readU2le();
            tileIds = new ArrayList<>(((Number) ((width() * height()))).intValue());
            for (int i = 0; i < (width() * height()); i++) {
                this.tileIds.add(new ZoneSpot(this._io, this, _root));
            }
            this.numHotspots = this._io.readU2le();
            hotspots = new ArrayList<>(((Number) (numHotspots())).intValue());
            for (int i = 0; i < numHotspots(); i++) {
                this.hotspots.add(new Hotspot(this._io, this, _root));
            }
            this.izax = new ZoneAuxiliary(this._io, this, _root);
            this.izx2 = new ZoneAuxiliary2(this._io, this, _root);
            this.izx3 = new ZoneAuxiliary3(this._io, this, _root);
            this.izx4 = new ZoneAuxiliary4(this._io, this, _root);
            this.numActions = this._io.readU2le();
            actions = new ArrayList<>(((Number) (numActions())).intValue());
            for (int i = 0; i < numActions(); i++) {
                this.actions.add(new Action(this._io, this, _root));
            }
        }
        private Planet planet;
        private long size;
        private int index;
        private byte[] marker;
        private long size2;
        private int width;
        private int height;
        private ZoneType type;
        private int sharedCounter;
        private int planetAgain;
        private ArrayList<ZoneSpot> tileIds;
        private int numHotspots;
        private ArrayList<Hotspot> hotspots;
        private ZoneAuxiliary izax;
        private ZoneAuxiliary2 izx2;
        private ZoneAuxiliary3 izx3;
        private ZoneAuxiliary4 izx4;
        private int numActions;
        private ArrayList<Action> actions;
        private final Yodesk _root;
        private final Yodesk.Zones _parent;

        /**
         * Planet this zone can be placed on.
         *
         * During world generation the goal puzzle dictates which planet is
         * chosen. Apart from `swamp` zones, only the zones with type `empty`
         * or the chosen type are loaded when a game is in progress.
         */
        public Planet planet() { return planet; }
        public long size() { return size; }
        public int index() { return index; }
        public byte[] marker() { return marker; }
        public long size2() { return size2; }

        /**
         * Width of the zone in tiles. Either 9 or 18.
         */
        public int width() { return width; }

        /**
         * Height of the zone in tiles. Either 9 or 18.
         */
        public int height() { return height; }
        public ZoneType type() { return type; }

        /**
         * Scripting register shared between the zone and its rooms.
         */
        public int sharedCounter() { return sharedCounter; }

        /**
         * Repetition of the `planet` field
         */
        public int planetAgain() { return planetAgain; }

        /**
         * `tile_ids` is made up of three interleaved tile layers ordered from
         * bottom (floor) to top (roof).
         * Tiles are often references via 3 coordinates (xyz), which
         * corresponds to an index into this array calculated as `n = y * width
         * * 3 + x * 3 = z`.
         */
        public ArrayList<ZoneSpot> tileIds() { return tileIds; }
        public int numHotspots() { return numHotspots; }
        public ArrayList<Hotspot> hotspots() { return hotspots; }
        public ZoneAuxiliary izax() { return izax; }
        public ZoneAuxiliary2 izx2() { return izx2; }
        public ZoneAuxiliary3 izx3() { return izx3; }
        public ZoneAuxiliary4 izx4() { return izx4; }
        public int numActions() { return numActions; }
        public ArrayList<Action> actions() { return actions; }
        public Yodesk _root() { return _root; }
        public Yodesk.Zones _parent() { return _parent; }
    }

    public static class Endf extends KaitaiStruct {
        public static Endf fromFile(String fileName) throws IOException {
            return new Endf(new ByteBufferKaitaiStream(fileName));
        }

        public Endf(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Endf(KaitaiStream _io, Yodesk.CatalogEntry _parent) {
            this(_io, _parent, null);
        }

        public Endf(KaitaiStream _io, Yodesk.CatalogEntry _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
        }
        private final Yodesk _root;
        private final Yodesk.CatalogEntry _parent;
        public Yodesk _root() { return _root; }
        public Yodesk.CatalogEntry _parent() { return _parent; }
    }

    /**
     * Actions are the game's way to make static tile based maps more engaging
     * and interactive. Each action consists of zero or more conditions and
     * instructions, once all conditions are satisfied, all instructions are
     * executed in order.
     *
     * To facilitate state, each zone has three 16-bit registers. These registers
     * are named `counter`, `shared-counter` and `random`. In addition to these
     * registers hidden tiles are sometimes used to mark state.
     * There are conditions and instructions to query and update these registers.
     * The `shared-counter` register is special in that it is shared between a
     * zone and it's rooms. Instruction `0xc` roll_dice can be used to set the
     * `random` register to a random value.
     *
     * A naive implementation of the scripting engine could look like this:
     *
     * ```
     * for action in zone.actions:
     *   all_conditions_satisfied = False
     *   for condition in action.conditions:
     *       all_conditions_satisfied = check(condition)
     *       if !all_conditions_satisfied:
     *         break
     *
     *   if !all_conditions_satisfied:
     *     continue
     *
     *   for instruction in action.instructions:
     *     execute(instruction)
     * ```
     *
     * See `condition_opcode` and `instruction_opcode` enums for a list of
     * available opcodes and their meanings.
     */
    public static class Action extends KaitaiStruct {
        public static Action fromFile(String fileName) throws IOException {
            return new Action(new ByteBufferKaitaiStream(fileName));
        }

        public Action(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Action(KaitaiStream _io, Yodesk.Zone _parent) {
            this(_io, _parent, null);
        }

        public Action(KaitaiStream _io, Yodesk.Zone _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this.marker = this._io.readBytes(4);
            if (!(Arrays.equals(marker(), new byte[] { 73, 65, 67, 84 }))) {
                throw new KaitaiStream.ValidationNotEqualError(new byte[] { 73, 65, 67, 84 }, marker(), _io(), "/types/action/seq/0");
            }
            this.size = this._io.readU4le();
            this.numConditions = this._io.readU2le();
            conditions = new ArrayList<>(((Number) (numConditions())).intValue());
            for (int i = 0; i < numConditions(); i++) {
                this.conditions.add(new Condition(this._io, this, _root));
            }
            this.numInstructions = this._io.readU2le();
            instructions = new ArrayList<>(((Number) (numInstructions())).intValue());
            for (int i = 0; i < numInstructions(); i++) {
                this.instructions.add(new Instruction(this._io, this, _root));
            }
        }
        private byte[] marker;
        private long size;
        private int numConditions;
        private ArrayList<Condition> conditions;
        private int numInstructions;
        private ArrayList<Instruction> instructions;
        private final Yodesk _root;
        private final Yodesk.Zone _parent;
        public byte[] marker() { return marker; }
        public long size() { return size; }
        public int numConditions() { return numConditions; }
        public ArrayList<Condition> conditions() { return conditions; }
        public int numInstructions() { return numInstructions; }
        public ArrayList<Instruction> instructions() { return instructions; }
        public Yodesk _root() { return _root; }
        public Yodesk.Zone _parent() { return _parent; }
    }

    public static class CatalogEntry extends KaitaiStruct {
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
        private String type;
        private Long size;
        private KaitaiStruct content;
        private final Yodesk _root;
        private final Yodesk _parent;
        public String type() { return type; }
        public Long size() { return size; }
        public KaitaiStruct content() { return content; }
        public Yodesk _root() { return _root; }
        public Yodesk _parent() { return _parent; }
    }

    public static class Tile extends KaitaiStruct {
        public static Tile fromFile(String fileName) throws IOException {
            return new Tile(new ByteBufferKaitaiStream(fileName));
        }

        public Tile(KaitaiStream _io) {
            this(_io, null, null);
        }

        public Tile(KaitaiStream _io, Yodesk.TilesEntries _parent) {
            this(_io, _parent, null);
        }

        public Tile(KaitaiStream _io, Yodesk.TilesEntries _parent, Yodesk _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }
        private void _read() {
            this._raw_attributes = this._io.readBytes(4);
            KaitaiStream _io__raw_attributes = new ByteBufferKaitaiStream(_raw_attributes);
            this.attributes = new TileAttributes(_io__raw_attributes, this, _root);
            this.pixels = this._io.readBytes((32 * 32));
        }
        private TileAttributes attributes;
        private byte[] pixels;
        private final Yodesk _root;
        private final Yodesk.TilesEntries _parent;
        private byte[] _raw_attributes;
        public TileAttributes attributes() { return attributes; }
        public byte[] pixels() { return pixels; }
        public Yodesk _root() { return _root; }
        public Yodesk.TilesEntries _parent() { return _parent; }
        public byte[] _raw_attributes() { return _raw_attributes; }
    }

    private ArrayList<CatalogEntry> catalog;
    private final Yodesk _root;
    private final KaitaiStruct _parent;
    public ArrayList<CatalogEntry> catalog() { return catalog; }
    public Yodesk _root() { return _root; }
    public KaitaiStruct _parent() { return _parent; }
}
