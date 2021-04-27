package md.leonis.ystt.model.zones;

import java.util.HashMap;
import java.util.Map;

public enum ConditionOpcode {

    ZONE_NOT_INITIALISED(0),
    ZONE_ENTERED(1),
    BUMP(2),
    PLACED_ITEM_IS(3),
    STANDING_ON(4),
    COUNTER_IS(5),
    RANDOM_IS(6),
    RANDOM_IS_GREATER_THAN(7),
    RANDOM_IS_LESS_THAN(8),
    ENTER_BY_PLANE(9),
    TILE_AT_IS(10),
    MONSTER_IS_DEAD(11),
    HAS_NO_ACTIVE_MONSTERS(12),
    HAS_ITEM(13),
    REQUIRED_ITEM_IS(14),
    ENDING_IS(15),
    ZONE_IS_SOLVED(16),
    NO_ITEM_PLACED(17),
    ITEM_PLACED(18),
    HEALTH_IS_LESS_THAN(19),
    HEALTH_IS_GREATER_THAN(20),
    UNUSED(21),
    FIND_ITEM_IS(22),
    PLACED_ITEM_IS_NOT(23),
    HERO_IS_AT(24),
    SHARED_COUNTER_IS(25),
    SHARED_COUNTER_IS_LESS_THAN(26),
    SHARED_COUNTER_IS_GREATER_THAN(27),
    GAMES_WON_IS(28),
    DROPS_QUEST_ITEM_AT(29),
    HAS_ANY_REQUIRED_ITEM(30),
    COUNTER_IS_NOT(31),
    RANDOM_IS_NOT(32),
    SHARED_COUNTER_IS_NOT(33),
    IS_VARIABLE(34),
    GAMES_WON_IS_GREATER_THAN(35);

    private final long id;

    private static final Map<Long, ConditionOpcode> byId = new HashMap<>(36);

    static {
        for (ConditionOpcode e : ConditionOpcode.values()) {
            byId.put(e.id(), e);
        }
    }

    public static ConditionOpcode byId(long id) {
        return byId.get(id);
    }

    ConditionOpcode(long id) {
        this.id = id;
    }

    public long id() {
        return id;
    }

}
