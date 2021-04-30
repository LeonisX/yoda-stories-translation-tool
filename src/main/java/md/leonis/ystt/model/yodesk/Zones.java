package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.zones.Zone;

import java.io.IOException;
import java.util.ArrayList;

public class Zones extends KaitaiStruct {

    private int numZones;
    private ArrayList<Zone> zones;

    private final Yodesk root;
    private final CatalogEntry parent;

    public static Zones fromFile(String fileName) throws IOException {
        return new Zones(new ByteBufferKaitaiStream(fileName));
    }

    public Zones(KaitaiStream io) {
        this(io, null, null);
    }

    public Zones(KaitaiStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Zones(KaitaiStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.numZones = this.io.readU2le();
        zones = new ArrayList<>(numZones);
        for (int i = 0; i < numZones; i++) {
            this.zones.add(new Zone(this.io, this, root));
        }
    }

    public int getNumZones() {
        return numZones;
    }

    public ArrayList<Zone> getZones() {
        return zones;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
