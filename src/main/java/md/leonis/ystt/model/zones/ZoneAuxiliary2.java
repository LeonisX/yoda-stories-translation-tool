package md.leonis.ystt.model.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ZoneAuxiliary2 extends KaitaiStruct {

    private byte[] marker;
    private long size;
    private int numProvidedItems;
    // Items that can be gained when the zone is solved.
    private ArrayList<Integer> providedItems;

    private final Yodesk _root;
    private final Zone _parent;

    public static ZoneAuxiliary2 fromFile(String fileName) throws IOException {
        return new ZoneAuxiliary2(new ByteBufferKaitaiStream(fileName));
    }

    public ZoneAuxiliary2(KaitaiStream _io) {
        this(_io, null, null);
    }

    public ZoneAuxiliary2(KaitaiStream _io, Zone _parent) {
        this(_io, _parent, null);
    }

    public ZoneAuxiliary2(KaitaiStream _io, Zone _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.marker = this._io.readBytes(4);
        if (!(Arrays.equals(marker(), new byte[]{73, 90, 88, 50}))) { // IZX2
            throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 90, 88, 50}, marker(), _io(), "/types/zone_auxiliary_2/seq/0");
        }
        this.size = this._io.readU4le();
        this.numProvidedItems = this._io.readU2le();
        providedItems = new ArrayList<>(numProvidedItems);
        for (int i = 0; i < numProvidedItems; i++) {
            this.providedItems.add(this._io.readU2le());
        }
    }

    public byte[] marker() {
        return marker;
    }

    public long size() {
        return size;
    }

    public int numProvidedItems() {
        return numProvidedItems;
    }

    public ArrayList<Integer> providedItems() {
        return providedItems;
    }

    public Yodesk _root() {
        return _root;
    }

    public Zone _parent() {
        return _parent;
    }
}
