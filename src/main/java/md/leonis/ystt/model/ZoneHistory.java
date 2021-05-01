package md.leonis.ystt.model;

import java.util.Objects;

public class ZoneHistory {

    private final int zoneId;
    private final int x;
    private final int y;
    private final int layerId;
    private final int oldValue;
    private final int newValue;

    public ZoneHistory(int zoneId, int x, int y, int layerId, int oldValue, int newValue) {
        this.zoneId = zoneId;
        this.x = x;
        this.y = y;
        this.layerId = layerId;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public int getZoneId() {
        return zoneId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLayerId() {
        return layerId;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZoneHistory that = (ZoneHistory) o;
        return zoneId == that.zoneId &&
                x == that.x &&
                y == that.y &&
                layerId == that.layerId &&
                oldValue == that.oldValue &&
                newValue == that.newValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, x, y, layerId, oldValue, newValue);
    }
}
