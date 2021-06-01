package md.leonis.ystt.model.yodasav.rooms;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodasav.Yodasav;
import md.leonis.ystt.model.yodesk.zones.HotspotType;

import java.io.IOException;
import java.util.List;

public class Rooms extends KaitaiStruct {

    private short zoneId;
    private boolean visited;
    private Room room;
    private Rooms rooms;

    private int start;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static Rooms fromFile(String fileName) throws IOException {
        return new Rooms(new ByteBufferKaitaiInputStream(fileName));
    }

    public Rooms(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Rooms(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public Rooms(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    public Rooms(KaitaiInputStream io, KaitaiStruct parent, Yodasav root, short zoneId, int start) {
        super(io);
        this.zoneId = zoneId;
        this.start = start;
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        md.leonis.ystt.model.yodesk.zones.Zone zone = Yodasav.getYodesk().getZones().getZones().get(zoneId);
		List<md.leonis.ystt.model.yodesk.zones.Hotspot> hotspots = zone.getHotspots();
		int hotspotsCount = hotspots.size(); // TODO

        for (int i = start; i < hotspotsCount; i++) {
            start = i;
            int door;
            md.leonis.ystt.model.yodesk.zones.Hotspot hotspot = hotspots.get(i);
            if (hotspot.getType().equals(HotspotType.DOOR_IN)) {
                if (hotspot.getArgument() == -1) {
                    continue;
                }

                door = hotspot.getArgument();
            } else continue;

			zoneId = io.readS2le();
			visited = io.readBool4le();

            /*assert(
                    !zoneId || zoneID === door,
            "Expected door to lead to zone {} instead of {}",
                    zoneID,
                    door
			);*/

            room = new Room(io, this, root, zoneId, visited);
            break;
        }

        if (start + 1 < hotspotsCount) {
            rooms = new Rooms(io, this, root, zoneId, start + 1);
        }

    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public short getZoneId() {
        return zoneId;
    }

    public Room getRoom() {
        return room;
    }

    public Rooms getRooms() {
        return rooms;
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
