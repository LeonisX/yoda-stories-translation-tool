package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZoneAuxiliary2 extends KaitaiStruct {

    private byte[] marker;
    private long size;
    private int numProvidedItems;
    // Items that can be gained when the zone is solved.
    private List<Integer> providedItems;

    private final Yodesk root;
    private final Zone parent;

    public static ZoneAuxiliary2 fromFile(String fileName) throws IOException {
        return new ZoneAuxiliary2(new ByteBufferKaitaiStream(fileName));
    }

    public ZoneAuxiliary2(KaitaiStream io) {
        this(io, null, null);
    }

    public ZoneAuxiliary2(KaitaiStream io, Zone parent) {
        this(io, parent, null);
    }

    public ZoneAuxiliary2(KaitaiStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.marker = this.io.readBytes(4);
        if (!(Arrays.equals(marker, new byte[]{73, 90, 88, 50}))) { // IZX2
            throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 90, 88, 50}, marker, getIo(), "/types/zone_auxiliary_2/seq/0");
        }
        this.size = this.io.readU4le();
        this.numProvidedItems = this.io.readU2le();
        providedItems = new ArrayList<>(numProvidedItems);
        for (int i = 0; i < numProvidedItems; i++) {
            this.providedItems.add(this.io.readU2le());
        }
    }

    public byte[] getMarker() {
        return marker;
    }

    public long getSize() {
        return size;
    }

    public int getNumProvidedItems() {
        return numProvidedItems;
    }

    public List<Integer> getProvidedItems() {
        return providedItems;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Zone getParent() {
        return parent;
    }
}
