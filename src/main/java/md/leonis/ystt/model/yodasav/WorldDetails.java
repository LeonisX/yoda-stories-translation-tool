package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodasav.rooms.Room;

public class WorldDetails extends KaitaiStruct {

    private int x;
    private int y;
    private short zoneId;
    private boolean visited;
    private Room room;

    private final transient Yodasav root;
    private final transient KaitaiStruct parent;

    public WorldDetails(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        x = io.readS4le();
        y = io.readS4le();

        if (x != -1 && y != -1) {
            zoneId = io.readS2le();
			visited = io.readBool4le();

            room = new Room(io, this, root, zoneId, visited);
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {

        os.writeS4le(x);
        os.writeS4le(y);

        if (x != -1 && y != -1) {
            os.writeS2le(zoneId);
            os.writeBool4le(visited);
            room.write(os);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public short getZoneId() {
        return zoneId;
    }

    public boolean getVisited() {
        return visited;
    }

    public Room getRoom() {
        return room;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZoneId(short zoneId) {
        this.zoneId = zoneId;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
