package md.leonis.ystt.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Arrays;

public class PaletteUtils {

    public static int[] getBGRASample(int[] gamePalette) {

        return Arrays.copyOfRange(gamePalette, 0, 64);
    }

    public static int[] dumpBGRAPalette(byte[] dump, int paletteStartIndex) {

        int[] bgraPalette = new int[256 * 4];

        for (int i = 0; i < bgraPalette.length; i++) {
            bgraPalette[i] = Byte.toUnsignedInt(dump[paletteStartIndex + i]);
        }

        return bgraPalette;
    }

    /*
    JASC Palette (PAL) file format

    JASC PAL file used in Irfan View and Paintshop Pro.

    This file works like this: The PAL file is a text file, so NO binary data.
    The first line of data contains the text 'JASC-PAL', leave it this way.
    The second line contains the text '0100', don't know what this means, just leave it like this.
    The third line contains the text '256'. This is the number of colors in the palette, most likely 256 colors.

    After that, there are 256 lines of text, containing 3 numbers per line, with spaces between them.
    These numbers are respectively the Red, Green and Blue values (the so-called RGB palette).
    For example, the color white would be '255 255 255' and black '0 0 0' (255 is the most bright, 0 the less bright)
    */
    public static void saveToFile(int[] palette, Path path) throws IOException {
        saveToFile(palette, path.toFile(), false);
    }

    // Exlude colors, used for cycling animation: A0-A7, E0-F5
    public static void saveSafeToFile(int[] palette, Path path) throws IOException {
        saveToFile(palette, path.toFile(), true);
    }

    public static void saveToFile(int[] palette, File file, boolean safe) throws IOException {

        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println("JASC-PAL");
        printWriter.println("0100");
        printWriter.println(palette.length / 4);

        for (int i = 0; i < palette.length; i += 4) {
            if (isSafeColor(i, safe)) {
                int r = palette[i + 2];
                int g = palette[i + 1];
                int b = palette[i];
                printWriter.printf("%s %s %s\n", r, g, b);
            }
        }
        printWriter.close();
    }

    private static boolean isSafeColor(int i, boolean safe) {

        if (!safe) {
            return true;
        }

        if (i >= 10 * 4 && i <= 15 * 4) {
            return false;
        }

        if (i >= 198 * 4 && i <= 207 * 4) {
            return false;
        }

        return i < 224 * 4 || i > 245 * 4;
    }
}
