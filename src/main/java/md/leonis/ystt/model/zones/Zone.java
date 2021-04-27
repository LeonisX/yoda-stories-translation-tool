package md.leonis.ystt.model.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;
import md.leonis.ystt.model.Zones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Zone extends KaitaiStruct {

    /**
     * Planet this zone can be placed on.
     *
     * During world generation the goal puzzle dictates which planet is
     * chosen. Apart from `swamp` zones, only the zones with type `empty`
     * or the chosen type are loaded when a game is in progress.
     */
    private Planet planet;
    private long size;
    private int index;
    private byte[] marker;
    private long size2;
    /**
     * Width of the zone in tiles. Either 9 or 18.
     */
    private int width;
    /**
     * Height of the zone in tiles. Either 9 or 18.
     */
    private int height;
    private ZoneType type;
    /**
     * Scripting register shared between the zone and its rooms.
     */
    private int sharedCounter;
    /**
     * Repetition of the `planet` field
     */
    private int planetAgain;
    /**
     * `tile_ids` is made up of three interleaved tile layers ordered from
     * bottom (floor) to top (roof).
     * Tiles are often references via 3 coordinates (xyz), which
     * corresponds to an index into this array calculated as `n = y * width
     * * 3 + x * 3 = z`.
     */
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
    private final Zones _parent;

    public static Zone fromFile(String fileName) throws IOException {
        return new Zone(new ByteBufferKaitaiStream(fileName));
    }

    public Zone(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Zone(KaitaiStream _io, Zones _parent) {
        this(_io, _parent, null);
    }

    public Zone(KaitaiStream _io, Zones _parent, Yodesk _root) {
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

    public Planet planet() { return planet; }
    public long size() { return size; }
    public int index() { return index; }
    public byte[] marker() { return marker; }
    public long size2() { return size2; }
    public int width() { return width; }
    public int height() { return height; }
    public ZoneType type() { return type; }
    public int sharedCounter() { return sharedCounter; }
    public int planetAgain() { return planetAgain; }
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
    public Zones _parent() { return _parent; }
}
