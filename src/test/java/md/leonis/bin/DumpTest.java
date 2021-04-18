package md.leonis.bin;

import md.leonis.ystt.oldmodel.SaveState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class DumpTest extends Assert {

    private Dump romData;

    private int FF = 0xFF;
    private int OO = 0x00;

    @Test
    public void size() throws Exception {
        assertEquals(romData.size(), 8188);
    }

    @Test
    public void moveTo() throws Exception {
        for (int i = 0; i < SaveState.START_TEXT.length(); i++) {
            romData.moveTo(i);
            assertEquals(romData.getByte(), SaveState.START_TEXT.codePointAt(i));
        }
        for (int i = SaveState.START_TEXT.length() - 1; i >= 0 ; i--) {
            romData.moveTo(i);
            assertEquals(romData.getByte(), SaveState.START_TEXT.codePointAt(i));
        }
    }

    @Test
    public void readString() throws Exception {
        assertEquals(romData.readString(SaveState.START_TEXT.length()), SaveState.START_TEXT);
    }

    @Test
    public void checkZeroes() throws Exception {
        romData.moveTo(0x00);
        assertFalse(romData.checkZeroes(0x10));
        romData.moveTo(0x40);
        assertTrue(romData.checkZeroes(0x10));
    }

    @Test
    public void getByte() throws Exception {
        romData.moveTo(0x40);
        assertEquals(romData.getByte(), 0x00);
        romData.moveTo(0x100);
        assertEquals(romData.getByte(), 0xF1);
    }

    @Test
    public void getBytePos() throws Exception {
        assertEquals(romData.getByte(0x40), 0x00);
        assertEquals(romData.getByte(0x100), 0xF1);
    }

    @Test
    public void getShort() throws Exception {
        romData.moveTo(0x5E0);
        assertEquals(romData.getShort(), 0xFFC8);
        romData.moveTo(0x500);
        assertEquals(romData.getShort(), 0x0192);
        romData.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        romData.moveTo(0x5E0);
        assertEquals(romData.getShort(), 0xC8FF);
        romData.moveTo(0x500);
        assertEquals(romData.getShort(), 0x9201);
        romData.setByteOrder(ByteOrder.BIG_ENDIAN);
    }

    @Test
    public void getShortPos() throws Exception {
        assertEquals(romData.getShort(0x5E0), 0xFFC8);
    }

    @Test
    public void getBoolean() throws Exception {
        romData.moveTo(0x499);
        assertFalse(romData.getBoolean());
        romData.moveTo(0x500);
        assertTrue(romData.getBoolean());
    }


    @Test
    public void getArray() throws Exception {
        int[] dump = romData.getArray(0x100, 5);
        assertTrue(dump.length == 5);
        assertEquals(dump[0], 0xF1);
        assertEquals(dump[1], 0x11);
        assertEquals(dump[2], 0xF2);
        assertEquals(dump[3], 0x11);
        assertEquals(dump[4], 0xF2);
        dump = romData.getArray(0x100, 0);
        assertEquals(dump.length, 0);
        dump = romData.getArray(0x2, 1);
        assertEquals(dump.length, 1);
        assertEquals(dump[0], 0x41);
        dump = romData.getArray(0x1FFA, 2);
        assertEquals(dump.length, 2);
        dump = romData.getArray(0x1FFA, 3);
        assertEquals(dump.length, 0);
    }

    @Test
    public void setByte() throws Exception {
        int address = 0x1CA0;
        romData.moveTo(address);
        romData.setByte(FF);
        romData.moveTo(address);
        assertEquals(romData.getByte(), FF);
        romData.moveTo(address);
        romData.setByte(OO);
        assertEquals(romData.getByte(address), OO);
    }

    @Test
    public void setByte1() throws Exception {
        int address = 0x1CA1;
        romData.setByte(address, FF);
        assertEquals(romData.getByte(address), FF);
        romData.setByte(address, OO);
        assertEquals(romData.getByte(address), OO);
    }

    @Test
    public void setShort() throws Exception {
        int address = 0x220;
        assertEquals(romData.getShort(address), OO);
        romData.moveTo(address);
        romData.setShort(0xFF44);
        assertEquals(romData.getShort(address), 0xFF44);
        assertEquals(romData.getByte(address), 0xFF);
        assertEquals(romData.getByte(), 0x44);
        romData.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        romData.moveTo(address);
        romData.setShort(0xFF44);
        assertEquals(romData.getShort(address), 0xFF44);
        assertEquals(romData.getByte(address), 0x44);
        assertEquals(romData.getByte(), 0xFF);
        romData.setByteOrder(ByteOrder.BIG_ENDIAN);
        romData.moveTo(address);
        romData.setShort(OO);
        assertEquals(romData.getShort(address), OO);
    }

    @Test
    public void setShort1() throws Exception {
        int address = 0x220;
        assertEquals(romData.getShort(address), OO);
        romData.setShort(address, 0xFF44);
        assertEquals(romData.getShort(address), 0xFF44);
        assertEquals(romData.getByte(address), 0xFF);
        assertEquals(romData.getByte(), 0x44);
        romData.setShort(address, OO);
        assertEquals(romData.getShort(address), OO);
    }

    @Test
    public void setBoolean() throws Exception {
        int address = 0x222;
        romData.moveTo(address);
        romData.setBoolean(true);
        assertTrue(romData.getBoolean(address));
        romData.moveTo(address);
        romData.setBoolean(false);
        assertFalse(romData.getBoolean(address));
    }

    @Test
    public void setBoolean1() throws Exception {
        int address = 0x222;
        romData.setBoolean(address, true);
        assertTrue(romData.getBoolean(address));
        romData.setBoolean(address, false);
        assertFalse(romData.getBoolean(address));
    }

    @Test
    public void writeHexDump() throws Exception {
        String s1 = "00001111";
        String s2 = "FA44";
        int address = 0x240;
        romData.moveTo(address);
        romData.writeHexDump(s1);
        assertEquals(romData.getByte(address),  OO);
        assertEquals(romData.getByte(),  OO);
        assertEquals(romData.getByte(),  0x11);
        assertEquals(romData.getByte(),  0x11);
        romData.moveTo(address);
        romData.writeHexDump(s2);
        assertEquals(romData.getByte(address),  0xFA);
        assertEquals(romData.getByte(),  0x44);
        s2 = "FA 44";
        romData.moveTo(address);
        romData.writeHexDump(s2);
        assertEquals(romData.getByte(address),  0xFA);
        assertEquals(romData.getByte(),  0x44);
    }

    @Test
    public void erase() throws Exception {
        String dump = "FFFFFFFF";
        int address = 0x230;
        romData.writeHexDump(address, dump);
        romData.moveTo(address + 1);
        romData.erase(2);
        assertEquals(romData.getByte(address),  FF);
        assertEquals(romData.getByte(),  OO);
        assertEquals(romData.getByte(),  OO);
        assertEquals(romData.getByte(),  FF);
        romData.writeHexDump(address, dump);
        romData.erase(address + 1, 2);
        assertEquals(romData.getByte(address),  FF);
        assertEquals(romData.getByte(),  OO);
        assertEquals(romData.getByte(),  OO);
        assertEquals(romData.getByte(),  FF);
    }

    @Before
    public void init() throws URISyntaxException, IOException {
        romData = new Dump(new File(Dump.class.getResource("/ps.ssm").toURI()));
    }

    @Test
    public void saveToFile() throws Exception {
        File file = File.createTempFile("dump", ".tmp");
        romData.saveToFile(file);
        Dump romData2 = new Dump(file);
        assertEquals(romData2, romData);
        file.delete();
    }

}
