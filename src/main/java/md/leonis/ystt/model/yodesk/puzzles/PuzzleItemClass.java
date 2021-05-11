package md.leonis.ystt.model.yodesk.puzzles;

import java.util.HashMap;
import java.util.Map;

public enum PuzzleItemClass {

    KEYCARD(0),
    TOOL(1),
    PART(2),        // PUZZLE_ITEM_RARE
    UNKNOWN(3),
    VALUABLE(4),    // PUZZLE_ITEM
    NONE(4294967295L);

    private final long id;

    private static final Map<Long, PuzzleItemClass> byId = new HashMap<>(5);

    static {
        for (PuzzleItemClass e : PuzzleItemClass.values())
            byId.put(e.id, e);
    }

    public static PuzzleItemClass byId(long id) {
        return byId.get(id);
    }

    PuzzleItemClass(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
