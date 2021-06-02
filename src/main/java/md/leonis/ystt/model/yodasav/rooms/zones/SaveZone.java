package md.leonis.ystt.model.yodasav.rooms.zones;

import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodasav.Yodasav;

import java.util.ArrayList;
import java.util.List;

public class SaveZone extends KaitaiStruct {

    private int counter;
    private int random;

    private int x;
    private int y;

    private short sectorCounter;
    private short planet;

    private boolean visited;

    private List<Short> tileIds;

    private List<SaveHotspot> hotspots;
    private List<SaveMonster> monsters;
    private List<SaveAction> actions;

    private List<Integer> extraActions;

    private final transient md.leonis.ystt.model.yodesk.zones.Zone zone;
    private final transient boolean visitedArg;

    private final transient Yodasav root;
    private final transient KaitaiStruct parent;

    public SaveZone(KaitaiInputStream io, KaitaiStruct parent, Yodasav root, md.leonis.ystt.model.yodesk.zones.Zone zone, boolean visited) {
        super(io);
        this.zone = zone;
        this.visitedArg = visited;
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        if (visitedArg) {
            counter = io.readS4le();
            random = io.readS4le();

            x = io.readS4le();
            y = io.readS4le();

            sectorCounter = io.readS2le();
            planet = io.readS2le();
            // console.assert(planet === zone.planet.rawValue);

			int tileCount = zone.getWidth() * zone.getHeight() * 3;
            tileIds = new ArrayList<>();
			for (int i = 0; i < tileCount; i++) {
                tileIds.add(io.readS2le());
            }
        }

        visited = io.readBool4le();
        readHotspots();

        if (visited || visitedArg) {
            readMonsters();
            readActions();
        }
    }

    private void readHotspots() {

        int hotSpotsCount = io.readS4le();
        assert hotSpotsCount >= 0;

        /*if (count !== zone.hotspots.length) {
            zone.hotspots = Array.Repeat(new Hotspot(), count);
        }*/
        hotspots = new ArrayList<>();
        for (int i = 0; i < hotSpotsCount; i++) {
            hotspots.add(new SaveHotspot(io, this, root));
        }
    }

    private void readMonsters() {
        int monstersCount = io.readS4le();
        assert monstersCount >= 0;
        monsters = new ArrayList<>();
        for (int i = 0; i < monstersCount; i++) {
            SaveMonster monster = new SaveMonster(io, this, root);
            monster.setId(i);
            monsters.add(monster);
        }
    }

    private void readActions() {
        int actionsCount = io.readS4le();
        assert actionsCount > 0;
        actions = new ArrayList<>();
        for (int i = 0; i < actionsCount; i++) {
            actions.add(new SaveAction(io, this, root));
        }
        extraActions = new ArrayList<>();
        for (int i = zone.getActions().size(); i < actionsCount; i++) {
            if (i == zone.getActions().size()) {
                System.out.printf("Zone #%s has additional actions%n", zone.getIndex());
            }
            int extraAction = io.readS4le();
            extraActions.add(extraAction);
            System.out.println("extraAction: " + extraAction);
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {

        if (visitedArg) {
            os.writeS4le(counter);
            os.writeS4le(random);

            os.writeS4le(x);
            os.writeS4le(y);

            os.writeS2le(sectorCounter);
            os.writeS2le(planet);

            tileIds.forEach(os::writeS2le);
        }

        os.writeBool4le(visited);
        writeHotspots(os);

        if (visited || visitedArg) {
            writeMonsters(os);
            writeActions(os);
        }
    }

    private void writeHotspots(KaitaiOutputStream os) {
        os.writeS4le(hotspots.size());
        hotspots.forEach(h -> h.write(os));
    }

    private void writeMonsters(KaitaiOutputStream os) {
        os.writeS4le(monsters.size());
        monsters.forEach(m -> m.write(os));
    }

    private void writeActions(KaitaiOutputStream os) {
        os.writeS4le(actions.size());
        actions.forEach(a -> a.write(os));
        extraActions.forEach(os::writeS4le);
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

    public List<SaveHotspot> getHotspots() {
        return hotspots;
    }

    public List<SaveMonster> getMonsters() {
        return monsters;
    }

    public List<SaveAction> getActions() {
        return actions;
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
