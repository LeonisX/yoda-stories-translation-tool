package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ZoneAuxiliary3 extends KaitaiStruct {

    private byte[] marker;
    private long size;
    // NPCs that can be placed in the zone to trade items with the hero.
    private int numNpcs;
    private ArrayList<Integer> npcs;

    private final Yodesk root;
    private final Zone parent;

    public static ZoneAuxiliary3 fromFile(String fileName) throws IOException {
        return new ZoneAuxiliary3(new ByteBufferKaitaiStream(fileName));
    }

    public ZoneAuxiliary3(KaitaiStream io) {
        this(io, null, null);
    }

    public ZoneAuxiliary3(KaitaiStream io, Zone parent) {
        this(io, parent, null);
    }

    public ZoneAuxiliary3(KaitaiStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.marker = this.io.readBytes(4);
        if (!(Arrays.equals(marker, new byte[]{73, 90, 88, 51}))) { // IZX3
            throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 90, 88, 51}, marker, getIo(), "/types/zone_auxiliary_3/seq/0");
        }
        this.size = this.io.readU4le();
        this.numNpcs = this.io.readU2le();
        npcs = new ArrayList<>(numNpcs);
        for (int i = 0; i < numNpcs; i++) {
            this.npcs.add(this.io.readU2le());
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

    public ArrayList<Integer> getNpcs() {
        return npcs;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Zone getParent() {
        return parent;
    }
}
