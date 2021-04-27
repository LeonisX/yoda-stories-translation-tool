package md.leonis.ystt.model.characters;

import java.util.HashMap;
import java.util.Map;

public enum MovementType {

    NONE(0),
    SIT(4),
    WANDER(9),
    PATROL(10),
    ANIMATION(12);

    private final long id;

    private static final Map<Long, MovementType> byId = new HashMap<>(5);

    static {
        for (MovementType e : MovementType.values()) {
            byId.put(e.id(), e);
        }
    }

    public static MovementType byId(long id) {
        return byId.get(id);
    }

    MovementType(long id) {
        this.id = id;
    }

    public long id() {
        return id;
    }
}
