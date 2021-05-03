package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
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
        return new ZoneAuxiliary4(new ByteBufferKaitaiInputStream(fileName));
    }

    public ZoneAuxiliary4(KaitaiInputStream io) {
        this(io, null, null);
    }

    public ZoneAuxiliary4(KaitaiInputStream io, Zone parent) {
        this(io, parent, null);
    }

    public ZoneAuxiliary4(KaitaiInputStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        marker = io.readBytes(4);
        if (!Arrays.equals(marker, new byte[]{73, 90, 88, 52})) { // IZX4
            throw new KaitaiInputStream.ValidationNotEqualError(new byte[]{73, 90, 88, 52}, marker, getIo(), "/types/zone_auxiliary_4/seq/0");
        }
        size = io.readU4le();
        _unnamed2 = io.readU2le();
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeBytesFull(marker);
        os.writeU4le(size);
        os.writeU2le(_unnamed2);
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
