package md.leonis.ystt.oldmodel2;

import java.util.ArrayList;
import java.util.List;

public class Puzzle implements Movable {

    private int id;
    private int position;
    private int size;
    private List<Phrase> phrases = new ArrayList<>();

    public Puzzle(int id, int position, int size) {
        this.id = id;
        this.position = position;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<Phrase> phrases) {
        this.phrases = phrases;
    }
}
