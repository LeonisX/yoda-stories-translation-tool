package md.leonis.ystt.model;


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import md.leonis.bin.Dump;
import md.leonis.ystt.utils.Config;
import md.leonis.ystt.utils.JavaFxUtils;
import md.leonis.ystt.view.SaveGamePaneController;

import static md.leonis.ystt.utils.Config.currentHero;
import static md.leonis.ystt.utils.Config.currentHeroIndex;


public class Hero {

    private String name;
    private boolean isAlive;    // 0x500. 1 byte
    private int hp;             // 0x501. 1 byte
    private int mp;             // 0x502. 1 byte
    private int experience;     // 0x50330x504. 2 bytes
    private int level;          // 0x505. max = 1E (30)
    private int maxHp;          // 0x506. 1 byte
    private int maxMp;          // 0x507. 1 byte
    private int attack;         // 0x508. 1 byte
    private int defense;        // 0x509. 1 byte
    private int weapon;         // 0x50A. 1 byte
    private int armor;          // 0x50B. 1 byte
    private int shield;         // 0x50C. 1 byte
    private int state;          // 0x50D. 1 byte //TODO research in RAM. Trapped,... ???
    private int combatSpells;   // 0x50E. 1 byte
    private int curativeSpells; // 0x50F. 1 byte

    private BorderPane borderPane;
    private HBox leftHBox;
    private HBox rightHBox;
    private Label nameLabel;
    private Label textLabel;
    private Button customizeButton;
    private Button healButton;
    private Button reviveButton;
    private Button hireButton;

//TODO research for all heroes start equipment

    public static Hero readFromRom(Dump romData, int index) {
        return new Hero(index, romData.getBoolean(), romData.getByte(), romData.getByte(), romData.getShort() ,romData.getByte(),
                romData.getByte(), romData.getByte(),romData.getByte(), romData.getByte(), romData.getByte(), romData.getByte(), romData.getByte(),
                romData.getByte(), romData.getByte(), romData.getByte());
    }


    public void writeToRom(Dump romData) {
        romData.setBoolean(isAlive);
        romData.setByte(hp);
        romData.setByte(mp);
        //ByteOrder byteOrder = romData.getByteOrder();
        //romData.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        romData.setShort(experience);
        //romData.setByteOrder(ByteOrder.BIG_ENDIAN);
        romData.setByte(level);
        romData.setByte(maxHp);
        romData.setByte(maxMp);
        romData.setByte(attack);
        romData.setByte(defense);
        romData.setByte(weapon);
        romData.setByte(armor);
        romData.setByte(shield);
        romData.setByte(state);
        romData.setByte(combatSpells);
        romData.setByte(curativeSpells);
    }

    public Hero(int index, boolean isAlive, int hp, int mp, int experience, int level, int maxHp, int maxMp, int attack, int defense, int weapon, int armor, int shield, int state, int combatSpells, int curativeSpells) {
        this.name = Config.heroes.get(index);
        this.isAlive = isAlive;
        this.hp = hp;
        this.mp = mp;
        this.experience = experience;
        this.level = level;
        this.maxHp = maxHp;
        this.maxMp = maxMp;
        this.attack = attack;
        this.defense = defense;
        this.weapon = weapon;
        this.armor = armor;
        this.shield = shield;
        this.state = state;
        //TODO battle??
        this.combatSpells = combatSpells;
        //TODO overworld
        this.curativeSpells = curativeSpells;

        nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: bold;");
        nameLabel.setPrefWidth(70);
        textLabel = new Label();
        healButton = new Button("heal");
        reviveButton = new Button("revive");
        hireButton = new Button("hire");
        customizeButton = new Button("customize");

        healButton.setUserData(index);
        reviveButton.setUserData(index);
        hireButton.setUserData(index);
        customizeButton.setUserData(index);

        healButton.setOnAction(this::healButtonClick);
        reviveButton.setOnAction(this::healButtonClick);
        hireButton.setOnAction(this::hireButtonClick);
        customizeButton.setOnAction(this::customizeButtonClick);



        healButton.managedProperty().bind(healButton.visibleProperty());
        reviveButton.managedProperty().bind(reviveButton.visibleProperty());
        hireButton.managedProperty().bind(hireButton.visibleProperty());
        //TODO currentHero
        customizeButton.managedProperty().bind(customizeButton.visibleProperty());

        leftHBox = new HBox(nameLabel, textLabel);
        rightHBox = new HBox(healButton, reviveButton, hireButton, customizeButton);
        rightHBox.setSpacing(5);
        borderPane = new BorderPane(leftHBox);
        borderPane.setRight(rightHBox);

        update();
    }

    public void update() {
        boolean exist = level > 0;
        if (exist) {
            double health = (maxHp + maxMp) == 0 ? 0 : (hp + mp) * 100.0 / (maxHp + maxMp);
            String state = Config.states.get(0);
            if (health < 90) state = Config.states.get(1);
            if (health < 60) state = Config.states.get(2);
            if (health < 30) state = Config.states.get(3);
            if (!isAlive) state = Config.states.get(4);
            textLabel.setText("level: " + level + "; health: " + state);
        } else {
            textLabel.setText("somewhere far away...");
        }
        healButton.setVisible((maxHp > hp || maxHp > hp) && exist);
        reviveButton.setVisible(!isAlive && exist);
        hireButton.setVisible(!exist);
        customizeButton.setVisible(exist && isAlive);
    }

    private void healButtonClick(ActionEvent actionEvent) {
        int index = (int) ((Node) actionEvent.getSource()).getUserData();
        Hero hero = Config.currentSaveGame.getHeroes()[index];
        hero.setAlive(true);
        hero.setHp(hero.getMaxHp());
        hero.setMp(hero.getMaxMp());
        hero.update();
    }

    private void customizeButtonClick(ActionEvent actionEvent) {
        ((SaveGamePaneController) JavaFxUtils.currentController).save();
        Config.selectCurrentHero((int) ((Node) actionEvent.getSource()).getUserData());
        JavaFxUtils.showPane("HeroGamePane.fxml");
    }


    private void hireButtonClick(ActionEvent actionEvent) {
        int index = (int) ((Node) actionEvent.getSource()).getUserData();
        Config.selectCurrentHero(index);
        currentHero.setLevel(1);
        currentHero.update();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int levelId) {
        level = levelId;
        Level lev = Config.getLevel(currentHeroIndex, levelId);
        hp = lev.getHp();
        mp = lev.getMp();
        maxHp = lev.getHp();
        maxMp = lev.getMp();
        experience = lev.getExperience();
        attack = lev.getAttack();
        defense = lev.getDefense();
        combatSpells = lev.getCombatSpells();
        curativeSpells = lev.getCurativeSpells();
        isAlive = hp > 0;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
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

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public HBox getLeftHBox() {
        return leftHBox;
    }

    public void setLeftHBox(HBox leftHBox) {
        this.leftHBox = leftHBox;
    }

    public HBox getRightHBox() {
        return rightHBox;
    }

    public void setRightHBox(HBox rightHBox) {
        this.rightHBox = rightHBox;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public Label getTextLabel() {
        return textLabel;
    }

    public void setTextLabel(Label textLabel) {
        this.textLabel = textLabel;
    }

    public Button getCustomizeButton() {
        return customizeButton;
    }

    public void setCustomizeButton(Button customizeButton) {
        this.customizeButton = customizeButton;
    }

    public Button getHealButton() {
        return healButton;
    }

    public void setHealButton(Button healButton) {
        this.healButton = healButton;
    }

    public Button getReviveButton() {
        return reviveButton;
    }

    public void setReviveButton(Button reviveButton) {
        this.reviveButton = reviveButton;
    }

    public Button getHireButton() {
        return hireButton;
    }

    public void setHireButton(Button hireButton) {
        this.hireButton = hireButton;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", isAlive=" + isAlive +
                ", hp=" + hp +
                ", mp=" + mp +
                ", experience=" + experience +
                ", level=" + level +
                ", maxHp=" + maxHp +
                ", maxMp=" + maxMp +
                ", attack=" + attack +
                ", defense=" + defense +
                ", weapon=" + weapon +
                ", armor=" + armor +
                ", shield=" + shield +
                ", state=" + state +
                ", combatSpells=" + combatSpells +
                ", curativeSpells=" + curativeSpells +
                '}';
    }

}
