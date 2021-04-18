package md.leonis.ystt.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import md.leonis.ystt.oldmodel.Level;
import md.leonis.ystt.utils.Config;
import md.leonis.ystt.utils.JavaFxUtils;

import static md.leonis.ystt.utils.Config.currentHero;
import static md.leonis.ystt.utils.Config.currentHeroIndex;

public class HeroGamePaneController {

    @FXML
    public Button okButton;
    @FXML
    public Button dismissButton;

    @FXML
    public Button allowButton;
    @FXML
    public Button dontAllowButton;

    @FXML
    public Label name;
    @FXML
    public TextField hp;
    @FXML
    public TextField maxHp;
    @FXML
    public TextField mp;
    @FXML
    public TextField maxMp;
    @FXML
    public TextField experience;
    @FXML
    public Label nextLevel;
    @FXML
    public ComboBox<String> weapon;
    @FXML
    public ComboBox<String> armor;
    @FXML
    public ComboBox<String> shield;
    @FXML
    public TextField attack;
    @FXML
    public TextField defense;
    @FXML
    public ComboBox<Integer> level;
    @FXML
    public Slider combatSpells;
    @FXML
    public Slider curativeSpells;

    @FXML
    public ImageView image;
    @FXML
    public ImageView icon;
    @FXML
    public Label nextLevelString;

    private ObservableList<String> observableWeaponsList = FXCollections.observableList(Config.weaponNames);
    private ObservableList<String> observableArmorsList = FXCollections.observableList(Config.armorNames);
    private ObservableList<String> observableShieldsList = FXCollections.observableList(Config.shieldNames);
    private ObservableList<String> observableItemsList = FXCollections.observableList(Config.items);

    @FXML
    private void initialize() {
        name.setText(currentHero.getName());
        for (int i = 1; i <= 30; i++) {
            level.getItems().add(i);
        }
        level.getSelectionModel().select(currentHero.getLevel() - 1);
        fillCombos();
        // state - we don't need this for save state
        level.setOnAction(this::levelAction);
        weapon.setOnAction(this::weaponAction);
        armor.setOnAction(this::armorAction);
        shield.setOnAction(this::shieldAction);

        nextLevelString.managedProperty().bind(nextLevelString.visibleProperty());
        allowButton.managedProperty().bind(allowButton.visibleProperty());
        dontAllowButton.managedProperty().bind(dontAllowButton.visibleProperty());
        update();
    }

    private void fillCombos() {
        if (allowButton.isVisible()) {
            weapon.setItems(observableWeaponsList);
            armor.setItems(observableArmorsList);
            shield.setItems(observableShieldsList);
        } else {
            weapon.setItems(observableItemsList);
            armor.setItems(observableItemsList);
            shield.setItems(observableItemsList);
        }
    }

    //TODO bug - Alisa can select Axes, Guns, ...
    //TODO bug - calculate attach/defense, using items characteristics. Verify with
    // real game data (change equipped items)
    private void update() {
        hp.setText(Integer.toString(currentHero.getHp()));
        mp.setText(Integer.toString(currentHero.getMp()));
        experience.setText(Integer.toString(currentHero.getExperience()));
        if (currentHero.getLevel() > 30) {
            currentHero.setLevel(30);
        }
        boolean maxLevel = currentHero.getLevel() == 30;
        nextLevel.setVisible(!maxLevel);
        nextLevelString.setVisible(!maxLevel);
        experience.setText(Integer.toString(currentHero.getExperience()));
        if (!maxLevel) {
            Level level = Config.getLevel(currentHeroIndex, currentHero.getLevel() + 1);
            int diff = level.getExperience() - currentHero.getExperience();
            if (diff < 0) diff = 0;
            nextLevel.setText(String.valueOf(diff));
        }

        maxHp.setText(Integer.toString(currentHero.getMaxHp()));
        maxMp.setText(Integer.toString(currentHero.getMaxMp()));

        attack.setText(Integer.toString(currentHero.getAttack()));
        defense.setText(Integer.toString(currentHero.getDefense()));

        weapon.getSelectionModel().select(currentHero.getWeapon());
        if (allowButton.isVisible()) {
            int armorIndex = Math.max(currentHero.getArmor() - 15, 0);
            int shieldIndex = Math.max(currentHero.getShield() - 24, 0);
            armor.getSelectionModel().select(armorIndex);
            shield.getSelectionModel().select(shieldIndex);
        } else {
            armor.getSelectionModel().select(currentHero.getArmor());
            shield.getSelectionModel().select(currentHero.getShield());
        }

        combatSpells.setMax(Config.getLevel(currentHeroIndex, 30).getCombatSpells());
        combatSpells.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                if (object.equals(0.0)) {
                    return Config.spells.get(0);
                } else {
                    int spellIndex = Config.battleSpells[currentHeroIndex][object.intValue() - 1];
                    return Config.spells.get(spellIndex).replace(" ", "\n");
                }
            }
            @Override
            public Double fromString(String string) {
                return null;
            }
        });
        curativeSpells.setMax(Config.getLevel(currentHeroIndex, 30).getCurativeSpells());
        curativeSpells.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                if (object.equals(0.0)) {
                    return Config.spells.get(0);
                } else {
                    int spellIndex = Config.overworldSpells[currentHeroIndex][object.intValue() - 1];
                    return Config.spells.get(spellIndex).replace(" ", "\n");
                }
            }
            @Override
            public Double fromString(String string) {
                return null;
            }
        });
        combatSpells.setValue(currentHero.getCombatSpells());
        curativeSpells.setValue(currentHero.getCurativeSpells());
    }


    public void okButtonClick() {
        //TODO checks, error handler
        currentHero.setHp(correctByte(hp.getText()));
        currentHero.setMp(correctByte(mp.getText()));
        currentHero.setMaxHp(correctByte(maxHp.getText()));
        currentHero.setMaxMp(correctByte(maxMp.getText()));

        if (currentHero.getHp() == 0) currentHero.setAlive(false);

        currentHero.setExperience(correctShort(experience.getText()));

        currentHero.setAttack(correctByte(attack.getText()));
        currentHero.setDefense(correctByte(defense.getText()));

        currentHero.setCombatSpells((int) combatSpells.getValue());
        currentHero.setCurativeSpells((int) curativeSpells.getValue());

        currentHero.update();

        JavaFxUtils.showPane("SaveGamePane.fxml");
    }

    private int correctByte(String textValue) {
        int value = Integer.parseInt(textValue);
        if (value < 0) return 0;
        if (value > 255) return 255;
        return value;
    }

    private int correctShort(String textValue) {
        int value = Integer.parseInt(textValue);
        if (value < 0) return 0;
        if (value > 65535) return 65535;
        return value;
    }

    public void weaponAction(Event event) {
        currentHero.setWeapon(weapon.getSelectionModel().getSelectedIndex());
    }

    public void armorAction(Event event) {
        if (allowButton.isVisible()) {
            currentHero.setArmor(armor.getSelectionModel().getSelectedIndex() + 15);
        } else {
            currentHero.setArmor(armor.getSelectionModel().getSelectedIndex());
        }
    }

    public void shieldAction(Event event) {
        if (allowButton.isVisible()) {
            currentHero.setShield(shield.getSelectionModel().getSelectedIndex() + 24);
        } else {
            currentHero.setShield(shield.getSelectionModel().getSelectedIndex());
        }
    }

    public void levelAction(Event event) {
        currentHero.setLevel(level.getSelectionModel().getSelectedIndex() + 1);
        currentHero.update();
        update();
    }

    public void allowButtonClick(ActionEvent actionEvent) {
        allowButton.setVisible(!allowButton.isVisible());
        dontAllowButton.setVisible(!dontAllowButton.isVisible());
        fillCombos();
    }

    public void dismissButtonClick(ActionEvent actionEvent) {
        currentHero.setLevel(0);
        currentHero.update();
        JavaFxUtils.showPane("SaveGamePane.fxml");
    }
}
