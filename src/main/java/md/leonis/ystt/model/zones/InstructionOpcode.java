package md.leonis.ystt.model.zones;

import java.util.HashMap;
import java.util.Map;

public enum InstructionOpcode {

    PLACE_TILE(0),
    REMOVE_TILE(1),
    MOVE_TILE(2),
    DRAW_TILE(3),
    SPEAK_HERO(4),
    SPEAK_NPC(5),
    SET_TILE_NEEDS_DISPLAY(6),
    SET_RECT_NEEDS_DISPLAY(7),
    WAIT(8),
    REDRAW(9),
    PLAY_SOUND(10),
    STOP_SOUND(11),
    ROLL_DICE(12),
    SET_COUNTER(13),
    ADD_TO_COUNTER(14),
    SET_VARIABLE(15),
    HIDE_HERO(16),
    SHOW_HERO(17),
    MOVE_HERO_TO(18),
    MOVE_HERO_BY(19),
    DISABLE_ACTION(20),
    ENABLE_HOTSPOT(21),
    DISABLE_HOTSPOT(22),
    ENABLE_MONSTER(23),
    DISABLE_MONSTER(24),
    ENABLE_ALL_MONSTERS(25),
    DISABLE_ALL_MONSTERS(26),
    DROP_ITEM(27),
    ADD_ITEM(28),
    REMOVE_ITEM(29),
    MARK_AS_SOLVED(30),
    WIN_GAME(31),
    LOSE_GAME(32),
    CHANGE_ZONE(33),
    SET_SHARED_COUNTER(34),
    ADD_TO_SHARED_COUNTER(35),
    SET_RANDOM(36),
    ADD_HEALTH(37);

    private final long id;

    private static final Map<Long, InstructionOpcode> byId = new HashMap<>(38);

    static {
        for (InstructionOpcode e : InstructionOpcode.values()) {
            byId.put(e.id(), e);
        }
    }

    public static InstructionOpcode byId(long id) {
        return byId.get(id);
    }

    InstructionOpcode(long id) {
        this.id = id;
    }

    public long id() {
        return id;
    }
}