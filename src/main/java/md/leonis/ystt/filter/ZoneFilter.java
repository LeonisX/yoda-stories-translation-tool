package md.leonis.ystt.filter;

import md.leonis.ystt.model.yodesk.zones.Monster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ZoneFilter {

    PLANET("Planet"),
    DIMENSIONS("Dimensions"),
    TYPE("Zone Type"),
    HOTSPOT("HotSpot"),
    NPC("NPC"),
    MONSTER("Monster"),
    MONSTER_LOOT("Monster Loot"),
    MONSTER_DROP_LOOT("Monster Drops Loot"),
    MONSTER_MOVEMENT_TYPE("Monster Movement Type"),
    MONSTER_WAYPOINT("Monster Waypoints"),
    PROVIDED_ITEM("Provided Item"),
    REQUIRED_ITEM("Required Item"),
    GOAL_ITEM("Goal Item"),
    IZAX_UNNAMED2("IZAX_UNNAMED2"),
    IZX4_UNNAMED2("IZX4_UNNAMED2");

    //TODO zax, ...

    private final String title;

    ZoneFilter(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private static final Map<String, ZoneFilter> byTitle = new HashMap<>();

    static {
        for (ZoneFilter zoneFilter : ZoneFilter.values()) {
            byTitle.put(zoneFilter.getTitle(), zoneFilter);
        }
    }

    public static ZoneFilter byTitle(String title) {
        return byTitle.get(title);
    }
}
