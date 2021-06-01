package md.leonis.ystt.model.yodasav.rooms.zones;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodasav.Yodasav;
import md.leonis.ystt.model.yodesk.zones.HotspotType;

import java.io.IOException;

/**
 * In addition to actions some puzzles and events are triggered by
 * hotspots. These hotspots are triggered when the hero steps on them or
 * places an item at the location. Additionally, hotspots are used during
 * world generation to mark places where NPCs can spawn.
 */
public class SaveHotspot extends KaitaiStruct {

    private HotspotType type;
    private int x;
    private int y;
    // If disabled, hotspots can not be triggered. See instruction opcodes  called `enable_hotspot` and `disable_hotspot`.
    private boolean enabled;
    private int argument;

    private final transient Yodasav root;
    private final transient SaveZone parent;

    public static SaveHotspot fromFile(String fileName) throws IOException {
        return new SaveHotspot(new ByteBufferKaitaiInputStream(fileName));
    }

    public SaveHotspot(KaitaiInputStream io) {
        this(io, null, null);
    }

    public SaveHotspot(KaitaiInputStream io, SaveZone parent) {
        this(io, parent, null);
    }

    public SaveHotspot(KaitaiInputStream io, SaveZone parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        enabled = io.readU2le() != 0;
		argument = io.readS2le();
		type = HotspotType.byId(io.readU4le());
		x = io.readS2le();
		y = io.readS2le();
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
        /*os.writeU4le(type.getId());
        os.writeU2le(x);
        os.writeU2le(y);
        os.writeU2le(enabled);
        os.writeU2le(argument);*/
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
