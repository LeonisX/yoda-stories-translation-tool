package md.leonis.bin;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Dump {

    private byte[] dump;
    //private File file;

    private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN; // natural order, 0xFFAA == 0xFFAA

    private int offset = 0; // Current offset in dump.

    private int index = 0; // Relative address. Full address: offset + index

    private String charset;

    //TODO методы для доступа и проверки
    //всякие указатели

    public Dump(int size) {
        dump = new byte[size];
    }

    public Dump(File file) throws IOException {
        //this.file = file;
        this(file.toPath());
    }

    public Dump(Path path) throws IOException {
        dump = Files.readAllBytes(path);
    }

    public void saveToFile(String file) throws IOException {
        saveToFile(Paths.get(file));
    }

    public void saveToFile(File file) throws IOException {
        saveToFile(file.toPath());
    }

    public void saveToFile(Path path) throws IOException {
        Files.write(path, dump);
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
        return new String(slice, Charset.forName(charset));
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

    // 2 bytes
    public int getWord3() {
        int result;
        switch (byteOrder) {
            case LITTLE_ENDIAN:
                result = Byte.toUnsignedInt(dump[index]);
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256;
                index++;
                break;
            default:
                result = Byte.toUnsignedInt(dump[index]) * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]);
                index++;
                break;
        }
        return result;
    }

    // 4 bytes
    public long getLongWord() {
        long result;
        switch (byteOrder) {
            case LITTLE_ENDIAN:
                result = Byte.toUnsignedInt(dump[index]);
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256L;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256L;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256L;
                index++;
                break;
            default:
                result = Byte.toUnsignedInt(dump[index]) * 256 * 256 * 256L;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256 * 256L;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256L;
                index++;
                result += Byte.toUnsignedInt(dump[index]);
                index++;
                break;
        }
        return result;
    }

    // 5 bytes
    public long getLongWord5() {
        long result;
        switch (byteOrder) {
            case LITTLE_ENDIAN:
                result = Byte.toUnsignedInt(dump[index]);
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256L;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256L * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256L * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256L * 256 * 256 * 256;
                index++;
                break;
            default:
                result = Byte.toUnsignedInt(dump[index]) * 256L * 256 * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256L * 256 * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256L * 256;
                index++;
                result += Byte.toUnsignedInt(dump[index]) * 256L;
                index++;
                result += Byte.toUnsignedInt(dump[index]);
                index++;
                break;
        }
        return result;
    }

    public String getLongWordS() {
        BigInteger result = BigInteger.valueOf(0L);
        switch (byteOrder) {
            case LITTLE_ENDIAN:
                result = result.add(BigInteger.valueOf(Byte.toUnsignedInt(dump[index])));
                index++;
                result = result.add(BigInteger.valueOf(Byte.toUnsignedInt(dump[index]) * 256L));
                index++;
                result = result.add(BigInteger.valueOf(Byte.toUnsignedInt(dump[index]) * 256 * 256L));
                index++;
                BigInteger v = BigInteger.valueOf(Byte.toUnsignedInt(dump[index]) * 256 * 256L);
                result = result.add(v.multiply(BigInteger.valueOf(256)));
                index++;
                break;
            default:
                BigInteger w = BigInteger.valueOf(Byte.toUnsignedInt(dump[index]) * 256 * 256L);
                result = w.multiply(BigInteger.valueOf(256));
                index++;
                result = result.add(BigInteger.valueOf(Byte.toUnsignedInt(dump[index]) * 256 * 256L));
                index++;
                result = result.add(BigInteger.valueOf(Byte.toUnsignedInt(dump[index]) * 256L));
                index++;
                result = result.add(BigInteger.valueOf(Byte.toUnsignedInt(dump[index])));
                index++;
                break;
        }
        return result.toString();
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
        dump[index] = value ? (byte) 1 : 0;
        index++;
    }

    public void writeHexDump(int address, String data) {
        moveToAddress(address);
        writeHexDump(data);
    }

    public void writeHexDump(String data) {
        data = data.replace(" ", "");
        if (data.length() % 2 != 0) {
            throw new RuntimeException("Bad HexDump: " + data);
        }

        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (int i = 0; i < data.length() / 2; i++) {
                setByte(Integer.parseInt(data.substring(i * 2, i * 2 + 2), 16));
            }
        } else { // Little Endian
            for (int i = data.length() / 2 - 1; i >= 0; i--) {
                setByte(Integer.parseInt(data.substring(i * 2, i * 2 + 2), 16));
            }
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

    public int findAddress(int[] sample) {
        boolean done;
        int startIndex = -1;

        for (int i = 0; i < dump.length - sample.length; i++) {
            done = true;
            for (int j = 0; j < sample.length; j++) {
                if (!(Byte.toUnsignedInt(dump[i + j]) == sample[j])) {
                    done = false;
                    break;
                }
            }
            if (done) {
                startIndex = i;
                break;
            }
        }

        return startIndex;
    }

    // "??" as mask
    public Map<Integer, Long> findValueAddressByMask(String data) {
        data = data.replace(" ", "");
        if (data.length() % 2 != 0) {
            throw new RuntimeException("Bad HexDump: " + data);
        }
        int[] sample = new int[data.length() / 2];
        for (int i = 0; i < sample.length; i++) {
            try {
                sample[i] = Integer.parseInt(data.substring(i * 2, i * 2 + 2), 16);
            } catch (Exception e) {
                sample[i] = Integer.MAX_VALUE;
            }
        }

        int firstIndex = getFirstIndex(sample);

        if (firstIndex == -1) {
            throw new RuntimeException("Please, use another method to search w/o mask");
        }

        Map<Integer, Long> result = new LinkedHashMap<>();

        int startIndex = 0;
        int index;

        do {
            index = findAddressWithMask(startIndex, sample);

            if (index == -1) {
                break;
            }

            result.put(index + firstIndex, getMaskValue(index, sample));

            startIndex = index + 1;

        } while (true);

        return result;
    }

    private long getMaskValue(int index, int[] sample) {

        int firstIndex = getFirstIndex(sample);
        int lastIndex = getLastIndex(sample);
        int maskLength = lastIndex - firstIndex + 1;

        setIndex(index + firstIndex);

        switch (maskLength) {
            case 1:
                return getByte();
            case 2:
                return getWord();
            case 3:
                return getWord3();
            case 4:
                return getLongWord();
            case 5:
                return getLongWord5();
            default:
                throw new RuntimeException("Bad or not supported mask length: " + maskLength);
        }
    }

    private int getFirstIndex(int[] sample) {

        for (int i = 0; i < sample.length; i++) {
            if (sample[i] > 255) {
                return i;
            }
        }
        return -1;
    }

    private int getLastIndex(int[] sample) {

        for (int i = sample.length - 1; i >= 0; i--) {
            if (sample[i] > 255) {
                return i;
            }
        }
        return -1;
    }

    public int findAddressWithMask(int[] sample) {
        return findAddressWithMask(-1, sample);
    }

    public int findAddressWithMask(int startIndex, int[] sample) {

        boolean done;

        for (int i = startIndex; i < dump.length - sample.length; i++) {
            done = true;
            for (int j = 0; j < sample.length; j++) {
                if (sample[j] <= 255 && !(Byte.toUnsignedInt(dump[i + j]) == sample[j])) {
                    done = false;
                    break;
                }
            }
            if (done) {
                return i;
            }
        }

        return -1;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
