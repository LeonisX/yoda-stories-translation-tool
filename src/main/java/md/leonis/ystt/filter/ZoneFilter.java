package md.leonis.ystt.filter;

import java.util.HashMap;
import java.util.Map;

public enum ZoneFilter {

    PLANET("Planet"),
    DIMENSIONS("Dimensions"),
    TYPE("Zone Type"),
    HOTSPOT("HotSpot");

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
