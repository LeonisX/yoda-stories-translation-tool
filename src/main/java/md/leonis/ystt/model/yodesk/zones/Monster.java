package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A monster is a enemy in a zone.
 */
public class Monster extends KaitaiStruct {

    private int characterId;
    private int x;
    private int y;
    // References the item (loot - 1) that will be dropped if the monster is killed.
    // If set to `0xFFFF` the current zone's quest item will be dropped.
    private int loot;
    // If this field is anything other than 0 the monster may drop an item when killed.
    private long dropsLoot;
    private List<Waypoint> waypoints;

    private final transient Yodesk root;
    private final transient ZoneAuxiliary parent;

    public static Monster fromFile(String fileName) throws IOException {
        return new Monster(new ByteBufferKaitaiInputStream(fileName));
    }

    public Monster(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Monster(KaitaiInputStream io, ZoneAuxiliary parent) {
        this(io, parent, null);
    }

    public Monster(KaitaiInputStream io, ZoneAuxiliary parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        characterId = io.readU2le();
        x = io.readU2le();
        y = io.readU2le();
        loot = io.readU2le();
        dropsLoot = io.readU4le();
        waypoints = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            waypoints.add(new Waypoint(io, this, root));
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU2le(characterId);
        os.writeU2le(x);
        os.writeU2le(y);
        os.writeU2le(loot);
        os.writeU4le(dropsLoot);

        for (int i = 0; i < 4; i++) {
            waypoints.get(i).write(os);
        }
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLoot() {
        return loot;
    }

    public long getDropsLoot() {
        return dropsLoot;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public Yodesk getRoot() {
        return root;
    }

    public ZoneAuxiliary getParent() {
        return parent;
    }
}
