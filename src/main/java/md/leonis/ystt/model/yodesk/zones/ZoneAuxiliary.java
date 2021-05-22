package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
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

    private final transient Yodesk root;
    private final transient Zone parent;

    public static ZoneAuxiliary fromFile(String fileName) throws IOException {
        return new ZoneAuxiliary(new ByteBufferKaitaiInputStream(fileName));
    }

    public ZoneAuxiliary(KaitaiInputStream io) {
        this(io, null, null);
    }

    public ZoneAuxiliary(KaitaiInputStream io, Zone parent) {
        this(io, parent, null);
    }

    public ZoneAuxiliary(KaitaiInputStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        marker = io.readBytes(4);
        if (!(Arrays.equals(marker, new byte[]{73, 90, 65, 88}))) { // IZAX
            throw new KaitaiInputStream.ValidationNotEqualError(new byte[]{73, 90, 65, 88}, marker, getIo(), "/types/zone_auxiliary/seq/0");
        }
        size = io.readU4le();
        _unnamed2 = io.readU2le();
        numMonsters = io.readU2le();
        monsters = new ArrayList<>(numMonsters);
        for (int i = 0; i < numMonsters; i++) {
            monsters.add(new Monster(io, this, root));
        }
        numRequiredItems = io.readU2le();
        requiredItems = new ArrayList<>(numRequiredItems);
        for (int i = 0; i < numRequiredItems; i++) {
            requiredItems.add(io.readU2le());
        }
        numGoalItems = io.readU2le();
        goalItems = new ArrayList<>(numGoalItems);
        for (int i = 0; i < numGoalItems; i++) {
            goalItems.add(io.readU2le());
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeBytesFull(marker);
        os.writeU4le(size);
        os.writeU2le(_unnamed2);
        os.writeU2le(numMonsters);

        for (Monster monster : monsters) {
            monster.write(os);
        }

        os.writeU2le(numRequiredItems);

        for (Integer requiredItem : requiredItems) {
            os.writeU2le(requiredItem);
        }

        os.writeU2le(numGoalItems);

        for (Integer goalItem : goalItems) {
            os.writeU2le(goalItem);
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
