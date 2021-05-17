package md.leonis.bin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class DumpTest extends Assert {

    private Dump romData;

    private final int FF = 0xFF;
    private final int OO = 0x00;

    @Test
    public void size() {
        assertEquals(romData.size(), 8188);
    }

    /*@Test
    public void moveTo() throws Exception {
        for (int i = 0; i < SaveState.START_TEXT.length(); i++) {
            romData.moveToAddress(i);
            assertEquals(romData.getByte(), SaveState.START_TEXT.codePointAt(i));
        }
        for (int i = SaveState.START_TEXT.length() - 1; i >= 0 ; i--) {
            romData.moveToAddress(i);
            assertEquals(romData.getByte(), SaveState.START_TEXT.codePointAt(i));
        }
    }

    @Test
    public void readString() throws Exception {
        assertEquals(romData.readString(SaveState.START_TEXT.length()), SaveState.START_TEXT);
    }*/

    @Test
    public void checkZeroes() {
        romData.moveToAddress(0x00);
        assertFalse(romData.checkZeroes(0x10));
        romData.moveToAddress(0x40);
        assertTrue(romData.checkZeroes(0x10));
    }

    @Test
    public void getByte() {
        romData.moveToAddress(0x40);
        assertEquals(romData.getByte(), 0x00);
        romData.moveToAddress(0x100);
        assertEquals(romData.getByte(), 0xF1);
    }

    @Test
    public void getBytePos() {
        assertEquals(romData.getByte(0x40), 0x00);
        assertEquals(romData.getByte(0x100), 0xF1);
    }

    @Test
    public void getShort() {
        romData.moveToAddress(0x5E0);
        assertEquals(romData.getWord(), 0xFFC8);
        romData.moveToAddress(0x500);
        assertEquals(romData.getWord(), 0x0192);
        romData.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        romData.moveToAddress(0x5E0);
        assertEquals(romData.getWord(), 0xC8FF);
        romData.moveToAddress(0x500);
        assertEquals(romData.getWord(), 0x9201);
        romData.setByteOrder(ByteOrder.BIG_ENDIAN);
    }

    @Test
    public void getShortPos() {
        assertEquals(romData.getWord(0x5E0), 0xFFC8);
    }

    @Test
    public void getBoolean() {
        romData.moveToAddress(0x499);
        assertFalse(romData.getBoolean());
        romData.moveToAddress(0x500);
        assertTrue(romData.getBoolean());
    }


    @Test
    public void getArray() {
        int[] dump = romData.getArray(0x100, 5);
        assertEquals(5, dump.length);
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
    public void setByte() {
        int address = 0x1CA0;
        romData.moveToAddress(address);
        romData.setByte(FF);
        romData.moveToAddress(address);
        assertEquals(romData.getByte(), FF);
        romData.moveToAddress(address);
        romData.setByte(OO);
        assertEquals(romData.getByte(address), OO);
    }

    @Test
    public void setByte1() {
        int address = 0x1CA1;
        romData.setByte(address, FF);
        assertEquals(romData.getByte(address), FF);
        romData.setByte(address, OO);
        assertEquals(romData.getByte(address), OO);
    }

    @Test
    public void setShort() {
        int address = 0x220;
        assertEquals(romData.getWord(address), OO);
        romData.moveToAddress(address);
        romData.setWord(0xFF44);
        assertEquals(romData.getWord(address), 0xFF44);
        assertEquals(romData.getByte(address), 0xFF);
        assertEquals(romData.getByte(), 0x44);
        romData.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        romData.moveToAddress(address);
        romData.setWord(0xFF44);
        assertEquals(romData.getWord(address), 0xFF44);
        assertEquals(romData.getByte(address), 0x44);
        assertEquals(romData.getByte(), 0xFF);
        romData.setByteOrder(ByteOrder.BIG_ENDIAN);
        romData.moveToAddress(address);
        romData.setWord(OO);
        assertEquals(romData.getWord(address), OO);
    }

    @Test
    public void setShort1() {
        int address = 0x220;
        assertEquals(romData.getWord(address), OO);
        romData.setWord(address, 0xFF44);
        assertEquals(romData.getWord(address), 0xFF44);
        assertEquals(romData.getByte(address), 0xFF);
        assertEquals(romData.getByte(), 0x44);
        romData.setWord(address, OO);
        assertEquals(romData.getWord(address), OO);
    }

    @Test
    public void setBoolean() {
        int address = 0x222;
        romData.moveToAddress(address);
        romData.setBoolean(true);
        assertTrue(romData.getBoolean(address));
        romData.moveToAddress(address);
        romData.setBoolean(false);
        assertFalse(romData.getBoolean(address));
    }

    @Test
    public void setBoolean1() {
        int address = 0x222;
        romData.setBoolean(address, true);
        assertTrue(romData.getBoolean(address));
        romData.setBoolean(address, false);
        assertFalse(romData.getBoolean(address));
    }

    @Test
    public void writeHexDump() {
        String s1 = "00001111";
        String s2 = "FA44";
        int address = 0x240;
        romData.moveToAddress(address);
        romData.writeHexDump(s1);
        assertEquals(romData.getByte(address),  OO);
        assertEquals(romData.getByte(),  OO);
        assertEquals(romData.getByte(),  0x11);
        assertEquals(romData.getByte(),  0x11);
        romData.moveToAddress(address);
        romData.writeHexDump(s2);
        assertEquals(romData.getByte(address),  0xFA);
        assertEquals(romData.getByte(),  0x44);
        s2 = "FA 44";
        romData.moveToAddress(address);
        romData.writeHexDump(s2);
        assertEquals(romData.getByte(address),  0xFA);
        assertEquals(romData.getByte(),  0x44);
    }

    @Test
    public void erase() {
        String dump = "FFFFFFFF";
        int address = 0x230;
        romData.writeHexDump(address, dump);
        romData.moveToAddress(address + 1);
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
    @SuppressWarnings("all")
    public void saveToFile() throws Exception {
        File file = File.createTempFile("dump", ".tmp");
        romData.saveToFile(file);
        Dump romData2 = new Dump(file);
        assertEquals(romData2, romData);
        file.delete();
    }

    @Test(expected = RuntimeException.class)
    public void findValueAddressByMaskWithException() {
        prepareDump();
        romData.findValueAddressByMask("87654321");
    }

    @Test
    public void findValueAddressByMask1l() {
        prepareDump();
        validate(romData.findValueAddressByMask("??654321"), 0x87, 0);
    }

    @Test
    public void findValueAddressByMask1m() {
        prepareDump();
        validate(romData.findValueAddressByMask("87??4321"), 0x65, 1);
    }

    @Test
    public void findValueAddressByMask1r() {
        prepareDump();
        validate(romData.findValueAddressByMask("876543??"), 0x21, 3);
    }

    @Test
    public void findValueAddressByMask2l() {
        prepareDump();
        validate(romData.findValueAddressByMask("????4321"), 0x8765, 0);
    }

    @Test
    public void findValueAddressByMask2m() {
        prepareDump();
        validate(romData.findValueAddressByMask("87????21"), 0x6543, 1);
    }

    @Test
    public void findValueAddressByMask2r() {
        prepareDump();
        validate(romData.findValueAddressByMask("8765????"), 0x4321, 2);
    }

    @Test
    public void findValueAddressByMask3l() {
        prepareDump();
        Map<Integer, Long> result = romData.findValueAddressByMask("??????21 87654321");
        assertEquals(result.size(), 15);
        assertEquals(0x876543, result.get(0).longValue());
    }

    @Test
    public void findValueAddressByMask3m() {
        prepareDump();
        Map<Integer, Long> result = romData.findValueAddressByMask("8765???? ??654321");
        assertEquals(result.size(), 15);
        assertEquals(0x432187, result.get(2).longValue());
    }

    @Test
    public void findValueAddressByMask3r() {
        prepareDump();
        Map<Integer, Long> result = romData.findValueAddressByMask("87654321 87??????");
        assertEquals(result.size(), 15);
        assertEquals(0x654321, result.get(5).longValue());
    }

    @Test
    public void findValueAddressByMask4l() {
        prepareDump();
        Map<Integer, Long> result = romData.findValueAddressByMask("???????? 87654321");
        assertEquals(result.size(), 15);
        assertEquals(result.get(0).longValue(), 0x87654321);
    }

    @Test
    public void findValueAddressByMask4m() {
        prepareDump();
        Map<Integer, Long> result = romData.findValueAddressByMask("8765???? ????4321");
        assertEquals(result.size(), 15);
        assertEquals(result.get(2).longValue(), 0x43218765);
    }

    @Test
    public void findValueAddressByMask4r() {
        prepareDump();
        Map<Integer, Long> result = romData.findValueAddressByMask("87654321 ????????");
        assertEquals(result.size(), 16);
        assertEquals(result.get(4).longValue(), 0x87654321);
    }

    @Test
    public void findValueAddressByMask5l() {
        prepareDump();
        Map<Integer, Long> result = romData.findValueAddressByMask("???????? ??654321");
        assertEquals(15, result.size());
        assertEquals(0x8765432187L, result.get(0).longValue());
    }

    @Test
    public void findValueAddressByMask5m() {
        prepareDump();
        Map<Integer, Long> result = romData.findValueAddressByMask("8765???? ??????21");
        assertEquals(15, result.size());
        assertEquals(0x4321876543L, result.get(2).longValue());
    }

    @Test
    public void findValueAddressByMask5r() {
        prepareDump();
        Map<Integer, Long> result = romData.findValueAddressByMask("876543?? ????????");
        assertEquals(16, result.size());
        assertEquals(0x2187654321L, result.get(3).longValue());
    }

    private void validate(Map<Integer, Long> result, long value, int offset) {
        assertEquals(result.size(), 16);
        assertEquals(result.get(offset).longValue(), value);
        assertEquals(result.get(offset + 4).longValue(), value);
        assertEquals(result.get(offset + 8).longValue(), value);
        assertEquals(result.get(offset + 12).longValue(), value);
        assertEquals(result.get(offset + 16).longValue(), value);
        assertEquals(result.get(offset + 20).longValue(), value);
        assertEquals(result.get(offset + 24).longValue(), value);
        assertEquals(result.get(offset + 28).longValue(), value);
        assertEquals(result.get(offset + 32).longValue(), value);
    }


    private void prepareDump() {
        romData.setByteOrder(ByteOrder.BIG_ENDIAN);
        romData.setIndex(0);
        romData.writeHexDump("87654321 87654321 87654321 87654321 87654321 87654321 87654321 87654321");
        romData.writeHexDump("87654321 87654321 87654321 87654321 87654321 87654321 87654321 87654321");
    }

}
