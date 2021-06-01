package md.leonis.ystt.model.yodasav.rooms;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodasav.Yodasav;
import md.leonis.ystt.model.yodasav.rooms.zones.SaveZone;

import java.io.IOException;

public class Room extends KaitaiStruct {

    private SaveZone zone;
    private Rooms rooms;

    private short zoneId;
    private boolean visited;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static Room fromFile(String fileName) throws IOException {
        return new Room(new ByteBufferKaitaiInputStream(fileName));
    }

    public Room(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Room(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public Room(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    public Room(KaitaiInputStream io, KaitaiStruct parent, Yodasav root, short zoneId, boolean visited) {
        super(io);
        this.parent = parent;
        this.root = root;
        this.zoneId = zoneId;
        this.visited = visited;
        _read();
    }

    private void _read() {

        zone = new SaveZone(io, this, root, zoneId, visited);
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
