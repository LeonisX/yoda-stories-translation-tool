package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.zones.Zone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Zones extends KaitaiStruct {

    private int numZones;
    private List<Zone> zones;

    private final Yodesk root;
    private final CatalogEntry parent;

    public static Zones fromFile(String fileName) throws IOException {
        return new Zones(new ByteBufferKaitaiInputStream(fileName));
    }

    public Zones(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Zones(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Zones(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        numZones = io.readU2le();
        zones = new ArrayList<>(numZones);
        for (int i = 0; i < numZones; i++) {
            zones.add(new Zone(io, this, root));
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU2le(numZones);
        for (int i = 0; i < numZones; i++) {
            zones.get(i).write(os);
        }
    }

    public int getNumZones() {
        return numZones;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
