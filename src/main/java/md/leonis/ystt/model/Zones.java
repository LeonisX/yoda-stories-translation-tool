package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.zones.Zone;

import java.io.IOException;
import java.util.ArrayList;

public class Zones extends KaitaiStruct {

    private int numZones;
    private ArrayList<Zone> zones;

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static Zones fromFile(String fileName) throws IOException {
        return new Zones(new ByteBufferKaitaiStream(fileName));
    }

    public Zones(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Zones(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public Zones(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.numZones = this._io.readU2le();
        zones = new ArrayList<>(numZones);
        for (int i = 0; i < numZones; i++) {
            this.zones.add(new Zone(this._io, this, _root));
        }
    }

    public int getNumZones() {
        return numZones;
    }

    public ArrayList<Zone> getZones() {
        return zones;
    }

    public Yodesk get_root() {
        return _root;
    }

    public CatalogEntry get_parent() {
        return _parent;
    }
}
