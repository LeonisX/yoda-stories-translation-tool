package md.leonis.ystt.model.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ZoneAuxiliary extends KaitaiStruct {

    private byte[] marker;
    private long size;
    private int _unnamed2;
    private int numMonsters;
    private ArrayList<Monster> monsters;
    private int numRequiredItems;
    // List of items that can be used to solve the zone.
    private ArrayList<Integer> requiredItems;
    private int numGoalItems;
    // Additional items that are needed to solve the zone. Only used if the zone type is `goal`.
    private ArrayList<Integer> goalItems;

    private final Yodesk _root;
    private final Zone _parent;

    public static ZoneAuxiliary fromFile(String fileName) throws IOException {
        return new ZoneAuxiliary(new ByteBufferKaitaiStream(fileName));
    }

    public ZoneAuxiliary(KaitaiStream _io) {
        this(_io, null, null);
    }

    public ZoneAuxiliary(KaitaiStream _io, Zone _parent) {
        this(_io, _parent, null);
    }

    public ZoneAuxiliary(KaitaiStream _io, Zone _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.marker = this._io.readBytes(4);
        if (!(Arrays.equals(marker, new byte[]{73, 90, 65, 88}))) { // IZAX
            throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 90, 65, 88}, marker, _io(), "/types/zone_auxiliary/seq/0");
        }
        this.size = this._io.readU4le();
        this._unnamed2 = this._io.readU2le();
        this.numMonsters = this._io.readU2le();
        monsters = new ArrayList<>(numMonsters);
        for (int i = 0; i < numMonsters; i++) {
            this.monsters.add(new Monster(this._io, this, _root));
        }
        this.numRequiredItems = this._io.readU2le();
        requiredItems = new ArrayList<>(numRequiredItems);
        for (int i = 0; i < numRequiredItems; i++) {
            this.requiredItems.add(this._io.readU2le());
        }
        this.numGoalItems = this._io.readU2le();
        goalItems = new ArrayList<>(numGoalItems);
        for (int i = 0; i < numGoalItems; i++) {
            this.goalItems.add(this._io.readU2le());
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

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public int getNumRequiredItems() {
        return numRequiredItems;
    }

    public ArrayList<Integer> getRequiredItems() {
        return requiredItems;
    }

    public int getNumGoalItems() {
        return numGoalItems;
    }

    public ArrayList<Integer> getGoalItems() {
        return goalItems;
    }

    public Yodesk get_root() {
        return _root;
    }

    public Zone get_parent() {
        return _parent;
    }
}
