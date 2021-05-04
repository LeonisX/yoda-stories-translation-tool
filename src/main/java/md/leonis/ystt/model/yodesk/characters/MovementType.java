package md.leonis.ystt.model.yodesk.characters;

import java.util.HashMap;
import java.util.Map;

public enum MovementType {

    NONE(0),
    UNKNOWN1(1),
    UNKNOWN2(2),
    UNKNOWN3(3),
    SIT(4),
    UNKNOWN5(5),
    UNKNOWN6(6),
    UNKNOWN7(7),
    UNKNOWN8(8),
    WANDER(9),
    PATROL(10),
    UNKNOWN11(11),
    ANIMATION(12);

    private final long id;

    private static final Map<Long, MovementType> byId = new HashMap<>(5);

    static {
        for (MovementType e : MovementType.values()) {
            byId.put(e.id, e);
        }
    }

    public static MovementType byId(long id) {
        return byId.get(id);
    }

    MovementType(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
