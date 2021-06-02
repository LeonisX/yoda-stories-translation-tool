package md.leonis.ystt.model.yodasav.rooms.zones;

import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodasav.Yodasav;
import md.leonis.ystt.model.yodesk.zones.HotspotType;

/**
 * In addition to actions some puzzles and events are triggered by
 * hotspots. These hotspots are triggered when the hero steps on them or
 * places an item at the location. Additionally, hotspots are used during
 * world generation to mark places where NPCs can spawn.
 */
public class SaveHotspot extends KaitaiStruct {

    private HotspotType type;
    private short x;
    private short y;
    // If disabled, hotspots can not be triggered. See instruction opcodes  called `enable_hotspot` and `disable_hotspot`.
    private boolean enabled;
    private short argument;

    private int enabledInt;
    private final transient Yodasav root;
    private final transient SaveZone parent;

    public SaveHotspot(KaitaiInputStream io, SaveZone parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        enabledInt = io.readU2le();
        enabled = enabledInt != 0;
		argument = io.readS2le();
		type = HotspotType.byId(io.readU4le());
		x = io.readS2le();
		y = io.readS2le();
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU2le(enabledInt);
        os.writeS2le(argument);
        os.writeU4le(type.getId());
        os.writeS2le(x);
        os.writeS2le(y);
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

    public boolean isEnabled() {
        return enabled;
    }

    public int getArgument() {
        return argument;
    }

    public Yodasav getRoot() {
        return root;
    }

    public SaveZone getParent() {
        return parent;
    }
}
