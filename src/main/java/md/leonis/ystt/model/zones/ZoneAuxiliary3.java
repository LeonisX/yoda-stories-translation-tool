package md.leonis.ystt.model.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ZoneAuxiliary3 extends KaitaiStruct {

    private byte[] marker;
    private long size;
    // NPCs that can be placed in the zone to trade items with the hero.
    private int numNpcs;
    private ArrayList<Integer> npcs;
    private final Yodesk _root;
    private final Zone _parent;

    public static ZoneAuxiliary3 fromFile(String fileName) throws IOException {
        return new ZoneAuxiliary3(new ByteBufferKaitaiStream(fileName));
    }

    public ZoneAuxiliary3(KaitaiStream _io) {
        this(_io, null, null);
    }

    public ZoneAuxiliary3(KaitaiStream _io, Zone _parent) {
        this(_io, _parent, null);
    }

    public ZoneAuxiliary3(KaitaiStream _io, Zone _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.marker = this._io.readBytes(4);
        if (!(Arrays.equals(marker(), new byte[]{73, 90, 88, 51}))) { // IZX3
            throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 90, 88, 51}, marker(), _io(), "/types/zone_auxiliary_3/seq/0");
        }
        this.size = this._io.readU4le();
        this.numNpcs = this._io.readU2le();
        npcs = new ArrayList<>(numNpcs);
        for (int i = 0; i < numNpcs; i++) {
            this.npcs.add(this._io.readU2le());
        }
    }

    public byte[] marker() {
        return marker;
    }

    public long size() {
        return size;
    }

    public int numNpcs() {
        return numNpcs;
    }

    public ArrayList<Integer> npcs() {
        return npcs;
    }

    public Yodesk _root() {
        return _root;
    }

    public Zone _parent() {
        return _parent;
    }
}
