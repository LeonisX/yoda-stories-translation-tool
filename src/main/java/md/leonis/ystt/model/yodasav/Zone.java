package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Zone extends KaitaiStruct {

    private int counter;
    private int random;

    private int x;
    private int y;

    private short sectorCounter;
    private short planet;

    private List<Short> tileIds;

    private List<Hotspot> hotspots;
    private List<Monster> monsters;
    private List<Action> actions;

    private short zoneId;
    private boolean visited;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static Zone fromFile(String fileName) throws IOException {
        return new Zone(new ByteBufferKaitaiInputStream(fileName));
    }

    public Zone(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Zone(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public Zone(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    public Zone(KaitaiInputStream io, KaitaiStruct parent, Yodasav root, short zoneId, boolean visited) {
        super(io);
        this.zoneId = zoneId;
        this.visited = visited;
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        md.leonis.ystt.model.yodesk.zones.Zone zone = Yodasav.getYodesk().getZones().getZones().get(zoneId);

        if (visited) {
            counter = io.readS4le();
            random = io.readS4le();

            x = io.readS4le();
            y = io.readS4le();

            sectorCounter = io.readS2le();
            planet = io.readS2le();

            if (zoneId > 658) {
                System.out.println("sssss");
            }

			int tileCount = zone.getWidth() * zone.getHeight() * 3;
            tileIds = new ArrayList<>();
			for (int i = 0; i < tileCount; i++) {
                tileIds.add(io.readS2le());
            }
        }

        visited = io.readBool4le() || visited;
        int hotSpotsCount = io.readS4le();
        hotspots = new ArrayList<>();
        for (int i = 0; i < hotSpotsCount; i++) {
            hotspots.add(new Hotspot(io, this, root));
        }

        if (visited) {
            int monstersCount = io.readS4le();
            monsters = new ArrayList<>();
            for (int i = 0; i < monstersCount; i++) {
                monsters.add(new Monster(io, this, root));
            }
            int actionsCount = io.readS4le();
            assert actionsCount > 0;
            actions = new ArrayList<>();
            for (int i = 0; i < actionsCount; i++) {
                actions.add(new Action(io, this, root));
            }
            for (int i = 0; i < zone.getActions().size(); i++) {
                int n = io.readS4le(); //TODO
                System.out.println(n);
            }
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public int getCounter() {
        return counter;
    }

    public int getRandom() {
        return random;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public short getSectorCounter() {
        return sectorCounter;
    }

    public short getPlanet() {
        return planet;
    }

    public List<Short> getTileIds() {
        return tileIds;
    }

    public List<Hotspot> getHotspots() {
        return hotspots;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Action> getActions() {
        return actions;
    }

    public short getZoneId() {
        return zoneId;
    }

    public boolean isVisited() {
        return visited;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }


}
