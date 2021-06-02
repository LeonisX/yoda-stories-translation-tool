package md.leonis.ystt.model.yodasav.rooms;

import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodasav.Yodasav;
import md.leonis.ystt.model.yodasav.rooms.zones.SaveZone;

public class Room extends KaitaiStruct {

    private SaveZone zone;
    private Rooms rooms;

    private final transient short zoneId;
    private final transient boolean visited;

    private final transient Yodasav root;
    private final transient KaitaiStruct parent;

    public Room(KaitaiInputStream io, KaitaiStruct parent, Yodasav root, short zoneId, boolean visited) {
        super(io);
        this.parent = parent;
        this.root = root;
        this.zoneId = zoneId;
        this.visited = visited;
        _read();
    }

    private void _read() {
        readRoom(io, zoneId, visited);
    }

    private void readRoom(KaitaiInputStream io, short zoneId, boolean visited) {

        md.leonis.ystt.model.yodesk.zones.Zone gameZone = Yodasav.getYodesk().getZones().getZones().get(zoneId);

        zone = new SaveZone(io, this, root, gameZone, visited);
        rooms = new Rooms(io, this, root, zoneId, 0);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public SaveZone getZone() {
        return zone;
    }

    public Rooms getRooms() {
        return rooms;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
