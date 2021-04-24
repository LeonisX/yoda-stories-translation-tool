package md.leonis.ystt.model;

// Object info entry
public class Oie implements Movable {

    private int position;
    private int size;
    private int count;

    public Oie(int position, int size, int count) {
        this.position = position;
        this.size = size;
        this.count = count;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
