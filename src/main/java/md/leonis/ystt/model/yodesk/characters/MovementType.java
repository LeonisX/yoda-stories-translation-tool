package md.leonis.ystt.model.yodesk.characters;

import java.util.HashMap;
import java.util.Map;

public enum MovementType {

    NONE(0),         // PLAYER
    UNSPECIFIC1(1),  // ENEMY_HARD
    UNSPECIFIC2(2),  // ENEMY_MILD
    UNSPECIFIC3(3),  // ENEMY
    SIT(4),          // ENEMY_SIT
    UNUSED(5),       // UNUSED_INDY_ONLY
    UNSPECIFIC4(6),  // ENEMY_INACTIVE
    UNSPECIFIC5(7),  // ENEMY_ROBOT
    DROID(8),        // ENEMY_ROBOT_MILD
    WANDER(9),       // ENEMY_ROBOT_HARD
    PATROL(10),      // ENEMY_PATROL
    SCAREDY(11),     // ENEMY_SCARED
    ANIMATION(12);   // MAP_ITEM

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
