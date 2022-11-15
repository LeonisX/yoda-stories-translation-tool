package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
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
    // If disabled, hotspots can not be triggered. See instruction opcodes called `enable_hotspot` and `disable_hotspot`.
    private int enabled;
    private int argument;

    private final transient Yodesk root;
    private final transient Zone parent;

    public static Hotspot fromFile(String fileName) throws IOException {
        return new Hotspot(new ByteBufferKaitaiInputStream(fileName));
    }

    public Hotspot(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Hotspot(KaitaiInputStream io, Zone parent) {
        this(io, parent, null);
    }

    public Hotspot(KaitaiInputStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        type = HotspotType.byId(io.readU4le());
        x = io.readU2le();
        y = io.readU2le();
        enabled = io.readU2le();
        argument = io.readU2le();
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU4le(type.getId());
        os.writeU2le(x);
        os.writeU2le(y);
        os.writeU2le(enabled);
        os.writeU2le(argument);
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
