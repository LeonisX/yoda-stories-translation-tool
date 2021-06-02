package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.util.ArrayList;
import java.util.List;

public class World extends KaitaiStruct {

    private List<Sector> sectors;
    private List<WorldDetails> worldDetailsList;

    private final transient Yodasav root;
    private final transient Yodasav parent;

    public World(KaitaiInputStream io, Yodasav parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        sectors = new ArrayList<>(10 * 10);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                sectors.add(new Sector(io, this, root, x, y));
                // world.replaceSector(x, y, item);
            }
        }

        worldDetailsList = new ArrayList<>();
        do {
            WorldDetails worldDetails = new WorldDetails(io, this, root);
            worldDetailsList.add(worldDetails);

            if (null == worldDetails.getRoom()) {
                break;
            }
        } while (true);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        sectors.forEach(s -> s.write(os));
        worldDetailsList.forEach(w -> w.write(os));
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
