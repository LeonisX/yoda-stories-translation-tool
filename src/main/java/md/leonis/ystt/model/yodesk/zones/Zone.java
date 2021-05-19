package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;
import md.leonis.ystt.model.yodesk.Zones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private int sharedCounter; // 65535 in all maps (undefined)
    // Repetition of the `planet` field
    private int planetAgain;
    /**
     * `tile_ids` is made up of three interleaved tile layers ordered from bottom (floor) to top (roof).
     * Tiles are often references via 3 coordinates (xyz), which
     * corresponds to an index into this array calculated as `n = y * width * 3 + x * 3 = z`.
     */
    private List<ZoneSpot> tileIds;
    private int numHotspots;
    private List<Hotspot> hotspots;
    private ZoneAuxiliary izax;
    private ZoneAuxiliary2 izx2;
    private ZoneAuxiliary3 izx3;
    private ZoneAuxiliary4 izx4;
    private int numActions;
    private List<Action> actions;

    private final Yodesk root;
    private final Zones parent;

    public static Zone fromFile(String fileName) throws IOException {
        return new Zone(new ByteBufferKaitaiInputStream(fileName));
    }

    public Zone(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Zone(KaitaiInputStream io, Zones parent) {
        this(io, parent, null);
    }

    public Zone(KaitaiInputStream io, Zones parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        planet = Planet.byId(io.readU2le());
        size = io.readU4le();
        index = io.readU2le();
        marker = io.readBytes(4);
        if (!Arrays.equals(marker, new byte[]{73, 90, 79, 78})) { // IZON
            throw new KaitaiInputStream.ValidationNotEqualError(new byte[]{73, 90, 79, 78}, marker, getIo(), "/types/zone/seq/3");
        }
        size2 = io.readU4le();
        width = io.readU2le();
        height = io.readU2le();
        type = ZoneType.byId(io.readU4le());
        sharedCounter = io.readU2le();
        if (sharedCounter != 65535) {
            throw new KaitaiInputStream.ValidationNotEqualError(65535, sharedCounter, getIo(), "/types/zone/seq/8");
        }
        planetAgain = io.readU2le();
        tileIds = new ArrayList<>(width * height);
        for (int i = 0; i < width * height; i++) {
            tileIds.add(new ZoneSpot(io, this, root));
        }
        numHotspots = io.readU2le();
        hotspots = new ArrayList<>(numHotspots);
        for (int i = 0; i < numHotspots; i++) {
            hotspots.add(new Hotspot(io, this, root));
        }
        izax = new ZoneAuxiliary(io, this, root);
        izx2 = new ZoneAuxiliary2(io, this, root);
        izx3 = new ZoneAuxiliary3(io, this, root);
        izx4 = new ZoneAuxiliary4(io, this, root);
        numActions = io.readU2le();
        actions = new ArrayList<>(numActions);
        for (int i = 0; i < numActions; i++) {
            this.actions.add(new Action(io, this, root));
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU2le(planet.getId());
        os.writeU4le(size);
        os.writeU2le(index);
        os.writeBytesFull(marker);
        os.writeU4le(size2);
        os.writeU2le(width);
        os.writeU2le(height);
        os.writeU4le(type.getId());
        os.writeU2le(sharedCounter);
        os.writeU2le(planetAgain);

        for (ZoneSpot tileId : tileIds) {
            tileId.write(os);
        }

        os.writeU2le(numHotspots);

        for (Hotspot hotspot : hotspots) {
            hotspot.write(os);
        }

        izax.write(os);
        izx2.write(os);
        izx3.write(os);
        izx4.write(os);

        os.writeU2le(numActions);

        for (Action action : actions) {
            action.write(os);
        }
    }

    @SuppressWarnings("all")
    public void replaceTile(int tileId, int newTileId) {

        tileIds.forEach(zsp -> zsp.replaceTile(tileId, newTileId));

        Collections.replaceAll(izax.getGoalItems(), tileId, newTileId);
        Collections.replaceAll(izax.getRequiredItems(), tileId, newTileId);
        Collections.replaceAll(izx2.getProvidedItems(), tileId, newTileId);
    }

    public Planet getPlanet() {
        return planet;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
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

    public List<ZoneSpot> getTileIds() {
        return tileIds;
    }

    public int getNumHotspots() {
        return numHotspots;
    }

    public List<Hotspot> getHotspots() {
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

    public List<Action> getActions() {
        return actions;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Zones getParent() {
        return parent;
    }
}
