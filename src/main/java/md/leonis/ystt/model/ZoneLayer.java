package md.leonis.ystt.model;

import org.apache.commons.lang3.StringUtils;

public enum ZoneLayer {

    BOTTOM,
    MIDDLE,
    TOP;

    public static String byId(String id) {
        return StringUtils.capitalize(ZoneLayer.values()[Integer.parseInt(id)].name().toLowerCase());
    }
}
