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

public class ZoneAuxiliary2 extends KaitaiStruct {

    private byte[] marker;
    private long size;
    private int numProvidedItems;
    // Items that can be gained when the zone is solved.
    private List<Integer> providedItems;

    private final transient Yodesk root;
    private final transient Zone parent;

    public static ZoneAuxiliary2 fromFile(String fileName) throws IOException {
        return new ZoneAuxiliary2(new ByteBufferKaitaiInputStream(fileName));
    }

    public ZoneAuxiliary2(KaitaiInputStream io) {
        this(io, null, null);
    }

    public ZoneAuxiliary2(KaitaiInputStream io, Zone parent) {
        this(io, parent, null);
    }

    public ZoneAuxiliary2(KaitaiInputStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        marker = io.readBytes(4);
        if (!(Arrays.equals(marker, new byte[]{73, 90, 88, 50}))) { // IZX2
            throw new KaitaiInputStream.ValidationNotEqualError(new byte[]{73, 90, 88, 50}, marker, getIo(), "/types/zone_auxiliary_2/seq/0");
        }
        size = io.readU4le();
        numProvidedItems = io.readU2le();
        providedItems = new ArrayList<>(numProvidedItems);
        for (int i = 0; i < numProvidedItems; i++) {
            providedItems.add(io.readU2le());
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeBytesFull(marker);
        os.writeU4le(size);
        os.writeU2le(numProvidedItems);

        for (Integer providedItem : providedItems) {
            os.writeU2le(providedItem);
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
