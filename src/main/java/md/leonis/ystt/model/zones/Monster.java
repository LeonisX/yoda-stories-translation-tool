package md.leonis.ystt.model.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A monster is a enemy in a zone.
 */
public class Monster extends KaitaiStruct {

    private int character;
    private int x;
    private int y;
    /**
     * References the item (loot - 1) that will be dropped if the monster
     * is killed. If set to `0xFFFF` the current zone's quest item will be
     * dropped.
     */
    private int loot;
    /**
     * If this field is anything other than 0 the monster may drop an item when killed.
     */
    private long dropsLoot;
    private ArrayList<Waypoint> waypoints;

    private final Yodesk _root;
    private final ZoneAuxiliary _parent;

    public static Monster fromFile(String fileName) throws IOException {
        return new Monster(new ByteBufferKaitaiStream(fileName));
    }

    public Monster(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Monster(KaitaiStream _io, ZoneAuxiliary _parent) {
        this(_io, _parent, null);
    }

    public Monster(KaitaiStream _io, ZoneAuxiliary _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.character = this._io.readU2le();
        this.x = this._io.readU2le();
        this.y = this._io.readU2le();
        this.loot = this._io.readU2le();
        this.dropsLoot = this._io.readU4le();
        waypoints = new ArrayList<>(((Number) (4)).intValue());
        for (int i = 0; i < 4; i++) {
            this.waypoints.add(new Waypoint(this._io, this, _root));
        }
    }

    public int character() { return character; }
    public int x() { return x; }
    public int y() { return y; }
    public int loot() { return loot; }
    public long dropsLoot() { return dropsLoot; }
    public ArrayList<Waypoint> waypoints() { return waypoints; }
    public Yodesk _root() { return _root; }
    public ZoneAuxiliary _parent() { return _parent; }
}
