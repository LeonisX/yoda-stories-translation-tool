package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;

public class Waypoint extends KaitaiStruct {

    private long x;
    private long y;

    private final transient Yodesk root;
    private final transient Monster parent;

    public static Waypoint fromFile(String fileName) throws IOException {
        return new Waypoint(new ByteBufferKaitaiInputStream(fileName));
    }

    public Waypoint(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Waypoint(KaitaiInputStream io, Monster parent) {
        this(io, parent, null);
    }

    public Waypoint(KaitaiInputStream io, Monster parent, Yodesk root) {
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

    public Yodesk getRoot() {
        return root;
    }

    public Monster getParent() {
        return parent;
    }
}
