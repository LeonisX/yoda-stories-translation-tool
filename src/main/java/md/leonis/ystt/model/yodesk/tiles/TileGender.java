package md.leonis.ystt.model.yodesk.tiles;

import java.util.HashMap;
import java.util.Map;

public enum TileGender {

    MASCULINE(0),
    FEMININE(1),
    NEUTER(2),
    UNKNOWN(0x1684);

    private final int id;

    private static final Map<Integer, TileGender> byId = new HashMap<>(4);

    static {
        for (TileGender e : TileGender.values())
            byId.put(e.id, e);
    }

    public static TileGender byId(int id) {
        return byId.get(id);
    }

    TileGender(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
