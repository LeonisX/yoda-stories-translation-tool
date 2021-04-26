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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static md.leonis.ystt.utils.Config.*;

/**
 * Encodes images in BMP format.
 *
 * @author Ian McDonagh
 */
public class BMPWriter {



    /**
     * Creates a new instance of BMPEncoder
     */
    private BMPWriter() {
    }


    // 8 bit only
    public static void write8bit(InfoHeader ih, IndexColorModel icm, byte[] raster, File file) throws IOException {
        FileOutputStream fout = new FileOutputStream(file);
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
    public static void write8bit(Image wi, File file) throws IOException {

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

        FileOutputStream fout = new FileOutputStream(file);
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
    public static void write8bit(Canvas canvas, Path path) throws IOException {
        write8bit(canvas, path.toFile());
    }

    // 8 bit only
    public static void write8bit(Canvas canvas, File file) throws IOException {

        WritableImage wi = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, wi);
        write8bit(wi, file);
    }

    // 8 bit only
    public static void write8bit(ImageView imageView, Path path) throws IOException {
        write8bit(imageView.getImage(), path.toFile());
    }

    // 8 bit only
    public static void write8bit(ImageView imageView, File file) throws IOException {
        write8bit(imageView.getImage(), file);
    }

    // 8 bit only
    public static void write8bit(BufferedImage bi, Path path) throws IOException {
        write8bit(bi, path.toFile());
    }

    // 8 bit only
    public static void write8bit(BufferedImage bi, File file) throws IOException {

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

        FileOutputStream fout = new FileOutputStream(file);
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


    public static void write(BMPImage bmpImage, Path path) throws IOException {
        write(bmpImage, path.toFile());
    }

    public static void write(BMPImage bmpImage, File file) throws IOException {
        FileOutputStream fout = new FileOutputStream(file);
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


    public static void write(BufferedImage img, Path path) throws IOException {
        write(img, path.toFile());
    }
    /**
     * Encodes and writes BMP data the output file
     *
     * @param img  the image to encode
     * @param file the file to which encoded data will be written
     * @throws java.io.IOException if an error occurs
     */
    public static void write(BufferedImage img, File file) throws IOException {
        FileOutputStream fout = new FileOutputStream(file);
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
