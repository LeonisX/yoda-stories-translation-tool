package md.leonis.ystt.utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import md.leonis.config.Config;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

import static md.leonis.config.Config.palette;
import static md.leonis.config.Config.yodesk;

public class ImageUtils {

    private static final int TILE_SIZE = 32;

    public static BufferedImage getTile(int tileId) {
        return getTile(tileId, Config.icm0);
    }

    public static BufferedImage getTile(int tileId, IndexColorModel icm) {
        int position = yodesk.getTiles().getTilePixelsPosition(tileId);
        return ImageUtils.readBPicture(yodesk.getTiles().getRawTiles(), position, TILE_SIZE, TILE_SIZE, icm);
    }

    public static WritableImage readWPicture(byte[] bytes, int position, int width, int height, Color transparentColor) {
        WritableImage image = new WritableImage(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int colorIndex = bytes[position] & 0xFF;
                Color color = (colorIndex == 0 && transparentColor != null) ? transparentColor : Config.palette[colorIndex];
                image.getPixelWriter().setColor(x, y, color);
                /*titleScreenCanvas.getGraphicsContext2D().getPixelWriter().setArgb(x, y, palette[index]);
                image.getPixelWriter().setArgb(x, y, palette[index]);*/
                position++;
            }
        }

        return image;
    }

    public static BufferedImage readBPicture(byte[] bytes, int position, int width, int height, IndexColorModel icm) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED, icm);
        WritableRaster raster = image.getRaster();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int b = bytes[position] & 0xFF;
                raster.setSample(x, y, 0, b);
                position++;
            }
        }

        return image;
    }

    public static void drawOnBufferedImage(byte[] bytes, int position, BufferedImage bi, int xOffset, int yOffset, boolean transparent) {
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                int b = bytes[position] & 0xFF;
                if (!(transparent && b == 0)) {
                    bi.getRaster().setSample(xOffset + x, yOffset + y, 0, b);
                }
                position++;
            }
        }
    }

    public static void drawBorderOnBufferedImage(BufferedImage bi, int xOffset, int yOffset, java.awt.Color borderColor) {
        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(borderColor);
        //g2d.setStroke(borderColor);
        //graphics2D.setLineDashes(1.5, 4);
        //graphics2D.strokeRect(xOffset + 0.5, yOffset + 0.5, 32 - 0.5, 32 - 0.5);

        float[] dash1 = { 2f, 0f, 2f };

        BasicStroke bs1 = new BasicStroke(1,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND,
                1.0f,
                dash1,
                2f);
        g2d.setStroke(bs1);
        g2d.drawRect(xOffset, yOffset, 32, 32);
    }

    public static void drawPalette(Canvas canvas) {
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                canvas.getGraphicsContext2D().setFill(Config.palette[y * 16 + x]);
                canvas.getGraphicsContext2D().fillRect(x * 18, y * 18, 18, 18);
            }
        }
    }

    public static void drawOnCanvas(byte[] bytes, int position, Canvas canvas, int xOffset, int yOffset, Color transparentColor) {
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                int colorIndex = bytes[position] & 0xFF;
                if (colorIndex != 0) {
                    Color color = Config.palette[colorIndex];
                    canvas.getGraphicsContext2D().getPixelWriter().setColor(xOffset + x, yOffset + y, color);
                } else if (null != transparentColor) {
                    canvas.getGraphicsContext2D().getPixelWriter().setColor(xOffset + x, yOffset + y, transparentColor);
                }
                position++;
                /*titleScreenCanvas.getGraphicsContext2D().getPixelWriter().setArgb(x, y, palette[index]);*/
            }
        }
    }

    public static void drawBorderOnCanvas(Canvas canvas, int xOffset, int yOffset, Color borderColor) {
        canvas.getGraphicsContext2D().setStroke(borderColor);
        canvas.getGraphicsContext2D().setLineDashes(1.5, 4);
        canvas.getGraphicsContext2D().strokeRect(xOffset + 0.5, yOffset + 0.5, 32 - 0.5, 32 - 0.5);
    }

    public static void drawOnCanvas(BufferedImage bi, Canvas canvas, Color transparentColor) {
        canvas.setHeight(bi.getHeight());
        canvas.setWidth(bi.getWidth());

        int[] bytes = bi.getData().getPixels(0, 0, bi.getWidth(), bi.getHeight(), new int[bi.getWidth() * bi.getHeight()]);

        int position = 0;
        for (int y = 0; y < bi.getHeight(); y++) {
            for (int x = 0; x < bi.getWidth(); x++) {
                int colorIndex = bytes[position];
                if (colorIndex != 0) {
                    Color color = Config.palette[colorIndex];
                    canvas.getGraphicsContext2D().getPixelWriter().setColor( x,  y, color);
                } else if (null != transparentColor) {
                    canvas.getGraphicsContext2D().getPixelWriter().setColor( x,  y, transparentColor);
                }
                position++;
                /*titleScreenCanvas.getGraphicsContext2D().getPixelWriter().setArgb(x, y, palette[index]);*/
            }
        }
    }

    public static void fillCanvas(Canvas canvas, int xOffset, int yOffset, Color fillColor) {
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                canvas.getGraphicsContext2D().getPixelWriter().setColor(xOffset + x, yOffset + y, fillColor);
            }
        }
    }

    public static void fillCanvas(Canvas canvas, Color fillColor) {
        for (int y = 0; y < canvas.getHeight(); y++) {
            for (int x = 0; x < canvas.getWidth(); x++) {
                canvas.getGraphicsContext2D().getPixelWriter().setColor(x, y, fillColor);
            }
        }
    }

    public static WritableImage snapshot(Canvas canvas, int x, int y, int width, int height) {
        Canvas result = new Canvas(width, height);
        WritableImage wi = canvas.snapshot(null, null);
        result.getGraphicsContext2D().drawImage(wi, x, y, width, height, 0, 0, width, height);
        return result.snapshot(null, null);
    }

    public static byte[] getBytes(Canvas canvas, int x, int y, int width, int height, Color transparentColor) {
        WritableImage snap = canvas.snapshot(null, null);
        return getBytes(snap, x, y, width, height, transparentColor);
    }

    public static byte[] getBytes(WritableImage image, Color transparentColor) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        return getBytes(image, 0, 0, width, height, transparentColor);
    }

    public static byte[] getBytes(WritableImage image, int x, int y, int width, int height, Color transparentColor) {
        byte[] raster = new byte[width * height];

        int k = 0;
        for (int iy = 0; iy < height; iy++) {
            for (int ix = 0; ix < width; ix++) {
                Color color = image.getPixelReader().getColor(ix + x, iy + y);
                int id = -1;
                //TODO map map map
                for (int i = 0; i < palette.length; i++) {
                    if (palette[i].equals(color)) {
                        id = i;
                        break;
                    }
                }
                if (color.equals(transparentColor)) {
                    id = 0;
                }
                raster[k++] = (byte) (id & 0xff);
            }
        }
        return raster;
    }

    public static BufferedImage replaceIcm(BufferedImage bi, IndexColorModel icm) {
        return new BufferedImage(icm, bi.getRaster(), icm.isAlphaPremultiplied(), null);
    }

    public static BufferedImage clearBufferedImage(BufferedImage bi) {
        final IndexColorModel icm = (IndexColorModel) bi.getColorModel();
        return new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_BYTE_INDEXED, icm);
    }
}
