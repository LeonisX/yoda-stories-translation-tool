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
    /**
     * List of items that can be used to solve the zone.
     */
    private ArrayList<Integer> requiredItems;
    private int numGoalItems;
    /**
     * Additional items that are needed to solve the zone. Only used if the
     * zone type is `goal`.
     */
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

    public byte[] marker() { return marker; }
    public long size() { return size; }
    public int _unnamed2() { return _unnamed2; }
    public int numMonsters() { return numMonsters; }
    public ArrayList<Monster> monsters() { return monsters; }
    public int numRequiredItems() { return numRequiredItems; }
    public ArrayList<Integer> requiredItems() { return requiredItems; }
    public int numGoalItems() { return numGoalItems; }
    public ArrayList<Integer> goalItems() { return goalItems; }
    public Yodesk _root() { return _root; }
    public Zone _parent() { return _parent; }
}
