package md.leonis.ystt.model.yodesk.tiles;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;

public class TileAttributes extends KaitaiStruct {

    private boolean hasTransparency;
    private boolean isFloor;
    private boolean isObject;
    private boolean isDraggable;
    private boolean isRoof;
    private boolean isMap;
    private boolean isWeapon;
    private boolean isItem;
    private boolean isCharacter;
    private long unused;
    private Boolean isDoorway;
    private Boolean unused1;
    private Boolean isSpaceport;
    private Boolean isUnsolvedPuzzle;
    private Boolean isSolvedPuzzle;
    private Boolean isUnsolvedGateway;
    private Boolean isSolvedGateway;
    private Boolean isUnsolvedBlockadeNorth;
    private Boolean isUnsolvedBlockadeSouth;
    private Boolean isUnsolvedBlockadeWest;
    private Boolean isUnsolvedBlockadeEast;
    private Boolean isSolvedBlockadeNorth;
    private Boolean isSolvedBlockadeSouth;
    private Boolean isSolvedBlockadeWest;
    private Boolean isSolvedBlockadeEast;
    private Boolean isUnsolvedFinalChapter;
    private Boolean isLocationIndicator;
    private Boolean isKeycard;
    private Boolean isTool;
    private Boolean isPart;
    private Boolean isValuable;
    private Boolean isLocator;
    private Boolean unused2;
    private Boolean isEdible;
    private Boolean isBlaster;
    private Boolean isHeavy;
    private Boolean isLightsaber;
    private Boolean isTheForce;
    private Boolean isHero;
    private Boolean isEnemy;
    private Boolean isNpc;

    private final transient Yodesk root;
    private final transient Tile parent;

    public static TileAttributes fromFile(String fileName) throws IOException {
        return new TileAttributes(new ByteBufferKaitaiInputStream(fileName));
    }

    public TileAttributes(KaitaiInputStream io) {
        this(io, null, null);
    }

    public TileAttributes(KaitaiInputStream io, Tile parent) {
        this(io, parent, null);
    }

    public TileAttributes(KaitaiInputStream io, Tile parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    public TileAttributes(Tile parent, Yodesk root) {
        super(null);
        this.parent = parent;
        this.root = root;
    }

    private void _read() {

        hasTransparency = io.readBitsIntLe(1) != 0;
        isFloor = io.readBitsIntLe(1) != 0;
        isObject = io.readBitsIntLe(1) != 0;
        isDraggable = io.readBitsIntLe(1) != 0;
        isRoof = io.readBitsIntLe(1) != 0;
        isMap = io.readBitsIntLe(1) != 0;
        isWeapon = io.readBitsIntLe(1) != 0;
        isItem = io.readBitsIntLe(1) != 0;
        isCharacter = io.readBitsIntLe(1) != 0;
        unused = io.readBitsIntLe(7);
        if (isFloor()) {
            isDoorway = io.readBitsIntLe(1) != 0;
        }
        if (isMap()) {
            unused1 = io.readBitsIntLe(1) != 0;
            isSpaceport = io.readBitsIntLe(1) != 0;
            isUnsolvedPuzzle = io.readBitsIntLe(1) != 0;
            isSolvedPuzzle = io.readBitsIntLe(1) != 0;
            isUnsolvedGateway = io.readBitsIntLe(1) != 0;
            isSolvedGateway = io.readBitsIntLe(1) != 0;
            isUnsolvedBlockadeNorth = io.readBitsIntLe(1) != 0;
            isUnsolvedBlockadeSouth = io.readBitsIntLe(1) != 0;
            isUnsolvedBlockadeWest = io.readBitsIntLe(1) != 0;
            isUnsolvedBlockadeEast = io.readBitsIntLe(1) != 0;
            isSolvedBlockadeNorth = io.readBitsIntLe(1) != 0;
            isSolvedBlockadeSouth = io.readBitsIntLe(1) != 0;
            isSolvedBlockadeWest = io.readBitsIntLe(1) != 0;
            isSolvedBlockadeEast = io.readBitsIntLe(1) != 0;
            isUnsolvedFinalChapter = io.readBitsIntLe(1) != 0;
            isLocationIndicator = io.readBitsIntLe(1) != 0;
        }
        if (isItem()) {
            isKeycard = io.readBitsIntLe(1) != 0;
            isTool = io.readBitsIntLe(1) != 0;
            isPart = io.readBitsIntLe(1) != 0;
            isValuable = io.readBitsIntLe(1) != 0;
            isLocator = io.readBitsIntLe(1) != 0;
            unused2 = io.readBitsIntLe(1) != 0;
            isEdible = io.readBitsIntLe(1) != 0;
        }
        if (isWeapon()) {
            isBlaster = io.readBitsIntLe(1) != 0;
            isHeavy = io.readBitsIntLe(1) != 0;
            isLightsaber = io.readBitsIntLe(1) != 0;
            isTheForce = io.readBitsIntLe(1) != 0;
        }
        if (isCharacter()) {
            isHero = io.readBitsIntLe(1) != 0;
            isEnemy = io.readBitsIntLe(1) != 0;
            isNpc = io.readBitsIntLe(1) != 0;
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        // unneeded, we write raw bytes in Tiles class
    }

    /**
     * Affects how tile image should be drawn. If set, the value 0 in `pixels` is treated as transparent.
     * Otherwise it is drawn as black.
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
     * Tile is used in world mini-map
     */
    public boolean isMap() {
        return isMap;
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
    public Boolean isSpaceport() {
        return isSpaceport;
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
     * Marks a gateway on the map that has not been solved
     */
    public Boolean isUnsolvedGateway() {
        return isUnsolvedGateway;
    }

    /**
     * Marks a solved gateway on the map
     */
    public Boolean isSolvedGateway() {
        return isSolvedGateway;
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
     * Marks a solved sector on the map that used to block access to northern zones
     */
    public Boolean isSolvedBlockadeNorth() {
        return isSolvedBlockadeNorth;
    }

    /**
     * Marks a solved sector on the map that used to block access to southern zones
     */
    public Boolean isSolvedBlockadeSouth() {
        return isSolvedBlockadeSouth;
    }

    /**
     * Marks a solved sector on the map that used to block access to western zones
     */
    public Boolean isSolvedBlockadeWest() {
        return isSolvedBlockadeWest;
    }

    /**
     * Marks a solved sector on the map that used to block access to eastern zones
     */
    public Boolean isSolvedBlockadeEast() {
        return isSolvedBlockadeEast;
    }

    /**
     * The final puzzle of the world. Solving this wins the game
     */
    public Boolean isUnsolvedFinalChapter() {
        return isUnsolvedFinalChapter;
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

    public Boolean isLocator() {
        return isLocator;
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
    public Boolean isBlaster() {
        return isBlaster;
    }

    /**
     * Item is a high intensity blaster (like the blaster rifle), or thermal detonator
     */
    public Boolean isHeavy() {
        return isHeavy;
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

    public Yodesk getRoot() {
        return root;
    }

    public Tile getParent() {
        return parent;
    }
}
