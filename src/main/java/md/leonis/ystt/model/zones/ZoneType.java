package md.leonis.ystt.model.zones;

import java.util.HashMap;
import java.util.Map;

public enum ZoneType {
    NONE(0),
    EMPTY(1),
    BLOCKADE_NORTH(2),
    BLOCKADE_SOUTH(3),
    BLOCKADE_EAST(4),
    BLOCKADE_WEST(5),
    TRAVEL_START(6),
    TRAVEL_END(7),
    ROOM(8),
    LOAD(9),
    GOAL(10),
    TOWN(11),
    WIN(13),
    LOSE(14),
    TRADE(15),
    USE(16),
    FIND(17),
    FIND_UNIQUE_WEAPON(18);

    private final long id;
    ZoneType(long id) { this.id = id; }
    public long id() { return id; }
    private static final Map<Long, ZoneType> byId = new HashMap<Long, ZoneType>(18);
    static {
        for (ZoneType e : ZoneType.values())
            byId.put(e.id(), e);
    }
    public static ZoneType byId(long id) { return byId.get(id); }
}
