package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

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

    private final Yodesk root;
    private final Zone parent;

    public static Hotspot fromFile(String fileName) throws IOException {
        return new Hotspot(new ByteBufferKaitaiStream(fileName));
    }

    public Hotspot(KaitaiStream io) {
        this(io, null, null);
    }

    public Hotspot(KaitaiStream io, Zone parent) {
        this(io, parent, null);
    }

    public Hotspot(KaitaiStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.type = HotspotType.byId(this.io.readU4le());
        this.x = this.io.readU2le();
        this.y = this.io.readU2le();
        this.enabled = this.io.readU2le();
        this.argument = this.io.readU2le();
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

    public Yodesk getRoot() {
        return root;
    }

    public Zone getParent() {
        return parent;
    }
}
