package md.leonis.ystt.model.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

public class ZoneSpot extends KaitaiStruct {

    /**
     * from bottom to top, 0xFFFF indicates empty tiles
     */
    private ArrayList<Integer> column;

    private final Yodesk _root;
    private final Zone _parent;

    public static ZoneSpot fromFile(String fileName) throws IOException {
        return new ZoneSpot(new ByteBufferKaitaiStream(fileName));
    }

    public ZoneSpot(KaitaiStream _io) {
        this(_io, null, null);
    }

    public ZoneSpot(KaitaiStream _io, Zone _parent) {
        this(_io, _parent, null);
    }

    public ZoneSpot(KaitaiStream _io, Zone _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        column = new ArrayList<Integer>(((Number) (3)).intValue());
        for (int i = 0; i < 3; i++) {
            this.column.add(this._io.readU2le());
        }
    }

    public ArrayList<Integer> column() {
        return column;
    }

    public Yodesk _root() {
        return _root;
    }

    public Zone _parent() {
        return _parent;
    }
}
