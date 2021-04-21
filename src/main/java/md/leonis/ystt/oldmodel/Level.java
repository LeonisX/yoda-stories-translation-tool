package md.leonis.ystt.oldmodel;

import md.leonis.bin.Dump;

public class Level {

    private int experience;
    private int hp;
    private int mp;
    private int attack;
    private int defense;
    private int combatSpells;
    private int curativeSpells;

    public Level(int hp, int attack, int defense, int mp, int experience, int combatSpells, int curativeSpells) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.mp = mp;
        this.experience = experience;
        this.combatSpells = combatSpells;
        this.curativeSpells = curativeSpells;
    }

    public Level(Dump dump) {
        this.hp = dump.getByte();
        this.attack = dump.getByte();
        this.defense = dump.getByte();
        this.mp = dump.getByte();
        this.experience = dump.getWord();
        this.combatSpells = dump.getByte();
        this.curativeSpells = dump.getByte();
    }

    public String toCSV() {
        return String.join(";",
                String.format("%04X", experience),
                String.format("%02X", hp),
                String.format("%02X", mp),
                String.format("%02X", attack),
                String.format("%02X", defense),
                String.format("%02X", combatSpells),
                String.format("%02X", curativeSpells));
    }


    public static Level fromCSV(String data) {
        String[] chunks = data.split(";");
        return new Level(
                Integer.parseInt(chunks[1], 16),
                Integer.parseInt(chunks[3], 16),
                Integer.parseInt(chunks[4], 16),
                Integer.parseInt(chunks[2], 16),
                Integer.parseInt(chunks[0], 16),
                Integer.parseInt(chunks[5], 16),
                Integer.parseInt(chunks[6], 16)
        );
    }


    @Override
    public String toString() {
        return "Level{" +
                "experience=" + experience +
                ", hp=" + hp +
                ", mp=" + mp +
                ", attack=" + attack +
                ", defense=" + defense +
                ", combatSpells=" + combatSpells +
                ", curativeSpells=" + curativeSpells +
                '}';
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getCombatSpells() {
        return combatSpells;
    }

    public void setCombatSpells(int combatSpells) {
        this.combatSpells = combatSpells;
    }

    public int getCurativeSpells() {
        return curativeSpells;
    }

    public void setCurativeSpells(int curativeSpells) {
        this.curativeSpells = curativeSpells;
    }

}
