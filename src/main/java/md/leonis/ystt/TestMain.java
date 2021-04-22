package md.leonis.ystt;

import com.google.gson.Gson;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import net.sf.image4j.codec.bmp.BMPDecoder;
import net.sf.image4j.codec.bmp.BMPEncoder;
import net.sf.image4j.codec.bmp.BMPImage;
import net.sf.image4j.codec.bmp.InfoHeader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestMain {

    public static void main(String[] args) throws IOException {

        File file = new File("D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\STUP.bmp");
        
        BMPImage bmpImage = BMPDecoder.readExt(file);
        BufferedImage bufferedImage = BMPDecoder.read(file);
        WritableImage writableImage = BMPDecoder.readWI(file);
        
        BMPEncoder.write(bmpImage, new File("D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\STUPbi.bmp"));

        BMPEncoder.write(bmpImage.getImage(), new File("D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\STUPi.bmp"));
        BMPEncoder.write8bit(bufferedImage, new File("D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\STUPi8.bmp"));
        BMPEncoder.write8bit(writableImage, new File("D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\STUPwi.bmp"));

        //BMPEncoder.write(ih, icm, raster, new File("D:\\Working\\_Yoda\\YExplorer\\out\\output-eng-2\\STUPx.bmp"));

        // {"iSize":40,"iWidth":288,"iHeight":288,"sPlanes":1,"sBitCount":8,"iCompression":0,"iImageSize":82944,
        // "iXpixelsPerM":0,"iYpixelsPerM":0,"iColorsUsed":256,"iColorsImportant":0,"iNumColors":256}
        System.out.println(new Gson().toJson(bmpImage.getInfoHeader()));

        System.out.println();

        /*Log.Clear;
        CreateDir(c);
        TitleImage.Picture.SaveToFile(opath + knownSections[2] + '.bmp');
        Log.Debug('Title screen saved');*/
    }
}
