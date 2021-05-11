package io.kaitai.struct;

import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * KaitaiStream provides implementation of
 * <a href="https://github.com/kaitai-io/kaitai_struct/wiki/Kaitai-Struct-stream-API">Kaitai Struct stream API</a>
 * for Java.
 * <p>
 * It provides a wide variety of simple methods to write binary
 * representations of primitive types, such as integer and floating
 * point numbers, byte arrays and strings, and also provides stream
 * positioning / navigation methods with unified cross-language and
 * cross-toolkit semantics.
 * <p>
 * This is abstract class, which serves as an interface description and
 * a few default method implementations, which are believed to be common
 * for all (or at least most) implementations. Different implementations
 * of this interface may provide way to write data to local files,
 * in-memory buffers or arrays, remote files, network streams, etc.
 * <p>
 * Typically, end users won't access any of these Kaitai Stream classes
 * manually, but would describe a binary structure format using .ksy language
 * and then would use Kaitai Struct compiler to generate source code in
 * desired target language.  That code, in turn, would use this class
 * and API to do the actual parsing job.
 */
public abstract class KaitaiOutputStream implements Closeable {

    protected int bitsLeft = 0;
    protected long bits = 0;

    @Override
    abstract public void close() throws IOException;

    public abstract void saveAndClose() throws IOException;

    //region Stream positioning

    /**
     * Check if stream pointer is at the end of stream.
     *
     * @return true if we are located at the end of the stream
     */
    abstract public boolean isEof();

    /**
     * Set stream pointer to designated position (int).
     *
     * @param newPos new position (offset in bytes from the beginning of the stream)
     */
    abstract public void seek(int newPos);

    /**
     * Set stream pointer to designated position (long).
     *
     * @param newPos new position (offset in bytes from the beginning of the stream)
     */
    abstract public void seek(long newPos);

    /**
     * Get current position of a stream pointer.
     *
     * @return pointer position, number of bytes from the beginning of the stream
     */
    abstract public int pos();

    /**
     * Get total size of the stream in bytes.
     *
     * @return size of the stream in bytes
     */
    abstract public long size();

    //endregion

    //region Integer numbers

    //region Signed

    /**
     * Write one signed 1-byte value.
     */
    abstract public void writeS1(byte value);

    //region Big-endian

    abstract public void writeS2be(short value);

    abstract public void writeS4be(int value);

    abstract public void writeS8be(long value);

    //endregion

    //region Little-endian

    abstract public void writeS2le(short value);

    abstract public void writeS4le(int value);

    abstract public void writeS8le(long value);

    //endregion

    //endregion

    //region Unsigned

    abstract public void writeU1(byte value);

    //region Big-endian

    abstract public void writeU2be(short value);

    abstract public void writeU4be(int value);

    /**
     * Writes one unsigned 8-byte integer in big-endian encoding. As Java does not
     * have a primitive data type to accomodate it, we just reuse {@link #writeS8be(long value)}.
     */
    public void writeU8be(long value) {
        writeS8be(value);
    }

    //endregion

    //region Little-endian

    abstract public void writeU2le(short value);

    public void writeU2le(int value) {
        writeU2le((short) value);
    }

    public void writeU2le(long value) {
        writeU2le((short) value);
    }

    abstract public void writeU4le(int value);

    public void writeU4le(long value) {
        writeU4le((int) value);
    }

    /**
     * Writes one unsigned 8-byte integer in little-endian encoding. As Java does not
     * have a primitive data type to accomodate it, we just reuse {@link #writeS8le(long value)}.
     */
    public void writeU8le(long value) {
        writeS8le(value);
    }

    //endregion

    //endregion

    //endregion

    //region Floating point numbers

    //region Big-endian

    abstract void writeF4be(float value);

    abstract void writeF8be(double value);

    //endregion

    //region Little-endian

    abstract void writeF4le(float value);

    abstract void writeF8le(double value);

    //endregion

    //endregion

    //region Unaligned bit values

    public void alignToByte() {
        bits = 0;
        bitsLeft = 0;
    }

    public void writeBitsIntBe(long value, int n) {
        int bitsNeeded = n - bitsLeft;
        if (bitsNeeded > 0) {
            // 1 bit  => 1 byte
            // 8 bits => 1 byte
            // 9 bits => 2 bytes
            int bytesNeeded = ((bitsNeeded - 1) / 8) + 1;
            byte[] buf = longToBytes(value, bytesNeeded);
            for (byte b : buf) {
                bits <<= 8;
                // b is signed byte, convert to unsigned using "& 0xff" trick
                bits |= (b & 0xff);
                bitsLeft += 8;
            }
            writeBytes(buf, bytesNeeded);
        }

        // clear top bits that we've just read => AND with 1s
        bitsLeft -= n;
        long mask = getMaskOnes(bitsLeft);
        bits &= mask;
    }

    /**
     * Unused since Kaitai Struct Compiler v0.9+ - compatibility with older versions
     *
     * @deprecated use {@link #writeBitsIntBe(long, int)} instead
     */
    @Deprecated
    public void writeBitsInt(long value, int n) {
        writeBitsIntBe(value, n);
    }

    public void writeBitsIntLe(long value, int n) {
        int bitsNeeded = n - bitsLeft;
        if (bitsNeeded > 0) {
            // 1 bit  => 1 byte
            // 8 bits => 1 byte
            // 9 bits => 2 bytes
            int bytesNeeded = ((bitsNeeded - 1) / 8) + 1;
            byte[] buf = longToBytes(value, bytesNeeded);
            for (byte b : buf) {
                bits |= ((long) (b & 0xff) << bitsLeft);
                bitsLeft += 8;
            }
            writeBytes(buf, bytesNeeded);
        }

        // raw mask with required number of 1s, starting from lowest bit
        long mask = getMaskOnes(n);
        // derive reading result
        long res = bits & mask;
        // remove bottom bits that we've just read by shifting
        bits >>= n;
        bitsLeft -= n;
    }

    public byte[] longToBytes(long value, int bytesNeeded) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);
        return buffer.array();
    }

    private static long getMaskOnes(int n) {
        if (n == 64) {
            return 0xffffffffffffffffL;
        } else {
            return (1L << n) - 1;
        }
    }

    //endregion

    //region Byte arrays

    /**
     * Writes designated number of bytes to the stream.
     *
     * @param n number of bytes to write
     */
    abstract public void writeBytes(byte[] array, long n);

    /**
     * Writes all the remaining bytes in a stream as byte array.
     */
    abstract public void writeBytesFull(byte[] array);

    abstract public void writeBytesTerm(byte[] array, int term, boolean includeTerm, boolean consumeTerm, boolean eosError);

    public static byte[] bytesStripRight(byte[] bytes, byte padByte) {
        int newLen = bytes.length;
        while (newLen > 0 && bytes[newLen - 1] == padByte) {
            newLen--;
        }
        return Arrays.copyOf(bytes, newLen);
    }

    public static byte[] bytesTerminate(byte[] bytes, byte term, boolean includeTerm) {
        int newLen = 0;
        int maxLen = bytes.length;
        while (newLen < maxLen && bytes[newLen] != term) {
            newLen++;
        }
        if (includeTerm && newLen < maxLen) {
            newLen++;
        }
        return Arrays.copyOf(bytes, newLen);
    }

    /**
     * Checks if supplied number of bytes is a valid number of elements for Java
     * byte array: converts it to int, if it is, or throws an exception if it is not.
     *
     * @param n number of bytes for byte array as long
     * @return number of bytes, converted to int
     */
    protected int toByteArrayLength(long n) {
        if (n > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(
                    "Java byte arrays can be indexed only up to 31 bits, but " + n + " size was requested"
            );
        }
        if (n < 0) {
            throw new IllegalArgumentException(
                    "Byte array size can't be negative, but " + n + " size was requested"
            );
        }
        return (int) n;
    }

    //endregion

    //region Byte array processing

    /**
     * Performs a XOR processing with given data, XORing every byte of input with a single
     * given value.
     *
     * @param data data to process
     * @param key  value to XOR with
     * @return processed data
     */
    public static byte[] processXor(byte[] data, int key) {
        int dataLen = data.length;
        byte[] r = new byte[dataLen];
        for (int i = 0; i < dataLen; i++) {
            r[i] = (byte) (data[i] ^ key);
        }
        return r;
    }

    /**
     * Performs a XOR processing with given data, XORing every byte of input with a key
     * array, repeating key array many times, if necessary (i.e. if data array is longer
     * than key array).
     *
     * @param data data to process
     * @param key  array of bytes to XOR with
     * @return processed data
     */
    public static byte[] processXor(byte[] data, byte[] key) {
        int dataLen = data.length;
        int valueLen = key.length;

        byte[] r = new byte[dataLen];
        int j = 0;
        for (int i = 0; i < dataLen; i++) {
            r[i] = (byte) (data[i] ^ key[j]);
            j = (j + 1) % valueLen;
        }
        return r;
    }

    /**
     * Performs a circular left rotation shift for a given buffer by a given amount of bits,
     * using groups of groupSize bytes each time. Right circular rotation should be performed
     * using this procedure with corrected amount.
     *
     * @param data      source data to process
     * @param amount    number of bits to shift by
     * @param groupSize number of bytes per group to shift
     * @return copy of source array with requested shift applied
     */
    public static byte[] processRotateLeft(byte[] data, int amount, int groupSize) {
        byte[] r = new byte[data.length];
        if (groupSize == 1) {
            for (int i = 0; i < data.length; i++) {
                byte bits = data[i];
                // http://stackoverflow.com/a/19181827/487064
                r[i] = (byte) (((bits & 0xff) << amount) | ((bits & 0xff) >>> (8 - amount)));
            }
        } else {
            throw new UnsupportedOperationException("unable to rotate group of " + groupSize + " bytes yet");
        }
        return r;
    }

    private final static int ZLIB_BUF_SIZE = 4096;

    /**
     * Performs an unpacking ("inflation") of zlib-compressed data with usual zlib headers.
     *
     * @param data data to unpack
     * @return unpacked data
     * @throws RuntimeException if data can't be decoded
     */
    //TODO
    public static byte[] processZlib(byte[] data) {
        Inflater ifl = new Inflater();
        ifl.setInput(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[ZLIB_BUF_SIZE];
        while (!ifl.finished()) {
            try {
                int decBytes = ifl.inflate(buf);
                baos.write(buf, 0, decBytes);
            } catch (DataFormatException e) {
                throw new RuntimeException(e);
            }
        }
        ifl.end();
        return baos.toByteArray();
    }

    //endregion

    //region Misc runtime operations

    /**
     * Performs modulo operation between two integers: dividend `a`
     * and divisor `b`. Divisor `b` is expected to be positive. The
     * result is always 0 &lt;= x &lt;= b - 1.
     *
     * @param a dividend
     * @param b divisor
     * @return result
     */
    public static int mod(int a, int b) {
        if (b <= 0) {
            throw new ArithmeticException("mod divisor <= 0");
        }
        int r = a % b;
        if (r < 0) {
            r += b;
        }
        return r;
    }

    /**
     * Performs modulo operation between two integers: dividend `a`
     * and divisor `b`. Divisor `b` is expected to be positive. The
     * result is always 0 &lt;= x &lt;= b - 1.
     *
     * @param a dividend
     * @param b divisor
     * @return result
     */
    public static long mod(long a, long b) {
        if (b <= 0) {
            throw new ArithmeticException("mod divisor <= 0");
        }
        long r = a % b;
        if (r < 0) {
            r += b;
        }
        return r;
    }

    /**
     * Compares two byte arrays in lexicographical order. Makes extra effort
     * to compare bytes properly, as *unsigned* bytes, i.e. [0x90] would be
     * greater than [0x10].
     *
     * @param a first byte array to compare
     * @param b second byte array to compare
     * @return negative number if a &lt; b, 0 if a == b, positive number if a &gt; b
     * @see Comparable#compareTo(Object)
     */
    public static int byteArrayCompare(byte[] a, byte[] b) {
        if (a == b) {
            return 0;
        }
        int al = a.length;
        int bl = b.length;
        int minLen = Math.min(al, bl);
        for (int i = 0; i < minLen; i++) {
            int cmp = (a[i] & 0xff) - (b[i] & 0xff);
            if (cmp != 0)
                return cmp;
        }

        // Reached the end of at least one of the arrays
        if (al == bl) {
            return 0;
        } else {
            return al - bl;
        }
    }

    /**
     * Finds the minimal byte in a byte array, treating bytes as
     * unsigned values.
     *
     * @param b byte array to scan
     * @return minimal byte in byte array as integer
     */
    public static int byteArrayMin(byte[] b) {
        int min = Integer.MAX_VALUE;
        for (byte item : b) {
            int value = item & 0xff;
            if (value < min)
                min = value;
        }
        return min;
    }

    /**
     * Finds the maximal byte in a byte array, treating bytes as
     * unsigned values.
     *
     * @param b byte array to scan
     * @return maximal byte in byte array as integer
     */
    public static int byteArrayMax(byte[] b) {
        int max = 0;
        for (byte item : b) {
            int value = item & 0xff;
            if (value > max)
                max = value;
        }
        return max;
    }

    public void writeString(String string) {

        for (byte b : string.getBytes(Charset.forName(Yodesk.getOutputCharset()))) {
            writeS1(b);
        }
    }

    public void writeNullTerminatedString(String string) {

        writeString(string);
        writeS1((byte) 0);
    }

    public void writeNullTerminatedString(String string, int length) {

        writeString(string);
        writeS1((byte) 0);
        for (int i = string.length() + 1; i < length; i++) {
            writeS1((byte) 0);
        }
    }


    //endregion

    /**
     * Exception class for an error that occurs when some fixed content
     * was expected to appear, but actual data read was different.
     *
     * @deprecated Not used anymore in favour of {@code Validation*}-exceptions.
     */
    public static class UnexpectedDataError extends RuntimeException {
        public UnexpectedDataError(byte[] actual, byte[] expected) {
            super(
                    "Unexpected fixed contents: got " + byteArrayToHex(actual) +
                            ", was waiting for " + byteArrayToHex(expected)
            );
        }

        private static String byteArrayToHex(byte[] arr) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(String.format("%02x", arr[i]));
            }
            return sb.toString();
        }
    }

    /**
     * Error that occurs when default endianness should be decided with a
     * switch, but nothing matches (although using endianness expression
     * implies that there should be some positive result).
     */
    public static class UndecidedEndiannessError extends RuntimeException {
    }

    /**
     * Common ancestor for all error originating from Kaitai Struct usage.
     * Stores KSY source path, pointing to an element supposedly guilty of
     * an error.
     */
    public static class KaitaiStructError extends RuntimeException {
        public KaitaiStructError(String msg, String srcPath) {
            super(srcPath + ": " + msg);
            this.srcPath = srcPath;
        }

        protected String srcPath;
    }

    /**
     * Common ancestor for all validation failures. Stores pointer to
     * OutputKaitaiStream IO object which was involved in an error.
     */
    public static class ValidationFailedError extends KaitaiOutputStream.KaitaiStructError {
        public ValidationFailedError(String msg, KaitaiOutputStream io, String srcPath) {
            super("at pos " + io.pos() + ": validation failed: " + msg, srcPath);
            this.io = io;
        }

        protected KaitaiOutputStream io;

        protected static String byteArrayToHex(byte[] arr) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < arr.length; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(String.format("%02x", arr[i]));
            }
            sb.append(']');
            return sb.toString();
        }
    }

    /**
     * Signals validation failure: we required "actual" value to be equal to
     * "expected", but it turned out that it's not.
     */
    public static class ValidationNotEqualError extends KaitaiOutputStream.ValidationFailedError {
        public ValidationNotEqualError(byte[] expected, byte[] actual, KaitaiOutputStream io, String srcPath) {
            super("not equal, expected " + byteArrayToHex(expected) + ", but got " + byteArrayToHex(actual), io, srcPath);
        }

        public ValidationNotEqualError(Object expected, Object actual, KaitaiOutputStream io, String srcPath) {
            super("not equal, expected " + expected + ", but got " + actual, io, srcPath);
        }

        protected Object expected;
        protected Object actual;
    }

    public static class ValidationLessThanError extends KaitaiOutputStream.ValidationFailedError {
        public ValidationLessThanError(byte[] expected, byte[] actual, KaitaiOutputStream io, String srcPath) {
            super("not in range, min " + byteArrayToHex(expected) + ", but got " + byteArrayToHex(actual), io, srcPath);
        }

        public ValidationLessThanError(Object min, Object actual, KaitaiOutputStream io, String srcPath) {
            super("not in range, min " + min + ", but got " + actual, io, srcPath);
        }

        protected Object min;
        protected Object actual;
    }

    public static class ValidationGreaterThanError extends KaitaiOutputStream.ValidationFailedError {
        public ValidationGreaterThanError(byte[] expected, byte[] actual, KaitaiOutputStream io, String srcPath) {
            super("not in range, max " + byteArrayToHex(expected) + ", but got " + byteArrayToHex(actual), io, srcPath);
        }

        public ValidationGreaterThanError(Object max, Object actual, KaitaiOutputStream io, String srcPath) {
            super("not in range, max " + max + ", but got " + actual, io, srcPath);
        }

        protected Object max;
        protected Object actual;
    }

    public static class ValidationNotAnyOfError extends KaitaiOutputStream.ValidationFailedError {
        public ValidationNotAnyOfError(Object actual, KaitaiOutputStream io, String srcPath) {
            super("not any of the list, got " + actual, io, srcPath);
        }

        protected Object actual;
    }

    public static class ValidationExprError extends KaitaiOutputStream.ValidationFailedError {
        public ValidationExprError(Object actual, KaitaiOutputStream io, String srcPath) {
            super("not matching the expression, got " + actual, io, srcPath);
        }

        protected Object actual;
    }
}
