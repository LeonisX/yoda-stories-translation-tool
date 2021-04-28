package md.leonis.ystt.oldmodel2;

import md.leonis.bin.ByteOrder;
import md.leonis.bin.Dump;
import md.leonis.ystt.utils.BinaryUtils;
import md.leonis.ystt.utils.Config;
import md.leonis.ystt.utils.PaletteUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static md.leonis.ystt.utils.Config.gamePalette;

public class Section {

    public Dump dtaDump;
    public Dump exeDump;
    public String exeCrc32;
    public String dtaCrc32;
    public String revision;
    public String revisionId;

    public void LoadFileToArray(File file) throws IOException {

        Log.debug("DTA file internal structure");
        Log.debug("===========================");
        Log.newLine();

        Path exePath = file.toPath();
        Path dtaPath = getDtaPath(exePath.getParent());

        exeCrc32 = intToHex(BinaryUtils.crc32(exePath), 8);
        dtaCrc32 = intToHex(BinaryUtils.crc32(dtaPath), 8);

        Release release = Config.releases.stream().filter(r -> r.getExeCrc32().equals(exeCrc32) && r.getDtaCrc32().equals(dtaCrc32)).findFirst().orElse(null);

        if (null == release) {
            revision = "Unknown combination of files";
            revisionId = "unk";
        } else {
            revision = release.getTitle();
            revisionId = release.getId();
        }

        exeDump = new Dump(exePath);
        exeDump.setByteOrder(ByteOrder.LITTLE_ENDIAN);

        int paletteStartIndex = exeDump.findAddress(PaletteUtils.getBGRASample(gamePalette));

        if (paletteStartIndex < 0) {
            throw new RuntimeException("Palette isn't found in EXE file");
        }

        //TODO const
        boolean otherPaletteLocation = (0x550F0 != paletteStartIndex);

        Log.newLine();
        Log.debug("EXE:");
        Log.debug("---------");
        Log.newLine();
        Log.debug("Size: " + exeDump.size());
        Log.debug("CRC-32: " + exeCrc32);
        Log.debug("DTA revision: " + revision);
        Log.debug("Palette address: " + intToHex(paletteStartIndex, 6));
        exeDump.setIndex(0);
        if (otherPaletteLocation) {
            Log.debug("Unknown palette location. Standard is: 0x550F0");
        }

        dumpPalette(paletteStartIndex);

        dtaDump = new Dump(dtaPath);
        dtaDump.setByteOrder(ByteOrder.LITTLE_ENDIAN);

        Log.newLine();
        Log.debug("Sections:");
        Log.debug("---------");
        Log.newLine();
        Log.debug("Size: " + dtaDump.size());
        Log.debug("CRC-32: " + dtaCrc32);
        Log.debug("DTA revision: " + revision);
    }

    private void dumpPalette(int paletteStartIndex) {

        gamePalette = PaletteUtils.dumpBGRAPalette(exeDump.getDump(), paletteStartIndex);
        Config.updatePalette();
    }

    public static Path getDtaPath(Path dir) throws IOException {

        try (Stream<Path> stream = Files.list(dir)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.getFileName().toString().toLowerCase().endsWith(".dta"))
                    .findFirst().orElse(null);
        }
    }

    public void SaveToFile(String fileName) throws IOException {
        dtaDump.saveToFile(fileName);
    }

    public String intToHex(int value, int size) {

        String result = Integer.toHexString(value).toUpperCase();
        return /*"$" +*/ StringUtils.leftPad(result, size, '0');
    }

    public String longToHex(long value, int size) {

        String result = Long.toHexString(value);
        return StringUtils.leftPad(result, size, '0');
    }

    //TODO
    public static class Log {

        public static void error(String message) {
            System.out.println("ERROR: " + message);
        }

        public static void debug(String message) {
            System.out.println("DEBUG: " + message);
        }

        public static void debug(int integer) {
            debug(Integer.toString(integer));
        }

        public static void newLine() {
            System.out.println("");
        }

        public static void message(String message) {
            System.out.println("MESSAGE: " + message);
        }

        //TODO
        public static void clear() {
        }
    }
}
