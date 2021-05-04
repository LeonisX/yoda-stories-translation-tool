package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World extends KaitaiStruct {

    private List<WorldThing> worldThings;
    private List<Zone> zones;

    private final Yodasav root;
    private final Yodasav parent;

    public static World fromFile(String fileName) throws IOException {
        return new World(new ByteBufferKaitaiInputStream(fileName));
    }

    public World(KaitaiInputStream io) {
        this(io, null, null);
    }

    public World(KaitaiInputStream io, Yodasav parent) {
        this(io, parent, null);
    }

    public World(KaitaiInputStream io, Yodasav parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        worldThings = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            worldThings.add(new WorldThing(io, this, root));
        }
        zones = new ArrayList<>();
        Zone _it;
        do {
            _it = new Zone(io, this, root);
            zones.add(_it);
        } while (_it.getUnknown1() != -1);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public List<WorldThing> getWorldThings() {
        return worldThings;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public Yodasav getParent() {
        return parent;
    }
}
