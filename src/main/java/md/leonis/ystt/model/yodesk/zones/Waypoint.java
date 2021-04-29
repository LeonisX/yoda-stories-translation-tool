package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;

public class Waypoint extends KaitaiStruct {

    private long x;
    private long y;

    private final Yodesk root;
    private final Monster parent;

    public static Waypoint fromFile(String fileName) throws IOException {
        return new Waypoint(new ByteBufferKaitaiStream(fileName));
    }

    public Waypoint(KaitaiStream io) {
        this(io, null, null);
    }

    public Waypoint(KaitaiStream io, Monster parent) {
        this(io, parent, null);
    }

    public Waypoint(KaitaiStream io, Monster parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.x = this.io.readU4le();
        this.y = this.io.readU4le();
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
