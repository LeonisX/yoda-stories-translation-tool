package md.leonis.ystt.model.yodesk.puzzles;

import java.util.HashMap;
import java.util.Map;

public enum StringMeaning {

    REQUEST(0),
    THANK(1),
    OFFER(2),
    MISSION(3),
    UNUSED(4);

    private final int id;

    private static final Map<Integer, StringMeaning> byId = new HashMap<>(5);

    static {
        for (StringMeaning e : StringMeaning.values()) {
            byId.put(e.id, e);
        }
    }

    public static StringMeaning byId(int id) {
        return byId.get(id);
    }

    StringMeaning(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
