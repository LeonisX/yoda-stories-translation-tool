package md.leonis.ystt.model;

// IZON, IZAX, IZX2, IZX3, IZX4, IACT
public class Phrase implements Movable {

    private int position;
    private String phrase;

    public Phrase(int position, String phrase) {
        this.position = position;
        this.phrase = phrase;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
