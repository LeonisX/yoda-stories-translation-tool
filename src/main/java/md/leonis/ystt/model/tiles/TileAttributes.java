package md.leonis.ystt.model.tiles;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;

public class TileAttributes extends KaitaiStruct {

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
    private final Tile _parent;

    public static TileAttributes fromFile(String fileName) throws IOException {
        return new TileAttributes(new ByteBufferKaitaiStream(fileName));
    }

    public TileAttributes(KaitaiStream _io) {
        this(_io, null, null);
    }

    public TileAttributes(KaitaiStream _io, Tile _parent) {
        this(_io, _parent, null);
    }

    public TileAttributes(KaitaiStream _io, Tile _parent, Yodesk _root) {
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

    /**
     * Affects how tile image should be drawn. If set, the
     * value 0 in `pixels` is treated as transparent. Otherwise
     * it is drawn as black.
     */
    public boolean hasTransparency() {
        return hasTransparency;
    }

    /**
     * Tile is usually placed on the lowest layer of a zone
     */
    public boolean isFloor() {
        return isFloor;
    }

    /**
     * Object, tile is usually placed on the middle layer of a zone
     */
    public boolean isObject() {
        return isObject;
    }

    /**
     * If set and the tile is placed on the object layer it can be
     * dragged and pushed around by the hero.
     */
    public boolean isDraggable() {
        return isDraggable;
    }

    /**
     * Tile is usually placed on the top layer (roof)
     */
    public boolean isRoof() {
        return isRoof;
    }

    /**
     * Locator, tile is used in world map view overview
     */
    public boolean isLocator() {
        return isLocator;
    }

    /**
     * Identifies tiles that are mapped to weapons
     */
    public boolean isWeapon() {
        return isWeapon;
    }

    public boolean isItem() {
        return isItem;
    }

    /**
     * Tile forms part of a character
     */
    public boolean isCharacter() {
        return isCharacter;
    }

    public long unused() {
        return unused;
    }

    /**
     * These tiles are doorways, monsters can't go
     */
    public Boolean isDoorway() {
        return isDoorway;
    }

    public Boolean unused1() {
        return unused1;
    }

    /**
     * Marks the spaceport on the map
     */
    public Boolean isTown() {
        return isTown;
    }

    /**
     * Marks a discovered, but unsolved puzzle on the map
     */
    public Boolean isUnsolvedPuzzle() {
        return isUnsolvedPuzzle;
    }

    /**
     * Marks a solved puzzle on the map
     */
    public Boolean isSolvedPuzzle() {
        return isSolvedPuzzle;
    }

    /**
     * Marks a place of travel on the map that has not been solved
     */
    public Boolean isUnsolvedTravel() {
        return isUnsolvedTravel;
    }

    /**
     * Marks a solved place of travel on the map
     */
    public Boolean isSolvedTravel() {
        return isSolvedTravel;
    }

    /**
     * Marks a sector on the map that blocks access to northern zones
     */
    public Boolean isUnsolvedBlockadeNorth() {
        return isUnsolvedBlockadeNorth;
    }

    /**
     * Marks a sector on the map that blocks access to southern zones
     */
    public Boolean isUnsolvedBlockadeSouth() {
        return isUnsolvedBlockadeSouth;
    }

    /**
     * Marks a sector on the map that blocks access to western zones
     */
    public Boolean isUnsolvedBlockadeWest() {
        return isUnsolvedBlockadeWest;
    }

    /**
     * Marks a sector on the map that blocks access to eastern zones
     */
    public Boolean isUnsolvedBlockadeEast() {
        return isUnsolvedBlockadeEast;
    }

    /**
     * Marks a solved sector on the map that used to block access to
     * northern zones
     */
    public Boolean isSolvedBlockadeNorth() {
        return isSolvedBlockadeNorth;
    }

    /**
     * Marks a solved sector on the map that used to block access to
     * southern zones
     */
    public Boolean isSolvedBlockadeSouth() {
        return isSolvedBlockadeSouth;
    }

    /**
     * Marks a solved sector on the map that used to block access to
     * western zones
     */
    public Boolean isSolvedBlockadeWest() {
        return isSolvedBlockadeWest;
    }

    /**
     * Marks a solved sector on the map that used to block access to
     * eastern zones
     */
    public Boolean isSolvedBlockadeEast() {
        return isSolvedBlockadeEast;
    }

    /**
     * The final puzzle of the world. Solving this wins the game
     */
    public Boolean isUnsolvedGoal() {
        return isUnsolvedGoal;
    }

    /**
     * Overlay to mark the current position on the map
     */
    public Boolean isLocationIndicator() {
        return isLocationIndicator;
    }

    public Boolean isKeycard() {
        return isKeycard;
    }

    public Boolean isTool() {
        return isTool;
    }

    public Boolean isPart() {
        return isPart;
    }

    public Boolean isValuable() {
        return isValuable;
    }

    public Boolean isMap() {
        return isMap;
    }

    public Boolean unused2() {
        return unused2;
    }

    public Boolean isEdible() {
        return isEdible;
    }

    /**
     * Item is a low intensity blaster (like the blaster pistol)
     */
    public Boolean isLowBlaster() {
        return isLowBlaster;
    }

    /**
     * Item is a high intensity blaster (like the blaster rifle)
     */
    public Boolean isHighBlaster() {
        return isHighBlaster;
    }

    public Boolean isLightsaber() {
        return isLightsaber;
    }

    public Boolean isTheForce() {
        return isTheForce;
    }

    public Boolean isHero() {
        return isHero;
    }

    public Boolean isEnemy() {
        return isEnemy;
    }

    public Boolean isNpc() {
        return isNpc;
    }

    public Yodesk _root() {
        return _root;
    }

    public Tile _parent() {
        return _parent;
    }
}
