package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class WorldThing extends KaitaiStruct {

    private long unknown1;
    private long unknown2;
    private long unknown3;
    private long unknown4;
    private long unknown5;
    private int zoneId;
    private int unknown6;
    private int requiredItem;
    private int providedItem;
    private int unknown7;
    private int additionallyRequiredItem;
    private int unknown8;
    private int puzzleNpc;
    private long unknown9;
    private int unknown10;
    private Room rooms;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static WorldThing fromFile(String fileName) throws IOException {
        return new WorldThing(new ByteBufferKaitaiInputStream(fileName));
    }

    public WorldThing(KaitaiInputStream io) {
        this(io, null, null);
    }

    public WorldThing(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public WorldThing(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        unknown1 = io.readU4le();
        unknown2 = io.readU4le();
        unknown3 = io.readU4le();
        unknown4 = io.readU4le();
        unknown5 = io.readU4le();
        zoneId = io.readU2le();
        unknown6 = io.readU2le();
        requiredItem = io.readU2le();
        providedItem = io.readU2le();
        unknown7 = io.readU2le();
        additionallyRequiredItem = io.readU2le();
        unknown8 = io.readU2le();
        puzzleNpc = io.readU2le();
        unknown9 = io.readU4le();
        unknown10 = io.readU2le();
        rooms = new Room(io, this, root);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public long getUnknown1() {
        return unknown1;
    }

    public long getUnknown2() {
        return unknown2;
    }

    public long getUnknown3() {
        return unknown3;
    }

    public long getUnknown4() {
        return unknown4;
    }

    public long getUnknown5() {
        return unknown5;
    }

    public int getZoneId() {
        return zoneId;
    }

    public int getUnknown6() {
        return unknown6;
    }

    public int getRequiredItem() {
        return requiredItem;
    }

    public int getProvidedItem() {
        return providedItem;
    }

    public int getUnknown7() {
        return unknown7;
    }

    public int getAdditionallyRequiredItem() {
        return additionallyRequiredItem;
    }

    public int getUnknown8() {
        return unknown8;
    }

    public int getPuzzleNpc() {
        return puzzleNpc;
    }

    public long getUnknown9() {
        return unknown9;
    }

    public int getUnknown10() {
        return unknown10;
    }

    public Room getRooms() {
        return rooms;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
