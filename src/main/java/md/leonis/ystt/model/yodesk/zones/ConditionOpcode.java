package md.leonis.ystt.model.yodesk.zones;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// https://github.com/cyco/WebFun/tree/master/src/engine/script/conditions
// https://www.webfun.io/docs/scripting/conditions.html
// https://www.webfun.io/docs/appendix/yodesk.html
public enum ConditionOpcode {

    ZONE_NOT_INITIALISED(0, 0, "Evaluates to true exactly once (used for initialization)"),
    ZONE_ENTERED(1, 0, "Evaluates to true if hero just entered the zone"),
    BUMP(2, 3, "x,y,itemId"),
    PLACED_ITEM_IS(3, 5, "x, y, z, target, item"), // https://github.com/cyco/WebFun/blob/master/src/engine/script/conditions/placed-item-is.ts
    STANDING_ON(4, 3, "Check if hero is at `args[0]`x`args[1]` and the floor tile is `args[2]`"),
    COUNTER_IS(5, 1, "Current zone's `counter` value is equal to `args[0]`"),
    RANDOM_IS(6, 1, "Current zone's `random` value is equal to `args[0]`"),
    RANDOM_IS_GREATER_THAN(7, 1, "Current zone's `random` value is greater than `args[0]`"),
    RANDOM_IS_LESS_THAN(8, 1, "Current zone's `random` value is less than `args[0]`"),
    ENTER_BY_PLANE(9, 0, ""),
    TILE_AT_IS(10, 4, "tileId,x,y,z. Check if tile at `args[1]`x`args[2]`x`args[3]` is equal to `args[0]`"),
    MONSTER_IS_DEAD(11, 1, "True if monster `args[0]` is dead. `args[0]`: monsterId"),
    HAS_NO_ACTIVE_MONSTERS(12, 0, "undefined"),
    HAS_ITEM(13, 1, "True if inventory contains `args[0]`. " +
            "If `args[0]` is `0xFFFF` check if inventory contains the item provided by the current zone's puzzle"),
    REQUIRED_ITEM_IS(14, 1, "itemId"),
    ENDING_IS(15, 1, "True if `args[0]` is equal to current goal item id"),
    ZONE_IS_SOLVED(16, 0, "True if the current zone is solved"),
    NO_ITEM_PLACED(17, 0, "Returns true if the user did not place an item."),
    HAS_GOAL_ITEM(18, 0, "Returns true if the user placed an item"),
    HEALTH_IS_LESS_THAN(19, 1, "Hero's health is less than `args[0]`."),
    HEALTH_IS_GREATER_THAN(20, 1, "Hero's health is greater than `args[0]`."),
    UNUSED(21, 5, ""),
    FIND_ITEM_IS(22, 1, "True the item provided by current zone is `args[0]`"),
    PLACED_ITEM_IS_NOT(23, 5, "x, y, z, target, item"), //https://github.com/cyco/WebFun/blob/master/src/engine/script/conditions/placed-item-is-not.ts
    HERO_IS_AT(24, 2, "True if hero's x/y position is `args_0`x`args_1`."),
    SECTOR_COUNTER_IS(25, 1, "Current zone's `shared_counter` value is equal to `args[0]`"),
    SECTOR_COUNTER_IS_LESS_THAN(26, 1, "Current zone's `shared_counter` value is less than `args[0]`"),
    SECTOR_COUNTER_IS_GREATER_THAN(27, 1, "Current zone's `shared_counter` value is greater than `args[0]`"),
    GAMES_WON_IS(28, 1, "Total games won is equal to `args[0]`"),
    DROPS_QUEST_ITEM_AT(29, 2, "zoneX, zoneY"),
    HAS_ANY_REQUIRED_ITEM(30, 0, "Determines if inventory contains any of the required items needed for current zone"),
    COUNTER_IS_NOT(31, 1, "Current zone's `counter` value is not equal to `args[0]`"),
    RANDOM_IS_NOT(32, 1, "Current zone's `random` value is not equal to `args[0]`"),
    SECTOR_COUNTER_IS_NOT(33, 1, "Current zone's `shared_counter` value is not equal to `args[0]`"),
    IS_VARIABLE(34, 4, "Check if variable identified by `args[0]`⊕`args[1]`⊕`args[2]` is set to `args[3]`. " +
            "Internally this is implemented as opcode 0x0a, check if tile at `args[0]`x`args[1]`x`args[2]` is equal to `args[3]`. " +
            "zone.getTileID(args[1], args[2], args[3]) === args[0]; tileId, x, y, z"),
    GAMES_WON_IS_GREATER_THAN(35, 1, "True, if total games won is greater than `args[0]`");

    private final int id;
    private final int argsCount;
    private final String description;

    private static final Map<Integer, ConditionOpcode> byId = new HashMap<>(36);

    static {
        for (ConditionOpcode e : ConditionOpcode.values()) {
            byId.put(e.id, e);
        }
    }

    public static ConditionOpcode byId(int id) {
        return byId.get(id);
    }

    ConditionOpcode(int id, int argsCount, String description) {
        this.id = id;
        this.argsCount = argsCount;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public int getArgsCount() {
        return argsCount;
    }

    public String getOpcode() {
        return name().toLowerCase().replace("_", "-");
    }

    public String getCamelCaseOpcode() {
        return Arrays.stream(name().split("_")).map(s -> StringUtils.capitalize(s.toLowerCase())).collect(Collectors.joining());
    }

    public String getDescription() {
        return description;
    }
}
