package md.leonis.ystt.model.zones;

import java.util.HashMap;
import java.util.Map;

public enum Planet {

    NONE(0),
    DESERT(1),
    SNOW(2),
    FOREST(3),
    SWAMP(5);

    private final long id;

    private static final Map<Long, Planet> byId = new HashMap<>(5);

    static {
        for (Planet e : Planet.values()) {
            byId.put(e.id(), e);
        }
    }

    public static Planet byId(long id) {
        return byId.get(id);
    }

    Planet(long id) {
        this.id = id;
    }

    public long id() {
        return id;
    }
}
