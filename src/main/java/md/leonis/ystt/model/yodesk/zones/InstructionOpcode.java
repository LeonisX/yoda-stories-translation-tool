package md.leonis.ystt.model.yodesk.zones;

import java.util.HashMap;
import java.util.Map;

// https://github.com/cyco/WebFun/tree/master/src/engine/script/instructions
// https://www.webfun.io/docs/scripting/instructions.html
// https://www.webfun.io/docs/appendix/yodesk.html
public enum InstructionOpcode {

    PLACE_TILE(0, 4, "Place tile `arg_3` at `arg_0`x`arg_1`x`arg_2`. To remove a tile the id -1 is used."),
    REMOVE_TILE(1, 3, "Remove tile at `arg_0`x`arg_1`x`arg_2`"),
    MOVE_TILE(2, 5, "Move Tile at `arg_0`x`arg_1`x`arg_2` to `arg_3`x`arg_4`x`arg_2`. " +
            "*Note that this can not be used to move tiles between layers!*"),
    DRAW_TILE(3, 5, ""), // Very strange... https://github.com/cyco/WebFun/blob/master/src/engine/script/instructions/draw-tile.ts
    SPEAK_HERO(4, 0, "Show speech bubble next to hero. _Uses `text` attribute_."),
    SPEAK_NPC(5, 2, "Show speech bubble at `arg_0`x`arg_1`. _Uses `text` attribute_. " +
            " characters `¢` and `¥` are used as placeholders for provided and required items of the current zone, respectively."),
    SET_TILE_NEEDS_DISPLAY(6, 2, "Redraw tile at `arg_0`x`arg_1`"),
    SET_RECT_NEEDS_DISPLAY(7, 4, "Redraw the part of the current scene, " +
            "specified by a rectangle positioned at `arg_0`x`arg_1` with width `arg_2` and height `arg_3`."),
    WAIT(8, 0, "Pause script execution for one tick."),
    REDRAW(9, 0, "Redraw the whole scene immediately"),
    PLAY_SOUND(10, 1, "Play sound specified by `arg_0`"),
    STOP_SOUND(11, 0, "Stop playing sounds."),
    ROLL_DICE(12, 1, "Set current zone's `random` to a random value between 1 and `arg_0`."),
    SET_COUNTER(13, 1, "Set current zone's `counter` value to a `arg_0`"),
    ADD_TO_COUNTER(14, 1, "Add `arg_0` to current zone's `counter` value"),
    SET_VARIABLE(15, 4, "Set variable identified by `arg_0`⊕`arg_1`⊕`arg_2` to `arg_3`. " +
            "Internally this is implemented as opcode 0x00, setting tile at `arg_0`x`arg_1`x`arg_2` to `arg_3`."),
    HIDE_HERO(16, 0, "Hide hero"),
    SHOW_HERO(17, 0, "Show hero"),
    MOVE_HERO_TO(18, 2, "Set hero's position to `arg_0`x`arg_1` ignoring impassable tiles. " +
            "Execute hotspot actions, redraw the current scene and move camera if the hero is not hidden."),
    MOVE_HERO_BY(19, 4, "relx, rely, absx, absy"),
    DISABLE_ACTION(20, 0, "Disable current action"),
    ENABLE_HOTSPOT(21, 1, "Enable hotspot `arg_0`"),
    DISABLE_HOTSPOT(22, 1, "Disable hotspot `arg_0`"),
    ENABLE_MONSTER(23, 1, "Enable monster `arg_0`"),
    DISABLE_MONSTER(24, 1, "Disable monster `arg_0`"),
    ENABLE_ALL_MONSTERS(25, 0, "Enable all monsters"),
    DISABLE_ALL_MONSTERS(26, 0, "Disable all monsters"),
    DROP_ITEM(27, 3, "Drops item `arg_0` for pickup at `arg_1`x`arg_2`. " +
            "If the item is -1, it drops the current sector's find item. instead"),
    ADD_ITEM(28, 1, "Add item with id `arg_0` to inventory"),
    REMOVE_ITEM(29, 1, "Remove one instance of item `arg_0` from the inventory"),
    MARK_AS_SOLVED(30, 0, ""),
    WIN_GAME(31, 0, ""),
    LOSE_GAME(32, 0, ""),
    CHANGE_ZONE(33, 3, "Change current zone to `arg_0`. Hero will be placed at `arg_1`x`arg_2` in the new zone."),
    SET_SECTOR_COUNTER(34, 1, "Set current zone's `sector-counter` value to a `arg_0`"),
    ADD_TO_SECTOR_COUNTER(35, 1, "Add `arg_0` to current zone's `sector-counter` value"),
    SET_RANDOM(36, 1, "Set current zone's `random` value to a `arg_0`"),
    ADD_HEALTH(37, 1, "Increase hero's health by `arg_0`. New health is capped at hero's max health (0x300).");

    private final int id;
    private final int argsCount;
    private final String description;

    private static final Map<Integer, InstructionOpcode> byId = new HashMap<>(38);

    static {
        for (InstructionOpcode e : InstructionOpcode.values()) {
            byId.put(e.id, e);
        }
    }

    public static InstructionOpcode byId(int id) {
        return byId.get(id);
    }

    InstructionOpcode(int id, int argsCount, String description) {
        this.id = id;
        this.argsCount = argsCount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getArgsCount() {
        return argsCount;
    }

    public String getDescription() {
        return description;
    }
}
