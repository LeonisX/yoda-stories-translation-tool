package md.leonis.ystt.model;

import java.util.ArrayList;
import java.util.List;

public class Iact implements Movable {

    private int position;
    private int size;
    private List<Phrase> phrases = new ArrayList<>();

    public Iact(int position, int size) {
        this.position = position;
        this.size = size;
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
