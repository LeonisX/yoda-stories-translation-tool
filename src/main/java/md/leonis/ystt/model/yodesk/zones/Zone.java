package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;
import md.leonis.ystt.model.yodesk.Zones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Zone extends KaitaiStruct {

    /**
     * Planet this zone can be placed on.
     * <p>
     * During world generation the goal puzzle dictates which planet is
     * chosen. Apart from `swamp` zones, only the zones with type `empty`
     * or the chosen type are loaded when a game is in progress.
     */
    private Planet planet;
    private long size;
    private int index;
    private byte[] marker;
    private long size2;
    // Width of the zone in tiles. Either 9 or 18.
    private int width;
    // Height of the zone in tiles. Either 9 or 18.
    private int height;
    private ZoneType type;
    // Scripting register shared between the zone and its rooms.
    private int sharedCounter;
    // Repetition of the `planet` field
    private int planetAgain;
    /**
     * `tile_ids` is made up of three interleaved tile layers ordered from bottom (floor) to top (roof).
     * Tiles are often references via 3 coordinates (xyz), which
     * corresponds to an index into this array calculated as `n = y * width * 3 + x * 3 = z`.
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

    private final Yodesk root;
    private final Zones parent;

    public static Zone fromFile(String fileName) throws IOException {
        return new Zone(new ByteBufferKaitaiStream(fileName));
    }

    public Zone(KaitaiStream io) {
        this(io, null, null);
    }

    public Zone(KaitaiStream io, Zones parent) {
        this(io, parent, null);
    }

    public Zone(KaitaiStream io, Zones parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.planet = Planet.byId(this.io.readU2le());
        this.size = this.io.readU4le();
        this.index = this.io.readU2le();
        this.marker = this.io.readBytes(4);
        if (!(Arrays.equals(marker, new byte[]{73, 90, 79, 78}))) { // IZON
            throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 90, 79, 78}, marker, getIo(), "/types/zone/seq/3");
        }
        this.size2 = this.io.readU4le();
        this.width = this.io.readU2le();
        this.height = this.io.readU2le();
        this.type = ZoneType.byId(this.io.readU4le());
        this.sharedCounter = this.io.readU2le();
        if (sharedCounter != 65535) {
            throw new KaitaiStream.ValidationNotEqualError(65535, sharedCounter, getIo(), "/types/zone/seq/8");
        }
        this.planetAgain = this.io.readU2le();
        tileIds = new ArrayList<>(width * height);
        for (int i = 0; i < width * height; i++) {
            this.tileIds.add(new ZoneSpot(this.io, this, root));
        }
        this.numHotspots = this.io.readU2le();
        hotspots = new ArrayList<>(numHotspots);
        for (int i = 0; i < numHotspots; i++) {
            this.hotspots.add(new Hotspot(this.io, this, root));
        }
        this.izax = new ZoneAuxiliary(this.io, this, root);
        this.izx2 = new ZoneAuxiliary2(this.io, this, root);
        this.izx3 = new ZoneAuxiliary3(this.io, this, root);
        this.izx4 = new ZoneAuxiliary4(this.io, this, root);
        this.numActions = this.io.readU2le();
        actions = new ArrayList<>(numActions);
        for (int i = 0; i < numActions; i++) {
            this.actions.add(new Action(this.io, this, root));
        }
    }

    public Planet getPlanet() {
        return planet;
    }

    public long getSize() {
        return size;
    }

    public int getIndex() {
        return index;
    }

    public byte[] getMarker() {
        return marker;
    }

    public long getSize2() {
        return size2;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ZoneType getType() {
        return type;
    }

    public int getSharedCounter() {
        return sharedCounter;
    }

    public int getPlanetAgain() {
        return planetAgain;
    }

    public ArrayList<ZoneSpot> getTileIds() {
        return tileIds;
    }

    public int getNumHotspots() {
        return numHotspots;
    }

    public ArrayList<Hotspot> getHotspots() {
        return hotspots;
    }

    public ZoneAuxiliary getIzax() {
        return izax;
    }

    public ZoneAuxiliary2 getIzx2() {
        return izx2;
    }

    public ZoneAuxiliary3 getIzx3() {
        return izx3;
    }

    public ZoneAuxiliary4 getIzx4() {
        return izx4;
    }

    public int getNumActions() {
        return numActions;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Zones getParent() {
        return parent;
    }
}
