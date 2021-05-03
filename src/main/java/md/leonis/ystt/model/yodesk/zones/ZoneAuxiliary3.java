package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZoneAuxiliary3 extends KaitaiStruct {

    private byte[] marker;
    private long size;
    // NPCs that can be placed in the zone to trade items with the hero.
    private int numNpcs;
    private List<Integer> npcs;

    private final Yodesk root;
    private final Zone parent;

    public static ZoneAuxiliary3 fromFile(String fileName) throws IOException {
        return new ZoneAuxiliary3(new ByteBufferKaitaiInputStream(fileName));
    }

    public ZoneAuxiliary3(KaitaiInputStream io) {
        this(io, null, null);
    }

    public ZoneAuxiliary3(KaitaiInputStream io, Zone parent) {
        this(io, parent, null);
    }

    public ZoneAuxiliary3(KaitaiInputStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        marker = io.readBytes(4);
        if (!(Arrays.equals(marker, new byte[]{73, 90, 88, 51}))) { // IZX3
            throw new KaitaiInputStream.ValidationNotEqualError(new byte[]{73, 90, 88, 51}, marker, getIo(), "/types/zone_auxiliary_3/seq/0");
        }
        size = io.readU4le();
        numNpcs = io.readU2le();
        npcs = new ArrayList<>(numNpcs);
        for (int i = 0; i < numNpcs; i++) {
            npcs.add(io.readU2le());
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeBytesFull(marker);
        os.writeU4le(size);
        os.writeU2le(numNpcs);

        for (Integer npc : npcs) {
            os.writeU2le(npc);
        }
    }

    public byte[] getMarker() {
        return marker;
    }

    public long getSize() {
        return size;
    }

    public int getNumNpcs() {
        return numNpcs;
    }

    public List<Integer> getNpcs() {
        return npcs;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Zone getParent() {
        return parent;
    }
}
