package md.leonis.ystt.oldmodel;


import md.leonis.bin.Dump;
import md.leonis.ystt.utils.Config;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static md.leonis.ystt.oldmodel.SaveGameStatus.EMPTY;

public class SaveState {

    private static int MINIMAL_ROM_SIZE = 8188;
    public static String START_TEXT = "PHANTASY STAR         BACKUP RAMPROGRAMMED BY          NAKA YUJI";
    private static int FIRST_ZEROES_SIZE = 0xC0;
    //100 - 1d7 - данные сохранёнки — полное окно с рамкой. 12 строк по 18 байт. Буквы — 2 байта. Примеры: C010 F113
    public static int SAVE_SLOTS_FRAME_OFFSET = 0x100;
    public static int FIRST_SAVE_SLOT_NAME_OFFSET = SAVE_SLOTS_FRAME_OFFSET + 0x12 * 2 + 6; // 0x12A

    private static int SECOND_ZEROES_OFFSET = 0x206;
    private static int SECOND_ZEROES_SIZE = 0x1FA;

    private static String FRAME_LINE_1 = "F1 11 F2 11 F2 11 F2 11 F2 11 F2 11 F2 11 F2 11 F1 13";
    private static String FRAME_LINE_2 = "F3 11 C0 10 C0 10 C0 10 C0 10 C0 10 C0 10 C0 10 F3 13";
    private static String FRAME_LINE_12 = "F1 15 F2 15 F2 15 F2 15 F2 15 F2 15 F2 15 F2 15 F1 17";



    //201-205 - жива ли игра (1)
    public static int SAVE_GAME_STATUS_OFFSET = 0x201;

    public static int FIRST_SAVE_GAME_OFFSET = 0x400;
    public static int SAVE_GAME_SIZE = 0x400; // 400 → 800 → C00 → 1000 → 1400

    private static int ALISA_LEVEL_OFFSET = 0x105;


    private int initialOffset;

    private Dump romData;

    private SaveGame[] saveGames = new SaveGame[5];

    public SaveState(File file) throws IOException {
        romData = new Dump(file);
        updateObject();
    }

    public SaveState(Dump romData) {
        this.romData = romData;
    }

    private void testInnerData() {
        if (romData.size() < MINIMAL_ROM_SIZE) throw new RuntimeException("Save State too small");
        //TODO other sizes
        romData.moveTo(0);
        if (!romData.readString(START_TEXT.length()).equals(START_TEXT)) throw new RuntimeException("Corrupted header / not Phantasy Star Save State");
        if (!romData.checkZeroes(FIRST_ZEROES_SIZE)) throw new RuntimeException("Garbage in header / not Phantasy Star Save State");
        romData.moveTo(SECOND_ZEROES_OFFSET);
        if (!romData.checkZeroes(SECOND_ZEROES_SIZE)) throw new RuntimeException("Garbage in header / not Phantasy Star Save State");
        for (int i = 0; i < 5; i ++) {
            if (romData.getByte(0x126 + i * 0x24) - 0xC1 != i + 1 ) throw new RuntimeException("Corrupt save slots listing");
        }

        romData.moveTo(SAVE_GAME_STATUS_OFFSET);
        for (int i = 0; i < 5; i ++) {
            if (romData.getByte() == 1) saveGames[i].setStatus(SaveGameStatus.ACTIVE);
        }
        // check for deleted
        for (int i = 0; i < 5; i ++) {
            //romData.moveTo(FIRST_SAVE_GAME_OFFSET + i * SAVE_GAME_SIZE + ALISA_LEVEL_OFFSET);
            //if ((romData.getByte() != 0) && (saveGames[i].getStatus().equals(SaveGameStatus.EMPTY))) saveGames[i].setStatus(SaveGameStatus.DELETED);
            if ((romData.getByte(0x126 + 0x12 + 5 + i * 0x24) == 0x00)) saveGames[i].setStatus(SaveGameStatus.DELETED);
        }

    }

    public Dump getRomData() {
        return romData;
    }

    public void setRomData(Dump romData) {
        this.romData = romData;
    }

    public SaveGame[] getSaveGames() {
        return saveGames;
    }

    public void setSaveGames(SaveGame[] saveGames) {
        this.saveGames = saveGames;
    }

    @Override
    public String toString() {
        return "SaveState{" +
                "initialOffset=" + initialOffset +
                ", romData=" + romData +
                ", saveGames=" + Arrays.toString(saveGames) +
                '}';
    }

    public void updateObject() {
        for(int i = 0; i < 5; i ++) {
            saveGames[i] = new SaveGame();
        }
        testInnerData();
        // read saveGames
        for(int i = 0; i < 5; i ++) {
            saveGames[i].setName(readName(i));
            saveGames[i].readFromRom(romData, i * SAVE_GAME_SIZE + FIRST_SAVE_GAME_OFFSET);
        }
        System.out.println(this);
    }

    public void updateDump() {
        for (int i = 0; i < 5; i++) {
            if (!saveGames[i].getStatus().equals(EMPTY)) {
                saveGames[i].writeToRom(romData, i * SAVE_GAME_SIZE + FIRST_SAVE_GAME_OFFSET);
            }
            //System.out.println(this);
        }
    }

    //TODO test
    public String readName(int index) {
        String name = "";
        for (int i = 0; i < 5; i ++) {
            romData.moveTo(FIRST_SAVE_SLOT_NAME_OFFSET + index * 0x24 + i * 2);
            //TODO charset select
            name += Config.languageTable.getProperty(Integer.toHexString(romData.getByte()));
        }
        return name;
    }



    //TODO test
    public void eraseName(int index) {
        romData.moveTo(FIRST_SAVE_SLOT_NAME_OFFSET + index * 0x24);
        for (int i = 0; i < 5; i ++) {
            romData.setByte(0xC0);
            romData.setByte(0x00);
        }
        romData.moveTo(FIRST_SAVE_SLOT_NAME_OFFSET + index * 0x24 + 0x12);
        for (int i = 0; i < 5; i ++) {
            romData.setByte(0xC0);
            romData.setByte(0x00);
        }
    }

    //TODO test
    public void writeName(int index, String name) {
        //System.out.println(Integer.toHexString(FIRST_SAVE_SLOT_NAME_OFFSET + index * 0x24));
        //String name = Config.saveState.getSaveGames()[index].getName();
        romData.moveTo(FIRST_SAVE_SLOT_NAME_OFFSET + index * 0x24);
        for (int i = 0; i < 5; i ++) {
            romData.setByte(0xC0);
            romData.setByte(0x10);
        }
        romData.moveTo(FIRST_SAVE_SLOT_NAME_OFFSET + index * 0x24 + 0x12);
        for (int i = 0; i < 5; i ++) {
            romData.setByte(0xC0);
            romData.setByte(0x10);
        }

        System.out.println(name);
        for (int i = 0; i < name.length(); i ++) {
            //System.out.println(name.charAt(i));
            //System.out.println(Config.getKeyByValue(name.charAt(i)));
            romData.setByte(FIRST_SAVE_SLOT_NAME_OFFSET + index * 0x24 + i * 2, Integer.decode("0x" + Config.getKeyByValue(name.charAt(i))));
        }
    }

    //TODO test
    public void clearArea(int index) {
        int start = FIRST_SAVE_GAME_OFFSET + index * SAVE_GAME_SIZE;
        Arrays.fill(romData.getDump(), start, start + SAVE_GAME_SIZE, (byte) 0x00);
    }

    public void save() throws IOException {
        romData.saveToFile(Config.saveStateFile);
    }
}
