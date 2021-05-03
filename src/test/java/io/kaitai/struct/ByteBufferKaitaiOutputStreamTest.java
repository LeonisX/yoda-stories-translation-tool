package io.kaitai.struct;

import junit.framework.TestCase;
import md.leonis.ystt.model.yodesk.Yodesk;

public class ByteBufferKaitaiOutputStreamTest extends TestCase {

    //TODO constructors

    //TODO
    public void testClose() {
    }

    //TODO
    public void testIsEof() {
    }

    //TODO
    public void testSeek() {
    }

    //TODO
    public void testTestSeek() {
    }

    public void testPos() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[2]);
        bbkos.writeS1((byte) 0x01);
        bbkos.writeS1((byte) 0xFF);
        assertEquals(2, bbkos.pos());
    }

    public void testSize() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[2]);
        assertEquals(2, bbkos.size());
    }

    public void testWriteS1() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[2]);
        bbkos.writeS1((byte) 0x01);
        bbkos.writeS1((byte) 0xFF);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0xFF, bbkis.readS1());
    }

    public void testWriteS2be() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[4]);
        bbkos.writeS2be((short) 0x0001);
        bbkos.writeS2be((short) 0xEEEF);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals((short) 0x0001, bbkis.readS2be());
        assertEquals((short) 0xEEEF, bbkis.readS2be());

        bbkis.seek(0);
        assertEquals((byte) 0x00, bbkis.readS1());
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0xEE, bbkis.readS1());
        assertEquals((byte) 0xEF, bbkis.readS1());
    }

    public void testWriteS4be() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[8]);
        bbkos.writeS4be(0x01234567);
        bbkos.writeS4be(0xFEDCBA98);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x01234567, bbkis.readS4be());
        assertEquals(0xFEDCBA98, bbkis.readS4be());

        bbkis.seek(0);
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x23, bbkis.readS1());
        assertEquals((byte) 0x45, bbkis.readS1());
        assertEquals((byte) 0x67, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
        assertEquals((byte) 0xDC, bbkis.readS1());
        assertEquals((byte) 0xBA, bbkis.readS1());
        assertEquals((byte) 0x98, bbkis.readS1());
    }

    public void testWriteS8be() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[16]);
        bbkos.writeS8be(0x01234567_89ABCDEFL);
        bbkos.writeS8be(0xFEDCBA98_76543210L);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x01234567_89ABCDEFL, bbkis.readS8be());
        assertEquals(0xFEDCBA98_76543210L, bbkis.readS8be());

        bbkis.seek(0);
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x23, bbkis.readS1());
        assertEquals((byte) 0x45, bbkis.readS1());
        assertEquals((byte) 0x67, bbkis.readS1());
        assertEquals((byte) 0x89, bbkis.readS1());
        assertEquals((byte) 0xAB, bbkis.readS1());
        assertEquals((byte) 0xCD, bbkis.readS1());
        assertEquals((byte) 0xEF, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
        assertEquals((byte) 0xDC, bbkis.readS1());
        assertEquals((byte) 0xBA, bbkis.readS1());
        assertEquals((byte) 0x98, bbkis.readS1());
        assertEquals((byte) 0x76, bbkis.readS1());
        assertEquals((byte) 0x54, bbkis.readS1());
        assertEquals((byte) 0x32, bbkis.readS1());
        assertEquals((byte) 0x10, bbkis.readS1());
    }

    public void testWriteS2le() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[4]);
        bbkos.writeS2le((short) 0x0001);
        bbkos.writeS2le((short) 0xEEEF);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals((short) 0x0001, bbkis.readS2le());
        assertEquals((short) 0xEEEF, bbkis.readS2le());

        bbkis.seek(0);
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x00, bbkis.readS1());
        assertEquals((byte) 0xEF, bbkis.readS1());
        assertEquals((byte) 0xEE, bbkis.readS1());
    }

    public void testWriteS4le() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[8]);
        bbkos.writeS4le(0x01234567);
        bbkos.writeS4le(0xFEDCBA98);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x01234567, bbkis.readS4le());
        assertEquals(0xFEDCBA98, bbkis.readS4le());

        bbkis.seek(0);
        assertEquals((byte) 0x67, bbkis.readS1());
        assertEquals((byte) 0x45, bbkis.readS1());
        assertEquals((byte) 0x23, bbkis.readS1());
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x98, bbkis.readS1());
        assertEquals((byte) 0xBA, bbkis.readS1());
        assertEquals((byte) 0xDC, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
    }

    public void testWriteS8le() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[16]);
        bbkos.writeS8le(0x01234567_89ABCDEFL);
        bbkos.writeS8le(0xFEDCBA98_76543210L);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x01234567_89ABCDEFL, bbkis.readS8le());
        assertEquals(0xFEDCBA98_76543210L, bbkis.readS8le());

        bbkis.seek(0);

        assertEquals((byte) 0xEF, bbkis.readS1());
        assertEquals((byte) 0xCD, bbkis.readS1());
        assertEquals((byte) 0xAB, bbkis.readS1());
        assertEquals((byte) 0x89, bbkis.readS1());
        assertEquals((byte) 0x67, bbkis.readS1());
        assertEquals((byte) 0x45, bbkis.readS1());
        assertEquals((byte) 0x23, bbkis.readS1());
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x10, bbkis.readS1());
        assertEquals((byte) 0x32, bbkis.readS1());
        assertEquals((byte) 0x54, bbkis.readS1());
        assertEquals((byte) 0x76, bbkis.readS1());
        assertEquals((byte) 0x98, bbkis.readS1());
        assertEquals((byte) 0xBA, bbkis.readS1());
        assertEquals((byte) 0xDC, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
    }

    public void testWriteU1() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[2]);
        bbkos.writeU1((byte) 0x01);
        bbkos.writeU1((byte) 0xFF);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x01, bbkis.readU1());
        assertEquals(0xFF, bbkis.readU1());
    }

    public void testWriteU2be() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[4]);
        bbkos.writeU2be((short) 0x0001);
        bbkos.writeU2be((short) 0xEEEF);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x0001, bbkis.readU2be());
        assertEquals(0xEEEF, bbkis.readU2be());

        bbkis.seek(0);
        assertEquals((short) 0x0001, (short) bbkis.readU2be());
        assertEquals((short) 0xEEEF, (short) bbkis.readU2be());

        bbkis.seek(0);
        assertEquals((byte) 0x00, bbkis.readS1());
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0xEE, bbkis.readS1());
        assertEquals((byte) 0xEF, bbkis.readS1());
    }

    public void testWriteU4be() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[8]);
        bbkos.writeU4be(0x01234567);
        bbkos.writeU4be(0xFEDCBA98);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x01234567L, bbkis.readU4be());
        assertEquals(0xFEDCBA98L, bbkis.readU4be());
        bbkis.seek(0);
        assertEquals(0x01234567, (int) bbkis.readU4be());
        assertEquals(0xFEDCBA98, (int) bbkis.readU4be());

        bbkis.seek(0);
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x23, bbkis.readS1());
        assertEquals((byte) 0x45, bbkis.readS1());
        assertEquals((byte) 0x67, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
        assertEquals((byte) 0xDC, bbkis.readS1());
        assertEquals((byte) 0xBA, bbkis.readS1());
        assertEquals((byte) 0x98, bbkis.readS1());
    }

    public void testWriteU8be() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[16]);
        bbkos.writeU8be(0x01234567_89ABCDEFL);
        bbkos.writeU8be(0xFEDCBA98_76543210L);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x01234567_89ABCDEFL, bbkis.readU8be());
        assertEquals(0xFEDCBA98_76543210L, bbkis.readU8be());

        bbkis.seek(0);
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x23, bbkis.readS1());
        assertEquals((byte) 0x45, bbkis.readS1());
        assertEquals((byte) 0x67, bbkis.readS1());
        assertEquals((byte) 0x89, bbkis.readS1());
        assertEquals((byte) 0xAB, bbkis.readS1());
        assertEquals((byte) 0xCD, bbkis.readS1());
        assertEquals((byte) 0xEF, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
        assertEquals((byte) 0xDC, bbkis.readS1());
        assertEquals((byte) 0xBA, bbkis.readS1());
        assertEquals((byte) 0x98, bbkis.readS1());
        assertEquals((byte) 0x76, bbkis.readS1());
        assertEquals((byte) 0x54, bbkis.readS1());
        assertEquals((byte) 0x32, bbkis.readS1());
        assertEquals((byte) 0x10, bbkis.readS1());
    }

    public void testWriteU2le() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[4]);
        bbkos.writeU2le((short) 0x0001);
        bbkos.writeU2le((short) 0xEEEF);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x0001, bbkis.readU2le());
        assertEquals(0xEEEF, bbkis.readU2le());

        bbkis.seek(0);
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x00, bbkis.readS1());
        assertEquals((byte) 0xEF, bbkis.readS1());
        assertEquals((byte) 0xEE, bbkis.readS1());
    }

    public void testWriteU2leInt() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[4]);
        bbkos.writeU2le(0x0001);
        bbkos.writeU2le(0xEEEF);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x0001, bbkis.readU2le());
        assertEquals(0xEEEF, bbkis.readU2le());

        bbkis.seek(0);
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x00, bbkis.readS1());
        assertEquals((byte) 0xEF, bbkis.readS1());
        assertEquals((byte) 0xEE, bbkis.readS1());
    }

    public void testWriteU2leLong() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[4]);
        bbkos.writeU2le(0x0001L);
        bbkos.writeU2le(0xEEEFL);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x0001, bbkis.readU2le());
        assertEquals(0xEEEF, bbkis.readU2le());

        bbkis.seek(0);
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x00, bbkis.readS1());
        assertEquals((byte) 0xEF, bbkis.readS1());
        assertEquals((byte) 0xEE, bbkis.readS1());
    }

    public void testWriteU4le() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[8]);
        bbkos.writeU4le(0x01234567);
        bbkos.writeU4le(0xFEDCBA98);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x01234567L, bbkis.readU4le());
        assertEquals(0xFEDCBA98L, bbkis.readU4le());

        bbkis.seek(0);
        assertEquals((byte) 0x67, bbkis.readS1());
        assertEquals((byte) 0x45, bbkis.readS1());
        assertEquals((byte) 0x23, bbkis.readS1());
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x98, bbkis.readS1());
        assertEquals((byte) 0xBA, bbkis.readS1());
        assertEquals((byte) 0xDC, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
    }

    public void testWriteU4leLong() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[8]);
        bbkos.writeU4le(0x01234567L);
        bbkos.writeU4le(0xFEDCBA98L);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x01234567L, bbkis.readU4le());
        assertEquals(0xFEDCBA98L, bbkis.readU4le());

        bbkis.seek(0);
        assertEquals((byte) 0x67, bbkis.readS1());
        assertEquals((byte) 0x45, bbkis.readS1());
        assertEquals((byte) 0x23, bbkis.readS1());
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x98, bbkis.readS1());
        assertEquals((byte) 0xBA, bbkis.readS1());
        assertEquals((byte) 0xDC, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
    }

    public void testWriteU8le() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[16]);
        bbkos.writeU8le(0x01234567_89ABCDEFL);
        bbkos.writeU8le(0xFEDCBA98_76543210L);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x01234567_89ABCDEFL, bbkis.readU8le());
        assertEquals(0xFEDCBA98_76543210L, bbkis.readU8le());

        bbkis.seek(0);

        assertEquals((byte) 0xEF, bbkis.readS1());
        assertEquals((byte) 0xCD, bbkis.readS1());
        assertEquals((byte) 0xAB, bbkis.readS1());
        assertEquals((byte) 0x89, bbkis.readS1());
        assertEquals((byte) 0x67, bbkis.readS1());
        assertEquals((byte) 0x45, bbkis.readS1());
        assertEquals((byte) 0x23, bbkis.readS1());
        assertEquals((byte) 0x01, bbkis.readS1());
        assertEquals((byte) 0x10, bbkis.readS1());
        assertEquals((byte) 0x32, bbkis.readS1());
        assertEquals((byte) 0x54, bbkis.readS1());
        assertEquals((byte) 0x76, bbkis.readS1());
        assertEquals((byte) 0x98, bbkis.readS1());
        assertEquals((byte) 0xBA, bbkis.readS1());
        assertEquals((byte) 0xDC, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
    }

    //TODO
    public void testWriteF4be() {
    }

    //TODO
    public void testWriteF8be() {
    }

    //TODO
    public void testWriteF4le() {
    }

    //TODO
    public void testWriteF8le() {
    }

    public void testWriteBytesFullLength() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[8]);
        bbkos.writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, 8);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals(0x00, bbkis.readS1());
        assertEquals(0x01, bbkis.readS1());
        assertEquals(0x02, bbkis.readS1());
        assertEquals(0x03, bbkis.readS1());
        assertEquals(0x04, bbkis.readS1());
        assertEquals(0x05, bbkis.readS1());
        assertEquals(0x06, bbkis.readS1());
        assertEquals(0x07, bbkis.readS1());
    }

    public void testWriteBytesPartial() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[8]);
        bbkos.writeBytes(new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xFD, (byte) 0xFC, (byte) 0xFB, (byte) 0xFA, (byte) 0xF9}, 4);
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals((byte) 0xFF, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
        assertEquals((byte) 0xFD, bbkis.readS1());
        assertEquals((byte) 0xFC, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
    }

    public void testWriteBytesFullPartial() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[8]);
        bbkos.writeBytesFull(new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xFD, (byte) 0xFC, (byte) 0xFB, (byte) 0xFA, (byte) 0xF9});
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals((byte) 0xFF, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
        assertEquals((byte) 0xFD, bbkis.readS1());
        assertEquals((byte) 0xFC, bbkis.readS1());
        assertEquals((byte) 0xFB, bbkis.readS1());
        assertEquals((byte) 0xFA, bbkis.readS1());
        assertEquals((byte) 0xF9, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
    }

    public void testWriteBytesFull() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[8]);
        bbkos.writeBytesFull(new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xFD, (byte) 0xFC, (byte) 0xFB, (byte) 0xFA, (byte) 0xF9, (byte) 0xF8});
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals((byte) 0xFF, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
        assertEquals((byte) 0xFD, bbkis.readS1());
        assertEquals((byte) 0xFC, bbkis.readS1());
        assertEquals((byte) 0xFB, bbkis.readS1());
        assertEquals((byte) 0xFA, bbkis.readS1());
        assertEquals((byte) 0xF9, bbkis.readS1());
        assertEquals((byte) 0xF8, bbkis.readS1());
    }

    public void testWriteBytesTermEosError() {

        try {
            ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[5]);
            bbkos.writeBytesTerm(new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xFD, (byte) 0xFC}, 0, false, false, true);
        } catch (Exception e) {
            assertEquals("End of stream reached, but no terminator 0 found", e.getMessage());
        }
    }

    public void testWriteBytesTermEosReturn() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[5]);
        bbkos.writeBytesTerm(new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xFD, (byte) 0xFC}, 0, false, false, false);
        assertEquals(4, bbkos.pos());
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals((byte) 0xFF, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
        assertEquals((byte) 0xFD, bbkis.readS1());
        assertEquals((byte) 0xFC, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
    }

    public void testWriteBytesTerm() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[5]);
        bbkos.writeBytesTerm(new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xFD, (byte) 0xFC}, (byte) 0xFE, false, false, false);
        assertEquals(0, bbkos.pos());
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals((byte) 0xFF, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
    }

    public void testWriteBytesTermIncludeTerm() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[5]);
        bbkos.writeBytesTerm(new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xFD, (byte) 0xFC}, (byte) 0xFE, true, false, false);
        assertEquals(1, bbkos.pos());
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals((byte) 0xFF, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
    }

    public void testWriteBytesTermConsumeTerm() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[5]);
        bbkos.writeBytesTerm(new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xFD, (byte) 0xFC}, (byte) 0xFE, false, true, false);
        assertEquals(1, bbkos.pos());
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals((byte) 0xFF, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
    }

    public void testWriteBytesTermIncludeConsumeTerm() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[5]);
        bbkos.writeBytesTerm(new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xFD, (byte) 0xFC}, (byte) 0xFE, true, true, false);
        assertEquals(2, bbkos.pos());
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals((byte) 0xFF, bbkis.readS1());
        assertEquals((byte) 0xFE, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
    }

    //TODo test bit operations

    public void testWriteString() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[5]);
        bbkos.writeString("TEST");
        assertEquals(4, bbkos.pos());
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals("TEST", bbkis.readString(4));
        assertEquals(0, bbkis.readS1());
    }

    public void testWriteStringRusWrongCharset() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[5]);
        bbkos.writeString("ТЕСТ");
        assertEquals(4, bbkos.pos());
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals("????", bbkis.readString(4));
        assertEquals(0, bbkis.readS1());
    }

    public void testWriteStringRus() {

        String charset = Yodesk.getCharset();

        Yodesk.setCharset("Cp1251");

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[5]);
        bbkos.writeString("ТЕСТ");

        assertEquals(4, bbkos.pos());
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals("ТЕСТ", bbkis.readString(4));
        assertEquals(0, bbkis.readS1());

        Yodesk.setCharset(charset);
    }

    public void testReadNullTerminatedString() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[6]);
        bbkos.writeString("TEST\u00002");
        assertEquals(6, bbkos.pos());
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals("TEST", bbkis.readNullTerminatedString(6));
    }

    public void testWriteNullTerminatedString() {

        ByteBufferKaitaiOutputStream bbkos = new ByteBufferKaitaiOutputStream(new byte[6]);
        bbkos.writeNullTerminatedString("TEST");
        assertEquals(5, bbkos.pos());
        bbkos.seek(0);

        ByteBufferKaitaiInputStream bbkis = new ByteBufferKaitaiInputStream(bbkos.getBb());

        assertEquals("TEST", bbkis.readString(4));
        assertEquals(0, bbkis.readS1());
        assertEquals(0, bbkis.readS1());
    }
}