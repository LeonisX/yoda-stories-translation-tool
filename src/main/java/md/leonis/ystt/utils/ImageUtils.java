package md.leonis.ystt.utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

import static md.leonis.ystt.utils.Config.*;
import static md.leonis.ystt.utils.Config.section;

public class ImageUtils {

    public static WritableImage readWPicture(int width, int height, Color transparentColor) {

        WritableImage image = new WritableImage(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int colorIndex = section.ReadByte();
                Color color = (colorIndex == 0) ? transparentColor : Config.palette[colorIndex];
                image.getPixelWriter().setColor(x, y, color);
                /*titleScreenCanvas.getGraphicsContext2D().getPixelWriter().setArgb(x, y, palette[index]);
                image.getPixelWriter().setArgb(x, y, palette[index]);*/
            }
        }

        return image;
    }


    //TODO use transparent color
    public static BufferedImage readBPicture(int width, int height, Color transparentColor) {

        IndexColorModel icm = new IndexColorModel(8, 256, ra, ga, ba);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED, icm);

        WritableRaster raster = image.getRaster();

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                int b = section.dump.getByte();
                raster.setSample(x, y, 0, b);
            }
        }

        return image;
    }

    public static void drawPalette(Canvas canvas) {

        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                canvas.getGraphicsContext2D().setFill(Config.palette[y * 16 + x]);
                canvas.getGraphicsContext2D().fillRect(x * 18, y * 18, 18, 18);
            }
        }
    }

    public static void drawOnCanvas(Canvas canvas, int xOffset, int yOffset) {

        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                int colorIndex = section.ReadByte();
                if (colorIndex != 0) {
                    Color color = Config.palette[colorIndex];
                    canvas.getGraphicsContext2D().getPixelWriter().setColor(xOffset + x, yOffset + y, color);
                }
                /*titleScreenCanvas.getGraphicsContext2D().getPixelWriter().setArgb(x, y, palette[index]);*/
            }
        }
    }

    public static void drawOnCanvas(Canvas canvas, int xOffset, int yOffset, Color color) {

        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                canvas.getGraphicsContext2D().getPixelWriter().setColor(xOffset + x, yOffset + y, color);
            }
        }
    }
}
