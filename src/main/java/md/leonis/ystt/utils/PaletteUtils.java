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
    public static void saveToFile(int[] gamePalette, Path path) throws IOException {
        saveToFile(gamePalette, path.toFile());
    }

    public static void saveToFile(int[] gamePalette, File file) throws IOException {

        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println("JASC-PAL");
        printWriter.println("0100");
        printWriter.println(gamePalette.length / 4);

        for (int i = 0; i < gamePalette.length; i += 4) {
            int r = gamePalette[i + 2];
            int g = gamePalette[i + 1];
            int b = gamePalette[i];
            printWriter.printf("%s %s %s\n", r, g, b);
        }
        printWriter.close();
    }
}
