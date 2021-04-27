package md.leonis.ystt.model.characters;

import java.util.HashMap;
import java.util.Map;

public enum CharacterType {

    HERO(1),
    ENEMY(2),
    WEAPON(4);

    private final long id;

    private static final Map<Long, CharacterType> byId = new HashMap<>(3);
    static {
        for (CharacterType e : CharacterType.values())
            byId.put(e.id(), e);
    }
    public static CharacterType byId(long id) { return byId.get(id); }
    CharacterType(long id) { this.id = id; }
    public long id() { return id; }
}
