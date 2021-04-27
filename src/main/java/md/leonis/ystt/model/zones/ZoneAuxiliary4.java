package md.leonis.ystt.model.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.Arrays;

public class ZoneAuxiliary4 extends KaitaiStruct {

    private byte[] marker;
    private long size;
    private int _unnamed2;

    private final Yodesk _root;
    private final Zone _parent;

    public static ZoneAuxiliary4 fromFile(String fileName) throws IOException {
        return new ZoneAuxiliary4(new ByteBufferKaitaiStream(fileName));
    }

    public ZoneAuxiliary4(KaitaiStream _io) {
        this(_io, null, null);
    }

    public ZoneAuxiliary4(KaitaiStream _io, Zone _parent) {
        this(_io, _parent, null);
    }

    public ZoneAuxiliary4(KaitaiStream _io, Zone _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.marker = this._io.readBytes(4);
        if (!(Arrays.equals(marker(), new byte[]{73, 90, 88, 52}))) {
            throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 90, 88, 52}, marker(), _io(), "/types/zone_auxiliary_4/seq/0");
        }
        this.size = this._io.readU4le();
        this._unnamed2 = this._io.readU2le();
    }

    public byte[] marker() {
        return marker;
    }

    public long size() {
        return size;
    }

    public int _unnamed2() {
        return _unnamed2;
    }

    public Yodesk _root() {
        return _root;
    }

    public Zone _parent() {
        return _parent;
    }
}
