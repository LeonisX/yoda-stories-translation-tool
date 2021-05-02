package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.Arrays;

public class ZoneAuxiliary4 extends KaitaiStruct {

    private byte[] marker;
    private long size;
    private int _unnamed2; // 3 swamp maps, except start map: 0; all other maps: 1

    private final Yodesk root;
    private final Zone parent;

    public static ZoneAuxiliary4 fromFile(String fileName) throws IOException {
        return new ZoneAuxiliary4(new ByteBufferKaitaiStream(fileName));
    }

    public ZoneAuxiliary4(KaitaiStream io) {
        this(io, null, null);
    }

    public ZoneAuxiliary4(KaitaiStream io, Zone parent) {
        this(io, parent, null);
    }

    public ZoneAuxiliary4(KaitaiStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.marker = this.io.readBytes(4);
        if (!(Arrays.equals(marker, new byte[]{73, 90, 88, 52}))) { // IZX4
            throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 90, 88, 52}, marker, getIo(), "/types/zone_auxiliary_4/seq/0");
        }
        this.size = this.io.readU4le();
        this._unnamed2 = this.io.readU2le();
    }

    public byte[] getMarker() {
        return marker;
    }

    public long getSize() {
        return size;
    }

    public int get_unnamed2() {
        return _unnamed2;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Zone getParent() {
        return parent;
    }
}
