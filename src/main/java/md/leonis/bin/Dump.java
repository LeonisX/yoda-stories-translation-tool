package md.leonis.bin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class Dump {

    private byte[] dump;
    //private File file;

    private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN; // natural order, 0xFFAA == 0xFFAA

    private int offset = 0; // Current offset in dump.

    private int index = 0; // Relative address. Full address: offset + index

    //TODO методы для доступа и проверки
    //всякие указатели

    public Dump(int size) {
        dump = new byte[size];
    }

    public Dump(File file) throws IOException {
        //this.file = file;
        dump = Files.readAllBytes(file.toPath());
    }

    public void saveToFile(String file) throws IOException {
        saveToFile(new File(file));
    }

    public void saveToFile(File file) throws IOException {
        Files.write(file.toPath(), dump);
    }

    public int size() {
        return dump.length;
    }

    public void moveToAddress(int address) {
        index = offset + address;
    }

    public void moveTo(int offset) {
        index += offset;
    }

    public String readString(int length) {
        byte[] slice = Arrays.copyOfRange(dump, index, index + length);
        index += length;
        return new String(slice);
    }

    public boolean checkZeroes(int length) {
        byte[] slice = Arrays.copyOfRange(dump, index, index + length);
        int sum = 0;
        for (int i : slice) sum += i;
        index += length;
        return sum == 0;
    }

    public char getChar() {
        return readString(1).charAt(0);
    }

    public int getByte() {
        int result = Byte.toUnsignedInt(dump[index]);
        index++;
        return result;
    }

    public int getByte(int address) {
        moveToAddress(address);
        return getByte();
    }

    // 2 bytes
    public int getWord() {
        int result;
        switch (byteOrder) {
            case LITTLE_ENDIAN:
                result = Byte.toUnsignedInt(dump[index]);
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256;
                index++;
                break;
            default:
                result = Byte.toUnsignedInt(dump[index]) * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]);
                index++;
                break;
        }
        return result;
    }

    // 4 bytes
    public long getLongWord() {
        int result;
        switch (byteOrder) {
            case LITTLE_ENDIAN:
                result = Byte.toUnsignedInt(dump[index]);
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256;
                index++;
                break;
            default:
                result = Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]);
                index++;
                break;
        }
        return result;
    }

    /*public int getInt64() {
        int result;
        switch (byteOrder) {
            case LITTLE_ENDIAN:
                result = Byte.toUnsignedInt(dump[index]);
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256 * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256 * 256 * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256 * 256 * 256 * 256 * 256;
                index++;
                break;
            default:
                result = Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256 * 256 * 256 * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256 * 256 * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256 * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]);
                index++;
                break;
        }
        return result;
    }*/

    public int getWord(int address) {
        moveToAddress(address);
        return getWord();
    }

    public boolean getBoolean() {
        boolean result = dump[index] != 0;
        index++;
        return result;
    }

    public boolean getBoolean(int address) {
        moveToAddress(address);
        return getBoolean();
    }

    //TODO test, byteswap
    public int[] getArray(int address, int length) {
        if (address + length > dump.length) return new int[0];
        int[] result = new int[length];
        moveToAddress(address);
        for (int i = 0; i < length; i++) {
            result[i] = Byte.toUnsignedInt(dump[index]);
            index++;
        }
        return result;
    }

    public void setByte(int address, int value) {
        moveToAddress(address);
        setByte(value);
    }

    public void setByte(int value) {
        //System.out.print(Integer.toHexString(index) + ": ");
        //System.out.println(Integer.toHexString(value));
        dump[index] = (byte) value;
        index++;
    }

    // 2 bytes
    public void setWord(int address, int value) {
        moveToAddress(address);
        setWord(value);
    }

    // 2 bytes
    public void setWord(int value) { // 01 FE -> byte1 = 01; byte2 = FE
        byte byte1 = (byte) (value >>> 8);
        byte byte2 = (byte) (value);
        switch (byteOrder) {
            case BIG_ENDIAN:
                setByte(byte1);
                setByte(byte2);
                break;
            default:
                setByte(byte2);
                setByte(byte1);
                break;
        }
    }

    // 4 bytes
    public void setLongWord(long value) { // 01 FE -> byte1 = 01; byte2 = FE
        byte byte1 = (byte) (value >>> 8 >>> 8 >>> 8);
        byte byte2 = (byte) (value >>> 8 >>> 8);
        byte byte3 = (byte) (value >>> 8);
        byte byte4 = (byte) (value);
        switch (byteOrder) {
            case BIG_ENDIAN:
                setByte(byte1);
                setByte(byte2);
                setByte(byte3);
                setByte(byte4);
                break;
            default:
                setByte(byte4);
                setByte(byte3);
                setByte(byte2);
                setByte(byte1);
                break;
        }
    }

    /*public void setInt64(long value) { // 01 FE -> byte1 = 01; byte2 = FE
        byte byte1 = (byte) (value >>> 8 >>> 8 >>> 8 >>> 8 >>> 8 >>> 8 >>> 8);
        byte byte2 = (byte) (value >>> 8 >>> 8 >>> 8 >>> 8 >>> 8 >>> 8);
        byte byte3 = (byte) (value >>> 8 >>> 8 >>> 8 >>> 8 >>> 8);
        byte byte4 = (byte) (value >>> 8 >>> 8 >>> 8 >>> 8);
        byte byte5 = (byte) (value >>> 8 >>> 8 >>> 8);
        byte byte6 = (byte) (value >>> 8 >>> 8);
        byte byte7 = (byte) (value >>> 8);
        byte byte8 = (byte) (value);
        switch (byteOrder) {
            case BIG_ENDIAN:
                setByte(byte1);
                setByte(byte2);
                setByte(byte3);
                setByte(byte4);
                setByte(byte5);
                setByte(byte6);
                setByte(byte7);
                setByte(byte8);
                break;
            default:
                setByte(byte8);
                setByte(byte7);
                setByte(byte6);
                setByte(byte5);
                setByte(byte4);
                setByte(byte3);
                setByte(byte2);
                setByte(byte1);
                break;
        }
    }*/

    public void setString(String value) {
        byte[] bytes = value.getBytes();
        for (byte aByte : bytes) {
            dump[index] = aByte;
            index++;
        }
    }

    public void setBoolean(int address, boolean value) {
        moveToAddress(address);
        setBoolean(value);
    }

    public void setBoolean(boolean value) {
        //System.out.print(Integer.toHexString(index) + ": ");
        //System.out.println(Integer.toHexString(value));
        dump[index] = value ? (byte) 1 : 0;
        index++;
    }


    //TODO byteorder, test
    public void writeHexDump(int address, String data) {
        moveToAddress(address);
        writeHexDump(data);
    }

    //TODO byteorder, test
    public void writeHexDump(String data) {
        data = data.replace(" ", "");
        if (data.length() % 2 != 0) throw new RuntimeException("Bad HexDump: " + data);
        for (int i = 0; i < data.length() / 2; i++) {
            setByte(Integer.parseInt(data.substring(i * 2, i * 2 + 2) + "", 16));
        }
    }


    public void erase(int address, int size) {
        moveToAddress(address);
        erase(size);
    }

    public void erase(int size) {
        for (int i = 0; i < size; i++) {
            setByte(0x00);
        }
    }


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


    public byte[] getDump() {
        return dump;
    }

    public void setDump(byte[] dump) {
        this.dump = dump;
    }

    public ByteOrder getByteOrder() {
        return byteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dump dump = (Dump) o;
        return Arrays.equals(this.dump, dump.dump);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(dump);
    }

    public void insertEmptyArea(int length) {
        byte[] newDump = new byte[dump.length + length];
        System.arraycopy(dump, 0, newDump, 0, length);
        System.arraycopy(dump, index + 1, newDump, dump.length, length);
        dump = newDump;
    }

    public void deleteArea(int length) {
        byte[] newDump = new byte[dump.length - length];
        System.arraycopy(dump, 0, newDump, 0, length);
        System.arraycopy(dump, index + 1 + length, newDump, dump.length, length);
        dump = newDump;
    }
}
