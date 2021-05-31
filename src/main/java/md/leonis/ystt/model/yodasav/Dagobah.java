package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dagobah extends KaitaiStruct {

    private List<Sector> sectors;
    private List<WorldDetails> worldDetailsList;

    private final Yodasav root;
    private final Yodasav parent;

    public static Dagobah fromFile(String fileName) throws IOException {
        return new Dagobah(new ByteBufferKaitaiInputStream(fileName));
    }

    public Dagobah(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Dagobah(KaitaiInputStream io, Yodasav parent) {
        this(io, parent, null);
    }

    public Dagobah(KaitaiInputStream io, Yodasav parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        sectors = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            sectors.add(new Sector(io, this, root));
        }

        worldDetailsList = new ArrayList<>();
        do {
            WorldDetails worldDetails = new WorldDetails(io, this, root);
            worldDetailsList.add(worldDetails);

            if (worldDetails.getX() == -1 || worldDetails.getY() == -1) {
                break;
            }
        } while (true);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public List<WorldDetails> getWorldDetailsList() {
        return worldDetailsList;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public Yodasav getParent() {
        return parent;
    }
}
