package md.leonis.ystt.model.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;

/**
 * In addition to actions some puzzles and events are triggered by
 * hotspots. These hotspots are triggered when the hero steps on them or
 * places an item at the location. Additionally, hotspots are used during
 * world generation to mark places where NPCs can spawn.
 */
public class Hotspot extends KaitaiStruct {

    private HotspotType type;
    private int x;
    private int y;
    // If disabled, hotspots can not be triggered. See instruction opcodes  called `enable_hotspot` and `disable_hotspot`.
    private int enabled;
    private int argument;

    private final Yodesk _root;
    private final Zone _parent;

    public static Hotspot fromFile(String fileName) throws IOException {
        return new Hotspot(new ByteBufferKaitaiStream(fileName));
    }

    public Hotspot(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Hotspot(KaitaiStream _io, Zone _parent) {
        this(_io, _parent, null);
    }

    public Hotspot(KaitaiStream _io, Zone _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.type = HotspotType.byId(this._io.readU4le());
        this.x = this._io.readU2le();
        this.y = this._io.readU2le();
        this.enabled = this._io.readU2le();
        this.argument = this._io.readU2le();
    }

    public HotspotType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getEnabled() {
        return enabled;
    }

    public int getArgument() {
        return argument;
    }

    public Yodesk get_root() {
        return _root;
    }

    public Zone get_parent() {
        return _parent;
    }
}