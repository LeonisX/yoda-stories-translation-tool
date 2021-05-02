package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZoneAuxiliary extends KaitaiStruct {

    private byte[] marker;
    private long size;
    private int _unnamed2; // 0 or 1
    private int numMonsters;
    private List<Monster> monsters;
    private int numRequiredItems;
    // List of items that can be used to solve the zone.
    private List<Integer> requiredItems;
    private int numGoalItems;
    // Additional items that are needed to solve the zone. Only used if the zone type is `goal`.
    private List<Integer> goalItems;

    private final Yodesk root;
    private final Zone parent;

    public static ZoneAuxiliary fromFile(String fileName) throws IOException {
        return new ZoneAuxiliary(new ByteBufferKaitaiStream(fileName));
    }

    public ZoneAuxiliary(KaitaiStream io) {
        this(io, null, null);
    }

    public ZoneAuxiliary(KaitaiStream io, Zone parent) {
        this(io, parent, null);
    }

    public ZoneAuxiliary(KaitaiStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.marker = this.io.readBytes(4);
        if (!(Arrays.equals(marker, new byte[]{73, 90, 65, 88}))) { // IZAX
            throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 90, 65, 88}, marker, getIo(), "/types/zone_auxiliary/seq/0");
        }
        this.size = this.io.readU4le();
        this._unnamed2 = this.io.readU2le();
        this.numMonsters = this.io.readU2le();
        monsters = new ArrayList<>(numMonsters);
        for (int i = 0; i < numMonsters; i++) {
            this.monsters.add(new Monster(this.io, this, root));
        }
        this.numRequiredItems = this.io.readU2le();
        requiredItems = new ArrayList<>(numRequiredItems);
        for (int i = 0; i < numRequiredItems; i++) {
            this.requiredItems.add(this.io.readU2le());
        }
        this.numGoalItems = this.io.readU2le();
        goalItems = new ArrayList<>(numGoalItems);
        for (int i = 0; i < numGoalItems; i++) {
            this.goalItems.add(this.io.readU2le());
        }
    }

    public byte[] getMarker() {
        return marker;
    }

    public long getSize() {
        return size;
    }

    public int get_unnamed2() {
        return _unnamed2;
    }

    public int getNumMonsters() {
        return numMonsters;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public int getNumRequiredItems() {
        return numRequiredItems;
    }

    public List<Integer> getRequiredItems() {
        return requiredItems;
    }

    public int getNumGoalItems() {
        return numGoalItems;
    }

    public List<Integer> getGoalItems() {
        return goalItems;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Zone getParent() {
        return parent;
    }
}
