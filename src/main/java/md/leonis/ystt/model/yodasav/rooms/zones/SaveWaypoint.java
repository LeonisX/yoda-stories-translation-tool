package md.leonis.ystt.model.yodasav.rooms.zones;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodasav.Yodasav;

import java.io.IOException;

public class SaveWaypoint extends KaitaiStruct {

    private long x;
    private long y;

    private final transient Yodasav root;
    private final transient SaveMonster parent;

    public static SaveWaypoint fromFile(String fileName) throws IOException {
        return new SaveWaypoint(new ByteBufferKaitaiInputStream(fileName));
    }

    public SaveWaypoint(KaitaiInputStream io) {
        this(io, null, null);
    }

    public SaveWaypoint(KaitaiInputStream io, SaveMonster parent) {
        this(io, parent, null);
    }

    public SaveWaypoint(KaitaiInputStream io, SaveMonster parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        x = io.readU4le();
        y = io.readU4le();
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU4le(x);
        os.writeU4le(y);
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public Yodasav getRoot() {
        return root;
    }

    public SaveMonster getParent() {
        return parent;
    }
}
