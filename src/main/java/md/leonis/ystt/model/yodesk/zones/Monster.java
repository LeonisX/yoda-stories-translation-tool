package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A monster is a enemy in a zone.
 */
public class Monster extends KaitaiStruct {

    private int character;
    private int x;
    private int y;
    // References the item (loot - 1) that will be dropped if the monster is killed.
    // If set to `0xFFFF` the current zone's quest item will be dropped.
    private int loot;
    // If this field is anything other than 0 the monster may drop an item when killed.
    private long dropsLoot;
    private ArrayList<Waypoint> waypoints;

    private final Yodesk root;
    private final ZoneAuxiliary parent;

    public static Monster fromFile(String fileName) throws IOException {
        return new Monster(new ByteBufferKaitaiStream(fileName));
    }

    public Monster(KaitaiStream io) {
        this(io, null, null);
    }

    public Monster(KaitaiStream io, ZoneAuxiliary parent) {
        this(io, parent, null);
    }

    public Monster(KaitaiStream io, ZoneAuxiliary parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.character = this.io.readU2le();
        this.x = this.io.readU2le();
        this.y = this.io.readU2le();
        this.loot = this.io.readU2le();
        this.dropsLoot = this.io.readU4le();
        waypoints = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            this.waypoints.add(new Waypoint(this.io, this, root));
        }
    }

    public int getCharacter() {
        return character;
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

    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }

    public Yodesk getRoot() {
        return root;
    }

    public ZoneAuxiliary getParent() {
        return parent;
    }
}
