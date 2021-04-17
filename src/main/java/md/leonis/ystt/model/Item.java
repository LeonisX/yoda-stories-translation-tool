package md.leonis.ystt.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Item {

    private String name;
    private int effect;
    private List<Integer> heroes;

    public Item(String[] chunks) {
        this.name = chunks[0].trim();
        this.effect = Integer.parseInt(chunks[1].trim());
        this.heroes = Arrays.stream(chunks[2].trim().split(","))
        .map(Integer::parseInt).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Integer> heroes) {
        this.heroes = heroes;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }
}
