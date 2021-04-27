package md.leonis.ystt.model.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;

public class Waypoint extends KaitaiStruct {

    private long x;
    private long y;

    private final Yodesk _root;
    private final Monster _parent;

    public static Waypoint fromFile(String fileName) throws IOException {
        return new Waypoint(new ByteBufferKaitaiStream(fileName));
    }

    public Waypoint(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Waypoint(KaitaiStream _io, Monster _parent) {
        this(_io, _parent, null);
    }

    public Waypoint(KaitaiStream _io, Monster _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.x = this._io.readU4le();
        this.y = this._io.readU4le();
    }

    public long x() { return x; }
    public long y() { return y; }
    public Yodesk _root() { return _root; }
    public Monster _parent() { return _parent; }
}
