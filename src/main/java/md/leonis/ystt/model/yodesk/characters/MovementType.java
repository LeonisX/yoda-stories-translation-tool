package md.leonis.ystt.model.yodesk.characters;

import java.util.HashMap;
import java.util.Map;

public enum MovementType {

    NONE(0),        // PLAYER
    UNKNOWN1(1),    // ENEMY_HARD
    UNKNOWN2(2),    // ENEMY_MILD
    UNKNOWN3(3),    // ENEMY
    SIT(4),         // ENEMY_SIT
    UNKNOWN5(5),    // unused
    UNKNOWN6(6),    // ENEMY_INACTIVE
    UNKNOWN7(7),    // ENEMY_ROBOT
    UNKNOWN8(8),    // ENEMY_ROBOT_MILD
    WANDER(9),      // ENEMY_ROBOT_HARD
    PATROL(10),     // ENEMY_PATROL
    UNKNOWN11(11),  // ENEMY_SCARED
    ANIMATION(12);  // MAP_ITEM

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
