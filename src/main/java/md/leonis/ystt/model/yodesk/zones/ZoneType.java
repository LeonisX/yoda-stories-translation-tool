package md.leonis.ystt.model.yodesk.zones;

import java.util.HashMap;
import java.util.Map;

public enum ZoneType {

    NONE(0, 355),
    EMPTY(1, 832),
    BLOCKADE_NORTH(2, 821),
    BLOCKADE_SOUTH(3, 825),
    BLOCKADE_EAST(4, 823),
    BLOCKADE_WEST(5, 827),
    GATEWAY_START(6, 820),
    GATEWAY_END(7, 820),
    ROOM(8, 835),
    LOAD(9, 836),
    GOAL(10, 830),
    TOWN(11, 829),
    // UnknownIndyOnly
    WIN(13, 780),
    LOSE(14, 1951),
    TRADE(15, 818),
    USE(16, 818),
    FIND(17, 818),
    FIND_UNIQUE_WEAPON(18, 818);

    private final long id;
    private final int tileId;

    private static final Map<Long, ZoneType> byId = new HashMap<>(18);

    static {
        for (ZoneType e : ZoneType.values()) {
            byId.put(e.id, e);
        }
    }

    public static ZoneType byId(long id) {
        return byId.get(id);
    }

    ZoneType(long id, int tileId) {
        this.id = id;
        this.tileId = tileId;
    }

    public long getId() {
        return id;
    }

    public int getTileId() {
        return tileId;
    }
}
