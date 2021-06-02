package md.leonis.ystt.model.yodasav.rooms;

import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodasav.Yodasav;
import md.leonis.ystt.model.yodesk.zones.Hotspot;
import md.leonis.ystt.model.yodesk.zones.HotspotType;

import java.util.ArrayList;
import java.util.List;

public class Rooms extends KaitaiStruct {

    private List<Room> rooms;
    private Rooms roomz;

    private List<Rec> zoneIds;

    private final transient short zoneId;
    private final transient int start;

    private final transient Yodasav root;
    private final transient KaitaiStruct parent;

    public Rooms(KaitaiInputStream io, KaitaiStruct parent, Yodasav root, short zoneId, int start) {
        super(io);
        this.zoneId = zoneId;
        this.start = start;
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        readRooms(io, zoneId, start);
    }

    private void readRooms(KaitaiInputStream io, short zoneId, int start) {

        zoneIds = new ArrayList<>();

        md.leonis.ystt.model.yodesk.zones.Zone zone = Yodasav.getYodesk().getZones().getZones().get(zoneId);

        List<Hotspot> hotspots = zone.getHotspots();
        int count = hotspots.size();

        for (int i = start; i < count; i++) {
            start = i;
            short door;
            Hotspot hotspot = hotspots.get(i);
            if (HotspotType.DOOR_IN.equals(hotspot.getType())) {
                if (hotspot.getArgument() == -1) {
                    continue;
                }

                door = (short) hotspot.getArgument();
            } else continue;

            Rec rec = new Rec(io);

            if (rec.zoneId != door) {
                System.out.printf("Expected door to lead to zone %s instead of %s%n", rec.zoneId, door);
            }
            zoneIds.add(rec);
            break;
        }

        rooms = new ArrayList<>();

        for (Rec rec : zoneIds) {
            rooms.add(new Room(io, this, root, rec.zoneId, rec.visited));
        }

        if (start + 1 < count) {
            roomz = new Rooms(io, this, root, zoneId, start + 1);
        }
    }

    static class Rec extends KaitaiStruct {

        private final short zoneId;
        private final boolean visited;

        public Rec(KaitaiInputStream io) {
            super(io);
            zoneId = io.readS2le();
            visited = io.readBool4le();
        }

        public void write(KaitaiOutputStream os) {
            os.writeS2le(zoneId);
            os.writeBool4le(visited);
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {

        zoneIds.forEach(z -> z.write(os));
        rooms.forEach(r -> r.write(os));

        if (roomz != null) {
            roomz.write(os);
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Rooms getRoomz() {
        return roomz;
    }

    public int getStart() {
        return start;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
