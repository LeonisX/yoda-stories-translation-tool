package md.leonis.ystt.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum ZoneLayer {

    BOTTOM(0),
    MIDDLE(1),
    TOP(2);

    private final int id;

    ZoneLayer(int id) {
        this.id = id;
    }

    public static String byId(String id) {
        return Arrays.stream(ZoneLayer.values()).filter(z -> z.id == Integer.parseInt(id)).findFirst()
                .map(z -> StringUtils.capitalize(z.name().toLowerCase())).orElse(null);
    }
}
