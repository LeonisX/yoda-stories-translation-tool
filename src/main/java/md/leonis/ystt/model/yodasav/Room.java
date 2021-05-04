package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class Room extends KaitaiStruct {

    private int unknown1;
    private int unknown2;
    private Short zoneId;
    private Long unknown3;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static Room fromFile(String fileName) throws IOException {
        return new Room(new ByteBufferKaitaiInputStream(fileName));
    }

    public Room(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Room(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public Room(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        unknown1 = io.readS4le();
        unknown2 = io.readS4le();
        if (unknown1 == -1) {
            zoneId = io.readS2le();
            unknown3 = io.readU4le();
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public int getUnknown1() {
        return unknown1;
    }

    public int getUnknown2() {
        return unknown2;
    }

    public Short getZoneId() {
        return zoneId;
    }

    public Long getUnknown3() {
        return unknown3;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
