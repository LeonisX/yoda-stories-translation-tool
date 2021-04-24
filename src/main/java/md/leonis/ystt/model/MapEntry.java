package md.leonis.ystt.model;

import java.util.ArrayList;
import java.util.List;

//TODO may be keep IACT, PUZ2 text count
public class MapEntry implements Movable {

    private int id;

    private int offset;
    private int size;

    private Oie oie;
    private Izon izon;
    private Izon izax;
    private Izon izx2;
    private Izon izx3;
    private Izon izx4;

    //TODO may be remove after unification (will be position of first iact)
    private int iactPosition;
    //TODO may be remove - need to test that this size is sum of all iacts sizes;
    private int iactSize;

    private List<Izon> iacts = new ArrayList<>();

    public MapEntry(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getPosition() {
        return offset;
    }

    @Override
    public void setPosition(int position) {
        this.offset = position;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Oie getOie() {
        return oie;
    }

    public void setOie(Oie oie) {
        this.oie = oie;
    }

    public Izon getIzon() {
        return izon;
    }

    public void setIzon(Izon izon) {
        this.izon = izon;
    }

    public Izon getIzax() {
        return izax;
    }

    public void setIzax(Izon izax) {
        this.izax = izax;
    }

    public Izon getIzx2() {
        return izx2;
    }

    public void setIzx2(Izon izx2) {
        this.izx2 = izx2;
    }

    public Izon getIzx3() {
        return izx3;
    }

    public void setIzx3(Izon izx3) {
        this.izx3 = izx3;
    }

    public Izon getIzx4() {
        return izx4;
    }

    public void setIzx4(Izon izx4) {
        this.izx4 = izx4;
    }

    public List<Izon> getIacts() {
        return iacts;
    }

    public void setIacts(List<Izon> iacts) {
        this.iacts = iacts;
    }

    public int getIactPosition() {
        return iactPosition;
    }

    public void setIactPosition(int iactPosition) {
        this.iactPosition = iactPosition;
    }

    public int getIactSize() {
        return iactSize;
    }

    public void setIactSize(int iactSize) {
        this.iactSize = iactSize;
    }
}
