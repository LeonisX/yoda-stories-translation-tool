package md.leonis.ystt.model;

import java.util.ArrayList;
import java.util.List;

//TODO may be keep IACT, PUZ2 text count
public class MapEntry {

    private int id;
    private int mapOffset;
    private int izonOffset;
    private int izaxOffset;
    private int izx2Offset;
    private int izx3Offset;
    private int izx4Offset;
    private int iactOffset;

    private int mapSize;
    private int izonSize;
    private int izaxSize;
    private int izx2Size;
    private int izx3Size;
    private int izx4Size;
    private int iactSize;
    private int oieOffset;
    private int oieSize;
    private int oieCount;

    private List<Integer> IACTS = new ArrayList<>();

    public MapEntry(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMapOffset() {
        return mapOffset;
    }

    public void setMapOffset(int mapOffset) {
        this.mapOffset = mapOffset;
    }

    public int getIzonOffset() {
        return izonOffset;
    }

    public void setIzonOffset(int izonOffset) {
        this.izonOffset = izonOffset;
    }

    public int getIzaxOffset() {
        return izaxOffset;
    }

    public void setIzaxOffset(int izaxOffset) {
        this.izaxOffset = izaxOffset;
    }

    public int getIzx2Offset() {
        return izx2Offset;
    }

    public void setIzx2Offset(int izx2Offset) {
        this.izx2Offset = izx2Offset;
    }

    public int getIzx3Offset() {
        return izx3Offset;
    }

    public void setIzx3Offset(int izx3Offset) {
        this.izx3Offset = izx3Offset;
    }

    public int getIzx4Offset() {
        return izx4Offset;
    }

    public void setIzx4Offset(int izx4Offset) {
        this.izx4Offset = izx4Offset;
    }

    public int getIactOffset() {
        return iactOffset;
    }

    public void setIactOffset(int iactOffset) {
        this.iactOffset = iactOffset;
    }

    public int getMapSize() {
        return mapSize;
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public int getIzonSize() {
        return izonSize;
    }

    public void setIzonSize(int izonSize) {
        this.izonSize = izonSize;
    }

    public int getIzaxSize() {
        return izaxSize;
    }

    public void setIzaxSize(int izaxSize) {
        this.izaxSize = izaxSize;
    }

    public int getIzx2Size() {
        return izx2Size;
    }

    public void setIzx2Size(int izx2Size) {
        this.izx2Size = izx2Size;
    }

    public int getIzx3Size() {
        return izx3Size;
    }

    public void setIzx3Size(int izx3Size) {
        this.izx3Size = izx3Size;
    }

    public int getIzx4Size() {
        return izx4Size;
    }

    public void setIzx4Size(int izx4Size) {
        this.izx4Size = izx4Size;
    }

    public int getIactSize() {
        return iactSize;
    }

    public void setIactSize(int iactSize) {
        this.iactSize = iactSize;
    }

    public int getOieOffset() {
        return oieOffset;
    }

    public void setOieOffset(int oieOffset) {
        this.oieOffset = oieOffset;
    }

    public int getOieSize() {
        return oieSize;
    }

    public void setOieSize(int oieSize) {
        this.oieSize = oieSize;
    }

    public int getOieCount() {
        return oieCount;
    }

    public void setOieCount(int oieCount) {
        this.oieCount = oieCount;
    }

    public List<Integer> getIACTS() {
        return IACTS;
    }

    public void setIACTS(List<Integer> IACTS) {
        this.IACTS = IACTS;
    }
}
