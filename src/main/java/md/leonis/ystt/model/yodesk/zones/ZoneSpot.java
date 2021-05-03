package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZoneSpot extends KaitaiStruct {

    /**
     * from bottom to top, 0xFFFF indicates empty tiles
     */
    private List<Integer> column;

    private final Yodesk root;
    private final Zone parent;

    public static ZoneSpot fromFile(String fileName) throws IOException {
        return new ZoneSpot(new ByteBufferKaitaiInputStream(fileName));
    }

    public ZoneSpot(KaitaiInputStream io) {
        this(io, null, null);
    }

    public ZoneSpot(KaitaiInputStream io, Zone parent) {
        this(io, parent, null);
    }

    public ZoneSpot(KaitaiInputStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        column = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            this.column.add(this.io.readU2le());
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        for (int i = 0; i < 3; i++) {
            os.writeU2le(column.get(i));
        }
    }

    public List<Integer> getColumn() {
        return column;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Zone getParent() {
        return parent;
    }
}
