/*
 * BMPEncoder.java
 *
 * Created on 11 May 2006, 04:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.image4j.codec.bmp;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import net.sf.image4j.io.LittleEndianOutputStream;

import java.awt.image.*;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * Encodes images in BMP format.
 *
 * @author Ian McDonagh
 */
public class BMPEncoder {

    static int[] gamePalette = {0xFE, 0x00, 0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x8B, 0x00, 0xC3, 0xCF, 0x4B, 0x00,
            0x8B, 0xA3, 0x1B, 0x00, 0x57, 0x77, 0x00, 0x00, 0x8B, 0xA3, 0x1B, 0x00, 0xC3, 0xCF, 0x4B, 0x00,
            0xFB, 0xFB, 0xFB, 0x00, 0xEB, 0xE7, 0xE7, 0x00, 0xDB, 0xD3, 0xD3, 0x00, 0xCB, 0xC3, 0xC3, 0x00,
            0xBB, 0xB3, 0xB3, 0x00, 0xAB, 0xA3, 0xA3, 0x00, 0x9B, 0x8F, 0x8F, 0x00, 0x8B, 0x7F, 0x7F, 0x00,
            0x7B, 0x6F, 0x6F, 0x00, 0x67, 0x5B, 0x5B, 0x00, 0x57, 0x4B, 0x4B, 0x00, 0x47, 0x3B, 0x3B, 0x00,
            0x33, 0x2B, 0x2B, 0x00, 0x23, 0x1B, 0x1B, 0x00, 0x13, 0x0F, 0x0F, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0xC7, 0x43, 0x00, 0x00, 0xB7, 0x43, 0x00, 0x00, 0xAB, 0x3F, 0x00, 0x00, 0x9F, 0x3F, 0x00,
            0x00, 0x93, 0x3F, 0x00, 0x00, 0x87, 0x3B, 0x00, 0x00, 0x7B, 0x37, 0x00, 0x00, 0x6F, 0x33, 0x00,
            0x00, 0x63, 0x33, 0x00, 0x00, 0x53, 0x2B, 0x00, 0x00, 0x47, 0x27, 0x00, 0x00, 0x3B, 0x23, 0x00,
            0x00, 0x2F, 0x1B, 0x00, 0x00, 0x23, 0x13, 0x00, 0x00, 0x17, 0x0F, 0x00, 0x00, 0x0B, 0x07, 0x00,
            0x4B, 0x7B, 0xBB, 0x00, 0x43, 0x73, 0xB3, 0x00, 0x43, 0x6B, 0xAB, 0x00, 0x3B, 0x63, 0xA3, 0x00,
            0x3B, 0x63, 0x9B, 0x00, 0x33, 0x5B, 0x93, 0x00, 0x33, 0x5B, 0x8B, 0x00, 0x2B, 0x53, 0x83, 0x00,
            0x2B, 0x4B, 0x73, 0x00, 0x23, 0x4B, 0x6B, 0x00, 0x23, 0x43, 0x5F, 0x00, 0x1B, 0x3B, 0x53, 0x00,
            0x1B, 0x37, 0x47, 0x00, 0x1B, 0x33, 0x43, 0x00, 0x13, 0x2B, 0x3B, 0x00, 0x0B, 0x23, 0x2B, 0x00,
            0xD7, 0xFF, 0xFF, 0x00, 0xBB, 0xEF, 0xEF, 0x00, 0xA3, 0xDF, 0xDF, 0x00, 0x8B, 0xCF, 0xCF, 0x00,
            0x77, 0xC3, 0xC3, 0x00, 0x63, 0xB3, 0xB3, 0x00, 0x53, 0xA3, 0xA3, 0x00, 0x43, 0x93, 0x93, 0x00,
            0x33, 0x87, 0x87, 0x00, 0x27, 0x77, 0x77, 0x00, 0x1B, 0x67, 0x67, 0x00, 0x13, 0x5B, 0x5B, 0x00,
            0x0B, 0x4B, 0x4B, 0x00, 0x07, 0x3B, 0x3B, 0x00, 0x00, 0x2B, 0x2B, 0x00, 0x00, 0x1F, 0x1F, 0x00,
            0xDB, 0xEB, 0xFB, 0x00, 0xD3, 0xE3, 0xFB, 0x00, 0xC3, 0xDB, 0xFB, 0x00, 0xBB, 0xD3, 0xFB, 0x00,
            0xB3, 0xCB, 0xFB, 0x00, 0xA3, 0xC3, 0xFB, 0x00, 0x9B, 0xBB, 0xFB, 0x00, 0x8F, 0xB7, 0xFB, 0x00,
            0x83, 0xB3, 0xF7, 0x00, 0x73, 0xA7, 0xFB, 0x00, 0x63, 0x9B, 0xFB, 0x00, 0x5B, 0x93, 0xF3, 0x00,
            0x5B, 0x8B, 0xEB, 0x00, 0x53, 0x8B, 0xDB, 0x00, 0x53, 0x83, 0xD3, 0x00, 0x4B, 0x7B, 0xCB, 0x00,
            0x9B, 0xC7, 0xFF, 0x00, 0x8F, 0xB7, 0xF7, 0x00, 0x87, 0xB3, 0xEF, 0x00, 0x7F, 0xA7, 0xF3, 0x00,
            0x73, 0x9F, 0xEF, 0x00, 0x53, 0x83, 0xCF, 0x00, 0x3B, 0x6B, 0xB3, 0x00, 0x2F, 0x5B, 0xA3, 0x00,
            0x23, 0x4F, 0x93, 0x00, 0x1B, 0x43, 0x83, 0x00, 0x13, 0x3B, 0x77, 0x00, 0x0B, 0x2F, 0x67, 0x00,
            0x07, 0x27, 0x57, 0x00, 0x00, 0x1B, 0x47, 0x00, 0x00, 0x13, 0x37, 0x00, 0x00, 0x0F, 0x2B, 0x00,
            0xFB, 0xFB, 0xE7, 0x00, 0xF3, 0xF3, 0xD3, 0x00, 0xEB, 0xE7, 0xC7, 0x00, 0xE3, 0xDF, 0xB7, 0x00,
            0xDB, 0xD7, 0xA7, 0x00, 0xD3, 0xCF, 0x97, 0x00, 0xCB, 0xC7, 0x8B, 0x00, 0xC3, 0xBB, 0x7F, 0x00,
            0xBB, 0xB3, 0x73, 0x00, 0xAF, 0xA7, 0x63, 0x00, 0x9B, 0x93, 0x47, 0x00, 0x87, 0x7B, 0x33, 0x00,
            0x6F, 0x67, 0x1F, 0x00, 0x5B, 0x53, 0x0F, 0x00, 0x47, 0x43, 0x00, 0x00, 0x37, 0x33, 0x00, 0x00,
            0xFF, 0xF7, 0xF7, 0x00, 0xEF, 0xDF, 0xDF, 0x00, 0xDF, 0xC7, 0xC7, 0x00, 0xCF, 0xB3, 0xB3, 0x00,
            0xBF, 0x9F, 0x9F, 0x00, 0xB3, 0x8B, 0x8B, 0x00, 0xA3, 0x7B, 0x7B, 0x00, 0x93, 0x6B, 0x6B, 0x00,
            0x83, 0x57, 0x57, 0x00, 0x73, 0x4B, 0x4B, 0x00, 0x67, 0x3B, 0x3B, 0x00, 0x57, 0x2F, 0x2F, 0x00,
            0x47, 0x27, 0x27, 0x00, 0x37, 0x1B, 0x1B, 0x00, 0x27, 0x13, 0x13, 0x00, 0x1B, 0x0B, 0x0B, 0x00,
            0xF7, 0xB3, 0x37, 0x00, 0xE7, 0x93, 0x07, 0x00, 0xFB, 0x53, 0x0B, 0x00, 0xFB, 0x00, 0x00, 0x00,
            0xCB, 0x00, 0x00, 0x00, 0x9F, 0x00, 0x00, 0x00, 0x6F, 0x00, 0x00, 0x00, 0x43, 0x00, 0x00, 0x00,
            0xBF, 0xBB, 0xFB, 0x00, 0x8F, 0x8B, 0xFB, 0x00, 0x5F, 0x5B, 0xFB, 0x00, 0x93, 0xBB, 0xFF, 0x00,
            0x5F, 0x97, 0xF7, 0x00, 0x3B, 0x7B, 0xEF, 0x00, 0x23, 0x63, 0xC3, 0x00, 0x13, 0x53, 0xB3, 0x00,
            0x00, 0x00, 0xFF, 0x00, 0x00, 0x00, 0xEF, 0x00, 0x00, 0x00, 0xE3, 0x00, 0x00, 0x00, 0xD3, 0x00,
            0x00, 0x00, 0xC3, 0x00, 0x00, 0x00, 0xB7, 0x00, 0x00, 0x00, 0xA7, 0x00, 0x00, 0x00, 0x9B, 0x00,
            0x00, 0x00, 0x8B, 0x00, 0x00, 0x00, 0x7F, 0x00, 0x00, 0x00, 0x6F, 0x00, 0x00, 0x00, 0x63, 0x00,
            0x00, 0x00, 0x53, 0x00, 0x00, 0x00, 0x47, 0x00, 0x00, 0x00, 0x37, 0x00, 0x00, 0x00, 0x2B, 0x00,
            0x00, 0xFF, 0xFF, 0x00, 0x00, 0xE3, 0xF7, 0x00, 0x00, 0xCF, 0xF3, 0x00, 0x00, 0xB7, 0xEF, 0x00,
            0x00, 0xA3, 0xEB, 0x00, 0x00, 0x8B, 0xE7, 0x00, 0x00, 0x77, 0xDF, 0x00, 0x00, 0x63, 0xDB, 0x00,
            0x00, 0x4F, 0xD7, 0x00, 0x00, 0x3F, 0xD3, 0x00, 0x00, 0x2F, 0xCF, 0x00, 0x97, 0xFF, 0xFF, 0x00,
            0x83, 0xDF, 0xEF, 0x00, 0x73, 0xC3, 0xDF, 0x00, 0x5F, 0xA7, 0xCF, 0x00, 0x53, 0x8B, 0xC3, 0x00,
            0x2B, 0x2B, 0x00, 0x00, 0x23, 0x23, 0x00, 0x00, 0x1B, 0x1B, 0x00, 0x00, 0x13, 0x13, 0x00, 0x00,
            0xFF, 0x0B, 0x00, 0x00, 0xFF, 0x00, 0x4B, 0x00, 0xFF, 0x00, 0xA3, 0x00, 0xFF, 0x00, 0xFF, 0x00,
            0x00, 0xFF, 0x00, 0x00, 0x00, 0x4B, 0x00, 0x00, 0xFF, 0xFF, 0x00, 0x00, 0xFF, 0x33, 0x2F, 0x00,
            0x00, 0x00, 0xFF, 0x00, 0x00, 0x1F, 0x97, 0x00, 0xDF, 0x00, 0xFF, 0x00, 0x73, 0x00, 0x77, 0x00,
            0x6B, 0x7B, 0xC3, 0x00, 0x57, 0x57, 0xAB, 0x00, 0x57, 0x47, 0x93, 0x00, 0x53, 0x37, 0x7F, 0x00,
            0x4F, 0x27, 0x67, 0x00, 0x47, 0x1B, 0x4F, 0x00, 0x3B, 0x13, 0x3B, 0x00, 0x27, 0x77, 0x77, 0x00,
            0x23, 0x73, 0x73, 0x00, 0x1F, 0x6F, 0x6F, 0x00, 0x1B, 0x6B, 0x6B, 0x00, 0x1B, 0x67, 0x67, 0x00,
            0x1B, 0x6B, 0x6B, 0x00, 0x1F, 0x6F, 0x6F, 0x00, 0x23, 0x73, 0x73, 0x00, 0x27, 0x77, 0x77, 0x00,
            0xFF, 0xFF, 0xEF, 0x00, 0xF7, 0xF7, 0xDB, 0x00, 0xF3, 0xEF, 0xCB, 0x00, 0xEF, 0xEB, 0xBB, 0x00,
            0xF3, 0xEF, 0xCB, 0x00, 0xE7, 0x93, 0x07, 0x00, 0xE7, 0x97, 0x0F, 0x00, 0xEB, 0x9F, 0x17, 0x00,
            0xEF, 0xA3, 0x23, 0x00, 0xF3, 0xAB, 0x2B, 0x00, 0xF7, 0xB3, 0x37, 0x00, 0xEF, 0xA7, 0x27, 0x00,
            0xEB, 0x9F, 0x1B, 0x00, 0xE7, 0x97, 0x0F, 0x00, 0x0B, 0xCB, 0xFB, 0x00, 0x0B, 0xA3, 0xFB, 0x00,
            0x0B, 0x73, 0xFB, 0x00, 0x0B, 0x4B, 0xFB, 0x00, 0x0B, 0x23, 0xFB, 0x00, 0x0B, 0x73, 0xFB, 0x00,
            0x00, 0x13, 0x93, 0x00, 0x00, 0x0B, 0xD3, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0x00};

    static Color[] palette = new Color[256];

    static byte[] ra = new byte[256];
    static byte[] ga = new byte[256];
    static byte[] ba = new byte[256];

    static {
        for (int i = 0; i < 1024; i += 4) {
            int id = i / 4;
            int r = gamePalette[i + 2];
            int g = gamePalette[i + 1];
            int b = gamePalette[i];
            int a = ~gamePalette[i + 3];
            // palette[i / 4] = (a << 24) | (r << 16) | (g << 8) | b;
            palette[id] = Color.rgb(r, g, b);

            ra[id] = (byte) r;
            ga[id] = (byte) g;
            ba[id] = (byte) b;
        }
    }

    /**
     * Creates a new instance of BMPEncoder
     */
    private BMPEncoder() {
    }


    // 8 bit only
    public static void write8bit(InfoHeader ih, IndexColorModel icm, byte[] raster, java.io.File file) throws IOException {
        java.io.FileOutputStream fout = new java.io.FileOutputStream(file);
        try {
            BufferedOutputStream out = new BufferedOutputStream(fout);
            write8bit(ih, icm, raster, out);
            out.flush();
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
            }
        }
    }


    // 8 bit only
    public static void write8bit(Image wi, java.io.File file) throws IOException {

        int width = (int) wi.getWidth();
        int height = (int) wi.getHeight();

        byte[] raster = new byte[width * height];
        Set<Integer> colorsUsed = new HashSet<>();

        int k = 0;
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                Color color = wi.getPixelReader().getColor(x, y);
                int id = -1;
                //TODO map map map
                for (int i = 0; i < palette.length; i++) {
                    if (palette[i].equals(color)) {
                        id = i;
                        break;
                    }
                }
                raster[k] = (byte) (id & 0xff);
                colorsUsed.add(id);
                k++;
            }
        }

        IndexColorModel icm = new IndexColorModel(8, 256, ra, ga, ba);

        InfoHeader ih = new InfoHeader();
        ih.iColorsImportant = 0;
        ih.iCompression = 0;
        ih.iHeight = (int) wi.getHeight();
        ih.iWidth = (int) wi.getWidth();
        ih.sBitCount = (short) 8;
        ih.iNumColors = 1 << ih.sBitCount;
        ih.iColorsUsed = ih.iNumColors;
        //ih.iColorsUsed = colorsUsed.size();
        ih.iImageSize = 0;

        java.io.FileOutputStream fout = new java.io.FileOutputStream(file);
        try {
            BufferedOutputStream out = new BufferedOutputStream(fout);
            write8bit(ih, icm, raster, out);
            out.flush();
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
            }
        }
    }

    // 8 bit only
    public static void write8bit(Canvas canvas, java.io.File file) throws IOException {

        WritableImage wi = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, wi);
        write8bit(wi, file);
    }

    // 8 bit only
    public static void write8bit(ImageView imageView, java.io.File file) throws IOException {
        write8bit(imageView.getImage(), file);
    }

    // 8 bit only
    public static void write8bit(BufferedImage bi, java.io.File file) throws IOException {

        int width = bi.getWidth();
        int height = bi.getHeight();

        byte[] raster = new byte[width * height];
        Set<Integer> colorsUsed = new HashSet<>();

        DataBuffer dataBuffer = bi.getRaster().getDataBuffer();
        DataBufferByte dataBufferByte = (DataBufferByte) dataBuffer;

        byte[] data = dataBufferByte.getData();

        int k = 0;
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                int arrayIndex = x + y * width;
                int colorIndex = data[arrayIndex];
                raster[k] = (byte) (colorIndex & 0xff);
                colorsUsed.add(colorIndex);
                k++;
            }
        }

        IndexColorModel icm = new IndexColorModel(8, 256, ra, ga, ba);

        InfoHeader ih = new InfoHeader();
        ih.iColorsImportant = 0;
        ih.iCompression = 0;
        ih.iHeight = height;
        ih.iWidth = width;
        ih.sBitCount = (short) 8;
        ih.iNumColors = 1 << ih.sBitCount;
        ih.iColorsUsed = ih.iNumColors;
        //ih.iColorsUsed = colorsUsed.size();
        ih.iImageSize = 0;

        java.io.FileOutputStream fout = new java.io.FileOutputStream(file);
        try {
            BufferedOutputStream out = new BufferedOutputStream(fout);
            write8bit(ih, icm, raster, out);
            out.flush();
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
            }
        }
    }


    public static void write8bit(InfoHeader ih, IndexColorModel icm, byte[] raster, java.io.OutputStream os) throws IOException {

        // Create colour map if the image uses an indexed colour model.
        // Images with colour depth of 8 bits or less use an indexed colour model.
        int mapSize = icm.getMapSize();

        // Calculate header size
        int headerSize = 14 //file header
                + ih.iSize; //info header

        // Calculate map size
        int mapBytes = 4 * mapSize;

        // Calculate data offset
        int dataOffset = headerSize + mapBytes;

        // Calculate bytes per line
        int bytesPerLine = getBytesPerLine8(ih.iWidth);

        ih.iImageSize = bytesPerLine * ih.iHeight;

        // calculate file size
        int fileSize = dataOffset + ih.iImageSize;

        // output little endian byte order
        LittleEndianOutputStream out = new LittleEndianOutputStream(os);

        //write file header
        writeFileHeader(fileSize, dataOffset, out);

        //write info header
        ih.write(out);

        //write color map (bit count <= 8)
        if (ih.sBitCount <= 8) {
            writeColorMap(icm, out);
        }

        out.write(raster);
    }


    public static void write(BMPImage bmpImage, java.io.File file) throws IOException {
        java.io.FileOutputStream fout = new java.io.FileOutputStream(file);
        try {
            BufferedOutputStream out = new BufferedOutputStream(fout);
            write(bmpImage, out);
            out.flush();
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
            }
        }
    }


    public static void write(BMPImage bmpImage, java.io.OutputStream os) throws IOException {

        BufferedImage img = bmpImage.getImage();

        // create info header
        InfoHeader ih = createInfoHeader(img);

        // Create colour map if the image uses an indexed colour model.
        // Images with colour depth of 8 bits or less use an indexed colour model.
        int mapSize = 0;
        IndexColorModel icm = null;

        if (ih.sBitCount <= 8) {
            icm = (IndexColorModel) img.getColorModel();
            mapSize = icm.getMapSize();
        }

        // Calculate header size
        int headerSize = 14 //file header
                + ih.iSize; //info header

        // Calculate map size
        int mapBytes = 4 * mapSize;

        // Calculate data offset
        int dataOffset = headerSize + mapBytes;

        // Calculate bytes per line
        int bytesPerLine = 0;

        switch (ih.sBitCount) {
            case 1:
                bytesPerLine = getBytesPerLine1(ih.iWidth);
                break;
            case 4:
                bytesPerLine = getBytesPerLine4(ih.iWidth);
                break;
            case 8:
                bytesPerLine = getBytesPerLine8(ih.iWidth);
                break;
            case 24:
                bytesPerLine = getBytesPerLine24(ih.iWidth);
                break;
            case 32:
                bytesPerLine = ih.iWidth * 4;
                break;
        }

        ih.iImageSize = bytesPerLine * ih.iHeight;

        // calculate file size
        int fileSize = dataOffset + ih.iImageSize;

        // output little endian byte order
        LittleEndianOutputStream out = new LittleEndianOutputStream(os);

        //write file header
        writeFileHeader(fileSize, dataOffset, out);

        //write info header
        ih.write(out);

        //write color map (bit count <= 8)
        if (ih.sBitCount <= 8) {
            writeColorMap(icm, out);
        }

        //write raster data
        switch (ih.sBitCount) {
            case 1:
                write1(img.getRaster(), out);
                break;
            case 4:
                write4(img.getRaster(), out);
                break;
            case 8:
                write8(img.getRaster(), out);
                break;
            case 24:
                write24(img.getRaster(), out);
                break;
            case 32:
                write32(img.getRaster(), img.getAlphaRaster(), out);
                break;
        }
    }


    /**
     * Encodes and writes BMP data the output file
     *
     * @param img  the image to encode
     * @param file the file to which encoded data will be written
     * @throws java.io.IOException if an error occurs
     */
    public static void write(BufferedImage img, java.io.File file) throws IOException {
        java.io.FileOutputStream fout = new java.io.FileOutputStream(file);
        try {
            BufferedOutputStream out = new BufferedOutputStream(fout);
            write(img, out);
            out.flush();
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Encodes and writes BMP data to the output
     *
     * @param img the image to encode
     * @param os  the output to which encoded data will be written
     * @throws java.io.IOException if an error occurs
     */
    public static void write(BufferedImage img, java.io.OutputStream os) throws IOException {

        // create info header
        InfoHeader ih = createInfoHeader(img);

        // Create colour map if the image uses an indexed colour model.
        // Images with colour depth of 8 bits or less use an indexed colour model.
        int mapSize = 0;
        IndexColorModel icm = null;

        if (ih.sBitCount <= 8) {
            icm = (IndexColorModel) img.getColorModel();
            mapSize = icm.getMapSize();
        }

        // Calculate header size
        int headerSize = 14 //file header
                + ih.iSize; //info header

        // Calculate map size
        int mapBytes = 4 * mapSize;

        // Calculate data offset
        int dataOffset = headerSize + mapBytes;

        // Calculate bytes per line
        int bytesPerLine = 0;

        switch (ih.sBitCount) {
            case 1:
                bytesPerLine = getBytesPerLine1(ih.iWidth);
                break;
            case 4:
                bytesPerLine = getBytesPerLine4(ih.iWidth);
                break;
            case 8:
                bytesPerLine = getBytesPerLine8(ih.iWidth);
                break;
            case 24:
                bytesPerLine = getBytesPerLine24(ih.iWidth);
                break;
            case 32:
                bytesPerLine = ih.iWidth * 4;
                break;
        }

        ih.iImageSize = bytesPerLine * ih.iHeight;

        // calculate file size
        int fileSize = dataOffset + ih.iImageSize;

        // output little endian byte order
        LittleEndianOutputStream out = new LittleEndianOutputStream(os);

        //write file header
        writeFileHeader(fileSize, dataOffset, out);

        //write info header
        ih.write(out);

        //write color map (bit count <= 8)
        if (ih.sBitCount <= 8) {
            writeColorMap(icm, out);
        }

        //write raster data
        switch (ih.sBitCount) {
            case 1:
                write1(img.getRaster(), out);
                break;
            case 4:
                write4(img.getRaster(), out);
                break;
            case 8:
                write8(img.getRaster(), out);
                break;
            case 24:
                write24(img.getRaster(), out);
                break;
            case 32:
                write32(img.getRaster(), img.getAlphaRaster(), out);
                break;
        }
    }

    /**
     * Creates an <tt>InfoHeader</tt> from the source image.
     *
     * @param img the source image
     * @return the resultant <tt>InfoHeader</tt> structure
     */
    public static InfoHeader createInfoHeader(BufferedImage img) {
        InfoHeader ret = new InfoHeader();
        ret.iColorsImportant = 0;
        ret.iColorsUsed = 0;
        ret.iCompression = 0;
        ret.iHeight = img.getHeight();
        ret.iWidth = img.getWidth();
        ret.sBitCount = (short) img.getColorModel().getPixelSize();
        ret.iNumColors = 1 << (ret.sBitCount == 32 ? 24 : ret.sBitCount);
        ret.iColorsUsed = ret.iNumColors;
        ret.iImageSize = 0;

        return ret;
    }

    /**
     * Writes the file header.
     *
     * @param fileSize   the calculated file size for the BMP data being written
     * @param dataOffset the calculated offset within the BMP data where the actual bitmap begins
     * @param out        the output to which the file header will be written
     * @throws java.io.IOException if an error occurs
     */
    public static void writeFileHeader(int fileSize, int dataOffset,
                                       net.sf.image4j.io.LittleEndianOutputStream out) throws IOException {
        //signature
        byte[] signature = BMPConstants.FILE_HEADER.getBytes(StandardCharsets.UTF_8);
        out.write(signature);
        //file size
        out.writeIntLE(fileSize);
        //reserved
        out.writeIntLE(0);
        //data offset
        out.writeIntLE(dataOffset);
    }

    /**
     * Writes the colour map resulting from the source <tt>IndexColorModel</tt>.
     *
     * @param icm the source <tt>IndexColorModel</tt>
     * @param out the output to which the colour map will be written
     * @throws java.io.IOException if an error occurs
     */
    public static void writeColorMap(IndexColorModel icm, net.sf.image4j.io.LittleEndianOutputStream out) throws IOException {
        int mapSize = icm.getMapSize();
        for (int i = 0; i < mapSize; i++) {
            int rgb = icm.getRGB(i);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = (rgb) & 0xFF;
            out.writeByte(b);
            out.writeByte(g);
            out.writeByte(r);
            out.writeByte(0);
        }
    }

    /**
     * Calculates the number of bytes per line required for the given width in pixels,
     * for a 1-bit bitmap.  Lines are always padded to the next 4-byte boundary.
     *
     * @param width the width in pixels
     * @return the number of bytes per line
     */
    public static int getBytesPerLine1(int width) {
        int ret = width / 8;
        if (ret * 8 < width) {
            ret++;
        }
        if (ret % 4 != 0) {
            ret = (ret / 4 + 1) * 4;
        }
        return ret;
    }

    /**
     * Calculates the number of bytes per line required for the given with in pixels,
     * for a 4-bit bitmap.  Lines are always padded to the next 4-byte boundary.
     *
     * @param width the width in pixels
     * @return the number of bytes per line
     */
    public static int getBytesPerLine4(int width) {
        int ret = width / 2;
        if (ret % 4 != 0) {
            ret = (ret / 4 + 1) * 4;
        }
        return ret;
    }

    /**
     * Calculates the number of bytes per line required for the given with in pixels,
     * for a 8-bit bitmap.  Lines are always padded to the next 4-byte boundary.
     *
     * @param width the width in pixels
     * @return the number of bytes per line
     */
    public static int getBytesPerLine8(int width) {
        int ret = width;
        if (ret % 4 != 0) {
            ret = (ret / 4 + 1) * 4;
        }
        return ret;
    }

    /**
     * Calculates the number of bytes per line required for the given with in pixels,
     * for a 24-bit bitmap.  Lines are always padded to the next 4-byte boundary.
     *
     * @param width the width in pixels
     * @return the number of bytes per line
     */
    public static int getBytesPerLine24(int width) {
        int ret = width * 3;
        if (ret % 4 != 0) {
            ret = (ret / 4 + 1) * 4;
        }
        return ret;
    }

    /**
     * Calculates the size in bytes of a bitmap with the specified size and colour depth.
     *
     * @param w   the width in pixels
     * @param h   the height in pixels
     * @param bpp the colour depth (bits per pixel)
     * @return the size of the bitmap in bytes
     */
    public static int getBitmapSize(int w, int h, int bpp) {
        int bytesPerLine = 0;
        switch (bpp) {
            case 1:
                bytesPerLine = getBytesPerLine1(w);
                break;
            case 4:
                bytesPerLine = getBytesPerLine4(w);
                break;
            case 8:
                bytesPerLine = getBytesPerLine8(w);
                break;
            case 24:
                bytesPerLine = getBytesPerLine24(w);
                break;
            case 32:
                bytesPerLine = w * 4;
                break;
        }
        return bytesPerLine * h;
    }

    /**
     * Encodes and writes raster data as a 1-bit bitmap.
     *
     * @param raster the source raster data
     * @param out    the output to which the bitmap will be written
     * @throws java.io.IOException if an error occurs
     */
    public static void write1(Raster raster, net.sf.image4j.io.LittleEndianOutputStream out) throws IOException {
        int bytesPerLine = getBytesPerLine1(raster.getWidth());

        byte[] line = new byte[bytesPerLine];

        for (int y = raster.getHeight() - 1; y >= 0; y--) {
            for (int i = 0; i < bytesPerLine; i++) {
                line[i] = 0;
            }

            for (int x = 0; x < raster.getWidth(); x++) {
                int bi = x / 8;
                int i = x % 8;
                int index = raster.getSample(x, y, 0);
                line[bi] = setBit(line[bi], i, index);
            }

            out.write(line);
        }
    }

    /**
     * Encodes and writes raster data as a 4-bit bitmap.
     *
     * @param raster the source raster data
     * @param out    the output to which the bitmap will be written
     * @throws java.io.IOException if an error occurs
     */
    public static void write4(Raster raster, net.sf.image4j.io.LittleEndianOutputStream out) throws IOException {

        // The approach taken here is to use a buffer to hold encoded raster data
        // one line at a time.
        // Perhaps we could just write directly to output instead
        // and avoid using a buffer altogether.  Hypothetically speaking,
        // a very wide image would require a large line buffer here, but then again,
        // large 4 bit bitmaps are pretty uncommon, so using the line buffer approach
        // should be okay.

        int width = raster.getWidth();
        int height = raster.getHeight();

        // calculate bytes per line
        int bytesPerLine = getBytesPerLine4(width);

        // line buffer
        byte[] line = new byte[bytesPerLine];

        // encode and write lines
        for (int y = height - 1; y >= 0; y--) {

            // clear line buffer
            for (int i = 0; i < bytesPerLine; i++) {
                line[i] = 0;
            }

            // encode raster data for line
            for (int x = 0; x < width; x++) {

                // calculate buffer index
                int bi = x / 2;

                // calculate nibble index (high order or low order)
                int i = x % 2;

                // get color index
                int index = raster.getSample(x, y, 0);
                // set color index in buffer
                line[bi] = setNibble(line[bi], i, index);
            }

            // write line data (padding bytes included)
            out.write(line);
        }
    }

    /**
     * Encodes and writes raster data as an 8-bit bitmap.
     *
     * @param raster the source raster data
     * @param out    the output to which the bitmap will be written
     * @throws java.io.IOException if an error occurs
     */
    public static void write8(Raster raster, net.sf.image4j.io.LittleEndianOutputStream out) throws IOException {

        int width = raster.getWidth();
        int height = raster.getHeight();

        // calculate bytes per line
        int bytesPerLine = getBytesPerLine8(width);

        // write lines
        for (int y = height - 1; y >= 0; y--) {

            // write raster data for each line
            for (int x = 0; x < width; x++) {

                // get color index for pixel
                int index = raster.getSample(x, y, 0);

                // write color index
                out.writeByte(index);
            }

            // write padding bytes at end of line
            for (int i = width; i < bytesPerLine; i++) {
                out.writeByte(0);
            }

        }
    }

    /**
     * Encodes and writes raster data as a 24-bit bitmap.
     *
     * @param raster the source raster data
     * @param out    the output to which the bitmap will be written
     * @throws java.io.IOException if an error occurs
     */
    public static void write24(Raster raster, net.sf.image4j.io.LittleEndianOutputStream out) throws IOException {

        int width = raster.getWidth();
        int height = raster.getHeight();

        // calculate bytes per line
        int bytesPerLine = getBytesPerLine24(width);

        // write lines
        for (int y = height - 1; y >= 0; y--) {

            // write pixel data for each line
            for (int x = 0; x < width; x++) {

                // get RGB values for pixel
                int r = raster.getSample(x, y, 0);
                int g = raster.getSample(x, y, 1);
                int b = raster.getSample(x, y, 2);

                // write RGB values
                out.writeByte(b);
                out.writeByte(g);
                out.writeByte(r);
            }

            // write padding bytes at end of line
            for (int i = width * 3; i < bytesPerLine; i++) {
                out.writeByte(0);
            }
        }
    }

    /**
     * Encodes and writes raster data, together with alpha (transparency) data, as a 32-bit bitmap.
     *
     * @param raster the source raster data
     * @param alpha  the source alpha data
     * @param out    the output to which the bitmap will be written
     * @throws java.io.IOException if an error occurs
     */
    public static void write32(Raster raster, Raster alpha, net.sf.image4j.io.LittleEndianOutputStream out) throws IOException {

        int width = raster.getWidth();
        int height = raster.getHeight();

        // write lines
        for (int y = height - 1; y >= 0; y--) {

            // write pixel data for each line
            for (int x = 0; x < width; x++) {

                // get RGBA values
                int r = raster.getSample(x, y, 0);
                int g = raster.getSample(x, y, 1);
                int b = raster.getSample(x, y, 2);
                int a = alpha.getSample(x, y, 0);

                // write RGBA values
                out.writeByte(b);
                out.writeByte(g);
                out.writeByte(r);
                out.writeByte(a);
            }
        }
    }

    /**
     * Sets a particular bit in a byte.
     *
     * @param bits  the source byte
     * @param index the index of the bit to set
     * @param bit   the value for the bit, which should be either <tt>0</tt> or <tt>1</tt>.
     * @return the resultant byte
     */
    private static byte setBit(byte bits, int index, int bit) {
        if (bit == 0) {
            bits &= ~(1 << (7 - index));
        } else {
            bits |= 1 << (7 - index);
        }
        return bits;
    }

    /**
     * Sets a particular nibble (4 bits) in a byte.
     *
     * @param nibbles the source byte
     * @param index   the index of the nibble to set
     * @return the value for the nibble, which should be in the range <tt>0x0..0xF</tt>.
     */
    private static byte setNibble(byte nibbles, int index, int nibble) {
        nibbles |= (nibble << ((1 - index) * 4));

        return nibbles;
    }

    /**
     * Calculates the size in bytes for a colour map with the specified bit count.
     *
     * @param sBitCount the bit count, which represents the colour depth
     * @return the size of the colour map, in bytes if <tt>sBitCount</tt> is less than or equal to 8,
     * otherwise <tt>0</tt> as colour maps are only used for bitmaps with a colour depth of 8 bits or less.
     */
    public static int getColorMapSize(short sBitCount) {
        int ret = 0;
        if (sBitCount <= 8) {
            ret = (1 << sBitCount) * 4;
        }
        return ret;
    }

}
