package md.leonis.ystt.model.yodesk.zones;

import java.util.HashMap;
import java.util.Map;

public enum HotspotType {

    DROP_QUEST_ITEM(0),
    SPAWN_LOCATION(1),
    DROP_UNIQUE_WEAPON(2),
    VEHICLE_TO(3),
    VEHICLE_BACK(4),
    DROP_LOCATOR(5),
    DROP_ITEM(6),
    NPC(7),
    DROP_WEAPON(8),
    DOOR_IN(9),
    DOOR_OUT(10),
    UNUSED(11),
    LOCK(12),
    TELEPORTER(13),
    SHIP_TO_PLANET(14),
    SHIP_FROM_PLANET(15);

    private final long id;

    private static final Map<Long, HotspotType> byId = new HashMap<>(16);

    static {
        for (HotspotType e : HotspotType.values())
            byId.put(e.id, e);
    }

    public static HotspotType byId(long id) {
        return byId.get(id);
    }

    HotspotType(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
